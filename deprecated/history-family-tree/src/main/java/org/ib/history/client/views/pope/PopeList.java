package org.ib.history.client.views.pope;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import org.ib.history.client.views.base.BaseList;
import org.ib.history.commons.data.GwtDateFormat;
import org.ib.history.commons.data.PopeData;

public class PopeList extends BaseList<PopeData> {

    final String COLUMN_NUMBER = "Number";
    final String COLUMN_FROM = "From";
    final String COLUMN_TO = "To";

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnNumber(), buildHeader(COLUMN_NUMBER));
        ctList.addColumn(buildColumnFrom(), buildHeader(COLUMN_FROM));
        ctList.addColumn(buildColumnTo(), buildHeader(COLUMN_TO));
    }

    private Column<PopeData, String> buildColumnNumber() {
        TextColumn<PopeData> columnNumber = new TextColumn<PopeData>() {
            @Override
            public String getValue(PopeData popeData) {
                return popeData.getNumber() + "";
            }
        };
        columnNumber.setDataStoreName(COLUMN_NUMBER);
        return columnNumber;
    }

    private Column<PopeData, String> buildColumnFrom() {
        TextColumn<PopeData> columnFrom = new TextColumn<PopeData>() {
            @Override
            public String getValue(PopeData popeData) {
                return GwtDateFormat.convert(popeData.getFrom());
            }
        };
        columnFrom.setDataStoreName(COLUMN_FROM);
        return columnFrom;
    }

    private Column<PopeData, String> buildColumnTo() {
        TextColumn<PopeData> columnTo = new TextColumn<PopeData>() {
            @Override
            public String getValue(PopeData popeData) {
                return GwtDateFormat.convert(popeData.getTo());
            }
        };
        columnTo.setDataStoreName(COLUMN_TO);
        return columnTo;
    }
}
