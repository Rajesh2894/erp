package com.abm.mainet.asset.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInventoryInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPostingInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;

/**
 * Service class providing methods to retrieve various information about an asset.
 * 
 * @author Vardan.Savarde
 */
@WebService
public interface IAssetInformationService {
    /**
     * Returns summary of the asset
     * 
     * @param id asset id
     * @return AssetSummaryDTO containing the summary of asset
     */
    SummaryDTO getSummaryByAssetId(Long id);

    SummaryDTO getAssetInfo(AssetDetailsDTO dto);

    /**
     * Returns summary of the asset
     * 
     * @param barcode barcode of the asset
     * @return AssetSummaryDTO containing the summary of asset
     */
    SummaryDTO getSummaryByBarcode(String barcode);

    /**
     * Returns summary of the asset
     * 
     * @param id asset id
     * @return AssetSummaryDTO containing the summary of asset
     */
    // AssetSummaryDTO getSummary(Long id);
    /**
     * Returns details of the asset
     * 
     * @param id asset id
     * @return AssetDetailsDTO containing the details of asset
     */
    AssetDetailsDTO getDetailsByAssetId(Long id);

    AssetDetailsDTO getDetailsByOrgIdAndAssetCode(AssetDetailsDTO astDetRequest);

    AssetDetailsDTO getDetailsByIdAndUrlparamRev(Long assetClassIdRev, String urlParam);
    
    AssetDetailsDTO getITAssetDetailsByIdRev(String assetClassIdRev);

    List<AssetInsuranceDetailsDTO> getInsuranceInfoListByGroupIdRev(Long id);

    /**
     * Returns inventory related information of the asset
     * 
     * @param id asset id
     * @return AssetInventoryInformationDTO containing the summary of asset
     */
    AssetInventoryInformationDTO getInventoryInfo(Long id);

    /**
     * Returns posting information of the asset
     * 
     * @param id asset id
     * @return AssetPostingInformationDTO containing the posting information of asset
     */
    AssetPostingInformationDTO getPostingInfo(Long id);

    /**
     * Returns specification information of the asset
     * 
     * @param id asset id
     * @return AssetSpecificationDTO containing the specification information of asset
     */
    AssetSpecificationDTO getSpecification(Long id);

    /**
     * Returns classification information of the asset
     * 
     * @param id asset id
     * @return AssetClassificationDTO containing the classification information of asset
     */
    AssetClassificationDTO getClassification(Long id);

    /**
     * Returns classification information of the asset
     * 
     * @param id asset id
     * @return AssetClassificationDTO containing the classification information of asset
     */
    AssetClassificationDTO getClassificationRev(Long id);

    /**
     * Returns purchase information of the asset
     * 
     * @param id asset id
     * @return AssetPurchaseInformationDTO containing the purchase information of asset
     */
    AssetPurchaseInformationDTO getPurchaseInfo(Long id);

    /**
     * Returns RealEstate information of the asset
     * 
     * @param id asset id
     * @return AssetRealEstateInformationDTO containing the purchase information of asset
     */
    AssetRealEstateInformationDTO getRealEstateInfo(Long id);

    /**
     * Returns RealEstate information of the asset
     * 
     * @param id asset id
     * @return AssetRealEstateInformationDTO containing the purchase information of asset
     */
    AssetRealEstateInformationDTO getRealEstateInfoRev(Long id);

    /**
     * Returns service information of the asset
     * 
     * @param id asset id
     * @return AssetServiceInformationDTO containing the service information of asset
     */
    List<AssetServiceInformationDTO> getServiceInfo(Long id);

    List<AssetServiceInformationDTO> getServiceInfoRev(Long id);

    /**
     * Returns real estate information of the asset
     * 
     * @param id asset id
     * @return AssetRealEstateInformationDTO containing the real estate information of asset
     */
    /* AssetRealEstateInformationDTO getRealEstateInfo(Long id); */

    /**
     * Returns insurance information of the asset
     * 
     * @param id asset id
     * @return AssetInsuranceDetailsDTO containing the insurance information of asset
     */
    AssetInsuranceDetailsDTO getInsuranceInfo(Long id);

    List<AssetInsuranceDetailsDTO> getInsuranceInfoList(Long id);

    /**
     * Returns lease information of the asset
     * 
     * @param id asset id
     * @return AssetLeasingCompanyDTO containing the lease information of asset
     */
    AssetLeasingCompanyDTO getLeaseInfo(Long id);

    AssetLeasingCompanyDTO getLeaseInfoRev(Long id);

    /**
     * Returns documents related to the asset
     * 
     * @param id asset id
     * @return documents related to the asset
     */
    void getDocuments(Long id);

    /**
     * Returns depreciation information of the asset
     * 
     * @param id asset id
     * @return depreciation information of asset
     */
    AssetDepreciationChartDTO getDepreciationInfo(Long id);

    /**
     * Returns information of the asset
     * 
     * @param id asset id
     * @return AssetInformationDTO containing the information of asset
     */
    AssetInformationDTO getInfo(Long id);

    AssetInformationDTO getInfoRev(Long id);

    /**
     * Returns Linear information of the asset
     * 
     * @param id asset id
     * @return AssetLinearDTO containing the linear information of asset
     */
    AssetLinearDTO getLinear(Long id);

    AssetLinearDTO getLinearRev(Long id);

    AssetPurchaseInformationDTO getPurchaseInfoRev(Long idRev);

    /* public List<LookUp> getCostCenterbyOrgId(final Long orgId, final Integer langId); */

    AssetDepreciationChartDTO getDepreciationInfoRev(Long id);

    /**
     * Used to get list of asset by matching asset class in an org
     * 
     * @param assetClass
     * @param assetStatus
     * @param orgId
     */
    public List<AssetInformationDTO> getAssetByAssetClass(final Long orgId, final Long assetClass,
            final Long assetStatus, final String appovalStatus);

    AssetInsuranceDetailsDTO getInsuranceInfoRev(Long id);

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param orgId
     * @param assetId
     * @return DepreciationAssetDTO
     */
    public AssetValuationDetailsDTO findLatestAssetId(final Long orgId, final Long assetId);

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId for change type "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    public AssetValuationDetailsDTO findLatestAssetIdWithChangeType(final Long orgId, final Long assetId,
            final String changeType);

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId for change type other than passed change Type
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    public List<AssetValuationDetailsDTO> checkTransaction(final Long orgId, final Long assetId,
            final String changeType);
    
    List<SummaryDTO> getDetailsByEmpId(Long id,Long orgId, Long langId);

	AssetPurchaseInformationDTO assetpurhcaseView(Long assetId);


}
