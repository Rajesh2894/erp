package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class NoDuesCertificateRespDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = 8505671177487474156L;

    private boolean isDues;
    private String consumerNo;
    private String consumerName;
    private String consumerAddress;
    private Long finYear;
    private Long orgId;
    private Long userId;
    private Long langId;
    private Long deptId;
    private Long serviceId;
    private long connectionId;
    private String csTitle;
    private String csName;
    private String csMname;
    private String csLname;
    private String csOrgName;
    private String csAdd;
    private String csFlatno;
    private String csBldplt;
    private String csLanear;
    private String csRdcross;
    private String csContactno;
    private Long csNoofusers;
    private Long csCcnsize;
    private String csRemark;
    private Long csNooftaps;
    private Long csMeteredccn;
    private String csCcnstatus;
    private Map<String, Double> duesList;
    private List<String> errorList = new ArrayList<>();
    private List<WebServiceResponseDTO> webServiceResponseDTOs;
    private String usageSubtype1;
    private String usageSubtype2;
    private String usageSubtype3;
    private String usageSubtype4;
    private String usageSubtype5;
    private String applicantType;
    private String checkListApplFlag;
    private boolean isBillGenerated;
    private Double propDueAmt;
    private Map<String, Double> ccnDuesList;
    
    public Double getPropDueAmt() {
		return propDueAmt;
	}

	public void setPropDueAmt(Double propDueAmt) {
		this.propDueAmt = propDueAmt;
	}

	public boolean isBillGenerated() {
		return isBillGenerated;
	}

	public void setBillGenerated(boolean isBillGenerated) {
		this.isBillGenerated = isBillGenerated;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	/**
     * @return the isDues
     */
    public boolean isDues() {
        return isDues;
    }

    /**
     * @return the duesList
     */
    public Map<String, Double> getDuesList() {
        return duesList;
    }

    /**
     * @param duesList the duesList to set
     */
    public void setDuesList(final Map<String, Double> duesList) {
        this.duesList = duesList;
    }

    /**
     * @param isDues the isDues to set
     */
    public void setDues(final boolean isDues) {
        this.isDues = isDues;
    }

    /**
     * @return the consumerNo
     */
    public String getConsumerNo() {
        return consumerNo;
    }

    /**
     * @param consumerNo the consumerNo to set
     */
    public void setConsumerNo(final String consumerNo) {
        this.consumerNo = consumerNo;
    }

    /**
     * @return the consumerName
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * @param consumerName the consumerName to set
     */
    public void setConsumerName(final String consumerName) {
        this.consumerName = consumerName;
    }

    /**
     * @return the consumerAddress
     */
    public String getConsumerAddress() {
        return consumerAddress;
    }

    /**
     * @param consumerAddress the consumerAddress to set
     */
    public void setConsumerAddress(final String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    /**
     * @return the finYear
     */
    public Long getFinYear() {
        return finYear;
    }

    /**
     * @param finYear the finYear to set
     */
    public void setFinYear(final Long finYear) {
        this.finYear = finYear;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @return the deptId
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the connectionId
     */
    public long getConnectionId() {
        return connectionId;
    }

    /**
     * @param connectionId the connectionId to set
     */
    public void setConnectionId(final long connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * @return the csTitle
     */
    public String getCsTitle() {
        return csTitle;
    }

    /**
     * @param csTitle the csTitle to set
     */
    public void setCsTitle(final String csTitle) {
        this.csTitle = csTitle;
    }

    /**
     * @return the csName
     */
    public String getCsName() {
        return csName;
    }

    /**
     * @param csName the csName to set
     */
    public void setCsName(final String csName) {
        this.csName = csName;
    }

    /**
     * @return the csMname
     */
    public String getCsMname() {
        return csMname;
    }

    /**
     * @param csMname the csMname to set
     */
    public void setCsMname(final String csMname) {
        this.csMname = csMname;
    }

    /**
     * @return the csLname
     */
    public String getCsLname() {
        return csLname;
    }

    /**
     * @param csLname the csLname to set
     */
    public void setCsLname(final String csLname) {
        this.csLname = csLname;
    }

    /**
     * @return the csOrgName
     */
    public String getCsOrgName() {
        return csOrgName;
    }

    /**
     * @param csOrgName the csOrgName to set
     */
    public void setCsOrgName(final String csOrgName) {
        this.csOrgName = csOrgName;
    }

    /**
     * @return the csAdd
     */
    public String getCsAdd() {
        return csAdd;
    }

    /**
     * @param csAdd the csAdd to set
     */
    public void setCsAdd(final String csAdd) {
        this.csAdd = csAdd;
    }

    /**
     * @return the csFlatno
     */
    public String getCsFlatno() {
        return csFlatno;
    }

    /**
     * @param csFlatno the csFlatno to set
     */
    public void setCsFlatno(final String csFlatno) {
        this.csFlatno = csFlatno;
    }

    /**
     * @return the csBldplt
     */
    public String getCsBldplt() {
        return csBldplt;
    }

    /**
     * @param csBldplt the csBldplt to set
     */
    public void setCsBldplt(final String csBldplt) {
        this.csBldplt = csBldplt;
    }

    /**
     * @return the csLanear
     */
    public String getCsLanear() {
        return csLanear;
    }

    /**
     * @param csLanear the csLanear to set
     */
    public void setCsLanear(final String csLanear) {
        this.csLanear = csLanear;
    }

    /**
     * @return the csRdcross
     */
    public String getCsRdcross() {
        return csRdcross;
    }

    /**
     * @param csRdcross the csRdcross to set
     */
    public void setCsRdcross(final String csRdcross) {
        this.csRdcross = csRdcross;
    }

    /**
     * @return the csContactno
     */
    public String getCsContactno() {
        return csContactno;
    }

    /**
     * @param csContactno the csContactno to set
     */
    public void setCsContactno(final String csContactno) {
        this.csContactno = csContactno;
    }

    /**
     * @return the csNoofusers
     */
    public Long getCsNoofusers() {
        return csNoofusers;
    }

    /**
     * @param csNoofusers the csNoofusers to set
     */
    public void setCsNoofusers(final Long csNoofusers) {
        this.csNoofusers = csNoofusers;
    }

    /**
     * @return the csCcnsize
     */
    public Long getCsCcnsize() {
        return csCcnsize;
    }

    /**
     * @param csCcnsize the csCcnsize to set
     */
    public void setCsCcnsize(final Long csCcnsize) {
        this.csCcnsize = csCcnsize;
    }

    /**
     * @return the csRemark
     */
    public String getCsRemark() {
        return csRemark;
    }

    /**
     * @param csRemark the csRemark to set
     */
    public void setCsRemark(final String csRemark) {
        this.csRemark = csRemark;
    }

    /**
     * @return the csNooftaps
     */
    public Long getCsNooftaps() {
        return csNooftaps;
    }

    /**
     * @param csNooftaps the csNooftaps to set
     */
    public void setCsNooftaps(final Long csNooftaps) {
        this.csNooftaps = csNooftaps;
    }

    /**
     * @return the csMeteredccn
     */
    public Long getCsMeteredccn() {
        return csMeteredccn;
    }

    /**
     * @param csMeteredccn the csMeteredccn to set
     */
    public void setCsMeteredccn(final Long csMeteredccn) {
        this.csMeteredccn = csMeteredccn;
    }

    /**
     * @return the csCcnstatus
     */
    public String getCsCcnstatus() {
        return csCcnstatus;
    }

    /**
     * @param csCcnstatus the csCcnstatus to set
     */
    public void setCsCcnstatus(final String csCcnstatus) {
        this.csCcnstatus = csCcnstatus;
    }

    /**
     * @return the errorList
     */
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * @param errorList the errorList to set
     */
    public void setErrorList(final List<String> errorList) {
        this.errorList = errorList;
    }

    /**
     * @return the webServiceResponseDTOs
     */
    public List<WebServiceResponseDTO> getWebServiceResponseDTOs() {
        return webServiceResponseDTOs;
    }

    /**
     * @param webServiceResponseDTOs the webServiceResponseDTOs to set
     */
    public void setWebServiceResponseDTOs(
            final List<WebServiceResponseDTO> webServiceResponseDTOs) {
        this.webServiceResponseDTOs = webServiceResponseDTOs;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NoDuesCertificateRespDTO [isDues=" + isDues
                + ", consumerNo=" + consumerNo + ", consumerName="
                + consumerName + ", consumerAddress=" + consumerAddress
                + ", finYear=" + finYear + ", orgId=" + orgId + ", userId="
                + userId + ", langId=" + langId + ", deptId=" + deptId
                + ", serviceId=" + serviceId + ", connectionId="
                + connectionId + ", csTitle=" + csTitle + ", csName="
                + csName + ", csMname=" + csMname + ", csLname=" + csLname
                + ", csOrgName=" + csOrgName + ", csAdd=" + csAdd
                + ", csFlatno=" + csFlatno + ", csBldplt=" + csBldplt
                + ", csLanear=" + csLanear + ", csRdcross=" + csRdcross
                + ", csContactno=" + csContactno + ", csNoofusers="
                + csNoofusers + ", csCcnsize=" + csCcnsize + ", csRemark="
                + csRemark + ", csNooftaps=" + csNooftaps
                + ", csMeteredccn=" + csMeteredccn + ", csCcnstatus="
                + csCcnstatus + ", errorList=" + errorList
                + ", webServiceResponseDTOs=" + webServiceResponseDTOs
                + "]";
    }

    public String getUsageSubtype1() {
        return usageSubtype1;
    }

    public void setUsageSubtype1(final String usageSubtype1) {
        this.usageSubtype1 = usageSubtype1;
    }

    public String getUsageSubtype2() {
        return usageSubtype2;
    }

    public void setUsageSubtype2(final String usageSubtype2) {
        this.usageSubtype2 = usageSubtype2;
    }

    public String getUsageSubtype3() {
        return usageSubtype3;
    }

    public void setUsageSubtype3(final String usageSubtype3) {
        this.usageSubtype3 = usageSubtype3;
    }

    public String getUsageSubtype4() {
        return usageSubtype4;
    }

    public void setUsageSubtype4(final String usageSubtype4) {
        this.usageSubtype4 = usageSubtype4;
    }

    public String getUsageSubtype5() {
        return usageSubtype5;
    }

    public void setUsageSubtype5(final String usageSubtype5) {
        this.usageSubtype5 = usageSubtype5;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(final String applicantType) {
        this.applicantType = applicantType;
    }

	public Map<String, Double> getCcnDuesList() {
		return ccnDuesList;
	}

	public void setCcnDuesList(Map<String, Double> ccnDuesList) {
		this.ccnDuesList = ccnDuesList;
	}

}
