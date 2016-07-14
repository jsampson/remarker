package org.remarker;

import java.io.*;

public class EscapingBenchmark
{
    public static void main(String[] args)
    {
        int n = 10000;
        int r = 100;
        StringBuilder inputBuilder = new StringBuilder();
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            inputBuilder.append("sf<>ffr&&HJwkfijfi3<><><><\"\"sdfsfds");
            outputBuilder.append("sf&lt;&gt;ffr&amp;&amp;HJwkfijfi3&lt;&gt;&lt;&gt;&lt;&gt;&lt;\"\"sdfsfds");
        }
        String inputString = inputBuilder.toString();
        String outputString = outputBuilder.toString();
        long total = 0;
        for (int i = 0; i < r; i++)
        {
            StringWriter writer = new StringWriter();
            HtmlOutputter<RuntimeException> outputter = new HtmlOutputter<>(writer::write);
            long start = System.nanoTime();
            outputter.output(inputString);
            long end = System.nanoTime();
            total += (end - start);
            if (!outputString.equals(writer.toString()))
            {
                throw new IllegalStateException("output not expected");
            }
        }
        System.out.println(total / 1000000000.0);
    }
}
