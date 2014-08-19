package org.ib.history.client.views.impl;

import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.RulerView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.RulerData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RulerViewImpl extends BaseCrudViewImpl<RulerData> implements RulerView {

    final String COLUMN_ALIAS = "Alias";
    final String COLUMN_TITLE = "Title";
    final String COLUMN_PERSON = "Person";

    @Override
    protected ItemEditor<RulerData> getItemEditor() {
        return new ItemEditorImpl();
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
                // TODO: call backendService.getPerson(rulerData);
                return "";
            }
        };
        columnHouse.setDataStoreName(COLUMN_PERSON);
        return columnHouse;
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
            widgets.add(tbAlias);

            TextBox tbTitle = new TextBox();
            widgets.add(tbTitle);

            return widgets;
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            return new ArrayList<Widget>(0);
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
