package com.krasama.remarker;

import static com.krasama.remarker.AttributeDefinition.Type.*;
import static com.krasama.remarker.ElementDefinition.DTD.*;

import java.util.*;

import junit.framework.*;

import com.krasama.remarker.AttributeDefinition.*;
import com.krasama.remarker.ElementDefinition.*;

public class SpecificationParserTest extends TestCase
{
    public void testCharacters() throws Exception
    {
        Map<String, CharacterDefinition> characters = SpecificationParser
                .parseCharacters();
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

    private void checkCharacter(Map<String, CharacterDefinition> characters,
            String name, char value)
    {
        CharacterDefinition character = characters.get(name);
        assertSame(name, character.name);
        assertEquals(value, character.value);
    }

    public void testElements() throws Exception
    {
        Map<String, ElementDefinition> elements = SpecificationParser
                .parseElements();
        assertEquals(91, elements.size());
        checkElement(elements, "a", "A", false, STRICT);
        checkElement(elements, "applet", "APPLET", false, LOOSE);
        checkElement(elements, "base", "BASE", true, STRICT);
        checkElement(elements, "isindex", "ISINDEX", true, LOOSE);
        checkElement(elements, "noframes", "NOFRAMES", false, FRAMESET);
        checkElement(elements, "var", "VAR", false, STRICT);
    }

    private void checkElement(Map<String, ElementDefinition> elements,
            String lowercase, String uppercase, boolean empty, DTD dtd)
    {
        ElementDefinition element = elements.get(lowercase);
        assertSame(lowercase, element.lowercase);
        assertSame(uppercase, element.uppercase);
        assertEquals(empty, element.empty);
        assertSame(dtd, element.dtd);
    }

    public void testAttributes() throws Exception
    {
        Map<String, AttributeDefinition> attributes = SpecificationParser
                .parseAttributes();
        assertEquals(119, attributes.size());
        checkAttribute(attributes, "abbr", new String[] { "td", "th" },
                new Type[] { STRING, STRING }, new DTD[] { STRICT, STRICT });
        checkAttribute(attributes, "border", new String[] { "table", "img",
                "object" }, new Type[] { STRING, STRING, STRING }, new DTD[] {
                STRICT, LOOSE, LOOSE });
        checkAttribute(attributes, "checked", new String[] { "input" },
                new Type[] { BOOLEAN }, new DTD[] { STRICT });
        checkAttribute(attributes, "cols", new String[] { "frameset",
                "textarea" }, new Type[] { STRING, NUMBER }, new DTD[] {
                FRAMESET, STRICT });
        checkAttribute(attributes, "colspan", new String[] { "td", "th" },
                new Type[] { NUMBER, NUMBER }, new DTD[] { STRICT, STRICT });
        checkAttribute(attributes, "class", new String[] { "*", "base",
                "basefont", "head", "html", "meta", "param", "script", "style",
                "title" }, new Type[] { STRING }, new DTD[] { STRICT });
        checkAttribute(attributes, "dir", new String[] { "*", "bdo", "applet",
                "base", "basefont", "br", "frame", "frameset", "iframe",
                "param", "script" }, new Type[] { STRING, STRING }, new DTD[] {
                STRICT, STRICT });
    }

    private void checkAttribute(Map<String, AttributeDefinition> attributes,
            String name, String[] elements, Type[] types, DTD[] dtds)
    {
        AttributeDefinition attribute = attributes.get(name);
        assertSame(name, attribute.name);
        assertEquals(elements.length, attribute.typesByElement.size());
        assertEquals(elements.length, attribute.dtdsByElement.size());
        for (int i = 0; i < elements.length; i++)
        {
            String element = elements[i];
            assertTrue(attribute.typesByElement.containsKey(element));
            assertTrue(attribute.dtdsByElement.containsKey(element));
            if (i < types.length)
            {
                assertSame(types[i], attribute.typesByElement.get(element));
                assertSame(dtds[i], attribute.dtdsByElement.get(element));
            }
            else
            {
                assertNull(attribute.typesByElement.get(element));
                assertNull(attribute.dtdsByElement.get(element));
            }
        }
    }
}
