package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class DeviceRegistrationReqDTO extends CommonAppRequestDTO implements Serializable {

    private static final long serialVersionUID = -4823863321187647302L;

    private String appRegisterId;
    private String appDeviceId;
    private char activeFlag;
    private Date registerationDate;

    /**
     * @return the appRegisterId
     */
    @NotEmpty
    @NotNull
    public String getAppRegisterId() {
        return appRegisterId;
    }

    /**
     * @param appRegisterId the appRegisterId to set
     */
    public void setAppRegisterId(final String appRegisterId) {
        this.appRegisterId = appRegisterId;
    }

    /**
     * @return the appDeviceId
     */
    @NotEmpty
    @NotNull
    public String getAppDeviceId() {
        return appDeviceId;
    }

    /**
     * @param appDeviceId the appDeviceId to set
     */
    public void setAppDeviceId(final String appDeviceId) {
        this.appDeviceId = appDeviceId;
    }

    /**
     * @return the activeFlag
     */
    public char getActiveFlag() {
        return activeFlag;
    }

    /**
     * @param activeFlag the activeFlag to set
     */
    public void setActiveFlag(final char activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * @return the registerationDate
     */
    public Date getRegisterationDate() {
        return registerationDate;
    }

    /**
     * @param registerationDate the registerationDate to set
     */
    public void setRegisterationDate(final Date registerationDate) {
        this.registerationDate = registerationDate;
    }

}
