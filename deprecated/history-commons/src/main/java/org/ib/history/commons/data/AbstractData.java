package org.ib.history.commons.data;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashMap;
import java.util.Map;

public class AbstractData<T extends AbstractData> implements IsSerializable {

    private Long id;
    private Map<String,T> locales;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, T> getLocales() {
        if (locales==null) {
            locales = new HashMap<String,T>();
        }
        return locales;
    }

    public void setLocales(Map<String, T> locales) {
        this.locales = locales;
    }

    public void addLocale(String locale, T data) {
        getLocales().put(locale, data);
    }

    public T removeLocale(String locale) {
        return getLocales().remove(locale);
    }

    public T removeLocale(T localeToRemove) {
        String localeFound = null;
        for (Map.Entry<String, T> locale: getLocales().entrySet()) {
            if (locale.getValue().equals(localeToRemove))
                localeFound = locale.getKey();
        }

        if (localeFound!=null) {
            return removeLocale(localeFound);
        }
        return null;
    }

    public void removeLocals() {
        getLocales().clear();
    }

    public T getLocale(String locale) {
        return getLocales().get(locale);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
