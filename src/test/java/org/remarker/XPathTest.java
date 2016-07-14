package org.remarker;

import org.junit.Test;

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
        assertThrowsIllegalArgumentException("XPath not supported: <body/./text()>", () -> ROOT.evaluateXPath("body/./text()"));
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
}
