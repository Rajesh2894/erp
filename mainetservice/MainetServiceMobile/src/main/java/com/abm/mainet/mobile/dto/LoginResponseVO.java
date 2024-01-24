package com.abm.mainet.mobile.dto;

import java.util.Date;

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
public class LoginResponseVO extends CommonAppResponseDTO {

    private static final long serialVersionUID = 3156960684490250840L;

    private String userName;
    private String title;
    private Long mobileNo;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String address;
    private Long addhaarNo;
    private String emailId;
    private Date dob;
    private String userStatus;
    private String userType;

    private Long deptId;
    private String deptDesc;
    private String deptCode;

    private String grCode;
    private String desgShortCode;
    private String locationName;

    /**
     * @return the userType
     */
    public String getUserType() {

        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(final String userType) {

        this.userType = userType;
    }

    /**
     * @return the userStatus
     */
    public String getUserStatus() {

        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(final String userStatus) {

        this.userStatus = userStatus;
    }

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
     * @return the title
     */
    public String getTitle() {

        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(final String title) {

        this.title = title;
    }

    /**
     * @return the mobileNo
     */
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
     * @return the firstName
     */
    public String getFirstName() {

        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {

        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(final String middleName) {

        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {

        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public String getGender() {

        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(final String gender) {

        this.gender = gender;
    }

    /**
     * @return the address
     */
    public String getAddress() {

        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final String address) {

        this.address = address;
    }

    /**
     * @return the addhaarNo
     */
    public Long getAddhaarNo() {

        return addhaarNo;
    }

    /**
     * @param addhaarNo the addhaarNo to set
     */
    public void setAddhaarNo(final Long addhaarNo) {

        this.addhaarNo = addhaarNo;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {

        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {

        this.emailId = emailId;
    }

    /**
     * @return the dob
     */
    public Date getDob() {

        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(final Date dob) {

        this.dob = dob;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "LoginResponseVO [userName=" + userName + ", title=" + title + ", mobileNo=" + mobileNo + ", firstName="
                + firstName + ", middleName="
                + middleName + ", lastName=" + lastName + ", gender=" + gender + ", address=" + address + ", addhaarNo="
                + addhaarNo + ", emailId="
                + emailId + ", dob=" + dob + "]";
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getGrCode() {
        return grCode;
    }

    public void setGrCode(String grCode) {
        this.grCode = grCode;
    }

	public String getDesgShortCode() {
		return desgShortCode;
	}

	public void setDesgShortCode(String desgShortCode) {
		this.desgShortCode = desgShortCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
