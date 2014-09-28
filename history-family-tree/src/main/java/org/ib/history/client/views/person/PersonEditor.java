package org.ib.history.client.views.person;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.Editor;
import org.ib.history.client.widget.ParentEditor;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonEditor extends BaseEditor<PersonData> {

    final String COLUMN_GENDER = "Gender";
    final String COLUMN_ALIAS = "Alias";
    final String COLUMN_DATE_OF_BIRTH = "Date of birth";
    final String COLUMN_DATE_OF_DEATH = "Date of death";
    final String COLUMN_HOUSE = "House";


    @Override
    protected PersonData getEmptyItem() {
        return new PersonData.Builder().name("").alias("").gender("M").dateOfBirth(null).dateOfDeath(null)
                .house(new HouseData.Builder().name("").build())
                .build();
    }

    @Override
    protected List<String> getHeaders() {
        return Arrays.asList(COLUMN_ALIAS, COLUMN_GENDER, COLUMN_DATE_OF_BIRTH, COLUMN_DATE_OF_DEATH);
    }

    @Override
    protected List<Widget> getDefaultLocaleWidgets() {
        List<Widget> widgets = new ArrayList<Widget>();

        TextBox tbAlias = new TextBox();
        tbAlias.setText(selectedItem.getAlias());
        widgets.add(tbAlias);

        TextBox tbGender = new TextBox();
        tbGender.setText(selectedItem.getGender());
        widgets.add(tbGender);

        TextBox tbDateOfBirth = new TextBox();
        tbDateOfBirth.setText( GwtDateFormat.convert(selectedItem.getDateOfBirth()) );
        widgets.add(tbDateOfBirth);

        TextBox tbDateOfDeath = new TextBox();
        tbDateOfDeath.setText( GwtDateFormat.convert(selectedItem.getDateOfDeath()) );
        widgets.add(tbDateOfDeath);

//            RpcSuggestOracle suggestOracle = new RpcSuggestOracle<HouseData>() {
//                @Override
//                public void setSuggestions(String pattern, AsyncCallback<List<HouseData>> callback) {
//                    ((PersonPresenter)presenter).setHouseSuggestions(pattern, callback);
//                }
//
//                @Override
//                public String displayString(HouseData selected) {
//                    return selected.getName();
//                }
//
//                @Override
//                public String replacementString(HouseData selected) {
//                    return selected.getName();
//                }
//            };
//            SuggestBox sbHouse = new SuggestBox(suggestOracle);
//            suggestOracle.setSuggestBox(sbHouse);
//            suggestOracle.setSelected(selectedItem.getHouse());
//            widgets.add(sbHouse);

        return widgets;
    }

    @Override
    protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
        List<Widget> widgets = new ArrayList<Widget>();

        PersonData selectedItemLocale = selectedItem.getLocale(locale.name());

        TextBox tbAlias = new TextBox();
        tbAlias.setText(selectedItemLocale!=null ? selectedItemLocale.getAlias() : "");
        widgets.add(tbAlias);

        return widgets;
    }

    @Override
    protected Editor<PersonData> getCustomEditor() {
        ParentEditor parentEditor = new ParentEditor();
        parentEditor.setText("Edit parents");
        return parentEditor;
    }

    @Override
    protected void updateDefaultLocale(List<Widget> widgets) {
        TextBox tbAlias = (TextBox) widgets.get(0);
        selectedItem.setAlias(tbAlias.getText());

        TextBox tbGender = (TextBox) widgets.get(1);
        selectedItem.setGender(tbGender.getText());

        TextBox tbDateOfBirth = (TextBox) widgets.get(2);
        selectedItem.setDateOfBirth( GwtDateFormat.convert(tbDateOfBirth.getText()) );

        TextBox tbDateOfDeath = (TextBox) widgets.get(3);
        selectedItem.setDateOfDeath(GwtDateFormat.convert(tbDateOfDeath.getText()));

//        SuggestBox sbHouse = (SuggestBox) widgets.get(4);
//        GWT.log("house selected: " + ((RpcSuggestOracle<HouseData>) sbHouse.getSuggestOracle()).getSelected().toString());
//            selectedItem.setHouse(((RpcSuggestOracle<HouseData>)sbHouse.getSuggestOracle()).getSelected());
    }

    @Override
    protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {
        TextBox tbAlias = (TextBox) widgets.get(0);
        selectedItem.getLocale(locale.name()).setAlias(tbAlias.getText());
    }
}
