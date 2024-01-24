package com.abm.mainet.water.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author deepika.pimpale
 *
 */
@Entity
@Table(name = "TB_WT_PLUMRENEWAL_REG")
public class PlumberRenewalRegisterMaster {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PLUM_RN_ID", precision = 12, scale = 0, nullable = false)
    private long plum_rn_id;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    private long plum_id;

    @Column(name = "RN_FROMDATE", nullable = true)
    private Date rn_fromdate;

    @Column(name = "RN_TODATE", nullable = true)
    private Date rn_todate;

    @Column(name = "RN_FEES", precision = 12, scale = 2, nullable = true)
    private long rn_fees;

    @Column(name = "RN_DATE", nullable = true)
    private Date rn_date;

    @Column(name = "RN_REMARK", length = 200, nullable = true)
    private String rn_remark;

    @Column(name = "RN_ISSUED", length = 1, nullable = true)
    private String rn_issued;

    @Column(name = "RN_RCPTFLAG", length = 1, nullable = true)
    private String rn_rcptflag;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Organization id
    private Long orgid;

    @Column(name = "USER_ID", precision = 7, scale = 0, nullable = true)
    // comments : User id
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // comments : Language id
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private long apm_application_id;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMacUpd;

    @Column(name = "RM_RCPTID", precision = 15, scale = 0, nullable = true)
    private long rm_rcptid;

    @Column(name = "RN_ENTRY_FLAG", length = 1, nullable = true)
    // comments : stores ip information
    private String rn_entry_flag;

    @Column(name = "RN_PORTED", length = 1, nullable = true)
    // comments : stores ip information
    private String rn_ported;

    @Column(name = "WT_V3", length = 100, nullable = true)
    // comments : stores ip information
    private String wt_v3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    // comments : stores ip information
    private String wt_v4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    // comments : stores ip information
    private String wt_v5;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private long wt_n1;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private long wt_n2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private long wt_n3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private long wt_n4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private long wt_n5;

    @Column(name = "WT_D1", nullable = true)
    private Date wt_d1;

    @Column(name = "WT_D2", nullable = true)
    private Date wt_d2;

    @Column(name = "WT_D3", nullable = true)
    private Date wt_d3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private Date wt_lo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private Date wt_lo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private Date wt_lo3;

    public long getPlum_rn_id() {
        return plum_rn_id;
    }

    public void setPlum_rn_id(final long plum_rn_id) {
        this.plum_rn_id = plum_rn_id;
    }

    public long getPlum_id() {
        return plum_id;
    }

    public void setPlum_id(final long plum_id) {
        this.plum_id = plum_id;
    }

    public Date getRn_fromdate() {
        return rn_fromdate;
    }

    public void setRn_fromdate(final Date rn_fromdate) {
        this.rn_fromdate = rn_fromdate;
    }

    public Date getRn_todate() {
        return rn_todate;
    }

    public void setRn_todate(final Date rn_todate) {
        this.rn_todate = rn_todate;
    }

    public long getRn_fees() {
        return rn_fees;
    }

    public void setRn_fees(final long rn_fees) {
        this.rn_fees = rn_fees;
    }

    public Date getRn_date() {
        return rn_date;
    }

    public void setRn_date(final Date rn_date) {
        this.rn_date = rn_date;
    }

    public String getRn_remark() {
        return rn_remark;
    }

    public void setRn_remark(final String rn_remark) {
        this.rn_remark = rn_remark;
    }

    public String getRn_issued() {
        return rn_issued;
    }

    public void setRn_issued(final String rn_issued) {
        this.rn_issued = rn_issued;
    }

    public String getRn_rcptflag() {
        return rn_rcptflag;
    }

    public void setRn_rcptflag(final String rn_rcptflag) {
        this.rn_rcptflag = rn_rcptflag;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

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

    public long getApm_application_id() {
        return apm_application_id;
    }

    public void setApm_application_id(final long apm_application_id) {
        this.apm_application_id = apm_application_id;
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

    public long getRm_rcptid() {
        return rm_rcptid;
    }

    public void setRm_rcptid(final long rm_rcptid) {
        this.rm_rcptid = rm_rcptid;
    }

    public String getRn_entry_flag() {
        return rn_entry_flag;
    }

    public void setRn_entry_flag(final String rn_entry_flag) {
        this.rn_entry_flag = rn_entry_flag;
    }

    public String getRn_ported() {
        return rn_ported;
    }

    public void setRn_ported(final String rn_ported) {
        this.rn_ported = rn_ported;
    }

    public String getWt_v3() {
        return wt_v3;
    }

    public void setWt_v3(final String wt_v3) {
        this.wt_v3 = wt_v3;
    }

    public String getWt_v4() {
        return wt_v4;
    }

    public void setWt_v4(final String wt_v4) {
        this.wt_v4 = wt_v4;
    }

    public String getWt_v5() {
        return wt_v5;
    }

    public void setWt_v5(final String wt_v5) {
        this.wt_v5 = wt_v5;
    }

    public long getWt_n1() {
        return wt_n1;
    }

    public void setWt_n1(final long wt_n1) {
        this.wt_n1 = wt_n1;
    }

    public long getWt_n2() {
        return wt_n2;
    }

    public void setWt_n2(final long wt_n2) {
        this.wt_n2 = wt_n2;
    }

    public long getWt_n3() {
        return wt_n3;
    }

    public void setWt_n3(final long wt_n3) {
        this.wt_n3 = wt_n3;
    }

    public long getWt_n4() {
        return wt_n4;
    }

    public void setWt_n4(final long wt_n4) {
        this.wt_n4 = wt_n4;
    }

    public long getWt_n5() {
        return wt_n5;
    }

    public void setWt_n5(final long wt_n5) {
        this.wt_n5 = wt_n5;
    }

    public Date getWt_d1() {
        return wt_d1;
    }

    public void setWt_d1(final Date wt_d1) {
        this.wt_d1 = wt_d1;
    }

    public Date getWt_d2() {
        return wt_d2;
    }

    public void setWt_d2(final Date wt_d2) {
        this.wt_d2 = wt_d2;
    }

    public Date getWt_d3() {
        return wt_d3;
    }

    public void setWt_d3(final Date wt_d3) {
        this.wt_d3 = wt_d3;
    }

    public Date getWt_lo1() {
        return wt_lo1;
    }

    public void setWt_lo1(final Date wt_lo1) {
        this.wt_lo1 = wt_lo1;
    }

    public Date getWt_lo2() {
        return wt_lo2;
    }

    public void setWt_lo2(final Date wt_lo2) {
        this.wt_lo2 = wt_lo2;
    }

    public Date getWt_lo3() {
        return wt_lo3;
    }

    public void setWt_lo3(final Date wt_lo3) {
        this.wt_lo3 = wt_lo3;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_PLUMRENEWAL_REG", "PLUM_RN_ID" };
    }

}
