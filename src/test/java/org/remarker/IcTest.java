package org.remarker;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

import static org.remarker.Html.*;

public class IcTest extends TestCase
{
    public void testIcAttributeAllowed() throws IOException
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter outputter = new HtmlOutputter(writer, ElementDefinition.DTD.STRICT);
        outputter.output(HTML(BODY(DIV(Ic.GetFrom("foo")))));
        assertEquals("<HTML>\r\n  <BODY>\r\n    <DIV ic-get-from=\"foo\"></DIV>\r\n  </BODY>\r\n</HTML>\r\n", writer.toString());
    }
}
