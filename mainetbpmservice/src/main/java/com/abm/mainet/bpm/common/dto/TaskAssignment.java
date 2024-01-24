package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String actorId;
    private String actorRole;
    private List<String> actorIds;
    private List<String> actorRoles;
    private Integer escalationIndex;
    private Integer escalationGroupIndex;
	private String sla;
    private String slaUnit;
    private String url;
    private Long orgId;
    private Long serviceEventId;
    private String serviceEventName;
    private String serviceEventNameReg;
    private Long deptId;
    private String deptName;
    private String deptNameReg;
    private Long serviceId;
    private String serviceName;
    private String serviceNameReg;
    private Integer approverCount;
    private Long taskSlaDurationInMS;
    private Long applicationSlaDurationInMS;

    public TaskAssignment() {
      //  actorId = "";
      //  actorRole = "";
      //  actorIds = new ArrayList<>();
      //  actorRoles = new ArrayList<>();
     //   escalationIndex = 0;
      //  sla = "";
       // slaUnit = "";
      //  url = "";
     //   escalationGroupIndex=0;
      //  approverCount = 1;
      //  taskSlaDurationInMS = 0l;
       // applicationSlaDurationInMS = 0l;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(final String actorId) {
        this.actorId = actorId;
    }

    public String getActorRole() {
        return actorRole;
    }

    public void setActorRole(final String actorRole) {
        this.actorRole = actorRole;
    }

    public List<String> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<String> actorIds) {
        this.actorIds = actorIds;
    }

    public List<String> getActorRoles() {
        return actorRoles;
    }

    public void setActorRoles(List<String> actorRoles) {
        this.actorRoles = actorRoles;
    }

    public Integer getEscalationIndex() {
        return escalationIndex;
    }

    public void setEscalationIndex(final Integer escalationIndex) {
        this.escalationIndex = escalationIndex;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(final String sla) {
        this.sla = sla;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getSlaUnit() {
        return slaUnit;
    }

    public void setSlaUnit(String slaUnit) {
        this.slaUnit = slaUnit;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public Integer getApproverCount() {
        return approverCount;
    }

    public void setApproverCount(Integer approverCount) {
        this.approverCount = approverCount;
    }

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public Long getApplicationSlaDurationInMS() {
        return applicationSlaDurationInMS;
    }

    public void setApplicationSlaDurationInMS(Long applicationSlaDurationInMS) {
        this.applicationSlaDurationInMS = applicationSlaDurationInMS;
    }

    public Integer getEscalationGroupIndex() {
		return escalationGroupIndex;
	}

	public void setEscalationGroupIndex(Integer escalationGroupIndex) {
		this.escalationGroupIndex = escalationGroupIndex;
	}



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actorId == null) ? 0 : actorId.hashCode());
        result = prime * result + ((actorRole == null) ? 0 : actorRole.hashCode());
        result = prime * result + ((escalationIndex == null) ? 0 : escalationIndex.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((sla == null) ? 0 : sla.hashCode());
        result = prime * result + ((slaUnit == null) ? 0 : slaUnit.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskAssignment other = (TaskAssignment) obj;
        if (actorId == null) {
            if (other.actorId != null)
                return false;
        } else if (!actorId.equals(other.actorId))
            return false;
        if (actorRole == null) {
            if (other.actorRole != null)
                return false;
        } else if (!actorRole.equals(other.actorRole))
            return false;
        if (escalationIndex == null) {
            if (other.escalationIndex != null)
                return false;
        } else if (!escalationIndex.equals(other.escalationIndex))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        if (sla == null) {
            if (other.sla != null)
                return false;
        } else if (!sla.equals(other.sla))
            return false;
        if (slaUnit == null) {
            if (other.slaUnit != null)
                return false;
        } else if (!slaUnit.equals(other.slaUnit))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaskAssignment [actorId=" + actorId + ", actorRole=" + actorRole + ", escalationIndex=" + escalationIndex
                + ", sla=" + sla + ", slaUnit=" + slaUnit + ", url=" + url + ", orgId=" + orgId + "]";
    }

}
