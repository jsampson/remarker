/*
 * Copyright 2009-2020 by Justin T. Sampson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.remarker.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public final class Element extends Content
{
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z]+[0-9]?");

    private final String name;
    private final boolean inline;
    private final boolean empty;
    private final List<Content> contents;
    private final Map<String, Attribute> attributes;

    public Element(String name, boolean inline, boolean empty, Object... contents)
    {
        if (name != null && !NAME_PATTERN.matcher(name).matches())
        {
            throw new IllegalArgumentException("Element name should be uppercase: " + name);
        }

        this.name = name;
        this.inline = inline;
        this.empty = empty;
        this.contents = new ArrayList<>();
        this.attributes = new LinkedHashMap<>();

        addContents(contents);

        if (empty && !this.contents.isEmpty())
        {
            throw new IllegalArgumentException("Empty element must not have contents: " + name);
        }
    }

    private void addContents(Object[] contents)
    {
        for (Object content : contents)
        {
            addContent(content);
        }
    }

    private void addContents(Iterable<?> contents)
    {
        for (Object content : contents)
        {
            addContent(content);
        }
    }

    private void addContents(Iterator<?> contents)
    {
        while (contents.hasNext())
        {
            addContent(contents.next());
        }
    }

    private void addContent(Object content)
    {
        if (content == null)
        {
            return;
        }
        else if (content instanceof String)
        {
            this.contents.add(new Text((String) content));
        }
        else if (content instanceof Content)
        {
            this.contents.add((Content) content);
        }
        else if (content instanceof Attribute)
        {
            Attribute attribute = (Attribute) content;
            if (isFragment())
            {
                throw new IllegalArgumentException(
                        "Attribute '" + attribute.getName() + "' must be contained in an element");
            }
            else
            {
                this.attributes.put(attribute.getName(), attribute);
            }
        }
        else if (content.getClass().isArray() && !content.getClass().getComponentType().isPrimitive())
        {
            addContents((Object[]) content);
        }
        else if (content instanceof Iterable)
        {
            addContents((Iterable<?>) content);
        }
        else if (content instanceof Iterator)
        {
            addContents((Iterator<?>) content);
        }
        else if (content instanceof Optional)
        {
            ((Optional<?>) content).ifPresent(value -> addContent(value));
        }
        else if (content instanceof Stream)
        {
            ((Stream<?>) content).forEach(value -> addContent(value));
        }
        else if (content instanceof Supplier)
        {
            addContent(((Supplier<?>) content).get());
        }
        else if (content instanceof Enum || content instanceof CharSequence || content instanceof Boolean ||
                content instanceof Character || content instanceof Number)
        {
            this.contents.add(new Text(content.toString()));
        }
        else
        {
            throw new IllegalArgumentException(content.getClass().getCanonicalName());
        }
    }

    public boolean isFragment()
    {
        return name == null;
    }

    public boolean isInline()
    {
        return inline;
    }

    public boolean isEmpty()
    {
        return empty;
    }

    public String getName()
    {
        return name;
    }

    public List<Content> getContents()
    {
        return unmodifiableList(contents);
    }

    public Collection<Attribute> getAttributes()
    {
        return unmodifiableCollection(attributes.values());
    }

    private static final Pattern XPATH_PART_PATTERN =
            Pattern.compile("([A-Za-z-]+)(?:\\[(?:text\\(\\)|@([A-Za-z-]+))='([^']*)'\\])?");
    private static final Pattern XPATH_LAST_PART_PATTERN = Pattern.compile("text\\(\\)|@([A-Za-z-]+)");

    public List<String> evaluateXPath(String xpath)
    {
        EVALUATE:
        {
            String[] parts = xpath.split("/", -1);
            Stream<Element> elements = Stream.of(this);

            for (int i = 0; i < parts.length - 1; i++)
            {
                Matcher partMatcher = XPATH_PART_PATTERN.matcher(parts[i]);
                if (partMatcher.matches())
                {
                    String childName = partMatcher.group(1);
                    String attributeName = partMatcher.group(2);
                    String expectedValue = partMatcher.group(3);

                    elements = elements.flatMap(Element::childStream)
                            .filter(element -> childName.equalsIgnoreCase(element.getName()));

                    if (attributeName != null)
                    {
                        elements = elements.filter(element -> element.attributeStream().anyMatch(
                                attribute -> attributeName.equalsIgnoreCase(attribute.getName())
                                        && expectedValue.equals(attribute.getValue())));
                    }
                    else if (expectedValue != null)
                    {
                        elements = elements.filter(element -> expectedValue.equals(element.getAllText()));
                    }
                }
                else
                {
                    break EVALUATE;
                }
            }

            Matcher lastPartMatcher = XPATH_LAST_PART_PATTERN.matcher(parts[parts.length - 1]);
            if (lastPartMatcher.matches())
            {
                String attributeName = lastPartMatcher.group(1);
                if (attributeName != null)
                {
                    return unmodifiableList(elements.flatMap(Element::attributeStream).filter(attribute ->
                            attributeName.equalsIgnoreCase(attribute.getName())).map(Attribute::getValue).collect(toList()));
                }
                else
                {
                    return unmodifiableList(elements.map(Element::getAllText).collect(toList()));
                }
            }
        }
        throw new IllegalArgumentException("XPath not supported: <" + xpath + ">");
    }

    public String getAllText()
    {
        StringBuilder builder = new StringBuilder();
        appendTextTo(builder);
        return builder.toString();
    }

    @Override
    void appendTextTo(StringBuilder builder)
    {
        contents.forEach(content -> content.appendTextTo(builder));
    }

    private Stream<Element> childStream()
    {
        return contents.stream().filter(Element.class::isInstance).map(Element.class::cast);
    }

    private Stream<Attribute> attributeStream()
    {
        return attributes.values().stream();
    }
}
