
package com.abm.mainet.water.rest.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBIllDetHist;
import com.abm.mainet.water.domain.TbWtBIllMasHist;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.repository.BillDetailJpaRepository;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.rest.dto.WaterBillRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillResponseDTO;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.IWaterPenaltyService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.TbWtBillScheduleService;

/**
 * @author Rahul.Yadav
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/WaterPaymentRestController")
public class WaterBillPaymentRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnerRestController.class);
    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    @Resource
    private TbWtBillMasService tbWtBillMasService;

    @Resource
    private IChallanService iChallanService;

    @Resource
    private BillMasterService billGenerationService;

    @Resource
    private BillMasterCommonService billMasterCommonService;

    @Resource
    private NewWaterRepository newWaterRepository;

    @Resource
    private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

    @Resource
    private TbTaxMasService tbTaxMasService;

    @Resource
    private IFinancialYearService iFinancialYearService;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Resource
    private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;

    @Autowired
    private IFinancialYearService financialYearService;

    @Autowired
    IWaterPenaltyService waterPenaltyService;

    @Autowired
    IOrganisationService organisationService;
    @Resource
    private TbTaxMasService taxMasService;

    @Autowired
    private NewWaterConnectionService newWaterConnectionService;

    private static Map<Long, String> dependsOnFactorMap = null;

    @Autowired
    private BankMasterService bankMasterService;
    @Resource
    private BillMasterJpaRepository billMasterJpaRepository;

    @Resource
    private BillDetailJpaRepository billDetailJpaRepository;

    @Resource
    private AuditService auditService;

    @RequestMapping(value = "/getPaymentData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getPaymentData(@RequestBody final WaterBillRequestDTO requestDTO, final HttpServletRequest request,
            final BindingResult bindingResult) {
        WaterBillResponseDTO response = new WaterBillResponseDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                response = billGenerationService.fetchBillPaymentData(requestDTO);
                if (response.getRebateAmount() > 0) {
                    response.setTotalPayableAmount(response.getTotalPayableAmount() - response.getRebateAmount());
                }
            } catch (final Exception ex) {
                response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception occured at Search WaterPaymentRestController Rest Controller", ex);
            }
        }
        return response;
    }

    @RequestMapping(value = "/saveBillPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveBillPayment(@RequestBody final WaterBillRequestDTO requestDTO, final HttpServletRequest request,
            final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        final WaterBillResponseDTO response = new WaterBillResponseDTO();
        if (!bindingResult.hasErrors()) {
            try {
                final Organisation org = new Organisation();
                org.setOrgid(requestDTO.getOrgid());

                List<String> validationMsg = new ArrayList<String>();
                final List<TbBillMas> billMasList = billGenerationService
                        .getBillMasterListByUniqueIdentifier(requestDTO.getCsIdn(), requestDTO.getOrgid());
                List<TbBillMas> unPaidBillMasList = billMasList.stream().filter(billMas -> billMas.getBmTotalBalAmount() > 0)
                        .collect(Collectors.toList());
                LookUp minimumAmntValidation = null;
                try {
                    minimumAmntValidation = CommonMasterUtility.getValueFromPrefixLookUp("MCV", "BPV", org);
                } catch (Exception exception) {
                }
                TbBillMas lastBillMas = null;
                if (CollectionUtils.isNotEmpty(unPaidBillMasList)) {
                    lastBillMas = unPaidBillMasList.get(unPaidBillMasList.size() - 1);
                }
                if (minimumAmntValidation != null && StringUtils.isNotBlank(minimumAmntValidation.getOtherField())
                        && StringUtils.equals(minimumAmntValidation.getOtherField(), MainetConstants.FlagY)
                        && CollectionUtils.isNotEmpty(unPaidBillMasList)) {
                    TbCsmrInfoDTO tbCsmrInfoDTO = ApplicationContextProvider.getApplicationContext()
                            .getBean(NewWaterConnectionService.class).getConnectionDetailsById(lastBillMas.getCsIdn());
                    final LookUp meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(tbCsmrInfoDTO.getCsMeteredccn(),
                            org);
                    List<TbWtBillSchedule> currentBillSchedule = ApplicationContextProvider.getApplicationContext()
                            .getBean(TbWtBillScheduleService.class).getBillScheduleByFinYearId(lastBillMas.getBmYear(),
                                    org.getOrgid(), meterType.getLookUpCode());

                    LookUp billFrequencyLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            currentBillSchedule.get(0).getCnsCpdid(), org);
                    int billDurationMnths = Utility.monthsBetweenDates(lastBillMas.getBmFromdt(), lastBillMas.getBmTodt()) + 1;
                    double oneMonthAmount = 0;
                    if (billDurationMnths > 1) {
                        oneMonthAmount = lastBillMas.getBmTotalAmount() / billDurationMnths;
                    } else {
                        oneMonthAmount = lastBillMas.getBmTotalAmount();
                    }

                    if (lastBillMas.getBmTotalBalAmount() < lastBillMas.getBmTotalAmount()) {
                        oneMonthAmount = 0;
                    }
                    if (billFrequencyLookUp != null && StringUtils.equals(billFrequencyLookUp.getLookUpCode(), "12")
                            && requestDTO.getAmountPaid() != null && requestDTO.getAmountPaid() < requestDTO.getSurcharge()) {
                        validationMsg.add("Please pay the minimum  amount surcharge i.e., " +
                                requestDTO.getSurcharge());
                    } else if (billFrequencyLookUp != null && !StringUtils.equals(billFrequencyLookUp.getLookUpCode(), "12")
                            && requestDTO.getAmountPaid() != null
                            && requestDTO.getAmountPaid() < (requestDTO.getSurcharge() + oneMonthAmount)) {
                        double minAmntDue = requestDTO.getSurcharge() + oneMonthAmount;
                        if (requestDTO.getSurcharge() > 0) {
                            validationMsg.add("Please pay the minimum  amount (surcharge + one month bill) i.e., "
                                    + requestDTO.getSurcharge() + "+ " + oneMonthAmount + "= " + minAmntDue);
                        } else {
                            validationMsg.add("Please pay the minimum  amount one month bill i.e., " +
                                    oneMonthAmount);
                        }
                    }
                }
                if (CollectionUtils.isEmpty(validationMsg)) {
                    Long finYearId = financialYearService.getFinanceYearId(new Date());
                    tbWtBillMasService.savePortalSurchargeData(requestDTO, finYearId);

                    billMasterCommonService.updateBillData(billMasList, requestDTO.getAmountPaid(), response.getDetails(),
                            response.getBilldetailsId(), org, null, null);
                    billMasList.get(billMasList.size() - 1).setBmLastRcptamt(requestDTO.getAmountPaid());
                    // Defect #125403 .Amount is not deducted after payment.-Not Done –Surcharge amount deducted but without
                    // surcharge amount is not deducted
                    List<TbBillMas> masList = savePaidBillAmount(billMasList);
                    // End of #125403
                    response.setStatus(MainetConstants.FlagS);
                    response.setCsIdn(requestDTO.getCsIdn());
                } else {
                    response.setValidationList(validationMsg);
                    response.setStatus(MainetConstants.MENU.F);
                }

            } catch (final NoResultException ex) {
                response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception in bill payment in WaterPaymentRestController", ex);
            } catch (final Exception ex) {
                response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception occured at save WaterPaymentRestController", ex);
            }
        }
        return response;
    }

    @RequestMapping(value = "/getTaxDetailAndCsIdn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getTaxDetailAndCsIdn(@RequestBody final WaterBillRequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        final WaterBillResponseDTO response = new WaterBillResponseDTO();
        if (!bindingResult.hasErrors()) {
            try {
                final TbKCsmrInfoMH entity = newWaterRepository
                        .getWaterConnectionDetailsConNumber(requestDTO.getCcnNumber(), requestDTO.getOrgid());
                final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(requestDTO.getOrgid(),
                        MainetConstants.WATER_DEPARTMENT_CODE, null);
                if ((entity != null) && (advanceTaxId != null)) {
                    response.getDetails().put(advanceTaxId, requestDTO.getAmountPaid());
                    response.setStatus(MainetConstants.FlagS);
                    if (entity.getApplicationNo() != null) {
                        response.setApplicationNumber(entity.getApplicationNo());
                    }
                    response.setCsIdn(entity.getCsIdn());
                } else {
                    response.setStatus(MainetConstants.MENU.F);
                }
            } catch (final NoResultException ex) {
                response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception in bill payment in WaterPaymentRestController", ex);
            } catch (final Exception ex) {
                response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception occured at save WaterPaymentRestController", ex);
            }
        }
        return response;
    }

    /*
     * @RequestMapping(value = "/billPaymentAtCounter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
     * consumes = MediaType.ALL_VALUE)
     * @ResponseStatus(value = HttpStatus.OK)
     * @ResponseBody public Object billPaymentAtCounter(
     * @RequestBody final CommonChallanDTO requestDTO, final HttpServletRequest request, final BindingResult bindingResult) {
     * final WaterBillResponseDTO response = new WaterBillResponseDTO(); if (!bindingResult.hasErrors()) { try {
     * requestDTO.setDocumentUploaded(false); requestDTO.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.
     * REVENUE_BASED); final Object[] finData = iFinancialYearService.getFinacialYearByDate(new Date()); if ((finData != null) &&
     * (finData.length > 0)) { requestDTO.setFinYearEndDate((Date) finData[2]); requestDTO.setFinYearStartDate((Date) finData[1]);
     * requestDTO.setFaYearId(finData[0].toString()); } final ServiceMaster waterService = serviceMasterService
     * .getServiceByShortName(PrefixConstants.NewWaterServiceConstants.WNC, requestDTO.getOrgId()); if (waterService != null) {
     * requestDTO.setServiceId(waterService.getSmServiceId()); requestDTO.setDeptId(waterService.getTbDepartment().getDpDeptid());
     * } final TbServiceReceiptMasEntity receiptNo = iChallanService.updateDataAfterPayment(requestDTO);
     * response.setReceiptNo(receiptNo.getRmRcptno()); response.setStatus(MainetConstants.FlagS); } catch (final Exception ex) {
     * response.setStatus(MainetConstants.MENU.F); LOGGER.error( "Exception occured at save WaterPaymentRestController", ex); } }
     * return response; }
     */

    @RequestMapping(value = "/getPrefixDataForPayAtCounter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object payAtCounterPrefix(@RequestBody final WaterBillRequestDTO requestDTO,
            final HttpServletRequest httprequest) {
        final Map<String, List<LookUp>> lookupMap = new HashMap<>();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(requestDTO.getOrgid());
        List<LookUp> bankLookUp = new ArrayList<>(0);
        LookUp bank = null;
        final List<LookUp> payPrefix = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                organisation);

        List<BankMasterEntity> bankMasterList = bankMasterService.getBankList();

        for (final BankMasterEntity bankDetail : bankMasterList) {

            String bankAndBranchName = bankDetail.getBank() + " :: " + bankDetail.getBranch();
            bank = new LookUp();
            bank.setLookUpId(bankDetail.getBankId());
            bank.setLookUpDesc(bankAndBranchName);
            bank.setDescLangFirst(bankAndBranchName);
            bank.setDescLangSecond(bankAndBranchName);
            bank.getLookUpDesc();
            bankLookUp.add(bank);

        }
        lookupMap.put("BANK", bankLookUp);
        lookupMap.put(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, payPrefix);
        return lookupMap;
    }

    @RequestMapping(value = "/dependentparams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public WSResponseDTO getApplicableTaxes(@RequestBody final WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms water getApplicableTaxes execution start..");
        try {
            if (requestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                WaterRateMaster waterRateMaster = (WaterRateMaster) CommonMasterUtility
                        .castRequestToDataModel(requestDTO, WaterRateMaster.class);
                validateDataModel(waterRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(waterRateMaster, responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info("brms water getApplicableTaxes execution end..");
        return responseDTO;
    }

    private WSResponseDTO validateDataModel(WaterRateMaster waterRateMaster, WSResponseDTO responseDTO) {
        LOGGER.info("validateDataModel execution start..");
        StringBuilder builder = new StringBuilder();
        if (waterRateMaster.getServiceCode() == null || waterRateMaster.getServiceCode().isEmpty()) {
            builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
        }
        if (waterRateMaster.getOrgId() == 0l) {
            builder.append(ORG_ID_CANT_BE_ZERO).append(",");
        }
        if (waterRateMaster.getChargeApplicableAt() == null || waterRateMaster.getChargeApplicableAt().isEmpty()) {
            builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
        } else if (!StringUtils.isNumeric(waterRateMaster.getChargeApplicableAt())) {
            builder.append(CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC);
        }
        if (builder.toString().isEmpty()) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(builder.toString());
        }
        return responseDTO;
    }

    // preparing applicable tax details from tax master
    public WSResponseDTO populateOtherFieldsForServiceCharge(WaterRateMaster waterRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {
        LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
        List<WaterRateMaster> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(waterRateMaster.getServiceCode(),
                waterRateMaster.getOrgId());
        LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
                Long.valueOf(waterRateMaster.getChargeApplicableAt()),
                organisationService.getOrganisationById(waterRateMaster.getOrgId()));
        if (serviceMas.getSmFeesSchedule().equals(1l)
                && ((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
                || ((serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY))) {
            List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
                    serviceMas.getSmServiceId(), waterRateMaster.getOrgId(),
                    Long.parseLong(waterRateMaster.getChargeApplicableAt()));
            Organisation organisation = new Organisation();
            organisation.setOrgid(waterRateMaster.getOrgId());
            listOfCharges = settingAllFields(applicableCharges, waterRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setFree(true);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }
        LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
        return responseDTO;
    }

    private List<WaterRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, WaterRateMaster rateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        LOGGER.info("settingAllFields execution start..");
        List<WaterRateMaster> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
            WaterRateMaster waterRateMaster = (WaterRateMaster) rateMaster.clone();
            // SLD for dependsOnFactor
            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(entity.getTaxMethod()),
                    MainetConstants.FlagE, rateMaster.getOrgId());
            String chargeApplicableAt = CommonMasterUtility.getCPDDescription(entity.getTaxApplicable(),
                    MainetConstants.FlagE, entity.getOrgid());
            waterRateMaster.setTaxType(taxType);
            waterRateMaster.setTaxCode(entity.getTaxCode());
            waterRateMaster.setChargeApplicableAt(chargeApplicableAt);
            waterRateMaster.setChargeDescEng(entity.getTaxDesc());
            waterRateMaster.setChargeDescReg(entity.getTaxDesc());
            settingTaxCategories(waterRateMaster, entity, organisation);
            waterRateMaster.setDependsOnFactorList(settingDependsOnFactor(entity.getListOfTbTaxDetMas(), organisation));
            waterRateMaster.setTaxId(entity.getTaxId());
            list.add(waterRateMaster);
        }
        LOGGER.info("settingAllFields execution end..");
        return list;
    }

    private WaterRateMaster settingTaxCategories(WaterRateMaster waterRateMaster, TbTaxMasEntity enity,
            Organisation organisation) {

        if (enity.getTaxCategory1() != null) {
            waterRateMaster.setTaxCategory(CommonMasterUtility
                    .getHierarchicalLookUp(enity.getTaxCategory1(), organisation).getDescLangFirst());
        }
        if (enity.getTaxCategory2() != null) {
            waterRateMaster.setTaxSubCategory(CommonMasterUtility
                    .getHierarchicalLookUp(enity.getTaxCategory2(), organisation).getDescLangFirst());
        }
        return waterRateMaster;
    }

    private List<String> settingDependsOnFactor(List<TbTaxDetMasEntity> taxDetList, Organisation orgId) {

        if (dependsOnFactorMap == null) {
            cacheDependsOnFactors(orgId);
        }
        List<String> dependsOnFactorList = new ArrayList<>();

        if (taxDetList != null) {
            for (TbTaxDetMasEntity entity : taxDetList) {
                if (StringUtils.equalsIgnoreCase(entity.getStatus(), "A"))
                    dependsOnFactorList.add(dependsOnFactorMap.get(entity.getTdDependFact()));
            }
        }
        return dependsOnFactorList;
    }

    private static void cacheDependsOnFactors(Organisation orgId) {
        List<LookUp> lookUps = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.SLD, orgId);
        dependsOnFactorMap = new HashMap<>();
        for (LookUp lookUp : lookUps) {
            dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
        }
    }

    @RequestMapping(value = "/searchData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<WaterDataEntrySearchDTO> geBillPaymentData(@RequestBody final WaterDataEntrySearchDTO requestDTO,
            final HttpServletRequest request,
            final BindingResult bindingResult) {
        List<WaterDataEntrySearchDTO> response = new ArrayList<>();

        Assert.notNull(requestDTO, "Request DTO Can not be null");
        if (!bindingResult.hasErrors()) {
            try {
                response = newWaterConnectionService.searchConnectionDetails(requestDTO, null, null, null);
            } catch (final Exception ex) {
                // response.setStatus(MainetConstants.MENU.F);
                LOGGER.error("Exception occured at Search WaterPaymentRestController Rest Controller", ex);
            }
        }
        return response;
    }

    private List<TbBillMas> savePaidBillAmount(final List<TbBillMas> billMasList) {
        final List<TbWtBillMasEntity> billsUpdated = new ArrayList<>(0);
        TbWtBIllMasHist billHistory = null;
        TbWtBIllDetHist detHistory = null;
        for (final TbBillMas bill : billMasList) {
            final TbWtBillMasEntity tbWtBillMasEntity = new TbWtBillMasEntity();
            List<TbWtBillDetEntity> details = new ArrayList<>(0);
            BeanUtils.copyProperties(bill, tbWtBillMasEntity);
            if (tbWtBillMasEntity.getBmIdno() > 0) {
                TbWtBillMasEntity tbWtBillMasEntityOld = billMasterJpaRepository.findOne(tbWtBillMasEntity.getBmIdno());
                billHistory = new TbWtBIllMasHist();
                billHistory.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
                auditService.createHistory(tbWtBillMasEntityOld, billHistory);
            }
            for (final TbBillDet det : bill.getTbWtBillDet()) {
                final TbWtBillDetEntity tbWtBillDetEntity = new TbWtBillDetEntity();
                det.setBmIdno(tbWtBillMasEntity.getBmIdno());
                BeanUtils.copyProperties(det, tbWtBillDetEntity);
                tbWtBillDetEntity.setBmIdNo(tbWtBillMasEntity);
                if (det.getBdBilldetid() > 0) {
                    detHistory = new TbWtBIllDetHist();
                    detHistory.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
                    final TbWtBillDetEntity tbWtBillDetEntityOld = billDetailJpaRepository
                            .findOne(det.getBdBilldetid());
                    auditService.createHistory(tbWtBillDetEntityOld, detHistory);
                }
                details.add(tbWtBillDetEntity);
            }
            tbWtBillMasEntity.setBillDetEntity(details);
            billsUpdated.add(tbWtBillMasEntity);
        }
        billMasterJpaRepository.save(billsUpdated);
        return billMasList;
    }
}
