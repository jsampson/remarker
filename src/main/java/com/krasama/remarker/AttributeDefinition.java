package com.krasama.remarker;

import java.util.*;

import com.krasama.remarker.ElementDefinition.*;

public class AttributeDefinition
{
    public static enum Type
    {
        STRING, BOOLEAN, NUMBER
    }

    public final String name;
    public final Map<String, Type> typesByElement;
    public final Map<String, DTD> dtdsByElement;
    public final Set<Type> allTypes;
    public final Type loosestType;

    public AttributeDefinition(String name, Map<String, Type> typesByElement, Map<String, DTD> dtdsByElement)
    {
        this.name = name.toLowerCase().intern();
        this.typesByElement = Collections.unmodifiableMap(new HashMap<String, Type>(typesByElement));
        this.dtdsByElement = Collections.unmodifiableMap(new HashMap<String, DTD>(dtdsByElement));
        this.allTypes = Collections.unmodifiableSet(new HashSet<Type>(typesByElement.values()));
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

    public String javaName()
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

    public String xmlName()
    {
        return name;
    }

    public void generateCode()
    {
        generateMethod("String", true);
        if (allTypes.contains(Type.BOOLEAN))
        {
            System.out.println();
            generateMethod("Boolean", false);
        }
        if (allTypes.contains(Type.NUMBER))
        {
            System.out.println();
            generateMethod("Integer", false);
        }
    }

    private void generateMethod(String valueType, boolean withType)
    {
        String typeParam = withType ? ", " + loosestType : "";
        System.out.println("    public static Attribute " + javaName() + "(" + valueType + " value)");
        System.out.println("    {");
        System.out.println("        return attribute(\"" + xmlName() + "\", value" + typeParam + ");");
        System.out.println("    }");
    }
}
