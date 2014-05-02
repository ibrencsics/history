package org.ib.history.commons.data;

public class EmpirorDto extends AbstractDto {
    private String name;
    private String alias;
    private String from;
    private String to;
    private CountryDto countryDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public CountryDto getCountryDto() {
        return countryDto;
    }

    public void setCountryDto(CountryDto countryDto) {
        this.countryDto = countryDto;
    }

    @Override
    public String toString() {
        return "EmpirorDto id:" + getId() + ", name:" + getName() + ", alias: " + getAlias();
    }
}
