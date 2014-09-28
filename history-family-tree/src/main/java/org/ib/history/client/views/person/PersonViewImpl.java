package org.ib.history.client.views.person;

import org.ib.history.client.views.base.BaseCrudViewImpl;
import org.ib.history.client.views.base.BaseList;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.commons.data.PersonData;

public class PersonViewImpl extends BaseCrudViewImpl<PersonData> implements PersonView {

    @Override
    protected BaseEditor<PersonData> getItemEditor() {
        return new PersonEditor();
    }

    @Override
    protected BaseList<PersonData> getItemList() {
        return new PersonList();
    }
}