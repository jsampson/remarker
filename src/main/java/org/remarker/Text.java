package org.remarker;

import static java.util.Objects.requireNonNull;

public final class Text extends Content
{
    private final String value;

    Text(String value)
    {
        this.value = requireNonNull(value);
    }

    public String getValue()
    {
        return value;
    }

    @Override
    void appendTextTo(StringBuilder builder)
    {
        builder.append(value);
    }
}
