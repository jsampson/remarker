package org.remarker;

public class CharacterDefinition
{
    public final String name;
    public final char value;

    public CharacterDefinition(String name, char value)
    {
        this.name = name.intern();
        this.value = value;
    }

    public String javaName()
    {
        return "_" + name;
    }

    public char charValue()
    {
        return value;
    }

    public void generateCode()
    {
        System.out.println("    public static final String " + javaName() + " = String.valueOf((char) " + (int) charValue() + ");");
        if (name.endsWith("sp"))
        {
            // ensp, emsp, nbsp, thinsp
            String duplicated = javaName();
            for (int n = 2; n <= 8; n++)
            {
                duplicated += " + " + javaName();
                System.out.println("    public static final String " + javaName() + "_" + n + " = " + duplicated + ";");
            }
        }
    }
}
