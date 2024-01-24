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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pranit.Mhatre
 * @since 14 February, 2014
 */
@Entity
@Table(name = "TB_EIP_SUB_LINKS_MASTER")
public class SubLinkMaster extends BaseEntity {

    private static final long serialVersionUID = -6301896745265683671L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_MAS_ID", nullable = false, precision = 12)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LINK_ID", referencedColumnName = "LINK_ID", nullable = false)
    @ForeignKey(name = "FK_LINK_ID")
    private LinksMaster linksMaster;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUB_LINK_PAR_ID", referencedColumnName = "SUB_LINK_MAS_ID", nullable = true)
    @ForeignKey(name = "FK_SUB_LINK_PAR_ID")
    private SubLinkMaster subLinkMaster;

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subLinkMaster", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @Where(clause = "IS_USED = 'Y'")
    private List<SubLinkFieldMapping> subLinkFieldMappings = new ArrayList<>(0);

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subLinkMaster", cascade = CascadeType.ALL)
    @OrderBy("subLinkFieldDtlOrd asc")
    @Where(clause = "ISDELETED = 'N'")
    private List<SubLinkFieldDetails> subLinkFieldDetails = new ArrayList<>(0);

    @Column(name = "CPD_SECION_TYPE", length = 10, nullable = true)
    private Long sectionType0;

    @Column(name = "CPD_SECION_TYPE1", length = 10, nullable = true)
    private Long sectionType1;

    @Column(name = "CPD_SECION_TYPE2", length = 10, nullable = true)
    private Long sectionType2;

    @Column(name = "CPD_SECION_TYPE3", length = 10, nullable = true)
    private Long sectionType3;

    @Column(name = "CPD_SECION_TYPE4", length = 10, nullable = true)
    private Long sectionType4;

    @Column(name = "CPD_IMG_LINK_TYPE", length = 10, nullable = true)
    private Long imageLinkType;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "SUB_LINK_ORDER", length = 6, nullable = true)
    private Double subLinkOrder;

    @Column(name = "IS_LINK_MODIFY")
    private String isLinkModify;
    
    @Column(name = "ISARCHIVE")
    private String isArchive;
    
    @Column(name = "REMARK", length = 1000, nullable = true)
    private String remark;
    
    @Transient
    private String custOrderMenuEng;
    
    @Transient
    private String custOrderMenuReg;

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
    public SubLinkMaster() {
        hasSubLink = "N";
        isCustom = "N";
        isDeleted = "N";
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the linksMaster
     */
    public LinksMaster getLinksMaster() {
        return linksMaster;
    }

    /**
     * @param linksMaster the linksMaster to set
     */
    public void setLinksMaster(final LinksMaster linksMaster) {
        this.linksMaster = linksMaster;
    }

    /**
     * @return the subLinkMaster
     */
    public SubLinkMaster getSubLinkMaster() {
        return subLinkMaster;
    }

    /**
     * @param subLinkMaster the subLinkMaster to set
     */
    public void setSubLinkMaster(final SubLinkMaster subLinkMaster) {
        this.subLinkMaster = subLinkMaster;
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

    /**
     * @return the subLinkFieldMappings
     */
    public List<SubLinkFieldMapping> getSubLinkFieldMappings() {
        return subLinkFieldMappings;
    }

    /**
     * @param subLinkFieldMappings the subLinkFieldMappings to set
     */
    public void setSubLinkFieldMappings(final List<SubLinkFieldMapping> subLinkFieldMappings) {
        this.subLinkFieldMappings = subLinkFieldMappings;
    }

    /**
     * @return the subLinkFieldDetails
     */
    public List<SubLinkFieldDetails> getSubLinkFieldDetails() {
        return subLinkFieldDetails;
    }

    /**
     * @param subLinkFieldDetails the subLinkFieldDetails to set
     */
    public void setSubLinkFieldDetails(final List<SubLinkFieldDetails> subLinkFieldDetails) {
        this.subLinkFieldDetails = subLinkFieldDetails;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((hasSubLink == null) ? 0 : hasSubLink.hashCode());
        result = (prime * result) + (int) (id ^ (id >>> 32));
        result = (prime * result) + ((isCustom == null) ? 0 : isCustom.hashCode());
        result = (prime * result) + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = (prime * result) + ((lgIpMac == null) ? 0 : lgIpMac.hashCode());
        result = (prime * result) + ((lgIpMacUpd == null) ? 0 : lgIpMacUpd.hashCode());
        result = (prime * result) + ((linksMaster == null) ? 0 : linksMaster.hashCode());
        result = (prime * result) + ((lmodDate == null) ? 0 : lmodDate.hashCode());
        result = (prime * result) + ((orgId == null) ? 0 : orgId.hashCode());
        result = (prime * result) + ((pageUrl == null) ? 0 : pageUrl.hashCode());
        result = (prime * result) + ((subLinkFieldDetails == null) ? 0 : subLinkFieldDetails.hashCode());
        result = (prime * result) + ((subLinkFieldMappings == null) ? 0 : subLinkFieldMappings.hashCode());
        result = (prime * result) + ((subLinkMaster == null) ? 0 : subLinkMaster.hashCode());
        result = (prime * result) + ((subLinkNameEn == null) ? 0 : subLinkNameEn.hashCode());
        result = (prime * result) + ((subLinkNameRg == null) ? 0 : subLinkNameRg.hashCode());
        result = (prime * result) + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = (prime * result) + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        result = (prime * result) + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubLinkMaster other = (SubLinkMaster) obj;
        if (hasSubLink == null) {
            if (other.hasSubLink != null) {
                return false;
            }
        } else if (!hasSubLink.equals(other.hasSubLink)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (isCustom == null) {
            if (other.isCustom != null) {
                return false;
            }
        } else if (!isCustom.equals(other.isCustom)) {
            return false;
        }
        if (isDeleted == null) {
            if (other.isDeleted != null) {
                return false;
            }
        } else if (!isDeleted.equals(other.isDeleted)) {
            return false;
        }
        if (lgIpMac == null) {
            if (other.lgIpMac != null) {
                return false;
            }
        } else if (!lgIpMac.equals(other.lgIpMac)) {
            return false;
        }
        if (lgIpMacUpd == null) {
            if (other.lgIpMacUpd != null) {
                return false;
            }
        } else if (!lgIpMacUpd.equals(other.lgIpMacUpd)) {
            return false;
        }
        if (linksMaster == null) {
            if (other.linksMaster != null) {
                return false;
            }
        } else if (!linksMaster.equals(other.linksMaster)) {
            return false;
        }
        if (lmodDate == null) {
            if (other.lmodDate != null) {
                return false;
            }
        } else if (!lmodDate.equals(other.lmodDate)) {
            return false;
        }
        if (orgId == null) {
            if (other.orgId != null) {
                return false;
            }
        } else if (!orgId.equals(other.orgId)) {
            return false;
        }
        if (pageUrl == null) {
            if (other.pageUrl != null) {
                return false;
            }
        } else if (!pageUrl.equals(other.pageUrl)) {
            return false;
        }
        if (subLinkFieldDetails == null) {
            if (other.subLinkFieldDetails != null) {
                return false;
            }
        } else if (!subLinkFieldDetails.equals(other.subLinkFieldDetails)) {
            return false;
        }
        if (subLinkFieldMappings == null) {
            if (other.subLinkFieldMappings != null) {
                return false;
            }
        } else if (!subLinkFieldMappings.equals(other.subLinkFieldMappings)) {
            return false;
        }
        if (subLinkMaster == null) {
            if (other.subLinkMaster != null) {
                return false;
            }
        } else if (!subLinkMaster.equals(other.subLinkMaster)) {
            return false;
        }
        if (subLinkNameEn == null) {
            if (other.subLinkNameEn != null) {
                return false;
            }
        } else if (!subLinkNameEn.equals(other.subLinkNameEn)) {
            return false;
        }
        if (subLinkNameRg == null) {
            if (other.subLinkNameRg != null) {
                return false;
            }
        } else if (!subLinkNameRg.equals(other.subLinkNameRg)) {
            return false;
        }
        if (updatedBy == null) {
            if (other.updatedBy != null) {
                return false;
            }
        } else if (!updatedBy.equals(other.updatedBy)) {
            return false;
        }
        if (updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        } else if (!updatedDate.equals(other.updatedDate)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (linksMaster != null) {
            builder.append("SubLinkMaster [id=");
            builder.append(id);
            builder.append(", linksMaster=");
            builder.append(linksMaster.getLinkTitleEg() + " : " + linksMaster.getLinkTitleReg());
            builder.append(", hasSubLink=");
            builder.append(hasSubLink);
            builder.append(", subLinkNameEn=");
            builder.append(subLinkNameEn);
            builder.append(", subLinkNameRg=");
            builder.append(subLinkNameRg);
            builder.append(", pageUrl=");
            builder.append(pageUrl);
            builder.append(", isCustom=");
            builder.append(isCustom);
            builder.append(", isDeleted=");
            builder.append(isDeleted);
            builder.append(", orgId=");
            builder.append(orgId);
            builder.append(", userId=");
            builder.append(userId);
            builder.append(", lmodDate=");
            builder.append(lmodDate);
            builder.append(", updatedBy=");
            builder.append(updatedBy);
            builder.append(", updatedDate=");
            builder.append(updatedDate);
            builder.append(", lgIpMac=");
            builder.append(lgIpMac);
            builder.append(", lgIpMacUpd=");
            builder.append(lgIpMacUpd);
            builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
            return builder.toString();
        }
        return MainetConstants.BLANK;
    }

    @JsonIgnore
    public LookUp getModuleName() {
        final LookUp lookUp = new LookUp();
        lookUp.setLookUpId(getLinksMaster().getId());
        lookUp.setDescLangFirst(getLinksMaster().getLinkTitleEg());
        lookUp.setDescLangSecond(getLinksMaster().getLinkTitleReg());
        return lookUp;
    }

    @JsonIgnore
    public LookUp getFunctionName() {
        final LookUp lookUp = new LookUp();
        if (getSubLinkMaster() != null) {
            lookUp.setLookUpId(getSubLinkMaster().getId());
            lookUp.setDescLangFirst(getSubLinkMaster().getSubLinkNameEn());
            lookUp.setDescLangSecond(getSubLinkMaster().getSubLinkNameEn());
        }
        return lookUp;
    }

    /**
     * @return the sectionType
     */
    public Long getSectionType0() {
        return sectionType0;
    }

    /**
     * @param sectionType the sectionType to set
     */
    public void setSectionType0(final Long sectionType0) {
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

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINKS_MASTER", "SUB_LINK_MAS_ID" };
    }

    @Transient
    private long editRowId;

    public long getEditRowId() {
        return editRowId;
    }

    public void setEditRowId(long editRowId) {
        this.editRowId = editRowId;
    }

    @Transient
    private List<SubLinkFieldDetailsHistory> detailsHistories = new ArrayList<>(0);

    public List<SubLinkFieldDetailsHistory> getDetailsHistories() {
        return detailsHistories;
    }

    public void setDetailsHistories(List<SubLinkFieldDetailsHistory> detailsHistories) {
        this.detailsHistories = detailsHistories;
    }

    @Transient
    private String checkerFlag1;

    public String getCheckerFlag1() {
        return checkerFlag1;
    }

    public void setCheckerFlag1(String checkerFlag1) {
        this.checkerFlag1 = checkerFlag1;
    }

    public void setImageLinkType(Long imageLinkType) {
        this.imageLinkType = imageLinkType;
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
	 public String getCustOrderMenuEng() {
			return custOrderMenuEng;
		}

		public void setCustOrderMenuEng(String custOrderMenuEng) {
			this.custOrderMenuEng = custOrderMenuEng;
		}

		public String getCustOrderMenuReg() {
			return custOrderMenuReg;
		}

		public void setCustOrderMenuReg(String custOrderMenuReg) {
			this.custOrderMenuReg = custOrderMenuReg;
		}
}
