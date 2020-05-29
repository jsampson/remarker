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
    private final List<Attribute> attributes;

    public Element(String name, boolean inline, boolean empty, Object... contents)
    {
        if (!NAME_PATTERN.matcher(requireNonNull(name, "name")).matches())
        {
            throw new IllegalArgumentException("Element name should be uppercase: " + name);
        }

        List<Content> processedContents = new ArrayList<>();
        Map<String, Attribute> processedAttributes = new LinkedHashMap<>();

        processContents(contents, processedContents::add, attribute -> {
            if (processedAttributes.containsKey(attribute.getName()))
            {
                throw new IllegalArgumentException("Duplicate attribute '" + attribute.getName() + "' in element '" + name + "'");
            }
            else
            {
                processedAttributes.put(attribute.getName(), attribute);
            }
        });

        if (empty && !processedContents.isEmpty())
        {
            throw new IllegalArgumentException("Empty element must not have contents: " + name);
        }

        this.name = name;
        this.inline = inline;
        this.empty = empty;
        this.contents = unmodifiableList(processedContents);
        this.attributes = unmodifiableList(new ArrayList<>(processedAttributes.values()));
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
        return contents;
    }

    public List<Attribute> getAttributes()
    {
        return attributes;
    }

    @Override
    void appendTextTo(StringBuilder builder)
    {
        contents.forEach(content -> content.appendTextTo(builder));
    }

    @Override
    Stream<Element> childStream()
    {
        return contents.stream().filter(Element.class::isInstance).map(Element.class::cast);
    }

    @Override
    Stream<Attribute> attributeStream()
    {
        return attributes.stream();
    }
}
