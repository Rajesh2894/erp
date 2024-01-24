package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SW_DESLUDGING")
public class LogBookDesludgingDetail implements Serializable {
    private static final long serialVersionUID = 2282298322099133546L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DES_ID")
    private Long desId;

    @Column(name = "DES_MONTH")
    private String desMonth;

    @Column(name = "DES_YEAR")
    private String desYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "DES_Date")
    private Date desDate;

    @Column(name = "DES_DRIVER_NAME")
    private String desDriverName;

    @Column(name = "DES_VNO")
    private String desVno;

    @Column(name = "DES_DRIVER_MOBNO")
    private String desDriverMobno;

    @Column(name = "DES_APPNAME")
    private String desAppname;

    @Column(name = "DES_APPADDRESS")
    private String desAppaddress;

    @Column(name = "DES_APPMOBNO")
    private String desAppmobno;

    @Column(name = "DES_TNTRIP")
    private Long desTntrip;

    @Column(name = "DES_TAMT")
    private BigDecimal desTamt;

    @Column(name = "DES_CRRECEIPTNO")
    private String desCrreceiptno;

    @Column(name = "DES_NRNO")
    private String desNrno;

    @Column(name = "DES_SWNAME")
    private String desSwname;

    @Column(name = "DES_CTOQTY")
    private BigDecimal desCtoqty;

    @Column(name = "DES_PONAME")
    private String desPoname;

    @Column(name = "DES_SWCOM")
    private BigDecimal desSwcom;

    @Column(name = "DES_WWCOM")
    private BigDecimal desWwcom;

    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public LogBookDesludgingDetail() {
    }

    public Long getDesId() {
        return desId;
    }

    public void setDesId(Long desId) {
        this.desId = desId;
    }

    public String getDesMonth() {
        return desMonth;
    }

    public void setDesMonth(String desMonth) {
        this.desMonth = desMonth;
    }

    public String getDesYear() {
        return desYear;
    }

    public void setDesYear(String desYear) {
        this.desYear = desYear;
    }

    public Date getDesDate() {
        return desDate;
    }

    public void setDesDate(Date desDate) {
        this.desDate = desDate;
    }

    public String getDesDriverName() {
        return desDriverName;
    }

    public void setDesDriverName(String desDriverName) {
        this.desDriverName = desDriverName;
    }

    public String getDesVno() {
        return desVno;
    }

    public void setDesVno(String desVno) {
        this.desVno = desVno;
    }

    public String getDesDriverMobno() {
        return desDriverMobno;
    }

    public void setDesDriverMobno(String desDriverMobno) {
        this.desDriverMobno = desDriverMobno;
    }

    public String getDesAppname() {
        return desAppname;
    }

    public void setDesAppname(String desAppname) {
        this.desAppname = desAppname;
    }

    public String getDesAppaddress() {
        return desAppaddress;
    }

    public void setDesAppaddress(String desAppaddress) {
        this.desAppaddress = desAppaddress;
    }

    public String getDesAppmobno() {
        return desAppmobno;
    }

    public void setDesAppmobno(String desAppmobno) {
        this.desAppmobno = desAppmobno;
    }

    public Long getDesTntrip() {
        return desTntrip;
    }

    public void setDesTntrip(Long desTntrip) {
        this.desTntrip = desTntrip;
    }

    public BigDecimal getDesTamt() {
        return desTamt;
    }

    public void setDesTamt(BigDecimal desTamt) {
        this.desTamt = desTamt;
    }

    public String getDesCrreceiptno() {
        return desCrreceiptno;
    }

    public void setDesCrreceiptno(String desCrreceiptno) {
        this.desCrreceiptno = desCrreceiptno;
    }

    public String getDesNrno() {
        return desNrno;
    }

    public void setDesNrno(String desNrno) {
        this.desNrno = desNrno;
    }

    public String getDesSwname() {
        return desSwname;
    }

    public void setDesSwname(String desSwname) {
        this.desSwname = desSwname;
    }

    public BigDecimal getDesCtoqty() {
        return desCtoqty;
    }

    public void setDesCtoqty(BigDecimal desCtoqty) {
        this.desCtoqty = desCtoqty;
    }

    public String getDesPoname() {
        return desPoname;
    }

    public void setDesPoname(String desPoname) {
        this.desPoname = desPoname;
    }

    public BigDecimal getDesSwcom() {
        return desSwcom;
    }

    public void setDesSwcom(BigDecimal desSwcom) {
        this.desSwcom = desSwcom;
    }

    public BigDecimal getDesWwcom() {
        return desWwcom;
    }

    public void setDesWwcom(BigDecimal desWwcom) {
        this.desWwcom = desWwcom;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_DESLUDGING", "DES_ID" };
    }

}
