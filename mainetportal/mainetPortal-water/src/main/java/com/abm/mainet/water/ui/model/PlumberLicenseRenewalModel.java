/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.IPlumberLicenseFormService;
import com.abm.mainet.water.service.IWaterBRMSService;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Component
@Scope("session")
public class PlumberLicenseRenewalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4174546854449658851L;

	@Autowired
	private IPortalServiceMasterService iPortalService;
	@Autowired
	private IChallanService iChallanService;
	@Autowired
	private IPlumberLicenseFormService iPlumberLicenseFormService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;
	private PlumberLicenseRequestDTO plumberLicenseReqDTO;
	private List<PlumberQualificationDTO> plumberQualificationDTOList = new ArrayList<>();
	private List<PlumberExperienceDTO> plumberExperienceDTOList = new ArrayList<>();
	private Long deptId;
	private List<DocumentDetailsVO> checkList;
	private String isFree;
	private double amountToPay;
	private String checkListNCharges;
	private String plumberImage;
	private double totalExp;
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private String flag;

	private Map<Long, Double> chargesMap = new HashMap<>();
	private List<LookUp> lookUpList = new ArrayList<>(0);
	@Autowired
	private IWaterBRMSService checklistAndChargeService;

	@Override
	protected final String findPropertyPathPrefix(final String parentCode) {
		switch (parentCode) {

		case MainetConstants.NewWaterServiceConstants.WWZ:

			return "applicantDetailDto.dwzid";

		default:
			return null;
		}
	}

	/**
	 * This method is used for getting checklist and charges.
	 */
	public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {

		// [START] BRMS call initialize model
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName("ChecklistModel|WaterRateMaster");
		final WSRequestDTO initDTO = new WSRequestDTO();
		initDTO.setModelName(MainetConstants.MODEL_NAME);
		final WSResponseDTO response = iCommonBRMSService.initializeModel(initDTO);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			setCheckListNCharges(MainetConstants.YES);
			final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(serviceCode);
			checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
			checkListModel2.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
			dto.setDataModel(checkListModel2);
			checkListList = iCommonBRMSService.getChecklist(checkListModel2);
			if ((checkListList != null) && !checkListList.isEmpty()) {
				long cnt = 1;
				for (final DocumentDetailsVO doc : checkListList) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
				if ((checkListList != null) && !checkListList.isEmpty()) {
					setCheckList(checkListList);
				}
			}

			final WSResponseDTO res = checklistAndChargeService.getApplicableTaxes(WaterRateMaster,
					UserSession.getCurrent().getOrganisation().getOrgid(), serviceCode);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
				if (!res.isFree()) {
					final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final WaterRateMaster master1 = (WaterRateMaster) rate;
						populateChargeModel(master1);
						master1.setOrgId(orgId);
						master1.setServiceCode(serviceCode);
						requiredCHarges.add(master1);
					}
					final List<ChargeDetailDTO> detailDTOs = checklistAndChargeService
							.getApplicableCharges(requiredCHarges);
					setIsFree(MainetConstants.NewWaterServiceConstants.NO);
					setChargesInfo(detailDTOs);
					setAmountToPay(chargesToPay(detailDTOs));
					this.setChargesMap(detailDTOs);
					setIsFree(MainetConstants.NO);
					getOfflineDTO().setAmountToShow(getAmountToPay());
				} else {
					setIsFree(MainetConstants.NewWaterServiceConstants.YES);
				}
			} else {
				throw new FrameworkException("Problem while checking Application level charges applicable or not.");
			}
		} else {
			throw new FrameworkException(
					"Problem while checking Application level charges and Checklist applicable or not.");
		}
	}

	private void populateChargeModel(final WaterRateMaster master1) {
		master1.setIsBPL(getApplicantDetailDto().getIsBPL());

		master1.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		master1.setRateStartDate(new Date().getTime());
		// master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
		// UserSession.getCurrent().getOrganisation()));
	}

	private void setChargesMap(final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		this.setChargesMap(chargesMap);

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	/**
	 * This method is used for validating saving data
	 */
	@Override
	public boolean saveForm() {
		try {
			final ApplicantDetailDTO applicantDetailDTO = getApplicantDetailDto();

			List<DocumentDetailsVO> docs = getCheckList();
			docs = setFileUploadMethod(docs);
			validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
			final boolean result = validateInputs(docs, getPlumberImage());
			final CommonChallanDTO offline = getOfflineDTO();
			if (MainetConstants.NewWaterServiceConstants.NO.equals(getIsFree())) {
				final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
				offline.setOfflinePaymentText(modeDesc);
				validateBean(offline, CommonOfflineMasterValidator.class);
			}
			if (hasValidationErrors() || result) {
				return false;
			}
			final PlumberLicenseRequestDTO requestDTO = getPlumberLicenseReqDTO();
			requestDTO.setDocumentList(docs);
			requestDTO.setPlumberFName(applicantDetailDTO.getApplicantFirstName());
			requestDTO.setPlumberMName(applicantDetailDTO.getApplicantMiddleName());
			requestDTO.setPlumberLName(applicantDetailDTO.getApplicantLastName());
			requestDTO.setPlumSex(applicantDetailDTO.getGender());
			requestDTO.setPlumAppDate(new Date());
			setUpdateFields(requestDTO);
			requestDTO.setDocumentList(getCheckList());
			requestDTO.setServiceId(getServiceId());
			requestDTO.setDeptId(getDeptId());
			requestDTO.setApplicant(getApplicantDetailDto());
			requestDTO.setPlumberExperienceDTOList(getPlumberExperienceDTOList());
			requestDTO.setPlumberQualificationDTOList(getPlumberQualificationDTOList());
			requestDTO.setAmount(getAmountToPay());
			PlumberLicenseResponseDTO outPutObject = null;

			outPutObject = iPlumberLicenseFormService.savePlumberRenewalData(requestDTO);

			if ((outPutObject != null) && (outPutObject.getApplicationId() != null)) {
				ApplicationPortalMaster applicationMaster = null;
				requestDTO.setApplicationId(outPutObject.getApplicationId());
				applicationMaster = saveApplcationMaster(getServiceId(), outPutObject.getApplicationId(),
						FileUploadUtility.getCurrent().getFileMap().entrySet().size());
				iPortalService.saveApplicationMaster(applicationMaster, getAmountToPay(),
						FileUploadUtility.getCurrent().getFileMap().entrySet().size());
				if (getAmountToPay() > 0d) {
					setChallanDToandSaveChallanData(offline, getApplicantDetailDto(), outPutObject);
				}
				setSuccessMessage(ApplicationSession.getInstance().getMessage("Your application for License Renewal saved successfully",
						new Object[] { String.valueOf(outPutObject.getApplicationId()) }));
			} else {
				throw new FrameworkException(
						"Sorry,Your application for Plumber has not been saved due to some technical problem.");
			}
		} catch (final Exception e1) {
			throw new FrameworkException(
					"Sorry,Your application for Plumber has not been saved due to some technical problem.", e1);
		}
		return true;
	}

	public ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {
		final PortalService appealMaster = iPortalService.getService(serviceId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		calculateDate(appealMaster, applicationMaster, documentListSize);
		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;
	}

	public ApplicationPortalMaster calculateDate(final PortalService portalService,
			final ApplicationPortalMaster applicationMaster, final int documentListSize) throws Exception {

		final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		if (documentListSize > 0) {
			applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.YES); // Y
		} else {
			applicationMaster.setPamChecklistApp(MainetConstants.Common_Constant.NO); // N

			calendar.add(Calendar.DATE, 0);
			final String output1 = sdf.format(calendar.getTime());
			applicationMaster.setPamDocVerificationDate(sdf.parse(output1));

			calendar.setTime(sdf.parse(output1));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSlaDays().toString()));
			final String output2 = sdf.format(calendar.getTime());
			applicationMaster.setPamSlaDate(sdf.parse(output2));

			calendar.setTime(sdf.parse(output2));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getFirstAppealDuration().toString()));
			final String output3 = sdf.format(calendar.getTime());
			applicationMaster.setPamFirstAppealDate(sdf.parse(output3));

			calendar.setTime(sdf.parse(output3));
			calendar.add(Calendar.DATE, Integer.parseInt(portalService.getSecondAppealDuration().toString()));
			final String output4 = sdf.format(calendar.getTime());
			applicationMaster.setPamSecondAppealDate(sdf.parse(output4));
		}

		return applicationMaster;

	}

	/**
	 * This method is used for generating byte code for uploaded files
	 * 
	 * @param docs
	 *            is list of uploaded file
	 * @return uploaded file name and byte code
	 */
	private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			/*
			 * for (final Iterator<Entry<Long, Set<File>>> it =
			 * FileUploadUtility.getCurrent().getFileMap().entrySet() .iterator();
			 * it.hasNext();) { final Entry<Long, Set<File>> entry = it.next(); if
			 * (entry.getKey().longValue() == 0) { it.remove(); break; } }
			 */

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						logger.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		if (!docs.isEmpty() && !listOfString.isEmpty()) {
			for (final DocumentDetailsVO d : docs) {
				final long count = d.getDocumentSerialNo();
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
			}
		}
		return docs;
	}

	/**
	 * This method is used for set common field
	 */
	private void setUpdateFields(final PlumberLicenseRequestDTO plumberDTO) {
		final UserSession session = UserSession.getCurrent();
		plumberDTO.setUserId(session.getEmployee().getEmpId());
		plumberDTO.setLangId((long) session.getLanguageId());
		plumberDTO.setOrgId(session.getOrganisation().getOrgid());
		plumberDTO.setLgIpMac(session.getEmployee().getEmppiservername());
	}

	/**
	 * This is method used to save challan details.
	 * 
	 * @param offline
	 *            is offline details
	 * @param applicantDetailDto
	 *            is applicant details
	 * @param outPutObject
	 *            is application related details
	 */
	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final ApplicantDetailDTO applicantDetailDto,
			final PlumberLicenseResponseDTO outPutObject) {
		offline.setApplNo(outPutObject.getApplicationId());
		offline.setAmountToPay(String.valueOf(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		offline.setEmailId(applicantDetailDto.getEmailId());
		offline.setApplicantName((applicantDetailDto.getApplicantFirstName() != null
				? applicantDetailDto.getApplicantFirstName() + MainetConstants.WHITE_SPACE
				: MainetConstants.BLANK)
				+ (applicantDetailDto.getApplicantMiddleName() != null
						? applicantDetailDto.getApplicantMiddleName() + " "
						: MainetConstants.WHITE_SPACE)
				+ (applicantDetailDto.getApplicantLastName() != null ? applicantDetailDto.getApplicantLastName()
						: MainetConstants.BLANK));
		offline.setMobileNumber(applicantDetailDto.getMobileNo());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(getServiceId());
		if (FileUploadUtility.getCurrent().getFileMap().entrySet().size() > 0) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		for (final ChargeDetailDTO charges : getChargesInfo()) {
			offline.getFeeIds().put(charges.getChargeCode(), charges.getChargeAmount());
		}
		offline.setDeptId(getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			offline = iChallanService.generateChallanNumber(offline);
		}
		setOfflineDTO(offline);
	}

	/**
	 * This method is used for Online Payment PayU parameter.
	 */
	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final PlumberLicenseRequestDTO dto = getPlumberLicenseReqDTO();
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		String userName = (applicant.getApplicantFirstName() == null ? ""
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? ""
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? "" : applicant.getApplicantLastName().trim();
		final PortalService portalServiceMaster = iPortalService.getService(dto.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(dto.getApplicationId()));
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf6(applicant.getCfcCitizenId());
		payURequestDTO.setApplicationId(dto.getApplicationId().toString());
		payURequestDTO.setDueAmt(new BigDecimal(this.getAmountToPay()));
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		payURequestDTO.setApplicantName(userName.trim());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(String.valueOf(portalServiceMaster.getPsmDpDeptid()));
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	/**
	 * This method is used for validate User Details
	 */
	private boolean validateInputs(final List<DocumentDetailsVO> dto, final String plumberPhoto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(ApplicationSession.getInstance()
								.getMessage("water.plumberLicense.valMsg.uploadDocument"));
						flag = true;
						break;
					}
				}
			}

		}

		/*if ((plumberPhoto == null) || MainetConstants.BLANK.equals(plumberPhoto)) {
			addValidationError(
					ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.uploadPlumberPhoto"));
			flag = true;
		}*/
		return flag;

	}

	public PlumberLicenseRequestDTO getPlumberLicenseReqDTO() {
		return plumberLicenseReqDTO;
	}

	public void setPlumberLicenseReqDTO(PlumberLicenseRequestDTO plumberLicenseReqDTO) {
		this.plumberLicenseReqDTO = plumberLicenseReqDTO;
	}

	public List<PlumberQualificationDTO> getPlumberQualificationDTOList() {
		return plumberQualificationDTOList;
	}

	public void setPlumberQualificationDTOList(List<PlumberQualificationDTO> plumberQualificationDTOList) {
		this.plumberQualificationDTOList = plumberQualificationDTOList;
	}

	public List<PlumberExperienceDTO> getPlumberExperienceDTOList() {
		return plumberExperienceDTOList;
	}

	public void setPlumberExperienceDTOList(List<PlumberExperienceDTO> plumberExperienceDTOList) {
		this.plumberExperienceDTOList = plumberExperienceDTOList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getCheckListNCharges() {
		return checkListNCharges;
	}

	public void setCheckListNCharges(String checkListNCharges) {
		this.checkListNCharges = checkListNCharges;
	}

	public String getPlumberImage() {
		return plumberImage;
	}

	public void setPlumberImage(String plumberImage) {
		this.plumberImage = plumberImage;
	}

	public double getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(double totalExp) {
		this.totalExp = totalExp;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public List<LookUp> getLookUpList() {
		return lookUpList;
	}

	public void setLookUpList(List<LookUp> lookUpList) {
		this.lookUpList = lookUpList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

}
