package org.remarker;

import junit.framework.TestCase;

import java.io.StringWriter;

import static org.remarker.Html.*;

public class IcTest extends TestCase
{
    public void testIcAttributeAllowed()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(HTML(BODY(DIV(Ic.GetFrom("foo")))));
        assertEquals("<!DOCTYPE HTML>\r\n<HTML>\r\n  <BODY>\r\n    <DIV ic-get-from=\"foo\"></DIV>\r\n  </BODY>\r\n</HTML>\r\n", writer.toString());
    }
}
