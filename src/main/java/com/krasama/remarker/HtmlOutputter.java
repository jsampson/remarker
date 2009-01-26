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

    public void output(Object... contents) throws IOException
    {
        for (Content content : Html.asHtml(contents))
        {
            dispatch(content);
        }
    }

    private void dispatch(Content content) throws IOException
    {
        if (content instanceof Element)
        {
            element((Element) content);
        }
        else if (content instanceof Text)
        {
            text((Text) content);
        }
        else
        {
            throw new UnsupportedOperationException(content.getClass().getCanonicalName());
        }
    }

    private void element(Element element) throws IOException
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
            attribute((Attribute) attribute, elementDefinition);
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
                dispatch((Content) content);
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

    private void text(Text text) throws IOException
    {
        escape(text.getText(), false);
    }

    private void attribute(Attribute attribute, ElementDefinition elementDefinition) throws IOException
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
        int n = string.length();
        for (int i = 0; i < n; i++)
        {
            int c = string.charAt(i);
            int d = (i < n - 1) ? string.charAt(i + 1) : -1;
            switch (c)
            {
            case '<':
                raw("&lt;");
                break;
            case '>':
                raw("&gt;");
                break;
            case '&':
                raw("&amp;");
                break;
            case '"':
                if (inAttributeValue)
                {
                    raw("&quot;");
                }
                else
                {
                    raw('"');
                }
                break;
            case '\r':
            case '\n':
                if (inAttributeValue)
                {
                    numericCharacterReference(c);
                }
                else
                {
                    newLine();
                }
                break;
            default:
                if (c >= ' ' && c <= '~')
                {
                    raw((char) c);
                }
                else if (c >= 0xD800 && c <= 0xDBFF && d >= 0xDC00 && d <= 0xDFFF)
                {
                    int highPart = c - 0xD800;
                    int lowPart = d - 0xDC00;
                    int character = (highPart << 10) + lowPart + 0x10000;
                    numericCharacterReference(character);
                    i++;
                }
                else
                {
                    numericCharacterReference(c);
                }
                break;
            }
        }
    }

    private void numericCharacterReference(int c) throws IOException
    {
        raw("&#");
        raw(Integer.toString(c));
        raw(";");
    }
}
