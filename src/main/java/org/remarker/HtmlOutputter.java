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
import java.util.regex.Pattern;
import org.remarker.dom.*;

import static org.remarker.dom.BreakStyle.*;
import static org.remarker.dom.ContentModel.*;

public final class HtmlOutputter<X extends Exception>
{
    @FunctionalInterface
    public interface BufferConsumer<X extends Exception>
    {
        void accept(char[] buffer, int offset, int length) throws X;
    }

    private enum EOL
    {
        NO, CR, LF, TAG,
    }

    private final BufferConsumer<X> consumer;
    private boolean nothingWritten = true;
    private EOL eol = EOL.TAG;
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
            element((Element) content);
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
        BreakStyle breakStyle = element.getBreakStyle();
        ContentModel contentModel = element.getContentModel();
        boolean hasNonInlineContents = hasNonInlineContents(element.getContents());
        boolean suppressIndentation = breakStyle == PRE || breakStyle == TEXTAREA;
        boolean increaseIndentation = !suppressIndentation && hasNonInlineContents;
        boolean newLinesOutside = breakStyle == BLOCK || breakStyle == PRE;
        boolean newLineAfterStart = hasNonInlineContents && breakStyle != INLINE;
        boolean newLineBeforeEnd = newLineAfterStart && breakStyle == BLOCK;
        if (newLinesOutside)
        {
            newLine(EOL.TAG);
        }
        raw('<');
        raw(element.getName());
        for (Attribute attribute : element.getAttributes())
        {
            attribute(attribute, element);
        }
        if (contentModel == VOID)
        {
            raw(">");
        }
        else
        {
            raw('>');
            if (suppressIndentation)
            {
                indentSuppressionLevel++;
            }
            if (increaseIndentation)
            {
                indentLevel++;
            }
            if (newLineAfterStart)
            {
                newLine(EOL.TAG);
            }

            if (contentModel == RAW_TEXT)
            {
                dispatchRawText(element.getContents());
            }
            else
            {
                dispatch(element.getContents());
            }

            if (newLineBeforeEnd)
            {
                newLine(EOL.TAG);
            }
            if (increaseIndentation)
            {
                indentLevel--;
            }
            raw("</");
            raw(element.getName());
            raw('>');
            if (suppressIndentation)
            {
                indentSuppressionLevel--;
            }
        }
        if (newLinesOutside)
        {
            newLine(EOL.TAG);
        }
    }

    private boolean hasNonInlineContents(List<Content> children)
    {
        for (Content child : children)
        {
            if (child instanceof Element)
            {
                Element element = (Element) child;
                BreakStyle breakStyle = element.getBreakStyle();
                if (breakStyle == BLOCK || breakStyle == PRE || hasNonInlineContents(element.getContents()))
                {
                    return true;
                }
            }
            else
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

    private void dispatchRawText(List<Content> contents) throws X
    {
        for (Content content : contents)
        {
            Text text = (Text) content;
            String value = text.getValue();
            int n = value.length();
            for (int i = 0; i < n; i++)
            {
                int c = value.charAt(i);
                if (c == '\r' || c == '\n')
                {
                    newLine(c == '\r' ? EOL.CR : EOL.LF);
                }
                else
                {
                    raw((char) c);
                }
            }
        }
    }

    private void text(Text text) throws X
    {
        escape(text.getValue(), false);
    }

    // The HTML syntax allows more than these characters in unquoted attribute values,
    // but these are the most common and therefore a reasonably conservative set.
    private static final Pattern UNQUOTED_VALUE_PATTERN = Pattern.compile("[A-Za-z0-9_.+-]+");

    private void attribute(Attribute attribute, Element element) throws X
    {
        raw(" ");
        raw(attribute.getName());
        String value = attribute.getValue();
        if (!value.isEmpty())
        {
            if (UNQUOTED_VALUE_PATTERN.matcher(value).matches())
            {
                raw('=');
                raw(value);
            }
            else
            {
                raw("=\"");
                escape(value, true);
                raw('"');
            }
        }
    }

    private void raw(String string) throws X
    {
        if (string.length() != 0)
        {
            writeIndent();
            append(string);
            eol = EOL.NO;
        }
    }

    private void raw(char character) throws X
    {
        writeIndent();
        append(character);
        eol = EOL.NO;
    }

    private void writeIndent() throws X
    {
        if (eol != EOL.NO && indentLevel > 0 && indentSuppressionLevel == 0)
        {
            for (int i = 0; i < indentLevel; i++)
            {
                append("  ");
            }
            eol = EOL.NO;
        }
    }

    private void newLine(EOL reason) throws X
    {
        if (reason == EOL.TAG && eol != EOL.TAG
                || reason == EOL.CR
                || reason == EOL.LF && eol != EOL.CR)
        {
            append("\r\n");
        }
        eol = reason;
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
                    newLine(c == '\r' ? EOL.CR : EOL.LF);
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
