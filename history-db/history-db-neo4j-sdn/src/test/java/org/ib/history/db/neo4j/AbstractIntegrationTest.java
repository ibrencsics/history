package org.ib.history.db.neo4j;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestApplicationConfig.class })
@Transactional
public abstract class AbstractIntegrationTest {

    @Autowired
    protected Neo4jTemplate template;
}
