package com.abm.mainet.rts.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.abm.mainet.rts.service.IDeathCertificateService;

@Component
@Scope("session")
public class DeathCertificateModel extends AbstractFormModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IDeathCertificateService iDeathCertificateService;
	
	@Autowired
	private IPortalServiceMasterService iPortalService;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
	
	private ChallanReceiptPrintDTO receiptDTO = null;
	
	private RequestDTO requestDTO = new RequestDTO();
	
	private String saveMode;

	private String checkListApplFlag = null;

	private String applicationChargeFlag = null;
	
	private String formName;
	
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		deathCertificateDTO.setLangId(UserSession.getCurrent().getLanguageId());
		deathCertificateDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		deathCertificateDTO.setUserId(employee.getEmpId());
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
		.prepareFileUpload(checkList);
		deathCertificateDTO.setDocumentList(checkList);
		deathCertificateDTO.setUploadDocument(checkList);
		requestDTO.setUpdatedBy(deathCertificateDTO.getUpdatedBy());
		requestDTO.setMacId(deathCertificateDTO.getLgIpMacUpd());
		requestDTO.setLangId(Long.valueOf(deathCertificateDTO.getLangId()));
		
		CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		offline.setAmountToShow(deathCertificateDTO.getAmount());
		deathCertificateDTO.getOfflineDTO().setOfflinePaymentText(offline.getOfflinePaymentText());
		deathCertificateDTO.getOfflineDTO().setOnlineOfflineCheck(getOfflineDTO().getOnlineOfflineCheck());
		deathCertificateDTO.getOfflineDTO().setAmountToShow(offline.getAmountToShow());
		deathCertificateDTO.setChargesInfo(this.getChargesInfo());
		deathCertificateDTO.setChargesAmount(this.getChargesAmount());
		deathCertificateDTO.getOfflineDTO().setOflPaymentMode(offline.getOflPaymentMode());
		deathCertificateDTO.setRequestDTO(requestDTO);
		deathCertificateDTO.setChargeApplicableAt(this.getDeathCertificateDTO().getChargeApplicableAt());
		DeathCertificateDTO deathDTO =iDeathCertificateService.savedeathCertificateDeatils(getDeathCertificateDTO());
		this.setReceiptDTO(deathDTO.getReceiptDTO());
		this.setOfflineDTO(deathDTO.getOfflineDTO());
		this.getOfflineDTO().setOflPaymentMode(offline.getOflPaymentMode());
		this.setDeathCertificateDTO(deathDTO);
		
		RtsServiceFormModel model = ApplicationContextProvider.getApplicationContext().getBean(RtsServiceFormModel.class);
        model.setApmApplicationId(deathDTO.getApplnId());
        model.setCheckList(this.getCheckList());
		setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.succes.msg")
	 					+ deathDTO.getApplnId());
		
		return true;
	}
	
	
	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final DeathCertificateDTO reqDTO = this.getDeathCertificateDTO();
		final PortalService portalServiceMaster = iPortalService.getService(reqDTO.getRequestDTO().getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(reqDTO.getApplnId()));
		String fullName = String.join(" ", Arrays.asList(reqDTO.getRequestDTO().getfName(), reqDTO.getRequestDTO().getmName(),
				reqDTO.getRequestDTO().getlName()));
		payURequestDTO.setApplicantName(fullName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(reqDTO.getApplnId()));
		payURequestDTO.setMobNo(reqDTO.getRequestDTO().getMobileNo());
		
		if (reqDTO.getChargesAmount() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(reqDTO.getChargesAmount()));
		}
		 
		// payURequestDTO.setDueAmt(paymentAmount);
		payURequestDTO.setEmail(reqDTO.getRequestDTO().getEmail());
		payURequestDTO.setApplicationId(reqDTO.getApplnId().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}
	
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public DeathCertificateDTO getDeathCertificateDTO() {
		return deathCertificateDTO;
	}

	public void setDeathCertificateDTO(DeathCertificateDTO deathCertificateDTO) {
		this.deathCertificateDTO = deathCertificateDTO;
	}

	public ChallanReceiptPrintDTO getReceiptDTO() {
		return receiptDTO;
	}

	public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
		this.receiptDTO = receiptDTO;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}


	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}


	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}


	public String getApplicationChargeFlag() {
		return applicationChargeFlag;
	}


	public void setApplicationChargeFlag(String applicationChargeFlag) {
		this.applicationChargeFlag = applicationChargeFlag;
	}


	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}


	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}


	public String getFormName() {
		return formName;
	}


	public void setFormName(String formName) {
		this.formName = formName;
	}
	
}
