package org.remarker;

import static java.util.Objects.requireNonNull;

public final class Attribute
{
    private final String name;
    private final String value;
    private final AttributeTypeFunction typeFunction;

    Attribute(String name, String value, AttributeTypeFunction typeFunction)
    {
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

    AttributeDefinition.Type getType(ElementDefinition elementDefinition)
    {
        return typeFunction.getType(elementDefinition.lowercase);
    }
}
