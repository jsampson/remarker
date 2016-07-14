package org.remarker;

public final class Attribute
{
    private final String name;
    private final String value;
    private final boolean extended;

    Attribute(String name, String value)
    {
        this(name, value, false);
    }

    Attribute(String name, String value, boolean extended)
    {
        this.name = name;
        this.value = value;
        this.extended = extended;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    boolean isExtended()
    {
        return extended;
    }
}
