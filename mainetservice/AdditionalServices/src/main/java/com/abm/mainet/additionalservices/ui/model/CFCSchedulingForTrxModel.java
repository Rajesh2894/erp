package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.dto.CFCCollectionMasterDto;
import com.abm.mainet.additionalservices.dto.CFCCounterScheduleDto;
import com.abm.mainet.additionalservices.dto.CFCSchedularSummaryDto;
import com.abm.mainet.additionalservices.service.CFCSchedulingTrxService;
import com.abm.mainet.additionalservices.ui.validator.CFCSchedulingForTrxValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CFCSchedulingForTrxModel extends AbstractFormModel {

	private static final long serialVersionUID = 4944950939506059892L;

	@Autowired
	private CFCSchedulingTrxService cfcSchedulingTrxService;

	private Map<Long, String> wardList = null;

	private List<Employee> empList = new ArrayList<>();

	private CFCCollectionMasterDto cfcCollectionMasterDto;

	private String saveMode;

	private Long counterIndex;

	private List<String> collectionNos;

	private List<CFCSchedularSummaryDto> cfcSchedularSummaryDtos;

	private CFCCounterScheduleDto cfcCounterScheduleDto;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CFCCounterScheduleDto getCfcCounterScheduleDto() {
		return cfcCounterScheduleDto;
	}

	public void setCfcCounterScheduleDto(CFCCounterScheduleDto cfcCounterScheduleDto) {
		this.cfcCounterScheduleDto = cfcCounterScheduleDto;
	}

	public List<String> getCollectionNos() {
		return collectionNos;
	}

	public void setCollectionNos(List<String> collectionNos) {
		this.collectionNos = collectionNos;
	}

	public List<CFCSchedularSummaryDto> getCfcSchedularSummaryDtos() {
		return cfcSchedularSummaryDtos;
	}

	public void setCfcSchedularSummaryDtos(List<CFCSchedularSummaryDto> cfcSchedularSummaryDtos) {
		this.cfcSchedularSummaryDtos = cfcSchedularSummaryDtos;
	}

	public Long getCounterIndex() {
		return counterIndex;
	}

	public void setCounterIndex(Long counterIndex) {
		this.counterIndex = counterIndex;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public CFCCollectionMasterDto getCfcCollectionMasterDto() {
		return cfcCollectionMasterDto;
	}

	public void setCfcCollectionMasterDto(CFCCollectionMasterDto cfcCollectionMasterDto) {
		this.cfcCollectionMasterDto = cfcCollectionMasterDto;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public Map<Long, String> getWardList() {
		return wardList;
	}

	public void setWardList(Map<Long, String> wardList) {
		this.wardList = wardList;
	}

	@Override
	public boolean saveForm() {

		String savemode = getSaveMode();
		//121825  Validation class called to do validation for the dates). 
		cfcCollectionMasterDto.getCfcCounterMasterDtos().get(0).setCmCollncentreno(cfcCollectionMasterDto.getCmCollncentreno());
		validateBean(cfcCollectionMasterDto.getCfcCounterMasterDtos(), CFCSchedulingForTrxValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		

		if (savemode == MainetConstants.FlagC) {

			cfcCollectionMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			cfcCollectionMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			cfcCollectionMasterDto.setCreationDate(new Date());
			cfcCollectionMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			if (!cfcCollectionMasterDto.getCfcCounterMasterDtos().isEmpty()) {
				cfcCollectionMasterDto.getCfcCounterMasterDtos().forEach(cfcCounterDto -> {
					cfcCounterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					cfcCounterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					cfcCounterDto.setCreationDate(new Date());
					cfcCounterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					if (!cfcCounterDto.getCfcCounterScheduleDtos().isEmpty()) {
						cfcCounterDto.getCfcCounterScheduleDtos().forEach(cfcScheduleDto -> {
							cfcScheduleDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
							cfcScheduleDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
							cfcScheduleDto.setCreationDate(new Date());
							cfcScheduleDto.setCsStatus(MainetConstants.FlagA);
							cfcScheduleDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						});
					}
				});

			}
			setSuccessMessage(
					ApplicationSession.getInstance().getMessage("Collection Master Entry Added Successfully"));
			cfcSchedulingTrxService.saveCollectionDetail(cfcCollectionMasterDto);
		} else if (saveMode == MainetConstants.FlagE) {
			cfcCollectionMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			cfcCollectionMasterDto.setUpdatedDate(new Date());
			cfcCollectionMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			if (!cfcCollectionMasterDto.getCfcCounterMasterDtos().isEmpty()) {
				cfcCollectionMasterDto.getCfcCounterMasterDtos().forEach(cfcCounterDto -> {
					cfcCounterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					cfcCounterDto.setUpdatedDate(new Date());
					cfcCounterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
					if (!cfcCounterDto.getCfcCounterScheduleDtos().isEmpty()) {
						cfcCounterDto.getCfcCounterScheduleDtos().forEach(cfcScheduleDto -> {
							cfcScheduleDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
							cfcScheduleDto.setUpdatedDate(new Date());
							cfcScheduleDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
						});
					}
				});

			}
			cfcSchedulingTrxService.saveCollectionDetail(cfcCollectionMasterDto);
		}

		setSuccessMessage(ApplicationSession.getInstance().getMessage("Collection Master Entry Added Successfully"));
		return true;
	}

}
