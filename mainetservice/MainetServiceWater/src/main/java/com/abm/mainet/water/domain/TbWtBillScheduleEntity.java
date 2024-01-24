/*
 * Created on 4 May 2016 ( Time 15:33:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a composite Primary Key  

package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.domain.TbComparentDetEntity;

/**
 * Persistent class for entity stored in table "TB_WT_BILL_SCHEDULE"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_WT_BILL_SCHEDULE")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbWtBillScheduleEntity.countAll", query = "SELECT COUNT(x) FROM TbWtBillScheduleEntity x")
})
public class TbWtBillScheduleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( EMBEDDED IN AN EXTERNAL CLASS )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CNS_ID", nullable = false)
    private Long cnsId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CNS_FROM_DATE")
    private Long cnsFromDate;

    @Column(name = "CNS_TO_DATE")
    private Long cnsToDate;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CNS_YEARID")
    private Long cnsYearid;

    @Column(name = "CNS_MN")
    private String cnsMn;

    @Column(name = "WT_V1")
    private String wtV1;

    @Column(name = "WT_V2")
    private String wtV2;

    @Column(name = "WT_V3")
    private String wtV3;

    @Column(name = "WT_V4")
    private String wtV4;

    @Column(name = "WT_V5")
    private String wtV5;

    @Column(name = "WT_N1")
    private Long wtN1;

    @Column(name = "WT_N2")
    private Long wtN2;

    @Column(name = "WT_N3")
    private Long wtN3;

    @Column(name = "WT_N4")
    private Long wtN4;

    @Column(name = "WT_N5")
    private Long wtN5;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D1")
    private Date wtD1;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D2")
    private Date wtD2;

    @Temporal(TemporalType.DATE)
    @Column(name = "WT_D3")
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1)
    private String wtLo3;

    @Column(name = "COPY_CNS_CPDID")
    private String copyCnsCpdid;

    @Column(name = "COPY_CNS_MFCPDVAL")
    private String copyCnsMfcpdval;

    @Column(name = "COPY_CNS_PRB_FRQ")
    private String copyCnsPrbFrq;

    @Column(name = "COD_ID1_WWZ")
    private Long codIdWwz1;

    @Column(name = "COD_ID2_WWZ")
    private Long codIdWwz2;

    @Column(name = "COD_ID3_WWZ")
    private Long codIdWwz3;

    @Column(name = "COD_ID4_WWZ")
    private Long codIdWwz4;

    @Column(name = "COD_ID5_WWZ")
    private Long codIdWwz5;

    @Column(name = "DEPENDS_ON_TYPE", length = 20)
    private String dependsOnType;

    // "cnsCpdid" (column "CNS_CPDID") is not defined by itself because used as FK in a link
    // "cnsCcgid1" (column "CNS_CCGID1") is not defined by itself because used as FK in a link
    // "cnsMfcpdval" (column "CNS_MFCPDVAL") is not defined by itself because used as FK in a link
    // "cnsCcgid2" (column "CNS_CCGID2") is not defined by itself because used as FK in a link
    // "cnsCcgid3" (column "CNS_CCGID3") is not defined by itself because used as FK in a link
    // "cnsCcgid4" (column "CNS_CCGID4") is not defined by itself because used as FK in a link
    // "cnsCcgid5" (column "CNS_CCGID5") is not defined by itself because used as FK in a link
    // "cnsPrbFrq" (column "CNS_PRB_FRQ") is not defined by itself because used as FK in a link

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CCGID4", referencedColumnName = "COD_ID")
    private TbComparentDetEntity tbComparentDet3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_PRB_FRQ", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CCGID3", referencedColumnName = "COD_ID")
    private TbComparentDetEntity tbComparentDet2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CCGID5", referencedColumnName = "COD_ID")
    private TbComparentDetEntity tbComparentDet4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_MFCPDVAL", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CCGID2", referencedColumnName = "COD_ID")
    private TbComparentDetEntity tbComparentDet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CPDID", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNS_CCGID1", referencedColumnName = "COD_ID")
    private TbComparentDetEntity tbComparentDet5;

    @OneToMany(mappedBy = "tbWtBillSchedule", cascade = CascadeType.ALL)
    private List<TbWtBillScheduleDetailEntity> listOfTbWtBillScheduleDetail;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbWtBillScheduleEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE COMPOSITE KEY
    // ----------------------------------------------------------------------

    /**
     * @return the cnsId
     */
    public Long getCnsId() {
        return cnsId;
    }

    /**
     * @param cnsId the cnsId to set
     */
    public void setCnsId(final Long cnsId) {
        this.cnsId = cnsId;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : CNS_FROM_DATE ( NUMBER )
    public void setCnsFromDate(final Long cnsFromDate) {
        this.cnsFromDate = cnsFromDate;
    }

    public Long getCnsFromDate() {
        return cnsFromDate;
    }

    // --- DATABASE MAPPING : CNS_TO_DATE ( NUMBER )
    public void setCnsToDate(final Long cnsToDate) {
        this.cnsToDate = cnsToDate;
    }

    public Long getCnsToDate() {
        return cnsToDate;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
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

    // --- DATABASE MAPPING : CNS_YEARID ( NUMBER )
    public void setCnsYearid(final Long cnsYearid) {
        this.cnsYearid = cnsYearid;
    }

    public Long getCnsYearid() {
        return cnsYearid;
    }

    // --- DATABASE MAPPING : CNS_MN ( NVARCHAR2 )
    public void setCnsMn(final String cnsMn) {
        this.cnsMn = cnsMn;
    }

    public String getCnsMn() {
        return cnsMn;
    }

    // --- DATABASE MAPPING : WT_V1 ( NVARCHAR2 )
    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV1() {
        return wtV1;
    }

    // --- DATABASE MAPPING : WT_V2 ( NVARCHAR2 )
    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV2() {
        return wtV2;
    }

    // --- DATABASE MAPPING : WT_V3 ( NVARCHAR2 )
    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV3() {
        return wtV3;
    }

    // --- DATABASE MAPPING : WT_V4 ( NVARCHAR2 )
    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV4() {
        return wtV4;
    }

    // --- DATABASE MAPPING : WT_V5 ( NVARCHAR2 )
    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public String getWtV5() {
        return wtV5;
    }

    // --- DATABASE MAPPING : WT_N1 ( NUMBER )
    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Long getWtN1() {
        return wtN1;
    }

    // --- DATABASE MAPPING : WT_N2 ( NUMBER )
    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN2() {
        return wtN2;
    }

    // --- DATABASE MAPPING : WT_N3 ( NUMBER )
    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN3() {
        return wtN3;
    }

    // --- DATABASE MAPPING : WT_N4 ( NUMBER )
    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN4() {
        return wtN4;
    }

    // --- DATABASE MAPPING : WT_N5 ( NUMBER )
    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Long getWtN5() {
        return wtN5;
    }

    // --- DATABASE MAPPING : WT_D1 ( DATE )
    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD1() {
        return wtD1;
    }

    // --- DATABASE MAPPING : WT_D2 ( DATE )
    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD2() {
        return wtD2;
    }

    // --- DATABASE MAPPING : WT_D3 ( DATE )
    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public Date getWtD3() {
        return wtD3;
    }

    // --- DATABASE MAPPING : WT_LO1 ( CHAR )
    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    // --- DATABASE MAPPING : WT_LO2 ( CHAR )
    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    // --- DATABASE MAPPING : WT_LO3 ( CHAR )
    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    // --- DATABASE MAPPING : COPY_CNS_CPDID ( NVARCHAR2 )
    public void setCopyCnsCpdid(final String copyCnsCpdid) {
        this.copyCnsCpdid = copyCnsCpdid;
    }

    public String getCopyCnsCpdid() {
        return copyCnsCpdid;
    }

    // --- DATABASE MAPPING : COPY_CNS_MFCPDVAL ( NVARCHAR2 )
    public void setCopyCnsMfcpdval(final String copyCnsMfcpdval) {
        this.copyCnsMfcpdval = copyCnsMfcpdval;
    }

    public String getCopyCnsMfcpdval() {
        return copyCnsMfcpdval;
    }

    // --- DATABASE MAPPING : COPY_CNS_PRB_FRQ ( NVARCHAR2 )
    public void setCopyCnsPrbFrq(final String copyCnsPrbFrq) {
        this.copyCnsPrbFrq = copyCnsPrbFrq;
    }

    public String getCopyCnsPrbFrq() {
        return copyCnsPrbFrq;
    }

    // --- DATABASE MAPPING : COD_ID1_WWZ ( NUMBER )
    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------
    public void setTbComparentDet3(final TbComparentDetEntity tbComparentDet3) {
        this.tbComparentDet3 = tbComparentDet3;
    }

    /**
     * @return the codIdWwz1
     */
    public Long getCodIdWwz1() {
        return codIdWwz1;
    }

    /**
     * @param codIdWwz1 the codIdWwz1 to set
     */
    public void setCodIdWwz1(final Long codIdWwz1) {
        this.codIdWwz1 = codIdWwz1;
    }

    /**
     * @return the codIdWwz2
     */
    public Long getCodIdWwz2() {
        return codIdWwz2;
    }

    /**
     * @param codIdWwz2 the codIdWwz2 to set
     */
    public void setCodIdWwz2(final Long codIdWwz2) {
        this.codIdWwz2 = codIdWwz2;
    }

    /**
     * @return the codIdWwz3
     */
    public Long getCodIdWwz3() {
        return codIdWwz3;
    }

    /**
     * @param codIdWwz3 the codIdWwz3 to set
     */
    public void setCodIdWwz3(final Long codIdWwz3) {
        this.codIdWwz3 = codIdWwz3;
    }

    /**
     * @return the codIdWwz4
     */
    public Long getCodIdWwz4() {
        return codIdWwz4;
    }

    /**
     * @param codIdWwz4 the codIdWwz4 to set
     */
    public void setCodIdWwz4(final Long codIdWwz4) {
        this.codIdWwz4 = codIdWwz4;
    }

    /**
     * @return the codIdWwz5
     */
    public Long getCodIdWwz5() {
        return codIdWwz5;
    }

    /**
     * @param codIdWwz5 the codIdWwz5 to set
     */
    public void setCodIdWwz5(final Long codIdWwz5) {
        this.codIdWwz5 = codIdWwz5;
    }

    /**
     * @return the dependsOnType
     */
    public String getDependsOnType() {
        return dependsOnType;
    }

    /**
     * @param dependsOnType the dependsOnType to set
     */
    public void setDependsOnType(final String dependsOnType) {
        this.dependsOnType = dependsOnType;
    }

    public TbComparentDetEntity getTbComparentDet3() {
        return tbComparentDet3;
    }

    public void setTbComparamDet3(final TbComparamDetEntity tbComparamDet3) {
        this.tbComparamDet3 = tbComparamDet3;
    }

    public TbComparamDetEntity getTbComparamDet3() {
        return tbComparamDet3;
    }

    public void setTbComparentDet2(final TbComparentDetEntity tbComparentDet2) {
        this.tbComparentDet2 = tbComparentDet2;
    }

    public TbComparentDetEntity getTbComparentDet2() {
        return tbComparentDet2;
    }

    public void setTbComparentDet4(final TbComparentDetEntity tbComparentDet4) {
        this.tbComparentDet4 = tbComparentDet4;
    }

    public TbComparentDetEntity getTbComparentDet4() {
        return tbComparentDet4;
    }

    public void setTbComparamDet2(final TbComparamDetEntity tbComparamDet2) {
        this.tbComparamDet2 = tbComparamDet2;
    }

    public TbComparamDetEntity getTbComparamDet2() {
        return tbComparamDet2;
    }

    public void setTbComparentDet(final TbComparentDetEntity tbComparentDet) {
        this.tbComparentDet = tbComparentDet;
    }

    public TbComparentDetEntity getTbComparentDet() {
        return tbComparentDet;
    }

    public void setTbComparamDet(final TbComparamDetEntity tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDet;
    }

    public void setTbComparentDet5(final TbComparentDetEntity tbComparentDet5) {
        this.tbComparentDet5 = tbComparentDet5;
    }

    public TbComparentDetEntity getTbComparentDet5() {
        return tbComparentDet5;
    }

    /**
     * @return the listOfTbWtBillScheduleDetail
     */
    public List<TbWtBillScheduleDetailEntity> getListOfTbWtBillScheduleDetail() {
        return listOfTbWtBillScheduleDetail;
    }

    /**
     * @param listOfTbWtBillScheduleDetail the listOfTbWtBillScheduleDetail to set
     */
    public void setListOfTbWtBillScheduleDetail(
            final List<TbWtBillScheduleDetailEntity> listOfTbWtBillScheduleDetail) {
        this.listOfTbWtBillScheduleDetail = listOfTbWtBillScheduleDetail;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(cnsId);
        sb.append("]:");
        sb.append(orgid);
        sb.append("|");
        sb.append(cnsFromDate);
        sb.append("|");
        sb.append(cnsToDate);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(cnsYearid);
        sb.append("|");
        sb.append(cnsMn);
        sb.append("|");
        sb.append(wtV1);
        sb.append("|");
        sb.append(wtV2);
        sb.append("|");
        sb.append(wtV3);
        sb.append("|");
        sb.append(wtV4);
        sb.append("|");
        sb.append(wtV5);
        sb.append("|");
        sb.append(wtN1);
        sb.append("|");
        sb.append(wtN2);
        sb.append("|");
        sb.append(wtN3);
        sb.append("|");
        sb.append(wtN4);
        sb.append("|");
        sb.append(wtN5);
        sb.append("|");
        sb.append(wtD1);
        sb.append("|");
        sb.append(wtD2);
        sb.append("|");
        sb.append(wtD3);
        sb.append("|");
        sb.append(wtLo1);
        sb.append("|");
        sb.append(wtLo2);
        sb.append("|");
        sb.append(wtLo3);
        sb.append("|");
        sb.append(copyCnsCpdid);
        sb.append("|");
        sb.append(copyCnsMfcpdval);
        sb.append("|");
        sb.append(copyCnsPrbFrq);
        sb.append("|");
        sb.append(codIdWwz1);
        sb.append("|");
        sb.append(codIdWwz2);
        sb.append("|");
        sb.append(codIdWwz3);
        sb.append("|");
        sb.append(codIdWwz4);
        sb.append("|");
        sb.append(codIdWwz5);
        sb.append("|");
        sb.append(dependsOnType);
        return sb.toString();
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WT_BILL_SCHEDULE", "CNS_ID" };
    }

}
