package org.remarker;

@FunctionalInterface
interface AttributeTypeFunction
{
    AttributeTypeFunction STRING = elementName -> AttributeDefinition.Type.STRING;
    AttributeTypeFunction BOOLEAN = elementName -> AttributeDefinition.Type.BOOLEAN;

    AttributeDefinition.Type getType(String elementName);
}
