
package com.abm.mainet.rnl.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.HolidayMasterService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.CalanderDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.EstatePropertyEventDTO;
import com.abm.mainet.rnl.dto.EstatePropertyShiftDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.service.BRMSRNLService;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.ui.model.EstateBookingModel;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("EstateBooking.html")
public class EstateBookingController extends AbstractFormController<EstateBookingModel> {

    private final Logger LOGGER = Logger.getLogger(EstateBookingController.class);

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private BRMSRNLService brmsRNLService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private TbServicesMstService servicesMstService;
    /*
     * @Autowired private ICommonBRMSService commonBRMSService;
     */

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IEstateBookingService bookingService;
    
    @Autowired
    private HolidayMasterService holidayMasterService;

    /**
     * @param uiModel
     * @param httpServletRequest
     * @return Summary Page
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();

        final List<LookUp> list = CommonMasterUtility.getLevelData(PrefixConstants.CATEGORY_PREFIX_NAME, 2,
                UserSession.getCurrent().getOrganisation());
        /*
         * final PropertyResDTO dto = iEstateBookingService.getAllRentedProperties(PrefixConstants.CPD_VALUE_RENT,
         * PrefixConstants.CATEGORY_PREFIX_NAME, UserSession.getCurrent().getOrganisation().getOrgid(), null);
         * uiModel.addAttribute(MainetConstants.EstateBooking.PROP_LIST, dto.getEstatePropResponseDTOs());
         */
        uiModel.addAttribute(MainetConstants.EstateBooking.CATEGORY, list);
        final ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.RNL_ESTATE_BOOKING,
                UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().setService(service);
        getModel().setDeptId(getModel().getService().getTbDepartment().getDpDeptid());
        return new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKING_HOME, MainetConstants.FORM_NAME,
                getModel());
    }

    /**
     * Shows a form page in order to Book Property
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "propId") final Long propId) {
        final EstateBookingModel estateBookingModel = getModel();
        // sessionCleanup(httpServletRequest);
        final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        estateBookingModel.getBookingReqDTO().setEstatePropResponseDTO(estatePropResponseDTO);
        final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDates(propId,
                UserSession.getCurrent().getOrganisation().getOrgid(), false);
        estateBookingModel.setFromAndToDate(fromAndtoDate);
        estateBookingModel.setPropId(propId);
        /*
         * Bpl and Is Employee Requirement Code
         */
        LookUp lookupPrefix = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.EMP_ORG,
                PrefixConstants.EPLOYEE_ORGNA_ISBPLM,
                UserSession.getCurrent().getOrganisation());
        if (lookupPrefix != null) {
            // String empOrg= lookupPrefix.getDefaultVal();
            estateBookingModel.setOrgShowHide(lookupPrefix.getDefaultVal());
        }
        LookUp lookupCode = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.IS_BPL,
                PrefixConstants.EPLOYEE_ORGNA_ISBPLM,
                UserSession.getCurrent().getOrganisation());
        if (lookupCode != null) {
            // String isBpl= lookupCode.getDefaultVal();
            estateBookingModel.setIsBplShowHide(lookupCode.getDefaultVal());
        } else {
            this.getModel().addValidationError(
                    getApplicationSession().getMessage("rnl.prefix.notdefined.for.ISBpl.or.IsEmployee.organisation"));
            LOGGER.error("Prefix Is Not Define for ISBpl or IsEmployee oganisation");
        }
        List<EstatePropertyEventDTO> eventList = iEstatePropertyService.getPropEventListBypropId(propId);
        List<EstatePropertyEventDTO> eventLists = eventList.stream()
                .filter(list -> list.getPropEvent().equals(this.getModel().getEstatePropReqestDTO().getEventId()))
                .collect(Collectors.toList());
        estateBookingModel.setEventDTOsList(eventLists);
        return new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKING, MainetConstants.FORM_NAME,
                estateBookingModel);
    }

    /**
     * method is Get Applicable Check List And Charges
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final EstateBookingModel model = getModel();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView modelAndView = null;
        try {
            if (model.validateInputs()) {
                findApplicableCheckListAndCharges(MainetConstants.RNL_ESTATE_BOOKING, orgId, httpServletRequest);
                getModel().setEnableSubmit(true);
                getModel().setEnableCheckList(false);
                modelAndView = new ModelAndView(MainetConstants.EstateBooking.PAYMENT_CHECK_LIST_RENT,
                        MainetConstants.CommonConstants.COMMAND, getModel());
            }

            if (getModel().getBindingResult() != null) {
                modelAndView = new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKIN_VALID,
                        MainetConstants.CommonConstants.COMMAND, getModel());
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());
            }
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            modelAndView = new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKIN_VALID,
                    MainetConstants.CommonConstants.COMMAND, getModel());
            getModel().getBindingResult().addError(new ObjectError("", ex.getMessage()));
            modelAndView.addObject(
                    BindingResult.MODEL_KEY_PREFIX
                            + ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
                    getModel().getBindingResult());
        }

        return modelAndView;
    }

    /**
     * method is used for find Applicable Check List And Charges
     * @param serviceId
     * @param orgId
     */

    @SuppressWarnings("unchecked")
    public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId,
            final HttpServletRequest httpServletRequest) {
        // [START] BRMS call initialize model
        getModel().bind(httpServletRequest);
        final EstateBookingModel model = getModel();

        final WSRequestDTO initReqDTO = new WSRequestDTO();
        initReqDTO.setModelName(MainetConstants.RnLCommon.CHECKLIST_RNLRATEMASTER);

        final WSResponseDTO response = brmsCommonService.initializeModel(initReqDTO);

        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final BookingReqDTO bookingReqDTO = getModel().getBookingReqDTO();
            final EstatePropResponseDTO estatePropResponseDTO = bookingReqDTO.getEstatePropResponseDTO();
            final EstateBookingDTO estateBookingDTO = bookingReqDTO.getEstateBookingDTO();
            final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
            final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 1);
            final CheckListModel checkListModel = (CheckListModel) checklistModel.get(0);
            final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
            final WSRequestDTO checkRequestDto = new WSRequestDTO();
            // setting checkList parameter
            ServiceMaster master = servicesMstService
                    .findShortCodeByOrgId(MainetConstants.RNL_ESTATE_BOOKING, orgId);
            String chkApplicableOrNot = CommonMasterUtility.getCPDDescription(master.getSmChklstVerify(),
                    MainetConstants.FlagV, UserSession.getCurrent().getOrganisation().getOrgid());
            populateCheckListModel(model, checkListModel, estatePropResponseDTO);

            checkRequestDto.setDataModel(checkListModel);

            WSResponseDTO checkListResponse = brmsCommonService.getChecklist(checkRequestDto);
            if (chkApplicableOrNot.equalsIgnoreCase(MainetConstants.FlagA)) {
                if (checkListResponse != null
                        && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checkListResponse.getWsStatus())) {

                    List<DocumentDetailsVO> checklistDoc = Collections.emptyList();
                    if (checkListResponse.getWsStatus() != null
                            && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checkListResponse.getWsStatus())) {
                        checklistDoc = (List<DocumentDetailsVO>) checkListResponse.getResponseObj();
                        if (checklistDoc != null && !checklistDoc.isEmpty()) {
                            long cnt = 1;
                            for (final DocumentDetailsVO doc : checklistDoc) {
                                doc.setDocumentSerialNo(cnt);
                                cnt++;
                            }
                        }
                        this.getModel().setCheckList(checklistDoc);
                    } else {
                        model
                                .addValidationError(getApplicationSession().getMessage("rnl.problem.while.getting.checklist"));
                        LOGGER.error("Problem while getting CheckList");
                    }
                }
            }

            // setting default parameter rnl rate master parameter
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            rnlRateMaster.setOrgId(orgId);
            rnlRateMaster.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
            rnlRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL, PrefixConstants.LookUpPrefix.CAA)
                    .getLookUpId()));
            taxReqDTO.setDataModel(rnlRateMaster);

            // taxex
            final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);
            // D#101787
            LookUp lookupPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue("SHF",
                    PrefixConstants.EPLOYEE_ORGNA_ISBPLM, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());

            // for Booking Fees (BKF) Only
            LookUp lookupBooking = CommonMasterUtility.getValueFromPrefixLookUp("BKF",
                    "TXN", /* TAX_PREFIX */ UserSession.getCurrent().getOrganisation());

            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

                if (!taxResponseDTO.isFree()) {
                    // final List<?> rates = RestClient.castResponse(taxResponseDTO,
                    // RNLRateMaster.class);
                    final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        final RNLRateMaster master1 = (RNLRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
                        master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
                        master1.setChargeApplicableAt(
                                CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
                                        UserSession.getCurrent().getOrganisation().getOrgid(),
                                        Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
                        master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
                                UserSession.getCurrent().getOrganisation()));
                        master1.setRateStartDate(estateBookingDTO.getToDate().getTime());
                        int days = noOfBookingDays(estateBookingDTO);
                        master1.setNoOfBookingDays(days);
                        master1.setFactor4(estatePropResponseDTO.getPropName());
                        master1.setOccupancyType(CommonMasterUtility
                                .getCPDDescription(bookingReqDTO.getEstateBookingDTO().getPurpose(), MainetConstants.FlagE));

                        String isbPl = bookingReqDTO.getApplicantDetailDto().getIsBPL();

                        master1.setIsBPL(isbPl);

                        String isEMployee = bookingReqDTO.getApplicantDetailDto().getIsOrganisationEmployeeFalg();

                        master1.setIsOrganisationalEmployee(isEMployee);
                        //**User Story #151502  Start**
                        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
                        	Date bookStartDate = estateBookingDTO.getFromDate();
    						Calendar calendar = Calendar.getInstance();
    						calendar.setTime(bookStartDate);
    						// Add one day to the calendar
    						calendar.add(Calendar.DAY_OF_YEAR, 1);
    						// Get the updated Date after adding one day
    						Date nextDay = calendar.getTime();
    						LocalDate localDate = bookStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    						String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    								.substring(0, 3).toUpperCase();
    						String[] week = { MainetConstants.Common_Constant.WEEK.MON,
    								MainetConstants.Common_Constant.WEEK.TUE, MainetConstants.Common_Constant.WEEK.WED,
    								MainetConstants.Common_Constant.WEEK.THU, MainetConstants.Common_Constant.WEEK.FRI };
    						List<HolidayMasterDto> holiday = holidayMasterService.getHolidayDates(bookStartDate, orgId);
    						List<HolidayMasterDto> dayBeforeHoliday = holidayMasterService.getHolidayDates(nextDay, orgId);
    						if (dayBeforeHoliday != null && !dayBeforeHoliday.isEmpty()) {
    							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.FOUR);
    						} else if ((holiday != null && !holiday.isEmpty())
    								|| (MainetConstants.Common_Constant.WEEK.SUN.equalsIgnoreCase(dayOfWeek))) {
    							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.THREE);
    						} else if (MainetConstants.Common_Constant.WEEK.SAT.equalsIgnoreCase(dayOfWeek)) {
    							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.TWO);
    						} else if (Arrays.asList(week).contains(dayOfWeek)) {
    							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.ONE);
    						}
                        }
                        //**User Story #151502  End**
						//As Discuused with Samadhan sir commenting this condition
                        //if (lookupBooking.getLookUpDesc().equalsIgnoreCase(master1.getTaxSubCategory())) {
                            // here find out the shift Value using shift id
                            LookUp shiftLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                                    model.getBookingReqDTO().getEstateBookingDTO().getShiftId(), orgId,
                                    PrefixConstants.SHIFT_PREFIX);
                            master1.setShiftType(shiftLookup.getLookUpCode());
                        //}

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
                                 * if (StringUtils.equalsIgnoreCase(dependFactor, "OT")) { master1.setOccupancyType(); }
                                 */
                                if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
                                    master1.setFloorLevel(estatePropResponseDTO.getFloor());
                                }
                            }
                        }
                        /*
                         * master1.setFloorLevel(estatePropResponseDTO.getFloor());
                         * master1.setRoadType(estatePropResponseDTO.getRoadType());
                         */
                        requiredCHarges.add(master1);
                    }
                    WSRequestDTO chargeReqDTO = new WSRequestDTO();
                    chargeReqDTO.setDataModel(requiredCHarges);
                    // final List<ChargeDetailDTO> output =
                    // iRNLChecklistAndChargeService.getApplicableCharges(requiredCHarges);
                    WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
                    final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    if (output == null) {
                        throw new FrameworkException("Charges not Found in brms Sheet");
                    } else {
                        model.setChargesInfo(newChargesToPay(output));
                        model.setAmountToPay(chargesToPayWithoutGST(model.getChargesInfo()));
                        model.setIsFree(MainetConstants.Common_Constant.NO);
                    }
                    // taxex
				final WSResponseDTO taxResponseDTONew = brmsRNLService.getApplicableTaxes(taxReqDTO);
                    if (taxResponseDTO.getWsStatus() != null
                            && MainetConstants.WebServiceStatus.SUCCESS
                                    .equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

                        if (!taxResponseDTONew.isFree()) {
                            final List<Object> ratesAgain = (List<Object>) taxResponseDTONew.getResponseObj();
                            final List<RNLRateMaster> requiredCHargesAgain = new ArrayList<>();

                            for (final Object rate : ratesAgain) {
                                final RNLRateMaster master1 = (RNLRateMaster) rate;
                                master1.setOrgId(orgId);
                                master1.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
                                master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
                                master1.setChargeApplicableAt(
                                        CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
                                                UserSession.getCurrent().getOrganisation().getOrgid(),
                                                Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
                                master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
                                        UserSession.getCurrent().getOrganisation()));

                                master1.setRateStartDate(estateBookingDTO.getToDate().getTime());
                                int days = noOfBookingDays(estateBookingDTO);
                                master1.setNoOfBookingDays(days);
                                master1.setTotalAmount(model.getAmountToPay());
                                master1.setFactor4(estatePropResponseDTO.getPropName());
                                master1.setOccupancyType(CommonMasterUtility.getCPDDescription(
                                        bookingReqDTO.getEstateBookingDTO().getPurpose(), MainetConstants.FlagE));
                                String isbPl = bookingReqDTO.getApplicantDetailDto().getIsBPL();
                                String isEMployee = bookingReqDTO.getApplicantDetailDto().getIsOrganisationEmployeeFalg();
                                master1.setIsBPL(isbPl);
                                master1.setIsOrganisationalEmployee(isEMployee);
                              //**User Story #151502  Start**
                                if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
                                	Date bookStartDate = estateBookingDTO.getFromDate();
            						Calendar calendar = Calendar.getInstance();
            						calendar.setTime(bookStartDate);
            						// Add one day to the calendar
            						calendar.add(Calendar.DAY_OF_YEAR, 1);
            						// Get the updated Date after adding one day
            						Date nextDay = calendar.getTime();
            						LocalDate localDate = bookStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            						String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            								.substring(0, 3).toUpperCase();
            						String[] week = { MainetConstants.Common_Constant.WEEK.MON,
            								MainetConstants.Common_Constant.WEEK.TUE, MainetConstants.Common_Constant.WEEK.WED,
            								MainetConstants.Common_Constant.WEEK.THU, MainetConstants.Common_Constant.WEEK.FRI };
            						List<HolidayMasterDto> holiday = holidayMasterService.getHolidayDates(bookStartDate, orgId);
            						List<HolidayMasterDto> dayBeforeHoliday = holidayMasterService.getHolidayDates(nextDay, orgId);
            						if (dayBeforeHoliday != null && !dayBeforeHoliday.isEmpty()) {
            							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.FOUR);
            						} else if ((holiday != null && !holiday.isEmpty())
            								|| (MainetConstants.Common_Constant.WEEK.SUN.equalsIgnoreCase(dayOfWeek))) {
            							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.THREE);
            						} else if (MainetConstants.Common_Constant.WEEK.SAT.equalsIgnoreCase(dayOfWeek)) {
            							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.TWO);
            						} else if (Arrays.asList(week).contains(dayOfWeek)) {
            							master1.setUsageSubtype5(MainetConstants.Common_Constant.NUMBER.ONE);
            						}
                                }
                                //**User Story #151502  End**
								//As Discuused with Samadhan sir commenting this condition
                               //if (lookupBooking.getLookUpDesc().equalsIgnoreCase(master1.getTaxSubCategory())) {
                                    // here find out the shift Value using shift id
                                    LookUp shiftLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                                            model.getBookingReqDTO().getEstateBookingDTO().getShiftId(), orgId,
                                            PrefixConstants.SHIFT_PREFIX);
                                    master1.setShiftType(shiftLookup.getLookUpCode());
                               // }

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
                                        if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
                                            master1.setFloorLevel(estatePropResponseDTO.getFloor());
                                        }
                                    }
                                }

                                requiredCHargesAgain.add(master1);
                            }

                            WSRequestDTO chargeReqDTO1 = new WSRequestDTO();
                            chargeReqDTO1.setDataModel(requiredCHargesAgain);
                            WSResponseDTO applicableCharges1 = brmsRNLService.getApplicableCharges(chargeReqDTO1);

                            final List<ChargeDetailDTO> output1 = (List<ChargeDetailDTO>) applicableCharges1.getResponseObj();

                            double percentageAmount = 0.0;

                            for (int i = 0; i < output1.size(); i++) {
                                percentageAmount = output1.get(i).getPercentageRate();
                                String chargLebelDesc = output1.get(i).getChargeDescEng();

                                if (chargLebelDesc.equalsIgnoreCase("GST")) {

                                    model.setGstPercentage(percentageAmount);
                                } else if (chargLebelDesc.equalsIgnoreCase("SGST")) {

                                    model.setSgtPercenatge(percentageAmount);

                                } else if (chargLebelDesc.equalsIgnoreCase("CGST")) {
                                    model.setCgstPercenatge(percentageAmount);
                                }

                            }

                            model.setChargesInfo(newChargesToPay(output1));
                            model.setAmountToPay(chargesToPay(model.getChargesInfo()));
                            model.setIsFree(MainetConstants.Common_Constant.NO);

                            model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
                            if (model.getAmountToPay() == 0.0d) {
                                // Defect #32656
                                logger.error("Service charge amountToPay cannot be "
                                        + model.getAmountToPay() + " if service configured as Chageable");
                            }
                        } else {
                            model.setIsFree(MainetConstants.Common_Constant.FREE);
                        }

                    } else {
                        throw new FrameworkException("Problem while checking dependent param for rnl rate .");
                    }
                } else {
                    throw new FrameworkException("Problem  check list not Applicable in Servive Master.");
                }

            }

        }
    }

    private Double chargesToPayWithoutGST(List<ChargeDetailDTO> chargesInfo) {
    	double amountSum = 0.0;

        for (final ChargeDetailDTO charge : chargesInfo) {
        	if(!charge.getChargeDescEng().equalsIgnoreCase("SGST") && !charge.getChargeDescEng().equalsIgnoreCase("CGST") ) {
        		amountSum = amountSum + charge.getChargeAmount();
        	}    
        }
        return amountSum;
	}

	private int noOfBookingDays(EstateBookingDTO estateBookingDTO) {
        Date bookStartDate = estateBookingDTO.getFromDate();
        Date bookEndDate = estateBookingDTO.getToDate();
        // Defect #70809
        int days = 0;
        EstatePropertyShiftDTO shiftDetailsTime = getShiftDetailsTime(estateBookingDTO.getShiftId(),
                getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropId());

        if (shiftDetailsTime != null) {
        	if(null==shiftDetailsTime.getStartTime() && null !=estateBookingDTO.getFromTime()) {
        		shiftDetailsTime.setStartTime(estateBookingDTO.getFromTime());
        	}
        	if(null==shiftDetailsTime.getEndTime() && null !=estateBookingDTO.getToTime()) {
        		shiftDetailsTime.setEndTime(estateBookingDTO.getToTime());
        	}
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
        if (daysBetween == 0) {
            return Math.round(daysBetween) + 1;
        } else {
            return Math.round(daysBetween);
        }
    }

    private BigDecimal calculateRentalChargesBetweenDates(final EstateBookingModel model,
            final List<ChargeDetailDTO> output) {
        double chargesToPay = chargesToPay(output);
        Date bookStartDate = model.getBookingReqDTO().getEstateBookingDTO().getFromDate();
        Date bookEndDate = model.getBookingReqDTO().getEstateBookingDTO().getToDate();
        int days = Utility.getDaysBetweenDates(bookStartDate, bookEndDate);
        BigDecimal amount = new BigDecimal(chargesToPay);
        amount = amount.multiply(new BigDecimal(days));
        return amount;
    }

    private CheckListModel populateCheckListModel(final EstateBookingModel model, final CheckListModel checklistModel,
            final EstatePropResponseDTO estatePropResponseDTO) {
        checklistModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        checklistModel.setServiceCode(MainetConstants.RNL_ESTATE_BOOKING);
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

    private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        EstateBookingModel estateModel = getModel();
        Date bookStartDate = estateModel.getBookingReqDTO().getEstateBookingDTO().getFromDate();
        Date bookEndDate = estateModel.getBookingReqDTO().getEstateBookingDTO().getToDate();
        int days = Utility.getDaysBetweenDates(bookStartDate, bookEndDate);
        for (final ChargeDetailDTO charge : charges) {
            BigDecimal amount = new BigDecimal(charge.getChargeAmount());
            /* amount = amount.multiply(new BigDecimal(days)); */
            charge.setChargeAmount(amount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }

    /**
     * get Filterd List
     * @param categoryId
     * @param eventId
     * @param capcityFrom
     * @param capcityTo
     * @param rentFrom
     * @param rentTo
     * @param httpServletRequest
     * @return depend
     */
    @RequestMapping(params = "filterEstateBookingList", method = RequestMethod.POST)
    public @ResponseBody List<EstatePropResponseDTO> getFilterdList(
            @RequestParam("category") final Integer categoryId, @RequestParam("eventId") final Long eventId,
            @RequestParam("capcityFrom") final long capcityFrom,
            @RequestParam("capcityTo") final long capcityTo, @RequestParam("rentFrom") final double rentFrom,
            @RequestParam("rentTo") final double rentTo,
            final HttpServletRequest httpServletRequest) {
        PropertyResDTO propResDto = iEstateBookingService.getFilteredRentedProperties(categoryId, eventId, capcityFrom, capcityTo,
                rentFrom, rentTo,
                UserSession.getCurrent().getOrganisation());
        //#34039 - to get data on back button
        this.getModel().getEstatePropReqestDTO().setCapcityFrom(capcityFrom);
        this.getModel().getEstatePropReqestDTO().setCapcityTo(capcityTo);
        this.getModel().getEstatePropReqestDTO().setRentFrom(rentFrom);
        this.getModel().getEstatePropReqestDTO().setRentTo(rentTo);
        this.getModel().getEstatePropReqestDTO().setCategoryTypeId(categoryId);
        this.getModel().getEstatePropReqestDTO().setEventId(eventId);
        return propResDto.getEstatePropResponseDTOs();
    }

    /**
     * Calendar Data
     * @param propId
     * @return
     */
    @RequestMapping(params = "getCalAndMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public Object[] calenderData(@RequestParam("propId") final Long propId) {

        /* chnages By SUHEL date 13/02/2020 Defect #33666 Booking not opening if challan not paid */

        List<EstateBookingDTO> defEntityList = iEstateBookingService
                .checkedReceiptValiadtion(UserSession.getCurrent().getOrganisation().getOrgid(), propId);
        if (!defEntityList.isEmpty()) {
            for (EstateBookingDTO comObject : defEntityList) {
                String bookinNo = comObject.getBookingNo();
                // Date fromDate=comObject.getFromDate();
                // Date toDate=comObject.getToDate();
                Date bookingDate = comObject.getBookingDate();
                Date date = new Date();

                int challanrcvFlag = getDaysBetweenDatesOrFullDays(bookingDate, date);
                // int challanrcvFlags= Utility.getDaysBetweenDates(bookingDate,date);
                ServiceMaster master = servicesMstService
                        .findShortCodeByOrgId(MainetConstants.RNL_ESTATE_BOOKING,
                                UserSession.getCurrent().getOrganisation().getOrgid());
                Long challanDay = master.getComN1();
                if (challanrcvFlag > challanDay) {
                    iEstateBookingService.enableEstateBookingStatus(bookinNo,
                            UserSession.getCurrent().getOrganisation().getOrgid(), new Date(), new Date());
                }

            }

        }

        final Object data[] = new Object[] { null, null, null, null };
        final List<CalanderDTO> mainList = new ArrayList<>();
        final List<EstateBookingDTO> bookingDTOs = iEstateBookingService.findByPropId(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        CalanderDTO calanderDTO = null;
        if (null != bookingDTOs) {
            for (final EstateBookingDTO estateBookingDTO : bookingDTOs) {
                // D#74975 at the time of freeze property record insert with applicationId - 0
                /*
                 * if (estateBookingDTO.getApplicationId() == 0) { continue; }
                 */
                if (estateBookingDTO.getBookingStatus().equals(MainetConstants.RnLCommon.F_FLAG)) {
                    calanderDTO = new CalanderDTO(estateBookingDTO.getId(), estateBookingDTO.getFromDate(),
                            estateBookingDTO.getShiftName(), "bg-green-1", estateBookingDTO.getToDate(), "");
                    // D#79810
                } else if (!estateBookingDTO.getBookingStatus().equals(MainetConstants.FlagU)
                        && estateBookingDTO.getApplicationId() != 0) {
                    calanderDTO = new CalanderDTO(estateBookingDTO.getId(), estateBookingDTO.getFromDate(),
                            estateBookingDTO.getShiftName(),
                            estateBookingDTO.getShiftName().equals(MainetConstants.SHIFT_PREFIX_GENERAL) ? "bg-red-1"
                                    : "bg-blue-2",
                            estateBookingDTO.getToDate(), "");
                }

                mainList.add(calanderDTO);
            }

        }
        final EstatePropMaster estatePropMaster = iEstatePropertyService.findLatLong(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final String[] mapData = new String[] { estatePropMaster.getName(), estatePropMaster.getPropLatitude(),
                estatePropMaster.getPropLongitude() };
        data[0] = mainList;
        data[1] = mapData;
        final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final List<AttachDocs> attachDocsList = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(), estatePropResponseDTO.getPropertyNo());
        final List<String> imagePaths = new ArrayList<>();
        for (final AttachDocs attachDocs : attachDocsList) {
            if (attachDocs.getSerialNo() == 1) {
                final String value = attachDocs.getAttPath() + "\\" + attachDocs.getAttFname();
                final String actualPath = value.replace("\\", "\\\\");
                data[2] = actualPath;
                getModel().setDocName(actualPath);
            } else if (attachDocs.getSerialNo() == 0) {
                imagePaths.add(getPropImages(attachDocs));
            }
        }
        data[3] = imagePaths;
        return data;
    }

    /**
     * get Sub Category Desc
     * @param taxsubCategory
     * @param org
     * @return
     */
    private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
        String subCategoryDesc = "";
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.TAC_PREFIX,
                MainetConstants.EstateBooking.LEVEL, org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals(taxsubCategory)) {
                subCategoryDesc = lookup.getDescLangFirst();
                break;
            }
        }
        return subCategoryDesc;
    }

    /**
     * @author vishwajeet.kumar
     * @param fromDate
     * @param toDate
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "getShiftsBasedOnDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<EstatePropertyShiftDTO> getShiftsBasedOnDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, final HttpServletRequest httpServletRequest) {
        List<EstatePropertyShiftDTO> shiftDTOs = null;
        try {
            shiftDTOs = iEstateBookingService.getShiftDetailsFromDateAndTodate(
                    getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropId(), fromDate, toDate,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            final Date dateFrom = UtilityService.convertStringDateToDateFormat(fromDate);
            final Date dateTo = UtilityService.convertStringDateToDateFormat(toDate);
            this.getModel().setFromedate(dateFrom);
            this.getModel().setToDate(dateTo);
        } catch (final Exception exception) {
            logger.error("Exception found in getShiftsBasedOnDate method: ", exception);
        }
        return shiftDTOs;
    }

    /**
     * date Rang Bet Booked Date
     * @param fromDate
     * @param toDate
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "dateRangBetBookedDate", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String dateRangBetBookedDate(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, final HttpServletRequest httpServletRequest) {
        String flag = MainetConstants.EstateBooking.PASS;
        try {
            final List<String> fromAndtoDate = iEstateBookingService.getEstateBookingFromAndToDatesForGeneral(
                    getModel().getBookingReqDTO().getEstatePropResponseDTO().getPropId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            final Calendar calendar = new GregorianCalendar();
            final Date dateFrom = UtilityService.convertStringDateToDateFormat(fromDate);
            final Date dateTo = UtilityService.convertStringDateToDateFormat(toDate);
            calendar.setTime(dateFrom);
            final List<String> bookedDate = new ArrayList<>();
            while (calendar.getTime().before(dateTo) || calendar.getTime().equals(dateTo)) {
                final Date result = calendar.getTime();
                bookedDate.add(new SimpleDateFormat("d-M-yyyy").format(result));
                calendar.add(Calendar.DATE, 1);
            }
            for (final String date : bookedDate) {
                if (fromAndtoDate.contains(date)) {
                    flag = MainetConstants.EstateBooking.FAIL;
                    ;
                    break;
                }
            }
        } catch (final Exception exception) {
            logger.error("Exception found in dateRangBetBookedDate method: ", exception);
        }
        return flag;
    }

    /**
     * get property Info
     * @param bookId
     * @return
     */
    @RequestMapping(params = "propertyInfo", method = RequestMethod.POST)
    public ModelAndView propertyInfo(@RequestParam(value = "bookId") final Long bookId) {
        final EstateBookingModel estateBookingModel = getModel();
        final PropInfoDTO propInfoDTO = iEstateBookingService.findBooking(bookId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (propInfoDTO != null) {
            propInfoDTO.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        }
        estateBookingModel.setPropInfoDTO(propInfoDTO);
        return new ModelAndView(MainetConstants.EstateBooking.PROP_INFO, MainetConstants.FORM_NAME, estateBookingModel);
    }

    /**
     * has Property In Contract Or Booking
     * @param propId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "IsPropAssociated")
    public @ResponseBody boolean hasPropertyInContractOrBooking(@RequestParam final Long propId) {
        final boolean count = iEstateBookingService
                .findCountForProperty(UserSession.getCurrent().getOrganisation().getOrgid(), propId);
        return count;
    }

    private String getPropImages(final AttachDocs attachDocs) {

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
     * method to display service charge details
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET }, params = "showRnLChargeDetails")
    public ModelAndView showRnLChargesDetails(final HttpServletRequest httpServletRequest, final Model model) {
        EstateBookingModel estateModel = getModel();

        /*
         * Date bookStartDate = estateModel.getBookingReqDTO().getEstateBookingDTO().getFromDate(); Date bookEndDate =
         * estateModel.getBookingReqDTO().getEstateBookingDTO().getToDate(); int days = Utility.getDaysBetweenDates(bookStartDate,
         * bookEndDate); model.addAttribute("days", days);
         */
        // re-initialize charges details and set
        List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
        List<ChargeDetailDTO> gstList = new ArrayList<>();
        for (ChargeDetailDTO charges : estateModel.getChargesInfo()) {
            ChargeDetailDTO dto = new ChargeDetailDTO();
            dto = charges;
            if (charges.getChargeDescEng().equalsIgnoreCase("CGST") || charges.getChargeDescEng().equalsIgnoreCase("SGST")) {
                gstList.add(dto);
            } else {
                // modify the list based on given D#31001
                if (charges.getChargeDescEng().equalsIgnoreCase("Booking Fees")) {
                    chargesInfo.add(0, dto);
                } else if (charges.getChargeDescEng().equalsIgnoreCase("Maintenance Charge")) {
                    chargesInfo.add(1, dto);
                } else if (charges.getChargeDescEng().equalsIgnoreCase("Electricity Bill deposit")) {
                    chargesInfo.add(2, dto);

                } else {
                    chargesInfo.add(dto);
                }
            }
        }
        chargesInfo.addAll(gstList);
        estateModel.setChargesInfo(chargesInfo);
        return new ModelAndView(MainetConstants.EstateBooking.RNL_CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND,
                estateModel);
    }

    /**
     * get Property shift Form To Time get Shift Details Time
     * @author vishwajeet.kumar
     * @param shiftId
     * @param propId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "getPropFormToTime", method = RequestMethod.POST)
    public EstatePropertyShiftDTO getShiftDetailsTime(@RequestParam("shiftId") Long shiftId,
            @RequestParam("propId") Long propId) {

        EstatePropertyShiftDTO shiftDTO = iEstatePropertyService.getPropertyShiftTimingByShift(shiftId, propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (shiftDTO == null) {
            shiftDTO = new EstatePropertyShiftDTO();
        }
        return shiftDTO;
    }

    /**
     * get Validation From Date To Date
     * @author vishwajeet.kumar
     * @param fromDate
     * @param toDate
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "getValidationFromDateToDate", method = RequestMethod.POST)
    public Map<String, String> getValidationFromDateToDate(@RequestParam("fromDate") Date fromDate,
            @RequestParam("toDate") Date toDate) {
        EstatePropMaster master = iEstatePropertyService.findByPropDetailsById(this.getModel().getPropId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        int resultNoOfDays = 0;
        try {
            String lookUp = CommonMasterUtility.getLookUps("AND", organisation).get(0).getLookUpCode();
            resultNoOfDays = Integer.parseInt(lookUp);
        } catch (Exception exception) {
            throw new FrameworkException(" Exception occured when fetching Prefix ----> AND " + exception);
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        if ((master.getPropNoDaysAllow() != null) && (resultNoOfDays != 0)) {
            Long noOfDays = master.getPropNoDaysAllow();
            Integer integer = Math.toIntExact(noOfDays);
            Date date = new Date();
            Integer noOfDaysUADLevel = Utility.getDaysBetweenDates(date, fromDate);
            Integer count = Utility.getDaysBetweenDates(fromDate, toDate);
            if (resultNoOfDays > noOfDaysUADLevel) {
                if (integer >= count) {
                    // map.put("succes", MainetConstants.SUCCESS_MESSAGE);
                } else {
                    map.put("days", noOfDays.toString());
                    map.put("propName", master.getName());
                    // map.put("date", date.toString());
                }
            } else {
                map.put("UadLevelError", String.valueOf(resultNoOfDays));
                map.put("propName", master.getName());
                map.put("currentDate", Utility.dateToString(date));
            }

        }
        return map;
    }

    /**
     * view Details Booking Facility
     * @author vishwajeet.kumar
     * @param propId
     * @return view Amenity And Facilities
     */
    @ResponseBody
    @RequestMapping(params = "viewEstateBookingFacility", method = RequestMethod.POST)
    public ModelAndView viewDetailsBookingFacility(@RequestParam("propId") Long propId) {

        EstatePropResponseDTO master = iEstatePropertyService.findFacilityAndAmenities(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getBookingReqDTO().setEstatePropResponseDTO(master);
        return new ModelAndView("viewAmiAndFacilities", MainetConstants.FORM_NAME,
                this.getModel());
    }

//#34039 - to get data on back button
	@RequestMapping(params = "backToSearchForm", method = RequestMethod.POST)
	public ModelAndView backToSearchForm(final Model uiModel, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		final List<LookUp> list = CommonMasterUtility.getLevelData(PrefixConstants.CATEGORY_PREFIX_NAME, 2,
				UserSession.getCurrent().getOrganisation());
		uiModel.addAttribute(MainetConstants.EstateBooking.CATEGORY, list);
		PropertyResDTO propResDto = iEstateBookingService.getFilteredRentedProperties(
				this.getModel().getEstatePropReqestDTO().getCategoryTypeId(),
				this.getModel().getEstatePropReqestDTO().getEventId(),
				this.getModel().getEstatePropReqestDTO().getCapcityFrom(),
				this.getModel().getEstatePropReqestDTO().getCapcityTo(),
				this.getModel().getEstatePropReqestDTO().getRentFrom(),
				this.getModel().getEstatePropReqestDTO().getRentTo(), UserSession.getCurrent().getOrganisation());
		List<EstatePropertyEventDTO> categoryList = displayCategory(
				this.getModel().getEstatePropReqestDTO().getCategoryTypeId());
		uiModel.addAttribute("eventDTOsList", categoryList);
		this.getModel().setEstatePropResponseDTOs(propResDto.getEstatePropResponseDTOs());
		return new ModelAndView("estateBookingHomeValidn", MainetConstants.FORM_NAME, this.getModel());
	}
    /*
     * @RequestMapping(params = "PrintReport") public ModelAndView printF(final HttpServletRequest request) {
     * logger.info("Start the PrintReport()"); try { return new ModelAndView("redirect:/PrintReport"); } catch (Exception
     * exception) { throw new FrameworkException("While Feching Receipt information for Estate Booking " + exception); } }
     */

    @ResponseBody
    @RequestMapping(params = "getCategoryAndEvent", method = RequestMethod.POST)
    public List<EstatePropertyEventDTO> displayCategory(@RequestParam("category") Integer category) {    
        List<EstatePropertyEventDTO> propertyDto = iEstateBookingService.getEventOrPropertyId(category,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (propertyDto.isEmpty()) {
            throw new FrameworkException("Event does Not Exist In Property");
        }
     
        return propertyDto;

    }
    
    @ResponseBody
    @RequestMapping(params="backToApplicantForm", method=RequestMethod.POST)
    public ModelAndView backToApplicantForm(final HttpServletRequest request, Model model){
    	this.getModel().bind(request);   
    	getModel().setEnableSubmit(false);
    	getModel().setEnableCheckList(true);
    	getModel().setCheckList(new ArrayList<>());
    	getModel().setAmountToPay(0.0);
    	return new ModelAndView(MainetConstants.EstateBooking.ESTATE_BOOKING, MainetConstants.FORM_NAME,
    			this.getModel());
    }
}
