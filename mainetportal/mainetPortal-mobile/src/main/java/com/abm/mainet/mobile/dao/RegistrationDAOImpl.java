package com.abm.mainet.mobile.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.mobile.dto.EmployeeRequestDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class RegistrationDAOImpl extends AbstractDAO<Employee> implements RegistrationDAO {

    private static final Logger LOG = Logger.getLogger(RegistrationDAOImpl.class);

    @Override
    public boolean getEmployee(final EmployeeRequestDTO employeeDTO) {
        LOG.info("getEmployee ()");
        boolean flag = false;
        try {

            final Query query = entityManager
                    .createQuery("select db from Employee  db where db.empmobno=:empmobno and db.organisation.orgid =:orgId");
            query.setParameter(MainetConstants.Common.EMP_MOB_NO, String.valueOf(employeeDTO.getMobileNo()));
            query.setParameter(MainetConstants.Common.ORGID, employeeDTO.getOrgId());
            final Employee emp = (Employee) query.getSingleResult();
            if (emp != null) {
                flag = true;
            }

        } catch (final Exception exception) {
            LOG.error("Exception occur in getEmployee", exception);
            flag = false;
        }
        return flag;
    }

}
