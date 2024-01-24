package com.abm.mainet.common.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
//import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IAbstractDAO<T> {

    Class<T> getPersistentClass();

    T create(T entity);

    T Update(T entity);

    T findById(Object primaryKey);

    void flush();

    void clear();

    boolean contains(T entity);

    Query createNamedQuery(String name);  // pass the named query name

    TypedQuery<T> createTypedQuery(String query);  // pass the named query name

    Query createNativeQuery(String sqlString);  // pass the native query

    Query createQuery(String jpaQuery);        // pass the jpa query

    void detach(T entity);

    EntityManagerFactory getEntityManagerFactory();  // Return the entity manager factory for the entity manager.

    EntityTransaction getTransaction(); // method returns the EntityTransaction interface.

    void refresh(T entity);  // Refresh the state of the instance from the database, overwriting changes made to the entity, if
                             // any.

    void delete(T entity); // Remove the entity instance

    List<LookUp> getLookUps(String lookUpCode, Organisation organisation);

    String getHierarchicalLookUp(long lookUpId);

    Long getValueFromPrefix(String value, String prefix);

    LookUp getValueFromPrefixLookUp(String value, String prefix);

    LookUp getNonHierarchicalLookUpObject(long lookUpId);

    String getCpdDesc(Long value, String type);

    ResultSet getResultSet(String queryString);

    void closeConnection();

}
