package com.abm.mainet.buildingplan.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.model.Khasra;
import org.model.Must;
import org.model.Owner;
import org.model.Tehsil;
import org.model.Village;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandScheduleDTO;
import com.abm.mainet.buildingplan.dto.LicenseApplicationMasterDTO;
import com.abm.mainet.buildingplan.service.IDeveloperRegistrationService;
import com.abm.mainet.buildingplan.service.INewLicenseFormService;
import com.abm.mainet.buildingplan.ui.model.NewTCPLicenseFormModel;
import com.abm.mainet.buildingplan.ui.model.SiteAffectedModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DirectorDetails;
import com.abm.mainet.common.integration.dto.DirectorInfo;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.MCACompany;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IAPISetuService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.aspose.slides.Collections.Specialized.CollectionsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/NewTCPLicenseForm.html")
public class NewTCPLicenseFormController extends AbstractFormController<NewTCPLicenseFormModel> {
	@Resource
	private IFileUploadService fileUpload;
	
	@Autowired
	IDeveloperRegistrationService developerRegistrationService;
	
	@Resource
	private IEmployeeService employeeService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	INewLicenseFormService newLicenseFormService;
	
	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IAPISetuService apiSetuService;
   
	private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperRegistrationFormController.class);


	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		EmployeeBean employee = employeeService.findById(UserSession.getCurrent().getEmployee().getEmpId());
		Long empId = null;
		if (null == employee.getReportingManager())
			empId = employee.getEmpId();
		else
			empId = employee.getReportingManager();
		this.getModel().setApplicationMasDTOList(newLicenseFormService
				.getNewLicenseSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), employee.getEmpId()));		
		return new ModelAndView("newLicenseSummary", MainetConstants.FORM_NAME,this.getModel());		 		
	}

	@RequestMapping(params = "addNewLicenseForm", method = RequestMethod.POST)
	public ModelAndView addNewLicenseForm(@RequestParam(value = "mode") String mode,
			@RequestParam(value = "id") Long id, final HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		final NewTCPLicenseFormModel model = getModel();

		if (MainetConstants.FlagA.equalsIgnoreCase(mode))
			model.setSaveMode(MainetConstants.FlagA);
		if (MainetConstants.FlagE.equalsIgnoreCase(mode))
			model.setSaveMode(MainetConstants.FlagE);
		if (MainetConstants.FlagV.equalsIgnoreCase(mode))
			model.setSaveMode(MainetConstants.FlagV);

		EmployeeBean employee = employeeService.findById(UserSession.getCurrent().getEmployee().getEmpId());
		Long empId = null;
		/*
		 * if (null == employee.getReportingManager()) { return new
		 * ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, model); } else {
		 */
		if (null == employee.getReportingManager()) {
			empId = employee.getEmpId();
		} else {
			empId = employee.getReportingManager();
		}
		model.setDeveloperRegistrationDTO(developerRegistrationService.getDeveloperRegistrationDtoById(empId,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		if(id != null) {
			model.setLicenseApplicationMasterDTO(setDataForDraftById(id)); 
			Map<Long, Set<File>> fileMap = new LinkedHashMap<>();
			
			Long applicationNo= model.getLicenseApplicationMasterDTO().getApplicationNo();
			
			model.setApplicantDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.APPLICANT_INFORMATION + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap = getUploadedFileList(model.getApplicantDocumentList(), FileNetApplicationClient.getInstance(),fileMap,0L);
			
			model.setAppPurposeDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.KHASRA_DEVELOPED_COLLABORATION + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getAppPurposeDocumentList(), FileNetApplicationClient.getInstance(),fileMap,300L);
			
			model.setEncumbranceDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ENCUMBRANCE_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getEncumbranceDocumentList(), FileNetApplicationClient.getInstance(),fileMap,400L);

			model.setCourtOrdersLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.COURT_ORDER_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getCourtOrdersLandDocumentList(), FileNetApplicationClient.getInstance(),fileMap,450L);

			model.setInsolvencyLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.INSOLVENCY_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getInsolvencyLandDocumentList(), FileNetApplicationClient.getInstance(),fileMap,500L);

			model.setShajraAppLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.SHAJRA_APP_LAND_DOCUMENTS + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getShajraAppLandDocumentList(), FileNetApplicationClient.getInstance(),fileMap,550L);

			model.setReleaseOrderDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.RELEASE_ORDER_DOC + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getReleaseOrderDocumentList(), FileNetApplicationClient.getInstance(),fileMap,600L);

			model.setUsageAllotteesDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.USAGE_ALLOTEE_DOC + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getUsageAllotteesDocumentList(), FileNetApplicationClient.getInstance(),fileMap,650L);

			model.setAccessNHSRDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.ACCESS_NHSR_DOC + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getAccessNHSRDocumentList(), FileNetApplicationClient.getInstance(),fileMap,700L);

			model.setExistingApproachDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.EXISTING_APPROACH_DOC + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getExistingApproachDocumentList(), FileNetApplicationClient.getInstance(),fileMap,750L);
			
			model.setLandScheduleDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.LAND_SCHEDULE + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getLandScheduleDocumentList(), FileNetApplicationClient.getInstance(),fileMap,100L);
				
			model.setDGPSDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DGPS_DOC + MainetConstants.WINDOWS_SLASH + applicationNo));
			fileMap =getUploadedFileList(model.getDGPSDocumentList(), FileNetApplicationClient.getInstance(),fileMap,800L);
			if(!CollectionUtils.isEmpty(model.getDGPSDocumentList())) {
				setDGSPFilePath(model.getDGPSDocumentList().get(0));
			}
	    	model.setAppLandDocumentList(iChecklistVerificationService.getDocumentUploadedByAppNoAndRefId(applicationNo, "NL"+ MainetConstants.WINDOWS_SLASH + MainetConstants.DETAILS_OF_LAND + MainetConstants.WINDOWS_SLASH + applicationNo));
	    	fileMap =getUploadedFileList(model.getAppLandDocumentList(), FileNetApplicationClient.getInstance(),fileMap,200L);
	    	
	    	FileUploadUtility.getCurrent().setFileMap(fileMap);
			LicenseApplicationMasterDTO masterDto = model.getLicenseApplicationMasterDTO();

	    	try {
				model.setTehsilList(getTehsilListByDistId(masterDto.getDdz1()));
				model.setVillageList(getVillagesList(masterDto.getDdz1(),masterDto.getKhrsThesil()));
				model.setMust(getMurabaByNVCODE(masterDto.getDdz1(), masterDto.getKhrsThesil(),  masterDto.getKhrsRevEst()));
				model.setKhasraList(getKhasraListByNVCODE(masterDto.getDdz1(), masterDto.getKhrsThesil(), 
						masterDto.getKhrsRevEst(), masterDto.getKhrsMustil()));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setServiceId(sm.getSmServiceId());
		model.getLicenseApplicationMasterDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.setApplicantCheckList(fetchCheckList(model, MainetConstants.APPLICANT_INFORMATION, null));
		model.setLandScheduleCheckList(fetchCheckList(model, MainetConstants.LAND_SCHEDULE, null));
		model.setDetailsOfLandCheckList(fetchCheckList(model, MainetConstants.DETAILS_OF_LAND, null));
		return new ModelAndView("newLicenseForm", MainetConstants.FORM_NAME, model);
		//}
	}
    
    private void setDGSPFilePath(CFCAttachment documentDetailsVO) {
    	try {
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
			String downloadLink=documentDetailsVO.getAttPath()+ MainetConstants.FILE_PATH_SEPARATOR+documentDetailsVO.getAttFname();
				String filePath = Utility.downloadedFileUrl(downloadLink, outputPath, this.getModel().getFileNetClient());
				this.getModel().setDgpsdocPath(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private LicenseApplicationMasterDTO setDataForDraftById(Long id) {
    	LicenseApplicationMasterDTO licMasDto = new LicenseApplicationMasterDTO();
    	
    	licMasDto = newLicenseFormService.findLicMasById(id);
    	
    	return licMasDto;
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveNewLicense")
    public ModelAndView saveNewLicense(final HttpServletRequest request) { 
    	getModel().bind(request);
    	final NewTCPLicenseFormModel model = getModel();
    	ModelAndView mv = null;
    	final List<DocumentDetailsVO> applicantDocList = fileUpload
				.prepareFileUpload(model.getApplicantCheckList());
		if (model.validateDocuments(applicantDocList)) {
			model.getLicenseApplicationMasterDTO().setDraftFlag(MainetConstants.FlagY);
			Boolean saveData = true;
			if(!model.getSaveMode().equals("V")) {
				 saveData = model.saveForm();
			}
	
	    	List<LookUp> purposeList = null;
	    	try {
	    		purposeList = CommonMasterUtility.getLevelData("PUR", 1,
	    				UserSession.getCurrent().getOrganisation());
	    	}catch(Exception Ex) {
	    		
	    	}   	
	    	model.setPurposeList(purposeList);
	    	if (!model.getSaveMode().equals(MainetConstants.FlagA)) {
	    	    LicenseApplicationMasterDTO licenseApplicationMasterDTO = model.getLicenseApplicationMasterDTO();
 
	    	    if (MainetConstants.YES.equalsIgnoreCase(licenseApplicationMasterDTO.getKhrsDevCollab())) {
	    	        List<DocumentDetailsVO> applicantPurposeCheckList = model.getApplicantPurposeCheckList();
 
	    	        if (applicantPurposeCheckList.isEmpty()) {
	    	            model.setApplicantPurposeCheckList(fetchCheckList(model, MainetConstants.KHASRA_DEVELOPED_COLLABORATION_QUESTION, null));
	    	        }
	    	    }
	    	}
	    	
	    	if (!saveData) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("Form save fail!!!"));
				mv = new ModelAndView("newLicenseAppFormValidn", MainetConstants.FORM_NAME, this.getModel());
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
				return mv;
	
			} else {
			model.getLicenseApplicationMasterDTO().setApplicantCheckListDoc(applicantDocList);
			newLicenseFormService.saveApplicantCheckList(model.getLicenseApplicationMasterDTO(), model.getServiceId());
	        mv = new ModelAndView("newLicensePurposeForm", MainetConstants.FORM_NAME, model);
	        
			}
		}else{
			mv = new ModelAndView("newLicenseAppFormValidn", MainetConstants.FORM_NAME, this.getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "saveNewLicensePurpose")
    public ModelAndView saveNewLicensePurpose(final HttpServletRequest request) { 
    	getModel().bind(request);
    	final NewTCPLicenseFormModel model = getModel();  
		
		ModelAndView mv = null;
		final List<DocumentDetailsVO> applicantPurDocList = newLicenseFormService
				.prepareFileUploadForNewLicenseDoc(model.getApplicantPurposeCheckList(), 300L);
		if(model.getLicenseApplicationMasterDTO().getKhrsDevCollab().equals(MainetConstants.NO) || model.validateDocuments(applicantPurDocList)){
			model.getLicenseApplicationMasterDTO().setDraftFlag(MainetConstants.FlagY);		
			Boolean saveData = true;
			if(!model.getSaveMode().equals("V")) {
				 saveData = model.saveForm();
			}
			if (!model.getSaveMode().equals(MainetConstants.FlagA)) {
	    	    LicenseApplicationMasterDTO licenseApplicationMasterDTO = model.getLicenseApplicationMasterDTO();
	    	    LicenseApplicationLandScheduleDTO landScheduleDTO = licenseApplicationMasterDTO.getLicenseApplicationLandScheduleDTO();
 
	    	    if (!("None".equals(landScheduleDTO.getEncumb()))) {
	    	        if (model.getEncumbranceCheckList().isEmpty()) {
	    	            model.setEncumbranceCheckList(fetchCheckList(model, MainetConstants.ENCUMBRANCE_CHECKLIST_DOCUMENTS, null));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getInsolv())) {
	    	        if (model.getInsolvencyLandCheckList().isEmpty()) {
	    	            model.setInsolvencyLandCheckList(fetchCheckList(model, MainetConstants.INSOLVENCY_LAND_DOC_TYPE1, null));
	    	        }
	    	    }
 
	    	    if ("No".equals(landScheduleDTO.getSpAppliedLand())) {
	    	        if (model.getShajraAppLandCheckList().isEmpty()) {
	    	            model.setShajraAppLandCheckList(fetchCheckList(model, MainetConstants.SHAJRA_APP_LAND_TYPE1, MainetConstants.SHAJRA_APP_LAND_TYPE2));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getExiLitigationCOrd())) {
	    	        if (model.getCourtOrdersLandCheckList().isEmpty()) {
	    	            model.setCourtOrdersLandCheckList(fetchCheckList(model, MainetConstants.COURT_ORDER_DOC_TYPE1, MainetConstants.COURT_ORDER_DOC_TYPE2));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getAccessNHSRFlag())) {
	    	        if (model.getAccessNHSRCheckList().isEmpty()) {
	    	            model.setAccessNHSRCheckList(fetchCheckList(model, MainetConstants.ACCESS_NHSR_DOC_TYPE1, MainetConstants.ACCESS_NHSR_DOC_TYPE2));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getPaSiteAprOther())) {
	    	        if (model.getExistingApproachCheckList().isEmpty()) {
	    	            model.setExistingApproachCheckList(fetchCheckList(model, MainetConstants.EXISTING_APPROACH_DOC_TYPE1, MainetConstants.EXISTING_APPROACH_DOC_TYPE2));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getSpAcqStatusExcluAquPr())) {
	    	        if (model.getReleaseOrderCheckList().isEmpty()) {
	    	            model.setReleaseOrderCheckList(fetchCheckList(model, MainetConstants.RELEASE_ORDER_DOC_TYPE1, MainetConstants.RELEASE_ORDER_DOC_TYPE2));
	    	        }
	    	    }
 
	    	    if ("Yes".equals(landScheduleDTO.getDeveloperConsentFlag())) {
	    	        if (model.getUsageAllotteesCheckList().isEmpty()) {
	    	            model.setUsageAllotteesCheckList(fetchCheckList(model, MainetConstants.USAGE_ALLOTEE_DOC_TYPE1, MainetConstants.USAGE_ALLOTEE_DOC_TYPE2));
	    	        }
	    	    }
 
	    	}
			if (!saveData) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("Form save fail!!!"));
				mv = new ModelAndView("newLicensePurposeFormValidn", MainetConstants.FORM_NAME, this.getModel());
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
				return mv;
	
			} else {
				model.getLicenseApplicationMasterDTO().setPurposeAttachments(applicantPurDocList);
				newLicenseFormService.saveApplicantPurposeCheckList(model.getLicenseApplicationMasterDTO(), model.getServiceId());
	    		mv = new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
	    	}
		}else{
			mv = new ModelAndView("newLicensePurposeFormValidn", MainetConstants.FORM_NAME, this.getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "saveNewLicenseLandDetails")
    public ModelAndView saveNewLicenseLandDetails(final HttpServletRequest request) { 
    	getModel().bind(request);
    	final NewTCPLicenseFormModel model = getModel();  
    	ModelAndView mv = null;
    	final List<DocumentDetailsVO> landScheduleDocList = newLicenseFormService
				.prepareFileUploadForNewLicenseDoc(model.getLandScheduleCheckList(),100L);
		if(model.validateDocuments(landScheduleDocList)){
			model.getLicenseApplicationMasterDTO().setDraftFlag(MainetConstants.FlagY);
			Boolean saveData = true;
			if(!model.getSaveMode().equals("V")) {
				 saveData = model.saveForm();
			}
			
			List<LookUp> subPurposeList = getSubPurposeList(model.getLicenseApplicationMasterDTO().getAppPLicPurposeId());
	    	model.setSuPurposeList(subPurposeList);
	
			
			if (!saveData) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("Form save fail!!!"));
				mv = new ModelAndView("newLicenseLandDetailsFormValidn", MainetConstants.FORM_NAME, this.getModel());
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
				return mv;
			}else {					
				model.getLicenseApplicationMasterDTO().setLandScheduleCheckListDoc(landScheduleDocList);	

				model.getLicenseApplicationMasterDTO().setEncumbranceCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getEncumbranceCheckList(),400L));
				
				model.getLicenseApplicationMasterDTO().setCourtOrdersLandCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getCourtOrdersLandCheckList(),450L));
				
				model.getLicenseApplicationMasterDTO().setInsolvencyLandCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getInsolvencyLandCheckList(),500L));
				
				model.getLicenseApplicationMasterDTO().setShajraAppLandCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getShajraAppLandCheckList(),550L));
				
				model.getLicenseApplicationMasterDTO().setReleaseOrderCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getReleaseOrderCheckList(),600L));
				
				model.getLicenseApplicationMasterDTO().setUsageAllotteesCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getUsageAllotteesCheckList(),650L));
				
				model.getLicenseApplicationMasterDTO().setAccessNHSRCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getAccessNHSRCheckList(),700L));
				
				model.getLicenseApplicationMasterDTO().setExistingApproachCheckListDoc(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getExistingApproachCheckList(),750L));
				model.setDGPSCheckList(newLicenseFormService
						.prepareFileUploadForNewLicenseDoc(model.getDGPSCheckList(),800L));
				newLicenseFormService.saveLandScheduleCheckListDoc(model.getLicenseApplicationMasterDTO(), model.getServiceId());
				
				
				if(model.getDGPSCheckList().isEmpty())
					model.setDGPSCheckList(fetchCheckList(model, MainetConstants.DGPS_DOC_TYPE1, MainetConstants.DGPS_DOC_TYPE2));
		        mv = new ModelAndView("newLicenseDetailsofAppLandForm", MainetConstants.FORM_NAME, model);
			}
		}else{
			mv = new ModelAndView("newLicenseLandDetailsFormValidn", MainetConstants.FORM_NAME, this.getModel());
		}
		 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());		
		 return mv;
    }
    
   
	@RequestMapping(method = RequestMethod.POST, params = "saveNewLicenseDetailsOfLandAndGetCharges")
    public ModelAndView saveNewLicenseDetailsOfLandAndGetCharges(final HttpServletRequest request) { 
    	getModel().bind(request);
       	final NewTCPLicenseFormModel model = this.getModel();
       	LicenseApplicationMasterDTO masDTO = this.getModel().getLicenseApplicationMasterDTO();
       	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
       	masDTO.setOrgId(orgid);
       	ModelAndView mv = null;
       	 List<DocumentDetailsVO> landDetailsDocList = newLicenseFormService
				.prepareFileUploadForNewLicenseDoc(model.getDetailsOfLandCheckList(), 200L);
       	final List<DocumentDetailsVO> dgpDocList = newLicenseFormService
    				.prepareFileUploadForNewLicenseDoc(model.getDGPSCheckList(),800L);
		if(model.validateDocuments(landDetailsDocList)||model.validateDocuments(dgpDocList)){
			model.getLicenseApplicationMasterDTO().setDraftFlag(MainetConstants.FlagY);
	
			Boolean saveData = true;
			if(!model.getSaveMode().equals("V")) {
				 saveData = model.saveForm();
			}
	
			
			if (!saveData) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("Form save fail!!!"));
				mv = new ModelAndView("newLicenseDetailsofAppLandFormValidn", MainetConstants.FORM_NAME, this.getModel());
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
				return mv;
			}else {
			model.getLicenseApplicationMasterDTO().setLandDetailsCheckListDoc(landDetailsDocList);
			model.getLicenseApplicationMasterDTO().setDGPSCheckListDoc(dgpDocList);
			newLicenseFormService.saveAppLandDetailsCheckListDoc(model.getLicenseApplicationMasterDTO(), model.getServiceId());	
	       	ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE,
					UserSession.getCurrent().getOrganisation().getOrgid());
	       	if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY) && !model.getSaveMode().equals("V")) {
	       		try {
	       			this.getModel().setLicenseApplicationMasterDTO(newLicenseFormService.getLicenceAppChargesFromBrmsRule(model.getLicenseApplicationMasterDTO()));
	       			
	       		}catch(Exception e) {
	       			ModelAndView mvExc = defaultResult();
	       			mvExc.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
					model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));
	
					return mvExc;
	       		}
	       	}
	       	if(!model.getLicenseApplicationMasterDTO().getFeeMasterDto().isEmpty()) {

	       		model.getLicenseApplicationMasterDTO().setScrutinyFeeStr(CommonMasterUtility.getAmountInIndianCurrency(model.getLicenseApplicationMasterDTO().getScrutinyFee()));
	       		model.getLicenseApplicationMasterDTO().setLicenseFeeStr(CommonMasterUtility.getAmountInIndianCurrency(model.getLicenseApplicationMasterDTO().getLicenseFee()));
	       		model.getLicenseApplicationMasterDTO().setTotalFeesStr(CommonMasterUtility.getAmountInIndianCurrency(model.getLicenseApplicationMasterDTO().getTotalFees()));
	       		
       			model.getLicenseApplicationMasterDTO().getFeeMasterDto().forEach(feedto -> {
       				if(feedto.getFees() != null) {
       					feedto.setFeesStr(CommonMasterUtility.getAmountInIndianCurrency(feedto.getFees()));
       				}
       			});
	       	}
	        mv = new ModelAndView("newLicenseDetailsFees", MainetConstants.FORM_NAME, model);	        
			}
		}else{
			mv = new ModelAndView("newLicenseDetailsofAppLandFormValidn", MainetConstants.FORM_NAME, this.getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
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
	
	
	@RequestMapping(params = "getSubPurpose2List", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getSubPurpose2List(@RequestParam("subpurpose1")Long subPurpose1) {
		List<LookUp> lookUpList1 = new ArrayList<>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("PUR", 3,
					UserSession.getCurrent().getOrganisation());

			lookUpList1 = lookUpList.stream().filter(lookUp -> subPurpose1.equals(lookUp.getLookUpParentId()))
					.collect(Collectors.toList());

			return lookUpList1;
		} catch (Exception e) {
			return lookUpList1;
		}
	}
	
	@RequestMapping(params = "getTehsilListByDistId", method = { RequestMethod.POST })
	@ResponseBody
	public List<Tehsil> getTehsilListByDistId(@RequestParam("districtId")Long districtId) throws Exception {
		
		List<Tehsil> tehsilList = null;
		LookUp distLookup = null;
		try {
			distLookup = CommonMasterUtility.getHierarchicalLookUp(districtId,
					UserSession.getCurrent().getOrganisation());
		}catch(Exception ex) {
			
		}
		if(distLookup != null) {
			tehsilList = newLicenseFormService.getTehsil(distLookup.getLookUpCode());
		}
		
		
		return tehsilList;
	}
	
	
	@RequestMapping(params = "getVillagesList", method = { RequestMethod.POST })
	@ResponseBody
	public List<Village> getVillagesList(@RequestParam("districtId") Long districtId,
			@RequestParam("tCode") String tCode) throws Exception {

		List<Village> villageList = null;
		LookUp distLookup = null;
		try {
			distLookup = CommonMasterUtility.getHierarchicalLookUp(districtId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception ex) {

		}
		if (distLookup != null) {
			villageList = newLicenseFormService.getVillages(distLookup.getLookUpCode(), tCode);
		}

		return villageList;
	}
	
	@RequestMapping(params = "getMurabaByNVCODE", method = { RequestMethod.POST })
	@ResponseBody
	public Must getMurabaByNVCODE(@RequestParam("districtId") Long districtId,
			@RequestParam("tCode") String tCode, @RequestParam("NVCode") String NVCode) throws Exception {
		
		Must must =null;
		LookUp distLookup = null;
		try {
			distLookup = CommonMasterUtility.getHierarchicalLookUp(districtId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception ex) {

		}
		if (distLookup != null) {
			must = newLicenseFormService.getMurabaByNVCODE(distLookup.getLookUpCode(), tCode, NVCode);
		}

		return must;
	}
	
	@RequestMapping(params = "getKhasraListByNVCODE", method = { RequestMethod.POST })
	@ResponseBody
	public List<Khasra> getKhasraListByNVCODE(@RequestParam("districtId") Long districtId,
			@RequestParam("tCode") String tCode, @RequestParam("NVCode") String NVCode, 
			@RequestParam("muraba") String muraba) throws Exception {
		
		List<Khasra> khasaraList =null;
		LookUp distLookup = null;
		try {
			distLookup = CommonMasterUtility.getHierarchicalLookUp(districtId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception ex) {

		}
		if (distLookup != null) {
			khasaraList = newLicenseFormService.getKhasraListByNVCODE(distLookup.getLookUpCode(), tCode, NVCode, muraba);
		}

		return khasaraList;
	}
	
	@RequestMapping(params = "getOwnersbykhewatOnline", method = { RequestMethod.POST })
	@ResponseBody
	public List<Owner> getOwnersbykhewatOnline(@RequestParam("districtId") Long districtId,
			@RequestParam("tCode") String tCode, @RequestParam("NVCode") String NVCode, 
			@RequestParam("_Khewat") String _Khewat) throws Exception {
		
		List<Owner> ownerList =null;
		LookUp distLookup = null;
		try {
			distLookup = CommonMasterUtility.getHierarchicalLookUp(districtId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception ex) {

		}
		if (distLookup != null) {
			ownerList = newLicenseFormService.getOwnersbykhewatOnline(distLookup.getLookUpCode(), tCode, NVCode, _Khewat);
		}

		return ownerList;
	}
	 

	public List<DocumentDetailsVO> fetchCheckList(NewTCPLicenseFormModel model, String licenseType, String type2){
    	final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.CHECKLIST_TCP_NEW_LICENSE_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		List<DocumentDetailsVO> checkList = new ArrayList<>();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {			
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			populateCheckListModel(model, checkListModel2, licenseType, type2);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
			checklistReqDto.setDataModel(checkListModel2);
			try {
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
							checkList = checkListList;						
						}
					}
				}
			} catch (Exception e) {
				LOGGER.info("Checklist not found..!");
				model.addValidationError(getApplicationSession().getMessage("Checklist not found..!"));
			}
			
		}
		return checkList;
    }
    
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

    private void populateCheckListModel(NewTCPLicenseFormModel model, CheckListModel checkListModel, String licenseType, String type2) {
		checkListModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		checkListModel.setServiceCode(MainetConstants.TCP_NEW_LICENSE);		
		checkListModel.setUsageSubtype1(licenseType);
		if(type2!=null)
			checkListModel.setUsageSubtype2(type2);
	}
    @RequestMapping(params = "getApplicantPurposeCheckList", method = RequestMethod.POST)
	public ModelAndView getApplicantPurposeCheckList(final HttpServletRequest request) {	
    	getModel().bind(request);
		final NewTCPLicenseFormModel model = getModel();
		if(model.getApplicantPurposeCheckList().isEmpty()){
			model.setApplicantPurposeCheckList(fetchCheckList(model, MainetConstants.KHASRA_DEVELOPED_COLLABORATION_QUESTION, null));
		}
		LicenseApplicationMasterDTO masterDto = model.getLicenseApplicationMasterDTO();
		if(masterDto.getDdz1() != null) {
			try {
				
				model.setTehsilList(getTehsilListByDistId(masterDto.getDdz1()));
				model.setVillageList(getVillagesList(masterDto.getDdz1(),masterDto.getKhrsThesil()));
				model.setMust(getMurabaByNVCODE(masterDto.getDdz1(), masterDto.getKhrsThesil(),  masterDto.getKhrsRevEst()));
				model.setKhasraList(getKhasraListByNVCODE(masterDto.getDdz1(), masterDto.getKhrsThesil(), 
						masterDto.getKhrsRevEst(), masterDto.getKhrsMustil()));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return new ModelAndView("newLicensePurposeFormValidn", MainetConstants.FORM_NAME, model);
	}
    
    @RequestMapping(params = "getEncumbranceCheckList", method = RequestMethod.POST)
   	public ModelAndView getEncumbranceCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getEncumbranceCheckList().isEmpty()){
   			model.setEncumbranceCheckList(fetchCheckList(model, MainetConstants.ENCUMBRANCE_CHECKLIST_DOCUMENTS, null));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getCourtOrdersCheckList", method = RequestMethod.POST)
   	public ModelAndView getCourtOrdersCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getCourtOrdersLandCheckList().isEmpty()){
   	   		model.setCourtOrdersLandCheckList(fetchCheckList(model, MainetConstants.COURT_ORDER_DOC_TYPE1, MainetConstants.COURT_ORDER_DOC_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getInsolvencyCheckList", method = RequestMethod.POST)
   	public ModelAndView getInsolvencyCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getInsolvencyLandCheckList().isEmpty()){
   	   		model.setInsolvencyLandCheckList(fetchCheckList(model, MainetConstants.INSOLVENCY_LAND_DOC_TYPE1, null));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getShajraAppCheckList", method = RequestMethod.POST)
   	public ModelAndView getShajraAppCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getShajraAppLandCheckList().isEmpty()){
   	   		model.setShajraAppLandCheckList(fetchCheckList(model, MainetConstants.SHAJRA_APP_LAND_TYPE1, MainetConstants.SHAJRA_APP_LAND_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getReleaseOrdCheckList", method = RequestMethod.POST)
   	public ModelAndView getReleaseOrdCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getReleaseOrderCheckList().isEmpty()){
   	   		model.setReleaseOrderCheckList(fetchCheckList(model, MainetConstants.RELEASE_ORDER_DOC_TYPE1, MainetConstants.RELEASE_ORDER_DOC_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getUsageAllotteeCheckList", method = RequestMethod.POST)
   	public ModelAndView getUsageAllotteeCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getUsageAllotteesCheckList().isEmpty()){
   	   		model.setUsageAllotteesCheckList(fetchCheckList(model, MainetConstants.USAGE_ALLOTEE_DOC_TYPE1, MainetConstants.USAGE_ALLOTEE_DOC_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getNHSRCheckList", method = RequestMethod.POST)
   	public ModelAndView getNHSRCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getAccessNHSRCheckList().isEmpty()){
   	   		model.setAccessNHSRCheckList(fetchCheckList(model, MainetConstants.ACCESS_NHSR_DOC_TYPE1, MainetConstants.ACCESS_NHSR_DOC_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getExistingApprCheckList", method = RequestMethod.POST)
   	public ModelAndView getExistingApprCheckList(final HttpServletRequest request) {	
       	getModel().bind(request);
   		final NewTCPLicenseFormModel model = getModel();
   		if(model.getExistingApproachCheckList().isEmpty()){
   	   		model.setExistingApproachCheckList(fetchCheckList(model, MainetConstants.EXISTING_APPROACH_DOC_TYPE1, MainetConstants.EXISTING_APPROACH_DOC_TYPE2));
   		}
   		return new ModelAndView("newLicenseLandDetailsForm", MainetConstants.FORM_NAME, model);
   	}
    
    @RequestMapping(params = "getPaymentStatus", method = RequestMethod.POST)
	public @ResponseBody List<LicenseApplicationMasterDTO> getPaymentStatus(
			final HttpServletRequest httpServletRequest,final HttpServletResponse httpServletResponse) {
		sessionCleanup(httpServletRequest);
		EmployeeBean employee = employeeService.findById(UserSession.getCurrent().getEmployee().getEmpId());
		Long empId = null;
		if (null == employee.getReportingManager())
			empId = employee.getEmpId();
		else
			empId = employee.getReportingManager();
		List<LicenseApplicationMasterDTO> summaryData = newLicenseFormService
				.getNewLicenseSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), employee.getEmpId());
		List<LicenseApplicationMasterDTO> filteredSummaryData = summaryData.stream()
				.filter(dto -> !"Y".equals(dto.getDraftFlag())).collect(Collectors.toList());
		return filteredSummaryData;
	}
    
    @ResponseBody
	@RequestMapping(params = "onlinePaymentReceipt", method = { RequestMethod.POST ,RequestMethod.GET})
	public String onlinePaymentReceipt(@RequestParam("applicationNo") String applicationNo,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
	long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
			+ "=OnlinePaymentReceiptReport.rptdesign&OrgId=" + orgid + "&Rp_ApplicationId=" + applicationNo;
    }
    
    
    public Map<Long, Set<File>> getUploadedFileList(List<CFCAttachment> newMap,

			FileNetApplicationClient fileNetApplicationClient,Map<Long, Set<File>> fileMap,Long count) {

		Set<File> fileList = null;
		for (CFCAttachment doc : newMap) {
			fileList = new HashSet<>();
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER

					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;

			String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();

			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,

					existingPath);

			String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,

					existingPath);

			directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);

			FileOutputStream fos = null;

			File file = null;

			try {

				final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

				file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

				fos = new FileOutputStream(file);

				fos.write(image);

				fos.close();

			} catch (final Exception e) {

				throw new FrameworkException("Exception in getting getUploadedFileList", e);

			} finally {

				try {

					if (fos != null) {

						fos.close();

					}

				} catch (final IOException e) {

					throw new FrameworkException("Exception in getting getUploadedFileList", e);

				}

			}

			fileList.add(file);
			fileMap.put(count, fileList);
			count++;
		}

		return fileMap;

	}
    
    @ResponseBody
    @RequestMapping(params = "checkIfDetailsExist", method = { RequestMethod.POST ,RequestMethod.GET})
    public String checkIfDetailsExist(@RequestParam("khrsDist") Long khrsDist,@RequestParam("khrsDevPlan") Long khrsDevPlan,
			@RequestParam("khrsZone") Long khrsZone, @RequestParam("khrsSec") Long khrsSec, @RequestParam("khrsThesil") String khrsThesil,
			@RequestParam("khrsRevEst") String khrsRevEst, @RequestParam("khrsHadbast") String khrsHadbast, @RequestParam("khrsMustil") String khrsMustil,
			@RequestParam("khrsKilla") String khrsKilla, final HttpServletRequest request) {	
    	getModel().bind(request);
		final NewTCPLicenseFormModel model = getModel();

		List<LicenseApplicationMaster> licenseApplications = newLicenseFormService.doesLicenseApplicationExist(
				khrsDist, khrsDevPlan, khrsZone, khrsSec, khrsThesil, khrsRevEst, khrsHadbast, khrsMustil,
				khrsKilla);
		if(CollectionUtils.isNotEmpty(licenseApplications))
			return licenseApplications.get(0).getApplicationNoEService();
		else
			return null;
	}
    
	@RequestMapping(method = RequestMethod.POST, params = "viewFeeCharges")
	public ModelAndView viewFeeCharges(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setFeeListDto(newLicenseFormService.getFeeAndCharges(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		mv = new ModelAndView("newLicenseDetailsFeesView", MainetConstants.FORM_NAME, getModel());

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "saveFeeCharges")
	public ModelAndView saveFeeCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final NewTCPLicenseFormModel model = getModel();
		if (model.saveFeeCharges()) {
			return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
		
		 } else { 
			 return defaultMyResult(); 
		 }
		 

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "viewSurroundingDetail")
	public ModelAndView viewSurroundingDetail(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setLicenseApplicationLandSurroundingsDtoList(newLicenseFormService.getSurroundingDetail(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		mv = new ModelAndView("SurroundingDetailView", MainetConstants.FORM_NAME, getModel());

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "saveSurroundingDetail")
	public ModelAndView saveSurroundingDetail(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final NewTCPLicenseFormModel model = getModel();
		if (model.saveSurroundingDetail()) {
			return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));

		} else {
			return defaultMyResult();
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "fetchCinDataOnNewLicense")
    public @ResponseBody MCACompany fetchCinDataOnNewLicense(@RequestParam("cinNo") String cinNo, final HttpServletRequest request, final HttpServletResponse httpServletResponse) { 
    	MCACompany companyDetails = new MCACompany();
    	MCACompany fetchCompanyDetails = apiSetuService.fetchCompanyDetails(cinNo);
    	companyDetails=fetchCompanyDetails;
		return companyDetails;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "fetchDirectorDataOnNewLicense")
    public @ResponseBody List<DirectorInfo> fetchDirectorDataOnNewLicense(@RequestParam("cinNo") String cinNo, final HttpServletRequest request, final HttpServletResponse httpServletResponse) { 
    	List<DirectorInfo> developerDirectorDTOList = new ArrayList<>();
    	DirectorDetails fetchDirectorDetails = apiSetuService.fetchDirectorDetails(cinNo);
    	List<DirectorInfo> developerDirectorDetailsDTOList = new ArrayList<>();
    	if(!fetchDirectorDetails.getDirectorInfo().isEmpty()){
    		fetchDirectorDetails.getDirectorInfo().forEach(directorList->{
    			DirectorInfo directorDetail = new DirectorInfo();
    			directorDetail.setDin(directorList.getDin());
    			directorDetail.setContactNumber(directorList.getContactNumber());
    			directorDetail.setName(directorList.getName());
    			developerDirectorDetailsDTOList.add(directorDetail);
    			
    		});    		
    		developerDirectorDTOList = developerDirectorDetailsDTOList;
    	}
       return developerDirectorDTOList;
    }
	 
}
