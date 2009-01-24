package com.krasama.remarker;

import static com.krasama.remarker.AttributeDefinition.Type.*;
import static com.krasama.remarker.SpecificationParser.*;

import java.io.*;
import java.util.*;

import org.jdom.*;

public class HtmlOutputter
{
    private final Writer writer;
    private boolean atStartOfLine = true;
    private int indentLevel = 0;

    public HtmlOutputter(Writer writer)
    {
        this.writer = writer;
    }

    public void output(Content content) throws IOException
    {
        if (content instanceof Element)
        {
            output((Element) content);
        }
        else if (content instanceof Text)
        {
            output((Text) content);
        }
        else
        {
            throw new UnsupportedOperationException(content.getClass().getCanonicalName());
        }
    }

    public void output(Element element) throws IOException
    {
        ElementDefinition elementDefinition = ELEMENTS.get(element.getName());
        boolean newLinesOutside = !elementDefinition.inline;
        boolean newLinesInside = !elementDefinition.inline && hasNonInlineContents(element.getContent());
        if (newLinesOutside)
        {
            newLine();
        }
        raw('<');
        raw(elementDefinition.uppercase);
        for (Object attribute : element.getAttributes())
        {
            output((Attribute) attribute, elementDefinition);
        }
        if (elementDefinition.empty)
        {
            raw(">");
        }
        else
        {
            raw('>');
            if (newLinesInside)
            {
                indentLevel++;
                newLine();
            }
            for (Object content : element.getContent())
            {
                output((Content) content);
            }
            if (newLinesInside)
            {
                newLine();
                indentLevel--;
            }
            raw("</");
            raw(elementDefinition.uppercase);
            raw('>');
        }
        if (newLinesOutside)
        {
            newLine();
        }
    }

    private boolean hasNonInlineContents(List<?> children)
    {
        for (Object child : children)
        {
            if (child instanceof Element)
            {
                Element element = (Element) child;
                ElementDefinition elementDefinition = ELEMENTS.get(element.getName());
                if (!elementDefinition.inline || hasNonInlineContents(element.getContent()))
                {
                    return true;
                }
            }
            else if (child instanceof Text)
            {
                Text text = (Text) child;
                if (text.getText().indexOf('\r') != -1 || text.getText().indexOf('\r') != -1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void output(Text text) throws IOException
    {
        escape(text.getText(), false);
    }

    private void output(Attribute attribute, ElementDefinition elementDefinition) throws IOException
    {
        AttributeDefinition attributeDefinition = ATTRIBUTES.get(attribute.getName());
        raw(" ");
        raw(attributeDefinition.name);
        if (attributeDefinition.typesByElement.get(elementDefinition.lowercase) != BOOLEAN)
        {
            raw("=\"");
            escape(attribute.getValue(), true);
            raw('"');
        }
    }

    private void raw(String string) throws IOException
    {
        if (string.length() != 0)
        {
            writeIndent();
            writer.write(string);
            atStartOfLine = false;
        }
    }

    private void raw(char character) throws IOException
    {
        writeIndent();
        writer.write(character);
        atStartOfLine = false;
    }

    private void writeIndent() throws IOException
    {
        if (atStartOfLine && indentLevel > 0)
        {
            for (int i = 0; i < indentLevel; i++)
            {
                writer.write("  ");
            }
            atStartOfLine = false;
        }
    }

    private void newLine() throws IOException
    {
        if (!atStartOfLine)
        {
            writer.write("\r\n");
            atStartOfLine = true;
        }
    }

    private void escape(String string, boolean inAttributeValue) throws IOException
    {
        for (int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            switch (c)
            {
            case '<':
                raw("&lt;");
                continue;
            case '>':
                raw("&gt;");
                continue;
            case '&':
                raw("&amp;");
                continue;
            case '\r':
            case '\n':
                if (inAttributeValue)
                {
                    raw("&#");
                    raw(Integer.toString(c));
                    raw(";");
                }
                else
                {
                    newLine();
                }
                continue;
            case '"':
                if (inAttributeValue)
                {
                    raw("&quot;");
                    continue;
                }
            default:
                if (c >= ' ' && c <= '~')
                {
                    raw(c);
                }
                else
                {
                    raw("&#");
                    raw(Integer.toString(c));
                    raw(";");
                }
                continue;
            }
        }
    }
}
