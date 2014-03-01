package org.ib.history.db.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components.
 */
@Configuration
@ComponentScan
@ImportResource("classpath:META-INF/spring/spring-data-context.xml")
@EnableTransactionManagement
class ApplicationConfig {

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new EmbeddedGraphDatabase("target/graph.db");
    }
}
