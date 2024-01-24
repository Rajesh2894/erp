package com.abm.mainet.care.ui.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;

@Component
@Scope("session")
public class GrievanceComplaintStatusModel extends AbstractFormModel {

	private static final long serialVersionUID = -6524872082243721314L;

	private Set<LookUp> departments = new HashSet<>();
	private Set<LookUp> complaintTypes = new HashSet<>();
	List<ComplaintGrid> complaintGrid = new ArrayList<>();
	private CareRequestDTO careRequest;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<CareRequestDTO> careRequests = new ArrayList<>();
	private ComplaintAcknowledgementModel complaintAcknowledgementModel;
	private String loggedInEmpDept; 

	public String getLoggedInEmpDept() {
		return loggedInEmpDept;
	}

	public void setLoggedInEmpDept(String loggedInEmpDept) {
		this.loggedInEmpDept = loggedInEmpDept;
	}

	public Set<LookUp> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<LookUp> departments) {
		this.departments = departments;
	}

	public Set<LookUp> getComplaintTypes() {
		return complaintTypes;
	}

	public void setComplaintTypes(Set<LookUp> complaintTypes) {
		this.complaintTypes = complaintTypes;
	}

	public List<ComplaintGrid> getComplaintGrid() {
		return complaintGrid;
	}

	public void setComplaintGrid(List<ComplaintGrid> complaintGrid) {
		this.complaintGrid = complaintGrid;
	}

	public CareRequestDTO getCareRequest() {
		return careRequest;
	}

	public void setCareRequest(CareRequestDTO careRequest) {
		this.careRequest = careRequest;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<CareRequestDTO> getCareRequests() {
		return careRequests;
	}

	public void setCareRequests(List<CareRequestDTO> careRequests) {
		this.careRequests = careRequests;
	}

	public ComplaintAcknowledgementModel getComplaintAcknowledgementModel() {
		return complaintAcknowledgementModel;
	}

	public void setComplaintAcknowledgementModel(ComplaintAcknowledgementModel complaintAcknowledgementModel) {
		this.complaintAcknowledgementModel = complaintAcknowledgementModel;
	}

}
