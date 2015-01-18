package org.ib.history.wiki.domain;

import java.net.URI;
import java.net.URISyntaxException;

public class WikiResource {

    private static final String DBPEDIA_BASE = "http://dbpedia.org/resource/";
    private static final String WIKIPEDIA_BASE = "http://en.wikipedia.org/wiki/";

    protected final String localPart;

    WikiResource(String localPart) {
        this.localPart = localPart;
    }

    public String getLocalPart() {
        return localPart;
    }

    public URI getFullWikipedia() {
        try {
            return new URI(WIKIPEDIA_BASE + localPart);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URI getFullDbpedia() {
        try {
            return new URI(DBPEDIA_BASE + localPart);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WikiResource fromURIString(String uriStr) {
        return new WikiResource(getLastBitFromUrl(uriStr));
    }

    public static WikiResource fromLocalPart(String localPart) {
        return new WikiResource(localPart.replace(" ", "_"));
    }

    protected static String getLastBitFromUrl(final String url){
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiResource that = (WikiResource) o;

        if (localPart != null ? !localPart.equals(that.localPart) : that.localPart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return localPart != null ? localPart.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WikiResource{" +
                "localPart='" + localPart + '\'' +
                '}';
    }
}
