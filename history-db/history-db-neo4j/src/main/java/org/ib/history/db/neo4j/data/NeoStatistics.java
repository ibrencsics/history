package org.ib.history.db.neo4j.data;

public class NeoStatistics {

    private Long personNodeCount;
    private Long countryNodeCount;
    private Long houseNodeCount;

    private Long personNodeFailureCount;

    public Long getPersonNodeCount() {
        return personNodeCount;
    }

    public void setPersonNodeCount(Long personNodeCount) {
        this.personNodeCount = personNodeCount;
    }

    public Long getCountryNodeCount() {
        return countryNodeCount;
    }

    public void setCountryNodeCount(Long countryNodeCount) {
        this.countryNodeCount = countryNodeCount;
    }

    public Long getHouseNodeCount() {
        return houseNodeCount;
    }

    public void setHouseNodeCount(Long houseNodeCount) {
        this.houseNodeCount = houseNodeCount;
    }

    public Long getPersonNodeFailureCount() {
        return personNodeFailureCount;
    }

    public void setPersonNodeFailureCount(Long personNodeFailureCount) {
        this.personNodeFailureCount = personNodeFailureCount;
    }
}
