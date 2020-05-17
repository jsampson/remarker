/*
 * Copyright 2009-2020 by Justin T. Sampson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
