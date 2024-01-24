/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.abm.mainet.asset.ui.dto.RetirementDTO;
import com.abm.mainet.asset.ui.model.AssetRetirementModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_RETIRE_PATH, "ITAssetRetirement.html" })
public class AssetRetirementController extends AbstractFormController<AssetRetirementModel> {

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
    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private IFileUploadService fileUpload;
    @Autowired
    private IAssetValuationService valuationService;
    @Resource
    private BankMasterService bankMasterService;
    @Resource
    private TbTaxMasService tbTaxMasService;
    @Resource
    private TbDepartmentService tbDepartmentService;

    @Autowired
    IMaintenanceService maintenanceService;

    private static final Logger LOGGER = Logger.getLogger(AssetRetirementController.class);

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        return index();
    }

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView retirementForm(final Model model,
            @RequestParam(value = MainetConstants.AssetManagement.AST_ID, required = false) final Long assetId,
            final HttpServletRequest request) {
        sessionCleanup(request);
        long langId = UserSession.getCurrent().getLanguageId();
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(assetId);
        final Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        AssetRetirementModel retireModel = this.getModel();
        final RetirementDTO dto = retireModel.getRetireDTO();
        retireModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        retireModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(request));
        List<LookUp> taxBankAccHead = tbTaxMasService.getAllTaxesBasedOnDept(orgId,
                tbDepartmentService.getDepartmentIdByDeptCode(UserSession.getCurrent().getModuleDeptCode()));
        if (taxBankAccHead != null) {
            retireModel.setTaxMasAccHead(taxBankAccHead);
        } else {
            retireModel.setTaxMasterError("Tax Master Not Configured Correctly");
            LOGGER.error("Your Tax master is not configure properly thats why that page cant be open");
            // throw new FrameworkException("Your Tax master is not configure properly");
        }
        dto.setAssetId(detDTO.getAssetInformationDTO().getAssetId());
        dto.setSerialNo(detDTO.getAssetInformationDTO().getAstCode());
        dto.setAssetClass(detDTO.getAssetInformationDTO().getAssetClass2());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(detDTO.getAssetInformationDTO().getAssetClass2(), organisation);
        if (langId == 1) {
            dto.setAssetClassDesc(lookUp.getDescLangFirst());
        } else {
            dto.setAssetClassDesc(lookUp.getDescLangSecond());
        }

        dto.setDescription(detDTO.getAssetInformationDTO().getDetails());
        if (detDTO.getAssetClassificationDTO() != null) {
        	if( UserSession.getCurrent().getModuleDeptCode() != MainetConstants.ITAssetManagement.IT_ASSET_CODE) {
        		 dto.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
                 dto.setDepartmentDesc(
                         iTbDepartmentService.findById(detDTO.getAssetClassificationDTO().getDepartment()).getDpDeptdesc());
        	}
           
            if (StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
                dto.setCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
                dto.setCostCenterDesc(detDTO.getAssetClassificationDTO().getCostCenter());
            }
            if (detDTO.getAssetClassificationDTO().getLocation() != null && UserSession.getCurrent().getModuleDeptCode() != MainetConstants.ITAssetManagement.IT_ASSET_CODE) {
                dto.setLocationDesc(iLocationMasService
                        .findById(detDTO.getAssetClassificationDTO().getLocation()).getLocNameEng());
                dto.setLocation(detDTO.getAssetClassificationDTO().getLocation());
            }
        }
        if (detDTO.getAssetPurchaseInformationDTO() != null) {
            dto.setAmount(detDTO.getAssetPurchaseInformationDTO().getCostOfAcquisition());
        }

        AssetValuationDetailsDTO valuationDTO = valuationService.findLatestAssetId(orgId, assetId);
        if (valuationDTO == null) {
            AssetPurchaseInformationDTO purDTO = detDTO.getAssetPurchaseInformationDTO();
            if (purDTO != null &&
                    purDTO.getInitialBookValue() != null) {
                dto.setCurrentValue(purDTO.getInitialBookValue());
            } else if (purDTO != null &&
                    purDTO.getCostOfAcquisition() != null) {
                dto.setCurrentValue(purDTO.getCostOfAcquisition());
            }
        } else {
            dto.setCurrentValue(valuationDTO.getCurrentBookValue());
        }

        dto.setOrgId(orgId);
        retireModel.setPaymode(UserSession.getCurrent().getPaymentMode());
        retireModel.setBankList(bankMasterService.getBankList());
        this.getModel().setCommonHelpDocs("AssetSearch.html");

        /* check account module is live or not */
        if (maintenanceService.checkAccountActiveOrNot()) {
            this.getModel().setAccountLive(MainetConstants.Common_Constant.YES);
        }
        return new ModelAndView("AssetRetirement", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showRetirementApproval(@RequestParam("appNo") final String serialNoModified,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        long langId = UserSession.getCurrent().getLanguageId();
        AssetRetirementModel retireModel = this.getModel();
        retireModel.setTaskId(actualTaskId);
        retireModel.getWorkflowActionDto().setReferenceId(serialNoModified);
        retireModel.getWorkflowActionDto().setTaskId(actualTaskId);
        // load new information as recorded in the transfer request
        retireModel.loadRetirementDetails();
        model.addAttribute("mode", "APR");
        final RetirementDTO retireDTO = retireModel.getRetireDTO();
        final AssetDetailsDTO detDTO = astService.getDetailsByAssetId(retireDTO.getAssetId());
        final Long orgId = detDTO.getAssetInformationDTO().getOrgId();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        final RetirementDTO dto = retireModel.getRetireDTO();
        retireModel.getAudit().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        retireModel.getAudit().setEmpIpMac(Utility.getClientIpAddress(httpServletRequest));
        retireModel.setTaxMasAccHead(tbTaxMasService.getAllTaxesBasedOnDept(orgId,
                tbDepartmentService.getDepartmentIdByDeptCode(UserSession.getCurrent().getModuleDeptCode())));
        dto.setAssetId(detDTO.getAssetInformationDTO().getAssetId());
        dto.setSerialNo(detDTO.getAssetInformationDTO().getAstCode());
        dto.setAssetClass(detDTO.getAssetInformationDTO().getAssetClass2());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(detDTO.getAssetInformationDTO().getAssetClass2(), organisation);
        if (langId == 1) {
            dto.setAssetClassDesc(
                    lookUp.getDescLangFirst());
        } else {
            dto.setAssetClassDesc(
                    lookUp.getDescLangSecond());

        }
        dto.setDescription(detDTO.getAssetInformationDTO().getDetails());
        if (detDTO.getAssetClassificationDTO() != null) {
            dto.setDepartment(detDTO.getAssetClassificationDTO().getDepartment());
            dto.setDepartmentDesc(
                    iTbDepartmentService.findById(detDTO.getAssetClassificationDTO().getDepartment()).getDpDeptdesc());
            if (StringUtils.isNotBlank(detDTO.getAssetClassificationDTO().getCostCenter())) {
                dto.setCostCenter(detDTO.getAssetClassificationDTO().getCostCenter());
                dto.setCostCenterDesc(detDTO.getAssetClassificationDTO().getCostCenter());
                // D#85383
                if (detDTO.getAssetClassificationDTO().getLocation() != null) {
                    dto.setLocationDesc(
                            iLocationMasService.findById(detDTO.getAssetClassificationDTO().getLocation()).getLocNameEng());
                }
                dto.setLocation(detDTO.getAssetClassificationDTO().getLocation());
            }
        }

        dto.setOrgId(orgId);
        retireModel.setPaymode(UserSession.getCurrent().getPaymentMode());
        retireModel.setBankList(bankMasterService.getBankList());
        return new ModelAndView("AssetRetirement", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "appRtrAction", method = RequestMethod.POST)
    public ModelAndView transferAppAction(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        AssetRetirementModel asstModel = this.getModel();

        String decision = asstModel.getWorkflowActionDto().getDecision();
        boolean updFlag = asstModel.apprRetirementAction(asstModel.getRetireDTO().getDeptCode());
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
