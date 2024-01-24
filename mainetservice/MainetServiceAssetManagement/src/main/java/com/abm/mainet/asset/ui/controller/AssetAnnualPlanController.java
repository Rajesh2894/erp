package com.abm.mainet.asset.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.service.IAssetAnnualPlanService;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDTO;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDetailsDTO;
import com.abm.mainet.asset.ui.model.AssetAnnualPlanModel;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbFinancialyear;
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

@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_ANNUAL_INVENTORY_URL, "ITAssetAnnualPlan.html" })
public class AssetAnnualPlanController extends AbstractFormController<AssetAnnualPlanModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAssetAnnualPlanService annualPlanService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        this.getModel().setCommonHelpDocs(MainetConstants.AssetManagement.ASSET_ANNUAL_INVENTORY_URL);

        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String smShortDesc = httpServletRequest.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, httpServletRequest.getRequestURI(),
                        "ITAssetAnnualPlan.html"));
        /*
         * this.getModel().setFinancialYearMap(
         * ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getAllFinincialYear());
         */
        makeCommonData();
        this.getModel().setApprovalViewFlag(MainetConstants.MODE_CREATE);
        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        List<AssetAnnualPlanDTO> astAnnualPlanDTOs = annualPlanService.fetchAnnualPlanSearchData(null, null,
                null, UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getModuleDeptCode());
        this.getModel().setAstAnnualPlanDTOs(astAnnualPlanDTOs);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchAnnualPlanData", method = RequestMethod.POST)
    public List<AssetAnnualPlanDTO> searchAnnualPlanData(@RequestParam(value = "finYearId") final Long finYearId,
            @RequestParam(value = "deptId") final Long deptId,
            @RequestParam(value = "locId") final Long locId,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<AssetAnnualPlanDTO> annualPlanList = annualPlanService.fetchAnnualPlanSearchData(finYearId, deptId, locId, orgId,
                UserSession.getCurrent().getModuleDeptCode());
        return annualPlanList;
    }

    @RequestMapping(params = "addAnnualPlan", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addAnnualPlan(final HttpServletRequest request) {
        getModel().bind(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setApprovalViewFlag(MainetConstants.MODE_CREATE);
        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        this.getModel().setCompletedFlag(MainetConstants.Y_FLAG);
        makeCommonData();
        return new ModelAndView("addAnnualPlan", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewAnnualPlanData", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewAnnualPlanData(@RequestParam("astAnnualPlanId") final Long astAnnualPlanId,
            final HttpServletRequest request) {
        AssetAnnualPlanDTO astAnnualPlanDTO = annualPlanService.getAstAnnualPlanDataById(astAnnualPlanId);
        AssetAnnualPlanModel astAnnualPlanModel = this.getModel();
        astAnnualPlanModel.setAstAnnualPlanDTO(astAnnualPlanDTO);
        astAnnualPlanModel.setAstAnnualPlanDetDTO(astAnnualPlanDTO.getAstAnnualPlanDetailsDTO());
        astAnnualPlanModel.setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        astAnnualPlanModel.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        /*
         * astAnnualPlanModel.setFinancialYearMap(
         * ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getAllFinincialYear());
         */
        // D#76416 set financial year
        List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
        astAnnualPlanModel.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    astAnnualPlanModel.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException("EXCEPTION_IN_FINANCIAL_YEAR_DETAIL" + ex);
                }
            });
        }
        this.getModel().setCompletedFlag(MainetConstants.Y_FLAG);
        Collections.reverse(astAnnualPlanModel.getFaYears());
        this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
        astAnnualPlanModel.setApprovalViewFlag(MainetConstants.MODE_VIEW);
        return new ModelAndView("addAnnualPlan", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showDetails(@RequestParam("appNo") final String appNo,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam(value = "taskName", required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        AssetAnnualPlanModel astAnnualPlanModel = this.getModel();
        this.getModel().setCompletedFlag(MainetConstants.Y_FLAG);
        astAnnualPlanModel.setTaskId(actualTaskId);
        astAnnualPlanModel.getWorkflowActionDto().setReferenceId(appNo);
        astAnnualPlanModel.getWorkflowActionDto().setTaskId(actualTaskId);
        Long astAnnualPlanId = Long.parseLong(appNo.substring(appNo.lastIndexOf('/') + 1));
        // Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        AssetAnnualPlanDTO astAnnualPlanDTO = annualPlanService.getAstAnnualPlanDataById(astAnnualPlanId);

        astAnnualPlanModel.setAstAnnualPlanDTO(astAnnualPlanDTO);
        astAnnualPlanModel.setAstAnnualPlanDetDTO(astAnnualPlanDTO.getAstAnnualPlanDetailsDTO());
        astAnnualPlanModel.setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        astAnnualPlanModel.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        /*
         * astAnnualPlanModel.setFinancialYearMap(
         * ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getAllFinincialYear());
         */
        // D#76416 set financial year
        List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
        astAnnualPlanModel.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    astAnnualPlanModel.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException("EXCEPTION_IN_FINANCIAL_YEAR_DETAIL" + ex);
                }
            });
        }
        Collections.reverse(astAnnualPlanModel.getFaYears());
        astAnnualPlanModel.setApprovalViewFlag(MainetConstants.MODE_VIEW);
        this.getModel().setSaveMode("A");
        astAnnualPlanModel.getWorkflowActionDto().setComments("");
        astAnnualPlanModel.getWorkflowActionDto().setDecision(null);
        return new ModelAndView("AssetAnnualPlanApproval", MainetConstants.FORM_NAME, astAnnualPlanModel);

    }

    /**
     * Maker Checker submit
     * 
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;
        this.bindModel(httpServletRequest);
        AssetAnnualPlanModel asstModel = this.getModel();
        String decision = asstModel.getWorkflowActionDto().getDecision();
        boolean updFlag = asstModel.updateApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid(),
                asstModel.getAstAnnualPlanDTO().getDeptCode());
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.sendBack"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }

    public void makeCommonData() {
        this.getModel().setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        this.getModel().setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        // D#76416 set financial year
        List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
        this.getModel().getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    this.getModel().getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException("EXCEPTION_IN_FINANCIAL_YEAR_DETAIL" + ex);
                }
            });
        }
        Collections.reverse(this.getModel().getFaYears());
        this.getModel().setAstClassMovList(loadAssetClassMovList(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAstAnnualPlanDTO(new AssetAnnualPlanDTO());
        this.getModel().setAstAnnualPlanDetDTO(new ArrayList<AssetAnnualPlanDetailsDTO>());
    }

    public List<LookUp> loadAssetClassMovList(Long orgId) {
        List<LookUp> assetClassMovList = new ArrayList<>();
        List<LookUp> lookupList = CommonMasterUtility
                .lookUpListByPrefix(UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE)
                        ? PrefixConstants.ASSET_PREFIX.ASSET_CLASS
                        : "ICL", orgId);
        assetClassMovList = lookupList.stream()
                .filter(lookup -> lookup.getOtherField().equalsIgnoreCase(PrefixConstants.ASSET_PREFIX.ASSET_CLASSIFICATION_MOV))
                .collect(Collectors.toList());
        return assetClassMovList;
    }
    
    
    @Override 
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long taskId, final HttpServletRequest request )throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(request);
        AssetAnnualPlanModel astAnnualPlanModel = this.getModel();
        astAnnualPlanModel.setTaskId(taskId);
        astAnnualPlanModel.getWorkflowActionDto().setReferenceId(applicationId);
        astAnnualPlanModel.getWorkflowActionDto().setTaskId(taskId);
        Long astAnnualPlanId = Long.parseLong(applicationId.substring(applicationId.lastIndexOf('/') + 1));
        
        AssetAnnualPlanDTO astAnnualPlanDTO = annualPlanService.getAstAnnualPlanDataById(astAnnualPlanId);

        astAnnualPlanModel.setAstAnnualPlanDTO(astAnnualPlanDTO);
        astAnnualPlanModel.setAstAnnualPlanDetDTO(astAnnualPlanDTO.getAstAnnualPlanDetailsDTO());
        astAnnualPlanModel.setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        astAnnualPlanModel.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
       
        List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
        astAnnualPlanModel.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    astAnnualPlanModel.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    throw new FrameworkException("EXCEPTION_IN_FINANCIAL_YEAR_DETAIL" + ex);
                }
            });
        }
        Collections.reverse(astAnnualPlanModel.getFaYears());
       
        astAnnualPlanModel.setCompletedFlag(MainetConstants.MODE_VIEW);
        astAnnualPlanModel.setApprovalViewFlag(MainetConstants.MODE_VIEW);
        return new ModelAndView("addAnnualPlan", MainetConstants.FORM_NAME, this.getModel());

    }

}
