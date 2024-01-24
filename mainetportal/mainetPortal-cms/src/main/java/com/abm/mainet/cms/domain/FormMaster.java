package com.abm.mainet.cms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EIP_FORMS_MASTER")
public class FormMaster extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -3937375515816504428L;

    @Id

    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FM_ID", precision = 12, scale = 0, nullable = false)
    private long fmId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPT_ID", nullable = false)
    private Department department;

    @Column(name = "FORM_TITLE_EN", length = 250, nullable = true)
    private String formTitleEn;

    @Column(name = "FORM_TITLE_REG", length = 500, nullable = true)
    private String formTitleReg;

    @Column(name = "PROFILE_IMG_PATH", length = 1000, nullable = true)
    private String profileImgPath;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    /**
     * @return the fmId
     */
    public long getFmId() {
        return fmId;
    }

    /**
     * @param fmId the fmId to set
     */
    public void setFmId(final long fmId) {
        this.fmId = fmId;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final Department department) {
        this.department = department;
    }

    /**
     * @return the formTitleEn
     */
    public String getFormTitleEn() {
        return formTitleEn;
    }

    /**
     * @param formTitleEn the formTitleEn to set
     */
    public void setFormTitleEn(final String formTitleEn) {
        this.formTitleEn = formTitleEn;
    }

    /**
     * @return the formTitleReg
     */
    public String getFormTitleReg() {
        return formTitleReg;
    }

    /**
     * @param formTitleReg the formTitleReg to set
     */
    public void setFormTitleReg(final String formTitleReg) {
        this.formTitleReg = formTitleReg;
    }

    /**
     * @return the profileImgPath
     */
    public String getProfileImgPath() {
        return profileImgPath;
    }

    /**
     * @param profileImgPath the profileImgPath to set
     */
    public void setProfileImgPath(final String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    /**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {

        return getFmId();
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_FORMS_MASTER", "FM_ID" };
    }

}