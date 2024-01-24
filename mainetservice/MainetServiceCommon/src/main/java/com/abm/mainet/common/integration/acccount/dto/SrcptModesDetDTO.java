package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SrcptModesDetDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SrcptModesDetDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class SrcptModesDetDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1004036403242813700L;

    @XmlElement(name="rdModesid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long rdModesid;
    @XmlElement(name="cpdFeemode",namespace="http://service.soap.account.mainet.abm.com/")
    private Long cpdFeemode;
    @XmlElement(name="baAccountid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long baAccountid;
    @XmlElement(name="tranRefNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String tranRefNumber;
    @XmlElement(name="tranRefDatetemp",namespace="http://service.soap.account.mainet.abm.com/")
    private String tranRefDatetemp;
    @XmlElement(name="rdAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal rdAmount;

    @XmlElement(name="CpdFeemodeDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String CpdFeemodeDesc;
    @XmlElement(name="cpdFeemodeCode",namespace="http://service.soap.account.mainet.abm.com/")
    private String cpdFeemodeCode;

    @XmlElement(name="baAccountidDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String baAccountidDesc;
    @XmlElement(name="cbBankidDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String cbBankidDesc;
    @XmlElement(name="cbBankid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long cbBankid;
    @XmlElement(name="rdChequedddate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date rdChequedddate;
    @XmlElement(name="rdChequedddatetemp",namespace="http://service.soap.account.mainet.abm.com/")
    private String rdChequedddatetemp;
    @XmlElement(name="tranRefDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date tranRefDate;
    @XmlElement(name="budgetCodeId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long budgetCodeId;
    @XmlElement(name="rdDrawnon",namespace="http://service.soap.account.mainet.abm.com/")
    private String rdDrawnon;

    public Long getRdModesid() {
        return rdModesid;
    }

    public void setRdModesid(final Long rdModesid) {
        this.rdModesid = rdModesid;
    }

    public Long getCpdFeemode() {
        return cpdFeemode;
    }

    public void setCpdFeemode(final Long cpdFeemode) {
        this.cpdFeemode = cpdFeemode;
    }

    public Long getBaAccountid() {
        return baAccountid;
    }

    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    public String getTranRefNumber() {
        return tranRefNumber;
    }

    public void setTranRefNumber(final String tranRefNumber) {
        this.tranRefNumber = tranRefNumber;
    }

    public String getTranRefDatetemp() {
        return tranRefDatetemp;
    }

    public void setTranRefDatetemp(final String tranRefDatetemp) {
        this.tranRefDatetemp = tranRefDatetemp;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(final BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getCpdFeemodeDesc() {
        return CpdFeemodeDesc;
    }

    public void setCpdFeemodeDesc(final String cpdFeemodeDesc) {
        CpdFeemodeDesc = cpdFeemodeDesc;
    }

    public String getCpdFeemodeCode() {
        return cpdFeemodeCode;
    }

    public void setCpdFeemodeCode(final String cpdFeemodeCode) {
        this.cpdFeemodeCode = cpdFeemodeCode;
    }

    public String getBaAccountidDesc() {
        return baAccountidDesc;
    }

    public void setBaAccountidDesc(final String baAccountidDesc) {
        this.baAccountidDesc = baAccountidDesc;
    }

    public String getCbBankidDesc() {
        return cbBankidDesc;
    }

    public void setCbBankidDesc(final String cbBankidDesc) {
        this.cbBankidDesc = cbBankidDesc;
    }

    public Long getCbBankid() {
        return cbBankid;
    }

    public void setCbBankid(final Long cbBankid) {
        this.cbBankid = cbBankid;
    }

    public Date getRdChequedddate() {
        return rdChequedddate;
    }

    public void setRdChequedddate(final Date rdChequedddate) {
        this.rdChequedddate = rdChequedddate;
    }

    public String getRdChequedddatetemp() {
        return rdChequedddatetemp;
    }

    public void setRdChequedddatetemp(final String rdChequedddatetemp) {
        this.rdChequedddatetemp = rdChequedddatetemp;
    }

    public Date getTranRefDate() {
        return tranRefDate;
    }

    public void setTranRefDate(final Date tranRefDate) {
        this.tranRefDate = tranRefDate;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getRdDrawnon() {
        return rdDrawnon;
    }

    public void setRdDrawnon(String rdDrawnon) {
        this.rdDrawnon = rdDrawnon;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SrcptModesDetDTO [rdModesid=");
        builder.append(rdModesid);
        builder.append(", cpdFeemode=");
        builder.append(cpdFeemode);
        builder.append(", baAccountid=");
        builder.append(baAccountid);
        builder.append(", tranRefNumber=");
        builder.append(tranRefNumber);
        builder.append(", tranRefDatetemp=");
        builder.append(tranRefDatetemp);
        builder.append(", rdAmount=");
        builder.append(rdAmount);
        builder.append(", CpdFeemodeDesc=");
        builder.append(CpdFeemodeDesc);
        builder.append(", cpdFeemodeCode=");
        builder.append(cpdFeemodeCode);
        builder.append(", baAccountidDesc=");
        builder.append(baAccountidDesc);
        builder.append(", cbBankidDesc=");
        builder.append(cbBankidDesc);
        builder.append(", cbBankid=");
        builder.append(cbBankid);
        builder.append(", rdChequedddate=");
        builder.append(rdChequedddate);
        builder.append(", rdChequedddatetemp=");
        builder.append(rdChequedddatetemp);
        builder.append(", tranRefDate=");
        builder.append(tranRefDate);
        builder.append(", budgetCodeId=");
        builder.append(budgetCodeId);
        builder.append(", rdDrawnon=");
        builder.append(rdDrawnon);
        builder.append("]");
        return builder.toString();
    }

}
