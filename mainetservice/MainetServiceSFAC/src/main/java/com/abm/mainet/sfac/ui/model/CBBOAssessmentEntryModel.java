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
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.sfac.dto.AssessmentKeyParameterDto;
import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.dto.AssessmentSubParameterDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.CBBOAssesementEntryService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class CBBOAssessmentEntryModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7611510902162454270L;

	@Autowired
	private CBBOAssesementEntryService cbboAssEntryService;

	private AssessmentMasterDto assementMasterDto = new AssessmentMasterDto();

	private AssessmentKeyParameterDto assessmentKeyParameterDto = new AssessmentKeyParameterDto();

	List<CBBOMasterDto> cbboMasterDtoList = new ArrayList<CBBOMasterDto>();
	private List<IAMasterDto> iaNameList = new ArrayList<>();

	List<AssessmentMasterDto> assessmentDtoList = new ArrayList<>();

	private List<AssessmentKeyParameterDto> assementKeyParamDtoList = new ArrayList<>();

	private List<AssessmentSubParameterDto> assementSubParamDtoList = new ArrayList<>();

	private String saveMode;

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private String showParamTables;

	private String assessementNo;
	private String cbboAssNo;
	
	private String assYear;

	/**
	 * @return the assementMasterDto
	 */
	public AssessmentMasterDto getAssementMasterDto() {
		return assementMasterDto;
	}

	/**
	 * @param assementMasterDto the assementMasterDto to set
	 */
	public void setAssementMasterDto(AssessmentMasterDto assementMasterDto) {
		this.assementMasterDto = assementMasterDto;
	}

	/**
	 * @return the assessmentKeyParameterDto
	 */
	public AssessmentKeyParameterDto getAssessmentKeyParameterDto() {
		return assessmentKeyParameterDto;
	}

	/**
	 * @param assessmentKeyParameterDto the assessmentKeyParameterDto to set
	 */
	public void setAssessmentKeyParameterDto(AssessmentKeyParameterDto assessmentKeyParameterDto) {
		this.assessmentKeyParameterDto = assessmentKeyParameterDto;
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

	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date newDate = new Date();
		Employee emp = UserSession.getCurrent().getEmployee();
		AssessmentMasterDto dto = getAssementMasterDto();

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
			dto.getAssementKeyParamDtoList().forEach(key->{
				if (key.getCreatedBy() == null) {
					key.setCreatedBy(createdBy);
					key.setCreatedDate(newDate);
					key.setOrgId(orgId);
					key.setLgIpMac(lgIp);
				}
					key.getAssSubParamDtoList().forEach(sub->{
						if (sub.getCreatedBy() == null) {
							sub.setCreatedBy(createdBy);
							sub.setCreatedDate(newDate);
							sub.setOrgId(orgId);
							sub.setLgIpMac(lgIp);
						}
						sub.getAssSubParamDetailDtoList().forEach(subDet->{
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
			
			dto.getAssementKeyParamDtoList().forEach(key->{
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
				
				key.getAssSubParamDtoList().forEach(sub->{
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
					sub.getAssSubParamDetailDtoList().forEach(subDet->{
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
		AssessmentMasterDto masterDto = cbboAssEntryService.saveDetails(dto, this);
		this.setAssementMasterDto(masterDto);
		this.setSuccessMessage(getAppSession().getMessage("sfac.assement.save") + masterDto.getApplicationId());
		return true;

	}

	/**
	 * @return the cbboMasterDtoList
	 */
	public List<CBBOMasterDto> getCbboMasterDtoList() {
		return cbboMasterDtoList;
	}

	/**
	 * @param cbboMasterDtoList the cbboMasterDtoList to set
	 */
	public void setCbboMasterDtoList(List<CBBOMasterDto> cbboMasterDtoList) {
		this.cbboMasterDtoList = cbboMasterDtoList;
	}

	/**
	 * @return the assementKeyParamDtoList
	 */
	public List<AssessmentKeyParameterDto> getAssementKeyParamDtoList() {
		return assementKeyParamDtoList;
	}

	/**
	 * @param assementKeyParamDtoList the assementKeyParamDtoList to set
	 */
	public void setAssementKeyParamDtoList(List<AssessmentKeyParameterDto> assementKeyParamDtoList) {
		this.assementKeyParamDtoList = assementKeyParamDtoList;
	}

	/**
	 * @return the iaNameList
	 */
	public List<IAMasterDto> getIaNameList() {
		return iaNameList;
	}

	/**
	 * @param iaNameList the iaNameList to set
	 */
	public void setIaNameList(List<IAMasterDto> iaNameList) {
		this.iaNameList = iaNameList;
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
	 * @return the showParamTables
	 */
	public String getShowParamTables() {
		return showParamTables;
	}

	/**
	 * @param showParamTables the showParamTables to set
	 */
	public void setShowParamTables(String showParamTables) {
		this.showParamTables = showParamTables;
	}

	/**
	 * @return the assessementNo
	 */
	public String getAssessementNo() {
		return assessementNo;
	}

	/**
	 * @param assessementNo the assessementNo to set
	 */
	public void setAssessementNo(String assessementNo) {
		this.assessementNo = assessementNo;
	}

	/**
	 * @return the cbboAssNo
	 */
	public String getCbboAssNo() {
		return cbboAssNo;
	}

	/**
	 * @param cbboAssNo the cbboAssNo to set
	 */
	public void setCbboAssNo(String cbboAssNo) {
		this.cbboAssNo = cbboAssNo;
	}

	/**
	 * @return the assessmentDtoList
	 */
	public List<AssessmentMasterDto> getAssessmentDtoList() {
		return assessmentDtoList;
	}

	/**
	 * @param assessmentDtoList the assessmentDtoList to set
	 */
	public void setAssessmentDtoList(List<AssessmentMasterDto> assessmentDtoList) {
		this.assessmentDtoList = assessmentDtoList;
	}

	/**
	 * @return the assementSubParamDtoList
	 */
	public List<AssessmentSubParameterDto> getAssementSubParamDtoList() {
		return assementSubParamDtoList;
	}

	/**
	 * @param assementSubParamDtoList the assementSubParamDtoList to set
	 */
	public void setAssementSubParamDtoList(List<AssessmentSubParameterDto> assementSubParamDtoList) {
		this.assementSubParamDtoList = assementSubParamDtoList;
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
	 * 
	 */
	/*public boolean assementMasterForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date newDate = new Date();
		AssessmentMasterDto dto = getAssementMasterDto();
		if (dto.getCreatedBy() == null) {
			dto.setCreatedBy(createdBy);
			dto.setCreatedDate(newDate);
			dto.setOrgId(orgId);
			dto.setLgIpMac(lgIp);
			dto.setAssStatus(MainetConstants.FlagD);
		} else {
			dto.setUpdatedBy(createdBy);
			dto.setUpdatedDate(newDate);
			dto.setOrgId(orgId);
			dto.setLgIpMac(lgIp);
		}
		AssessmentMasterDto masterDto = cbboAssEntryService.saveAssMastDetail(dto, this);
		this.setAssementMasterDto(masterDto);
		return true;
	}*/

	/**
	 * @return
	 */
	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(this.getCbboAssNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision(MainetConstants.Sfac.SET_DECISION_SUBMITTED);
		return taskAction;
	}

}
