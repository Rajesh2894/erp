package com.abm.mainet.payment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author hiren.poriya
 * @Since 17-Jul-2018
 */
@Entity
@Table(name = "TB_PG_BANK_PARAMETER_DETAIL")
public class PGBankParameter implements Serializable {

    private static final long serialVersionUID = 8662323761913226753L;

    @Id
    @GenericGenerator(name = "generator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "generator")
    @Column(name = "PG_PRAM_DET_ID", precision = 15, scale = 0, nullable = false)
    private Long pgPramDetId;

    @Column(name = "PG_ID", precision = 15, scale = 0, nullable = true)
    private long pgId;

    @Column(name = "PAR_NAME", length = 500, nullable = true)
    private String parName;

    @Column(name = "PAR_VALUE", length = 500, nullable = true)
    private String paraValue;

    @Column(name = "PAR_STATUS", length = 1, nullable = true)
    private String parStatus;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "UPDATED_BY", nullable = false, updatable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "COMM_N1", precision = 15, scale = 0, nullable = true)
    private Long commN1;

    @Column(name = "COMM_V1", length = 100, nullable = true)
    private String commV1;

    @Column(name = "COMM_D1", nullable = true)
    private Date commD1;

    @Column(name = "COMM_LO1", length = 1, nullable = true)
    private String commLo1;

    public Long getPgPramDetId() {
        return pgPramDetId;
    }

    public void setPgPramDetId(Long pgPramDetId) {
        this.pgPramDetId = pgPramDetId;
    }

    public long getPgId() {
        return pgId;
    }

    public void setPgId(long pgId) {
        this.pgId = pgId;
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

    public String getParStatus() {
        return parStatus;
    }

    public void setParStatus(String parStatus) {
        this.parStatus = parStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getCommN1() {
        return commN1;
    }

    public void setCommN1(Long commN1) {
        this.commN1 = commN1;
    }

    public String getCommV1() {
        return commV1;
    }

    public void setCommV1(String commV1) {
        this.commV1 = commV1;
    }

    public Date getCommD1() {
        return commD1;
    }

    public void setCommD1(Date commD1) {
        this.commD1 = commD1;
    }

    public String getCommLo1() {
        return commLo1;
    }

    public void setCommLo1(String commLo1) {
        this.commLo1 = commLo1;
    }

    public String[] getPkValues() {

        return new String[] { "CFC", "TB_PG_BANK_PARAMETER_DETAIL", "PG_PRAM_DET_ID" };
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

}
