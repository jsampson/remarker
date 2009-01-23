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
                "<HTML>\r\n  <HEAD>\r\n    <TITLE>Example</TITLE>\r\n  </HEAD>\r\n  <BODY>\r\n    <H1>Hello!</H1>\r\n    <P>Look...\u00A0<A href=\"http://...\" title=\"Somewhere Else\">Go There.</A></P>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testEscaping() throws Exception
    {
        checkHtml(P(Class("'\"<>&"), "'\"<>&"), "<P class=\"'&quot;&lt;&gt;&amp;\">'\"&lt;&gt;&amp;</P>\r\n");
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

    private void checkHtml(Element html, String expected) throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer);
        outputter.output(html);
        assertEquals(expected, writer.toString());
    }
}
