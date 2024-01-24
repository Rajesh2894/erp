/**
 * 
 */
package com.abm.mainet.common.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cherupelli.srikanth
 * @since 03 March 2021
 */
public class CommonChallanPayModeDTO {


	 private Long billMasId;

	    private String billMasNo;

	    private Long bmBankAccountId;

	    private Long rcptModeId;

	    private Long payModeIn;                                                                     // holds
	    // Payment
	    // Mode

	    private String bmDrawOn;                                                                    // Banks IFSC Code

	    private Long bmChqDDNo;                                                                     // hold
	    // cheque
	    // No

	    private Date bmChqDDDate;

	    private Double amount;

	    private Long micrCode;

	    private String remark;

	    private String receiptNo;

	    private Date receiptDate;

	    private Long receiptAmouont;

	    private Long rmRcptId;

	    private String ccnNum;

	    private String ccnName;

	    private String errorMesg;
	    
	    private Long supBillIdNo;
	    
	    private List<Long> modeIds= new ArrayList<Long>();
	    
	    private Long cbBankid;
	    
	    private String payModeDesc;
	    
	    private String bmChqDDDateTemp;
	    
	    private String bankName;

		public Long getBillMasId() {
			return billMasId;
		}

		public void setBillMasId(Long billMasId) {
			this.billMasId = billMasId;
		}

		public String getBillMasNo() {
			return billMasNo;
		}

		public void setBillMasNo(String billMasNo) {
			this.billMasNo = billMasNo;
		}

		public Long getBmBankAccountId() {
			return bmBankAccountId;
		}

		public void setBmBankAccountId(Long bmBankAccountId) {
			this.bmBankAccountId = bmBankAccountId;
		}

		public Long getRcptModeId() {
			return rcptModeId;
		}

		public void setRcptModeId(Long rcptModeId) {
			this.rcptModeId = rcptModeId;
		}

		public Long getPayModeIn() {
			return payModeIn;
		}

		public void setPayModeIn(Long payModeIn) {
			this.payModeIn = payModeIn;
		}

		public String getBmDrawOn() {
			return bmDrawOn;
		}

		public void setBmDrawOn(String bmDrawOn) {
			this.bmDrawOn = bmDrawOn;
		}

		public Long getBmChqDDNo() {
			return bmChqDDNo;
		}

		public void setBmChqDDNo(Long bmChqDDNo) {
			this.bmChqDDNo = bmChqDDNo;
		}

		public Date getBmChqDDDate() {
			return bmChqDDDate;
		}

		public void setBmChqDDDate(Date bmChqDDDate) {
			this.bmChqDDDate = bmChqDDDate;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public Long getMicrCode() {
			return micrCode;
		}

		public void setMicrCode(Long micrCode) {
			this.micrCode = micrCode;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getReceiptNo() {
			return receiptNo;
		}

		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}

		public Date getReceiptDate() {
			return receiptDate;
		}

		public void setReceiptDate(Date receiptDate) {
			this.receiptDate = receiptDate;
		}

		public Long getReceiptAmouont() {
			return receiptAmouont;
		}

		public void setReceiptAmouont(Long receiptAmouont) {
			this.receiptAmouont = receiptAmouont;
		}

		public Long getRmRcptId() {
			return rmRcptId;
		}

		public void setRmRcptId(Long rmRcptId) {
			this.rmRcptId = rmRcptId;
		}

		public String getCcnNum() {
			return ccnNum;
		}

		public void setCcnNum(String ccnNum) {
			this.ccnNum = ccnNum;
		}

		public String getCcnName() {
			return ccnName;
		}

		public void setCcnName(String ccnName) {
			this.ccnName = ccnName;
		}

		public String getErrorMesg() {
			return errorMesg;
		}

		public void setErrorMesg(String errorMesg) {
			this.errorMesg = errorMesg;
		}

		public Long getSupBillIdNo() {
			return supBillIdNo;
		}

		public void setSupBillIdNo(Long supBillIdNo) {
			this.supBillIdNo = supBillIdNo;
		}

		public List<Long> getModeIds() {
			return modeIds;
		}

		public void setModeIds(List<Long> modeIds) {
			this.modeIds = modeIds;
		}

		public Long getCbBankid() {
			return cbBankid;
		}

		public void setCbBankid(Long cbBankid) {
			this.cbBankid = cbBankid;
		}

		public String getPayModeDesc() {
			return payModeDesc;
		}

		public void setPayModeDesc(String payModeDesc) {
			this.payModeDesc = payModeDesc;
		}

		public String getBmChqDDDateTemp() {
			return bmChqDDDateTemp;
		}

		public void setBmChqDDDateTemp(String bmChqDDDateTemp) {
			this.bmChqDDDateTemp = bmChqDDDateTemp;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
	    

		
}
