package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.PersonView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonViewImpl extends BaseCrudViewImpl<PersonData> implements PersonView {

    final String COLUMN_DATE_OF_BIRTH_NAME = "Date of birth";
    final String COLUMN_DATE_OF_DEATH_NAME = "Date of death";


    @Override
    protected ItemEditor<PersonData> getItemEditor() {
        return new ItemEditorImpl();
    }

//    @Override
//    protected void buildEditColumns() {
//        ctEdit.addColumn(buildEditableColumnDateOfBirth(), COLUMN_DATE_OF_BIRTH_NAME);
//        ctEdit.addColumn(buildEditableColumnDateOfDeath(), COLUMN_DATE_OF_DEATH_NAME);
//    }

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

//    private Column<LocaleWrapper<PersonData>,String> buildEditableColumnDateOfBirth() {
//        Column<LocaleWrapper<PersonData>,String> columnDateOfBirth = new Column<LocaleWrapper<PersonData>, String>(new EditTextCell()) {
//            @Override
//            public String getValue(LocaleWrapper<PersonData> localeWrapper) {
//                if (localeWrapper.getItem().getDateOfBirth()==null)
//                    return "";
//                else
//                    return GwtDateFormat.convert(localeWrapper.getItem().getDateOfBirth());
//            }
//        };
//        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH_NAME);
//        columnDateOfBirth.setFieldUpdater(new FieldUpdater<LocaleWrapper<PersonData>, String>() {
//            @Override
//            public void update(int i, LocaleWrapper<PersonData> personDataLocaleWrapper, String newDate) {
//                if (newDate==null || newDate.equals(""))
//                    personDataLocaleWrapper.getItem().setDateOfBirth(null);
//                else
//                    personDataLocaleWrapper.getItem().setDateOfBirth( GwtDateFormat.convert(newDate) );
//            }
//        });
//        return columnDateOfBirth;
//    }
//
//    private Column<LocaleWrapper<PersonData>,String> buildEditableColumnDateOfDeath() {
//        Column<LocaleWrapper<PersonData>,String> columnDateOfDeath = new Column<LocaleWrapper<PersonData>, String>(new EditTextCell()) {
//            @Override
//            public String getValue(LocaleWrapper<PersonData> localeWrapper) {
//                if (localeWrapper.getItem().getDateOfDeath()==null)
//                    return "";
//                else
//                    return GwtDateFormat.convert(localeWrapper.getItem().getDateOfDeath());
//            }
//        };
//        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH_NAME);
//        columnDateOfDeath.setFieldUpdater(new FieldUpdater<LocaleWrapper<PersonData>, String>() {
//            @Override
//            public void update(int i, LocaleWrapper<PersonData> personDataLocaleWrapper, String newDate) {
//                if (newDate==null || newDate.equals(""))
//                    personDataLocaleWrapper.getItem().setDateOfDeath(null);
//                else
//                    personDataLocaleWrapper.getItem().setDateOfDeath( GwtDateFormat.convert(newDate) );
//            }
//        });
//        return columnDateOfDeath;
//    }

    @Override
    protected PersonData getEmptyItem() {
        return new PersonData.Builder().name("").dateOfBirth(null).dateOfDeath(null).build();
    }

    private class ItemEditorImpl extends ItemEditor<PersonData> {

        @Override
        protected List<String> getHeaders() {
            return Arrays.asList(COLUMN_DATE_OF_BIRTH_NAME, COLUMN_DATE_OF_DEATH_NAME);
        }

        @Override
        protected List<Widget> getDefaultLocaleWidgets() {
            List<Widget> widgets = new ArrayList<Widget>();

            TextBox tbDateOfBirth = new TextBox();
            tbDateOfBirth.setText( GwtDateFormat.convert(selectedItem.getDateOfBirth()) );
            widgets.add(tbDateOfBirth);

            TextBox tbDateOfDeath = new TextBox();
            tbDateOfDeath.setText( GwtDateFormat.convert(selectedItem.getDateOfDeath()) );
            widgets.add(tbDateOfDeath);

            return widgets;
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            List<Widget> widgets = new ArrayList<Widget>();

            PersonData selectedItemLocale = selectedItem.getLocale(locale.name());

            TextBox tbDateOfBirth = new TextBox();
            tbDateOfBirth.setText( selectedItemLocale!=null ? GwtDateFormat.convert(selectedItemLocale.getDateOfBirth()) : "" );
            widgets.add(tbDateOfBirth);

            TextBox tbDateOfDeath = new TextBox();
            tbDateOfDeath.setText( selectedItemLocale!=null ? GwtDateFormat.convert(selectedItemLocale.getDateOfDeath()) : "" );
            widgets.add(tbDateOfDeath);

            return widgets;
        }
    }
}