package org.ib.history.wiki.wikipedia;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiSuccession;

import java.util.*;

public class RegressionTestData {

    public static class TestVector {
        String name;
        FlexibleDate dateOfBirth;
        FlexibleDate dateOfDeath;
        WikiNamedResource father;
        WikiNamedResource mother;
        List<WikiNamedResource> spouses;
        List<WikiNamedResource> issues;
        List<WikiNamedResource> houses;
        List<WikiSuccession> successions;

        public TestVector(
                String name,
                FlexibleDate dateOfBirth,
                FlexibleDate dateOfDeath,
                WikiNamedResource father,
                WikiNamedResource mother,
                List<WikiNamedResource> spouses,
                List<WikiNamedResource> issues,
                List<WikiNamedResource> houses,
                List<WikiSuccession> successions) {
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.dateOfDeath = dateOfDeath;
            this.father = father;
            this.mother = mother;
            this.spouses = spouses;
            this.issues = issues;
            this.houses = houses;
            this.successions = successions;
        }
    }

    public static final Map<String,TestVector> expectedData = new HashMap<>();

    static {
        expectedData.put("William_the_Conqueror",
                new TestVector(
                        "William the Conqueror",
                        new FlexibleDate.Builder().year(1028).noMonth().noDay().build(),
                        new FlexibleDate.Builder().year(1087).month(9).day(9).build(),
                        new WikiNamedResource("Robert I, Duke of Normandy"),
                        new WikiNamedResource("Herleva", "Herleva of Falaise"),
                        Arrays.asList(new WikiNamedResource("Matilda of Flanders")),
                        Arrays.asList(
                                new WikiNamedResource("Robert Curthose"),
                                new WikiNamedResource("Richard of Normandy", "Richard"),
                                new WikiNamedResource("William II of England"),
                                // Matilda
                                new WikiNamedResource("Cecilia of Normandy", "Cecilia"),
                                new WikiNamedResource("Henry I of England"),
                                new WikiNamedResource("Adeliza of Normandy", "Adeliza"),
                                new WikiNamedResource("Constance of Normandy", "Constance"),
                                new WikiNamedResource("Adela of Normandy", "Adela, Countess of Blois")
                                // Agatha of Normandy (existence doubtful)
                        ),
                        Arrays.asList(new WikiNamedResource("Norman dynasty")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1066).month(12).day(25).build())
                                        .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                                        .predecessor(new WikiNamedResource("Edgar the Ætheling"))
                                        .successor(new WikiNamedResource("William II of England", "William II"))
                                                // predecessor [[Harold Godwinson|Harold II]]
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1035).month(7).day(3).build())
                                        .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                                        .predecessor(new WikiNamedResource("Robert the Magnificent"))
                                        .successor(new WikiNamedResource("Robert Curthose"))
                                        .titleAndCountries(new Tuple2<>("Duke", Arrays.asList("Normandy")))
                                        .build())
                ));

        expectedData.put("William_III_of_England",
                new TestVector(
                        "William III",
                        new FlexibleDate.Builder().year(1650).month(11).day(4).build(),
                        new FlexibleDate.Builder().year(1702).month(3).day(8).build(),
                        new WikiNamedResource("William II, Prince of Orange"),
                        new WikiNamedResource("Mary, Princess Royal and Princess of Orange", "Mary, Princess Royal"),
                        Arrays.asList(new WikiNamedResource("Mary II of England")),
                        new ArrayList<WikiNamedResource>(),
                        Arrays.asList(new WikiNamedResource("House of Orange-Nassau")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1650).month(11).day(4).build())
                                        .to(new FlexibleDate.Builder().year(1702).month(3).day(8).build())
                                        .predecessor(new WikiNamedResource("William II, Prince of Orange", "William II"))
                                        .successor(new WikiNamedResource("John William Friso, Prince of Orange", "John William Friso"))
                                        .titleAndCountries(new Tuple2<>("Prince", Arrays.asList("Orange")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1672).month(7).noDay().build())
                                        .to(new FlexibleDate.Builder().year(1702).month(3).day(8).build())
                                        .predecessor(new WikiNamedResource("William II, Prince of Orange", "William II"))
                                        .successor(new WikiNamedResource("William IV, Prince of Orange", "William IV"))
                                        .titleAndCountries(new Tuple2<>("Stadtholder",
                                                Arrays.asList("Holland", "Zeeland", "Utrecht", "Gelderland", "Overijssel")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1689).month(2).day(13).build())
                                        .to(new FlexibleDate.Builder().year(1702).month(3).day(8).build())
                                        .predecessor(new WikiNamedResource("James II of England", "James II & VII"))
                                        .successor(new WikiNamedResource("Anne, Queen of Great Britain", "Anne"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England", "Scotland", "Ireland")))
                                        .build())
                ));

        expectedData.put("Henry_VIII_of_England",
                new TestVector(
                        "Henry VIII",
                        new FlexibleDate.Builder().year(1491).month(6).day(28).build(),
                        new FlexibleDate.Builder().year(1547).month(1).day(28).build(),
                        new WikiNamedResource("Henry VII of England"),
                        new WikiNamedResource("Elizabeth of York"),
                        Arrays.asList(
                                new WikiNamedResource("Catherine of Aragon"),
                                new WikiNamedResource("Anne Boleyn"),
                                new WikiNamedResource("Jane Seymour"),
                                new WikiNamedResource("Anne of Cleves"),
                                new WikiNamedResource("Catherine Howard"),
                                new WikiNamedResource("Catherine Parr")),
                        Arrays.asList(
                                new WikiNamedResource("Mary I of England"),
                                new WikiNamedResource("Elizabeth I of England"),
                                new WikiNamedResource("Edward VI of England"),
                                new WikiNamedResource("Henry FitzRoy, 1st Duke of Richmond and Somerset", "Henry Fitzroy")),
                        Arrays.asList(new WikiNamedResource("House of Tudor")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1509).month(4).day(21).build())
                                        .to(new FlexibleDate.Builder().year(1547).month(1).day(28).build())
                                        .predecessor(new WikiNamedResource("Henry VII of England", "Henry VII"))
                                        .successor(new WikiNamedResource("Edward VI of England", "Edward VI"))
                                        .raw("King of England; Lord/King of Ireland")
                                        .build()
                        )
                ));

        expectedData.put("Louis_I_of_Hungary",
                new TestVector(
                        "Louis I",
                        new FlexibleDate.Builder().year(1326).month(3).day(5).build(),
                        new FlexibleDate.Builder().year(1382).month(9).day(10).build(),
                        new WikiNamedResource("Charles I of Hungary"),
                        new WikiNamedResource("Elizabeth of Poland, Queen of Hungary", "Elizabeth of Poland"),
                        Arrays.asList(
                                new WikiNamedResource("Margaret of Bohemia, Queen of Hungary", "Margaret of Bohemia"),
                                new WikiNamedResource("Elizabeth of Bosnia")),
                        Arrays.asList(
                                new WikiNamedResource("Catherine of Hungary (1370–1378)", "Catherine of Hungary"),
                                new WikiNamedResource("Mary of Hungary"),
                                new WikiNamedResource("Jadwiga of Poland", "Hedwig of Poland")
                        ),
                        Arrays.asList(new WikiNamedResource("Capetian House of Anjou")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1342).noMonth().noDay().build())
                                        .to(new FlexibleDate.Builder().year(1382).noMonth().noDay().build())
                                        .predecessor(new WikiNamedResource("Charles I of Hungary", "Charles I"))
                                        .successor(new WikiNamedResource("Mary of Hungary", "Mary"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Hungary", "Croatia")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1370).noMonth().noDay().build())
                                        .to(new FlexibleDate.Builder().year(1382).noMonth().noDay().build())
                                        .predecessor(new WikiNamedResource("Casimir III of Poland", "Casimir III"))
                                        .successor(new WikiNamedResource("Jadwiga of Poland", "Hedwig"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Poland")))
                                        .build()
                        )
                ));

        expectedData.put("Anne_of_Austria",
                new TestVector(
                        null,
                        new FlexibleDate.Builder().year(1601).month(9).day(22).build(),
                        new FlexibleDate.Builder().year(1666).month(1).day(20).build(),
                        new WikiNamedResource("Philip III of Spain"),
                        new WikiNamedResource("Margaret of Austria (1584-1611)", "Margaret of Austria"),
                        Arrays.asList(new WikiNamedResource("Louis XIII of France")),
                        Arrays.asList(
                                new WikiNamedResource("Louis XIV of France"),
                                new WikiNamedResource("Philippe I, Duke of Orléans", "Philippe, Duke of Orléans")),
                        Arrays.asList(new WikiNamedResource("House of Habsburg")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1615).month(11).day(24).build())
                                        .to(new FlexibleDate.Builder().year(1643).month(5).day(14).build())
                                        .predecessor(null)
                                        .successor(null)
                                        .titleAndCountries(new Tuple2<>("Queen consort", Arrays.asList("France", "Navarre")))
                                        .build()
                        )
                ));

        expectedData.put("Empress_Matilda",
                new TestVector(
                        "Empress Matilda",
                        new FlexibleDate.Builder().year(1102).month(2).day(7).build(),
                        new FlexibleDate.Builder().year(1167).month(9).day(10).build(),
                        new WikiNamedResource("Henry I of England"),
                        new WikiNamedResource("Matilda of Scotland"),
                        Arrays.asList(
                                new WikiNamedResource("Henry V, Holy Roman Emperor"),
                                new WikiNamedResource("Geoffrey V, Count of Anjou")),
                        Arrays.asList(
                                new WikiNamedResource("Henry II of England"),
                                new WikiNamedResource("Geoffrey, Count of Nantes"),
                                new WikiNamedResource("William FitzEmpress", "William X, Count of Poitou")),
                        Arrays.asList(new WikiNamedResource("Norman dynasty", "Normandy")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1114).month(1).day(7).build())
                                        .to(new FlexibleDate.Builder().year(1125).month(5).day(23).build())
                                        .predecessor(null)
                                        .successor(null)
//                                        .title("Holy Roman Empress, Queen of the Romans")
                                        .titleAndCountries(new Tuple2<>("Holy Roman Empress Queen", Arrays.asList("Romans")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1141).month(4).day(7).build())
                                        .to(new FlexibleDate.Builder().year(1141).month(11).day(1).build())
                                        .predecessor(new WikiNamedResource("Stephen, King of England", "Stephen"))
                                        .successor(new WikiNamedResource("Stephen, King of England", "Stephen"))
//                                        .title("Lady of the English (disputed)")
                                        .titleAndCountries(new Tuple2<>("Lady", Arrays.asList("English")))
                                        .build()
                        )
                ));

        expectedData.put("Stephen,_King_of_England",
                new TestVector(
                        "Stephen",
                        new FlexibleDate.Builder().year(1092).noMonth().noDay().build(), // or 1096
                        new FlexibleDate.Builder().year(1154).month(10).day(25).build(),
                        new WikiNamedResource("Stephen, Count of Blois", "Stephen II, Count of Blois"),
                        new WikiNamedResource("Adela of Normandy"),
                        Arrays.asList(new WikiNamedResource("Matilda of Boulogne", "Matilda I, Countess of Boulogne")),
                        Arrays.asList(
                                new WikiNamedResource("Eustace IV, Count of Boulogne"),
                                new WikiNamedResource("Marie I, Countess of Boulogne"),
                                new WikiNamedResource("William I, Count of Boulogne")),
                        Arrays.asList(new WikiNamedResource("Counts of Blois", "House of Blois")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1135).month(12).day(22).build())
                                        .to(new FlexibleDate.Builder().year(1141).month(4).noDay().build())
                                        .predecessor(new WikiNamedResource("Henry I of England", "Henry I"))
                                        .successor(new WikiNamedResource("Empress Matilda", "Matilda"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1141).month(11).noDay().build())
                                        .to(new FlexibleDate.Builder().year(1154).month(10).day(25).build())
                                        .predecessor(new WikiNamedResource("Empress Matilda", "Matilda"))
                                        .successor(new WikiNamedResource("Henry II of England", "Henry II"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England")))
                                        .build()
                        )
                ));

        expectedData.put("Charles_II_of_England",
                new TestVector(
                        "Charles II",
                        new FlexibleDate.Builder().year(1630).month(5).day(29).build(),
                        new FlexibleDate.Builder().year(1685).month(2).day(6).build(),
                        new WikiNamedResource("Charles I of England", "Charles I"),
                        new WikiNamedResource("Henrietta Maria of France"),
                        Arrays.asList(new WikiNamedResource("Catherine of Braganza")),
                        Arrays.asList(
                                new WikiNamedResource("James Scott, 1st Duke of Monmouth", "James Scott, Duke of Monmouth"),
                                new WikiNamedResource("Charles FitzCharles, 1st Earl of Plymouth", "Charles FitzCharles, Earl of Plymouth"),
                                new WikiNamedResource("Charles FitzRoy, 2nd Duke of Cleveland", "Charles FitzRoy, Duke of Cleveland"),
                                new WikiNamedResource("Charlotte Lee, Countess of Lichfield"),
                                new WikiNamedResource("Henry FitzRoy, 1st Duke of Grafton", "Henry FitzRoy, Duke of Grafton"),
                                new WikiNamedResource("George FitzRoy, 1st Duke of Northumberland", "George FitzRoy, Duke of Northumberland"),
                                new WikiNamedResource("Charles Beauclerk, 1st Duke of St Albans", "Charles Beauclerk, Duke of St Albans"),
                                new WikiNamedResource("Charles Lennox, 1st Duke of Richmond", "Charles Lennox, Duke of Richmond")
                        ),
                        Arrays.asList(new WikiNamedResource("House of Stuart")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1660).month(5).day(29).build())
                                        .to(new FlexibleDate.Builder().year(1685).month(2).day(6).build())
                                        .predecessor(new WikiNamedResource("Charles I of England", "Charles I"))
                                        .successor(new WikiNamedResource("James II of England", "James II & VII"))
//                                        .title("King of England, Scotland, and Ireland")
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England", "Scotland", "Ireland")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1649).month(1).day(30).build())
                                        .to(new FlexibleDate.Builder().year(1651).month(9).day(3).build())
                                        .predecessor(new WikiNamedResource("Charles I of England", "Charles I"))
                                        .successor(null)
//                                        .title("King of Scotland")
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Scotland")))
                                        .build()
                        )
                ));

        expectedData.put("James_II_of_England",
                new TestVector(
                        "James II & VII",
                        new FlexibleDate.Builder().year(1633).month(10).day(14).build(),
                        new FlexibleDate.Builder().year(1701).month(9).day(16).build(),
                        new WikiNamedResource("Charles I of England", "Charles I"),
                        new WikiNamedResource("Henrietta Maria of France"),
                        Arrays.asList(
                                new WikiNamedResource("Anne Hyde"),
                                new WikiNamedResource("Mary of Modena")),
                        Arrays.asList(
                                new WikiNamedResource("Mary II of England"),
                                new WikiNamedResource("Anne, Queen of Great Britain"),
                                new WikiNamedResource("James Francis Edward Stuart"),
                                new WikiNamedResource("Louisa Maria Teresa Stuart"),
                                new WikiNamedResource("Henrietta FitzJames"),
                                new WikiNamedResource("James FitzJames, 1st Duke of Berwick"),
                                new WikiNamedResource("Henry FitzJames")
                        ),
                        Arrays.asList(new WikiNamedResource("House of Stuart")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1685).month(2).day(6).build())
                                        .to(new FlexibleDate.Builder().year(1688).month(12).day(11).build())
                                        .predecessor(new WikiNamedResource("Charles II of England", "Charles II"))
                                        .successor(new WikiNamedResource("William III of England", "William III & II"))
                                                // successor [[Mary II of England|Mary II]]
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England", "Scotland", "Ireland")))
                                        .build()
                        )
                ));

        expectedData.put("Henry_V_of_England",
                new TestVector(
                        "Henry V",
                        new FlexibleDate.Builder().year(1386).month(9).day(16).build(),
                        new FlexibleDate.Builder().year(1422).month(8).day(31).build(),
                        new WikiNamedResource("Henry IV of England"),
                        new WikiNamedResource("Mary de Bohun"),
                        Arrays.asList(new WikiNamedResource("Catherine of Valois")),
                        Arrays.asList(new WikiNamedResource("Henry VI of England")),
                        Arrays.asList(new WikiNamedResource("House of Lancaster")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1413).month(3).day(20).build())
                                        .to(new FlexibleDate.Builder().year(1422).month(8).day(31).build())
                                        .predecessor(new WikiNamedResource("Henry IV of England", "Henry IV"))
                                        .successor(new WikiNamedResource("Henry VI of England", "Henry VI"))
                                        .raw("King of England; Lord of Ireland")
                                        .build()
                        )
                ));

        expectedData.put("Edward_VII",
                new TestVector(
                        "Edward VII",
                        new FlexibleDate.Builder().year(1841).month(11).day(9).build(),
                        new FlexibleDate.Builder().year(1910).month(5).day(6).build(),
                        new WikiNamedResource("Albert, Prince Consort"),
                        new WikiNamedResource("Queen Victoria"),
                        Arrays.asList(new WikiNamedResource("Alexandra of Denmark")),
                        Arrays.asList(
                                new WikiNamedResource("Prince Albert Victor, Duke of Clarence and Avondale"),
                                new WikiNamedResource("George V"),
                                new WikiNamedResource("Louise, Princess Royal"),
                                new WikiNamedResource("Princess Victoria of the United Kingdom", "Princess Victoria"),
                                new WikiNamedResource("Maud of Wales", "Maud, Queen of Norway")
                                // Prince Alexander John of Wales
                        ),
                        Arrays.asList(new WikiNamedResource("House of Saxe-Coburg and Gotha", "Saxe-Coburg and Gotha")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1901).month(1).day(22).build())
                                        .to(new FlexibleDate.Builder().year(1910).month(5).day(6).build())
                                        .predecessor(null)
                                        .successor(null)
                                        .raw("King of the United Kingdom and the British Dominions, Emperor of India")
                                        .build()
                                // likely error:
                                // predecessor1    = [[Queen Victoria|Victoria]]
                                // successor1      = [[George V]]
                        )
                ));

        expectedData.put("Matthias_Corvinus",
                new TestVector(
                        "Matthias Corvinus",
                        new FlexibleDate.Builder().year(1443).month(2).day(23).build(),
                        new FlexibleDate.Builder().year(1490).month(4).day(6).build(),
                        new WikiNamedResource("John Hunyadi"),
                        new WikiNamedResource("Erzsébet Szilágyi", "Elisabeth Szilágyi"),
                        Arrays.asList(
                                new WikiNamedResource("Elizabeth of Celje"),
                                new WikiNamedResource("Catherine of Poděbrady"),
                                new WikiNamedResource("Beatrice of Naples")),
                        Arrays.asList(new WikiNamedResource("John Corvinus")),
                        Arrays.asList(new WikiNamedResource("House of Hunyadi")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1458).noMonth().noDay().build())
                                        .to(new FlexibleDate.Builder().year(1490).noMonth().noDay().build())
                                        .predecessor(new WikiNamedResource("Ladislaus the Posthumous", "Ladislaus V"))
                                        .successor(new WikiNamedResource("Vladislas II of Bohemia and Hungary", "Vladislaus II"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Hungary", "Croatia")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1469).noMonth().noDay().build())
                                        .to(new FlexibleDate.Builder().year(1490).noMonth().noDay().build())
                                        .predecessor(new WikiNamedResource("George of Poděbrady"))
                                        .successor(new WikiNamedResource("Vladislas II of Bohemia and Hungary", "Vladislaus II"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Bohemia")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1487).noMonth().noDay().build())
                                        .to(new FlexibleDate.Builder().year(1490).noMonth().noDay().build())
                                        .predecessor(new WikiNamedResource("Frederick III, Holy Roman Emperor", "Frederick V"))
                                        .successor(new WikiNamedResource("Frederick III, Holy Roman Emperor", "Frederick V"))
                                        .titleAndCountries(new Tuple2<>("Duke", Arrays.asList("Austria")))
                                        .build()
                        )
                ));

        expectedData.put("Charles_V,_Holy_Roman_Emperor",
                new TestVector(
                        "Charles V",
                        new FlexibleDate.Builder().year(1500).month(2).day(24).build(),
                        new FlexibleDate.Builder().year(1558).month(9).day(21).build(),
                        new WikiNamedResource("Philip I of Castile"),
                        new WikiNamedResource("Joanna of Castile"),
                        Arrays.asList(new WikiNamedResource("Isabella of Portugal")),
                        Arrays.asList(
                                new WikiNamedResource("Philip II of Spain"),
                                new WikiNamedResource("Maria of Spain", "Maria, Holy Roman Empress"),
                                new WikiNamedResource("Joanna of Austria, Princess of Portugal", "Joanna, Princess of Portugal"),
                                new WikiNamedResource("John of Austria"),
                                new WikiNamedResource("Margaret of Parma", "Margaret, Duchess of Parma")),
                        Arrays.asList(new WikiNamedResource("House of Habsburg")),
                        Arrays.asList(
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1519).month(6).day(28).build())
                                        .to(new FlexibleDate.Builder().year(1556).month(8).day(27).build())
                                        .predecessor(new WikiNamedResource("Maximilian I, Holy Roman Emperor", "Maximilian I"))
                                        .successor(new WikiNamedResource("Ferdinand I, Holy Roman Emperor", "Ferdinand I"))
                                        .raw("Holy Roman Emperor;King of Germany;King of Italy")
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1516).month(1).day(23).build())
                                        .to(new FlexibleDate.Builder().year(1556).month(1).day(16).build())
                                        .predecessor(new WikiNamedResource("Joanna of Castile", "Joanna")) // and [[Ferdinand II of Aragon|Ferdinand II]]
                                        .successor(new WikiNamedResource("Philip II of Spain", "Philip II"))
                                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("Spain")))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1506).month(9).day(25).build())
                                        .to(new FlexibleDate.Builder().year(1555).month(10).day(25).build())
                                        .predecessor(new WikiNamedResource("Philip I of Castile", "Philip IV"))
                                        .successor(new WikiNamedResource("Philip II of Spain", "Philip V"))
                                        .raw("Lord of the Netherlands; Count Palatine of Burgundy")
                                        .build()
                        )
                ));
        expectedData.put("John_Hunyadi",
                new TestVector(
                        null,
                        new FlexibleDate.Builder().year(1406).noMonth().noDay().build(),
                        new FlexibleDate.Builder().year(1456).month(8).day(11).build(),
                        new WikiNamedResource("Voyk"),
                        new WikiNamedResource("Elizabeth Morsina"),
                        Arrays.asList(new WikiNamedResource("Erzsébet Szilágyi (noblewoman)", "Erzsébet Szilágyi")),
                        Arrays.asList(
                                new WikiNamedResource("Ladislaus Hunyadi"),
                                new WikiNamedResource("Matthias Corvinus")),
                        Arrays.asList(new WikiNamedResource("House of Hunyadi")),
                        Arrays.asList()
                ));
    }

    /*
                                    new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year().month().day().build())
                                        .to(new FlexibleDate.Builder().year().month().day().build())
                                        .predecessor(new WikiNamedResource(""))
                                        .successor(new WikiNamedResource(""))
                                        .title("")
                                        .build()
     */
}
