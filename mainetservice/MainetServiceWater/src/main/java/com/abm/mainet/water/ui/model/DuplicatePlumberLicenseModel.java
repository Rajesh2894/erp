package com.abm.mainet.water.ui.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.DuplicatePlumberLicenseService;
import com.abm.mainet.water.service.PlumberLicenseService;

@Component
@Scope("session")
public class DuplicatePlumberLicenseModel extends AbstractFormModel implements Serializable {

	private static final long serialVersionUID = 7066690001782939196L;

	@Autowired
	private PlumberLicenseService plumberLicenseService;

	@Autowired
	private ICFCApplicationMasterService icfcApplicationMasterService;

	@Autowired
	private DuplicatePlumberLicenseService duplicatePlumberLicenseService;

	@Resource
	private ServiceMasterService iServiceMasterService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private BRMSWaterService brmsWaterService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private IChallanService iChallanService;

	@Autowired
	private TbLoiMasService iTbLoiMasService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	private PlumberLicenseRequestDTO plumDto = new PlumberLicenseRequestDTO();

	private TbCfcApplicationMstEntity cfcEntity;

	private ApplicantDetailDTO applicantDetailDto;

	private PlumberMaster plumberMaster;

	private String serviceName;

	private Long applicationId;

	private Long plumberId;

	private String fileDownLoadPath;

	private String appovalStatus;

	private String eventName;

	private boolean hideRejection;

	private boolean loiStatus;

	private Double totalAmount;

	private String checkListNCharges;
	private List<CFCAttachment> documentList = null;
	private List<DocumentDetailsVO> checkList;
	PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
	private double amountToPay;
	private String isFree;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private Long deptId;
	private Double total = new Double(0d);
	private TbLoiMas entity = new TbLoiMas();
	private String loiId;
	private List<PlumberQualificationDTO> plumberQualificationDTOList;
	private List<PlumberExperienceDTO> plumberExperienceDTOList;
	private String totalExp;
	private List<LookUp> lookUpList = new ArrayList<>(0);
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private boolean finalProcess;

	public void populateApplicationData(final long applicationId) {
		setApplicationId(applicationId);
		plumDto = getPlumDto();
		if (plumDto == null) {
			plumDto = new PlumberLicenseRequestDTO();
		}
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		applicantDetailDto = new ApplicantDetailDTO();
		cfcEntity = icfcApplicationMasterService.getCFCApplicationByApplicationId(applicationId, orgId);
		if (null != cfcEntity) {
			setServiceId(cfcEntity.getTbServicesMst().getSmServiceId());
			applicantDetailDto.setGender(Utility.getGenderId(cfcEntity.getApmSex()));
		}
		PlumberLicenseRequestDTO pDto = plumberLicenseService.getPlumberDetailsByLicenseNumber(orgId,
				cfcEntity.getRefNo());
		setPlumberMaster(plumberLicenseService.getPlumberDetailsByAppId(pDto.getApplicationId(), orgId));

		if (null != plumberMaster) {
			plumberId = plumberMaster.getPlumId();
			plumDto.setPlumberId(plumberId);
			if (plumberMaster.getPlumLicFromDate() != null)
				plumDto.setPlumLicFromDate(plumberMaster.getPlumLicFromDate());
			if (plumberMaster.getPlumLicToDate() != null)
				plumDto.setPlumLicToDate(plumberMaster.getPlumLicToDate());
			if (plumberMaster.getPlumLicNo() != null)
				plumDto.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
			// setInterviewDateTimeD(plumberMaster.getPlumInterviewDate());
			// setInterviewRemark(plumberMaster.getPlumInterviewRemark());
			if (plumberMaster.getPlumLicNo() != null && !plumberMaster.getPlumLicNo().isEmpty())
				plumDto.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
			setFileDownLoadPath(
					Utility.downloadedFileUrl(plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR
							+ plumberMaster.getPlumImage(), outputPath, getFileNetClient()));
			setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
		}

		ApplicantDetailDTO dto = getApplicantDetailDto();
		if (dto == null) {
			dto = new ApplicantDetailDTO();
		}
		/*
		 * applicantDetailDto =
		 * initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity),
		 * cfcService.getApplicantsDetails(applicationId));
		 */
		plumDto.setApplicant(pDto.getApplicant());
		setApplicantDetailDto(pDto.getApplicant());
		final List<PlumberQualificationDTO> plumberQualificationDTOs = plumberLicenseService
				.getPlumberQualificationDetails(plumberId, orgId);
		plumDto.setPlumberQualificationDTOList(plumberQualificationDTOs);
		setPlumberQualificationDTOList(plumberQualificationDTOs);
		final List<PlumberExperienceDTO> plumberExperienceDTOs = plumberLicenseService
				.getPlumberExperienceDetails(plumberId, orgId);
		plumDto.setPlumberExperienceDTOList(plumberExperienceDTOs);
		setPlumberExperienceDTOList(plumberExperienceDTOs);
		BigInteger sumYear = BigInteger.ZERO;
		BigInteger sumMonth = BigInteger.ZERO;
		BigInteger totalMonth = BigInteger.ZERO;
		if ((plumberExperienceDTOs != null) && !plumberExperienceDTOs.isEmpty()) {
			for (final PlumberExperienceDTO plumberExperienceDTO : plumberExperienceDTOs) {
				final Long year = plumberExperienceDTO.getPlumExpYear();
				final Long month = plumberExperienceDTO.getPlumExpMonth();
				if (year != null) {
					sumYear = sumYear.add(BigInteger.valueOf(year));
				}
				if (month != null) {
					sumMonth = sumMonth.add(BigInteger.valueOf(month));
				}
			}
		}
		String userName = (cfcEntity.getApmFname() == null ? MainetConstants.BLANK
				: cfcEntity.getApmFname() + MainetConstants.WHITE_SPACE);
		userName += cfcEntity.getApmMname() == null ? MainetConstants.BLANK
				: cfcEntity.getApmMname() + MainetConstants.WHITE_SPACE;
		userName += cfcEntity.getApmLname() == null ? MainetConstants.BLANK : cfcEntity.getApmLname();
		plumDto.setPlumberFullName(userName);
		final BigInteger yearToMonth = sumYear.multiply(BigInteger.valueOf(12));
		totalMonth = yearToMonth.add(sumMonth);
		final BigInteger divide = totalMonth.divide(BigInteger.valueOf(12));
		final BigInteger remainder = totalMonth.mod(BigInteger.valueOf(12));
		final String exp = divide.toString() + MainetConstants.operator.DOT + remainder.toString();
		setTotalExp(exp);
		/*
		 * final ServiceMaster service = cfcEntity.getTbServicesMst(); if
		 * (MainetConstants.MENU.Y.equals(service.getSmScrutinyApplicableFlag())) {
		 * setScrutinyApplicable(true); }
		 */
	}

	@Override
	public boolean saveForm() {
		if (hasValidationErrors()) {
			return false;
		}
		final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		getPlumDto().setDeptId(deptId);
		getPlumDto().setLangId(Long.valueOf(getUserSession().getLanguageId()));
		getPlumDto().setUserId(getUserSession().getEmployee().getEmpId());
		getPlumDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.setSuccessMessage("Application Is Saved Sucessfully");
		try {
			List<DocumentDetailsVO> docs = mapCheckList(getCheckList(), getBindingResult());
			getPlumDto().setDocumentList(docs);
			getPlumDto().setDocumentList(docs);
			setResponseDTO(duplicatePlumberLicenseService.saveDuplicatePlumberData(plumDto));
		} catch (final Exception e1) {
			throw new FrameworkException(
					"Sorry,Your application for Duplicate Plumber has not been saved due to some technical problem.",
					e1);
		}
		return true;
	}

	public List<DocumentDetailsVO> mapCheckList(final List<DocumentDetailsVO> docs, final BindingResult bindingResult) {

		final List<DocumentDetailsVO> docList = fileUploadService.prepareFileUpload(docs);

		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO doc : docList) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
						bindingResult.addError(new ObjectError(MainetConstants.BLANK,
								ApplicationSession.getInstance().getMessage("water.fileuplaod.validtnMsg")));

						break;
					}
				}
			}
		}

		return docList;

	}

	public void getDuplicatePlumberDetails(Long applicationNumber) {
		setApplicationId(applicationNumber);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long plumId = duplicatePlumberLicenseService.getPlumberMasterPlumId(applicationNumber, orgId);
		if (plumId != null) {
			final List<PlumberLicenseRequestDTO> plumberLicenseRequestDTO = duplicatePlumberLicenseService
					.getPlumberDetailsByPlumId(plumId, orgId);
			if (plumberLicenseRequestDTO != null && !plumberLicenseRequestDTO.isEmpty()) {
				this.setPlumDto(plumberLicenseRequestDTO.get(0));
			}
		}

	}

	/**
	 * This method is used for getting checklist and charges.
	 */
	@SuppressWarnings("unchecked")
	public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName("ChecklistModel|WaterRateMaster");
		final WSRequestDTO initDTO = new WSRequestDTO();

		initDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
		final WSResponseDTO response = brmsCommonService.initializeModel(initDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			setCheckListNCharges(MainetConstants.FlagY);
			final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(serviceCode);
			checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
			checkListModel2.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
			dto.setDataModel(checkListModel2);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
				List<DocumentDetailsVO> checkListList = Collections.emptyList();
				checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

				long cnt = 1;
				for (final DocumentDetailsVO doc : checkListList) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
				if ((checkListList != null) && !checkListList.isEmpty()) {
					setCheckList(checkListList);
				}
			} else {
				throw new FrameworkException(
						"Problem while checking Application level charges and Checklist applicable or not.");
			}
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			WaterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			WaterRateMaster.setServiceCode(serviceCode);
			WaterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			taxRequestDto.setDataModel(WaterRateMaster);
			WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
				if (!res.isFree()) {
					final List<Object> rates = (List<Object>) res.getResponseObj();
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final WaterRateMaster master1 = (WaterRateMaster) rate;
						populateChargeModel(master1);
						master1.setOrgId(orgId);
						master1.setServiceCode(serviceCode);
						requiredCHarges.add(master1);
					}
					WSRequestDTO chargeReqDto = new WSRequestDTO();
					chargeReqDto.setModelName("WaterRateMaster");
					chargeReqDto.setDataModel(requiredCHarges);
					WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
					List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
					setChargesInfo(detailDTOs);
					setAmountToPay(chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
					getPlumDto().setAmount(getAmountToPay());
					setIsFree(MainetConstants.Common_Constant.NO);
					this.setChargesMap(detailDTOs);
					getOfflineDTO().setAmountToShow(getAmountToPay());
					if (getAmountToPay() == 0.0d) {
						throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
								+ " if service configured as Chageable");
					}

				} else {
					throw new FrameworkException("Problem while checking dependent param for water rate .");
				}
			} else {
				throw new FrameworkException(
						"Problem while checking Application level charges and Checklist applicable or not.");
			}
		} else {
			throw new FrameworkException(
					"Problem while checking Application level charges and Checklist applicable or not.");
		}

	}

	public boolean save() {

		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		// getPlumberLicenseReqDTO().setPayMode(offline.getOnlineOfflineCheck());
		if (((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO))
				|| (getAmountToPay() > 0d)) {
			offline.setApplNo(getPlumDto().getApplicationId());
			offline.setAmountToPay(Double.toString(getAmountToPay()));
			offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			offline.setLangId(UserSession.getCurrent().getLanguageId());
			offline.setDeptId(getDeptId());
			offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
			if ((getCheckList() != null) && (getCheckList().size() > 0)) {
				offline.setDocumentUploaded(true);
			} else {
				offline.setDocumentUploaded(false);
			}
			offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			offline.setFaYearId(UserSession.getCurrent().getFinYearId());
			offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
			offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
			offline.setServiceId(getServiceId());
			String userName = (getApplicantDetailDto().getApplicantFirstName() == null ? MainetConstants.BLANK
					: getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE);
			userName += getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
					: getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
			userName += getApplicantDetailDto().getApplicantLastName() == null ? MainetConstants.BLANK
					: getApplicantDetailDto().getApplicantLastName();
			offline.setApplicantName(userName);
			offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
			offline.setEmailId(getApplicantDetailDto().getEmailId());
			for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
				offline.getFeeIds().put(entry.getKey(), entry.getValue());
			}
			offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
				final ChallanMaster challanNumber = iChallanService.InvokeGenerateChallan(offline);
				offline.setChallanNo(challanNumber.getChallanNo());
				offline.setChallanValidDate(challanNumber.getChallanValiDate());
				setOfflineDTO(offline);
			} else if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
				final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
				setReceiptDTO(printDto);
				setSuccessMessage(getAppSession().getMessage("water.receipt"));
			}
		}
		return true;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setChargesMap(final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		this.setChargesMap(chargesMap);

	}

	private void populateChargeModel(final WaterRateMaster master1) {
		master1.setIsBPL(getApplicantDetailDto().getIsBPL());

		master1.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		master1.setRateStartDate(new Date().getTime());
	}

	public boolean plumberUpdateAction() {

		WorkflowTaskAction wfAction = this.getWorkflowActionDto();
		UserSession userSession = UserSession.getCurrent();
		Long empId = userSession.getEmployee().getEmpId();
		String empName = userSession.getEmployee().getEmpname();
		wfAction.setCreatedBy(empId);
		wfAction.setEmpName(empName);
		wfAction.setCreatedDate(new Date());
		wfAction.setDateOfAction(new Date());
		wfAction.setIsFinalApproval(isFinalProcess());
		wfAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
		wfAction.setEmpId(empId);
		wfAction.setOrgId(userSession.getOrganisation().getOrgid());
		return plumberLicenseService.executeWfAction(wfAction, "DPL", getServiceId());

	}

	public boolean saveLoiData() {
		boolean status;
		final UserSession session = UserSession.getCurrent();
		Long serviceId = this.getServiceId();
		Map<Long, Double> loiCharges = plumberLicenseService.getLoiCharges(applicationId, serviceId,
				session.getOrganisation().getOrgid(), "DPL", getPlumDto().getPlumLicToDate());
		//T#90050
		if (MapUtils.isNotEmpty(loiCharges)) {
		    List<TbLoiDet> loiDetail = new ArrayList<TbLoiDet>();
		    TbLoiMas loiMasDto = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class).saveLOIAppData(
	                    loiCharges,serviceId, loiDetail, true/* approvalLetterGenerationApplicable */,
	                    getWorkflowActionDto());
		    if(loiMasDto != null) {
		        setLoiId(loiMasDto.getLoiNo());
		        return true;
		    }
		}
		return false;
	}

	public PlumberLicenseRequestDTO getPlumDto() {
		return plumDto;
	}

	public void setPlumDto(PlumberLicenseRequestDTO plumDto) {
		this.plumDto = plumDto;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public PlumberMaster getPlumberMaster() {
		return plumberMaster;
	}

	public void setPlumberMaster(PlumberMaster plumberMaster) {
		this.plumberMaster = plumberMaster;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getPlumberId() {
		return plumberId;
	}

	public void setPlumberId(Long plumberId) {
		this.plumberId = plumberId;
	}

	public String getFileDownLoadPath() {
		return fileDownLoadPath;
	}

	public void setFileDownLoadPath(String fileDownLoadPath) {
		this.fileDownLoadPath = fileDownLoadPath;
	}

	public String getAppovalStatus() {
		return appovalStatus;
	}

	public void setAppovalStatus(String appovalStatus) {
		this.appovalStatus = appovalStatus;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public boolean isHideRejection() {
		return hideRejection;
	}

	public void setHideRejection(boolean hideRejection) {
		this.hideRejection = hideRejection;
	}

	public boolean isLoiStatus() {
		return loiStatus;
	}

	public void setLoiStatus(boolean loiStatus) {
		this.loiStatus = loiStatus;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCheckListNCharges() {
		return checkListNCharges;
	}

	public void setCheckListNCharges(String checkListNCharges) {
		this.checkListNCharges = checkListNCharges;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public PlumberLicenseResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(PlumberLicenseResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public TbLoiMas getEntity() {
		return entity;
	}

	public void setEntity(TbLoiMas entity) {
		this.entity = entity;
	}

	public String getLoiId() {
		return loiId;
	}

	public void setLoiId(String loiId) {
		this.loiId = loiId;
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

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public List<LookUp> getLookUpList() {
		return lookUpList;
	}

	public void setLookUpList(List<LookUp> lookUpList) {
		this.lookUpList = lookUpList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public boolean isFinalProcess() {
		return finalProcess;
	}

	public void setFinalProcess(boolean finalProcess) {
		this.finalProcess = finalProcess;
	}

}
