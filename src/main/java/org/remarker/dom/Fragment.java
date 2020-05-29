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
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;

public final class Fragment extends Node
{
    private final List<Content> contents;

    public Fragment(Object... contents)
    {
        List<Content> processedContents = new ArrayList<>();

        processContents(contents, processedContents::add, attribute -> {
            throw new IllegalArgumentException(
                    "Attribute '" + attribute.getName() + "' must be contained in an element");
        });

        this.contents = unmodifiableList(processedContents);
    }

    public List<Content> getContents()
    {
        return contents;
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
        return Stream.empty();
    }
}
