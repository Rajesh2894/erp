package com.abm.mainet.common.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.abm.mainet.common.domain.BaseEntity;

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

    public <TEntity extends BaseEntity> long findLoiValue(Class<TEntity> entity, String colName);

    void updateHqlAuditDetails(Query query);

    /**
     * @param query
     */
    void updateHqlAuditDetailsPrimitive(Query query);

}
