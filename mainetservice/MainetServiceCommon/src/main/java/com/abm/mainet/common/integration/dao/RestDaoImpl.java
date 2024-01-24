/**
 *
 */
package com.abm.mainet.common.integration.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class RestDaoImpl<T> implements RestDao<T> {

    protected Logger logger = Logger.getLogger(RestDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getPersistentClass()
     */

    private void handleDataAccessException(final DataAccessException ex) {
        throw new FrameworkException(
                "Data access failed for DAO " + this.getClass() + " due to error: " + ex.getLocalizedMessage(), ex);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#create(T)
     */
    @Override
    public T create(final T entity) {
        logger.info("create");

        try {
            this.entityManager.persist(entity);
        } catch (final DataAccessException de) {
            handleDataAccessException(de);
        }

        logger.info("create");

        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#persistUpdate(T)
     */
    @Override
    public T Update(final T entity) {
        logger.info("makeTransient");

        return this.entityManager.merge(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#flush()
     */
    @Override
    public void flush() {
        logger.info("flush");

        this.entityManager.flush();

        logger.info("flush");
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#clear()
     */
    @Override
    public void clear() {
        logger.info("clear");

        this.entityManager.clear();

        logger.info("clear");
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#delete(T)
     */
    @Override
    public void delete(final T entity) {
        logger.info("delete");

        this.entityManager.remove(entity);

        logger.info("delete");
    }

    @Override
    public boolean contains(final T entity) {

        return this.entityManager.contains(entity);
    }

    @Override
    public Query createNamedQuery(final String name) {

        return this.entityManager.createNamedQuery(name);
    }

    @Override
    public Query createNativeQuery(final String sqlString) {

        return this.entityManager.createNativeQuery(sqlString);
    }

    @Override
    public EntityManager getEntityManager() {

        final EntityManager entityManager = this.entityManager;
        return entityManager;
    }

    @Override
    public Query createQuery(final String jpaQuery) {

        return this.entityManager.createQuery(jpaQuery);
    }

    @Override
    public void detach(final T entity) {

        this.entityManager.detach(entity);
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {

        return this.entityManager.getEntityManagerFactory();
    }

    @Override
    public EntityTransaction getTransaction() {

        return this.entityManager.getTransaction();
    }

    @Override
    public void refresh(final T entity) {
        this.entityManager.refresh(entity);

    }

    @Override
    public void closeEntityManager(final EntityManager entityManager) {
        entityManager.close();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.common.dao.RestDao#findById(java.lang.Object)
     */
    @Override
    public T findById(final Object primaryKey) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.common.dao.RestDao#createTypedQuery(java.lang.String)
     */
    @Override
    public TypedQuery<T> createTypedQuery(final String query) {
        // TODO Auto-generated method stub
        return null;
    }

}
