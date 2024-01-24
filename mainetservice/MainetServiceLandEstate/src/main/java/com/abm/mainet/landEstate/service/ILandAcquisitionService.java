package com.abm.mainet.landEstate.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;

public interface ILandAcquisitionService {

    public LandAcquisitionDto saveLandAcquisition(LandAcquisitionDto acqDto, ServiceMaster serviceMaster,
            List<DocumentDetailsVO> checkList,
            RequestDTO requestDTO);

    LandAcquisitionDto getAcquisitionChargesFromBrmsRule(LandAcquisitionDto acqDto);

    LandAcquisitionDto getLandAcqDataByApplicationId(Long apmApplicationId, Long orgId);

    LandAcquisitionDto getLandAcqProposalByAppId(Long apmApplicationId);

    int checkDuplicateLand(String lnServno, String lnArea, Long locId, String payTo);

    List<LandAcquisitionDto> fetchSearchData(String servid, String payTo, String acqStatus, Long acqModeId, Long orgid);

    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

    List<String> fetchProposalNoList();

    void updateLandValuationData(Long apmApplicationId, BigDecimal acqCost, Long vendorId, String billNo, Long assetId,
            String transferStatus, Date acqDate, Long orgId);

    Long pushAssetDetails(AssetDetailsDTO astDet);

    void updateLandProposalAcqStatusById(Long apmApplicationId, Employee employee,
            Long serviceId, Long orgId,
            Long taskId);

    void updateLoiPayableData(LandAcquisitionDto acquisitionDto, String workFlowDecision, Long taskId, Employee employee,
            Organisation org, int langId, Long orgId);

}
