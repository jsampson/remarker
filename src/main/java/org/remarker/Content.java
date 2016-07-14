package org.remarker;

public abstract class Content
{
    Content()
    {
        // to prevent extension outside of package
    }

    abstract void appendTextTo(StringBuilder builder);
}
