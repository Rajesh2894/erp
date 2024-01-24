package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.abm.mainet.common.dto.RequestDTO;

public class WaterRequestDto extends RequestDTO implements Serializable {

    private static final long serialVersionUID = 9009968275747459199L;
    private Long orgId;
    private Long serviceId;
    private Long deptId;
    private Long langId;
    private String propertyNo;
    private String flatPrstnId;

    private String flag;
    private String procedureFlag;

    private Long ccApproved;
    private String ccReason;
    private Date ccDate;

    private Long trafficId;
    private Long serviceType;

    Map<Long, String> mapData;

    private Long subId;

    private Long subId1;
    private Long subId2;
    private Long subId3;
    private Long subId4;
    private Long subId5;

    private Date consDate;

    private String connectionNo;
    private String databaseType;

    private String checkForSlumName;

    private Date assesmentDate;
    private Date constructionCompletiontDate;

    private int count;

    private Long applicationId;

    @Override
    public Long getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public Long getDeptId() {
        return deptId;
    }

    @Override
    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public Long getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(final String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getFlatPrstnId() {
        return flatPrstnId;
    }

    public void setFlatPrstnId(final String flatPrstnId) {
        this.flatPrstnId = flatPrstnId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public String getProcedureFlag() {
        return procedureFlag;
    }

    public void setProcedureFlag(final String procedureFlag) {
        this.procedureFlag = procedureFlag;
    }

    public Long getCcApproved() {
        return ccApproved;
    }

    public void setCcApproved(final Long ccApproved) {
        this.ccApproved = ccApproved;
    }

    public String getCcReason() {
        return ccReason;
    }

    public void setCcReason(final String ccReason) {
        this.ccReason = ccReason;
    }

    public Date getCcDate() {
        return ccDate;
    }

    public void setCcDate(final Date ccDate) {
        this.ccDate = ccDate;
    }

    public Long getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(final Long trafficId) {
        this.trafficId = trafficId;
    }

    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(final Long serviceType) {
        this.serviceType = serviceType;
    }

    public Map<Long, String> getMapData() {
        return mapData;
    }

    public void setMapData(final Map<Long, String> mapData) {
        this.mapData = mapData;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(final Long subId) {
        this.subId = subId;
    }

    public Long getSubId1() {
        return subId1;
    }

    public void setSubId1(final Long subId1) {
        this.subId1 = subId1;
    }

    public Long getSubId2() {
        return subId2;
    }

    public void setSubId2(final Long subId2) {
        this.subId2 = subId2;
    }

    public Long getSubId3() {
        return subId3;
    }

    public void setSubId3(final Long subId3) {
        this.subId3 = subId3;
    }

    public Long getSubId4() {
        return subId4;
    }

    public void setSubId4(final Long subId4) {
        this.subId4 = subId4;
    }

    public Long getSubId5() {
        return subId5;
    }

    public void setSubId5(final Long subId5) {
        this.subId5 = subId5;
    }

    public Date getConsDate() {
        return consDate;
    }

    public void setConsDate(final Date consDate) {
        this.consDate = consDate;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(final String databaseType) {
        this.databaseType = databaseType;
    }

    public String getCheckForSlumName() {
        return checkForSlumName;
    }

    public void setCheckForSlumName(final String checkForSlumName) {
        this.checkForSlumName = checkForSlumName;
    }

    public Date getAssesmentDate() {
        return assesmentDate;
    }

    public void setAssesmentDate(final Date assesmentDate) {
        this.assesmentDate = assesmentDate;
    }

    public Date getConstructionCompletiontDate() {
        return constructionCompletiontDate;
    }

    public void setConstructionCompletiontDate(final Date constructionCompletiontDate) {
        this.constructionCompletiontDate = constructionCompletiontDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    @Override
    public Long getApplicationId() {
        return applicationId;
    }

    @Override
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

}
