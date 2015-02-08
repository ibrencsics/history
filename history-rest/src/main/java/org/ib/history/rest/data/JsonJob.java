package org.ib.history.rest.data;

import java.util.List;

public class JsonJob {
    private String title;
    private String from;
    private String to;
    private JsonPerson predecessor;
    private JsonPerson succecessor;
    private List<JsonCountry> countries;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public JsonPerson getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(JsonPerson predecessor) {
        this.predecessor = predecessor;
    }

    public JsonPerson getSuccecessor() {
        return succecessor;
    }

    public void setSuccecessor(JsonPerson succecessor) {
        this.succecessor = succecessor;
    }

    public List<JsonCountry> getCountries() {
        return countries;
    }

    public void setCountries(List<JsonCountry> countries) {
        this.countries = countries;
    }
}
