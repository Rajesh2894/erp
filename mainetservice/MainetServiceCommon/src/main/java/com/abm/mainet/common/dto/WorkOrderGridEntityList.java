package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author alpesh.mehta
 *
 */
public class WorkOrderGridEntityList implements Serializable {

    private static final long serialVersionUID = -5120483766311816529L;

    /**
     * @return the applicationId
     */

    private Long applicationId;

    private String consumerName;

    private String plumberFName;

    private String plumberMName;

    private String plumberLName;
    private String woPrintFlg;
    
    private Long plumberId;

    /**
     * @return the woPrintFlg
     */
    public String getWoPrintFlg() {
        return woPrintFlg;
    }

    /**
     * @param woPrintFlg the woPrintFlg to set
     */
    public void setWoPrintFlg(final String woPrintFlg) {
        this.woPrintFlg = woPrintFlg;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the consumerName
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * @param consumerName the consumerName to set
     */
    public void setConsumerName(final String consumerName) {
        this.consumerName = consumerName;
    }

    /**
     * @return the plumberFName
     */
    public String getPlumberFName() {
        return plumberFName;
    }

    /**
     * @param plumberFName the plumberFName to set
     */
    public void setPlumberFName(final String plumberFName) {
        this.plumberFName = plumberFName;
    }

    /**
     * @return the plumberMName
     */
    public String getPlumberMName() {
        return plumberMName;
    }

    /**
     * @param plumberMName the plumberMName to set
     */
    public void setPlumberMName(final String plumberMName) {
        this.plumberMName = plumberMName;
    }

    /**
     * @return the plumberLName
     */
    public String getPlumberLName() {
        return plumberLName;
    }

    /**
     * @param plumberLName the plumberLName to set
     */
    public void setPlumberLName(final String plumberLName) {
        this.plumberLName = plumberLName;
    }

	public Long getPlumberId() {
		return plumberId;
	}

	public void setPlumberId(Long plumberId) {
		this.plumberId = plumberId;
	}

    
}
