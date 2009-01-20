package com.krasama.remarker;

import static com.krasama.remarker.Html.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.regex.*;

import junit.framework.*;

import org.jdom.*;
import org.jdom.output.*;

public class HtmlTest extends TestCase
{
    public void testDocument() throws Exception
    {
        checkHtml(
                HTML(HEAD(TITLE("Example")), BODY(H1("Hello!"), P("Look...", _nbsp, A(Href("http://..."), Title("Somewhere Else"),
                        "Go There.")))),
                "<html><head><title>Example</title></head><body><h1>Hello!</h1><p>Look...\u00A0<a href=\"http://...\" title=\"Somewhere Else\">Go There.</a></p></body></html>");
    }

    public void testArray() throws Exception
    {
        checkHtml(P((Object) new Integer[] { 17, -42, 500 }), "<p>17-42500</p>");
    }

    public void testIterable() throws Exception
    {
        checkHtml(P(asList(17, -42, 500)), "<p>17-42500</p>");
    }

    public void testIterator() throws Exception
    {
        checkHtml(P(asList(17, -42, 500).iterator()), "<p>17-42500</p>");
    }

    public void testNull() throws Exception
    {
        checkHtml(P("foo", null, "bar"), "<p>foobar</p>");
    }

    public void testStringifying() throws Exception
    {
        checkHtml(P(AttributeDefinition.Type.BOOLEAN, new StringBuilder(" la la la "), Boolean.TRUE, Character.valueOf('*'),
                Double.valueOf(3.1415)), "<p>BOOLEAN la la la true*3.1415</p>");
    }

    public void testElementContentError()
    {
        try
        {
            P(Pattern.compile("[abc]"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("Pattern", expected.getMessage());
        }
        try
        {
            P((Object) new int[] { 1, 2, 3 });
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("int[]", expected.getMessage());
        }
    }

    public void testBooleanAttribute() throws Exception
    {
        checkHtml(P(Checked(true)), "<p checked=\"checked\" />");
        checkHtml(P(Checked(false)), "<p />");
        checkHtml(P(Checked((Boolean) null)), "<p />");
        checkHtml(P(Checked("checked")), "<p checked=\"checked\" />");
        checkHtml(P(Checked((String) null)), "<p />");
        try
        {
            P(Checked("foo"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("Value for boolean attribute 'checked' must be either \"checked\" or null; got \"foo\"",
                    expected.getMessage());
        }
    }

    public void testNumberAttribute() throws Exception
    {
        checkHtml(P(Colspan(42)), "<p colspan=\"42\" />");
        checkHtml(P(Colspan(-17)), "<p colspan=\"-17\" />");
        checkHtml(P(Colspan((Integer) null)), "<p />");
        checkHtml(P(Colspan("42")), "<p colspan=\"42\" />");
        checkHtml(P(Colspan("-17")), "<p colspan=\"-17\" />");
        checkHtml(P(Colspan((String) null)), "<p />");
        try
        {
            P(Colspan("foo"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("Value for number attribute 'colspan' must be an integer or null; got \"foo\"", expected.getMessage());
        }
    }

    private void checkHtml(Element html, String expected) throws IOException
    {
        XMLOutputter outputter = new XMLOutputter();
        StringWriter writer = new StringWriter();
        outputter.output(html, writer);
        assertEquals(expected, writer.toString());
    }
}
