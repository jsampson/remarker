package org.remarker;

import java.util.*;

class AttributeDefinition
{
    enum Type
    {
        STRING, BOOLEAN, NUMBER
    }

    final String name;
    final Map<String, Type> typesByElement;
    final Set<Type> allTypes;
    final Type loosestType;

    AttributeDefinition(String name, Map<String, Type> typesByElement)
    {
        this.name = name.toLowerCase().intern();
        this.typesByElement = Collections.unmodifiableMap(new HashMap<>(typesByElement));
        this.allTypes = Collections.unmodifiableSet(new HashSet<>(typesByElement.values()));
        if (allTypes.isEmpty())
        {
            throw new IllegalArgumentException("Impossible! Attribute must have at least one type");
        }
        if (allTypes.contains(Type.BOOLEAN) && allTypes.size() != 1)
        {
            throw new IllegalArgumentException("Boolean attributes are always only boolean");
        }
        if (allTypes.contains(Type.STRING))
        {
            this.loosestType = Type.STRING;
        }
        else if (allTypes.contains(Type.NUMBER))
        {
            this.loosestType = Type.NUMBER;
        }
        else
        {
            this.loosestType = Type.BOOLEAN;
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
        if (allTypes.contains(Type.BOOLEAN))
        {
            System.out.println();
            generateMethod("Boolean");
        }
        if (allTypes.contains(Type.NUMBER))
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

    Type getType(String elementName)
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
