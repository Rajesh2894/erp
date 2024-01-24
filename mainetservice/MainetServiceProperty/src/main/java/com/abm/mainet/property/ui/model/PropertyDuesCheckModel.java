package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.ProperySearchDto;

/**
 * @author anwarul.hassan
 * @since 23-Feb-2022
 */
@Component
@Scope("session")
public class PropertyDuesCheckModel  extends AbstractFormModel{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyDuesCheckModel.class);

	private ProperySearchDto searchDto = new ProperySearchDto();
	private List<ProperySearchDto> searchDtoResult = new ArrayList<>();
	
	private double currentBalAmt = 0d;
	
	private double surchargeAmount = 0d;
	
	private double curSurchargeAmount = 0d;
	
	private double totalPaybale = 0d;
	
	private double adjustmentAmount = 0d;
	
	private double advanceAmount = 0d;

	private double rebateAmount = 0d;
	
	private double totalArrearsAmt = 0d;
	
	private double totalPenalty = 0d;

	private String skdclEnv;

	private String showForm;
	
	private String billingMethod;
	
	private List<String> flatNoList;

	private String positiveAajustment;
	
	private String showFlag;
	
	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public List<ProperySearchDto> getSearchDtoResult() {
		return searchDtoResult;
	}

	public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
		this.searchDtoResult = searchDtoResult;
	}

	public double getCurrentBalAmt() {
		return currentBalAmt;
	}

	public void setCurrentBalAmt(double currentBalAmt) {
		this.currentBalAmt = currentBalAmt;
	}

	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public double getCurSurchargeAmount() {
		return curSurchargeAmount;
	}

	public void setCurSurchargeAmount(double curSurchargeAmount) {
		this.curSurchargeAmount = curSurchargeAmount;
	}

	public double getTotalPaybale() {
		return totalPaybale;
	}

	public void setTotalPaybale(double totalPaybale) {
		this.totalPaybale = totalPaybale;
	}

	public double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public double getTotalArrearsAmt() {
		return totalArrearsAmt;
	}

	public void setTotalArrearsAmt(double totalArrearsAmt) {
		this.totalArrearsAmt = totalArrearsAmt;
	}

	public double getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(double totalPenalty) {
		this.totalPenalty = totalPenalty;
	}

	public String getSkdclEnv() {
		return skdclEnv;
	}

	public void setSkdclEnv(String skdclEnv) {
		this.skdclEnv = skdclEnv;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(String showForm) {
		this.showForm = showForm;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getPositiveAajustment() {
		return positiveAajustment;
	}

	public void setPositiveAajustment(String positiveAajustment) {
		this.positiveAajustment = positiveAajustment;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
}
