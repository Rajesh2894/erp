package com.abm.mainet.water.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public interface WaterChecklistAndChargeService {

    List<DocumentDetailsVO> getFileUploadList(List<DocumentDetailsVO> checkList, Map<Long, Set<File>> fileMap);

    /**
     * @return
     */
    // WSResponseDTO initializeModel();

    /**
     * @param dto
     * @return
     */
    // List<DocumentDetailsVO> getChecklist(CheckListModel checklistModel);

    /**
     * @param rate
     * @param orgid
     * @param serviceId
     * @return
     */
    // WSResponseDTO getApplicableTaxes(final WaterRateMaster rate, final long orgid, final String serviceShortCode);

    /**
     * @param requiredCHarges
     * @return
     */
    // List<ChargeDetailDTO> getApplicableCharges(List<WaterRateMaster> requiredCHarges);

    /**
     * @param charges
     * @return
     */
    double chargesToPay(List<ChargeDetailDTO> charges);

}
