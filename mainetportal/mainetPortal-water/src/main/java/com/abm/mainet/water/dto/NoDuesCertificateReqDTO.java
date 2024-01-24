package com.abm.mainet.water.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.RequestDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class NoDuesCertificateReqDTO extends RequestDTO implements Serializable {

    private static final long serialVersionUID = 4926808796522875741L;
    private String consumerNo;
    private String consumerName;
    private String consumerAddress;
    private Long noOfCopies;
    private boolean isDues;
    private String payMode;
    private Double charges;
    private String waterDues;
    private Double duesAmount;
    private Long[] finYear;
    private Long wardId;
    private Long zoneId;
    private String lgIpMac;
    private boolean error;
    private String errorMsg;
    private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();
    private boolean isBillGenerated;
	private Double propDueAmt;
	private String chequeClearanceStatus;

    public boolean isBillGenerated() {
		return isBillGenerated;
	}

	public void setBillGenerated(boolean isBillGenerated) {
		this.isBillGenerated = isBillGenerated;
	}

	/**
     * @return the waterDues
     */
    public String getWaterDues() {

        return waterDues;
    }

    /**
     * @param waterDues the waterDues to set
     */
    public void setWaterDues(final String waterDues) {

        this.waterDues = waterDues;
    }

    /**
     * @return the duesAmount
     */
    public Double getDuesAmount() {

        return duesAmount;
    }

    /**
     * @param duesAmount the duesAmount to set
     */
    public void setDuesAmount(final Double duesAmount) {

        this.duesAmount = duesAmount;
    }

    /**
     * @return the error
     */
    public boolean isError() {

        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(final boolean error) {

        this.error = error;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {

        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {

        this.errorMsg = errorMsg;
    }

    /**
     * @return the payMode
     */
    public String getPayMode() {

        return payMode;
    }

    /**
     * @param payMode the payMode to set
     */
    public void setPayMode(final String payMode) {

        this.payMode = payMode;
    }

    /**
     * @return the charges
     */
    public Double getCharges() {

        return charges;
    }

    /**
     * @param charges the charges to set
     */
    public void setCharges(final Double charges) {

        this.charges = charges;
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
    public Long[] getFinYear() {

        return finYear;
    }

    /**
     * @param finYear the finYear to set
     */
    public void setFinYear(final Long[] finYear) {

        this.finYear = finYear;
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
     * @return the noOfCopies
     */
    public Long getNoOfCopies() {

        return noOfCopies;
    }

    /**
     * @param noOfCopies the noOfCopies to set
     */
    public void setNoOfCopies(final Long noOfCopies) {

        this.noOfCopies = noOfCopies;
    }

    /**
     * @return the applicantDTO
     */
    public ApplicantDetailDTO getApplicantDTO() {

        return applicantDTO;
    }

    /**
     * @param applicantDTO the applicantDTO to set
     */
    public void setApplicantDTO(final ApplicantDetailDTO applicantDTO) {

        this.applicantDTO = applicantDTO;
    }

    /**
     * @return the isDues
     */
    public boolean isDues() {

        return isDues;
    }

    /**
     * @param isDues the isDues to set
     */
    public void setDues(final boolean isDues) {

        this.isDues = isDues;
    }

    /**
     * @return the wardId
     */
    public Long getWardId() {

        return wardId;
    }

    /**
     * @param wardId the wardId to set
     */
    public void setWardId(final Long wardId) {

        this.wardId = wardId;
    }

    /**
     * @return the zoneId
     */
    public Long getZoneId() {

        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(final Long zoneId) {

        this.zoneId = zoneId;
    }
	
	public Double getPropDueAmt() {
		return propDueAmt;
	}

	public void setPropDueAmt(final Double propDueAmt) {
		this.propDueAmt = propDueAmt;
	}

	public String getChequeClearanceStatus() {
		return chequeClearanceStatus;
	}

	public void setChequeClearanceStatus(String chequeClearanceStatus) {
		this.chequeClearanceStatus = chequeClearanceStatus;
	}

}
