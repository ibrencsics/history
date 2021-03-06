package org.ib.history.db.neo4j.impexp;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.db.neo4j.services.CountryService;
import org.ib.history.db.neo4j.services.HouseService;
import org.ib.history.db.neo4j.services.PersonService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class ImpExpTool {

    private static final String BACKUP_FOLDER = "/home/ivan/Projects/Experiments/github/history/history-db/history-db-neo4j-sdn/docs/export/";
    private static final String BACKUP_COUNTRY = "countries.txt";
    private static final String BACKUP_HOUSE = "houses.txt";
    private static final String BACKUP_PERSON = "persons.txt";
    private static final String BACKUP_RULER = "rulers.txt";
    private static final String RESTORE_FOLDER = "/home/ivan/history-neo4j-data";

    private static final String APPCTX_TO_BACKUP_FROM = "sdn-ApplicationContext.xml";
    private static final String APPCTX_TO_RESTORE_TO = "sdn-ApplicationContext.xml";

    Map<Long, Long> countryIdMap = new HashMap<>();
    Map<Long, Long> houseIdMap = new HashMap<>();
    Map<Long, Long> personIdMap = new HashMap<>();
    Map<Long, PersonData> personMap = new HashMap<>();


    public static void main(String[] args) throws IOException {
        new ImpExpTool().exportToFile();
//        new ImpExpTool().importFromFile();
    }

    public void exportToFile() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPCTX_TO_BACKUP_FROM);
        Gson gson = new Gson();

        File dir = new File(BACKUP_FOLDER);
        for(File file: dir.listFiles()) file.delete();

        BufferedWriter out = new BufferedWriter(new FileWriter(BACKUP_FOLDER + BACKUP_COUNTRY));
        StringBuilder sb = new StringBuilder();
        CountryService countryService = context.getBean(CountryService.class);
        for (CountryData country : countryService.getCountries()) {
            sb.append(gson.toJson(country));
            sb.append("\r\n");
        }
        out.write(sb.toString());
        out.close();

        out = new BufferedWriter(new FileWriter(BACKUP_FOLDER + BACKUP_HOUSE));
        sb = new StringBuilder();
        HouseService houseService = context.getBean(HouseService.class);
        for (HouseData house : houseService.getHouses()) {
            sb.append(gson.toJson(house));
            sb.append("\r\n");
        }
        out.write(sb.toString());
        out.close();

        out = new BufferedWriter(new FileWriter(BACKUP_FOLDER + BACKUP_PERSON));
        sb = new StringBuilder();
        PersonService personService = context.getBean(PersonService.class);
        for (PersonData person : personService.getPersons()) {
            sb.append(gson.toJson(person));
            sb.append("\r\n");
        }
        out.write(sb.toString());
        out.close();

        out = new BufferedWriter(new FileWriter(BACKUP_FOLDER + BACKUP_RULER));
        sb = new StringBuilder();
//        RulerService rulerService = context.getBean(RulerService.class);
//        for (RulerData ruler : rulerService.getAllRulers()) {
//            sb.append(gson.toJson(ruler));
//            sb.append("\r\n");
//        }
        out.write(sb.toString());
        out.close();
    }

    private void importFromFile() throws IOException {
        File dir = new File(RESTORE_FOLDER);
        if (dir.exists())
            for(File file: dir.listFiles()) file.delete();

        Gson gson = new Gson();
        ApplicationContext context = new ClassPathXmlApplicationContext(APPCTX_TO_RESTORE_TO);

        GraphDatabaseService graphDb = context.getBean(GraphDatabaseService.class);

        try ( Transaction tx = graphDb.beginTx() ) {
            importCountries(gson, context);
            importHouses(gson, context);
            importPersons(gson, context);

            tx.success();
//            tx.failure();
        }
    }

    private void importCountries(Gson gson, ApplicationContext context) throws IOException {
        CountryService countryService = context.getBean(CountryService.class);

        BufferedReader in = new BufferedReader(new FileReader(BACKUP_FOLDER + BACKUP_COUNTRY));

        while (in.ready()) {
            String s = in.readLine();

            CountryData countryData = gson.fromJson(s, CountryData.class);

            Long oldId = countryData.getId();
            countryData.setId(null);
            for (CountryData locale : countryData.getLocales().values()) {
                locale.setId(null);
            }

            CountryData countryDataCreated = countryService.addCountry(countryData);
            countryIdMap.put(oldId, countryDataCreated.getId());
        }
        in.close();
    }

    private void importHouses(Gson gson, ApplicationContext context) throws IOException {
        HouseService houseService = context.getBean(HouseService.class);

        BufferedReader in = new BufferedReader(new FileReader(BACKUP_FOLDER + BACKUP_HOUSE));

        while (in.ready()) {
            String s = in.readLine();

            HouseData houseData = gson.fromJson(s, HouseData.class);

            Long oldId = houseData.getId();
            houseData.setId(null);
            for (HouseData locale : houseData.getLocales().values()) {
                locale.setId(null);
            }

            HouseData houseDataCreated = houseService.addHouse(houseData);
            houseIdMap.put(oldId, houseDataCreated.getId());
        }
        in.close();

//        for (Map.Entry<Long,Long> idEntry : houseIdMap.entrySet()) {
//            System.out.println("house old: " + idEntry.getKey() + " new: " + idEntry.getValue());
//        }
    }

    private void importPersons(Gson gson, ApplicationContext context) throws IOException {
        PersonService personService = context.getBean(PersonService.class);

        BufferedReader in = new BufferedReader(new FileReader(BACKUP_FOLDER + BACKUP_PERSON));

        while (in.ready()) {
            String s = in.readLine();

            PersonData personData = gson.fromJson(s, PersonData.class);

            Long oldId = personData.getId();

            personData.setId(null);
//            personData.setHouse(new HouseData.Builder().id( houseIdMap.get(personData.getHouse().getId()) ).build());
            for (PersonData locale : personData.getLocales().values()) {
                locale.setId(null);
            }

            List<PersonData> parents = personData.getParents();
//            personData.getParents().clear();
            personData.setParents(null);
//            personData.getRulers().clear();

            PersonData personDataCreated = personService.addPerson(personData);
            personIdMap.put(oldId, personDataCreated.getId());
            personDataCreated.setParents(parents);
//            persons.add(personDataCreated);
            personMap.put(oldId, personDataCreated);
        }

        for (PersonData personData : personMap.values()) {
            for (PersonData parent : personData.getParents()) {
                parent.setId( personIdMap.get(parent.getId()) );
            }

            personService.addPerson(personData);
        }

        in.close();
    }


//    {"id":44940,"locales":{"DE":{"id":44938,"name":"England"},"HU":{"id":44937,"name":"Anglia"}},"name":"England"}
//    {"id":59916,"locales":{"DE":{"id":45001,"name":"Papsttum"},"HU":{"id":45000,"name":"Pápaság"}},"name":"Papacy"}
//    {"id":59928,"name":"Duchy of Normandy"}
//    {"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"}
//    {"id":74902,"name":"House of Flanders"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1028,"value":"1028"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":59931},{"rules":[],"id":74897}],"id":44944,"name":"William"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1056,"value":"1056"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":2,"month":8,"year":1100,"value":"1100-08-02"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74905}],"id":44945,"name":"William"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1020,"value":"1020"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":25,"month":5,"year":1085,"value":"1085-05-25"},"house":{"id":59921},"rulers":[{"rules":[],"id":59926}],"id":59920,"locales":{"DE":{"id":59918,"name":"Hildebrand (von Sovana)"},"HU":{"id":59917,"name":"Hildebrand"}},"name":"Hildebrand of Sovana"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1050,"value":"1050"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":3,"month":2,"year":1134,"value":"1134-02-03"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74900}],"id":74899,"name":"Robert Curthose"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1031,"value":"1031"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":2,"month":11,"year":1083,"value":"1083-11-02"},"house":{"id":74902,"name":"House of Flanders"},"id":74901,"name":"Matilda of Flanders"}
//    {"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1068,"value":"1068"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":1,"month":12,"year":1135,"value":"1135-12-01"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74916},{"rules":[],"id":74912}],"id":74908,"locales":{"DE":{"id":74907,"name":"Heinrich"},"HU":{"id":74906,"name":"Henrik"}},"name":"Henry"}
//    {"alias":"Pope Gregory VII","title":"Pope","rules":[{"id":56320,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":22,"month":4,"year":1073,"value":"1073-04-22"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":25,"month":5,"year":1085,"value":"1085-05-25"},"country":{"id":59916,"locales":{"DE":{"id":45001,"name":"Papsttum"},"HU":{"id":45000,"name":"Pápaság"}},"name":"Papacy"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1020,"value":"1020"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":25,"month":5,"year":1085,"value":"1085-05-25"},"house":{"id":59921},"rulers":[{"rules":[],"id":59926}],"id":59920,"locales":{"DE":{"id":59918,"name":"Hildebrand (von Sovana)"},"HU":{"id":59917,"name":"Hildebrand"}},"name":"Hildebrand of Sovana"},"id":59926,"locales":{"DE":{"alias":"Gregor VII.","title":"Papst","rules":[],"id":59925,"name":"Gregor VII."},"HU":{"alias":"Szent VII. Gergely","title":"Pápa","rules":[],"id":59923,"name":"Szent VII. Gergely"}},"name":"Pope Gregory VII"}
//    {"alias":"Henry Beauclerc","title":"King","rules":[{"id":56347,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":2,"month":8,"year":110,"value":"110-08-02"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":1,"month":12,"year":1135,"value":"1135-12-01"},"country":{"id":44940,"locales":{"DE":{"id":44938,"name":"England"},"HU":{"id":44937,"name":"Anglia"}},"name":"England"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1068,"value":"1068"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":1,"month":12,"year":1135,"value":"1135-12-01"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74912},{"rules":[],"id":74916}],"id":74908,"locales":{"DE":{"id":74907,"name":"Heinrich"},"HU":{"id":74906,"name":"Henrik"}},"name":"Henry"},"id":74916,"locales":{"DE":{"alias":"","title":"König","rules":[],"id":74911,"name":"Heinrich I"},"HU":{"alias":"","title":"Király","rules":[],"id":74909,"name":"I. Henrik"}},"name":"Henry I"}
//    {"alias":"William Rufus","title":"King","rules":[{"id":56338,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":2,"month":8,"year":1100,"value":"1100-08-02"},"country":{"id":44940,"locales":{"DE":{"id":44938,"name":"England"},"HU":{"id":44937,"name":"Anglia"}},"name":"England"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1056,"value":"1056"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":2,"month":8,"year":1100,"value":"1100-08-02"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74905}],"id":44945,"name":"William"},"id":74905,"locales":{"DE":{"alias":"Wilhelm Rufus","title":"König","rules":[],"id":74904,"name":"Wilhelm II"},"HU":{"alias":"Rúfusz","title":"Király","rules":[],"id":74903,"name":"II. Vilmos"}},"name":"William II"}
//    {"alias":"William the Conqueror","title":"King","rules":[{"id":56323,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":25,"month":12,"year":1066,"value":"1066-12-25"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"country":{"id":44940,"locales":{"DE":{"id":44938,"name":"England"},"HU":{"id":44937,"name":"Anglia"}},"name":"England"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1028,"value":"1028"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":59931},{"rules":[],"id":74897}],"id":44944,"name":"William"},"id":59931,"locales":{"DE":{"alias":"Wilhelm der Eroberer","title":"König","rules":[],"id":59930,"name":"Wilhelm I"},"HU":{"alias":"Hódító Vilmos","title":"Király","rules":[],"id":59929,"name":"I. Vilmos"}},"name":"William I"}
//    {"alias":"","title":"Duke","rules":[{"id":56330,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":3,"month":7,"year":1035,"value":"1035-07-03"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"country":{"id":59928,"name":"Duchy of Normandy"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1028,"value":"1028"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74897},{"rules":[],"id":59931}],"id":44944,"name":"William"},"id":74897,"locales":{"DE":{"alias":"","title":"Herzog","rules":[],"id":74896,"name":"Wilhelm"},"HU":{"alias":"","title":"Herceg","rules":[],"id":74895,"name":"Vilmos"}},"name":"William"}
//    {"alias":"","title":"Duke","rules":[{"id":56349,"from":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1106,"value":"1106"},"to":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":1,"month":12,"year":1135,"value":"1135-12-01"},"country":{"id":59928,"name":"Duchy of Normandy"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1068,"value":"1068"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":1,"month":12,"year":1135,"value":"1135-12-01"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74912},{"rules":[],"id":74916}],"id":74908,"locales":{"DE":{"id":74907,"name":"Heinrich"},"HU":{"id":74906,"name":"Henrik"}},"name":"Henry"},"id":74912,"name":"Henry"}
//    {"alias":"","title":"Duke","rules":[{"id":56328,"from":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":9,"month":9,"year":1087,"value":"1087-09-09"},"to":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1106,"value":"1106"},"country":{"id":59928,"name":"Duchy of Normandy"}}],"person":{"dateOfBirth":{"isThereDay":false,"isThereMonth":false,"isAD":true,"day":1,"month":0,"year":1050,"value":"1050"},"dateOfDeath":{"isThereDay":true,"isThereMonth":true,"isAD":true,"day":3,"month":2,"year":1134,"value":"1134-02-03"},"parents":[{"id":74901},{"id":44944}],"house":{"id":44943,"locales":{"DE":{"id":44942,"name":"Normannische Dynastie"},"HU":{"id":44941,"name":"Normandiai ház"}},"name":"House of Normandy"},"rulers":[{"rules":[],"id":74900}],"id":74899,"name":"Robert Curthose"},"id":74900,"name":"Robert Curthose"}

}
