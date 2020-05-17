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

class ElementDefinition
{
    static final ElementDefinition CONTAINER = new ElementDefinition();

    final String lowercase;
    final String uppercase;
    final boolean inline;
    final boolean empty;

    ElementDefinition(String name, boolean inline, boolean empty)
    {
        this.lowercase = name.toLowerCase().intern();
        this.uppercase = name.toUpperCase().intern();
        this.inline = inline;
        this.empty = empty;
    }

    private ElementDefinition()
    {
        this.lowercase = null;
        this.uppercase = null;
        this.inline = true;
        this.empty = false;
    }

    boolean isContainer()
    {
        return lowercase == null;
    }

    void generateCode()
    {
        if (isContainer())
        {
            throw new IllegalStateException();
        }
        System.out.println("    public static Element " + uppercase + "(Object... contents)");
        System.out.println("    {");
        System.out.println("        return element(\"" + lowercase + "\", contents);");
        System.out.println("    }");
    }
}
