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

import java.io.StringWriter;
import junit.framework.TestCase;
import org.remarker.ext.Ic;

import static org.remarker.Html.*;

public class IcTest extends TestCase
{
    public void testIcAttributeAllowed()
    {
        StringWriter writer = new StringWriter();
        HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
        outputter.output(HTML(BODY(DIV(Ic.GetFrom("foo")))));
        assertEquals("<!DOCTYPE HTML>\r\n<HTML>\r\n  <BODY>\r\n    <DIV ic-get-from=foo></DIV>\r\n  </BODY>\r\n</HTML>\r\n", writer.toString());
    }
}
