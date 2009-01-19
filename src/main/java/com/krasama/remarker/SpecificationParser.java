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
    public static Map<String, CharacterDefinition> parseCharacters() throws Exception
    {
        Map<String, CharacterDefinition> characters = new TreeMap<String, CharacterDefinition>();
        characters.put("apos", new CharacterDefinition("apos", '\''));
        Pattern pattern = Pattern.compile("<!ENTITY ([A-Za-z0-9]+) +CDATA \"&#([0-9]+);\"");
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

    public static Map<String, ElementDefinition> parseElements() throws Exception
    {
        Map<String, ElementDefinition> elements = new TreeMap<String, ElementDefinition>();
        Document document = load("elements.html");
        List<?> nodes = XPath.selectNodes(document, "//tr[td[1]/@title='Name']");
        for (Object node : nodes)
        {
            Element tr = (Element) node;
            String name = getCellValue(tr, 0);
            String empty = getCellValue(tr, 3);
            String dtd = getCellValue(tr, 5);
            elements.put(name.toLowerCase().intern(), new ElementDefinition(name, empty.equals("E"), parseDTD(dtd)));
        }
        return elements;
    }

    public static Map<String, AttributeDefinition> parseAttributes() throws Exception
    {
        String elementNamesRegex = "([A-Z0-9]+(,[ \n\r]+[A-Z0-9]+)*)";
        Pattern elementNamesPattern = Pattern.compile("^" + elementNamesRegex + "$");
        Pattern allButElementNamesPattern = Pattern.compile("^All elements but " + elementNamesRegex + "$");
        Map<String, AttributeDefinition> attributes = new TreeMap<String, AttributeDefinition>();
        Document document = load("attributes.html");
        List<?> nodes = XPath.selectNodes(document, "//tr[td[1]/@title='Name']");
        for (Object node : nodes)
        {
            Element tr = (Element) node;
            String name = getCellValue(tr, 0).toLowerCase().intern();
            String elements = getCellValue(tr, 1);
            String typeCode = getCellValue(tr, 2);
            String dtdCode = getCellValue(tr, 5);
            Type type = typeCode.equals("(" + name + ")") ? BOOLEAN : typeCode.equals("NUMBER") ? NUMBER : STRING;
            DTD dtd = parseDTD(dtdCode);
            Map<String, Type> typesByElement = new HashMap<String, Type>();
            Map<String, DTD> dtdsByElement = new HashMap<String, DTD>();
            if (attributes.containsKey(name))
            {
                AttributeDefinition oldDefinition = attributes.get(name);
                typesByElement.putAll(oldDefinition.typesByElement);
                dtdsByElement.putAll(oldDefinition.dtdsByElement);
            }
            Matcher elementNamesMatcher = elementNamesPattern.matcher(elements);
            Matcher allButElementNamesMatcher = allButElementNamesPattern.matcher(elements);
            if (elementNamesMatcher.matches())
            {
                putTypeAndDTD(elementNamesMatcher, typesByElement, dtdsByElement, type, dtd);
            }
            else if (allButElementNamesMatcher.matches())
            {
                typesByElement.put("*", type);
                dtdsByElement.put("*", dtd);
                putTypeAndDTD(allButElementNamesMatcher, typesByElement, dtdsByElement, null, null);
            }
            else
            {
                throw new IllegalStateException(elements);
            }
            attributes.put(name, new AttributeDefinition(name, typesByElement, dtdsByElement));
        }
        return attributes;
    }

    private static Document load(String filename) throws Exception
    {
        InputStream in = SpecificationParser.class.getResourceAsStream("/com/krasama/remarker/" + filename);
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

    private static String getCellValue(Element tr, int i)
    {
        return ((Element) tr.getChildren().get(i)).getValue().trim();
    }

    private static DTD parseDTD(String dtd)
    {
        return dtd.equals("L") ? LOOSE : dtd.equals("F") ? FRAMESET : STRICT;
    }

    private static void putTypeAndDTD(Matcher elementNamesMatcher, Map<String, Type> typesByElement,
            Map<String, DTD> dtdsByElement, Type type, DTD dtd)
    {
        String[] elementNames = getElementNames(elementNamesMatcher);
        for (String elementName : elementNames)
        {
            String elementNameInterned = elementName.toLowerCase().intern();
            typesByElement.put(elementNameInterned, type);
            dtdsByElement.put(elementNameInterned, dtd);
        }
    }

    private static String[] getElementNames(Matcher elementNamesMatcher)
    {
        return elementNamesMatcher.group(1).split(",[ \n\r]+");
    }
}
