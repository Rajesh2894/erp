package com.abm.mainet.cms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author rajendra.bhujbal
 * @since 06 May 2014
 */
@Entity
@Table(name = "VW_EIP_ONLINE_SERVICES")
public class EIPOnlineServicesView implements Serializable {

    private static final long serialVersionUID = 548220231101223672L;

    @Column(name = "DEPTH", precision = 0, scale = 0, nullable = true)
    private Long depth;

    @Id
    @Column(name = "SMFID", precision = 12, scale = 0, nullable = false)
    private Long smfid;

    @Column(name = "SMFNAME", length = 1000, nullable = false)
    private String smfname;

    @Column(name = "SMFNAME_MAR", length = 1000, nullable = true)
    private String smfnameMar;

    @Column(name = "SMFACTION", length = 200, nullable = true)
    private String smfaction;

    @Column(name = "SMFDESCRIPTION", length = 1000, nullable = true)
    private String smfdescription;

    @Column(name = "SMFFLAG", length = 1, nullable = true)
    private String smfflag;

    @Column(name = "SMFCATEGORY", length = 1, nullable = true)
    private String smfcategory;

    @Column(name = "SMFSRNO", precision = 15, scale = 10, nullable = true)
    private Double smfsrno;

    @Column(name = "MNTPARENTID", precision = 12, scale = 0, nullable = true)
    private Long mntparentid;

    @Column(name = "MNTROOTID", precision = 12, scale = 0, nullable = true)
    private Long mntrootid;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptid;

    @Column(name = "MENUPRM1", length = 100, nullable = true)
    private String menuprm1;

    @Column(name = "MENUPRM2", length = 100, nullable = true)
    private String menuprm2;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    /**
     * @return the depth
     */
    public Long getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(final Long depth) {
        this.depth = depth;
    }

    /**
     * @return the smfid
     */
    public Long getSmfid() {
        return smfid;
    }

    /**
     * @param smfid the smfid to set
     */
    public void setSmfid(final Long smfid) {
        this.smfid = smfid;
    }

    /**
     * @return the smfname
     */
    public String getSmfname() {
        return smfname;
    }

    /**
     * @param smfname the smfname to set
     */
    public void setSmfname(final String smfname) {
        this.smfname = smfname;
    }

    /**
     * @return the smfnameMar
     */
    public String getSmfnameMar() {
        return smfnameMar;
    }

    /**
     * @param smfnameMar the smfnameMar to set
     */
    public void setSmfnameMar(final String smfnameMar) {
        this.smfnameMar = smfnameMar;
    }

    /**
     * @return the smfaction
     */
    public String getSmfaction() {
        return smfaction;
    }

    /**
     * @param smfaction the smfaction to set
     */
    public void setSmfaction(final String smfaction) {
        this.smfaction = smfaction;
    }

    /**
     * @return the smfdescription
     */
    public String getSmfdescription() {
        return smfdescription;
    }

    /**
     * @param smfdescription the smfdescription to set
     */
    public void setSmfdescription(final String smfdescription) {
        this.smfdescription = smfdescription;
    }

    /**
     * @return the smfflag
     */
    public String getSmfflag() {
        return smfflag;
    }

    /**
     * @param smfflag the smfflag to set
     */
    public void setSmfflag(final String smfflag) {
        this.smfflag = smfflag;
    }

    /**
     * @return the smfcategory
     */
    public String getSmfcategory() {
        return smfcategory;
    }

    /**
     * @param smfcategory the smfcategory to set
     */
    public void setSmfcategory(final String smfcategory) {
        this.smfcategory = smfcategory;
    }

    /**
     * @return the smfsrno
     */
    public Double getSmfsrno() {
        return smfsrno;
    }

    /**
     * @param smfsrno the smfsrno to set
     */
    public void setSmfsrno(final Double smfsrno) {
        this.smfsrno = smfsrno;
    }

    /**
     * @return the mntparentid
     */
    public Long getMntparentid() {
        return mntparentid;
    }

    /**
     * @param mntparentid the mntparentid to set
     */
    public void setMntparentid(final Long mntparentid) {
        this.mntparentid = mntparentid;
    }

    /**
     * @return the mntrootid
     */
    public Long getMntrootid() {
        return mntrootid;
    }

    /**
     * @param mntrootid the mntrootid to set
     */
    public void setMntrootid(final Long mntrootid) {
        this.mntrootid = mntrootid;
    }

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the menuprm1
     */
    public String getMenuprm1() {
        return menuprm1;
    }

    /**
     * @param menuprm1 the menuprm1 to set
     */
    public void setMenuprm1(final String menuprm1) {
        this.menuprm1 = menuprm1;
    }

    /**
     * @return the menuprm2
     */
    public String getMenuprm2() {
        return menuprm2;
    }

    /**
     * @param menuprm2 the menuprm2 to set
     */
    public void setMenuprm2(final String menuprm2) {
        this.menuprm2 = menuprm2;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

}