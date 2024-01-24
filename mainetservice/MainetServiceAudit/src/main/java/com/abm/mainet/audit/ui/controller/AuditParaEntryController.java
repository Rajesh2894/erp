package com.abm.mainet.audit.ui.controller;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.dto.SearchParaRestResponseDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.audit.ui.model.AuditParaEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Controller
@RequestMapping(IAuditConstants.AUDIT_PARA_SUMMARY_URL)
public class AuditParaEntryController extends AbstractFormController<AuditParaEntryModel> {

	@Autowired
	private IAuditParaEntryService auditParaService;

	@Autowired
	private ILocationMasService locService;
	
	@Autowired
	private TbDepartmentService deptService;
	
	@Autowired
	private IWorkflowActionService workflowActionService;
	
	@Autowired
	private IAttachDocsService iAttachDocsService;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	// default
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		SearchParaRestResponseDto paramsList = new SearchParaRestResponseDto();
		// Used in JSP to populate input 

		
		
		/* Need to send below paramsList for Output table grid */
		paramsList.setLocLst(loadLocation());
		paramsList.setDeptLst(loadDepartment());
		paramsList.setStatusLst(loadStatus());
		request.setAttribute("paramsList", paramsList);
		request.getSession().setAttribute("paramsList", paramsList);

		/*
		 * request.setAttribute("locList", loadLocation());
		 * request.setAttribute("deptList", loadDepartment());
		 * request.setAttribute("statusList", loadStatus());
		 */
		
		List<AuditParaEntryDto> auditList = auditParaService.getAuditParaEntryByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		Iterator<AuditParaEntryDto> iterator = auditList.iterator();
        while (iterator.hasNext()) {
        	AuditParaEntryDto obj = iterator.next();
        	String auditStatus = CommonMasterUtility.findLookUpCode(IAuditConstants.AUDIT_PARA_STATUS_PREFIX, obj.getOrgId(), obj.getAuditParaStatus());
            if (auditStatus.equals(MainetConstants.FlagD) && !obj.getCreatedBy().equals(UserSession.getCurrent().getEmployee().getEmpId())) {
                iterator.remove();
            }
        }

		this.getModel().setAuditParaEntryDtoList(auditList);
		return defaultResult();
	}

	// add
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_ADD_PARAM, method = { RequestMethod.POST })
	public ModelAndView addAuditPara(final HttpServletRequest request) {
		getModel().bind(request);
		fileUpload.sessionCleanUpForFileUpload();
		Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
         List<TbFinancialyear> faYears = new ArrayList<>();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    faYears.add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        request.setAttribute("aFinancialYr", faYears);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView(IAuditConstants.AUDIT_PARA_ADD_VIEW_EDIT_TILES, MainetConstants.FORM_NAME, this.getModel());
	}

	// search
	@ResponseBody
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_SEARCH_PARAM, method = { RequestMethod.POST, RequestMethod.GET })
	public SearchParaRestResponseDto getAuditParaEntryDtoList(@RequestParam("auditType") final Long auditType,
			@RequestParam("auditDeptId") final Long auditDeptId, @RequestParam("auditWard1") Long auditWard1,
			@RequestParam("auditParaStatus") final Long auditParaStatus,
			@RequestParam("auditParaCode") final String auditParaCode,
			@RequestParam("fromDate") final Date fromDate, @RequestParam("toDate") final Date toDate,
			final HttpServletRequest request) {
		SearchParaRestResponseDto paramsList = new SearchParaRestResponseDto();
		
		paramsList=(SearchParaRestResponseDto)request.getSession().getAttribute("paramsList");

		getModel().bind(request);
		List<AuditParaEntryDto> lst = auditParaService.searchAuditParaService(auditType, auditDeptId,
				UserSession.getCurrent().getOrganisation().getOrgid(), auditWard1, auditParaStatus, auditParaCode, fromDate, toDate);
		Iterator<AuditParaEntryDto> iterator = lst.iterator();
        while (iterator.hasNext()) {
        	AuditParaEntryDto obj = iterator.next();
        	String auditStatus = CommonMasterUtility.findLookUpCode(IAuditConstants.AUDIT_PARA_STATUS_PREFIX, obj.getOrgId(), obj.getAuditParaStatus());
            if (auditStatus.equals(MainetConstants.FlagD) && !obj.getCreatedBy().equals(UserSession.getCurrent().getEmployee().getEmpId())) {
                iterator.remove();
            }
        }

		paramsList.setSearchAuditParaEntryDtoList(lst);
		return paramsList;
	}

	// edit
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_EDIT_PARAM, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editAuditPara(final HttpServletRequest request,
			@RequestParam("auditParaId") final long auditParaId) {
		fileUpload.sessionCleanUpForFileUpload();
		AuditParaEntryDto dto = auditParaService.getAuditParaEntryByAuditParaId(auditParaId);
		/*
		 * request.setAttribute("locList", loadLocation());
		 * request.setAttribute("deptList", loadDepartment());
		 */
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
		
		if(dto.getAuditAppendix() != null)
			this.getModel().setResolutionComments(dto.getAuditAppendix());
		// get financial years
		Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
         List<TbFinancialyear> faYears = new ArrayList<>();
         if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    faYears.add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        request.setAttribute("aFinancialYr", faYears);
		
		// get attached document
		
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        IAuditConstants.AUDIT_DEPT_CODE + MainetConstants.WINDOWS_SLASH + auditParaId);
        this.getModel().setAttachDocsList(attachDocs);
		
		this.getModel().setAuditParaEntryDto(dto);
		return new ModelAndView(IAuditConstants.AUDIT_PARA_ADD_VIEW_EDIT_TILES, MainetConstants.FORM_NAME, this.getModel());
	}

	// view
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_VIEW_PARAM, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewAuditPara(final HttpServletRequest request,
			@RequestParam("auditParaId") final long auditParaId) {
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
		fileUpload.sessionCleanUpForFileUpload();
		AuditParaEntryDto dto = auditParaService.getAuditParaEntryByAuditParaId(auditParaId);
		// get attached document
		
		if(dto.getAuditAppendix() != null)
			this.getModel().setResolutionComments(dto.getAuditAppendix());
        final List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                IAuditConstants.AUDIT_DEPT_CODE + MainetConstants.WINDOWS_SLASH + auditParaId);
		this.getModel().setAttachDocsList(attachDocs);
		final List<AttachDocs> docsList = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                "AUDIT_PARA_ENTRY" + MainetConstants.WINDOWS_SLASH + auditParaId);
		this.getModel().setAttachDocumentList(docsList);
		Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
         List<TbFinancialyear> faYears = new ArrayList<>();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    faYears.add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        request.setAttribute("aFinancialYr", faYears);
		
		this.getModel().setAuditParaEntryDto(dto);
		/* request.setAttribute("deptList", loadDepartment()); */
		return new ModelAndView(IAuditConstants.AUDIT_PARA_ADD_VIEW_EDIT_TILES, MainetConstants.FORM_NAME, this.getModel());
	}
	
	//history
	@RequestMapping(method = RequestMethod.POST, params = IAuditConstants.AUDIT_PARA_WORKFLOW_HISTORY)
    public ModelAndView showHistoryDetails(
            @RequestParam("refId") String refId,
            @RequestParam("appId") String appId,
            @RequestParam("appDate") final String appDate,
            @RequestParam("servName") final String servName,
            @RequestParam("workflowReqId") final Long workflowReqId,
            final HttpServletRequest httpServletRequest, ModelMap modelMap) {
        List<WorkflowTaskActionWithDocs> actionHistory = workflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(appId, refId, workflowReqId,(short) UserSession.getCurrent().getLanguageId());
        String empName = actionHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getId)).collect(Collectors.toList()).get(0).getEmpName();

        if (appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL)
                || (!appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL))) {
            appId = refId;
        }
        if (!appId.equals(MainetConstants.NULL) && refId.equals(MainetConstants.NULL)) {
            refId = appId;
        }

        modelMap.put("actions", actionHistory);
        modelMap.put("raisedBy", empName);
        modelMap.put("appDate", appDate);
        modelMap.put("servName", servName);
        modelMap.put("appId", appId);
        modelMap.put("refId", refId);
       
        return new ModelAndView(IAuditConstants.AUDIT_PARA_WORKFLOW_HISTORY_TILES, MainetConstants.FORM_NAME, modelMap);
        //return modelMap;
    }

	// Get Active Location List from Location Master Dto
	public List<TbLocationMas> loadLocation() {
		return locService.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	}
	
	// Get Active Department List from Department Master Dto
	public List<TbDepartment> loadDepartment() {
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			return deptService.getAllDeptBasedOnPrefix();
		}else {
			return deptService.findAllDept(UserSession.getCurrent().getOrganisation().getOrgid());
		}
		
	}
	
	// Get Audit Para Status List from Prefix
	public List<LookUp> loadStatus() {
		
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		return CommonMasterUtility.getLookUps(IAuditConstants.AUDIT_PARA_STATUS_PREFIX, org);
	}

}
