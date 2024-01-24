package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vivek.Kumar
 * @since 29-Feb-2016
 */
public class DesignationDTO implements Serializable {

    private static final long serialVersionUID = 3359684921623512860L;

    private long dsgid;
    private OrganisationDTO organisation;
    private String dsgname;
    private String dsgshortname;
    private String dsgdescription;

    private Long departmentLocation;

    private String action;
    private String isdeleted;
    private String dsgoname;

    private Date ondate;
    private String ontime;

    public DesignationDTO() {
    }

    public long getDsgid() {
        return dsgid;
    }

    public void setDsgid(final long dsgid) {
        this.dsgid = dsgid;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    public String getDsgname() {
        return dsgname;
    }

    public void setDsgname(final String dsgname) {
        this.dsgname = dsgname;
    }

    public String getDsgshortname() {
        return dsgshortname;
    }

    public void setDsgshortname(final String dsgshortname) {
        this.dsgshortname = dsgshortname;
    }

    public String getDsgdescription() {
        return dsgdescription;
    }

    public void setDsgdescription(final String dsgdescription) {
        this.dsgdescription = dsgdescription;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getDsgoname() {
        return dsgoname;
    }

    public void setDsgoname(final String dsgoname) {
        this.dsgoname = dsgoname;
    }

    public Date getOndate() {
        return ondate;
    }

    public void setOndate(final Date ondate) {
        this.ondate = ondate;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(final String ontime) {
        this.ontime = ontime;
    }

    /*
     * @OneToOne(cascade = javax.persistence.CascadeType.ALL)
     * @JoinColumn(referencedColumnName = "DEPID", name = "DEPID")
     * @ForeignKey(name = "FK_DEPID_DESIG")
     */
    public Long getDepartmentLocation() {
        return departmentLocation;
    }

    /**
     * @param departmentLocation the departmentLocation to set
     */
    public void setDepartmentLocation(final Long departmentLocation) {
        this.departmentLocation = departmentLocation;
    }

}
