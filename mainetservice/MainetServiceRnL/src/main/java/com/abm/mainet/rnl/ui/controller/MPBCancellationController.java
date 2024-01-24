package com.abm.mainet.rnl.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.service.BRMSRNLService;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.MpbCancellationService;
import com.abm.mainet.rnl.ui.model.MPBCancelModel;

@Controller
@RequestMapping("MPBCancellation.html")
public class MPBCancellationController extends AbstractFormController<MPBCancelModel> {

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Resource
    MpbCancellationService mpbCancellationService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private BRMSRNLService brmsRNLService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private ServiceMasterService serviceMaster;

    private static final Logger LOGGER = Logger.getLogger(MPBCancellationController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        MPBCancelModel model = getModel();
        TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository = ApplicationContextProvider
                .getApplicationContext().getBean(TbCfcApplicationMstJpaRepository.class);
        // fetch data from estate Booking
        // TB_RL_ESTATE_BOOKING table
        List<EstateBookingDTO> bookingNos = new ArrayList<>();

        /*
         * List<EstateBookingDTO> estateBookings = iEstateBookingService
         * .fetchAllBookingsByOrg(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagY);
         */

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        List<EstateBookingDTO> estateBookings = mpbCancellationService.getAllBookedPropertyByOrgId(orgId);

        SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
        Date currentDate = null;
        try {
            currentDate = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        // make data for booking id

        for (EstateBookingDTO dto : estateBookings) {

            // Only Future Bookings will be visible

            if (dto.getCancelDate() == null
                    && (currentDate.before(dto.getFromDate()) || currentDate.equals(dto.getFromDate()))) {

                EstateBookingDTO bookingNo = new EstateBookingDTO();

                BeanUtils.copyProperties(dto, bookingNo);
                List<Object[]> appliInfoList = tbCfcApplicationMstJpaRepository.getApplicantInfo(dto.getApplicationId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                String name = "";
                for (final Object[] strings : appliInfoList) {
                    if (strings[1] != null) {
                        name = strings[1].toString();
                    }
                    bookingNo.setBookingNo(bookingNo.getBookingNo() + " - " + strings[0].toString() + " " + name + " "
                            + strings[2].toString());
                }
                bookingNos.add(bookingNo);

            }
        }

        model.setEstateBookings(bookingNos);
        model.setOfflineDTO(null);

        return new ModelAndView("MPBCancel", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "getBookedPropertyDetails")
    public ModelAndView getBookedPropertyDetails(final HttpServletRequest request) {
        this.getModel().bind(request);

        MPBCancelModel model = this.getModel();
        ModelAndView modelAndView = null;
        long orgID = UserSession.getCurrent().getOrganisation().getOrgid();

        ServiceMaster service = serviceMaster.getServiceByShortName("EBC", orgID);

        EstateBooking estateBookingEntity = iEstateBookingService.findbookingIdbyBookingNo(
                model.getEstateBookingDTO().getBookingNo(), UserSession.getCurrent().getOrganisation().getOrgid());

        /* fetch property details */

        final PropInfoDTO propInfoDTO = iEstateBookingService.findBooking(Long.valueOf(estateBookingEntity.getId()),
                UserSession.getCurrent().getOrganisation().getOrgid());

        Organisation organisation = new Organisation();

        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

        if (propInfoDTO != null) {
            propInfoDTO.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            propInfoDTO.setBookingNo(model.getEstateBookingDTO().getBookingNo());

            /* fetch receipt details */

            TbServiceReceiptMasEntity entity = iReceiptEntryService.getReceiptDetailsByAppId(
                    Long.valueOf(propInfoDTO.getApplicationId()),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (entity != null) {

                Long feemode = entity.getReceiptModeDetail().get(0).getCpdFeemode();

                LookUp paymentModedesc = CommonMasterUtility.getNonHierarchicalLookUpObject(feemode, organisation);

                propInfoDTO.setPaymentModedesc(String.valueOf(paymentModedesc.getDescLangFirst()));

                if (entity.getRmAmount() != null) {
                    propInfoDTO.setAmount(entity.getRmAmount());
                }
                if (entity.getRmRcptno() != null) {
                    propInfoDTO.setReceiptNo(String.valueOf(entity.getRmRcptno()));
                }
            }
            /*
             * If payment not done for Booking then validation showing first complete payment then cancel booking
             */

            /* #33678 */
            else {

                model.addValidationError(
                        ("Payment not done for Booking No.") + model.getEstateBookingDTO().getBookingNo());
                modelAndView = new ModelAndView("MPBCancelValidn", MainetConstants.FORM_NAME, this.getModel());
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());
                return modelAndView;

            }
        }

        model.setPropInfoDTO(propInfoDTO);
        /* fetch receipt details end */

        /* fetch cancellation charges */

        long propId = mpbCancellationService.findAllDetailsbyBookingId(Long.valueOf(estateBookingEntity.getId()),
                UserSession.getCurrent().getOrganisation().getOrgid());

        EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService.findPropertyForBooking(propId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = servicesMstService
                .findShortCodeByOrgId(MainetConstants.EstateBooking.ESTATE_BOOKING_CANCELLATION_SERVICECODE, orgId);

        WSRequestDTO initRequestDto = new WSRequestDTO();

        initRequestDto.setModelName(MainetConstants.RnLCommon.CHECKLIST_RNLRATEMASTER);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);

        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

            Organisation organisation1 = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> rNLRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 1);
            RNLRateMaster rNLRateMaster = (RNLRateMaster) rNLRateMasterList.get(0);
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            rNLRateMaster.setOrgId(orgId);
            rNLRateMaster.setServiceCode(MainetConstants.EstateBooking.ESTATE_BOOKING_CANCELLATION_SERVICECODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);

            rNLRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));

            taxReqDTO.setDataModel(rNLRateMaster);
            final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);
            BookingCancelDTO dto = new BookingCancelDTO();
            dto.setIsFree(taxResponseDTO.isFree());
            model.setBookingCancelDTO(dto);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

                if (!taxResponseDTO.isFree()) {
                    final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        RNLRateMaster master1 = (RNLRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.EstateBooking.ESTATE_BOOKING_CANCELLATION_SERVICECODE);
                        master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
                        master1.setRateStartDate(new Date().getTime());
                        master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
                                UserSession.getCurrent().getOrganisation()));

                        master1.setUsageSubtype1(estatePropResponseDTO.getUsage());
                        master1.setUsageSubtype2(estatePropResponseDTO.getType());
                        master1.setFloorLevel(estatePropResponseDTO.getFloor());
                        master1.setUsageSubtype3(estatePropResponseDTO.getSubType());
                        master1.setFactor4(estatePropResponseDTO.getPropName());

                        requiredCHarges.add(master1);
                    }
                    WSRequestDTO chargeReqDTO = new WSRequestDTO();
                    chargeReqDTO.setDataModel(requiredCHarges);
                    WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);

                    final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();

                    if (output == null) {
                        LOGGER.error("Charges not Found in brms Sheet");
                        model.addValidationError("Charges not Found in brms Sheet");

                        modelAndView = new ModelAndView("mpbBookedPropertyDetValidn", MainetConstants.FORM_NAME,
                                this.getModel());
                        modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                                getModel().getBindingResult());
                        return modelAndView;

                    } else {
                        model.setChargesInfo(newChargesToPay(output));
                        model.setAmountToPay(chargesToPay(model.getChargesInfo()));

                        /* model.getOfflineDTO().setAmountToShow(model.getAmountToPay()); */
                        if (model.getAmountToPay() == 0.0d) {
                            model.addValidationError(getApplicationSession()
                                    .getMessage("Service charge amountToPay cannot be") + model.getAmountToPay()
                                    + getApplicationSession().getMessage("if service configured as Chargeable"));
                            LOGGER.error("Service charge amountToPay cannot be " + model.getAmountToPay()
                                    + " if service configured as Chargeable");
                        }

                    }

                }
                /*
                 * else { model.setIsFree(MainetConstants.Common_Constant.YES); model.setCharges(0.0d); }
                 */
                // model.getBookingCancelDTO().setFree(true); }

            } else {
                model.addValidationError(getApplicationSession()
                        .getMessage("Exception occured while fecthing Depends on factor for RNL Rate Mster"));
                LOGGER.error("Exception occured while fecthing Depends on factor for RNL Rate Mster ");
            }
        }
        /* fetch cancellation charges */
        return new ModelAndView("mpbBookedPropertyDetails", MainetConstants.FORM_NAME, this.getModel());
    }

    // change for defect Defect #39643
    @RequestMapping(method = RequestMethod.POST, params = "saveMPBCancellation")
    public ModelAndView saveMPBCancellation(final HttpServletRequest request) {
        this.getModel().bind(request);
        MPBCancelModel model = this.getModel();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        JsonViewObject respObj;
        EstateBookingDTO estateBookingDTO = new EstateBookingDTO();
        estateBookingDTO.setBookingNo(model.getEstateBookingDTO().getBookingNo());
        estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        ModelAndView mv = null;
        if (this.getModel().saveMPBCancellation()) {
            return jsonResult(JsonViewObject.successResult(this.getModel().getSuccessMessage()));

        } else {
            return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
        }

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, params = "saveMPBCancellationForFreeCharge")
    public Map<String, Object> saveMPBCancellationForFreeCharge(final HttpServletRequest request) {
        this.getModel().bind(request);
        MPBCancelModel model = this.getModel();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        JsonViewObject respObj;
        EstateBookingDTO estateBookingDTO = new EstateBookingDTO();
        estateBookingDTO.setBookingNo(model.getEstateBookingDTO().getBookingNo());
        estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        if (this.getModel().saveMPBCancellation()) {
            object.put("bookingNo", this.getModel().getEstateBookingDTO().getBookingNo());
        } else {
            object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        }

        return object;

    }

    private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {

        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        MPBCancelModel mPBCancelModel = getModel();

        for (final ChargeDetailDTO charge : charges) {
            BigDecimal amount = new BigDecimal(charge.getChargeAmount());

            charge.setChargeAmount(amount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;

        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

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

    /* Task #33101 */
    @RequestMapping(params = "getMPBCancellationReport", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String getMPBCancellationReport(final HttpServletRequest request) {
        MPBCancelModel mPBCancelModel = getModel();

        String bookingNo = mPBCancelModel.getPropInfoDTO().getBookingNo();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RefundNote.rptdesign&OrgId=" + orgId + "&BookingId="
                + bookingNo;

    }
}
