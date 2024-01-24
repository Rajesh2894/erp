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
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.springframework.context.annotation.Primary;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pranit.Mhatre
 * @since 13 February, 2014
 */
@Entity
@Table(name = "TB_EIP_SUB_LINK_FIELDS_DTL")
@FilterDef(name = "filterSubLinkFieldDetailMapping", parameters = @ParamDef(name = "IS_DELETED_VAL", type = "string"))
@Primary
public class SubLinkFieldDetails extends BaseEntity {

    private static final long serialVersionUID = 2573496011462818449L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_FIELD_DTL_ID", nullable = false, precision = 12, scale = 0)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUB_LINKS_MAS_ID", referencedColumnName = "SUB_LINK_MAS_ID", nullable = false, insertable = true, updatable = false)
    @ForeignKey(name = "FK_LINKS_FIELD_MAS_ID")
    private SubLinkMaster subLinkMaster;

    /**
     * For Form Text Fields.
     */

    @Column(name = "TXT_01_EN", nullable = true, length = 150)
    private String txt_01_en;
    @Column(name = "TXT_01_RG", nullable = true, length = 150)
    private String txt_01_rg;
    @Column(name = "TXT_02_EN", nullable = true, length = 150)
    private String txt_02_en;
    @Column(name = "TXT_02_RG", nullable = true, length = 150)
    private String txt_02_rg;
    @Column(name = "TXT_03_EN", nullable = true, length = 150)
    private String txt_03_en;
    @Column(name = "TXT_03_RG", nullable = true, length = 150)
    private String txt_03_rg;

    @Column(name = "TXT_03_EN_BLOB", nullable = true)
    private String txta_03_ren_blob;

    @Column(name = "TXT_03_EN_NNCLOB", nullable = true)
    private String txta_03_en_nnclob;

    @Column(name = "TXT_04_EN", nullable = true, length = 150)
    private String txt_04_en;
    @Column(name = "TXT_04_RG", nullable = true, length = 150)
    private String txt_04_rg;
    @Column(name = "TXT_05_EN", nullable = true, length = 150)
    private String txt_05_en;
    @Column(name = "TXT_05_RG", nullable = true, length = 150)
    private String txt_05_rg;
    @Column(name = "TXT_06_EN", nullable = true, length = 150)
    private String txt_06_en;
    @Column(name = "TXT_06_RG", nullable = true, length = 150)
    private String txt_06_rg;
    @Column(name = "TXT_07_EN", nullable = true, length = 150)
    private String txt_07_en;
    @Column(name = "TXT_07_RG", nullable = true, length = 150)
    private String txt_07_rg;
    @Column(name = "TXT_08_EN", nullable = true, length = 150)
    private String txt_08_en;
    @Column(name = "TXT_08_RG", nullable = true, length = 150)
    private String txt_08_rg;
    @Column(name = "TXT_09_EN", nullable = true, length = 150)
    private String txt_09_en;
    @Column(name = "TXT_09_RG", nullable = true, length = 150)
    private String txt_09_rg;
    @Column(name = "TXT_10_EN", nullable = true, length = 150)
    private String txt_10_en;
    @Column(name = "TXT_10_RG", nullable = true, length = 150)
    private String txt_10_rg;
    @Column(name = "TXT_11_EN", nullable = true, length = 150)
    private String txt_11_en;
    @Column(name = "TXT_11_RG", nullable = true, length = 150)
    private String txt_11_rg;
    @Column(name = "TXT_12_EN", nullable = true, length = 150)
    private String txt_12_en;
    @Column(name = "TXT_12_RG", nullable = true, length = 150)
    private String txt_12_rg;
    
    /**
     * For Form Dropdown.
     */
    
    @Column(name = "DD_01_EN", nullable = true, length = 150)
    private String dd_01_en;
    @Column(name = "DD_01_RG", nullable = true, length = 150)
    private String dd_01_rg;
    @Column(name = "DD_02_EN", nullable = true, length = 150)
    private String dd_02_en;
    @Column(name = "DD_02_RG", nullable = true, length = 150)
    private String dd_02_rg;
	@Column(name = "DD_03_EN", nullable = true, length = 150)
    private String dd_03_en;
    @Column(name = "DD_03_RG", nullable = true, length = 150)
    private String dd_03_rg;
    @Column(name = "DD_04_EN", nullable = true, length = 150)
    private String dd_04_en;
    @Column(name = "DD_04_RG", nullable = true, length = 150)
    private String dd_04_rg;
    @Column(name = "DD_05_EN", nullable = true, length = 150)
    private String dd_05_en;
    @Column(name = "DD_05_RG", nullable = true, length = 150)
    private String dd_05_rg;
    @Column(name = "DD_06_EN", nullable = true, length = 150)
    private String dd_06_en;
    @Column(name = "DD_06_RG", nullable = true, length = 150)
    private String dd_06_rg;
    @Column(name = "DD_07_EN", nullable = true, length = 150)
    private String dd_07_en;
    @Column(name = "DD_07_RG", nullable = true, length = 150)
    private String dd_07_rg;
    @Column(name = "DD_08_EN", nullable = true, length = 150)
    private String dd_08_en;
    @Column(name = "DD_08_RG", nullable = true, length = 150)
    private String dd_08_rg;
    @Column(name = "DD_09_EN", nullable = true, length = 150)
    private String dd_09_en;
    @Column(name = "DD_09_RG", nullable = true, length = 150)
    private String dd_09_rg;
    @Column(name = "DD_10_EN", nullable = true, length = 150)
    private String dd_10_en;
    @Column(name = "DD_10_RG", nullable = true, length = 150)
    private String dd_10_rg;
    @Column(name = "DD_11_EN", nullable = true, length = 150)
    private String dd_11_en;
    @Column(name = "DD_11_RG", nullable = true, length = 150)
    private String dd_11_rg;
    @Column(name = "DD_12_EN", nullable = true, length = 150)
    private String dd_12_en;
    @Column(name = "DD_12_RG", nullable = true, length = 150)
    private String dd_12_rg;

    /**
     * For Form Text Area.
     */
    @Column(name = "TXTA_01_EN", nullable = true, length = 4000)
    private String txta_01_en;
    @Column(name = "TXTA_01_RG", nullable = true, length = 4000)
    private String txta_01_rg;
    @Column(name = "TXTA_02_EN", nullable = true, length = 4000)
    private String txta_02_en;
    @Column(name = "TXTA_02_RG", nullable = true, length = 4000)
    private String txta_02_rg;

    /**
     * For Form Date Fields
     */

    @Column(name = "DATE_01", nullable = true)
    private Date date_01;
    @Column(name = "DATE_02", nullable = true)
    private Date date_02;
    @Column(name = "DATE_03", nullable = true)
    private Date date_03;
    @Column(name = "DATE_04", nullable = true)
    private Date date_04;

    
    /**
     * For Form Attachment(s)
     */
    @Column(name = "ATT_01", nullable = true, length = 3000)
    private String att_01;
    @Column(name = "ATT_02", nullable = true, length = 3000)
    private String att_02;

    @Column(name = "PROFILE_IMG_PATH", nullable = true, length = 1000)
    private String profile_img_path;

    @Column(name = "ATT_VIDEO_PATH", nullable = true, length = 1000)
    private String att_video_path;

    @Column(name = "ISDELETED", nullable = false, length = 1)
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

    @Column(name = "SUB_LINK_FIELD_DTL_ORD", length = 12, nullable = true)
    private Double subLinkFieldDtlOrd;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "VALIDITY_DATE", nullable = true)
    private Date validityDate;
    
    @Column(name = "REMARK", length = 1000, nullable = false)
    private String remark;
    
    @Column(name = "IS_HIGHLIGHTED", nullable = true, length = 1)
    private String isHighlighted;
    
    @Column(name = "ISSUE_DATE", nullable = true)
    private Date issueDate;

	@Transient
    private String img_path;

    @Transient
    private String att_path;

    @Transient
    private String video_path;
    
    @Column(name = "LINK_01", nullable = true)
    private String link_01;
    
    @Column(name = "LINK_02", nullable = true)
    private String link_02;

	public String getFieldNameEn() {
        return fieldNameEn;
    }

    public void setFieldNameEn(final String fieldNameEn) {
        this.fieldNameEn = fieldNameEn;
    }

    public String getFieldNameRg() {
        return fieldNameRg;
    }

    public void setFieldNameRg(final String fieldNameRg) {
        this.fieldNameRg = fieldNameRg;
    }

    @Transient
    private String fieldNameEn;

    @Transient
    private String fieldNameRg;

    @Transient
    private int fieldType;
    
    @Transient
    private String lastApprovedBy;

	@Transient
    private String createdBy;//added to overcome the lazy initialization and improve the performance
    
    @Transient
    private String modifiedBy;//added to overcome the lazy initialization and improve the performance    

	
	@Transient
    private Date lastApprovedDate;

   

	public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(final int fieldType) {
        this.fieldType = fieldType;
    }

    public SubLinkFieldDetails() {
        isDeleted = "N";
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
     * @return the txt_01_en
     */
    public String getTxt_01_en() {
        return txt_01_en;
    }

    /**
     * @param txt_01_en the txt_01_en to set
     */
    public void setTxt_01_en(final String txt_01_en) {
        this.txt_01_en = txt_01_en;
    }

    /**
     * @return the txt_01_rg
     */
    public String getTxt_01_rg() {
        return txt_01_rg;
    }

    /**
     * @param txt_01_rg the txt_01_rg to set
     */
    public void setTxt_01_rg(final String txt_01_rg) {
        this.txt_01_rg = txt_01_rg;
    }

    /**
     * @return the txt_02_en
     */
    public String getTxt_02_en() {
        return txt_02_en;
    }

    /**
     * @param txt_02_en the txt_02_en to set
     */
    public void setTxt_02_en(final String txt_02_en) {
        this.txt_02_en = txt_02_en;
    }

    /**
     * @return the txt_02_rg
     */
    public String getTxt_02_rg() {
        return txt_02_rg;
    }

    /**
     * @param txt_02_rg the txt_02_rg to set
     */
    public void setTxt_02_rg(final String txt_02_rg) {
        this.txt_02_rg = txt_02_rg;
    }

    /**
     * @return the txt_03_en
     */

    /**
     * @param txt_03_en the txt_03_en to set
     */
    public void setTxta_03_en(final String txta_03_en) {
        this.txt_03_en = txta_03_en;
    }

    public String getTxt_03_en() {
        return txt_03_en;
    }

    public void setTxt_03_en(String txt_03_en) {
        this.txt_03_en = txt_03_en;
    }

    public String getTxt_03_rg() {
        return txt_03_rg;
    }

    /**
     * @return the txt_03_rg
     */
    public String getTxta_03_rg() {
        return txt_03_rg;
    }

    /**
     * @param txt_03_rg the txt_03_rg to set
     */

    /**
     * @return the txt_04_en
     */
    public String getTxt_04_en() {
        return txt_04_en;
    }

    /**
     * @param txt_04_en the txt_04_en to set
     */
    public void setTxt_04_en(final String txt_04_en) {
        this.txt_04_en = txt_04_en;
    }

    /**
     * @return the txt_04_rg
     */
    public String getTxt_04_rg() {
        return txt_04_rg;
    }

    /**
     * @param txt_04_rg the txt_04_rg to set
     */
    public void setTxt_04_rg(final String txt_04_rg) {
        this.txt_04_rg = txt_04_rg;
    }

    /**
     * @return the txt_05_en
     */
    public String getTxt_05_en() {
        return txt_05_en;
    }

    /**
     * @param txt_05_en the txt_05_en to set
     */
    public void setTxt_05_en(final String txt_05_en) {
        this.txt_05_en = txt_05_en;
    }

    /**
     * @return the txt_05_rg
     */
    public String getTxt_05_rg() {
        return txt_05_rg;
    }

    /**
     * @param txt_05_rg the txt_05_rg to set
     */
    public void setTxt_05_rg(final String txt_05_rg) {
        this.txt_05_rg = txt_05_rg;
    }

    /**
     * @return the txt_06_en
     */
    public String getTxt_06_en() {
        return txt_06_en;
    }

    /**
     * @param txt_06_en the txt_06_en to set
     */
    public void setTxt_06_en(final String txt_06_en) {
        this.txt_06_en = txt_06_en;
    }

    /**
     * @return the txt_06_rg
     */
    public String getTxt_06_rg() {
        return txt_06_rg;
    }

    /**
     * @param txt_06_rg the txt_06_rg to set
     */
    public void setTxt_06_rg(final String txt_06_rg) {
        this.txt_06_rg = txt_06_rg;
    }

    /**
     * @return the txt_07_en
     */
    public String getTxt_07_en() {
        return txt_07_en;
    }

    /**
     * @param txt_07_en the txt_07_en to set
     */
    public void setTxt_07_en(final String txt_07_en) {
        this.txt_07_en = txt_07_en;
    }

    /**
     * @return the txt_07_rg
     */
    public String getTxt_07_rg() {
        return txt_07_rg;
    }

    /**
     * @param txt_07_rg the txt_07_rg to set
     */
    public void setTxt_07_rg(final String txt_07_rg) {
        this.txt_07_rg = txt_07_rg;
    }

    /**
     * @return the txt_08_en
     */
    public String getTxt_08_en() {
        return txt_08_en;
    }

    /**
     * @param txt_08_en the txt_08_en to set
     */
    public void setTxt_08_en(final String txt_08_en) {
        this.txt_08_en = txt_08_en;
    }

    /**
     * @return the txt_08_rg
     */
    public String getTxt_08_rg() {
        return txt_08_rg;
    }

    /**
     * @param txt_08_rg the txt_08_rg to set
     */
    public void setTxt_08_rg(final String txt_08_rg) {
        this.txt_08_rg = txt_08_rg;
    }

    /**
     * @return the txt_09_en
     */
    public String getTxt_09_en() {
        return txt_09_en;
    }

    /**
     * @param txt_09_en the txt_09_en to set
     */
    public void setTxt_09_en(final String txt_09_en) {
        this.txt_09_en = txt_09_en;
    }

    /**
     * @return the txt_09_rg
     */
    public String getTxt_09_rg() {
        return txt_09_rg;
    }

    /**
     * @param txt_09_rg the txt_09_rg to set
     */
    public void setTxt_09_rg(final String txt_09_rg) {
        this.txt_09_rg = txt_09_rg;
    }

    /**
     * @return the txt_10_en
     */
    public String getTxt_10_en() {
        return txt_10_en;
    }

    /**
     * @param txt_10_en the txt_10_en to set
     */
    public void setTxt_10_en(final String txt_10_en) {
        this.txt_10_en = txt_10_en;
    }

    /**
     * @return the txt_10_rg
     */
    public String getTxt_10_rg() {
        return txt_10_rg;
    }

    /**
     * @param txt_10_rg the txt_10_rg to set
     */
    public void setTxt_10_rg(final String txt_10_rg) {
        this.txt_10_rg = txt_10_rg;
    }

    /**
     * @return the txt_11_en
     */
    public String getTxt_11_en() {
        return txt_11_en;
    }

    /**
     * @param txt_11_en the txt_11_en to set
     */
    public void setTxt_11_en(final String txt_11_en) {
        this.txt_11_en = txt_11_en;
    }

    /**
     * @return the txt_11_rg
     */
    public String getTxt_11_rg() {
        return txt_11_rg;
    }

    /**
     * @param txt_11_rg the txt_11_rg to set
     */
    public void setTxt_11_rg(final String txt_11_rg) {
        this.txt_11_rg = txt_11_rg;
    }

    /**
     * @return the txt_12_en
     */
    public String getTxt_12_en() {
        return txt_12_en;
    }

    /**
     * @param txt_12_en the txt_12_en to set
     */
    public void setTxt_12_en(final String txt_12_en) {
        this.txt_12_en = txt_12_en;
    }

    /**
     * @return the txt_12_rg
     */
    public String getTxt_12_rg() {
        return txt_12_rg;
    }

    /**
     * @param txt_12_rg the txt_12_rg to set
     */
    public void setTxt_12_rg(final String txt_12_rg) {
        this.txt_12_rg = txt_12_rg;
    }

    /**
     * @return the txta_01_en
     */
    public String getTxta_01_en() {
        return txta_01_en;
    }

    /**
     * @param txta_01_en the txta_01_en to set
     */
    public void setTxta_01_en(final String txta_01_en) {
        this.txta_01_en = txta_01_en;
    }

    /**
     * @return the txta_01_rg
     */
    public String getTxta_01_rg() {
        return txta_01_rg;
    }

    /**
     * @param txta_01_rg the txta_01_rg to set
     */
    public void setTxta_01_rg(final String txta_01_rg) {
        this.txta_01_rg = txta_01_rg;
    }

    /**
     * @return the txta_02_en
     */
    public String getTxta_02_en() {
        return txta_02_en;
    }

    /**
     * @param txta_02_en the txta_02_en to set
     */
    public void setTxta_02_en(final String txta_02_en) {
        this.txta_02_en = txta_02_en;
    }

    /**
     * @return the txta_02_rg
     */
    public String getTxta_02_rg() {
        return txta_02_rg;
    }

    /**
     * @param txta_02_rg the txta_02_rg to set
     */
    public void setTxta_02_rg(final String txta_02_rg) {
        this.txta_02_rg = txta_02_rg;
    }

    /**
     * @return the date_01
     */
    public Date getDate_01() {
        return date_01;
    }

    /**
     * @param date_01 the date_01 to set
     */
    public void setDate_01(final Date date_01) {
        this.date_01 = date_01;
    }

    /**
     * @return the date_02
     */
    public Date getDate_02() {
        return date_02;
    }

    /**
     * @param date_02 the date_02 to set
     */
    public void setDate_02(final Date date_02) {
        this.date_02 = date_02;
    }

    /**
     * @return the date_03
     */
    public Date getDate_03() {
        return date_03;
    }

    /**
     * @param date_03 the date_03 to set
     */
    public void setDate_03(final Date date_03) {
        this.date_03 = date_03;
    }

    /**
     * @return the date_04
     */
    public Date getDate_04() {
        return date_04;
    }

    /**
     * @param date_04 the date_04 to set
     */
    public void setDate_04(final Date date_04) {
        this.date_04 = date_04;
    }

    /**
     * @return the att_01
     */
    public String getAtt_01() {
        return att_01;
    }

    /**
     * @param att_01 the att_01 to set
     */
    public void setAtt_01(final String att_01) {
        this.att_01 = att_01;
    }

    /**
     * @return the att_02
     */
    public String getAtt_02() {
        return att_02;
    }

    /**
     * @param att_02 the att_02 to set
     */
    public void setAtt_02(final String att_02) {
        this.att_02 = att_02;
    }

    /**
     * @return the profile_img_path
     */
    public String getProfile_img_path() {
        return profile_img_path;
    }

    /**
     * @param profile_img_path the profile_img_path to set
     */
    public void setProfile_img_path(final String profile_img_path) {
        this.profile_img_path = profile_img_path;
    }
    
    

    public String getDd_01_en() {
		return dd_01_en;
	}

	public void setDd_01_en(final String dd_01_en) {
		this.dd_01_en = dd_01_en;
	}

	public String getDd_02_en() {
		return dd_02_en;
	}

	public void setDd_02_en(final String dd_02_en) {
		this.dd_02_en = dd_02_en;
	}

	public String getDd_01_rg() {
		return dd_01_rg;
	}

	public void setDd_01_rg(final String dd_01_rg) {
		this.dd_01_rg = dd_01_rg;
	}

	public String getDd_02_rg() {
		return dd_02_rg;
	}

	public void setDd_02_rg(final String dd_02_rg) {
		this.dd_02_rg = dd_02_rg;
	}

	public String getDd_03_en() {
		return dd_03_en;
	}

	public void setDd_03_en(String dd_03_en) {
		this.dd_03_en = dd_03_en;
	}

	public String getDd_03_rg() {
		return dd_03_rg;
	}

	public void setDd_03_rg(String dd_03_rg) {
		this.dd_03_rg = dd_03_rg;
	}

	public String getDd_04_en() {
		return dd_04_en;
	}

	public void setDd_04_en(String dd_04_en) {
		this.dd_04_en = dd_04_en;
	}

	public String getDd_04_rg() {
		return dd_04_rg;
	}

	public void setDd_04_rg(String dd_04_rg) {
		this.dd_04_rg = dd_04_rg;
	}

	public String getDd_05_en() {
		return dd_05_en;
	}

	public void setDd_05_en(String dd_05_en) {
		this.dd_05_en = dd_05_en;
	}

	public String getDd_05_rg() {
		return dd_05_rg;
	}

	public void setDd_05_rg(String dd_05_rg) {
		this.dd_05_rg = dd_05_rg;
	}

	public String getDd_06_en() {
		return dd_06_en;
	}

	public void setDd_06_en(String dd_06_en) {
		this.dd_06_en = dd_06_en;
	}

	public String getDd_06_rg() {
		return dd_06_rg;
	}

	public void setDd_06_rg(String dd_06_rg) {
		this.dd_06_rg = dd_06_rg;
	}

	public String getDd_07_en() {
		return dd_07_en;
	}

	public void setDd_07_en(String dd_07_en) {
		this.dd_07_en = dd_07_en;
	}

	public String getDd_07_rg() {
		return dd_07_rg;
	}

	public void setDd_07_rg(String dd_07_rg) {
		this.dd_07_rg = dd_07_rg;
	}

	public String getDd_08_en() {
		return dd_08_en;
	}

	public void setDd_08_en(String dd_08_en) {
		this.dd_08_en = dd_08_en;
	}

	public String getDd_08_rg() {
		return dd_08_rg;
	}

	public void setDd_08_rg(String dd_08_rg) {
		this.dd_08_rg = dd_08_rg;
	}

	public String getDd_09_en() {
		return dd_09_en;
	}

	public void setDd_09_en(String dd_09_en) {
		this.dd_09_en = dd_09_en;
	}

	public String getDd_09_rg() {
		return dd_09_rg;
	}

	public void setDd_09_rg(String dd_09_rg) {
		this.dd_09_rg = dd_09_rg;
	}

	public String getDd_10_en() {
		return dd_10_en;
	}

	public void setDd_10_en(String dd_10_en) {
		this.dd_10_en = dd_10_en;
	}

	public String getDd_10_rg() {
		return dd_10_rg;
	}

	public void setDd_10_rg(String dd_10_rg) {
		this.dd_10_rg = dd_10_rg;
	}

	public String getDd_11_en() {
		return dd_11_en;
	}

	public void setDd_11_en(String dd_11_en) {
		this.dd_11_en = dd_11_en;
	}

	public String getDd_11_rg() {
		return dd_11_rg;
	}

	public void setDd_11_rg(String dd_11_rg) {
		this.dd_11_rg = dd_11_rg;
	}

	public String getDd_12_en() {
		return dd_12_en;
	}

	public void setDd_12_en(String dd_12_en) {
		this.dd_12_en = dd_12_en;
	}

	public String getDd_12_rg() {
		return dd_12_rg;
	}

	public void setDd_12_rg(String dd_12_rg) {
		this.dd_12_rg = dd_12_rg;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((att_01 == null) ? 0 : att_01.hashCode());
        result = (prime * result) + ((att_02 == null) ? 0 : att_02.hashCode());
        result = (prime * result) + ((date_01 == null) ? 0 : date_01.hashCode());
        result = (prime * result) + ((date_02 == null) ? 0 : date_02.hashCode());
        result = (prime * result) + ((date_03 == null) ? 0 : date_03.hashCode());
        result = (prime * result) + ((date_04 == null) ? 0 : date_04.hashCode());
        result = (prime * result) + (int) (id ^ (id >>> 32));
        result = (prime * result) + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = (prime * result) + ((lgIpMac == null) ? 0 : lgIpMac.hashCode());
        result = (prime * result) + ((lgIpMacUpd == null) ? 0 : lgIpMacUpd.hashCode());
        result = (prime * result) + ((lmodDate == null) ? 0 : lmodDate.hashCode());
        result = (prime * result) + ((orgId == null) ? 0 : orgId.hashCode());
        result = (prime * result) + ((profile_img_path == null) ? 0 : profile_img_path.hashCode());
        result = (prime * result) + ((subLinkMaster == null) ? 0 : subLinkMaster.hashCode());
        result = (prime * result) + ((txt_01_en == null) ? 0 : txt_01_en.hashCode());
        result = (prime * result) + ((txt_01_rg == null) ? 0 : txt_01_rg.hashCode());
        result = (prime * result) + ((txt_02_en == null) ? 0 : txt_02_en.hashCode());
        result = (prime * result) + ((txt_02_rg == null) ? 0 : txt_02_rg.hashCode());
        result = (prime * result) + ((txt_03_en == null) ? 0 : txt_03_en.hashCode());
        result = (prime * result) + ((txt_03_rg == null) ? 0 : txt_03_rg.hashCode());
        result = (prime * result) + ((txta_03_ren_blob == null) ? 0 : txta_03_ren_blob.hashCode());
        /*
         * result = prime * result + ((txta_03_ren_blob2 == null) ? 0 : txta_03_ren_blob2.hashCode()); result = prime * result +
         * ((txta_03_ren_clob == null) ? 0 : txta_03_ren_clob.hashCode());
         */
        result = (prime * result) + ((txt_04_en == null) ? 0 : txt_04_en.hashCode());
        result = (prime * result) + ((txt_04_rg == null) ? 0 : txt_04_rg.hashCode());
        result = (prime * result) + ((txt_05_en == null) ? 0 : txt_05_en.hashCode());
        result = (prime * result) + ((txt_05_rg == null) ? 0 : txt_05_rg.hashCode());
        result = (prime * result) + ((txt_06_en == null) ? 0 : txt_06_en.hashCode());
        result = (prime * result) + ((txt_06_rg == null) ? 0 : txt_06_rg.hashCode());
        result = (prime * result) + ((txt_07_en == null) ? 0 : txt_07_en.hashCode());
        result = (prime * result) + ((txt_07_rg == null) ? 0 : txt_07_rg.hashCode());
        result = (prime * result) + ((txt_08_en == null) ? 0 : txt_08_en.hashCode());
        result = (prime * result) + ((txt_08_rg == null) ? 0 : txt_08_rg.hashCode());
        result = (prime * result) + ((txt_09_en == null) ? 0 : txt_09_en.hashCode());
        result = (prime * result) + ((txt_09_rg == null) ? 0 : txt_09_rg.hashCode());
        result = (prime * result) + ((txt_10_en == null) ? 0 : txt_10_en.hashCode());
        result = (prime * result) + ((txt_10_rg == null) ? 0 : txt_10_rg.hashCode());
        result = (prime * result) + ((txt_11_en == null) ? 0 : txt_11_en.hashCode());
        result = (prime * result) + ((txt_11_rg == null) ? 0 : txt_11_rg.hashCode());
        result = (prime * result) + ((txt_12_en == null) ? 0 : txt_12_en.hashCode());
        result = (prime * result) + ((txt_12_rg == null) ? 0 : txt_12_rg.hashCode());
        result = (prime * result) + ((txta_01_en == null) ? 0 : txta_01_en.hashCode());
        result = (prime * result) + ((txta_01_rg == null) ? 0 : txta_01_rg.hashCode());
        result = (prime * result) + ((txta_02_en == null) ? 0 : txta_02_en.hashCode());
        result = (prime * result) + ((txta_02_rg == null) ? 0 : txta_02_rg.hashCode());
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
        final SubLinkFieldDetails other = (SubLinkFieldDetails) obj;
        if (att_01 == null) {
            if (other.att_01 != null) {
                return false;
            }
        } else if (!att_01.equals(other.att_01)) {
            return false;
        }
        if (att_02 == null) {
            if (other.att_02 != null) {
                return false;
            }
        } else if (!att_02.equals(other.att_02)) {
            return false;
        }
        if (date_01 == null) {
            if (other.date_01 != null) {
                return false;
            }
        } else if (!date_01.equals(other.date_01)) {
            return false;
        }
        if (date_02 == null) {
            if (other.date_02 != null) {
                return false;
            }
        } else if (!date_02.equals(other.date_02)) {
            return false;
        }
        if (date_03 == null) {
            if (other.date_03 != null) {
                return false;
            }
        } else if (!date_03.equals(other.date_03)) {
            return false;
        }
        if (date_04 == null) {
            if (other.date_04 != null) {
                return false;
            }
        } else if (!date_04.equals(other.date_04)) {
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

        if (profile_img_path == null) {
            if (other.profile_img_path != null) {
                return false;
            }
        } else if (!profile_img_path.equals(other.profile_img_path)) {
            return false;
        }
        if (subLinkMaster == null) {
            if (other.subLinkMaster != null) {
                return false;
            }
        } else if (!subLinkMaster.equals(other.subLinkMaster)) {
            return false;
        }
        if (txt_01_en == null) {
            if (other.txt_01_en != null) {
                return false;
            }
        } else if (!txt_01_en.equals(other.txt_01_en)) {
            return false;
        }
        if (txt_01_rg == null) {
            if (other.txt_01_rg != null) {
                return false;
            }
        } else if (!txt_01_rg.equals(other.txt_01_rg)) {
            return false;
        }
        if (txt_02_en == null) {
            if (other.txt_02_en != null) {
                return false;
            }
        } else if (!txt_02_en.equals(other.txt_02_en)) {
            return false;
        }
        if (txt_02_rg == null) {
            if (other.txt_02_rg != null) {
                return false;
            }
        } else if (!txt_02_rg.equals(other.txt_02_rg)) {
            return false;
        }
        if (txt_03_en == null) {
            if (other.txt_03_en != null) {
                return false;
            }
        } else if (!txt_03_en.equals(other.txt_03_en)) {
            return false;
        }
        if (txt_03_rg == null) {
            if (other.txt_03_rg != null) {
                return false;
            }
        } else if (!txt_03_rg.equals(other.txt_03_rg)) {
            return false;
        }
        if (txt_04_en == null) {
            if (other.txt_04_en != null) {
                return false;
            }
        } else if (!txt_04_en.equals(other.txt_04_en)) {
            return false;
        }
        if (txt_04_rg == null) {
            if (other.txt_04_rg != null) {
                return false;
            }
        } else if (!txt_04_rg.equals(other.txt_04_rg)) {
            return false;
        }
        if (txt_05_en == null) {
            if (other.txt_05_en != null) {
                return false;
            }
        } else if (!txt_05_en.equals(other.txt_05_en)) {
            return false;
        }
        if (txt_05_rg == null) {
            if (other.txt_05_rg != null) {
                return false;
            }
        } else if (!txt_05_rg.equals(other.txt_05_rg)) {
            return false;
        }
        if (txt_06_en == null) {
            if (other.txt_06_en != null) {
                return false;
            }
        } else if (!txt_06_en.equals(other.txt_06_en)) {
            return false;
        }
        if (txt_06_rg == null) {
            if (other.txt_06_rg != null) {
                return false;
            }
        } else if (!txt_06_rg.equals(other.txt_06_rg)) {
            return false;
        }
        if (txt_07_en == null) {
            if (other.txt_07_en != null) {
                return false;
            }
        } else if (!txt_07_en.equals(other.txt_07_en)) {
            return false;
        }
        if (txt_07_rg == null) {
            if (other.txt_07_rg != null) {
                return false;
            }
        } else if (!txt_07_rg.equals(other.txt_07_rg)) {
            return false;
        }
        if (txt_08_en == null) {
            if (other.txt_08_en != null) {
                return false;
            }
        } else if (!txt_08_en.equals(other.txt_08_en)) {
            return false;
        }
        if (txt_08_rg == null) {
            if (other.txt_08_rg != null) {
                return false;
            }
        } else if (!txt_08_rg.equals(other.txt_08_rg)) {
            return false;
        }
        if (txt_09_en == null) {
            if (other.txt_09_en != null) {
                return false;
            }
        } else if (!txt_09_en.equals(other.txt_09_en)) {
            return false;
        }
        if (txt_09_rg == null) {
            if (other.txt_09_rg != null) {
                return false;
            }
        } else if (!txt_09_rg.equals(other.txt_09_rg)) {
            return false;
        }
        if (txt_10_en == null) {
            if (other.txt_10_en != null) {
                return false;
            }
        } else if (!txt_10_en.equals(other.txt_10_en)) {
            return false;
        }
        if (txt_10_rg == null) {
            if (other.txt_10_rg != null) {
                return false;
            }
        } else if (!txt_10_rg.equals(other.txt_10_rg)) {
            return false;
        }
        if (txt_11_en == null) {
            if (other.txt_11_en != null) {
                return false;
            }
        } else if (!txt_11_en.equals(other.txt_11_en)) {
            return false;
        }
        if (txt_11_rg == null) {
            if (other.txt_11_rg != null) {
                return false;
            }
        } else if (!txt_11_rg.equals(other.txt_11_rg)) {
            return false;
        }
        if (txt_12_en == null) {
            if (other.txt_12_en != null) {
                return false;
            }
        } else if (!txt_12_en.equals(other.txt_12_en)) {
            return false;
        }
        if (txt_12_rg == null) {
            if (other.txt_12_rg != null) {
                return false;
            }
        } else if (!txt_12_rg.equals(other.txt_12_rg)) {
            return false;
        }
        if (txta_01_en == null) {
            if (other.txta_01_en != null) {
                return false;
            }
        } else if (!txta_01_en.equals(other.txta_01_en)) {
            return false;
        }
        if (txta_01_rg == null) {
            if (other.txta_01_rg != null) {
                return false;
            }
        } else if (!txta_01_rg.equals(other.txta_01_rg)) {
            return false;
        }
        if (txta_02_en == null) {
            if (other.txta_02_en != null) {
                return false;
            }
        } else if (!txta_02_en.equals(other.txta_02_en)) {
            return false;
        }
        if (txta_02_rg == null) {
            if (other.txta_02_rg != null) {
                return false;
            }
        } else if (!txta_02_rg.equals(other.txta_02_rg)) {
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
        builder.append("SubLinkFieldDetails [id=");
        builder.append(id);
        builder.append(", subLinkMaster=");
        builder.append(subLinkMaster);
        builder.append(", txt_01_en=");
        builder.append(txt_01_en);
        builder.append(", txt_01_rg=");
        builder.append(txt_01_rg);
        builder.append(", txt_02_en=");
        builder.append(txt_02_en);
        builder.append(", txt_02_rg=");
        builder.append(txt_02_rg);
        builder.append(", txt_03_en=");
        builder.append(txt_03_en);
        builder.append(", txt_03_rg=");
        builder.append(txta_03_ren_blob);
        builder.append(", txta_03_ren_blob=");
        /*
         * builder.append(txta_03_ren_blob2); builder.append(", txta_03_ren_blob2="); builder.append(txta_03_ren_clob);
         * builder.append(", txta_03_ren_clob=");
         */
        builder.append(txt_03_rg);
        builder.append(", txt_04_en=");
        builder.append(txt_04_en);
        builder.append(", txt_04_rg=");
        builder.append(txt_04_rg);
        builder.append(", txt_05_en=");
        builder.append(txt_05_en);
        builder.append(", txt_05_rg=");
        builder.append(txt_05_rg);
        builder.append(", txt_06_en=");
        builder.append(txt_06_en);
        builder.append(", txt_06_rg=");
        builder.append(txt_06_rg);
        builder.append(", txt_07_en=");
        builder.append(txt_07_en);
        builder.append(", txt_07_rg=");
        builder.append(txt_07_rg);
        builder.append(", txt_08_en=");
        builder.append(txt_08_en);
        builder.append(", txt_08_rg=");
        builder.append(txt_08_rg);
        builder.append(", txt_09_en=");
        builder.append(txt_09_en);
        builder.append(", txt_09_rg=");
        builder.append(txt_09_rg);
        builder.append(", txt_10_en=");
        builder.append(txt_10_en);
        builder.append(", txt_10_rg=");
        builder.append(txt_10_rg);
        builder.append(", txt_11_en=");
        builder.append(txt_11_en);
        builder.append(", txt_11_rg=");
        builder.append(txt_11_rg);
        builder.append(", txt_12_en=");
        builder.append(txt_12_en);
        builder.append(", txt_12_rg=");
        builder.append(txt_12_rg);
        builder.append(", txta_01_en=");
        builder.append(txta_01_en);
        builder.append(", txta_01_rg=");
        builder.append(txta_01_rg);
        builder.append(", txta_02_en=");
        builder.append(txta_02_en);
        builder.append(", txta_02_rg=");
        builder.append(txta_02_rg);
        builder.append(", date_01=");
        builder.append(date_01);
        builder.append(", date_02=");
        builder.append(date_02);
        builder.append(", date_03=");
        builder.append(date_03);
        builder.append(", date_04=");
        builder.append(date_04);
        builder.append(", att_01=");
        builder.append(att_01);
        builder.append(", att_02=");
        builder.append(att_02);
        builder.append(", profile_img=");
        builder.append(profile_img_path);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", langId=");
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
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the subLinkFieldDtlOrd
     */
    public Double getSubLinkFieldDtlOrd() {
        return subLinkFieldDtlOrd;
    }

    /**
     * @param subLinkFieldDtlOrd the subLinkFieldDtlOrd to set
     */
    public void setSubLinkFieldDtlOrd(final Double subLinkFieldDtlOrd) {
        this.subLinkFieldDtlOrd = subLinkFieldDtlOrd;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(final String img_path) {
        this.img_path = img_path;
    }

    public String getAtt_path() {
        return att_path;
    }

    public void setAtt_path(final String att_path) {
        this.att_path = att_path;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(final String video_path) {
        this.video_path = video_path;
    }

    public String getAtt_video_path() {
        return att_video_path;
    }

    public void setAtt_video_path(final String att_video_path) {
        this.att_video_path = att_video_path;
    }

    @Override
    public int getLangId() {

        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    public String getTxta_03_ren_blob() {
        return txta_03_ren_blob;
    }

    public void setTxta_03_ren_blob(String txta_03_ren_blob) {
        this.txta_03_ren_blob = txta_03_ren_blob;
    }

    public String getTxta_03_en_nnclob() {
        return txta_03_en_nnclob;
    }

    public void setTxta_03_en_nnclob(String txta_03_en_nnclob) {
        this.txta_03_en_nnclob = txta_03_en_nnclob;
    }

    public void setTxt_03_rg(String txt_03_rg) {
        this.txt_03_rg = txt_03_rg;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    @Transient
    private String txta_en_nnclob;

    public String getTxta_en_nnclob() {
        return txta_en_nnclob;
    }

    public void setTxta_en_nnclob(String txta_en_nnclob) {
        this.txta_en_nnclob = txta_en_nnclob;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINK_FIELDS_DTL", "SUB_LINK_FIELD_DTL_ID" };
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsHighlighted() {
		return isHighlighted;
	}

	public void setIsHighlighted(String isHighlighted) {
		this.isHighlighted = isHighlighted;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getLink_01() {
		return link_01;
	}

	public void setLink_01(String link_01) {
		this.link_01 = link_01;
	}

	public String getLink_02() {
		return link_02;
	}

	public void setLink_02(String link_02) {
		this.link_02 = link_02;
	}
	
	public String getLastApprovedBy() {
		return lastApprovedBy;
	}

	public void setLastApprovedBy(String lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}


	public Date getLastApprovedDate() {
		return lastApprovedDate;
	}

	public void setLastApprovedDate(Date lastApprovedDate) {
		this.lastApprovedDate = lastApprovedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


}
