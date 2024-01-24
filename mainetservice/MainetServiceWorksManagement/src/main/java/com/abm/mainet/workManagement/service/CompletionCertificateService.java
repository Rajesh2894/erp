package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.workManagement.dto.ContractCompletionDto;
import com.abm.mainet.workManagement.dto.SearchDTO;
import com.abm.mainet.workManagement.dto.SummaryDto;

/**
 * @author Saiprasad.Vengurekar
 *
 */
public interface CompletionCertificateService {

    /**
     * used to get Asset Details
     * @param searchDTO
     * @return
     */
    List<SummaryDto> getAssetDetails(SearchDTO searchDTO);

    /**
     * this method is used for update Work Compltion No And Date
     * @param contractCompletionDto
     * @param orgId
     */
    void updateWorkCompltionNoAndDate(ContractCompletionDto contractCompletionDto, Long orgId);

    /**
     * this method is used for push Asset Details
     * @param astDet
     * @return
     */
    Long pushAssetDetails(AssetDetailsDTO astDet);

    // void doWmsVoucherPosting(ContractCompletionDto contractCompletionDto);
}
