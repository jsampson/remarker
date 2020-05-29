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

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import junit.framework.TestCase;
import org.remarker.dom.*;

import static java.util.Arrays.asList;
import static org.remarker.Html.*;

public class HtmlTest extends TestCase
{
    public void testDocument()
    {
        checkHtml(
                HTML(HEAD(TITLE("Example")), BODY(H1("Hello!"), P("Look...", _nbsp, A(Href("http://..."), Title("Somewhere Else"),
                        "Go There.")))),
                "<html><head><title>Example</title></head><body><h1>Hello!</h1><p>Look...\u00A0<a href=\"http://...\" title=\"Somewhere Else\">Go There.</a></p></body></html>");
    }

    public void testArray()
    {
        checkHtml(P((Object) new Integer[]{17, -42, 500}), "<p>17-42500</p>");
    }

    public void testIterable()
    {
        checkHtml(P(asList(17, -42, 500)), "<p>17-42500</p>");
    }

    public void testIterator()
    {
        checkHtml(P(asList(17, -42, 500).iterator()), "<p>17-42500</p>");
    }

    public void testOptionalEmpty()
    {
        checkHtml(P("x", Optional.empty(), "z"), "<p>xz</p>");
    }

    public void testOptionalPresent()
    {
        checkHtml(P("x", Optional.of("y"), "z"), "<p>xyz</p>");
    }

    public void testEmptyStream()
    {
        checkHtml(P("x", Stream.empty(), "z"), "<p>xz</p>");
    }

    public void testNonEmptyStream()
    {
        checkHtml(P("x", Stream.of("a", "b", "c"), "z"), "<p>xabcz</p>");
    }

    public void testSupplier()
    {
        checkHtml(P("x", (Supplier<String>) () -> "y", "z"), "<p>xyz</p>");
    }

    public void testNull()
    {
        checkHtml(P("foo", null, "bar"), "<p>foobar</p>");
    }

    public void testStringifying()
    {
        checkHtml(P(AttributeType.BOOLEAN, new StringBuilder(" la la la "), Boolean.TRUE, '*',
                3.1415), "<p>BOOLEAN la la la true*3.1415</p>");
    }

    public void testElementContentError()
    {
        assertThrowsIllegalArgumentException(
                "java.util.regex.Pattern",
                () -> P(Pattern.compile("[abc]")));
        assertThrowsIllegalArgumentException(
                "int[]",
                () -> P((Object) new int[] { 1, 2, 3 }));
    }

    public void testBooleanAttribute()
    {
        checkHtml(INPUT(Checked(true)), "<input checked />");
        checkHtml(INPUT(Checked(false)), "<input />");
        checkHtml(INPUT(Checked((Boolean) null)), "<input />");
        checkHtml(INPUT(Checked()), "<input checked />");
    }

    public void testNumberAttribute()
    {
        checkHtml(TD(Colspan(42)), "<td colspan=\"42\" />");
        checkHtml(TD(Colspan(-17)), "<td colspan=\"-17\" />");
        checkHtml(TD(Colspan((Integer) null)), "<td />");
    }

    public void testAttributeDisallowedInFragment()
    {
        assertThrowsIllegalArgumentException(
                "Attribute 'onclick' must be contained in an element",
                () -> asHtml(Onclick("...")));
    }

    private void checkHtml(Element html, String expected)
    {
        StringBuilder builder = new StringBuilder();
        renderForTesting(html, builder);
        assertEquals(expected, builder.toString());
    }

    private void renderForTesting(Element element, StringBuilder builder)
    {
        builder.append("<").append(element.getName().toLowerCase());
        for (Attribute attribute : element.getAttributes())
        {
            builder.append(" ").append(attribute.getName());
            if (!attribute.getValue().isEmpty())
            {
              builder.append("=\"").append(attribute.getValue()).append("\"");
            }
        }
        if (element.getContents().isEmpty())
        {
            builder.append(" />");
        }
        else
        {
            builder.append(">");
            for (Content content : element.getContents())
            {
                if (content instanceof Element)
                {
                    renderForTesting((Element) content, builder);
                }
                else
                {
                    builder.append(((Text) content).getValue());
                }
            }
            builder.append("</").append(element.getName().toLowerCase()).append(">");
        }
    }

    static void assertThrowsIllegalArgumentException(String expectedMessage, Runnable runnable)
    {
        try
        {
            runnable.run();
            fail("Not thrown: <" + expectedMessage + ">");
        }
        catch (IllegalArgumentException actual)
        {
            assertEquals(expectedMessage, actual.getMessage());
        }
    }
}
