package com.abm.mainet.water.ui.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.NewWaterConnectionFormModel;

@Controller
@RequestMapping("/NewWaterConnectionForm.html")
public class NewWaterConnectionFormController extends AbstractFormController<NewWaterConnectionFormModel>
		implements Serializable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NewWaterConnectionFormController.class);

	private static final long serialVersionUID = 1773269167981579902L;

	@Autowired
	IPortalServiceMasterService iPortalService;

	@Autowired
	private IWaterBRMSService checklistService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	private INewWaterConnectionService iNewWaterConnectionService;

	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
    private transient IServiceMasterService serviceMaster;
	
	@Autowired
	IEmployeeService employeeService;

	/*
	 * @Autowired DesignationService designationService;
	 */
	@Autowired
	WaterCommonService waterCommonService;
	

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		getModel().setCommonHelpDocs("NewWaterConnectionForm.html");
		
		final NewWaterConnectionFormModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		setCommonFields(model);
		 LookUp propNoOptional = null;
			try {
				propNoOptional = CommonMasterUtility.getValueFromPrefixLookUp("PNO", "WEV", UserSession.getCurrent().getOrganisation());
			}catch (Exception exception) {
				LOGGER.error("No prefix found for WEV(PNO)");
			}
			if(propNoOptional != null) {
				model.setPropNoOptionalFlag(propNoOptional.getOtherField());
			}else {
				model.setPropNoOptionalFlag(MainetConstants.FlagN);
			}
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {
				model.setSudaEnv(MainetConstants.FlagY);
			}
		
			
		ModelAndView mv = new ModelAndView("NewWaterConnectionForm", MainetConstants.FORM_NAME, getModel());
		
		
		String authentication="";
		
		if(null!=str && null!=ns && null!=ULBID && null!=ULBDistrict){
			EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
			LOGGER.info("Encrypted Key: " + str);
			authentication=encryptDecrypt.authentication(str,ns);	
			
			if(!authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)){
				model.getReqDTO().setTenant(authentication);
			}
		}
		
		
		Employee emp = UserSession.getCurrent().getEmployee();
		if(emp.getEmploginname().equalsIgnoreCase("NOUSER") && !authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE) && StringUtils.isNotEmpty(authentication)) {
			mv= new ModelAndView("NewWaterConnectionForm", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("NewWaterConnectionForm", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}
		return mv;
	}

	private void setCommonFields(final NewWaterConnectionFormModel model) {
		final TbCsmrInfoDTO dto = model.getCsmrInfo();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		final PortalService service = serviceMaster.findShortCodeByOrgId(MainetConstants.NewWaterServiceConstants.WNC,
				orgId);
		model.setServiceMaster(service);
		final Long deptId = service.getPsmDpDeptid();
		model.setDeptId(deptId);
		if (service != null) {
			model.setServiceId(service.getServiceId());
			model.getReqDTO().setServiceId(service.getServiceId());
		}
		model.setServiceName(service.getServiceName());
		
		List<PlumberDTO> allPlumberList = null;
		
		
		
		allPlumberList = iNewWaterConnectionService.getListofplumber(orgId);
		/*
		 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.APP_NAME.SUDA)){ model.setIsBillingSame(MainetConstants.NO);
		 * }
		 */
		 
		
		  if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		  MainetConstants.APP_NAME.SUDA)){ 
			  model.setIsBillingSame(MainetConstants.NO);
			  allPlumberList = allPlumberList.stream() 
					  .filter(x -> MainetConstants.NewWaterServiceConstants.ANY_PLUMBER.equals(x.getPlumberFName().trim())) 
					  .collect(Collectors.toList()); 
			 
			  if(allPlumberList != null && allPlumberList.size() > 0 ) {
				  model.getCsmrInfo().setPlumId(allPlumberList.get(0).getPlumberId());
				  model.getReqDTO().setPlumberName(allPlumberList.get(0).getPlumberFName()); 
			  }
			  
		}
			 
		
		/*
		 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.APP_NAME.SUDA)){ PlumberDTO anyPlumber = new PlumberDTO();
		 * anyPlumber.setOrgid(orgId); anyPlumber.setPlumberFName("Any Available");
		 * anyPlumber.setPlumberId((long) 0); allPlumberList.add(anyPlumber);
		 * plumberList.add(anyPlumber); model.getCsmrInfo().setPlumId(0l); } else {
		 * model.setPlumberList(iNewWaterConnectionService.getListofplumber(orgId)); }
		 */
		  
		  model.setPlumberList(allPlumberList);
		  
		/*
		 * PortalService dsg = serviceMaster.findShortCodeByOrgId("PLM",orgId);
		 * 
		 * PlumberDTO master = null;
		 * if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.APP_NAME.SUDA)){ if (dsg != null) {
		 * 
		 * List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getId(),
		 * orgId); if (!empList.isEmpty()) { for (final Object empObj[] : empList) {
		 * master = new PlumberDTO();
		 * master.setPlumberId(Long.valueOf(empObj[0].toString()));
		 * master.setPlumberFName(empObj[1].toString());
		 * master.setPlumberMName(empObj[2].toString());
		 * master.setPlumberLName(empObj[3].toString()); plumberList.add(master); }
		 * 
		 * } } }
		 * 
		 * model.setPlumberList(plumberList);
		 */
		
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
			this.getModel().getCsmrInfo().setBplFlag(MainetConstants.FlagN);
			 LookUp propDuesCheck = null;
				try {
					propDuesCheck = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.NewWaterServiceConstants.CHECK_PROPERTY_DUES, UserSession.getCurrent().getOrganisation());
					if(propDuesCheck!=null) {
						if(MainetConstants.NewWaterServiceConstants.AT_TIME_OF_APPLICATION.equals(propDuesCheck.getLookUpCode()) || MainetConstants.NewWaterServiceConstants.BOTH.equals(propDuesCheck.getLookUpCode())){
							this.getModel().setPropDuesCheck(true);
						}
					}
				}catch (Exception exception) {
					LOGGER.error("No prefix found for CPD(Check Property Dues)");
				}
				
		}
	}

	private void setApplicantDetail(final Employee emp, final NewWaterConnectionFormModel model) {
		model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
		model.setHiddenFname(emp.getEmpname());
		model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpMName());
		model.setHiddenMname(emp.getEmpMName());
		model.getApplicantDetailDto().setApplicantLastName(emp.getEmpLName());
		model.setHiddenLname(emp.getEmpLName());
		model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
		model.setHiddenMobile(emp.getEmpmobno());
		model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
		model.setHiddenEmail(emp.getEmpemail());
		model.getApplicantDetailDto().setApplicantTitle(emp.getTitle());
		model.setHiddenTitle(emp.getTitle());
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((emp.getEmpGender() != null) && !emp.getEmpGender().isEmpty()) {
				if (lookUp.getLookUpCode().equals(emp.getEmpGender())) {
					model.getApplicantDetailDto().setGender(String.valueOf(lookUp.getLookUpId()));
					break;
				}
			}
		}
		model.setHiddenGender(model.getApplicantDetailDto().getGender());
		if (emp.getPincode() != null) {
			model.getApplicantDetailDto().setPinCode(emp.getPincode());
		}
	}

	@RequestMapping(params = "ShowViewForm", method = RequestMethod.POST)
	public ModelAndView ShowViewForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.convertFileToByteString(docs);
		model.setCheckListForPreview(fileUpload.getUploadedDocForPreview(docs));
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setDocumentList(docs);
		reqDTO.setCsmrInfo(model.getCsmrInfo());
		reqDTO.setAadhaarNo(model.getCsmrInfo().getAadharBilling());
		reqDTO.setApplicantDTO(model.getApplicantDetailDto());
		List<PlumberDTO> listofplumber = iNewWaterConnectionService.getListofplumber(UserSession.getCurrent().getOrganisation().getOrgid());
		Optional<PlumberDTO> isPlumPresent = listofplumber.stream().filter(plum->plum.getPlumberId().equals(reqDTO.getCsmrInfo().getPlumId())).findFirst();
		if(isPlumPresent!=null && isPlumPresent.isPresent()) {
			String plumName = (isPlumPresent.get().getPlumberFName()!=null ? isPlumPresent.get().getPlumberFName() : "" ) +
//					(isPlumPresent.get().getPlumberMName()!=null ? isPlumPresent.get().getPlumberMName() : "" ) +
					(isPlumPresent.get().getPlumberLName()!=null ? isPlumPresent.get().getPlumberLName() : "" );

			reqDTO.setPlumberName(isPlumPresent.get().getPlumberFullName());
			
		}
		setRequestApplicantDetails(model);
		setUpdateFields(reqDTO);
		getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		setCsmrInfoApplicantDetails(reqDTO);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA) 
				|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : lookUps) {
				if ((model.getCsmrInfo().getCsGender() != null) && model.getCsmrInfo().getCsGender() != 0l) {
					if (lookUp.getLookUpId() == model.getCsmrInfo().getCsGender()) {
						model.getCsmrInfo().setGenderDesc(lookUp.getLookUpDesc());
						break;
					}
				}

			}
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {
				final List<LookUp> lookUpDis = CommonMasterUtility.getLookUps("DIS", UserSession.getCurrent().getOrganisation());
				for (final LookUp lookUp : lookUpDis) {
					if (model.getCsmrInfo().getCsDistrict() != 0l) {
						if (lookUp.getLookUpId() == model.getCsmrInfo().getCsDistrict()) {
							model.getCsmrInfo().setCsDistrictDesc(lookUp.getLookUpDesc());
							break;
						}
					}
	
				}
			}
			final List<LookUp> wardLookUps = CommonMasterUtility.getLevelData("WWZ", 1, UserSession.getCurrent().getOrganisation());// getNextLevelData(, 1, .getOrgid());
			for (final LookUp lookUp : wardLookUps) {
				if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
					if (lookUp.getLookUpId() ==model.getCsmrInfo().getCodDwzid1()) {
						model.getReqDTO().setWard(lookUp.getDescLangFirst());
						break;
					}
				}

			}
			
			  
			final List<LookUp> categoryLookUps = CommonMasterUtility.getLevelData("CCG", 1,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : categoryLookUps) {
				if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
					if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory1()) {
						model.getReqDTO().setCategory(lookUp.getDescLangFirst());
						if("T".equals(lookUp.getLookUpCode()) )
							model.getCsmrInfo().setTypeOfApplication(lookUp.getLookUpCode());
						break;
					}
				}
			  
			 }
			
			final List<LookUp> subCategoryLookUps = CommonMasterUtility.getLevelData("CCG", 2,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : subCategoryLookUps) {
				if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
					if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory2()) {
						model.getReqDTO().setSubCategory(lookUp.getDescLangFirst());
						break;
					}
				}

			}
			
			final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getLevelData("TRF", 1,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : tarrifCategoryLookUps) {
				if ((model.getCsmrInfo().getTrmGroup1() != null) && model.getCsmrInfo().getTrmGroup1() != 0l) {
					if (lookUp.getLookUpId() == model.getCsmrInfo().getTrmGroup1()) {
						model.getReqDTO().setTarrifCategory(lookUp.getDescLangFirst());
						break;
					}
				}
				//model.getReqDTO().s(model.getCsmrInfo().getPlumId());
			}
			
			final List<LookUp> connsectionSizeLookUps = CommonMasterUtility.getLookUps("CSZ", UserSession.getCurrent().getOrganisation());
			for (final LookUp lookUp : connsectionSizeLookUps) {
				if ((model.getCsmrInfo().getCsCcnsize() != null) && model.getCsmrInfo().getCsCcnsize() != 0l) {
					if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcnsize()) {
						model.getReqDTO().setConnSize(lookUp.getLookUpDesc());
						break;
					}
				}

			}
			
		}
		if (model.validateInputs()) {
			model.saveForm();
			mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME, getModel());
		}
		else {
			mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = "EditApplicationForm", method = RequestMethod.POST)
	public ModelAndView EditApplicationForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		return defaultMyResult();
	}

	@RequestMapping(params = "SaveAndViewApplication", method = RequestMethod.POST)
	public ModelAndView SaveAndViewApplication(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME,
				getModel());
		final NewWaterConnectionFormModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final ProvisionalCertificateDTO provisional = new ProvisionalCertificateDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());
			reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
			reqDTO.setBlockNo(model.getApplicantDetailDto().getBlockName());
			reqDTO.setPlumberName(null);
			reqDTO.setGender(CommonMasterUtility
            .getNonHierarchicalLookUpObject(reqDTO.getCsmrInfo().getCsGender(), UserSession.getCurrent().getOrganisation())
            .getLookUpCode());;
            provisional.setAddress1(getModel().getCsmrInfo().getCsAdd());
            provisional.setApplicationFee(getModel().getCharges());
            
			final NewWaterConnectionResponseDTO outPutObject = iNewWaterConnectionService
					.saveOrUpdateNewConnection(reqDTO);
			if (outPutObject != null) {
				if ((outPutObject.getStatus() != null)
						&& outPutObject.getStatus().equals(MainetConstants.NewWaterServiceConstants.SUCCESS)) {
					model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
					provisional.setApplicationNo(outPutObject.getApplicationNo());
					final ApplicationPortalMaster applicationMaster = saveApplcationMaster(reqDTO.getServiceId(),
							outPutObject.getApplicationNo(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					iPortalService.saveApplicationMaster(applicationMaster, model.getCharges(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					if ((model.getFree() != null)
							&& model.getFree().equals(MainetConstants.NewWaterServiceConstants.NO)) {
						model.saveForm();
					}
					long duration = 0;
					if (model.getServiceMaster().getSlaDays() != null) {
						duration = model.getServiceMaster().getSlaDays();
					}
					model.setServiceDuration(
							LocalDate.now().plusDays(duration).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					model.setDistrictDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdDis(), org)
							.getLookUpDesc());
					model.setStateDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdState(), org)
							.getLookUpDesc());
					model.setTalukaDesc(
							CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdId(), org).getLookUpDesc());
					mv = new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME,
							getModel());
					
					if(null!=reqDTO.getTenant() && null!=model.getResponseDTO().getApplicationNo()){
						try{
						LOGGER.error("Aaple Sarkar: " + reqDTO.getTenant() + "ApplicationId:" + model.getResponseDTO().getApplicationNo());
						String request= reqDTO.getTenant();
						
						LOGGER.error("Before Updating Status: " + request);
						
						String replace = request.replace("appId", model.getResponseDTO().getApplicationNo().toString());
						
						String replace1= replace.replace("appStatus", MainetConstants.Common_Constant.NUMBER.THREE);
						
						String replace2= replace1.replace("remark", "Dept Scrutiny");
						
						LOGGER.error("Final Request: " + replace2);
			
						EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
						
						encryptDecrypt.getUpdateStatus(replace2);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				} else {
					if (!outPutObject.getErrorList().isEmpty()) {
						for (final String msg : outPutObject.getErrorList()) {
							model.addValidationError(msg);
						}
					} else {
						return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
					}
				}
			} else {
				return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
				}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		ModelAndView mv = null;
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		setApplicantDetails();
		// iNewWaterConnectionService.findNoOfDaysCalculation(model.getCsmrInfo(), org);
		if (model.validateInputs()) {
			// [START] BRMS call initialize model
			final WSRequestDTO dto = new WSRequestDTO();
			dto.setModelName(MainetConstants.MODEL_NAME);
			final WSResponseDTO response = iCommonBRMSService.initializeModel(dto);
			List<DocumentDetailsVO> checkListList = new ArrayList<>();
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
				final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
				final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
				final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
				populateCheckListModel(model, checkListModel2);
				checkListList = iCommonBRMSService.getChecklist(checkListModel2);
				if (checkListList != null && !checkListList.isEmpty()) {
					Long fileSerialNo = 1L;
					for (final DocumentDetailsVO docSr : checkListList) {
						docSr.setDocumentSerialNo(fileSerialNo);
						fileSerialNo++;
					}
					model.setCheckList(checkListList);
					mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
				} else {

					final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
					reqDTO.setCsmrInfo(model.getCsmrInfo());
					reqDTO.setApplicantDTO(model.getApplicantDetailDto());
					setRequestApplicantDetails(model);
					setUpdateFields(reqDTO);
					if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {
						final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : lookUps) {
							if ((model.getCsmrInfo().getCsGender() != null) && model.getCsmrInfo().getCsGender() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getCsGender()) {
									model.getCsmrInfo().setGenderDesc(lookUp.getLookUpDesc());
									break;
								}
							}

						}
						final List<LookUp> lookUpDis = CommonMasterUtility.getLookUps("DIS", UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : lookUpDis) {
							if (model.getCsmrInfo().getCsDistrict() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getCsDistrict()) {
									model.getCsmrInfo().setCsDistrictDesc(lookUp.getLookUpDesc());
									break;
								}
							}			
						}
						final List<LookUp> wardLookUps = CommonMasterUtility.getLevelData("WWZ", 1, UserSession.getCurrent().getOrganisation());// getNextLevelData(, 1, .getOrgid());
						for (final LookUp lookUp : wardLookUps) {
							if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
								if (lookUp.getLookUpId() ==model.getCsmrInfo().getCodDwzid1()) {
									model.getReqDTO().setWard(lookUp.getDescLangFirst());
									break;
								}
							}
						}												  
						final List<LookUp> categoryLookUps = CommonMasterUtility.getLevelData("CCG", 1,
								UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : categoryLookUps) {
							if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory1()) {
									model.getReqDTO().setCategory(lookUp.getDescLangFirst());
									if("T".equals(lookUp.getLookUpCode()) )
										model.getCsmrInfo().setTypeOfApplication(lookUp.getLookUpCode());
									break;
								}
							}						  
						 }					
						final List<LookUp> subCategoryLookUps = CommonMasterUtility.getLevelData("CCG", 2,
								UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : subCategoryLookUps) {
							if ((model.getCsmrInfo().getCodDwzid1() != null) && model.getCsmrInfo().getCodDwzid1() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcncategory2()) {
									model.getReqDTO().setSubCategory(lookUp.getDescLangFirst());
									break;
								}
							}
						}					
						final List<LookUp> tarrifCategoryLookUps = CommonMasterUtility.getLevelData("TRF", 1,
								UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : tarrifCategoryLookUps) {
							if ((model.getCsmrInfo().getTrmGroup1() != null) && model.getCsmrInfo().getTrmGroup1() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getTrmGroup1()) {
									model.getReqDTO().setTarrifCategory(lookUp.getDescLangFirst());
									break;
								}
							}
						}					
						final List<LookUp> connsectionSizeLookUps = CommonMasterUtility.getLookUps("CSZ", UserSession.getCurrent().getOrganisation());
						for (final LookUp lookUp : connsectionSizeLookUps) {
							if ((model.getCsmrInfo().getCsCcnsize() != null) && model.getCsmrInfo().getCsCcnsize() != 0l) {
								if (lookUp.getLookUpId() == model.getCsmrInfo().getCsCcnsize()) {
									model.getReqDTO().setConnSize(lookUp.getLookUpDesc());
									break;
								}
							}
						}			
					}
					getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
					setCsmrInfoApplicantDetails(reqDTO);
					mv = new ModelAndView("NewWaterConnectionApplicationFormView", MainetConstants.FORM_NAME,
							getModel());

				}
				// checklist done
				// Charges Start
				final WSResponseDTO res = checklistService.getApplicableTaxes(WaterRateMaster,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.NewWaterServiceConstants.WNC);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
					if (!res.isFree()) {
						final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							WaterRateMaster master1 = (WaterRateMaster) rate;
							master1 = populateChargeModel(model, master1);
							requiredCHarges.add(master1);
						}
						final List<ChargeDetailDTO> detailDTOs = checklistService.getApplicableCharges(requiredCHarges);
						model.setFree(MainetConstants.NewWaterServiceConstants.NO);
						model.getReqDTO().setFree(false);
						model.setChargesInfo(detailDTOs);
						model.setCharges((chargesToPay(detailDTOs)));
						setChargeMap(model, detailDTOs);
						model.getOfflineDTO().setAmountToShow(model.getCharges());
					} else {
						model.setFree(MainetConstants.NewWaterServiceConstants.YES);
						model.getReqDTO().setFree(true);
						model.getReqDTO().setCharges(0.0d);
					}
				} else {
					mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
				}
			}
			// [END]
			else {
				logger.error("Exception found in initializing Charges and Checklist: ");
				mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
		} else {
			mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void setChargeMap(final NewWaterConnectionFormModel model, final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();

		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setPayMode(model.getOfflineDTO().getOnlineOfflineCheck());
		reqDTO.setCharges(model.getCharges());
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.convertFileToByteString(docs);
		reqDTO.setDocumentList(docs);

		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			setRequestApplicantDetails(model);
			reqDTO.setCsmrInfo(model.getCsmrInfo());
			reqDTO.getCsmrInfo().setLinkDetails(reqDTO.getLinkDetails());
			setCsmrInfoApplicantDetails(reqDTO);
			if (reqDTO.getCsmrInfo().getOwnerList() != null) {
				if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(MainetConstants.BLANK)) {
					reqDTO.getCsmrInfo().setOwnerList(null);
				}
			}
			if (reqDTO.getCsmrInfo().getLinkDetails() != null) {
				if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
					reqDTO.getCsmrInfo().setLinkDetails(null);
				}
			}
			setUpdateFields(reqDTO);
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());
			
			reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
			final NewWaterConnectionResponseDTO outPutObject = iNewWaterConnectionService
					.saveOrUpdateNewConnection(reqDTO);
			if (outPutObject != null) {
				if ((outPutObject.getStatus() != null)
						&& outPutObject.getStatus().equals(MainetConstants.NewWaterServiceConstants.SUCCESS)) {
					model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
					final ApplicationPortalMaster applicationMaster = saveApplcationMaster(reqDTO.getServiceId(),
							outPutObject.getApplicationNo(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					iPortalService.saveApplicationMaster(applicationMaster, model.getCharges(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					if ((model.getFree() != null)
							&& model.getFree().equals(MainetConstants.NewWaterServiceConstants.NO)) {
						if (model.saveForm()) {
							final CommonChallanDTO offline = model.getOfflineDTO();
							if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck()
									.equals(MainetConstants.NewWaterServiceConstants.NO)) {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("continue.forchallan")));
							} else {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("continue.forpayment")));
							}
						}
					} else {

						return jsonResult(
								JsonViewObject.successResult(getApplicationSession().getMessage("water.free.msg1") + " "
										+ outPutObject.getApplicationNo() + MainetConstants.WHITE_SPACE
										+ getApplicationSession().getMessage("water.free.msg2")));
					}
					if(null!=reqDTO.getTenant()){
					LOGGER.error("Aaple Sarkar: " + reqDTO.getTenant());
					}
					
				} else {
					if (!outPutObject.getErrorList().isEmpty()) {
						for (final String msg : outPutObject.getErrorList()) {
							model.addValidationError(msg);
						}
					} else {
						return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);

					}

				}
			}
		}
		mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void setRequestApplicantDetails(final NewWaterConnectionFormModel model) {
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final ApplicantDetailDTO appDTO = model.getApplicantDetailDto();
		reqDTO.setApplicationType(model.getCsmrInfo().getApplicantType());
		reqDTO.setmName(appDTO.getApplicantMiddleName());
		reqDTO.setfName(appDTO.getApplicantFirstName());
		reqDTO.setlName(appDTO.getApplicantLastName());
		reqDTO.setEmail(appDTO.getEmailId());
		reqDTO.setMobileNo(appDTO.getMobileNo());
		// reqDTO.setTitleId(appDTO.getApplicantTitle());
		reqDTO.setBldgName(appDTO.getBuildingName());
		reqDTO.setRoadName(appDTO.getRoadName());
		reqDTO.setAreaName(appDTO.getAreaName());
		if ((appDTO.getPinCode() != null) && !appDTO.getPinCode().isEmpty()) {
			reqDTO.setPincodeNo(Long.valueOf(appDTO.getPinCode()));
		}
		reqDTO.setWing(appDTO.getWing());
		reqDTO.setBplNo(appDTO.getBplNo());
		reqDTO.setDeptId(model.getDeptId());
		reqDTO.setFloor(appDTO.getFloorNo());
		reqDTO.setWardNo(appDTO.getDwzid2());
		reqDTO.setZoneNo(appDTO.getDwzid1());
		reqDTO.setCityName(appDTO.getVillageTownSub());
		reqDTO.setBlockName(appDTO.getBlockName());
		reqDTO.setHouseComplexName(appDTO.getHousingComplexName());
		reqDTO.setFlatBuildingNo(appDTO.getFlatBuildingNo());
		reqDTO.setBillingAdharNo(appDTO.getAadharNo());
		/*
		 * if ((appDTO.getAadharNo() != null) &&
		 * !appDTO.getAadharNo().equals(MainetConstants.BLANK)) {
		 * reqDTO.setUid(Long.valueOf(appDTO.getAadharNo())); }
		 */
		if (reqDTO.getCsmrInfo().getOwnerList() != null) {
			if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(0)) {
				reqDTO.getCsmrInfo().setOwnerList(null);
			}
		}
		if (reqDTO.getCsmrInfo().getLinkDetails() != null) {
			if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
				reqDTO.getCsmrInfo().setLinkDetails(null);
			}
		}

	}

	private void setUpdateFields(final NewWaterConnectionReqDTO reqDTO) {
		final UserSession session = UserSession.getCurrent();
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
		final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
		reqDTO.setUserId(session.getEmployee().getEmpId());
		reqDTO.setLangId((long) session.getLanguageId());
		reqDTO.setUpdatedBy(session.getEmployee().getEmpId());
		reqDTO.setOrgId(session.getOrganisation().getOrgid());
		reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		dto.setOrgId(reqDTO.getOrgId());
		dto.setUserId(session.getEmployee().getEmpId());
		dto.setLangId(session.getLanguageId());
		dto.setLgIpMac(reqDTO.getLgIpMac());
		dto.setUpdatedDate(new Date());
		if ((dto.getLinkDetails() != null) && !dto.getLinkDetails().isEmpty()) {
			for (final TbKLinkCcnDTO link : dto.getLinkDetails()) {
				link.setOrgIds(reqDTO.getOrgId());
				link.setUserIds(reqDTO.getUserId());
				link.setLangId(dto.getLangId());
				link.setLgIpMac(reqDTO.getLgIpMac());
				link.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
				tempLinkList.add(link);
			}
			dto.setLinkDetails(tempLinkList);
		}
		if ((dto.getOwnerList() != null) && !dto.getOwnerList().isEmpty()) {
			for (final AdditionalOwnerInfoDTO owner : dto.getOwnerList()) {
				owner.setOrgid(reqDTO.getOrgId());
				owner.setUserId(reqDTO.getUserId());
				owner.setLangId(reqDTO.getLangId());
				owner.setLgIpMac(reqDTO.getLgIpMac());
				owner.setLmoddate(new Date());
				owner.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
				tempOwnerList.add(owner);
			}
			dto.setOwnerList(tempOwnerList);
		}
	}

	private void setConsumerDetails(ApplicantDetailDTO applicantDTO, TbCsmrInfoDTO tbCsmrInfoDTO) {
		// tbCsmrInfoDTO.setCsTitle(applicantDTO.getApplicantTitle().toString());
		tbCsmrInfoDTO.setCsName(applicantDTO.getApplicantFirstName());
		tbCsmrInfoDTO.setCsMname(applicantDTO.getApplicantMiddleName());
		tbCsmrInfoDTO.setCsLname(applicantDTO.getApplicantLastName());
		tbCsmrInfoDTO.setCsAdd(applicantDTO.getAreaName());
		tbCsmrInfoDTO.setCsBldplt(applicantDTO.getBuildingName());
		tbCsmrInfoDTO.setCsLanear(applicantDTO.getFlatBuildingNo());
		tbCsmrInfoDTO.setCsRdcross(applicantDTO.getRoadName());
		tbCsmrInfoDTO.setCsCcityName(applicantDTO.getVillageTownSub());
		if (applicantDTO.getPinCode() != null && !applicantDTO.getPinCode().isEmpty())
			tbCsmrInfoDTO.setCsCpinCode(Long.valueOf(applicantDTO.getPinCode()));
	}

	private void setBillingDetails(final TbCsmrInfoDTO tbCsmrInfoDTO) {
		tbCsmrInfoDTO.setCsBadd(tbCsmrInfoDTO.getCsAdd());
		tbCsmrInfoDTO.setCsBbldplt(tbCsmrInfoDTO.getCsBldplt());
		tbCsmrInfoDTO.setCsBlanear(tbCsmrInfoDTO.getCsLanear());
		tbCsmrInfoDTO.setCsBrdcross(tbCsmrInfoDTO.getCsRdcross());
		tbCsmrInfoDTO.setCsBcityName(tbCsmrInfoDTO.getCsCcityName());
		tbCsmrInfoDTO.setBpincode(tbCsmrInfoDTO.getCsCpinCode().toString());
	}

	private void setCsmrInfoApplicantDetails(final NewWaterConnectionReqDTO reqDTO) {
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final ApplicantDetailDTO applicantDTO = reqDTO.getApplicantDTO();

		if (reqDTO.getIsConsumer() != null && !reqDTO.getIsConsumer().isEmpty()) {
			setConsumerDetails(applicantDTO, dto);
		}
		if (reqDTO.getIsBillingAddressSame() != null && !reqDTO.getIsBillingAddressSame().isEmpty()) {
			setBillingDetails(dto);
		}
		if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA))
		{
		dto.setCsName(applicantDTO.getApplicantFirstName());
		reqDTO.setfName(applicantDTO.getApplicantFirstName());
		dto.setCsLname(applicantDTO.getApplicantLastName());
		dto.setCsMname(applicantDTO.getApplicantMiddleName());
		dto.setCsAdd(applicantDTO.getAreaName());
		dto.setCsBldplt((applicantDTO.getBuildingName()));
		dto.setCsFlatno(applicantDTO.getFloorNo());
		dto.setCsRdcross(applicantDTO.getRoadName());
		dto.setCsContactno(applicantDTO.getMobileNo());
		dto.setCsLanear(applicantDTO.getAreaName());
		}
		if (dto.getBplFlag().equals("Y"))
			applicantDTO.setBplNo(dto.getBplNo());
		// dto.setCsTitle(String.valueOf(applicantDTO.getApplicantTitle()));
		/*
		 * reqDTO.setTitleId(applicantDTO.getApplicantTitle()); if
		 * ((applicantDTO.getAadharNo() != null) &&
		 * !applicantDTO.getAadharNo().isEmpty()) {
		 * dto.setCsUid(Long.valueOf(applicantDTO.getAadharNo())); }
		 */
		dto.setCsApldate(new Date());
		/*
		 * dto.setCodDwzid1(applicantDTO.getDwzid1());
		 * dto.setCodDwzid2(applicantDTO.getDwzid2());
		 * dto.setCodDwzid3(applicantDTO.getDwzid3());
		 * dto.setCodDwzid4(applicantDTO.getDwzid4());
		 * dto.setCodDwzid5(applicantDTO.getDwzid5());
		 * dto.setBplNo(applicantDTO.getBplNo());
		 */
		dto.setBplFlag(applicantDTO.getIsBPL());
	}

	private void populateCheckListModel(final NewWaterConnectionFormModel model, final CheckListModel checklistModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		checklistModel.setOrgId(model.getOrgId());
		checklistModel.setServiceCode(MainetConstants.NewWaterServiceConstants.WNC);
		final TbCsmrInfoDTO data = model.getCsmrInfo();
		checklistModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		LookUp lookUp = null;
		try {
			lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.CCG,
					MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV - CCG ", e);
		}
		if (lookUp != null && StringUtils.equals(MainetConstants.FlagY, lookUp.getOtherField())) {
			if (data.getCsCcncategory1() != null) {
				checklistModel.setUsageSubtype1(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory1(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory2() != null) {
				checklistModel.setUsageSubtype2(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory2(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory3() != null) {
				checklistModel.setUsageSubtype3(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory3(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory4() != null) {
				checklistModel.setUsageSubtype4(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory4(), org).getDescLangFirst());
			}
			if (data.getCsCcncategory5() != null) {
				checklistModel.setUsageSubtype5(
						CommonMasterUtility.getHierarchicalLookUp(data.getCsCcncategory5(), org).getDescLangFirst());
			}
		} else {
			if (data.getTrmGroup1() != null) {
				checklistModel.setUsageSubtype1(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1()).getDescLangFirst());
			}
			if (data.getTrmGroup2() != null) {
				checklistModel.setUsageSubtype2(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2()).getDescLangFirst());
			}
			if (data.getTrmGroup3() != null) {
				checklistModel.setUsageSubtype3(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3()).getDescLangFirst());
			}
			if (data.getTrmGroup4() != null) {
				checklistModel.setUsageSubtype4(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4()).getDescLangFirst());
			}
			if (data.getTrmGroup5() != null) {
				checklistModel.setUsageSubtype5(
						CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5()).getDescLangFirst());
			}
			/*
			 * final LookUp applicantType =
			 * CommonMasterUtility.getNonHierarchicalLookUpObject(data.getApplicantType());
			 */

			checklistModel.setDeptCode(MainetConstants.DeptCode.WATER);
			if ((model.getReqDTO() != null) && (model.getReqDTO().getExistingConsumerNumber() == null)) {
				checklistModel.setIsExistingConnectionOrConsumerNo(MainetConstants.NewWaterServiceConstants.NO);

			} else {
				checklistModel.setIsExistingConnectionOrConsumerNo(MainetConstants.NewWaterServiceConstants.YES);

			}
			if ((model.getReqDTO() != null) && (model.getReqDTO().getPropertyNo() == null)) {
				checklistModel.setIsExistingProperty(MainetConstants.NewWaterServiceConstants.NO);
			} else {
				checklistModel.setIsExistingProperty(MainetConstants.NewWaterServiceConstants.YES);
			}
		}
	}

	private WaterRateMaster populateChargeModel(final NewWaterConnectionFormModel model,
			final WaterRateMaster chargeModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final TbCsmrInfoDTO data = model.getCsmrInfo();
		chargeModel.setOrgId(model.getOrgId());
		chargeModel.setServiceCode(MainetConstants.NewWaterServiceConstants.WNC);
		chargeModel.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		chargeModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		chargeModel.setRateStartDate(new Date().getTime());
		if (data.getCsCcnsize() != null) {
			chargeModel.setConnectionSize(Double.valueOf(
					CommonMasterUtility.getNonHierarchicalLookUpObject(data.getCsCcnsize()).getDescLangFirst()));
		}
		if (data.getTrmGroup1() != null) {
			chargeModel.setUsageSubtype1(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1()).getDescLangFirst());
		}
		if (data.getTrmGroup2() != null) {
			chargeModel.setUsageSubtype2(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2()).getDescLangFirst());
		}
		if (data.getTrmGroup3() != null) {
			chargeModel.setUsageSubtype3(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3()).getDescLangFirst());
		}
		if (data.getTrmGroup4() != null) {
			chargeModel.setUsageSubtype4(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4()).getDescLangFirst());
		}
		if (data.getTrmGroup5() != null) {
			chargeModel.setUsageSubtype5(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5()).getDescLangFirst());
		}
		if (data.getNoOfFamilies() != null) {
			chargeModel.setNoOfFamilies(data.getNoOfFamilies().intValue());
		}
		if (data.getCsTaxPayerFlag() == null || data.getCsTaxPayerFlag().isEmpty()) {
			chargeModel.setTaxPayer(MainetConstants.IsDeleted.NOT_DELETE);
		} else {
			chargeModel.setTaxPayer(data.getCsTaxPayerFlag());
		}
		return chargeModel;
	}

	public ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {
		/*
		 * final PortalService portalMaster = iPortalService.getService(serviceId,
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		// calculateDate(portalMaster, applicationMaster, documentListSize);
		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	@RequestMapping(params = "clearCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView clearCheckListAndChrages(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final NewWaterConnectionFormModel model = getModel();

		model.setFree(MainetConstants.NewWaterServiceConstants.OPEN);
		model.setChargesInfo(null);
		model.setCharges(0.0d);
		model.getChargesMap().clear();
		model.getOfflineDTO().setAmountToShow(0.0d);
		model.getReqDTO().setFree(true);
		model.getReqDTO().setCharges(0.0d);
		model.getCheckList().clear();
		model.getReqDTO().getDocumentList().clear();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final ModelAndView mv = new ModelAndView("NewWaterConnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest request) {
		bindModel(request);
		NewWaterConnectionFormModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		TbCsmrInfoDTO propInfoDTO = iNewWaterConnectionService.getPropertyDetailsByPropertyNumber(reqDTO);
		String respMsg = "";
		if (propInfoDTO.getCsOname() != null) {
			infoDTO.setCsOname(propInfoDTO.getCsOname());
			infoDTO.setCsOcontactno(propInfoDTO.getCsOcontactno());
			infoDTO.setCsOEmail(propInfoDTO.getCsOEmail());
			infoDTO.setOpincode(propInfoDTO.getOpincode());
			infoDTO.setCsOadd(propInfoDTO.getCsOadd());
			// infoDTO.setCsOrdcross(propInfoDTO.getCsOrdcross());
			infoDTO.setPropertyUsageType(propInfoDTO.getPropertyUsageType());
			if(propInfoDTO.getArv()!= null){
				if (propInfoDTO.getArv() != 0) {
	                infoDTO.setArv(propInfoDTO.getArv());
	            }
			}		
			if (propInfoDTO.getCsOGender() != null && propInfoDTO.getCsOGender() != 0l) {
				infoDTO.setCsOGender(propInfoDTO.getCsOGender());
			}
			reqDTO.setApplicantDTO(new ApplicantDetailDTO());
			infoDTO.setPropertyNo(propInfoDTO.getPropertyNo());
			infoDTO.setTotalOutsatandingAmt(propInfoDTO.getTotalOutsatandingAmt());
			if (infoDTO.getTotalOutsatandingAmt() > 0) {
				model.setPropOutStanding("Y");
			}
		} else {
			respMsg = ApplicationSession.getInstance().getMessage("water.dataentry.validation.property.not.found");
			return new ModelAndView(new MappingJackson2JsonView(), "errMsg", respMsg);
		}
		return defaultMyResult();
	}

	/**
	 * Get Connection Details
	 * 
	 * @param connectionNo
	 * @return TbCsmrInfoDTO
	 *//*
		 * @ResponseBody
		 * 
		 * @RequestMapping(params = "getConnectionDetails", method = RequestMethod.POST)
		 * public TbCsmrInfoDTO getConnectionDetails(@RequestParam("ccnNo") String
		 * ccnNo) { try { TbCsmrInfoDTO infoDto =
		 * waterCommonService.fetchConnectionDetailsByConnNo(ccnNo,
		 * UserSession.getCurrent().getOrganisation().getOrgid(),
		 * MainetConstants.FlagA); List<TbBillMas> bill =
		 * billMasterService.getBillMasterListByUniqueIdentifier(infoDto.getCsIdn(),
		 * UserSession.getCurrent().getOrganisation().getOrgid()); if (!bill.isEmpty())
		 * { infoDto.setCcnOutStandingAmt(Double.toString(bill.get(bill.size() -
		 * 1).getBmTotalOutstanding())); } else { infoDto.setCcnOutStandingAmt("0"); }
		 * return infoDto; } catch (Exception e) { return new TbCsmrInfoDTO(); }
		 * 
		 * }
		 */

	private void setApplicantDetails() {
		NewWaterConnectionFormModel model = this.getModel();
		UserSession session = UserSession.getCurrent();
		NewWaterConnectionReqDTO connectionDTO = model.getReqDTO();
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		connectionDTO.setUserId(session.getEmployee().getEmpId());
		connectionDTO.setOrgId(session.getOrganisation().getOrgid());
		connectionDTO.setLangId((long) session.getLanguageId());
		connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

		if (model.getIsConsumerSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsConsumer("Y");
			infoDTO.setCsName(infoDTO.getCsOname());
			if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
				infoDTO.setCsOGender(infoDTO.getCsGender());
			}
			infoDTO.setCsContactno(infoDTO.getCsOcontactno());
			if(infoDTO.getCsOEmail() != null)
			infoDTO.setCsEmail(infoDTO.getCsOEmail());
			if(infoDTO.getCsOadd()!=null)
			infoDTO.setCsAdd(infoDTO.getCsOadd());
			if (infoDTO.getOpincode() != null) {
				infoDTO.setCsCpinCode(infoDTO.getCsCpinCode());
			}
			if(infoDTO.getCsName()!=null)
				infoDTO.setCsName(infoDTO.getCsName());
		}
		if (model.getIsBillingSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsBillingAddressSame("Y");
			if(infoDTO.getCsBadd()!=null) {
			infoDTO.setCsBadd(infoDTO.getCsBadd());
			}
			if(infoDTO.getContactNoBilling()!=null) {
			infoDTO.setContactNoBilling(infoDTO.getContactNoBilling());
			}
			if (infoDTO.getCsCpinCode() != null) {
				infoDTO.setBpincode(infoDTO.getCsCpinCode().toString());
			}
			if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
				infoDTO.setCsOGender(infoDTO.getCsGender());
			}
		}

		connectionDTO.setCityName(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		connectionDTO.setRoadName(infoDTO.getCsAdd());
		if (infoDTO.getCsCpinCode() != null) {
			connectionDTO.setPinCode(infoDTO.getCsCpinCode());
			connectionDTO.setPincodeNo(infoDTO.getCsCpinCode());
		}
		connectionDTO.setAreaName(infoDTO.getCsAdd());
		connectionDTO.setBldgName(infoDTO.getCsBadd());
		connectionDTO.setBlockName(infoDTO.getCsAdd());
		connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsBadd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		appDTO.setIsBPL(infoDTO.getBplFlag());
		appDTO.setBplNo(infoDTO.getBplNo());
		
		 if (infoDTO.getBplFlag().equals("Y")) 
			 appDTO.setBplNo(infoDTO.getBplNo());
		 
		// Need To Check
		appDTO.setAadharNo(connectionDTO.getApplicantDTO().getAadharNo());
		if (infoDTO.getCsCpinCode() != null) {
			appDTO.setPinCode(infoDTO.getCsCpinCode().toString());
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((infoDTO.getCsOGender() != null) && infoDTO.getCsOGender() != 0l) {
				if (lookUp.getLookUpId() == infoDTO.getCsOGender()) {
					appDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		appDTO.setAreaName(infoDTO.getCsAdd());
		model.setApplicantDetailDto(appDTO);
		connectionDTO.setCsmrInfo(infoDTO);

	}

	/**
	 * Get Connection Details
	 * 
	 * @param connectionNo
	 * @return TbCsmrInfoDTO
	 */
	@ResponseBody
	@RequestMapping(params = "getConnectionDetails", method = RequestMethod.POST)
	public TbCsmrInfoDTO getConnectionDetails(@RequestParam("ccnNo") String ccnNo) {
		try {
			TbCsmrInfoDTO infoDto = iNewWaterConnectionService.fetchConnectionDetailsByConnNo(ccnNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
			return infoDto;
		} catch (Exception e) {
			return new TbCsmrInfoDTO();
		}

	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);

		NewWaterConnectionFormModel model = this.getModel();
		try {
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			NewWaterConnectionReqDTO requestDto = new NewWaterConnectionReqDTO();
			requestDto.setOrgId(orgId);
			requestDto.setApplicationId(appId);
			final NewWaterConnectionReqDTO responseDto = iNewWaterConnectionService.getApplicationData(requestDto);

			if (responseDto != null) {
				model.setReqDTO(responseDto);
				model.setApplicantDetailDto(responseDto.getApplicantDTO());
				model.setCsmrInfo(responseDto.getCsmrInfo());
				model.getReqDTO().setPropertyNo(responseDto.getPropertyNo());
				model.querySearchResults(appId,MainetConstants.BLANK);
				if(CollectionUtils.isEmpty(model.getAttachmentList())) {
					model.querySearchResults(appId,MainetConstants.FlagY);
				}
				List<DocumentDetailsVO> checkList =iCommonBRMSService.getChecklistDocument(String.valueOf(appId), orgId, MainetConstants.FlagY);
			model.setCheckList(checkList);
			}
		} catch (Exception exception) {
			throw new FrameworkException(exception);
		}
		return new ModelAndView("newWaterConnectionFormDashboardView", MainetConstants.FORM_NAME, getModel());
	}
	
	}
