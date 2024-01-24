package com.abm.mainet.mrm.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MRMRateMaster;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.dto.WitnessDetailsDTO;
import com.abm.mainet.mrm.service.IBRMSMRMService;
import com.abm.mainet.mrm.service.IMarriageService;
import com.abm.mainet.mrm.ui.model.MarriageRegistrationModel;

@Controller
@RequestMapping(value = { MainetConstants.MRM.MAR_REG_URL, "MarChecklistApproval.html" })
public class MarriageRegistrationController extends AbstractFormController<MarriageRegistrationModel> {

    private static final Logger LOGGER = Logger.getLogger(MarriageRegistrationController.class);

    @Autowired
    IMarriageService marriageService;

    @Resource
    IFileUploadService fileUpload;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ServiceMasterService smService;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @Autowired
    IBRMSMRMService BRMSMRMService;

    @Autowired
    private IChecklistSearchService checklistSearchService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    private static final String MARRIAGE = "Marriage";
    private static final String HUSBAND = "Husband";
    private static final String WIFE = "Wife";
    private static final String WITNESS = "Witness";
    private static final String APPOINTMENT = "Appointment";

    private static final String RESET_OPTION = "resetOption";
    private static final String RESET = "reset";
    private static final String MAR_CHECKLIST_APP = "MarChecklistApproval";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        MarriageRequest mrgReq = new MarriageRequest();
        mrgReq.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
                UserSession.getCurrent().getOrganisation());
        // D#128597
        Boolean skdclENVPresent = envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.SKDCL)
                        && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        if (skdclENVPresent) {
            this.getModel().setApplicableENV(true);
        } else {
            this.getModel().setApplicableENV(false);
        }
        mrgReq.setSkdclENVPresent(skdclENVPresent);
        List<MarriageDTO> marriageDTOs = marriageService.fetchSearchData(mrgReq);
        List<HusbandDTO> husbandList = new ArrayList<>();
        marriageDTOs.forEach(marriageDTO -> {
            husbandList.add(marriageDTO.getHusbandDTO());
        });
        this.getModel().setHusbandList(husbandList);
        this.getModel().setMarriageDTOs(marriageDTOs);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchMarriageData", method = RequestMethod.POST)
    public List<MarriageDTO> searchMarriageData(@RequestParam("marDate") final Date marriageDate,
            @RequestParam("appDate") final Date appDate,
            @RequestParam("husbandId") final Long husbandId,
            @RequestParam("status") final String status, final HttpServletRequest request) {
        getModel().bind(request);
        MarriageRequest mrgReq = new MarriageRequest();
        mrgReq.setMarriageDate(marriageDate);
        mrgReq.setAppDate(appDate);
        mrgReq.setHusbandId(husbandId);
        mrgReq.setStatus(status);
        mrgReq.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        mrgReq.setSkdclENVPresent(this.getModel().getApplicableENV());
        return marriageService.fetchSearchData(mrgReq);
    }

    /*
     * this handler used for opening form as add/view/draft Mode
     */
    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView marriageRegForm(
            @RequestParam(value = "marId", required = false) Long marId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            @RequestParam(value = "saveMode", required = false) String saveMode, final Model model,
            HttpServletRequest request) {

        Long taskId = this.getModel().getWorkflowActionDto().getTaskId();
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        MarriageRegistrationModel marriageModel = this.getModel();
        marriageModel.setCommonHelpDocs("MarriageRegistration.html");
        // marriageModel.setModeType(MainetConstants.MODE_CREATE);
        marriageModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().populateModel(marriageModel, marId, null, type);
        marriageModel.getMarriageDTO().setMarId(marId);
        ServiceMaster serviceMaster = smService.getServiceByShortName(
                MainetConstants.MRM.SERVICE_CODE.MRG,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setServiceMaster(serviceMaster);
        this.getModel().getMarriageDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
        this.getModel().getWorkflowActionDto().setTaskId(taskId);
        // Checking condition whether checkList applicable for particular Service.
        if (StringUtils
                .equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmChklstVerify(),
                        UserSession.getCurrent().getOrganisation()).getLookUpCode(), MainetConstants.FlagA)) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
        } else {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
        }
        if (StringUtils.equalsIgnoreCase(serviceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagY);
            this.getModel().getMarriageDTO().setFree(false);
        } else {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
            this.getModel().getMarriageDTO().setFree(true);
        }
        marriageModel.setApprovalProcess("N");
        if (StringUtils.isEmpty(saveMode)) {
            this.getModel().setStatus(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
        } else if (saveMode.equals("A")) {
            this.getModel().setStatus("APPROVED");
            marriageModel.getMarriageDTO().setCustomField("DIS_MAR_DATE");
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
            marriageModel.setWitnessModeType("N");
        }
        switch (type) {
        case "V":
            marriageModel.setModeType(MainetConstants.MODE_VIEW);
            marriageModel.setWitnessModeType("N");
            return new ModelAndView("MarriageRegView", MainetConstants.FORM_NAME, marriageModel);
        case "D":
            // draft mode view page use with modeType=D
            marriageModel.setModeType(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
            // D#133974 applnCopyTo radio button set
            /*
             * String firstName = ""; if (marriageModel.getMarriageDTO().getHusbandDTO() != null) { firstName =
             * marriageModel.getMarriageDTO().getHusbandDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName) &&
             * firstName .equalsIgnoreCase(marriageModel.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
             * marriageModel.getMarriageDTO().setApplnCopyTo("H"); } else if (marriageModel.getMarriageDTO().getWifeDTO() != null)
             * { firstName = marriageModel.getMarriageDTO().getWifeDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName)
             * && firstName .equalsIgnoreCase(marriageModel.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
             * marriageModel.getMarriageDTO().setApplnCopyTo("W"); } else { marriageModel.getMarriageDTO().setApplnCopyTo("O"); }
             * } }
             */

            return new ModelAndView("MarriageRegView", MainetConstants.FORM_NAME, marriageModel);
        default:
            marriageModel.getMarriageDTO().setApplnCopyTo("O");
            return new ModelAndView("MarriageRegForm", MainetConstants.FORM_NAME, marriageModel);
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "showMarriagePage")
    public ModelAndView showMarriagePage(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();

        if (resetOption != null && resetOption.trim().equals(RESET)) {
            model.getMarriageDTO().setApplicantDetailDto(new ApplicantDetailDTO());
            // model.getMarriageDTO().setApplicationId(null);
            model.getMarriageDTO().setCreatedBy(null);
            model.getMarriageDTO().setCreatedDate(null);
            model.getMarriageDTO().setLgIpMac(null);
            model.getMarriageDTO().setLgIpMacUpd(null);
            model.getMarriageDTO().setMarDate(null);
            model.getMarriageDTO().setOrgId(null);
            model.getMarriageDTO().setPersonalLaw(null);
            model.getMarriageDTO().setPlaceMarE(null);
            model.getMarriageDTO().setPlaceMarR(null);
            model.getMarriageDTO().setPriestAddress(null);
            model.getMarriageDTO().setPriestAge(0);
            model.getMarriageDTO().setPriestNameE(null);
            model.getMarriageDTO().setPriestNameR(null);
            model.getMarriageDTO().setPlaceMarR(null);
            model.getMarriageDTO().setPriestReligion(null);
            model.getMarriageDTO().setStatus(null);
            model.getMarriageDTO().setUrlParam(null);
        } else {
            // D#133974 applnCopyTo radio button set
            /*
             * String firstName = ""; if (model.getMarriageDTO().getHusbandDTO() != null) { firstName =
             * model.getMarriageDTO().getHusbandDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName) &&
             * firstName.equalsIgnoreCase(model.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
             * model.getMarriageDTO().setApplnCopyTo("H"); } else if (model.getMarriageDTO().getWifeDTO() != null) { firstName =
             * model.getMarriageDTO().getWifeDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName) && firstName
             * .equalsIgnoreCase(model.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
             * model.getMarriageDTO().setApplnCopyTo("W"); } else { model.getMarriageDTO().setApplnCopyTo("O"); } } }
             */
        }
        return new ModelAndView(MARRIAGE, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveMarriagePage")
    public ModelAndView saveMarriagePage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        BindingResult bindingResult = marriageModel.getBindingResult();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView mv = null;
        if (marriageModel.validateInputs()) {
            // save in TB_MRM_MARRIAGE table
            if (marriageDTO.getAppDate() == null) {
                // set SYS date
                marriageDTO.setAppDate(
                        Utility.stringToDate(Utility.dateToString(new Date()), MainetConstants.DATE_FORMAT));
            }
            marriageDTO.setOrgId(orgId);
            if (marriageModel.getStatus().equals("APPROVED")) {
                marriageDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                marriageDTO.setUpdatedDate(new Date());
                marriageDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            } else {
                marriageDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                marriageDTO.setCreatedDate(new Date());
                marriageDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            }

            marriageDTO.setServiceId(this.getModel().getServiceMaster().getSmServiceId());
            marriageDTO.setDeptId(this.getModel().getServiceMaster().getTbDepartment().getDpDeptid());
            marriageDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            marriageDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            marriageDTO.setStatus(this.getModel().getStatus());
            marriageDTO.setUrlParam(MainetConstants.MRM.SHOW_MRG_Page);
            marriageDTO.setUrlShortCode(MainetConstants.MRM.MRG_URL_CODE);
            if (marriageDTO.getApplicationId() != null && marriageDTO.getMarId() == null) {
                // check here application id already present or not in tb_mrm_marriage table
                // than throw framework exception
                Boolean isExist = marriageService.checkApplicationIdExist(marriageDTO.getApplicationId(), orgId);
                if (isExist) {
                    LOGGER.info("YASH ISSUE " + marriageDTO.getApplicationId());
                    throw new FrameworkException(
                            "Exception occured duplicate Application Id  Please Try Again");
                }

            }

            marriageService.saveMarriageRegInDraftMode(marriageDTO);
            if (!marriageDTO.getErrorList().isEmpty()) {

                getModel().addValidationError(getApplicationSession()
                        .getMessage(marriageDTO.getErrorList().get(0)));

                mv = new ModelAndView("MarriageValidn", MainetConstants.FORM_NAME, getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;

            }
            // D#127349
            if (StringUtils.isNotBlank(marriageDTO.getApplnCopyTo()) && marriageDTO.getApplnCopyTo().equals("H")) {
                marriageDTO.getHusbandDTO().setFirstNameEng(marriageDTO.getApplicantDetailDto().getApplicantFirstName());
                marriageDTO.getHusbandDTO().setMiddleNameEng(marriageDTO.getApplicantDetailDto().getApplicantMiddleName());
                marriageDTO.getHusbandDTO().setLastNameEng(marriageDTO.getApplicantDetailDto().getApplicantLastName());
                if (StringUtils.isNotBlank(marriageDTO.getApplicantDetailDto().getAadharNo())) {
                    marriageDTO.getHusbandDTO().setUidNo(marriageDTO.getApplicantDetailDto().getAadharNo());
                }

                // address set
                marriageDTO.getHusbandDTO().setFullAddrEng(marriageDTO.getApplicantDetailDto().getAreaName() + " "
                        + marriageDTO.getApplicantDetailDto().getVillageTownSub() + " "
                        + marriageDTO.getApplicantDetailDto().getRoadName());

            } else if (StringUtils.isNotBlank(marriageDTO.getApplnCopyTo()) && marriageDTO.getApplnCopyTo().equals("W")) {
                marriageDTO.getWifeDTO().setFirstNameEng(marriageDTO.getApplicantDetailDto().getApplicantFirstName());
                marriageDTO.getWifeDTO().setMiddleNameEng(marriageDTO.getApplicantDetailDto().getApplicantMiddleName());
                marriageDTO.getWifeDTO().setLastNameEng(marriageDTO.getApplicantDetailDto().getApplicantLastName());
                if (StringUtils.isNotBlank(marriageDTO.getApplicantDetailDto().getAadharNo())) {
                    marriageDTO.getWifeDTO().setUidNo(marriageDTO.getApplicantDetailDto().getAadharNo());
                }

                // address set
                marriageDTO.getWifeDTO().setFullAddrEng(marriageDTO.getApplicantDetailDto().getAreaName() + " "
                        + marriageDTO.getApplicantDetailDto().getVillageTownSub() + " "
                        + marriageDTO.getApplicantDetailDto().getRoadName());
            }

            this.getModel().getMarriageDTO().setMarId(marriageDTO.getMarId());
            marriageModel.getMarriageDTO().setMarId(marriageDTO.getMarId());
            mv = new ModelAndView(HUSBAND, MainetConstants.FORM_NAME, marriageModel);

        } else {
            mv = new ModelAndView("MarriageValidn", MainetConstants.FORM_NAME, getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showHusbandPage")
    public ModelAndView showHusbandPage(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();

        if (resetOption != null && resetOption.trim().equals(RESET)) {
            model.getMarriageDTO().setHusbandDTO(new HusbandDTO());
            getModel().setPhotoId(null);
            getModel().setThumbId(null);
        } else {
            getModel().setPhotoId(90L);
            getModel().setThumbId(91L);
            getModel().setUploadType("H");
            getModel().setUploadedfile(getModel().getCachePathUpload("H"));
            model.getMarriageDTO().getHusbandDTO()
                    .setCapturePhotoPath(getModel().getUploadedfile().size() >= 1 ? getModel().getUploadedfile().get(0l)
                            : "");
            model.getMarriageDTO().getHusbandDTO()
                    .setCaptureFingerprintPath(getModel().getUploadedfile().size() >= 1 ? getModel().getUploadedfile().get(1l)
                            : "");
        }

        return new ModelAndView(HUSBAND, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveHusbandPage")
    public ModelAndView saveHusbandPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        BindingResult bindingResult = marriageModel.getBindingResult();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();
        Long marId = marriageDTO.getMarId();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        HusbandDTO husbandDTO = marriageDTO.getHusbandDTO();
        ModelAndView mv = null;
        // validateConstraints(marriageDTO, MarriageDTO.class, bindingResult);

        if (!bindingResult.hasErrors()) {
            // save in TB_MRM_HUSBAND table
            husbandDTO.setMarId(marriageDTO);
            husbandDTO.setOrgId(orgId);
            if (getModel().getStatus().equals("APPROVED")) {
                husbandDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                husbandDTO.setUpdatedDate(new Date());
                husbandDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

            } else {
                husbandDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                husbandDTO.setCreatedDate(new Date());
                husbandDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            }

            marriageDTO.getHusbandDTO().getMarId().setMarId(marId);
            marriageDTO.setUrlParam(MainetConstants.MRM.SHOW_HUS_Page);
            marriageDTO.setUrlShortCode(MainetConstants.MRM.HUS_URL_CODE);
            marriageDTO.setUploadMap(marriageModel.getUploadMap());

            marriageService.saveMarriageRegInDraftMode(marriageDTO);

            mv = new ModelAndView(WIFE, MainetConstants.FORM_NAME, marriageModel);

        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, marriageModel);
            mv = new ModelAndView(HUSBAND, MainetConstants.FORM_NAME, marriageModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showWifePage")
    public ModelAndView showWifePage(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            model.getMarriageDTO().setWifeDTO(new WifeDTO());
            getModel().setPhotoId(null);
            getModel().setThumbId(null);
        } else {
            getModel().setPhotoId(990L);
            getModel().setThumbId(991L);
            getModel().setUploadType("W");
            getModel().setUploadedfile(getModel().getCachePathUpload("W"));
        }

        return new ModelAndView(WIFE, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveWifePage")
    public ModelAndView saveWifePage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        BindingResult bindingResult = marriageModel.getBindingResult();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();
        Long marId = marriageDTO.getMarId();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        WifeDTO wifeDTO = marriageDTO.getWifeDTO();

        ModelAndView mv = null;
        // validateConstraints(marriageDTO, MarriageDTO.class, bindingResult);

        if (!bindingResult.hasErrors()) {
            // save in TB_MRM_WIFE table
            wifeDTO.setMarId(marriageDTO);
            wifeDTO.setOrgId(orgId);

            if (getModel().getStatus().equals("APPROVED")) {
                wifeDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                wifeDTO.setUpdatedDate(new Date());
                wifeDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

            } else {
                wifeDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                wifeDTO.setCreatedDate(new Date());
                wifeDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
            marriageDTO.getWifeDTO().getMarId().setMarId(marId);
            marriageDTO.setUrlParam(MainetConstants.MRM.SHOW_WIFE_Page);
            marriageDTO.setUrlShortCode(MainetConstants.MRM.WIFE_URL_CODE);
            marriageDTO.setUploadMap(marriageModel.getUploadMap());
            marriageService.saveMarriageRegInDraftMode(marriageDTO);

            mv = new ModelAndView(WITNESS, MainetConstants.FORM_NAME, marriageModel);

        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, marriageModel);
            mv = new ModelAndView(WIFE, MainetConstants.FORM_NAME, marriageModel);
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "showWitnessPage")
    public ModelAndView showWitnessPage(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();
        // D#108556 set no of witness using NOW prefix
        model.getMarriageDTO().setNoOfWitness(2L);
        LookUp nowPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(MainetConstants.MRM.NOW_DESC,
                MainetConstants.MRM.NOW_PREFIX, 1, UserSession.getCurrent().getOrganisation());
        if (nowPrefix != null) {
            model.getMarriageDTO().setNoOfWitness(Long.parseLong(nowPrefix.getLookUpCode()));
        }
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            model.setWitnessDTO(new WitnessDetailsDTO()); // Create a blank Witness DTO object
            model.getMarriageDTO().setWitnessDetailsDTO(new ArrayList<WitnessDetailsDTO>()); // Create an empty list of Witness
                                                                                             // DTO object
        }
        if (!StringUtils.isEmpty(model.getStatus()) && model.getStatus().equals("APPROVED")) {
            model.setStatus("APPROVED");
        } else if (model.getStatus().equals("PROCESSING")) {

        } else {
            model.setStatus("PENDING");
        }
        return new ModelAndView(WITNESS, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "addWitness")
    public ModelAndView addWitness(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (resetOption != null && resetOption.trim().equals(RESET)) {

        } else {

            // D#128492 check here duplicate AADHAR NO
            /*
             * boolean duplicateUID = model.getMarriageDTO().getWitnessDetailsDTO().stream() .anyMatch(witness ->
             * witness.getUidNo().equals(model.getWitnessDTO().getUidNo())); if (duplicateUID) {
             * model.addValidationError(getApplicationSession().getMessage("mrm.witness.duplicateUI")); ModelAndView mv = new
             * ModelAndView("WitnessValidn", MainetConstants.FORM_NAME, getModel()); mv.addObject(BindingResult.MODEL_KEY_PREFIX +
             * MainetConstants.FORM_NAME, getModel().getBindingResult()); return mv; }
             */

            // get occupation DESC from occupation prefix
            LookUp lookupOCU = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                    model.getWitnessDTO().getOccupation(), orgId, "OCU");
            // D#127392 get Relation DESC from Relationship prefix
            LookUp lookupRLS = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                    model.getWitnessDTO().getRelation(), orgId, "RLS");
            // here 1st check witness OBJ has srNo than it mean update the existing object list
            if (!model.getMarriageDTO().getWitnessDetailsDTO().isEmpty() && model.getWitnessDTO().getSrNo() != 0) {
                model.getMarriageDTO().getWitnessDetailsDTO().forEach(witness -> {
                    if (witness.getSrNo() == model.getWitnessDTO().getSrNo()) {
                        model.getWitnessDTO().setOccupationDesc(lookupOCU.getLookUpDesc());
                        if (lookupRLS != null) {
                            model.getWitnessDTO().setRelationDesc(lookupRLS.getLookUpDesc());
                        }
                        if (lookupRLS.getLookUpCode().equals("OT") && StringUtils.isNotBlank(witness.getOtherRel())) {
                            model.getWitnessDTO().setRelationDesc(witness.getOtherRel());
                        }
                        witness = model.getWitnessDTO();
                    }
                });
            } else {
                model.getWitnessDTO().setSrNo(model.getMarriageDTO().getWitnessDetailsDTO().size() + 1);
                model.getWitnessDTO().setOccupationDesc(lookupOCU.getLookUpDesc());
                if (lookupRLS != null) {
                    model.getWitnessDTO().setRelationDesc(lookupRLS.getLookUpDesc());
                }
                if (lookupRLS.getLookUpCode().equals("OT") && StringUtils.isNotBlank(model.getWitnessDTO().getOtherRel())) {
                    model.getWitnessDTO().setRelationDesc(model.getWitnessDTO().getOtherRel());
                }
                // Add the new Witness DTO object to the list
                model.getMarriageDTO().getWitnessDetailsDTO().add(model.getWitnessDTO());

            }
            // reset Witness object
            model.setWitnessDTO(new WitnessDetailsDTO());

        }
        return new ModelAndView(WITNESS, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "openActionWitness")
    public ModelAndView openActionWitness(final HttpServletRequest request,
            @RequestParam(value = "srNo", required = false) int srNo,
            @RequestParam(value = "type", required = false) String type) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();
        model.setWitnessModeType(type);
        // fetch from witness details list by using srNo
        WitnessDetailsDTO witnessDTO = new WitnessDetailsDTO();
        witnessDTO = model.getMarriageDTO().getWitnessDetailsDTO().get(srNo - 1);
        model.setWitnessDTO(witnessDTO);

        return new ModelAndView(WITNESS, MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = "saveWitnessPage", method = RequestMethod.POST)
    public Map<String, Object> saveWitnessPage(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        final MarriageRegistrationModel marriageModel = this.getModel();
        List<WitnessDetailsDTO> witnessDetailsDTOs = marriageModel.getMarriageDTO().getWitnessDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // validateConstraints(marriageDTO, MarriageDTO.class, bindingResult);

        witnessDetailsDTOs.forEach(witness -> {
            witness.setOrgId(orgId);
            witness.setMarId(marriageModel.getMarriageDTO());

            if (getModel().getStatus().equals("APPROVED")) {
                witness.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                witness.setUpdatedDate(new Date());
                witness.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

            } else {
                witness.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                witness.setCreatedDate(new Date());
                witness.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            }

        });

        // final application save after checklist

        if (this.getModel().saveForm(witnessDetailsDTOs)) {
            object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());
        } else {
            object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        }

        return object;
    }

    @RequestMapping(params = "printMarriageRegAckw", method = {
            RequestMethod.POST })
    public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();

        marriageDTO.setApplicantName(marriageDTO.getApplicantDetailDto().getApplicantFirstName() + " " +
                marriageDTO.getApplicantDetailDto().getApplicantLastName());
        marriageDTO.setServiceShortCode(marriageModel.getServiceMaster().getSmShortdesc());
        marriageDTO.setDepartmentName(marriageModel.getServiceMaster().getTbDepartment().getDpDeptdesc());
        marriageDTO.setAppDate(new Date());
        marriageDTO.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        marriageDTO.setDueDate(Utility.getAddedDateBy2(marriageDTO.getAppDate(),
                marriageModel.getServiceMaster().getSmServiceDuration().intValue()));
        // service sla unit + application date
        // marriageDTO.setDueTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        marriageDTO.setHelpLine("Help Line -25015256");
        // runtime print acknowledge or certificate
        String viewName = "MarriageRegAcknow";
        /*
         * if (this.getModel().getStatus().equalsIgnoreCase("APPROVED")) { viewName = "marriageCertificate"; }
         */
        // fetch checklist result if not fetch already
        if (marriageModel.getCheckList().isEmpty()) {
            // call for fetch checklist based on Marriage Status (STA)
            fetchCheckListBySTA(marriageModel);
        }
        ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    @RequestMapping(method = RequestMethod.POST, params = "showAppointmentPage")
    public ModelAndView showAppointmentPage(final HttpServletRequest request,
            @RequestParam(value = RESET_OPTION, required = false) String resetOption) {
        bindModel(request);
        MarriageRegistrationModel model = this.getModel();
        if (resetOption != null && resetOption.trim().equals(RESET)) {
            model.getMarriageDTO().setAppointmentDTO(new AppointmentDTO());
        }
        return new ModelAndView(APPOINTMENT, MainetConstants.FORM_NAME, model);
    }

    // currently not is use
    @RequestMapping(method = RequestMethod.POST, params = "submitAppointment")
    public ModelAndView saveAppointmentPage(final Model model, final HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        BindingResult bindingResult = marriageModel.getBindingResult();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();
        Long marId = marriageDTO.getMarId();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        AppointmentDTO appointmentDTO = marriageDTO.getAppointmentDTO();
        ModelAndView mv = null;
        // validateConstraints(marriageDTO, MarriageDTO.class, bindingResult);

        if (!bindingResult.hasErrors()) {
            // save in TB_MRM_APPOINTMENT table
            appointmentDTO.setMarId(marriageDTO);
            appointmentDTO.setOrgId(orgId);
            appointmentDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            appointmentDTO.setCreatedDate(new Date());
            appointmentDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            marriageDTO.setMarId(marId);
            marriageDTO.setUrlParam(MainetConstants.MRM.SHOW_APPOINTMENT_Page);
            marriageDTO.setUrlShortCode(MainetConstants.MRM.APPOINTMENT_URL_CODE);

            marriageService.saveMarriageRegInDraftMode(marriageDTO);

            // mv = new ModelAndView(APPOINTMENT,
            // MainetConstants.FORM_NAME, this.getModel());
            marriageDTO.setApplicantName("Shri Sharad Joshi");
            marriageDTO.setServiceShortCode("Marriage Registration");
            marriageDTO.setDepartmentName("Marriage Registration Department");
            marriageDTO.setAppDate(new Date());
            marriageDTO.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            marriageDTO.setDueDate(new Date());
            marriageDTO.setDueTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            marriageDTO.setHelpLine("Help Line -25015256");
            mv = new ModelAndView("MarriageRegistrationAcknowledgement", MainetConstants.FORM_NAME, marriageModel);

        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, marriageModel);
            mv = new ModelAndView(APPOINTMENT, MainetConstants.FORM_NAME, marriageModel);
        }
        return mv;
    }

    @RequestMapping(params = "getChecklistAndCharges", method = { RequestMethod.POST })
    public ModelAndView getChecklistAndCharges(final HttpServletRequest request) {

        this.getModel().bind(request);
        MarriageRegistrationModel model = this.getModel();
        // validation server side
        // model.validateBean(model.getAgencyRequestDto().getMasterDto(), AdverstiserMasterValidator.class);

        ModelAndView mv = new ModelAndView("WitnessValidn",
                MainetConstants.FORM_NAME, getModel());

        if (!model.hasValidationErrors()) {
            if (StringUtils.equalsIgnoreCase(this.getModel().getCheckListApplFlag(), MainetConstants.FlagY)) {
                // common method for re use at acknowledge screen
                fetchCheckListBySTA(model);
            }
        }

        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    /**
     * This method is used to set required fields in checkListModel to call BRMS Sheet
     * 
     * @param model
     * @param checklistModel
     */
    private void populateChecklistModel(MarriageRegistrationModel model, CheckListModel checklistModel) {

        checklistModel.setOrgId(model.getMarriageDTO().getOrgId());
        checklistModel.setServiceCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        checklistModel.setDeptCode(MainetConstants.MRM.MRM_DEPT_CODE);
    }

    private void fetchCheckListBySTA(MarriageRegistrationModel model) {
        final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
        checkListRateRequestModel.setModelName(MainetConstants.MRM.CHECKLIST_MRMRATEMASTER);
        WSResponseDTO checkListRateResponseModel = brmsCommonService.initializeModel(checkListRateRequestModel);

        if (StringUtils.equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS,
                checkListRateResponseModel.getWsStatus())) {
            List<DocumentDetailsVO> finalCheckList = new ArrayList<>();
            final List<Object> castCheckListModel = RestClient.castResponse(checkListRateResponseModel,
                    CheckListModel.class, 0);
            final List<Object> mrmRateMasterList = RestClient.castResponse(checkListRateResponseModel,
                    MRMRateMaster.class, 1);

            final CheckListModel checkListModel = (CheckListModel) castCheckListModel.get(0);
            final MRMRateMaster MRMRateMaster = (MRMRateMaster) mrmRateMasterList.get(0);

            populateChecklistModel(model, checkListModel);
            // D#122248
            // call checklist without usageSubtype1 i.e for unmarried
            finalCheckList = callBrmsForCheckList(model, checkListModel);

            if (CollectionUtils.isNotEmpty(finalCheckList)) {
                // D#124595 check here husband and wife status if both have same STA Prefix(U) than do not call again for
                // checklist
                String husbSTACode = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(model.getMarriageDTO().getHusbandDTO().getStatusMarTime(),
                                UserSession.getCurrent().getOrganisation())
                        .getLookUpCode();
                String wifeSTACode = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(model.getMarriageDTO().getWifeDTO().getStatusMarTime(),
                                UserSession.getCurrent().getOrganisation())
                        .getLookUpCode();
                if (!MainetConstants.FlagU.equals(husbSTACode)) {
                    // call using husband status selected
                    checkListModel.setUsageSubtype1(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(model.getMarriageDTO().getHusbandDTO().getStatusMarTime(),
                                    UserSession.getCurrent().getOrganisation())
                            .getLookUpCode());
                    List<DocumentDetailsVO> husbandCheckList = new ArrayList<>();
                    husbandCheckList = callBrmsForCheckList(model, checkListModel);
                    Long count = (long) finalCheckList.size();
                    for (DocumentDetailsVO docHusb : husbandCheckList) {
                        count++;
                        docHusb.setDocumentSerialNo(count);
                    }
                    finalCheckList.addAll(husbandCheckList);
                }
                if (!MainetConstants.FlagU.equals(wifeSTACode)) {
                    // call using wife status selected
                    checkListModel.setUsageSubtype1(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(model.getMarriageDTO().getWifeDTO().getStatusMarTime(),
                                    UserSession.getCurrent().getOrganisation())
                            .getLookUpCode());
                    List<DocumentDetailsVO> wifeCheckList = new ArrayList<>();
                    wifeCheckList = callBrmsForCheckList(model, checkListModel);
                    Long countWife = (long) finalCheckList.size();
                    for (DocumentDetailsVO docWife : wifeCheckList) {
                        countWife++;
                        docWife.setDocumentSerialNo(countWife);
                    }
                    finalCheckList.addAll(wifeCheckList);
                }

                model.setCheckList(finalCheckList);
            } else {
                model.addValidationError(getApplicationSession().getMessage("mrm.checklist.not.found"));
            }

            if (StringUtils.equals(model.getApplicationChargeApplFlag(), MainetConstants.FlagY)) {
                Double applicationCharge = callBrmsForApplicationCharges(model, MRMRateMaster);
                if (applicationCharge <= 0) {
                    model.addValidationError(getApplicationSession().getMessage("mrm.charges.not.found"));
                }
            }

        } else {
            model.addValidationError(
                    getApplicationSession().getMessage("mrm.problem.occured.initializing.checklist"));
        }
    }

    @SuppressWarnings("unchecked")
    private List<DocumentDetailsVO> callBrmsForCheckList(MarriageRegistrationModel model, CheckListModel checkListModel) {
        List<DocumentDetailsVO> checkList = new ArrayList<>();
        WSRequestDTO checklistReqDto = new WSRequestDTO();
        checklistReqDto.setModelName(MainetConstants.MRM.CHECK_LIST);
        checklistReqDto.setDataModel(checkListModel);
        WSResponseDTO checklistResponse = brmsCommonService.getChecklist(checklistReqDto);

        // checking the response after getting applicable checklist from BRMS is
        // success.(If not then, displaying the validation message on Screen)
        if (StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.WebServiceStatus.SUCCESS)
                || StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.CommonConstants.NA)) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
            if (!StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.CommonConstants.NA)) {

                List<DocumentDetailsVO> checklistList = Collections.emptyList();
                checklistList = (List<DocumentDetailsVO>) checklistResponse.getResponseObj();
                if (CollectionUtils.isNotEmpty(checklistList)) {
                    long count = 1;
                    for (DocumentDetailsVO doc : checklistList) {
                        doc.setDocumentSerialNo(count);
                        count++;
                    }
                    checkList = checklistList;
                }
            }
        }

        return checkList;
    }

    @SuppressWarnings("unchecked")
    private Double callBrmsForApplicationCharges(MarriageRegistrationModel model, MRMRateMaster MRMRateMaster) {
        Double amoutToPay = 0.0;
        final WSRequestDTO mrmRateRequestDto = new WSRequestDTO();
        MRMRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        MRMRateMaster.setServiceCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        MRMRateMaster
                .setChargeApplicableAt(
                        Long.toString(CommonMasterUtility
                                .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                                        PrefixConstants.LookUpPrefix.CAA, UserSession.getCurrent().getOrganisation())
                                .getLookUpId()));
        // set no of days (application date - marriage date)
        MRMRateMaster.setNoOfDays(
                Utility.getDaysBetweenDates(model.getMarriageDTO().getMarDate(), model.getMarriageDTO().getAppDate()));
        mrmRateRequestDto.setDataModel(MRMRateMaster);
        WSResponseDTO mrmRateResponseDto = BRMSMRMService.getApplicableTaxes(mrmRateRequestDto);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(mrmRateResponseDto.getWsStatus())) {
            if (!mrmRateResponseDto.isFree()) {
                final List<Object> rates = (List<Object>) mrmRateResponseDto.getResponseObj();
                final List<MRMRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    MRMRateMaster master1 = (MRMRateMaster) rate;
                    master1 = populateChargeModel(model, master1);
                    requiredCHarges.add(master1);
                }
                WSRequestDTO chargeReqDto = new WSRequestDTO();
                chargeReqDto.setModelName("MRMRateMaster");
                chargeReqDto.setDataModel(requiredCHarges);
                WSResponseDTO applicableCharges = BRMSMRMService.getApplicableCharges(chargeReqDto);
                if (StringUtils.equalsIgnoreCase(applicableCharges.getWsStatus(),
                        MainetConstants.WebServiceStatus.SUCCESS)) {
                    List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    // model.getAgencyRequestDto().setFree(false);
                    model.setPayableFlag(MainetConstants.FlagY);
                    model.setChargesInfo(detailDTOs);
                    model.setAmountToPay((chargesToPay(detailDTOs)));
                    model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
                    model.setApplicationChargeApplFlag(MainetConstants.FlagN);
                    amoutToPay = model.getAmountToPay();
                }

            } else {
                model.setPayableFlag(MainetConstants.FlagN);
                model.setAmountToPay(0.0d);
            }
        }

        return amoutToPay;
    }

    private MRMRateMaster populateChargeModel(final MarriageRegistrationModel model, final MRMRateMaster chargeModel) {
        chargeModel.setOrgId(model.getMarriageDTO().getOrgId());
        chargeModel.setServiceCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        chargeModel.setRateStartDate(new Date().getTime());
        chargeModel.setDeptCode(MainetConstants.MRM.MRM_DEPT_CODE);
        return chargeModel;
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    // Below code is for Workflow Approval Section
    @RequestMapping(params = "showDetails", method = { RequestMethod.POST })
    public ModelAndView showDetails(@RequestParam("appNo") final Long applicationId, @RequestParam("taskId") String taskId,
            @RequestParam(value = "actualTaskId") Long actualTaskId, final HttpServletRequest request, ModelMap modelMap) {

        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().bind(request);

        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
        this.getModel().setApmApplicationId(applicationId);
        this.getModel().setStatus("PROCESSING");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = smService
                .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
            this.getModel().setServiceId(serviceDto.getSmServiceId());
            this.getModel().setServiceName(serviceDto.getSmServiceName());
            this.getModel().getMarriageDTO().setDeptId(serviceDto.getTbDepartment().getDpDeptid());
        }

        this.getModel().setOrgId(orgId);
        try {
            // D#129640
            /* getModel().populateModel(this.getModel(), null, applicationId, "V"); */
            getModel().populateModel(this.getModel(), null, applicationId, "D");
            getModel().getMarriageDTO().setCustomField("DIS_MAR_DATE");
            // D#127379
            // D#122249 in case of checklist at charge screen level
            this.getModel().setApplicationDetails(checklistSearchService.getCheckListDataByApplication(orgId, applicationId));
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(applicationId,
                    orgId);
            if (address != null) {
                this.getModel().setMobNo(address.getApaMobilno());
                this.getModel().setEmail(address.getApaEmail());
            }
            final List<LookUp> documentDetailsList = new ArrayList<>(0);
            // setPath(this.getModel().getDocumentList().get(0).getAttPath());
            LookUp lookUp = null;
            for (final CFCAttachment temp : this.getModel().getDocumentList()) {
                lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
                lookUp.setDescLangFirst(temp.getClmDescEngl());
                lookUp.setDescLangSecond(temp.getClmDesc());
                lookUp.setLookUpId(temp.getClmId());
                lookUp.setDefaultVal(temp.getAttPath());
                lookUp.setLookUpCode(temp.getAttFname());
                lookUp.setLookUpType(temp.getDmsDocId());
                lookUp.setLookUpParentId(temp.getAttId());
                lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
                lookUp.setOtherField(temp.getMandatory());
                lookUp.setExtraStringField1(temp.getClmRemark());// reject message
                // 128836
                lookUp.setDocDescription(temp.getDocDescription());
                documentDetailsList.add(lookUp);
            }
            // D#127379 custom checklist screen add here
            if (request.getRequestURI().contains(MAR_CHECKLIST_APP)) {
                modelMap.put("documentDetailsList", documentDetailsList);
                modelMap.put("docAndCharge", false);
                return new ModelAndView("MarriageCheckList", MainetConstants.CommonConstants.COMMAND, this.getModel());

            } else {
                boolean lastApproval = workFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId);
                // last approval than display checklist and after calculate BRMS charges
                if (lastApproval) {
                    getModel().setSuccessFlag(MainetConstants.FlagY);
                    modelMap.put("documentDetailsList", documentDetailsList);
                    modelMap.put("docAndCharge", true);
                    // D#129628
                    getModel().setPhotoThumbDisp("Y");
                    return new ModelAndView("MarriageCheckList", MainetConstants.CommonConstants.COMMAND, this.getModel());
                }
            }

        } catch (Exception exception) {
            logger.error("Error While Rendoring the form", exception);
            return defaultExceptionFormView();
        }

        this.getModel().setPayableFlag(MainetConstants.FlagN);
        // D#129640
        this.getModel().setApprovalProcess("N");
        this.getModel().setWitnessModeType("N");

        // D#130012 applnCopyTo radio button set
        /*
         * String firstName = ""; if (this.getModel().getMarriageDTO().getHusbandDTO() != null) { firstName =
         * this.getModel().getMarriageDTO().getHusbandDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName) && firstName
         * .equalsIgnoreCase(this.getModel().getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
         * this.getModel().getMarriageDTO().setApplnCopyTo("H"); } else if (this.getModel().getMarriageDTO().getWifeDTO() != null)
         * { firstName = this.getModel().getMarriageDTO().getWifeDTO().getFirstNameEng(); if (StringUtils.isNotBlank(firstName) &&
         * firstName .equalsIgnoreCase(this.getModel().getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
         * this.getModel().getMarriageDTO().setApplnCopyTo("W"); } else { this.getModel().getMarriageDTO().setApplnCopyTo("O"); }
         * } }
         */
        this.getModel().setHideMarSaveBT("Y");
        return new ModelAndView("MarriageRegView",
                MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    // D#122249
    @RequestMapping(params = "displayMarCharge", method = { RequestMethod.POST })
    public ModelAndView openMarriageCharge(final HttpServletRequest request) {
        this.getModel().bind(request);
        getModel().setSuccessFlag(MainetConstants.FlagY);

        // save husband data in TB_MRM_WITNESS_DET
        // data read from DB for update photo and thumb
        MarriageDTO marriageDTODB = new MarriageDTO();
        marriageDTODB = marriageService.getMarriageDetailsById(this.getModel().getMarriageDTO().getMarId(), null,
                null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MRM.hitFrom);

        HusbandDTO husDTO = new HusbandDTO();
        BeanUtils.copyProperties(marriageDTODB.getHusbandDTO(), husDTO);
        MarriageDTO marId = new MarriageDTO();
        marId.setMarId(marriageDTODB.getMarId());
        husDTO.setMarId(marId);
        marriageDTODB.setHusbandDTO(husDTO);

        this.getModel().getMarriageDTO().setUrlShortCode(MainetConstants.MRM.WITNESS_LEASE_URL_CODE);
        this.getModel().getMarriageDTO().setUploadMap(this.getModel().getUploadMap());

        marriageService.saveMarriageRegInDraftMode(this.getModel().getMarriageDTO());

        if (getModel().saveLoiData()) {
            getModel().setPayableFlag(MainetConstants.FlagN);
            List<TbLoiDet> loiDetailList = new ArrayList<>();
            Double totalAmount = new Double(0);
            if (CollectionUtils.isNotEmpty(getModel().getLoiDetail())) {
                for (TbLoiDet detail : getModel().getLoiDetail()) {
                    TbLoiDet loiDetail = new TbLoiDet();
                    BeanUtils.copyProperties(detail, loiDetail);
                    String taxDesc = ApplicationContextProvider.getApplicationContext()
                            .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
                                    UserSession.getCurrent().getOrganisation().getOrgid());
                    loiDetail.setLoiRemarks(taxDesc);
                    totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
                    loiDetailList.add(loiDetail);
                    getModel().setTotalLoiAmount(totalAmount);
                }
            }
            getModel().setLoiDetail(loiDetailList);
            getModel().setAmountToPay(totalAmount);
            getModel().getOfflineDTO().setAmountToShow(getModel().getAmountToPay());

        } else {
            getModel().addValidationError(getApplicationSession()
                    .getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));
        }
        // D#134963
        MarriageRegistrationModel marriageModel = this.getModel();
        getModel().populateModel(marriageModel, marriageDTODB.getMarId(), null, "V");
        return new ModelAndView("MarriageCharge", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    @Override
    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView saveform(HttpServletRequest request) {
        JsonViewObject responseObj = null;
        this.getModel().bind(request);

        MarriageRegistrationModel model = this.getModel();
        // D#123056
        /*
         * if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
         * getModel().addValidationError(getApplicationSession().getMessage("mrm.approval.decision")); return defaultMyResult(); }
         * if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
         * getModel().addValidationError(getApplicationSession().getMessage("mrm.approval.remarks")); return defaultMyResult(); }
         */
        // this.getModel().getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        this.getModel().getWorkflowActionDto().setComments("Appointment");
        final MarriageRegistrationModel marriageModel = this.getModel();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();
        AppointmentDTO appointmentDTO = marriageDTO.getAppointmentDTO();
        appointmentDTO.setMarId(marriageDTO);
        appointmentDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        appointmentDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        appointmentDTO.setCreatedDate(new Date());
        appointmentDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        responseObj = JsonViewObject.successResult(model.saveAppointmentAndDecision(appointmentDTO));
        return jsonResult(responseObj);

    }

    // currently not use
    @RequestMapping(params = "checkLastApproval", method = RequestMethod.POST)
    public ModelAndView checkLastApproval(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        MarriageRegistrationModel model = this.getModel();
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("mrm.approval.decision"));

        }
        if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("mrm.approval.remarks"));

        }
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        boolean lastApproval = workFlowTypeService
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
        if (lastApproval
                && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {
            model.setSuccessFlag(MainetConstants.FlagY);

        } else {
            model.setSuccessFlag(MainetConstants.FlagN);
            this.getModel().setPayableFlag(MainetConstants.FlagN);
        }
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

        mv = new ModelAndView("AppointmentValidn", MainetConstants.FORM_NAME, getModel());
        return mv;

    }

    @RequestMapping(params = "getUploadedImage", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getUploadedImage(final HttpServletRequest httpServletRequest) {

        getModel().setUploadedfile(getModel().getCachePathUpload(getModel().getUploadType()));
        return getModel().getUploadedfile();
    }

    @RequestMapping(params = "deleteSingleUpload", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> deleteSingleUpload(@RequestParam("deleteMapId") final Long deleteMapId,
            @RequestParam("deleteId") final Long deleteId) {

        return getModel().deleteSingleUpload(deleteMapId, deleteId);
    }

    @RequestMapping(params = "saveMarriageCertificateAfterEdit", method = RequestMethod.POST)
    public String saveMarriageCertificateAfterEdit(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final MarriageRegistrationModel marriageModel = this.getModel();
        List<WitnessDetailsDTO> witnessDetailsDTOs = marriageModel.getMarriageDTO().getWitnessDetailsDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // validateConstraints(marriageDTO, MarriageDTO.class, bindingResult);

        witnessDetailsDTOs.forEach(witness -> {
            witness.setOrgId(orgId);
            witness.setMarId(marriageModel.getMarriageDTO());
            // new witness add than set createdBy
            if (witness.getCreatedBy() == null) {
                witness.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                witness.setCreatedDate(new Date());
                witness.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            } else {
                witness.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                witness.setUpdatedDate(new Date());
                witness.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }

        });

        if (!this.getModel().saveForm(witnessDetailsDTOs)) {
            LOGGER.error("Exception occured while update Marriage certifiacate submit :");
        }

        httpServletRequest.getSession().setAttribute("appNo", this.getModel().getMarriageDTO().getApplicationId());
        httpServletRequest.getSession().setAttribute("actualTaskId", this.getModel().getWorkflowActionDto().getTaskId());

        return "redirect:MarriageCertificateGeneration.html?showDetails";

    }

    // D#127379
    @RequestMapping(params = "marChecklistSubmit", method = { RequestMethod.POST })
    public ModelAndView marChecklistSubmit(final HttpServletRequest request) {
        this.getModel().bind(request);
        // workflow update
        WorkflowTaskAction taskAction = this.getModel().getWorkflowActionDto();
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setCreatedDate(new Date());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setIsFinalApproval(MainetConstants.FAILED);
        taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setApplicationId(this.getModel().getApmApplicationId());
        taskAction.setTaskId(this.getModel().getWorkflowActionDto().getTaskId());
        taskAction.setComments("checklist approve");
        marriageService.executeApprovalWorkflowAction(taskAction, this.getModel().getServiceMaster().getSmServiceId(),
                "CHECKLIST");

        return jsonResult(
                JsonViewObject.successResult(getApplicationSession().getMessage("checklistVerification.saveSuccessMsg")));
    }

    // D#129628 start
    @RequestMapping(params = "openHusbPhotoThumb", method = { RequestMethod.POST })
    public ModelAndView openHusbPhotoThumb(final HttpServletRequest request) {
        // this.getModel().bind(request);
        bindModel(request);
        // value bind with photo and thumb to identify whose image is
        getModel().setPhotoId(90L);
        getModel().setThumbId(91L);
        getModel().setUploadType("H");
        getModel().setUploadedfile(getModel().getCachePathUpload("H"));

        // D#136526
        getModel().setModeType("D");
        getModel().setApplicationDetails(checklistSearchService.getCheckListDataByApplication(
                UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getMarriageDTO().getApplicationId()));
        final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(
                this.getModel().getMarriageDTO().getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
        if (address != null) {
            getModel().setMobNo(address.getApaMobilno());
            getModel().setEmail(address.getApaEmail());
        }

        getModel().setPhotoThumbDisp("Y");
        return new ModelAndView("HusbandPhotoThumb", MainetConstants.CommonConstants.COMMAND, getModel());

    }

    @RequestMapping(params = "saveHusbPhotoThumb", method = { RequestMethod.POST })
    public ModelAndView saveHusbPhotoThumb(final HttpServletRequest request) {
        bindModel(request);
        // save husband data in TB_MRM_HUSBAND
        // data read from DB for update photo and thumb
        MarriageDTO marriageDTODB = new MarriageDTO();
        marriageDTODB = marriageService.getMarriageDetailsById(this.getModel().getMarriageDTO().getMarId(), null,
                null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MRM.hitFrom);

        HusbandDTO husDTO = new HusbandDTO();
        BeanUtils.copyProperties(marriageDTODB.getHusbandDTO(), husDTO);
        MarriageDTO marId = new MarriageDTO();
        marId.setMarId(marriageDTODB.getMarId());
        husDTO.setMarId(marId);

        marriageDTODB.setHusbandDTO(husDTO);

        marriageDTODB.setUrlShortCode(MainetConstants.MRM.HUS_URL_CODE);
        marriageDTODB.setUploadMap(this.getModel().getUploadMap());

        marriageService.saveMarriageRegInDraftMode(marriageDTODB);

        // opening wife section

        getModel().setPhotoId(990L);
        getModel().setThumbId(991L);
        getModel().setUploadType("W");
        getModel().setUploadedfile(getModel().getCachePathUpload("W"));
        return new ModelAndView("WifePhotoThumb", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    // D#129628 end

    @RequestMapping(params = "saveWifePhotoThumb", method = { RequestMethod.POST })
    public ModelAndView saveWifePhotoThumb(final HttpServletRequest request) {
        bindModel(request);
        // save husband data in TB_MRM_WIFE

        // D129628 wife data read from DB start
        MarriageDTO marriageDTODB = new MarriageDTO();
        marriageDTODB = marriageService.getMarriageDetailsById(this.getModel().getMarriageDTO().getMarId(), null,
                null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MRM.hitFrom);
        WifeDTO wifeDTO = new WifeDTO();
        BeanUtils.copyProperties(marriageDTODB.getWifeDTO(), wifeDTO);
        MarriageDTO marId = new MarriageDTO();
        marId.setMarId(marriageDTODB.getMarId());
        wifeDTO.setMarId(marId);
        marriageDTODB.setWifeDTO(wifeDTO);
        marriageDTODB.setUrlShortCode(MainetConstants.MRM.WIFE_URL_CODE);
        marriageDTODB.setUploadMap(this.getModel().getUploadMap());

        marriageService.saveMarriageRegInDraftMode(marriageDTODB);
        // D129628 wife data read from DB end

        getModel().setUploadType("WT");
        getModel().setUploadedfile(getModel().getCachePathUpload("WT"));
        this.getModel().getMarriageDTO();

        this.getModel().setMarriageDTO(marriageDTODB);
        return new ModelAndView("WitnessPhotoThumb", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    // webcam code start
    @RequestMapping(params = "uploadPhotoThumbBase64", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> uploadPhotoThumbBase64(final HttpServletRequest request,
            @RequestParam("base64String") final String base64String, @RequestParam("uploadDesc") final String uploadDesc) {
        this.getModel().bind(request);

        // upload doc in TEMP physical location
        final String newPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber()
                + MainetConstants.FILE_PATH_SEPARATOR;

        String fileNetPath = Filepaths.getfilepath();
        /*
         * String dirPath = null; final StringBuilder builder = new StringBuilder();
         * builder.append(UserSession.getCurrent().getOrganisation().getOrgid()).append(MainetConstants.FILE_PATH_SEPARATOR)
         * .append(File.separator).append(Utility.getTimestamp()); dirPath = builder.toString();
         */
        String fileName = uploadDesc + Utility.getTimestamp() + MainetConstants.MRM.PNG_EXTENSION;
        final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
        documentDetailsVO.setDocumentByteCode(base64String);
        fileUpload.convertAndSaveFile(documentDetailsVO, fileNetPath, newPath, fileName);

        // make full path with file name
        final String completeDirpath = fileNetPath + MainetConstants.FILE_PATH_SEPARATOR + newPath;
        final String completPath = completeDirpath + MainetConstants.FILE_PATH_SEPARATOR + fileName;

        Set<File> fileDetails = new LinkedHashSet<>();
        final File file = new File(completPath);
        fileDetails.add(file);

        // make it runtime when photo and thumb upload like base64 style
        FileUploadUtility.getCurrent().getFileMap()
                .put(uploadDesc.equals("photo") ? getModel().getPhotoId() : getModel().getThumbId(), fileDetails);
        FileUploadUtility.getCurrent().setFileMap(FileUploadUtility.getCurrent().getFileMap());

        getModel().setUploadedfile(getModel().getCachePathUpload(getModel().getUploadType()));
        return getModel().getUploadedfile();
    }
    // webcam code end

    // D#129628 start
    @RequestMapping(params = "openWitnessPhotoThumb", method = { RequestMethod.POST })
    public ModelAndView openWitnessPhotoThumb(final HttpServletRequest request) {
        this.getModel().bind(request);

        getModel().setUploadType("WT");
        getModel().setUploadedfile(getModel().getCachePathUpload("WT"));
        this.getModel().getMarriageDTO();
        MarriageDTO marriageDTODB = new MarriageDTO();
        marriageDTODB = marriageService.getMarriageDetailsById(this.getModel().getMarriageDTO().getMarId(), null,
                null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MRM.hitFrom);
        this.getModel().setMarriageDTO(marriageDTODB);
        return new ModelAndView("WitnessPhotoThumb", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    @RequestMapping(params = "getWitnessUploadedImage", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getWitnessUploadedImage(final HttpServletRequest request,
            @RequestParam(value = "photoId", required = false) Long photoId,
            @RequestParam(value = "thumbId", required = false) Long thumbId) {
        this.getModel().bind(request);
        // test code if not work proper than uncomment below code
        /*
         * getModel().getUploadedfile().clear(); if ((FileUploadUtility.getCurrent().getFileMap() != null) &&
         * !FileUploadUtility.getCurrent().getFileMap().isEmpty()) { Long count = 0l; for (final Map.Entry<Long, Set<File>> entry
         * : FileUploadUtility.getCurrent().getFileMap().entrySet()) { if ((photoId != null && (entry.getKey() != null) &&
         * (entry.getKey().longValue() == photoId)) || (thumbId != null && (entry.getKey() != null) && (entry.getKey().longValue()
         * == thumbId))) { if ((entry.getKey() != null) && (entry.getKey().longValue() == thumbId)) { count = 1l; } for (final
         * File file : entry.getValue()) { final String mapKey = entry.getKey().toString() + "WT";
         * getModel().getUploadMap().put(mapKey, file); String fileName = null; final String path =
         * file.getPath().replace("\\", "/"); fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
         * getModel().getUploadedfile().put(count, fileName); count = 0l; } } } }
         */
        getModel().setPhotoId(photoId);
        getModel().setThumbId(thumbId);
        getModel().setUploadedfile(getModel().getCachePathUpload("WT"));
        // getModel().setUploadedfile(getModel().getUploadedfile());
        return getModel().getUploadedfile();
    }

    @RequestMapping(params = "uploadPhotoThumbWitBase64", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> uploadPhotoThumbWitBase64(final HttpServletRequest request,
            @RequestParam("base64String") final String base64String, @RequestParam("uploadType") final String uploadType,
            @RequestParam(value = "photoId", required = false) Long photoId,
            @RequestParam(value = "thumbId", required = false) Long thumbId) {
        this.getModel().bind(request);
        getModel().getPhotoId();
        getModel().getThumbId();
        // upload doc in TEMP physical location
        final String newPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber()
                + MainetConstants.FILE_PATH_SEPARATOR;

        String fileNetPath = Filepaths.getfilepath();
        String fileName = uploadType + Utility.getTimestamp() + MainetConstants.MRM.PNG_EXTENSION;
        final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
        documentDetailsVO.setDocumentByteCode(base64String);
        fileUpload.convertAndSaveFile(documentDetailsVO, fileNetPath, newPath, fileName);

        // make full path with file name
        final String completeDirpath = fileNetPath + MainetConstants.FILE_PATH_SEPARATOR + newPath;
        final String completPath = completeDirpath + MainetConstants.FILE_PATH_SEPARATOR + fileName;

        Set<File> fileDetails = new LinkedHashSet<>();
        final File file = new File(completPath);
        fileDetails.add(file);

        // make it runtime when photo and thumb upload like base64 style
        FileUploadUtility.getCurrent().getFileMap()
                .put(uploadType.equals("photo") ? photoId : thumbId, fileDetails);
        FileUploadUtility.getCurrent().setFileMap(FileUploadUtility.getCurrent().getFileMap());

        getModel().setUploadedfile(getModel().getCachePathUpload("WT"));
        return getModel().getUploadedfile();
    }

    @RequestMapping(params = "saveWitnessPhotoThumb", method = { RequestMethod.POST })
    public ModelAndView saveWitnessPhotoThumb(final HttpServletRequest request) {
        bindModel(request);
        // save husband data in TB_MRM_WITNESS_DET
        // data read from DB for update photo and thumb
        MarriageDTO marriageDTODB = new MarriageDTO();
        marriageDTODB = marriageService.getMarriageDetailsById(this.getModel().getMarriageDTO().getMarId(), null,
                null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MRM.hitFrom);

        /*
         * HusbandDTO husDTO = new HusbandDTO(); BeanUtils.copyProperties(marriageDTODB.getHusbandDTO(), husDTO); MarriageDTO
         * marId = new MarriageDTO(); marId.setMarId(marriageDTODB.getMarId()); husDTO.setMarId(marId);
         * marriageDTODB.setHusbandDTO(husDTO);
         */

        this.getModel().getMarriageDTO().setUrlShortCode(MainetConstants.MRM.WITNESS_LEASE_URL_CODE);
        this.getModel().getMarriageDTO().setUploadMap(this.getModel().getUploadMap());

        marriageService.saveMarriageRegInDraftMode(this.getModel().getMarriageDTO());

        return new ModelAndView("WifePhotoThumb", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    // D#134156
    @RequestMapping(params = "viewApplicantDetails", method = RequestMethod.POST)
    public ModelAndView viewApplicantDetails(@RequestParam(value = "marId", required = false) Long marId,
            @RequestParam(value = "taskId", required = false) Long taskId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        // fileUpload.sessionCleanUpForFileUpload();
        MarriageRegistrationModel marriageModel = this.getModel();
        marriageModel.setCommonHelpDocs("MarriageRegistration.html");
        // marriageModel.setModeType(MainetConstants.MODE_CREATE);
        marriageModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().populateModel(marriageModel, marriageModel.getMarriageDTO().getMarId(), null, MainetConstants.MODE_VIEW);
        ServiceMaster serviceMaster = smService.getServiceByShortName(
                MainetConstants.MRM.SERVICE_CODE.MRG,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setServiceMaster(serviceMaster);
        this.getModel().setServiceName(serviceMaster.getSmServiceName());
        this.getModel().getMarriageDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());

        marriageModel.setApprovalProcess("N");
        /*
         * marriageModel.getMarriageDTO().setCustomField("DIS_MAR_DATE"); marriageModel.setWitnessModeType("N");
         */
        marriageModel.setModeType(MainetConstants.MODE_VIEW);
        marriageModel.setWitnessModeType("N");
        marriageModel.setConditionFlag("CLOSE_BT");
        return new ModelAndView("MarriageRegViewApproval", MainetConstants.FORM_NAME, marriageModel);
    }

    @RequestMapping(params = "approvalDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        MarriageRegistrationModel marModel = this.getModel();

        String decision = marModel.getWorkflowActionDto().getDecision();
        boolean updFlag = marModel.updateApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid(),
                marModel.getMarriageDTO().getApplicationId());

        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("mrm.info.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("mrm.info.application.sendBack"));
        }

        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

    }

}
