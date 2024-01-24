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
public class EmployeeRequestDTO extends CommonAppRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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
     * @return the firstName
     */
    @NotNull
    @NotEmpty
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
     * @return the lastName
     */
    @NotNull(message = "FirstName can not be Null or Empty")
    @NotEmpty(message = "FirstName can not be Null or Empty")
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
    @NotNull
    @NotEmpty
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "EmployeeRequestDTO [title=" + title + ", mobileNo=" + mobileNo + ", firstName=" + firstName
                + ", middleName=" + middleName + ", lastName=" + lastName + ", gender=" + gender + ", address=" + address
                + ", addhaarNo="
                + addhaarNo + ", emailId=" + emailId + ", dob=" + dob + "]";
    }

}
