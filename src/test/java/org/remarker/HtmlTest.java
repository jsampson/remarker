package org.remarker;

import junit.framework.TestCase;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        checkHtml(P(AttributeDefinition.Type.BOOLEAN, new StringBuilder(" la la la "), Boolean.TRUE, '*',
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
        checkHtml(INPUT(Checked(true)), "<input checked=\"checked\" />");
        checkHtml(INPUT(Checked(false)), "<input />");
        checkHtml(INPUT(Checked((Boolean) null)), "<input />");
        checkHtml(INPUT(Checked("checked")), "<input checked=\"checked\" />");
        checkHtml(INPUT(Checked((String) null)), "<input />");
        assertThrowsIllegalArgumentException(
                "The 'checked' attribute must be boolean for the 'input' element; got \"foo\"",
                () -> INPUT(Checked("foo")));
    }

    public void testNumberAttribute()
    {
        checkHtml(TD(Colspan(42)), "<td colspan=\"42\" />");
        checkHtml(TD(Colspan(-17)), "<td colspan=\"-17\" />");
        checkHtml(TD(Colspan((Integer) null)), "<td />");
        checkHtml(TD(Colspan("42")), "<td colspan=\"42\" />");
        checkHtml(TD(Colspan("-17")), "<td colspan=\"-17\" />");
        checkHtml(TD(Colspan((String) null)), "<td />");
        assertThrowsIllegalArgumentException(
                "The 'colspan' attribute must be a number for the 'td' element; got \"foo\"",
                () -> TD(Colspan("foo")));
    }

    public void testAllowedAttributes()
    {
        // "action" is allowed only for "form"
        checkHtml(FORM(Action("...")), "<form action=\"...\" />");
        assertThrowsIllegalArgumentException(
                "The 'action' attribute is not allowed for the 'body' element",
                () -> BODY(Action("...")));
        // "onclick" is allowed for *all elements but* a certain list
        checkHtml(BODY(Onclick("...")), "<body onclick=\"...\" />");
        assertThrowsIllegalArgumentException(
                "The 'onclick' attribute is not allowed for the 'head' element",
                () -> HEAD(Onclick("...")));
        // "cols" has a different type for "frameset" than for "textarea"
        checkHtml(TEXTAREA(Cols("123")), "<textarea cols=\"123\" />");
        assertThrowsIllegalArgumentException(
                "The 'cols' attribute must be a number for the 'textarea' element; got \"1,2,3\"",
                () -> TEXTAREA(Cols("1,2,3")));
    }

    public void testBogusAttribute()
    {
        assertThrowsIllegalArgumentException(
                "The 'foo' attribute is not allowed for the 'p' element",
                () -> P(new Attribute("foo", "bar")));
    }

    private void checkHtml(Element html, String expected)
    {
        StringBuilder builder = new StringBuilder();
        renderForTesting(html, builder);
        assertEquals(expected, builder.toString());
    }

    private void renderForTesting(Element element, StringBuilder builder)
    {
        builder.append("<").append(element.getName());
        for (Attribute attribute : element.getAttributes())
        {
            builder.append(" ").append(attribute.getName()).append("=\"").append(attribute.getValue()).append("\"");
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
            builder.append("</").append(element.getName()).append(">");
        }
    }

    static void assertThrowsIllegalArgumentException(String expectedMessage, Runnable runnable)
    {
        try
        {
            runnable.run();
            fail();
        }
        catch (IllegalArgumentException actual)
        {
            assertEquals(expectedMessage, actual.getMessage());
        }
    }
}
