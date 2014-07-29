package org.ib.history.client.views.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;
import org.ib.history.client.views.PersonView;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

public class PersonViewImpl extends BaseCrudViewImpl<PersonData> implements PersonView {

    final String COLUMN_DATE_OF_BIRTH_NAME = "Date of birth";
    final String COLUMN_DATE_OF_DEATH_NAME = "Date of death";

    @Override
    protected void buildEditColumns() {

    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
        ctList.addColumn(buildColumnDateOfBirth(), buildHeader(COLUMN_DATE_OF_BIRTH_NAME));
        ctList.addColumn(buildColumnDateOfDeath(), buildHeader(COLUMN_DATE_OF_DEATH_NAME));
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_EDIT));
        ctList.addColumn(buildColumnDelete(), buildHeader(COLUMN_DELETE));
    }

    private TextColumn<PersonData>  buildColumnDateOfBirth() {
        TextColumn<PersonData> columnDateOfBirth = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfBirth());
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH_NAME);
        return columnDateOfBirth;
    }

    private TextColumn<PersonData>  buildColumnDateOfDeath() {
        TextColumn<PersonData> columnDateOfDeath = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfDeath());
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH_NAME);
        return columnDateOfDeath;
    }

    @Override
    protected PersonData getEmptyItem() {
        return new PersonData.Builder().name("").dateOfBirth(null).dateOfDeath(null).build();
    }
}