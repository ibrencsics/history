package org.ib.history.client.views.impl;

import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {

    @Override
    protected void buildEditColumns() {
        localeProvider.getList().add(new LocaleWrapper("EN", getEmptyItem()));
        ctEdit.addColumn(buildEditableColumnLang(), buildHeader(COLUMN_LANG));
        ctEdit.addColumn(buildEditableColumnName(), buildHeader(COLUMN_NAME));
        ctEdit.addColumn(buildEditableColumnDelete(), buildHeader(COLUMN_DELETE));
    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_EDIT));
        ctList.addColumn(buildColumnDelete(), buildHeader(COLUMN_DELETE));
    }

    @Override
    protected CountryData getEmptyItem() {
        return new CountryData.Builder().name("").build();
    }
}