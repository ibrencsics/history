package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
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
        ctEdit.addColumn(buildEditableColumnDateOfBirth(), COLUMN_DATE_OF_BIRTH_NAME);
        ctEdit.addColumn(buildEditableColumnDateOfDeath(), COLUMN_DATE_OF_DEATH_NAME);
    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnDateOfBirth(), buildHeader(COLUMN_DATE_OF_BIRTH_NAME));
        ctList.addColumn(buildColumnDateOfDeath(), buildHeader(COLUMN_DATE_OF_DEATH_NAME));
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

    private Column<LocaleWrapper<PersonData>,String> buildEditableColumnDateOfBirth() {
        Column<LocaleWrapper<PersonData>,String> columnDateOfBirth = new Column<LocaleWrapper<PersonData>, String>(new EditTextCell()) {
            @Override
            public String getValue(LocaleWrapper<PersonData> localeWrapper) {
                if (localeWrapper.getItem().getDateOfBirth()==null)
                    return "";
                else
                    return GwtDateFormat.convert(localeWrapper.getItem().getDateOfBirth());
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH_NAME);
        columnDateOfBirth.setFieldUpdater(new FieldUpdater<LocaleWrapper<PersonData>, String>() {
            @Override
            public void update(int i, LocaleWrapper<PersonData> personDataLocaleWrapper, String newDate) {
                if (newDate==null || newDate.equals(""))
                    personDataLocaleWrapper.getItem().setDateOfBirth(null);
                else
                    personDataLocaleWrapper.getItem().setDateOfBirth( GwtDateFormat.convert(newDate) );
            }
        });
        return columnDateOfBirth;
    }

    private Column<LocaleWrapper<PersonData>,String> buildEditableColumnDateOfDeath() {
        Column<LocaleWrapper<PersonData>,String> columnDateOfDeath = new Column<LocaleWrapper<PersonData>, String>(new EditTextCell()) {
            @Override
            public String getValue(LocaleWrapper<PersonData> localeWrapper) {
                if (localeWrapper.getItem().getDateOfDeath()==null)
                    return "";
                else
                    return GwtDateFormat.convert(localeWrapper.getItem().getDateOfDeath());
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH_NAME);
        columnDateOfDeath.setFieldUpdater(new FieldUpdater<LocaleWrapper<PersonData>, String>() {
            @Override
            public void update(int i, LocaleWrapper<PersonData> personDataLocaleWrapper, String newDate) {
                if (newDate==null || newDate.equals(""))
                    personDataLocaleWrapper.getItem().setDateOfDeath(null);
                else
                    personDataLocaleWrapper.getItem().setDateOfDeath( GwtDateFormat.convert(newDate) );
            }
        });
        return columnDateOfDeath;
    }

    @Override
    protected PersonData getEmptyItem() {
        return new PersonData.Builder().name("").dateOfBirth(null).dateOfDeath(null).build();
    }
}