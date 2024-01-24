package com.abm.mainet.landEstate.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.landEstate.domain.LandAcquisition;

public interface ILandAcquisitionDao {
    List<LandAcquisition> searchLandAcquisitionData(String servid, String payTo, String acqStatus, Long acqModeId, Long orgid);

    void updateLandValuationData(Long apmApplicationId, BigDecimal acqCost, Long vendorId, String billNo, Long assetId,
            String transferStatus, Date acqDate, Long orgId);

    TbLoiMasEntity getLoiMasDataByApplicationId(Long applicationId);

}
