package com.anecon.taf.client.db;

import com.anecon.taf.core.AutomationFrameworkException;
import com.anecon.taf.core.Cleanable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class DatabaseClient implements Cleanable {
    private static final Logger log = LoggerFactory.getLogger(DatabaseClient.class);

    private final String connectionString;
    private final String user;
    private final String pass;

    public DatabaseClient(String connectionString, String user, String pass) {
        this.connectionString = connectionString;
        this.user = user;
        this.pass = pass;
    }

    private Connection connection;

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            log.debug("Connecting to {} with credentials {}:{}", connectionString, user, pass);
            connection = DriverManager.getConnection(connectionString, user, pass);
        }

        return connection;
    }

    public DbResult query(String sql) throws SQLException {
        log.trace("Creating query with sql: {}", sql);

        return new DbResult(getConnection().createStatement(), sql);
    }

    public DbPreparedStatement prepare(String sql) throws SQLException {
        log.trace("Creating prepared statement with sql: {}", sql);

        return new DbPreparedStatement(getConnection().prepareStatement(sql));
    }

    @Override
    public void cleanUp() {
        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new AutomationFrameworkException("Can't close database connection", e);
        }
    }
}
