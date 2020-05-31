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

import java.io.*;
import junit.framework.*;
import org.remarker.dom.*;

import static org.remarker.Html.*;
import static org.remarker.HtmlTest.assertThrowsIllegalArgumentException;

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
                "  <INPUT type=text>",
                "</P>");
    }

    public void testFragment()
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
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked(false))),
                "<P class=class><INPUT type=checkbox></P>\r\n");
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked(true))),
                "<P class=class><INPUT type=checkbox checked></P>\r\n");
        checkHtml(P(Class("class"), INPUT(Type("checkbox"), Checked())),
                "<P class=class><INPUT type=checkbox checked></P>\r\n");
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
                HTML(BODY("Hello", PRE("First line.\nSecond line.\rThird line.\r\nFourth line.\r\n\r\nFifth line."), "World")),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <BODY>",
                "    Hello",
                "    <PRE>",
                "First line.",
                "Second line.",
                "Third line.",
                "Fourth line.",
                "",
                "Fifth line.</PRE>",
                "    World",
                "  </BODY>",
                "</HTML>");
    }

    public void testTextareaIndentation()
    {
        checkHtml(
                HTML(BODY("Hello", TEXTAREA("First line.\nSecond line.\rThird line.\r\nFourth line.\r\n\r\nFifth line."), "World")),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <BODY>",
                "    Hello<TEXTAREA>",
                "First line.",
                "Second line.",
                "Third line.",
                "Fourth line.",
                "",
                "Fifth line.</TEXTAREA>World",
                "  </BODY>",
                "</HTML>");
    }

    public void testSelectIndentation()
    {
        checkHtml(
                P("Hello", SELECT(OPTION("One"), OPTION("Two")), "World"),
                "<P>",
                "  Hello<SELECT>",
                "    <OPTION>One</OPTION>",
                "    <OPTION>Two</OPTION>",
                "  </SELECT>World",
                "</P>");
    }

    public void testNewlinesInAttribute()
    {
        checkHtml(INPUT(Type("hidden"), Value("first line\r\nsecond line")),
                "<INPUT type=hidden value=\"first line&#13;&#10;second line\">");
    }

    public void testSurrogatePairs()
    {
        checkHtml(P("\u6C34\u007A\uD834\uDD1E"), "<P>&#27700;z&#119070;</P>\r\n");
    }

    public void testRawText()
    {
        checkHtml(
                HTML(HEAD(SCRIPT("var v = 1 < 2\n        && 3 > 4;"), STYLE(".foo > .bar {\n  ...\n}"))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <HEAD>",
                "    <SCRIPT>",
                "      var v = 1 < 2",
                "              && 3 > 4;",
                "    </SCRIPT>",
                "    <STYLE>",
                "      .foo > .bar {",
                "        ...",
                "      }",
                "    </STYLE>",
                "  </HEAD>",
                "</HTML>");
    }

    public void testEmptyRawText()
    {
        checkHtml(
                HTML(HEAD(SCRIPT(), STYLE())),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <HEAD>",
                "    <SCRIPT></SCRIPT>",
                "    <STYLE></STYLE>",
                "  </HEAD>",
                "</HTML>");
    }

    public void testEscapableRawText()
    {
        checkHtml(
                HTML(HEAD(TITLE("Hello & Goodbye")), BODY(P(TEXTAREA("1 < 2")))),
                "<!DOCTYPE HTML>",
                "<HTML>",
                "  <HEAD>",
                "    <TITLE>Hello &amp; Goodbye</TITLE>",
                "  </HEAD>",
                "  <BODY>",
                "    <P><TEXTAREA>1 &lt; 2</TEXTAREA></P>",
                "  </BODY>",
                "</HTML>");
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

    public void testDoctype()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(HTML());
        assertEquals("<!DOCTYPE HTML>\r\n<HTML></HTML>\r\n", writer.toString());
    }
}
