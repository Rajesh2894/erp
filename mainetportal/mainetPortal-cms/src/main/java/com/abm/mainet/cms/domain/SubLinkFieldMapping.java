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
 * @author Pranit.Mhatre
 * @since 14 February, 2014
 */
@Entity
@Table(name = "TB_EIP_SUB_LINK_FIELD_MAP")
public class SubLinkFieldMapping extends BaseEntity {

    private static final long serialVersionUID = 7965722206083111441L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_FIELD_ID", nullable = false, precision = 12, scale = 0)
    private long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_LINK_MAS_ID", referencedColumnName = "SUB_LINK_MAS_ID")
    @ForeignKey(name = "SUB_LINK_MAS_ID")
    private SubLinkMaster subLinkMaster;

    @Column(name = "FIELD_NAME_EN", nullable = false, length = 100)
    private String fieldNameEn;

    @Column(name = "FIELD_NAME_RG", nullable = false, length = 6)
    private String fieldNameRg;

    @Column(name = "FIELD_TYPE_ID", precision = 4, nullable = false)
    private int fieldType;

    @Column(name = "FIELD_SEQ", precision = 4, nullable = false)
    private int fieldSequence;

    @Column(name = "FIELD_NAME_MAP", nullable = false, length = 6)
    private String filedNameMap;

    @Column(name = "IS_USED", nullable = true, length = 1)
    private String isUsed;

    @Column(name = "IS_MANDATORY", nullable = true, length = 1)
    private String isMandatory;

    @Column(name = "ISDELETED", nullable = true, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", nullable = false, precision = 4)
    private int langId;

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

    @Column(name = "SUB_SECTION_TYPE", length = 12, nullable = true)
    private long sectionType;
    
    @Column(name = "DD_OPT_EN", length = 4000, nullable = false)
    private String dropDownOptionEn;
    
    @Column(name = "DD_OPT_REG", length = 4000, nullable = false)
    private String dropDownOptionReg;
    
    @Column(name = "ORDER_NO", nullable = false, length = 12)
    private Double orderNo;

    public long getSectionType() {
        return sectionType;
    }

    public void setSectionType(final long sectionType) {
        this.sectionType = sectionType;
    }

    @Transient
    private List<SubLinkFieldDetails> subLinkFieldlist = new ArrayList<>(0);

    public List<SubLinkFieldDetails> getSubLinkFieldlist() {
        return subLinkFieldlist;
    }

    public void setSubLinkFieldlist(final List<SubLinkFieldDetails> subLinkFieldlist) {
        this.subLinkFieldlist = subLinkFieldlist;
    }

    public SubLinkFieldMapping() {
        isUsed = "Y";
        isMandatory = "N";
        isDeleted = "N";
    }

    public SubLinkFieldMapping(final SubLinkMaster subLinkMaster, final String fieldNameEn, final int fieldType,
            final int fieldSequence, final String filedNameMap) {
        this();
        this.subLinkMaster = subLinkMaster;
        this.fieldNameEn = fieldNameEn;
        this.fieldType = fieldType;
        this.fieldSequence = fieldSequence;
        this.filedNameMap = filedNameMap;
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final long id) {
        this.id = id;
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
     * @return the fieldNameEn
     */
    public String getFieldNameEn() {
        return fieldNameEn;
    }

    /**
     * @param fieldNameEn the fieldNameEn to set
     */
    public void setFieldNameEn(final String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    /**
     * @return the fieldNameRg
     */
    public String getFieldNameRg() {
        return fieldNameRg;
    }

    /**
     * @param fieldNameRg the fieldNameRg to set
     */
    public void setFieldNameRg(final String fieldNameRg) {
        this.fieldNameRg = fieldNameRg;
    }

    /**
     * @return the fieldType
     */
    public int getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(final int fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the fieldSequence
     */
    public int getFieldSequence() {
        return fieldSequence;
    }

    /**
     * @param fieldSequence the fieldSequence to set
     */
    public void setFieldSequence(final int fieldSequence) {
        this.fieldSequence = fieldSequence;
    }

    /**
     * @return the filedNameMap
     */
    public String getFiledNameMap() {
        return filedNameMap;
    }

    /**
     * @param filedNameMap the filedNameMap to set
     */
    public void setFiledNameMap(final String filedNameMap) {
        this.filedNameMap = filedNameMap;
    }

    /**
     * @return the isUsed
     */
    public String getIsUsed() {
        return isUsed;
    }

    /**
     * @param isUsed the isUsed to set
     */
    public void setIsUsed(String isUsed) {
        if (isUsed == null) {
            isUsed = "Y";
        }
        this.isUsed = isUsed;
    }

    /**
     * @return the isMandatory
     */
    public String getIsMandatory() {
        return isMandatory;
    }

    /**
     * @param isMandatory the isMandatory to set
     */
    public void setIsMandatory(String isMandatory) {
        if (isMandatory == null) {
            isMandatory = "N";
        }
        this.isMandatory = isMandatory;
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
    public void setIsDeleted(String isDeleted) {
        if (isDeleted == null) {
            isDeleted = "N";
        }
        this.isDeleted = isDeleted;

    }

    public String getDropDownOptionEn() {
		return dropDownOptionEn;
	}

	public void setDropDownOptionEn(String dropDownOptionEn) {
		this.dropDownOptionEn = dropDownOptionEn;
	}

	public String getDropDownOptionReg() {
		return dropDownOptionReg;
	}

	public void setDropDownOptionReg(String dropDownOptionReg) {
		this.dropDownOptionReg = dropDownOptionReg;
	}
	

	public Double getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Double orderNo) {
		this.orderNo = orderNo;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((fieldNameEn == null) ? 0 : fieldNameEn.hashCode());
        result = (prime * result) + ((fieldNameRg == null) ? 0 : fieldNameRg.hashCode());
        result = (prime * result) + fieldSequence;
        result = (prime * result) + fieldType;
        result = (prime * result) + ((filedNameMap == null) ? 0 : filedNameMap.hashCode());
        result = (prime * result) + (int) (id ^ (id >>> 32));
        result = (prime * result) + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = (prime * result) + ((isMandatory == null) ? 0 : isMandatory.hashCode());
        result = (prime * result) + ((isUsed == null) ? 0 : isUsed.hashCode());
        result = (prime * result) + ((subLinkMaster == null) ? 0 : subLinkMaster.hashCode());
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
        final SubLinkFieldMapping other = (SubLinkFieldMapping) obj;
        if (fieldNameEn == null) {
            if (other.fieldNameEn != null) {
                return false;
            }
        } else if (!fieldNameEn.equals(other.fieldNameEn)) {
            return false;
        }
        if (fieldNameRg == null) {
            if (other.fieldNameRg != null) {
                return false;
            }
        } else if (!fieldNameRg.equals(other.fieldNameRg)) {
            return false;
        }
        if (fieldSequence != other.fieldSequence) {
            return false;
        }
        if (fieldType != other.fieldType) {
            return false;
        }
        if (filedNameMap == null) {
            if (other.filedNameMap != null) {
                return false;
            }
        } else if (!filedNameMap.equals(other.filedNameMap)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (isDeleted == null) {
            if (other.isDeleted != null) {
                return false;
            }
        } else if (!isDeleted.equals(other.isDeleted)) {
            return false;
        }
        if (isMandatory == null) {
            if (other.isMandatory != null) {
                return false;
            }
        } else if (!isMandatory.equals(other.isMandatory)) {
            return false;
        }
        if (isUsed == null) {
            if (other.isUsed != null) {
                return false;
            }
        } else if (!isUsed.equals(other.isUsed)) {
            return false;
        }

        if (subLinkMaster == null) {
            if (other.subLinkMaster != null) {
                return false;
            }
        } else if (!subLinkMaster.equals(other.subLinkMaster)) {
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
        builder.append("SubLinkFieldMapping [id=");
        builder.append(id);
        builder.append(", subLinkMaster=");
        builder.append(subLinkMaster.getId());
        builder.append(", fieldNameEn=");
        builder.append(fieldNameEn);
        builder.append(", fieldNameRg=");
        builder.append(fieldNameRg);
        builder.append(", fieldType=");
        builder.append(fieldType);
        builder.append(", fieldSequence=");
        builder.append(fieldSequence);
        builder.append(", filedNameMap=");
        builder.append(filedNameMap);
        builder.append(", isUsed=");
        builder.append(isUsed);
        builder.append(", isMandatory=");
        builder.append(isMandatory);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINK_FIELD_MAP", "SUB_LINK_FIELD_ID" };
    }

}
