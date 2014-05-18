package org.ib.history.db.neo4j.jdbc;

import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jTemplateDeprecated;
import org.neo4j.jdbc.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Neo4jJdbcTemplate implements Neo4jTemplateDeprecated<ResultSet> {

    private Driver driver;
    private String url;
    private Properties props;
    private Connection conn;

    public Neo4jJdbcTemplate(String url) {
        driver = new Driver();
        props = new Properties();
        this.url = url;
        try {
            conn = driver.connect(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T executeQuery(Converter<T, ResultSet> converter, String query, String... params) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            for (int i=1; i<=params.length; i++) {
                ps.setString(i, params[i-1]);
            }
            ResultSet rs = ps.executeQuery();
            T domain = converter.convert(rs);
            return domain;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public void executeUpdate(String query, String... params) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            for (int i=1; i<=params.length; i++) {
                ps.setString(i, params[i-1]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
