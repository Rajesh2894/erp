
package com.abm.mainet.account.dto;

/**
 * @author prasant.sahu
 *
 */
public class DeasMasterEntryDto {

    private Long entityId;

    private Long fundId;
    private Long fieldId;
    private Long functionId;
    private Long primaryCodeId;
    private Long secondaryCodeId;
    private String remark;

    private String fundCode;
    private String fieldCode;
    private String functionCode;
    private String primaryCode;
    private String secondaryCode;
    private String status;

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the functionId
     */
    public Long getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId the functionId to set
     */
    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    /**
     * @return the primaryCodeId
     */
    public Long getPrimaryCodeId() {
        return primaryCodeId;
    }

    /**
     * @param primaryCodeId the primaryCodeId to set
     */
    public void setPrimaryCodeId(final Long primaryCodeId) {
        this.primaryCodeId = primaryCodeId;
    }

    /**
     * @return the secondaryCodeId
     */
    public Long getSecondaryCodeId() {
        return secondaryCodeId;
    }

    /**
     * @param secondaryCodeId the secondaryCodeId to set
     */
    public void setSecondaryCodeId(final Long secondaryCodeId) {
        this.secondaryCodeId = secondaryCodeId;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(final String remark) {
        this.remark = remark;
    }

    /**
     * @return the fundCode
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * @param fundCode the fundCode to set
     */
    public void setFundCode(final String fundCode) {
        this.fundCode = fundCode;
    }

    /**
     * @return the fieldCode
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * @param fieldCode the fieldCode to set
     */
    public void setFieldCode(final String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * @return the functionCode
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * @param functionCode the functionCode to set
     */
    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * @return the entityId
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the primaryCode
     */
    public String getPrimaryCode() {
        return primaryCode;
    }

    /**
     * @param primaryCode the primaryCode to set
     */
    public void setPrimaryCode(final String primaryCode) {
        this.primaryCode = primaryCode;
    }

    /**
     * @return the secondaryCode
     */
    public String getSecondaryCode() {
        return secondaryCode;
    }

    /**
     * @param secondaryCode the secondaryCode to set
     */
    public void setSecondaryCode(final String secondaryCode) {
        this.secondaryCode = secondaryCode;
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
}
