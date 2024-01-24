package com.abm.mainet.cms.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.FilterDef;
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
@Table(name = "TB_EIP_SUB_LINK_FIELDS_DTL_HIST")
@FilterDef(name = "filterSubLinkFieldDetailMapping", parameters = @ParamDef(name = "IS_DELETED_VAL", type = "string"))
@Primary
public class SubLinkFieldDetailsHistory extends BaseEntity {

    private static final long serialVersionUID = 2573496011462818449L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUB_LINK_FIELD_DTL_ID_H", nullable = false, precision = 12, scale = 0)
    private long hId;

    @Column(name = "SUB_LINK_FIELD_DTL_ID", nullable = false, precision = 12, scale = 0)
    private long id;

    @Column(name = "SUB_LINKS_MAS_ID", nullable = false)
    private long subLinkMaster;

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
     * For Form Option Fields
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

    @Column(name = "H_STATUS", length = 1, nullable = false)
    private String status;

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

    @Column(name = "LINK_01", nullable = true)
    private String link_01;
    
    @Column(name = "LINK_02", nullable = true)
    private String link_02;
    
	@Transient
    private String img_path;

    @Transient
    private String att_path;

    @Transient
    private String video_path;

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

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(final int fieldType) {
        this.fieldType = fieldType;
    }

    public SubLinkFieldDetailsHistory() {
        isDeleted = "N";
    }

    /**
     * @return the id
     */

    /**
     * @return the subLinkMaster
     */

    /**
     * @return the txt_01_en
     */
    public String getTxt_01_en() {
        return txt_01_en;
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

    public String getTxta_03_ren_blob() {
        return txta_03_ren_blob;
    }

    public void setTxta_03_ren_blob(String txta_03_ren_blob) {
        this.txta_03_ren_blob = txta_03_ren_blob;
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
     * @return the txt_04_en
     */
    public String getTxt_04_en() {
        return txt_04_en;
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

    public void setTxt_03_rg(String txt_03_rg) {
        this.txt_03_rg = txt_03_rg;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_SUB_LINK_FIELDS_DTL_HIST", "SUB_LINK_FIELD_DTL_ID_H" };
    }

    @Transient
    private String checkerFlag1;

    public String getCheckerFlag1() {
        return checkerFlag1;
    }

    public void setCheckerFlag1(String checkerFlag1) {
        this.checkerFlag1 = checkerFlag1;
    }

    public String getTxta_03_en_nnclob() {
        return txta_03_en_nnclob;
    }

    public void setTxta_03_en_nnclob(String txta_03_en_nnclob) {
        this.txta_03_en_nnclob = txta_03_en_nnclob;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
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

}
