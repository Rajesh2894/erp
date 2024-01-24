package com.abm.mainet.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;

public class C3P0NativeJdbcExtractorImpl extends C3P0NativeJdbcExtractor {

    /**
     * @see use this to get Oracle specific Native Connection while you making some Oracle database specific calls like while you
     * are going to make a procedure call by passing Array as input parameter in that case first you have to convert
     * ComboPooledDataSource returned Connection to Native Connection otherwise you might get {
     * @throws ClassCastException} by saying pooled Connection can not be cast to oracle Connection(as like)
     */
    @Override
    public Connection getNativeConnection(final Connection con) throws SQLException {

        return doGetNativeConnection(con);
    }
}
