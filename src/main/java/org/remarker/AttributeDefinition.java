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
import org.remarker.dom.AttributeType;

class AttributeDefinition
{
    final String name;
    final Map<String, AttributeType> typesByElement;
    final Set<AttributeType> allTypes;

    AttributeDefinition(String name, Map<String, AttributeType> typesByElement)
    {
        this.name = name.toLowerCase().intern();
        this.typesByElement = Collections.unmodifiableMap(new HashMap<>(typesByElement));
        this.allTypes = Collections.unmodifiableSet(new HashSet<>(typesByElement.values()));
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
        generateMethod("String");
        if (allTypes.contains(AttributeType.BOOLEAN))
        {
            System.out.println();
            generateMethod("Boolean");
        }
        if (allTypes.contains(AttributeType.NUMBER))
        {
            System.out.println();
            generateMethod("Integer");
        }
    }

    private void generateMethod(String valueType)
    {
        System.out.println("    public static Attribute " + javaName() + "(" + valueType + " value)");
        System.out.println("    {");
        System.out.println("        return attribute(\"" + xmlName() + "\", value);");
        System.out.println("    }");
    }

    AttributeType getType(String elementName)
    {
        if (typesByElement.containsKey(elementName))
        {
            return typesByElement.get(elementName);
        }
        else
        {
            return typesByElement.get("*");
        }
    }
}
