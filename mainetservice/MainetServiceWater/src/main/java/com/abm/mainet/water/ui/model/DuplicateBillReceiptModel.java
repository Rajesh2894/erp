/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.DuplicateBillPrintingService;
/**
 * @author akshata.bhat
 *
 */
@Component
@Scope("session")
public class DuplicateBillReceiptModel extends AbstractFormModel{


	private static final long serialVersionUID = 1129331368823722196L;

	@Autowired
	private DuplicateBillPrintingService duplicatePaymentReceiptService;

	@Resource
    private TbTaxMasService tbTaxMasService;
	
	private List<TbDepartment> departmentList;

	private int langId;

	private String paymentCheck;

	private ServiceMaster serviceMaster = new ServiceMaster();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();

	private String applicationCharge;
	private String receiptFlag;
	private Long rmRcptid;
	private Long serviceId;
	private List<String> finYearList;
	private Long finYear;
	private String connectionNo;
	private String billNo;
	private Integer noOfCopies;
	private List<TbBillMas> billList;

	public List<String> getFinYearList() {
		return finYearList;
	}

	public void setFinYearList(List<String> finYearList) {
		this.finYearList = finYearList;
	}

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getApplicationCharge() {
		return applicationCharge;
	}

	public void setApplicationCharge(String applicationCharge) {
		this.applicationCharge = applicationCharge;
	}

	public String getReceiptFlag() {
		return receiptFlag;
	}

	public void setReceiptFlag(String receiptFlag) {
		this.receiptFlag = receiptFlag;
	}

	public Long getRmRcptid() {
		return rmRcptid;
	}

	public void setRmRcptid(Long rmRcptid) {
		this.rmRcptid = rmRcptid;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}
	
	private Map<Long, String> finYearData = new LinkedHashMap<>();

	@Override
	public boolean saveForm() {

		getOfflineDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		duplicatePaymentReceiptService.saveData(this, getOfflineDTO());
		setSuccessMessage(getAppSession().getMessage("CFC.application.successMsg") + this.getConnectionNo());
		return true;
	}
	
	public boolean validateInputs() {
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	/*
	 * public List<TbWtBillMasEntity> getBillDetails() { return billDetails; }
	 * 
	 * public void setBillDetails(List<TbWtBillMasEntity> billDetails) {
	 * this.billDetails = billDetails; }
	 */
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Long getFinYear() {
		return finYear;
	}

	public void setFinYear(Long finYear) {
		this.finYear = finYear;
	}

	public Map<Long, String> getFinYearData() {
		return finYearData;
	}

	public void setFinYearData(Map<Long, String> finYearData) {
		this.finYearData = finYearData;
	}

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public List<TbBillMas> getBillList() {
		return billList;
	}

	public void setBillList(List<TbBillMas> billList) {
		this.billList = billList;
	}

}
