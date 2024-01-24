package com.abm.mainet.common.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;

public class SequenceIdGenerator implements IdentifierGenerator, ISequenceIdGenerator {

    private static final Logger LOG = Logger.getLogger(SequenceIdGenerator.class);

    private static final String QUERY_CALL_STORE_PROC = "{ call PR_JAVA_SQ_GENERATION(?,?,?,?,?,?)}";

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.upload.repository.IMastersSequenceGenerator#generate(org.hibernate.engine.spi.SessionImplementor,
     * java.lang.Object)
     */
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.dao.ISequenceIdGenerator#generate(org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
     */
    @Override
    public Serializable generate(final SessionImplementor session, final Object object)
            throws HibernateException {
        String[] tableValues = null;

        Long result = null;

        try {
            tableValues = (String[]) object.getClass().getMethod("getPkValues", new Class[] {})
                    .invoke(object.getClass().newInstance(), new Object[0]);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
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
