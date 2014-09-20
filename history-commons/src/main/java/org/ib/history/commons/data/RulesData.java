package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RulesData implements IsSerializable {

    private Long id;
    private String title;
    private Integer number;
    private FlexibleDate from;
    private FlexibleDate to;

    private PersonData person;
    private CountryData country;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("RulesData{ id=" + getId());
        sb.append(" title=" + getTitle());
        sb.append(" number=" + getNumber());
        if (getFrom()!=null)
            sb.append(" from=" + getFrom().toString());
        if (getTo()!=null)
            sb.append(" to=" + getTo().toString());
        if (getPerson()!=null)
            sb.append(" person id=" + getPerson().getId() + " name=" + getPerson().getName());
        if (getCountry()!=null)
            sb.append(" country id=" + getCountry().getId() + " name=" + getCountry().getName());
        sb.append(" }");

        return sb.toString();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public FlexibleDate getFrom() {
        return from;
    }

    public void setFrom(FlexibleDate from) {
        this.from = from;
    }

    public FlexibleDate getTo() {
        return to;
    }

    public void setTo(FlexibleDate to) {
        this.to = to;
    }

    public PersonData getPerson() {
        return person;
    }

    public void setPerson(PersonData person) {
        this.person = person;
    }

    public CountryData getCountry() {
        return country;
    }

    public void setCountry(CountryData country) {
        this.country = country;
    }


    public static class Builder {
        RulesData rules = new RulesData();

        public Builder id(Long id) {
            rules.setId(id);
            return this;
        }

        public Builder title(String title) {
            rules.setTitle(title);
            return this;
        }

        public Builder number(Integer number) {
            rules.setNumber(number);
            return this;
        }

        public Builder from(FlexibleDate from) {
            rules.setFrom(from);
            return this;
        }

        public Builder to(FlexibleDate to) {
            rules.setTo(to);
            return this;
        }

        public Builder person(PersonData person) {
            rules.setPerson(person);
            return this;
        }

        public Builder country(CountryData country) {
            rules.setCountry(country);
            return this;
        }

        public RulesData build() {
            return rules;
        }
    }
}
