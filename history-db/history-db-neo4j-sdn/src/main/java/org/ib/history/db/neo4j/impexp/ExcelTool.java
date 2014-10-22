package org.ib.history.db.neo4j.impexp;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.ib.history.commons.data.*;
import org.ib.history.db.neo4j.services.CountryService;
import org.ib.history.db.neo4j.services.HouseService;
import org.ib.history.db.neo4j.services.PersonService;
import org.ib.history.db.neo4j.services.PopeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelTool {

    private static final String APPCTX_TO_BACKUP_FROM = "sdn-ApplicationContext.xml";

    private XSSFWorkbook workbook = new XSSFWorkbook();
    private ApplicationContext context = new ClassPathXmlApplicationContext(APPCTX_TO_BACKUP_FROM);

    Map<Long,Long> countryIdMap = new HashMap<>();
    Map<Long,Long> houseIdMap = new HashMap<>();
    Map<Long,Long> personIdMap = new HashMap<>();
    Map<Long,Long> popeIdMap = new HashMap<>();

    public static void main(String[] args) {
        ExcelTool excelTool = new ExcelTool();
//        excelTool.export();
        excelTool.createCountrySheet();
        excelTool.createHouseSheet();
        excelTool.createPopeSheet();
        excelTool.createPersonSheet();
        excelTool.saveExcel();
    }

    private void export() {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[] {1, "Amit", "Shukla"});
        data.put("3", new Object[] {2, "Lokesh", "Gupta"});
        data.put("4", new Object[] {3, "John", "Adwards"});
        data.put("5", new Object[] {4, "Brian", "Schultz"});

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }

    }

    private void createCountrySheet() {
        XSSFSheet sheet = workbook.createSheet("Country");

        int rownum = 0;
        CountryService countryService = context.getBean(CountryService.class);

        Long newCountryId = 1L;
        for (CountryData country : countryService.getCountries()) {
            countryIdMap.put(country.getId(), newCountryId);

            Row row = sheet.createRow(rownum++);
            Cell cell;
            CountryData locale;
            int columnnum = 0;

            cell = row.createCell(columnnum++);
            cell.setCellValue(newCountryId++);

            cell = row.createCell(columnnum++);
            cell.setCellValue(country.getName());

            cell = row.createCell(columnnum++);
            locale = country.getLocale("DE");
            cell.setCellValue(locale!=null ? locale.getName() : "");

            cell = row.createCell(columnnum++);
            locale = country.getLocale("HU");
            cell.setCellValue(locale!=null ? locale.getName() : "");
        }
    }

    private void createHouseSheet() {
        XSSFSheet sheet = workbook.createSheet("House");

        int rownum = 0;
        HouseService houseService = context.getBean(HouseService.class);

        Long newHouseId = 1L;
        for (HouseData house : houseService.getHouses()) {
            houseIdMap.put(house.getId(), newHouseId);

            Row row = sheet.createRow(rownum++);
            Cell cell;
            HouseData locale;
            int columnnum = 0;

            cell = row.createCell(columnnum++);
            cell.setCellValue(newHouseId++);

            cell = row.createCell(columnnum++);
            cell.setCellValue(house.getName());

            cell = row.createCell(columnnum++);
            locale = house.getLocale("DE");
            cell.setCellValue(locale != null ? locale.getName() : "");

            cell = row.createCell(columnnum++);
            locale = house.getLocale("HU");
            cell.setCellValue(locale!=null ? locale.getName() : "");
        }
    }

    private void createPopeSheet() {
        XSSFSheet sheet = workbook.createSheet("Pope");

        int rownum = 0;
        PopeService popeService = context.getBean(PopeService.class);

        Long newPopeId = 1L;
        for (PopeData pope : popeService.getPopes()) {
            popeIdMap.put(pope.getId(), newPopeId);

            Row row = sheet.createRow(rownum++);
            Cell cell;
            PopeData locale;
            int columnnum = 0;

            cell = row.createCell(columnnum++);
            cell.setCellValue(newPopeId++);

            cell = row.createCell(columnnum++);
            cell.setCellValue(pope.getName());

            cell = row.createCell(columnnum++);
            cell.setCellValue(pope.getNumber());

            cell = row.createCell(columnnum++);
            cell.setCellValue(GwtDateFormat.convert(pope.getFrom()));

            cell = row.createCell(columnnum++);
            cell.setCellValue(GwtDateFormat.convert(pope.getTo()));

            cell = row.createCell(columnnum++);
            locale = pope.getLocale("DE");
            cell.setCellValue(locale != null ? locale.getName() : "");

            cell = row.createCell(columnnum++);
            locale = pope.getLocale("HU");
            cell.setCellValue(locale!=null ? locale.getName() : "");
        }
    }

    private void createPersonSheet() {
        XSSFSheet sheet = workbook.createSheet("Person");

        int rownum = 0;
        PersonService personService = context.getBean(PersonService.class);

        Long newPersonId = 1L;
        for (PersonData person : personService.getPersons()) {
            personIdMap.put(person.getId(), newPersonId++);
        }

        for (PersonData person : personService.getPersons()) {
            Row row = sheet.createRow(rownum++);
            Cell cell;
            PersonData locale;
            int columnnum=0;

            cell = row.createCell(columnnum++);
            cell.setCellValue(personIdMap.get(person.getId()));

            cell = row.createCell(columnnum++);
            cell.setCellValue(person.getName());

            cell = row.createCell(columnnum++);
            cell.setCellValue(person.getAlias());

            cell = row.createCell(columnnum++);
            cell.setCellValue(person.getGender());

            cell = row.createCell(columnnum++);
            cell.setCellValue(GwtDateFormat.convert(person.getDateOfBirth()));

            cell = row.createCell(columnnum++);
            cell.setCellValue(GwtDateFormat.convert(person.getDateOfDeath()));

            cell = row.createCell(columnnum++);
            locale = person.getLocale("DE");
            cell.setCellValue(locale!=null ? locale.getName() : "");

            cell = row.createCell(columnnum++);
            cell.setCellValue(locale!=null ? locale.getAlias() : "");

            cell = row.createCell(columnnum++);
            locale = person.getLocale("HU");
            cell.setCellValue(locale!=null ? locale.getName() : "");

            cell = row.createCell(columnnum++);
            cell.setCellValue(locale!=null ? locale.getAlias() : "");


            StringBuilder sb;

            if (person.getParents()!=null) {
                sb = new StringBuilder();

                for (PersonData parent : person.getParents()) {
                    sb.append(personIdMap.get(parent.getId()));
                    sb.append(" ");
                }

                cell = row.createCell(columnnum++);
                cell.setCellValue(sb.toString());
            }

            if (person.getHouses()!=null) {
                sb = new StringBuilder();

                for (HouseData house : person.getHouses()) {
                    sb.append(houseIdMap.get(house.getId()));
                    sb.append(" ");
                }

                cell = row.createCell(columnnum++);
                cell.setCellValue(sb.toString());
            }

            if (person.getRules()!=null) {
                sb = new StringBuilder();

                for (RulesData rules : person.getRules()) {
                    sb.append("(");
                    if (rules.getFrom()!=null && rules.getFrom().getYear()!=0) {
                        sb.append("from=");
                        sb.append(GwtDateFormat.convert(rules.getFrom()));
                    }
                    if (rules.getTo()!=null && rules.getTo().getYear()!=0) {
                        sb.append(" to=");
                        sb.append(GwtDateFormat.convert(rules.getTo()));
                    }
                    if (rules.getNumber()!=null) {
                        sb.append(" number=");
                        sb.append(rules.getNumber());
                    }
                    sb.append(" title=");
                    sb.append(rules.getTitle());
                    sb.append(" country=" + countryIdMap.get(rules.getCountry().getId()));
                    sb.append(")");
                }

                cell = row.createCell(columnnum++);
                cell.setCellValue(sb.toString());
            }

            if (person.getSpouses()!=null) {
                sb = new StringBuilder();

                for (SpouseData spouse : person.getSpouses()) {
                    sb.append("(");

                    if (spouse.getFrom()!=null && spouse.getFrom().getYear()!=0) {
                        sb.append("from=");
                        sb.append(GwtDateFormat.convert(spouse.getFrom()));
                    }

                    if (spouse.getTo()!=null && spouse.getTo().getYear()!=0) {
                        sb.append(" to=");
                        sb.append(GwtDateFormat.convert(spouse.getTo()));
                    }

                    sb.append(" p1=");
                    sb.append(personIdMap.get(spouse.getPerson1().getId()));
                    sb.append(" p2=");
                    sb.append(personIdMap.get(spouse.getPerson2().getId()));

                    sb.append(")");
                }

                cell = row.createCell(columnnum++);
                cell.setCellValue(sb.toString());
            }

            if (person.getPope()!=null) {
                cell = row.createCell(columnnum++);
                cell.setCellValue(popeIdMap.get(person.getPope().getId()));
            }
        }
    }

    private void saveExcel() {
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("history-0.3.0.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("history.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
