package com.abm.mainet.common.authentication.ldap;

public class UserProfile {
    private String uID;
    private String firstName;
    private String lastName;
    private String role;
    private String pwd;
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(final String uID) {
        this.uID = uID;
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
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(final String role) {
        this.role = role;
    }

}
