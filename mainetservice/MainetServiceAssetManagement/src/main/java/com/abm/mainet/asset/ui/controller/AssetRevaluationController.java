/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.service.IAssetValuationService;
import com.abm.mainet.asset.service.IMaintenanceService;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.RevaluationDTO;
import com.abm.mainet.asset.ui.model.AssetRevaluationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping(MainetConstants.AssetManagement.ASSET_REVALUTATION_PATH)
public class AssetRevaluationController extends AbstractFormController<AssetRevaluationModel> {

    @Autowired
    private IAssetInformationService astService;
    @Autowired
    private IOrganisationService iOrganisationService;
    @Resource
    private SecondaryheadMasterService shmService;
    @Resource
    private ILocationMasService iLocationMasService;
    @Resource
    private TbDepartmentService iTbDepartmentService;
    @Resource
    private IFileUploadService fileUpload;
    @Autowired
    private IAssetValuationService valuationService;

    @Autowired
    IMaintenanceService maintenanceService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        return index();
    }

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView revaluationForm(final Model model,
            @RequestParam(value = MainetConstants.AssetManagement.AST_ID, required = false) final Long assetId,
            @RequestParam(value = MainetConstants.AssetManagement.REVAL_MODE, required = false) final String revalMode,
            final HttpServletRequest request) {
        sessionCleanup(request);
        final AssetDetailsDTO detDTO = populateModel(assetId, model);
        final Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        final AssetDepreciationChartDTO deprCharDto = detDTO.getAstDepreChartDTO();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        AssetRevaluationModel revalutionModel = this.getModel();
        final RevaluationDTO dto = revalutionModel.getRevaluationDTO();
        if (revalMode != null && !revalMode.isEmpty() && revalMode.equals("IMP")) {
            model.addAttribute("revalMode", "IMP");
        }
        revalutionModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        revalutionModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(request));
        revalutionModel.setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        dto.setAssetId(detDTO.getAssetInformationDTO().getAssetId());
        dto.setSerialNo(detDTO.getAssetInformationDTO().getAstCode());
        dto.setAssetClass(detDTO.getAssetInformationDTO().getAssetClass2());
        dto.setAssetClassDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(detDTO.getAssetInformationDTO().getAssetClass2(), organisation)
                .getDescLangFirst());
        dto.setDescription(detDTO.getAssetInformationDTO().getDetails());
        // D#72448 if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getLocation() != null) {
        if (detDTO.getAssetClassificationDTO().getLocation() != null) {
            dto.setLocation(detDTO.getAssetClassificationDTO().getLocation());
            dto.setLocationDesc(iLocationMasService
                    .findById(detDTO.getAssetClassificationDTO().getLocation()).getLocNameEng());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            dto.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
            dto.setDepartmentDesc(
                    iTbDepartmentService.findById(detDTO.getAssetClassificationDTO().getDepartment()).getDpDeptdesc());
            if (StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
                dto.setCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
                /*
                 * dto.setCostCenterDesc(secondaryheadMasterService
                 * .findBysacHeadCodeDesc(detDTO.getAssetClassificationDTO().getCostCenter(), orgId));
                 * dto.setChartOfAccount(detDTO.getAssetClassificationDTO().getCostCenter());
                 */
                dto.setCostCenterDesc(detDTO.getAssetClassificationDTO().getCostCenter());
            }
        }
        AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(orgId, assetId);
        if (depAssetDTO != null && depAssetDTO.getCurrentBookValue() != null) {
            dto.setCurrentValue(depAssetDTO.getCurrentBookValue());
            dto.setNewAmount(depAssetDTO.getCurrentBookValue());
        } else if (detDTO.getAssetPurchaseInformationDTO() != null) {
            dto.setCurrentValue(detDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition());
            dto.setNewAmount(detDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition());
        }
        if (deprCharDto != null && deprCharDto.getOriUseYear() != null) {
            dto.setOrigUsefulLife(deprCharDto.getOriUseYear());
            dto.setUpdUsefulLife(deprCharDto.getOriUseYear());
        }
        dto.setOrgId(orgId);
        /* check account module is live or not */
        if (maintenanceService.checkAccountActiveOrNot()) {
            this.getModel().setAccountLive(MainetConstants.Common_Constant.YES);
        }
        return new ModelAndView("AssetRevaluation", MainetConstants.FORM_NAME, this.getModel());
    }

    private AssetDetailsDTO populateModel(final Long assetId, final Model model) {
        AssetDetailsDTO dto = null;
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        dto = astService.getDetailsByAssetId(assetId);
        return dto;
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showRevaluationApproval(@RequestParam("appNo") final String serialNoModified,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        AssetRevaluationModel revalutionModel = this.getModel();
        revalutionModel.setTaskId(actualTaskId);
        revalutionModel.getWorkflowActionDto().setReferenceId(serialNoModified);
        revalutionModel.getWorkflowActionDto().setTaskId(actualTaskId);
        // load new information as recorded in the transfer request
        revalutionModel.loadTransferDetails();
        model.addAttribute("mode", "APR");
        final RevaluationDTO dto = revalutionModel.getRevaluationDTO();
        if (dto.getImpType() != null && dto.getImpType() != 0) {
            model.addAttribute("revalMode", "IMP");
        }
        final AssetDetailsDTO detDTO = populateModel(dto.getAssetId(), model);
        final Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        revalutionModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        revalutionModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(httpServletRequest));
        revalutionModel.setAccountHead(shmService.findAccountHeadsByOrgIdAndStatusId(orgId, langId));
        dto.setAssetId(detDTO.getAssetInformationDTO().getAssetId());
        dto.setSerialNo(detDTO.getAssetInformationDTO().getAstCode());
        dto.setAssetClass(detDTO.getAssetInformationDTO().getAssetClass2());
        dto.setAssetClassDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(detDTO.getAssetInformationDTO().getAssetClass2(), organisation)
                .getDescLangFirst());
        dto.setDescription(detDTO.getAssetInformationDTO().getDetails());
        if (detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getLocation() != null) {
            dto.setLocation(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getLocation());
            dto.setLocationDesc(iLocationMasService
                    .findById(detDTO.getAssetInformationDTO().getAssetPostingInfoDTO().getLocation()).getLocNameEng());
        }
        if (detDTO.getAssetClassificationDTO() != null) {
            dto.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
            dto.setDepartmentDesc(
                    iTbDepartmentService.findById(detDTO.getAssetClassificationDTO().getDepartment()).getDpDeptdesc());
            if (StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
                dto.setCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
                /*
                 * dto.setCostCenterDesc(secondaryheadMasterService
                 * .findBysacHeadCodeDesc(detDTO.getAssetClassificationDTO().getCostCenter(), orgId));
                 * dto.setChartOfAccount(detDTO.getAssetClassificationDTO().getCostCenter());
                 */
                dto.setCostCenterDesc(detDTO.getAssetClassificationDTO().getCostCenter());
            }
        }
        AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(orgId, dto.getAssetId());
        if (depAssetDTO != null && depAssetDTO.getCurrentBookValue() != null) {
            dto.setCurrentValue(depAssetDTO.getCurrentBookValue());
        } else if (detDTO.getAssetPurchaseInformationDTO() != null) {
            dto.setCurrentValue(detDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition());
        }
        dto.setOrgId(orgId);
        return new ModelAndView("AssetRevaluation", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "appRevalAction", method = RequestMethod.POST)
    public ModelAndView revaluationAppAction(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        AssetRevaluationModel asstModel = this.getModel();

        String decision = asstModel.getWorkflowActionDto().getDecision();
        boolean updFlag = asstModel.apprRevaluationAction();
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
        }

        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }
}
