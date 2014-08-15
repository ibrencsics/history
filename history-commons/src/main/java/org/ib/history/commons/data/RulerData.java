package org.ib.history.commons.data;

import java.util.Set;

public class RulerData extends AbstractData<RulerData> {

    private String alias;
    private String title;
    private Set<Rules> jobs;

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

        for (Rules job : getJobs()) {
            sb.append("\n\t" + job.toString());
        }

        return sb.toString();
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

    public Set<Rules> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Rules> jobs) {
        this.jobs = jobs;
    }


    public static class Rules {

        private Long id;
        private FlexibleDate from;
        private FlexibleDate to;
        private CountryData country;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("Rules{ id=" + getId());
            if (getFrom()!=null)
                sb.append(" from=" + getFrom().toString());
            if (getTo()!=null)
                sb.append(" to=" + getTo().toString());
            if (getCountry()!=null)
                sb.append(" country=" + getCountry().getName());
            sb.append(" }");

            return sb.toString();
        }

        public static class Builder {
            Rules rules = new Rules();

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

            public Rules build() {
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
