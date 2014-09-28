package org.ib.history.client.views.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.widget.FlexTableWrapper;
import org.ib.history.commons.data.AbstractData;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEditor<T extends AbstractData<T>> extends Composite implements Editor<T> {

    private static ItemEditorUiBinder uiBinder = GWT.create(ItemEditorUiBinder.class);

    interface ItemEditorUiBinder extends UiBinder<Widget, BaseEditor> {
    }

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    protected AbsolutePanel customPanel;

    @UiField
    Button btnNew;

    @UiField
    Button btnSave;

    protected T selectedItem;
    protected CrudPresenter<T> presenter;
    protected FlexTableWrapper flexTableWrapper;
    protected Editor<T> customEditor;

    public BaseEditor() {
        customEditor = getCustomEditor();
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * Visualize
     */

    @Override
    public void hide() {
        flexTable.removeAllRows();
        selectedItem = null;
        if (customEditor!=null) {
            customEditor.hide();
        }
    }

    @Override
    public void setSelected(T selectedItem) {
        this.selectedItem = selectedItem;
        if (customEditor!=null)
            customEditor.setSelected(selectedItem);
        GWT.log(selectedItem.toString());
        visualize();
    }

    public void createNewItem(T emptyNewItem) {
        this.selectedItem = emptyNewItem;
        customEditor = getCustomEditor();
        if (customEditor!=null)
            customEditor.setPresenter(presenter);
        visualize();
    }

    private void visualize() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(5);
        headers.add("Lang");
        headers.add("Name");
        headers.addAll(getHeaders());
        flexTableWrapper.addStringRow(headers);

        // default locale
        List<Widget> defaultLocaleRow = new ArrayList<Widget>(5);
        defaultLocaleRow.add(new Label(SupportedLocale.getDefault().name()));

        TextBox tbName = new TextBox();
        tbName.setText(selectedItem != null ? selectedItem.getName() : "");
        defaultLocaleRow.add(tbName);

        defaultLocaleRow.addAll(getDefaultLocaleWidgets());

        flexTableWrapper.addWidgetRow(defaultLocaleRow);

        // locales
        for (SupportedLocale locale : SupportedLocale.getLocalesExceptDefault()) {
            List<Widget> localeRow = new ArrayList<Widget>();

            localeRow.add(new Label(locale.name()));

            T selectedItemLocale = selectedItem!=null ? selectedItem.getLocale(locale.name()) : null;

            tbName = new TextBox();
            tbName.setText(selectedItemLocale != null ? selectedItem.getLocale(locale.name()).getName() : "");
            localeRow.add(tbName);

            localeRow.addAll(getLocaleWidgets(locale));

            flexTableWrapper.addWidgetRow(localeRow);
        }

        // custom

        if (customEditor != null) {
            customPanel.clear();
            customPanel.add(customEditor);
        }
    }

    protected abstract T getEmptyItem();
    protected abstract List<String> getHeaders();
    protected abstract List<Widget> getDefaultLocaleWidgets();
    protected abstract List<Widget> getLocaleWidgets(SupportedLocale locale);
    protected abstract Editor<T> getCustomEditor();

    /**
     * Save
     */

    @Override
    public void save() {

        // default locale
        List<Widget> defaultLocale = flexTableWrapper.getRow(1);
        selectedItem.setName( ((TextBox)defaultLocale.get(1)).getText() );
        updateDefaultLocale(defaultLocale.subList(2, defaultLocale.size()));

        // locales
        int localeNum = 2;
        for (SupportedLocale locale : SupportedLocale.getLocalesExceptDefault()) {
            List<Widget> localeWidgets = flexTableWrapper.getRow(localeNum++);
            TextBox tbName = ((TextBox)localeWidgets.get(1));
            if (tbName.getText()!=null && !tbName.getText().equals("")) {
                if (selectedItem.getLocale(locale.name()) == null) {
                    selectedItem.addLocale(locale.name(), getEmptyItem());
                }
                selectedItem.getLocale(locale.name()).setName( tbName.getText() );
                updateLocale(locale, localeWidgets.subList(2, localeWidgets.size()));
            }
        }

        if (customEditor!=null)
            customEditor.save(selectedItem);

        GWT.log("item to save" + selectedItem.toString());
        presenter.addItem(selectedItem);
    }

    @Override
    public void save(T created) {
        // Not implemented
    }

    protected abstract void updateDefaultLocale(List<Widget> widgets);
    protected abstract void updateLocale(SupportedLocale locale, List<Widget> widgets);


    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
        if (customEditor!=null)
            customEditor.setPresenter(presenter);
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    @UiHandler("btnNew")
    public void newItem(ClickEvent clickEvent) {
        createNewItem(getEmptyItem());
    }

    @UiHandler("btnSave")
    public void saveItem(ClickEvent clickEvent) {
        save();
    }
}