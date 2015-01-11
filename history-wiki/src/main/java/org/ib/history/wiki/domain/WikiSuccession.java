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
//                ", country='" + country + '\'' +
                ", title='" + title + '\'' +
                ", predecessor=" + predecessor +
                ", successor=" + successor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiSuccession that = (WikiSuccession) o;

//        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (predecessor != null ? !predecessor.equals(that.predecessor) : that.predecessor != null) return false;
        if (successor != null ? !successor.equals(that.successor) : that.successor != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (predecessor != null ? predecessor.hashCode() : 0);
        result = 31 * result + (successor != null ? successor.hashCode() : 0);
        return result;
    }
}
