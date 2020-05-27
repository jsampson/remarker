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

class CharacterDefinition
{
    final String name;
    final char value;

    CharacterDefinition(String name, char value)
    {
        this.name = name.intern();
        this.value = value;
    }

    String javaName()
    {
        return "_" + name;
    }

    char charValue()
    {
        return value;
    }

    void generateCode()
    {
        System.out.println("    public static final String " + javaName() + " = String.valueOf((char) " + (int) charValue() + ");");
        if (name.endsWith("sp"))
        {
            // ensp, emsp, nbsp, thinsp
            String duplicated = javaName();
            for (int n = 2; n <= 8; n++)
            {
                duplicated += " + " + javaName();
                System.out.println("    public static final String " + javaName() + "_" + n + " = " + duplicated + ";");
            }
        }
    }
}
