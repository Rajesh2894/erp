package com.abm.mainet.buildingplan.ui.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.dto.DevLicenseHDRUDTO;
import com.abm.mainet.buildingplan.dto.DeveloperAuthorizedUserDTO;
import com.abm.mainet.buildingplan.dto.DeveloperDirectorInfoDTO;
import com.abm.mainet.buildingplan.dto.DeveloperStakeholderDTO;
import com.abm.mainet.buildingplan.service.IDeveloperRegistrationService;
import com.abm.mainet.buildingplan.ui.model.DeveloperRegistrationFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CertificateParameters;
import com.abm.mainet.common.integration.dto.Consent;
import com.abm.mainet.common.integration.dto.ConsentArtifact;
import com.abm.mainet.common.integration.dto.Data;
import com.abm.mainet.common.integration.dto.DataConsumer;
import com.abm.mainet.common.integration.dto.DataProvider;
import com.abm.mainet.common.integration.dto.DateRange;
import com.abm.mainet.common.integration.dto.DirectorDetails;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.Frequency;
import com.abm.mainet.common.integration.dto.MCACompany;
import com.abm.mainet.common.integration.dto.PanCard;
import com.abm.mainet.common.integration.dto.Permission;
import com.abm.mainet.common.integration.dto.Purpose;
import com.abm.mainet.common.integration.dto.Signature;
import com.abm.mainet.common.integration.dto.User;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IAPISetuService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/DeveloperRegistrationForm.html")
public class DeveloperRegistrationFormController extends AbstractFormController<DeveloperRegistrationFormModel> {
	@Resource
	private IFileUploadService fileUpload;
	
	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Resource
	private IDeveloperRegistrationService devRegService;
	
	@Autowired
	private IAPISetuService apiSetuService;
	
	@Resource
	private IEmployeeService employeeService;
	
	@Autowired
	ServiceMasterService serviceMaster;

	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperRegistrationFormController.class);
   

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final DeveloperRegistrationFormModel model = getModel();
        model.setCommonHelpDocs("DeveloperRegistrationForm.html");
        EmployeeBean employee = employeeService.findById(UserSession.getCurrent().getEmployee().getEmpId());
		Long empId = employee.getEmpId();
		model.setDeveloperRegistrationDTO(devRegService.getDeveloperRegistrationDtoById(empId,
				UserSession.getCurrent().getOrganisation().getOrgid()));
	    model.setCheckListDocument(model.getDeveloperRegistrationDTO().getCheckListDocumentSet());	
		ServiceMaster serviceMst = serviceMaster.getServiceByShortName("DRN",
				UserSession.getCurrent().getOrganisation().getOrgid());
    	model.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	model.setServiceId(serviceMst.getSmServiceId());
    	model.setDeptCode(serviceMst.getTbDepartment().getDpDeptcode());
    	Map<Long, Set<File>> fileMap = new LinkedHashMap<>();
    	model.getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().forEach(stkDoc->{
    		if(!stkDoc.getStakeholderDocsList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    				devRegService.getDocUploadedFileList(stkDoc.getStakeholderDocsList(), FileNetApplicationClient.getInstance(), fileMap, 500L));
    		}
    	});
    	
    	model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().forEach(dirDoc->{
    		if(!dirDoc.getDirectorDocsList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(dirDoc.getDirectorDocsList(), FileNetApplicationClient.getInstance(), fileMap, 100L));
    		}
    	});
    	
    	model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().forEach(authDoc->{
    		if(!authDoc.getAuthDocumentList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(authDoc.getAuthDocumentList(), FileNetApplicationClient.getInstance(), fileMap, 200L));
    		}
    		if(!authDoc.getAuthDigitalPDFList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(authDoc.getAuthDigitalPDFList(), FileNetApplicationClient.getInstance(), fileMap, 300L));
    		}
    	});
    	
    	if(!model.getDeveloperRegistrationDTO().getAttachDocsList().isEmpty()){
    		FileUploadUtility.getCurrent().setFileMap(
					devRegService.getDocUploadedFileList(model.getDeveloperRegistrationDTO().getAttachDocsList(), FileNetApplicationClient.getInstance(), fileMap, 400L));
    	}
		return new ModelAndView("developerRegForm", MainetConstants.FORM_NAME, model);
    }
 
    @RequestMapping(method = RequestMethod.POST, params = "saveDevloperInfo")
    public ModelAndView saveDevloperInfo(final HttpServletRequest request) { 
    	getModel().bind(request);
    	final DeveloperRegistrationFormModel model = getModel(); 
    	if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag()!=null){
    		if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag().equals(MainetConstants.FlagN)){
        		model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().clear();
        	}
    	}
    	if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag()!=null){
    		if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals(MainetConstants.FlagN)){
        		model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().clear();
        	}
    	}

    	model.saveForm();
    	devRegService.saveApplicantInfoDocuments(model.getDeveloperRegistrationDTO(), UserSession.getCurrent().getEmployee().getEmpId(), model.getOrgId(),
    			model.getServiceId(), model.getDeptCode());
    	Map<Long, Set<File>> fileMap = new LinkedHashMap<>();
    	model.getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().forEach(stkDoc->{
    		if(!stkDoc.getStakeholderDocsList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    				devRegService.getDocUploadedFileList(stkDoc.getStakeholderDocsList(), FileNetApplicationClient.getInstance(), fileMap, 500L));
    		}
    	});
    	
    	model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().forEach(dirDoc->{
    		if(!dirDoc.getDirectorDocsList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(dirDoc.getDirectorDocsList(), FileNetApplicationClient.getInstance(), fileMap, 100L));
    		}
    	});
    	
    	model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().forEach(authDoc->{
    		if(!authDoc.getAuthDocumentList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(authDoc.getAuthDocumentList(), FileNetApplicationClient.getInstance(), fileMap, 200L));
    		}
    		if(!authDoc.getAuthDigitalPDFList().isEmpty()){
    			FileUploadUtility.getCurrent().setFileMap(
    					devRegService.getDocUploadedFileList(authDoc.getAuthDigitalPDFList(), FileNetApplicationClient.getInstance(), fileMap, 300L));
    		}
    	});
    	
    	if(!model.getDeveloperRegistrationDTO().getAttachDocsList().isEmpty()){
    		FileUploadUtility.getCurrent().setFileMap(
					devRegService.getDocUploadedFileList(model.getDeveloperRegistrationDTO().getAttachDocsList(), FileNetApplicationClient.getInstance(), fileMap, 400L));
    	}
    	
        return new ModelAndView("authorizedUser", MainetConstants.FORM_NAME, model);
    }
    

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = "saveAuthorizedUserInfo")
	public ModelAndView saveauthorizedUserInfo(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		final DeveloperRegistrationFormModel model = getModel();	
		List<String> duplicateMobileList = new ArrayList<>();
		List<String> duplicateEmailList = new ArrayList<>();
		List<String> authMobileNoList = this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList()
				.stream().filter(authDto-> authDto.getAuthUserId()==null).map(dto -> dto.getAuthMobileNo().toString()).collect(Collectors.toList());
		List<String> authEmailList = this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList()
				.stream().filter(authDto-> authDto.getAuthUserId()==null).map(dto -> dto.getAuthEmail()).collect(Collectors.toList());
		if(!authMobileNoList.isEmpty() && !authEmailList.isEmpty()){
		List<Object[]> emplMobileEmailObjects = employeeService.validateIsAuthUsersMobilesAndEmails(authMobileNoList, authEmailList);
		
		duplicateMobileList = emplMobileEmailObjects.stream().map(object -> object[0].toString())
		        .map(mobile -> mobile.isEmpty() ? null : mobile).filter(mobile -> mobile != null).collect(Collectors.toList());
		duplicateEmailList = emplMobileEmailObjects.stream().map(object -> object[1].toString())
		        .map(email -> email.isEmpty() ? null : email).filter(email -> email != null).collect(Collectors.toList());
		
		}
		if(!duplicateMobileList.isEmpty() || !duplicateEmailList.isEmpty()) {
			if(!duplicateMobileList.isEmpty())
				model.addValidationError(getApplicationSession().getMessage(
					"The Following Mobile Number/s Have Been Already Registerd") + MainetConstants.WHITE_SPACE
					+ String.join(MainetConstants.WHITE_SPACE + MainetConstants.operator.COMA, duplicateMobileList));
			if(!duplicateEmailList.isEmpty())
				model.addValidationError(getApplicationSession().getMessage(
					"The Following Email/s Have Been Already Registerd") + MainetConstants.WHITE_SPACE
					+ String.join(MainetConstants.WHITE_SPACE + MainetConstants.operator.COMA, duplicateEmailList));			
			this.getModel().setIsMobileValidation(MainetConstants.FlagY);
			mv = new ModelAndView("authorizedUser", MainetConstants.FORM_NAME, getModel());
		} else {
			if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag()!=null){
	    		if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag().equals(MainetConstants.FlagN)){
	        		model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().clear();
	        	}
	    	}
	    	if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag()!=null){
	    		if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals(MainetConstants.FlagN)){
	        		model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().clear();
	        	}
	    	}
	    	model.saveForm();
			devRegService.saveAuthUserDocuments(model.getDeveloperRegistrationDTO(), UserSession.getCurrent().getEmployee().getEmpId(), model.getOrgId(),
	    			model.getServiceId(), model.getDeptCode());
			if(model.getCheckList().isEmpty()){
				getCheckListDoc(model);
			}
						
			Map<Long, Set<File>> fileMap = new LinkedHashMap<>();
			
			if(model.getCheckListDocument()!=null){
				FileUploadUtility.getCurrent().setFileMap(
					devRegService.getUploadedFileList(model.getCheckListDocument(), FileNetApplicationClient.getInstance(), fileMap));
			}
			
	    	model.getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().forEach(stkDoc->{
	    		if(!stkDoc.getStakeholderDocsList().isEmpty()){
	    			FileUploadUtility.getCurrent().setFileMap(
	    				devRegService.getDocUploadedFileList(stkDoc.getStakeholderDocsList(), FileNetApplicationClient.getInstance(), fileMap, 500L));
	    		}
	    	});
	    	
	    	model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().forEach(dirDoc->{
	    		if(!dirDoc.getDirectorDocsList().isEmpty()){
	    			FileUploadUtility.getCurrent().setFileMap(
	    					devRegService.getDocUploadedFileList(dirDoc.getDirectorDocsList(), FileNetApplicationClient.getInstance(), fileMap, 100L));
	    		}
	    	});
	    	
	    	model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().forEach(authDoc->{
	    		if(!authDoc.getAuthDocumentList().isEmpty()){
	    			FileUploadUtility.getCurrent().setFileMap(
	    					devRegService.getDocUploadedFileList(authDoc.getAuthDocumentList(), FileNetApplicationClient.getInstance(), fileMap, 200L));
	    		}
	    		if(!authDoc.getAuthDigitalPDFList().isEmpty()){
	    			FileUploadUtility.getCurrent().setFileMap(
	    					devRegService.getDocUploadedFileList(authDoc.getAuthDigitalPDFList(), FileNetApplicationClient.getInstance(), fileMap, 300L));
	    		}
	    	});
	    	
	    	if(!model.getDeveloperRegistrationDTO().getAttachDocsList().isEmpty()){
	    		FileUploadUtility.getCurrent().setFileMap(
						devRegService.getDocUploadedFileList(model.getDeveloperRegistrationDTO().getAttachDocsList(), FileNetApplicationClient.getInstance(), fileMap, 401L));
	    	}
			mv = new ModelAndView("developerCapacity", MainetConstants.FORM_NAME, getModel());
		} 
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveDevCapacityForm")
    public ModelAndView saveDevCapacityForm(final HttpServletRequest request, final HttpServletResponse httpServletResponse) {   	
		getModel().bind(request);
    	ModelAndView mv = null;
        final DeveloperRegistrationFormModel model = this.getModel(); 
		if (model.validateInputs()) {
			if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag()!=null){
	    		if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag().equals(MainetConstants.FlagN)){
	        		model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().clear();
	        	}
	    	}
	    	if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag()!=null){
	    		if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals(MainetConstants.FlagN)){
	        		model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().clear();
	        	}
	    	}
			model.saveForm();	
			devRegService.saveCapacityFormDocuments(model.getDeveloperRegistrationDTO(), UserSession.getCurrent().getEmployee().getEmpId(), model.getOrgId(),
	    			model.getServiceId(), model.getDeptCode(), model.getCheckListDoc());

        	final List<LookUp> lookUps = CommonMasterUtility.getLookUps("DEV", UserSession.getCurrent().getOrganisation());
    		for (final LookUp lookUp : lookUps) {
    			if ((model.getDeveloperRegistrationDTO().getDevType() != null)) {
    				if (lookUp.getLookUpId() == model.getDeveloperRegistrationDTO().getDevType()) {
    					model.getDeveloperRegistrationDTO().setDevTypeDesc(lookUp.getLookUpDesc());
    					model.getDeveloperRegistrationDTO().setDevTypeCode(lookUp.getLookUpCode());
    					break;
    				}
    			}

    		}

    		final List<LookUp> genderLookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
    		for (final LookUp lookUp : genderLookUps) {
    			if ((model.getDeveloperRegistrationDTO().getGender() != null) && model.getDeveloperRegistrationDTO().getGender() != 0l) {
    				if (lookUp.getLookUpId() == model.getDeveloperRegistrationDTO().getGender()) {
    					model.getDeveloperRegistrationDTO().setGenderDesc(lookUp.getLookUpDesc());
    					break;
    				}
    			}

    		}
    		
    		final List<LookUp> purLookUps = CommonMasterUtility.getLevelData("PUR", 1,UserSession.getCurrent().getOrganisation());
    		for (final LookUp lookUp : purLookUps) {
    			for(DevLicenseHDRUDTO devLicenseList : model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList()){
    				if (devLicenseList.getPurposeOfColony() != null && devLicenseList.getPurposeOfColony() != 0l) {
    					if (lookUp.getLookUpId() == devLicenseList.getPurposeOfColony()) {
    						devLicenseList.setPurposeOfColonyDesc(lookUp.getLookUpDesc());
    						break;
    					}
    				}
    			}    			

    		}
    		
    		final List<LookUp> authGenLookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
    		for (final LookUp lookUp : authGenLookUps) {
    			for(DeveloperAuthorizedUserDTO authorizedUserList : model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList()){
    				if (authorizedUserList.getAuthGender() != null && authorizedUserList.getAuthGender() != 0l) {
    					if (lookUp.getLookUpId() == authorizedUserList.getAuthGender()) {
    						authorizedUserList.setAuthGenderDesc(lookUp.getLookUpDesc());
    					}
    				}
    			}    			

    		}
       	
    	mv = new ModelAndView("developerSummary", MainetConstants.FORM_NAME, this.getModel());
    	Map<String, Set<File>> fileMap1 = new LinkedHashMap<>();   	
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			fileMap1.put(entry.getKey().toString()+"doc", entry.getValue());
		}
        this.getModel().getDeveloperRegistrationDTO().setFileList(fileMap1);
        }
        else{
           mv = new ModelAndView("developerCapacityValidn", MainetConstants.FORM_NAME, this.getModel());
        }
       
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "addStakeholderRow")
	public ModelAndView addStakeholderRow(final HttpServletRequest request) {
    	getModel().bind(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		
		
		List<DeveloperStakeholderDTO> list = new ArrayList<>();
		
		for (DeveloperStakeholderDTO developerStakeholderDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList()) {
			list.add(developerStakeholderDTO);
		}		
		int count = 0;
		for (DeveloperStakeholderDTO developerStakeholderDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList()) {

			if (developerStakeholderDTO.getStakeholderName() != null && developerStakeholderDTO.getStakeholderDesignation() != null
					&& developerStakeholderDTO.getStakeholderPercentage()!=null) {
				try {
					BeanUtils.copyProperties(developerStakeholderDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
		}		
		this.getModel().getDeveloperRegistrationDTO().setDeveloperStakeholderDTOList(list);
		long lengthOfList = list.size() - 1;
		List<DeveloperStakeholderDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			DeveloperStakeholderDTO tesDto = list.get(i);
			DeveloperStakeholderDTO newData = new DeveloperStakeholderDTO();
			newData = tesDto;
			newData.setStakeholderName(this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().get(i).getStakeholderName());
			newData.setStakeholderDesignation(this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().get(i).getStakeholderDesignation());
			newData.setStakeholderPercentage(this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().get(i).getStakeholderPercentage());

			listData.add(newData);
		}
		
		Long count1 = 500l;
		Long count2 = 100l;
		Long count3 = 2000l;
		Long count4 = 300l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
				fileMap1.put(count2, entry.getValue());
				count2++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
				fileMap1.put(count3, entry.getValue());
				count3++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
				fileMap1.put(count4, entry.getValue());
				count4++;
			}
			
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new DeveloperStakeholderDTO());

		this.getModel().getDeveloperRegistrationDTO().setDeveloperStakeholderDTOList(listData);
		return new ModelAndView("developerRegFormValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "deleteStakeholderRow")
	public ModelAndView deleteStakeholderRow(final HttpServletRequest request) {
		getModel().bind(request);
		int id = this.getModel().getStkId().intValue();
		List<Long> enclosureRemoveById = new ArrayList<>();
		if (this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().get(id).getStakeholderId()!= null) {
			enclosureRemoveById.add(this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().get(id).getStakeholderId());

			this.getModel().setRemovedIds(enclosureRemoveById);
		}

		this.getModel().getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			Long count1 = 500l;
			Long count2 = 100l;
			Long count3 = 200l;
			Long count4 = 300l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
				}else{
					final List<File> list1 = new ArrayList<>(entry.getValue());
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
						fileMap1.put(count1, entry.getValue());
						count1++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
						fileMap1.put(count2, entry.getValue());
						count2++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
						fileMap1.put(count3, entry.getValue());
						count3++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
						fileMap1.put(count4, entry.getValue());
						count4++;
					}
				}				
			}		
			FileUploadUtility.getCurrent().setFileMap(fileMap1);
		}
		return new ModelAndView("developerRegFormValidn", MainetConstants.FORM_NAME, getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "addDirectorRow")
	public ModelAndView addDirectorRow(final HttpServletRequest request) {
    	getModel().bind(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DeveloperDirectorInfoDTO> list = new ArrayList<>();
		
		for (DeveloperDirectorInfoDTO developerDirectorInfoDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList()) {
			list.add(developerDirectorInfoDTO);
		}		
		int count = 0;
		for (DeveloperDirectorInfoDTO developerDirectorInfoDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList()) {

			if (developerDirectorInfoDTO.getDinNumber() != null && developerDirectorInfoDTO.getDirectorContactNumber() != null
					&& developerDirectorInfoDTO.getDirectorName() !=null) {
				try {
					BeanUtils.copyProperties(developerDirectorInfoDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
		}		
		this.getModel().getDeveloperRegistrationDTO().setDeveloperDirectorInfoDTOList(list);
		long lengthOfList = list.size() - 1;
		List<DeveloperDirectorInfoDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			DeveloperDirectorInfoDTO tesDto = list.get(i);
			DeveloperDirectorInfoDTO newData = new DeveloperDirectorInfoDTO();
			newData = tesDto;
			newData.setDirectorName(this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(i).getDirectorName());
			newData.setDinNumber(this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(i).getDinNumber());
			newData.setDirectorContactNumber(this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(i).getDirectorContactNumber());

			listData.add(newData);
		}
		
		Long count1 = 500l;
		Long count2 = 100l;
		Long count3 = 200l;
		Long count4 = 300l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
				fileMap1.put(count2, entry.getValue());
				count2++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
				fileMap1.put(count3, entry.getValue());
				count3++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
				fileMap1.put(count4, entry.getValue());
				count4++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new DeveloperDirectorInfoDTO());

		this.getModel().getDeveloperRegistrationDTO().setDeveloperDirectorInfoDTOList(listData);
		return new ModelAndView("developerRegFormValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "deleteDirectorRow")
	public ModelAndView deleteDirectorRow(final HttpServletRequest request) {
		getModel().bind(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		int id = this.getModel().getDirectId().intValue();
		if (this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(id).getDirectorId()!= null) {
			enclosureRemoveById.add(this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(id).getDirectorId());
			this.getModel().setRemovedIds(enclosureRemoveById);
		}

		this.getModel().getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			Long count1 = 500l;
			Long count2 = 100l;
			Long count3 = 200l;
			Long count4 = 300l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id+100 == entry.getKey().intValue()) {
					entry.getValue().clear();
				}else{
					final List<File> list1 = new ArrayList<>(entry.getValue());
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
						fileMap1.put(count1, entry.getValue());
						count1++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
						fileMap1.put(count2, entry.getValue());
						count2++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
						fileMap1.put(count3, entry.getValue());
						count3++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
						fileMap1.put(count4, entry.getValue());
						count4++;
					}
				}				
			}	
			FileUploadUtility.getCurrent().setFileMap(fileMap1);
		}
		return new ModelAndView("developerRegFormValidn", MainetConstants.FORM_NAME, getModel());

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "addAuthUserRow")
	public ModelAndView addAuthUserRow(final HttpServletRequest request) {
    	getModel().bind(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DeveloperAuthorizedUserDTO> list = new ArrayList<>();
		
		for (DeveloperAuthorizedUserDTO developerAuthorizedUserDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList()) {
			list.add(developerAuthorizedUserDTO);
		}		
		int count = 0;
		for (DeveloperAuthorizedUserDTO developerAuthorizedUserDTO : this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList()) {

			if (developerAuthorizedUserDTO.getAuthUserName() != null && developerAuthorizedUserDTO.getAuthMobileNo() != null
					&& developerAuthorizedUserDTO.getAuthEmail()!=null && developerAuthorizedUserDTO.getAuthGender()!=null && developerAuthorizedUserDTO.getAuthDOB()!=null
					&& developerAuthorizedUserDTO.getAuthPanNumber()!=null) {
				try {
					BeanUtils.copyProperties(developerAuthorizedUserDTO, list.get(count));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
		}		
		this.getModel().getDeveloperRegistrationDTO().setDeveloperAuthorizedUserDTOList(list);
		long lengthOfList = list.size() - 1;
		List<DeveloperAuthorizedUserDTO> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			DeveloperAuthorizedUserDTO tesDto = list.get(i);
			DeveloperAuthorizedUserDTO newData = new DeveloperAuthorizedUserDTO();
			newData = tesDto;
			newData.setAuthUserName(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthUserName());
			newData.setAuthMobileNo(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthMobileNo());
			newData.setAuthEmail(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthEmail());
			newData.setAuthGender(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthGender());
			newData.setAuthDOB(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthDOB());
			newData.setAuthPanNumber(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i).getAuthPanNumber());
			listData.add(newData);
		}
		
		Long count1 = 500l;
		Long count2 = 100l;
		Long count3 = 200l;
		Long count4 = 300l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
				fileMap1.put(count2, entry.getValue());
				count2++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
				fileMap1.put(count3, entry.getValue());
				count3++;
			}
			if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
				fileMap1.put(count4, entry.getValue());
				count4++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);

		listData.add(new DeveloperAuthorizedUserDTO());

		this.getModel().getDeveloperRegistrationDTO().setDeveloperAuthorizedUserDTOList(listData);
		return new ModelAndView("authorizedUser", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "deleteAuthUserRow")
	public ModelAndView deleteAuthUserRow(final HttpServletRequest request) {
		getModel().bind(request);
		int id = this.getModel().getAuthId().intValue();
		List<Long> enclosureRemoveById = new ArrayList<>();
		if (this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(id).getAuthUserId()!= null) {
			enclosureRemoveById.add(this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(id).getAuthUserId());

			this.getModel().setRemovedIds(enclosureRemoveById);
		}

		this.getModel().getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			Long count1 = 500l;
			Long count2 = 100l;
			Long count3 = 200l;
			Long count4 = 300l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id+200 == entry.getKey().intValue()) {
					entry.getValue().clear();
				}
				else if (id+300 == entry.getKey().intValue()) {
					entry.getValue().clear();
				}else{
					final List<File> list1 = new ArrayList<>(entry.getValue());
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("stakeholder")) {
						fileMap1.put(count1, entry.getValue());
						count1++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("directorInfo")) {
						fileMap1.put(count2, entry.getValue());
						count2++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("boardResolution")) {
						fileMap1.put(count3, entry.getValue());
						count3++;
					}
					if (!list1.isEmpty()  && list1.get(0).getPath().contains("digitalSign")) {
						fileMap1.put(count4, entry.getValue());
						count4++;
					}
				}				
			}	
			FileUploadUtility.getCurrent().setFileMap(fileMap1);
		}
		return new ModelAndView("authorizedUser", MainetConstants.FORM_NAME, getModel());

	}
	
	private void populateCheckListModel(DeveloperRegistrationFormModel model, CheckListModel checkListModel) {
		checkListModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		checkListModel.setServiceCode(MainetConstants.DEVELOPER_REGISTRATION);
		LookUp lookUp = null;
		try {
			lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getDeveloperRegistrationDTO().getDevType(), UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV - CCG ", e);
		}
		if (lookUp != null) {
			checkListModel.setUsageSubtype1(lookUp.getDescLangFirst());
		}
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
	
	@RequestMapping(params = "verifyPanDetails", method = RequestMethod.POST)
	public Map<String, Object> verifyPanDetails(@RequestParam(value= "fullName")String fullName, @RequestParam(value= "panno") String panno, @RequestParam(value= "dob") String dob,
			@RequestParam(value= "gender") Long gender, @RequestParam(value= "email") String email, @RequestParam(value= "mobile") String mobile, final HttpServletRequest request) throws ParseException{
		getModel().bind(request);
		final DeveloperRegistrationFormModel model = getModel();	
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		PanCard panDto = new PanCard();
		panDto.setTxnId(UUID.randomUUID().toString());
		panDto.setFormat("xml");
		String genderDesc = null;
		final List<LookUp> genderLookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : genderLookUps) {
			if ((gender != null) && gender != 0l) {
				if (lookUp.getLookUpId() == gender) {
					genderDesc=lookUp.getLookUpDesc();
					break;
				}
			}

		}
		
		CertificateParameters certificateParameters = new CertificateParameters();
		certificateParameters.setFullName(fullName);
		certificateParameters.setpANFullName(fullName);
		
		Date date1=new SimpleDateFormat(MainetConstants.DATE_FORMAT).parse(dob);
		String date2=new SimpleDateFormat(MainetConstants.DATE_FORMATS).format(date1);
		certificateParameters.setdOB(date2);
		certificateParameters.setgENDER(genderDesc);
		certificateParameters.setPanno(panno);		
		panDto.setCertificateParameters(certificateParameters);
		
		ConsentArtifact consentArtifact = new ConsentArtifact();
		
		Consent consent = new Consent();
		consent.setConsentId(UUID.randomUUID().toString());
		SimpleDateFormat date = new SimpleDateFormat(MainetConstants.DATE_AND_TIME);		 
		consent.setTimestamp(date.format(new Date()));
		
		DataConsumer dataConsumer = new DataConsumer();
		dataConsumer.setId("string");
		consent.setDataConsumer(dataConsumer);
		
		DataProvider dataProvider = new DataProvider();
		dataProvider.setId("string");
		consent.setDataProvider(dataProvider);
		
		Purpose purpose = new Purpose();
		purpose.setDescription("string");
		consent.setPurpose(purpose);
		
		User user = new User();
		user.setIdType("string");
		user.setIdNumber("string");
		user.setEmail(email);
		user.setMobile(mobile);
		consent.setUser(user);
		
		Data data = new Data();
		data.setId("string");
		consent.setData(data);
		
		Permission permission = new Permission();
		DateRange dateRange = new DateRange();
		Frequency frequency = new Frequency();
		
		permission.setAccess("string");
		
		dateRange.setFrom(date.format(new Date()));
		dateRange.setMyto(date.format(new Date()));
		permission.setDateRange(dateRange);
		
		frequency.setUnit("string");
		frequency.setValue(0);
		frequency.setRepeats(0);
		permission.setFrequency(frequency);
		consent.setPermission(permission);
		
		consentArtifact.setConsent(consent);
		
		Signature signature = new Signature();
		signature.setSignature("string");
		consentArtifact.setSignature(signature);
				
		panDto.setConsentArtifact(consentArtifact);
		Map<String, String> verifyPanCardResponse = apiSetuService.verifyPanCard(panDto);
		if(verifyPanCardResponse.get("result")=="success"){
			object.put("result", "success");
		}else{
			String errMsg = ApplicationSession.getInstance().getMessage("pan.api.error.msg");
			object.put("result", errMsg);	    
		}
		return object;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "fetchCinData")
    public ModelAndView fetchCinData(final HttpServletRequest request, final HttpServletResponse httpServletResponse) { 
    	getModel().bind(request);
    	ModelAndView mv = null;
    	final DeveloperRegistrationFormModel model = getModel(); 
    	MCACompany fetchCompanyDetails = apiSetuService.fetchCompanyDetails(model.getDeveloperRegistrationDTO().getCinNo());
    	if(fetchCompanyDetails.isApiStatus()){
    		model.getDeveloperRegistrationDTO().setCompanyName(fetchCompanyDetails.getCompanyName());
    		model.getDeveloperRegistrationDTO().setDateOfIncorporation(fetchCompanyDetails.getIncorporationDate());
    		model.getDeveloperRegistrationDTO().setRegisteredAddress(fetchCompanyDetails.getRegisteredAddress());
    		model.getDeveloperRegistrationDTO().setEmail(fetchCompanyDetails.getEmail());
    		model.getDeveloperRegistrationDTO().setMobileNo(Long.valueOf(fetchCompanyDetails.getRegisteredContactNo()));
    		model.getDeveloperRegistrationDTO().setCompanyDetailsAPIFlag(MainetConstants.FlagY);
    	}else if(!fetchCompanyDetails.isApiStatus() && fetchCompanyDetails.getErrorMsg()!=null){
    		model.addValidationError(fetchCompanyDetails.getErrorMsg());
    	}
    	
    	DirectorDetails fetchDirectorDetails = apiSetuService.fetchDirectorDetails(model.getDeveloperRegistrationDTO().getCinNo());
    	List<DeveloperDirectorInfoDTO> developerDirectorDetailsDTOList = new ArrayList<>();
    	if(fetchDirectorDetails.getDirectorInfo()!=null && !fetchDirectorDetails.getDirectorInfo().isEmpty()){
    		fetchDirectorDetails.getDirectorInfo().forEach(directorList->{
    			DeveloperDirectorInfoDTO directorDetail = new DeveloperDirectorInfoDTO();
    			directorDetail.setDinNumber(directorList.getDin());
    			directorDetail.setDirectorContactNumber(Long.valueOf(directorList.getContactNumber()));
    			directorDetail.setDirectorName(directorList.getName());
    			developerDirectorDetailsDTOList.add(directorDetail);
    		});
    		
    		model.getDeveloperRegistrationDTO().setDeveloperDirectorDetailsDTOList(developerDirectorDetailsDTOList);
    		model.getDeveloperRegistrationDTO().setDeveloperDirectorInfoDTOList(developerDirectorDetailsDTOList);
    	}
        mv = new ModelAndView("applicantInformationValidn", MainetConstants.FORM_NAME, model);
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		return mv;
    }
	
	private void getCheckListDoc(DeveloperRegistrationFormModel model){
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.CHECKLIST_DEVELOPER_REGISTRATION_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			populateCheckListModel(model, checkListModel2);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
			checklistReqDto.setDataModel(checkListModel2);
			try {
				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
						|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
						List<DocumentDetailsVO> checkListList = Collections.emptyList();
						checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

						long cnt = 1;
						for (final DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
						if ((checkListList != null) && !checkListList.isEmpty()) {
							model.setCheckList(checkListList);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.info("Checklist not found..!");
				model.addValidationError(getApplicationSession().getMessage("Checklist not found..!"));
			}	
		}
		
	}
}
