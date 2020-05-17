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

import static org.remarker.AttributeDefinition.Type.*;

import java.util.*;

import junit.framework.*;

import org.remarker.AttributeDefinition.*;

public class SpecificationParserTest extends TestCase
{
    public void testCharacters()
    {
        Map<String, CharacterDefinition> characters = SpecificationParser.CHARACTERS;
        assertEquals(253, characters.size());
        // all standard XML characters
        checkCharacter(characters, "lt", '<');
        checkCharacter(characters, "gt", '>');
        checkCharacter(characters, "quot", '"');
        checkCharacter(characters, "apos", '\'');
        checkCharacter(characters, "amp", '&');
        // sampling of "ISO 8859-1 characters"
        checkCharacter(characters, "nbsp", '\u00A0');
        checkCharacter(characters, "reg", '\u00AE');
        checkCharacter(characters, "deg", '\u00B0');
        checkCharacter(characters, "yuml", '\u00FF');
        // sampling of "symbols, mathematical symbols, and Greek letters"
        checkCharacter(characters, "fnof", (char) 402);
        checkCharacter(characters, "Alpha", (char) 913);
        checkCharacter(characters, "pi", (char) 960);
        checkCharacter(characters, "trade", (char) 8482);
        checkCharacter(characters, "diams", (char) 9830);
        // sampling of "markup-significant and internationalization characters"
        checkCharacter(characters, "OElig", (char) 338);
        checkCharacter(characters, "oelig", (char) 339);
        checkCharacter(characters, "ndash", (char) 8211);
        checkCharacter(characters, "mdash", (char) 8212);
        checkCharacter(characters, "euro", (char) 8364);
    }

    private void checkCharacter(Map<String, CharacterDefinition> characters, String name, char value)
    {
        CharacterDefinition character = characters.get(name);
        assertSame(name, character.name);
        assertEquals(value, character.value);
    }

    public void testElements()
    {
        Map<String, ElementDefinition> elements = SpecificationParser.ELEMENTS;
        assertEquals(77, elements.size());
        checkElement(elements, "a", "A", false);
        checkNoElement(elements, "applet");
        checkElement(elements, "base", "BASE", true);
        checkNoElement(elements, "isindex");
        checkNoElement(elements, "iframe");
        checkNoElement(elements, "noframes");
        checkElement(elements, "var", "VAR", false);
    }

    private void checkElement(Map<String, ElementDefinition> elements, String lowercase, String uppercase, boolean empty)
    {
        ElementDefinition element = elements.get(lowercase);
        assertSame(lowercase, element.lowercase);
        assertSame(uppercase, element.uppercase);
        assertEquals(empty, element.empty);
    }

    private void checkNoElement(Map<String, ElementDefinition> elements, String lowercase)
    {
        assertNull(elements.get(lowercase));
    }

    public void testAttributes()
    {
        Map<String, AttributeDefinition> attributes = SpecificationParser.ATTRIBUTES;
        assertEquals(93, attributes.size());
        checkAttribute(attributes, "abbr", new String[] { "td", "th" }, new Type[] { STRING, STRING });
        checkAttribute(attributes, "border", new String[] { "table" }, new Type[] { STRING });
        checkAttribute(attributes, "checked", new String[] { "input" }, new Type[] { BOOLEAN });
        checkAttribute(attributes, "cols", new String[] { "textarea" }, new Type[] { NUMBER });
        checkAttribute(attributes, "colspan", new String[] { "td", "th" }, new Type[] { NUMBER, NUMBER });
        checkAttribute(attributes, "class", new String[] { "*", "base", "basefont", "head", "html", "meta", "param", "script",
                "style", "title" }, new Type[] { STRING });
        checkAttribute(attributes, "dir", new String[] { "*", "bdo", "applet", "base", "basefont", "br", "frame", "frameset",
                "iframe", "param", "script" }, new Type[] { STRING, STRING });
    }

    private void checkAttribute(Map<String, AttributeDefinition> attributes, String name, String[] elements, Type[] types)
    {
        AttributeDefinition attribute = attributes.get(name);
        assertSame(name, attribute.name);
        assertEquals(elements.length, attribute.typesByElement.size());
        for (int i = 0; i < elements.length; i++)
        {
            String element = elements[i];
            assertTrue(attribute.typesByElement.containsKey(element));
            if (i < types.length)
            {
                assertSame(types[i], attribute.typesByElement.get(element));
            }
            else
            {
                assertNull(attribute.typesByElement.get(element));
            }
        }
    }
}
