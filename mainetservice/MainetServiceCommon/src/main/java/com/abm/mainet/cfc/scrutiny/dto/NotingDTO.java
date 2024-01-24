package com.abm.mainet.cfc.scrutiny.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

public class NotingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long level;
	
	private List<ViewCFCScrutinyLabelValue> scrutinyLabelValues = new ArrayList<>();
	
	private List<ViewCFCScrutinyLabelValue> scrutinyFieldLabelValues = new ArrayList<>();
	
	private List<LicenseApplicationLandAcquisitionDetailDTO> listLandDetailDTO = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();

	private List<SiteAffectedDTO> siteAffectedDTO = new ArrayList<>();
	
	private List<LicenseGrantedDTO> listLicenseDto = new ArrayList<LicenseGrantedDTO>();
	
    private String empName;
    
    private String slDsgName;
    
    private String stringDate;
    
    private String remark;
    
    private List<CFCAttachment> landCfcAttachment = new ArrayList<>();
    
    private List<CFCAttachment> siteCfcAttachment = new ArrayList<>();
    
    private List<CFCAttachment> jECfcAttachment = new ArrayList<>();
    
    private List<CFCAttachment> laoCfcAttachment = new ArrayList<>();
    
    private String laoRemark;
    
    private Long taskId;

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public List<ViewCFCScrutinyLabelValue> getScrutinyLabelValues() {
		return scrutinyLabelValues;
	}

	public void setScrutinyLabelValues(List<ViewCFCScrutinyLabelValue> scrutinyLabelValues) {
		this.scrutinyLabelValues = scrutinyLabelValues;
	}

	public List<LicenseApplicationLandAcquisitionDetailDTO> getListLandDetailDTO() {
		return listLandDetailDTO;
	}

	public void setListLandDetailDTO(List<LicenseApplicationLandAcquisitionDetailDTO> listLandDetailDTO) {
		this.listLandDetailDTO = listLandDetailDTO;
	}

	public List<SiteAffectedDTO> getSiteAffectedDTO() {
		return siteAffectedDTO;
	}

	public void setSiteAffectedDTO(List<SiteAffectedDTO> siteAffectedDTO) {
		this.siteAffectedDTO = siteAffectedDTO;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSlDsgName() {
		return slDsgName;
	}

	public void setSlDsgName(String slDsgName) {
		this.slDsgName = slDsgName;
	}

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<LicenseGrantedDTO> getListLicenseDto() {
		return listLicenseDto;
	}

	public void setListLicenseDto(List<LicenseGrantedDTO> listLicenseDto) {
		this.listLicenseDto = listLicenseDto;
	}

	public List<CFCAttachment> getLandCfcAttachment() {
		return landCfcAttachment;
	}

	public void setLandCfcAttachment(List<CFCAttachment> landCfcAttachment) {
		this.landCfcAttachment = landCfcAttachment;
	}

	public List<CFCAttachment> getSiteCfcAttachment() {
		return siteCfcAttachment;
	}

	public void setSiteCfcAttachment(List<CFCAttachment> siteCfcAttachment) {
		this.siteCfcAttachment = siteCfcAttachment;
	}

	public List<CFCAttachment> getjECfcAttachment() {
		return jECfcAttachment;
	}

	public void setjECfcAttachment(List<CFCAttachment> jECfcAttachment) {
		this.jECfcAttachment = jECfcAttachment;
	}

	public List<CFCAttachment> getLaoCfcAttachment() {
		return laoCfcAttachment;
	}

	public void setLaoCfcAttachment(List<CFCAttachment> laoCfcAttachment) {
		this.laoCfcAttachment = laoCfcAttachment;
	}

	public String getLaoRemark() {
		return laoRemark;
	}

	public void setLaoRemark(String laoRemark) {
		this.laoRemark = laoRemark;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<ViewCFCScrutinyLabelValue> getScrutinyFieldLabelValues() {
		return scrutinyFieldLabelValues;
	}

	public void setScrutinyFieldLabelValues(List<ViewCFCScrutinyLabelValue> scrutinyFieldLabelValues) {
		this.scrutinyFieldLabelValues = scrutinyFieldLabelValues;
	}
	
}
