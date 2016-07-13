package org.remarker;

public final class Text extends Content
{
    private final String value;

    Text(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
