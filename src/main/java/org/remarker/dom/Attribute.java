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

import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public final class Attribute
{
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-z]+(?:-[a-z]+)*");

    public static Attribute simple(String name, String value)
    {
        if (value == null)
        {
            return null;
        }
        else
        {
            return new Attribute(name, value, AttributeTypeFunction.STRING);
        }
    }

    public static Attribute simple(String name, Boolean value)
    {
        if (Boolean.TRUE.equals(value))
        {
            return new Attribute(name, name, AttributeTypeFunction.BOOLEAN);
        }
        else
        {
            return null;
        }
    }

    private final String name;
    private final String value;
    private final AttributeTypeFunction typeFunction;

    public Attribute(String name, String value, AttributeTypeFunction typeFunction)
    {
        if (name != null && !NAME_PATTERN.matcher(name).matches())
        {
            throw new IllegalArgumentException("Attribute name should be lowercase with hyphens: " + name);
        }

        this.name = requireNonNull(name);
        this.value = requireNonNull(value);
        this.typeFunction = requireNonNull(typeFunction);
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public AttributeType getType(String elementName)
    {
        return typeFunction.getType(elementName);
    }
}
