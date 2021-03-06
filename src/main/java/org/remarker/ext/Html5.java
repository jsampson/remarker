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

package org.remarker.ext;

import org.remarker.dom.Attribute;
import org.remarker.dom.Element;

import static org.remarker.dom.BreakStyle.*;
import static org.remarker.dom.ContentModel.*;

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

    public static Element EMBED(Object... contents)
    {
        return new Element("EMBED", BLOCK, VOID, contents);
    }

    public static Element SOURCE(Object... contents)
    {
        return new Element("SOURCE", BLOCK, VOID, contents);
    }

    public static Element TEMPLATE(Object... contents)
    {
        return new Element("TEMPLATE", BLOCK, MIXED, contents);
    }

    public static Element TRACK(Object... contents)
    {
        return new Element("TRACK", BLOCK, VOID, contents);
    }

    public static Element WBR(Object... contents)
    {
        return new Element("WBR", INLINE, VOID, contents);
    }

    public static Attribute Autofocus(Boolean value)
    {
        return Attribute.traditionalBoolean("autofocus", value);
    }

    public static Attribute Form(String value)
    {
        return Attribute.quotedString("form", value);
    }

    public static Attribute Max(String value)
    {
        return Attribute.quotedString("max", value);
    }

    public static Attribute Min(String value)
    {
        return Attribute.quotedString("min", value);
    }

    public static Attribute Pattern(String value)
    {
        return Attribute.quotedString("pattern", value);
    }

    public static Attribute Placeholder(String value)
    {
        return Attribute.quotedString("placeholder", value);
    }

    public static Attribute Required(Boolean value)
    {
        return Attribute.traditionalBoolean("required", value);
    }
}
