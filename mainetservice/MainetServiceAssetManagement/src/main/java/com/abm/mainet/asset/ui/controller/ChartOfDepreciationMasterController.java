/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.service.IChartOfDepreciationMasterService;
import com.abm.mainet.asset.service.IMaintenanceService;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.asset.ui.model.ChartOfDepreciationMasterModel;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sarojkumar.yadav
 *
 * Controller Class for Chart of Depreciation Master
 */
@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_CHART_OF_DEPRECIATION_MASTER_URL,
        "ITChartOfDepreciationMaster.html" })
public class ChartOfDepreciationMasterController extends AbstractFormController<ChartOfDepreciationMasterModel> {

    @Resource
    private IChartOfDepreciationMasterService cdmService;

    @Resource
    private SecondaryheadMasterService shmService;
    @Resource
    private IMaintenanceService maintenanceService;

    /**
     * used for showing default home page for Chart Of Depreciation Master
     * 
     * @param httpServletRequest
     * @return default view
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        this.getModel().setCommonHelpDocs("ChartOfDepreciationMaster.html");
        model.addAttribute("accountCodeHead", shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagE));
        /*
         * List<LookUp> lookupList = getApplicationSession().getLastLevelChildsForHierarchical(
         * MainetConstants.AssetManagement.ASSET_CLASSIFICATION, orgId);
         */
        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String smShortDesc = httpServletRequest.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, httpServletRequest.getRequestURI(),
                        "ITChartOfDepreciationMaster.html"));
        List<LookUp> lookupList = CommonMasterUtility.getListLookup(
                UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL" : "ICL",
                UserSession.getCurrent().getOrganisation());
        model.addAttribute("assetClassHead", lookupList);
        List<ChartOfDepreciationMasterDTO> dTOList = this.getModel().filterCdmDataList(null, null, null, null, orgId);
        model.addAttribute("DepreciationList", dTOList);
        model.addAttribute("accountFlag", maintenanceService.checkAccountActiveOrNot());
        return index();
    }

    /**
     * update chart Of Depreciation Master fields by name or/and by accountCode or/and by assetClass or/and by frequency with
     * primary Key groupId.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List<ChartOfDepreciationMasterDTO> if record found else return empty dto
     */
    @RequestMapping(params = MainetConstants.AssetManagement.FILTER_CDM_DATA, method = RequestMethod.POST)
    public @ResponseBody List<ChartOfDepreciationMasterDTO> filterMstDataList(final HttpServletRequest request,
            @RequestParam(value = MainetConstants.AssetManagement.ACCOUNT_CODE, required = false) final String accountCode,
            @RequestParam(value = MainetConstants.AssetManagement.ASSET_CLASS, required = false) final String assetClass,
            @RequestParam(value = MainetConstants.AssetManagement.FREQUENCY, required = false) final String frequency,
            @RequestParam(value = MainetConstants.AssetManagement.GROUP_NAME, required = false) final String name) {

        String actCode = null;
        Long astcls = null, freq = null;
        String groupname = name.trim();

        // D#33152
        actCode = (accountCode.isEmpty()) ? null : accountCode;
        astcls = (assetClass.isEmpty()) ? null : Long.valueOf(assetClass);
        freq = (frequency.isEmpty()) ? null : Long.valueOf(frequency);

        return this.getModel().filterCdmDataList(actCode, astcls, freq, groupname,
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    /**
     * used for create Chart Of Depreciation Master details form
     * 
     * @param groupId
     * @param modeType
     * @return
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView cdmForm(
            @RequestParam(value = MainetConstants.AssetManagement.GROUP_ID, required = false) Long groupId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            final Model model) {
        ChartOfDepreciationMasterModel cdmModel = this.getModel();
        populateModel(cdmModel, groupId, type, model);
        model.addAttribute("accountFlag", maintenanceService.checkAccountActiveOrNot());
        return new ModelAndView(MainetConstants.AssetManagement.CDM_FORM, MainetConstants.FORM_NAME, cdmModel);
    }

    /**
     * populate common details
     * 
     * @param cdmModel
     * @param groupId
     * @param modeType
     */
    private void populateModel(final ChartOfDepreciationMasterModel cdmModel, final Long groupId, final String mode,
            final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        if (maintenanceService.checkAccountActiveOrNot()) {
            // "E" is use for the expenditure head
            model.addAttribute("accountCodeAst", shmService.findExpenditureAccountHeadOnly(orgId, langId, MainetConstants.FlagE));
        }
        /*
         * List<LookUp> lookupList = getApplicationSession().getLastLevelChildsForHierarchical(
         * MainetConstants.AssetManagement.ASSET_CLASSIFICATION, orgId);
         */
        List<LookUp> lookupList = CommonMasterUtility.getListLookup(
                UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE) ? "ACL" : "ICL",
                UserSession.getCurrent().getOrganisation());
        model.addAttribute("assetClassAST", lookupList);
        if (mode.equals(MainetConstants.MODE_CREATE)) {
            cdmModel.setAssetChartOfDepreciationMasterDTO(new ChartOfDepreciationMasterDTO());
            cdmModel.setModeType(MainetConstants.MODE_CREATE);
        } else {
            final ChartOfDepreciationMasterDTO mastDTO = getChartOfDepreMaster(groupId);
            cdmModel.setAssetChartOfDepreciationMasterDTO(mastDTO);
            if (mode.equals(MainetConstants.MODE_EDIT)) {
                cdmModel.setModeType(MainetConstants.MODE_EDIT);

            } else {
                cdmModel.setModeType(MainetConstants.MODE_VIEW);
            }
        }
    }

    private ChartOfDepreciationMasterDTO getChartOfDepreMaster(final Long groupId) {
        return cdmService.findByGroupId(groupId);
    }

    /**
     * Method gets called when the object when clicked on search criteria
     * 
     * @param name
     * @return if record found else return empty dto
     */
    @RequestMapping(params = "validateDepreciationName", method = { RequestMethod.POST })
    public ModelAndView validateDepreciationName(
            @RequestParam(value = MainetConstants.AssetManagement.GROUP_NAME, required = false) final String name,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        boolean dupFlag = this.getModel().isDuplicateName(name);
        String respMsg = "";
        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("assset.depreciationMaster.duplicate.name");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }

    /**
     * Method gets called when the object when clicked on search criteria
     * 
     * @param asset Class
     * @return if record found else return empty dto
     */
    @RequestMapping(params = "validateAssetClass", method = { RequestMethod.POST })
    public ModelAndView validateAssetClass(@RequestParam(value = "assetClass", required = false) final Long assetClass,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        boolean dupFlag = this.getModel().isDuplicateAssetClass(assetClass);
        String respMsg = "";
        if (dupFlag) {
            respMsg = ApplicationSession.getInstance().getMessage("assset.depreciationMaster.duplicate.name");
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
    }
}
