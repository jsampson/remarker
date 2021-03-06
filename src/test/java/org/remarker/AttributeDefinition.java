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

class AttributeDefinition
{
    final String name;
    final Set<AttributeType> allTypes;

    AttributeDefinition(String name, EnumSet<AttributeType> types)
    {
        this.name = name.toLowerCase().intern();
        this.allTypes = Collections.unmodifiableSet(types.clone());
        if (allTypes.isEmpty())
        {
            throw new IllegalArgumentException("Impossible! Attribute must have at least one type");
        }
        if (allTypes.contains(AttributeType.BOOLEAN) && allTypes.size() != 1)
        {
            throw new IllegalArgumentException("Boolean attributes are always only boolean");
        }
    }

    String javaName()
    {
        String[] words = name.split("-");
        StringBuilder result = new StringBuilder();
        for (String word : words)
        {
            result.append(word.substring(0, 1).toUpperCase());
            result.append(word.substring(1));
        }
        return result.toString();
    }

    String xmlName()
    {
        return name;
    }

    void generateCode()
    {
        if (allTypes.contains(AttributeType.STRING))
        {
            System.out.println();
            System.out.println("    public static Attribute " + javaName() + "(String value)");
            System.out.println("    {");
            System.out.println("        return Attribute.quotedString(\"" + xmlName() + "\", value);");
            System.out.println("    }");
        }
        if (allTypes.contains(AttributeType.BOOLEAN))
        {
            System.out.println();
            System.out.println("    public static Attribute " + javaName() + "()");
            System.out.println("    {");
            System.out.println("        return Attribute.traditionalBoolean(\"" + xmlName() + "\", true);");
            System.out.println("    }");
            System.out.println();
            System.out.println("    public static Attribute " + javaName() + "(Boolean value)");
            System.out.println("    {");
            System.out.println("        return Attribute.traditionalBoolean(\"" + xmlName() + "\", value);");
            System.out.println("    }");
        }
        if (allTypes.contains(AttributeType.NUMBER))
        {
            System.out.println();
            System.out.println("    public static Attribute " + javaName() + "(Integer value)");
            System.out.println("    {");
            System.out.println("        return Attribute.quotedString(\"" + xmlName() + "\", value);");
            System.out.println("    }");
        }
    }
}
