package com.krasama.remarker;

public class CharacterDefinition
{
    public final String name;
    public final char value;

    public CharacterDefinition(String name, char value)
    {
        this.name = name.intern();
        this.value = value;
    }
}
