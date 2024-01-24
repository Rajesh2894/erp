package com.abm.mainet.account.domain;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_AC_VOUCHER_POST_MASTER"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_AC_VOUCHER_POST_MASTER")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbAcVoucherPostMasterEntity.countAll", query = "SELECT COUNT(x) FROM TbAcVoucherPostMasterEntity x")
})
public class TbAcVoucherPostMasterEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VP_ID")
    private Long vpId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "TRAN_TOKENNO")
    private String tranTokenno;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRAN_DATE")
    private Date tranDate;

    @Column(name = "TRANTYPE_CPD_ID")
    private Long trantypeCpdId;

    @Column(name = "VOUCHERSUBTYPE_CPD_ID")
    private Long vouchersubtypeCpdId;

    @Column(name = "MODE_CPD_ID")
    private Long modeCpdId;

    @Column(name = "ACCOUNTENTRYTYPE_CPD_ID")
    private Long accountentrytypeCpdId;

    @Column(name = "POSTINGTYPE_CPD_ID")
    private Long postingtypeCpdId;

    @Temporal(TemporalType.DATE)
    @Column(name = "POSTING_DATE")
    private Date postingDate;

    @Column(name = "POSTING_REMARK")
    private String postingRemark;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    @Column(name = "FIELD_ID", precision = 12, scale = 0, nullable = true)
    private Long fieldId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptId;

    @Column(name = "EMP_ID", precision = 12, scale = 0, nullable = true)
    private Long empId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vpId", cascade = CascadeType.ALL)
    private List<TbAcVoucherPostDetailEntity> listOfTbAcVoucherPostDetail = new ArrayList<>(0);

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbAcVoucherPostMasterEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setVpId(final Long vpId) {
        this.vpId = vpId;
    }

    public Long getVpId() {
        return vpId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "master", cascade = CascadeType.ALL)
    private final List<AccountVoucherEntryDetailsEntity> details = new ArrayList<>(0);

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_VOUCHER_POST_MASTER", "VP_ID" };
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : TRAN_TOKENNO ( NVARCHAR2 )
    public void setTranTokenno(final String tranTokenno) {
        this.tranTokenno = tranTokenno;
    }

    public String getTranTokenno() {
        return tranTokenno;
    }

    // --- DATABASE MAPPING : TRAN_DATE ( DATE )
    public void setTranDate(final Date tranDate) {
        this.tranDate = tranDate;
    }

    public Date getTranDate() {
        return tranDate;
    }

    // --- DATABASE MAPPING : TRANTYPE_CPD_ID ( NUMBER )
    public void setTrantypeCpdId(final Long trantypeCpdId) {
        this.trantypeCpdId = trantypeCpdId;
    }

    public Long getTrantypeCpdId() {
        return trantypeCpdId;
    }

    // --- DATABASE MAPPING : VOUCHERSUBTYPE_CPD_ID ( NUMBER )
    public void setVouchersubtypeCpdId(final Long vouchersubtypeCpdId) {
        this.vouchersubtypeCpdId = vouchersubtypeCpdId;
    }

    public Long getVouchersubtypeCpdId() {
        return vouchersubtypeCpdId;
    }

    // --- DATABASE MAPPING : MODE_CPD_ID ( NUMBER )
    public void setModeCpdId(final Long modeCpdId) {
        this.modeCpdId = modeCpdId;
    }

    public Long getModeCpdId() {
        return modeCpdId;
    }

    // --- DATABASE MAPPING : ACCOUNTENTRYTYPE_CPD_ID ( NUMBER )
    public void setAccountentrytypeCpdId(final Long accountentrytypeCpdId) {
        this.accountentrytypeCpdId = accountentrytypeCpdId;
    }

    public Long getAccountentrytypeCpdId() {
        return accountentrytypeCpdId;
    }

    // --- DATABASE MAPPING : POSTINGTYPE_CPD_ID ( NUMBER )
    public void setPostingtypeCpdId(final Long postingtypeCpdId) {
        this.postingtypeCpdId = postingtypeCpdId;
    }

    public Long getPostingtypeCpdId() {
        return postingtypeCpdId;
    }

    // --- DATABASE MAPPING : POSTING_DATE ( DATE )
    public void setPostingDate(final Date postingDate) {
        this.postingDate = postingDate;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    // --- DATABASE MAPPING : POSTING_REMARK ( NVARCHAR2 )
    public void setPostingRemark(final String postingRemark) {
        this.postingRemark = postingRemark;
    }

    public String getPostingRemark() {
        return postingRemark;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : CREATED_BY ( NUMBER )
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    // --- DATABASE MAPPING : CREATED_DATE ( DATE )
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : FI04_N1 ( NUMBER )
    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    // --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    // --- DATABASE MAPPING : FI04_D1 ( DATE )
    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    // --- DATABASE MAPPING : FI04_LO1 ( CHAR )
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    public void setListOfTbAcVoucherPostDetail(final List<TbAcVoucherPostDetailEntity> listOfTbAcVoucherPostDetail) {
        this.listOfTbAcVoucherPostDetail = listOfTbAcVoucherPostDetail;
    }

    public List<TbAcVoucherPostDetailEntity> getListOfTbAcVoucherPostDetail() {
        return listOfTbAcVoucherPostDetail;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(vpId);
        sb.append("]:");
        sb.append(tranTokenno);
        sb.append("|");
        sb.append(tranDate);
        sb.append("|");
        sb.append(trantypeCpdId);
        sb.append("|");
        sb.append(vouchersubtypeCpdId);
        sb.append("|");
        sb.append(modeCpdId);
        sb.append("|");
        sb.append(accountentrytypeCpdId);
        sb.append("|");
        sb.append(postingtypeCpdId);
        sb.append("|");
        sb.append(postingDate);
        sb.append("|");
        sb.append(fieldId);
        sb.append("|");
        sb.append(dpDeptId);
        sb.append("|");
        sb.append(postingRemark);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        return sb.toString();
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the dpDeptId
     */
    public Long getDpDeptId() {
        return dpDeptId;
    }

    /**
     * @param dpDeptId the dpDeptId to set
     */
    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    /**
     * @return the empId
     */
    public Long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

}
