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
@Table(name = "TB_EIP_SUB_LINK_FIELD_MAP_HIST")
public class SubLinkFieldMappingHistory extends BaseEntity {

    private static final long serialVersionUID = 7965722206083111441L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_FIELD_ID_H", nullable = false, precision = 12, scale = 0)
    private long hId;
    
    @Column(name = "SUB_LINK_FIELD_ID", nullable = false, precision = 12, scale = 0)
    private long id;
    
    @Column(name = "SUB_LINK_MAS_ID")
    private long subLinkMaster;

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
    
    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String status;
    
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

    public SubLinkFieldMappingHistory() {
        isUsed = "Y";
        isMandatory = "N";
        isDeleted = "N";
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

	

	public long getSubLinkMaster() {
		return subLinkMaster;
	}

	public void setSubLinkMaster(long subLinkMaster) {
		this.subLinkMaster = subLinkMaster;
	}

	@Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINK_FIELD_MAP_HIST", "SUB_LINK_FIELD_ID_H" };
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
