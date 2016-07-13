package org.remarker;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

import static org.remarker.Html.*;

public class IcTest extends TestCase
{
    public void testIcAttributeAllowedWithHtmlOutputterWhenEnabled() throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer, ElementDefinition.DTD.STRICT).allowIcAttributes();
        outputter.output(HTML(BODY(DIV(Ic.GetFrom("foo")))));
        assertEquals("<HTML>\r\n  <BODY>\r\n    <DIV ic-get-from=\"foo\"></DIV>\r\n  </BODY>\r\n</HTML>\r\n", writer.toString());
    }

    public void testIcAttributeNotAllowedWithHtmlOutputterWhenNotEnabled() throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer, ElementDefinition.DTD.STRICT);
        try
        {
            outputter.output(HTML(BODY(DIV(Ic.Target("foo")))));
            fail();
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals("The 'ic-target' attribute is not allowed for the 'div' element with the strict DTD", expected.getMessage());
        }
    }
}
