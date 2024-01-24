/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.checklist.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.checklist.dto.DocumentResubmissionRequestDTO;
import com.abm.mainet.common.checklist.dto.DocumentResubmissionResponseDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.ui.validator.IllegalToLegalConnectionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Component
@Scope("session")
public class IllegalToLegalConnectionModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IChallanService challanService;

	private static String conenctionUrl = ServiceEndpoints.ServiceCallURI.GET_UPLOAD_DOCUMENT_FOR_NEWWATER;
	private NewWaterConnectionReqDTO reqDTO = new NewWaterConnectionReqDTO();

	private NewWaterConnectionResponseDTO responseDTO = new NewWaterConnectionResponseDTO();

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private TbCsmrInfoDTO csmrInfoView = new TbCsmrInfoDTO();

	private ApplicantDetailDTO applicantDetailDtoView = new ApplicantDetailDTO();

	private Long serviceId;

	private Long orgId;

	private Long deptId;

	private Long langId;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<DocumentDetailsVO> checkListView = new ArrayList<>();

	private Double charges = 0.0d;

	private String free = MainetConstants.NewWaterServiceConstants.OPEN;

	private Map<Long, Double> chargesMap = new HashMap<>();

	private Long hiddenTitle;

	private String hiddenFname;

	private String hiddenMname;

	private String hiddenLname;

	private String hiddenGender;

	private String hiddenMobile;

	private String hiddenEmail;

	private String mode;

	private String saveMode = MainetConstants.NO;

	private String isBillingSame = MainetConstants.YES;

	private String isConsumerSame = MainetConstants.NewWaterServiceConstants.YES;
	private PortalService serviceMaster = null;

	private List<DocumentDetailsVO> checkListForPreview = new ArrayList<>();

	private List<CFCAttachmentsDTO> attachmentList = new ArrayList<>(0);

	private String serviceDuration;
	private String paymentMode;
	private String propOutStanding;
	public List<PlumberDTO> plumberList = new ArrayList<>();

	@Override
	public boolean saveForm() {
		CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		if (((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO))
				|| (getCharges() > 0d)) {
			offline.setApplNo(responseDTO.getApplicationNo());
			offline.setAmountToPay(Double.toString(getCharges()));
			offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			offline.setLangId(UserSession.getCurrent().getLanguageId());
			offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
			if ((getCheckList() != null) && (getCheckList().size() > 0)) {
				offline.setDocumentUploaded(true);
			} else {
				offline.setDocumentUploaded(false);
			}
			offline.setServiceId(getServiceId());
			offline.setApplicantName((getApplicantDetailDto().getApplicantFirstName() != null
					? getApplicantDetailDto().getApplicantFirstName() + " "
					: MainetConstants.BLANK)
					+ (getApplicantDetailDto().getApplicantMiddleName() != null
							? getApplicantDetailDto().getApplicantMiddleName() + " "
							: MainetConstants.WHITE_SPACE)
					+ (getApplicantDetailDto().getApplicantLastName() != null
							? getApplicantDetailDto().getApplicantLastName()
							: MainetConstants.BLANK));
			offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
			offline.setEmailId(getApplicantDetailDto().getEmailId());
			for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
				offline.getFeeIds().put(entry.getKey(), entry.getValue());
			}
			final PortalService portalServiceMaster = getServiceMaster();
			offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.YES)) {
				setPaymentMode("Online");
			}
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
				setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
						UserSession.getCurrent().getOrganisation()).getLookUpDesc());
				offline = challanService.generateChallanNumber(offline);
				setOfflineDTO(offline);
			}
		}
		return true;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {

		final TbCsmrInfoDTO reqDTO = getCsmrInfo();
		final NewWaterConnectionResponseDTO responseDTO = getResponseDTO();
		String userName = (reqDTO.getCsName() == null ? MainetConstants.WHITE_SPACE : reqDTO.getCsName().trim())
				+ MainetConstants.WHITE_SPACE;
		userName += reqDTO.getCsMname() == null ? MainetConstants.WHITE_SPACE
				: reqDTO.getCsMname().trim() + MainetConstants.WHITE_SPACE;
		userName += reqDTO.getCsLname() == null ? MainetConstants.WHITE_SPACE : reqDTO.getCsLname().trim();
		final PortalService portalServiceMaster = getServiceMaster();
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(responseDTO.getApplicationNo()));
		payURequestDTO.setApplicantName(userName.trim());
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(responseDTO.getApplicationNo()));
		payURequestDTO.setMobNo(reqDTO.getCsContactno());
		payURequestDTO.setServiceName(MainetConstants.NewWaterServiceConstants.SERVICE_NAME);
		if (getCharges() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getCharges()));
		}
		payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
		payURequestDTO.setApplicationId(responseDTO.getApplicationNo().toString());

		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	public boolean validateInputs() {
		validateBean(this, IllegalToLegalConnectionValidator.class);
		if (getSaveMode().equals(MainetConstants.NewWaterServiceConstants.YES)) {
			if (MainetConstants.NewWaterServiceConstants.NO.equals(getFree())) {
				validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
			}
		}

		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public List<LookUp> getDocumentsList() {
		final List<LookUp> documentDetailsList = new ArrayList<>(0);

		LookUp lookUp = null;
		for (final CFCAttachmentsDTO temp : attachmentList) {
			lookUp = new LookUp(temp.getAttId(), temp.getAttPath());

			lookUp.setOtherField(temp.getMandatory());
			lookUp.setDescLangFirst(temp.getClmDescEngl());
			lookUp.setDescLangSecond(temp.getClmDesc());
			lookUp.setLookUpId(temp.getClmId());
			lookUp.setLookUpCode(temp.getAttFname());
			lookUp.setLookUpType(temp.getClmStatus());
			lookUp.setLookUpParentId(temp.getAttId());
			lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
			lookUp.setDescLangSecond(temp.getAttPath());
			lookUp.setExtraStringField1(temp.getClmRemark());

			documentDetailsList.add(lookUp);
		}
		return documentDetailsList;
	}

	public void querySearchResults(Long applicationId) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		if (applicationId != null) {
			final DocumentResubmissionRequestDTO request = new DocumentResubmissionRequestDTO();
			request.setApplicationId(applicationId);
			request.setApplicationStatus(MainetConstants.BLANK);
			request.setOrgId(orgId);

			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(request, conenctionUrl);

			final String response = new JSONObject(responseVo).toString();

			DocumentResubmissionResponseDTO responseDTO = null;
			try {
				responseDTO = new ObjectMapper().readValue(response, DocumentResubmissionResponseDTO.class);
			} catch (final IOException e) {
				throw new RuntimeException(MainetConstants.ERROR_OCCURED, e);
			}

			attachmentList = responseDTO.getAttachmentList();

		}
	}

	public NewWaterConnectionReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(NewWaterConnectionReqDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public NewWaterConnectionResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(NewWaterConnectionResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;
	}

	public TbCsmrInfoDTO getCsmrInfoView() {
		return csmrInfoView;
	}

	public void setCsmrInfoView(TbCsmrInfoDTO csmrInfoView) {
		this.csmrInfoView = csmrInfoView;
	}

	public ApplicantDetailDTO getApplicantDetailDtoView() {
		return applicantDetailDtoView;
	}

	public void setApplicantDetailDtoView(ApplicantDetailDTO applicantDetailDtoView) {
		this.applicantDetailDtoView = applicantDetailDtoView;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<DocumentDetailsVO> getCheckListView() {
		return checkListView;
	}

	public void setCheckListView(List<DocumentDetailsVO> checkListView) {
		this.checkListView = checkListView;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public Long getHiddenTitle() {
		return hiddenTitle;
	}

	public void setHiddenTitle(Long hiddenTitle) {
		this.hiddenTitle = hiddenTitle;
	}

	public String getHiddenFname() {
		return hiddenFname;
	}

	public void setHiddenFname(String hiddenFname) {
		this.hiddenFname = hiddenFname;
	}

	public String getHiddenMname() {
		return hiddenMname;
	}

	public void setHiddenMname(String hiddenMname) {
		this.hiddenMname = hiddenMname;
	}

	public String getHiddenLname() {
		return hiddenLname;
	}

	public void setHiddenLname(String hiddenLname) {
		this.hiddenLname = hiddenLname;
	}

	public String getHiddenGender() {
		return hiddenGender;
	}

	public void setHiddenGender(String hiddenGender) {
		this.hiddenGender = hiddenGender;
	}

	public String getHiddenMobile() {
		return hiddenMobile;
	}

	public void setHiddenMobile(String hiddenMobile) {
		this.hiddenMobile = hiddenMobile;
	}

	public String getHiddenEmail() {
		return hiddenEmail;
	}

	public void setHiddenEmail(String hiddenEmail) {
		this.hiddenEmail = hiddenEmail;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getIsBillingSame() {
		return isBillingSame;
	}

	public void setIsBillingSame(String isBillingSame) {
		this.isBillingSame = isBillingSame;
	}

	public String getIsConsumerSame() {
		return isConsumerSame;
	}

	public void setIsConsumerSame(String isConsumerSame) {
		this.isConsumerSame = isConsumerSame;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<DocumentDetailsVO> getCheckListForPreview() {
		return checkListForPreview;
	}

	public void setCheckListForPreview(List<DocumentDetailsVO> checkListForPreview) {
		this.checkListForPreview = checkListForPreview;
	}

	public List<CFCAttachmentsDTO> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<CFCAttachmentsDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(String serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPropOutStanding() {
		return propOutStanding;
	}

	public void setPropOutStanding(String propOutStanding) {
		this.propOutStanding = propOutStanding;
	}

	public List<PlumberDTO> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberDTO> plumberList) {
		this.plumberList = plumberList;
	}
}
