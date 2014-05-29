package org.ib.history.db.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
//@EnableTransactionManagement
public class TestApplicationConfig /*extends ApplicationConfig*/ {
    @Bean(destroyMethod = "shutdown")
    @Scope(SCOPE_PROTOTYPE)
    public GraphDatabaseService graphDatabaseService() {
        try {
            FileUtils.deleteRecursively(new File("target/test-db"));
//            return new EmbeddedGraphDatabase("target/test-db");
            return new TestGraphDatabaseFactory().newEmbeddedDatabase("target/test-db");
//            return new TestGraphDatabaseFactory().newImpermanentDatabase();
        } catch (/*IO*/Exception e) {
            throw new RuntimeException(e);
        }
    }
}
