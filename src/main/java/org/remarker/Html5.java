package org.remarker;

import static org.remarker.Html.element;
import static org.remarker.Html.extendedAttribute;

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

    private static final ElementDefinition TEMPLATE = new ElementDefinition("template", false, false);

    public static Element TEMPLATE(Object... contents)
    {
        return element(TEMPLATE, contents);
    }

    public static Attribute Autofocus(Boolean value)
    {
        return extendedAttribute("autofocus", value);
    }

    public static Attribute Form(String value)
    {
        return extendedAttribute("form", value);
    }

    public static Attribute Max(String value)
    {
        return extendedAttribute("max", value);
    }

    public static Attribute Min(String value)
    {
        return extendedAttribute("min", value);
    }

    public static Attribute Pattern(String value)
    {
        return extendedAttribute("pattern", value);
    }

    public static Attribute Placeholder(String value)
    {
        return extendedAttribute("placeholder", value);
    }

    public static Attribute Required(Boolean value)
    {
        return extendedAttribute("required", value);
    }
}
