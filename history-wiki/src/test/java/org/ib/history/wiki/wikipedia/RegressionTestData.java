package org.ib.history.wiki.wikipedia;

import org.ib.history.commons.data.FlexibleDate;
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
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1035).month(7).day(3).build())
                                        .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                                        .predecessor(new WikiNamedResource("Robert the Magnificent"))
                                        .successor(new WikiNamedResource("Robert Curthose"))
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
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1672).month(7).noDay().build())
                                        .to(new FlexibleDate.Builder().year(1702).month(3).day(8).build())
                                        .predecessor(new WikiNamedResource("William II, Prince of Orange", "William II"))
                                        .successor(new WikiNamedResource("William IV, Prince of Orange", "William IV"))
                                        .build(),
                                new WikiSuccession.Builder()
                                        .from(new FlexibleDate.Builder().year(1689).month(2).day(13).build())
                                        .to(new FlexibleDate.Builder().year(1702).month(3).day(8).build())
                                        .predecessor(new WikiNamedResource("James II of England", "James II & VII"))
                                        .successor(new WikiNamedResource("Anne, Queen of Great Britain", "Anne"))
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
                        Arrays.asList()
                ));

        expectedData.put("Louis_I_of_Hungary",
                new TestVector(
                        "Louis I",
                        new FlexibleDate.Builder().year(1326).month(3).day(5).build(),
                        new FlexibleDate.Builder().year(1382).month(9).day(10).build(),
                        new WikiNamedResource("Charles I of Hungary"),
                        new WikiNamedResource("Elizabeth of Poland, Queen of Hungary", "Elizabeth of Poland"),
                        Arrays.asList(
                                new WikiNamedResource("Margaret of Luxembourg"),
                                new WikiNamedResource("Elizabeth of Bosnia")),
                        Arrays.asList(
                                new WikiNamedResource("Catherine of Hungary (1370–1378)", "Catherine of Hungary"),
                                new WikiNamedResource("Mary of Hungary"),
                                new WikiNamedResource("Jadwiga of Poland", "Hedwig of Poland")
                        ),
                        Arrays.asList(new WikiNamedResource("Capetian House of Anjou")),
                        Arrays.asList()
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
                        Arrays.asList()
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
                        Arrays.asList()
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
                        Arrays.asList()
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
                        Arrays.asList()
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
                        Arrays.asList()
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
                        Arrays.asList()
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
                                new WikiNamedResource("Princess Victoria of the United Kingdom"),
                                new WikiNamedResource("Maud of Wales", "Maud, Queen of Norway")
                                // Prince Alexander John of Wales
                        ),
                        Arrays.asList(new WikiNamedResource("House of Saxe-Coburg and Gotha", "Saxe-Coburg and Gotha")),
                        Arrays.asList()
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
                        Arrays.asList()
                ));
    }
}
