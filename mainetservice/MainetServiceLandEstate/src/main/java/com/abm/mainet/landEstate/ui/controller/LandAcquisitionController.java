package com.abm.mainet.landEstate.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.landEstate.dao.ILandAcquisitionDao;
import com.abm.mainet.landEstate.dto.LandAcquisitionDto;
import com.abm.mainet.landEstate.service.ILandAcquisitionService;
import com.abm.mainet.landEstate.ui.model.LandAcquisitionModel;

@Controller
@RequestMapping(MainetConstants.LandEstate.LAND_URL)
public class LandAcquisitionController extends AbstractFormController<LandAcquisitionModel> {

    private static final String LOOKUP_NOT_FOUND_FOR_APL = "LookUps not found for Prefix APL for orgId=";

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    ServiceMasterService serviceMaster;

    @Autowired
    ILandAcquisitionService iLandAcquisitionService;

    @Autowired
    ILandAcquisitionDao iLandAcquisitionDao;

    @Autowired
    ServiceMasterRepository serviceRepo;

    @Autowired
    private TbOrganisationService organisationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        final LandAcquisitionModel model = getModel();
        this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        setCommonFields(model);
        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
        this.getModel().setLocationList(locationList);
        List<String> proposalNoList = iLandAcquisitionService.fetchProposalNoList();
        this.getModel().setProposalNoList(proposalNoList);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // D#78185
        List<LandAcquisitionDto> landAcquisitionList = iLandAcquisitionService.fetchSearchData(null, null,
                /* MainetConstants.LandEstate.LandAcquisition.ACQUIRED_STATUS */null, null, orgId);
        this.getModel().setLandAcquisitionList(landAcquisitionList);
        return defaultResult();
    }

    private void setCommonFields(final LandAcquisitionModel model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long deptId = -1L;
        model.setOrgId(orgId);
        model.setCommonHelpDocs(MainetConstants.LandEstate.LAND_URL);
        model.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        model.setLangId((long) UserSession.getCurrent().getLanguageId());
        final ServiceMaster service = serviceMaster
                .getServiceByShortName(MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE, orgId);
        model.setServiceMaster(service);
        if (service != null) {
            model.setServiceId(service.getSmServiceId());
            model.setServiceName(service.getSmServiceName());
            deptId = service.getTbDepartment().getDpDeptid();
        }
        model.setDeptId(deptId);

    }

    @ResponseBody
    @RequestMapping(params = "searchAcqData", method = RequestMethod.POST)
    public List<LandAcquisitionDto> searchAcqData(@RequestParam(value = "proposalNo") final String proposalNo,
            @RequestParam(value = "payTo") final String payTo, @RequestParam(value = "acqStatus") final String acqStatus,
            @RequestParam(value = "locId") final Long locId,
            final HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<LandAcquisitionDto> landAcquisitionDto = iLandAcquisitionService.fetchSearchData(proposalNo, payTo, acqStatus,
                locId, orgId);
        return landAcquisitionDto;

    }

    @RequestMapping(params = "addLandAcq", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addLandAcq(final HttpServletRequest request) {
        getModel().bind(request);
        fileUpload.sessionCleanUpForFileUpload();
        final LandAcquisitionModel model = getModel();
        setCommonFields(model);
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        // check here checklist applicable or not by using service master
        // String checklistFlag = null;
        // final List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL",
        // org);
        // for (final LookUp lookUp : lookUps) {
        // if (serviceMaster.getSmChklstVerify() != null
        // && lookUp.getLookUpId() == serviceMaster.getSmChklstVerify().longValue()) {
        // checklistFlag = lookUp.getLookUpCode();
        // break;
        // }
        // }
        // if (MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
        // applicationData.setIsCheckListApplicable(true);
        // }

        // or another way to check checklist applicable or not
        Boolean isChecklistApplicable = isChecklistApplicable(MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE,
                model.getOrgId());

        model.setIsChecklistApplicable(isChecklistApplicable);

        return new ModelAndView("addLandAcquisition", MainetConstants.FORM_NAME, model);
    }

    // handler for view land acquisition data
    @RequestMapping(params = "viewLandAcqData", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewLandAcqData(@RequestParam("lnaqId") final Long lnaqId,
            @RequestParam("apmApplicationId") final Long apmApplicationId,
            final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        this.getModel().populateApplicationData(apmApplicationId);
        return new ModelAndView("addLandAcquisition", MainetConstants.FORM_NAME, this.getModel());

    }

    // method for check checklist applicable or not
    public boolean isChecklistApplicable(String serviceShortCode, long orgId) {
        Long smCheckListVerifyId = serviceRepo.isCheckListApplicable(serviceShortCode, orgId);
        if (smCheckListVerifyId == null || smCheckListVerifyId == 0) {
            throw new NullPointerException("No record found from TB_SERVICES_MST for serviceShortCode="
                    + serviceShortCode + "and orgId=" + orgId + "OR sm_chklst_verify column found null");
        }
        String flag = StringUtils.EMPTY;

        // get APL prefix from default organization
        Organisation defaultOrg = organisationService.findDefaultOrganisation();
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.ChargeApplicableAt.APPLICATION, defaultOrg);
        if (lookUps == null || lookUps.isEmpty()) {
            throw new NullPointerException(
                    LOOKUP_NOT_FOUND_FOR_APL + defaultOrg.getONlsOrgname() + ", orgId:" + defaultOrg.getOrgid());
        }
        for (LookUp lookUp : lookUps) {
            if (lookUp.getLookUpId() == smCheckListVerifyId) {
                flag = lookUp.getLookUpCode();
                break;
            }
        }
        if (flag.isEmpty()) {
            throw new IllegalArgumentException(
                    "conflicts! Prefix APL ids does'nt match to id found from TB_SERVICES_MST");
        }

        return MainetConstants.APPLICABLE.equalsIgnoreCase(flag) ? true : false;
    }

    @RequestMapping(params = "landValuation", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView landValuation(final HttpServletRequest request) {
        getModel().bind(request);
        final ScrutinyLableValueDTO lableDTO = getModel().getLableValueDTO();
        final UserSession session = UserSession.getCurrent();
        final Long appId = Long.valueOf(request.getParameter("applId"));
        final Long serviceId = Long.valueOf(request.getParameter("serviceId"));
        final Long lableId = Long.valueOf(request.getParameter("labelId"));
        final String lableValue = request.getParameter("labelVal");
        final Long level = Long.valueOf(request.getParameter("level"));
        this.getModel().setServiceId(serviceId);
        this.getModel().setApmApplicationId(appId);
        lableDTO.setApplicationId(appId);
        lableDTO.setUserId(session.getEmployee().getEmpId());
        lableDTO.setOrgId((session.getOrganisation().getOrgid()));
        lableDTO.setLangId((long) session.getLanguageId());
        lableDTO.setLableId(lableId);
        lableDTO.setLableValue(lableValue);
        lableDTO.setLevel(level);
        this.getModel().setAccountIntegrateBT(false);
        ModelAndView mv = new ModelAndView("landValuation", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("handler", "valuation");
        return mv;

    }

    /**
     * this request mapping used when land valuation form submit from scrutiny labels
     */
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveLandValuation(final HttpServletRequest request) {
        bindModel(request);

        // update cost and vendorId in TB_EST_AQUISN table
        try {

            // business logic write here
            BigDecimal acqCost = this.getModel().getAcquisitionDto().getAcqCost();
            Long vendorId = this.getModel().getAcquisitionDto().getVendorId();
            Long apmApplicationId = this.getModel().getAcquisitionDto().getApmApplicationId();
            // this.getModel().accountIntegrate();
            iLandAcquisitionService.updateLandValuationData(apmApplicationId, acqCost, vendorId, null, null, null, null,
                    UserSession.getCurrent().getOrganisation().getOrgid());

        } catch (final Exception ex) {
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "payableLoiPosting", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView payableLoiPosting(final HttpServletRequest request) {
        getModel().bind(request);
        final ScrutinyLableValueDTO lableDTO = getModel().getLableValueDTO();
        final UserSession session = UserSession.getCurrent();
        final Long appId = Long.valueOf(request.getParameter("applId"));
        final Long serviceId = Long.valueOf(request.getParameter("serviceId"));
        final Long lableId = Long.valueOf(request.getParameter("labelId"));
        final String lableValue = request.getParameter("labelVal");
        final Long level = Long.valueOf(request.getParameter("level"));
        this.getModel().setServiceId(serviceId);
        this.getModel().setApmApplicationId(appId);
        lableDTO.setApplicationId(appId);
        lableDTO.setUserId(session.getEmployee().getEmpId());
        lableDTO.setOrgId((session.getOrganisation().getOrgid()));
        lableDTO.setLangId((long) session.getLanguageId());
        lableDTO.setLableId(lableId);
        lableDTO.setLableValue(lableValue);
        lableDTO.setLevel(level);

        this.getModel().setAccountIntegrateBT(false);
        if (this.getModel().getAcquisitionDto().getAcqCost() != null
                && this.getModel().getAcquisitionDto().getAcqCost().compareTo(BigDecimal.ZERO) != 0) {
            // also check account module live or not
            // LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
            LookUp lookup = CommonMasterUtility
                    .getLookUpFromPrefixLookUpValue(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

            if (lookup != null) {
                String accountCode = lookup.getLookUpCode();
                if (lookup.getDefaultVal().equals(MainetConstants.FlagY)
                        && accountCode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
                    this.getModel().setAccountIntegrateBT(true);
                }
            }
        }
        // check for hide accountIntegrate BT
        // ask to sir this validation is valid or not if valid than problem occur when land valuation edit AMT and vendor
        // than account integration code not update because we restrict BT based on billNo is null to not
        /*
         * if (this.getModel().getAcquisitionDto().getLnBillNo() != null) { this.getModel().setAccountIntegrateBT(false); }
         */

        ModelAndView mv = new ModelAndView("landValuation", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("handler", "account");
        return mv;
    }

    /**
     * this request mapping used when account integrate
     */
    @RequestMapping(params = "accountIntegrate", method = RequestMethod.POST)
    public ModelAndView accountIntegrate(final HttpServletRequest request) {
        bindModel(request);
        try {
            // account integration code
            Long serviceId = this.getModel().getServiceId();
            Long apmApplicationId = this.getModel().getApmApplicationId();
            List<TbLoiMas> loiData = ApplicationContextProvider.getApplicationContext().getBean(TbLoiMasService.class)
                    .getloiByApplicationId(apmApplicationId, serviceId, UserSession.getCurrent().getOrganisation().getOrgid());
            if (loiData == null) {
                return jsonResult(JsonViewObject.failureResult("First LOI No generate"));
            }

            // Double check account module live or not
            LookUp lookup = CommonMasterUtility
                    .getLookUpFromPrefixLookUpValue(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

            if (lookup != null) {
                String accountCode = lookup.getLookUpCode();
                if (lookup.getDefaultVal().equals(MainetConstants.FlagY) && accountCode.equals(MainetConstants.FlagL)) {
                    this.getModel().accountIntegrate(loiData.get(0).getLoiNo());
                    return jsonResult(JsonViewObject.successResult("land.acq.loi.posting.success"));
                } /*
                   * else { return jsonResult(JsonViewObject.failureResult("account module not live")); }
                   */
            }
        } catch (final Exception ex) {
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return defaultMyResult();
    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        this.getModel().bind(httpServletRequest);
        if (getModel().saveForm()) {
            return jsonResult(
                    JsonViewObject.successResult(getApplicationSession().getMessage(this.getModel().getSuccessMessage())));
        }
        return defaultMyResult();
    }

    // below handler use or not ask to SAMADHAN sir if use than checklist CONFIG. for sir
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.LandEstate.GET_CHECKLIST_AND_CHARGES)
    public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        ModelAndView mv = null;
        final LandAcquisitionModel model = getModel();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        model.setIsChecklistApplicable(false);// trial test

        // [START] BRMS call initialize model
        final WSRequestDTO requestDTO = new WSRequestDTO();
        requestDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
        WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
        // List<DocumentDetailsVO> checkListList = new ArrayList<>();
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);

            final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);

            populateCheckListModel(model, checkListModel2);
            // checkListList = checklistAndChargeService.getChecklist(checkListModel2);
            WSRequestDTO checklistReqDto = new WSRequestDTO();
            checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
            checklistReqDto.setDataModel(checkListModel2);
            WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
                    || MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

                if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
                    List<DocumentDetailsVO> checkListList = Collections.emptyList();
                    // final List<?> docs = RestClient.castResponse(checklistRespDto,
                    // DocumentDetailsVO.class);
                    checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

                    long cnt = 1;
                    for (final DocumentDetailsVO doc : checkListList) {
                        doc.setDocumentSerialNo(cnt);
                        cnt++;
                    }
                    if ((checkListList != null) && !checkListList.isEmpty()) {
                        model.setCheckList(checkListList);
                    }
                    mv = new ModelAndView("LandAcquisitionValidn", MainetConstants.FORM_NAME, getModel());
                }
                // checklist done

                // code write below if charges apply
                // refer change of usage or any other services

            } else {
                mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
            }
        }

        // [END]
        else {
            mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
        }
        // [END]

        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    private void populateCheckListModel(final LandAcquisitionModel model, final CheckListModel checklistModel) {
        checklistModel.setOrgId(model.getOrgId());
        checklistModel.setServiceCode(MainetConstants.LandEstate.LandAcquisition.SERVICE_SHOT_CODE);
        checklistModel.setDeptCode(MainetConstants.LandEstate.LandEstateCode);

        final Organisation org = UserSession.getCurrent().getOrganisation();

    }

    @ResponseBody
    @RequestMapping(params = "assetIntegrate", method = RequestMethod.POST)
    public String assetIntegrate(final HttpServletRequest request,
            @RequestParam(name = "apmApplicationId") final Long apmApplicationId,
            @RequestParam(name = "lnaqId") final Long lnaqId) {
        bindModel(request);
        String pushInAsset = "Y";
        // code for asset integrate
        // again validation check Asset module is Live or not
        // CODE FAS IS FIXED ASSET
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.LandEstate.ASSET_CODE_VALUE,
                MainetConstants.LandEstate.MODULE_LIVE_INTEGRATION,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        if (lookUp != null && lookUp.getOtherField() != null && lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
            String assetIntegrateMSG = this.getModel().assetIntegrate(apmApplicationId, lnaqId);
            if (!assetIntegrateMSG.equalsIgnoreCase(MainetConstants.SUCCESS_MSG)) {
                pushInAsset = assetIntegrateMSG;
            }
        } else {
            pushInAsset = MainetConstants.LandEstate.ASSET_NOT_LIVE_MSG;
        }
        return pushInAsset;
    }

}
