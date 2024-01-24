package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.TenderMasterEntity;

/**
 * @author hiren.poriya
 * @Since 24-Apr-2018
 */
public interface TenderInitiationRepositoryCustom {

    /**
     * search tender details based on search criteria
     * @param orgid
     * @param projId
     * @param initiationNo
     * @param initiationDate
     * @param tenderCategory
     * @param venderClassId
     * @return List<TenderMasterEntity>
     */
    List<TenderMasterEntity> searchTenderDetails(Long orgid, Long projId, String initiationNo, Date initiationDate,
            Long tenderCategory, String flag);

}
