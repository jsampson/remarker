package com.krasama.remarker;

import static com.krasama.remarker.SpecificationParser.*;

import java.io.*;

import org.jdom.*;

public class HtmlOutputter
{
    public void output(Content content, Writer writer) throws IOException
    {
        if (content instanceof Element)
        {
            output((Element) content, writer);
        }
        else if (content instanceof Text)
        {
            output((Text) content, writer);
        }
        else
        {
            throw new UnsupportedOperationException(content.getClass().getCanonicalName());
        }
    }

    public void output(Element element, Writer writer) throws IOException
    {
        ElementDefinition elementDefinition = ELEMENTS.get(element.getName());
        writer.write('<');
        writer.write(elementDefinition.uppercase);
        for (Object attribute : element.getAttributes())
        {
            output((Attribute) attribute, writer);
        }
        writer.write('>');
        for (Object content : element.getContent())
        {
            output((Content) content, writer);
        }
        writer.write("</");
        writer.write(elementDefinition.uppercase);
        writer.write('>');
    }

    public void output(Text text, Writer writer) throws IOException
    {
        escape(text.getText(), writer, false);
    }

    private void output(Attribute attribute, Writer writer) throws IOException
    {
        AttributeDefinition attributeDefinition = ATTRIBUTES.get(attribute.getName());
        writer.write(" ");
        writer.write(attributeDefinition.name);
        writer.write("=\"");
        escape(attribute.getValue(), writer, true);
        writer.write('"');
    }

    private void escape(String string, Writer writer, boolean escapeQuotes) throws IOException
    {
        for (int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            switch (c)
            {
            case '<':
                writer.write("&lt;");
                continue;
            case '>':
                writer.write("&gt;");
                continue;
            case '&':
                writer.write("&amp;");
                continue;
            case '"':
                if (escapeQuotes)
                {
                    writer.write("&quot;");
                    continue;
                }
            default:
                writer.write(c);
                continue;
            }
        }
    }
}
