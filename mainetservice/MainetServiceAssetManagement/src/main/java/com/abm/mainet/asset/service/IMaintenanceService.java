package com.abm.mainet.asset.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.asset.ui.dto.AssetAccountPostDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.CalculationDTO;
import com.abm.mainet.asset.ui.dto.DocumentDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;

/**
 * Provides methods to be implemented for maintenance of an asset
 * 
 * @author Vardan.Savarde
 *
 */
@WebService
public interface IMaintenanceService {
    /**
     * Registers a new asset
     * 
     * @param detailsDTO
     * @param orgId
     * @param auditDTO
     */
    void registerDetailDto(final AssetDetailsDTO detailsDTO);

    void addValuationEntry(final AssetDetailsDTO detailsDTO, Date date, final AssetValuationDetailsDTO valuationDTO,
            final Long astId);

    /**
     * Updates asset related information
     * 
     * @param detailsDTO
     */
    void updateDetailDto(final AssetDetailsDTO detailsDTO, final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset information to be updated
     * @param auditDTO audit related details to be updated
     */
    void updateInformation(final Long assetId, final AssetInformationDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * saves just the data passed in AssetInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset information to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveInformationRev(final Long assetId, final AssetInformationDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetClassificationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset classification details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateClassification(final Long assetId, final AssetClassificationDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * saves the data passed in AssetClassificationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset classification details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveClassificationRev(final Long assetId, final AssetClassificationDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetPurchaseInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset purchase details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updatePurchaseInformation(final Long assetId, final AssetPurchaseInformationDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetPurchaseInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset purchase details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long savePurchaseInformationRev(final Long assetId, final AssetPurchaseInformationDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetRealEstateDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset real estate details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateRealEstateInformation(final Long assetId, final AssetRealEstateInformationDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetRealEstateDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset real estate rev details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveupdateRealEstateInformationRev(final Long assetId, final AssetRealEstateInformationDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetLeasingCompanyDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset lease details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateLeaseInformation(Long assetId, AssetLeasingCompanyDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * save just the data passed in AssetLeasingCompanyDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset lease details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveLeaseInformationRev(Long assetId, AssetLeasingCompanyDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetServiceInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset service information to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateServiceInformation(final Long assetId, final List<AssetServiceInformationDTO> dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetServiceInformationDTO in case of send back
     * 
     * @param assetId asset to be updated
     * @param dto asset service information to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateServiceInformation(final Long assetId, final AssetServiceInformationDTO dto,
            final AuditDetailsDTO auditDTO);

    Long saveServiceInformationRev(Long assetId, List<AssetServiceInformationDTO> dto, final AuditDetailsDTO auditDTO,
            Long orgId);

    Long saveRealStateInfoRev(Long assetId, AssetRealEstateInformationDTO dto, final AuditDetailsDTO auditDTO,
            Long orgId);

    /**
     * Updates just the data passed in AssetServiceInformationDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset service information to be updated
     * @param auditDTO audit related details to be updated
     */
    /*
     * void saveServiceInformationRev(final Long assetId, final AssetServiceInformationDTO dto, final AuditDetailsDTO auditDTO);
     */
    /**
     * Updates just the data passed in AssetInsuranceDetailsDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset insurance details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateInsuranceDetails(final Long assetId, final AssetInsuranceDetailsDTO dto, final AuditDetailsDTO auditDTO);

    // Long updateInsuranceDetailsRev(final Long groupId, AssetInsuranceDetailsDTO insDTO, final AuditDetailsDTO auditDTO);

    Long updateInsuranceDetailsListRev(List<AssetInsuranceDetailsDTO> insDTOList, final AuditDetailsDTO auditDTO,
            final Long astId);

    Long saveInsuranceDetails(final Long assetId, final AssetInsuranceDetailsDTO dto, final AuditDetailsDTO auditDTO);

    List<AssetInsuranceDetailsDTO> getAllInsuranceDetailsList(final Long assetId, final AssetInsuranceDetailsDTO dto);

    Long saveInsuranceDetailsRev(final Long assetId, final List<AssetInsuranceDetailsDTO> dtoList,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetDepreciationChartDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset depreciation details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateDepreciationChart(final Long assetId, final AssetDepreciationChartDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * save just the data passed in AssetDepreciationChartDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset depreciation details to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveDepreciationChartRev(final Long assetId, final AssetDepreciationChartDTO dto,
            final AuditDetailsDTO auditDTO);

    /**
     * Updates just the data passed in AssetLinearDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset linear information to be updated
     * @param auditDTO audit related details to be updated
     */
    Long updateLinearInformation(final Long assetId, final AssetLinearDTO dto, final AuditDetailsDTO auditDTO);

    /**
     * save just the data passed in AssetLinearDTO
     * 
     * @param assetId asset to be updated
     * @param dto asset linear information to be updated
     * @param auditDTO audit related details to be updated
     */
    Long saveLinearInformationRev(final Long assetId, final AssetLinearDTO dto, final AuditDetailsDTO auditDTO);

    // void retire();

    void updateDocumentDetails(final Long assetId, final Long orgId, final AuditDetailsDTO auditDTO,
            final DocumentDto dto, String moduelDeptCode);

    /**
     * Calculate depreciation for given asset for a given month
     * 
     * @param dto
     * @param months
     * @return
     */
    AssetValuationDetailsDTO calculateDepreciation(final CalculationDTO dto, final Integer months);

    void postDepreciationVoucher(AssetAccountPostDTO postDTO, String activeFlag, Organisation org,
            String narration, List<VoucherPostDetailDTO> voucherDTO, String moduleDeptCode);

    Boolean checkAccountActiveOrNot();

    Long assetRegisterUtility(AssetDetailsDTO assetDetailsDTO);

    void externalVoucherPostingInAccount(Long orgId, VoucherPostExternalDTO vouPostDto);

    void saveAssetEntryDataInDraftMode(Long astId, String urlShortCode, AssetDetailsDTO detailsDTO);

}
