package org.ib.history.commons.data;

public class HouseData extends AbstractData<HouseData> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("HouseData{ id=" + getId() + " name=" + getName() + " }");
        for (String locale : getLocales().keySet()) {
            HouseData localeHouse = getLocales().get(locale);
            sb.append("\n\tid=" + localeHouse.getId() + " name=" + localeHouse.getName() + " lang=" + locale);
        }

        return sb.toString();
    }

    public static class Builder{
        HouseData houseData = new HouseData();

        public Builder id(Long id) {
            houseData.setId(id);
            return this;
        }

        public Builder name(String name) {
            houseData.setName(name);
            return this;
        }

        public Builder locale(String locale, HouseData localeHouseData) {
            houseData.addLocale(locale, localeHouseData);
            return this;
        }

        public HouseData build() {
            return houseData;
        }
    }
}
