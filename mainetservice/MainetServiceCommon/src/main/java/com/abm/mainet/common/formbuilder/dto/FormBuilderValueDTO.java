/*
 * Created on 21 Mar 2016 ( Time 19:07:04 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.formbuilder.dto;

import java.io.Serializable;
import java.util.Date;

public class FormBuilderValueDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7421106997963890702L;

    private Long referenceNo;
    private Long orgId;
    private Long createdBy;
    private String empName;
    private Date createdDate;

    public Long getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(Long referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

}
