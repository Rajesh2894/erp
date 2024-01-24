package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 * @since 22 Mar 2016
 * @comment This table stores Recommedation details of new connection.
 */
@Entity
@Table(name = "TB_CSMRRCMD_MAS")
public class TBCsmrrCmdMas implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5598174793384467122L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CS_ID", precision = 14, scale = 0, nullable = false)
    private long csId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "distribution")
    private TbKCsmrInfoMH csIdnO;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;
    
    @Column(name = "COD_ID1", precision = 12, scale = 0, nullable = true)
    private Long codId1;

    @Column(name = "RC_DISTPRES", precision = 8, scale = 2, nullable = true)
    private Double rcDistpres;

    @Column(name = "RC_DISTTIMEFR", length = 5, nullable = true)
    private String rcDisttimefr;

    @Column(name = "RC_DISTTIMETO", length = 5, nullable = true)
    private String rcDisttimeto;

    @Column(name = "RC_DISTCCNDIF", precision = 8, scale = 3, nullable = true)
    private Double rcDistccndif;

    @Column(name = "RC_DAILYDISCHG", precision = 7, scale = 2, nullable = true)
    private Double rcDailydischg;

    @Column(name = "RC_GRANTED", length = 1, nullable = true)
    private String rcGranted;

    @Column(name = "RC_STATUS", length = 1, nullable = true)
    private String rcStatus;

    @Column(name = "RC_LENGTH", precision = 3, scale = 0, nullable = true)
    private Long rcLength;

    @Column(name = "RC_RECOMMENDED", length = 1, nullable = true)
    private String rcRecommended;

    @Column(name = "RC_DAILYDISCHGC", precision = 7, scale = 2, nullable = true)
    private Double rcDailydischgc;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "RC_RHGL", precision = 7, scale = 2, nullable = true)
    private Double rcRhgl;

    @Column(name = "RC_AHGL", precision = 7, scale = 2, nullable = true)
    private Double rcAhgl;

    @Column(name = "RC_DISP_WT", length = 250, nullable = true)
    private String rcDispWt;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

    @Column(name = "WT_D2", nullable = true)
    private Date wtD2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private String wtLo3;

    @Column(name = "INST_ID", length = 12, nullable = true)
    private Long instId;

    @Column(name = "COD_ID2", precision = 12, scale = 0, nullable = true)
    private Long codId2;

    @Column(name = "COD_ID3", precision = 12, scale = 0, nullable = true)
    private Long codId3;

    @Column(name = "COD_ID4", precision = 12, scale = 0, nullable = true)
    private Long codId4;

    @Column(name = "COD_ID5", precision = 12, scale = 0, nullable = true)
    private Long codId5;

    @Column(name = "RC_TOT_DIST_TIME", precision = 10, scale = 2, nullable = true)
    private Double rcTotdisttime;

    @Column(name="DIAMETER", precision = 15, scale = 2, nullable = true)
    private Double diameter;
    
    public String[] getPkValues() {
        return new String[] { "WT", "TB_CSMRRCMD_MAS", "CS_ID" };
    }

    /**
     * @param csId the csId to set
     */
    public void setCsId(final long csId) {
        this.csId = csId;
    }

    /**
     * @return the csIdn
     */
//    public TbKCsmrInfoMH getCsIdn() {
//        return csIdn;
//    }
//
//    /**
//     * @param csIdn the csIdn to set
//     */
//    public void setCsIdn(final TbKCsmrInfoMH csIdn) {
//        this.csIdn = csIdn;
//    }

    /**
     * @return the rcDistpres
     */
    public Double getRcDistpres() {
        return rcDistpres;
    }

    /**
     * @param rcDistpres the rcDistpres to set
     */
    public void setRcDistpres(final Double rcDistpres) {
        this.rcDistpres = rcDistpres;
    }

    /**
     * @return the rcDisttimefr
     */
    public String getRcDisttimefr() {
        return rcDisttimefr;
    }

    /**
     * @param rcDisttimefr the rcDisttimefr to set
     */
    public void setRcDisttimefr(final String rcDisttimefr) {
        this.rcDisttimefr = rcDisttimefr;
    }

    /**
     * @return the rcDisttimeto
     */
    public String getRcDisttimeto() {
        return rcDisttimeto;
    }

    /**
     * @param rcDisttimeto the rcDisttimeto to set
     */
    public void setRcDisttimeto(final String rcDisttimeto) {
        this.rcDisttimeto = rcDisttimeto;
    }

    /**
     * @return the rcDistccndif
     */
    public Double getRcDistccndif() {
        return rcDistccndif;
    }

    /**
     * @param rcDistccndif the rcDistccndif to set
     */
    public void setRcDistccndif(final Double rcDistccndif) {
        this.rcDistccndif = rcDistccndif;
    }

    /**
     * @return the rcDailydischg
     */
    public Double getRcDailydischg() {
        return rcDailydischg;
    }

    /**
     * @param rcDailydischg the rcDailydischg to set
     */
    public void setRcDailydischg(final Double rcDailydischg) {
        this.rcDailydischg = rcDailydischg;
    }

    /**
     * @return the rcGranted
     */
    public String getRcGranted() {
        return rcGranted;
    }

    /**
     * @param rcGranted the rcGranted to set
     */
    public void setRcGranted(final String rcGranted) {
        this.rcGranted = rcGranted;
    }

    /**
     * @return the rcStatus
     */
    public String getRcStatus() {
        return rcStatus;
    }

    /**
     * @param rcStatus the rcStatus to set
     */
    public void setRcStatus(final String rcStatus) {
        this.rcStatus = rcStatus;
    }

    /**
     * @return the rcLength
     */
    public Long getRcLength() {
        return rcLength;
    }

    /**
     * @param rcLength the rcLength to set
     */
    public void setRcLength(final Long rcLength) {
        this.rcLength = rcLength;
    }

    /**
     * @return the rcRecommended
     */
    public String getRcRecommended() {
        return rcRecommended;
    }

    /**
     * @param rcRecommended the rcRecommended to set
     */
    public void setRcRecommended(final String rcRecommended) {
        this.rcRecommended = rcRecommended;
    }

    /**
     * @return the rcDailydischgc
     */
    public Double getRcDailydischgc() {
        return rcDailydischgc;
    }

    /**
     * @param rcDailydischgc the rcDailydischgc to set
     */
    public void setRcDailydischgc(final Double rcDailydischgc) {
        this.rcDailydischgc = rcDailydischgc;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the rcRhgl
     */
    public Double getRcRhgl() {
        return rcRhgl;
    }

    /**
     * @param rcRhgl the rcRhgl to set
     */
    public void setRcRhgl(final Double rcRhgl) {
        this.rcRhgl = rcRhgl;
    }

    /**
     * @return the rcAhgl
     */
    public Double getRcAhgl() {
        return rcAhgl;
    }

    /**
     * @param rcAhgl the rcAhgl to set
     */
    public void setRcAhgl(final Double rcAhgl) {
        this.rcAhgl = rcAhgl;
    }

    /**
     * @return the rcDispWt
     */
    public String getRcDispWt() {
        return rcDispWt;
    }

    /**
     * @param rcDispWt the rcDispWt to set
     */
    public void setRcDispWt(final String rcDispWt) {
        this.rcDispWt = rcDispWt;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the wtV1
     */
    public String getWtV1() {
        return wtV1;
    }

    /**
     * @param wtV1 the wtV1 to set
     */
    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    /**
     * @return the wtV2
     */
    public String getWtV2() {
        return wtV2;
    }

    /**
     * @param wtV2 the wtV2 to set
     */
    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    /**
     * @return the wtV3
     */
    public String getWtV3() {
        return wtV3;
    }

    /**
     * @param wtV3 the wtV3 to set
     */
    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    /**
     * @return the wtV4
     */
    public String getWtV4() {
        return wtV4;
    }

    /**
     * @param wtV4 the wtV4 to set
     */
    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    /**
     * @return the wtV5
     */
    public String getWtV5() {
        return wtV5;
    }

    /**
     * @param wtV5 the wtV5 to set
     */
    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    /**
     * @return the wtN1
     */
    public Long getWtN1() {
        return wtN1;
    }

    /**
     * @param wtN1 the wtN1 to set
     */
    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    /**
     * @return the wtN2
     */
    public Long getWtN2() {
        return wtN2;
    }

    /**
     * @param wtN2 the wtN2 to set
     */
    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    /**
     * @return the wtN3
     */
    public Long getWtN3() {
        return wtN3;
    }

    /**
     * @param wtN3 the wtN3 to set
     */
    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    /**
     * @return the wtN4
     */
    public Long getWtN4() {
        return wtN4;
    }

    /**
     * @param wtN4 the wtN4 to set
     */
    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    /**
     * @return the wtN5
     */
    public Long getWtN5() {
        return wtN5;
    }

    /**
     * @param wtN5 the wtN5 to set
     */
    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    /**
     * @return the wtD1
     */
    public Date getWtD1() {
        return wtD1;
    }

    /**
     * @param wtD1 the wtD1 to set
     */
    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    /**
     * @return the wtD2
     */
    public Date getWtD2() {
        return wtD2;
    }

    /**
     * @param wtD2 the wtD2 to set
     */
    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    /**
     * @return the wtD3
     */
    public Date getWtD3() {
        return wtD3;
    }

    /**
     * @param wtD3 the wtD3 to set
     */
    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    /**
     * @return the wtLo1
     */
    public String getWtLo1() {
        return wtLo1;
    }

    /**
     * @param wtLo1 the wtLo1 to set
     */
    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    /**
     * @return the wtLo2
     */
    public String getWtLo2() {
        return wtLo2;
    }

    /**
     * @param wtLo2 the wtLo2 to set
     */
    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    /**
     * @return the wtLo3
     */
    public String getWtLo3() {
        return wtLo3;
    }

    /**
     * @param wtLo3 the wtLo3 to set
     */
    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public Long getInstId() {
        return instId;
    }

    public void setInstId(final Long instId) {
        this.instId = instId;
    }

    public Long getCodId1() {
        return codId1;
    }

    public void setCodId1(final Long codId1) {
        this.codId1 = codId1;
    }

    public Long getCodId2() {
        return codId2;
    }

    public void setCodId2(final Long codId2) {
        this.codId2 = codId2;
    }

    public Long getCodId3() {
        return codId3;
    }

    public void setCodId3(final Long codId3) {
        this.codId3 = codId3;
    }

    public Long getCodId4() {
        return codId4;
    }

    public void setCodId4(final Long codId4) {
        this.codId4 = codId4;
    }

    public Long getCodId5() {
        return codId5;
    }

    public void setCodId5(final Long codId5) {
        this.codId5 = codId5;
    }

    public long getCsId() {
        return csId;
    }

    public Double getRcTotdisttime() {
        return rcTotdisttime;
    }

    public void setRcTotdisttime(final Double rcTotdisttime) {
        this.rcTotdisttime = rcTotdisttime;
    }

	public Long getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(Long csIdn) {
		this.csIdn = csIdn;
	}

	public TbKCsmrInfoMH getCsIdnO() {
		return csIdnO;
	}

	public void setCsIdnO(TbKCsmrInfoMH csIdnO) {
		this.csIdnO = csIdnO;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}
	
}