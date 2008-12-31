package com.krasama.remarker;

public class ElementDefinition
{
    public static enum DTD
    {
        STRICT, LOOSE, FRAMESET
    }

    public final String lowercase;
    public final String uppercase;
    public final boolean empty;
    public final DTD dtd;

    public ElementDefinition(String name, boolean empty, DTD dtd)
    {
        this.lowercase = name.toLowerCase().intern();
        this.uppercase = name.toUpperCase().intern();
        this.empty = empty;
        this.dtd = dtd;
    }
}
