package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class AbstractEntity {

    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (id == null || obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return id.equals(((AbstractEntity) obj).id);

    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
