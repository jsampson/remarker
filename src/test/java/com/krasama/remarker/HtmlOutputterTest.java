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
                "<HTML><HEAD><TITLE>Example</TITLE></HEAD><BODY><H1>Hello!</H1><P>Look...\u00A0<A href=\"http://...\" title=\"Somewhere Else\">Go There.</A></P></BODY></HTML>");
    }

    public void testEscaping() throws Exception
    {
        checkHtml(P(Class("'\"<>&"), "'\"<>&"), "<P class=\"'&quot;&lt;&gt;&amp;\">'\"&lt;&gt;&amp;</P>");
    }

    public void testEmptyTags() throws Exception
    {
        checkHtml(P(B(), BR(), INPUT(Type("text"))), "<P><B></B><BR><INPUT type=\"text\"></P>");
    }

    public void testBooleanAttributes() throws Exception
    {
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked("checked"))),
                "<P class=\"class\"><INPUT type=\"checkbox\" checked></P>");
    }

    private void checkHtml(Element html, String expected) throws IOException
    {
        HtmlOutputter outputter = new HtmlOutputter();
        StringWriter writer = new StringWriter();
        outputter.output(html, writer);
        assertEquals(expected, writer.toString());
    }
}
