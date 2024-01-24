package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.PrimaryPropertyService;

/**
 * @author Arun Shinde
 *
 */
@Component
@Scope("session")
public class PropertyStatusUpdateModel extends AbstractFormModel {

	private static final Logger LOGGER = Logger.getLogger(PropertyStatusUpdateModel.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@Autowired
	private ILocationMasService locationService;

	@Autowired
	private IFileUploadService fileUploadService;

	private String ownershipPrefix;
	private String statusFlag;
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();
	
	private ProperySearchDto searchDto = new ProperySearchDto();

	@Override
	public boolean saveForm() {
		setCustomViewName("PropertyStatusDetailsEdit");
		String status = this.getStatusFlag();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<DocumentDetailsVO> docs = fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>());
		if (CollectionUtils.isEmpty(docs)) {
			addValidationError(getAppSession().getMessage("property.uploadAtleastOneDoc"));
			return false;
		}
		if (StringUtils.equals(status, MainetConstants.FlagA)) {
			status = MainetConstants.FlagI;
		} else {
			status = MainetConstants.FlagA;
		}
		String propNo = this.getProvisionalAssesmentMstDto().getAssNo();
		try {
			LOGGER.info("save uploaded file ");
			doFileUpload(docs, propNo, orgId);
		} catch (Exception e1) {
			addValidationError(getAppSession().getMessage("propertyTax.documentUploadException"));
			return false;
		}
		try {	
			LOGGER.info("Update status into Assessment table ");
			assesmentMastService.updateAssessmentMstStatus(propNo, status, orgId);

			LOGGER.info("Update status into Property master table ");
			primaryPropertyService.updatePropertyMstStatus(propNo, status, orgId);

		} catch (Exception e) {
			addValidationError(getAppSession().getMessage("propertyTax.statusUpdateExcpetion"));
			LOGGER.error("Exception occured while updating status " + e.getMessage());		
			return false;
		}	
		setSuccessMessage(getAppSession().getMessage("propertyTax.recordSaved"));
		return true;
	}

	public void doFileUpload(List<DocumentDetailsVO> docs, String propNo, Long orgId) {
		FileUploadDTO uploadDTO = new FileUploadDTO();
		uploadDTO.setOrgId(orgId);
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
		uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		uploadDTO.setIdfId(MainetConstants.Property.PROP_DEPT_SHORT_CODE + MainetConstants.WINDOWS_SLASH + propNo);
		fileUploadService.doMasterFileUpload(docs, uploadDTO);
	}

	public void setDropDownValues(Organisation org) {
		this.getProvisionalAssesmentMstDto().setProAssOwnerTypeName(CommonMasterUtility
				.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(), org).getDescLangFirst());

		if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setProAssdRoadfactorDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
							.getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getAssActive() != null) {
			if (StringUtils.equals(provisionalAssesmentMstDto.getAssActive(), MainetConstants.FlagA)) {
				this.getProvisionalAssesmentMstDto().setAssStatus(MainetConstants.Common_Constant.ACTIVE);
			} else {
				this.getProvisionalAssesmentMstDto().setAssStatus(MainetConstants.Common_Constant.INACTIVE);
			}
		}

		String ownerTypeCode = CommonMasterUtility
				.getNonHierarchicalLookUpObject(getProvisionalAssesmentMstDto().getAssOwnerType(), org).getLookUpCode();
		if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
			for (ProvisionalAssesmentOwnerDtlDto dto : getProvisionalAssesmentMstDto()
					.getProvisionalAssesmentOwnerDtlDtoList()) {
				if (dto.getGenderId() != null && dto.getGenderId() > 0) {
					dto.setProAssGenderId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org)
							.getDescLangFirst());
				}
				if (dto.getRelationId() != null && dto.getRelationId() > 0) {
					dto.setProAssRelationId(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org)
							.getDescLangFirst());
				}
			}
		} else {
			ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
			ownerDto.setGenderId(null);
			ownerDto.setRelationId(null);
			ownerDto.setAssoAddharno(null);
		}

		if (provisionalAssesmentMstDto.getLocId() != null) {
			this.getProvisionalAssesmentMstDto().setLocationName(
					locationService.getLocationNameById(provisionalAssesmentMstDto.getLocId(), org.getOrgid()));
		}

		if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setProAssdRoadfactorDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
							.getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssLandType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setAssLandTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssLandType(), org)
							.getDescLangFirst());

		}

		this.getProvisionalAssesmentMstDto().setAssWardDesc1(CommonMasterUtility
				.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());

		if (provisionalAssesmentMstDto.getAssWard2() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc2(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard3() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc3(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard4() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc4(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard5() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc5(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getSurveyType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setSurveyTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getSurveyType(), org)
							.getDescLangFirst());
		}

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}

	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}
	
	

}
