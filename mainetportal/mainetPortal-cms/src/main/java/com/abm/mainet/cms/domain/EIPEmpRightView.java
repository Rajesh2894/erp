package com.abm.mainet.cms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rajendra.bhujbal
 * @since 06 May 2014
 */

@Entity
@Table(name = "vw_EIP_emp_right")
public class EIPEmpRightView implements Serializable {
    private static final long serialVersionUID = 6328065514918126037L;

    @Column(name = "EMPID", nullable = false, updatable = false)
    private Long empid;

    @Column(name = "DEPTH", nullable = true)
    private Long depth;

    @Id
    @Column(name = "SMFID", nullable = false)
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

    @Column(name = "SMFSRNO", nullable = true)
    private Double smfsrno;

    @Column(name = "MNTPARENTID", nullable = true)
    private Long mntparentid;

    @Column(name = "MNTROOTID", nullable = true)
    private Long mntrootid;

    @Column(name = "SM_SERVICE_ID", nullable = true)
    private Long smServiceId;

    @Column(name = "DP_DEPTID", nullable = true)
    private Long dpDeptid;

    @Column(name = "MENUPRM1", length = 100, nullable = true)
    private String menuprm1;

    @Column(name = "MENUPRM2", length = 100, nullable = true)
    private String menuprm2;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private long orgId;

    public Long getDepth() {
        return depth;
    }

    public void setDepth(final Long depth) {
        this.depth = depth;
    }

    public Long getSmfid() {
        return smfid;
    }

    public void setSmfid(final Long smfid) {
        this.smfid = smfid;
    }

    public String getSmfname() {
        return smfname;
    }

    public void setSmfname(final String smfname) {
        this.smfname = smfname;
    }

    public String getSmfnameMar() {
        return smfnameMar;
    }

    public void setSmfnameMar(final String smfnameMar) {
        this.smfnameMar = smfnameMar;
    }

    public String getSmfaction() {
        return smfaction;
    }

    public void setSmfaction(final String smfaction) {
        this.smfaction = smfaction;
    }

    public String getSmfdescription() {
        return smfdescription;
    }

    public void setSmfdescription(final String smfdescription) {
        this.smfdescription = smfdescription;
    }

    public String getSmfflag() {
        return smfflag;
    }

    public void setSmfflag(final String smfflag) {
        this.smfflag = smfflag;
    }

    public String getSmfcategory() {
        return smfcategory;
    }

    public void setSmfcategory(final String smfcategory) {
        this.smfcategory = smfcategory;
    }

    public Double getSmfsrno() {
        return smfsrno;
    }

    public void setSmfsrno(final Double smfsrno) {
        this.smfsrno = smfsrno;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(final Long empid) {
        this.empid = empid;
    }

    public Long getMntparentid() {
        return mntparentid;
    }

    public void setMntparentid(final Long mntparentid) {
        this.mntparentid = mntparentid;
    }

    public Long getMntrootid() {
        return mntrootid;
    }

    public void setMntrootid(final Long mntrootid) {
        this.mntrootid = mntrootid;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getMenuprm1() {
        return menuprm1;
    }

    public void setMenuprm1(final String menuprm1) {
        this.menuprm1 = menuprm1;
    }

    public String getMenuprm2() {
        return menuprm2;
    }

    public void setMenuprm2(final String menuprm2) {
        this.menuprm2 = menuprm2;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }
}
