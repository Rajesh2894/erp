package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.ADHPublicNoticeDto;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.IInspectionEntryService;
import com.abm.mainet.adh.ui.model.InspectionEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Anwarul.Hassan
 * @since 23-Oct-2019
 */
@Controller
@RequestMapping("/InspectionEntry.html")
public class InspectionEntryController extends AbstractFormController<InspectionEntryModel> {

    private static final Logger LOGGER = Logger.getLogger(InspectionEntryController.class);

    @Autowired
    private IInspectionEntryService inspectionEntryService;
    @Autowired
    private IAdvertiserMasterService advertiserMasterService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private TbServicesMstService servicesMstService;
    @Autowired
    private TbApprejMasService apprejMasService;

    @Autowired
    private BRMSCommonService brmsCommonService;
    @Autowired
    private IBRMSADHService brmsadhService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setLicenseNumberList(inspectionEntryService
                .getLicenseNoByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));

        ServiceMaster master = servicesMstService.findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setServiceId(master.getSmServiceId());
        this.getModel().setInspectorName(employeeService.getAllListEmployeeByDeptId(UserSession.getCurrent().getOrganisation(),
                master.getTbDepartment().getDpDeptid()));

        LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
                PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM, UserSession.getCurrent().getOrganisation());

        this.getModel().setRemark(apprejMasService.findByRemarkType(this.getModel().getServiceId(),
                artTypeLookUp.getLookUpId()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public NewAdvertisementApplicationDto getAgencyNamesAgencyLicNoAndOrgId(@RequestParam("licenseNo") String licenseNo,
            HttpServletRequest request) {
        NewAdvertisementApplicationDto applicationDto = inspectionEntryService.getAdvertisementDetails(licenseNo,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getInspectionEntryDto().setAdhId(applicationDto.getAdhId());
        String agencyNameByAgnIdAndOrgId = advertiserMasterService.getAgencyNameByAgnIdAndOrgId(applicationDto.getAgencyId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        applicationDto.setAgencyName(agencyNameByAgnIdAndOrgId);
        return applicationDto;
    }

    @RequestMapping(params = "savePublicNotice", method = RequestMethod.POST)
    public ModelAndView savePublicNotice(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        InspectionEntryModel model = getModel();
        ADHPublicNoticeDto publicNoticeDto = model.getPublicNoticeDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        String noOfDays = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC,
                org).getOtherField();
        publicNoticeDto.setNoOfDays(Long.valueOf(noOfDays));
        model.setPublicNoticeDto(publicNoticeDto);
        if (model.savePublicNotice()) {

        }
        return jsonResult(JsonViewObject.successResult(
                ApplicationSession.getInstance().getMessage("Notice generated successfully proceed to print")));
    }

    @RequestMapping(params = "generateNotice", method = RequestMethod.POST)
    public ModelAndView generateNotice(final HttpServletRequest request) {
        InspectionEntryModel model = getModel();
        ADHPublicNoticeDto publicNoticeDto = model.getPublicNoticeDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        findApplicableChargeList(request);
        NewAdvertisementApplicationDto applicationDto = inspectionEntryService.getAdvertisementDetails(
                model.getAdvertisementDto().getLicenseNo(),
                UserSession.getCurrent().getOrganisation().getOrgid());

        AdvertiserMasterDto advertiserMasterDto = advertiserMasterService.getAdvertiserMasterByOrgidAndAgencyId(org.getOrgid(),
                applicationDto.getAgencyId());
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        String notDate = sm.format(new Date());
        publicNoticeDto.setNoticeDate(notDate);
        publicNoticeDto.setNoticeNo(model.getAdvertisementDto().getLicenseNo());
        publicNoticeDto.setAgencyOwner(advertiserMasterDto.getAgencyOwner());
        publicNoticeDto.setAgencyAddress(advertiserMasterDto.getAgencyAdd());
        publicNoticeDto.setLicenceNo(model.getAdvertisementDto().getLicenseNo());
        publicNoticeDto.setNoOfDays(publicNoticeDto.getNoOfDays());
        model.setPublicNoticeDto(publicNoticeDto);
        return new ModelAndView("ADHPublicNotice", MainetConstants.FORM_NAME, getModel());

    }

    private void findApplicableChargeList(HttpServletRequest request) {
        InspectionEntryModel applicationModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = servicesMstService
                .findShortCodeByOrgId("INS", orgId);
        WSRequestDTO initRequestDto = new WSRequestDTO();
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.ADH_Rate_Master);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = RestClient.castResponse(response, ADHRateMaster.class, 0);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode("INS");
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            adhRateMaster.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
            taxReqDTO.setDataModel(adhRateMaster);
            final WSResponseDTO taxResponseDTO = brmsadhService.getPenaltyTaxes(taxReqDTO);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
                final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    ADHRateMaster master1 = (ADHRateMaster) rate;
                    master1.setOrgId(orgId);
                    // master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                    master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                    master1.setRateStartDate(new Date().getTime());
                    requiredCHarges.add(master1);
                }
                WSRequestDTO chargeReqDTO = new WSRequestDTO();
                chargeReqDTO.setDataModel(requiredCHarges);
                WSResponseDTO applicableCharges = brmsadhService.getApplicableCharges(chargeReqDTO);
                final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                if (output == null) {
                    applicationModel.addValidationError(
                            getApplicationSession().getMessage("Charges not Found in brms Sheet"));
                    LOGGER.error("Charges not Found in brms Sheet");
                } else {
                    applicationModel.setChargesInfo(newChargesToPay(output));
                    applicationModel.setAmountToPay(chargesToPay(applicationModel.getChargesInfo()));
                    if (applicationModel.getAmountToPay() == 0.0d) {
                        applicationModel.addValidationError(getApplicationSession().getMessage(
                                "Penalty charge amountToPay cannot be") + applicationModel.getAmountToPay()
                                + getApplicationSession().getMessage("if service configured as Chargeable"));
                    }
                    this.getModel().getPublicNoticeDto().setAmount(applicationModel.getAmountToPay());
                    this.getModel().setPayable(true);
                }
            }
        }

    }

    private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        for (final ChargeDetailDTO charge : charges) {
            BigDecimal amount = new BigDecimal(charge.getChargeAmount());
            charge.setChargeAmount(amount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }

    public double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        if (!CollectionUtils.isEmpty(charges)) {
            for (final ChargeDetailDTO charge : charges) {
                amountSum = amountSum + charge.getChargeAmount();
            }
        }
        return amountSum;
    }
}
