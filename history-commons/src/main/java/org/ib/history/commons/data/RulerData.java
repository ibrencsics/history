package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.HashSet;
import java.util.Set;

public class RulerData extends AbstractData<RulerData> {

    private String alias;
    private String title;
    private Set<RulesData> rules = new HashSet<RulesData>();
    private PersonData person;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ruler{ id=" + getId() + " name=" + getName());
        if (getAlias()!=null)
            sb.append(" alias=" + getAlias());
        if (getTitle()!=null)
            sb.append(" title=" + getTitle());
        sb.append(" }");

        for (String locale : getLocales().keySet()) {
            RulerData localeRuler = getLocales().get(locale);
            sb.append("\n\t lang=" + locale +  " " + localeRuler.toString());
        }

        for (RulesData rule : getRules()) {
            sb.append("\n\t" + rule.toString());
        }

        if (getPerson() != null) {
            sb.append("\n\t person id=" + getPerson().getId() + " name=" + getPerson().getName());
        }

        return sb.toString();
    }

    public static class Builder {
        RulerData rulerData = new RulerData();

        public Builder id(Long id) {
            rulerData.setId(id);
            return this;
        }

        public Builder name(String name) {
            rulerData.setName(name);
            return this;
        }

        public Builder alias(String alias) {
            rulerData.setAlias(alias);
            return this;
        }

        public Builder title(String title) {
            rulerData.setTitle(title);
            return this;
        }

        public Builder rule(RulesData rule) {
            rulerData.getRules().add(rule);
            return this;
        }

        public Builder person(PersonData person) {
            rulerData.setPerson(person);
            return this;
        }

        public Builder locale(String locale, RulerData localeRulerData) {
            rulerData.addLocale(locale, localeRulerData);
            return this;
        }

        public RulerData build() {
            return rulerData;
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<RulesData> getRules() {
        return rules;
    }

    public void setRules(Set<RulesData> rules) {
        this.rules = rules;
    }

    public PersonData getPerson() {
        return person;
    }

    public void setPerson(PersonData person) {
        this.person = person;
    }


    public static class RulesData implements IsSerializable {

        private Long id;
        private FlexibleDate from;
        private FlexibleDate to;
        private CountryData country;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("RulesData{ id=" + getId());
            if (getFrom()!=null)
                sb.append(" from=" + getFrom().toString());
            if (getTo()!=null)
                sb.append(" to=" + getTo().toString());
            if (getCountry()!=null)
                sb.append(" country id=" + getCountry().getId() + " name=" + getCountry().getName());
            sb.append(" }");

            return sb.toString();
        }

        public static class Builder {
            RulesData rules = new RulesData();

            public Builder id(Long id) {
                rules.setId(id);
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

            public Builder country(CountryData country) {
                rules.setCountry(country);
                return this;
            }

            public RulesData build() {
                return rules;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public CountryData getCountry() {
            return country;
        }

        public void setCountry(CountryData country) {
            this.country = country;
        }
    }
}
