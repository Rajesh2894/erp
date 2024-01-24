
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class TbOrganisationRest implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Size(max = 100)
    private String ONlsOrgname;

    private Long userId;

    private short langId;

    private Date lmoddate;

    private String OLogo;

    private Long updatedBy;

    private Date updatedDate;

    private Date esdtDate;

    private String tdsAccountno;

    @Size(max = 20)
    private String tdsCircle;

    @Size(max = 20)
    private String tdsPanGirNo;

    @Size(max = 70)
    private String orgTAxDedName;

    private String orgTaxDedAddr;

    private String orgShortNm;

    private String orgAddress;

    private String ONlsOrgnameMar;

    private String orgAddressMar;

    private String orgStatus;

    private Long orgCpdId;

    private String defaultStatus;

    private Long orgCpdIdDiv;

    private Long orgCpdIdOst;

    private Long orgCpdIdDis;

    private String orgEmailId;

    private String vatCircle;

    private String vatDedName;

    private Long ulbOrgID;

    private Long orgCpdIdState;

    private String estDtStr;

    private Date tranStartDate;

    private String trnsDtStr;

    private String orgLatitude;

    private String orgLongitude;

    @Size(max = 15)
    private String orgGstNo;

    private String deleteFlag;

    private String filePath;

    private String isReset;
    
    private Long sdbId1;
    private Long sdbId2;
    private Long sdbId3;
    private Long sdbId4;
    private Long sdbId5;

    private List<PortalServiceDTO> serviceMstList;
    // used to add default employee details for portal organization
    private PortalEmployeeDto portalEmpDto;

    /**
     * @return the orgLatitude
     */
    public String getOrgLatitude() {
        return orgLatitude;
    }

    /**
     * @param orgLatitude the orgLatitude to set
     */
    public void setOrgLatitude(String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

    /**
     * @return the orgLongitude
     */
    public String getOrgLongitude() {
        return orgLongitude;
    }

    /**
     * @param orgLongitude the orgLongitude to set
     */
    public void setOrgLongitude(String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    /**
     * @return the orgGstNo
     */
    public String getOrgGstNo() {
        return orgGstNo;
    }

    /**
     * @param orgGstNo the orgGstNo to set
     */
    public void setOrgGstNo(String orgGstNo) {
        this.orgGstNo = orgGstNo;
    }

    /**
     * @return the deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the isReset
     */
    public String getIsReset() {
        return isReset;
    }

    /**
     * @param isReset the isReset to set
     */
    public void setIsReset(String isReset) {
        this.isReset = isReset;
    }

    /**
     * @return the estDtStr
     */
    public String getEstDtStr() {
        return estDtStr;
    }

    /**
     * @param estDtStr the estDtStr to set
     */
    public void setEstDtStr(String estDtStr) {
        this.estDtStr = estDtStr;
    }

    /**
     * @return the tranStartDate
     */
    public Date getTranStartDate() {
        return tranStartDate;
    }

    /**
     * @param tranStartDate the tranStartDate to set
     */
    public void setTranStartDate(Date tranStartDate) {
        this.tranStartDate = tranStartDate;
    }

    /**
     * @return the trnsDtStr
     */
    public String getTrnsDtStr() {
        return trnsDtStr;
    }

    /**
     * @param trnsDtStr the trnsDtStr to set
     */
    public void setTrnsDtStr(String trnsDtStr) {
        this.trnsDtStr = trnsDtStr;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    public void setONlsOrgname(String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public short getLangId() {
        return langId;
    }

    public void setLangId(short langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public Date getEsdtDate() {
        return esdtDate;
    }

    public void setEsdtDate(Date esdtDate) {
        this.esdtDate = esdtDate;
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

    @Override
    public String toString() {
        return "TbOrganisationRest [orgid=" + orgid + ", ONlsOrgname=" + ONlsOrgname + ", userId=" + userId
                + ", langId=" + langId + ", lmoddate=" + lmoddate + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", esdtDate="
                + esdtDate + ", tdsAccountno=" + tdsAccountno + ", tdsCircle=" + tdsCircle + ", tdsPanGirNo="
                + tdsPanGirNo + ", orgTAxDedName=" + orgTAxDedName + ", orgTaxDedAddr=" + orgTaxDedAddr
                + ", orgShortNm=" + orgShortNm + ", orgAddress=" + orgAddress + ", ONlsOrgnameMar=" + ONlsOrgnameMar
                + ", orgAddressMar=" + orgAddressMar + ", orgStatus=" + orgStatus + ", orgCpdId=" + orgCpdId
                + ", defaultStatus=" + defaultStatus + ", orgCpdIdDiv=" + orgCpdIdDiv + ", orgCpdIdOst=" + orgCpdIdOst
                + ", orgCpdIdDis=" + orgCpdIdDis + ", orgEmailId=" + orgEmailId + ", vatCircle=" + vatCircle
                + ", vatDedName=" + vatDedName + ", ulbOrgID=" + ulbOrgID + ", orgCpdIdState=" + orgCpdIdState
                + ", sdbId1=" + sdbId1 + ", sdbId2=" + sdbId2 + ", sdbId3=" + sdbId3
                + ", sdbId4=" + sdbId4 + ", " + "sdbId5=" + sdbId5
                + "]";
    }

    public String getOLogo() {
        return OLogo;
    }

    public void setOLogo(String oLogo) {
        OLogo = oLogo;
    }

    public List<PortalServiceDTO> getServiceMstList() {
        return serviceMstList;
    }

    public void setServiceMstList(List<PortalServiceDTO> serviceMstList) {
        this.serviceMstList = serviceMstList;
    }

    public PortalEmployeeDto getPortalEmpDto() {
        return portalEmpDto;
    }

    public void setPortalEmpDto(PortalEmployeeDto portalEmpDto) {
        this.portalEmpDto = portalEmpDto;
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
