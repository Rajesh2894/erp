package com.abm.mainet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${workflow.bpm.runtime:jbpm}")
    private String bpmRuntime;

    @Value("${mainet.service.rest.url}")
    private String mainetServiceRestUrl;

    @Value("${mainet.service.rest.url.taskAssignment.notify}")
    private String taskAssignmentNotifyUrl;

    @Value("${mainet.service.rest.url.taskAssignment.initial}")
    private String taskAssignmentInitialUrl;

    @Value("${mainet.service.rest.url.taskAssignment.nextEscalation}")
    private String taskAssignmentNextEscalationUrl;

    @Value("${mainet.service.rest.url.taskAssignment.serviceEventId}")
    private String taskAssignmentServiceEventIdUrl;

    @Value("${mainet.service.rest.url.taskAssignment.serviceEventName}")
    private String taskAssignmentServiceEventNameUrl;

    @Value("${mainet.service.rest.url.taskAssignment.eventLevels}")
    private String taskAssignmentEventLevelsUrl;

    @Value("${mainet.service.rest.url.taskAssignment.eventLevelGroups}")
    private String taskAssignmentEventLevelGroupsUrl;

    @Value("${mainet.service.rest.url.taskAssignment.department}")
    private String taskAssignmentDepartmentUrl;

    @Value("${mainet.service.rest.url.taskAssignment.departmentService}")
    private String taskAssignmentDepartmentServiceUrl;

    @Value("${mainet.service.rest.url.taskAssignment.departmentComplaint}")
    private String taskAssignmentDepartmentComplaintUrl;

    @Value("${workflow.task.add.notification.enable:false}")
    private boolean taskAddNotifiaction;

    @Value("${drools.artifact.autoscan:Y}")
    private String droolsArtifactAutoscan;

    @Value("${drools.artifact.scan.interval:180000}")
    private Long droolsArtifactScanInterval;

    @Value("${jbpm.kie.workbench.url}")
    private String jbpmKieWbUrl;

    @Value("${jbpm.kie.workbench.username:admin}")
    private String jbpmKieWbUsername;

    @Value("${jbpm.kie.workbench.password:admin}")
    private String jbpmKieWbPassword;

    @Value("${jbpm.kie.workbench.timeout:30}")
    private Integer jbpmKieWbTimeout;

    @Value("${jbpm.kie.task.insecure:true}")
    private boolean jbpmTaskInsecure;

    @Value("${jbpm.kie.taskAdministrator.user:Administrator}")
    private String jbpmTaskAdministratorUser;

    @Value("${jbpm.kie.taskAdministrator.role:Administrators}")
    private String jbpmTaskAdministratorRole;
    
    @Value("${mainet.service.rest.url.taskAssignment.getTaskAssignmentByLoc}")
    private String taskAssignmentDepartmentLocationServiceUrl;

    public String getBpmRuntime() {
        return bpmRuntime;
    }

    public String getMainetServiceRestUrl() {
        return mainetServiceRestUrl;
    }

    public String getTaskAssignmentNotifyUrl() {
        return taskAssignmentNotifyUrl;
    }

    public String getTaskAssignmentInitialUrl() {
        return taskAssignmentInitialUrl;
    }

    public String getTaskAssignmentNextEscalationUrl() {
        return taskAssignmentNextEscalationUrl;
    }

    public String getTaskAssignmentServiceEventIdUrl() {
        return taskAssignmentServiceEventIdUrl;
    }

    public String getTaskAssignmentServiceEventNameUrl() {
        return taskAssignmentServiceEventNameUrl;
    }

    public String getTaskAssignmentEventLevelsUrl() {
        return taskAssignmentEventLevelsUrl;
    }

    public String getTaskAssignmentEventLevelGroupsUrl() {
        return taskAssignmentEventLevelGroupsUrl;
    }

    public String getTaskAssignmentDepartmentUrl() {
        return taskAssignmentDepartmentUrl;
    }

    public String getTaskAssignmentDepartmentServiceUrl() {
        return taskAssignmentDepartmentServiceUrl;
    }

    public String getTaskAssignmentDepartmentComplaintUrl() {
        return taskAssignmentDepartmentComplaintUrl;
    }

    public boolean isTaskAddNotifiaction() {
        return taskAddNotifiaction;
    }

    public String getDroolsArtifactAutoscan() {
        return droolsArtifactAutoscan;
    }

    public Long getDroolsArtifactScanInterval() {
        return droolsArtifactScanInterval;
    }

    public String getJbpmKieWbUrl() {
        return jbpmKieWbUrl;
    }

    public String getJbpmKieWbUsername() {
        return jbpmKieWbUsername;
    }

    public String getJbpmKieWbPassword() {
        return jbpmKieWbPassword;
    }

    public Integer getJbpmKieWbTimeout() {
        return jbpmKieWbTimeout;
    }

    public boolean isJbpmTaskInsecure() {
        return jbpmTaskInsecure;
    }

    public String getJbpmTaskAdministratorUser() {
        return jbpmTaskAdministratorUser;
    }

    public String getJbpmTaskAdministratorRole() {
        return jbpmTaskAdministratorRole;
    }

	public String getTaskAssignmentDepartmentLocationServiceUrl() {
		return taskAssignmentDepartmentLocationServiceUrl;
	}

}
