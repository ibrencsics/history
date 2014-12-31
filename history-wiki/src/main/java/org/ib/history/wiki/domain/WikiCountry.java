package org.ib.history.wiki.domain;

public class WikiCountry extends WikiNamedResource {

    public WikiCountry(String pageName) {
        super(pageName);
    }

    public WikiCountry(String pageName, String displayText) {
        super(pageName, displayText);
    }
}
