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

import java.util.List;
import org.remarker.dom.*;

import static org.remarker.dom.AttributeType.BOOLEAN;

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
    private int indentSuppressionLevel = 0;
    private char[] buffer = new char[8192];
    private int index = 0;

    public HtmlOutputter(BufferConsumer<X> consumer)
    {
        this.consumer = consumer;
    }

    public void output(Object... contents) throws X
    {
        dispatch(Html.asHtml(contents).getContents());
        flush();
    }

    private void dispatch(Iterable<Content> contents) throws X
    {
        for (Content content : contents)
        {
            dispatch(content);
        }
    }

    private void dispatch(Content content) throws X
    {
        if (content instanceof Element)
        {
            Element element = (Element) content;
            if (element.isFragment())
            {
                dispatch(element.getContents());
            }
            else
            {
                element(element);
            }
        }
        else
        {
            text((Text) content);
        }
    }

    private void element(Element element) throws X
    {
        if (element.getName().equals("HTML") && nothingWritten)
        {
            append("<!DOCTYPE HTML>\r\n");
        }
        boolean pre = element.getName().equals("PRE");
        boolean newLinesOutside = !element.isInline();
        boolean newLinesInside = !pre && !element.isInline() && hasNonInlineContents(element.getContents());
        if (newLinesOutside)
        {
            newLine();
        }
        raw('<');
        raw(element.getName());
        for (Attribute attribute : element.getAttributes())
        {
            attribute(attribute, element);
        }
        if (element.isEmpty())
        {
            raw(">");
        }
        else
        {
            raw('>');
            if (pre)
            {
                indentSuppressionLevel++;
            }
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
            raw(element.getName());
            raw('>');
            if (pre)
            {
                indentSuppressionLevel--;
            }
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
                if (!element.isInline() || hasNonInlineContents(element.getContents()))
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

    private void attribute(Attribute attribute, Element element) throws X
    {
        AttributeType attributeType = attribute.getType(element.getName());
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
        if (atStartOfLine && indentLevel > 0 && indentSuppressionLevel == 0)
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
