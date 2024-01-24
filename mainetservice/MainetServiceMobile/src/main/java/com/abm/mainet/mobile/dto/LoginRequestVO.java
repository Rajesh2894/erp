package com.abm.mainet.mobile.dto;

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
public class LoginRequestVO extends CommonAppRequestDTO {

    private static final long serialVersionUID = 3515266333772560644L;
    private String userName;
    private String passWord;
    private String loginType;

    /**
     * @return the userName
     */
    @NotEmpty
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * @return the passWord
     */
    @NotEmpty
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord the passWord to set
     */
    public void setPassWord(final String passWord) {
        this.passWord = passWord;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "LoginRequestVO [userName=" + userName + ", passWord=" + passWord + ", loginType=" + loginType + "]";
    }

}
