/**
 *
 */
package com.abm.mainet.cfc.scrutiny.dto;

import java.io.Serializable;

/**
 * @author umashanker.kanaujiya
 *
 */
public class ScrutinyLableValueDTO implements Serializable {

    private static final long serialVersionUID = -3643984209623287035L;

    private Long lableId;
    private Long applicationId;
    private String lableValue;
    private Long userId;
    private Long orgId;
    private Long level;
    private Long langId;
    private String resolutionComments;

    /**
     * @return the lableId
     */
    public Long getLableId() {
        return lableId;
    }

    /**
     * @param lableId the lableId to set
     */
    public void setLableId(final Long lableId) {
        this.lableId = lableId;
    }

    /**
     * @return the applicationId
     */
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
     * @return the lableValue
     */
    public String getLableValue() {
        return lableValue;
    }

    /**
     * @param lableValue the lableValue to set
     */
    public void setLableValue(final String lableValue) {
        this.lableValue = lableValue;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

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
     * @return the level
     */
    public Long getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(final Long level) {
        this.level = level;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

}
