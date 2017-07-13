package org.remarker;

import static org.remarker.Html.*;
import static org.remarker.HtmlTest.assertThrowsIllegalArgumentException;

import java.io.*;

import junit.framework.*;

public class HtmlOutputterTest extends TestCase
{
    public void testDocument()
    {
        checkHtml(
                HTML(HEAD(TITLE("Example")), BODY(H1("Hello!"), P("Look...", _nbsp, A(Href("http://..."), Title("Somewhere Else"),
                        "Go There.")))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <HEAD>",
                "    <TITLE>Example</TITLE>",
                "  </HEAD>",
                "  <BODY>",
                "    <H1>Hello!</H1>",
                "    <P>Look...&#160;<A href=\"http://...\" title=\"Somewhere Else\">Go There.</A></P>",
                "  </BODY>",
                "</HTML>");
    }

    public void testEscaping()
    {
        checkHtml(P(Class("'\"<>&\u0009 ~\u007F"), "'\"<>&\u0009 ~\u007F"),
                "<P class=\"'&quot;&lt;&gt;&amp;&#9; ~&#127;\">'\"&lt;&gt;&amp;&#9; ~&#127;</P>\r\n");
    }

    public void testEmptyTags()
    {
        checkHtml(P(B(), BR(), INPUT(Type("text"))),
                "<P>",
                "  <B></B>",
                "  <BR>",
                "  <INPUT type=\"text\">",
                "</P>");
    }

    public void testDummyTag()
    {
        checkHtml(DIV(
                DIV("One"),
                Html.asHtml(
                        DIV("Two"),
                        DIV("Three")),
                DIV("Four")),
                "<DIV>",
                "  <DIV>One</DIV>",
                "  <DIV>Two</DIV>",
                "  <DIV>Three</DIV>",
                "  <DIV>Four</DIV>",
                "</DIV>");
    }

    public void testBooleanAttributes()
    {
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked("checked"))),
                "<P class=\"class\"><INPUT type=\"checkbox\" checked></P>\r\n");
    }

    public void testTableIndentation()
    {
        checkHtml(
                HTML(BODY(TABLE(TR(TD("Hello."), TD(P("Goodbye.")))))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <BODY>",
                "    <TABLE>",
                "      <TR>",
                "        <TD>Hello.</TD>",
                "        <TD>",
                "          <P>Goodbye.</P>",
                "        </TD>",
                "      </TR>",
                "    </TABLE>",
                "  </BODY>",
                "</HTML>");
    }

    public void testTextIndentation()
    {
        checkHtml(
                HTML(BODY(P("First line.\nSecond line.\rThird line.\r\nFourth line."))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <BODY>",
                "    <P>",
                "      First line.",
                "      Second line.",
                "      Third line.",
                "      Fourth line.",
                "    </P>",
                "  </BODY>",
                "</HTML>");
    }

    public void testPreIndentation()
    {
        checkHtml(
                HTML(BODY(PRE("First line.\nSecond line.\rThird line.\r\nFourth line."))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <BODY>",
                "    <PRE>First line.",
                "Second line.",
                "Third line.",
                "Fourth line.</PRE>",
                "  </BODY>",
                "</HTML>");
    }

    public void testSelectIndentation()
    {
        checkHtml(
                SELECT(OPTION("One"), OPTION("Two")),
                "<SELECT>",
                "  <OPTION>One</OPTION>",
                "  <OPTION>Two</OPTION>",
                "</SELECT>");
    }

    public void testNewlinesInAttribute()
    {
        checkHtml(INPUT(Type("hidden"), Value("first line\r\nsecond line")),
                "<INPUT type=\"hidden\" value=\"first line&#13;&#10;second line\">");
    }

    public void testSurrogatePairs()
    {
        checkHtml(P("\u6C34\u007A\uD834\uDD1E"), "<P>&#27700;z&#119070;</P>\r\n");
    }

    private void checkHtml(Element html, String... expectedLines)
    {
        StringBuilder expected = new StringBuilder();
        for (String expectedLine : expectedLines)
        {
            expected.append(expectedLine);
            if (expectedLines.length > 1)
            {
                expected.append("\r\n");
            }
        }
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(html);
        assertEquals(expected.toString(), writer.toString());
    }

    public void testMixedOutput()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output("<escapeme>", 15, P("bla"));
        assertEquals("&lt;escapeme&gt;15\r\n<P>bla</P>\r\n", writer.toString());
    }

    public void testMixedOutputError()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        assertThrowsIllegalArgumentException(
                "Attribute 'class' must be contained in an element",
                () -> outputter.output(Class("foo")));
    }

    public void testAttributeDTDValidation()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> strict = new HtmlOutputter<>(writer::write);
        // Id is okay in all DTDs
        strict.output(P(Id("foo")));
        // Align is not allowed in the strict DTD
        assertThrowsIllegalArgumentException(
                "The 'align' attribute is not allowed for the 'p' element",
                () -> strict.output(P(Align("center"))));
        // Name is allowed on A in the strict DTD, but on IFRAME only in the
        // frameset DTD
        strict.output(A(Name("foo")));
        // don't throw a NullPointerException if the attribute isn't allowed at all
        assertThrowsIllegalArgumentException(
                "The 'checked' attribute is not allowed for the 'p' element",
                () -> strict.output(P(Checked(true))));
        assertThrowsIllegalArgumentException(
                "The 'foo' attribute is not allowed for the 'p' element",
                () -> strict.output(P(new Attribute("foo", "bar", elementName -> null))));
    }

    public void testDoctype()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(HTML());
        assertEquals("<!DOCTYPE HTML>\r\n<HTML></HTML>\r\n", writer.toString());
    }
}
