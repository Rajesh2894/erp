package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * Composite class containing all information about an asset from the asset register
 * 
 * @author Vardan.Savarde
 *
 */
@XmlRootElement(name = "AssetDetails")
public class AssetDetailsDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5964757029200461751L;
    @Valid
    @NotNull
    private AssetInformationDTO assetInformationDTO;
    private AssetClassificationDTO assetClassificationDTO;
    private AssetInsuranceDetailsDTO astInsuDTO;
    private AssetLeasingCompanyDTO astLeaseDTO;
    /* Task #5318 */
    @Valid
    @NotNull(message = "Acquisition Details can not be null")
    private AssetPurchaseInformationDTO assetPurchaseInformationDTO;
    private AssetServiceInformationDTO astSerInfoDTO;
    private AssetDepreciationChartDTO astDepreChartDTO;
    private AssetLinearDTO astLinearDTO;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AssetInsuranceDetailsDTO> astInsuDTOList = new ArrayList<AssetInsuranceDetailsDTO>();
    @NotNull
    private Long orgId;
    private Long langId;
    @Valid
    private AuditDetailsDTO auditDTO = new AuditDetailsDTO();
    private List<AssetServiceInformationDTO> astSerList;
   // For ITAsset bulk excel export
    private Set<String> excelEportSerialNo = new HashSet<String>();
    private List<Long> assetIds = new ArrayList<Long>();

    private Boolean isBulkExport = false;
    public List<Long> getAssetIds() {
		return assetIds;
	}

	public void setAssetIds(List<Long> assetIds) {
		this.assetIds = assetIds;
	}

	public Set<String> getExcelEportSerialNo() {
		return excelEportSerialNo;
	}

	public void setExcelEportSerialNo(Set<String> excelEportSerialNo) {
		this.excelEportSerialNo = excelEportSerialNo;
	}

	

	private AssetRealEstateInformationDTO assetRealEstateInfoDTO;

    private Boolean isServiceAplicable = false;

    public AssetRealEstateInformationDTO getAssetRealEstateInfoDTO() {
        return assetRealEstateInfoDTO;
    }

    public void setAssetRealEstateInfoDTO(AssetRealEstateInformationDTO assetRealEstateInfoDTO) {
        this.assetRealEstateInfoDTO = assetRealEstateInfoDTO;
    }

    /**
     * @return the assetInformationDTO
     */
    public AssetInformationDTO getAssetInformationDTO() {
        return assetInformationDTO;
    }

    /**
     * @param assetInformationDTO the assetInformationDTO to set
     */
    public void setAssetInformationDTO(AssetInformationDTO assetInformationDTO) {
        this.assetInformationDTO = assetInformationDTO;
    }

    /**
     * @return the assetClassificationDTO
     */
    public AssetClassificationDTO getAssetClassificationDTO() {
        return assetClassificationDTO;
    }

    /**
     * @param assetClassificationDTO the assetClassificationDTO to set
     */
    public void setAssetClassificationDTO(AssetClassificationDTO assetClassificationDTO) {
        this.assetClassificationDTO = assetClassificationDTO;
    }

    /**
     * @return the astInsuDTO
     */
    public AssetInsuranceDetailsDTO getAstInsuDTO() {
        return astInsuDTO;
    }

    /**
     * @param astInsuDTO the astInsuDTO to set
     */
    public void setAstInsuDTO(AssetInsuranceDetailsDTO astInsuDTO) {
        this.astInsuDTO = astInsuDTO;
    }

    /**
     * @return the astLeaseDTO
     */
    public AssetLeasingCompanyDTO getAstLeaseDTO() {
        return astLeaseDTO;
    }

    /**
     * @param astLeaseDTO the astLeaseDTO to set
     */
    public void setAstLeaseDTO(AssetLeasingCompanyDTO astLeaseDTO) {
        this.astLeaseDTO = astLeaseDTO;
    }

    /**
     * @return the assetPurchaseInformationDTO
     */
    public AssetPurchaseInformationDTO getAssetPurchaseInformationDTO() {
        return assetPurchaseInformationDTO;
    }

    /**
     * @param assetPurchaseInformationDTO the assetPurchaseInformationDTO to set
     */
    public void setAssetPurchaseInformationDTO(AssetPurchaseInformationDTO assetPurchaseInformationDTO) {
        this.assetPurchaseInformationDTO = assetPurchaseInformationDTO;
    }

    /**
     * @return the astSerInfoDTO
     */
    public AssetServiceInformationDTO getAstSerInfoDTO() {
        return astSerInfoDTO;
    }

    /**
     * @param astSerInfoDTO the astSerInfoDTO to set
     */
    public void setAstSerInfoDTO(AssetServiceInformationDTO astSerInfoDTO) {
        this.astSerInfoDTO = astSerInfoDTO;
    }

    /**
     * @return the astDepreChartDTO
     */
    public AssetDepreciationChartDTO getAstDepreChartDTO() {
        return astDepreChartDTO;
    }

    /**
     * @param astDepreChartDTO the astDepreChartDTO to set
     */
    public void setAstDepreChartDTO(AssetDepreciationChartDTO astDepreChartDTO) {
        this.astDepreChartDTO = astDepreChartDTO;
    }

    /**
     * @return the astLinearDTO
     */
    public AssetLinearDTO getAstLinearDTO() {
        return astLinearDTO;
    }

    /**
     * @param astLinearDTO the astLinearDTO to set
     */
    public void setAstLinearDTO(AssetLinearDTO astLinearDTO) {
        this.astLinearDTO = astLinearDTO;
    }

    /**
     * @return the attachments
     */
    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    /**
     * @return the attachDocsList
     */
    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    /**
     * @param attachDocsList the attachDocsList to set
     */
    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    /**
     * @return the auditDTO
     */
    public AuditDetailsDTO getAuditDTO() {
        return auditDTO;
    }

    /**
     * @param auditDTO the auditDTO to set
     */
    public void setAuditDTO(AuditDetailsDTO auditDTO) {
        this.auditDTO = auditDTO;
    }

    public List<AssetInsuranceDetailsDTO> getAstInsuDTOList() {
        return astInsuDTOList;
    }

    public void setAstInsuDTOList(List<AssetInsuranceDetailsDTO> astInsuDTOList) {
        this.astInsuDTOList = astInsuDTOList;
    }

    /**
     * @return the astSerList
     */
    public List<AssetServiceInformationDTO> getAstSerList() {
        return astSerList;
    }

    /**
     * @param astSerList the astSerList to set
     */
    public void setAstSerList(List<AssetServiceInformationDTO> astSerList) {
        this.astSerList = astSerList;
    }

    /**
     * @return the isServiceAplicable
     */
    public Boolean getIsServiceAplicable() {
        return isServiceAplicable;
    }

    /**
     * @param isServiceAplicable the isServiceAplicable to set
     */
    public void setIsServiceAplicable(Boolean isServiceAplicable) {
        this.isServiceAplicable = isServiceAplicable;
    }

    public Boolean getIsBulkExport() {
		return isBulkExport;
	}

	public void setIsBulkExport(Boolean isBulkExport) {
		this.isBulkExport = isBulkExport;
	}

	@Override
    public String toString() {
        return "AssetDetailsDTO [assetInformationDTO=" + assetInformationDTO + ", assetClassificationDTO="
                + assetClassificationDTO + ", astInsuDTO=" + astInsuDTO + ", astLeaseDTO=" + astLeaseDTO
                + ", assetPurchaseInformationDTO=" + assetPurchaseInformationDTO + ", astSerInfoDTO=" + astSerInfoDTO
                + ", astDepreChartDTO=" + astDepreChartDTO + ", astLinearDTO=" + astLinearDTO + ", attachments=" + attachments
                + ", attachDocsList=" + attachDocsList + ", astInsuDTOList=" + astInsuDTOList + ", orgId=" + orgId + ", auditDTO="
                + auditDTO + ", astSerList=" + astSerList + ", assetRealEstateInfoDTO=" + assetRealEstateInfoDTO
                + ", isServiceAplicable=" + isServiceAplicable + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

}
