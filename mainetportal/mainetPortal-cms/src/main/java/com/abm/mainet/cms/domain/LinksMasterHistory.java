package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 *
 */
@Entity
@Table(name = "TB_EIP_LINKS_MASTER_HIST")
public class LinksMasterHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LINK_ID_H", nullable = false, precision = 22, scale = 0)
    private long linkIHistd;

    @Column(name = "LINK_ID", nullable = false, precision = 22, scale = 0)
    private long linkId;

    @Column(name = "LINK_PATH", nullable = true, length = 250)
    private String linkPath;

    @Column(name = "LINK_IMAGE_NAME", nullable = true, length = 100)
    private String linkImageName;

    @Column(name = "LINK_ORDER", nullable = true, precision = 5, scale = 5)
    private Double linkOrder;

    @Column(name = "LINK_TITLE_EN", nullable = true, length = 500)
    private String linkTitleEg;

    @Column(name = "LINK_TITLE_REG", nullable = true, length = 1000)
    private String linkTitleReg;

    @Column(name = "CPD_SECTION", nullable = true)
    private Long cpdSection;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    @ForeignKey(name = "FK_EIP_PM_ORG_ID")
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    @ForeignKey(name = "FK_CHG_UPD_EMPID")
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true, length = 100)
    private String lgIpMacUpd;

    @Column(name = "IS_LINK_MODIFY")
    private String isLinkModify;

    @Transient
    private String exLink;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "H_STATUS", length = 1)
    private String status;
    
    @Column(name = "REMARK", length = 1000, nullable = false)
	private String remark;

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    /**
     * @return the linkId
     */
    public long getLinkId() {
        return linkId;
    }

    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(final long linkId) {
        this.linkId = linkId;
    }

    /**
     * @return the linkPath
     */
    public String getLinkPath() {
        return linkPath;
    }

    /**
     * @param linkPath the linkPath to set
     */
    public void setLinkPath(final String linkPath) {
        this.linkPath = linkPath;
    }

    /**
     * @return the linkImageName
     */
    public String getLinkImageName() {
        return linkImageName;
    }

    /**
     * @param linkImageName the linkImageName to set
     */
    public void setLinkImageName(final String linkImageName) {
        this.linkImageName = linkImageName;
    }

    /**
     * @return the linkOrder
     */
    public Double getLinkOrder() {
        return linkOrder;
    }

    /**
     * @param linkOrder the linkOrder to set
     */
    public void setLinkOrder(final Double linkOrder) {
        this.linkOrder = linkOrder;
    }

    /**
     * @return the linkTitleEg
     */
    public String getLinkTitleEg() {
        return linkTitleEg;
    }

    /**
     * @param linkTitleEg the linkTitleEg to set
     */
    public void setLinkTitleEg(final String linkTitleEg) {
        this.linkTitleEg = linkTitleEg;
    }

    /**
     * @return the linkTitleReg
     */
    public String getLinkTitleReg() {
        return linkTitleReg;
    }

    /**
     * @param linkTitleReg the linkTitleReg to set
     */
    public void setLinkTitleReg(final String linkTitleReg) {
        this.linkTitleReg = linkTitleReg;
    }

    /**
     * @return the cpdSection
     */
    public Long getCpdSection() {
        return cpdSection;
    }

    /**
     * @param cpdSection the cpdSection to set
     */
    public void setCpdSection(final Long cpdSection) {
        this.cpdSection = cpdSection;
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

    @Override
    public long getId() {

        return getLinkId();
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

    public String getIsLinkModify() {
        return isLinkModify;
    }

    public void setIsLinkModify(final String isLinkModify) {
        this.isLinkModify = isLinkModify;
    }

    public String getExLink() {
        return exLink;
    }

    public void setExLink(final String exLink) {
        this.exLink = exLink;
    }

    @Override
    public int getLangId() {

        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

    @Transient
    List<SubLinkMaster> subLinkMasters = new ArrayList<>();

    public List<SubLinkMaster> getSubLinkMasters() {
        return subLinkMasters;
    }

    public void setSubLinkMasters(final List<SubLinkMaster> subLinkMasters) {
        this.subLinkMasters = subLinkMasters;
    }

    public long getLinkIHistd() {
        return linkIHistd;
    }

    public void setLinkIHistd(long linkIHistd) {
        this.linkIHistd = linkIHistd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_LINKS_MASTER_HIST", "LINK_ID_H" };
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
