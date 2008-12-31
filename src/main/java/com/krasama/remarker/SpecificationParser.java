package com.krasama.remarker;

import static com.krasama.remarker.AttributeDefinition.Type.*;
import static com.krasama.remarker.ElementDefinition.DTD.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.xpath.*;

import com.krasama.remarker.AttributeDefinition.*;
import com.krasama.remarker.ElementDefinition.*;

public class SpecificationParser
{
    private static Document load(String filename) throws Exception
    {
        InputStream in = SpecificationParser.class
                .getResourceAsStream("/com/krasama/remarker/" + filename);
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            return document;
        }
        finally
        {
            in.close();
        }
    }

    public static Map<String, CharacterDefinition> parseCharacters()
            throws Exception
    {
        Map<String, CharacterDefinition> characters = new HashMap<String, CharacterDefinition>();
        characters.put("apos", new CharacterDefinition("apos", '\''));
        Pattern pattern = Pattern
                .compile("<!ENTITY ([A-Za-z0-9]+) +CDATA \"&#([0-9]+);\"");
        Document document = load("characters.html");
        List<?> nodes = XPath.selectNodes(document, "//pre");
        for (Object node : nodes)
        {
            Element pre = (Element) node;
            String text = pre.getText();
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

    public static Map<String, ElementDefinition> parseElements()
            throws Exception
    {
        HashMap<String, ElementDefinition> elements = new HashMap<String, ElementDefinition>();
        Document document = load("elements.html");
        List<?> nodes = XPath
                .selectNodes(document, "//tr[td[1]/@title='Name']");
        for (Object node : nodes)
        {
            Element tr = (Element) node;
            List<?> children = tr.getChildren();
            String name = ((Element) children.get(0)).getValue().trim();
            String empty = ((Element) children.get(3)).getValue().trim();
            String dtd = ((Element) children.get(5)).getValue().trim();
            elements.put(name.toLowerCase().intern(), new ElementDefinition(
                    name, empty.equals("E"), dtd.equals("L") ? LOOSE : dtd
                            .equals("F") ? FRAMESET : STRICT));
        }
        return elements;
    }

    public static Map<String, AttributeDefinition> parseAttributes()
            throws Exception
    {
        // <td title="Name"><a
        // href="../struct/tables.html#adef-abbr">abbr</a></td>
        // <td align="center" title="Related Elements"><a href=
        // "../struct/tables.html#edef-TD" class="noxref">TD</a>, <a href=
        // "../struct/tables.html#edef-TH" class="noxref">TH</a></td>
        // <td align="center" title="Type"><a
        // href="../sgml/dtd.html#Text">%Text;</a></td>
        // <td align="center" title="Default">#IMPLIED</td>
        // <td align="center" title="Depr.">&#xA0;</td>
        // <td align="center" title="DTD">&#xA0;</td>
        // <td align="center" title="Comment">abbreviation for header cell</td>

        HashMap<String, AttributeDefinition> attributes = new HashMap<String, AttributeDefinition>();
        Document document = load("attributes.html");
        List<?> nodes = XPath
                .selectNodes(document, "//tr[td[1]/@title='Name']");
        for (Object node : nodes)
        {
            Element tr = (Element) node;
            List<?> children = tr.getChildren();
            String name = ((Element) children.get(0)).getValue().trim()
                    .toLowerCase().intern();
            String elements = ((Element) children.get(1)).getValue().trim();
            String typeCode = ((Element) children.get(2)).getValue().trim();
            Type type = typeCode.equals("(" + name + ")") ? BOOLEAN : typeCode
                    .equals("NUMBER") ? NUMBER : STRING;
            String dtdCode = ((Element) children.get(5)).getValue().trim();
            DTD dtd = dtdCode.equals("L") ? LOOSE
                    : dtdCode.equals("F") ? FRAMESET : STRICT;
            Map<String, Type> typesByElement;
            Map<String, DTD> dtdsByElement;
            if (attributes.containsKey(name))
            {
                AttributeDefinition oldDefinition = attributes.get(name);
                typesByElement = new HashMap<String, Type>(
                        oldDefinition.typesByElement);
                dtdsByElement = new HashMap<String, DTD>(
                        oldDefinition.dtdsByElement);
            }
            else
            {
                typesByElement = new HashMap<String, Type>();
                dtdsByElement = new HashMap<String, DTD>();
            }
            if (elements.matches("^[A-Z0-9]+(,[ \n\r]+[A-Z0-9]+)*$"))
            {
                String[] elementNames = elements.split(",[ \n\r]+");
                for (String elementName : elementNames)
                {
                    typesByElement
                            .put(elementName.toLowerCase().intern(), type);
                    dtdsByElement.put(elementName.toLowerCase().intern(), dtd);
                }
            }
            else if (elements
                    .matches("^All elements but [A-Z0-9]+(,[ \n\r]+[A-Z0-9]+)*$"))
            {
                String[] elementNames = elements.substring(
                        "All elements but ".length()).split(",[ \n\r]+");
                typesByElement.put("*", type);
                dtdsByElement.put("*", dtd);
                for (String elementName : elementNames)
                {
                    typesByElement
                            .put(elementName.toLowerCase().intern(), null);
                    dtdsByElement.put(elementName.toLowerCase().intern(), null);
                }

            }
            else
            {
                throw new IllegalStateException(elements);
            }
            attributes.put(name, new AttributeDefinition(name, typesByElement,
                    dtdsByElement));
        }
        return attributes;
    }
}
