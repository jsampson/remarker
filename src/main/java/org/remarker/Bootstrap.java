package org.remarker;

import static org.remarker.Html.extendedAttribute;

public final class Bootstrap
{
    private Bootstrap()
    {
        // to prevent instantiation
    }

    public static Attribute AriaExpanded(String value)
    {
        return extendedAttribute("aria-expanded", value);
    }

    public static Attribute AriaHidden(String value)
    {
        return extendedAttribute("aria-hidden", value);
    }

    public static Attribute DataParent(String value)
    {
        return extendedAttribute("data-parent", value);
    }

    public static Attribute DataTarget(String value)
    {
        return extendedAttribute("data-target", value);
    }

    public static Attribute DataToggle(String value)
    {
        return extendedAttribute("data-toggle", value);
    }
}
