package com.abm.mainet.rts.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.abm.mainet.rts.service.IBirthCertificateService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthCertificateModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4083705507473672484L;

	BirthCertificateDTO birthCertificateDto = new BirthCertificateDTO();

	private RequestDTO requestDTO = new RequestDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	@Autowired
	private IBirthCertificateService birthCertificateService;
	
	@Autowired
	private IPortalServiceMasterService iPortalService;

	private Map<Long, Double> chargesMap = new HashMap<>();
	
	private String saveMode;
	
	private String checkListApplFlag = null;
	
	private String applicationChargeFlag = null;
	
	private String formName;
	
	private List<DocumentDetailsVO> documentList = new ArrayList<>();

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public BirthCertificateDTO getBirthCertificateDto() {
		return birthCertificateDto;
	}

	public void setBirthCertificateDto(BirthCertificateDTO birthCertificateDto) {
		this.birthCertificateDto = birthCertificateDto;
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

	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		BirthCertificateDTO statusDto = new BirthCertificateDTO();
		birthCertificateDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		birthCertificateDto.setLmoddate(new Date());
		birthCertificateDto.setLangId(langId);
		birthCertificateDto.setUserId(employee.getEmpId());
		birthCertificateDto.setUpdatedDate(new Date());
		birthCertificateDto.setLgIpMac(employee.getEmppiservername());
		birthCertificateDto.setLgIpMacUpd(employee.getEmppiservername());
		this.getRequestDTO().setLangId(Long.valueOf(birthCertificateDto.getLangId()));
		birthCertificateDto.setRequestDTO(this.getRequestDTO());
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.prepareFileUpload(checkList);
		birthCertificateDto.setUploadDocument(checkList);
		CommonChallanDTO offline = getOfflineDTO();
		this.getOfflineDTO().setOflPaymentMode(offline.getOflPaymentMode());
		offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
		birthCertificateDto.getOfflineDTO().setOfflinePaymentText(offline.getOfflinePaymentText());
		birthCertificateDto.getOfflineDTO().setOnlineOfflineCheck(getOfflineDTO().getOnlineOfflineCheck());
		birthCertificateDto.getOfflineDTO().setAmountToShow(offline.getAmountToShow());
		birthCertificateDto.setChargesInfo(this.getChargesInfo());
		birthCertificateDto.setChargesAmount(this.getChargesAmount());
		birthCertificateDto.getOfflineDTO().setOflPaymentMode(offline.getOflPaymentMode());
		birthCertificateDto.setChargeApplicableAt(this.getBirthCertificateDto().getChargeApplicableAt());
		statusDto = birthCertificateService.saveBirthCertificateP(birthCertificateDto);
		this.setOfflineDTO(statusDto.getOfflineDTO());
        this.setBirthCertificateDto(statusDto);
   
        RtsServiceFormModel model = ApplicationContextProvider.getApplicationContext().getBean(RtsServiceFormModel.class);
        model.setApmApplicationId(statusDto.getApmApplicationId());
        model.setCheckList(this.getCheckList());
		setSuccessMessage(getAppSession().getMessage("BirthCertificateDTO.successMsg")
				+ statusDto.getApplicationId());

		return true;

	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final BirthCertificateDTO reqDTO = this.getBirthCertificateDto();
		final PortalService portalServiceMaster = iPortalService.getService(reqDTO.getRequestDTO().getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(reqDTO.getApplicationId());
		String fullName = String.join(" ", Arrays.asList(reqDTO.getRequestDTO().getfName(), reqDTO.getRequestDTO().getmName(),
				reqDTO.getRequestDTO().getlName()));
		payURequestDTO.setApplicantName(fullName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(reqDTO.getApmApplicationId()));
		payURequestDTO.setMobNo(reqDTO.getRequestDTO().getMobileNo());
		
		if (reqDTO.getChargesAmount() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(reqDTO.getChargesAmount()));
		}
		 
		// payURequestDTO.setDueAmt(paymentAmount);
		payURequestDTO.setEmail(reqDTO.getRequestDTO().getEmail());
		payURequestDTO.setApplicationId(reqDTO.getApmApplicationId().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}
	
	
}
