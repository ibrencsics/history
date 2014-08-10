package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.PersonView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonViewImpl extends BaseCrudViewImpl<PersonData> implements PersonView {

    final String COLUMN_DATE_OF_BIRTH = "Date of birth";
    final String COLUMN_DATE_OF_DEATH = "Date of death";
    final String COLUMN_HOUSE = "House";


    @Override
    protected ItemEditor<PersonData> getItemEditor() {
        return new ItemEditorImpl();
    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnDateOfBirth(), buildHeader(COLUMN_DATE_OF_BIRTH));
        ctList.addColumn(buildColumnDateOfDeath(), buildHeader(COLUMN_DATE_OF_DEATH));
        ctList.addColumn(buildColumnHouse(), buildHeader(COLUMN_HOUSE));
    }

    private TextColumn<PersonData> buildColumnDateOfBirth() {
        TextColumn<PersonData> columnDateOfBirth = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfBirth());
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH);
        return columnDateOfBirth;
    }

    private TextColumn<PersonData> buildColumnDateOfDeath() {
        TextColumn<PersonData> columnDateOfDeath = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfDeath());
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH);
        return columnDateOfDeath;
    }

    private TextColumn<PersonData> buildColumnHouse() {
        TextColumn<PersonData> columnHouse = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return (personData.getHouse()!=null) ? personData.getHouse().getName() : "";
            }
        };
        columnHouse.setDataStoreName(COLUMN_HOUSE);
        return columnHouse;
    }


    private class ItemEditorImpl extends ItemEditor<PersonData> {

        @Override
        protected PersonData getEmptyItem() {
            return new PersonData.Builder().name("").dateOfBirth(null).dateOfDeath(null).build();
        }

        @Override
        protected List<String> getHeaders() {
            return Arrays.asList(COLUMN_DATE_OF_BIRTH, COLUMN_DATE_OF_DEATH, COLUMN_HOUSE);
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

            RpcSuggestOracle suggestOracle = new RpcSuggestOracle<HouseData>() {
                @Override
                public void setSuggestions(String pattern, AsyncCallback<List<HouseData>> callback) {
                    ((PersonPresenter)presenter).setHouseSuggestions(pattern, callback);
                }
            };
            SuggestBox sbHouse = new SuggestBox(suggestOracle);
            suggestOracle.setSuggestBox(sbHouse);
            suggestOracle.setSelected(selectedItem.getHouse());
            widgets.add(sbHouse);

            return widgets;
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            List<Widget> widgets = new ArrayList<Widget>();

//            PersonData selectedItemLocale = selectedItem.getLocale(locale.name());
//
//            TextBox tbDateOfBirth = new TextBox();
//            tbDateOfBirth.setText( selectedItemLocale!=null ? GwtDateFormat.convert(selectedItemLocale.getDateOfBirth()) : "" );
//            widgets.add(tbDateOfBirth);
//
//            TextBox tbDateOfDeath = new TextBox();
//            tbDateOfDeath.setText( selectedItemLocale!=null ? GwtDateFormat.convert(selectedItemLocale.getDateOfDeath()) : "" );
//            widgets.add(tbDateOfDeath);

            return widgets;
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {
            TextBox tbDateOfBirth = (TextBox) widgets.get(0);
            selectedItem.setDateOfBirth( GwtDateFormat.convert(tbDateOfBirth.getText()) );

            TextBox tbDateOfDeath = (TextBox) widgets.get(1);
            selectedItem.setDateOfDeath(GwtDateFormat.convert(tbDateOfDeath.getText()));

            SuggestBox sbHouse = (SuggestBox) widgets.get(2);
            GWT.log("house selected: " + ((RpcSuggestOracle<HouseData>)sbHouse.getSuggestOracle()).getSelected().toString());
            selectedItem.setHouse(((RpcSuggestOracle<HouseData>)sbHouse.getSuggestOracle()).getSelected());
        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {
//            TextBox tbDateOfBirth = (TextBox) widgets.get(0);
//            selectedItem.getLocale(locale.name()).setDateOfBirth( GwtDateFormat.convert(tbDateOfBirth.getText()) );
//
//            TextBox tbDateOfDeath = (TextBox) widgets.get(1);
//            selectedItem.getLocale(locale.name()).setDateOfDeath( GwtDateFormat.convert(tbDateOfDeath.getText()) );
        }
    }
}