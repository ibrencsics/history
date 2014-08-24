package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.presenters.RulerPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.RulerView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.client.widget.RuleEditor;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.*;

public class RulerViewImpl extends BaseCrudViewImpl<RulerData> implements RulerView {

    final String COLUMN_ALIAS = "Alias";
    final String COLUMN_TITLE = "Title";
    final String COLUMN_PERSON = "Person";

    private RuleEditor ruleEditor;

    public RulerViewImpl() {
        ruleEditor = new RuleEditor();
        ruleEditor.setText("Edit Rule");
        addCustomPanel(ruleEditor);
    }

    @Override
    protected ItemEditor<RulerData> getItemEditor() {
        return new ItemEditorImpl();
    }

    @Override
    protected void notifyCustomPanel(RulerData selected) {
        ruleEditor.setSelected(selected);
    }

    @Override
    public void setPresenter(CrudPresenter<RulerData> presenter) {
        super.setPresenter(presenter);
        ruleEditor.setPresenter((RulerPresenter) presenter);
    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnAlias(), buildHeader(COLUMN_ALIAS));
        ctList.addColumn(buildColumnTitle(), buildHeader(COLUMN_TITLE));
        ctList.addColumn(buildColumnPerson(), buildHeader(COLUMN_PERSON));
    }

    private TextColumn<RulerData> buildColumnAlias() {
        TextColumn<RulerData> columnDateOfBirth = new TextColumn<RulerData>() {
            @Override
            public String getValue(RulerData rulerData) {
                return rulerData.getAlias();
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_ALIAS);
        return columnDateOfBirth;
    }

    private TextColumn<RulerData> buildColumnTitle() {
        TextColumn<RulerData> columnDateOfDeath = new TextColumn<RulerData>() {
            @Override
            public String getValue(RulerData rulerData) {
                return rulerData.getTitle();
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_TITLE);
        return columnDateOfDeath;
    }

    private TextColumn<RulerData> buildColumnPerson() {
        TextColumn<RulerData> columnHouse = new TextColumn<RulerData>() {
            @Override
            public String getValue(RulerData rulerData) {
                return rulerData.getPerson().getName();
            }
        };
        columnHouse.setDataStoreName(COLUMN_PERSON);
        return columnHouse;
    }

    private String personDisplayText(PersonData personData) {
        if (personData==null)
            return "";
        return personData.getName() + " (" + GwtDateFormat.convert(personData.getDateOfDeath()) + ")";
    }


    private class ItemEditorImpl extends ItemEditor<RulerData> {

        @Override
        protected RulerData getEmptyItem() {
            return new RulerData.Builder().name("").build();
        }

        @Override
        protected List<String> getHeaders() {
            return Arrays.asList(COLUMN_ALIAS, COLUMN_TITLE, COLUMN_PERSON);
        }

        @Override
        protected List<Widget> getDefaultLocaleWidgets() {
            List<Widget> widgets = new ArrayList<Widget>();

            TextBox tbAlias = new TextBox();
            tbAlias.setText(selectedItem!=null ? selectedItem.getAlias() : "");
            widgets.add(tbAlias);

            TextBox tbTitle = new TextBox();
            tbTitle.setText(selectedItem!=null ? selectedItem.getTitle() : "");
            widgets.add(tbTitle);

            RpcSuggestOracle<PersonData> suggestOracle = new RpcSuggestOracle<PersonData>() {
                @Override
                public void setSuggestions(String pattern, AsyncCallback<List<PersonData>> callback) {
                    ((RulerPresenter)presenter).setPersonSuggestions(pattern, callback);
                }

                @Override
                public String displayString(PersonData selected) {
                    return personDisplayText(selected);
                }

                @Override
                public String replacementString(PersonData selected) {
                    return personDisplayText(selected);
                }
            };
            SuggestBox sbPerson = new SuggestBox(suggestOracle);
            suggestOracle.setSuggestBox(sbPerson);
            suggestOracle.setSelected(selectedItem!=null ? selectedItem.getPerson() : null);
            widgets.add(sbPerson);

            return widgets;
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            List<Widget> widgets = new ArrayList<Widget>(0);

            RulerData selectedItemLocale = selectedItem.getLocale(locale.name());

            TextBox tbAlias = new TextBox();
            tbAlias.setText( selectedItemLocale!=null ? selectedItemLocale.getAlias() : "" );
            widgets.add(tbAlias);

            TextBox tbTitle = new TextBox();
            tbTitle.setText( selectedItemLocale!=null ? selectedItemLocale.getTitle() : "" );
            widgets.add(tbTitle);

            return widgets;
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {
            TextBox tbAlias = (TextBox) widgets.get(0);
            selectedItem.setAlias(tbAlias.getText());

            TextBox tbTitle = (TextBox) widgets.get(1);
            selectedItem.setTitle(tbTitle.getText());

            SuggestBox sbPerson = (SuggestBox) widgets.get(2);
            GWT.log("person selected: " + ((RpcSuggestOracle<PersonData>) sbPerson.getSuggestOracle()).getSelected().toString());
            selectedItem.setPerson(((RpcSuggestOracle<PersonData>) sbPerson.getSuggestOracle()).getSelected());
        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {
            TextBox tbAlias = (TextBox) widgets.get(0);
            selectedItem.getLocale(locale.name()).setAlias(tbAlias.getText());

            TextBox tbTitle = (TextBox) widgets.get(1);
            selectedItem.getLocale(locale.name()).setTitle(tbTitle.getText());
        }

        @Override
        protected void updateCustom() {
            selectedItem.setRules(new HashSet<RulerData.RulesData>(ruleEditor.save()));
        }
    }
}
