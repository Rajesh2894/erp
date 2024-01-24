package com.abm.mainet.buildingplan.ui.model;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.model.Khasra;
import org.model.Must;
import org.model.Tehsil;
import org.model.Village;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.buildingplan.dto.DeveloperRegistrationDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationFeeMasDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandSurroundingsDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.buildingplan.service.IDeveloperRegistrationService;
import com.abm.mainet.buildingplan.service.INewLicenseFormService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.dto.SMSRequestDto;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class NewTCPLicenseFormModel extends AbstractFormModel {

	private static final long serialVersionUID = -3779370072422607616L;

    private static Logger log = Logger.getLogger(NewTCPLicenseFormModel.class);
	
	private DeveloperRegistrationDTO developerRegistrationDTO = new DeveloperRegistrationDTO();
	private LicenseApplicationMasterDTO licenseApplicationMasterDTO = new LicenseApplicationMasterDTO();
	private List<LicenseApplicationMasterDTO> applicationMasDTOList = new ArrayList<>();
	private String khrsDevCollab;
	private String isIrrevocable;
	private String consolidation;
	private String nonConsolidation;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	List<LookUp> purposeList = new ArrayList<>();
	List<LookUp> suPurposeList = new ArrayList<>();
	List<LookUp> suPurpose2List = new ArrayList<>();
	private List<DocumentDetailsVO> applicantCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> landScheduleCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> detailsOfLandCheckList = new ArrayList<>();
	private String saveMode;
	private List<DocumentDetailsVO> applicantPurposeCheckList = new ArrayList<>();	
	private List<DocumentDetailsVO> encumbranceCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> courtOrdersLandCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> insolvencyLandCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> shajraAppLandCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> releaseOrderCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> usageAllotteesCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> accessNHSRCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> existingApproachCheckList = new ArrayList<>();
	private List<DocumentDetailsVO> DGPSCheckList = new ArrayList<>();
	private Long serviceId;
	List<Tehsil> tehsilList = new ArrayList<>();
	List<Village> villageList = new ArrayList<>();
	Must must = new Must();
	List<Khasra> khasraList = new ArrayList<>();
	private List<CFCAttachment> applicantDocumentList = new ArrayList<>();
	private List<CFCAttachment> landScheduleDocumentList = new ArrayList<>();
	private List<CFCAttachment> appLandDocumentList = new ArrayList<>();
	private List<CFCAttachment> appPurposeDocumentList = new ArrayList<>();
	private List<CFCAttachment> encumbranceDocumentList = new ArrayList<>();
	private List<CFCAttachment> courtOrdersLandDocumentList = new ArrayList<>();
	private List<CFCAttachment> insolvencyLandDocumentList = new ArrayList<>();
	private List<CFCAttachment> shajraAppLandDocumentList = new ArrayList<>();
	private List<CFCAttachment> releaseOrderDocumentList = new ArrayList<>();
	private List<CFCAttachment> usageAllotteesDocumentList = new ArrayList<>();
	private List<CFCAttachment> accessNHSRDocumentList = new ArrayList<>();
	private List<CFCAttachment> existingApproachDocumentList = new ArrayList<>();
	private List<CFCAttachment> DGPSDocumentList = new ArrayList<>();
	private String dgpsdocPath;
	private List<LicenseApplicationFeeMasDTO> feeListDto = new ArrayList<>();
	private List<LicenseApplicationLandSurroundingsDTO> licenseApplicationLandSurroundingsDtoList = new ArrayList<LicenseApplicationLandSurroundingsDTO>();
	private LicenseApplicationMasterDTO licenseApplicationMaster = new LicenseApplicationMasterDTO();
	
	private String viewFlag;

	@Autowired
	INewLicenseFormService newLicenseFormService;
	
	@Resource
	private IFileUploadService fileUpload;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	IDeveloperRegistrationService developerRegistrationService;
	
	@Resource
	private IEmployeeService employeeService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	
	@Override
	public boolean saveForm() {
		
		try {			
			licenseApplicationMasterDTO.setKhrsDist(licenseApplicationMasterDTO.getDdz1());
			licenseApplicationMasterDTO.setKhrsDevPlan(licenseApplicationMasterDTO.getDdz2());
			licenseApplicationMasterDTO.setKhrsZone(licenseApplicationMasterDTO.getDdz3());
			licenseApplicationMasterDTO.setKhrsSec(licenseApplicationMasterDTO.getDdz4());
		    
			licenseApplicationMasterDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			licenseApplicationMasterDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
			licenseApplicationMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			licenseApplicationMasterDTO.setCreatedDate(new Date());
			licenseApplicationMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			if(!licenseApplicationMasterDTO.getFeeMasterDto().isEmpty())
			  generateDiaryAndCaseNoAndAppNo(licenseApplicationMasterDTO);
			licenseApplicationMasterDTO = newLicenseFormService.saveRegForm(licenseApplicationMasterDTO);
			
			if(!licenseApplicationMasterDTO.getFeeMasterDto().isEmpty()) {
				 triggerNewLicenceSubmissionAlert();
				 setSuccessMessage("License Application Form Saved Successfully :" + licenseApplicationMasterDTO.getApplicationNo());
			}
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/** SMS And Email Alert For Issuance of New License Submission*/
	public void triggerNewLicenceSubmissionAlert() {
		log.info("Starting SMS And Email Alert For Issuance of New License Submission");
		Employee loggedInUser = UserSession.getCurrent().getEmployee();

		final SMSAndEmailDTO smsEmailDto = new SMSAndEmailDTO();
		if(null != loggedInUser.getEmpmobno())
			smsEmailDto.setMobnumber(loggedInUser.getEmpmobno());
		if(null != loggedInUser.getEmpemail())
			smsEmailDto.setEmail(loggedInUser.getEmpemail());
		if(null != licenseApplicationMasterDTO.getApplicationNo())
			smsEmailDto.setAppNo(licenseApplicationMasterDTO.getApplicationNo().toString());
		smsEmailDto.setUserId(loggedInUser.getEmpId());
		smsEmailDto.setServName(MainetConstants.TradeLicense.SERVICE_CODE);
		try {
			boolean smsEmailStatus = iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE,
					"TradeApplicationForm.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsEmailDto,
					UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
			if(smsEmailStatus)
				log.info("SMS and Email For Issuance of New License For Application No "
						+ licenseApplicationMasterDTO.getApplicationNo() + " Sent SuccessFully To "
						+ loggedInUser.getEmpmobno() + " And " + loggedInUser.getEmpemail());
			else
				log.error("Problem During Sending SMS/Email...");
		} catch (Exception e) {
			log.error("Exception occur while sending SMS and Email For Issuance of New License For Application No :"
					+ " " + licenseApplicationMasterDTO.getApplicationNo(), e);
		}
		log.info("SMS And Email Alert For Issuance of New License Submission End...");
	}
	
	
	private String genearteToken() throws RestClientException, URISyntaxException {
		SMSRequestDto smsTokenreq = new SMSRequestDto();
		smsTokenreq.setUserId(ApplicationSession.getInstance().getMessage("tcp_sms_userId"));
		smsTokenreq.setTpUserId(ApplicationSession.getInstance().getMessage("tcp_sms_tpUserId"));
		smsTokenreq.setEmailId(ApplicationSession.getInstance().getMessage("tcp_sms_emailId"));
		RestTemplate restToken = new RestTemplate();
		HttpHeaders headersToken = new HttpHeaders();
		headersToken.add("access_key", ApplicationSession.getInstance().getMessage("tcp_sms_access_key"));
		headersToken.add("secret_key", ApplicationSession.getInstance().getMessage("tcp_sms_secret_key"));
		headersToken.add("Content-Type", "application/json");
		HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(smsTokenreq, headersToken);
		ResponseEntity<String> respToken = restToken.exchange(new URI(ApplicationSession.getInstance().getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
				httpentity1, String.class);
		String token = null;
		if (respToken != null && respToken.getStatusCode() == HttpStatus.OK) {
			JSONObject jsonObject = new JSONObject(respToken.getBody());
			java.util.Map<String, String> myMap = new java.util.HashMap<>();
			myMap.put(jsonObject.getString("Key"), jsonObject.getString("Value"));
			token = (String) myMap.get("1");
		}
		return token;
	}
	
	private void generateDiaryAndCaseNoAndAppNo(LicenseApplicationMasterDTO licenseApplicationMasterDTO) {
		String token=StringUtils.EMPTY; 
		try {
			token =genearteToken();
		
		RestTemplate restToken = new RestTemplate();
		HttpHeaders headersToken = new HttpHeaders();
		headersToken.add("Content-Type", "application/json");
		Map<String, Object> mapDNo = new HashMap<String, Object>();
		setDiaryDetails(mapDNo,licenseApplicationMasterDTO);
		//HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(mapDNo, headersToken);
		/*ResponseEntity<String> respToken = restToken.exchange(new URI(ApplicationSession.getInstance().getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
				httpentity1, String.class);*/
		mapDNo.put("TokenId", token);
		ResponseEntity<?> response = RestClient.callRestTemplateClient(mapDNo, ApplicationSession.getInstance().getMessage("api.getdiary.number"), HttpMethod.POST, Map.class);
		String diaryNo = null;
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			Map<String, String> mapdiary = (Map<String, String>) response.getBody();
			if(!"1".equals(mapdiary.get("Key"))) {
				log.error("APi is failing due to "+ mapdiary.get("Value"));
			}else {
				diaryNo = (String) mapdiary.get("Value");
				licenseApplicationMasterDTO.setDiaryNo(diaryNo);
			}
			
		}
		
		Map<String, Object> caseMap = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(diaryNo)) {
			generateCaseNo(licenseApplicationMasterDTO,mapDNo,caseMap);
			if(!StringUtils.isEmpty(licenseApplicationMasterDTO.getCaseNno())) {
				generateApplicationNo(licenseApplicationMasterDTO,mapDNo,caseMap);
			}
		}
		
		} catch (Exception e) {
			log.error("Exception while calling Diary Case Application no API");
			e.printStackTrace();
		} 
		
	}
	
	private void generateApplicationNo(LicenseApplicationMasterDTO licenseApplicationMasterDTO2,
			Map<String, Object> mapDNo, Map<String, Object> caseMap) {
		String token=StringUtils.EMPTY; 
		try {
			token =genearteToken();
		
		RestTemplate restToken = new RestTemplate();
		HttpHeaders headersToken = new HttpHeaders();
		headersToken.add("Content-Type", "application/json");
		Map<String, Object> mapAppNo = new HashMap<String, Object>();
		setApplicationDetails(licenseApplicationMasterDTO,mapDNo,mapAppNo);
		//HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(mapDNo, headersToken);
		/*ResponseEntity<String> respToken = restToken.exchange(new URI(ApplicationSession.getInstance().getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
				httpentity1, String.class);*/
		mapAppNo.put("TokenId", token);
		ResponseEntity<?> response = RestClient.callRestTemplateClient(mapAppNo, ApplicationSession.getInstance().getMessage("api.getApp.number"), HttpMethod.POST, Map.class);
		String appNo = null;
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			Map<String, String> mapApp = (Map<String, String>) response.getBody();
			if(!"1".equals(mapApp.get("Key"))) {
				log.error("APi is failing due to "+ mapApp.get("Value"));
			}else {
				appNo = (String) mapApp.get("Value");
				licenseApplicationMasterDTO.setApplicationNoEService(appNo);
			}
			
		}
		
		} catch (Exception e) {
			log.error("Exception while calling Diary Case Application no API");
			e.printStackTrace();
		} 
		
	}

	private void setApplicationDetails(LicenseApplicationMasterDTO licenseApplicationMasterDTO2, Map<String, Object> mapDNo,
			 Map<String, Object> mapAppNo) {
		
		mapAppNo.put("DiaryNo", licenseApplicationMasterDTO2.getDiaryNo());
		mapAppNo.put("CaseId", licenseApplicationMasterDTO2.getCaseNno().split("~")[1]);
		mapAppNo.put("DiaryDate", mapDNo.get("DiaryDate"));
		mapAppNo.put("TotalArea", licenseApplicationMasterDTO2.getCiTotArea());
		mapAppNo.put("Village",mapDNo.get("Village"));
		mapAppNo.put("PurposeId", licenseApplicationMasterDTO2.getAppPLicPurposeId());
		mapAppNo.put("NameofOwner",UserSession.getCurrent().getEmployee().getEmpname());
		mapAppNo.put("DateOfHearing", mapDNo.get("DiaryDate"));
		mapAppNo.put("DateForFilingOfReply",  mapDNo.get("DiaryDate"));
		mapAppNo.put("ApplicationTypeId", ApplicationSession.getInstance().getMessage("tcp.applicationtypeid"));
		// request.put("ApplicationId", applicationId);
		// request.put("ApplicationNo", applicationNumber);
		mapAppNo.put("PlotNo", ApplicationSession.getInstance().getMessage("tcp.plotno"));
		mapAppNo.put("RelatedApplicationId", ApplicationSession.getInstance().getMessage("tcp.relatedapplicationid"));
		mapAppNo.put("IsBpocForResiPlotted", ApplicationSession.getInstance().getMessage("tcp.IsBpocForResiPlotted"));
		// request.put("DetailsOfApplication", detailsOfApplication);
		mapAppNo.put("PlotId", ApplicationSession.getInstance().getMessage("tcp.plotid"));
		mapAppNo.put("CreatedByRoleId",  ApplicationSession.getInstance().getMessage("tcp.createdbyroleid"));
		mapAppNo.put("IsConfirmed",  ApplicationSession.getInstance().getMessage("tcp.isconfirmed"));
		mapAppNo.put("UserId", ApplicationSession.getInstance().getMessage("tcp.UserId"));
		mapAppNo.put("UserLoginId", ApplicationSession.getInstance().getMessage("tcp.UserLoginId"));
		
	}

	private void setDiaryDetails(Map<String, Object> mapDNo, LicenseApplicationMasterDTO licenseApplicationMasterDTO2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		LocalDateTime localDateTime = LocalDateTime.now();
		String date = formatter.format(localDateTime);
		mapDNo.put("Village",licenseApplicationMasterDTO2.getKhrsRevEst());
		mapDNo.put("DiaryDate", date);
		mapDNo.put("ReceivedFrom", UserSession.getCurrent().getEmployee().getEmpname());
		mapDNo.put("UserId", "1265");
		if(null != licenseApplicationMasterDTO2.getKhrsDist()) {
		   LookUp distLookup = CommonMasterUtility.getHierarchicalLookUp(licenseApplicationMasterDTO2.getKhrsDist(),
				UserSession.getCurrent().getOrganisation());
		   mapDNo.put("DistrictCode", distLookup.getOtherField());
		}else {
			 mapDNo.put("DistrictCode", "0");
		}
		  //0618
		mapDNo.put("UserLoginId", "39");
		mapDNo.put("ApplicationDocId", ApplicationSession.getInstance().getMessage("tcp.applicationdocid"));
		mapDNo.put("ApplicationId", ApplicationSession.getInstance().getMessage("tcp.applicationid"));
		mapDNo.put("Flag", ApplicationSession.getInstance().getMessage("tcp.flag"));
		mapDNo.put("UserId", ApplicationSession.getInstance().getMessage("tcp.UserId"));
		mapDNo.put("UserLoginId", ApplicationSession.getInstance().getMessage("tcp.UserLoginId"));
    }

	private void generateCaseNo(LicenseApplicationMasterDTO licenseApplicationMasterDTO,Map<String, Object> diaryMap,
			Map<String, Object> caseMap) {
		String token=StringUtils.EMPTY; 
		try {
			token =genearteToken();
		
		RestTemplate restToken = new RestTemplate();
		HttpHeaders headersToken = new HttpHeaders();
		headersToken.add("Content-Type", "application/json");
		
		setCaseDetails(caseMap,licenseApplicationMasterDTO,diaryMap);
		//HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(mapDNo, headersToken);
		/*ResponseEntity<String> respToken = restToken.exchange(new URI(ApplicationSession.getInstance().getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
				httpentity1, String.class);*/
		caseMap.put("TokenId", token);
		ResponseEntity<?> response = RestClient.callRestTemplateClient(caseMap, ApplicationSession.getInstance().getMessage("api.getCase.number"), HttpMethod.POST, Map.class);
		String caseNo = null;
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			Map<String, String> mapCase = (Map<String, String>) response.getBody();
			if(!"1".equals(mapCase.get("Key"))) {
				log.error("APi is failing due to "+ mapCase.get("Value"));
			}else {
				caseNo = (String) mapCase.get("Value");
				licenseApplicationMasterDTO.setCaseNno(caseNo);
			}
			
		}
		
		} catch (Exception e) {
			log.error("Exception while calling Diary Case Application no API");
			e.printStackTrace();
		} 
		
	}
	
	private void setCaseDetails(Map<String, Object> caseMap, LicenseApplicationMasterDTO licenseApplicationMasterDTO2,
			Map<String, Object> diaryMap) {
		
		caseMap.put("DiaryNo", licenseApplicationMasterDTO2.getDiaryNo());
		caseMap.put("DiaryDate", diaryMap.get("DiaryDate"));
		caseMap.put("DeveloperId", ApplicationSession.getInstance().getMessage("tcp.developerId"));
		caseMap.put("PurposeId", licenseApplicationMasterDTO2.getAppPLicPurposeId()); 
		caseMap.put("StartDate", diaryMap.get("DiaryDate"));
		caseMap.put("DistrictCode", diaryMap.get("DistrictCode"));
		caseMap.put("Village",diaryMap.get("Village"));
		caseMap.put("ChallanAmount",licenseApplicationMasterDTO2.getTotalFees()); 
		caseMap.put("CaseId", ApplicationSession.getInstance().getMessage("tcp.caseid"));
		caseMap.put("CaseTypeId", ApplicationSession.getInstance().getMessage("tcp.casetypeid"));
		caseMap.put("CaseNo", ApplicationSession.getInstance().getMessage("tcp.casenumber"));
		caseMap.put("UserId", ApplicationSession.getInstance().getMessage("tcp.UserId"));
		caseMap.put("UserLoginId", ApplicationSession.getInstance().getMessage("tcp.UserLoginId"));
		
	}

	
	
	public DeveloperRegistrationDTO getDeveloperRegistrationDTO() {
		return developerRegistrationDTO;
	}

	public void setDeveloperRegistrationDTO(DeveloperRegistrationDTO developerRegistrationDTO) {
		this.developerRegistrationDTO = developerRegistrationDTO;
	}

	public LicenseApplicationMasterDTO getLicenseApplicationMasterDTO() {
		return licenseApplicationMasterDTO;
	}

	public void setLicenseApplicationMasterDTO(LicenseApplicationMasterDTO licenseApplicationMasterDTO) {
		this.licenseApplicationMasterDTO = licenseApplicationMasterDTO;
	}

	public String getKhrsDevCollab() {
		return khrsDevCollab;
	}
	public void setKhrsDevCollab(String khrsDevCollab) {
		this.khrsDevCollab = khrsDevCollab;
	}
	public String getIsIrrevocable() {
		return isIrrevocable;
	}
	public void setIsIrrevocable(String isIrrevocable) {
		this.isIrrevocable = isIrrevocable;
	}
	public String getConsolidation() {
		return consolidation;
	}
	public void setConsolidation(String consolidation) {
		this.consolidation = consolidation;
	}
	public String getNonConsolidation() {
		return nonConsolidation;
	}
	public void setNonConsolidation(String nonConsolidation) {
		this.nonConsolidation = nonConsolidation;
	}
	public INewLicenseFormService getNewLicenseFormService() {
		return newLicenseFormService;
	}
	public void setNewLicenseFormService(INewLicenseFormService newLicenseFormService) {
		this.newLicenseFormService = newLicenseFormService;
	}
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	public List<LookUp> getPurposeList() {
		return purposeList;
	}
	public void setPurposeList(List<LookUp> purposeList) {
		this.purposeList = purposeList;
	}
	public List<LookUp> getSuPurposeList() {
		return suPurposeList;
	}
	public void setSuPurposeList(List<LookUp> suPurposeList) {
		this.suPurposeList = suPurposeList;
	}
	public List<LookUp> getSuPurpose2List() {
		return suPurpose2List;
	}
	public void setSuPurpose2List(List<LookUp> suPurpose2List) {
		this.suPurpose2List = suPurpose2List;
	}
	public List<DocumentDetailsVO> getApplicantCheckList() {
		return applicantCheckList;
	}
	public void setApplicantCheckList(List<DocumentDetailsVO> applicantCheckList) {
		this.applicantCheckList = applicantCheckList;
	}
	public List<DocumentDetailsVO> getLandScheduleCheckList() {
		return landScheduleCheckList;
	}
	public void setLandScheduleCheckList(List<DocumentDetailsVO> landScheduleCheckList) {
		this.landScheduleCheckList = landScheduleCheckList;
	}
	public List<DocumentDetailsVO> getDetailsOfLandCheckList() {
		return detailsOfLandCheckList;
	}
	public void setDetailsOfLandCheckList(List<DocumentDetailsVO> detailsOfLandCheckList) {
		this.detailsOfLandCheckList = detailsOfLandCheckList;
	}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public List<LicenseApplicationMasterDTO> getApplicationMasDTOList() {
		return applicationMasDTOList;
	}
	public void setApplicationMasDTOList(List<LicenseApplicationMasterDTO> applicationMasDTOList) {
		this.applicationMasDTOList = applicationMasDTOList;
	}
	public List<Tehsil> getTehsilList() {
		return tehsilList;
	}
	public void setTehsilList(List<Tehsil> tehsilList) {
		this.tehsilList = tehsilList;
	}
	public List<Village> getVillageList() {
		return villageList;
	}
	public void setVillageList(List<Village> villageList) {
		this.villageList = villageList;
	}
	public Must getMust() {
		return must;
	}
	public void setMust(Must must) {
		this.must = must;
	}
	public List<Khasra> getKhasraList() {
		return khasraList;
	}
	public void setKhasraList(List<Khasra> khasraList) {
		this.khasraList = khasraList;
	}
	@Override
	public void redirectToPayDetails(HttpServletRequest httpServletRequest, PaymentRequestDTO dto) {
		LicenseApplicationMasterDTO licdto=this.getLicenseApplicationMasterDTO();
		DeveloperRegistrationDTO dDto=this.getDeveloperRegistrationDTO();
		Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE, orgId);
		if(sm!=null) {
			dto.setServiceId(sm.getSmServiceId());
			dto.setServiceName(sm.getSmServiceName());	
		}
		dto.setPinCode("123456");
		if(CollectionUtils.isNotEmpty(dDto.getDeveloperAuthorizedUserDTOList())&&dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthMobileNo()!=null)
		dto.setMobNo(dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthMobileNo().toString());
		else
			dto.setMobNo("0123456789");
		dto.setAddress(dDto.getRegisteredAddress());
		dto.setCityName(dDto.getRegisteredAddress());
		if (ApplicationSession.getInstance() != null
				&& (ApplicationSession.getInstance().getMessage("transaction.amount.nl") == null
						|| ApplicationSession.getInstance().getMessage("transaction.amount.nl").equals("Y"))) {
				
			dto.setDueAmt(new BigDecimal(1));
			try {
				dto.setFinalAmount(EncryptionAndDecryption.encrypt("1"));
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			dto.setDueAmt((licdto.getTotalFees()));
			try {
				dto.setFinalAmount(EncryptionAndDecryption.encrypt(licdto.getTotalFees().toString()));
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}
		}
		if(CollectionUtils.isNotEmpty(dDto.getDeveloperAuthorizedUserDTOList())&&dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthEmail()!=null)
			dto.setEmail(dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthEmail());
			else
				dto.setEmail("Test@gmail.com");	
		dto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLangId(UserSession.getCurrent().getLanguageId());
		dto.setUdf2(licdto.getApplicationNo().toString());
		dto.setOrgId(orgId);
		if(CollectionUtils.isNotEmpty(dDto.getDeveloperAuthorizedUserDTOList())&&dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthUserName()!=null)
		dto.setApplicantName(dDto.getDeveloperAuthorizedUserDTOList().get(0).getAuthUserName());
		else
			dto.setApplicantName("Test");	
		dto.setChallanServiceType(MainetConstants.FlagN);
		dto.setFeeIds(licdto.getFeeIds().toString());
		dto.setControlUrl(httpServletRequest.getRequestURI());

		dto.setSuccessUrl(httpServletRequest.getRequestURI() + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_NIC);
      
		dto.setCancelUrl(httpServletRequest.getRequestURI() + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_NIC);
		dto.setFailUrl(httpServletRequest.getRequestURI() + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_NIC);
      
		this.setActionUrl("NewTCPLicenseForm.html");
	}
	
	
	@Override
	public void populateApplicationData(long applicationId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.setLicenseApplicationMasterDTO(newLicenseFormService.findByApplicationNoAndOrgId(applicationId, orgId));
		Long empId = this.getLicenseApplicationMasterDTO().getCreatedBy();
		EmployeeBean employee = employeeService.findById(empId);
		this.setDeveloperRegistrationDTO(developerRegistrationService.getDeveloperRegistrationDtoById(employee.getReportingManager(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		List<LookUp> purposeList = null;
		List<LookUp> subPurposeList = null;
    	try {
    		purposeList = CommonMasterUtility.getLevelData("PUR", 1,
    				UserSession.getCurrent().getOrganisation());
    		subPurposeList = getSubPurposeList(this.getLicenseApplicationMasterDTO().getAppPLicPurposeId());
        
    	}catch(Exception Ex) {
    		
    	}
    	setFeetDetailsWithFormat(this.getLicenseApplicationMasterDTO());
    	this.setPurposeList(purposeList);
    	this.setSuPurposeList(subPurposeList);
    	setApplicantDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.APPLICANT_INFORMATION + MainetConstants.WINDOWS_SLASH + applicationId));
    	setLandScheduleDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.LAND_SCHEDULE + MainetConstants.WINDOWS_SLASH + applicationId));
    	setAppLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DETAILS_OF_LAND + MainetConstants.WINDOWS_SLASH + applicationId));
    	setAppPurposeDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.KHASRA_DEVELOPED_COLLABORATION + MainetConstants.WINDOWS_SLASH + applicationId));
    	setEncumbranceDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ENCUMBRANCE_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationId));
    	setCourtOrdersLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.COURT_ORDER_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationId));
    	setInsolvencyLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.INSOLVENCY_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationId));
    	setShajraAppLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.SHAJRA_APP_LAND_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationId));
    	setReleaseOrderDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.RELEASE_ORDER_DOC + MainetConstants.WINDOWS_SLASH + applicationId));
    	setUsageAllotteesDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.USAGE_ALLOTEE_DOC + MainetConstants.WINDOWS_SLASH + applicationId));
    	setAccessNHSRDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ACCESS_NHSR_DOC + MainetConstants.WINDOWS_SLASH + applicationId));
    	setExistingApproachDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.EXISTING_APPROACH_DOC + MainetConstants.WINDOWS_SLASH + applicationId));
    	setDGPSDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationId, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DGPS_DOC + MainetConstants.WINDOWS_SLASH + applicationId));
	}
	
	private void setFeetDetailsWithFormat(LicenseApplicationMasterDTO licenseApplicationMasterDTO2) {
		if(licenseApplicationMasterDTO2!=null&&CollectionUtils.isNotEmpty(licenseApplicationMasterDTO2.getFeeMasterDto())) {
			licenseApplicationMasterDTO2.getFeeMasterDto().forEach(fee->{
				if(fee.getFees()!=null)
				fee.setFeesStr(CommonMasterUtility.getAmountInIndianCurrency(fee.getFees().setScale(2, BigDecimal.ROUND_UP)));
			});
		}
		if(licenseApplicationMasterDTO2.getTotalFees()!=null)
			licenseApplicationMasterDTO2.setTotalFeesStr(CommonMasterUtility.getAmountInIndianCurrency(licenseApplicationMasterDTO2.getTotalFees()));
		if(licenseApplicationMasterDTO2.getLicenseFee()!=null)
			licenseApplicationMasterDTO2.setLicenseFeeStr(CommonMasterUtility.getAmountInIndianCurrency(licenseApplicationMasterDTO2.getLicenseFee()));
		if(licenseApplicationMasterDTO2.getScrutinyFee()!=null)
			licenseApplicationMasterDTO2.setScrutinyFeeStr(CommonMasterUtility.getAmountInIndianCurrency(licenseApplicationMasterDTO2.getScrutinyFee()));
	
	}


	private List<LookUp> getSubPurposeList(Long appPLicPurposeId) {
		List<LookUp> lookUpList1 = new ArrayList<>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("PUR", 2,
					UserSession.getCurrent().getOrganisation());

			lookUpList1 = lookUpList.stream().filter(lookUp -> appPLicPurposeId.equals(lookUp.getLookUpParentId()))
					.collect(Collectors.toList());

			return lookUpList1;
		} catch (Exception e) {
			return lookUpList1;
		}
	}
	
	public boolean validateDocuments(List<DocumentDetailsVO> docList) {
		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO doc : docList) {
				if(doc.getCheckkMANDATORY()!=null){
					if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("doc.upload.validn ")+doc.getDoc_DESC_ENGL());
					}
				}					
			}
		}
			
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
	
	public boolean saveFeeCharges() {
		newLicenseFormService.saveFeeCharges(feeListDto,this.getTaskId());
		setSuccessMessage("Records has been saved successfully");
		return true;
	}
	
	public boolean saveSurroundingDetail() {
		newLicenseFormService.saveSurroundingDetail(licenseApplicationLandSurroundingsDtoList,this.getTaskId());
		setSuccessMessage("Records has been saved successfully");
		return true;
	}
	
	public List<DocumentDetailsVO> getApplicantPurposeCheckList() {
		return applicantPurposeCheckList;
	}
	public void setApplicantPurposeCheckList(List<DocumentDetailsVO> applicantPurposeCheckList) {
		this.applicantPurposeCheckList = applicantPurposeCheckList;
	}
	public List<DocumentDetailsVO> getEncumbranceCheckList() {
		return encumbranceCheckList;
	}
	public void setEncumbranceCheckList(List<DocumentDetailsVO> encumbranceCheckList) {
		this.encumbranceCheckList = encumbranceCheckList;
	}
	public List<DocumentDetailsVO> getCourtOrdersLandCheckList() {
		return courtOrdersLandCheckList;
	}
	public void setCourtOrdersLandCheckList(List<DocumentDetailsVO> courtOrdersLandCheckList) {
		this.courtOrdersLandCheckList = courtOrdersLandCheckList;
	}
	public List<DocumentDetailsVO> getInsolvencyLandCheckList() {
		return insolvencyLandCheckList;
	}
	public void setInsolvencyLandCheckList(List<DocumentDetailsVO> insolvencyLandCheckList) {
		this.insolvencyLandCheckList = insolvencyLandCheckList;
	}
	public List<DocumentDetailsVO> getShajraAppLandCheckList() {
		return shajraAppLandCheckList;
	}
	public void setShajraAppLandCheckList(List<DocumentDetailsVO> shajraAppLandCheckList) {
		this.shajraAppLandCheckList = shajraAppLandCheckList;
	}
	public List<DocumentDetailsVO> getReleaseOrderCheckList() {
		return releaseOrderCheckList;
	}
	public void setReleaseOrderCheckList(List<DocumentDetailsVO> releaseOrderCheckList) {
		this.releaseOrderCheckList = releaseOrderCheckList;
	}
	public List<DocumentDetailsVO> getUsageAllotteesCheckList() {
		return usageAllotteesCheckList;
	}
	public void setUsageAllotteesCheckList(List<DocumentDetailsVO> usageAllotteesCheckList) {
		this.usageAllotteesCheckList = usageAllotteesCheckList;
	}
	public List<DocumentDetailsVO> getAccessNHSRCheckList() {
		return accessNHSRCheckList;
	}
	public void setAccessNHSRCheckList(List<DocumentDetailsVO> accessNHSRCheckList) {
		this.accessNHSRCheckList = accessNHSRCheckList;
	}
	public List<DocumentDetailsVO> getExistingApproachCheckList() {
		return existingApproachCheckList;
	}
	public void setExistingApproachCheckList(List<DocumentDetailsVO> existingApproachCheckList) {
		this.existingApproachCheckList = existingApproachCheckList;
	}
	public List<DocumentDetailsVO> getDGPSCheckList() {
		return DGPSCheckList;
	}
	public void setDGPSCheckList(List<DocumentDetailsVO> dGPSCheckList) {
		DGPSCheckList = dGPSCheckList;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<CFCAttachment> getApplicantDocumentList() {
		return applicantDocumentList;
	}

	public void setApplicantDocumentList(List<CFCAttachment> applicantDocumentList) {
		this.applicantDocumentList = applicantDocumentList;
	}

	public List<CFCAttachment> getLandScheduleDocumentList() {
		return landScheduleDocumentList;
	}

	public void setLandScheduleDocumentList(List<CFCAttachment> landScheduleDocumentList) {
		this.landScheduleDocumentList = landScheduleDocumentList;
	}

	public List<CFCAttachment> getAppLandDocumentList() {
		return appLandDocumentList;
	}

	public void setAppLandDocumentList(List<CFCAttachment> appLandDocumentList) {
		this.appLandDocumentList = appLandDocumentList;
	}

	public List<CFCAttachment> getAppPurposeDocumentList() {
		return appPurposeDocumentList;
	}

	public void setAppPurposeDocumentList(List<CFCAttachment> appPurposeDocumentList) {
		this.appPurposeDocumentList = appPurposeDocumentList;
	}

	public List<CFCAttachment> getEncumbranceDocumentList() {
		return encumbranceDocumentList;
	}

	public void setEncumbranceDocumentList(List<CFCAttachment> encumbranceDocumentList) {
		this.encumbranceDocumentList = encumbranceDocumentList;
	}

	public List<CFCAttachment> getCourtOrdersLandDocumentList() {
		return courtOrdersLandDocumentList;
	}

	public void setCourtOrdersLandDocumentList(List<CFCAttachment> courtOrdersLandDocumentList) {
		this.courtOrdersLandDocumentList = courtOrdersLandDocumentList;
	}

	public List<CFCAttachment> getInsolvencyLandDocumentList() {
		return insolvencyLandDocumentList;
	}

	public void setInsolvencyLandDocumentList(List<CFCAttachment> insolvencyLandDocumentList) {
		this.insolvencyLandDocumentList = insolvencyLandDocumentList;
	}

	public List<CFCAttachment> getShajraAppLandDocumentList() {
		return shajraAppLandDocumentList;
	}

	public void setShajraAppLandDocumentList(List<CFCAttachment> shajraAppLandDocumentList) {
		this.shajraAppLandDocumentList = shajraAppLandDocumentList;
	}

	public List<CFCAttachment> getReleaseOrderDocumentList() {
		return releaseOrderDocumentList;
	}

	public void setReleaseOrderDocumentList(List<CFCAttachment> releaseOrderDocumentList) {
		this.releaseOrderDocumentList = releaseOrderDocumentList;
	}

	public List<CFCAttachment> getUsageAllotteesDocumentList() {
		return usageAllotteesDocumentList;
	}

	public void setUsageAllotteesDocumentList(List<CFCAttachment> usageAllotteesDocumentList) {
		this.usageAllotteesDocumentList = usageAllotteesDocumentList;
	}

	public List<CFCAttachment> getAccessNHSRDocumentList() {
		return accessNHSRDocumentList;
	}

	public void setAccessNHSRDocumentList(List<CFCAttachment> accessNHSRDocumentList) {
		this.accessNHSRDocumentList = accessNHSRDocumentList;
	}

	public List<CFCAttachment> getExistingApproachDocumentList() {
		return existingApproachDocumentList;
	}

	public void setExistingApproachDocumentList(List<CFCAttachment> existingApproachDocumentList) {
		this.existingApproachDocumentList = existingApproachDocumentList;
	}

	public List<CFCAttachment> getDGPSDocumentList() {
		return DGPSDocumentList;
	}

	public void setDGPSDocumentList(List<CFCAttachment> dGPSDocumentList) {
		DGPSDocumentList = dGPSDocumentList;
	}
	public String getDgpsdocPath() {
		return dgpsdocPath;
	}


	public void setDgpsdocPath(String dgpsdocPath) {
		this.dgpsdocPath = dgpsdocPath;
	}


	public List<LicenseApplicationFeeMasDTO> getFeeListDto() {
		return feeListDto;
	}


	public void setFeeListDto(List<LicenseApplicationFeeMasDTO> feeListDto) {
		this.feeListDto = feeListDto;
	}

	public String getViewFlag() {
		return viewFlag;
	}


	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}


	public List<LicenseApplicationLandSurroundingsDTO> getLicenseApplicationLandSurroundingsDtoList() {
		return licenseApplicationLandSurroundingsDtoList;
	}


	public void setLicenseApplicationLandSurroundingsDtoList(
			List<LicenseApplicationLandSurroundingsDTO> licenseApplicationLandSurroundingsDtoList) {
		this.licenseApplicationLandSurroundingsDtoList = licenseApplicationLandSurroundingsDtoList;
	}


	public LicenseApplicationMasterDTO getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}


	public void setLicenseApplicationMaster(LicenseApplicationMasterDTO licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}
	
}
