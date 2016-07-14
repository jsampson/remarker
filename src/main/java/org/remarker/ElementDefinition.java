package org.remarker;

class ElementDefinition
{
    final String lowercase;
    final String uppercase;
    final boolean inline;
    final boolean empty;

    ElementDefinition(String name, boolean inline, boolean empty)
    {
        this.lowercase = name.toLowerCase().intern();
        this.uppercase = name.toUpperCase().intern();
        this.inline = inline;
        this.empty = empty;
    }

    private String javaName()
    {
        return uppercase;
    }

    private String xmlName()
    {
        return lowercase;
    }

    void generateCode()
    {
        System.out.println("    public static Element " + javaName() + "(Object... contents)");
        System.out.println("    {");
        System.out.println("        return element(\"" + xmlName() + "\", contents);");
        System.out.println("    }");
    }
}
