package com.abm.mainet.tradeLicense.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class DuplicateLicenseFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String paymentCheck;
	private String licFromDateDesc;
	private String licToDateDesc;
	private ServiceMaster serviceMaster = new ServiceMaster();
	private String licenseDetails;
	private String checklistCheck;
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private String dateDesc;
	private String issuanceDateDesc;
	private String viewMode;
	private String immediateServiceMode;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String imagePath;
	private BigDecimal rmAmount;
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private String saveMode;
	private Long rmRcptno;
	private String loiDateDesc;
	private String licenseFromDateDesc;
	private Long taskId;
	private String filePath;
	private String categoryDesc;
	private String trdWard1Desc;
	private String trdWard2Desc;
	private String trdWard3Desc;
	private String trdWard4Desc;
	private String trdWard5Desc;
	private String ward1Level;
	private String ward2Level;
	private String ward3Level;
	private String ward4Level;
	private String ward5Level;
	private String ownerName;
	private String serviceName;
	private Long applicationId;
	private String applicantName;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Department department;
	private BigDecimal depositeAmount;
	private BigDecimal licenseFee;
	private String categoryCode;
	private String subCategoryDesc;
	private Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();
	


	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	
	private List<TradeMasterDetailDTO> listTradeMasterDetailDTO = new ArrayList<>();
	
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		//User Story #147721
  		try {
  			if( getOfflineDTO().getPayModeIn()!=null) {
  			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(getOfflineDTO().getPayModeIn());
  			if (lookup != null && lookup.getLookUpCode().equals("POS")) {
  				CommonChallanDTO dto=tradeLicenseApplicationService.createPushToPayApiRequest(getOfflineDTO(), createdBy, orgId,MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE , getOfflineDTO().getAmountToPay());
  				if(dto!=null && dto.getPushToPayErrMsg()!=null) {
  					this.addValidationError(dto.getPushToPayErrMsg());
  					return false;
  				}
  			}}
  		} catch (Exception e) {
  			// TODO: handle exception
  		}
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		if (masDto.getCreatedBy() == null) {
			masDto.setCreatedBy(createdBy);
			masDto.setCreatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				ownDto.setCreatedBy(createdBy);
				ownDto.setCreatedDate(newDate);
				ownDto.setOrgid(orgId);
				ownDto.setLgIpMac(lgIp);
			});

			masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				itemDto.setCreatedBy(createdBy);
				itemDto.setCreatedDate(newDate);
				itemDto.setOrgid(orgId);
				itemDto.setLgIpMac(lgIp);
			});

		} else {
			masDto.setUpdatedBy(createdBy);
			masDto.setUpdatedDate(newDate);
			masDto.setOrgid(orgId);
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
				if (ownDto.getCreatedBy() == null) {
					ownDto.setCreatedBy(createdBy);
					ownDto.setCreatedDate(newDate);
					ownDto.setOrgid(orgId);
					ownDto.setLgIpMac(lgIp);
				} else {
					ownDto.setUpdatedBy(createdBy);
					ownDto.setUpdatedDate(newDate);
					ownDto.setOrgid(orgId);
					ownDto.setLgIpMac(lgIp);
				}
			});

			masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
				if (itemDto.getCreatedBy() == null) {
					itemDto.setCreatedBy(createdBy);
					itemDto.setCreatedDate(newDate);
					itemDto.setOrgid(orgId);
					itemDto.setLgIpMac(lgIp);
				} else {
					itemDto.setUpdatedBy(createdBy);
					itemDto.setUpdatedDate(newDate);
					itemDto.setOrgid(orgId);
					itemDto.setLgIpMac(lgIp);
				}
			});

		}

		masDto.setOrgId(orgId);
		masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		//masDto.setSource(MainetConstants.FlagS);
		masDto = ApplicationContextProvider.getApplicationContext().getBean(IDuplicateLicenseApplicationService.class)
				.saveAndGenerateApplnNo(masDto);
		setTradeMasterDetailDTO(masDto);

		final CommonChallanDTO offline = getOfflineDTO();
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);

		if (getServiceMaster().getSmFeesSchedule().longValue() != 0l) {

			if (getServiceMaster().getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
				setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
			}
		}
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
		requestDTO1.setStatus(MainetConstants.FlagA);
		requestDTO1.setIdfId(masDto.getApmApplicationId().toString());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.MARKET_LICENSE);
		requestDTO1.setApplicationId(masDto.getApmApplicationId());
		requestDTO1.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(getServiceMaster().getSmServiceId());
		requestDTO1.setReferenceId(getTradeMasterDetailDTO().getApmApplicationId().toString());
		// Defect #106834 start
		String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getProcessName(getServiceMaster().getSmServiceId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		if (processName == null || (processName != null && processName.equals(MainetConstants.NOT_APPLICABLE))) {
			this.setSuccessMessage(getAppSession().getMessage("trade.successMessage"));
		} else {
			this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
		}
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			getReceiptDTO().setSubject(getAppSession().getMessage("trade.license.duplicate.receipt.subject",
					new Object[] { getServiceMaster().getSmServiceName(),
							Utility.dateToString(masDto.getTrdLicfromDate(), MainetConstants.DATE_FORMAT_UPLOAD),
							Utility.dateToString(masDto.getTrdLictoDate(), MainetConstants.DATE_FORMAT_UPLOAD),
							masDto.getTrdLicno() }));
		}
		// end
		return true;
		
	}
	

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
						session.getOrganisation().getOrgid());
		setServiceMaster(sm);
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantName(this.getOwnerName());
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
		offline.setDeptId(sm.getTbDepartment().getDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(sm.getSmServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		offline.setFromedate(tradeMaster.getTrdLicfromDate());
		offline.setToDate(tradeMaster.getTrdLictoDate());
		offline.setServiceName(sm.getSmServiceName());
		offline.setPosPayApplicable(true);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}

		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, session.getOrganisation());

		/*
		 * final List<TbTaxMas> taxesMaster =
		 * applicationContext.getBean(TbTaxMasService.class)
		 * .findAllTaxesForBillGeneration(session.getOrganisation().getOrgid(),
		 * sm.getTbDepartment().getDpDeptid(), chargeApplicableAt.getLookUpId(), null);
		 */

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(sm.getSmServiceId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), chargeApplicableAt.getLookUpId());

		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
			offline.getFeeIds().put(tbTaxMas.getTaxId(), tradeMaster.getTotalApplicationFee().doubleValue());
		}
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final ChallanMaster master = ApplicationContextProvider.getApplicationContext()
					.getBean(IChallanService.class).InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());			
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = ApplicationContextProvider.getApplicationContext()
					.getBean(IChallanService.class).savePayAtUlbCounter(offline, sm.getSmServiceName());
			setReceiptDTO(printDto);
		}
		setOfflineDTO(offline);
	}

	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms() {

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);

			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
					long cnt = 1;
					for (final DocumentDetailsVO doc : checkListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					if ((checkListList != null) && !checkListList.isEmpty()) {
						setCheckList(checkListList);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {

		if (getServiceMaster().getSmFeesSchedule().longValue() != 0l) {
			if (getServiceMaster().getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
				validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
			}
		}
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}
	/* end of validation method */

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}
	

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
	}

	public void setLicToDateDesc(String licToDateDesc) {
		this.licToDateDesc = licToDateDesc;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getImmediateServiceMode() {
		return immediateServiceMode;
	}

	public void setImmediateServiceMode(String immediateServiceMode) {
		this.immediateServiceMode = immediateServiceMode;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public List<TbLoiMas> getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(List<TbLoiMas> tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getTrdWard1Desc() {
		return trdWard1Desc;
	}

	public void setTrdWard1Desc(String trdWard1Desc) {
		this.trdWard1Desc = trdWard1Desc;
	}

	public String getTrdWard2Desc() {
		return trdWard2Desc;
	}

	public void setTrdWard2Desc(String trdWard2Desc) {
		this.trdWard2Desc = trdWard2Desc;
	}

	public String getTrdWard3Desc() {
		return trdWard3Desc;
	}

	public void setTrdWard3Desc(String trdWard3Desc) {
		this.trdWard3Desc = trdWard3Desc;
	}

	public String getTrdWard4Desc() {
		return trdWard4Desc;
	}

	public void setTrdWard4Desc(String trdWard4Desc) {
		this.trdWard4Desc = trdWard4Desc;
	}

	public String getTrdWard5Desc() {
		return trdWard5Desc;
	}

	public void setTrdWard5Desc(String trdWard5Desc) {
		this.trdWard5Desc = trdWard5Desc;
	}

	public String getWard1Level() {
		return ward1Level;
	}

	public void setWard1Level(String ward1Level) {
		this.ward1Level = ward1Level;
	}

	public String getWard2Level() {
		return ward2Level;
	}

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public String getWard3Level() {
		return ward3Level;
	}

	public void setWard3Level(String ward3Level) {
		this.ward3Level = ward3Level;
	}

	public String getWard4Level() {
		return ward4Level;
	}

	public void setWard4Level(String ward4Level) {
		this.ward4Level = ward4Level;
	}

	public String getWard5Level() {
		return ward5Level;
	}

	public void setWard5Level(String ward5Level) {
		this.ward5Level = ward5Level;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}

	public List<TradeMasterDetailDTO> getListTradeMasterDetailDTO() {
		return listTradeMasterDetailDTO;
	}

	public void setListTradeMasterDetailDTO(List<TradeMasterDetailDTO> listTradeMasterDetailDTO) {
		this.listTradeMasterDetailDTO = listTradeMasterDetailDTO;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public BigDecimal getDepositeAmount() {
		return depositeAmount;
	}

	public void setDepositeAmount(BigDecimal depositeAmount) {
		this.depositeAmount = depositeAmount;
	}

	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}

	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}

	public Map<String, Double> getChargeDescAndAmount() {
		return chargeDescAndAmount;
	}

	public void setChargeDescAndAmount(Map<String, Double> chargeDescAndAmount) {
		this.chargeDescAndAmount = chargeDescAndAmount;
	}

	public BRMSCommonService getBrmsCommonService() {
		return brmsCommonService;
	}

	public void setBrmsCommonService(BRMSCommonService brmsCommonService) {
		this.brmsCommonService = brmsCommonService;
	}

	public ITradeLicenseApplicationService getTradeLicenseApplicationService() {
		return tradeLicenseApplicationService;
	}

	public void setTradeLicenseApplicationService(ITradeLicenseApplicationService tradeLicenseApplicationService) {
		this.tradeLicenseApplicationService = tradeLicenseApplicationService;
	}


}
