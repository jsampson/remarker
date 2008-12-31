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

    public AttributeDefinition(String name, Map<String, Type> typesByElement, Map<String, DTD> dtdsByElement)
    {
        this.name = name.toLowerCase().intern();
        this.typesByElement = Collections.unmodifiableMap(new HashMap<String, Type>(typesByElement));
        this.dtdsByElement = Collections.unmodifiableMap(new HashMap<String, DTD>(dtdsByElement));
    }
}
