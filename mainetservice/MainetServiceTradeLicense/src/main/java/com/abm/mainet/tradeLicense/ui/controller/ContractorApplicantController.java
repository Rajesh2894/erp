package com.abm.mainet.tradeLicense.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbAcVendormaster;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;

import com.abm.mainet.common.integration.dms.service.IFileUploadService;

import com.abm.mainet.common.integration.dto.RequestDTO;

import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.ui.validator.VendorMasterValidator;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.tradeLicense.service.IContractorApplicantService;
import com.abm.mainet.tradeLicense.ui.model.ContractorApplicantModel;



@Controller
@RequestMapping("/ContractorApplicantDetail.html")
public class ContractorApplicantController extends AbstractFormController<ContractorApplicantModel>{
	
	  	protected static final String MODE = "mode";
	    protected static final String MODE_CREATE = "create";
	    protected static final String MODE_UPDATE = "update";
	    protected static final String MODE_EDIT = "edit";
	    protected static final String MODE_VIEW = "view";
	  
	    private static final String JSP_FORM = "ContractorApplicant";
	    private static final String SAVE_ACTION_CREATE = "ContractorApplicantDetail.html?create";
	    private static final String SAVE_ACTION_UPDATE = "ContractorApplicantDetail.html?update";
	    private static final String LISTOFTBACFUNCTIONMASTERITEMS = "listOfTbAcFunctionMasterItems";
	    

	    protected static final String SAVE_ACTION = "saveAction";
	 @Resource
	 private TbAcVendormasterService tbAcVendormasterService;
	 
	 @Resource
	 private TbOrganisationService tbOrganisationService;
	 
	 @Resource
	 private TbBankmasterService banksMasterService;
	 
	 @Resource
	 private IEmployeeService employeeService;
	 
	 @Resource
	 private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	 
	 @Autowired
	 private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;
	 
	 
	 @Autowired
		ICFCApplicationMasterService cfcApplicationService;
	 
	 @Autowired
	 private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	 
	 @Autowired
	 private DepartmentJpaRepository deptRepo;
	 
	 @Autowired
	 private ApplicationService applicationService;
	 
	 @Resource
	 private AccountFunctionMasterService tbAcFunctionMasterService;
	 
	 @Autowired
	 private IFileUploadService fileUpload;
	 
	 @Autowired
	 private ServiceMasterService serviceMaster;
	 
	 @Autowired
	 private CommonService commonService;
	 
	 @Autowired
	 private IContractorApplicantService contractorApplicantService;
	 
	 @Autowired
		private IWorkFlowTypeService workFlowTypeService;
	
	 List<LookUp> venderType;
	 
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, HttpServletRequest request) throws Exception{
		sessionCleanup(request);
		
		//ModelAndView mv = 
			//mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		final TbAcVendormaster tbAcVendormaster = new TbAcVendormaster();
		this.getModel().setContractorLicense(true);
        populateModel(model, tbAcVendormaster, FormMode.CREATE);
      //  this.getModel().setTbAcVendormaster(tbAcVendormaster);
        model.addAttribute("tbAcVendormaster", tbAcVendormaster);
		return new ModelAndView(JSP_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params ="saveForm", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody long saveForm(@Valid final TbAcVendormaster tbAcVendormaster1,final BindingResult bindingResult,
			final Model model, HttpServletRequest request) {
		getModel().bind(request);
		final VendorMasterValidator validator = new VendorMasterValidator();
      	validator.setTbAcVendormasterService(tbAcVendormasterService);
      	ContractorApplicantModel contractorApplicantModel = getModel();
      	RequestDTO requestDto = contractorApplicantModel.getRequestDto();
      	TbAcVendormaster tbAcVendormaster = contractorApplicantModel.getTbAcVendormaster();
      	tbAcVendormaster.setSliMode("L");
      	validator.validate(tbAcVendormaster, bindingResult);
        
        Employee employee = UserSession.getCurrent().getEmployee();
        if(!bindingResult.hasErrors()) {
        	  final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
              Organisation defaultOrg = null;
              if (isDafaultOrgExist) {
                  defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
              } else {
                  defaultOrg = UserSession.getCurrent().getOrganisation();
              }
              final LookUp secndVendorType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                      PrefixConstants.TbAcVendormaster.VD,
                      PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
                      UserSession.getCurrent().getLanguageId(), defaultOrg);
              final Organisation org = UserSession.getCurrent().getOrganisation();
              final int langId = UserSession.getCurrent().getLanguageId();
              tbAcVendormaster.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
              tbAcVendormaster.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
              tbAcVendormaster.setLgIpMac(employee.getLgIpMac());
              
              tbAcVendormaster.setLangId((long) UserSession.getCurrent().getLanguageId());
              if(tbAcVendormaster.getVendorClass() !=  null) {
              	tbAcVendormaster.setVendorClassName(CommonMasterUtility
                          .getNonHierarchicalLookUpObjectByPrefix(tbAcVendormaster.getVendorClass(), tbAcVendormaster.getOrgid(),
                                  PrefixConstants.TbAcVendormaster.VEC)
                          .getDescLangFirst());
              }                            
              final TbAcVendormaster tbAcVendormasterCreated = tbAcVendormasterService.create(tbAcVendormaster,
                      venderType, secndVendorType.getLookUpId(), defaultOrg, org, langId);
             requestDto.setVendorCode(String.valueOf(tbAcVendormasterCreated.getVmVendorid())); 
            
          }
		
		
		//ContractorApplicantModel contractorApplicantModel = getModel();
		
		UserSession session = UserSession.getCurrent();
		/* contractor applicant */
		//TbAcVendormaster tbAcVendormaster = contractorApplicantModel.getTbAcVendormaster();
		
		
		
		requestDto.setOrgId(employee.getOrganisation().getOrgid());
		requestDto.setLangId((long)session.getLanguageId());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("VLP", UserSession.getCurrent().getOrganisation().getOrgid());
		final Long deptId = service.getTbDepartment().getDpDeptid();
		requestDto.setDeptId(deptId);
		requestDto.setServiceId(service.getSmServiceId());
		
		/*
		 * TbCfcApplicationMst TbCfcApplicationMst =
		 * contractorApplicantModel.getTbCfcApplicationMst(); 
		 * 
		 * TbCfcApplicationMst.setUpdatedBy(employee.getUpdatedBy());
		 * TbCfcApplicationMst.setUpdatedDate(employee.getUpdatedDate());
		 * TbCfcApplicationMst.setOrgid(employee.getOrganisation().getOrgid());
		 */ 
		
		long appId = applicationService.createApplication(requestDto);
		requestDto.setApplicationId(appId);
		// Workflow initialization
		  if (service.getSmFeesSchedule() == 0 ) {
			  initializeWorkFlowForFreeService(requestDto); 
		  }
		 

		//ModelAndView mv = new ModelAndView("ContractorApplicant", MainetConstants.FORM_NAME, getModel());
		return appId;
	}
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
	public ModelAndView viewApproval(final Model model,final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
			@RequestParam("workflowId") Long workflowId, @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName) throws Exception {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		//final ContractorApplicantModel model1 = this.getModel();
		//this.getModel().getWorkflowActionDto().setReferenceId(String.valueOf(applicationId));
		this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
		this.getModel().getWorkflowActionDto().setTaskId(taskId);
		this.getModel().getWorkflowActionDto().setTaskName(taskName); 
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
		long serviceIdByApplicationId = cfcApplicationService.getServiceIdByApplicationId(applicationId, orgId);
		
		RequestDTO requestDto = contractorApplicantService.getContractorApplicant(applicationId, orgId);
		this.getModel().setTaskId(taskId);
		TbAcVendormaster  tbAcVendormaster= tbAcVendormasterService.findById(Long.valueOf(requestDto.getVendorCode()), orgId);
		populateModel(model, tbAcVendormaster, FormMode.CREATE);
		this.getModel().setRequestDto(requestDto);
		this.getModel().setTbAcVendormaster(tbAcVendormaster);
		ModelAndView mv = new ModelAndView("ContractorApplicantApproval", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
		//return new ModelAndView(JSP_FORM, MainetConstants.FORM_NAME, getModel());
	}

	
	private void initializeWorkFlowForFreeService(RequestDTO requestDto) {
		// TODO Auto-generated method stub
		boolean checkList = false;
		iCFCApplicationMasterDAO.updateCFCApplicationMasterPaymentStatus(requestDto.getApplicationId() ,
				MainetConstants.PAY_STATUS.PAID, requestDto.getOrgId());
		ServiceMaster service = serviceMaster.getServiceMasterByShortCode("VLP", UserSession.getCurrent().getOrganisation().getOrgid());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		
		 applicantDto.setApplicantFirstName(requestDto.getfName());
         applicantDto.setServiceId(requestDto.getServiceId());
         applicantDto.setDepartmentId(requestDto.getDeptId());
         applicantDto.setMobileNo(requestDto.getMobileNo());
         applicantDto.setUserId(requestDto.getUserId());
		
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicationMetaData.setApplicationId(requestDto.getApplicationId());
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		
		if (service.getSmFeesSchedule().longValue() == 0) {
			applicationMetaData.setIsLoiApplicable(false);
		} else {
			applicationMetaData.setIsLoiApplicable(true);
		}
		 try {
             commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
         } catch (Exception e) {
             throw new FrameworkException("Exception occured while calling workflow");
         }
		
		
		
	}

	 private void populateModel(final Model model, final TbAcVendormaster tbAcVendormaster, final FormMode formMode)
	            throws Exception {
	        // --- Main entity

	        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
	        Organisation defaultOrg = null;
	        if (isDafaultOrgExist) {
	            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
	        } else {
	            defaultOrg = UserSession.getCurrent().getOrganisation();
	        }
	        venderType = CommonMasterUtility.getLookUps(PrefixConstants.VNT, defaultOrg);
	        if (formMode == FormMode.CREATE) {
	            if (venderType != null) {
	                for (final LookUp vendorTypeLookUp : venderType) {
	                    if ((vendorTypeLookUp.getDefaultVal() != null) && !vendorTypeLookUp.getDefaultVal().isEmpty()) {
	                        if (vendorTypeLookUp.getDefaultVal().equals(MainetConstants.MENU.Y)) {
	                            if (tbAcVendormaster.getCpdVendortype() == null) {
	                                tbAcVendormaster.setCpdVendortype(vendorTypeLookUp.getLookUpId());
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        final List<LookUp> vendorStatus = CommonMasterUtility.getLookUps(PrefixConstants.VST, defaultOrg);
	        if (formMode == FormMode.CREATE) {
	            if (vendorStatus != null) {
	                for (final LookUp vendorSubTypeLookUp : vendorStatus) {
	                    if ((vendorSubTypeLookUp.getDefaultVal() != null)
	                            && !vendorSubTypeLookUp.getDefaultVal().isEmpty()) {
	                        if (vendorSubTypeLookUp.getDefaultVal().equals(MainetConstants.MENU.Y)) {
	                            if (tbAcVendormaster.getCpdVendorSubType() == null) {
	                                tbAcVendormaster.setCpdVendorSubType(vendorSubTypeLookUp.getLookUpId());
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        final Map<Long, String> bankMap = new HashMap<>();
	        final List<Object[]> blist = banksMasterService.findActiveBankList();
	        for (final Object[] obj : blist) {
	            bankMap.put((Long) obj[0],
	                    obj[1] + MainetConstants.SEPARATOR + obj[2] + MainetConstants.SEPARATOR + obj[3]);
	        }

	        String sliMode = null;
	        sliMode = tbAcVendormasterService.getCpdMode();

	        final List<Employee> emplist = employeeService
	                .findEmpList(UserSession.getCurrent().getOrganisation().getOrgid());
	        if (formMode == FormMode.CREATE) {
	            final LookUp vendorStatusAcIn = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
	                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_SEQ_DEPARTMENT_TYPE, PrefixConstants.VSS,
	                    UserSession.getCurrent().getLanguageId(), defaultOrg);
	            final List<LookUp> vendorStAc = new ArrayList<>();
	            vendorStAc.add(vendorStatusAcIn);
	            model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS, vendorStAc);
	            model.addAttribute(MODE, MODE_CREATE);
	            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);

	            boolean primaryDefaultFlag = false;
	            if (isDafaultOrgExist) {
	                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
	                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
	                        MainetConstants.MASTER.Y);
	            } else {
	                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
	                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
	            }
	            Organisation defaultorg = null;
	            if (isDafaultOrgExist && primaryDefaultFlag) {
	                defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
	            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
	                defaultorg = UserSession.getCurrent().getOrganisation();
	            } else {
	                defaultorg = UserSession.getCurrent().getOrganisation();
	            }

	            final int langId = UserSession.getCurrent().getLanguageId();
	            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
	                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
	                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId,
	                    UserSession.getCurrent().getOrganisation());
	            final Long lookUpStatusId = statusLookup.getLookUpId();
	            final Long orgid = defaultorg.getOrgid();
	            final LookUp vendorLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
	                    PrefixConstants.TbAcVendormaster.VN, PrefixConstants.TbAcVendormaster.SAM, langId, defaultorg);
	            final Long cpdVendortype = vendorLookup.getLookUpId();
	            Long cpdVendorSubType = null;
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && tbAcVendormaster.getCpdVendortype()==null) {
					final Organisation organisation = UserSession.getCurrent().getOrganisation();
					Long deptid = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
					String deptCode = deptRepo.getDeptCode(deptid);
					LookUp lookup =null;
					if (deptCode.equalsIgnoreCase("RL")) {
						 lookup = CommonMasterUtility.getValueFromPrefixLookUp("T", PrefixConstants.VNT,
								organisation);
					} else {
						 lookup = CommonMasterUtility.getValueFromPrefixLookUp("C", PrefixConstants.VNT,
								organisation);
					}
					if(lookup.getLookUpId() != 0L) {
						tbAcVendormaster.setCpdVendortype(lookup.getLookUpId());
						cpdVendorSubType = tbAcVendormaster.getCpdVendortype();
					}
				}else {
					  cpdVendorSubType = tbAcVendormaster.getCpdVendortype();
				}
	            if ((cpdVendortype != null) && (cpdVendorSubType != null)) {

	                if (sliMode != null && !sliMode.isEmpty()) {
	                    if (sliMode.equals("L")) {
	                        final Map<Long, String> venderPrimaryHead = tbAcPrimaryheadMasterService
	                                .getVendorTypeWisePrimaryAcHead(cpdVendortype, cpdVendorSubType, lookUpStatusId, orgid);
	                        model.addAttribute(LISTOFTBACFUNCTIONMASTERITEMS, venderPrimaryHead);
	                    }
	                }
	            }

	        } else if (formMode == FormMode.UPDATE) {
	            final List<LookUp> vendorStatusAcIn = CommonMasterUtility.getLookUps(PrefixConstants.VSS, defaultOrg);
	            model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS, vendorStatusAcIn);
	            model.addAttribute(MODE, MODE_UPDATE);
	            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
	        }
	        model.addAttribute(MainetConstants.TbAcChequebookleafMas.EMP_LIST, emplist);
	        model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_TYPE, venderType);
	        model.addAttribute(MainetConstants.TbAcVendormaster.VENDOR_STATUS_IN, vendorStatus);

	        final Organisation organisation = UserSession.getCurrent().getOrganisation();
	        final Long orgid = organisation.getOrgid();
	        final int langId = UserSession.getCurrent().getLanguageId();

	        boolean functionDefaultFlag = false;
	        if (isDafaultOrgExist) {
	            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
	                    PrefixConstants.FUNCTION_CPD_VALUE,
	                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
	        } else {
	            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
	                    PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
	                    MainetConstants.MASTER.Y);
	        }
	        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
	                UserSession.getCurrent().getOrganisation());
	        for (final LookUp lookUp : accountTypeLevel) {
	            if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
	                if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
	                    if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {

	                        if (sliMode != null && !sliMode.isEmpty()) {
	                            if (sliMode.equals("L")) {
	                                if (isDafaultOrgExist && functionDefaultFlag) {
	                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
	                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(
	                                                    ApplicationSession.getInstance().getSuperUserOrganization()
	                                                            .getOrgid(),
	                                                    ApplicationSession.getInstance().getSuperUserOrganization(),
	                                                    langId));
	                                } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
	                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
	                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid,
	                                                    organisation, langId));
	                                } else {
	                                    model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
	                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid,
	                                                    organisation, langId));
	                                }
	                                model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
	                                        MainetConstants.MASTER.Y);
	                            }
	                        }
	                    }
	                }
	            }
	        }

	        if (sliMode != null && !sliMode.isEmpty()) {
	            tbAcVendormaster.setSliMode(sliMode);
	        }
	        model.addAttribute(MainetConstants.TbAcVendormaster.SLI_MODE, sliMode);

	        final List<TbAcVendormaster> list = tbAcVendormasterService
	                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
	        for (final TbAcVendormaster tbAcVendormasters : list) {
	            tbAcVendormasters.setVenderCodeAndName(tbAcVendormasters.getVmVendorcode() + MainetConstants.SEPARATOR
	                    + tbAcVendormasters.getVmVendorname());
	        }

	        model.addAttribute("vendorClass", CommonMasterUtility.getListLookup(PrefixConstants.TbAcVendormaster.VEC,
	                UserSession.getCurrent().getOrganisation()));

	        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_ENTITY_NAME, tbAcVendormaster);
	        model.addAttribute(MainetConstants.VENDOR_MASTER.CUST_BANKLIST, bankMap);
	      //  model.addAttribute("SudaEnv", Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA));

	    }
	 
		@ResponseBody
		@RequestMapping(params = "saveContractorApplicantApprovalData", method = RequestMethod.POST)
		public Map<String, Object> saveAuditParaApp(HttpServletRequest request) {
			getModel().bind(request);
			this.getModel().saveContractorApplicantApproval(this.getModel().getRequestDto().getApplicationId(),
					UserSession.getCurrent().getOrganisation().getOrgid(),
					this.getModel().getWorkflowActionDto().getTaskName());
			Map<String, Object> object = new LinkedHashMap<String, Object>();
			object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
			object.put("wfStatus", this.getModel().getRequestDto().getStatus());
			return object;
		}
	
}
