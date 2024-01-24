package com.abm.mainet.rti.dto;

import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class RtiRemarksHistDto extends RequestDTO {
	private static final long serialVersionUID = 4587771458335377655L;
	private String actionname;
	private String actionBy;
	private String actionByDesg;
	private String remarks;
	private List<CFCAttachment> docList = new ArrayList<CFCAttachment>();

	public String getActionname() {
		return actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	public String getActionBy() {
		return actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public String getActionByDesg() {
		return actionByDesg;
	}

	public void setActionByDesg(String actionByDesg) {
		this.actionByDesg = actionByDesg;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<CFCAttachment> getDocList() {
		return docList;
	}

	public void setDocList(List<CFCAttachment> docList) {
		this.docList = docList;
	}

}
