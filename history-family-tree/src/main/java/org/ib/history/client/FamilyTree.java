package org.ib.history.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ib.history.client.views.WelcomeView;
import org.ib.history.client.views.impl.WelcomeViewImpl;

public class FamilyTree implements EntryPoint {

    SimplePanel container = new SimplePanel();

    public void onModuleLoad() {
        FamilyTreeApp familyTreeApp = new FamilyTreeApp();
//        container.setSize("100%","100%");
//        container.add(new Label("asdasd"));
//        familyTreeApp.go(container);
//        RootLayoutPanel.get().add(container);
        familyTreeApp.go(RootLayoutPanel.get());
//        History.fireCurrentHistoryState();
//        History.newItem(Tokens.HOME);
    }
}
