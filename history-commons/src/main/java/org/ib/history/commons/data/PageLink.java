package org.ib.history.commons.data;

public class PageLink {

    private final String pageName;
    private final String displayText;

    public PageLink(String pageName) {
        this.pageName = pageName;
        this.displayText = pageName;
    }

    public PageLink(String pageName, String displayText) {
        this.pageName = pageName;
        this.displayText = displayText;
    }

    public String getPageName() {
        return pageName;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return "PageLink{" +
                "p='" + pageName + '\'' +
                (pageName.equals(displayText) ? "" : ", t='" + displayText + '\'') +
                '}';
    }
}
