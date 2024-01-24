package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Department;

/**
 * @author Pranit.Mhatre
 * @since 05 February, 2014
 */
@Repository
public class DepartmentDAO extends AbstractDAO<Department> implements IDepartmentDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDepartmentDAO#getAllDepartment(java.lang.String)
     */

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getAllDepartment(String activeStatus) {
        final Query query = createQuery("Select d from Department d where d.status = ?1 ORDER BY d.dpDeptdesc ASC");
        query.setParameter(1, activeStatus);
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDepartmentDAO#getDepartment(java.lang.String, java.lang.String)
     */
    @Override
    public Department getDepartment(final String dpDeptcode, final String status) {
        final StringBuilder queryAppender = new StringBuilder("Select d from Department d where d.dpDeptcode = ?1 ");

        if ((status != null) && (!status.equalsIgnoreCase(""))) {
            queryAppender.append(" AND d.status =?2 ");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, dpDeptcode);

        if ((status != null) && (!status.equalsIgnoreCase(""))) {
            query.setParameter(2, status);
        }

        @SuppressWarnings("unchecked")
        final List<Department> departmentList = query.getResultList();

        if ((departmentList == null) || departmentList.isEmpty()) {
            return null;
        } else {
            return departmentList.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.dao.IDepartmentDAO#getDepartmentIdByDeptCode(java.lang.String, java.lang.String)
     */
    @Override
    public Long getDepartmentIdByDeptCode(final String dpDeptcode, final String status) {
        final StringBuilder queryAppender = new StringBuilder(
                "Select d.dpDeptid from Department d where d.dpDeptcode = ?1 and d.status =?2 ");
        Long department = null;
        try {
            final Query query = createQuery(queryAppender.toString());
            query.setParameter(1, dpDeptcode);
            query.setParameter(2, status);
            department = (Long) query.getSingleResult();
        } catch (final NoResultException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Department> getAllDepartmentWithDpCheck(final String activeStatus, final String dpCheck) {
        final Query query = createQuery(
                "Select d from Department d where d.status = ?1 and d.dpCheck = ?2 ORDER BY d.dpDeptdesc ASC");
        query.setParameter(1, activeStatus);
        query.setParameter(2, dpCheck);
        return query.getResultList();

    }

	

}
