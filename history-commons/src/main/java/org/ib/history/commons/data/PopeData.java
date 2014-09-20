package org.ib.history.commons.data;

public class PopeData extends AbstractData<PopeData> {

    private Long id;
    private String name;
    private Integer number;
    private FlexibleDate from;
    private FlexibleDate to;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PopeData{ id=" + getId());
        sb.append(" name=" + getName());
        sb.append(" number=" + getNumber());
        if (getFrom()!=null)
            sb.append(" from=" + getFrom().toString());
        if (getTo()!=null)
            sb.append(" to=" + getTo().toString());
        sb.append(" }");

        return sb.toString();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public static class Builder {
        private PopeData pope = new PopeData();

        public Builder id(Long id) {
            pope.setId(id);
            return this;
        }

        public Builder name(String name) {
            pope.setName(name);
            return this;
        }

        public Builder number(Integer number) {
            pope.setNumber(number);
            return this;
        }

        public Builder from(FlexibleDate from) {
            pope.setFrom(from);
            return this;
        }

        public Builder to(FlexibleDate to) {
            pope.setTo(to);
            return this;
        }

        public PopeData build() {
            return pope;
        }
    }
}
