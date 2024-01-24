package com.abm.mainet.mrm.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MRMRateMaster;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.dto.WitnessDetailsDTO;
import com.abm.mainet.mrm.service.IBRMSMRMService;
import com.abm.mainet.mrm.service.IMarriageService;
import com.abm.mainet.mrm.ui.model.MarriageRegistrationModel;

@Controller
@RequestMapping("MarriageRegistration.html")
public class MarriageRegistrationController extends AbstractFormController<MarriageRegistrationModel> {

	private static final Logger LOG = Logger.getLogger(MarriageRegistrationController.class);

    @Autowired
    IMarriageService marriageService;

    @Resource
    IFileUploadService fileUpload;

    
    @Autowired
    private ICommonBRMSService brmsCommonService;


    

    @Autowired
    IBRMSMRMService BRMSMRMService;

    private static final String MARRIAGE = "Marriage";
    private static final String HUSBAND = "Husband";
    private static final String WIFE = "Wife";
    private static final String WITNESS = "Witness";
    private static final String APPOINTMENT = "Appointment";

    private static final String RESET_OPTION = "resetOption";
    private static final String RESET = "reset";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        MarriageRegistrationModel marriageModel = this.getModel();
        marriageModel.setCommonHelpDocs("MarriageRegistration.html");
        MarriageRequest marRequest = new MarriageRequest();
        marRequest.setSmShortCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        marRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        MarriageResponse marResponse=marriageService.getMarriageData(marRequest);
        this.getModel().getMarriageDTO().setServiceId(marResponse.getServiceId());
        this.getModel().getMarriageDTO().setDeptId(marResponse.getDeptId());
        this.getModel().getMarriageDTO().setDepartmentName(marResponse.getDeptName());
        this.getModel().getMarriageDTO().setServiceShortCode(marResponse.getServiceName());
        this.getModel().getMarriageDTO().setSmServiceDuration(marResponse.getSmSLA());
        this.getModel().getMarriageDTO().setServiceShortCodeMar(marResponse.getServiceNameMar());
        this.getModel().getMarriageDTO().setDepartmentNameMar(marResponse.getDeptNameMar());
        // Checking condition whether checkList applicable for particular Service.
        if (StringUtils
                .equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(marResponse.getSmChklstVerify(),
                        UserSession.getCurrent().getOrganisation()).getLookUpCode(), "A")) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
        } else {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
        }
        if (StringUtils.equalsIgnoreCase(marResponse.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagY);
            this.getModel().getMarriageDTO().setFree(false);
        } else {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
            this.getModel().getMarriageDTO().setFree(true);
        }
        marriageModel.setApprovalProcess("N");
        marriageModel.setModeType("C");
        //marriageModel.setWitnessModeType("N");
        // set applicableENV
        List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
                UserSession.getCurrent().getOrganisation());
        Boolean envPresent = envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.SKDCL)
                        && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        if (envPresent) {
            marriageModel.setApplicableENV(true);
        } else {
            marriageModel.setApplicableENV(false);
        }
        this.getModel().setStatus(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
        marriageModel.getMarriageDTO().setApplnCopyTo("O");
        return new ModelAndView("MarriageRegForm", MainetConstants.FORM_NAME, marriageModel);
        
        
        
        
       //below code use when summary screen required on design and above one comment 
        //and change BaseLayout
        /*MarriageRequest mrgReq = new MarriageRequest();
        mrgReq.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        mrgReq.setSkdclENVPresent(envPresent);
        List<MarriageDTO> marriageDTOs = marriageService.fetchMarriageData(mrgReq);
        List<HusbandDTO> husbandList = new ArrayList<>();
        marriageDTOs.forEach(marriageDTO -> {
            husbandList.add(marriageDTO.getHusbandDTO());
        });
        this.getModel().setHusbandList(husbandList);
        this.getModel().setMarriageDTOs(marriageDTOs);
        return defaultResult();*/
        
        
    }
    
    //below handler use when summary screen required on design
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView marriageRegForm(
            @RequestParam(value = "marId", required = false) Long marId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "saveMode", required = false) String saveMode, final Model model,
            HttpServletRequest request)throws Exception {

    	sessionCleanup(request);
    	FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        MarriageRegistrationModel marriageModel = this.getModel();
        marriageModel.setCommonHelpDocs("MarriageRegistration.html");
        MarriageRequest marRequest = new MarriageRequest();
        marRequest.setSmShortCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        marRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        marRequest.setMarId(marId);
        MarriageResponse marResponse=marriageService.getMarriageData(marRequest);
        getModel().populateModel(marriageModel,type,marResponse);
        this.getModel().getMarriageDTO().setServiceId(marResponse.getServiceId());
        this.getModel().getMarriageDTO().setDeptId(marResponse.getDeptId());
        this.getModel().getMarriageDTO().setDepartmentName(marResponse.getDeptName());
        this.getModel().getMarriageDTO().setServiceShortCode(marResponse.getServiceName());
        this.getModel().getMarriageDTO().setSmServiceDuration(marResponse.getSmSLA());
        // Checking condition whether checkList applicable for particular Service.
        if (StringUtils
                .equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(marResponse.getSmChklstVerify(),
                        UserSession.getCurrent().getOrganisation()).getLookUpCode(), "A")) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
        } else {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
        }
        if (StringUtils.equalsIgnoreCase(marResponse.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
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
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
            marriageModel.setWitnessModeType("N");
        }
        switch (type) {
        case "V":
            marriageModel.setModeType("V");
            marriageModel.setWitnessModeType("N");
            return new ModelAndView("MarriageRegView", MainetConstants.FORM_NAME, marriageModel);
        case "D":
            // draft mode view page use with modeType=D
            marriageModel.setModeType(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
            //D#133974 applnCopyTo radio button set
            /*String firstName = "";
            if (marriageModel.getMarriageDTO().getHusbandDTO() != null) {
                firstName = marriageModel.getMarriageDTO().getHusbandDTO().getFirstNameEng();
                if (StringUtils.isNotBlank(firstName) && firstName.equalsIgnoreCase(marriageModel.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
                    marriageModel.getMarriageDTO().setApplnCopyTo("H");
                }else if (marriageModel.getMarriageDTO().getWifeDTO() != null) {
                    firstName = marriageModel.getMarriageDTO().getWifeDTO().getFirstNameEng();
                    if (StringUtils.isNotBlank(firstName) && firstName.equalsIgnoreCase(marriageModel.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
                        marriageModel.getMarriageDTO().setApplnCopyTo("W");
                 }else {
                	 marriageModel.getMarriageDTO().setApplnCopyTo("O");	 
                 }
                }
            }*/
            
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
            model.getMarriageDTO().setApplicationId(null);
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
        }else {
        	//D#133974 applnCopyTo radio button set
            /*String firstName = "";
            if (model.getMarriageDTO().getHusbandDTO() != null) {
                firstName = model.getMarriageDTO().getHusbandDTO().getFirstNameEng();
                if (StringUtils.isNotBlank(firstName)
                        && firstName.equalsIgnoreCase(model.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
                    model.getMarriageDTO().setApplnCopyTo("H");
                }else if (model.getMarriageDTO().getWifeDTO() != null) {
                    firstName = model.getMarriageDTO().getWifeDTO().getFirstNameEng();
                    if (StringUtils.isNotBlank(firstName)
                            && firstName.equalsIgnoreCase(model.getMarriageDTO().getApplicantDetailDto().getApplicantFirstName())) {
                        model.getMarriageDTO().setApplnCopyTo("W");
                    }else {
                        model.getMarriageDTO().setApplnCopyTo("O");
                    }
            } 
            } */
        }
        return new ModelAndView(MARRIAGE, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveMarriagePage")
    public ModelAndView saveMarriagePage(final Model model, final HttpServletRequest request)  {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
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
            
            marriageDTO.setServiceId(this.getModel().getMarriageDTO().getServiceId());
            marriageDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            marriageDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            marriageDTO.setStatus(this.getModel().getStatus());
            marriageDTO.setUrlParam(MainetConstants.MRM.SHOW_MRG_Page);
            marriageDTO.setUrlShortCode(MainetConstants.MRM.MRG_URL_CODE);
            
            try {
				marriageDTO=marriageService.saveMarriageRegInDraftMode(marriageDTO);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}
            if (!marriageDTO.getErrorList().isEmpty()) {

                getModel().addValidationError(getApplicationSession()
                        .getMessage(marriageDTO.getErrorList().get(0)));

                mv = new ModelAndView("MarriageValidn", MainetConstants.FORM_NAME, getModel());
                //mv = new ModelAndView(MARRIAGE, MainetConstants.FORM_NAME, marriageModel);
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;

            }
            if(marriageDTO.getApplicationId()== null) {
            	marriageModel.addValidationError("Something went wrong \n Please contact to Administrator and try again later!");
            	model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, marriageModel);
                mv = new ModelAndView(MARRIAGE, MainetConstants.FORM_NAME, marriageModel);
                return mv;
            }
            
            
            
            this.getModel().getMarriageDTO().setMarId(marriageDTO.getMarId());
            this.getModel().getMarriageDTO().setApplicationId(marriageDTO.getApplicationId());
            marriageModel.getMarriageDTO().setMarId(marriageDTO.getMarId());
            marriageModel.getMarriageDTO().setApplicationId(marriageDTO.getApplicationId());
            
            // D#127349
            if (StringUtils.isNotBlank(marriageDTO.getApplnCopyTo())&& marriageDTO.getApplnCopyTo().equals("H")) {
            	marriageModel.getMarriageDTO().getHusbandDTO().setFirstNameEng(marriageDTO.getApplicantDetailDto().getApplicantFirstName());
            	marriageModel.getMarriageDTO().getHusbandDTO().setMiddleNameEng(marriageDTO.getApplicantDetailDto().getApplicantMiddleName());
            	marriageModel.getMarriageDTO().getHusbandDTO().setLastNameEng(marriageDTO.getApplicantDetailDto().getApplicantLastName());
            	
            	if(StringUtils.isNotBlank(marriageDTO.getApplicantDetailDto().getAadharNo())) {
            		marriageModel.getMarriageDTO().getHusbandDTO().setUidNo(marriageDTO.getApplicantDetailDto().getAadharNo());     
                }
            	
                // address set
            	marriageModel.getMarriageDTO().getHusbandDTO().setFullAddrEng(marriageDTO.getApplicantDetailDto().getAreaName() + " "
                        + marriageDTO.getApplicantDetailDto().getVillageTownSub() + " "
                        + marriageDTO.getApplicantDetailDto().getRoadName());

            } else if (StringUtils.isNotBlank(marriageDTO.getApplnCopyTo())&&marriageDTO.getApplnCopyTo().equals("W")) {
            	marriageModel.getMarriageDTO().getWifeDTO().setFirstNameEng(marriageDTO.getApplicantDetailDto().getApplicantFirstName());
            	marriageModel.getMarriageDTO().getWifeDTO().setMiddleNameEng(marriageDTO.getApplicantDetailDto().getApplicantMiddleName());
            	marriageModel.getMarriageDTO().getWifeDTO().setLastNameEng(marriageDTO.getApplicantDetailDto().getApplicantLastName());
            	
            	if(StringUtils.isNotBlank(marriageDTO.getApplicantDetailDto().getAadharNo())) {
            		marriageModel.getMarriageDTO().getWifeDTO().setUidNo(marriageDTO.getApplicantDetailDto().getAadharNo());   
                }
            	
            	
                // address set
            	marriageModel.getMarriageDTO().getWifeDTO().setFullAddrEng(marriageDTO.getApplicantDetailDto().getAreaName() + " "
                        + marriageDTO.getApplicantDetailDto().getVillageTownSub() + " "
                        + marriageDTO.getApplicantDetailDto().getRoadName());
            } 
            
            mv = new ModelAndView(HUSBAND, MainetConstants.FORM_NAME, marriageModel);

        }else {
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
            //file convert in base64
            final File photoFile = marriageDTO.getUploadMap().get("90H");
            if(photoFile!= null) {
            	marriageDTO.getHusbandDTO().setCapturePhotoName(getFileName(photoFile));
            	marriageDTO.getHusbandDTO().setCapturePhotoPath(convertInBase64(photoFile));	
            }
            
            final File thumbFile = marriageDTO.getUploadMap().get("91H");
            if(thumbFile!= null) {
            	marriageDTO.getHusbandDTO().setCaptureFingerprintName(getFileName(thumbFile));
            	marriageDTO.getHusbandDTO().setCaptureFingerprintPath(convertInBase64(thumbFile));	
            }
            
            try {
				marriageDTO=marriageService.saveMarriageRegInDraftMode(marriageDTO);
			} catch (IOException e) {
				throw new FrameworkException(e);
			}
            this.getModel().getMarriageDTO().getHusbandDTO().setHusbandId(marriageDTO.getHusbandDTO().getHusbandId());
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
    public ModelAndView saveWifePage(final Model model, final HttpServletRequest request) throws Exception  {
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
            
            //file convert in base64
            final File photoFile = marriageDTO.getUploadMap().get("990W");
            if(photoFile!= null) {
                marriageDTO.getWifeDTO().setCapturePhotoName(getFileName(photoFile));
            	marriageDTO.getWifeDTO().setCapturePhotoPath(convertInBase64(photoFile));	
            }
            
            final File thumbFile = marriageDTO.getUploadMap().get("991W");
            if(thumbFile!= null) {
                marriageDTO.getWifeDTO().setCaptureFingerprintName(getFileName(thumbFile));	
            	marriageDTO.getWifeDTO().setCaptureFingerprintPath(convertInBase64(thumbFile));	
            }

            marriageDTO=marriageService.saveMarriageRegInDraftMode(marriageDTO);
            this.getModel().getMarriageDTO().getWifeDTO().setWifeId(marriageDTO.getWifeDTO().getWifeId());
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
        if (resetOption != null && resetOption.trim().equals(RESET)) {

        } else {
        	// D#128492 check here duplicate AADHAR NO
            /*boolean duplicateUID = model.getMarriageDTO().getWitnessDetailsDTO().stream()
                    .anyMatch(witness -> witness.getUidNo().equals(model.getWitnessDTO().getUidNo()));
            if (duplicateUID) {
                model.addValidationError(getApplicationSession().getMessage("mrm.witness.duplicateUI"));
                ModelAndView mv = new ModelAndView("WitnessValidn", MainetConstants.FORM_NAME, getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }*/
        	
            // get occupation DESC from occupation prefix -OCU
            LookUp lookupOCU = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getWitnessDTO().getOccupation());
            //D#127392
            LookUp lookupRLS = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getWitnessDTO().getRelation());
            // here 1st check witness OBJ has srNo than it mean update the existing object list
            if (!model.getMarriageDTO().getWitnessDetailsDTO().isEmpty() && model.getWitnessDTO().getSrNo() != 0) {
                model.getMarriageDTO().getWitnessDetailsDTO().forEach(witness -> {
                    if (witness.getSrNo() == model.getWitnessDTO().getSrNo()) {
                        model.getWitnessDTO().setOccupationDesc(lookupOCU.getLookUpDesc());
                        model.getWitnessDTO().setRelationDesc(lookupRLS.getLookUpDesc());
                        if (lookupRLS.getLookUpCode().equals("OT") && StringUtils.isNotBlank(witness.getOtherRel())) {
                            model.getWitnessDTO().setRelationDesc(witness.getOtherRel());
                        }
                        witness = model.getWitnessDTO();
                    }
                });
            } else {
                model.getWitnessDTO().setSrNo(model.getMarriageDTO().getWitnessDetailsDTO().size() + 1);
                model.getWitnessDTO().setOccupationDesc(lookupOCU.getLookUpDesc());
                if(lookupRLS!= null) {
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
        WitnessDetailsDTO witnessDetailsDTO = model.getMarriageDTO().getWitnessDetailsDTO().get(srNo - 1);
        model.setWitnessDTO(witnessDetailsDTO);

        return new ModelAndView(WITNESS, MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = "saveWitnessPage", method = RequestMethod.POST)
    public Map<String, Object> saveWitnessPage(HttpServletRequest httpServletRequest) throws Exception {
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
        /*if(this.getModel().saveForm(witnessDetailsDTOs)) {
        	if ((getModel().getSuccessMessage() != null) && !getModel().getSuccessMessage().isEmpty()) {
                return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
            }
        }else {
            return jsonResult(JsonViewObject.failureResult("Something Went Wrong"));
        }*/
        
        if (this.getModel().saveForm(witnessDetailsDTOs)) {
            //object.put("applicationId",this.getModel().getMarriageDTO().getApplicationId());
        	object.put(MainetConstants.SUCCESS,getModel().getSuccessMessage());
        } else {
            object.put(MainetConstants.MRM.ERROR,this.getModel().getBindingResult().getAllErrors());
        }
        return object;
        //return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));

    }

    @RequestMapping(params = "printMarriageRegAckw", method = {
            RequestMethod.POST })
    public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        MarriageDTO marriageDTO = marriageModel.getMarriageDTO();

        marriageDTO.setApplicantName(marriageDTO.getApplicantDetailDto().getApplicantFirstName() + " "
                + marriageDTO.getApplicantDetailDto().getApplicantLastName());
        marriageDTO.setServiceShortCode(marriageDTO.getServiceShortCode());
        marriageDTO.setDepartmentName(marriageDTO.getDepartmentName());
        marriageDTO.setServiceShortCodeMar(marriageDTO.getServiceShortCodeMar());
        marriageDTO.setDepartmentNameMar(marriageDTO.getDepartmentNameMar());
        marriageDTO.setAppDate(new Date());
        marriageDTO.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        marriageDTO.setDueDate(Utility.getAddedDateBy2(marriageDTO.getAppDate(),
        		marriageDTO.getSmServiceDuration().intValue()));// service sla unit + application date
        //marriageDTO.setHelpLine("Help Line -25015256");
        // runtime print acknowledge or certificate
        String viewName = "MarriageRegAcknow";
        if (this.getModel().getStatus().equalsIgnoreCase("APPROVED")) {
            viewName = "marriageCertificate";
        }
        //D#122248 fetch checklist result if not fetch already
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

    

    @RequestMapping(params = "getChecklistAndCharges", method = { RequestMethod.POST })
    public ModelAndView getChecklistAndCharges(final HttpServletRequest request) {

        this.getModel().bind(request);
        MarriageRegistrationModel model = this.getModel();

        ModelAndView mv = new ModelAndView("WitnessValidn",MainetConstants.FORM_NAME, getModel());

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

            final List<Object> castCheckListModel = JersyCall.castResponse(checkListRateResponseModel,
                    CheckListModel.class, 0);
            final List<Object> mrmRateMasterList = JersyCall.castResponse(checkListRateResponseModel,
                    MRMRateMaster.class, 1);

            final CheckListModel checkListModel = (CheckListModel) castCheckListModel.get(0);
            final MRMRateMaster MRMRateMaster = (MRMRateMaster) mrmRateMasterList.get(0);

            populateChecklistModel(model, checkListModel);
            
            //D#122248
            
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(MainetConstants.MRM.CHECK_LIST);
			checklistReqDto.setDataModel(checkListModel);
			//D#124595 call checklist without usageSubtype1 i.e for unmarried
			finalCheckList = brmsCommonService.getChecklist(checkListModel);
			
			if (finalCheckList != null && !finalCheckList.isEmpty()) {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
				
				if (CollectionUtils.isNotEmpty(finalCheckList)) {
					long count = 1;
					for (DocumentDetailsVO doc : finalCheckList) {
						doc.setDocumentSerialNo(count);
						count++;
					}
					
					// check here husband and wife status if both have same STA Prefix(U) than do not call again for checklist
	                String husbSTACode = CommonMasterUtility
	                        .getNonHierarchicalLookUpObject(model.getMarriageDTO().getHusbandDTO().getStatusMarTime(),
	                                UserSession.getCurrent().getOrganisation())
	                        .getLookUpCode();
	                String wifeSTACode = CommonMasterUtility
	                        .getNonHierarchicalLookUpObject(model.getMarriageDTO().getWifeDTO().getStatusMarTime(),
	                                UserSession.getCurrent().getOrganisation())
	                        .getLookUpCode();
	                //U means unmarried
	                if (!"U".equals(husbSTACode)) {
	                    // call using husband status selected
	                    checkListModel.setUsageSubtype1(CommonMasterUtility
	                            .getNonHierarchicalLookUpObject(model.getMarriageDTO().getHusbandDTO().getStatusMarTime(),
	                                    UserSession.getCurrent().getOrganisation())
	                            .getLookUpCode());
	                    List<DocumentDetailsVO> husbandCheckList = new ArrayList<>();
	                    husbandCheckList =  brmsCommonService.getChecklist(checkListModel);
	                    Long husbCount = (long) finalCheckList.size();
	                    for (DocumentDetailsVO docHusb : husbandCheckList) {
	                    	husbCount++;
	                        docHusb.setDocumentSerialNo(husbCount);
	                    }
	                    finalCheckList.addAll(husbandCheckList);
	                }
	                if (!"U".equals(wifeSTACode)) {
	                    // call using wife status selected
	                    checkListModel.setUsageSubtype1(CommonMasterUtility
	                            .getNonHierarchicalLookUpObject(model.getMarriageDTO().getWifeDTO().getStatusMarTime(),
	                                    UserSession.getCurrent().getOrganisation())
	                            .getLookUpCode());
	                    List<DocumentDetailsVO> wifeCheckList = new ArrayList<>();
	                    wifeCheckList = brmsCommonService.getChecklist(checkListModel);
	                    Long countWife = (long) finalCheckList.size();
	                    for (DocumentDetailsVO docWife : wifeCheckList) {
	                        countWife++;
	                        docWife.setDocumentSerialNo(countWife);
	                    }
	                    finalCheckList.addAll(wifeCheckList);
	                }
					
					model.setCheckList(finalCheckList);
				}
			}else {
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


    
    private Double callBrmsForApplicationCharges(MarriageRegistrationModel model, MRMRateMaster MRMRateMaster) {
        Double amoutToPay = 0.0;
        final WSRequestDTO mrmRateRequestDto = new WSRequestDTO();
        MRMRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        MRMRateMaster.setServiceCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        MRMRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.APL,
                                		PrefixConstants.Common.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
        // set no of days (application date - marriage date)
        MRMRateMaster.setNoOfDays(
                Utility.getDaysBetweenDates(model.getMarriageDTO().getMarDate(), model.getMarriageDTO().getAppDate()));
        mrmRateRequestDto.setDataModel(MRMRateMaster);
        WSResponseDTO mrmRateResponseDto = BRMSMRMService.getApplicableTaxes(mrmRateRequestDto);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(mrmRateResponseDto.getWsStatus())) {
            if (!mrmRateResponseDto.isFree()) {
            	final List<?> rates = JersyCall.castResponse(mrmRateResponseDto, MRMRateMaster.class);
                final List<MRMRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    MRMRateMaster master1 = (MRMRateMaster) rate;
                    master1 = populateChargeModel(model, master1);
                    requiredCHarges.add(master1);
                }
                WSRequestDTO chargeReqDto = new WSRequestDTO();
                chargeReqDto.setModelName("MRMRateMaster");
                chargeReqDto.setDataModel(requiredCHarges);
                
                
                List<ChargeDetailDTO> detailDTOs = BRMSMRMService.getApplicableCharges(requiredCHarges);
                //model.getAgencyRequestDto().setFree(false);
				model.setPayableFlag(MainetConstants.FlagY);
				model.setChargesInfo(detailDTOs);
				model.setAmountToPay((chargesToPay(detailDTOs)));
				model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
				model.setApplicationChargeApplFlag(MainetConstants.FlagN);
                amoutToPay = model.getAmountToPay();

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

    @RequestMapping(params = "getCustomUploadedImage", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getUploadedImage(final HttpServletRequest httpServletRequest) {

        getModel().setUploadedfile(getModel().getCachePathUpload(getModel().getUploadType()));
        return getModel().getUploadedfile();
    }

    @RequestMapping(params = "deleteSingleUpload", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> deleteSingleUpload(@RequestParam("deleteMapId") final Long deleteMapId,
            @RequestParam("deleteId") final Long deleteId) {

        return getModel().deleteSingleUpload(deleteMapId, deleteId);
    }
    
    
    //D#110655
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
   	public ModelAndView showDetails(@RequestParam("appId") final long appId,
   			final HttpServletRequest httpServletRequest) throws Exception {
   		getModel().bind(httpServletRequest);
   		
   		sessionCleanup(httpServletRequest);
    	FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        MarriageRegistrationModel marriageModel = this.getModel();
        marriageModel.setCommonHelpDocs("MarriageRegistration.html");
        MarriageRequest marRequest = new MarriageRequest();
        marRequest.setSmShortCode(MainetConstants.MRM.SERVICE_CODE.MRG);
        marRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        marRequest.setApplicationId(appId);
        MarriageResponse marResponse=marriageService.getMarriageData(marRequest);
        marriageModel.setApprovalProcess("N");
        if(marResponse.getMarriageDTO().getStatus().equals("D")) {
        	getModel().populateModel(marriageModel,"D",marResponse);
        	marriageModel.setModeType("D");
            marriageModel.setWitnessModeType("");
        }else {
        	getModel().populateModel(marriageModel,"V",marResponse);
        	marriageModel.setModeType("V");
            marriageModel.setWitnessModeType("N");
        }

        this.getModel().getMarriageDTO().setServiceId(marResponse.getServiceId());
        this.getModel().getMarriageDTO().setDeptId(marResponse.getDeptId());
        this.getModel().getMarriageDTO().setDepartmentName(marResponse.getDeptName());
        this.getModel().getMarriageDTO().setServiceShortCode(marResponse.getServiceName());
        this.getModel().getMarriageDTO().setSmServiceDuration(marResponse.getSmSLA());
        // Checking condition whether checkList applicable for particular Service.
        if (StringUtils
                .equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(marResponse.getSmChklstVerify(),
                        UserSession.getCurrent().getOrganisation()).getLookUpCode(), "A")) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
        } else {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
        }
        if (StringUtils.equalsIgnoreCase(marResponse.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagY);
            this.getModel().getMarriageDTO().setFree(false);
        } else {
            this.getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
            this.getModel().getMarriageDTO().setFree(true);
        }
   		
   		
        return new ModelAndView("MarriageRegView", MainetConstants.FORM_NAME, marriageModel);
   	}
    
    
    private String getFileName(File uploadfile) {
    	String uploadFileName = uploadfile.getName();
        if ((uploadFileName != null) && uploadFileName.contains("/")) {
            uploadFileName = uploadFileName.replace("/", "_");
        }
        return uploadFileName;
    }
    
    private String convertInBase64(File file) {
    	final Base64 base64 = new Base64();
        String base64String= null;
		try {
			base64String = base64.encodeToString(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			LOG.error("Exception has been occurred in file byte to string conversions Marriage Module ", e);
			e.printStackTrace();
		}
        return base64String;
    }

    

}
