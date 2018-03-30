package com.anecon.taf.client.meux;

import meuxplugin.meuxsystem.Property;
import meuxplugin.meuxsystem.Util;

import java.util.HashMap;
import java.util.Map;

public class MeuxDescription {
    private final Map<String, Property> descriptions;
    private final MeuxDescription parent;
    private final String type;

    MeuxDescription(MeuxDescription parent, String type) {
        descriptions = new HashMap<>();
        this.parent = parent;
        this.type = type;
    }

    MeuxDescription(MeuxDescription meuxLocator, String key, String value) {
        this(meuxLocator, key, value, false);
    }

    MeuxDescription(MeuxDescription meuxLocator, String key, String value, boolean regex) {
        descriptions = new HashMap<>(meuxLocator.descriptions);
        if (key != null) {
            descriptions.put(key, new Property(key, value, regex));
        } else if (value == null) {
            throw new IllegalArgumentException("If key is null, value has to be null to");
        }
        parent = meuxLocator.parent;
        type = meuxLocator.type;
    }

    MeuxDescription(String type, MeuxDescription meuxDescription) {
        if (meuxDescription != null) {
            descriptions = new HashMap<>(meuxDescription.descriptions);
            parent = meuxDescription.parent;
        } else {
            descriptions = new HashMap<>();
            parent = null;
        }
        this.type = type;
    }

    private MeuxDescription(MeuxDescription meuxLocator, MeuxDescription parent) {
        descriptions = new HashMap<>(meuxLocator.descriptions);
        this.parent = parent;
        type = meuxLocator.type;
    }

    MeuxDescription withParent(MeuxDescription parent) {
        return new MeuxDescription(this, parent);
    }

    boolean hasParent() {
        return parent != null;
    }

    MeuxDescription getParent() {
        return parent;
    }

    private String getDescriptionXmlBegin() {
        final StringBuilder xmlPart = new StringBuilder();
        if (parent != null) {
            xmlPart.append(parent.getDescriptionXmlBegin());
        }

        xmlPart.append("<Element");
        if (type != null) {
            xmlPart.append(" type=\"").append(Util.convertXMLtoString(type)).append("\"");
        }
        xmlPart.append(">");


        if (type != null) {
            xmlPart.append(propertyToString(new Property("micclass", type)));
        }
        for (Property p : descriptions.values()) {
            xmlPart.append(propertyToString(p));
        }

        return xmlPart.toString();
    }

    private static String propertyToString(Property p) {
        return "<Property name=\"" +
            Util.convertXMLtoString(p.getName()) +
            "\" value=\"" +
            Util.convertXMLtoString(p.getValue()) +
            "\" regExp=\"" +
            String.valueOf(p.isRegExp()).toLowerCase() +
            "\"/>";
    }

    private String getDescriptionXmlEnd() {
        final StringBuilder xmlPart = new StringBuilder();
        if (parent != null) {
            xmlPart.append(parent.getDescriptionXmlEnd());
        }

        xmlPart.append("</Element>");
        return xmlPart.toString();
    }

    @Override
    public String toString() {
        return getDescriptionXmlBegin() + getDescriptionXmlEnd();
    }
}
