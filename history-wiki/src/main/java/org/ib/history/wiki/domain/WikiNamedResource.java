package org.ib.history.wiki.domain;

public class WikiNamedResource extends WikiResource {

    private final String displayText;

    public WikiNamedResource(String pageName) {
        this(pageName, pageName);
    }

    public WikiNamedResource(String pageName, String displayText) {
        super(pageName);
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return "{" +
                "p='" + getLocalPart() + '\'' +
                (getLocalPart().equals(displayText) ? "" : ", t='" + displayText + '\'') +
                '}';
    }

    public static WikiNamedResource fromLocalPart(String localPart) {
        return new WikiNamedResource(localPart.trim().replace(" ", "_"));
    }

    public static WikiNamedResource fromURIString(String uriStr) {
        return new WikiNamedResource(getLastBitFromUrl(uriStr));
    }
}
