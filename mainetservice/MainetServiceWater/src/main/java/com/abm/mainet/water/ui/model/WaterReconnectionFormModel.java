package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.dto.RoadTypeDTO;
import com.abm.mainet.water.dto.WaterApplicationAcknowledgementDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterReconnectionService;

/**
 * @author Arun.Chavda
 *
 */

@Component
@Scope("session")
public class WaterReconnectionFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 2560570009227338264L;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private WaterReconnectionService waterReconnectionService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private WaterCommonService waterCommonService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private BRMSWaterService brmsWaterService;

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	DesignationService designationService;

	@Autowired
	IEmployeeService employeeService;

	private List<CFCAttachment> documentList = new ArrayList<>();
	private List<RoadTypeDTO> roadTypeList = new ArrayList<>();
	private TbWaterReconnection waterReconnection = new TbWaterReconnection();
	private CFCApplicationAddressEntity cfcAddressEntity;
	private TbCfcApplicationMstEntity cfcEntity;
	private boolean scrutinyApplicable;
	private ApplicantDetailDTO applicantDetailDto;
	private String discType;
	private String discMethod;
	private Long connectionSize;
	private String tarrifCate;
	private String primiseType;
	private String connectionNo;
	private String consumerName;
	private String discDate;
	private String discRemarks;
	private String plumberLicNo;
	private String isULBPlumber;
	private String plumber;
	private Long reconnectionMethod;
	private String reconnectionRemark;
	private Long applicationId;
	private Long serviceId;
	private Long consumerId;
	private Long plumberId;
	private WaterReconnectionRequestDTO reconnRequestDTO = new WaterReconnectionRequestDTO();
	private WaterReconnectionResponseDTO reconnResponseDTO = new WaterReconnectionResponseDTO();
	public List<PlumberMaster> plumberList = new ArrayList<>();
	public List<PlumberMaster> ulbPlumberList = new ArrayList<>();
	private long deptId;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private String checkListNCharges;
	private List<DocumentDetailsVO> checkList;
	private double amountToPay;
	private String isFree;
	private String csoldCcn;
	
	private WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO;
	private ServiceMaster serviceMaster;

	@Override
	public void populateApplicationData(final long applicationId) {
		setApplicationId(applicationId);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
		applicantDetailDto = new ApplicantDetailDTO();
		cfcEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);

		if (null != cfcEntity) {
			setServiceId(cfcEntity.getTbServicesMst().getSmServiceId());
		}
		applicantDetailDto = commonService.populateApplicantInfo(getApplicantDetailDto(), cfcEntity);
		CFCApplicationAddressEntity addressEntity = cfcService.getApplicantsDetails(applicationId);
		if (addressEntity.getApaZoneNo() != null)
			applicantDetailDto.setDwzid1(addressEntity.getApaZoneNo());
		if (addressEntity.getApaWardNo() != null)
			applicantDetailDto.setDwzid2(addressEntity.getApaWardNo());
		setIsULBPlumber(MainetConstants.Common_Constant.YES);
		waterReconnection = waterReconnectionService.getReconnectionDetails(applicationId, orgId);

		if (null != waterReconnection.getPlumId()) {
			/*
			 * final PlumberMaster plumberMaster =
			 * waterCommonService.getPlumberDetailsById(waterReconnection.getPlumId());
			 * setPlumberLicNo(plumberMaster.getPlumLicNo());
			 * setIsULBPlumber(MainetConstants.Common_Constant.NO);
			 */
			setPlumberList(waterCommonService.listofplumber(orgId));
			Designation dsg = designationService.findByShortname("PLM");
			List<PlumberMaster> plumberList = new ArrayList<>();
			PlumberMaster master = null;
			if (dsg != null) {

				List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getDsgid(), orgId);
				if (!empList.isEmpty()) {
					for (final Object empObj[] : empList) {
						master = new PlumberMaster();
						master.setPlumId(Long.valueOf(empObj[0].toString()));
						master.setPlumFname(empObj[1].toString());
						master.setPlumMname(empObj[2].toString());
						master.setPlumLname(empObj[3].toString());
						plumberList.add(master);
					}

				}
			}
			setUlbPlumberList(plumberList);
		}

		final TbKCsmrInfoMH connectionDetails = waterReconnectionService
				.getWaterConnectionDetailsById(waterReconnection.getCsIdn(), orgId);
		final Organisation organisation = UserSession.getCurrent().getOrganisation();
		final LookUp tarrifCate = CommonMasterUtility.getHierarchicalLookUp(connectionDetails.getTrmGroup1(),
				organisation);
		final LookUp disType = CommonMasterUtility.getNonHierarchicalLookUpObject(waterReconnection.getDiscType(),
				organisation);
		final LookUp disMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(waterReconnection.getDiscMethod(),
				organisation);

		setTarrifCate(tarrifCate.getLookUpDesc());
		// setPrimiseType(preType.getLookUpDesc());
		setConnectionSize(connectionDetails.getCsCcnsize());
		setConnectionNo(connectionDetails.getCsCcn());
		setConsumerName(connectionDetails.getCsName());
		setDiscMethod(disMethod.getLookUpDesc());
		setDiscType(disType.getLookUpDesc());
		setDiscRemarks(waterReconnection.getDiscReason());
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		final java.util.Date time = waterReconnection.getDiscAppdate();
		setDiscDate(simpleDateFormat.format(time));
		setConsumerId(waterReconnection.getCsIdn());
		setPlumberId(waterReconnection.getPlumId());
		setReconnectionRemark(waterReconnection.getRcnRemark());
		setReconnectionMethod(waterReconnection.getRccnMethod());
		final ServiceMaster service = cfcEntity.getTbServicesMst();
		if ((service.getSmScrutinyApplicableFlag() != null)
				&& service.getSmScrutinyApplicableFlag().equals(MainetConstants.Common_Constant.YES)) {
			setScrutinyApplicable(true);
		}
	}

	@Override
	public boolean saveForm() {

		final boolean result = validateFormData(getReconnectionMethod());

		if (result || hasValidationErrors()) {
			return false;
		}
		final TbWaterReconnection reconnection = getWaterReconnection();
		reconnection.setRcnDate(new Date());
		reconnection.setRccnMethod(getReconnectionMethod());
		reconnection.setRcnRemark(getReconnectionRemark());
		waterReconnectionService.updatedWaterReconnectionDetailsByDept(reconnection);

		return true;
	}

	private boolean validateFormData(final Long reconnectionMethod) {

		boolean status = false;

		if ((reconnectionMethod == null) || (reconnectionMethod.longValue() == 0l)) {
			addValidationError(getAppSession().getMessage("recon.method"));
			status = true;
		}
		return status;
	}

	public boolean saveWaterReconnectionform() {

		if (MainetConstants.NewWaterServiceConstants.NO.equals(getIsFree())) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}
		validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);

		List<DocumentDetailsVO> docs = getCheckList();
		docs = setFileUploadMethod(docs);
		mapCheckList(docs, getBindingResult());
		getReconnRequestDTO().setUploadedDocList(docs);
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			  getReconnRequestDTO().setDocumentList(docs);
		}
		 
		if (hasValidationErrors()) {
			return false;
		}
		final CommonChallanDTO offline = getOfflineDTO();
		setWaterReconnectionData();
		final WaterReconnectionResponseDTO outPutObject = waterReconnectionService
				.saveWaterReconnectionDetails(getReconnRequestDTO());
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(outPutObject.getStatus())) {
			if (outPutObject.getApplicationId() != null) {
				getReconnRequestDTO().setApplicationId(outPutObject.getApplicationId());
				waterReconnectionService.initiateWorkflowForFreeService(getReconnRequestDTO());
				
				if (getReconnRequestDTO().getAmount() > 0d) {
					proceedForChallan(offline, outPutObject);
				}
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					waterApplicationAcknowledgementDTO =  new WaterApplicationAcknowledgementDTO();
					waterApplicationAcknowledgementDTO.setServiceName(getServiceName());
					
					StringBuilder applicantName = new StringBuilder();
					if(getApplicantDetailDto().getApplicantFirstName() != null)
						applicantName.append(getApplicantDetailDto().getApplicantFirstName());
					
					if(getApplicantDetailDto().getApplicantMiddleName() != null) {
						applicantName.append(MainetConstants.WHITE_SPACE);
						applicantName.append(getApplicantDetailDto().getApplicantMiddleName());
					}
					if(getApplicantDetailDto().getApplicantLastName() != null) {
						applicantName.append(MainetConstants.WHITE_SPACE);
						applicantName.append(getApplicantDetailDto().getApplicantLastName());
					}
						
					waterApplicationAcknowledgementDTO.setApplicantName(applicantName.toString());
					
					waterApplicationAcknowledgementDTO.setAppTime(new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(new Date()));
					waterApplicationAcknowledgementDTO.setCheckList(getCheckList());
					waterApplicationAcknowledgementDTO.setApplicationId(outPutObject.getApplicationId().toString());
					cfcEntity = cfcService.getCFCApplicationByApplicationId(outPutObject.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
			        serviceMaster = cfcEntity.getTbServicesMst();
					waterApplicationAcknowledgementDTO.setDepartmentName(serviceMaster.getTbDepartment().getDpDeptdesc());
					setDepartmentCode(serviceMaster.getTbDepartment().getDpDeptcode());
					long duration = 0;
					if (serviceMaster.getSmServiceDuration() != null) {
						duration = serviceMaster.getSmServiceDuration();
						waterApplicationAcknowledgementDTO.setWtConnDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(Utility.getAddedDateBy2(new Date(), (int)duration)));
					}
				}
			} else {
				logger.error("Exception occured while saving application");
			}
		} else {
			throw new FrameworkException("web service call failed to save Water Reconnection form");
		}
		return true;
	}

	private void proceedForChallan(CommonChallanDTO offline, WaterReconnectionResponseDTO outPutObject) {

		offline.setApplNo(outPutObject.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(getReconnRequestDTO().getServiceId());
		offline.setApplicantName(getApplicantDetailDto().getApplicantFirstName());
		offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
		offline.setPropNoConnNoEstateNoV(connectionNo);
		offline.setReferenceNo(csoldCcn);
		for (final ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}
		if ((getCheckList() != null) && !getCheckList().isEmpty()) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("water.receipt"));
			
			//US#102672					 // pushing document to DMS		
            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
					+ ApplicationSession.getInstance().getMessage("birtName.mutationReceipt")
					+ "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTNO=" + printDto.getReceiptId();
            Utility.pushDocumentToDms(URL, getConnectionNo(), MainetConstants.DEPT_SHORT_NAME.WATER,fileUpload);
		}

	}

	private void setWaterReconnectionData() {
		final UserSession session = UserSession.getCurrent();
		final WaterReconnectionRequestDTO requestDTO = getReconnRequestDTO();
		ApplicantDetailDTO dto = getApplicantDetailDto();
		requestDTO.setServiceId(getServiceId());
		requestDTO.setOrgId(session.getOrganisation().getOrgid());
		requestDTO.setApplicant(dto);
		requestDTO.setDeptId(getDeptId());
		requestDTO.setAmount(getAmountToPay());
		requestDTO.setUserId(session.getEmployee().getEmpId());
		requestDTO.setLangId((long) session.getLanguageId());
		requestDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		setReconnRequestDTO(requestDTO);
	}

	/**
	 * 
	 * @param serviceId
	 * @param orgId
	 */
	@SuppressWarnings("unchecked")
	public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {
		final WSRequestDTO initDTO = new WSRequestDTO();
		initDTO.setModelName("ChecklistModel|WaterRateMaster");
		final WSResponseDTO response = brmsCommonService.initializeModel(initDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			setCheckListNCharges(MainetConstants.FlagY);
			final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			checkListModel2.setOrgId(orgId);
			checkListModel2.setServiceCode(serviceCode);
			populateChecklistModel(checkListModel2, getReconnResponseDTO());
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
			}

			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			waterRateMaster.setServiceCode(serviceCode);
			waterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			taxRequestDto.setDataModel(waterRateMaster);
			WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
				if (!res.isFree()) {
					final List<Object> rates = (List<Object>) res.getResponseObj();
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final WaterRateMaster master1 = (WaterRateMaster) rate;
						populateChargeMaster(master1, getReconnResponseDTO());
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
					setIsFree(MainetConstants.Common_Constant.NO);
					this.setChargeMap(detailDTOs);
					getOfflineDTO().setAmountToShow(getAmountToPay());
					if (getAmountToPay() == 0.0d) {
						throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
								+ " if service configured as Chageable");
					}

				} else {
					setAmountToPay(0.0d);
				}
			} else {
				throw new FrameworkException("Problem while checking dependent param for water rate .");
			}

			// [END]
		}
	}

	private void setChargeMap(final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		setChargesMap(chargesMap);
	}

	private void populateChecklistModel(final CheckListModel checkListModel2,
			final WaterReconnectionResponseDTO reconnResponseDTO2) {
		checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
		checkListModel2.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		/* checkListModel2.setApplicantType(reconnResponseDTO2.getApplicantType()); */
		checkListModel2.setUsageSubtype1(reconnResponseDTO2.getTarrifCategory());
		// checkListModel2.setUsageSubtype2(reconnResponseDTO2.getPremiseType());

	}

	private void populateChargeMaster(final WaterRateMaster master1,
			final WaterReconnectionResponseDTO reconnResponseDTO2) {
		master1.setUsageSubtype1(reconnResponseDTO2.getTarrifCategory());
		master1.setUsageSubtype2("NA");
		master1.setIsBPL(getApplicantDetailDto().getIsBPL());
		master1.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		master1.setMeterType(reconnResponseDTO2.getMeterType());
		if (reconnResponseDTO2.getConnectionSize() != null) {
			master1.setConnectionSize(Double
					.valueOf(CommonMasterUtility.getNonHierarchicalLookUpObject(reconnResponseDTO2.getConnectionSize(),
							UserSession.getCurrent().getOrganisation()).getDescLangFirst()));
		}
		master1.setTaxCode(master1.getTaxCode());
		master1.setRateStartDate(new Date().getTime());
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	@Override
	public String getConnectioNo(final long applicationid, final long serviceid) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		waterReconnection = waterReconnectionService.getReconnectionDetails(applicationid, orgId);
		setPlumberId(waterReconnection.getPlumId());
		setConsumerId(waterReconnection.getCsIdn());
		return getConsumerId() + MainetConstants.operator.COMMA + getPlumberId();
	}

	private List<DocumentDetailsVO> mapCheckList(final List<DocumentDetailsVO> docs,
			final BindingResult bindingResult) {

		if ((docs != null) && !docs.isEmpty()) {
			for (final DocumentDetailsVO doc : docs) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
						bindingResult.addError(new ObjectError(MainetConstants.BLANK,
								ApplicationSession.getInstance().getMessage("water.fileuplaod.validtnMsg")));

						break;
					}
				}
			}
		}

		return docs;

	}

	/**
	 * This method is used for generating byte code for uploaded files
	 * 
	 * @param docs
	 *            is list of uploaded file
	 * @return uploaded file name and byte code
	 */
	public List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

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
		if (!listOfString.isEmpty() && !CollectionUtils.isEmpty(docs)) {
			for (final DocumentDetailsVO d : docs) {
				final long count = d.getDocumentSerialNo() - 1;
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
			}
		}
		return docs;
	}

	/**
	 * @return the documentList
	 */
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList
	 *            the documentList to set
	 */
	public void setDocumentList(final List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the cfcAddressEntity
	 */
	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	/**
	 * @param cfcAddressEntity
	 *            the cfcAddressEntity to set
	 */
	public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	/**
	 * @return the cfcEntity
	 */
	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	/**
	 * @param cfcEntity
	 *            the cfcEntity to set
	 */
	public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	/**
	 * @return the scrutinyApplicable
	 */
	public boolean isScrutinyApplicable() {
		return scrutinyApplicable;
	}

	/**
	 * @param scrutinyApplicable
	 *            the scrutinyApplicable to set
	 */
	public void setScrutinyApplicable(final boolean scrutinyApplicable) {
		this.scrutinyApplicable = scrutinyApplicable;
	}

	/**
	 * @return the applicantDetailDto
	 */
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	/**
	 * @param applicantDetailDto
	 *            the applicantDetailDto to set
	 */
	public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	/**
	 * @return the discType
	 */
	public String getDiscType() {
		return discType;
	}

	/**
	 * @param discType
	 *            the discType to set
	 */
	public void setDiscType(final String discType) {
		this.discType = discType;
	}

	/**
	 * @return the discMethod
	 */
	public String getDiscMethod() {
		return discMethod;
	}

	/**
	 * @param discMethod
	 *            the discMethod to set
	 */
	public void setDiscMethod(final String discMethod) {
		this.discMethod = discMethod;
	}

	/**
	 * @return the tarrifCate
	 */
	public String getTarrifCate() {
		return tarrifCate;
	}

	/**
	 * @param tarrifCate
	 *            the tarrifCate to set
	 */
	public void setTarrifCate(final String tarrifCate) {
		this.tarrifCate = tarrifCate;
	}

	/**
	 * @return the primiseType
	 */
	public String getPrimiseType() {
		return primiseType;
	}

	/**
	 * @param primiseType
	 *            the primiseType to set
	 */
	public void setPrimiseType(final String primiseType) {
		this.primiseType = primiseType;
	}

	/**
	 * @return the connectionNo
	 */
	public String getConnectionNo() {
		return connectionNo;
	}

	/**
	 * @param connectionNo
	 *            the connectionNo to set
	 */
	public void setConnectionNo(final String connectionNo) {
		this.connectionNo = connectionNo;
	}

	/**
	 * @return the consumerName
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * @param consumerName
	 *            the consumerName to set
	 */
	public void setConsumerName(final String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * @return the discDate
	 */
	public String getDiscDate() {
		return discDate;
	}

	/**
	 * @param discDate
	 *            the discDate to set
	 */
	public void setDiscDate(final String discDate) {
		this.discDate = discDate;
	}

	/**
	 * @return the discRemarks
	 */
	public String getDiscRemarks() {
		return discRemarks;
	}

	/**
	 * @param discRemarks
	 *            the discRemarks to set
	 */
	public void setDiscRemarks(final String discRemarks) {
		this.discRemarks = discRemarks;
	}

	/**
	 * @return the roadTypeList
	 */
	public List<RoadTypeDTO> getRoadTypeList() {
		return roadTypeList;
	}

	/**
	 * @param roadTypeList
	 *            the roadTypeList to set
	 */
	public void setRoadTypeList(final List<RoadTypeDTO> roadTypeList) {
		this.roadTypeList = roadTypeList;
	}

	/**
	 * @return the plumberLicNo
	 */
	public String getPlumberLicNo() {
		return plumberLicNo;
	}

	/**
	 * @param plumberLicNo
	 *            the plumberLicNo to set
	 */
	public void setPlumberLicNo(final String plumberLicNo) {
		this.plumberLicNo = plumberLicNo;
	}

	/**
	 * @return the isULBPlumber
	 */
	public String getIsULBPlumber() {
		return isULBPlumber;
	}

	/**
	 * @param isULBPlumber
	 *            the isULBPlumber to set
	 */
	public void setIsULBPlumber(final String isULBPlumber) {
		this.isULBPlumber = isULBPlumber;
	}

	/**
	 * @return the plumber
	 */
	public String getPlumber() {
		return plumber;
	}

	/**
	 * @param plumber
	 *            the plumber to set
	 */
	public void setPlumber(final String plumber) {
		this.plumber = plumber;
	}

	/**
	 * @return the reconnectionMethod
	 */
	public Long getReconnectionMethod() {
		return reconnectionMethod;
	}

	/**
	 * @param reconnectionMethod
	 *            the reconnectionMethod to set
	 */
	public void setReconnectionMethod(final Long reconnectionMethod) {
		this.reconnectionMethod = reconnectionMethod;
	}

	/**
	 * @return the reconnectionRemark
	 */
	public String getReconnectionRemark() {
		return reconnectionRemark;
	}

	/**
	 * @param reconnectionRemark
	 *            the reconnectionRemark to set
	 */
	public void setReconnectionRemark(final String reconnectionRemark) {
		this.reconnectionRemark = reconnectionRemark;
	}

	/**
	 * @return the waterReconnection
	 */
	public TbWaterReconnection getWaterReconnection() {
		return waterReconnection;
	}

	/**
	 * @param waterReconnection
	 *            the waterReconnection to set
	 */
	public void setWaterReconnection(final TbWaterReconnection waterReconnection) {
		this.waterReconnection = waterReconnection;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the serviceId
	 */
	@Override
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            the serviceId to set
	 */
	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the consumerId
	 */
	public Long getConsumerId() {
		return consumerId;
	}

	/**
	 * @param consumerId
	 *            the consumerId to set
	 */
	public void setConsumerId(final Long consumerId) {
		this.consumerId = consumerId;
	}

	/**
	 * @return the plumberId
	 */
	public Long getPlumberId() {
		return plumberId;
	}

	/**
	 * @param plumberId
	 *            the plumberId to set
	 */
	public void setPlumberId(final Long plumberId) {
		this.plumberId = plumberId;
	}

	public WaterReconnectionRequestDTO getReconnRequestDTO() {
		return reconnRequestDTO;
	}

	public void setReconnRequestDTO(WaterReconnectionRequestDTO reconnRequestDTO) {
		this.reconnRequestDTO = reconnRequestDTO;
	}

	public List<PlumberMaster> getPlumberList() {
		return plumberList;
	}

	public void setPlumberList(List<PlumberMaster> plumberList) {
		this.plumberList = plumberList;
	}

	public List<PlumberMaster> getUlbPlumberList() {
		return ulbPlumberList;
	}

	public void setUlbPlumberList(List<PlumberMaster> ulbPlumberList) {
		this.ulbPlumberList = ulbPlumberList;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
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

	public WaterReconnectionResponseDTO getReconnResponseDTO() {
		return reconnResponseDTO;
	}

	public void setReconnResponseDTO(WaterReconnectionResponseDTO reconnResponseDTO) {
		this.reconnResponseDTO = reconnResponseDTO;
	}

	public Long getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(Long connectionSize) {
		this.connectionSize = connectionSize;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public WaterApplicationAcknowledgementDTO getWaterApplicationAcknowledgementDTO() {
		return waterApplicationAcknowledgementDTO;
	}

	public void setWaterApplicationAcknowledgementDTO(
			WaterApplicationAcknowledgementDTO waterApplicationAcknowledgementDTO) {
		this.waterApplicationAcknowledgementDTO = waterApplicationAcknowledgementDTO;
	}

	public String getCsoldCcn() {
		return csoldCcn;
	}

	public void setCsoldCcn(String csoldCcn) {
		this.csoldCcn = csoldCcn;
	}
	

}
