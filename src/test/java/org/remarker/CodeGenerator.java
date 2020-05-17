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

import java.util.*;

public class CodeGenerator
{
    public static void main(String[] args)
    {
        System.out.println("    // BEGIN GENERATED CODE");
        System.out.println();

        Map<String, ElementDefinition> elements = SpecificationParser.ELEMENTS;
        for (ElementDefinition element : elements.values())
        {
            element.generateCode();
            System.out.println();
        }

        Map<String, AttributeDefinition> attributes = SpecificationParser.ATTRIBUTES;
        for (AttributeDefinition attribute : attributes.values())
        {
            attribute.generateCode();
            System.out.println();
        }

        Map<String, CharacterDefinition> characters = SpecificationParser.CHARACTERS;
        for (CharacterDefinition character : characters.values())
        {
            character.generateCode();
        }

        System.out.println();
        System.out.println("    // END GENERATED CODE");
    }
}
