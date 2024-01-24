package com.abm.mainet.cms.domain;

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

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pranit.Mhatre
 * @since 14 February, 2014
 */
@Entity
@Table(name = "TB_EIP_SUB_LINKS_MASTER_HIST")
public class SubLinkMasterHistory extends BaseEntity {

    private static final long serialVersionUID = -6301896745265683671L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_MAS_ID_H", nullable = false, precision = 12)
    private long hId;

    @Column(name = "SUB_LINK_MAS_ID")
    private long id;

    @Column(name = "LINK_ID")
    private long linksMaster;

    @Column(name = "HAS_SUB_LINK", nullable = false, length = 1, columnDefinition = "default 'N'")
    private String hasSubLink;

    @Column(name = "SUB_LINK_NAME_EN", nullable = false, length = 100)
    private String subLinkNameEn;

    @Column(name = "SUB_LINK_NAME_RG", nullable = false, length = 150)
    private String subLinkNameRg;

    @Column(name = "PAGE_URL", nullable = true, length = 200)
    private String pageUrl;

    @Column(name = "IS_CUSTOM", nullable = false, length = 1, columnDefinition = "default 'N'")
    private String isCustom;

    @Column(name = "ISDELETED", nullable = false, length = 1, columnDefinition = "default 'N'")
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
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
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = false)
    private String lgIpMacUpd;

    @Column(name = "CPD_SECION_TYPE", length = 10, nullable = true)
    private long sectionType0;

    @Column(name = "CPD_SECION_TYPE1", length = 10, nullable = true)
    private Long sectionType1;

    @Column(name = "H_STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "ISARCHIVE")
    private String isArchive;
    
    @Column(name = "REMARK", length = 1000, nullable = false)
    private String remark;
    
    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    public Long getSectionType1() {
        return sectionType1;
    }

    public void setSectionType1(final Long sectionType1) {
        this.sectionType1 = sectionType1;
    }

    public Long getSectionType2() {
        return sectionType2;
    }

    public void setSectionType2(final Long sectionType2) {
        this.sectionType2 = sectionType2;
    }

    public Long getSectionType3() {
        return sectionType3;
    }

    public void setSectionType3(final Long sectionType3) {
        this.sectionType3 = sectionType3;
    }

    public Long getSectionType4() {
        return sectionType4;
    }

    public void setSectionType4(final Long sectionType4) {
        this.sectionType4 = sectionType4;
    }

    @Column(name = "CPD_SECION_TYPE2", length = 10, nullable = true)
    private Long sectionType2;

    @Column(name = "CPD_SECION_TYPE3", length = 10, nullable = true)
    private Long sectionType3;

    @Column(name = "CPD_SECION_TYPE4", length = 10, nullable = true)
    private Long sectionType4;

    @Column(name = "CPD_IMG_LINK_TYPE", length = 10, nullable = true)
    private Long imageLinkType;

    @Column(name = "SUB_LINK_ORDER", length = 12, nullable = true)
    private Double subLinkOrder;

    @Column(name = "IS_LINK_MODIFY")
    private String isLinkModify;

    @Transient
    private List<SubLinkFieldMapping> sectiontypevalue = new ArrayList<>(0);

    public List<SubLinkFieldMapping> getSectiontypevalue() {
        return sectiontypevalue;
    }

    public void setSectiontypevalue(final List<SubLinkFieldMapping> sectiontypevalue) {
        this.sectiontypevalue = sectiontypevalue;
    }

    /**
     *
     */
    public SubLinkMasterHistory() {
        hasSubLink = "N";
        isCustom = "N";
        isDeleted = "N";
    }
  
    /**
     * @return the hasSubLink
     */
    public String getHasSubLink() {
        return hasSubLink;
    }

    /**
     * @param hasSubLink the hasSubLink to set
     */
    public void setHasSubLink(String hasSubLink) {
        if (hasSubLink == null) {
            hasSubLink = "N";
        }

        this.hasSubLink = hasSubLink;
    }

    /**
     * @return the subLinkNameEn
     */
    public String getSubLinkNameEn() {
        return subLinkNameEn;
    }

    /**
     * @param subLinkNameEn the subLinkNameEn to set
     */
    public void setSubLinkNameEn(final String subLinkNameEn) {
        this.subLinkNameEn = subLinkNameEn;
    }

    /**
     * @return the subLinkNameRg
     */
    public String getSubLinkNameRg() {
        return subLinkNameRg;
    }

    /**
     * @param subLinkNameRg the subLinkNameRg to set
     */
    public void setSubLinkNameRg(final String subLinkNameRg) {
        this.subLinkNameRg = subLinkNameRg;
    }

    /**
     * @return the pageUrl
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * @param pageUrl the pageUrl to set
     */
    public void setPageUrl(final String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * @return the isCustom
     */
    public String getIsCustom() {
        return isCustom;
    }

    /**
     * @param isCustom the isCustom to set
     */
    public void setIsCustom(String isCustom) {
        if (isCustom == null) {
            isCustom = "N";
        }
        this.isCustom = isCustom;
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
    public void setIsDeleted(String isDeleted) {
        if (isDeleted == null) {
            isDeleted = "N";
        }
        this.isDeleted = isDeleted;
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

    /**
     * @return the sectionType
     */
    public long getSectionType0() {
        return sectionType0;
    }

    /**
     * @param sectionType the sectionType to set
     */
    public void setSectionType0(final long sectionType0) {
        this.sectionType0 = sectionType0;
    }

    /**
     * @return the imageLinkType
     */
    public Long getImageLinkType() {
        return imageLinkType;
    }

    /**
     * @param imageLinkType the imageLinkType to set
     */
    public void setImageLinkType(final long imageLinkType) {
        this.imageLinkType = imageLinkType;
    }

    /**
     * @return the subLinkOrder
     */
    public Double getSubLinkOrder() {
        return subLinkOrder;
    }

    /**
     * @param subLinkOrder the subLinkOrder to set
     */
    public void setSubLinkOrder(final Double subLinkOrder) {
        this.subLinkOrder = subLinkOrder;
    }

    public String getIsLinkModify() {
        return isLinkModify;
    }

    public void setIsLinkModify(final String isLinkModify) {
        this.isLinkModify = isLinkModify;
    }

    @Override
    public int getLangId() {

        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

    public void setImageLinkType(Long imageLinkType) {
        this.imageLinkType = imageLinkType;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINKS_MASTER_HIST", "SUB_LINK_MAS_ID_H" };
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long gethId() {
        return hId;
    }

    public void sethId(long hId) {
        this.hId = hId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLinksMaster() {
        return linksMaster;
    }

    public void setLinksMaster(long linksMaster) {
        this.linksMaster = linksMaster;
    }

   	public String getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(String isArchive) {
		this.isArchive = isArchive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
