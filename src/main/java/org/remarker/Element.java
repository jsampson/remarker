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

package org.remarker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public final class Element extends Content
{
    private final ElementDefinition definition;
    private final List<Content> contents;
    private final Map<String, Attribute> attributes;

    Element(ElementDefinition definition)
    {
        this.definition = requireNonNull(definition);
        this.contents = new ArrayList<>();
        this.attributes = new LinkedHashMap<>();
    }

    ElementDefinition getDefinition()
    {
        return definition;
    }

    public String getName()
    {
        return definition.lowercase;
    }

    public List<Content> getContents()
    {
        return unmodifiableList(contents);
    }

    public Collection<Attribute> getAttributes()
    {
        return Collections.unmodifiableCollection(attributes.values());
    }

    void addContent(Content content)
    {
        contents.add(content);
    }

    void putAttribute(Attribute attribute)
    {
        attributes.put(attribute.getName(), attribute);
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
                            .filter(element -> childName.equals(element.getName()));

                    if (attributeName != null)
                    {
                        elements = elements.filter(element -> element.attributeStream().anyMatch(
                                attribute -> attributeName.equals(attribute.getName())
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
                            attributeName.equals(attribute.getName())).map(Attribute::getValue).collect(toList()));
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
