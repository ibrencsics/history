package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.views.CountryListView;
import org.ib.history.commons.data.CountryDto;

import java.util.Arrays;
import java.util.List;

public class CountryListViewImpl extends Composite implements CountryListView {

    interface CountryListUiBinder extends UiBinder<Widget, CountryListViewImpl> {}
    private static CountryListUiBinder uiBinder = GWT.create(CountryListUiBinder.class);

    private CountryListPresenter presenter;

    @UiField(provided = true)
    CellTable<CountryDto> cellTable;
    final String COLUMN_NAME_NAME = "Name";
    final String COLUMN_NAME_DELETE = "Delete";

    TextColumn<CountryDto> columnName;
    Column<CountryDto, String> columnDelete;

    ListDataProvider<CountryDto> dataProviderList;

    Header<String> detailFooter;

    @UiField(provided = true)
    SimplePager pager;


    public CountryListViewImpl() {
        cellTable = new CellTable<CountryDto>();
        // Create paging controls.
        pager = new SimplePager();
        pager.setDisplay(cellTable);

        initWidget(uiBinder.createAndBindUi(this));
        cellTable.setSize("100%", "100%");
        this.setSize("100%", "100%");

        buildTable();
        Window.enableScrolling(false);

//        createWithAsyncDataProvider();
//        createWithListDataProvider();
//
//        cellTable.setPageSize(20);
    }

    @Override
    public void setPresenter(CountryListPresenter presenter) {
        this.presenter = presenter;
        createWithAsyncDataProvider();
    }

    private void buildTable() {

        // Create common footer
        detailFooter = new TextHeader("Details");

        // Build each of the 7 columns
        columnName = buildColumnName();
        columnDelete = buildColumnDelete();

        cellTable.addColumn(columnName,  buildHeader(COLUMN_NAME_NAME), buildHeader(COLUMN_NAME_NAME));
        cellTable.addColumn(columnDelete,  buildHeader(COLUMN_NAME_DELETE), detailFooter);

//        cellTable.setColumnWidth(0, 60, Style.Unit.PX);
//        cellTable.setColumnWidth(1, 60, Style.Unit.PX);
    }

    private TextColumn<CountryDto> buildColumnName() {
        columnName = new TextColumn<CountryDto>() {
            @Override
            public String getValue(CountryDto object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME_NAME);
        return columnName;
    }

    private Column<CountryDto, String> buildColumnDelete() {
        columnDelete = new Column<CountryDto, String>(new TextButtonCell()) {
            @Override
            public String getValue(CountryDto object) {
                return "Delete";
            }
        };
        columnDelete.setDataStoreName(COLUMN_NAME_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<CountryDto, String>() {
            @Override
            public void update(int index, CountryDto object, String value) {
                // presenter.onSelectPhotoClicked(object.getId());
                GWT.log(object.getName() + " pressed");
                presenter.deleteCountry(object);
            }
        });
        return columnDelete;
    }

    private Header<String> buildHeader(final String text) {
        Header<String> head = new Header<String>(new TextCell()) {
            @Override
            public String getValue() {
                return text;
            }
        };
        return head;
    }

    private void createWithAsyncDataProvider() {
        ((AsyncDataProvider<CountryDto>)presenter).addDataDisplay(cellTable);
    }
//
    private void createWithListDataProvider() {
        List<CountryDto> theList;

        dataProviderList = new ListDataProvider<CountryDto>();
        dataProviderList.addDataDisplay(cellTable);
        theList = dataProviderList.getList();
        populate(theList, dataProviderList);
        // Keep the size of the list up to date - easier to know that we are
        // showing 51-100 of 2000 etc
        cellTable.setRowCount(theList.size(), true);

        // Add sorting capability that is based on the list
//        addColumnSortListHandling();
    }

    private void populate(final List<CountryDto> theList, final ListDataProvider<CountryDto> dataListProvider) {
        Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand(){
            int counter = 0;

            @Override
            public boolean execute() {
                CountryDto countryDTO = new CountryDto();
                countryDTO.setName("asdsdf");
                theList.addAll(Arrays.asList(countryDTO));
                dataListProvider.refresh();
                return false;
            }
        });
    }

    @Override
    public void refreshGrid() {
        cellTable.setVisibleRangeAndClearData(new Range(0, 25), true);
        cellTable.redraw();
    }
}