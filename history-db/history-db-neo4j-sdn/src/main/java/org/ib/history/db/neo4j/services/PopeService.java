package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PopeData;

import java.util.List;

public interface PopeService {
    List<PopeData> getPopes();
    List<PopeData> getPopesByPattern(String pattern);
    PopeData getPopeById(PopeData popeOnlyId);
    PopeData addPope(PopeData popeData);
    void deletePope(PopeData popeData);
}
