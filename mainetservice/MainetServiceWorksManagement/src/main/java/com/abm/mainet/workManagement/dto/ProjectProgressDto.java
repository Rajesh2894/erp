package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class ProjectProgressDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6130266566246269024L;
	
	private String wmSchNameEng;
	
	private Long projectId;
	
	private Long workId;
	
	private String projNameEng;
	
	private String executingAgency;

	private String projNameReg;

	private String projDescription;

	private String projStartDate;

	private String projEndDate;

	private String workName;
	
	private String schFundName;

	private String workStartDate;

	private String workEndDate;

	private String workcode;
	
	private String vmVendorname;

	private List<MileStoneDTO> mileStoneDTO = new ArrayList<>();
	
	public String getVmVendorname() {
		return vmVendorname;
	}

	public void setVmVendorname(String vmVendorname) {
		this.vmVendorname = vmVendorname;
	}

	public String getWmSchNameEng() {
		return wmSchNameEng;
	}

	public void setWmSchNameEng(String wmSchNameEng) {
		this.wmSchNameEng = wmSchNameEng;
	}

	public String getSchFundName() {
		return schFundName;
	}

	public void setSchFundName(String schFundName) {
		this.schFundName = schFundName;
	}

	public String getExecutingAgency() {
		return executingAgency;
	}

	public void setExecutingAgency(String executingAgency) {
		this.executingAgency = executingAgency;
	}


	public String getProjNameEng() {
		return projNameEng;
	}

	public void setProjNameEng(String projNameEng) {
		this.projNameEng = projNameEng;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

	public String getProjDescription() {
		return projDescription;
	}

	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public List<MileStoneDTO> getMileStoneDTO() {
		return mileStoneDTO;
	}

	public void setMileStoneDTO(List<MileStoneDTO> mileStoneDTO) {
		this.mileStoneDTO = mileStoneDTO;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(String projStartDate) {
		this.projStartDate = projStartDate;
	}

	public String getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(String projEndDate) {
		this.projEndDate = projEndDate;
	}

	public String getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(String workStartDate) {
		this.workStartDate = workStartDate;
	}

	public String getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
	}

}
