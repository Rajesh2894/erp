package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EIP_ABOUTUS_HIST")
public class EIPAboutUsHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1425291402843447532L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ABOUTUS_ID_H", nullable = false)
    private long aboutUsHistId;

    @Column(name = "ABOUTUS_ID", nullable = false)
    private long aboutUsId;

    @Column(name = "DESCRIPTION_EN", nullable = true, length = 3000)
    private String descriptionEn;

    @Column(name = "DESCRIPTION_EN1", nullable = true, length = 3000)
    private String descriptionEn1;

    @Column(name = "DESCRIPTION_REG", nullable = true, length = 4000)
    private String descriptionReg;

    @Column(name = "DESCRIPTION_REG1", nullable = true, length = 4000)
    private String descriptionReg1;
    
    @Column(name= "REMARK" , length=1000 , nullable = true)
    private String remark;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", nullable = false, precision = 12, scale = 0)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true, length = 100)
    private String lgIpMacUpd;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "H_STATUS", length = 1)
    private String status;

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    @Override
    public long getId() {
        return aboutUsId;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the aboutUsId
     */
    public long getAboutUsId() {
        return aboutUsId;
    }

    /**
     * @param aboutUsId the aboutUsId to set
     */
    public void setAboutUsId(final long aboutUsId) {
        this.aboutUsId = aboutUsId;
    }

    /**
     * @return the descriptionEn
     */
    public String getDescriptionEn() {
        return descriptionEn;
    }

    /**
     * @param descriptionEn the descriptionEn to set
     */
    public void setDescriptionEn(final String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    /**
     * @return the descriptionReg
     */
    public String getDescriptionReg() {
        return descriptionReg;
    }

    /**
     * @param descriptionReg the descriptionReg to set
     */
    public void setDescriptionReg(final String descriptionReg) {
        this.descriptionReg = descriptionReg;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EIPAboutUs [aboutUsId=");
        builder.append(aboutUsId);
        builder.append(", descriptionEn=");
        builder.append(descriptionEn);
        builder.append(", descriptionReg=");
        builder.append(descriptionReg);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", orgId=");
        builder.append(", userId=");
        builder.append(", langId=");
        builder.append(langId);
        builder.append(", lmodDate=");
        builder.append(lmodDate);
        builder.append(", updatedBy=");
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the descriptionEn1
     */
    public String getDescriptionEn1() {
        return descriptionEn1;
    }

    /**
     * @param descriptionEn1 the descriptionEn1 to set
     */
    public void setDescriptionEn1(final String descriptionEn1) {
        this.descriptionEn1 = descriptionEn1;
    }

    /**
     * @return the descriptionReg1
     */
    public String getDescriptionReg1() {
        return descriptionReg1;
    }

    /**
     * @param descriptionReg1 the descriptionReg1 to set
     */
    public void setDescriptionReg1(final String descriptionReg1) {
        this.descriptionReg1 = descriptionReg1;
    }

    public long getAboutUsHistId() {
        return aboutUsHistId;
    }

    public void setAboutUsHistId(long aboutUsHistId) {
        this.aboutUsHistId = aboutUsHistId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_ABOUTUS_HIST", "ABOUTUS_ID_H" };
    }
    
    /**
     * @return the remark
     */
	public String getRemark() {
		return remark;
	}

	 /**
     * @param remark the remark to set
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
