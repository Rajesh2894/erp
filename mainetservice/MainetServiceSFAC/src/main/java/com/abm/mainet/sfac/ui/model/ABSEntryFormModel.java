package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.AuditBalanceSheetMasterDto;
import com.abm.mainet.sfac.service.AuditBalanceSheetEntryService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ABSEntryFormModel extends AbstractFormModel{

	/**
	 * 
	 */
	@Autowired
	IFileUploadService fileUpload;
	
	@Autowired AuditBalanceSheetEntryService auditBalanceSheetEntryService;
	
	private static final long serialVersionUID = 7510406841784089988L;
	
	AuditBalanceSheetMasterDto dto = new AuditBalanceSheetMasterDto();
	
	List<AuditBalanceSheetMasterDto> auditBalanceSheetMasterDtos = new ArrayList<AuditBalanceSheetMasterDto>();
	
	private String viewMode;

	public AuditBalanceSheetMasterDto getDto() {
		return dto;
	}

	public void setDto(AuditBalanceSheetMasterDto dto) {
		this.dto = dto;
	}

	public List<AuditBalanceSheetMasterDto> getAuditBalanceSheetMasterDtos() {
		return auditBalanceSheetMasterDtos;
	}

	public void setAuditBalanceSheetMasterDtos(List<AuditBalanceSheetMasterDto> auditBalanceSheetMasterDtos) {
		this.auditBalanceSheetMasterDtos = auditBalanceSheetMasterDtos;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	
	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date newDate = new Date();
		Employee emp = UserSession.getCurrent().getEmployee();
		AuditBalanceSheetMasterDto dto = getDto();

		
		if (dto.getCreatedBy() == null) {
			dto.setCreatedBy(createdBy);
			dto.setCreatedDate(newDate);
			dto.setOrgId(orgId);
			dto.setLgIpMac(lgIp);
			dto.setAbsStatus(MainetConstants.FlagP);
			dto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			dto.getAuditBalanceSheetKeyParameterDtos().forEach(key->{
				if (key.getCreatedBy() == null) {
					key.setCreatedBy(createdBy);
					key.setCreatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMac(lgIp);
				}
					key.getAuditBalanceSheetSubParameterDtos().forEach(sub->{
						if (sub.getCreatedBy() == null) {
							sub.setCreatedBy(createdBy);
							sub.setCreatedDate(newDate);
							sub.setOrgId(orgId);
							sub.setLgIpMac(lgIp);
						}
						sub.getAuditBalanceSheetSubParameterDetailDtos().forEach(subDet->{
							if (subDet.getCreatedBy() == null) {
								subDet.setCreatedBy(createdBy);
								subDet.setCreatedDate(newDate);
								subDet.setOrgId(orgId);
								subDet.setLgIpMac(lgIp);
							}
						});
						
					});
				
			});
		} else {
			dto.setUpdatedBy(createdBy);
			dto.setUpdatedDate(newDate);
			dto.setOrgId(orgId);
			dto.setLgIpMacUpd(lgIp);
			dto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			dto.getAuditBalanceSheetKeyParameterDtos().forEach(key->{
				if (key.getCreatedBy() == null) {
					key.setCreatedBy(createdBy);
					key.setCreatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMac(lgIp);
				}else {
					key.setUpdatedBy(createdBy);
					key.setUpdatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMacUpd(lgIp);
				}
				
				key.getAuditBalanceSheetSubParameterDtos().forEach(sub->{
					if (sub.getCreatedBy() == null) {
						sub.setCreatedBy(createdBy);
						sub.setCreatedDate(newDate);
						sub.setOrgId(orgId);
						sub.setLgIpMac(lgIp);
					}else {
						sub.setUpdatedBy(createdBy);
						sub.setUpdatedDate(newDate);
						sub.setOrgId(orgId);
						sub.setLgIpMacUpd(lgIp);
					}
					sub.getAuditBalanceSheetSubParameterDetailDtos().forEach(subDet->{
						if (subDet.getCreatedBy() == null) {
							subDet.setCreatedBy(createdBy);
							subDet.setCreatedDate(newDate);
							subDet.setOrgId(orgId);
							subDet.setLgIpMac(lgIp);
						}
						else {
							subDet.setUpdatedBy(createdBy);
							sub.setUpdatedDate(newDate);
							subDet.setOrgId(orgId);
							subDet.setLgIpMacUpd(lgIp);
						}
					});
					
				});
			});
			
		}
		AuditBalanceSheetMasterDto masterDto = auditBalanceSheetEntryService.saveDetails(dto, this);
		this.setDto(masterDto);
		this.setSuccessMessage(getAppSession().getMessage("sfac.assement.save") + masterDto.getApplicationId());
		return true;

	}
	
	
	

}
