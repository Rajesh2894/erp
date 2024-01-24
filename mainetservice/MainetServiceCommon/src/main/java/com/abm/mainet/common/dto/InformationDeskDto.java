package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class InformationDeskDto implements Serializable{


	private static final long serialVersionUID = 95567539763732183L;

	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private Long serviceId;
	private Long deptId;
	private Long smServiceDuration;
	private String smScrutinyChargeFlag;
	private String smAppliChargeFlag;
    private Long smFeesSchedule;
    private String smScrutinyApplicableFlag;
    private String chargeApplicable;
    private String smDurationUnit;
    private Long categoryId;
    
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getSmServiceDuration() {
		return smServiceDuration;
	}
	public void setSmServiceDuration(Long smServiceDuration) {
		this.smServiceDuration = smServiceDuration;
	}
	public String getSmScrutinyChargeFlag() {
		return smScrutinyChargeFlag;
	}
	public void setSmScrutinyChargeFlag(String smScrutinyChargeFlag) {
		this.smScrutinyChargeFlag = smScrutinyChargeFlag;
	}
	public String getSmAppliChargeFlag() {
		return smAppliChargeFlag;
	}
	public void setSmAppliChargeFlag(String smAppliChargeFlag) {
		this.smAppliChargeFlag = smAppliChargeFlag;
	}
	public Long getSmFeesSchedule() {
		return smFeesSchedule;
	}
	public void setSmFeesSchedule(Long smFeesSchedule) {
		this.smFeesSchedule = smFeesSchedule;
	}
	public String getSmScrutinyApplicableFlag() {
		return smScrutinyApplicableFlag;
	}
	public void setSmScrutinyApplicableFlag(String smScrutinyApplicableFlag) {
		this.smScrutinyApplicableFlag = smScrutinyApplicableFlag;
	}
	public String getChargeApplicable() {
		return chargeApplicable;
	}
	public void setChargeApplicable(String chargeApplicable) {
		this.chargeApplicable = chargeApplicable;
	}
	public String getSmDurationUnit() {
		return smDurationUnit;
	}
	public void setSmDurationUnit(String smDurationUnit) {
		this.smDurationUnit = smDurationUnit;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
