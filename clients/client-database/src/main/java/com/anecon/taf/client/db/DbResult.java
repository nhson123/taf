package com.anecon.taf.client.db;

import com.anecon.taf.core.Cleanable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DbResult implements Cleanable {
    private static final Logger log = LoggerFactory.getLogger(DbResult.class);

    private final Statement statement;
    private final ResultSet rs;
    private final DbMetaData metaData;
    private final String sql;

    DbResult(PreparedStatement statement) throws SQLException {
        this(statement, null);
    }

    DbResult(Statement statement, String sql) throws SQLException {
        this.statement = statement;
        this.sql = sql;

        log.debug("Executing statement: {}", sql);
        this.rs = execute();

        this.metaData = new DbMetaData(rs.getMetaData());
    }

    private ResultSet execute() throws SQLException {
        if (statement instanceof PreparedStatement) {
            return ((PreparedStatement) statement).executeQuery();
        } else {
            statement.execute(sql);
            return statement.getResultSet();
        }
    }

    @SuppressWarnings("unused")
    public <T> List<T> map(Function<ResultSet, T> mappingFunction) throws SQLException {
        final List<T> entities = new ArrayList<>();

        log.debug("Mapping result...");
        while (rs.next()) {
            final T mappedEntity = mappingFunction.apply(rs);
            entities.add(mappedEntity);
        }
        log.debug("Mapped {} entities", entities.size());

        rs.close();

        return entities;
    }

    @SuppressWarnings("unused")
    public DbMetaData getMetaData() {
        return metaData;
    }

    @Override
    public void cleanUp() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
