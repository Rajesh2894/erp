
package com.abm.mainet.account.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AdvanceEntryRepositoryImpl extends AbstractDAO<AdvanceEntryEntity> implements AdvanceEntryRepositoryCustom {

    @Override
    public void updateAdvanceEntryAuditDetails(AdvanceEntryDTO advanceEntryDTO, Long orgId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<AdvanceEntryEntity> updateAdv = criteriaBuilder.createCriteriaUpdate(AdvanceEntryEntity.class);
        Root<AdvanceEntryEntity> updateAdvRoot = updateAdv.from(AdvanceEntryEntity.class);

        if (advanceEntryDTO.getUpdatedBy() != null && advanceEntryDTO.getUpdatedBy() != 0) {
            updateAdv.set("updatedBy", advanceEntryDTO.getUpdatedBy());
        }
        if (advanceEntryDTO.getUpdatedDate() != null) {
            updateAdv.set("updatedDate", advanceEntryDTO.getUpdatedDate());
        }
        if (advanceEntryDTO.getLgIpMacUpd() != null && !advanceEntryDTO.getLgIpMacUpd().isEmpty()) {
            updateAdv.set("lgIpMacUpd", advanceEntryDTO.getLgIpMacUpd());
        }
        updateAdv.where(criteriaBuilder.equal(updateAdvRoot.get("prAdvEntryId"), advanceEntryDTO.getPrAdvEntryId()),
                criteriaBuilder.equal(updateAdvRoot.get("orgid"), orgId));
        int result = entityManager.createQuery(updateAdv).executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Advance Entry Entity could not be updated");
        }

    }

}
