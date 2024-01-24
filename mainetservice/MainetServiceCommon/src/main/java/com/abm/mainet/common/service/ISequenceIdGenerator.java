package com.abm.mainet.common.service;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public interface ISequenceIdGenerator {

    public abstract Serializable generate(SessionImplementor session, Object classVariable)
            throws HibernateException;

}