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
