package com.abm.mainet.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeSearchDTO;

@SuppressWarnings("unchecked")
@Repository
public class EmployeeDAO extends AbstractDAO<Employee>
        implements Serializable, IEmployeeDAO {

    private static final String AND_STRING = " and  ";
    private static final long serialVersionUID = -5914813589396725739L;
    private static final Logger LOG = Logger.getLogger(EmployeeDAO.class);

    @Override
    public int getCountOfGroup(final Long gmId, final Long orgId,
            final String isDeleted) {

        final String strQuery = "select count(e.gmid) from Employee e where e.organisation.orgid = ?1"
                + " and e.isdeleted = ?2 and e.gmid = ?3 ";
        int count = 0;
        try {

            /* JPA Query Start */
            final Query query = createQuery(strQuery);
            query.setParameter(1, orgId);
            query.setParameter(2, isDeleted);
            query.setParameter(3, gmId);
            count = ((Long) query.getSingleResult())
                    .intValue();
            /* JPA Query End */

        } catch (NumberFormatException | HibernateException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByLoginName(java.lang.String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public Employee getEmployeeByLoginName(
            final String emploginname, final Organisation organisation,
            final String isDeleted) {

        final Query query = createQuery(
                "Select e from Employee e join FETCH e.organisation o  where  e.emploginname =?1 and e.organisation.orgid =?2 and e.isdeleted =?3 ");
        query.setParameter(1, emploginname);
        query.setParameter(2, organisation.getOrgid());
        query.setParameter(3, isDeleted);

        try {
            final List<Employee> employees = query
                    .getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeById(java.lang.Long, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public Employee getEmployeeById(final Long empId,
            final Organisation organisation, final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e where e.organisation = ?1 and e.empId = ?2 ");

            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("and e.isdeleted = ?3 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, organisation);
            query.setParameter(2, empId);

            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(3, isdeleted);
            }

            final List<Employee> employees = query
                    .getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAuthenticatedEmployee(java.lang. String, java.lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAuthenticatedEmployee(
            final String emploginname, final String emppassword,
            final Long emplType, final Organisation organisation,
            final String isdeleted, final String type) {
        try {

            final Map<Integer, Object> map = new LinkedHashMap<>();
            int count = 2;
            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e where e.emploginname = ?1 and  ");

            queryAppender.append(
                    "e.mobNoOtp =?" + count + AND_STRING);
            map.put(count, emppassword);
            count++;

            if (emplType != null) {
                queryAppender.append(
                        "e.emplType =?" + count + AND_STRING);
                map.put(count, emplType);
                count++;
            }

            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender.append("e.organisation =?"
                        + count + AND_STRING);
                map.put(count, organisation);
                count++;
            }

            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender.append("e.isdeleted =?"
                        + count + AND_STRING);
                map.put(count, isdeleted);
                count++;
            }

            queryAppender.delete(queryAppender.length() - 6,
                    queryAppender.length());
            final Set<Entry<Integer, Object>> entries = map
                    .entrySet();
            final Iterator<Entry<Integer, Object>> entrySetItr = entries
                    .iterator();
            Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);

            while (entrySetItr.hasNext()) {
                final Entry<Integer, Object> entry = entrySetItr
                        .next();
                query = query.setParameter(entry.getKey(),
                        entry.getValue());
            }

            final Employee employees = (Employee) query
                    .getSingleResult();

            return employees;

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }
    }

    @Override
    public Employee getAuthenticatedEmployee(
            final String emploginname, final String emppassword,
            final Long emplType, final Organisation organisation,
            final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e  where e.emploginname = ?1 and e.mobNoOtp = ?2 and  ");
            int count = 3;
            final Map<Integer, Object> map = new LinkedHashMap<>();
            if (emplType != null) {
                queryAppender.append(
                        "e.emplType =?" + count + AND_STRING);
                map.put(count, emplType);
                count++;
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender.append("e.organisation =?"
                        + count + AND_STRING);
                map.put(count, organisation);
                count++;
            }
            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender.append("e.isdeleted =?"
                        + count + AND_STRING);
                map.put(count, isdeleted);
                count++;
            }

            queryAppender.delete(queryAppender.length() - 6,
                    queryAppender.length());
            final Set<Entry<Integer, Object>> entries = map
                    .entrySet();
            final Iterator<Entry<Integer, Object>> iterator = entries
                    .iterator();
            Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);
            query.setParameter(2, emppassword);

            while (iterator.hasNext()) {
                final Entry<Integer, Object> entry = iterator
                        .next();
                query = query.setParameter(entry.getKey(),
                        entry.getValue());
            }

            final List<Employee> employees = query
                    .getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAdminAuthenticatedEmployee(java.lang. String, java.lang.String, java.lang.Long,
     * java.lang.Long, com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAdminAuthenticatedEmployee(
            final String emploginname, final String emppassword,
            final Long emplType, final Long empId,
            final Organisation organisation, final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e  where e.emploginname =?1 and e.empId =?2 and  ");
            int count = 3;
            final Map<Integer, Object> map = new LinkedHashMap<>();
            if (emppassword != null) {
                queryAppender.append(
                        "e.emppassword =?" + count + AND_STRING);
                map.put(count, emppassword);
                count++;
            }
            if (emplType != null) {
                queryAppender.append(
                        "e.emplType =?" + count + AND_STRING);
                map.put(count, emplType);
                count++;
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender.append("e.organisation =?"
                        + count + AND_STRING);
                map.put(count, organisation);
                count++;
            }
            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender.append("e.isdeleted =?"
                        + count + AND_STRING);
                map.put(count, isdeleted);
                count++;
            }

            queryAppender.delete(queryAppender.length() - 6,
                    queryAppender.length());
            final Set<Entry<Integer, Object>> entries = map
                    .entrySet();
            final Iterator<Entry<Integer, Object>> iterator = entries
                    .iterator();
            Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);
            query.setParameter(2, empId);

            while (iterator.hasNext()) {
                final Entry<Integer, Object> entry = iterator
                        .next();
                query = query.setParameter(entry.getKey(),
                        entry.getValue());
            }

            final List<Employee> employees = query
                    .getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                Hibernate.initialize(
                        employees.get(0).getOrganisation());
                Hibernate.initialize(
                        employees.get(0).getDesignation());
                return employees.get(0);
            }
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmailId(java.lang.String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public List<Employee> getEmployeeByEmailId(
            final String empemail, final Organisation organisation,
            final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append(
                    "Select e from Employee e  where e.empemail = ?1 ");
            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("and e.isdeleted =?2 ");
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender
                        .append("and e.organisation =?3 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empemail);
            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(2, isdeleted);
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }

            final List<Employee> employeeList = query
                    .getResultList();

            return employeeList;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmpMobileNo(java.lang. String, java.lang.String,
     * com.abm.mainet.domain.core.Organisation)
     */
    @Override
    public List<Employee> getEmployeeByEmpMobileNo(
            final String empmobno, final String empPassword,
            final Organisation organisation) {

        final StringBuilder queryAppender = new StringBuilder(
                "Select e from Employee e  where e.empmobno = ?1 ");
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            queryAppender.append("and e.organisation =?2 ");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, empmobno);
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            query.setParameter(2, organisation);
        }

        final List<Employee> employeeList = query.getResultList();
        return employeeList;
    }

    /*
     * Method to get Employee by Email Id, adding the check for employee_type
     */
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmailIdAndEmpType(java.lang. String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String, boolean)
     */
    @Override
    public List<Employee> getEmployeeByEmailIdAndEmpType(
            final String empEMail, final Long empType,
            final Organisation organisation, final String isDeleted,
            final boolean isAgency) {
        try {
            /* JPA Query Start */
            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e  where e.empemail = ?1 ");
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("and e.isdeleted =?2 ");
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender
                        .append("and e.organisation =?3 ");
            }
            if (empType != null) {
                if (isAgency) {
                    queryAppender
                            .append("and e.emplType <> ?4");
                } else {
                    queryAppender
                            .append("and e.emplType = ?4 ");
                }
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empEMail);
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }
            if (empType != null) {
                if (isAgency) {
                    query.setParameter(4, empType);
                } else {
                    query.setParameter(4, empType);
                }
            }

            final List<Employee> employeeList = query
                    .getResultList();
            return employeeList;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmpMobileNo(java.lang. String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public List<Employee> getEmployeeByEmpMobileNo(
            final String empmobno, final Organisation organisation,
            final String isDeleted,Long empType) {
        try {
            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append(
                    "Select e from Employee e  where e.empmobno = ?1 ");
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("AND e.isdeleted =?2 ");
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender
                        .append("AND e.organisation =?3 ");
            }
            if(empType !=null) {
                queryAppender
                        .append(" and e.emplType = ?4 ");
            }else {
            	 queryAppender
                 .append(" and e.emplType is null ");
            }
            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empmobno);
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }
            if(empType !=null) {
            	 query.setParameter(4, empType);
            }
            final List<Employee> employeeList = query
                    .getResultList();

            return employeeList;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmpMobileNoAndEmpType(java. lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String, boolean)
     */
    @Override
    public List<Employee> getEmployeeByEmpMobileNoAndEmpType(
            final String empMobileNo, final Long empType,
            final Organisation organisation, final String isDeleted,
            final boolean isAgency) {
        try {

            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append(
                    "Select e from Employee e  where e.empmobno = ?1 ");
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("and e.isdeleted =?2 ");
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender
                        .append("and e.organisation =?3 ");
            }
            if (empType != null) {
                if (isAgency) {
                    queryAppender.append(
                            "and e.emplType <> ?4 ");
                } else {
                    queryAppender
                            .append("and e.emplType = ?4 ");
                }
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empMobileNo);
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }
            if (empType != null) {
                query.setParameter(4, empType);
            }

            final List<Employee> employeeList = query
                    .getResultList();
            return employeeList;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeListByLoginName(java.lang. String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public List<Employee> getEmployeeListByLoginName(
            final String emploginname, final Organisation organisation,
            final String isDeleted) {

        try {
            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append(
                    "Select e from Employee e  where e.emploginname = ?1 ");
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender
                        .append("and e.isdeleted =?2 ");
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender
                        .append("and e.organisation.orgid =?3 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);
            if ((isDeleted != null)
                    && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation.getOrgid());
            }

            final List<Employee> employeeList = query
                    .getResultList();
            return employeeList;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return new ArrayList<>();
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#saveEmployee(com.abm.mainet.domain.core. Employee)
     */
    @Override
    public Employee saveEmployee(Employee employee) {
        try {
            employee = entityManager
                    .merge(employee);
            if (employee.getAddFlag()
                    .equalsIgnoreCase(MainetConstants.AUTH)) {
                if (employee.getEmpAddress() != null) {
                    employee.setEmpCorAddress1(
                            employee.getEmpAddress());
                }
                if (employee.getEmpAddress1() != null) {
                    employee.setEmpCorAddress2(
                            employee.getEmpAddress1());
                }
                if (employee.getPincode() != null) {
                    employee.setCorPincode(
                            employee.getPincode());
                }
            }
            employee = create(employee);
            return employee;
        } catch (final Exception exception) {
            return employee;
        }
    }

    @Override
    public Employee saveEmployeeForAgency(
            Employee employee) {
        try {
            employee = entityManager
                    .merge(employee);
            if (employee.getAddFlag()
                    .equalsIgnoreCase(MainetConstants.AUTH)) {
                if (employee.getEmpAddress() != null) {
                    employee.setEmpCorAddress1(
                            employee.getEmpAddress());
                }
                if (employee.getEmpAddress1() != null) {
                    employee.setEmpCorAddress2(
                            employee.getEmpAddress1());
                }
                if (employee.getPincode() != null) {
                    employee.setCorPincode(
                            employee.getPincode());
                }
            }
            entityManager.persist(employee);
            return employee;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;

        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#updateEmpDetails(com.abm.mainet.domain. core.Employee)
     */
    @Override
    public boolean updateEmpDetails(Employee employee) {

        try {
            employee = entityManager
                    .merge(employee);
            entityManager.persist(employee);

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAllAgencyList()
     */
    @Override
    public List<Employee> getAllAgencyList(
            final Organisation organisation) {

        final Query query = createQuery(
                "Select e from Employee e  where e.isDeleted = ?1 and e.orgId =?2 ");
        query.setParameter(1,
                MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);

        final List<Employee> list = query.getResultList();
        return list;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAgencyByEmplTypeAndMobile(java.lang. String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAgencyByEmplTypeAndMobile(
            final String mobile, final Long agencyType,
            final Organisation organisation, final String isDeleted) {

        /* JPA Query Start */
        final Query query = createQuery(
                "Select e from Employee e  where e.empmobno = ?1 and e.emplType =?2 and e.organisation = ?3 ");
        query.setParameter(1, mobile);
        query.setParameter(2, agencyType);
        query.setParameter(3, organisation);

        final List<Employee> list = query.getResultList();

        if ((null == list) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAgencyByEmplTypeAndEmail(java.lang. String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAgencyByEmplTypeAndEmail(
            final String email, final Long agencyType,
            final Organisation organisation, final String isDeleted) {

        /* JPA Query Start */
        final Query query = createQuery(
                "Select e from Employee e  where e.empemail = ?1 and e.emplType =?2 and e.organisation = ?3 and e.isdeleted =?4 ");
        query.setParameter(1, email);
        query.setParameter(2, agencyType);
        query.setParameter(3, organisation);
        query.setParameter(4, isDeleted);

        final List<Employee> list = query.getResultList();

        if ((null == list) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAgencyByEmplTypeAndEmploginName(java. lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAgencyByEmplTypeAndEmploginName(
            final String empLoginName, final Long agencyType,
            final Organisation organisation, final String isDeleted) {
        /* JPA Query Start */
        final Query query = createQuery(
                "Select e from Employee e  where e.emploginname = ?1 and e.emplType =?2 and e.organisation = ?3 and e.isdeleted =?4 ");
        query.setParameter(1, empLoginName);
        query.setParameter(2, agencyType);
        query.setParameter(3, organisation);
        query.setParameter(4, isDeleted);

        final List<Employee> list = query.getResultList();

        if ((null == list) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#persistModifiedCitizenInfo(com.abm.mainet .domain.core.Employee)
     */
    @Override
    public void persistModifiedCitizenInfo(
            final Employee modifiedEmployee) {
        entityManager.merge(modifiedEmployee);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#setEmployeeLoggedInFlag(com.abm.mainet. domain.core.Employee)
     */
    @Override
    public void setEmployeeLoggedInFlag(
            final Employee currentLoggedInEmployee) {
        entityManager.merge(currentLoggedInEmployee);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#resetEmployeeLoggedInFlag(com.abm.mainet. domain.core.Employee)
     */
    @Override
    public void resetEmployeeLoggedInFlag(final String flag,
            final long empid) {

        /* JPA Query Start */
        final Query query = createNamedQuery("updateLoggedInFlag");
        query.setParameter("empId", empid);
        query.setParameter("loggedIn", flag);
        query.executeUpdate();
        /* JPA Query End */

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#findEmployeeInfo(java.lang.String)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<EmployeeSearchDTO> findEmployeeInfo(
            final String empName, final Organisation organisation) {
        final String hql = "select e.empname,e.empId from Employee e where e.organisation=:organisation and lower(e.empname) like lower(:empName) and e.empname not like '%#%'  and e.emplType is null ";

        /* JPA Query Start */
        final List<EmployeeSearchDTO> employeeSearchDTOs = new ArrayList<>();
        final Query query = createQuery(hql);
        query.setParameter("organisation", organisation);
        query.setParameter("empName", MainetConstants.operator.PERCENTILE + empName + MainetConstants.operator.PERCENTILE);
        final List result = query.getResultList();

        for (final Iterator it = result.iterator(); it
                .hasNext();) {
            final Object[] dtoValue = (Object[]) it.next();

            final EmployeeSearchDTO employeeSearchDTO = new EmployeeSearchDTO();

            employeeSearchDTO.setEmployeeName(
                    dtoValue[0].toString());

            employeeSearchDTO.setId(
                    Long.valueOf(dtoValue[1].toString()));

            employeeSearchDTOs.add(employeeSearchDTO);

        }
        return employeeSearchDTOs;
    }

    @Override
    public Employee changeMobileNumber(final Employee emp,
            final String newMobNo) {

        if (emp.getAutMob().equals(MainetConstants.IsDeleted.NOT_DELETE)) {
            emp.setAutMob(MainetConstants.IsDeleted.DELETE);
        }
        emp.setUpdatedBy(emp);
        emp.setUpdatedDate(new Date());
        emp.setEmpmobno(newMobNo);
        emp.setEmploginname(newMobNo);
        entityManager.merge(emp);
        return emp;
    }

    public List<Object[]> getAllDeptEmployeeList(
            final Organisation orgId) {

        /* JPA Query Start */
        final Query query = createQuery(
                "Select e.title, e.empname, e.empMName, e.empLName, e.empId from Employee e where e.organisation =?1 and e.isdeleted = ?2 and e.emplType is null and e.empname not like '%#%' order by e.empname asc ");
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.MENU._0);

        final List<Object[]> listEmployee = query.getResultList();

        if (listEmployee.size() > 0) {
            return listEmployee;
        } else {
            return Collections.emptyList();
        }
    }

    public List<Object[]> getAllDeptEmpListByDeptId(
            final Organisation orgId, final long deptId) {

        final Query query = createQuery(
                "Select e.title, e.empname, e.empMName, e.empLName, e.empId from Employee e where e.organisation =?1 and e.dpDeptid = ?2 and e.isdeleted =?3 and e.emplType is null and e.empname not like '%#%' ");
        query.setParameter(1, orgId);
        query.setParameter(2, deptId);
        query.setParameter(3, MainetConstants.MENU._0);

        final List<Object[]> listEmployee = query.getResultList();

        if (listEmployee.size() > 0) {

            return listEmployee;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<Long, String> getEmployeeLookUp(
            final Organisation organisation) {
        final Map<Long, String> lookUps = new LinkedHashMap<>();

        /* JPA Query Start */
        final Query query = createQuery(
                "Select e.empname, e.empLName, e.empId from Employee e where e.organisation =?1 and e.isdeleted = ?2 and e.emplType is null order by e.empname asc ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.MENU._0);

        final List<Object[]> listEmployee = query.getResultList();
        /* JPA Query End */

        for (final Object[] indi : listEmployee) {
            if ((indi[0] != null) && (indi[1] != null)) {
                lookUps.put(new Long(indi[2].toString()),
                        indi[0].toString() + MainetConstants.WHITE_SPACE
                                + indi[1].toString());
            } else if (indi[0] != null) {
                lookUps.put(new Long(indi[2].toString()),
                        indi[0].toString());
            } else if (indi[1] != null) {
                lookUps.put(new Long(indi[2].toString()),
                        indi[1].toString());
            }

        }
        return lookUps;
    }

    @Override
    public void generateLicenseData(final Employee employee) {

    }

    @Override
    public boolean isUniqueUserAlias(final String userAlias,
            final Organisation organisation) {

        final Query query = createQuery(
                "Select Count(e.empId) from Employee e where e.organisation= ?1 and e.userAlias =?2 ");
        query.setParameter(1, organisation);
        query.setParameter(2, userAlias);
        final Long count = (Long) query.getSingleResult();
        if ((count != null) && (count > 0)) {
            return false;
        }
        return true;

    }

    @Override
    public Employee getEmployeeByUserAlias(final String userAlias,
            final Organisation organisation, final String isDeleted) {

        final StringBuilder queryAppender = new StringBuilder();
        queryAppender.append(
                "Select e from Employee e  where e.userAlias = ?1 ");
        if ((isDeleted != null)
                && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
            queryAppender.append("and e.isdeleted =?2 ");
        }
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            queryAppender.append("and e.organisation =?3 ");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, userAlias);
        if ((isDeleted != null)
                && (!isDeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
            query.setParameter(2, isDeleted);
        }
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            query.setParameter(3, organisation);
        }

        final List<Employee> employeeList = query.getResultList();
        if ((employeeList == null)
                || employeeList.isEmpty()) {
            return null;
        } else {
            return employeeList.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#saveEmployee(com.abm.mainet.domain.core. Employee)
     */
    @Override
    public Employee saveAgencyEmployeeDetails(Employee employee) {
        try {
            employee = entityManager
                    .merge(employee);
            if (employee.getAddFlag()
                    .equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                if (employee.getEmpAddress() != null) {
                    employee.setEmpCorAddress1(
                            employee.getEmpAddress());
                }
                if (employee.getEmpAddress1() != null) {
                    employee.setEmpCorAddress2(
                            employee.getEmpAddress1());
                }
                if (employee.getPincode() != null) {
                    employee.setCorPincode(
                            employee.getPincode());
                }
            }
            entityManager.persist(employee);
            return employee;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return employee;
        }
    }

    @Override
    public Employee getAuthenticatedAgencyEmployee(
            final String emploginname, final String emppassword,
            final Long emplType, final Organisation organisation,
            final String isdeleted, final String type) {

        final Map<Integer, Object> map = new LinkedHashMap<>();
        int count = 2;
        final StringBuilder queryAppender = new StringBuilder(
                "Select e from Employee e where e.emploginname = ?1 and  ");

        if (MainetConstants.MOBILE_NUMBER_IDENTIFICATION.NON_REGISTERED_NUMBER
                .equals(type)) {
            queryAppender.append(
                    "e.mobNoOtp =?" + count + AND_STRING);
            map.put(count, emppassword);
            count++;
        } else {

            queryAppender.append(
                    "e.emppassword =?" + count + AND_STRING);
            map.put(count, emppassword);
            count++;
        }

        if (emplType != null) {
            queryAppender.append(
                    "e.emplType =?" + count + AND_STRING);
            map.put(count, emplType);
            count++;
        }

        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            queryAppender.append(
                    "e.organisation =?" + count + AND_STRING);
            map.put(count, organisation);
            count++;
        }

        if ((isdeleted != null)
                && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
            queryAppender.append(
                    "e.isdeleted =?" + count + AND_STRING);
            map.put(count, isdeleted);
            count++;
        }

        queryAppender.delete(queryAppender.length() - 6,
                queryAppender.length());
        final Set<Entry<Integer, Object>> entries = map
                .entrySet();
        final Iterator<Entry<Integer, Object>> entrySetItr = entries
                .iterator();
        Query query = createQuery(queryAppender.toString());
        query.setParameter(1, emploginname);

        while (entrySetItr.hasNext()) {
            final Entry<Integer, Object> entry = entrySetItr
                    .next();
            query = query.setParameter(entry.getKey(),
                    entry.getValue());
        }

        final List<Employee> list = query.getResultList();

        if ((null == list) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public Employee getAuthenticatedAgencyEmployee(
            final String emploginname, final String emppassword,
            final Long emplType, final Organisation organisation,
            final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e  where e.emploginname = ?1 and  ");
            int count = 2;
            final Map<Integer, Object> map = new LinkedHashMap<>();
            if (emppassword != null) {
                queryAppender.append(
                        "e.emppassword =?" + count + AND_STRING);
                map.put(count, emppassword);
                count++;
            }
            if (emplType != null) {
                queryAppender.append(
                        "e.emplType =?" + count + AND_STRING);
                map.put(count, emplType);
                count++;
            }
            if ((organisation != null)
                    && (organisation.getOrgid() != 0l)) {
                queryAppender.append("e.organisation =?"
                        + count + AND_STRING);
                map.put(count, organisation);
                count++;
            }
            if ((isdeleted != null)
                    && (!isdeleted.equalsIgnoreCase(MainetConstants.BLANK))) {
                queryAppender.append("e.isdeleted =?"
                        + count + AND_STRING);
                map.put(count, isdeleted);
                count++;
            }

            queryAppender.delete(queryAppender.length() - 6,
                    queryAppender.length());
            final Set<Entry<Integer, Object>> entries = map
                    .entrySet();
            final Iterator<Entry<Integer, Object>> iterator = entries
                    .iterator();
            Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);

            while (iterator.hasNext()) {
                final Entry<Integer, Object> entry = iterator
                        .next();
                query = query.setParameter(entry.getKey(),
                        entry.getValue());
            }

            final List<Employee> employees = query
                    .getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return null;
        }
    }

    @Override
    public List<Employee> getAgencyEmployeeByEmpMobileNo(
            final String empMobileNo, final Organisation organisation,
            final String isDeleted) {

        final StringBuilder queryAppender = new StringBuilder(
                "Select e from Employee e  where e.empmobno = ?1 ");
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            queryAppender.append("and e.organisation =?2 ");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, empMobileNo);
        if ((organisation != null)
                && (organisation.getOrgid() != 0l)) {
            query.setParameter(2, organisation);
        }

        final List<Employee> employeeList = query.getResultList();
        return employeeList;
    }

    @Override
    public void setAgencyEmployeeLoggedInFlag(
            final Employee currentLoggedInEmployee) {

        entityManager.merge(currentLoggedInEmployee);

    }

    @Override
    public Employee saveUpdatedAgencyEmployeeDetails(
            final Employee employee) {
        entityManager.merge(employee);
        return employee;
    }

    @Override
    public Long findCountOfEmployee() {

        final Query query = createQuery(
                "SELECT count(e.empId) FROM Employee e ");
        return (Long) query.getSingleResult();
    }

    @Override
    public Long findCountOfLoggedInUser() {

        final Query query = createQuery(
                "SELECT count(e.empId) FROM Employee e where e.loggedIn = ?1");
        query.setParameter(1, MainetConstants.MENU.Y);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Employee> findAllEmployeeByDept(final Long orgId, final Long deptId) {
        final Query query = createQuery(
                "SELECT e FROM Employee e  WHERE e.organisation.orgid=?1 and  e.tbDepartment.dpDeptid =?2 and e.isDeleted = '0' order by e.empId asc");
        query.setParameter(1, orgId);
        query.setParameter(1, deptId);
        return query.getResultList();
    }

    public Employee create(Employee employee) {
        this.entityManager.persist(employee);
        return employee;
    }

    @Override
    public List<Object[]> getAllEmpByDesignation(Long desgId, Long orgId) {
        Query query = entityManager.createQuery(
                " select e.empId,e.empname,e.empmname,e.emplname from Employee e  where e.organisation.orgid=:orgId and e.designation.dsgid=:desgId ");
        query.setParameter("orgId", orgId).setParameter("desgId", desgId);

        return query.getResultList();
    }

    @Override
    public String findEmployeeSessionId(Long empId, String isdeleted) {
        Query query = entityManager
                .createQuery("select e.empisecuritykey from Employee e where e.empId=:empId and e.isdeleted=:isdeleted");
        query.setParameter("empId", empId);
        query.setParameter("isdeleted", isdeleted);

        String sessionId = (String) query.getSingleResult();

        return sessionId;

    }

	@Override
    public void updateEmployeeLoggedInFlag(String loggedIn, String sessionId) {
        final Query query = createQuery("UPDATE Employee SET loggedIn = :loggedIn  WHERE empisecuritykey = :sessionId");
        query.setParameter("sessionId", sessionId);
        query.setParameter("loggedIn", loggedIn);
        query.executeUpdate();
    }
}
