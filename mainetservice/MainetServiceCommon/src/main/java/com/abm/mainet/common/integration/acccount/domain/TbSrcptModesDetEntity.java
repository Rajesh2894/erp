package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rahul.Yadav
 * @since 22 Mar 2016
 */
@Entity
@Table(name = "TB_RECEIPT_MODE")
public class TbSrcptModesDetEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7607203641996979608L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RD_MODESID", precision = 12, scale = 0, nullable = false)
    private long rdModesid;

    /*
     * @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = true) private Long rmRcptid;
     */

	/*
	 * @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "RM_RCPTID", nullable = false, referencedColumnName =
	 * "RM_RCPTID") private TbServiceReceiptMasEntity rmRcptid;
	 */
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "RM_RCPTID", nullable = false,  referencedColumnName = "RM_RCPTID")
    private TbServiceReceiptMasEntity rmRcptid;

    @Column(name = "CPD_FEEMODE", precision = 12, scale = 0, nullable = true)
    private Long cpdFeemode;

    @Column(name = "RD_CHEQUEDDNO", precision = 19, scale = 0, nullable = true)
    private Long rdChequeddno;

    @Column(name = "RD_CHEQUEDDDATE", nullable = true)
    private Date rdChequedddate;

    @Column(name = "RD_DRAWNON", length = 200, nullable = true)
    private String rdDrawnon;

    @Column(name = "BANKID", precision = 12, scale = 0, nullable = true)
    private Long cbBankid;

    @Column(name = "RD_AMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rdAmount;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    // @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // private int langId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "RD_V1", length = 100, nullable = true)
    private String rdV1;

    @Column(name = "RD_V2", length = 100, nullable = true)
    private String rdV2;

    @Column(name = "RD_V3", length = 100, nullable = true)
    private String rdV3;

    @Column(name = "RD_V4", length = 100, nullable = true)
    private String rdV4;

    @Column(name = "RD_V5", length = 100, nullable = true)
    private String rdV5;

    @Column(name = "RD_N1", precision = 15, scale = 0, nullable = true)
    private Long rdN1;

    @Column(name = "RD_N2", precision = 15, scale = 0, nullable = true)
    private Long rdN2;

    @Column(name = "RD_N3", precision = 15, scale = 0, nullable = true)
    private Long rdN3;

    @Column(name = "RD_N4", precision = 15, scale = 0, nullable = true)
    private Long rdN4;

    @Column(name = "RD_N5", precision = 15, scale = 0, nullable = true)
    private Long rdN5;

    @Column(name = "RD_D1", nullable = true)
    private Date rdD1;

    @Column(name = "RD_D2", nullable = true)
    private Date rdD2;

    @Column(name = "RD_D3", nullable = true)
    private Date rdD3;

    @Column(name = "RD_LO1", length = 1, nullable = true)
    private String rdLo1;

    @Column(name = "RD_LO2", length = 1, nullable = true)
    private String rdLo2;

    @Column(name = "RD_LO3", length = 1, nullable = true)
    private String rdLo3;

    @Column(name = "RD_SR_CHK_DIS", length = 1, nullable = true)
    private String rdSrChkDis;

    @Column(name = "RD_SR_CHK_DATE", nullable = true)
    private Date rdSrChkDate;

    @Column(name = "RD_SR_CHK_DIS_CHG", nullable = true)
    private Double rdSrChkDisChg;

    @Column(name = "RD_DISHONOR_REMARK", nullable = true)
    private String rd_dishonor_remark;

    @Column(name = "RD_OUTSTATION_CHQ", length = 1, nullable = true)
    private String rdOutstationChq;

    @Column(name = "TRAN_REF_NUMBER")
    private String tranRefNumber;

    @Column(name = "TRAN_REF_DATE", nullable = true)
    private Date tranRefDate;
    
    @Column(name = "BM_NO")
    private String billNo;

    @Column(name = "BM_IDNO")
    private Long billIdNo;

    @Column(name = "SUPP_BILL_ID")
    private Long supBillIdNo;

    @Column(name = "RD_INS_MICR_CODE")
    private Long insMrcrCode;

    @Column(name = "RD_CCN_ID")
    private Long csId;

    @Column(name = "ISDELETED")
    private String isDeleted;
    
    @Column(name = "RD_CHEQUE_STATUS")
    private Long checkStatus;
    
    @Column(name = "RD_ACCT_NO")
    private String rdActNo;
    

    // "baAccountid" (column "BA_ACCOUNTID") is not defined by itself because used as FK in a link

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    /*
     * @ManyToOne
     * @JoinColumn(name="BA_ACCOUNTID", referencedColumnName="BA_ACCOUNTID") private TbBankaccountEntity tbBankaccount;
     */

    @Column(name = "BA_ACCOUNTID", precision = 12, scale = 0, nullable = true)
    private Long baAccountid;

    public long getRdModesid() {
        return rdModesid;
    }

    public void setRdModesid(final long rdModesid) {
        this.rdModesid = rdModesid;
    }

    public Long getCpdFeemode() {
        return cpdFeemode;
    }

    public void setCpdFeemode(final Long cpdFeemode) {
        this.cpdFeemode = cpdFeemode;
    }

    public Long getRdChequeddno() {
        return rdChequeddno;
    }

    public void setRdChequeddno(final Long rdChequeddno) {
        this.rdChequeddno = rdChequeddno;
    }

    public Date getRdChequedddate() {
        return rdChequedddate;
    }

    public void setRdChequedddate(final Date rdChequedddate) {
        this.rdChequedddate = rdChequedddate;
    }

    public String getRdDrawnon() {
        return rdDrawnon;
    }

    public void setRdDrawnon(final String rdDrawnon) {
        this.rdDrawnon = rdDrawnon;
    }

    public Long getCbBankid() {
        return cbBankid;
    }

    public void setCbBankid(final Long cbBankid) {
        this.cbBankid = cbBankid;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(final BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /*
     * public int getLangId() { return langId; } public void setLangId(int langId) { this.langId = langId; }
     */

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getRdSrChkDis() {
        return rdSrChkDis;
    }

    public void setRdSrChkDis(final String rdSrChkDis) {
        this.rdSrChkDis = rdSrChkDis;
    }

    public String getRdOutstationChq() {
        return rdOutstationChq;
    }

    public void setRdOutstationChq(final String rdOutstationChq) {
        this.rdOutstationChq = rdOutstationChq;
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_RECEIPT_MODE", "RD_MODESID" };
    }

    public TbServiceReceiptMasEntity getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final TbServiceReceiptMasEntity rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    /**
     * @return the tranRefNumber
     */
    public String getTranRefNumber() {
        return tranRefNumber;
    }

    /**
     * @param tranRefNumber the tranRefNumber to set
     */
    public void setTranRefNumber(final String tranRefNumber) {
        this.tranRefNumber = tranRefNumber;
    }

    /**
     * @return the tranRefDate
     */
    public Date getTranRefDate() {
        return tranRefDate;
    }

    /**
     * @param tranRefDate the tranRefDate to set
     */
    public void setTranRefDate(final Date tranRefDate) {
        this.tranRefDate = tranRefDate;
    }

    /**
     * @return the baAccountid
     */
    public Long getBaAccountid() {
        return baAccountid;
    }

    /**
     * @param baAccountid the baAccountid to set
     */
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the rdV1
     */
    public String getRdV1() {
        return rdV1;
    }

    /**
     * @param rdV1 the rdV1 to set
     */
    public void setRdV1(final String rdV1) {
        this.rdV1 = rdV1;
    }

    /**
     * @return the rdV2
     */
    public String getRdV2() {
        return rdV2;
    }

    /**
     * @param rdV2 the rdV2 to set
     */
    public void setRdV2(final String rdV2) {
        this.rdV2 = rdV2;
    }

    /**
     * @return the rdV3
     */
    public String getRdV3() {
        return rdV3;
    }

    /**
     * @param rdV3 the rdV3 to set
     */
    public void setRdV3(final String rdV3) {
        this.rdV3 = rdV3;
    }

    /**
     * @return the rdV4
     */
    public String getRdV4() {
        return rdV4;
    }

    /**
     * @param rdV4 the rdV4 to set
     */
    public void setRdV4(final String rdV4) {
        this.rdV4 = rdV4;
    }

    /**
     * @return the rdV5
     */
    public String getRdV5() {
        return rdV5;
    }

    /**
     * @param rdV5 the rdV5 to set
     */
    public void setRdV5(final String rdV5) {
        this.rdV5 = rdV5;
    }

    /**
     * @return the rdN1
     */
    public Long getRdN1() {
        return rdN1;
    }

    /**
     * @param rdN1 the rdN1 to set
     */
    public void setRdN1(final Long rdN1) {
        this.rdN1 = rdN1;
    }

    /**
     * @return the rdN2
     */
    public Long getRdN2() {
        return rdN2;
    }

    /**
     * @param rdN2 the rdN2 to set
     */
    public void setRdN2(final Long rdN2) {
        this.rdN2 = rdN2;
    }

    /**
     * @return the rdN3
     */
    public Long getRdN3() {
        return rdN3;
    }

    /**
     * @param rdN3 the rdN3 to set
     */
    public void setRdN3(final Long rdN3) {
        this.rdN3 = rdN3;
    }

    /**
     * @return the rdN4
     */
    public Long getRdN4() {
        return rdN4;
    }

    /**
     * @param rdN4 the rdN4 to set
     */
    public void setRdN4(final Long rdN4) {
        this.rdN4 = rdN4;
    }

    /**
     * @return the rdN5
     */
    public Long getRdN5() {
        return rdN5;
    }

    /**
     * @param rdN5 the rdN5 to set
     */
    public void setRdN5(final Long rdN5) {
        this.rdN5 = rdN5;
    }

    /**
     * @return the rdD1
     */
    public Date getRdD1() {
        return rdD1;
    }

    /**
     * @param rdD1 the rdD1 to set
     */
    public void setRdD1(final Date rdD1) {
        this.rdD1 = rdD1;
    }

    /**
     * @return the rdD2
     */
    public Date getRdD2() {
        return rdD2;
    }

    /**
     * @param rdD2 the rdD2 to set
     */
    public void setRdD2(final Date rdD2) {
        this.rdD2 = rdD2;
    }

    /**
     * @return the rdD3
     */
    public Date getRdD3() {
        return rdD3;
    }

    /**
     * @param rdD3 the rdD3 to set
     */
    public void setRdD3(final Date rdD3) {
        this.rdD3 = rdD3;
    }

    /**
     * @return the rdLo1
     */
    public String getRdLo1() {
        return rdLo1;
    }

    /**
     * @param rdLo1 the rdLo1 to set
     */
    public void setRdLo1(final String rdLo1) {
        this.rdLo1 = rdLo1;
    }

    /**
     * @return the rdLo2
     */
    public String getRdLo2() {
        return rdLo2;
    }

    /**
     * @param rdLo2 the rdLo2 to set
     */
    public void setRdLo2(final String rdLo2) {
        this.rdLo2 = rdLo2;
    }

    /**
     * @return the rdLo3
     */
    public String getRdLo3() {
        return rdLo3;
    }

    /**
     * @param rdLo3 the rdLo3 to set
     */
    public void setRdLo3(final String rdLo3) {
        this.rdLo3 = rdLo3;
    }

    public Date getRdSrChkDate() {
        return rdSrChkDate;
    }

    public void setRdSrChkDate(final Date rdSrChkDate) {
        this.rdSrChkDate = rdSrChkDate;
    }

    public Double getRdSrChkDisChg() {
        return rdSrChkDisChg;
    }

    public void setRdSrChkDisChg(final Double rdSrChkDisChg) {
        this.rdSrChkDisChg = rdSrChkDisChg;
    }

    public String getRd_dishonor_remark() {
        return rd_dishonor_remark;
    }

    public void setRd_dishonor_remark(final String rd_dishonor_remark) {
        this.rd_dishonor_remark = rd_dishonor_remark;
    }

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Long getBillIdNo() {
		return billIdNo;
	}

	public void setBillIdNo(Long billIdNo) {
		this.billIdNo = billIdNo;
	}

	public Long getSupBillIdNo() {
		return supBillIdNo;
	}

	public void setSupBillIdNo(Long supBillIdNo) {
		this.supBillIdNo = supBillIdNo;
	}

	public Long getInsMrcrCode() {
		return insMrcrCode;
	}

	public void setInsMrcrCode(Long insMrcrCode) {
		this.insMrcrCode = insMrcrCode;
	}

	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Long checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRdActNo() {
		return rdActNo;
	}

	public void setRdActNo(String rdActNo) {
		this.rdActNo = rdActNo;
	}
    
    

}