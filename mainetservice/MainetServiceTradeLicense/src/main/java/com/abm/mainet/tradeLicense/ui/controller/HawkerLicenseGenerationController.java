package com.abm.mainet.tradeLicense.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.HawkerLicenseGenerationModel;

@Controller
@RequestMapping("/HawkerLicenseGeneration.html")
public class HawkerLicenseGenerationController extends AbstractFormController<HawkerLicenseGenerationModel> {

    @Resource
    private IFileUploadService fileUpload;

    @Autowired
    private TbOrganisationService tbOrganisationService;

    @Autowired
    private ITradeLicenseApplicationService tradeLicenseApplicationService;

    @Autowired
    private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @Autowired
    private TbApprejMasService tbApprejMasService;

    /**
     * Trade License Approval Form VIew
     * @param httpServletRequest
     * @param applicationId
     * @param taskId
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
    public ModelAndView viewApproval(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
            @RequestParam("workflowId") Long workflowId)
            throws ParseException {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(httpServletRequest);
        final HawkerLicenseGenerationModel model = this.getModel();
        WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setTaskId(taskId);
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        ServiceMaster sm = applicationContext.getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("NHL",
                        wfmass.getOrganisation().getOrgid());
        getModel().setServiceMaster(sm);
     
        this.getModel().setOrgName(tbOrganisationService.findDefaultOrganisation().getONlsOrgname());
        model.setTradeDetailDTO(
                tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
        model.setViewMode(MainetConstants.FlagV);
        return new ModelAndView("HawkerLicenseApproval", MainetConstants.FORM_NAME, model);
    }

    /**
     * Trade License Certificate Form View
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_LICENSE_PRINT)
    public ModelAndView getTradeCertificate(final HttpServletRequest request) {
        this.getModel().bind(request);
        TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
        final HawkerLicenseGenerationModel model = getModel();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
                .getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());

        final List<LookUp> lookupList = CommonMasterUtility.getListLookup("MWZ", org);

        if (masDto.getTrdWard1() != null) {
            model.setTrdWard1Desc(lookupList.get(0).getLookUpDesc());
            model.setWard1Level(CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard1(), org)
                    .getDescLangFirst());
        }
        if (masDto.getTrdWard2() != null) {
            model.setTrdWard2Desc(lookupList.get(0).getLookUpDesc());
            model.setWard2Level(CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard2(), org)
                    .getDescLangFirst());
        }
        if (masDto.getTrdWard3() != null) {
            model.setTrdWard3Desc(lookupList.get(0).getLookUpDesc());
            model.setWard3Level(CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard3(), org)
                    .getDescLangFirst());
            
        }
        if (masDto.getTrdWard4() != null) {
            model.setTrdWard4Desc(lookupList.get(0).getLookUpDesc());
            model.setWard4Level(CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard4(), org)
                    .getDescLangFirst());
        }
        if (masDto.getTrdWard5() != null) {
            model.setTrdWard5Desc(lookupList.get(0).getLookUpDesc());
            model.setWard5Level(CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard5(), org)
                    .getDescLangFirst());
        }

        LookUp lookup = CommonMasterUtility
                .getHierarchicalLookUp(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);
        model.setCategoryDesc(lookup.getDescLangFirst());

        TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
                .getCFCApplicationByApplicationId(masDto.getApmApplicationId(), orgId);
        model.setTradeDetailDTO(tradeMasterDetailDTO);
        Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
        if (licenseEndDate != null) {
            tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
        }
        model.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
        model.setDateDesc(Utility.dateToString(cfcApplicationDetails.getApmApplicationDate()));
        List<CFCAttachment> imgList = checklistVerificationService.getDocumentUploadedByRefNo(
                model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);
        model.setDocumentList(imgList);
        if (!imgList.isEmpty() && imgList != null) {
            model.setImagePath(getPropImages(imgList.get(0)));
        }
        Long artId = 0l;
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpCode().equals(PrefixConstants.WATERMODULEPREFIX.APP)) {
                artId = Lookup1.getLookUpId();
            }
        }
        List<TbApprejMas> apprejMasList = tbApprejMasService.findByRemarkType(model.getServiceMaster().getSmServiceId(), artId);
        model.setApprejMasList(apprejMasList);
        
    
        return new ModelAndView("HawkerLicenseReportApproval", MainetConstants.FORM_NAME,
                this.getModel());
    }

    private String getPropImages(final CFCAttachment attachDocs) {

        new ArrayList<String>();
        final UUID uuid = UUID.randomUUID();
        final String randomUUIDString = uuid.toString();
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + UserSession.getCurrent().getOrganisation().getOrgid()
                + MainetConstants.FILE_PATH_SEPARATOR + randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR
                + "PROPERTY";
        final String path1 = attachDocs.getAttPath();
        final String name = attachDocs.getAttFname();
        final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
                FileNetApplicationClient.getInstance());
        return data;
    }

    /**
     * Used to get Generate License Number
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(params = MainetConstants.TradeLicense.GENERATE_LICENSE_NUMBER, method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        HawkerLicenseGenerationModel model = this.getModel();
     
        LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS", UserSession.getCurrent().getOrganisation());
        
        model.getTradeDetailDTO().setTrdStatus(lookUp.getLookUpId());
        if (model.saveForm()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

        } else
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

    }

}