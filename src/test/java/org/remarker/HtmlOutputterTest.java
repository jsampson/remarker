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
                "<!DOCTYPE HTML>\r\n<HTML>\r\n  <HEAD>\r\n    <TITLE>Example</TITLE>\r\n  </HEAD>\r\n  <BODY>\r\n    <H1>Hello!</H1>\r\n    <P>Look...&#160;<A href=\"http://...\" title=\"Somewhere Else\">Go There.</A></P>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testEscaping()
    {
        checkHtml(P(Class("'\"<>&\u0009 ~\u007F"), "'\"<>&\u0009 ~\u007F"),
                "<P class=\"'&quot;&lt;&gt;&amp;&#9; ~&#127;\">'\"&lt;&gt;&amp;&#9; ~&#127;</P>\r\n");
    }

    public void testEmptyTags()
    {
        checkHtml(P(B(), BR(), INPUT(Type("text"))), "<P>\r\n  <B></B>\r\n  <BR>\r\n  <INPUT type=\"text\">\r\n</P>\r\n");
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
                "<!DOCTYPE HTML>\r\n<HTML>\r\n  <BODY>\r\n    <TABLE>\r\n      <TR>\r\n        <TD>Hello.</TD>\r\n        <TD>\r\n          <P>Goodbye.</P>\r\n        </TD>\r\n      </TR>\r\n    </TABLE>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testTextIndentation()
    {
        checkHtml(
                HTML(BODY(P("First line.\nSecond line.\rThird line.\r\nFourth line."))),
                "<!DOCTYPE HTML>\r\n<HTML>\r\n  <BODY>\r\n    <P>\r\n      First line.\r\n      Second line.\r\n      Third line.\r\n      Fourth line.\r\n    </P>\r\n  </BODY>\r\n</HTML>\r\n");
    }

    public void testPreIndentation()
    {
        checkHtml(
                HTML(BODY(PRE("First line.\nSecond line.\rThird line.\r\nFourth line."))),
                "<!DOCTYPE HTML>\r\n<HTML>\r\n  <BODY>\r\n    <PRE>First line.\r\nSecond line.\r\nThird line.\r\nFourth line.</PRE>\r\n  </BODY>\r\n</HTML>\r\n");
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

    private void checkHtml(Element html, String expected)
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(html);
        assertEquals(expected, writer.toString());
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

    public void testElementDTDValidation()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> strict = new HtmlOutputter<>(writer::write);
        // P is okay in all DTDs
        strict.output(P("foo"));
        // CENTER is not allowed in the strict DTD
        assertThrowsIllegalArgumentException(
                "The 'center' element is not allowed",
                () -> strict.output(new Element("center")));
        // FRAME is only allowed in the frameset DTD
        assertThrowsIllegalArgumentException(
                "The 'frame' element is not allowed",
                () -> strict.output(new Element("frame")));
        // don't throw a NullPointerException if the element isn't found at all
        assertThrowsIllegalArgumentException(
                "The 'foo' element is not allowed",
                () -> strict.output(new Element("foo")));
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
                () -> {
                    Element element = new Element("p");
                    element.putAttribute(new Attribute("checked", "checked"));
                    strict.output(element);
                });
        assertThrowsIllegalArgumentException(
                "The 'foo' attribute is not allowed for the 'p' element",
                () -> {
                    Element element = new Element("p");
                    element.putAttribute(new Attribute("foo", "bar"));
                    strict.output(element);
                });
    }

    public void testDoctype()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(HTML());
        assertEquals("<!DOCTYPE HTML>\r\n<HTML></HTML>\r\n", writer.toString());
    }
}
