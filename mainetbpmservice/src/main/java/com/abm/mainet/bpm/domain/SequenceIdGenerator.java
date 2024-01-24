package com.abm.mainet.bpm.domain;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SequenceIdGenerator implements IdentifierGenerator {

    private static final String QUERY_CALL_STORE_PROC = "{ call PR_JAVA_SQ_GENERATION(?,?,?,?,?,?)}";

    @Override
    public Serializable generate(final SessionImplementor session, final Object object)
            throws HibernateException {
        String[] tableValues = null;

        Long result = null;

        try {
            tableValues = (String[]) object.getClass().getMethod("getPkValues", new Class[] {})
                    .invoke(object.getClass().newInstance(), new Object[0]);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Connection connection = null;
        CallableStatement callableStmt = null;

        try {

            connection = session.connection();

            callableStmt = connection.prepareCall(QUERY_CALL_STORE_PROC);

            callableStmt.setString(1, tableValues[0]);

            callableStmt.setString(2, tableValues[1]);

            callableStmt.setString(3, tableValues[2]);

            callableStmt.setString(4, null);

            callableStmt.setString(5, null);
            callableStmt.registerOutParameter(6, Types.NUMERIC);

            callableStmt.execute();

            result = callableStmt.getLong(6);

        } catch (final SQLException sqlException) {
            throw new HibernateException(sqlException);
        }

        return result;
    }

}
