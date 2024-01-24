/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.FpoAssKeyParameterDto;
import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.service.FPOAssessmentService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class FPOAssessmentEntryModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2061388383454775997L;

	@Autowired
	private FPOAssessmentService fpoAssEntryService;

	FpoAssessmentMasterDto assementMasterDto = new FpoAssessmentMasterDto();

	List<FpoAssessmentMasterDto> assessmentDtoList = new ArrayList<>();

	List<FpoAssKeyParameterDto> keyParamDtoList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private List<FPOMasterDto> fpoMastDtoList = new ArrayList<>();

	private String assYear;

	private String saveMode;

	private String fpoName;

	/**
	 * @return the assementMasterDto
	 */
	public FpoAssessmentMasterDto getAssementMasterDto() {
		return assementMasterDto;
	}

	/**
	 * @param assementMasterDto the assementMasterDto to set
	 */
	public void setAssementMasterDto(FpoAssessmentMasterDto assementMasterDto) {
		this.assementMasterDto = assementMasterDto;
	}

	/**
	 * @return the assessmentDtoList
	 */
	public List<FpoAssessmentMasterDto> getAssessmentDtoList() {
		return assessmentDtoList;
	}

	/**
	 * @param assessmentDtoList the assessmentDtoList to set
	 */
	public void setAssessmentDtoList(List<FpoAssessmentMasterDto> assessmentDtoList) {
		this.assessmentDtoList = assessmentDtoList;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	/**
	 * @return the fpoMastDtoList
	 */
	public List<FPOMasterDto> getFpoMastDtoList() {
		return fpoMastDtoList;
	}

	/**
	 * @param fpoMastDtoList the fpoMastDtoList to set
	 */
	public void setFpoMastDtoList(List<FPOMasterDto> fpoMastDtoList) {
		this.fpoMastDtoList = fpoMastDtoList;
	}

	/**
	 * @return the assYear
	 */
	public String getAssYear() {
		return assYear;
	}

	/**
	 * @param assYear the assYear to set
	 */
	public void setAssYear(String assYear) {
		this.assYear = assYear;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the keyParamDtoList
	 */
	public List<FpoAssKeyParameterDto> getKeyParamDtoList() {
		return keyParamDtoList;
	}

	/**
	 * @param keyParamDtoList the keyParamDtoList to set
	 */
	public void setKeyParamDtoList(List<FpoAssKeyParameterDto> keyParamDtoList) {
		this.keyParamDtoList = keyParamDtoList;
	}

	/**
	 * @return the fpoName
	 */
	public String getFpoName() {
		return fpoName;
	}

	/**
	 * @param fpoName the fpoName to set
	 */
	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date newDate = new Date();
		Employee emp = UserSession.getCurrent().getEmployee();
		FpoAssessmentMasterDto dto = getAssementMasterDto();
		dto.setFpoName(this.getFpoName());
		if (StringUtils.isNotEmpty(emp.getEmpname()) && StringUtils.isNotEmpty(emp.getEmplname()))
			dto.setUserName(emp.getEmpname() + "" + emp.getEmplname());
		else
			dto.setUserName(emp.getEmpname());
		if (StringUtils.isNotEmpty(emp.getEmpphoneno()))
			dto.setMobileNo(emp.getEmpphoneno());
		if (StringUtils.isNotEmpty(emp.getEmpemail()))
			dto.setEmail(emp.getEmpemail());

		if (dto.getCreatedBy() == null) {
			dto.setCreatedBy(createdBy);
			dto.setCreatedDate(newDate);
			dto.setOrgId(orgId);
			dto.setLgIpMac(lgIp);
			dto.setAssStatus(MainetConstants.FlagP);
			dto.getAssKeyDtoList().forEach(key -> {
				if (key.getCreatedBy() == null) {
					key.setCreatedBy(createdBy);
					key.setCreatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMac(lgIp);
				}
				key.getFpoSubParamDtoList().forEach(sub -> {
					if (sub.getCreatedBy() == null) {
						sub.setCreatedBy(createdBy);
						sub.setCreatedDate(newDate);
						sub.setOrgId(orgId);
						sub.setLgIpMac(lgIp);
					}
					sub.getFpoSubParamDetailDtoList().forEach(subDet -> {
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

			dto.getAssKeyDtoList().forEach(key -> {
				if (key.getCreatedBy() == null) {
					key.setCreatedBy(createdBy);
					key.setCreatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMac(lgIp);
				} else {
					key.setUpdatedBy(createdBy);
					key.setUpdatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMacUpd(lgIp);
				}

				key.getFpoSubParamDtoList().forEach(sub -> {
					if (sub.getCreatedBy() == null) {
						sub.setCreatedBy(createdBy);
						sub.setCreatedDate(newDate);
						sub.setOrgId(orgId);
						sub.setLgIpMac(lgIp);
					} else {
						sub.setUpdatedBy(createdBy);
						sub.setUpdatedDate(newDate);
						sub.setOrgId(orgId);
						sub.setLgIpMacUpd(lgIp);
					}
					sub.getFpoSubParamDetailDtoList().forEach(subDet -> {
						if (subDet.getCreatedBy() == null) {
							subDet.setCreatedBy(createdBy);
							subDet.setCreatedDate(newDate);
							subDet.setOrgId(orgId);
							subDet.setLgIpMac(lgIp);
						} else {
							subDet.setUpdatedBy(createdBy);
							sub.setUpdatedDate(newDate);
							subDet.setOrgId(orgId);
							subDet.setLgIpMacUpd(lgIp);
						}
					});

				});
			});

		}
		FpoAssessmentMasterDto masterDto = fpoAssEntryService.saveDetails(dto, this);
		this.setAssementMasterDto(masterDto);
		this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.assement.save") + masterDto.getApplicationId());
		return true;

	}

}
