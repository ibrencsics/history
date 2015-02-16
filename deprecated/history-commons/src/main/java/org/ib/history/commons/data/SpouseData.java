package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SpouseData implements IsSerializable {

    private Long id;
    private FlexibleDate from;
    private FlexibleDate to;
    private PersonData person1;
    private PersonData person2;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SpouseData{ id=" + getId());
        if (getFrom()!=null)
            sb.append(" from=" + getFrom().toString());
        if (getTo()!=null)
            sb.append(" to=" + getTo().toString());
        if (getPerson1()!=null)
            sb.append(" person1 id=" + getPerson1().getId() + " name=" + getPerson1().getName());
        if (getPerson2()!=null)
            sb.append(" person2 id=" + getPerson2().getId() + " name=" + getPerson2().getName());
        sb.append(" }");

        return sb.toString();
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

    public PersonData getPerson1() {
        return person1;
    }

    public void setPerson1(PersonData person1) {
        this.person1 = person1;
    }

    public PersonData getPerson2() {
        return person2;
    }

    public void setPerson2(PersonData person2) {
        this.person2 = person2;
    }


    public static class Builder {
        SpouseData spouse = new SpouseData();

        public Builder id(Long id) {
            spouse.setId(id);
            return this;
        }

        public Builder from(FlexibleDate from) {
            spouse.setFrom(from);
            return this;
        }

        public Builder to(FlexibleDate to) {
            spouse.setTo(to);
            return this;
        }

        public Builder person1(PersonData person) {
            spouse.setPerson1(person);
            return this;
        }

        public Builder person2(PersonData person) {
            spouse.setPerson2(person);
            return this;
        }

        public SpouseData build() {
            return spouse;
        }
    }
}