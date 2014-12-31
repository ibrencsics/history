package org.ib.history.wiki.domain;

import org.ib.history.commons.data.FlexibleDate;

public class WikiSuccession {

    private FlexibleDate from;
    private FlexibleDate to;
    private String country;
    private String title;
    private WikiNamedResource predecessor;
    private WikiNamedResource successor;

    public FlexibleDate getFrom() {
        return from;
    }

    public FlexibleDate getTo() {
        return to;
    }

    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }

    public WikiNamedResource getPredecessor() {
        return predecessor;
    }

    public WikiNamedResource getSuccessor() {
        return successor;
    }

    public static class Builder {
        WikiSuccession wikiSuccession = new WikiSuccession();

        public Builder from(FlexibleDate from) {
            wikiSuccession.from = from;
            return this;
        }

        public Builder to(FlexibleDate to) {
            wikiSuccession.to = to;
            return this;
        }

        public Builder country(String country) {
            wikiSuccession.country = country;
            return this;
        }

        public Builder title(String title) {
            wikiSuccession.title = title;
            return this;
        }

        public Builder predecessor(WikiNamedResource predecessor) {
            wikiSuccession.predecessor = predecessor;
            return this;
        }

        public Builder successor(WikiNamedResource successor) {
            wikiSuccession.successor = successor;
            return this;
        }

        public WikiSuccession build() {
            return wikiSuccession;
        }
    }

    @Override
    public String toString() {
        return "WikiSuccession{" +
                "from=" + from +
                ", to=" + to +
                ", country='" + country + '\'' +
                ", title='" + title + '\'' +
                ", predecessor=" + predecessor +
                ", successor=" + successor +
                '}';
    }
}
