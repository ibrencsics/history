package org.ib.history.wiki.domain;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;

import java.util.List;
import java.util.Optional;

public class WikiSuccession {

    private FlexibleDate from;
    private FlexibleDate to;
    private Optional<String> raw = Optional.empty();
    private Optional<Tuple2<String,List<String>>> titleAndCountries = Optional.empty();
    private WikiNamedResource predecessor;
    private WikiNamedResource successor;

    public FlexibleDate getFrom() {
        return from;
    }

    public FlexibleDate getTo() {
        return to;
    }

    public Optional<String> getRaw() {
        return raw;
    }

    public Optional<Tuple2<String, List<String>>> getTitleAndCountries() {
        return titleAndCountries;
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

        public Builder raw(String raw) {
            wikiSuccession.raw = Optional.of(raw);
            return this;
        }

        public Builder titleAndCountries(Tuple2<String,List<String>> titleAndCountries) {
            wikiSuccession.titleAndCountries = Optional.of(titleAndCountries);
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

        String titleAndCountry = ", titleAndCountry= ";
        if (titleAndCountries.isPresent()) {
            titleAndCountry += titleAndCountries.get().element1() + ":" + titleAndCountries.get().element2();
        } else if (raw.isPresent()) {
            titleAndCountry += raw.get();
        }

        return "WikiSuccession{" +
                "from=" + from +
                ", to=" + to +
                titleAndCountry +
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
//        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (raw.isPresent()) {
            if (that.getRaw().isPresent()) {
                if (!raw.get().equals(that.getRaw().get())) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (that.getRaw().isPresent()) {
            return false;
        }
        if (titleAndCountries.isPresent()) {
            if (that.getTitleAndCountries().isPresent()) {
                if (!titleAndCountries.get().equals(that.getTitleAndCountries().get())) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (that.getTitleAndCountries().isPresent()) {
            return false;
        }
        if (to != null ? !to.equals(that.to) : that.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (predecessor != null ? predecessor.hashCode() : 0);
        result = 31 * result + (successor != null ? successor.hashCode() : 0);
        return result;
    }
}
