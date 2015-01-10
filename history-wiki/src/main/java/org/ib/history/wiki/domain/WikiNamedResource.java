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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiNamedResource that = (WikiNamedResource) o;

        if (displayText != null ? !displayText.equals(that.displayText) : that.displayText != null) return false;
        if (localPart != null ? !localPart.equals(that.localPart) : that.localPart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return displayText != null ? displayText.hashCode() : 0;
    }

    public static WikiNamedResource fromLocalPart(String localPart) {
        return new WikiNamedResource(localPart.trim().replace(" ", "_"));
    }

    public static WikiNamedResource fromURIString(String uriStr) {
        return new WikiNamedResource(getLastBitFromUrl(uriStr));
    }
}
