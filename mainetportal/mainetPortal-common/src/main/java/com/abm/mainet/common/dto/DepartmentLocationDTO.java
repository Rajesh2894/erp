package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Vivek.Kumar
 * @since 29-Feb-2016
 */
public class DepartmentLocationDTO implements Serializable {

    private static final long serialVersionUID = -76006370264175486L;
    private long departmentId;										// DEPID;
    private String departmentName;									// DEPNAME
    private String departmentDesc;									// DEPDESCRIPTION
    private String action;												// ACTION
    private String isDeleted;											// ISDETELED
    private String serverName;										// DEPSERVERNAME
    private String address1;											// DEPADDRESS1
    private String address2;											// DEPADDRESS2
    private String city;												// DEPCITY
    private String depdsnname;										// DEPDSNNAME
    private String depdatabase;										// DEPDATABASE
    private String deponame;											// DEPONAME
    private Date onDate;												// ONDATE
    private String onTime;												// ONTIME

    private OrganisationDTO organisation;

    public DepartmentLocationDTO() {

    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(final String serverName) {
        this.serverName = serverName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getDepdsnname() {
        return depdsnname;
    }

    public void setDepdsnname(final String depdsnname) {
        this.depdsnname = depdsnname;
    }

    public String getDepdatabase() {
        return depdatabase;
    }

    public void setDepdatabase(final String depdatabase) {
        this.depdatabase = depdatabase;
    }

    public String getDeponame() {
        return deponame;
    }

    public void setDeponame(final String deponame) {
        this.deponame = deponame;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(final Date onDate) {
        this.onDate = onDate;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(final String onTime) {
        this.onTime = onTime;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    /**
     * @param organisation the organisation to set
     */
    public void setOrganisation(final OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DepartmentLocation [departmentId=");
        builder.append(departmentId);
        builder.append(", departmentName=");
        builder.append(departmentName);
        builder.append(", departmentDesc=");
        builder.append(departmentDesc);
        builder.append(", action=");
        builder.append(action);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", serverName=");
        builder.append(serverName);
        builder.append(", address1=");
        builder.append(address1);
        builder.append(", address2=");
        builder.append(address2);
        builder.append(", city=");
        builder.append(city);
        builder.append(", depdsnname=");
        builder.append(depdsnname);
        builder.append(", depdatabase=");
        builder.append(depdatabase);
        builder.append(", deponame=");
        builder.append(deponame);
        builder.append(", onDate=");
        builder.append(onDate);
        builder.append(", onTime=");
        builder.append(onTime);
        builder.append(", organisation=");
        builder.append(organisation);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

}
