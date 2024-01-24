package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.FPOProfileMasterDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.FPOProfileMasterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FPOProfileModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5616279491514099182L;

	@Autowired FPOProfileMasterService fpoProfileMasterService;
	
	 @Autowired
	 IFileUploadService fileUpload;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private TbDepartmentService departmentService;

	FPOProfileMasterDto dto = new FPOProfileMasterDto();

	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<TbFinancialyear> faYears = new ArrayList<>();
	private List<FPOMasterDto> masterDtoList = new ArrayList<>();
	private List<FPOMasterDto> fpoMasterDtoList = new ArrayList<>();
	private List<IAMasterDto> iaMasterDtoList = new ArrayList<>();
	private List<CBBOMasterDto> iaNameList = new ArrayList<>();
	 private List<Long> removedIds;
	 private FPOMasterDto fpoMasterDto ;
	private String viewMode;
	private Long fpoId;
	private String fpoRegNo;
	private Long fpmId;
	private List<Long> fileCountUpload;
	 private Long length;
	 private String error;
	
	

	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<IAMasterDto> getIaMasterDtoList() {
		return iaMasterDtoList;
	}

	public void setIaMasterDtoList(List<IAMasterDto> iaMasterDtoList) {
		this.iaMasterDtoList = iaMasterDtoList;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public Long getFpmId() {
		return fpmId;
	}

	public void setFpmId(Long fpmId) {
		this.fpmId = fpmId;
	}

	public Long getFpoId() {
		return fpoId;
	}

	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoRegNo() {
		return fpoRegNo;
	}

	public void setFpoRegNo(String fpoRegNo) {
		this.fpoRegNo = fpoRegNo;
	}

	public List<FPOMasterDto> getFpoMasterDtoList() {
		return fpoMasterDtoList;
	}

	public void setFpoMasterDtoList(List<FPOMasterDto> fpoMasterDtoList) {
		this.fpoMasterDtoList = fpoMasterDtoList;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<FPOMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	public void setMasterDtoList(List<FPOMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public FPOProfileMasterService getFpoProfileMasterService() {
		return fpoProfileMasterService;
	}

	public void setFpoProfileMasterService(FPOProfileMasterService fpoProfileMasterService) {
		this.fpoProfileMasterService = fpoProfileMasterService;
	}

	public IOrganisationService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganisationService orgService) {
		this.orgService = orgService;
	}

	public TbDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(TbDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public FPOProfileMasterDto getDto() {
		return dto;
	}

	public void setDto(FPOProfileMasterDto dto) {
		this.dto = dto;
	}
	
	

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	@Override
	public boolean saveForm() throws FrameworkException {
		
		FPOProfileMasterDto mastDto = getDto();

	
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
			Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
			String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
			Date newDate = new Date();
			//FPOProfileMasterDto mastDto = getDto();

			if (mastDto.getCreatedBy() == null) {
				mastDto.setCreatedBy(createdBy);
				mastDto.setCreatedDate(newDate);
				mastDto.setOrgId(org.getOrgid());
				mastDto.setLgIpMac(lgIp);
				//mastDto.setDeptId(deptId);
				//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			
				mastDto.getFpoProfileTrainingDetailDto().forEach(chsiDto -> {
					chsiDto.setCreatedBy(createdBy);
					chsiDto.setCreatedDate(newDate);
					chsiDto.setOrgId(org.getOrgid());
					chsiDto.setLgIpMac(lgIp);
				});
				

				
			} else {
				mastDto.setUpdatedBy(createdBy);
				mastDto.setUpdatedDate(newDate);
				mastDto.setOrgId(org.getOrgid());
				//mastDto.setDeptId(deptId);
				//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
				mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


			
				mastDto.getFpoProfileTrainingDetailDto().forEach(chciDto -> {
					if (chciDto.getCreatedBy() == null) {
						chciDto.setCreatedBy(createdBy);
						chciDto.setCreatedDate(newDate);
						chciDto.setOrgId(org.getOrgid());
						chciDto.setLgIpMac(lgIp);
					} else {
						chciDto.setUpdatedBy(createdBy);
						chciDto.setUpdatedDate(newDate);
						chciDto.setOrgId(org.getOrgid());
						chciDto.setLgIpMac(lgIp);
					}
				});
				

			}
			//	mastDto.setIaName(iaMasterName);
			// Cbbo name should popup automatically once registration of CBBO is done
			// mastDto.setCbboName(cbboMasterName);
			mastDto = fpoProfileMasterService.saveAndUpdateTraningInfo(mastDto);
			setDto(mastDto);
		if (mastDto.getFpmId()!=0)
			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.pm.save.msg"));
		else
			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.pm.save.msg"));
		return true;

	}

	public Long updateFPOProfileFinInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getFinancialInformationDto().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getFinancialInformationDto().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		
		
		
		
		
		mastDto = fpoProfileMasterService.saveAndUpdateApplication(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}
	
	


	public Long updateFPOProfileLicenseInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getLicenseInformationDetEntities().forEach(chsiDto -> {
				chsiDto.setAttachmentsLi(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
				chsiDto.setLicenseName("A");
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getLicenseInformationDetEntities().forEach(chciDto -> {
				 chciDto.setAttachmentsLi(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				chciDto.setLicenseName("A");
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
				
				 
		         
				
			});
			

		}
		
		
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		
		
	
		mastDto = fpoProfileMasterService.saveAndUpdateLicenseInfo(mastDto, removedIds);
		
		setDto(mastDto);
		removedIds = new ArrayList<>();
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileCreditInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getCreditInformationDetEntities().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getCreditInformationDetEntities().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateCreditInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileEquityInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getEquityInformationDetDto().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getEquityInformationDetDto().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateEquityInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileFarmerSummaryInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getFarmerSummaryDto().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getFarmerSummaryDto().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateFarmerSummary(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileCreditGrandInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getCreditGrantDetailDto().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getCreditGrantDetailDto().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateCreditGrand(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileManagementInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getManagementCostDetailDto().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getManagementCostDetailDto().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateManagementInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileStorageInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getStrorageInfomartionDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getStrorageInfomartionDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateStorageInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileCustomInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getEquipmentInfoDtos().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			
			mastDto.getCustomHiringServiceInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getEquipmentInfoDtos().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			
			mastDto.getCustomHiringServiceInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateCustomInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfilePNSInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getProductionInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			
			mastDto.getSalesInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getProductionInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			
			mastDto.getSalesInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdatePNSInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileSubsidiesInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getSubsidiesInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getSubsidiesInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateSubsidiesInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfilePreharveshInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getPreHarveshInfraInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getPreHarveshInfraInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdatePreharveshInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfilePostHarvestInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getPostHarvestInfraInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getPostHarvestInfraInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdatePostharvestInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileTransportInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getTranspostVehicleInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getTranspostVehicleInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateTransportVehicleInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileMLInfo(FPOProfileMasterDto mastDto, FPOMasterDto fpoMaster) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//FPOProfileMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getMarketLinkageInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getMarketLinkageInfoDTOs().forEach(chciDto -> {
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
			});
			

		}
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateMLInfo(mastDto, fpoMasterDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}

	public List<CBBOMasterDto> getIaNameList() {
		return iaNameList;
	}

	public void setIaNameList(List<CBBOMasterDto> iaNameList) {
		this.iaNameList = iaNameList;
	}

	public Long updateFPOProfileBPInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getBusinessPlanInfoDTOs().forEach(chsiDto -> {
				chsiDto.setAttachmentsBP(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
				chsiDto.setDocumentName("A");
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getBusinessPlanInfoDTOs().forEach(chciDto -> {
				 chciDto.setAttachmentsBP(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				 chciDto.setDocumentName("A");
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
				
			});
			

		}
		
		
		mastDto = fpoProfileMasterService.saveAndUpdateBPInfo(mastDto, removedIds);
		setDto(mastDto);
		removedIds = new ArrayList<>();
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileABSInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		
		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getAuditedBalanceSheetInfoDetailEntities().forEach(chsiDto -> {
				chsiDto.setAttachmentsABS(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
				chsiDto.setDocumentName("A");
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getAuditedBalanceSheetInfoDetailEntities().forEach(chciDto -> {
				 chciDto.setAttachmentsABS(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				chciDto.setDocumentName("A");
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
				
				 
		         
				
			});
			

		}
		
		
		//	mastDto.setIaName(iaMasterName);
		// Cbbo name should popup automatically once registration of CBBO is done
		// mastDto.setCbboName(cbboMasterName);
		mastDto = fpoProfileMasterService.saveAndUpdateABSInfo(mastDto, removedIds);
		setDto(mastDto);
		removedIds = new ArrayList<>();
	
		return mastDto.getFpmId();

	}

	public Long updateFPOProfileDPRInfo(FPOProfileMasterDto mastDto) {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
	//	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		
			mastDto.getDprInfoDTOs().forEach(chsiDto -> {
				chsiDto.setCreatedBy(createdBy);
				chsiDto.setCreatedDate(newDate);
				chsiDto.setOrgId(org.getOrgid());
				chsiDto.setLgIpMac(lgIp);
			});
			

			
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			//mastDto.setDeptId(deptId);
			//mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());


		
			mastDto.getDprInfoDTOs().forEach(chciDto -> {
				
				if (chciDto.getCreatedBy() == null) {
					chciDto.setCreatedBy(createdBy);
					chciDto.setCreatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				} else {
					chciDto.setUpdatedBy(createdBy);
					chciDto.setUpdatedDate(newDate);
					chciDto.setOrgId(org.getOrgid());
					chciDto.setLgIpMac(lgIp);
				}
				
				 
		         
				
			});
			

		}
		
	
		mastDto = fpoProfileMasterService.saveAndUpdateDPRInfo(mastDto);
		setDto(mastDto);
	
		return mastDto.getFpmId();

	}
	
	
	

	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}

	public FPOMasterDto getFpoMasterDto() {
		return fpoMasterDto;
	}

	public void setFpoMasterDto(FPOMasterDto fpoMasterDto) {
		this.fpoMasterDto = fpoMasterDto;
	}

	


	 

}
