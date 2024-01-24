package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.service.DuplicatePaymentReceiptService;
import com.abm.mainet.additionalservices.service.DuplicatePaymentReceiptServiceImpl;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbReceiptDuplicateEntity;
import com.abm.mainet.common.dto.CommonAcknowledgementDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.ui.validator.ReceiptFormValidator;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class DuplicatePaymentReceiptModel extends AbstractFormModel {

	private static final long serialVersionUID = 1129331368823722196L;

	@Autowired
	private DuplicatePaymentReceiptService duplicatePaymentReceiptService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	private List<TbDepartment> departmentList;

	private int langId;

	private String paymentCheck = null;

	TbServiceReceiptMasBean receiptMasBean = new TbServiceReceiptMasBean();

	TbServiceReceiptMasEntity receiptMasEntity = new TbServiceReceiptMasEntity();

	private List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<>();

	TbReceiptDuplicateEntity receiptDuplicateEntity;

	private TbCfcApplicationMst cfcApplicationMst;

	private CFCApplicationAddressEntity cfcAppAddressEntity;

	TbReceiptDuplicateDTO receiptDuplicateDTO;

	private ServiceMaster serviceMaster = new ServiceMaster();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	
	CommonAcknowledgementDto ackDto = new CommonAcknowledgementDto();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private String applicationCharge;
	private String receiptFlag;
	private Long rmRcptid;
	private Long serviceId;
   //Defect #145245
	private List<String> finYearList;

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

	public TbServiceReceiptMasBean getReceiptMasBean() {
		return receiptMasBean;
	}

	public void setReceiptMasBean(TbServiceReceiptMasBean receiptMasBean) {
		this.receiptMasBean = receiptMasBean;
	}

	public List<TbServiceReceiptMasBean> getReceiptMasBeanList() {
		return receiptMasBeanList;
	}

	public void setReceiptMasBeanList(List<TbServiceReceiptMasBean> receiptMasBeanList) {
		this.receiptMasBeanList = receiptMasBeanList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public TbReceiptDuplicateEntity getReceiptDuplicateEntity() {
		return receiptDuplicateEntity;
	}

	public void setReceiptDuplicateEntity(TbReceiptDuplicateEntity receiptDuplicateEntity) {
		this.receiptDuplicateEntity = receiptDuplicateEntity;
	}

	public TbCfcApplicationMst getCfcApplicationMst() {
		return cfcApplicationMst;
	}

	public void setCfcApplicationMst(TbCfcApplicationMst cfcApplicationMst) {
		this.cfcApplicationMst = cfcApplicationMst;
	}

	public CFCApplicationAddressEntity getCfcAppAddressEntity() {
		return cfcAppAddressEntity;
	}

	public void setCfcAppAddressEntity(CFCApplicationAddressEntity cfcAppAddressEntity) {
		this.cfcAppAddressEntity = cfcAppAddressEntity;
	}

	public String getApplicationCharge() {
		return applicationCharge;
	}

	public void setApplicationCharge(String applicationCharge) {
		this.applicationCharge = applicationCharge;
	}

	public TbReceiptDuplicateDTO getReceiptDuplicateDTO() {
		return receiptDuplicateDTO;
	}

	public void setReceiptDuplicateDTO(TbReceiptDuplicateDTO receiptDuplicateDTO) {
		this.receiptDuplicateDTO = receiptDuplicateDTO;
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

	public TbServiceReceiptMasEntity getReceiptMasEntity() {
		return receiptMasEntity;
	}

	public void setReceiptMasEntity(TbServiceReceiptMasEntity receiptMasEntity) {
		this.receiptMasEntity = receiptMasEntity;
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

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();

		CFCApplicationAddressEntity addressEntity = getCfcAppAddressEntity();
		addressEntity.setOrgId(UserSession.getCurrent().getOrganisation());
		addressEntity.setLgIpMac(lgIp);
		addressEntity.setLangId((long) UserSession.getCurrent().getLanguageId());
		addressEntity.setUserId(createdBy);

		TbCfcApplicationMst cfcApplicationMst = getCfcApplicationMst();
		cfcApplicationMst.setOrgid(orgId);
		cfcApplicationMst.setLangId((long) UserSession.getCurrent().getLanguageId());
		cfcApplicationMst.setUserId(createdBy);
		cfcApplicationMst.setLgIpMac(lgIp);

		TbReceiptDuplicateDTO receiptDuplicateDto = new TbReceiptDuplicateDTO();
		receiptDuplicateDto.setOrgId(orgId);
		receiptDuplicateDto.setCreatedBy(createdBy);
		receiptDuplicateDto.setCreatedDate(newDate);
		receiptDuplicateDto.setLgIpMac(lgIp);
		receiptDuplicateDto.setRmRcpNo(receiptMasEntity.getRmRcptno());
		receiptDuplicateDto.setRmDate(receiptMasEntity.getRmDate());
		String deptCode = departmentService.getDeptCode(receiptMasEntity.getDpDeptId());
		if (StringUtils.isNotEmpty(deptCode))
			receiptDuplicateDto.setDeptCode(deptCode);
		receiptDuplicateDto.setRmRcpId(receiptMasEntity.getRmRcptid());
		String serviceShortCode = serviceMasterService.fetchServiceShortCode(receiptMasEntity.getSmServiceId(),
				receiptMasEntity.getOrgId());
		if (StringUtils.isNotEmpty(serviceShortCode))
			receiptDuplicateDto.setServiceCode(serviceShortCode);
		receiptDuplicateDto.setDupReceiptData(new Date().toString());
		receiptDuplicateDto.setApplicationId(receiptMasEntity.getApmApplicationId());
		cfcApplicationMst.setApmAppRejFlag(null);
		TbCfcApplicationMst appMst = duplicatePaymentReceiptService.saveData(cfcApplicationMst, addressEntity, receiptDuplicateDto,
				this);
        //User Story #147721
		if(appMst!=null && appMst.getApmAppRejFlag()!=null) {
			this.addValidationError(appMst.getApmAppRejFlag());
			return false;
		}
		else
			this.addValidationError(null);
		setSuccessMessage(getAppSession().getMessage("CFC.application.successMsg") + appMst.getApmApplicationId());
		return true;
	}

	
	public boolean validateInputs() {
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public CommonAcknowledgementDto getAckDto() {
		return ackDto;
	}

	public void setAckDto(CommonAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
}
