package org.remarker;

import java.util.List;

import static org.remarker.AttributeDefinition.Type.BOOLEAN;
import static org.remarker.SpecificationParser.ATTRIBUTES;
import static org.remarker.SpecificationParser.ELEMENTS;

public final class HtmlOutputter<X extends Exception>
{
    @FunctionalInterface
    public interface BufferConsumer<X extends Exception>
    {
        void accept(char[] buffer, int offset, int length) throws X;
    }

    private final BufferConsumer<X> consumer;
    private boolean nothingWritten = true;
    private boolean atStartOfLine = true;
    private int indentLevel = 0;
    private char[] buffer = new char[8192];
    private int index = 0;

    public HtmlOutputter(BufferConsumer<X> consumer) throws X
    {
        this.consumer = consumer;
    }

    public void output(Object... contents) throws X
    {
        for (Content content : Html.asHtml(contents))
        {
            dispatch(content);
        }
        flush();
    }

    private void dispatch(Content content) throws X
    {
        if (content instanceof Element)
        {
            element((Element) content);
        }
        else
        {
            text((Text) content);
        }
    }

    private void element(Element element) throws X
    {
        ElementDefinition elementDefinition = ELEMENTS.get(element.getName());
        if (elementDefinition == null)
        {
            throw new IllegalArgumentException("The '" + element.getName() + "' element is not allowed");
        }
        if (element.getName().equals("html") && nothingWritten)
        {
            append("<!DOCTYPE HTML>\r\n");
        }
        boolean newLinesOutside = !elementDefinition.inline;
        boolean newLinesInside = !elementDefinition.inline && hasNonInlineContents(element.getContents());
        if (newLinesOutside)
        {
            newLine();
        }
        raw('<');
        raw(elementDefinition.uppercase);
        for (Attribute attribute : element.getAttributes())
        {
            attribute(attribute, elementDefinition);
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
            for (Content content : element.getContents())
            {
                dispatch(content);
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

    private boolean hasNonInlineContents(List<Content> children)
    {
        for (Content child : children)
        {
            if (child instanceof Element)
            {
                Element element = (Element) child;
                ElementDefinition elementDefinition = ELEMENTS.get(element.getName());
                if (!elementDefinition.inline || hasNonInlineContents(element.getContents()))
                {
                    return true;
                }
            }
            else if (child instanceof Text)
            {
                Text text = (Text) child;
                if (hasLineBreaks(text.getValue()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasLineBreaks(String text)
    {
        return text.indexOf('\r') != -1 || text.indexOf('\n') != -1;
    }

    private void text(Text text) throws X
    {
        escape(text.getValue(), false);
    }

    private void attribute(Attribute attribute, ElementDefinition elementDefinition) throws X
    {
        AttributeDefinition attributeDefinition = ATTRIBUTES.get(attribute.getName());
        AttributeDefinition.Type attributeType =
                attributeDefinition == null ? null : attributeDefinition.getType(elementDefinition.lowercase);
        if (attributeType == null && !attribute.isExtended())
        {
            throw new IllegalArgumentException("The '" + attribute.getName() + "' attribute is not allowed for the '" +
                    elementDefinition.lowercase + "' element");
        }
        raw(" ");
        raw(attribute.getName());
        if (attributeType != BOOLEAN)
        {
            raw("=\"");
            escape(attribute.getValue(), true);
            raw('"');
        }
    }

    private void raw(String string) throws X
    {
        if (string.length() != 0)
        {
            writeIndent();
            append(string);
            atStartOfLine = false;
        }
    }

    private void raw(char character) throws X
    {
        writeIndent();
        append(character);
        atStartOfLine = false;
    }

    private void writeIndent() throws X
    {
        if (atStartOfLine && indentLevel > 0)
        {
            for (int i = 0; i < indentLevel; i++)
            {
                append("  ");
            }
            atStartOfLine = false;
        }
    }

    private void newLine() throws X
    {
        if (!atStartOfLine)
        {
            append("\r\n");
            atStartOfLine = true;
        }
    }

    private void append(String s) throws X
    {
        int n = s.length();
        for (int i = 0; i < n; i++)
        {
            append(s.charAt(i));
        }
    }

    private void append(char c) throws X
    {
        nothingWritten = false;
        buffer[index++] = c;
        if (index == buffer.length)
        {
            flush();
        }
    }

    private void flush() throws X
    {
        if (index > 0)
        {
            consumer.accept(buffer, 0, index);
            index = 0;
        }
    }

    private void escape(String string, boolean inAttributeValue) throws X
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

    private void numericCharacterReference(int c) throws X
    {
        raw("&#");
        raw(Integer.toString(c));
        raw(";");
    }
}
