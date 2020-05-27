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

/**
 * @see <a href="https://htmx.org/reference">Htmx Reference</a>
 */
@SuppressWarnings("unused")
public final class Htmx
{
    private Htmx()
    {
        // to prevent instantiation
    }

    public static Attribute Boost(Boolean value)
    {
        return Attribute.quotedString("data-hx-boost", value);
    }

    public static Attribute Classes(String value)
    {
        return Attribute.quotedString("data-hx-classes", value);
    }

    public static Attribute Confirm(String value)
    {
        return Attribute.quotedString("data-hx-confirm", value);
    }

    public static Attribute Delete(String value)
    {
        return Attribute.quotedString("data-hx-delete", value);
    }

    public static Attribute ErrorUrl(String value)
    {
        return Attribute.quotedString("data-hx-error-url", value);
    }

    public static Attribute Get(String value)
    {
        return Attribute.quotedString("data-hx-get", value);
    }

    public static Attribute HistoryElt()
    {
        return Attribute.quotedString("data-hx-history-elt", true);
    }

    public static Attribute Include(String value)
    {
        return Attribute.quotedString("data-hx-include", value);
    }

    public static Attribute Indicator(String value)
    {
        return Attribute.quotedString("data-hx-indicator", value);
    }

    public static Attribute Params(String value)
    {
        return Attribute.quotedString("data-hx-params", value);
    }

    public static Attribute Patch(String value)
    {
        return Attribute.quotedString("data-hx-patch", value);
    }

    public static Attribute Post(String value)
    {
        return Attribute.quotedString("data-hx-post", value);
    }

    public static Attribute Prompt(String value)
    {
        return Attribute.quotedString("data-hx-prompt", value);
    }

    public static Attribute PushUrl(Boolean value)
    {
        return Attribute.quotedString("data-hx-push-url", value);
    }

    public static Attribute Put(String value)
    {
        return Attribute.quotedString("data-hx-put", value);
    }

    public static Attribute Select(String value)
    {
        return Attribute.quotedString("data-hx-select", value);
    }

    public static Attribute SseSrc(String value)
    {
        return Attribute.quotedString("data-hx-sse-src", value);
    }

    public static Attribute SwapOob(Boolean value)
    {
        return Attribute.quotedString("data-hx-swap-oob", value);
    }

    public static Attribute Swap(String value)
    {
        return Attribute.quotedString("data-hx-swap", value);
    }

    public static Attribute Target(String value)
    {
        return Attribute.quotedString("data-hx-target", value);
    }

    public static Attribute Trigger(String value)
    {
        return Attribute.quotedString("data-hx-trigger", value);
    }
}
