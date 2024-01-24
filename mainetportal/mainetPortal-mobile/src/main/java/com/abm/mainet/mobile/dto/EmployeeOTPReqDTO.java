package com.abm.mainet.mobile.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class EmployeeOTPReqDTO extends CommonAppRequestDTO implements Serializable {

    private static final long serialVersionUID = -2528785108407162086L;

    private Long mobileNo;
    private String otpPass;
    private String loginType;
    private String isregistered;

    /**
     * @return the mobileNo
     */
    @NotNull
    public Long getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(final Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the otpPass
     */

    public String getOtpPass() {
        return otpPass;
    }

    /**
     * @param otpPass the otpPass to set
     */
    public void setOtpPass(final String otpPass) {
        this.otpPass = otpPass;
    }

    /**
     * @return the loginType
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * @param loginType the loginType to set
     */
    public void setLoginType(final String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {

        return "EmployeeOTPReqDTO [mobileNo=" + mobileNo + ", otpPass=" + otpPass + ", loginType=" + loginType + "]";
    }

	public String getIsregistered() {
		return isregistered;
	}

	public void setIsregistered(String isregistered) {
		this.isregistered = isregistered;
	}

}
