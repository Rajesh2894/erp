/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetSpecificationDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.ContractCompletionDto;
import com.abm.mainet.workManagement.dto.SearchDTO;
import com.abm.mainet.workManagement.dto.SummaryDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationAssetInfoDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.CompletionCertificateService;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;

/**
 * @author Saiprasad.Vengurekar
 *
 */
@Component
@Scope("session")
public class CompletionCertificateModel extends AbstractFormModel {

	@Autowired
	WorkDefinitionService workDefinitionService;

	@Autowired
	private CompletionCertificateService completionCertificateService;

	private static final long serialVersionUID = -911499772472409236L;

	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();

	private ContractCompletionDto contractCompletionDto = new ContractCompletionDto();

	private List<ContractCompletionDto> completionDtos = new ArrayList<>();

	private SearchDTO searchDto = new SearchDTO();

	private SummaryDto summaryDto = new SummaryDto();

	private AssetDetailsDTO astDet = new AssetDetailsDTO();
	
	//D119882  new fields added to set the data on the completion certificate
	private String workName;
	
	private String vendorName;
	
	private String workorderNo;
	
	private String workDate;
	
	private String ulbName;
	
	private String orgName;
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUlbName() {
		return ulbName;
	}

	public void setUlbName(String ulbName) {
		this.ulbName = ulbName;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public List<ContractCompletionDto> getCompletionDtos() {
		return completionDtos;
	}

	public void setCompletionDtos(List<ContractCompletionDto> completionDtos) {
		this.completionDtos = completionDtos;
	}

	public SummaryDto getSummaryDto() {
		return summaryDto;
	}

	public void setSummaryDto(SummaryDto summaryDto) {
		this.summaryDto = summaryDto;
	}

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public ContractCompletionDto getContractCompletionDto() {
		return contractCompletionDto;
	}

	public void setContractCompletionDto(ContractCompletionDto contractCompletionDto) {
		this.contractCompletionDto = contractCompletionDto;
	}

	public SearchDTO getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(SearchDTO searchDto) {
		this.searchDto = searchDto;
	}

	public AssetDetailsDTO getAstDet() {
		return astDet;
	}

	public void setAstDet(AssetDetailsDTO astDet) {
		this.astDet = astDet;
	}

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee emp = UserSession.getCurrent().getEmployee();
		AssetDetailsDTO assetDet = getAstDet();
		AssetInformationDTO infoDto = assetDet.getAssetInformationDTO();
		infoDto.setAssetStatus((Long) CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("A", "AST", orgId));
		infoDto.setAcquisitionMethod((Long) CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("AM", "AQM", orgId));
		infoDto.setOrgId(orgId);
		infoDto.setAssetClass2(getContractCompletionDto().getWorkCategory());
		infoDto.setAssetClass1((Long) CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("IMO", "ASC", orgId));
		infoDto.setCreatedBy(emp.getEmpId());
		infoDto.setDeptCode(MainetConstants.AssetManagement.ASSETCODE);
		AssetPurchaseInformationDTO assetPurchase = assetDet.getAssetPurchaseInformationDTO();
		AssetClassificationDTO classDto = new AssetClassificationDTO();
		assetPurchase.setCostOfAcquisition(
				ApplicationContextProvider.getApplicationContext().getBean(MeasurementBookService.class)
						.getTotalMbAmountByWorkId(getContractCompletionDto().getWorkId(), orgId));
		classDto.setDepartment(getContractCompletionDto().getProjDeptId());
		assetPurchase.setFromWhomAcquired(getContractCompletionDto().getVendorId());
		assetDet.setAssetPurchaseInformationDTO(assetPurchase);
		assetDet.setAssetClassificationDTO(classDto);
		assetDet.setOrgId(orgId);
		assetDet.getAuditDTO().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		assetDet.getAuditDTO().setEmpIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		Long astId = null;
		WorkDefinitionDto definitionDto = workDefinitionService
				.findAllWorkDefinitionById(contractCompletionDto.getWorkId());
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(definitionDto.getWorkType(),
				UserSession.getCurrent().getOrganisation());
		
		if (lookUp.getLookUpCode().equals("DP")) {
			astId = completionCertificateService.pushAssetDetails(assetDet);
		}
		WorkDefinationAssetInfoDto assetDto = new WorkDefinationAssetInfoDto();
		assetDto.setWorkId(getContractCompletionDto().getWorkId());
		assetDto.setAssetStatus(MainetConstants.FlagA);
		AssetSpecificationDTO specDto = infoDto.getAssetSpecificationDTO();
		if (specDto.getLength() != null)
			assetDto.setAssetLength(specDto.getLength());
		if (specDto.getBreadth() != null)
			assetDto.setAssetBreadth(specDto.getBreadth());
		if (specDto.getHeight() != null)
			assetDto.setAssetHeight(specDto.getHeight());
		if (specDto.getNoOfFloor() != null)
			assetDto.setAssetNoOfFloors(specDto.getNoOfFloor());
		if (specDto.getWeight() != null)
			assetDto.setAssetWidth(specDto.getWeight());
		if (specDto.getCarpet() != null)
			assetDto.setAssetCaArea(specDto.getCarpet());
		if (specDto.getArea() != null)
			assetDto.setAssetPlotArea(specDto.getArea());
		if (infoDto.getPurpose() != null)
			assetDto.setAssetPurpose(infoDto.getPurpose());
		assetDto.setAssetAquiDate(assetPurchase.getDateOfAcquisition());
		getContractCompletionDto().setAcquisitionDate(assetPurchase.getDateOfAcquisition());
		assetDto.setAssetAquiCost(assetPurchase.getCostOfAcquisition());
		getContractCompletionDto().setTotAmount(assetPurchase.getCostOfAcquisition());
		if (astId != null)
			assetDto.setAssetId(astId);
		assetDto.setOrgId(orgId);
		assetDto.setCreatedBy(emp.getEmpId());
		assetDto.setCreatedDate(new Date());
		assetDto.setLgIpMac(emp.getEmppiservername());
		assetDto.setAssetActiveFlag(MainetConstants.FlagY);
		getContractCompletionDto().setEmp(emp);
		getContractCompletionDto().setOrgId(orgId);
		getContractCompletionDto().setCompletionDate(Utility.dateToString(infoDto.getCreationDate()));
		getContractCompletionDto().setAssetInfoDto(assetDto);
		getContractCompletionDto().setWorkCategory(infoDto.getAssetClass1());
		completionCertificateService.updateWorkCompltionNoAndDate(getContractCompletionDto(), orgId);
		// completionCertificateService.doWmsVoucherPosting(getContractCompletionDto());
		setSuccessMessage(ApplicationSession.getInstance().getMessage("wms.CompletionCertificateSaved"));
		return true;
	}

}
