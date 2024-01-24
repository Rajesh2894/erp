package com.abm.mainet.council.ui.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.dto.CouncilYearDetDto;
import com.abm.mainet.council.service.CouncilWorkFlowService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilProposalMasterModel;

/**
 * @author aarti.paan
 * @since 6th May 2019
 */
@Controller
@RequestMapping(MainetConstants.Council.Proposal.PROPOSAL_URL)

public class CouncilProposalMasterController extends AbstractFormController<CouncilProposalMasterModel> {

    private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";

    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private ICouncilProposalMasterService iCouncilProposalMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Resource
	private AccountFieldMasterService tbAcFieldMasterService;
    
    @Autowired
    private  IEmployeeService employeeService ;
    
    @Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    
    @Autowired
	private TbOrganisationService tbOrganisationService;
    
    @Resource
	private ILocationMasService locMasService;
    
    @Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
    
    @Autowired
    private TbDepartmentService tbDepartmentService;
   

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        CouncilProposalMasterModel model = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long employeeDept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
        String employeeDeptCode = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
        model.setCommonHelpDocs("CouncilProposalMaster.html");
        model.setLookupListLevel1(
                CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, orgId));
        model.setDepartmentsList(iTbDepartmentService.findMappedDepartments(orgId));

        ServiceMaster serviceForFinancial = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.Council.Proposal.SERVICE_COUNCIL_PROPOSAL, orgId);

        LookUp workflowLookUpForFinancial = CommonMasterUtility.getNonHierarchicalLookUpObject(
                serviceForFinancial.getSmProcessId(), UserSession.getCurrent().getOrganisation());
        ServiceMaster serviceForNonFinancial = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceMasterByShortCode("CPN", orgId);
        LookUp workflowLookUpForNonFinancial = CommonMasterUtility.getNonHierarchicalLookUpObject(
                serviceForNonFinancial.getSmProcessId(), UserSession.getCurrent().getOrganisation());
        List<CouncilProposalMasterDto> couProposalMasterDtoList = null;
        LookUp proposalDeptWise = null;
        
        try {
        	proposalDeptWise = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.ENV.VDWP,
                    MainetConstants.ENV, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        }catch(Exception e) {
        	
        }
        
        if(proposalDeptWise!=null && MainetConstants.FlagY.equalsIgnoreCase(proposalDeptWise.getOtherField())){
        	
        	if	(employeeDeptCode.equalsIgnoreCase(MainetConstants.Council.COUNCIL_MANAGEMENT)) {
        	
	        	couProposalMasterDtoList = iCouncilProposalMasterService
	                    .searchProposalMasterData(null, null, null, null, null, orgId, null, UserSession.getCurrent().getLanguageId(),null);
	        	
        	}else {
        		couProposalMasterDtoList = iCouncilProposalMasterService
	                    .searchProposalMasterData(employeeDept, null, null, null, null, orgId, null, UserSession.getCurrent().getLanguageId(),null);
        	}
        }
        else {
        	couProposalMasterDtoList = iCouncilProposalMasterService
                    .searchProposalMasterData(null, null, null, null, null, orgId, null, UserSession.getCurrent().getLanguageId(),null);
        }
       
        for (CouncilProposalMasterDto proposal : couProposalMasterDtoList) {
            if (proposal.getProposalStatus().equals(MainetConstants.FlagA)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_APPROVED);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.approved"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagP)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_PENDING);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.pending"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagD)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_DRAFT);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.draft"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagR)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_REJECTED);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.rejected"));
            }

            if (StringUtils.equals(proposal.getProposalType(), MainetConstants.FlagF)) {
                if (StringUtils.equals(workflowLookUpForFinancial.getLookUpCode(),
                        MainetConstants.CommonConstants.NA)) {
                    proposal.setApprovalFlag(MainetConstants.FlagN);
                } else {
                    proposal.setApprovalFlag(MainetConstants.FlagY);
                }
            }

            if (StringUtils.equals(proposal.getProposalType(), MainetConstants.FlagN)) {
                if (StringUtils.equals(workflowLookUpForNonFinancial.getLookUpCode(),
                        MainetConstants.CommonConstants.NA)) {
                    proposal.setApprovalFlag(MainetConstants.FlagN);
                } else {
                    proposal.setApprovalFlag(MainetConstants.FlagY);
                }
            }
        }
        model.setCouProposalMasterDtoList(couProposalMasterDtoList);
        // getting Financial Year and budget Head code for account integration

        Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
        model.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    model.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }

        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
            model.setCpdMode(defaultVal.getLookUpCode());
            this.getModel().setCpdMode(defaultVal.getLookUpCode());
            if (model.getCpdMode().equals(MainetConstants.FlagL)) {

                model.setBudgetList(ApplicationContextProvider.getApplicationContext()
                        .getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(org.getOrgid()));

                // comment on 06/08/2019 By ISRAT
                /*
                 * final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                 * PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                 * Long activeStatusId = lookUpFieldStatus.getLookUpId();
                 * model.setBudgetList(ApplicationContextProvider.getApplicationContext(). getBean(SecondaryheadMasterService.
                 * class) .getActiveSacHeadCodeDeatails(UserSession.getCurrent().getOrganisation(). getOrgid(), activeStatusId));
                 */
            }
        } else {
            model.setCpdMode(null);
        }
        return defaultResult();
    }
    
    

    // Add Proposal
    @RequestMapping(params = MainetConstants.Council.Proposal.ADD_PROPOSAL, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView addproposal(@RequestParam("isMOMProposal") String isMOMProposal,
            @RequestParam("meetingId") Long meetingId, @RequestParam("agendaId") Long agendaId,
            final HttpServletRequest request,final Model modelm) {
        getModel().bind(request);
        CouncilProposalMasterModel model = this.getModel();

        // changes done as per userstory#29823
        model.setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));

        /*
         * // user DEPT and DEPT id model.getCouProposalMasterDto()
         * .setProposalDepId(UserSession.getCurrent().getEmployee().getTbDepartment(). getDpDeptid());
         * model.getCouProposalMasterDto() .setProposalDeptName(UserSession.getCurrent().getEmployee().getTbDepartment()
         * .getDpDeptdesc());
         */
        model.getCouProposalMasterDto().setIsMOMProposal(isMOMProposal);
        model.setSaveMode(MainetConstants.Council.CREATE);
        if (StringUtils.equals(model.getCouProposalMasterDto().getIsMOMProposal(), MainetConstants.FlagY)) {
            model.getCouProposalMasterDto().setAgendaId(agendaId);
        }
        Map<Long, String> fieldList = tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid());
        modelm.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS, fieldList);
        model.setFieldList(fieldList);
        populateFund();
		
        return new ModelAndView(MainetConstants.Council.Proposal.ADD_PROPOSAL_FORM, MainetConstants.FORM_NAME, model);
    }

    // Search Proposal
    /* @ResponseBody */
    @RequestMapping(params = MainetConstants.Council.Proposal.SEARCH_COUNCIL_PROPOSAL, method = RequestMethod.POST)
    public ModelAndView getCouncilProposalList(
            @RequestParam(MainetConstants.Council.Proposal.DEPTID) final Long proposalDepId,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSALNO) final String proposalNo,
            @RequestParam(MainetConstants.Council.Proposal.FROM_DATE) final Date fromDate,
            @RequestParam(MainetConstants.Council.Proposal.TO_DATE) final Date toDate,
            @RequestParam(MainetConstants.Council.Proposal.Proposal_type) final String type,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_STATUS) final String proposalStatus,
            @RequestParam(MainetConstants.Council.Proposal.ELECTIONWZID) final Long wardId,
            final HttpServletRequest request) {
        getModel().bind(request);
        CouncilProposalMasterModel model = this.getModel();
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        ServiceMaster serviceForFinancial = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.Council.Proposal.SERVICE_COUNCIL_PROPOSAL, orgId);

        LookUp workflowLookUpForFinancial = CommonMasterUtility.getNonHierarchicalLookUpObject(
                serviceForFinancial.getSmProcessId(), UserSession.getCurrent().getOrganisation());
        ServiceMaster serviceForNonFinancial = ApplicationContextProvider.getApplicationContext()
                .getBean(ServiceMasterService.class).getServiceMasterByShortCode("CPN", orgId);
        LookUp workflowLookUpForNonFinancial = CommonMasterUtility.getNonHierarchicalLookUpObject(
                serviceForNonFinancial.getSmProcessId(), UserSession.getCurrent().getOrganisation());

        List<CouncilProposalMasterDto> councilProposalDto = iCouncilProposalMasterService
                .searchProposalMasterData(proposalDepId, proposalNo, fromDate, toDate, proposalStatus, orgId, wardId,
                        UserSession.getCurrent().getLanguageId(),type);
        for (CouncilProposalMasterDto proposal : councilProposalDto) {
            if (proposal.getProposalStatus().equals(MainetConstants.FlagA)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_APPROVED);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.approved"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagP)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_PENDING);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.pending"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagD)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_DRAFT);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.draft"));
            }
            if (proposal.getProposalStatus().equals(MainetConstants.FlagR)) {
                proposal.setProposalStatus(MainetConstants.TASK_STATUS_REJECTED);
                proposal.setProposalStatusDesc(ApplicationSession.getInstance().getMessage("council.rejected"));
            }

            if (StringUtils.equals(proposal.getProposalType(), MainetConstants.FlagF)) {
                if (StringUtils.equals(workflowLookUpForFinancial.getLookUpCode(),
                        MainetConstants.CommonConstants.NA)) {
                    proposal.setApprovalFlag(MainetConstants.FlagN);
                } else {
                    proposal.setApprovalFlag(MainetConstants.FlagY);
                }
            }

            if (StringUtils.equals(proposal.getProposalType(), MainetConstants.FlagN)) {
                if (StringUtils.equals(workflowLookUpForNonFinancial.getLookUpCode(),
                        MainetConstants.CommonConstants.NA)) {
                    proposal.setApprovalFlag(MainetConstants.FlagN);
                } else {
                    proposal.setApprovalFlag(MainetConstants.FlagY);
                }
            }
        }
        this.getModel().setCouProposalMasterDtoList(councilProposalDto);
        // model.setCouncilProposalDto(councilProposalDto);
        return new ModelAndView("CouncilProposalMasterSearch", MainetConstants.FORM_NAME, this.getModel());
    }

    // Edit Proposal
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Proposal.EDIT_PROPOSAL, method = RequestMethod.POST)
    public ModelAndView editCouncilProposalMasterData(
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_ID) final Long proposalId,
            HttpServletRequest request,final Model modelm) {
        this.getModel().bind(request);
        this.getModel().setSaveMode(MainetConstants.Council.EDIT);
        CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);
        this.getModel().setCouProposalMasterDto(proposalMasterDto);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        if(proposalMasterDto.getProposalDetails() != null)
        	this.getModel().setResolutionComments(proposalMasterDto.getProposalDetails());
        }
        CouncilProposalMasterModel model = this.getModel();
        // changes done as per userstory#29823
        model.setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));

        /*
         * // user DEPT and DEPT id this.getModel().getCouProposalMasterDto()
         * .setProposalDepId(UserSession.getCurrent().getEmployee().getTbDepartment(). getDpDeptid());
         * this.getModel().getCouProposalMasterDto() .setProposalDeptName(UserSession.getCurrent().getEmployee().getTbDepartment()
         * .getDpDeptdesc());
         */
        /*
         * // get attached document final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
         * .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
         * MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + proposalId);
         * this.getModel().setAttachDocsList(attachDocs);
         */

        // get attached document
        addAttachedDoc(proposalId);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        LookUp lookup=null;
     	Organisation org = UserSession.getCurrent().getOrganisation();
     	Map<Long, String> budgetMap = new HashMap<>();
     	List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
    	 VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
         budgetHeadDTO.setOrgId(org.getOrgid());
         budgetHeadDTO.setFieldId(proposalMasterDto.getFiledId());
     	int langId = UserSession.getCurrent().getLanguageId();
         try {
         	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                     AccountPrefix.AIC.toString(),langId,org);
         }catch(Exception e) {
         	//return budgetMap;
         }
         
         if(lookup!=null && lookup.getOtherField().equals("Y")) {
        	 budgetHeadDTO.setDepartmentId(proposalMasterDto.getProposalDepId());
        	 budgetMap=iCouncilProposalMasterService.getBudgetExpenditure(budgetHeadDTO);
        	 for (Map.Entry<Long, String> entry : budgetMap.entrySet()) {
        		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
        		 String a= String.valueOf(entry.getKey());
        		 dto.setSacHeadId(Long.valueOf(a));
        		 dto.setAcHeadCode(entry.getValue());
        		 budgetList.add(dto);
        	 }
         }
         else {
        	 budgetMap=iCouncilProposalMasterService.getBudgetExpenditure(budgetHeadDTO);
        	 for (Entry<Long, String> entry : budgetMap.entrySet()) {
        		 AccountHeadSecondaryAccountCodeMasterEntity dto=new AccountHeadSecondaryAccountCodeMasterEntity();
        		 Long key = Long.parseLong(String.valueOf(entry.getKey()));
        		    dto.setSacHeadId(key);

        		    String value = entry.getValue();
        		    dto.setAcHeadCode(value);
        		 budgetList.add(dto);
        	 }
         }
         //this.getModel().setBudgetList(budgetList);
        }
         Map<Long, String> fieldList = tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid());
         modelm.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS, fieldList);
         model.setFieldList(fieldList);
         
         populateFund();
        return new ModelAndView(MainetConstants.Council.Proposal.ADD_PROPOSAL_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    // View Proposal
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Proposal.VIEW_PROPOSAL, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewCouncilMemberMasterData(
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_ID) final Long proposalId,
            final HttpServletRequest request,final Model modelm) {
        this.getModel().setSaveMode(MainetConstants.Council.VIEW);

        /*
         * // get attached document final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
         * .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
         * MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + proposalId);
         * this.getModel().setAttachDocsList(attachDocs);
         */
        CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);

        this.getModel().setCouProposalMasterDto(proposalMasterDto);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        if(proposalMasterDto.getProposalDetails() != null)
        	this.getModel().setResolutionComments(proposalMasterDto.getProposalDetails());
        }
        /* Defect 90802 */
        // get attached document
        addAttachedDoc(proposalId);

        // changes done as per defectId#33084
        /*
         * this.getModel().getCouProposalMasterDto()
         * .setProposalDepId(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
         * this.getModel().getCouProposalMasterDto()
         * .setProposalDeptName(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptdesc());
         */

        // user DEPT and DEPT id
        this.getModel().setDepartmentsList(
                iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> fieldList = tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid());
        modelm.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS, fieldList);
        this.getModel().setFieldList(fieldList);
         populateFund();
        return new ModelAndView(MainetConstants.Council.Proposal.ADD_PROPOSAL_FORM, MainetConstants.FORM_NAME,
                this.getModel());

    }

    // Proposal sent for Approval
    @RequestMapping(params = MainetConstants.Council.Proposal.SENT_FOR_APPROVAL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendForApproval(final HttpServletRequest request,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_ID) final Long proposalId,
            @RequestParam("proposalType") final String proposalType) {
        bindModel(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String flag = null;
        Map<String, Object> object = new LinkedHashMap<String, Object>();

        ServiceMaster sm = null;
        WorkflowMas workFlowMas = null;
        LookUp lookup = null;
        try {
        	lookup = CommonMasterUtility.getValueFromPrefixLookUp(proposalType, MainetConstants.Council.PROPOSAL_TYPE_PREFIX, UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {}  
        
    	if(lookup!=null && lookup.getOtherField()!=null && !lookup.getOtherField().isEmpty()) {
        	String serviceCode = lookup.getOtherField();
        	sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(serviceCode, orgId);
        }else{
        	// here 1st check workflow type is not applicable or not
	        if (proposalType.equals("F")) {
	            sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                    .getServiceMasterByShortCode(MainetConstants.Council.Proposal.SERVICE_COUNCIL_PROPOSAL, orgId);
	        } else if (proposalType.equals("N")) {
	            sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                    .getServiceMasterByShortCode("CPN", orgId);
	        }
	        else if (proposalType.equals("B")) {
	            sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                    .getServiceMasterByShortCode("CPB", orgId);
	        }
        }

        // get smProcessId from COMPARAMDETAILS table for checking Workflow applicable
        // or not
        Long smProcessId = sm.getSmProcessId();
        TbComparamDet comparamDet = ApplicationContextProvider.getApplicationContext()
                .getBean(TbComparamDetService.class).findById(smProcessId);
        // get CPD value from comparamDet
        String cpdValue = comparamDet.getCpdValue();
        // compare from MainetConstant
        if (StringUtils.equals(cpdValue, MainetConstants.CommonConstants.NA)) {
            iCouncilProposalMasterService.updateProposalStatus(proposalId, MainetConstants.FlagA);
            flag = MainetConstants.FlagA;
        } else {
            CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                    .getCouncilProposalMasterByproposalId(proposalId);

            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            CouncilProposalMasterModel model = this.getModel();
            model.setProposalNumber(proposalMasterDto.getProposalNo());
            
            
            Long workFlowLevel1 = null;
			Long workFlowLevel2 = null;
			Long workFlowLevel3 = null;
			Long workFlowLevel4 = null;
			Long workFlowLevel5 = null;
			 try {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && proposalMasterDto.getFiledId() != null) {
				BigDecimal proposalAmount =null;
					/*
					 * if(!proposalMasterDto.getYearDtos().isEmpty()) { proposalAmount =
					 * BigDecimal.ZERO; for (final CouncilYearDetDto vendor :
					 * proposalMasterDto.getYearDtos()) { proposalAmount=
					 * proposalAmount.add(vendor.getYeBugAmount()); } }
					 */
				List<Long> locId = locMasService.getlocationListByFieldIdAndOrgId(proposalMasterDto.getFiledId(),
						UserSession.getCurrent().getOrganisation().getOrgid());

				if (!locId.isEmpty()) {
					for (final Long locId1 : locId) {
						List<Long> locIdBasedOnDeptOrgIdList = locMasService.getOperLocationIdBasedOnLocIdDeptIdOrgId(
								locId1, proposalMasterDto.getProposalDepId(), UserSession.getCurrent().getOrganisation().getOrgid());

						if (!locIdBasedOnDeptOrgIdList.isEmpty()) {

							LocOperationWZMappingDto operLocationAndDeptId = locMasService
									.findOperLocationAndDeptId(locIdBasedOnDeptOrgIdList.get(0), proposalMasterDto.getProposalDepId());
							if (operLocationAndDeptId == null) {
								flag = MainetConstants.FlagN;
								break;
							} else if (operLocationAndDeptId.getCodIdOperLevel1() == null) {
								String prefixName = tbDepartmentService.findDepartmentPrefixName(proposalMasterDto.getProposalDepId(),
										orgId);
								if (prefixName == null || prefixName.isEmpty()) {
									TbDepartment deptObj = ApplicationContextProvider.getApplicationContext()
											.getBean(TbDepartmentService.class)
											.findDeptByCode(orgId, MainetConstants.FlagA, "CFC");
									operLocationAndDeptId = locMasService.findOperLocationAndDeptId(
											locIdBasedOnDeptOrgIdList.get(0), deptObj.getDpDeptid());
								} else if (operLocationAndDeptId == null) {
									flag = MainetConstants.FlagN;
								}
							}
							if(operLocationAndDeptId!=null) {
								workFlowLevel1 = operLocationAndDeptId.getCodIdOperLevel1();
								workFlowLevel2 = operLocationAndDeptId.getCodIdOperLevel2();
								workFlowLevel3 = operLocationAndDeptId.getCodIdOperLevel3();
								workFlowLevel4 = operLocationAndDeptId.getCodIdOperLevel4();
								workFlowLevel5 = operLocationAndDeptId.getCodIdOperLevel5();
							}

							workFlowMas = workflowTyepResolverService.checkgetwmschcodeid2BasedWorkflowExist(
									UserSession.getCurrent().getOrganisation().getOrgid(),
									sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(),proposalMasterDto.getProposalDepId(),
									workFlowLevel1,workFlowLevel2,workFlowLevel3,
									workFlowLevel4,workFlowLevel5,null);
						}

					}

				}
			}
            if(workFlowMas == null) {
            if (proposalType.equals("F")) {
                BigDecimal proposalAmount = proposalMasterDto.getProposalAmt();
                workFlowMas = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
                                sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), proposalAmount, null, workFlowLevel1,
                                workFlowLevel2, workFlowLevel3, workFlowLevel4, workFlowLevel5);
            } else if (proposalType.equals("N")|| proposalType.equals("B")) {
                workFlowMas = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
                                sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), workFlowLevel1, workFlowLevel2, workFlowLevel3, workFlowLevel4, workFlowLevel5);
            } else {
            	workFlowMas = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
                                sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), workFlowLevel1, workFlowLevel2, workFlowLevel3, workFlowLevel4, workFlowLevel5);
            }
           }
			 }catch (Exception e) {
				 flag = MainetConstants.FlagN;
			 }  
            if (workFlowMas != null) {
                flag = ApplicationContextProvider.getApplicationContext().getBean(CouncilWorkFlowService.class)
                        .initiateWorkFlowCouncilService(this.getModel().prepareWorkFlowTaskAction(), workFlowMas,
                                MainetConstants.Council.Proposal.PROPOSAL_APPROVAL_URL, MainetConstants.FlagA);
                iCouncilProposalMasterService.updateProposalStatus(proposalId, MainetConstants.FlagP);

                flag = MainetConstants.FlagY;

            } else {
                flag = MainetConstants.FlagN;
            }
        }
        object.put(MainetConstants.Council.Proposal.CHECK_STATUS_APPROVAL, flag);
        return object;
    }

    // Account Integration Code (Setting values in VendorBillApprovalDto)

    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Proposal.GET_BUDGET_HEAD_DETAIL, method = RequestMethod.POST)
    public VendorBillApprovalDTO checkBudgetHeadDetails(
            @RequestParam(MainetConstants.Council.Proposal.SAC_HEAD_ID) final Long sacHeadId,
            @RequestParam(MainetConstants.Council.Proposal.YEAR_ID) final Long yearId,
            @RequestParam(MainetConstants.Council.Proposal.DEPTID) final Long proposalDepId,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_AMOUNT) final BigDecimal proposalAmt) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
        budgetHeadDTO.setBillAmount(proposalAmt);
        budgetHeadDTO.setDepartmentId(proposalDepId);
        budgetHeadDTO.setFaYearid(yearId);
        budgetHeadDTO.setBudgetCodeId(sacHeadId);
        budgetHeadDTO.setOrgId(orgId);
        VendorBillApprovalDTO dto = iCouncilProposalMasterService.getBudgetExpenditureDetails(budgetHeadDTO);
        if (dto != null) {
            dto.setBillAmount(proposalAmt.setScale(2, RoundingMode.UP));
            dto.setAuthorizationStatus(MainetConstants.FlagY);
            if (dto.getInvoiceAmount().subtract(dto.getSanctionedAmount()).compareTo(proposalAmt) < 0) {
                dto.setDisallowedRemark(MainetConstants.FlagY);
            }
        } else {
            dto = new VendorBillApprovalDTO();
            dto.setAuthorizationStatus(MainetConstants.FlagN);
        }

        return dto;
    }
    
    @ResponseBody
    @RequestMapping(params = "getBudgetHeadDetailsTSCL", method = RequestMethod.POST)
    public VendorBillApprovalDTO checkBudgetHeadDetailsTscl(
            @RequestParam(MainetConstants.Council.Proposal.SAC_HEAD_ID) final Long sacHeadId,
            @RequestParam(MainetConstants.Council.Proposal.YEAR_ID) final Long yearId,
            @RequestParam(MainetConstants.Council.Proposal.DEPTID) final Long proposalDepId,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_AMOUNT) final BigDecimal proposalAmt,
            @RequestParam("fieldId") final Long fieldId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
        budgetHeadDTO.setBillAmount(proposalAmt);
        budgetHeadDTO.setDepartmentId(proposalDepId);
        budgetHeadDTO.setFaYearid(yearId);
        budgetHeadDTO.setBudgetCodeId(sacHeadId);
        budgetHeadDTO.setOrgId(orgId);
        budgetHeadDTO.setFieldId(fieldId);
        VendorBillApprovalDTO dto = iCouncilProposalMasterService.getCouncilBudgetExpenditureDetail(budgetHeadDTO);
        if (dto != null) {
            dto.setBillAmount(proposalAmt.setScale(2, RoundingMode.UP));
            dto.setAuthorizationStatus(MainetConstants.FlagY);
            if (dto.getInvoiceAmount().subtract(dto.getSanctionedAmount()).compareTo(proposalAmt) < 0) {
                dto.setDisallowedRemark(MainetConstants.FlagY);
            }
        } else {
            dto = new VendorBillApprovalDTO();
            dto.setAuthorizationStatus(MainetConstants.FlagN);
        }

        return dto;
    }

    // Getting Financial Year by Proposal Date
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Proposal.GET_FINANCIALYEARIDBYDATE, method = { RequestMethod.POST,
            RequestMethod.GET })
    public Long getFinancialYear(@RequestParam(MainetConstants.Council.Proposal.PROPOSAL_DATE) final Date proposalDate,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long finyearId = null;
        try {
            finyearId = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
                    .getFinanciaYearIdByFromDate(proposalDate);
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured from get financial year" + exception);
        }
        return finyearId;
    }

    // Add attached doc to model
    private void addAttachedDoc(Long proposalId) {
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                                + proposalId);
        List<DocumentDetailsVO> documentDtos = new ArrayList<>();
        // iterate and set document details
        attachDocs.forEach(doc -> {
            DocumentDetailsVO docDto = new DocumentDetailsVO();
            // get employee name who attach this image
            // Employee emp = employeeService.findEmployeeById(doc.getUserId());
            docDto.setDocumentName(doc.getAttFname());
            docDto.setAttachmentId(doc.getAttId());
            docDto.setUploadedDocumentPath(doc.getAttPath());
            documentDtos.add(docDto);
        });
        this.getModel().setDocumentDtos(documentDtos);
    }
    
    
    @RequestMapping(method = RequestMethod.POST, params = "getBudgetHead")
    public @ResponseBody Map<Long, String> getBudget(@RequestParam("dpDeptId") final Long dpDeptId,@RequestParam("fieldId") final Long fieldId,
    		final HttpServletRequest request) {
        bindModel(request);
        Map<Long, String> budgetMap = new HashMap<>();
        LookUp lookup=null;
     	Organisation org = UserSession.getCurrent().getOrganisation();
    	 VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
         budgetHeadDTO.setOrgId(org.getOrgid());
         budgetHeadDTO.setFieldId(fieldId);
     	int langId = UserSession.getCurrent().getLanguageId();
         try {
         	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                     AccountPrefix.AIC.toString(),langId,org);
         }catch(Exception e) {
         	return budgetMap;
         }
         if(lookup!=null && lookup.getOtherField().equals("Y")) {
        	 budgetHeadDTO.setDepartmentId(dpDeptId);
        	 budgetMap=iCouncilProposalMasterService.getBudgetExpenditure(budgetHeadDTO);
         }
         else {
        	 budgetMap=iCouncilProposalMasterService.getBudgetExpenditure(budgetHeadDTO);
         }
       return budgetMap;
        
    }
    
    
    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORKFLOW_HISTORY, method = RequestMethod.POST)
    public @ResponseBody ModelAndView getWorkFlowHistory(
            @RequestParam(name = "proposalId") final Long proposalId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode, ModelMap modelMap) {

        //String workCode = workDefinitionService.findAllWorkDefinitionById(workId).getWorkcode();
        CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class).getWorkflowRequestByAppIdOrRefId(null, proposalMasterDto.getProposalNo(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
           if(workflowRequest != null) {
           List<WorkflowTaskActionWithDocs> acHistory = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowActionService.class).getActionLogByUuidAndWorkflowId(proposalMasterDto.getProposalNo(),
                        workflowRequest.getId(), (short) UserSession.getCurrent().getLanguageId());
           
           /*List<WorkflowTaskActionWithDocs> actionHistory = acHistory.stream()
   				.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());*/
           
           
            for (WorkflowTaskActionWithDocs workflowTaskAction : acHistory) {
            	  Employee employee =  employeeService.findEmployeeByIdAndOrgId(workflowTaskAction.getEmpId(), workflowTaskAction.getOrgId());
            	  //from changes
            	  StringBuffer empName=new StringBuffer(employee.getEmpname());
            	  if(employee.getEmpmname() != null && !(employee.getEmpmname()).isEmpty())
            		  empName.append(" "+employee.getEmpmname());
            	  if(employee.getEmplname()!=null)
            		  empName.append(" "+employee.getEmplname());
            	  workflowTaskAction.setEmpName(empName.toString()); //to
				/* workflowTaskAction.setEmpName(employee.getEmpname()); */
            	  workflowTaskAction.setEmpEmail(employee.getEmpemail());
            	  workflowTaskAction.setEmpGroupDescEng(employee.getDesignation().getDsgname());
            	  workflowTaskAction.setEmpGroupDescReg(employee.getDesignation().getDsgnameReg());
            	  modelMap.addAllAttributes(acHistory);
			}
           
        modelMap.put(MainetConstants.WorksManagement.ACTIONS, acHistory);
            }
        return new ModelAndView(MainetConstants.WorksManagement.WORK_WORKFLOW_HISTORY, MainetConstants.FORM_NAME,
                modelMap);
    }
    
    public void populateFund()
	{
		boolean fieldDefaultFlag = false;
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		boolean fundDefaultFlag = false;
		if (isDafaultOrgExist) {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
		} else {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
		}
		Organisation defultorg = null;
		Long defultorgId = null;
		if (isDafaultOrgExist && fundDefaultFlag) {
			defultorg = ApplicationSession.getInstance().getSuperUserOrganization();
			defultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		} else {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
				PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		List<AccountFundMasterBean> fundList = tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
				fundLookup.getLookUpId(), UserSession.getCurrent().getLanguageId());
		this.getModel().setFundList(fundList);
	}
    
    @RequestMapping(params = "getBudgetStatusReport", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getBudgetStatusReport(final HttpServletRequest request) {
    	 getModel().bind(request);
    	 CouncilProposalMasterModel model = this.getModel();
        CouncilProposalMasterDto proposalMasterDto = this.getModel().getCouProposalMasterDto();
        proposalMasterDto.setBudgetHeadDesc(tbAcFieldMasterService.getFieldCode(proposalMasterDto.getFiledId())+" "+tbAcFieldMasterService.getFieldDesc(proposalMasterDto.getFiledId()));
        proposalMasterDto=iCouncilProposalMasterService.generateReport(proposalMasterDto);
        
        return new ModelAndView("councilBudgetStatus", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "encyptData", method = RequestMethod.POST)
    public void encyptData(@RequestParam("proposalPurpose") final String proposalPurpose) {
    	try{
    		String decodedProposalPurpose = URLDecoder.decode(proposalPurpose, "UTF-8");   		
    		this.getModel().setProposalPurpose(decodedProposalPurpose);
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    	
    	
    }
    
	
}
