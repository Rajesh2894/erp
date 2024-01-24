package com.abm.mainet.agency.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.agency.dto.TPAgencyReqDTO;
import com.abm.mainet.agency.dto.TPAgencyResDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dao.RestDaoImpl;

/**
 * @author Arun.Chavda
 *
 */
@Repository
public class AgencyRegistrationProcessDaoImpl extends RestDaoImpl<Employee> implements AgencyRegistrationProcessDao {
    @Override
    public Employee saveAgnEmployeeDetails(final Employee employee) {
        entityManager.persist(employee);
        return employee;
    }

    @Override
    public TPAgencyResDTO getAuthStatus(final TPAgencyReqDTO requestDTO) {

        final TPAgencyResDTO responseDTO = new TPAgencyResDTO();

        final StringBuilder queryAppender = new StringBuilder(
                "Select e from Employee e  where e.organisation.orgid = ?1 and e.empId=?2 ");

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, requestDTO.getOrgId());
        query.setParameter(2, requestDTO.getEmpId());

        @SuppressWarnings("unchecked")
        final List<Employee> list = query.getResultList();

        if ((null != list) && !list.isEmpty()) {
            responseDTO.setAuthStatus(list.get(0).getAuthStatus());
            responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
        }
        return responseDTO;
    }
    @Override
    public void updatedAuthStatus(final Long empId, final Long orgId, final String flag) {

        final Query query = entityManager.createNativeQuery("UPDATE EMPLOYEE set AUTH_STATUS='P',ISUPLOADED='Y' WHERE EMPID ="
                + empId);
        query.executeUpdate();

    }

}
