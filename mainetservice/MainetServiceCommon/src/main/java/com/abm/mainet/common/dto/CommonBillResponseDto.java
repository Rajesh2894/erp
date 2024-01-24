/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cherupelli.srikanth
 * @since 18 Jan 2023
 */
public class CommonBillResponseDto implements Serializable {

	private static final long serialVersionUID = 726379871625317064L;

	private int status;

	private String reason;

	private double billAmount;

	private String billIdentifier;

	private String houseNo;

	private Map<String, String> chargeslipInfo = new HashMap<String, String>();

	private ArrayList<Long> allowedPaymentModes = new ArrayList<Long>();

	private String zone;

	private String ward;

	private String mohalla;

	private Double arv;

	private String propertyType;

	private String mobileNo;

	private Double arrears;

	private Double currentDemand;

	private Double totalDemand;

	private Double totalInterest;

	private Double totalAmountToBePaid;

	private Double rebateAmount;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public String getBillIdentifier() {
		return billIdentifier;
	}

	public void setBillIdentifier(String billIdentifier) {
		this.billIdentifier = billIdentifier;
	}

	public Map<String, String> getChargeslipInfo() {
		return chargeslipInfo;
	}

	public void setChargeslipInfo(Map<String, String> chargeslipInfo) {
		this.chargeslipInfo = chargeslipInfo;
	}

	public ArrayList<Long> getAllowedPaymentModes() {
		return allowedPaymentModes;
	}

	public void setAllowedPaymentModes(ArrayList<Long> allowedPaymentModes) {
		this.allowedPaymentModes = allowedPaymentModes;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getMohalla() {
		return mohalla;
	}

	public void setMohalla(String mohalla) {
		this.mohalla = mohalla;
	}

	public Double getArv() {
		return arv;
	}

	public void setArv(Double arv) {
		this.arv = arv;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getCurrentDemand() {
		return currentDemand;
	}

	public void setCurrentDemand(Double currentDemand) {
		this.currentDemand = currentDemand;
	}

	public Double getTotalDemand() {
		return totalDemand;
	}

	public void setTotalDemand(Double totalDemand) {
		this.totalDemand = totalDemand;
	}

	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Double getTotalAmountToBePaid() {
		return totalAmountToBePaid;
	}

	public void setTotalAmountToBePaid(Double totalAmountToBePaid) {
		this.totalAmountToBePaid = totalAmountToBePaid;
	}

	public Double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	
}
