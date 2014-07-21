package org.ib.history.client.presenters;

import org.ib.history.commons.data.PersonData;

public interface PersonPresenter extends Presenter {
    void addPerson(PersonData personData);
    void deletePerson(PersonData personData);
}
