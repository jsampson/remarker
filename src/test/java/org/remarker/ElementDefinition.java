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

import org.remarker.dom.BreakStyle;
import org.remarker.dom.ContentModel;

class ElementDefinition
{
    final String uppercase;
    final BreakStyle breakStyle;
    final ContentModel contentModel;

    ElementDefinition(String name, BreakStyle breakStyle, ContentModel contentModel)
    {
        this.uppercase = name.toUpperCase().intern();
        this.breakStyle = breakStyle;
        this.contentModel = contentModel;
    }

    void generateCode()
    {
        System.out.printf("%n");
        System.out.printf("    public static Element %s(Object... contents)%n", uppercase);
        System.out.printf("    {%n");
        System.out.printf("        return new Element(\"%s\", %s, %s, contents);%n", uppercase, breakStyle, contentModel);
        System.out.printf("    }%n");
    }
}
