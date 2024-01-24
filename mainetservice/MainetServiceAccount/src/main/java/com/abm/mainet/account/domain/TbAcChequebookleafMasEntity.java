package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "TB_AC_CHEQUEBOOKLEAF_MAS")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbAcChequebookleafMasEntity.countAll", query = "SELECT COUNT(x) FROM TbAcChequebookleafMasEntity x")
})
public class TbAcChequebookleafMasEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CHEQUEBOOK_ID", nullable = false)
    private Long chequebookId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    /*
     * @Column(name = "ULB_BANKID") private Long bmBankid;
     */

    @Column(name = "BA_ACCOUNTID")
    private Long baAccountid;

    @Column(name = "FROM_CHEQUE_NO", nullable = false, length = 12)
    private String fromChequeNo;

    @Column(name = "TO_CHEQUE_NO", nullable = false, length = 12)
    private String toChequeNo;

    @Column(name = "EMPID")
    private Long empid;

    @Column(name = "CHECKBOOK_LEAVE", nullable = false)
    private Long checkbookLeave;

    @Temporal(TemporalType.DATE)
    @Column(name = "RCPT_CHQBOOK_DATE")
    private Date rcptChqbookDate;

    @Column(name = "ISSUER_EMPID")
    private Long issuerEmpid;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHKBOOK_RTN_DATE")
    private Date chkbookRtnDate;

    @Column(name = "CHECK_BOOK_RETURN", length = 1)
    private String checkBookReturn;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
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

    @Column(name = "RETURN_REMARK")
    private String returnRemark;

    @Temporal(TemporalType.DATE)
    @Column(name = "ISSUED_DATE")
    private Date chequeIssueDate;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @OneToMany(mappedBy = "tbAcChequebookleafMas", targetEntity = TbAcChequebookleafDetEntity.class, cascade = CascadeType.ALL)
    private List<TbAcChequebookleafDetEntity> listOfTbAcChequebookleafDet;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbAcChequebookleafMasEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setChequebookId(final Long chequebookId) {
        this.chequebookId = chequebookId;
    }

    public Long getChequebookId() {
        return chequebookId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : BM_BANKID ( NUMBER )

    // --- DATABASE MAPPING : BA_ACCOUNTID ( NUMBER )
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    public Long getBaAccountid() {
        return baAccountid;
    }

    // --- DATABASE MAPPING : FROM_CHEQUE_NO ( VARCHAR2 )
    public void setFromChequeNo(final String fromChequeNo) {
        this.fromChequeNo = fromChequeNo;
    }

    public String getFromChequeNo() {
        return fromChequeNo;
    }

    // --- DATABASE MAPPING : TO_CHEQUE_NO ( VARCHAR2 )
    public void setToChequeNo(final String toChequeNo) {
        this.toChequeNo = toChequeNo;
    }

    public String getToChequeNo() {
        return toChequeNo;
    }

    // --- DATABASE MAPPING : EMPID ( NUMBER )
    public void setEmpid(final Long empid) {
        this.empid = empid;
    }

    public Long getEmpid() {
        return empid;
    }

    // --- DATABASE MAPPING : CHECKBOOK_LEAVE ( NUMBER )
    public void setCheckbookLeave(final Long checkbookLeave) {
        this.checkbookLeave = checkbookLeave;
    }

    public Long getCheckbookLeave() {
        return checkbookLeave;
    }

    // --- DATABASE MAPPING : RCPT_CHQBOOK_DATE ( DATE )
    public void setRcptChqbookDate(final Date rcptChqbookDate) {
        this.rcptChqbookDate = rcptChqbookDate;
    }

    public Date getRcptChqbookDate() {
        return rcptChqbookDate;
    }

    // --- DATABASE MAPPING : ISSUER_EMPID ( NUMBER )
    public void setIssuerEmpid(final Long issuerEmpid) {
        this.issuerEmpid = issuerEmpid;
    }

    public Long getIssuerEmpid() {
        return issuerEmpid;
    }

    // --- DATABASE MAPPING : CHKBOOK_RTN_DATE ( DATE )
    public void setChkbookRtnDate(final Date chkbookRtnDate) {
        this.chkbookRtnDate = chkbookRtnDate;
    }

    public Date getChkbookRtnDate() {
        return chkbookRtnDate;
    }

    // --- DATABASE MAPPING : CHECK_BOOK_RETURN ( CHAR )
    public void setCheckBookReturn(final String checkBookReturn) {
        this.checkBookReturn = checkBookReturn;
    }

    public String getCheckBookReturn() {
        return checkBookReturn;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
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
    public void setListOfTbAcChequebookleafDet(final List<TbAcChequebookleafDetEntity> listOfTbAcChequebookleafDet) {
        this.listOfTbAcChequebookleafDet = listOfTbAcChequebookleafDet;
    }

    public List<TbAcChequebookleafDetEntity> getListOfTbAcChequebookleafDet() {
        return listOfTbAcChequebookleafDet;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(final String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public Date getChequeIssueDate() {
        return chequeIssueDate;
    }

    public void setChequeIssueDate(final Date chequeIssueDate) {
        this.chequeIssueDate = chequeIssueDate;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(chequebookId);
        sb.append("]:");
        sb.append(baAccountid);
        sb.append("|");
        sb.append(fromChequeNo);
        sb.append("|");
        sb.append(toChequeNo);
        sb.append("|");
        sb.append(empid);
        sb.append("|");
        sb.append(checkbookLeave);
        sb.append("|");
        sb.append(rcptChqbookDate);
        sb.append("|");
        sb.append(issuerEmpid);
        sb.append("|");
        sb.append(chkbookRtnDate);
        sb.append("|");
        sb.append(checkBookReturn);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
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

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_CHEQUEBOOKLEAF_MAS", "CHEQUEBOOK_ID" };
    }

}
