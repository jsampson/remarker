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
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public abstract class Node
{
    Node()
    {
        // to prevent extension outside of package
    }

    static void processContents(Object[] contents, Consumer<Content> addContent, Consumer<Attribute> addAttribute)
    {
        new ContentProcessor(addContent, addAttribute).processContent(contents);
    }

    private static class ContentProcessor
    {
        private final Consumer<Content> addContent;
        private final Consumer<Attribute> addAttribute;

        ContentProcessor(Consumer<Content> addContent, Consumer<Attribute> addAttribute)
        {
            this.addContent = addContent;
            this.addAttribute = addAttribute;
        }

        void processContent(Object content)
        {
            if (content == null)
            {
                return;
            }
            else if (content instanceof String)
            {
                addContent.accept(new Text((String) content));
            }
            else if (content instanceof Content)
            {
                addContent.accept((Content) content);
            }
            else if (content instanceof Fragment)
            {
                ((Fragment) content).getContents().forEach(addContent);
            }
            else if (content instanceof Attribute)
            {
                addAttribute.accept((Attribute) content);
            }
            else if (content instanceof Object[])
            {
                for (Object item : (Object[]) content)
                {
                    processContent(item);
                }
            }
            else if (content instanceof Iterable)
            {
                ((Iterable<?>) content).forEach(this::processContent);
            }
            else if (content instanceof Iterator)
            {
                ((Iterator<?>) content).forEachRemaining(this::processContent);
            }
            else if (content instanceof Optional)
            {
                ((Optional<?>) content).ifPresent(this::processContent);
            }
            else if (content instanceof Stream)
            {
                ((Stream<?>) content).forEach(this::processContent);
            }
            else if (content instanceof Supplier)
            {
                processContent(((Supplier<?>) content).get());
            }
            else if (content instanceof Enum || content instanceof CharSequence || content instanceof Boolean ||
                    content instanceof Character || content instanceof Number)
            {
                addContent.accept(new Text(content.toString()));
            }
            else
            {
                throw new IllegalArgumentException(content.getClass().getCanonicalName());
            }
        }
    }

    private static final Pattern XPATH_PART_PATTERN = Pattern.compile("([A-Za-z-]+|[*.])"
            + "(?:\\[(?:(?:text\\(\\)|@([A-Za-z-]+))=(?:'([^']*)'|\"([^\"]*)\")|@([A-Za-z-]+))\\])?"
            + "(//?)");
    private static final Pattern XPATH_LAST_PART_PATTERN = Pattern.compile("text\\(\\)|@([A-Za-z-]+)");

    public final List<String> evaluateXPath(String xpath)
    {
        Matcher partMatcher = XPATH_PART_PATTERN.matcher(xpath);
        Stream<? extends Node> nodes = Stream.of(this);

        while (partMatcher.lookingAt())
        {
            String childName = partMatcher.group(1);
            String attributeName = partMatcher.group(2);
            String expectedValue = partMatcher.group(3) != null ? partMatcher.group(3) : partMatcher.group(4);
            String presentAttributeName = partMatcher.group(5);
            String pathSeparator = partMatcher.group(6);
            partMatcher.region(partMatcher.end(), partMatcher.regionEnd());

            if ("*".equals(childName))
            {
                nodes = nodes.flatMap(Node::childStream);
            }
            else if (!".".equals(childName))
            {
                nodes = nodes.flatMap(Node::childStream).filter(child -> childName.equalsIgnoreCase(child.getName()));
            }

            if (attributeName != null)
            {
                nodes = nodes.filter(node -> node.attributeStream().anyMatch(
                        attribute -> attributeName.equalsIgnoreCase(attribute.getName())
                                && expectedValue.equals(attribute.getValue())));
            }
            else if (expectedValue != null)
            {
                nodes = nodes.filter(node -> expectedValue.equals(node.getAllText()));
            }
            else if (presentAttributeName != null)
            {
                nodes = nodes.filter(node -> node.attributeStream().anyMatch(
                        attribute -> presentAttributeName.equalsIgnoreCase(attribute.getName())));
            }

            if ("//".equals(pathSeparator))
            {
                nodes = nodes.flatMap(Node::selfAndDescendentsStream);
            }
        }

        Matcher lastPartMatcher = XPATH_LAST_PART_PATTERN.matcher(xpath).region(partMatcher.regionStart(), partMatcher.regionEnd());
        if (lastPartMatcher.matches())
        {
            String attributeName = lastPartMatcher.group(1);
            if (attributeName != null)
            {
                return unmodifiableList(nodes.flatMap(Node::attributeStream).filter(attribute ->
                        attributeName.equalsIgnoreCase(attribute.getName())).map(Attribute::getValue).collect(toList()));
            }
            else
            {
                return unmodifiableList(nodes.map(Node::getAllText).collect(toList()));
            }
        }
        else
        {
            throw new IllegalArgumentException("XPath not supported: <" + xpath + ">");
        }
    }

    public final String getAllText()
    {
        StringBuilder builder = new StringBuilder();
        appendTextTo(builder);
        return builder.toString();
    }

    abstract void appendTextTo(StringBuilder builder);

    abstract Stream<Element> childStream();

    abstract Stream<Attribute> attributeStream();

    private Stream<Node> selfAndDescendentsStream()
    {
        return Stream.concat(Stream.of(this), childStream().flatMap(Node::selfAndDescendentsStream));
    }
}
