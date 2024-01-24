package com.abm.mainet.common.domain;

import java.io.Serializable;
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
@Table(name = "TB_ORGANISATION")
public class Organisation implements Serializable {

    private static final long serialVersionUID = 2673143643755004332L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "ORGID", nullable = false, scale = 0, precision = 12)
    private Long orgid;

    @Column(name = "USER_ID")
    private Long userId;

    //Defect #30136 
    @Column(name = "LANG_ID")
    private Short langId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "O_NLS_ORGNAME")
    private String ONlsOrgname;

    @Column(name = "TDS_ACCOUNTNO")
    private String tdsAccountno;

    @Column(name = "TDS_CIRCLE")
    private String tdsCircle;

    @Column(name = "TDS_PAN_GIR_NO")
    private String tdsPanGirNo;

    @Column(name = "ORG_TAX_DED_NAME")
    private String orgTAxDedName;

    @Column(name = "ORG_TAX_DED_ADDR")
    private String orgTaxDedAddr;

    @Column(name = "ORG_SHORT_NM")
    private String orgShortNm;

    @Column(name = "ORG_ADDRESS")
    private String orgAddress;

    @Column(name = "O_NLS_ORGNAME_MAR")
    private String ONlsOrgnameMar;

    @Column(name = "ORG_ADDRESS_MAR")
    private String orgAddressMar;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRAN_START_DATE")
    private Date tranStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ESDT_DATE")
    private Date esdtDate;

    @Column(name = "ORG_STATUS")
    private String orgStatus;

    @Column(name = "ORG_CPD_ID")
    private Long orgCpdId;

    @Column(name = "DEFAULT_STATUS", length = 1)
    private String defaultStatus;

    @Column(name = "ORG_CPD_ID_DIV")
    private Long orgCpdIdDiv;

    @Column(name = "ORG_CPD_ID_OST")
    private Long orgCpdIdOst;

    @Column(name = "ORG_CPD_ID_DIS")
    private Long orgCpdIdDis;

    @Column(name = "ORG_EMAIL_ID")
    private String orgEmailId;

    @Column(name = "VAT_CIRCLE")
    private String vatCircle;

    @Column(name = "VAT_DED_NAME")
    private String vatDedName;

    @Column(name = "ULB_ORG_ID")
    private Long ulbOrgID;

    @Column(name = "ORG_CPD_ID_STATE")
    private Long orgCpdIdState;

    @Column(name = "ORG_GST_NO")
    private String orgGstNo;
    @Column(name = "ORG_LATITUDE")
    private String orgLatitude;

    @Column(name = "ORG_LONGITUDE")
    private String orgLongitude;

    @Column(name = "O_LOGO")
    private String OLogo;
    
    @Column(name = "SDB_ID1")
    private Long sdbId1;
    @Column(name = "SDB_ID2")
    private Long sdbId2;
    @Column(name = "SDB_ID3")
    private Long sdbId3;
    @Column(name = "SDB_ID4")
    private Long sdbId4;
    @Column(name = "SDB_ID5")
    private Long sdbId5;

    public Organisation() {
        super();
    }

    public Organisation(Long orgid) {

    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getLangId() {
        return langId;
    }

    public void setLangId(Short langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    public void setONlsOrgname(String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
    }

    public String getTdsAccountno() {
        return tdsAccountno;
    }

    public void setTdsAccountno(String tdsAccountno) {
        this.tdsAccountno = tdsAccountno;
    }

    public String getTdsCircle() {
        return tdsCircle;
    }

    public void setTdsCircle(String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public String getTdsPanGirNo() {
        return tdsPanGirNo;
    }

    public void setTdsPanGirNo(String tdsPanGirNo) {
        this.tdsPanGirNo = tdsPanGirNo;
    }

    public String getOrgTAxDedName() {
        return orgTAxDedName;
    }

    public void setOrgTAxDedName(String orgTAxDedName) {
        this.orgTAxDedName = orgTAxDedName;
    }

    public String getOrgTaxDedAddr() {
        return orgTaxDedAddr;
    }

    public void setOrgTaxDedAddr(String orgTaxDedAddr) {
        this.orgTaxDedAddr = orgTaxDedAddr;
    }

    public String getOrgShortNm() {
        return orgShortNm;
    }

    public void setOrgShortNm(String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getONlsOrgnameMar() {
        return ONlsOrgnameMar;
    }

    public void setONlsOrgnameMar(String oNlsOrgnameMar) {
        ONlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getOrgAddressMar() {
        return orgAddressMar;
    }

    public void setOrgAddressMar(String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
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

    public Date getTranStartDate() {
        return tranStartDate;
    }

    public void setTranStartDate(Date tranStartDate) {
        this.tranStartDate = tranStartDate;
    }

    public Date getEsdtDate() {
        return esdtDate;
    }

    public void setEsdtDate(Date esdtDate) {
        this.esdtDate = esdtDate;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Long getOrgCpdId() {
        return orgCpdId;
    }

    public void setOrgCpdId(Long orgCpdId) {
        this.orgCpdId = orgCpdId;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Long getOrgCpdIdDiv() {
        return orgCpdIdDiv;
    }

    public void setOrgCpdIdDiv(Long orgCpdIdDiv) {
        this.orgCpdIdDiv = orgCpdIdDiv;
    }

    public Long getOrgCpdIdOst() {
        return orgCpdIdOst;
    }

    public void setOrgCpdIdOst(Long orgCpdIdOst) {
        this.orgCpdIdOst = orgCpdIdOst;
    }

    public Long getOrgCpdIdDis() {
        return orgCpdIdDis;
    }

    public void setOrgCpdIdDis(Long orgCpdIdDis) {
        this.orgCpdIdDis = orgCpdIdDis;
    }

    public String getOrgEmailId() {
        return orgEmailId;
    }

    public void setOrgEmailId(String orgEmailId) {
        this.orgEmailId = orgEmailId;
    }

    public String getVatCircle() {
        return vatCircle;
    }

    public void setVatCircle(String vatCircle) {
        this.vatCircle = vatCircle;
    }

    public String getVatDedName() {
        return vatDedName;
    }

    public void setVatDedName(String vatDedName) {
        this.vatDedName = vatDedName;
    }

    public Long getUlbOrgID() {
        return ulbOrgID;
    }

    public void setUlbOrgID(Long ulbOrgID) {
        this.ulbOrgID = ulbOrgID;
    }

    public Long getOrgCpdIdState() {
        return orgCpdIdState;
    }

    public void setOrgCpdIdState(Long orgCpdIdState) {
        this.orgCpdIdState = orgCpdIdState;
    }

    public String getOrgGstNo() {
        return orgGstNo;
    }

    public void setOrgGstNo(String orgGstNo) {
        this.orgGstNo = orgGstNo;
    }

    public String getOrgLatitude() {
        return orgLatitude;
    }

    public void setOrgLatitude(String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

    public String getOrgLongitude() {
        return orgLongitude;
    }

    public void setOrgLongitude(String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    public String getOLogo() {
        return OLogo;
    }

    public void setOLogo(String oLogo) {
        OLogo = oLogo;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(tdsAccountno);
        sb.append("|");
        sb.append(tdsCircle);
        sb.append("|");
        sb.append(tdsPanGirNo);
        sb.append("|");
        sb.append(orgTaxDedAddr);
        sb.append("|");
        sb.append(orgShortNm);
        // attribute 'oLogo' not usable (type = byte[])
        sb.append("|");
        sb.append(orgAddress);
        sb.append("|");
        sb.append(orgAddressMar);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(esdtDate);
        sb.append("|");
        sb.append(orgStatus);
        sb.append("|");
        sb.append(orgCpdId);
        sb.append("|");
        sb.append(defaultStatus);
        sb.append("|");
        sb.append(orgCpdIdDiv);
        sb.append("|");
        sb.append(orgCpdIdOst);
        sb.append("|");
        sb.append(orgCpdIdDis);
        sb.append("|");
        sb.append(orgEmailId);
        sb.append("|");
        sb.append(vatCircle);
        sb.append("|");
        sb.append(vatDedName);
        sb.append("|");
        sb.append(sdbId1);
        sb.append("|");
        sb.append(sdbId2);
        sb.append("|");
        sb.append(sdbId3);
        sb.append("|");
        sb.append(sdbId4);
        sb.append("|");
        sb.append(sdbId5);
        return sb.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_ORGANISATION", "ORGID" };
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgid == null) ? 0 : orgid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organisation other = (Organisation) obj;
		if (OLogo == null) {
			if (other.OLogo != null)
				return false;
		} else if (!OLogo.equals(other.OLogo))
			return false;
		if (ONlsOrgname == null) {
			if (other.ONlsOrgname != null)
				return false;
		} else if (!ONlsOrgname.equals(other.ONlsOrgname))
			return false;
		if (ONlsOrgnameMar == null) {
			if (other.ONlsOrgnameMar != null)
				return false;
		} else if (!ONlsOrgnameMar.equals(other.ONlsOrgnameMar))
			return false;
		if (defaultStatus == null) {
			if (other.defaultStatus != null)
				return false;
		} else if (!defaultStatus.equals(other.defaultStatus))
			return false;
		if (esdtDate == null) {
			if (other.esdtDate != null)
				return false;
		} else if (!esdtDate.equals(other.esdtDate))
			return false;
		if (langId != other.langId)
			return false;
		if (lmoddate == null) {
			if (other.lmoddate != null)
				return false;
		} else if (!lmoddate.equals(other.lmoddate))
			return false;
		if (orgAddress == null) {
			if (other.orgAddress != null)
				return false;
		} else if (!orgAddress.equals(other.orgAddress))
			return false;
		if (orgAddressMar == null) {
			if (other.orgAddressMar != null)
				return false;
		} else if (!orgAddressMar.equals(other.orgAddressMar))
			return false;
		if (orgCpdId == null) {
			if (other.orgCpdId != null)
				return false;
		} else if (!orgCpdId.equals(other.orgCpdId))
			return false;
		if (orgCpdIdDis == null) {
			if (other.orgCpdIdDis != null)
				return false;
		} else if (!orgCpdIdDis.equals(other.orgCpdIdDis))
			return false;
		if (orgCpdIdDiv == null) {
			if (other.orgCpdIdDiv != null)
				return false;
		} else if (!orgCpdIdDiv.equals(other.orgCpdIdDiv))
			return false;
		if (orgCpdIdOst == null) {
			if (other.orgCpdIdOst != null)
				return false;
		} else if (!orgCpdIdOst.equals(other.orgCpdIdOst))
			return false;
		if (orgCpdIdState == null) {
			if (other.orgCpdIdState != null)
				return false;
		} else if (!orgCpdIdState.equals(other.orgCpdIdState))
			return false;
		if (orgEmailId == null) {
			if (other.orgEmailId != null)
				return false;
		} else if (!orgEmailId.equals(other.orgEmailId))
			return false;
		if (orgGstNo == null) {
			if (other.orgGstNo != null)
				return false;
		} else if (!orgGstNo.equals(other.orgGstNo))
			return false;
		if (orgLatitude == null) {
			if (other.orgLatitude != null)
				return false;
		} else if (!orgLatitude.equals(other.orgLatitude))
			return false;
		if (orgLongitude == null) {
			if (other.orgLongitude != null)
				return false;
		} else if (!orgLongitude.equals(other.orgLongitude))
			return false;
		if (orgShortNm == null) {
			if (other.orgShortNm != null)
				return false;
		} else if (!orgShortNm.equals(other.orgShortNm))
			return false;
		if (orgStatus == null) {
			if (other.orgStatus != null)
				return false;
		} else if (!orgStatus.equals(other.orgStatus))
			return false;
		if (orgTAxDedName == null) {
			if (other.orgTAxDedName != null)
				return false;
		} else if (!orgTAxDedName.equals(other.orgTAxDedName))
			return false;
		if (orgTaxDedAddr == null) {
			if (other.orgTaxDedAddr != null)
				return false;
		} else if (!orgTaxDedAddr.equals(other.orgTaxDedAddr))
			return false;
		if (orgid == null) {
			if (other.orgid != null)
				return false;
		} else if (!orgid.equals(other.orgid))
			return false;
		if (tdsAccountno == null) {
			if (other.tdsAccountno != null)
				return false;
		} else if (!tdsAccountno.equals(other.tdsAccountno))
			return false;
		if (tdsCircle == null) {
			if (other.tdsCircle != null)
				return false;
		} else if (!tdsCircle.equals(other.tdsCircle))
			return false;
		if (tdsPanGirNo == null) {
			if (other.tdsPanGirNo != null)
				return false;
		} else if (!tdsPanGirNo.equals(other.tdsPanGirNo))
			return false;
		if (tranStartDate == null) {
			if (other.tranStartDate != null)
				return false;
		} else if (!tranStartDate.equals(other.tranStartDate))
			return false;
		if (ulbOrgID == null) {
			if (other.ulbOrgID != null)
				return false;
		} else if (!ulbOrgID.equals(other.ulbOrgID))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (vatCircle == null) {
			if (other.vatCircle != null)
				return false;
		} else if (!vatCircle.equals(other.vatCircle))
			return false;
		if (vatDedName == null) {
			if (other.vatDedName != null)
				return false;
		} else if (!vatDedName.equals(other.vatDedName))
			return false;
		return true;
	}

	public Long getSdbId1() {
		return sdbId1;
	}

	public void setSdbId1(Long sdbId1) {
		this.sdbId1 = sdbId1;
	}

	public Long getSdbId2() {
		return sdbId2;
	}

	public void setSdbId2(Long sdbId2) {
		this.sdbId2 = sdbId2;
	}

	public Long getSdbId3() {
		return sdbId3;
	}

	public void setSdbId3(Long sdbId3) {
		this.sdbId3 = sdbId3;
	}

	public Long getSdbId4() {
		return sdbId4;
	}

	public void setSdbId4(Long sdbId4) {
		this.sdbId4 = sdbId4;
	}

	public Long getSdbId5() {
		return sdbId5;
	}

	public void setSdbId5(Long sdbId5) {
		this.sdbId5 = sdbId5;
	}   

}
