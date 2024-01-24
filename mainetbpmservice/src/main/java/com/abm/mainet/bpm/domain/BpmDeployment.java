package com.abm.mainet.bpm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPM_DEPLOYMENT")
public class BpmDeployment {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.bpm.domain.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DP_STID")
    private Long id;

    @Column(name = "Group_ID")
    private String groupId;

    @Column(name = "Artifact_ID")
    private String artifactId;

    @Column(name = "Version")
    private String version;

    @Column(name = "Process_ID")
    private String processId;

    @Column(name = "BPM_RUNTIME")
    private String bpmRuntime;

    @Column(name = "Status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBpmRuntime() {
        return bpmRuntime;
    }

    public void setBpmRuntime(String bpmRuntime) {
        this.bpmRuntime = bpmRuntime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_BPM_DEPLOYMENT_STORE", "DP_STID" };
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }

}
