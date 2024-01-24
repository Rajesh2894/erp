package com.abm.mainet.common.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public interface ISequenceIdGenerator {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.upload.repository.IMastersSequenceGenerator#generate(org.hibernate.engine.spi.SessionImplementor,
     * java.lang.Object)
     */
    Serializable generate(SessionImplementor session, Object object)
            throws HibernateException;

}