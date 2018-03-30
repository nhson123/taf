package com.anecon.taf.client.db;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

@SuppressWarnings("unused")
public class DbPreparedStatement {
    private final PreparedStatement p;

    public DbPreparedStatement(PreparedStatement p) {
        this.p = p;
    }

    public DbResult execute() throws SQLException {
        return new DbResult(p);
    }

    public DbPreparedStatement setNull(int parameterIndex, int sqlType) throws SQLException {
        p.setNull(parameterIndex, sqlType);
        return this;
    }

    public DbPreparedStatement setBoolean(int parameterIndex, boolean x) throws SQLException {
        p.setBoolean(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setByte(int parameterIndex, byte x) throws SQLException {
        p.setByte(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setShort(int parameterIndex, short x) throws SQLException {
        p.setShort(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setInt(int parameterIndex, int x) throws SQLException {
        p.setInt(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setLong(int parameterIndex, long x) throws SQLException {
        p.setLong(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setFloat(int parameterIndex, float x) throws SQLException {
        p.setFloat(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setDouble(int parameterIndex, double x) throws SQLException {
        p.setDouble(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        p.setBigDecimal(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setString(int parameterIndex, String x) throws SQLException {
        p.setString(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setBytes(int parameterIndex, byte[] x) throws SQLException {
        p.setBytes(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setDate(int parameterIndex, Date x) throws SQLException {
        p.setDate(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setTime(int parameterIndex, Time x) throws SQLException {
        p.setTime(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        p.setTimestamp(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        p.setAsciiStream(parameterIndex, x, length);
        return this;
    }

    public DbPreparedStatement setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        p.setBinaryStream(parameterIndex, x, length);
        return this;
    }

    public DbPreparedStatement setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        p.setObject(parameterIndex, x, targetSqlType);
        return this;
    }

    public DbPreparedStatement setObject(int parameterIndex, Object x) throws SQLException {
        p.setObject(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        p.setCharacterStream(parameterIndex, reader, length);
        return this;
    }

    public DbPreparedStatement setRef(int parameterIndex, Ref x) throws SQLException {
        p.setRef(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setBlob(int parameterIndex, Blob x) throws SQLException {
        p.setBlob(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setClob(int parameterIndex, Clob x) throws SQLException {
        p.setClob(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setArray(int parameterIndex, Array x) throws SQLException {
        p.setArray(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        p.setDate(parameterIndex, x, cal);
        return this;
    }

    public DbPreparedStatement setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        p.setTime(parameterIndex, x, cal);
        return this;
    }

    public DbPreparedStatement setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        p.setTimestamp(parameterIndex, x, cal);
        return this;
    }

    public DbPreparedStatement setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        p.setNull(parameterIndex, sqlType, typeName);
        return this;
    }

    public DbPreparedStatement setURL(int parameterIndex, URL x) throws SQLException {
        p.setURL(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setRowId(int parameterIndex, RowId x) throws SQLException {
        p.setRowId(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setNString(int parameterIndex, String value) throws SQLException {
        p.setNString(parameterIndex, value);
        return this;
    }

    public DbPreparedStatement setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        p.setNCharacterStream(parameterIndex, value, length);
        return this;
    }

    public DbPreparedStatement setNClob(int parameterIndex, NClob value) throws SQLException {
        p.setNClob(parameterIndex, value);
        return this;
    }

    public DbPreparedStatement setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        p.setClob(parameterIndex, reader, length);
        return this;
    }

    public DbPreparedStatement setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        p.setBlob(parameterIndex, inputStream, length);
        return this;
    }

    public DbPreparedStatement setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        p.setNClob(parameterIndex, reader, length);
        return this;
    }

    public DbPreparedStatement setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        p.setSQLXML(parameterIndex, xmlObject);
        return this;
    }

    public DbPreparedStatement setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        p.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        return this;
    }

    public DbPreparedStatement setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        p.setAsciiStream(parameterIndex, x, length);
        return this;
    }

    public DbPreparedStatement setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        p.setBinaryStream(parameterIndex, x, length);
        return this;
    }

    public DbPreparedStatement setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        p.setCharacterStream(parameterIndex, reader, length);
        return this;
    }

    public DbPreparedStatement setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        p.setAsciiStream(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        p.setBinaryStream(parameterIndex, x);
        return this;
    }

    public DbPreparedStatement setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        p.setCharacterStream(parameterIndex, reader);
        return this;
    }

    public DbPreparedStatement setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        p.setNCharacterStream(parameterIndex, value);
        return this;
    }

    public DbPreparedStatement setClob(int parameterIndex, Reader reader) throws SQLException {
        p.setClob(parameterIndex, reader);
        return this;
    }

    public DbPreparedStatement setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        p.setBlob(parameterIndex, inputStream);
        return this;
    }

    public DbPreparedStatement setNClob(int parameterIndex, Reader reader) throws SQLException {
        p.setNClob(parameterIndex, reader);
        return this;
    }

    public DbPreparedStatement setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        p.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        return this;
    }

    public DbPreparedStatement setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        p.setObject(parameterIndex, x, targetSqlType);
        return this;
    }
}
