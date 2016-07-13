package org.remarker;

import java.util.*;

public final class Element extends Content
{
    private final String name;
    private final List<Content> contents;
    private final Map<String, Attribute> attributes;

    Element(String name)
    {
        this.name = name;
        this.contents = new ArrayList<>();
        this.attributes = new LinkedHashMap<>();
    }

    public String getName()
    {
        return name;
    }

    public List<Content> getContents()
    {
        return Collections.unmodifiableList(contents);
    }

    public Collection<Attribute> getAttributes()
    {
        return Collections.unmodifiableCollection(attributes.values());
    }

    void addContent(Content content)
    {
        contents.add(content);
    }

    void putAttribute(Attribute attribute)
    {
        attributes.put(attribute.getName(), attribute);
    }
}
