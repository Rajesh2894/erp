
package com.abm.mainet.rnl.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.ServiceShortCode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingCalanderDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.CalanderDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.service.IRNLChecklistAndChargeService;
import com.abm.mainet.rnl.ui.model.EstateBookingModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("EstateBooking.html")
public class EstateBookingController extends AbstractFormController<EstateBookingModel> {

    private final Logger LOGGER = Logger.getLogger(EstateBookingController.class);

    @Autowired
    private IPortalServiceMasterService iPortalService;
    

    @Autowired
    private IRNLChecklistAndChargeService iRNLChecklistAndChargeService;

    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

    @Autowired
    private ICommonBRMSService commonBRMSService;
    
    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
	ICommonBRMSService iCommonBRMSService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("EstateBooking.html");
        final Long orgId = Utility.getOrgId();
        final List<LookUp> list = CommonMasterUtility.getSecondLevelData(MainetConstants.EstateBooking.CATEGORY_PREFIX_NAME,
                MainetConstants.EstateBooking.LEVEL);
        uiModel.addAttribute("categorySubType", list);
        final Long serviceId = iPortalService.getServiceId(ServiceShortCode.RNL_ESTATE_BOOKING, orgId);
        final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
        getModel().setServiceId(serviceId);
        getModel().setDeptId(deptId);
        return new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKING_HOME, MainetConstants.FORM_NAME,
                getModel());
    }

    @ResponseBody
    @RequestMapping(params = "property/filterList", method = RequestMethod.POST)
    public List<EstatePropResponseDTO> getFilterdList(@RequestParam("type") final Integer typeId) {
        PropertyResDTO responseObj = null;
        final Long orgId = Utility.getOrgId();
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO(orgId, null, null, null, typeId, null, null, null);
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.FILTER_PROP_LIST);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            final String jsonStr = getJsonString(responseEntity);
            try {
                responseObj = new ObjectMapper().readValue(jsonStr, PropertyResDTO.class);
                return responseObj.getEstatePropResponseDTOs();
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
            }

        } else {
            LOGGER.error("Fetch all filterd rented properties failed due to :" + responseEntity.getBody());
        }
        return null;
    }

    /**
     * Shows a form page in order to Book Property
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "propId") final Long propId) {
        final EstateBookingModel estateBookingModel = getModel();
        
        final Long orgId = Utility.getOrgId();
        EstatePropResponseDTO estatePropResponseDTO = null;
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        requestObj.setOrgId(orgId);
        requestObj.setPropId(propId);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.EMP_ORG,PrefixConstants.Prefix.EPLOYEE_ORGNA_ISBPLM ,
                UserSession.getCurrent().getOrganisation());
        if(lookup!=null) {
             // String empOrg= lookup.getDefaultVal();
             // this.getModel().setOrgShowHide(empOrg);
             estateBookingModel.setOrgShowHide(lookup.getDefaultVal());
        }
               LookUp lookupCode = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.IS_BPL,PrefixConstants.Prefix.EPLOYEE_ORGNA_ISBPLM ,
                       UserSession.getCurrent().getOrganisation());
               if(lookupCode!=null) {
                     //String isBpl= lookupCode.getDefaultVal();
                     //this.getModel().setIsBplShowHide(isBpl);
                   estateBookingModel.setIsBplShowHide(lookupCode.getDefaultVal());
               }
               
           ModelAndView modelAndView = new ModelAndView("EstateBooking", MainetConstants.FORM_NAME, estateBookingModel);
          final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_PROPERTY);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            final String jsonStr = getJsonString(responseEntity);
            try {
                estatePropResponseDTO = new ObjectMapper().readValue(jsonStr, EstatePropResponseDTO.class);
                estateBookingModel.getBookingReqDTO().setEstatePropResponseDTO(estatePropResponseDTO);
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                modelAndView = new ModelAndView("defaultExceptionView");
            }

        } else {
            LOGGER.error("Fetch all rented properties details failed due to :" + responseEntity.getBody());
            modelAndView = new ModelAndView("defaultExceptionView");
        }

        final ResponseEntity<?> responseEntityDates = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_DISABLE_DATES);
        if (responseEntityDates.getStatusCode() == HttpStatus.OK) {
            final String jsonStr1 = getJsonString(responseEntityDates);
            try {
                final CalanderDTO calanderDTO = new ObjectMapper().readValue(jsonStr1, CalanderDTO.class);
                estateBookingModel.setFromAndToDate(calanderDTO.getDatesList());
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                modelAndView = new ModelAndView("defaultExceptionView");
            }

        } else {
            LOGGER.error("Fetch all disables dates list failed due to :" + responseEntityDates.getBody());
            modelAndView = new ModelAndView("defaultExceptionView");
        }
        estateBookingModel.setPropId(propId);
        setEmployeeDetails(estateBookingModel);
      
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        bindModel(httpServletRequest);
        final EstateBookingModel model = getModel();
        final long orgId = Utility.getOrgId();
        ModelAndView modelAndView = null;
        try {
            if (model.validateInputs()) {

                findApplicableCheckListAndCharges(ServiceShortCode.RNL_ESTATE_BOOKING, orgId, httpServletRequest);
                // change it accordingly as above
                getModel().setEnableSubmit(true);
                getModel().setEnableCheckList(false);
                modelAndView = new ModelAndView("paymantAndCheckListRent", "command", getModel());

            }

            if (getModel().getBindingResult() != null) {
            	modelAndView = new ModelAndView("EstateBookingValidn", "command", getModel());
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX
                        + MainetConstants.FORM_NAME, getModel().getBindingResult());
                
            }
            // this.getModel().setCheckFlag("Y");
        } catch (final Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
            modelAndView = new ModelAndView("EstateBookingValidn",MainetConstants.CommonConstants.COMMAND, getModel());
            getModel().getBindingResult().addError(new ObjectError("", ex.getMessage()));
            modelAndView.addObject(
                    BindingResult.MODEL_KEY_PREFIX
                            + ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
                    getModel().getBindingResult());
			/*
			 * modelAndView = defaultExceptionFormView(); throw new
			 * FrameworkException("Problem while finding checklist and charges: ", ex);
			 */
        }

        return modelAndView;
    }

    /**
     * 
     * @param serviceId
     * @param orgId
     */

    public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId,
            final HttpServletRequest httpServletRequest) {

        // [START] BRMS call initialize model
        getModel().bind(httpServletRequest);
        final EstateBookingModel model = getModel();
        final WSRequestDTO dto = new WSRequestDTO();
        // final WSResponseDTO response = iRNLChecklistAndChargeService.initializeModel();
        WSRequestDTO initReq = new WSRequestDTO();
        initReq.setModelName(MainetConstants.RNL_Common.CHECKLIST_RNL_MODEL_NAME);
        final WSResponseDTO response = commonBRMSService.initializeModel(initReq);

        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final BookingReqDTO bookingReqDTO = getModel().getBookingReqDTO();
            final EstatePropResponseDTO estatePropResponseDTO = bookingReqDTO.getEstatePropResponseDTO();
            final EstateBookingDTO estateBookingDTO = bookingReqDTO.getEstateBookingDTO();
            final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
            final List<Object> rnlRateMasterList = JersyCall.castResponse(response, RNLRateMaster.class, 1);
            final CheckListModel checkListModel = (CheckListModel) checklistModel.get(0);
            final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);

            // setting checkList parameter
            populateCheckListModel(model, checkListModel, estatePropResponseDTO);
            // dto.setDataModel(checkListModel);
            // final List<DocumentDetailsVO> checklistResponse = iRNLChecklistAndChargeService.getChecklist(checkListModel);
            final List<DocumentDetailsVO> checklistResponse = commonBRMSService.getChecklist(checkListModel);
            if (!CollectionUtils.isEmpty(checklistResponse)) {
                Long count = 1L;
                for (DocumentDetailsVO documentDetailsVO : checklistResponse) {
                    documentDetailsVO.setDocumentSerialNo(count);
                    count++;
                }
            }
            getModel().setCheckList(checklistResponse); // checklist done

            // setting default parameter rnl rate master parameter
            rnlRateMaster.setOrgId(orgId);
            rnlRateMaster.setServiceCode(ServiceShortCode.RNL_ESTATE_BOOKING);
            rnlRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
                    .getValueFromPrefixLookUp(MainetConstants.EstateBooking.APL_PREFIX, MainetConstants.EstateBooking.CAA_PREFIX)
                    .getLookUpId()));
            dto.setDataModel(rnlRateMaster);

            final WSResponseDTO res = iRNLChecklistAndChargeService.getApplicableTaxes(rnlRateMaster,
            		Utility.getOrgId(), serviceCode);
            
            // D#101787
            LookUp lookupPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue("SHF",
            		PrefixConstants.Prefix.EPLOYEE_ORGNA_ISBPLM, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());

            // for Booking Fees (BKF) Only
            LookUp lookupBooking = CommonMasterUtility.getValueFromPrefixLookUp("BKF",
            		"TXN", /* TAX_PREFIX */ UserSession.getCurrent().getOrganisation());
            
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

                if (!res.isFree()) {
                    final List<?> rates = JersyCall.castResponse(res, RNLRateMaster.class);
                    final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        final RNLRateMaster master1 = (RNLRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(ServiceShortCode.RNL_ESTATE_BOOKING);
                        master1.setDeptCode(MainetConstants.EstateBooking.RNL_DEPT_CODE);
                        master1.setChargeApplicableAt(CommonMasterUtility.findLookUpDesc(MainetConstants.EstateBooking.CAA_PREFIX,
                        		Utility.getOrgId(),
                                Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
                        master1.setTaxSubCategory(
                                getSubCategoryDesc(master1.getTaxSubCategory(), UserSession.getCurrent().getOrganisation()));
                        master1.setFactor4(estatePropResponseDTO.getPropName());

                        master1.setOccupancyType(
                                CommonMasterUtility.getCPDDescription(bookingReqDTO.getEstateBookingDTO().getPurpose(), "E"));
                        String isbPl = bookingReqDTO.getApplicantDetailDto().getIsBPL();

                        String isEMployee = bookingReqDTO.getApplicantDetailDto().getIsOrganisationEmployeeFalg();
                        master1.setIsOrganisationalEmployee(isEMployee);
                        master1.setIsBPL(isbPl);
                        /*
                         * master1.setUsageSubtype1(estatePropResponseDTO.getUsage());
                         * master1.setUsageSubtype2(estatePropResponseDTO.getType());
                         * master1.setUsageSubtype3(estatePropResponseDTO.getSubType());
                         * master1.setRateStartDate(estateBookingDTO.getToDate().getTime());
                         * master1.setFloorLevel(estatePropResponseDTO.getFloor());
                         * master1.setOccupancyType(estatePropResponseDTO.getOccupancy());
                         * master1.setRoadType(estatePropResponseDTO.getRoadType());
                         */
                        int days = noOfBookingDays(estateBookingDTO);
                        master1.setNoOfBookingDays(days);
                        

                        if (lookupBooking.getLookUpDesc().equalsIgnoreCase(master1.getTaxSubCategory())&& lookupPrefix.getOtherField() != null
                                && lookupPrefix.getOtherField().equalsIgnoreCase("Y")) {
                            // here find out the shift Value using shift id
                            LookUp shiftLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getBookingReqDTO().getEstateBookingDTO().getShiftId());
                            master1.setShiftType(shiftLookup.getLookUpCode());
                        }
                        
                        if (CollectionUtils.isNotEmpty(master1.getDependsOnFactorList())) {
                            for (String dependFactor : master1.getDependsOnFactorList()) {
                                if (StringUtils.equalsIgnoreCase(dependFactor, "USB")) {
                                    master1.setUsageSubtype1(estatePropResponseDTO.getUsage());
                                }
                                if (StringUtils.equalsIgnoreCase(dependFactor, "EST")) {
                                    master1.setUsageSubtype2(estatePropResponseDTO.getType());
                                }

                                if (StringUtils.equalsIgnoreCase(dependFactor, "ES")) {
                                    master1.setUsageSubtype3(estatePropResponseDTO.getSubType());
                                }
                                /*
                                 * if (StringUtils.equalsIgnoreCase(dependFactor, "OT")) {
                                 * master1.setOccupancyType(estatePropResponseDTO.getOccupancy()); }
                                 */
                                if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
                                    master1.setFloorLevel(estatePropResponseDTO.getFloor());
                                }

                            }
                        }
                        requiredCHarges.add(master1);
                    }
                    dto.setDataModel(requiredCHarges);
                    final List<ChargeDetailDTO> output = iRNLChecklistAndChargeService.getApplicableCharges(requiredCHarges);
                    if(output==null) {
                        throw new FrameworkException("Charges not Found in brms Sheet");
                     }
                    // model.setChargesInfo(output);
                    // model.setAmountToPay(chargesToPay(output));
                    model.setChargesInfo(newChargesToPay(output));
                    model.setAmountToPay(chargesToPay(model.getChargesInfo()));
                    model.setIsFree(MainetConstants.Common_Constant.NO);

                    // CHANGES BY SUHAIL

                    final WSResponseDTO res1 = iRNLChecklistAndChargeService.getApplicableTaxes(rnlRateMaster,
                    		Utility.getOrgId(), serviceCode);
                    if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res1.getWsStatus())) {

                        if (!res.isFree()) {
                            final List<?> rates1 = JersyCall.castResponse(res1, RNLRateMaster.class);
                            final List<RNLRateMaster> requiredCHarges1 = new ArrayList<>();
                            for (final Object rate : rates1) {
                                final RNLRateMaster master1 = (RNLRateMaster) rate;
                                master1.setOrgId(orgId);
                                master1.setServiceCode(ServiceShortCode.RNL_ESTATE_BOOKING);
                                master1.setDeptCode(MainetConstants.EstateBooking.RNL_DEPT_CODE);
                                master1.setChargeApplicableAt(
                                        CommonMasterUtility.findLookUpDesc(MainetConstants.EstateBooking.CAA_PREFIX,
                                        		Utility.getOrgId(),
                                                Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
                                master1.setTaxSubCategory(
                                        getSubCategoryDesc(master1.getTaxSubCategory(),
                                                UserSession.getCurrent().getOrganisation()));
                                master1.setFactor4(estatePropResponseDTO.getPropName());

                                master1.setOccupancyType(CommonMasterUtility
                                        .getCPDDescription(bookingReqDTO.getEstateBookingDTO().getPurpose(), "E"));
                                String isbPl = bookingReqDTO.getApplicantDetailDto().getIsBPL();

                                String isEMployee = bookingReqDTO.getApplicantDetailDto().getIsOrganisationEmployeeFalg();
                                master1.setIsOrganisationalEmployee(isEMployee);
                                master1.setIsBPL(isbPl);
                                
                                if (lookupBooking.getLookUpDesc().equalsIgnoreCase(master1.getTaxSubCategory())
                                        && lookupPrefix.getOtherField() != null
                                        && lookupPrefix.getOtherField().equalsIgnoreCase("Y")) {
                                    // here find out the shift Value using shift id
                                	LookUp shiftLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(model.getBookingReqDTO().getEstateBookingDTO().getShiftId());
                                    master1.setShiftType(shiftLookup.getLookUpCode());
                                }
                                /*
                                 * master1.setUsageSubtype1(estatePropResponseDTO.getUsage());
                                 * master1.setUsageSubtype2(estatePropResponseDTO.getType());
                                 * master1.setUsageSubtype3(estatePropResponseDTO.getSubType());
                                 * master1.setRateStartDate(estateBookingDTO.getToDate().getTime());
                                 * master1.setFloorLevel(estatePropResponseDTO.getFloor());
                                 * master1.setOccupancyType(estatePropResponseDTO.getOccupancy());
                                 * master1.setRoadType(estatePropResponseDTO.getRoadType());
                                 */
                                int days = noOfBookingDays(estateBookingDTO);
                                master1.setNoOfBookingDays(days);
                                master1.setTotalAmount(model.getAmountToPay());
                                if (CollectionUtils.isNotEmpty(master1.getDependsOnFactorList())) {
                                    for (String dependFactor : master1.getDependsOnFactorList()) {
                                        if (StringUtils.equalsIgnoreCase(dependFactor, "USB")) {
                                            master1.setUsageSubtype1(estatePropResponseDTO.getUsage());
                                        }
                                        if (StringUtils.equalsIgnoreCase(dependFactor, "EST")) {
                                            master1.setUsageSubtype2(estatePropResponseDTO.getType());
                                        }

                                        if (StringUtils.equalsIgnoreCase(dependFactor, "ES")) {
                                            master1.setUsageSubtype3(estatePropResponseDTO.getSubType());
                                        }
                                        /*
                                         * if (StringUtils.equalsIgnoreCase(dependFactor, "OT")) {
                                         * master1.setOccupancyType(estatePropResponseDTO.getOccupancy()); }
                                         */
                                        if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
                                            master1.setFloorLevel(estatePropResponseDTO.getFloor());
                                        }

                                    }
                                }
                                requiredCHarges1.add(master1);
                            }
                            dto.setDataModel(requiredCHarges1);
                            final List<ChargeDetailDTO> output1 = iRNLChecklistAndChargeService
                                    .getApplicableCharges(requiredCHarges1);

                            double percentageAmount=0.0;
                            
                            for (int i=0;i < output1.size();i++)
                            {
                               percentageAmount= output1.get(i).getPercentageRate();
                               String chargLebelDesc= output1.get(i).getChargeDescEng();
                               
                               
                               if(chargLebelDesc.equalsIgnoreCase("GST")) {
                                   
                                   model.setGstPercentage(percentageAmount);
                                  }
                               else if(chargLebelDesc.equalsIgnoreCase("SGST")) {
                                  
                                    model.setSgtPercenatge(percentageAmount);
                                   
                                }
                               else if(chargLebelDesc.equalsIgnoreCase("CGST")) {
                                   model.setCgstPercenatge(percentageAmount);
                               }
                               
                               }
                            

                            model.setChargesInfo(newChargesToPay(output1));
                            model.setAmountToPay(chargesToPay(model.getChargesInfo()));
                            model.setIsFree(MainetConstants.Common_Constant.NO);
                            model.getOfflineDTO().setAmountToShow(model.getAmountToPay());

                            if (model.getAmountToPay() == 0.0d) {
                            	logger.error("Service charge amountToPay cannot be " + model.getAmountToPay()
                                        + " if service configured as Chageable");
                            }
                        } else {
                            model.setIsFree(MainetConstants.Common_Constant.FREE);
                        }

                    } else {
                        throw new FrameworkException("Problem while checking dependent param for RNL rate .");
                    }
                } else {
                    throw new FrameworkException("Problem while initializing CheckList and RNLRateMaster Model");
                }
            }
        }

    }

    private int noOfBookingDays(EstateBookingDTO estateBookingDTO) {
        Date bookStartDate = estateBookingDTO.getFromDate();
        Date bookEndDate = estateBookingDTO.getToDate();
        //Defect #70809 
        int days = 0;
		EstatePropertyShiftDTO shiftDetailsTime = getShiftDetailsTime(estateBookingDTO.getShiftId(),
				getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropId());
		if (shiftDetailsTime != null) {
			if (shiftDetailsTime.getStartTime().equalsIgnoreCase(shiftDetailsTime.getEndTime())) {
				days = getDaysBetweenDatesOrFullDays(bookStartDate, bookEndDate);
			} else {
				days = Utility.getDaysBetweenDates(bookStartDate, bookEndDate);
			}
		}
		return days;
    }

    
 // Suhel converting 12 to 24 hours based on property no of days
    public static int getDaysBetweenDatesOrFullDays(Date faFromDate, Date faTodate) {
      long difference = faTodate.getTime() - faFromDate.getTime();

      // Below lines gives difference between startDate (which is exclusive) and endDate (which inclusive) so adding 1 while
      // returning
      
      float daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    if(daysBetween==0) {
      return Math.round(daysBetween) + 1;
   }
   else {
    return Math.round(daysBetween);
   }  
  }
    private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        /* EstateBookingModel estateModel = getModel(); */
        /*
         * Date bookStartDate = estateModel.getBookingReqDTO().getEstateBookingDTO().getFromDate(); Date bookEndDate =
         * estateModel.getBookingReqDTO().getEstateBookingDTO().getToDate(); int days = Utility.getDaysBetweenDates(bookStartDate,
         * bookEndDate);
         */
        for (final ChargeDetailDTO charge : charges) {
            BigDecimal amount = new BigDecimal(charge.getChargeAmount());
            /* amount = amount.multiply(new BigDecimal(days)); */
            charge.setChargeAmount(amount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }

    private CheckListModel populateCheckListModel(final EstateBookingModel model, final CheckListModel checklistModel,
            final EstatePropResponseDTO estatePropResponseDTO) {
        checklistModel.setOrgId(Utility.getOrgId());
        checklistModel.setServiceCode(ServiceShortCode.RNL_ESTATE_BOOKING);

        checklistModel.setUsageSubtype1(estatePropResponseDTO.getUsage());
        checklistModel.setUsageSubtype3(estatePropResponseDTO.getType());
        checklistModel.setUsageSubtype4(estatePropResponseDTO.getSubType());
        checklistModel.setFactor1(estatePropResponseDTO.getOccupancy());
        checklistModel.setFactor2(estatePropResponseDTO.getFloor());
        return checklistModel;

    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    @RequestMapping(params = "getCalAndMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public Object[] calenderData(@RequestParam("propId") final Long propId) {

        
      /*  chnages By SUHEL  date 13/02/2020  Defect #33666 Booking not opening if challan not paid*/
        
        EstateBookingDTO requestDTo = new EstateBookingDTO();
        requestDTo.setOrgId(Utility.getOrgId());
        requestDTo.setPropId(propId);
        this.getModel().setPropId(propId);
        Map<String, String> requestParam = new HashMap<>();
        DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
        URI uriNew = uriHandler.expand(ServiceEndpoints.JercyCallURL.GET_OPEN_DATE_CHALLAN_NOT_PAID, requestParam);
        List<LinkedHashMap<Long, Object>> responEntity = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(requestDTo, uriNew.toString());
        List<EstateBookingDTO> masterDtosList = new ArrayList<>();
            responEntity.forEach(requestObj -> {
            String jsonObject1 = new JSONObject(requestObj).toString();
            ResponseEntity<?> responseStatus =null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                EstateBookingDTO  responseObj= objectMapper.readValue(jsonObject1, EstateBookingDTO.class);
                masterDtosList.add(responseObj);
            
           EstateBookingDTO requestForStatusDto = new EstateBookingDTO();
           requestForStatusDto.setBookingNo(responseObj.getBookingNo());
           requestForStatusDto.setOrgId(Utility.getOrgId());
           requestForStatusDto.setFromDate(new Date());
           requestForStatusDto.setToDate(new Date());
           requestForStatusDto.setSortCode(ServiceShortCode.RNL_ESTATE_BOOKING);
           EstateBookingDTO responseDto=new EstateBookingDTO();
           responseStatus = JersyCall.callAnyRestTemplateClient(requestForStatusDto,
                   ServiceEndpoints.JercyCallURL.SERVICE_MASTER_CALL);
           if (responseStatus.getStatusCode() == HttpStatus.OK) {
               try {
                   System.out.println(responseStatus.getBody());
               } catch (final Exception e) {
                   LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
               }
           }
           Long challanDay=Long.valueOf(responseStatus.getBody().toString());
           int challanrcvFlag= Utility.getDaysBetweenDates(responseObj.getBookingDate(),new Date());
          if(challanrcvFlag>challanDay) {
              responseStatus = JersyCall.callAnyRestTemplateClient(requestForStatusDto,
                      ServiceEndpoints.JercyCallURL.UPDATE_DATE_BOOKING_STATUS_FLAG_N);
          }if (responseStatus.getStatusCode() == HttpStatus.OK) {
               LOGGER.info(" Date is Enbale Challan not paid ");
           }
            } catch (Exception e) {
                LOGGER.error("Date does not enable :" + responseStatus.getBody());
            }
            });
    
        final Object data[] = new Object[] { null, null, null, null };
        final List<CalanderDTO> mainList = new ArrayList<>();
        final Long orgId = Utility.getOrgId();
        BookingCalanderDTO bookingCalanderDTO = new BookingCalanderDTO();
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        EstatePropMaster estatePropMaster = null;
        requestObj.setOrgId(orgId);
        requestObj.setPropId(propId);
        String docName = null;
        List<String> pojos = null;
        List<EstateBookingDTO> bookingDTOs = null;
        String jsonStr = null;
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_CAL_DATA);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            jsonStr = getJsonString(responseEntity);
            try {
                bookingCalanderDTO = new ObjectMapper().readValue(jsonStr, BookingCalanderDTO.class);
                bookingDTOs = bookingCalanderDTO.getBookingDTOs();

                final ResponseEntity<?> responseEntityDates = JersyCall.callAnyRestTemplateClient(requestObj,
                        ServiceEndpoints.WebServiceUrl.GET_MAP_DATA);
                if (responseEntityDates.getStatusCode() == HttpStatus.OK) {
                    jsonStr = getJsonString(responseEntityDates);
                    estatePropMaster = new ObjectMapper().readValue(jsonStr, EstatePropMaster.class);

                    final ResponseEntity<?> responseEntityDoc = JersyCall.callAnyRestTemplateClient(requestObj,
                            ServiceEndpoints.WebServiceUrl.GET_TERMS);
                    if (responseEntityDoc.getStatusCode() == HttpStatus.OK) {
                        docName = (String) responseEntityDoc.getBody();
                        getModel().setDocName(docName);
                        final RestTemplate restTemplate = new RestTemplate();
                        final String uri = ApplicationSession.getInstance().getMessage("jersey.url");
                        final String completeUri = uri + ServiceEndpoints.WebServiceUrl.GET_PROP_IMAGES;
                        @SuppressWarnings("unchecked")
                        final List<String> lookup = restTemplate.postForObject(completeUri, requestObj, List.class);
                        final ObjectMapper mapper = new ObjectMapper();
                        pojos = mapper.convertValue(lookup, new TypeReference<List<String>>() {
                        });
                        //D#103436
                        final EstatePropReqestDTO propRequestObj = new EstatePropReqestDTO();
                        propRequestObj.setOrgId(orgId);
                        propRequestObj.setPropId(propId);
                        final ResponseEntity<?> propResp = JersyCall.callAnyRestTemplateClient(propRequestObj,
                                ServiceEndpoints.WebServiceUrl.GET_PROPERTY);
                        if (propResp.getStatusCode() == HttpStatus.OK) {
                            try {
                            	EstatePropResponseDTO estatePropResponse  = new ObjectMapper().readValue(getJsonString(propResp), EstatePropResponseDTO.class);
                            	iCommonBRMSService.getChecklistDocument(estatePropResponse.getPropertyNo(), orgId, "N");
                            } catch (final Exception e) {
                                LOGGER.error("Error while reading value from Estate property response: " + e.getMessage(), e);
                            }

                        } else {
                            LOGGER.error("Get Estate Property  details failed due to :" + propResp.getBody());
                            
                        }
                        
                    } else {
                        LOGGER.error("Fetch the document name failed due to :" + responseEntity.getBody());
                    }

                } else {
                    LOGGER.error("Fetch all disables dates list failed due to :" + responseEntityDates.getBody());
                }

                CalanderDTO calanderDTO = null;
                if (null != bookingDTOs) {
                    for (final EstateBookingDTO estateBookingDTO : bookingDTOs) {
                        if (estateBookingDTO.getBookingStatus().equals(MainetConstants.RNL_Common.F_FLAG)) {
                            calanderDTO = new CalanderDTO(estateBookingDTO.getId(), estateBookingDTO.getFromDate(),
                                    estateBookingDTO.getShiftName(), "bg-green-1", estateBookingDTO.getToDate(), "");
                         // D#79810
                        } else if (!estateBookingDTO.getBookingStatus().equals("U") && estateBookingDTO.getApplicationId() != 0) {
                            calanderDTO = new CalanderDTO(estateBookingDTO.getId(), estateBookingDTO.getFromDate(),
                                    estateBookingDTO.getShiftName(),
                                    estateBookingDTO.getShiftName().equals(MainetConstants.EstateBooking.SHIFT_PREFIX_GENERAL)? "bg-red-1": "bg-blue-2",
                                    estateBookingDTO.getToDate(), "");
                        }

                        mainList.add(calanderDTO);
                    }
                }
                
                final String[] mapData = new String[] { estatePropMaster.getName(), estatePropMaster.getPropLatitude(),
                        estatePropMaster.getPropLongitude() };
                data[0] = mainList;
                data[1] = mapData;
                data[2] = docName;
                data[3] = pojos;

            } catch (final Exception e) {
                throw new FrameworkException("Error while reading value from response: " + e.getMessage(), e);
            }

        } else {
            LOGGER.error("Fetch all rented properties details failed due to :" + responseEntity.getBody());
        }

        return data;
    }

    private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
        String subCategoryDesc = "";
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(MainetConstants.EstateBooking.TAC_PREFIX, 2, org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals(taxsubCategory)) {
                subCategoryDesc = lookup.getDescLangFirst();
                break;
            }
        }
        return subCategoryDesc;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(final HttpServletRequest request, final Exception exception) {
        logger.error("Exception found : ", exception);
        final boolean asyncRequest = HttpHelper.isAjaxRequest(request);
        if (asyncRequest) {
            return new ModelAndView("defaultExceptionFormView");
        } else {
            return new ModelAndView("defaultExceptionView");
        }
    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveEstateBooking(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        httpServletRequest.getSession();
        final EstateBookingModel model = getModel();
        ModelAndView mv = null;
        List<DocumentDetailsVO> docs = getModel().getCheckList();
        docs = setFileUploadMethod(docs);
        getModel().getBookingReqDTO().setDocumentList(docs);
        if (model.validateInputs()) {
            final EstateBookingDTO estateBookingDTO = model.getBookingReqDTO().getEstateBookingDTO();
            estateBookingDTO.setOrgId(Utility.getOrgId());
            estateBookingDTO.setLangId(UserSession.getCurrent().getLanguageId());
            estateBookingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            estateBookingDTO.setCreatedDate(new Date());
            estateBookingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            model.getBookingReqDTO().setPayAmount(model.getAmountToPay());
            model.getBookingReqDTO().setServiceId(model.getServiceId());
            model.getBookingReqDTO().setDeptId(model.getDeptId());
            BookingResDTO bookingResDTO = new BookingResDTO();
            final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(model.getBookingReqDTO(),
                    ServiceEndpoints.WebServiceUrl.SAVE_BOOKING);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final String jsonStr = getJsonString(responseEntity);
                try {
                    bookingResDTO = new ObjectMapper().readValue(jsonStr, BookingResDTO.class);
                    model.setApplicationNo(bookingResDTO.getApplicationNo());
                    final ApplicationPortalMaster applicationMaster = saveApplcationMaster(model.getServiceId(),
                            bookingResDTO.getApplicationNo(),
                            FileUploadUtility.getCurrent().getFileMap().entrySet().size());
                    iPortalServiceMasterService.saveApplicationMaster(applicationMaster, model.getCharges(),
                            FileUploadUtility.getCurrent().getFileMap().entrySet().size());
                    if (model.saveForm()) {
                        return jsonResult(
                                JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
                    }
                } catch (final Exception e) {
                    LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                    return new ModelAndView("defaultExceptionView");
                }

            } else {
                LOGGER.error("Fetch all filterd rented properties failed due to :" + responseEntity.getBody());
                return new ModelAndView("defaultExceptionView");
            }
        }

        mv = new ModelAndView("EstateBookingValidn", MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    public ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
            final int documentListSize) throws Exception {

        final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
        applicationMaster.setPamApplicationId(applicationNo);
        applicationMaster.setSmServiceId(serviceId);
        applicationMaster.setPamApplicationDate(new Date());
        applicationMaster.updateAuditFields();
        return applicationMaster;
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "termAndCondition")
    public ModelAndView termAndCondition(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        return new ModelAndView("RNLTermAndCondition", "command", getModel());
    }

    private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
        final Map<Long, String> listOfString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        fileName.put(entry.getKey(), file.getName());
                        listOfString.put(entry.getKey(), bytestring);
                    } catch (final IOException e) {
                        logger.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        if (!docs.isEmpty() && !listOfString.isEmpty()) {
            for (final DocumentDetailsVO d : docs) {
                final long count = d.getDocumentSerialNo() - 1;
                if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                    d.setDocumentByteCode(listOfString.get(count));
                    d.setDocumentName(fileName.get(count));
                }
            }
        }
        return docs;
    }

    @RequestMapping(params = "getShiftsBasedOnDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<EstatePropertyShiftDTO> getShiftsBasedOnDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, final HttpServletRequest httpServletRequest) {
        final EstateBookingModel estateBookingModel = getModel();
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        requestObj.setPropId( estateBookingModel.getPropId());
        requestObj.setFromDate(fromDate);
        requestObj.setToDate(toDate);
        requestObj.setOrgId(Utility.getOrgId());
        final RestTemplate restTemplate = new RestTemplate();
        final String uri = ApplicationSession.getInstance().getMessage("jersey.url");
        final String completeUri = uri + ServiceEndpoints.WebServiceUrl.GET_SHIFT_DATE;
        @SuppressWarnings("unchecked")
        final List<EstatePropertyShiftDTO> shiftList = restTemplate.postForObject(completeUri, requestObj, List.class);
        final ObjectMapper mapper = new ObjectMapper();
        final List<EstatePropertyShiftDTO> shiftDetailsData = mapper.convertValue(shiftList,
                new TypeReference<List<EstatePropertyShiftDTO>>() {
                });

        return shiftDetailsData;

    }

    @RequestMapping(params = "dateRangBetBookedDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String dateRangBetBookedDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, final HttpServletRequest httpServletRequest) {
        String flag = MainetConstants.EstateBooking.PASS;
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        requestObj.setPropId(getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropId());
        requestObj.setFromDate(fromDate);
        requestObj.setToDate(toDate);
        requestObj.setOrgId(Utility.getOrgId());
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_DATE_RANGE);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            try {
                flag = (String) responseEntity.getBody();
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                return "defaultExceptionView";
            }

        } else {
            LOGGER.error("Fetch all rented properties failed due to :" + responseEntity.getBody());
            return "defaultExceptionView";
        }

        return flag;
    }

    private String getJsonString(final ResponseEntity<?> responseEntity) {

        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> outPutObject = (LinkedHashMap<Long, Object>) responseEntity.getBody();
        final String jsonString = new JSONObject(outPutObject).toString();
        return jsonString;

    }

    
    private void setEmployeeDetails(final EstateBookingModel estateBookingModel) {
        final ApplicantDetailDTO applicantDetailDTO = estateBookingModel.getBookingReqDTO().getApplicantDetailDto();
        final Employee emp = UserSession.getCurrent().getEmployee();
        applicantDetailDTO.setApplicantFirstName(emp.getEmpname());
        applicantDetailDTO.setApplicantMiddleName(emp.getEmpMName());
        applicantDetailDTO.setApplicantLastName(emp.getEmpLName());
        applicantDetailDTO.setMobileNo(emp.getEmpmobno());
        applicantDetailDTO.setPinCode(emp.getPincode());
        applicantDetailDTO.setApplicantTitle(emp.getTitle());
        applicantDetailDTO.setEmailId(emp.getEmpemail());
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : lookUps) {
            if ((emp.getEmpGender() != null) && !emp.getEmpGender().isEmpty()) {
                if (lookUp.getLookUpCode().equals(emp.getEmpGender())) {
                    applicantDetailDTO.setGender(String.valueOf(lookUp.getLookUpId()));
                    break;
                }
            }
        }
    }

    @RequestMapping(params = "propertyInfo", method = RequestMethod.POST)
    public ModelAndView propertyInfo(@RequestParam(value = "bookId") final Long bookId) {
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        requestObj.setOrgId(Utility.getOrgId());
        requestObj.setPropId(this.getModel().getPropId());
        requestObj.setBooingId(bookId);
        PropInfoDTO responseObj = new PropInfoDTO();
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_PROP_INFO);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            final String jsonStr = getJsonString(responseEntity);
            try {
                responseObj = new ObjectMapper().readValue(jsonStr, PropInfoDTO.class);
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                return new ModelAndView("defaultExceptionView");
            }
            responseObj.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            getModel().setPropInfoDTO(responseObj);
        }
        return new ModelAndView("propInfo", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "filterEstateBookingList", method = RequestMethod.POST)
    public @ResponseBody List<EstatePropResponseDTO> getFilterdList(
            @RequestParam("category") final Integer categoryId, @RequestParam("eventId") final Long eventId,
            @RequestParam("capcityFrom") final long capcityFrom,
            @RequestParam("capcityTo") final long capcityTo, @RequestParam("rentFrom") final double rentFrom,
            @RequestParam("rentTo") final double rentTo,
            final HttpServletRequest httpServletRequest) {
    	Organisation org=organisationService.getOrganisationById(Utility.getOrgId());
        PropertyResDTO propResDto = iRNLChecklistAndChargeService.getFilteredRentedProperties(categoryId, eventId, capcityFrom,
                capcityTo, rentFrom, rentTo, org);
        return propResDto.getEstatePropResponseDTOs();
    }

    /**
     * get Shift Details Time
     * @param shiftId
     * @param propId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "getPropFormToTime", method = RequestMethod.POST)
    public EstatePropertyShiftDTO getShiftDetailsTime(@RequestParam("shiftId") Long shiftId,
            @RequestParam("propId") Long propId) {
        EstatePropertyShiftDTO shiftDTO = new EstatePropertyShiftDTO();
        if (this.getModel().getBookingReqDTO().getEstatePropResponseDTO().getShiftDTOsList() != null) {
            for (EstatePropertyShiftDTO shift : this.getModel().getBookingReqDTO().getEstatePropResponseDTO()
                    .getShiftDTOsList()) {
                if ((shift.getPropId().equals(propId)) && (shift.getPropShift().equals(shiftId))) {
                    shiftDTO = shift;
                }
            }
        }
        return shiftDTO;
    }

    /**
     * get Validation From Date To Date
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "getValidationFromDateToDate", method = RequestMethod.POST)
    public Map<String, String> getValidationFromDateToDate(@RequestParam("fromDate") Date fromDate,
            @RequestParam("toDate") Date toDate) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(Utility.getOrgId());
        int resultNoOfDays = 0;
        try {
            String lookUp = CommonMasterUtility.getLookUps("AND", organisation).get(0).getLookUpCode();
            resultNoOfDays = Integer.parseInt(lookUp);
        } catch (Exception exception) {
            throw new FrameworkException(" Exception occured when fetching Prefix ----> AND " + exception);
        }
        if (this.getModel().getBookingReqDTO().getEstatePropResponseDTO().getNoOfDaysAllowed() != null) {
            Long noOfDays = this.getModel().getBookingReqDTO().getEstatePropResponseDTO().getNoOfDaysAllowed();
            Integer integer = Math.toIntExact(noOfDays);
            Date date = new Date();
            Integer noOfDaysUADLevel = Utility.getDaysBetweenDates(date, fromDate);
            Integer count = Utility.getDaysBetweenDates(fromDate, toDate);

            if (resultNoOfDays > noOfDaysUADLevel) {
                if (integer >= count) {
                    // map.put("succes", MainetConstants.SUCCESS_MESSAGE);
                } else {
                    map.put("days", noOfDays.toString());
                    map.put("propName", this.getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropName());
                    // map.put("date", date.toString());
                }
            } else {
                map.put("UadLevelError", String.valueOf(resultNoOfDays));
                map.put("propName", this.getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropName());
                map.put("currentDate", Utility.dateToString(date));
            }

        }
        return map;
    }

    @ResponseBody
    @RequestMapping(params = "viewEstateBookingFacility", method = RequestMethod.POST)
    public ModelAndView viewDetailsBookingFacility(@RequestParam("propId") Long propId) {

        this.getModel().setPropId(propId);        
        final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
        requestObj.setOrgId(Utility.getOrgId());
        requestObj.setPropId(propId);
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.WebServiceUrl.GET_PROPERTY_AMINITIES_FACILITIES);
        EstatePropResponseDTO responseObj = new EstatePropResponseDTO();

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            final String jsonStr = getJsonString(responseEntity);
            try {
                responseObj = new ObjectMapper().readValue(jsonStr, EstatePropResponseDTO.class);
            } catch (final Exception e) {
                LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
                return new ModelAndView("defaultExceptionView");
            }
            this.getModel().getBookingReqDTO().setEstatePropResponseDTO(responseObj);
        }
        return new ModelAndView("viewAminities", MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = { RequestMethod.GET }, params = "showRnLChargeDetails")
    public ModelAndView showRnLChargesDetails(final HttpServletRequest httpServletRequest, final Model model) {
        EstateBookingModel estateModel = getModel();

        List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
        List<ChargeDetailDTO> gstList = new ArrayList<>();
        for(ChargeDetailDTO charges : estateModel.getChargesInfo()) {
            ChargeDetailDTO dto = new ChargeDetailDTO();
            dto = charges;
            if(charges.getChargeDescEng().equalsIgnoreCase("CGST") || charges.getChargeDescEng().equalsIgnoreCase("SGST") || charges.getChargeDescEng().equalsIgnoreCase("GST")) {
             gstList.add(dto);   
            }else {
                //modify the list based on given D#31001
                if(charges.getChargeDescEng().equalsIgnoreCase("Booking Fees")) {
                    chargesInfo.add(0, dto);
                }else if(charges.getChargeDescEng().equalsIgnoreCase("Maintenance Charge")) {
                    chargesInfo.add(1, dto);
                }else if(charges.getChargeDescEng().equalsIgnoreCase("Electricity Bill deposit")) {
                    chargesInfo.add(2, dto);
                     
               }else {
                    chargesInfo.add(dto);
                }    
            }
        }
        
        chargesInfo.addAll(gstList);
        estateModel.setChargesInfo(chargesInfo);
        return new ModelAndView("ChargesDetail", MainetConstants.FORM_NAME, getModel());
    }
    
    @ResponseBody
    @RequestMapping(params = "getCategoryAndEvent", method = RequestMethod.POST)
    public ResponseEntity<?> displayCategory(@RequestParam("category") Integer category) {
     

        EstatePropertyEventDTO requestObj = new EstatePropertyEventDTO();
        requestObj.setOrgId(Utility.getOrgId());
        requestObj.setCategoryId(category);
        final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
                ServiceEndpoints.JercyCallURL.GET_CATEGORY_BASED_SERVICE);
       
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Data Fetched");   
            }
        else {
            throw new FrameworkException("Event is Not crated in Property");
        }
        return responseEntity;
      
}
    //User Story #91836
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "getBookingDetails")
    public ModelAndView getBookingDetails(final Model uiModel, final HttpServletRequest httpServletRequest) { 
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("EstateBooking.html");
        final Long orgId = Utility.getOrgId();
        Employee employee = UserSession.getCurrent().getEmployee();
        if(employee.getEmploginname().equals(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
        	this.getModel().setCheckBookingFlag(MainetConstants.FlagY);
        }
        final List<LookUp> list = CommonMasterUtility.getSecondLevelData(MainetConstants.EstateBooking.CATEGORY_PREFIX_NAME,
                MainetConstants.EstateBooking.LEVEL);
        uiModel.addAttribute("categorySubType", list);
        final Long serviceId = iPortalService.getServiceId(ServiceShortCode.RNL_ESTATE_BOOKING, orgId);
        final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
        getModel().setServiceId(serviceId);
        getModel().setDeptId(deptId);
        PortalService portalService = iPortalService.getService(serviceId, orgId);
        getModel().setServiceURL(iPortalService.getServiceURL(portalService.getPsmSmfid()));
        return new ModelAndView("BookingAvailabilityForm", MainetConstants.FORM_NAME, getModel());
    }

    
    

}
