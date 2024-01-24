package com.abm.mainet.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonFormat
public class CommonAppRequestDTO implements Serializable {
    private static final long serialVersionUID = 7198670718620499974L;

    private Long orgId;
    private Long langId;
    private Long userId;
    private Long serviceId;

    /**
     * @return the orgId
     */
    @NotNull
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
     * @return the langId
     */
    @NotNull
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */

    public void setLangId(final Long langId) {
        this.langId = langId;
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

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

    
}
