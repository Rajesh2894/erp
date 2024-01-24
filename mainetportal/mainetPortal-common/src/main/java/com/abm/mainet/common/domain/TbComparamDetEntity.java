package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/***
 * Persistent class for entity stored in table"TB_COMPARAM_DET"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_COMPARAM_DET")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbComparamDetEntity.countAll", query = "SELECT COUNT(x) FROM TbComparamDetEntity x")
})
public class TbComparamDetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CPD_ID", nullable = false)
    private Long cpdId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CPD_DESC", nullable = false)
    private String cpdDesc;

    @Column(name = "CPD_VALUE")
    private String cpdValue;

    @Column(name = "CPD_STATUS", nullable = false)
    private String cpdStatus;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "CPD_DEFAULT", length = 1)
    private String cpdDefault;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CPD_DESC_MAR")
    private String cpdDescMar;

    @Column(name = "CPD_OTHERS", length = 60)
    private String cpdOthers;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "CPM_ID", referencedColumnName = "CPM_ID")
    private TbComparamMasEntity tbComparamMas;

    @Transient
    private List<Department> listOfTbDepartment;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbComparamDetEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setCpdId(Long cpdId) {
        this.cpdId = cpdId;
    }

    public Long getCpdId() {
        return this.cpdId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    // --- DATABASE MAPPING : CPD_DESC ( NVARCHAR2 )
    public void setCpdDesc(String cpdDesc) {
        this.cpdDesc = cpdDesc;
    }

    public String getCpdDesc() {
        return this.cpdDesc;
    }

    // --- DATABASE MAPPING : CPD_VALUE ( NVARCHAR2 )
    public void setCpdValue(String cpdValue) {
        this.cpdValue = cpdValue;
    }

    public String getCpdValue() {
        return this.cpdValue;
    }

    // --- DATABASE MAPPING : CPD_STATUS ( NVARCHAR2 )
    public void setCpdStatus(String cpdStatus) {
        this.cpdStatus = cpdStatus;
    }

    public String getCpdStatus() {
        return this.cpdStatus;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return this.langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return this.lmoddate;
    }

    // --- DATABASE MAPPING : CPD_DEFAULT ( CHAR )
    public void setCpdDefault(String cpdDefault) {
        this.cpdDefault = cpdDefault;
    }

    public String getCpdDefault() {
        return this.cpdDefault;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    // --- DATABASE MAPPING : CPD_DESC_MAR ( NVARCHAR2 )
    public void setCpdDescMar(String cpdDescMar) {
        this.cpdDescMar = cpdDescMar;
    }

    public String getCpdDescMar() {
        return this.cpdDescMar;
    }

    // --- DATABASE MAPPING : CPD_OTHERS ( VARCHAR2 )
    public void setCpdOthers(String cpdOthers) {
        this.cpdOthers = cpdOthers;
    }

    public String getCpdOthers() {
        return this.cpdOthers;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    /**
     * @return the tbComparamMas
     */
    public TbComparamMasEntity getTbComparamMas() {
        return tbComparamMas;
    }

    /**
     * @param tbComparamMas the tbComparamMas to set
     */
    public void setTbComparamMas(TbComparamMasEntity tbComparamMas) {
        this.tbComparamMas = tbComparamMas;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_COMPARAM_DET", "CPD_ID" };
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(cpdId);
        sb.append("]:");
        sb.append(orgid);
        sb.append("|");
        sb.append(cpdDesc);
        sb.append("|");
        sb.append(cpdValue);
        sb.append("|");
        sb.append(cpdStatus);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(cpdDefault);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cpdDescMar);
        sb.append("|");
        sb.append(cpdOthers);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

}
