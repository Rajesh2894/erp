package com.abm.mainet.dashboard.citizen.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ritesh.patil
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitizenDashBoardResDTO {

    private String appId;
    private String serviceName;
    private String smServiceNameMar;
    private String status;
    private String lastDecision;
    private String eventUrl;
    private String smShortdesc;
    private Long orgId;
    private String deptName;
    private String deptNameMar;
    private String appDate;
    private String refId;
    private Long onlTransactionId;
    private Long taskId;
    private Long taskSlaDurationInMS;
    private String taskStatus;
    private String actorId;
    private Long serviceEventId;
    private String serviceEventName;
    private String serviceEventNameReg;
    private String serviceEventUrl;
    private String ServiceType;
    private String dpDeptcode;
    private Long smServiceDuration;
    private String docName;
    private String docPath;
    private String filePath;
    private byte[] document;
    private String doc;
    private String statusDesc;

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the smShortdesc
     */
    public String getSmShortdesc() {
        return smShortdesc;
    }

    /**
     * @param smShortdesc the smShortdesc to set
     */
    public void setSmShortdesc(final String smShortdesc) {
        this.smShortdesc = smShortdesc;
    }

    /**
     * @return the smServiceNameMar
     */
    public String getSmServiceNameMar() {
        return smServiceNameMar;
    }

    /**
     * @param smServiceNameMar the smServiceNameMar to set
     */
    public void setSmServiceNameMar(final String smServiceNameMar) {
        this.smServiceNameMar = smServiceNameMar;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * @return the eventUrl
     */
    public String getEventUrl() {
        return eventUrl;
    }

    /**
     * @param eventUrl the eventUrl to set
     */
    public void setEventUrl(final String eventUrl) {
        this.eventUrl = eventUrl;
    }

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(final String appId) {
        this.appId = appId;
    }

	/**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getDeptNameMar() {
        return deptNameMar;
    }

    public void setDeptNameMar(String deptNameMar) {
        this.deptNameMar = deptNameMar;
    }

    public Long getOnlTransactionId() {
        return onlTransactionId;
    }

    public void setOnlTransactionId(Long onlTransactionId) {
        this.onlTransactionId = onlTransactionId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(Long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public String getServiceEventName() {
        return serviceEventName;
    }

    public void setServiceEventName(String serviceEventName) {
        this.serviceEventName = serviceEventName;
    }

    public String getServiceEventNameReg() {
        return serviceEventNameReg;
    }

    public void setServiceEventNameReg(String serviceEventNameReg) {
        this.serviceEventNameReg = serviceEventNameReg;
    }

    public String getServiceEventUrl() {
        return serviceEventUrl;
    }

    public void setServiceEventUrl(String serviceEventUrl) {
        this.serviceEventUrl = serviceEventUrl;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

	public String getDpDeptcode() {
		return dpDeptcode;
	}

	public void setDpDeptcode(String dpDeptcode) {
		this.dpDeptcode = dpDeptcode;
	}

	public Long getSmServiceDuration() {
		return smServiceDuration;
	}

	public void setSmServiceDuration(Long smServiceDuration) {
		this.smServiceDuration = smServiceDuration;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
    

}
