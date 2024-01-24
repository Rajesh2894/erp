package com.abm.mainet.common.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationDatasourceLoader;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.Logger;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Repository
public abstract class AbstractDAO<T> implements IAbstractDAO<T> {
    private static final String CREATE = "create";
    private static final String DELETE = "delete";
    private static final String CLEAR = "clear";
    private static final String FLUSH = "flush";
    protected Logger logger = new Logger(this.getClass());
    private final Class<T> persistentClass;

    @PersistenceContext
    protected EntityManager entityManager;

    private Connection conn = null;

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
        logger.inMethod(CREATE);

        try {
            this.entityManager.persist(entity);
        } catch (final DataAccessException de) {
            handleDataAccessException(de);
        }

        logger.outMethod(CREATE);

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
        logger.inMethod("makeTransient");

        return this.entityManager.merge(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#flush()
     */
    @Override
    public void flush() {
        logger.inMethod(FLUSH);

        this.entityManager.flush();

        logger.outMethod(FLUSH);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#clear()
     */
    @Override
    public void clear() {
        logger.inMethod(CLEAR);

        this.entityManager.clear();

        logger.outMethod(CLEAR);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#delete(T)
     */
    @Override
    public void delete(final T entity) {
        logger.inMethod(DELETE);

        this.entityManager.remove(entity);

        logger.outMethod(DELETE);
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

    protected ApplicationSession getAppSession() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getLookUps(java.lang.String, com.abm.mainet.domain.core.Organisation)
     */
    @Override
    public List<LookUp> getLookUps(final String lookUpCode, final Organisation organisation) {
        return getAppSession().getNonHierarchicalLookUp(organisation, lookUpCode).get(lookUpCode);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getHierarchicalLookUp(long)
     */
    @Override
    public String getHierarchicalLookUp(final long lookUpId) {
        final LookUp list = getAppSession().getHierarchicalLookUp(UserSession.getCurrent().getOrganisation(), lookUpId);

        return list.getLookUpDesc();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getValueFromPrefix(java.lang.String, java.lang.String)
     */
    @Override
    public Long getValueFromPrefix(final String value, final String prefix) {
        final Iterator<LookUp> lookup = getLookUps(prefix, UserSession.getCurrent().getOrganisation()).iterator();

        while (lookup.hasNext()) {

            final LookUp lookUp2 = lookup.next();
            if (lookUp2.getLookUpCode().equals(value)) {
                return lookUp2.getLookUpId();
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getValueFromPrefixLookUp(java.lang.String, java.lang.String)
     */
    @Override
    public LookUp getValueFromPrefixLookUp(final String value, final String prefix) {
        final Iterator<LookUp> lookup = getLookUps(prefix, UserSession.getCurrent().getOrganisation()).iterator();

        while (lookup.hasNext()) {

            final LookUp lookUp2 = lookup.next();
            if (lookUp2.getLookUpCode().equals(value)) {
                return lookUp2;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getNonHierarchicalLookUpObject(long)
     */
    @Override
    public LookUp getNonHierarchicalLookUpObject(final long lookUpId) {
        return getAppSession().getNonHierarchicalLookUp(UserSession.getCurrent().getOrganisation().getOrgid(), lookUpId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getCpdDesc(java.lang.Long, java.lang.String)
     */
    @Override
    public String getCpdDesc(final Long value, final String type) {
        return CommonMasterUtility.getCPDDescription(value, type);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#getResultSet(java.lang.String)
     */
    @Override
    public ResultSet getResultSet(final String queryString) {
        try {
            conn = new ApplicationDatasourceLoader().getConnection();
            final Statement stmt = conn.createStatement();

            return stmt.executeQuery(queryString);
        } catch (final Exception e) {
            logger.error(MainetConstants.ERROR_OCCURED, e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ao2.smart.dao.IAbstractDAO#closeConnection()
     */
    @Override
    public void closeConnection() {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }

        } catch (final SQLException e) {
            logger.error("Error occured during closing connection", e);
        }
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
        final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        final String output1 = sdf.format(calendar.getTime());
        return sdf.parse(output1);

    }

}
