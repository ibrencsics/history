package org.ib.history.client.views.impl;

import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.RulerView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.RulerData;

import java.util.ArrayList;
import java.util.List;

public class RulerViewImpl extends BaseCrudViewImpl<RulerData> implements RulerView {

    @Override
    protected ItemEditor<RulerData> getItemEditor() {
        return new ItemEditorImpl();
    }

    @Override
    protected void buildListColumns() {

    }

    private class ItemEditorImpl extends ItemEditor<RulerData> {

        @Override
        protected RulerData getEmptyItem() {
            return new RulerData.Builder().name("").build();
        }

        @Override
        protected List<String> getHeaders() {
            return new ArrayList<String>(0);
        }

        @Override
        protected List<Widget> getDefaultLocaleWidgets() {
            return new ArrayList<Widget>(0);
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
