package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Harsha.Ramachandran
 *
 */
public class PortalServiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long serviceId;

    private Long serviceOrgId;

    private String serviceName;

    private String serviceNameReg;

    private String shortName;

    private Date lmodDate;

    private String isDeleted;

    private Long langId;

    private Long userId;

    private Date updatedDate;

    private Long updatedBy;

    private Long psmDpDeptid;
    private String psmDpDeptCode;
    private String psmDpDeptDesc;
    private String psmDpNameMar;

    List<PortalServiceDTO> portalServiceList;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getServiceOrgId() {
        return serviceOrgId;
    }

    public void setServiceOrgId(Long serviceOrgId) {
        this.serviceOrgId = serviceOrgId;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getPsmDpDeptid() {
        return psmDpDeptid;
    }

    public void setPsmDpDeptid(Long psmDpDeptid) {
        this.psmDpDeptid = psmDpDeptid;
    }

    public String getPsmDpDeptCode() {
        return psmDpDeptCode;
    }

    public void setPsmDpDeptCode(String psmDpDeptCode) {
        this.psmDpDeptCode = psmDpDeptCode;
    }

    public String getPsmDpDeptDesc() {
        return psmDpDeptDesc;
    }

    public void setPsmDpDeptDesc(String psmDpDeptDesc) {
        this.psmDpDeptDesc = psmDpDeptDesc;
    }

    public String getPsmDpNameMar() {
        return psmDpNameMar;
    }

    public void setPsmDpNameMar(String psmDpNameMar) {
        this.psmDpNameMar = psmDpNameMar;
    }

    public List<PortalServiceDTO> getPortalServiceList() {
        return portalServiceList;
    }

    public void setPortalServiceList(List<PortalServiceDTO> portalServiceList) {
        this.portalServiceList = portalServiceList;
    }

}
