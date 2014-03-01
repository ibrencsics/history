match (n) optional match (n)-[r]-() delete n,r;

create (england:Country { name: "England", __type__: "org.ib.history.db.neo4j.domain.Country"})
create (england)-[:TRANSLATION{lang:"HU"}]->({name: "Anglia"})
create (england)-[:TRANSLATION{lang:"DE"}]->({name: "England"})

create (normandyHouse:House { name: "House of Normandy" }) 
create (normandyHouse)-[:TRANSLATION{lang:"HU"}]->({name: "Normandiai ház"})
create (normandyHouse)-[:TRANSLATION{lang:"DE"}]->({name: "Normannische Dynastie"})

create (bloisHouse:House {name: "House of Blois"})

create (williamI:Person { name: "William" })
create (williamI)-[:IN_HOUSE]->(normandyHouse)
create (williamI)-[:AS]->(williamIEmp:Empiror {name: "William I", alias: "William the Conqueror", title: "king"})
create (williamIEmp)-[:TRANSLATION{lang:"HU"}]->({name: "I. Vilmos", alias: "Hódító Vilmos", title: "király"})
create (williamIEmp)-[:TRANSLATION{lang:"DE"}]->({name: "Wilhelm I", alias: "Wilhelm der Eroberer", title: "König"})
create (williamIEmp)-[:RULES{from: "25-12-1066", to: "1087"}]->(england)

create (williamII:Person { name: "William" })
create (williamII)-[:IN_HOUSE]->(normandyHouse)
create (williamI)-[:AS]->(williamIIEmp:Empiror {name: "William II", alias: "William Rufus", title: "king"})
create (williamIIEmp)-[:TRANSLATION{lang:"HU"}]->({name: "II. Vilmos", alias: "Rúfusz", title: "király"})
create (williamIIEmp)-[:TRANSLATION{lang:"DE"}]->({name: "Wilhelm II", alias: "Wilhelm Rufus", title: "König"})
create (williamIIEmp)-[:RULES{from: "26-09-1087", to: "1100"}]->(england)
create (williamII)-[:CHILD_OF]->(williamI)

create (henryI:Person { name: "Henry" })
create (henryI)-[:IN_HOUSE]->(normandyHouse)
create (henryI)-[:AS]->(henryIEmp:Empiror {name: "Henry I", alias: "Henry Beaucierc", title: "king"})
create (henryIEmp)-[:RULES{from: "05-08-1100", to: "1135"}]->(england)
create (henryIEmp)-[:CHILD_OF]->(williamII)

create (stephen:Person { name: "Stephen" })
create (stephen)-[:IN_HOUSE]->(bloisHouse)
create (stephen)-[:AS]->(stephenEmp:Empiror {name: "Stephen", alias:"Stephen of Blois", title: "king"})
create (stephenEmp)-[:RULES{from: "22-12-1135", to: "1154"}]->(england)

;
