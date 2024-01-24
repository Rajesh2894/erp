package com.abm.mainet.common.dao;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Repository
public abstract class AbstractDAO<T> implements IAbstractDAO<T> {
    protected final Logger logger = Logger.getLogger(this.getClass());
    private final Class<T> persistentClass;

    @PersistenceContext
    protected EntityManager entityManager;

   

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getPersistentClass()
     */
    @Override
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

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
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#findById(ID)
     */
    @Override
    public T findById(final Object primaryKey) {
        return this.entityManager.find(getPersistentClass(), primaryKey);
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
    public TypedQuery<T> createTypedQuery(final String query) {

        return this.entityManager.createQuery(query, getPersistentClass());
    }

    @Override
    public Query createNativeQuery(final String sqlString) {

        return this.entityManager.createNativeQuery(sqlString);
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
    public void updateHqlAuditDetails(final Query query) {
        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.UPDATED_BY, UserSession.getCurrent().getEmployee());

        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.UPDATED_DATE, new Date());

        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.LG_IP_MAC, Utility.getMacAddress());
    }

    @Override
    public void updateHqlAuditDetailsPrimitive(final Query query) {
        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.UPDATED_BY, UserSession.getCurrent().getEmployee().getEmpId());

        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.UPDATED_DATE, new Date());

        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.LG_IP_MAC, Utility.getMacAddress());
    }

    protected final Session getCurrentSession() {

        return null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <TEntity extends BaseEntity> long findLoiValue(final Class<TEntity> entity, final String colName) {

        final UserSession userSession = UserSession.getCurrent();

        final Criteria criteria = this.getCurrentSession().createCriteria(entity).setProjection(Projections.max(colName));

        criteria.add(Restrictions.eq("orgId", userSession.getOrganisation()));

        final List<Long> list = criteria.list();

        if ((list != null) && (list.size() > 0)) {
            return (list.get(0) != null) ? list.get(0).longValue() : 0L;
        }

        return 0L;
    }

    /**
     *
     * @param criteria : pass your criteria
     * @return : List of entity with warning suppressed Use this method to avoid suppress warnings for type safety
     *
     */
    @SuppressWarnings({ "hiding", "unchecked" })
    public <T> List<T> listWithSuppressedWarnings(final Criteria criteria) {

        final List<?> list = criteria.list();

        return (List<T>) list;
    }

    /**
     * Use this method to avoid suppress warnings for type safety
     * @param Query : pass your query
     * @return : List of entity with warning suppressed
     *
     */
    @SuppressWarnings({ "hiding", "unchecked" })
    public <T> List<T> listWithSuppressedWarningsJPA(final Query query) {

        final List<?> list = query.getResultList();

        return (List<T>) list;
    }

    /**
     * add days to date in java
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(final Date date, final int days) throws Exception {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        final String output1 = sdf.format(calendar.getTime());
        return sdf.parse(output1);

    }

}
