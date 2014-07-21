package org.ib.history.client.views.create;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PersonData;

public class PersonAddViewImpl extends Composite {

    private TextBox tbName;
    private TextBox tbBirth;
    private TextBox tbDeath;
    private Button btmSubmit;

    private PersonPresenter presenter;

    public PersonAddViewImpl() {
        FlowPanel panel = new FlowPanel();
        tbName = new TextBox();
        tbBirth = new TextBox();
        tbDeath = new TextBox();
        btmSubmit = new Button("Add");
        panel.add(tbName);
        panel.add(tbBirth);
        panel.add(tbDeath);
        panel.add(btmSubmit);

        initWidget(panel);
    }

    public void setPresenter(PersonPresenter presenter) {
        this.presenter = presenter;
        bind();
    }

    private void bind() {
        btmSubmit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                System.out.println("presenter " + presenter);
                presenter.addPerson(
                        new PersonData.Builder()
                                .name(tbName.getText())
                                .dateOfBirth(new FlexibleDate.Builder().year(2345).build())
                                .dateOfDeath(new FlexibleDate.Builder().year(3456).build())
                                .build());
            }
        });
    }
}
