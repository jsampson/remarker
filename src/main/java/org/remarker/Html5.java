package org.remarker;

/**
 * @see <a href="https://www.w3.org/TR/html5-diff/">HTML5 Differences from HTML4</a>
 */
@SuppressWarnings("unused")
public final class Html5
{
    private Html5()
    {
        // to prevent instantiation
    }

    public static Attribute Autofocus(Boolean value)
    {
        return Boolean.TRUE.equals(value) ? attribute("autofocus", "autofocus") : null;
    }

    public static Attribute Form(String value)
    {
        return attribute("form", value);
    }

    public static Attribute Placeholder(String value)
    {
        return attribute("placeholder", value);
    }

    private static Attribute attribute(String name, String value)
    {
        if (value == null)
        {
            return null;
        }
        else
        {
            return new Attribute(name, value, true);
        }
    }
}
