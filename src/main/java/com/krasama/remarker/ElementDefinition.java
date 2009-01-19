package com.krasama.remarker;

public class ElementDefinition
{
    public static enum DTD
    {
        STRICT, LOOSE, FRAMESET
    }

    public final String lowercase;
    public final String uppercase;
    public final boolean empty;
    public final DTD dtd;

    public ElementDefinition(String name, boolean empty, DTD dtd)
    {
        this.lowercase = name.toLowerCase().intern();
        this.uppercase = name.toUpperCase().intern();
        this.empty = empty;
        this.dtd = dtd;
    }

    public String javaName()
    {
        return uppercase;
    }

    public String xmlName()
    {
        return lowercase;
    }

    public void generateCode()
    {
        String emptyParam = empty ? ", true" : ", false";
        System.out.println("    public static Element " + javaName() + "(Object... contents)");
        System.out.println("    {");
        System.out.println("        return element(\"" + xmlName() + "\", contents" + emptyParam + ");");
        System.out.println("    }");
    }
}
