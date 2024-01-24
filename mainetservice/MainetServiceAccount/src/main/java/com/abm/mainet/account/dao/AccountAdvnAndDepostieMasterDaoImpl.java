
package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountDepositeAndAdvnMasterEntity;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;

/**
 * @author prasant.sahu
 *
 */
@Repository
public class AccountAdvnAndDepostieMasterDaoImpl extends AbstractDAO<AccountFunctionMasterEntity>
        implements AccountAdvnAndDepostieMasterDao {

    @Override
    public List<AccountDepositeAndAdvnMasterEntity> getDepositeAndAdvncMappingEntityList(final long orgid, final Long mappingType,
            final Long advncOrDepType, final Long deptId) {
        final StringBuilder queryString = new StringBuilder(
                "select entity from AccountDepositeAndAdvnMasterEntity entity where entity.orgid =" + orgid
                        + " and entity.tbComparamDetHdm.cpdId= " + mappingType
                        + " and entity.tbComparamDetDtyAty.cpdId= " + advncOrDepType);

        if (deptId != 0) {
            queryString.append(" and entity.dept=" + deptId);
        }
        if (deptId == 0) {
            queryString.append(" and entity.dept is null");
        }

        final Query query = entityManager.createQuery(queryString.toString());

        @SuppressWarnings("unchecked")
        final List<AccountDepositeAndAdvnMasterEntity> listOfEntity = query.getResultList();
        return listOfEntity;
    }

}
