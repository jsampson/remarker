package org.remarker;

class CharacterDefinition
{
    final String name;
    final char value;

    CharacterDefinition(String name, char value)
    {
        this.name = name.intern();
        this.value = value;
    }

    String javaName()
    {
        return "_" + name;
    }

    char charValue()
    {
        return value;
    }

    void generateCode()
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
