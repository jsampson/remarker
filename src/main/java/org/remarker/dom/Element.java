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
import static org.remarker.dom.BreakStyle.*;
import static org.remarker.dom.ContentModel.*;

public final class Element extends Content
{
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z]+[0-9]?");

    private final String name;
    private final BreakStyle breakStyle;
    private final ContentModel contentModel;
    private final List<Content> contents;
    private final List<Attribute> attributes;

    public Element(String name, boolean inline, boolean empty, Object... contents)
    {
        this(name, name.equals("PRE") ? PRE : inline ? INLINE : BLOCK, empty ? VOID : MIXED, contents);
    }

    public Element(String name, BreakStyle breakStyle, ContentModel contentModel, Object... contents)
    {
        if (!NAME_PATTERN.matcher(requireNonNull(name, "name")).matches())
        {
            throw new IllegalArgumentException("Element name should be uppercase: " + name);
        }

        requireNonNull(breakStyle, "breakStyle");
        requireNonNull(contentModel, "contentModel");

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

        if (contentModel == ContentModel.VOID && !processedContents.isEmpty())
        {
            throw new IllegalArgumentException("Void element must not have contents: " + name);
        }

        this.name = name;
        this.breakStyle = breakStyle;
        this.contentModel = contentModel;
        this.contents = unmodifiableList(processedContents);
        this.attributes = unmodifiableList(new ArrayList<>(processedAttributes.values()));
    }

    public String getName()
    {
        return name;
    }

    public BreakStyle getBreakStyle()
    {
        return breakStyle;
    }

    public ContentModel getContentModel()
    {
        return contentModel;
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
