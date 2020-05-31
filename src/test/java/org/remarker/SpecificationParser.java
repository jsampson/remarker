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

import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.xpath.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.remarker.dom.BreakStyle;
import org.remarker.dom.ContentModel;
import org.xml.sax.InputSource;

import static java.util.Arrays.*;
import static javax.xml.xpath.XPathConstants.NODESET;
import static org.remarker.AttributeType.*;
import static org.remarker.dom.BreakStyle.*;
import static org.remarker.dom.ContentModel.*;

final class SpecificationParser
{
    static final Map<String, CharacterDefinition> CHARACTERS;
    static final Map<String, ElementDefinition> ELEMENTS;
    static final Map<String, AttributeDefinition> ATTRIBUTES;

    static
    {
        try
        {
            CHARACTERS = Collections.unmodifiableMap(parseCharacters());
            ELEMENTS = Collections.unmodifiableMap(parseElements());
            ATTRIBUTES = Collections.unmodifiableMap(parseAttributes());
        }
        catch (Exception impossible)
        {
            throw new ExceptionInInitializerError(impossible);
        }
    }

    private static Map<String, CharacterDefinition> parseCharacters() throws Exception
    {
        Map<String, CharacterDefinition> characters = new TreeMap<>();
        characters.put("apos", new CharacterDefinition("apos", '\''));
        Pattern pattern = Pattern.compile("<!ENTITY ([A-Za-z0-9]+) +CDATA \"&#([0-9]+);\"");
        NodeList nodes = load("characters.html", "//pre");
        for (int index = 0; index < nodes.getLength(); index++)
        {
            Element pre = (Element) nodes.item(index);
            String text = pre.getTextContent();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find())
            {
                String name = matcher.group(1);
                String code = matcher.group(2);
                char value = (char) Integer.parseInt(code);
                characters.put(name, new CharacterDefinition(name, value));
            }
        }
        return characters;
    }

    private static Map<String, ElementDefinition> parseElements() throws Exception
    {
        // definition of "inline" elements from the HTML 4.01 DTD
        Set<String> fontstyle = new HashSet<>(asList("TT", "I", "B", "BIG", "SMALL"));
        Set<String> phrase = new HashSet<>(asList("EM", "STRONG", "DFN", "CODE", "SAMP", "KBD", "VAR", "CITE", "ABBR",
                "ACRONYM"));
        Set<String> special = new HashSet<>(asList("A", "IMG", "OBJECT", "BR", "SCRIPT", "MAP", "Q", "SUB", "SUP", "SPAN",
                "BDO"));
        Set<String> formctrl = new HashSet<>(asList("INPUT", "SELECT", "TEXTAREA", "LABEL", "BUTTON"));

        Set<String> inline = new HashSet<>();
        inline.addAll(fontstyle);
        inline.addAll(phrase);
        inline.addAll(special);
        inline.addAll(formctrl);
        // remove BR and SCRIPT so that they are formatted on their own lines
        inline.remove("BR");
        inline.remove("SCRIPT");

        Set<String> prestyle = new HashSet<>(asList("PRE", "TEXTAREA"));
        Set<String> rawtext = new HashSet<>(asList("SCRIPT", "STYLE"));
        Set<String> escapablerawtext = new HashSet<>(asList("TEXTAREA", "TITLE"));

        Map<String, ElementDefinition> elements = new TreeMap<>();
        NodeList nodes = load("elements.html", "//tr[td[1]/@title='Name']");
        for (int index = 0; index < nodes.getLength(); index++)
        {
            Element tr = (Element) nodes.item(index);
            String name = getCellValue(tr, 0);
            String empty = getCellValue(tr, 3);
            String dtd = getCellValue(tr, 5);
            if (isStrict(dtd))
            {
                BreakStyle breakStyle = prestyle.contains(name) ? BreakStyle.valueOf(name)
                        : inline.contains(name) ? INLINE : BLOCK;
                ContentModel contentModel = empty.equals("E") ? VOID
                        : rawtext.contains(name) ? RAW_TEXT
                        : escapablerawtext.contains(name) ? ESCAPABLE_RAW_TEXT
                        : MIXED;
                elements.put(name.toLowerCase().intern(), new ElementDefinition(name, breakStyle, contentModel));
            }
        }
        return elements;
    }

    private static Map<String, AttributeDefinition> parseAttributes() throws Exception
    {
        Map<String, AttributeDefinition> attributes = new TreeMap<>();
        NodeList nodes = load("attributes.html", "//tr[td[1]/@title='Name']");
        for (int index = 0; index < nodes.getLength(); index++)
        {
            Element tr = (Element) nodes.item(index);
            String name = getCellValue(tr, 0).toLowerCase().intern();
            String elements = getCellValue(tr, 1);
            String typeCode = getCellValue(tr, 2);
            String dtdCode = getCellValue(tr, 5);
            if (!isStrict(dtdCode))
            {
                continue;
            }
            AttributeType type = typeCode.equals("(" + name + ")") ? BOOLEAN : typeCode.equals("NUMBER") ? NUMBER : STRING;
            EnumSet<AttributeType> types = EnumSet.of(type);
            if (attributes.containsKey(name))
            {
                AttributeDefinition oldDefinition = attributes.get(name);
                types.addAll(oldDefinition.allTypes);
            }
            attributes.put(name, new AttributeDefinition(name, types));
        }
        return attributes;
    }

    private static NodeList load(String filename, String xpath) throws Exception
    {
        try (InputStream in = SpecificationParser.class.getResourceAsStream("/org/remarker/" + filename))
        {
            return (NodeList) XPathFactory.newInstance().newXPath().evaluate(xpath, new InputSource(in), NODESET);
        }
    }

    private static String getCellValue(Element tr, int i)
    {
        NodeList children = tr.getChildNodes();
        int elementIndex = -1;
        for (int childIndex = 0; childIndex < children.getLength(); childIndex++)
        {
            if (children.item(childIndex) instanceof Element)
            {
                elementIndex++;
                if (elementIndex == i)
                {
                    return children.item(childIndex).getTextContent().trim();
                }
            }
        }
        throw new NoSuchElementException();
    }

    private static boolean isStrict(String dtd)
    {
        return !dtd.equals("L") && !dtd.equals("F");
    }
}
