package org.ib.history.client.views.base;

import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.commons.data.AbstractData;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultEditor<T extends AbstractData<T>> extends BaseEditor<T> {

        @Override
        protected abstract T getEmptyItem();

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
        protected Editor<T> getCustomEditor() {
            return null;
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {

        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {

        }
}
