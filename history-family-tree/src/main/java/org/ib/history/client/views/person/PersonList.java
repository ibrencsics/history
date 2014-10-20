package org.ib.history.client.views.person;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import org.ib.history.client.utils.Roman;
import org.ib.history.client.views.base.BaseList;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.GwtDateFormat;
import org.ib.history.commons.data.RulesData;

public class PersonList extends BaseList<PersonData> {

    final String COLUMN_GENDER = "Gender";
    final String COLUMN_ALIAS = "Alias";
    final String COLUMN_TITLE = "Title";
    final String COLUMN_DATE_OF_BIRTH = "Date of birth";
    final String COLUMN_DATE_OF_DEATH = "Date of death";


    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnGender(), buildHeader(COLUMN_GENDER));
        ctList.addColumn(buildColumnAlias(), buildHeader(COLUMN_ALIAS));
        ctList.addColumn(buildCOlumnTitle(), buildHeader(COLUMN_TITLE));
        ctList.addColumn(buildColumnDateOfBirth(), buildHeader(COLUMN_DATE_OF_BIRTH));
        ctList.addColumn(buildColumnDateOfDeath(), buildHeader(COLUMN_DATE_OF_DEATH));
    }

    private Column<PersonData, String> buildColumnGender() {
        TextColumn<PersonData> columnGender = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return personData.getGender();
            }
        };
        columnGender.setDataStoreName(COLUMN_GENDER);
        return columnGender;
    }

    private Column<PersonData, String> buildColumnAlias() {
        TextColumn<PersonData> columnAlias = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return personData.getAlias();
            }
        };
        columnAlias.setDataStoreName(COLUMN_ALIAS);
        return columnAlias;
    }

    private Column<PersonData, String> buildCOlumnTitle() {
        TextColumn<PersonData> columnTitle = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                StringBuilder sb = new StringBuilder();

                for (RulesData rules : personData.getRules()) {
                    if (rules.getNumber()!=null) {
                        sb.append(Roman.getRoman(rules.getNumber()));
                        sb.append(". ");
                        sb.append(personData.getName());
                        sb.append(" (");
                        sb.append(rules.getCountry().getName());
                        sb.append(")");
                    }
                }

                return sb.toString();
            }
        };
        columnTitle.setDataStoreName(COLUMN_TITLE);
        return columnTitle;
    }

    private TextColumn<PersonData> buildColumnDateOfBirth() {
        TextColumn<PersonData> columnDateOfBirth = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfBirth());
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH);
        return columnDateOfBirth;
    }

    private TextColumn<PersonData> buildColumnDateOfDeath() {
        TextColumn<PersonData> columnDateOfDeath = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfDeath());
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH);
        return columnDateOfDeath;
    }

}

