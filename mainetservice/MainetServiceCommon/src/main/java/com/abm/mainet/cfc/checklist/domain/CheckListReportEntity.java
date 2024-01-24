package com.abm.mainet.cfc.checklist.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jugnu.pandey
 * @since 13 May 2015
 */
@Entity
@Table(name = "VW_APP_REJ_REPORT")
public class CheckListReportEntity implements Serializable {
    private static final long serialVersionUID = 316521605592402158L;

    @Id
    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
    private Long apmApplicationId;

    @Column(name = "ADATE", length = 10, nullable = true)
    private String adate;

    @Column(name = "SERVICE", length = 100, nullable = true)
    private String service;

    @Column(name = "ANAME", length = 2000, nullable = true)
    private String aname;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Transient
    private List<LookUp> rejectedDocs = new ArrayList<>(0);

    @Transient
    public List<Object> object = new ArrayList<>();

    @Transient
    public List<Object> object1 = new ArrayList<>();

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(final String adate) {
        this.adate = adate;
    }

    public String getService() {
        return service;
    }

    public void setService(final String service) {
        this.service = service;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(final String aname) {
        this.aname = aname;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public List<LookUp> getRejectedDocs() {
        return rejectedDocs;
    }

    public void setRejectedDocs(final List<LookUp> rejectedDocs) {
        this.rejectedDocs = rejectedDocs;
    }

    public List<Object> getObject() {
        return object;
    }

    public void setObject(final List<Object> object) {
        this.object = object;
    }

    public List<Object> getObject1() {
        return object1;
    }

    public void setObject1(final List<Object> object1) {
        this.object1 = object1;
    }
}
