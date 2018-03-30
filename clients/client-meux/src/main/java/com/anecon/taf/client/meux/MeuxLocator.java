package com.anecon.taf.client.meux;

/**
 * Immutable object capable of building an XML structure which can be used for locating elements with M-eux.
 * <p>
 * Usage: {@code MeuxLocator.root().name("Some name")....}
 */
public class MeuxLocator extends MeuxDescription {
    private static final MeuxLocator EMPTY_INSTANCE = new MeuxLocator(null);

    private MeuxLocator(String type) {
        this(null, type);
    }

    private MeuxLocator(MeuxDescription parent, String type) {
        super(parent, type);
    }

    private MeuxLocator(MeuxLocator meuxLocator, String key, String value) {
        super(meuxLocator, key, value);
    }

    private MeuxLocator(MeuxLocator meuxLocator, String key, String value, boolean regex) {
        super(meuxLocator, key, value, regex);
    }

    public MeuxLocator(MeuxLocator meuxLocator, String type) {
        super(type, meuxLocator);
    }

    public static MeuxLocator root() {
        return EMPTY_INSTANCE;
    }

    public static MeuxLocator parent(MeuxDescription parent) {
        return new MeuxLocator(parent, null);
    }



    public MeuxLocator name(String name) { return new MeuxLocator(this, "name", name);}
    public MeuxLocator className(String className) {return new MeuxLocator(this, "classname", className); }
    public MeuxLocator id(String id) {return new MeuxLocator(this, "id", id);}
    public MeuxLocator fullClassName(String fullClassName) {return new MeuxLocator(this, "fullclassname", fullClassName);}
    public MeuxLocator text(String text) {return new MeuxLocator(this, "text", text);}
    public MeuxLocator title(String title) {return new MeuxLocator(this, "title", title);}

    public MeuxLocator name(String name, boolean regex) { return new MeuxLocator(this, "name", name,regex);}
    public MeuxLocator className(String className, boolean regex) {return new MeuxLocator(this, "classname", className,regex); }
    public MeuxLocator id(String id, boolean regex) {return new MeuxLocator(this, "id", id,regex);}
    public MeuxLocator fullClassName(String fullClassName, boolean regex) {return new MeuxLocator(this, "fullclassname", fullClassName,regex);}
    public MeuxLocator text(String text, boolean regex) {return new MeuxLocator(this, "text", text,regex);}
    public MeuxLocator title(String title, boolean regex) {return new MeuxLocator(this, "title", title,regex);}


    public MeuxLocator type(String type) { return new MeuxLocator(this, type); }
    public MeuxLocator width(String width) {return new MeuxLocator(this, "width", width);}
    public MeuxLocator height(String height) {return new MeuxLocator(this, "height", height);}
    public MeuxLocator index(String index) {return new MeuxLocator(this, "index", index);}
    public MeuxLocator itemCount(String itemCount) {return new MeuxLocator(this, "itemCount", itemCount);}
    public MeuxLocator childCount(String childCount) {return new MeuxLocator(this, "childCount", childCount);}
}
