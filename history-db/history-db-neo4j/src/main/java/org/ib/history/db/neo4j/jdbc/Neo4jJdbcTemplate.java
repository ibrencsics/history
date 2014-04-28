package org.ib.history.db.neo4j.jdbc;

import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jTemplate;
import org.neo4j.jdbc.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Neo4jJdbcTemplate implements Neo4jTemplate {

    private Driver driver;
    private Properties props;
    private Connection conn;

    public Neo4jJdbcTemplate() {
        driver = new Driver();
        props = new Properties();
    }

    @Override
    public <T> T query(String query, Converter<T> converter) {
        try {
            conn = driver.connect("jdbc:neo4j://localhost:7474", props);

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
