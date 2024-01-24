package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Department> getAllDepartment(final String activeStatus) {
        final Query query = createQuery("Select d from Department d where d.status = ?1 ");
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

        if ((status != null) && (!status.equalsIgnoreCase(MainetConstants.BLANK))) {
            queryAppender.append(" AND d.status =?2 ");
        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, dpDeptcode);

        if ((status != null) && (!status.equalsIgnoreCase(MainetConstants.BLANK))) {
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
     * @see com.abm.mainet.dao.IDepartmentDAO#getDepartment(java.lang.Long, java.lang.String)
     */
    @Override
    public Department getDepartment(final Long dpDeptId, final String status) {

		StringBuilder queryAppender = new StringBuilder("Select d from Department d where d.dpDeptid = ?1 ");
		
		if (status != null && (!status.equalsIgnoreCase(""))){
			queryAppender.append(" AND d.status =?2 ");
		}
		
		Query query=this.createQuery(queryAppender.toString());
		query.setParameter(1, dpDeptId);
		
		if (status != null && (!status.equalsIgnoreCase(""))){
		query.setParameter(2, status);
		}
		
		@SuppressWarnings("unchecked")
		List<Department> departmentList = query.getResultList();
		
		if (departmentList == null || departmentList.isEmpty()) {
	        return null;
	    }else
	      return departmentList.get(0);	
		
	}

		@Override
		public Department createDepartment(Department department) {
			this.entityManager.persist(department);
			return department;
		}

}
