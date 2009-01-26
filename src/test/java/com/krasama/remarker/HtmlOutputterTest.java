package com.krasama.remarker;

import static com.krasama.remarker.Html.*;

import java.io.*;

import junit.framework.*;

import org.jdom.*;

public class HtmlOutputterTest extends TestCase
{
    public void testDocument() throws Exception
    {
        checkHtml(
                HTML(HEAD(TITLE("Example")), BODY(H1("Hello!"), P("Look...", _nbsp, A(Href("http://..."), Title("Somewhere Else"),
                        "Go There.")))),
                "<HTML>\r\n  <HEAD>\r\n    <TITLE>Example</TITLE>\r\n  </HEAD>\r\n  <BODY>\r\n    <H1>Hello!</H1>\r\n    <P>Look...&#160;<A href=\"http://...\" title=\"Somewhere Else\">Go There.</A></P>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testEscaping() throws Exception
    {
        checkHtml(P(Class("'\"<>&\u0009 ~\u007F"), "'\"<>&\u0009 ~\u007F"),
                "<P class=\"'&quot;&lt;&gt;&amp;&#9; ~&#127;\">'\"&lt;&gt;&amp;&#9; ~&#127;</P>\r\n");
    }

    public void testEmptyTags() throws Exception
    {
        checkHtml(P(B(), BR(), INPUT(Type("text"))), "<P>\r\n  <B></B>\r\n  <BR>\r\n  <INPUT type=\"text\">\r\n</P>\r\n");
    }

    public void testBooleanAttributes() throws Exception
    {
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked("checked"))),
                "<P class=\"class\"><INPUT type=\"checkbox\" checked></P>\r\n");
    }

    public void testTableIndentation() throws Exception
    {
        checkHtml(
                HTML(BODY(TABLE(TR(TD("Hello."), TD(P("Goodbye.")))))),
                "<HTML>\r\n  <BODY>\r\n    <TABLE>\r\n      <TR>\r\n        <TD>Hello.</TD>\r\n        <TD>\r\n          <P>Goodbye.</P>\r\n        </TD>\r\n      </TR>\r\n    </TABLE>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testTextIndentation() throws Exception
    {
        checkHtml(
                HTML(BODY(P("First line.\nSecond line.\rThird line.\r\nFourth line."))),
                "<HTML>\r\n  <BODY>\r\n    <P>\r\n      First line.\r\n      Second line.\r\n      Third line.\r\n      Fourth line.\r\n    </P>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testNewlinesInAttribute() throws Exception
    {
        checkHtml(INPUT(Type("hidden"), Value("first line\r\nsecond line")),
                "<INPUT type=\"hidden\" value=\"first line&#13;&#10;second line\">");
    }

    public void testSurrogatePairs() throws IOException
    {
        checkHtml(P("\u6C34\u007A\uD834\uDD1E"), "<P>&#27700;z&#119070;</P>\r\n");
    }

    private void checkHtml(Element html, String expected) throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer);
        outputter.output(html);
        assertEquals(expected, writer.toString());
    }

    public void testMixedOutput() throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer);
        outputter.output("<escapeme>", 15, P("bla"));
        assertEquals("&lt;escapeme&gt;15\r\n<P>bla</P>\r\n", writer.toString());
    }

    public void testMixedOutputError() throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer);
        try
        {
            outputter.output(Class("foo"));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("Attribute 'class' must be contained in an element", expected.getMessage());
        }
    }
}
