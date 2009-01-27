package org.remarker;

import static java.util.Arrays.*;
import static org.remarker.Html.*;

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
            assertEquals("java.util.regex.Pattern", expected.getMessage());
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
        checkHtml(INPUT(Checked(true)), "<input checked=\"checked\" />");
        checkHtml(INPUT(Checked(false)), "<input />");
        checkHtml(INPUT(Checked((Boolean) null)), "<input />");
        checkHtml(INPUT(Checked("checked")), "<input checked=\"checked\" />");
        checkHtml(INPUT(Checked((String) null)), "<input />");
        try
        {
            INPUT(Checked("foo"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'checked' attribute must be boolean for the 'input' element; got \"foo\"", expected.getMessage());
        }
    }

    public void testNumberAttribute() throws Exception
    {
        checkHtml(TD(Colspan(42)), "<td colspan=\"42\" />");
        checkHtml(TD(Colspan(-17)), "<td colspan=\"-17\" />");
        checkHtml(TD(Colspan((Integer) null)), "<td />");
        checkHtml(TD(Colspan("42")), "<td colspan=\"42\" />");
        checkHtml(TD(Colspan("-17")), "<td colspan=\"-17\" />");
        checkHtml(TD(Colspan((String) null)), "<td />");
        try
        {
            TD(Colspan("foo"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'colspan' attribute must be a number for the 'td' element; got \"foo\"", expected.getMessage());
        }
    }

    public void testAllowedAttributes() throws Exception
    {
        // "action" is allowed only for "form"
        checkHtml(FORM(Action("...")), "<form action=\"...\" />");
        try
        {
            BODY(Action("..."));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'action' attribute is not allowed for the 'body' element", expected.getMessage());
        }
        // "onclick" is allowed for *all elements but* a certain list
        checkHtml(BODY(Onclick("...")), "<body onclick=\"...\" />");
        try
        {
            HEAD(Onclick("..."));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'onclick' attribute is not allowed for the 'head' element", expected.getMessage());
        }
        // "cols" has a different type for "frameset" than for "textarea"
        checkHtml(FRAMESET(Cols("1,2,3")), "<frameset cols=\"1,2,3\" />");
        checkHtml(TEXTAREA(Cols("123")), "<textarea cols=\"123\" />");
        try
        {
            TEXTAREA(Cols("1,2,3"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'cols' attribute must be a number for the 'textarea' element; got \"1,2,3\"", expected.getMessage());
        }
    }

    public void testBogusAttribute() throws Exception
    {
        try
        {
            P(new Attribute("foo", "bar"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'foo' attribute is not allowed for the 'p' element", expected.getMessage());
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
