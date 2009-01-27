package org.remarker;

import java.util.*;

public class CodeGenerator
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("    // BEGIN GENERATED CODE");
        System.out.println();

        Map<String, ElementDefinition> elements = SpecificationParser.parseElements();
        for (ElementDefinition element : elements.values())
        {
            element.generateCode();
            System.out.println();
        }

        Map<String, AttributeDefinition> attributes = SpecificationParser.parseAttributes();
        for (AttributeDefinition attribute : attributes.values())
        {
            attribute.generateCode();
            System.out.println();
        }

        Map<String, CharacterDefinition> characters = SpecificationParser.parseCharacters();
        for (CharacterDefinition character : characters.values())
        {
            character.generateCode();
        }

        System.out.println();
        System.out.println("    // END GENERATED CODE");
    }
}
