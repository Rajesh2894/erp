package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;

public class ApplicationStatusDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long applicationId;
    private String organizationName;
    private String organizationNameReg;
    private String serviceName;
    private String serviceNameReg;
    private String status;
    private String lastDecision;
    private Date date;
    private String formattedDate;
    private List<WorkflowTaskActionWithDocs> actions = new ArrayList<>();
    private String errors;
    private String empName;
    private String deptName;
    private String dueDate;
	
	private String referenceNo;
    private String ward1;
    private String ward2;
    private String ward3;
    

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationNameReg() {
        return organizationNameReg;
    }

    public void setOrganizationNameReg(String organizationNameReg) {
        this.organizationNameReg = organizationNameReg;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameReg() {
        return serviceNameReg;
    }

    public void setServiceNameReg(String serviceNameReg) {
        this.serviceNameReg = serviceNameReg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public List<WorkflowTaskActionWithDocs> getActions() {
        return actions;
    }

    public void setActions(List<WorkflowTaskActionWithDocs> actions) {
        this.actions = actions;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getWard1() {
		return ward1;
	}

	public void setWard1(String ward1) {
		this.ward1 = ward1;
	}

	public String getWard2() {
		return ward2;
	}

	public void setWard2(String ward2) {
		this.ward2 = ward2;
	}

	public String getWard3() {
		return ward3;
	}

	public void setWard3(String ward3) {
		this.ward3 = ward3;
	}


    
}
