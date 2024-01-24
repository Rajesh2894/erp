package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.exception.ErrorResponse;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 *
 *         Created Date : 18-Jun-2018
 */
public class SolidWasteConsumerMasterDTO implements Serializable {

    private static final long serialVersionUID = -5655101346469914743L;

    private Long registrationId;

    private Long createdBy;

    private Date creationDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private String swAddress;

    private Long swLocation;

    private Long swMobile;

    private String swName;

    private String swNewCustId;

    private String swOldCustId;

    private String swOldPropNo;

    private String swNewPropNo;

    private String swUserCategory1;

    private String swUserCategory2;

    private String swUserCategory3;

    private String swUserCategory4;

    private String swUserCategory5;

    private String swUserCategory1Reg;

    private String swUserCategory2Reg;

    private String swUserCategory3Reg;

    private String swUserCategory4Reg;

    private String swUserCategory5Reg;

    private Long swCount1;

    private Long swCount2;

    private Long swCount3;

    private Long swCount4;

    private Date laspPaymentDate;

    private BigDecimal lastPayAmount;

    private BigDecimal outstandingAmount;

    private BigDecimal advanceAmount;

    private Long updatedBy;

    private Date updatedDate;

    private Long swCod1;

    private Long swCod2;

    private Long swCod3;

    private Long swCod4;

    private Long swCod5;

    private List<DocumentDetailsVO> documentList;

    @JsonIgnore
    private SolidWasteBillMasterDTO tbSwNewBillMas;

    @JsonIgnore
    private List<HomeComposeDetailsDto> homeComposeDetailList = new ArrayList<>();
    
    private ErrorResponse errorResponse;
    
    
    
    public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public List<HomeComposeDetailsDto> getHomeComposeDetailList() {
        return homeComposeDetailList;
    }

    public void setHomeComposeDetailList(List<HomeComposeDetailsDto> homeComposeDetailList) {
        this.homeComposeDetailList = homeComposeDetailList;
    }

    public SolidWasteConsumerMasterDTO() {
    }

    public Long getRegistrationId() {
	return this.registrationId;
    }

    public void setRegistrationId(Long registrationId) {
	this.registrationId = registrationId;
    }

    public Long getCreatedBy() {
	return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreationDate() {
	return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

    public String getLgIpMac() {
	return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
	this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
	return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
	return this.orgid;
    }

    public void setOrgid(Long orgid) {
	this.orgid = orgid;
    }

    public String getSwAddress() {
	return this.swAddress;
    }

    public void setSwAddress(String swAddress) {
	this.swAddress = swAddress;
    }

    public Long getSwLocation() {
	return this.swLocation;
    }

    public void setSwLocation(Long swLocation) {
	this.swLocation = swLocation;
    }

    public Long getSwMobile() {
	return this.swMobile;
    }

    public void setSwMobile(Long swMobile) {
	this.swMobile = swMobile;
    }

    public String getSwName() {
	return this.swName;
    }

    public void setSwName(String swName) {
	this.swName = swName;
    }

    public String getSwNewCustId() {
	return this.swNewCustId;
    }

    public void setSwNewCustId(String swNewCustId) {
	this.swNewCustId = swNewCustId;
    }

    public String getSwOldCustId() {
	return this.swOldCustId;
    }

    public void setSwOldCustId(String swOldCustId) {
	this.swOldCustId = swOldCustId;
    }

    public String getSwOldPropNo() {
	return this.swOldPropNo;
    }

    public void setSwOldPropNo(String swOldPropNo) {
	this.swOldPropNo = swOldPropNo;
    }

    public String getSwUserCategory1() {
	return this.swUserCategory1;
    }

    public void setSwUserCategory1(String swUserCategory1) {
	this.swUserCategory1 = swUserCategory1;
    }

    public String getSwUserCategory2() {
	return this.swUserCategory2;
    }

    public void setSwUserCategory2(String swUserCategory2) {
	this.swUserCategory2 = swUserCategory2;
    }

    public String getSwUserCategory3() {
	return this.swUserCategory3;
    }

    public void setSwUserCategory3(String swUserCategory3) {
	this.swUserCategory3 = swUserCategory3;
    }

    public String getSwUserCategory4() {
	return this.swUserCategory4;
    }

    public void setSwUserCategory4(String swUserCategory4) {
	this.swUserCategory4 = swUserCategory4;
    }

    public String getSwUserCategory5() {
	return this.swUserCategory5;
    }

    public void setSwUserCategory5(String swUserCategory5) {
	this.swUserCategory5 = swUserCategory5;
    }

    public Long getUpdatedBy() {
	return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
	this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
	return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

    public String getSwNewPropNo() {
	return swNewPropNo;
    }

    public void setSwNewPropNo(String swNewPropNo) {
	this.swNewPropNo = swNewPropNo;
    }

    public Date getLaspPaymentDate() {
	return laspPaymentDate;
    }

    public void setLaspPaymentDate(Date laspPaymentDate) {
	this.laspPaymentDate = laspPaymentDate;
    }

    public BigDecimal getLastPayAmount() {
	return lastPayAmount;
    }

    public void setLastPayAmount(BigDecimal lastPayAmount) {
	this.lastPayAmount = lastPayAmount;
    }

    public BigDecimal getOutstandingAmount() {
	return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
	this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getAdvanceAmount() {
	return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
	this.advanceAmount = advanceAmount;
    }

    public List<DocumentDetailsVO> getDocumentList() {
	return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
	this.documentList = documentList;
    }

    public Long getSwCount1() {
	return swCount1;
    }

    public void setSwCount1(Long swCount1) {
	this.swCount1 = swCount1;
    }

    public Long getSwCount2() {
	return swCount2;
    }

    public void setSwCount2(Long swCount2) {
	this.swCount2 = swCount2;
    }

    public Long getSwCount3() {
	return swCount3;
    }

    public void setSwCount3(Long swCount3) {
	this.swCount3 = swCount3;
    }

    public Long getSwCount4() {
	return swCount4;
    }

    public void setSwCount4(Long swCount4) {
	this.swCount4 = swCount4;
    }

    public String getSwUserCategory1Reg() {
	return swUserCategory1Reg;
    }

    public void setSwUserCategory1Reg(String swUserCategory1Reg) {
	this.swUserCategory1Reg = swUserCategory1Reg;
    }

    public String getSwUserCategory2Reg() {
	return swUserCategory2Reg;
    }

    public void setSwUserCategory2Reg(String swUserCategory2Reg) {
	this.swUserCategory2Reg = swUserCategory2Reg;
    }

    public String getSwUserCategory3Reg() {
	return swUserCategory3Reg;
    }

    public void setSwUserCategory3Reg(String swUserCategory3Reg) {
	this.swUserCategory3Reg = swUserCategory3Reg;
    }

    public String getSwUserCategory4Reg() {
	return swUserCategory4Reg;
    }

    public void setSwUserCategory4Reg(String swUserCategory4Reg) {
	this.swUserCategory4Reg = swUserCategory4Reg;
    }

    public String getSwUserCategory5Reg() {
	return swUserCategory5Reg;
    }

    public void setSwUserCategory5Reg(String swUserCategory5Reg) {
	this.swUserCategory5Reg = swUserCategory5Reg;
    }

    public SolidWasteBillMasterDTO getTbSwNewBillMas() {
	return tbSwNewBillMas;
    }

    public void setTbSwNewBillMas(SolidWasteBillMasterDTO tbSwNewBillMas) {
	this.tbSwNewBillMas = tbSwNewBillMas;
    }

    public Long getSwCod1() {
	return swCod1;
    }

    public void setSwCod1(Long swCod1) {
	this.swCod1 = swCod1;
    }

    public Long getSwCod2() {
	return swCod2;
    }

    public void setSwCod2(Long swCod2) {
	this.swCod2 = swCod2;
    }

    public Long getSwCod3() {
	return swCod3;
    }

    public void setSwCod3(Long swCod3) {
	this.swCod3 = swCod3;
    }

    public Long getSwCod4() {
	return swCod4;
    }

    public void setSwCod4(Long swCod4) {
	this.swCod4 = swCod4;
    }

    public Long getSwCod5() {
	return swCod5;
    }

    public void setSwCod5(Long swCod5) {
	this.swCod5 = swCod5;
    }

}