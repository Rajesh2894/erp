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
@Table(name = "TB_SW_ALLFINE")
public class AllFineLogBookDetails implements Serializable {
    private static final long serialVersionUID = 280562018070801868L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ALLF_ID")
    private Long allfId;

    @Column(name = "ALLF_MONTH")
    private String allfMonth;

    @Column(name = "ALLF_YEAR")
    private String allfYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "ALLF_Date")
    private Date allfDate;

    @Column(name = "ALLF_APPNAME")
    private String allfAppname;

    @Column(name = "ALLF_APPADDRESS")
    private String allfAppaddress;

    @Column(name = "ALLF_APPMOBNO")
    private String allfAppmobno;

    @Column(name = "ALLF_WardNo")
    private String allfWardNo;

    @Column(name = "ALLF_FTYPE")
    private String allfFtype;

    @Column(name = "ALLF_FSTYPE")
    private String allfFstype;

    @Column(name = "ALLF_FRBOOKNO")
    private String allfFrbookno;

    @Column(name = "ALLF_FTOAMT")
    private BigDecimal allfFtoamt;

    @Column(name = "ALLF_FRESFIBE")
    private String allfFresfibe;

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

    public AllFineLogBookDetails() {
    }

    public Long getAllfId() {
        return allfId;
    }

    public void setAllfId(Long allfId) {
        this.allfId = allfId;
    }

    public String getAllfMonth() {
        return allfMonth;
    }

    public void setAllfMonth(String allfMonth) {
        this.allfMonth = allfMonth;
    }

    public String getAllfYear() {
        return allfYear;
    }

    public void setAllfYear(String allfYear) {
        this.allfYear = allfYear;
    }

    public Date getAllfDate() {
        return allfDate;
    }

    public void setAllfDate(Date allfDate) {
        this.allfDate = allfDate;
    }

    public String getAllfAppname() {
        return allfAppname;
    }

    public void setAllfAppname(String allfAppname) {
        this.allfAppname = allfAppname;
    }

    public String getAllfAppaddress() {
        return allfAppaddress;
    }

    public void setAllfAppaddress(String allfAppaddress) {
        this.allfAppaddress = allfAppaddress;
    }

    public String getAllfAppmobno() {
        return allfAppmobno;
    }

    public void setAllfAppmobno(String allfAppmobno) {
        this.allfAppmobno = allfAppmobno;
    }

    public String getAllfWardNo() {
        return allfWardNo;
    }

    public void setAllfWardNo(String allfWardNo) {
        this.allfWardNo = allfWardNo;
    }

    public String getAllfFtype() {
        return allfFtype;
    }

    public void setAllfFtype(String allfFtype) {
        this.allfFtype = allfFtype;
    }

    public String getAllfFstype() {
        return allfFstype;
    }

    public void setAllfFstype(String allfFstype) {
        this.allfFstype = allfFstype;
    }

    public String getAllfFrbookno() {
        return allfFrbookno;
    }

    public void setAllfFrbookno(String allfFrbookno) {
        this.allfFrbookno = allfFrbookno;
    }

    public BigDecimal getAllfFtoamt() {
        return allfFtoamt;
    }

    public void setAllfFtoamt(BigDecimal allfFtoamt) {
        this.allfFtoamt = allfFtoamt;
    }

    public String getAllfFresfibe() {
        return allfFresfibe;
    }

    public void setAllfFresfibe(String allfFresfibe) {
        this.allfFresfibe = allfFresfibe;
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

        return new String[] { "SWM", "TB_SW_ALLFINE", "ALLF_ID" };
    }

}
