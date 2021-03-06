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

import org.junit.Test;
import org.remarker.dom.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.remarker.Html.*;
import static org.remarker.Html.Class;
import static org.remarker.HtmlTest.assertThrowsIllegalArgumentException;

public class XPathTest
{
    public static final Element ROOT =
            HTML(BODY(Id("root"), DIV(Class("foo"), "Foo Text"), " ... ", DIV(Class("bar"), "Bar Text")));

    @Test
    public void emptyIsIllegal()
    {
        assertThrowsIllegalArgumentException("XPath not supported: <>", () -> ROOT.evaluateXPath(""));
    }

    @Test
    public void complicatedIsIllegal()
    {
        assertThrowsIllegalArgumentException("XPath not supported: <//body/../text()>", () -> ROOT.evaluateXPath("//body/../text()"));
    }

    @Test
    public void textOfSelf()
    {
        assertEquals(singletonList("Foo Text ... Bar Text"), ROOT.evaluateXPath("text()"));
    }

    @Test
    public void textOfDescendents()
    {
        assertEquals(asList("Foo Text", "Bar Text"), ROOT.evaluateXPath("body/div/text()"));
    }

    @Test
    public void attributeOfSelf()
    {
        Element body = (Element) ROOT.getContents().get(0);
        assertEquals(singletonList("root"), body.evaluateXPath("@id"));
    }

    @Test
    public void attributeOfDescendents()
    {
        assertEquals(asList("foo", "bar"), ROOT.evaluateXPath("body/div/@class"));
    }

    @Test
    public void filterByAttributeValue()
    {
        assertEquals(singletonList("Bar Text"), ROOT.evaluateXPath("body/div[@class='bar']/text()"));
    }

    @Test
    public void filterByText()
    {
        assertEquals(singletonList("foo"), ROOT.evaluateXPath("body/div[text()='Foo Text']/@class"));
    }

    @Test
    public void doubleQuotes()
    {
        assertEquals(singletonList("foo"), ROOT.evaluateXPath("body/div[text()=\"Foo Text\"]/@class"));
    }

    @Test
    public void filterByAttributePresence()
    {
        Element root = DIV(
                INPUT(Value("true"), Checked(true)),
                INPUT(Value("false"), Checked(false)),
                INPUT(Value("null"), Checked(null)),
                INPUT(Value("void"), Checked()));
        assertEquals(asList("true", "void"), root.evaluateXPath("input[@checked]/@value"));
    }

    @Test
    public void dot()
    {
        Element root = BODY(DIV(SPAN("foo")), DIV(SPAN("bar")));
        assertEquals(asList("foo", "bar"), root.evaluateXPath("./div/././span/./text()"));
    }

    @Test
    public void star()
    {
        Element root = BODY(DIV(SPAN(Class("foo"))), DIV(P(Class("bar"))));
        assertEquals(asList("foo", "bar"), root.evaluateXPath("div/*/@class"));
    }

    @Test
    public void descendentsAndSelf()
    {
        Element root = BODY(Class("root"), DIV(Class("child"), SPAN(Class("foo"))), DIV(P(Class("bar"))));
        assertEquals(asList("root", "child", "foo", "bar"), root.evaluateXPath(".//@class"));
    }

    @Test
    public void relativeToFragment()
    {
        Fragment fragment = asHtml(ROOT);
        assertEquals(asList("Foo Text", "Bar Text"), fragment.evaluateXPath("html/body/div/text()"));
    }
}
