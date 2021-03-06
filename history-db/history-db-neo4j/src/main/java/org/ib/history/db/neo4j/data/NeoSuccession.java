package org.ib.history.db.neo4j.data;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.utils.Neo4jDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NeoSuccession {
    private String title;
    private Optional<FlexibleDate> from = Optional.empty();
    private Optional<FlexibleDate> to = Optional.empty();
    private List<NeoCountry> countries = new ArrayList<>(1);
    private Optional<NeoPerson> predecessor = Optional.empty();
    private Optional<NeoPerson> successor = Optional.empty();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<FlexibleDate> getFrom() {
        return from;
    }

    public void setFrom(FlexibleDate from) {
        this.from = Optional.ofNullable(from);
    }

    public Optional<FlexibleDate> getTo() {
        return to;
    }

    public void setTo(FlexibleDate to) {
        this.to = Optional.ofNullable(to);
    }

    public void addCountry(NeoCountry country) {
        countries.add(country);
    }

    public List<NeoCountry> getCountries() {
        return Collections.unmodifiableList(countries);
    }

    public Optional<NeoPerson> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(NeoPerson predecessor) {
        this.predecessor = Optional.ofNullable(predecessor);
    }

    public Optional<NeoPerson> getSuccessor() {
        return successor;
    }

    public void setSuccessor(NeoPerson successor) {
        this.successor = Optional.ofNullable(successor);
    }

    @Override
    public String toString() {
        return "NeoSuccession{" +
                "title='" + title +
                ", from=" + (from.isPresent() ? Neo4jDateFormat.dateWrapperToString(from.get()) : "") +
                ", to=" + (to.isPresent() ? Neo4jDateFormat.dateWrapperToString(to.get()) : "") +
                ", countries=" + countries.stream().map(c -> c.getName()).collect(Collectors.joining(", ")) +
                ", predecessor=" + (predecessor.isPresent() ? predecessor.get().toWikiNameResourseString() : "") +
                ", successor=" + (successor.isPresent() ? successor.get().toWikiNameResourseString() : "") +
                '}';
    }
}
