package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Pope extends BaseEntityWithTranslation<Pope> {

    private String name;
    private Integer number;
    private String fromDate;
    private String toDate;

    public Pope() {
    }

    public Pope(String name, Integer number, String fromDate, String toDate) {
        this.name = name;
        this.number = number;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
}
