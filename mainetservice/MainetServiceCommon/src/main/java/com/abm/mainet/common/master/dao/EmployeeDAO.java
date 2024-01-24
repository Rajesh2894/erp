package com.abm.mainet.common.master.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.EmployeeSearchDTO;

@Repository
public class EmployeeDAO extends AbstractDAO<Employee> implements Serializable, IEmployeeDAO {

    private static final String AND_STRING = " and  ";
    private static final long serialVersionUID = -5914813589396725739L;

    private static Logger log = Logger.getLogger(EmployeeDAO.class);

    @Override
    public int getCountOfGroup(final Long gmId, final Long orgId, final String isDeleted) {

        final String strQuery = "select count(e.gmid) from Employee e where e.organisation.orgid = ?1"
                + " and e.isDeleted = ?2 and e.gmid = ?3 ";
        int count = 0;
        try {

            /* JPA Query Start */
            final Query query = createQuery(strQuery);
            query.setParameter(1, orgId);
            query.setParameter(2, isDeleted);
            query.setParameter(3, gmId);
            count = ((Long) query.getSingleResult()).intValue();
            /* JPA Query End */

        } catch (final NumberFormatException e) {
        } catch (final HibernateException e) {
        } catch (final Exception e) {
        }
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByLoginName(java.lang.String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public Employee getEmployeeByLoginName(final String emploginname, final Organisation organisation, final String isDeleted) {

        final Query query = createQuery(
                "Select e from Employee e join FETCH e.organisation o  where  e.emploginname =?1 and e.organisation.orgid =?2 and e.isDeleted =?3 ");
        query.setParameter(1, emploginname);
        query.setParameter(2, organisation.getOrgid());
        query.setParameter(3, isDeleted);

        try {
            final List<Employee> employees = query.getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {

            return null;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeById(java.lang.Long, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public Employee getEmployeeById(final Long empId, final Organisation organisation, final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e where e.organisation = ?1 and e.empId = ?2 ");

            if ((isdeleted != null) && (!isdeleted.equalsIgnoreCase(""))) {
                queryAppender.append("and e.isDeleted = ?3 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, organisation);
            query.setParameter(2, empId);

            if ((isdeleted != null) && (!isdeleted.equalsIgnoreCase(""))) {
                query.setParameter(3, isdeleted);
            }

            @SuppressWarnings("unchecked")
            final List<Employee> employees = query.getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            return null;
        }
    }

    @Override
    public Employee getAuthenticatedEmployee(final String emploginname, final String empOtp, final Long emplType,
            final Organisation organisation,
            final String isdeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder(
                    "Select e from Employee e  where e.emploginname = ?1 and e.mobNoOtp = ?2 and  ");
            int count = 3;
            final Map<Integer, Object> map = new LinkedHashMap<>();
            if (emplType != null) {
                queryAppender.append("e.emplType =?" + count + " and  ");
                map.put(count, emplType);
                count++;
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                queryAppender.append("e.organisation =?" + count + " and  ");
                map.put(count, organisation);
                count++;
            }
            if ((isdeleted != null) && (!isdeleted.equalsIgnoreCase(""))) {
                queryAppender.append("e.isDeleted =?" + count + " and  ");
                map.put(count, isdeleted);
                count++;
            }

            queryAppender.delete(queryAppender.length() - 6, queryAppender.length());
            final Set<Entry<Integer, Object>> entries = map.entrySet();
            final Iterator<Entry<Integer, Object>> iterator = entries.iterator();
            Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);
            query.setParameter(2, empOtp);

            while (iterator.hasNext()) {
                final Entry<Integer, Object> entry = iterator.next();
                query = query.setParameter(entry.getKey(), entry.getValue());
            }

            @SuppressWarnings("unchecked")
            final List<Employee> employees = query.getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAdminAuthenticatedEmployee(java.lang.String, java.lang.String, java.lang.Long,
     * java.lang.Long, com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAdminAuthenticatedEmployee(final String emploginname, final String emppassword, final Long emplType,
            final Long empId,
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
                queryAppender.append("e.isDeleted =?"
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

            @SuppressWarnings("unchecked")
            final List<Employee> employees = query.getResultList();
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
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmail(java.lang.String, java.lang.String,
     * com.abm.mainet.domain.core.Organisation)
     */
    @Override
    public List<Employee> getEmployeeByEmail(final String empEmail, final String empPassword, final Organisation organisation) {

        final StringBuilder queryAppender = new StringBuilder("Select e from Employee e  where e.empemail = ?1 ");
        if ((organisation != null) && (organisation.getOrgid() != 0l)) {
            queryAppender.append("and e.organisation =?2 ");
        }
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, empEmail);
        if ((organisation != null) && (organisation.getOrgid() != 0l)) {
            query.setParameter(2, organisation);
        }

        @SuppressWarnings("unchecked")
        final List<Employee> employeeList = query.getResultList();
        return employeeList;
    }

    /*
     * Method to get Employee by Email Id, adding the check for employee_type
     */
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmailIdAndEmpType(java.lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String, boolean)
     */
    @Override
    public List<Employee> getEmployeeByEmailIdAndEmpType(final String empEMail, final Long empType,
            final Organisation organisation,
            final String isDeleted, final boolean isAgency) {
        try {

            /* JPA Query Start */
            final StringBuilder queryAppender = new StringBuilder("Select e from Employee e  where e.empemail = ?1 ");
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                queryAppender.append("and e.isDeleted =?2 ");
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                queryAppender.append("and e.organisation =?3 ");
            }
            if (empType != null) {
                if (isAgency) {
                    queryAppender.append("and e.emplType <> ?4");
                } else {
                    queryAppender.append("and e.emplType = ?4 ");
                }
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empEMail);
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }
            if (empType != null) {
                if (isAgency) {
                    query.setParameter(4, empType);
                } else {
                    query.setParameter(4, empType);
                }
            }

            @SuppressWarnings("unchecked")
            final List<Employee> employeeList = query.getResultList();
            return employeeList;
        } catch (final Exception exception) {
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmpMobileNo(java.lang.String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public List<Employee> getEmployeeByEmpMobileNo(final String empmobno, final Organisation organisation,
            final String isDeleted) {
        try {

            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append("Select e from Employee e  where e.empmobno = ?1 or e.empemail =?2 ");
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                queryAppender.append("AND e.isDeleted =?3 ");
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                queryAppender.append("AND e.organisation =?4 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empmobno);
            query.setParameter(2, empmobno);
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                query.setParameter(3, isDeleted);
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                query.setParameter(4, organisation);
            }

            @SuppressWarnings("unchecked")
            final List<Employee> employeeList = query.getResultList();

            return employeeList;
        } catch (final Exception exception) {
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByEmpMobileNoAndEmpType(java.lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String, boolean)
     */
    @Override
    public List<Employee> getEmployeeByEmpMobileNoAndEmpType(final String empMobileNo, final Long empType,
            final Organisation organisation,
            final String isDeleted, final boolean isAgency) {
        try {

            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append("Select e from Employee e  where e.empmobno = ?1 ");
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                queryAppender.append("and e.isDeleted =?2 ");
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                queryAppender.append("and e.organisation =?3 ");
            }
            if (empType != null) {
                if (isAgency) {
                    queryAppender.append("and e.emplType <> ?4 ");
                } else {
                    queryAppender.append("and e.emplType = ?4 ");
                }
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, empMobileNo);
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }
            if (empType != null) {
                query.setParameter(4, empType);
            }
            @SuppressWarnings("unchecked")
            final List<Employee> employeeList = query.getResultList();
            return employeeList;
        } catch (final Exception exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeListByLoginName(java.lang.String, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public List<Employee> getEmployeeListByLoginName(final String emploginname, final Organisation organisation,
            final String isDeleted) {

        try {

            final StringBuilder queryAppender = new StringBuilder();
            queryAppender.append("Select e From Employee e  where e.emploginname = ?1 ");
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                queryAppender.append("and e.isDeleted =?2 ");
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                queryAppender.append("and e.organisation =?3 ");
            }

            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, emploginname);
            if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
                query.setParameter(2, isDeleted);
            }
            if ((organisation != null) && (organisation.getOrgid() != 0l)) {
                query.setParameter(3, organisation);
            }

            @SuppressWarnings("unchecked")
            final List<Employee> employeeList = query.getResultList();
            return employeeList;
        } catch (final Exception exception) {
            return new ArrayList<>();
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#saveEmployee(com.abm.mainet.domain.core.Employee)
     */
    @Override
    public Employee saveEmployee(Employee employee) {
        try {
            employee = entityManager.merge(employee);
            if (employee.getAddFlag() != null && employee.getAddFlag().equalsIgnoreCase("Y")) {
                if (employee.getEmpAddress() != null) {
                    employee.setEmpCorAdd1(employee.getEmpAddress());
                }
                if (employee.getEmpAddress1() != null) {
                    employee.setEmpCorAdd2(employee.getEmpAddress1());
                }
                if (employee.getEmppincode() != null) {
                    employee.setEmpCorPincode(employee.getEmppincode());
                }

            }
            employee = create(employee);
            return employee;
        } catch (final Exception exception) {
            return employee;
        }
    }

    @Override
    public Employee saveEmployeeForAgency(Employee employee) {
        try {
            employee = entityManager.merge(employee);
            if (employee.getAddFlag().equalsIgnoreCase("Y")) {
                if (employee.getEmpAddress() != null) {
                    employee.setEmpCorAdd1(employee.getEmpAddress());
                }
                if (employee.getEmpAddress1() != null) {
                    employee.setEmpCorAdd2(employee.getEmpAddress1());
                }
                if (employee.getEmppincode() != null) {
                    employee.setEmpCorPincode(employee.getEmppincode());
                }

            }

            return employee;
        } catch (final Exception exception) {
            return null;

        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAllEmployeeByDeptId(com.abm.mainet.domain.core.Organisation, long)
     */
    @Override
    public List<Employee> getAllEmployeeByDeptId(final Organisation orgId, final long deptId) {

        final Query query = createQuery(
                "Select e from Employee e  where e.organisation = ?1 and e.tbDepartment.dpDeptid =?2 and e.isDeleted =?3 ");
        query.setParameter(1, orgId);
        query.setParameter(2, deptId);
        query.setParameter(3, "0");

        @SuppressWarnings("unchecked")
        final

        List<Employee> listEmployee = query.getResultList();
        if (listEmployee.size() > 0) {
            final ListIterator<Employee> iterator = listEmployee.listIterator();

            while (iterator.hasNext()) {
                final Employee employee = iterator.next();
                employee.getDesignation().getDsgname();
            }

            return listEmployee;
        } else {
            return Collections.emptyList();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getEmployeeByAgencyTypeAndBySortOption(java.lang.Long, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeDTO> getEmployeeByAgencyTypeAndBySortOption(final Long agencyType, final String agencyName,
            final String sortValue, final Long orgId) {

        final StringBuilder queryAppender = new StringBuilder(
                "Select e.empId,e.empmobno,e.empname,e.authStatus from Employee e  where e.organisation.orgid = ?1 and e.emplType=?2 ");

        if (sortValue.equals("P")) {
            queryAppender.append(" and e.authStatus = 'P' ");
        } else if (sortValue.equals("R")) {

            queryAppender.append(" and(e.authStatus = 'R' or e.authStatus = 'D') ");
        } else if (sortValue.equals("H")) {

            queryAppender.append(" and(e.authStatus = 'H' or e.authStatus = 'D') ");
        } else {
            queryAppender.append(" and e.authStatus = '" + sortValue + "'");

        }
        if ((agencyName != null) && (agencyName != "")) {
            queryAppender.append(" and e.agencyName like '%"
                    + agencyName + "%'");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, agencyType);

        EmployeeDTO employeeDTO = null;
        final List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        final List<Object> result = query.getResultList();

        final int listSize = result.size();

        for (int iCounter = 0; iCounter < listSize; iCounter++) {

            final Object[] obj = (Object[]) result.get(iCounter);

            employeeDTO = new EmployeeDTO();
            if (obj[0] != null) {
                employeeDTO.setEmpId((Long.parseLong(obj[0].toString())));
            }
            if (obj[1] != null) {
                employeeDTO.setEmpname(obj[1].toString());
            }
            if (obj[2] != null) {
                employeeDTO.setEmpmobno(obj[2].toString());
            }

            if (sortValue != null) {
                if (sortValue.equalsIgnoreCase("H")) {
                    employeeDTO.setAuthStatus("On Hold");
                }
                if (sortValue.equalsIgnoreCase("D")) {
                    employeeDTO.setAuthStatus("After Reject");
                }
                if (sortValue.equalsIgnoreCase("A")) {
                    employeeDTO.setAuthStatus("Approved");
                }
                if (sortValue.equalsIgnoreCase("R")) {
                    employeeDTO.setAuthStatus("Rejected");
                }
                if (sortValue.equalsIgnoreCase("P")) {
                    employeeDTO.setAuthStatus("not verified");
                }

            }

            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAllAgencyList()
     */
    @Override
    public List<Employee> getAllAgencyList(final Organisation organisation) {

        final Query query = createQuery("Select e from Employee e  where e.isDeleted = ?1 and e.orgId =?2 ");
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);

        @SuppressWarnings("unchecked")
        final List<Employee> list = query.getResultList();
        return list;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#getAgencyByEmplTypeAndEmail(java.lang.String, java.lang.Long,
     * com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public Employee getAgencyByEmplTypeAndEmail(final String email, final Long agencyType, final Organisation organisation,
            final String isDeleted) {
        /* JPA Query Start */
        final Query query = createQuery(
                "Select e from Employee e  where e.empemail = ?1 and e.emplType =?2 and e.organisation = ?3 and e.isDeleted =?4 ");
        query.setParameter(1, email);
        query.setParameter(2, agencyType);
        query.setParameter(3, organisation);
        query.setParameter(4, isDeleted);
        try {
            final List<Employee> employees = query.getResultList();
            if ((employees == null) || employees.isEmpty()) {
                return null;
            } else {
                return employees.get(0);
            }

        } catch (final Exception exception) {

            return null;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#persistModifiedCitizenInfo(com.abm.mainet.domain.core.Employee)
     */
    @Override
    public void persistModifiedCitizenInfo(final Employee modifiedEmployee) {

        entityManager.merge(modifiedEmployee);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#setEmployeeLoggedInFlag(com.abm.mainet.domain.core.Employee)
     */
    @Override
    public void setEmployeeLoggedInFlag(final Employee currentLoggedInEmployee) {
        entityManager.merge(currentLoggedInEmployee);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IEmployeeDAO#resetEmployeeLoggedInFlag(com.abm.mainet.domain.core.Employee)
     */
    @Override
    public void resetEmployeeLoggedInFlag(final String flag, final long empid) {

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
    public List<EmployeeSearchDTO> findEmployeeInfo(final String empName, final Organisation organisation) {
        final String hql = "select e.empname,e.empId from Employee e where e.organisation=:organisation and lower(e.empname) like lower(:empName) and e.empname not like '%#%'  and e.emplType is null ";

        /* JPA Query Start */
        final List<EmployeeSearchDTO> employeeSearchDTOs = new ArrayList<>();
        final Query query = createQuery(hql);
        query.setParameter("organisation", organisation);
        query.setParameter("empName", "%" + empName + "%");
        final List result = query.getResultList();

        for (final Iterator it = result.iterator(); it.hasNext();) {
            final Object[] dtoValue = (Object[]) it.next();

            final EmployeeSearchDTO employeeSearchDTO = new EmployeeSearchDTO();

            employeeSearchDTO.setEmployeeName(dtoValue[0].toString());

            employeeSearchDTO.setId(Long.valueOf(dtoValue[1].toString()));

            employeeSearchDTOs.add(employeeSearchDTO);

        }
        return employeeSearchDTOs;
    }

    @Override
    public Map<Long, String> getEmployeeLookUp(final Organisation organisation) {
        final Map<Long, String> lookUps = new LinkedHashMap<>();

        /* JPA Query Start */
        final Query query = createQuery(
                "Select e.empname, e.empLName, e.empId from Employee e where e.organisation =?1 and e.isDeleted = ?2 and e.emplType is null order by e.empname asc ");
        query.setParameter(1, organisation);
        query.setParameter(2, "0");
        @SuppressWarnings("unchecked")
        final List<Object[]> listEmployee = query.getResultList();
        /* JPA Query End */

        for (final Object[] indi : listEmployee) {
            if ((indi[0] != null) && (indi[1] != null)) {
                lookUps.put(new Long(indi[2].toString()), indi[0].toString() + " " + indi[1].toString());
            } else if (indi[0] != null) {
                lookUps.put(new Long(indi[2].toString()), indi[0].toString());
            } else if (indi[1] != null) {
                lookUps.put(new Long(indi[2].toString()), indi[1].toString());
            }

        }
        return lookUps;
    }

    @Override
    public List<Employee> findMappedEmployeeLevel1(final Long empId, final Long orgId, final Long dpDeptid) {

        final StringBuilder queryBuilder = new StringBuilder(
                "select distinct employee.empId, employee.empname from Employee employee"
                        + " where employee.tbDepartment.dpDeptid=" + dpDeptid + " and employee.empId<>" + empId
                        + "and employee.organisation.orgid=" + orgId
                        + " and employee.isDeleted<>1 ");
        final List<Employee> empList = new ArrayList<>();
        Employee employee = null;
        final Query query = createQuery(queryBuilder.toString());
        @SuppressWarnings("unchecked")
        final List<Object> empObjList = query.getResultList();
        if (!empObjList.isEmpty()) {
            final int empSize = empObjList.size();
            for (int iCounter = 0; iCounter < empSize; iCounter++) {
                employee = new Employee();
                final Object[] empObj = (Object[]) empObjList.get(iCounter);
                employee.setEmpId(Long.valueOf(empObj[0].toString()));
                employee.setEmpname(empObj[1].toString());
                empList.add(employee);
            }
        }

        return empList;
    }

    @Override
    public int getTotalEmployeeCountByRoles(List<Long> roleIds, Long orgId) {

        final String strQuery = "select count(e.empId) from Employee e where e.organisation.orgid = ?1"
                + " and e.isDeleted = ?2 and e.gmid IN (?3) ";
        int count = 0;

        /* JPA Query Start */
        final Query query = createQuery(strQuery);
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.MENU._0);
        query.setParameter(3, roleIds);
        count = ((Long) query.getSingleResult()).intValue();
        /* JPA Query End */
        return count;
    }

    @Override
    public boolean updateEmpDetails(Employee employee) {

        try {
            /* employee = (Employee) getCurrentSession().merge(employee); */
            this.entityManager.merge(employee);

        } catch (Exception exception) {
            log.error(" Exeception occur in updateEmpDetails()", exception);
        }
        return true;

    }

    @Override
    public String findEmployeeSessionId(Long empId, String isdeleted) {
        Query query = entityManager
                .createQuery("select e.empisecuritykey from Employee e where e.empId=:empId and e.isDeleted=:isdeleted");
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

    @Override
    public List<Long> getEmpId(Long orgid, String isdeleted) {
        final Query query = createQuery(
                "select a.empId from Employee a , GroupMaster b where a.gmid = b.gmId and a.organisation.orgid = b.orgId and a.organisation.orgid =:orgid "
                        + " and b.grCode ='SUPER_ADMIN' and a.isDeleted=:isdeleted  order by a.lmodDate asc");
        query.setParameter("orgid", orgid);
        query.setParameter("isdeleted", isdeleted);
        @SuppressWarnings("unchecked")
        final List<Long> list = query.getResultList();
        return list;

    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmployeeByReportingManager(Long reportingManager, Long orgId,String isdeleted) {
		Query query = entityManager
                .createQuery("select e from Employee e where e.reportingManager=:reportingManager and e.isDeleted=:isDeleted and e.organisation.orgid =:orgid");
		query.setParameter("orgid", orgId);
        query.setParameter("isDeleted", isdeleted);
        query.setParameter("reportingManager", reportingManager);
        final List<Employee> list = query.getResultList();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.master.dao.IEmployeeDAO#findEmployeeDataByIdsSFAC(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Employee> findEmployeeDataByIdsSFAC(Long deptId, Long locId, Long designId, Long orgid, Long gmid,
			Long masId) {

		try {

			/* JPA Query Start */
			final StringBuilder queryAppender = new StringBuilder("Select e from Employee e  where e.organisation.orgid = ?1 ");

			if ((deptId != null) && (deptId != 0l)) {
				queryAppender.append("and e.tbDepartment.dpDeptid =?2 ");
			}

			if ((locId != null) && (locId != 0l)) {
				queryAppender.append("and e.tbLocationMas.locId =?3 ");
			}

			if ((designId != null) && (designId != 0l)) {
				queryAppender.append("and e.designation.dsgid =?4 ");
			}

			if ((gmid != null) && (gmid != 0l)) {
				queryAppender.append("and e.gmid =?5 ");
			}

			if ((masId != null) && (masId != 0l)) {
				queryAppender.append("and e.masId =?6 ");
			}

			final Query query = createQuery(queryAppender.toString());
			query.setParameter(1, orgid);

			if ((deptId != null) && (deptId != 0l)) {
				query.setParameter(2, deptId);
			}
			if ((locId != null) && (locId != 0l)) {
				query.setParameter(3, locId);
			}

			if ((designId != null) && (designId != 0l)) {
				query.setParameter(4, designId);
			}
			if ((gmid != null) && (gmid != 0l)) {
				query.setParameter(5, gmid);
			}

			if ((masId != null) && (masId != 0l)) {
				query.setParameter(6, masId);
			}

			@SuppressWarnings("unchecked")
			final List<Employee> employeeList = query.getResultList();
			return employeeList;
		} catch (final Exception exception) {
			return new ArrayList<>();
		}

	}

	@Override
	public List<Employee> getAllMPOSEmployee(String firstName, String lastName, String loginName) {
		Query query = entityManager
                .createQuery("select e from Employee e where e.empname=:firstName and e.emplname=:lastName and e.emploginname =:loginName");
		query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("loginName", loginName);
        final List<Employee> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Employee> getAllEmployeeByGmId(long orgId, long roleId) {
		Query query = entityManager
                .createQuery("select e from Employee e where e.organisation.orgid=:orgId and e.gmid=:roleId and e.isDeleted ='0'");
		query.setParameter("orgId", orgId);
        query.setParameter("roleId", roleId);
        final List<Employee> list = query.getResultList();
		return list;
	}
}
