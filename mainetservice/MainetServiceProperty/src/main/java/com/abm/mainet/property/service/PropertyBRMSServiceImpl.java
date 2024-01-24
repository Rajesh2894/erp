package com.abm.mainet.property.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.datamodel.ALVMasterModel;
import com.abm.mainet.property.datamodel.FactorMasterModel;
import com.abm.mainet.property.datamodel.PropertyRateMasterModel;
import com.abm.mainet.property.datamodel.PropertyTaxDataModel;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssessNoticeMasterEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.AlvFactorCombineResDto;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.AssessNoticeMasterRepository;
import com.abm.mainet.property.repository.SelfAssessmentMasJpaRepository;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * 
 * PropertyBRMSService is only service where all BRMS call required for property calculation is written. Bill generation
 * calculation,surcharge calculation,rebate calculation,application charge calculation serve by this service
 * @author Apurva.Salgaonkar
 * 
 */

@Service
public class PropertyBRMSServiceImpl implements PropertyBRMSService {

    private static final Logger LOGGER = Logger.getLogger(PropertyBRMSServiceImpl.class);
    @Resource
    private LocationMasJpaRepository locationMasJpaRepository;
    @Resource
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;
    @Resource
    private SelfAssessmentMasJpaRepository selfAssessmentJpaRepository;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private IFinancialYearService iFinancialYearService;
    @Autowired
    private PropertyTransferService propertyTransferService;
    
    @Autowired
    private IOrganisationService orgService;
    
    @Autowired
    private AssesmentMstRepository assesmentMstRepository;
    
    @Autowired
    private AssesmentMastService assesmentMastService;
    
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Override
    @Transactional
    public Map<Date, List<BillPresentAndCalculationDto>> fetchCharges(ProvisionalAssesmentMstDto sdto, Long deptId,
            Map<Long, BillDisplayDto> taxWiseRebate, List<Long> finYearList, TbServiceReceiptMasEntity manualReceiptMas,
            String assType, Date manualDate,String demandGenFlag) {
        LOGGER.info(
                "*********************************************** CHARGES CALL START*********************************************************");
        WSRequestDTO dto = new WSRequestDTO();
        Map<Date, List<BillPresentAndCalculationDto>> chargeMap = null;
       
        List<BillingScheduleDetailEntity> billSchDet = billingScheduleService.getSchedulebyOrgId(sdto.getOrgId());
        LOGGER.info("getSchedulebyOrgId method Executed");
        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL);
        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final Organisation organisation = new Organisation();
            organisation.setOrgid(sdto.getOrgId());
            List<String> wardZone = setDeptWardZone(sdto, organisation, deptId);
            chargeMap = calculateAllCharges(sdto, organisation, wardZone, response, deptId, taxWiseRebate,
                    finYearList, billSchDet, manualReceiptMas, assType, manualDate,demandGenFlag);
            if (chargeMap.isEmpty()) {
                Logger.getLogger(this.getClass()).error("No calculateAllCharges  called for DTO " + dto.toString());
            }

        }
        LOGGER.info( "*********************************************** CHARGES CALL END*********************************************************");
       
        return chargeMap;
    }

    /**
     * calculateAllCharges calculate year wise applicable taxList. 1)create Model for Factor,SddrRate,AlvRv,Rate(unit+ property
     * level) 2)call BRMS for all four model. Unit level taxes=taxes calculate for each unit. Property level taxes=taxes calculate
     * only once for all unit in that year(addition of alv and rv based)
     */
    private Map<Date, List<BillPresentAndCalculationDto>> calculateAllCharges(ProvisionalAssesmentMstDto sdto,
            final Organisation organisation, List<String> wardZone,
            WSResponseDTO response, Long deptId, Map<Long, BillDisplayDto> demandLevelRebate, List<Long> finYearList,
            List<BillingScheduleDetailEntity> billSchDetList, TbServiceReceiptMasEntity manualReceiptMas, String assType,
            Date manualDate, String demandGenFlag) {
        // initialize all model
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " calculateAllCharges() method");
        List<Object> factorMasterList = RestClient.castResponse(response, FactorMasterModel.class, 0);
        List<Object> alvMasterList = RestClient.castResponse(response, ALVMasterModel.class, 1);
        List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
                2);
        List<Object> propertySDDRRateList = RestClient.castResponse(response, PropertyTaxDataModel.class, 3);

        FactorMasterModel factorModelInit = (FactorMasterModel) factorMasterList.get(0);
        ALVMasterModel alvModelInit = (ALVMasterModel) alvMasterList.get(0);
        PropertyTaxDataModel sddrRateCvInit = (PropertyTaxDataModel) propertySDDRRateList.get(0);
        PropertyRateMasterModel rateMasterModel = (PropertyRateMasterModel) propertyRateMasterList
                .get(0);
        LookUp telescopicMethod = null;
        try {
        	telescopicMethod = CommonMasterUtility.getValueFromPrefixLookUp("TSM", "ENV", organisation);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for ENV(TSM)");
        }
        LookUp apartmentLevelCalculation = null;
        AtomicBoolean apartmentLevelCalculationAppl = new AtomicBoolean(false);
		try {
			apartmentLevelCalculation = CommonMasterUtility.getValueFromPrefixLookUp("APL", "ALC", organisation);
		}catch (Exception exception) {
			
		}
		if(apartmentLevelCalculation != null && StringUtils.equals(MainetConstants.FlagY, apartmentLevelCalculation.getOtherField())) {
			apartmentLevelCalculationAppl.set(true);
        }
        LookUp skdclEnv  = null;
    	try {
    		skdclEnv = CommonMasterUtility.getValueFromPrefixLookUp("SKDCL", "ENV", organisation);
    	}catch (Exception e) {
    		LOGGER.error("No Prefix found for ENV(SKDCL)");
		}
        AtomicReference<String> calculationType = new AtomicReference<>();

        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
                organisation);
        List<TbTaxMas> indepenTaxList = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
        Map<String, Long> taxCodeWithTaxDescId = new HashMap<String, Long>();
        indepenTaxList.forEach(tax ->{
        	taxCodeWithTaxDescId.put(tax.getTaxCode(), tax.getTaxDescId());
        });
        List<TbTaxMas> depenTaxList = tbTaxMasService.fetchAllDepenentTaxes(organisation.getOrgid(), deptId,
                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
        setCalculationMethod(organisation, calculationType);
        FinancialYear currentFinYear = iFinancialYearService
                .getFinincialYearsById(iFinancialYearService.getFinanceYearId(new Date()), organisation.getOrgid());
        FinancialYear previousFinYear = getPreviousFinYear(currentFinYear);
        Date penaltyDateExtended = getPenaltyDateAfterAddedExtendedDaysToPrevFinYear(previousFinYear, organisation);
        LOGGER.info("create maps for BRMS call");
        // create all map for BRMS call
        final Map<String, List<FactorMasterModel>> factorMap = new HashMap<>();
        Map<String, ALVMasterModel> alvRvMap = new HashMap<>();// <fin_unitId, ALVModel>
        Map<String, PropertyTaxDataModel> cvMap = new HashMap<>();
        Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMapIndependent = new HashMap<>();
        Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMapDependent = new HashMap<>();
        Map<String, TbTaxMas> taxMapWithTaxCode = new HashMap<>();
        List<ProvisionalAssesmentDetailDto> unitDetails = sdto.getProvisionalAssesmentDetailDtoList();
        Map<Long, Boolean> yearWisePropMixed = getYearWisePropertyMixedMap(unitDetails, finYearList);
        sdto.setYearWisePropMixed(yearWisePropMixed);
        Map<Long, Boolean> yearWiseConstructionTypeMixed = getYearWiseConstruTypeMixedMap(unitDetails, finYearList);
        Map<Long, AtomicDouble> yearWiseTotalBuiltUpArea = yearWiseTotalBuiltUpArea(unitDetails, finYearList);
        Map<Long, Boolean> yearWiseOccupancyTypeMixedMap = getYearWiseOccupancyTypeMixedMap(unitDetails, finYearList);

        if (sdto.getFaYearId() != null) { // for change and No change assessment
            FinancialYear lastFinacialYear = iFinancialYearService.getFinincialYearsById(sdto.getFaYearId(),
                    organisation.getOrgid());
            sdto.setLastAssessmentYear(lastFinacialYear.getFaFromDate());
        }
        List<TbBillMas> unPaidBillMasData = null;
        List<TbBillMas> paidBillMasData = null;
        int count = 0;
		
		if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && !Utility.isEnvPrefixAvailable(organisation, "ASCL") && sdto.getAssNo() != null) {
			count = ApplicationContextProvider.getApplicationContext().getBean(IAssessmentMastDao.class)
					.getCountWhetherMaxBmIdExistInMainBill(sdto.getAssNo(), organisation.getOrgid());
			unPaidBillMasData = getUnPaidBills(sdto.getAssNo(), organisation, count);
			paidBillMasData = getPaidBills(sdto.getAssNo(), organisation, count);
		}
		 
        // create all models
        double arrearAmount = setArrearsExist(sdto.getAssNo(), finYearList, organisation.getOrgid(), unPaidBillMasData, count,
                manualReceiptMas, paidBillMasData);
        int arreatBillPtCount = setArrerPtCountForExistBills(unPaidBillMasData, organisation, penaltyDateExtended,
                previousFinYear, manualReceiptMas, paidBillMasData, deptId, sdto.getAssNo(), manualDate);
        int lastAssesmentYear = 0;
        int currentYear = 0;
        if (unitDetails.get(unitDetails.size() - 1).getLastAssesmentDate() != null) {
            Date unitMaxDate = unitDetails.stream().map(u -> u.getLastAssesmentDate()).max(Date::compareTo).get();
            Object[] firstAssesDate = iFinancialYearService.getFinacialYearByDate(unitMaxDate);
            Date firstassesYear = (Date) firstAssesDate[2];
            lastAssesmentYear = Utility.getYearByDate(firstassesYear);

            Object[] currentFinDate = iFinancialYearService.getFinacialYearByDate(new Date());
            Date currentassesYear = (Date) currentFinDate[2];
            currentYear = Utility.getYearByDate(currentassesYear);

        }
        FinancialYear firstYearAssesment = iFinancialYearService.getFinincialYearsById(finYearList.get(0),
                organisation.getOrgid());
        LookUp delayedPenaltyConfg = null;
        String skipFirstYearPenalty = null;
        try {
            delayedPenaltyConfg = CommonMasterUtility.getValueFromPrefixLookUp("SPF", "DCP", organisation);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for DCP(SPF)");
        }
        if (delayedPenaltyConfg != null && StringUtils.isNotBlank(assType)
                && StringUtils.equals(assType, MainetConstants.FlagN)) {
            skipFirstYearPenalty = delayedPenaltyConfg.getOtherField();
        }
        FinancialYear financialYearForRevision =  iFinancialYearService.getFinincialYearsById(finYearList.get(0),
                organisation.getOrgid());;
        for (Long finYear : finYearList) {
            boolean isPropertyMixed = yearWisePropMixed.get(finYear);
            boolean isConstruTypeMixed = yearWiseConstructionTypeMixed.get(finYear);
            AtomicDouble totalBuiltUpArea = yearWiseTotalBuiltUpArea.get(finYear);
            boolean isOccupancyTypeMixed = yearWiseOccupancyTypeMixedMap.get(finYear);

            List<ProvisionalAssesmentDetailDto> listOutput = unitDetails.stream().filter(e -> e.getFaYearId().equals(finYear))
                    .collect(Collectors.toList());// Detail List Year wise
            Logger.getLogger(this.getClass()).error("for each year " + finYear);
            FinancialYear financialYear = iFinancialYearService.getFinincialYearsById(finYear,
                    organisation.getOrgid());
            Optional<BillingScheduleDetailEntity> billSchDet = billSchDetList.stream()
                    .filter(schDet -> schDet.getBillFromDate().equals(financialYear.getFaFromDate())).findFirst();
            // Defect #91917
            boolean isDueDatePassOrNot = billSchDet.isPresent();
            if (isDueDatePassOrNot) {
                getDueDateForSch(billSchDet.get().getBillFromDate(), billSchDet.get().getCalFromDate(),
                        billSchDet.get().getNoOfDays(),
                        billSchDet.get().getBillFromDate(), billSchDet.get().getBillToDate(), organisation.getOrgid());
            }
             List<String> propertyType = new ArrayList<>(0);
            // end of #91917
            int noOfMonthsPTCalculation = getNoOfMonthsToCalculatePT(sdto.getAssAcqDate(), financialYear,
                    organisation.getOrgid());
            unitDetails.stream().filter(unit -> unit.getFaYearId().equals(finYear)).forEach(unit -> {
                int unitLastYearAssesment = 0;
                int unitCurrentYear = 0;
                if (unit.getLastAssesmentDate() != null) {
                    Object[] firstAssesDate = iFinancialYearService.getFinacialYearByDate(unit.getLastAssesmentDate());
                    Date firstassesYear = (Date) firstAssesDate[2];
                    unitLastYearAssesment = Utility.getYearByDate(firstassesYear);

                    Object[] currentFinDate = iFinancialYearService.getFinacialYearByDate(new Date());
                    Date currentassesYear = (Date) currentFinDate[2];
                    unitCurrentYear = Utility.getYearByDate(currentassesYear);
                }

                List<String> usageType = new ArrayList<>(0);
                propertyType.clear();
                setUsageTypeAndPropertyType(unit, organisation, usageType, propertyType);
                String finAndUnitId = finYear + MainetConstants.operator.UNDER_SCORE + unit.getAssdUnitNo();
                // Defect_40635 Construction date need to remove. Currently it is mandatory
                Date unitStartDate;
                if (unit.getAssdYearConstruction() != null) {
                    unitStartDate = (unit.getAssdYearConstruction().after(financialYear.getFaFromDate())
                            ? unit.getAssdYearConstruction()
                            : financialYear.getFaFromDate());
                } else {
                    unitStartDate = financialYear.getFaFromDate();
                }

                /*
                 * Date unitStartDate = (unit.getAssdYearConstruction().after(financialYear.getFaFromDate()) ?
                 * unit.getAssdYearConstruction() : financialYear.getFaFromDate());
                 */
                createFactorModel(organisation, factorModelInit, factorMap, unit, finAndUnitId,
                        unitStartDate);// create factor model
                if (MainetConstants.Property.CV.equals(calculationType.get())) { // create CV model in case of CV calculation
                                                                                 // type
                    createCvModel(organisation, wardZone, sddrRateCvInit, cvMap, usageType, finAndUnitId,
                            unitStartDate);
                }
                
                ALVMasterModel alvModel = null;
                /**
                 * This remarks is used to identify this call is for Bill Revision aligarh. For all year age factor calculating based on first year selected fro UI for bill generation
                 *  Ex: 
                 *  17-18
                 *  18-19
                 *  19-20
                 *  20-21
                 *  For all years age factor value will be consider as -> 17 - 18 age factor value. 
                 *  This condition will be exceuted in aligarh bill revise.
                 */
                
                Map<String, Double> alvRvForApartment = null;
                if(apartmentLevelCalculationAppl.get()) {
                 alvRvForApartment = calculateFatorValueForApartment(organisation, wardZone, alvModelInit, usageType, finAndUnitId,
    						sdto, isPropertyMixed, propertyType, isConstruTypeMixed, isDueDatePassOrNot,
    						isOccupancyTypeMixed, financialYearForRevision, demandGenFlag, apartmentLevelCalculationAppl.get(),
    						finYear,deptId, finYearList);
                }
                
                if(("REVISION").equals(sdto.getRemarks()) && Utility.isEnvPrefixAvailable(organisation, "BRV")) {
                	  alvModel = createAlvRvModel(organisation, wardZone, alvModelInit, alvRvMap, unit, usageType,
                             finAndUnitId, unitStartDate, sdto, isPropertyMixed, propertyType, isConstruTypeMixed,
                             isDueDatePassOrNot, totalBuiltUpArea, isOccupancyTypeMixed, financialYearForRevision,demandGenFlag,apartmentLevelCalculationAppl.get(), alvRvForApartment);// create
                }else {
                	  alvModel = createAlvRvModel(organisation, wardZone, alvModelInit, alvRvMap, unit, usageType,
                             finAndUnitId, unitStartDate, sdto, isPropertyMixed, propertyType, isConstruTypeMixed,
                             isDueDatePassOrNot, totalBuiltUpArea, isOccupancyTypeMixed, financialYear,demandGenFlag,apartmentLevelCalculationAppl.get(),alvRvForApartment);// create
                }
                // Alv
                // Rv
                // model
                LOGGER.info("Create Rate Tax model");
                // create Rate Tax model for independent taxes at unit level
                createRateTaxModel(organisation, wardZone, rateMasterModel, calculationType, indepenTaxList,
                        taxMapWithTaxCode, rateMapIndependent,
                        unit, usageType, finAndUnitId, financialYear, unitStartDate, alvModel, isConstruTypeMixed,
                        isDueDatePassOrNot, isOccupancyTypeMixed, isPropertyMixed, sdto, arrearAmount, noOfMonthsPTCalculation,
                        unitLastYearAssesment, unitCurrentYear,propertyType);
                // create Rate Tax model for dependent taxes at unit level
                createRateTaxModel(organisation, wardZone, rateMasterModel, calculationType, depenTaxList,
                        taxMapWithTaxCode, rateMapDependent,
                        unit, usageType, finAndUnitId, financialYear, unitStartDate, alvModel, isConstruTypeMixed,
                        isDueDatePassOrNot, isOccupancyTypeMixed, isPropertyMixed, sdto, arrearAmount, noOfMonthsPTCalculation,
                        unitLastYearAssesment, unitCurrentYear,propertyType);
            });
            // create Rate Tax model for independent taxes at property level
            createRateTaxModelForIndependentTaxes(organisation, wardZone, rateMasterModel, calculationType,
                    indepenTaxList,
                    taxMapWithTaxCode, rateMapIndependent, finYear.toString(), financialYear, listOutput, isPropertyMixed,
                    sdto,
                    isConstruTypeMixed, isDueDatePassOrNot, totalBuiltUpArea, isOccupancyTypeMixed, arrearAmount,
                    noOfMonthsPTCalculation, lastAssesmentYear, currentYear,propertyType);
            // create Rate Tax model for dependent taxes at property level
            createRateTaxModelForDependentTaxes(organisation, wardZone, rateMasterModel, calculationType,
                    depenTaxList,
                    taxMapWithTaxCode, rateMapDependent, finYear.toString(), financialYear, listOutput, isPropertyMixed, sdto,
                    isConstruTypeMixed, isDueDatePassOrNot, totalBuiltUpArea, isOccupancyTypeMixed, arrearAmount,
                    noOfMonthsPTCalculation, lastAssesmentYear, currentYear,propertyType);
        }

        // call BRMS
        // call BRMS for factor model
        LOGGER.info("call BRMS for factor model");
        Map<String, List<FactorMasterModel>> factorMapRes = callBrmsForFactorModel(factorMap);

        /// call BRMS for CV charges model in case of CV
        Map<String, PropertyTaxDataModel> cvResult = null;
        if (MainetConstants.Property.CV.equals(calculationType.get())) {
            WSRequestDTO cvRequest = new WSRequestDTO();
            cvRequest.setDataModel(cvMap);
            WSResponseDTO cvResponse = RestClient.callBRMS(cvRequest, ServiceEndpoints.Property.PROP_SDDR);
            cvResult = castRequestToSddrRate(cvResponse);
        }
        // call BRMS to calculate ALV RV for each unit
        Map<String, ALVMasterModel> alvRvMapRes = null;
		if ((StringUtils.equals(MainetConstants.FlagY,demandGenFlag)
				&& Utility.isEnvPrefixAvailable(organisation, "ERV")) || (apartmentLevelCalculationAppl.get())) {
			alvRvMapRes = alvRvMap;
        }else {
        	alvRvMapRes = callBrmsOfAlvRv(alvRvMap, factorMapRes, cvResult);
        }
		if(telescopicMethod != null && StringUtils.equals(MainetConstants.FlagY, telescopicMethod.getOtherField())) {
        	calculateAlvRvTelescopic(alvRvMapRes, sdto);
        }
        setAlvRvInDto(alvRvMapRes, sdto);

        // combine ALV RV for each year for unit level Taxes
        Map<String, AlvFactorCombineResDto> combineAlvRvMapRes = setAlvFactorOutputInRateModel(alvRvMapRes, factorMapRes);

        Map<String, List<AtomicDouble>> totalRvOfYearWiseUnitWiseExemption = setTotalRvOfExemptionWise(unitDetails, alvRvMapRes,
                organisation, finYearList);
        // call BRMS to calculate Unit+Property level Independent taxes
        Map<String, Map<Date, List<PropertyRateMasterModel>>> unitWiseInDependentTaxMap = callBrmsOfTaxRateForIndependentTaxes(
                rateMapIndependent,
                taxMapWithTaxCode, combineAlvRvMapRes, alvRvMapRes, factorMapRes, organisation,
                totalRvOfYearWiseUnitWiseExemption);

        // call BRMS to calculate Unit+Property level dependent taxes
        Map<Date, List<PropertyRateMasterModel>> schWiseDependentMap = callBrmsOfTaxRateForDependentTaxes(null,
                unitWiseInDependentTaxMap,
                rateMapDependent,
                taxMapWithTaxCode, combineAlvRvMapRes, alvRvMapRes, factorMapRes, organisation,
                totalRvOfYearWiseUnitWiseExemption, currentFinYear, arreatBillPtCount, penaltyDateExtended, previousFinYear,
                manualReceiptMas, skipFirstYearPenalty, firstYearAssesment, manualDate,taxCodeWithTaxDescId);

        Map<Date, List<PropertyRateMasterModel>> schWiseIndependntMap = prepareScheduleWiseChargeMap(unitWiseInDependentTaxMap);

        // combine all independent and dependent taxes
        Map<Date, List<PropertyRateMasterModel>> combineMap = combineDependentAndIndependentTaxes(schWiseIndependntMap,
                schWiseDependentMap);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " prepareScheduleWisePreCalMap() method");
        if((Utility.isEnvPrefixAvailable(organisation, "SKDCL") || Utility.isEnvPrefixAvailable(organisation, "PSCL")) && StringUtils.equals(assType, MainetConstants.Property.NEW_ASESS)) {
        	return prepareScheduleWisePreCalMapForSkdcl(combineMap, organisation, demandLevelRebate,sdto, finYearList);
        }else {
        	return prepareScheduleWisePreCalMap(combineMap, organisation, demandLevelRebate);
        }
    }

    private boolean getDueDateForSch(Date finYearStrDate, Long calFromDate, Long noOfDays, Date schStartDate, Date schEndDate,
            long orgid) {
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        Date dueDate = null;
        String lookCode = CommonMasterUtility.getNonHierarchicalLookUpObject(calFromDate, org).getLookUpCode();
        Calendar cal = Calendar.getInstance();
        if (lookCode.equals(MainetConstants.Property.DueDatePerf.BGD)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDays.toString()));
            dueDate = cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.FYS)) {
            cal.setTime(finYearStrDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDays.toString()));
            dueDate = cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SSD)) {
            cal.setTime(schStartDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDays.toString()));
            dueDate = cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SED)) {
            cal.setTime(schEndDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDays.toString()));
            dueDate = cal.getTime();
        }
        boolean isDueDatePassOrNot = false;
        if (dueDate != null) {
            isDueDatePassOrNot = Utility.compareDate(dueDate, new Date());
        }
        return isDueDatePassOrNot;
    }

    private Map<String, List<FactorMasterModel>> callBrmsForFactorModel(final Map<String, List<FactorMasterModel>> factorMap) {
        LOGGER.info(
                "****************************************BRMS call for Factor Start********************************************** ");
        WSRequestDTO factorRequest = new WSRequestDTO();
        factorRequest.setDataModel(factorMap);
        WSResponseDTO factorResult = RestClient.callBRMS(factorRequest, ServiceEndpoints.Property.PROP_FACT);
        return castRequestToDataModelMapRate(factorResult);
    }

    private ALVMasterModel createAlvRvModel(final Organisation organisation, List<String> wardZone, ALVMasterModel alvModelInit,
            Map<String, ALVMasterModel> alvRvMap, ProvisionalAssesmentDetailDto unit, List<String> usageType, String finAndUnitId,
            Date unitStartDate, ProvisionalAssesmentMstDto sdto, boolean isPropertyMixed, List<String> propertyType,
            boolean isConstruTypeMixed, boolean isDueDatePassOrNot, AtomicDouble totalBuiltUpArea, boolean isOccupancyTypeMixed,
            FinancialYear financialYear, String demandGenFlag,boolean apartmentLevelCalculationAppl,Map<String, Double> alvRvForApartment) {
        ALVMasterModel alvModel = null;
        try {
            alvModel = (ALVMasterModel) alvModelInit.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }
        populateALVModel(alvModel, unit, wardZone, organisation, unitStartDate,
                usageType, sdto, propertyType, financialYear);
        if (isPropertyMixed) {
            alvModel.setPropertyIsMixed(MainetConstants.YESL);
        } else {
            alvModel.setPropertyIsMixed(MainetConstants.NOL);
        }
        if (isConstruTypeMixed) {
            alvModel.setConstructionTypeIsMixed(MainetConstants.YESL);
        } else {
            alvModel.setConstructionTypeIsMixed(MainetConstants.NOL);
        }
        if (isDueDatePassOrNot) {
            alvModel.setIsDueDatePassOrNot(MainetConstants.YESL);
        } else {
            alvModel.setIsDueDatePassOrNot(MainetConstants.NOL);
        }
        if (isOccupancyTypeMixed) {
            alvModel.setOccupacyTypeIsMixed(MainetConstants.YESL);
        } else {
            alvModel.setOccupacyTypeIsMixed(MainetConstants.NOL);
        }
        alvModel.setTotalTaxableArea(totalBuiltUpArea.doubleValue());
        if(unit.getCpdUsageCls() != null) {
        	 StringBuilder usageClass = new StringBuilder();
     		LookUp usageLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(unit.getCpdUsageCls(), organisation);
     		if(usageLookUp != null) {
     			usageClass.append("Class");
     			usageClass.append(MainetConstants.WHITE_SPACE);
				usageClass.append(usageLookUp.getDescLangFirst());
				alvModel.setUsageClass(usageClass.toString());
     		}
        }else {
        	alvModel.setUsageClass("NA");
        }
		if(StringUtils.equals(MainetConstants.FlagY,demandGenFlag)
				&& Utility.isEnvPrefixAvailable(organisation, "ERV")) {
			if (unit.getAssdAlv() != null) {
				alvModel.setArv(unit.getAssdAlv());
			} else {
				alvModel.setArv(0.0);
			}
			if (unit.getAssdRv() != null) {
				alvModel.setRv(unit.getAssdRv());
			} else {
				alvModel.setRv(0.0);
			}
		} else if (apartmentLevelCalculationAppl && StringUtils.isNotBlank(sdto.getParentPropNo()) && MapUtils.isNotEmpty(alvRvForApartment)) {

			alvModel.setArv(alvRvForApartment.get("ARV"));
			alvModel.setRv(alvRvForApartment.get("RV"));
			alvModel.setTotalTaxableArea(alvRvForApartment.get("TotalArea"));
		}
        alvRvMap.put(finAndUnitId, alvModel);
        return alvModel;
    }

    private void populateALVModel(ALVMasterModel alvMasterModel,
            ProvisionalAssesmentDetailDto unit, List<String> wardZone, Organisation organisation, Date startDate,
            List<String> usageType, ProvisionalAssesmentMstDto sdto, List<String> propertyType, FinancialYear financialYear) {
        LookUp constructionLookup = null;
        LookUp occupancyLookup = null;
        LookUp roadFactorLookup = null;
        LookUp floorLookup = null;
        LookUp ownerLookup = null;
        if (unit.getAssdConstruType() != null) {
            constructionLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(unit.getAssdConstruType(),
                    organisation);
        }
        if (unit.getAssdOccupancyType() != null) {
            occupancyLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    unit.getAssdOccupancyType(), organisation);
        }
        if (sdto.getPropLvlRoadType() != null) {
            roadFactorLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    sdto.getPropLvlRoadType(), organisation);
        }
        if (unit.getAssdFloorNo() != null) {
            floorLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    unit.getAssdFloorNo(), organisation);
        }
        if (sdto.getAssOwnerType() != null) {
            ownerLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    sdto.getAssOwnerType(), organisation);
        }
        alvMasterModel.setOrgId(organisation.getOrgid());
        if (constructionLookup != null) {
            alvMasterModel.setConstructionClass(constructionLookup.getDescLangFirst());
        }
        alvMasterModel.setUsageSubtype1(usageType.get(0));
        alvMasterModel.setUsageSubtype2(usageType.get(1));
        alvMasterModel.setUsageSubtype3(usageType.get(2));
        alvMasterModel.setUsageSubtype4(usageType.get(3));
        alvMasterModel.setUsageSubtype5(usageType.get(4));
        alvMasterModel.setWardZoneLevel1(wardZone.get(0));
        alvMasterModel.setWardZoneLevel3(wardZone.get(2));
        alvMasterModel.setWardZoneLevel2(wardZone.get(1));
        alvMasterModel.setWardZoneLevel4(wardZone.get(3));
        alvMasterModel.setWardZoneLevel5(wardZone.get(4));
        alvMasterModel.setPropertyTypeLevel1(propertyType.get(0));
        alvMasterModel.setPropertyTypeLevel2(propertyType.get(1));
        alvMasterModel.setPropertyTypeLevel3(propertyType.get(2));
        alvMasterModel.setPropertyTypeLevel4(propertyType.get(3));
        alvMasterModel.setPropertyTypeLevel5(propertyType.get(4));

        if (occupancyLookup != null)
            alvMasterModel.setOccupancyFactor(occupancyLookup.getDescLangFirst());
        if (roadFactorLookup != null)
            alvMasterModel.setRoadType(roadFactorLookup.getDescLangFirst());
        if (ownerLookup != null)
            alvMasterModel.setOwnershipType(ownerLookup.getDescLangFirst());
        if (unit.getAssdBuildupArea() != null)
            alvMasterModel.setTaxableArea(unit.getAssdBuildupArea());
        if (sdto.getAssPlotArea() != null)
            alvMasterModel.setPlotArea(sdto.getAssPlotArea());

        if (floorLookup != null) {
            List<String> alvfct = Arrays.asList(MainetConstants.Property.Floor.BASEMENT, MainetConstants.Property.Floor.LAND,
                    MainetConstants.Property.Floor.GROUND_FLOOR, MainetConstants.Property.Floor.FIRST,
                    MainetConstants.Property.Floor.SECOND, MainetConstants.Property.Floor.THIRD);
            if (alvfct.contains(floorLookup.getLookUpCode())) {
                alvMasterModel.setFloorNo(floorLookup.getDescLangFirst());
            } else {
                alvMasterModel.setFloorNo(MainetConstants.Property.Floor.OTHER);
            }
        }
        if (sdto.getAssAcqDate() != null) {
            LocalDate accqDate = sdto.getAssAcqDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Timestamp finToDateStamp = new Timestamp(financialYear.getFaToDate().getTime());
            Date finToDate = new Date(finToDateStamp.getTime());
            LocalDate finanToDate = finToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long numberOfYears = Period.between(accqDate, finanToDate).getYears();
            alvMasterModel.setAgeFactor(numberOfYears);
            if(unit.getAssdYearConstruction() != null) {
            	alvMasterModel.setConstructionCompletionDate(unit.getAssdYearConstruction().getTime());
            }
        }
        if (sdto.getAssAcqDate() != null) {
            alvMasterModel.setYearOfAcquisition(sdto.getAssAcqDate().getTime());
        }
        alvMasterModel.setMonthlyRent(unit.getAssdAnnualRent());
        alvMasterModel.setRateStartDate(startDate.getTime());
        if (sdto.getLastAssessmentYear() != null) {
            alvMasterModel.setLastAssessmentYear(Utility.getYearByDate(sdto.getLastAssessmentYear()));
        }
        if (sdto.getCreatedDate() != null) {
            alvMasterModel.setCurrentAssessmentYear(Utility.getYearByDate(sdto.getCreatedDate()));
        }
		if (sdto.getIsGroup() != null) {
			alvMasterModel.setIsGroupProperty(sdto.getIsGroup());
		}
    }

    private void createRateTaxModelForIndependentTaxes(final Organisation organisation, List<String> wardZone,
            PropertyRateMasterModel rateMasterModelInit, AtomicReference<String> calculationType, List<TbTaxMas> indepenTaxList,
            Map<String, TbTaxMas> taxMapWithTaxCode, Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap,
            String finAndUnitId, FinancialYear financialYear, List<ProvisionalAssesmentDetailDto> unitDetails,
            boolean isPropertyMixed, ProvisionalAssesmentMstDto sdto, boolean isConstruTypeMixed, boolean isDueDatePassOrNot,
            AtomicDouble totalBuiltUpArea, boolean isOccupancyTypeMixed, double arrearAmount, int noOfMonthsPTCalculation,
            int lastAssesmentYear, int currentYear,List<String> propertyType) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " createRateTaxModelForIndependentTaxes() method");
        List<Object[]> billschedule = billingScheduleService.getAllBillScheduleFromDate(financialYear.getFaFromDate(),
                financialYear.getFaToDate(),
                organisation.getOrgid());
        Map<Long, TbTaxMas> taxMap = new HashMap<>();
        Map<Date, List<PropertyRateMasterModel>> schMap = new HashMap<>();
        billschedule.forEach(billSch -> {   // for each bill schedule
            final Date scheStartDate = (Date) billSch[1];
            final Date scheEndDate = (Date) billSch[2];
            List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
            indepenTaxList.forEach(tax -> {
                taxMap.put(tax.getTaxId(), tax);
                taxMapWithTaxCode.put(tax.getTaxCode(), tax);
                PropertyRateMasterModel rateModel = null;
                try {
                    rateModel = (PropertyRateMasterModel) rateMasterModelInit.clone();
                } catch (CloneNotSupportedException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new FrameworkException(e);
                }
                populateChargeModel(rateModel, organisation, calculationType.get(), wardZone, scheStartDate, scheEndDate, taxMap,
                        tax, unitDetails, isPropertyMixed, sdto, lastAssesmentYear, currentYear,propertyType);
                if (isConstruTypeMixed) {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.NOL);
                }
                if (isDueDatePassOrNot) {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.YESL);
                } else {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.NOL);
                }
                if (isPropertyMixed) {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.NOL);
                }
                if (isOccupancyTypeMixed) {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.NOL);
                    if (CollectionUtils.isNotEmpty(unitDetails) && unitDetails.get(0).getAssdOccupancyType() != null) {
                        rateModel.setOccupancyType(CommonMasterUtility.getNonHierarchicalLookUpObject(
                                unitDetails.get(0).getAssdOccupancyType(), organisation).getDescLangFirst());
                    }
                }				
                rateModel.setNoOfMonthsPT(noOfMonthsPTCalculation);
                rateModel.setArrearAmountWithoutRebate(arrearAmount);
                rateModel.setTotalTaxableArea(totalBuiltUpArea.doubleValue());
                rateMasterList.add(rateModel);
            });
            schMap.put(scheStartDate, rateMasterList);
        });
        rateMap.put(finAndUnitId, schMap);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " createRateTaxModelForIndependentTaxes() method");
    }

    /*
     * indepentList=tax list of independent taxes with summation of tax value unit wise indepentListUnitWise=tax list of
     * independent taxes with unit level tax amount
     */

    private void setunitLevelAlvRv(Map<String, ALVMasterModel> alvRvMapRes, List<PropertyRateMasterModel> taxList,
            Map<String, List<FactorMasterModel>> factorMap, List<PropertyRateMasterModel> indepentList,
            List<PropertyRateMasterModel> indepentListUnitWise, AlvFactorCombineResDto resDto, String key) {
        ALVMasterModel alvModel = alvRvMapRes.get(key);
        Map<String, String> factorValue = new HashMap<>(0);
        if (factorMap != null && !factorMap.isEmpty()) {
            List<FactorMasterModel> factorList = factorMap.get(key);
            if (factorList != null && !factorList.isEmpty()) {
                factorList.forEach(factor -> {
                    String factValue = factorValue.get(factor.getFactor());
                    if (factValue == null) {
                        factorValue.put(factor.getFactor(), factor.getFactorValue());
                    } else {
                        factorValue.put(factor.getFactor(), factValue + "," + factor.getFactorValue());
                    }
                });
            }
        }
        taxList.forEach(rateModel -> {
            /*
             * if (indepentListUnitWise != null) { indepentListUnitWise.stream().filter(oldTax ->
             * oldTax.getTaxCode().equals(rateModel.getParentTaxCode())) .forEach(eachTax -> {
             * rateModel.setParentTaxValue(eachTax.getTaxValue()+rateModel.getParentTaxValue()); }); }
             */
            if (alvModel != null) {
                rateModel.setArv(alvModel.getArv());
                rateModel.setRv(alvModel.getRv());
                rateModel.setCv(alvModel.getCv());
                if (resDto.getResidentialRv() != null) {
                	rateModel.setTotalResidentialRv(resDto.getResidentialRv().doubleValue());
                }
                /*
                 * task: 30262 calculate property tax based on totalRV as required for rajnandgaon and ambikapur ULB changes made
                 * by Apeksha
                 */
                if (resDto.getArv() != null) {
                    rateModel.setTotalArv(resDto.getArv().doubleValue());
                }
                if (resDto.getRv() != null) {
                    rateModel.setTotalRv(resDto.getRv().doubleValue());
                }
                if (resDto.getCv() != null) {
                    rateModel.setTotalCv(resDto.getCv().doubleValue());
                }
                rateModel.setFactor(factorValue);
                rateModel.setMultiplicationFactor(alvModel.getMultiplicationFactorTax());
                rateModel.setAgeFactor(alvModel.getAgeFactor());
            }
        });

    }

    private void createRateTaxModelForDependentTaxes(final Organisation organisation, List<String> wardZone,
            PropertyRateMasterModel rateMasterModelInit, AtomicReference<String> calculationType, List<TbTaxMas> indepenTaxList,
            Map<String, TbTaxMas> taxMapWithTaxCode, Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap,
            String finAndUnitId, FinancialYear financialYear, List<ProvisionalAssesmentDetailDto> unitDetails,
            boolean isPropertyMixed, ProvisionalAssesmentMstDto sdto, boolean isConstruTypeMixed, boolean isDueDatePassOrNot,
            AtomicDouble totalBuiltUpArea, boolean isOccupancyTypeMixed, double arrearAmount, int noOfMonthsPTCalculation,
            int lastAssesmentYear, int currentYear,List<String> propertyType) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " createRateTaxModelForDependentTaxes() method");
    	List<Object[]> billschedule = billingScheduleService.getAllBillScheduleFromDate(financialYear.getFaFromDate(),
                financialYear.getFaToDate(),
                organisation.getOrgid());
        Map<Long, TbTaxMas> taxMap = new HashMap<>();
        // final ALVMasterModel alvModelF = alvModel;
        Map<Date, List<PropertyRateMasterModel>> schMap = new HashMap<>();
        billschedule.forEach(billSch -> {   // for each bill schedule
            final Date scheStartDate = (Date) billSch[1];
            final Date scheEndDate = (Date) billSch[2];
            List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
            indepenTaxList.forEach(tax -> {
                taxMap.put(tax.getTaxId(), tax);
                taxMapWithTaxCode.put(tax.getTaxCode(), tax);
                PropertyRateMasterModel rateModel = null;
                try {
                    rateModel = (PropertyRateMasterModel) rateMasterModelInit.clone();
                } catch (CloneNotSupportedException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new FrameworkException(e);
                }
                populateChargeModel(rateModel, organisation, calculationType.get(), wardZone, scheStartDate, scheEndDate, taxMap,
                        tax, unitDetails, isPropertyMixed, sdto, lastAssesmentYear, currentYear,propertyType);
                if (isConstruTypeMixed) {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.NOL);
                }
                if (isDueDatePassOrNot) {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.YESL);
                } else {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.NOL);
                }
                if (isPropertyMixed) {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.NOL);
                }
                if (isOccupancyTypeMixed) {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.NOL);
                    if (unitDetails.get(0).getAssdOccupancyType() != null) {
                        rateModel.setOccupancyType(CommonMasterUtility.getNonHierarchicalLookUpObject(
                                unitDetails.get(0).getAssdOccupancyType(), organisation).getDescLangFirst());
                    }
                }				
                rateModel.setNoOfMonthsPT(noOfMonthsPTCalculation);
                rateModel.setArrearAmountWithoutRebate(arrearAmount);
                rateModel.setTotalTaxableArea(totalBuiltUpArea.doubleValue());
                rateMasterList.add(rateModel);
            });
            schMap.put(scheStartDate, rateMasterList);
        });
        rateMap.put(finAndUnitId, schMap);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " createRateTaxModelForDependentTaxes() method");
    }

    private void createCvModel(final Organisation organisation, List<String> wardZone, PropertyTaxDataModel sddrRateCvInit,
            Map<String, PropertyTaxDataModel> cvMap, List<String> usageType,
            String finAndUnitId, Date unitStartDate) {
        // calculate CV rate in case of CV
        PropertyTaxDataModel sddrRateCv = null;
        try {
            sddrRateCv = (PropertyTaxDataModel) sddrRateCvInit.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }
        populateSddrRateModel(sddrRateCv, wardZone, organisation, unitStartDate, usageType);
        cvMap.put(finAndUnitId, sddrRateCv);
    }

    private void createRateTaxModel(final Organisation organisation, List<String> wardZone,
            PropertyRateMasterModel rateMasterModelInit, AtomicReference<String> calculationType, List<TbTaxMas> indepenTaxList,
            Map<String, TbTaxMas> taxMapWithTaxCode, Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap,
            ProvisionalAssesmentDetailDto unit, List<String> usageType, String finAndUnitId, FinancialYear financialYear,
            Date unitStartDate, ALVMasterModel alvModel, boolean isConstruTypeMixed, boolean isDueDatePassOrNot,
            boolean isOccupancyTypeMixed, boolean isPropertyMixed, ProvisionalAssesmentMstDto sdto, double arrearAmount,
            int noOfMonthsPTCalculation, int unitLastAssesmenyYear, int currentYear,List<String> propertyType) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " createRateTaxModel() method");
        List<Object[]> billschedule = billingScheduleService.getAllBillScheduleFromDate(unitStartDate,
                financialYear.getFaToDate(),
                organisation.getOrgid());
        Map<Long, TbTaxMas> taxMap = new HashMap<>();
        final ALVMasterModel alvModelF = alvModel;
        Map<Date, List<PropertyRateMasterModel>> schMap = new HashMap<>();
        billschedule.forEach(billSch -> {   // for each bill schedule
            final Date scheStartDate = (Date) billSch[1];
            final Date scheEndDate = (Date) billSch[2];
            List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
            indepenTaxList.forEach(tax -> {
                taxMap.put(tax.getTaxId(), tax);
                taxMapWithTaxCode.put(tax.getTaxCode(), tax);
                PropertyRateMasterModel rateModel = null;
                try {
                    rateModel = (PropertyRateMasterModel) rateMasterModelInit.clone();
                } catch (CloneNotSupportedException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new FrameworkException(e);
                }
                populateChargeModel(rateModel, alvModelF, organisation, calculationType.get(),
                        usageType, wardZone, scheStartDate, scheEndDate, unit.getAssdYearConstruction(), taxMap,
                        tax,propertyType);
                if (isConstruTypeMixed) {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setConstructionTypeIsMixed(MainetConstants.NOL);
                }

                if (isPropertyMixed) {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setPropertyTypeIsMixed(MainetConstants.NOL);
                }
                if (isDueDatePassOrNot) {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.YESL);
                } else {
                    rateModel.setIsDueDatePassOrNot(MainetConstants.NOL);
                }
                if (isOccupancyTypeMixed) {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.YESL);
                } else {
                    rateModel.setOccupacyTypeIsMixed(MainetConstants.NOL);
                }

                rateModel.setNoOfMonthsPT(noOfMonthsPTCalculation);
                rateModel.setArrearAmountWithoutRebate(arrearAmount);

                rateModel.setLastAssessmentYear(unitLastAssesmenyYear);
                rateModel.setCurrentAssessmentYear(currentYear);
                rateModel.setIsGroup(sdto.getIsGroup());
				if (sdto.getIsGroup() != null) {
					rateModel.setIsGroupProperty(sdto.getIsGroup());
				}
                if(StringUtils.isNotBlank(unit.getLegal()) && StringUtils.equals("Legal", unit.getLegal())) {
                	rateModel.setIllegalFlag("No");
                }else{
                	
                	Date acquisitionDate = null;
            		LookUp dateOfAcquisitionLookUp = null;

            		try {
            			dateOfAcquisitionLookUp = CommonMasterUtility.getValueFromPrefixLookUp("DAI", "ENV", organisation);
            		} catch (Exception exception) {
            			LOGGER.error("No Prefix found for ENV(SSD)");
            		}
            		if (dateOfAcquisitionLookUp != null
            				&& StringUtils.isNotBlank(dateOfAcquisitionLookUp.getOtherField())) {
            			String dateInString = dateOfAcquisitionLookUp.getOtherField();
            			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            			try {
            				acquisitionDate = formatter.parse(dateInString);
            			} catch (Exception e) {
            				e.printStackTrace();
            			}
            		}
            		
            		if(acquisitionDate != null && (Utility.comapreDates(acquisitionDate, sdto.getAssAcqDate()) || Utility.compareDate(acquisitionDate, sdto.getAssAcqDate()))) {
            			rateModel.setIllegalFlag("Yes");
            		}else {
            			rateModel.setIllegalFlag("No");
            		}
                }
                rateMasterList.add(rateModel);
            });
            schMap.put(scheStartDate, rateMasterList);
        });
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " createRateTaxModel() method");
        rateMap.put(finAndUnitId, schMap);
    }

    private PropertyRateMasterModel populateChargeModel(
            PropertyRateMasterModel propertyRateMasterModel, ALVMasterModel alv, Organisation organisation,
            String calculationMethod, List<String> usageType, List<String> wardZone, Date scheduleStartDate, Date scheduleEndDate,
            Date consDate, Map<Long, TbTaxMas> taxMap, TbTaxMas tax, List<String> propertyType) {
        propertyRateMasterModel.setOrgId(organisation.getOrgid());
        propertyRateMasterModel.setRateStartDate(alv.getRateStartDate());
        propertyRateMasterModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                organisation).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            propertyRateMasterModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    organisation).getDescLangFirst());
        }
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        propertyRateMasterModel.setTaxCode(tax.getTaxCode());
        propertyRateMasterModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());

        if (tax.getParentCode() != null && tax.getParentCode() > 0) {
            TbTaxMas tbTax = taxMap.get(tax.getParentCode());
            if (tbTax == null) {
                tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), organisation.getOrgid());
            }
            propertyRateMasterModel.setParentTaxCode(tbTax.getTaxCode());
        }
        // propertyRateMasterModel.setFactor(factor);
        propertyRateMasterModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.UNIT);
        propertyRateMasterModel.setUsageSubtype1(usageType.get(0));
        propertyRateMasterModel.setUsageSubtype2(usageType.get(1));
        propertyRateMasterModel.setUsageSubtype3(usageType.get(2));
        propertyRateMasterModel.setUsageSubtype4(usageType.get(3));
        propertyRateMasterModel.setUsageSubtype5(usageType.get(4));

        propertyRateMasterModel.setOccupancyType(alv.getOccupancyFactor());
        propertyRateMasterModel.setTaxableArea(alv.getTaxableArea());
        if (MainetConstants.Property.ARV.equals(calculationMethod)) {
            propertyRateMasterModel.setArv(alv.getArv());
            propertyRateMasterModel.setRv(alv.getRv());
        } else if (MainetConstants.Property.CV.equals(calculationMethod)) {
            propertyRateMasterModel.setCv(alv.getCv());
        }
        propertyRateMasterModel.setWardZoneLevel1(wardZone.get(0));
        propertyRateMasterModel.setWardZoneLevel3(wardZone.get(2));
        propertyRateMasterModel.setWardZoneLevel2(wardZone.get(1));
        propertyRateMasterModel.setWardZoneLevel4(wardZone.get(3));
        propertyRateMasterModel.setWardZoneLevel5(wardZone.get(4));
        propertyRateMasterModel.setConstructionClass(alv.getConstructionClass());
        propertyRateMasterModel.setConstructionCompletionDate(alv.getConstructionCompletionDate());
        propertyRateMasterModel.setOwnershipType(alv.getOwnershipType());
        propertyRateMasterModel.setYearOfAcquisition(alv.getYearOfAcquisition());
        // propertyRateMasterModel.setLastAssessmentYear(alv.getLastAssessmentYear());
        // propertyRateMasterModel.setCurrentAssessmentYear(alv.getCurrentAssessmentYear());
        // double billingPeriod = getBillingPeriod(scheduleStartDate, scheduleEndDate, consDate);
        // propertyRateMasterModel.setBillingPeriod(billingPeriod);
        propertyRateMasterModel.setTotalTaxableArea(alv.getTotalTaxableArea());
        propertyRateMasterModel.setFloorNo(alv.getFloorNo());
        propertyRateMasterModel.setPlotArea(alv.getPlotArea());
        if (alv.getMonthlyRent() != null) {
            propertyRateMasterModel.setMonthlyRent(alv.getMonthlyRent());
        }
        propertyRateMasterModel.setPropertyTypeLevel1(propertyType.get(0));
        propertyRateMasterModel.setPropertyTypeLevel2(propertyType.get(1));
        propertyRateMasterModel.setPropertyTypeLevel3(propertyType.get(2));
        propertyRateMasterModel.setPropertyTypeLevel4(propertyType.get(3));
        propertyRateMasterModel.setPropertyTypeLevel5(propertyType.get(4));
        return propertyRateMasterModel;
    }

    private void createFactorModel(final Organisation organisation, FactorMasterModel factorModelInit,
            final Map<String, List<FactorMasterModel>> factorMap, ProvisionalAssesmentDetailDto unit,
            String finAndUnitId, Date unitStartDate) {
        List<FactorMasterModel> factList = new ArrayList<>();
        Map<String, String> unitFactors = new HashMap<>();
        List<String> alvfct = Arrays.asList(MainetConstants.Property.ASSESS, MainetConstants.Property.ASS_AND_TAX,
                MainetConstants.Property.TAX);
      
        List<ProvisionalAssesmentFactorDtlDto> factors = unit.getProvisionalAssesmentFactorDtlDtoList();
        if (factors != null && !factors.isEmpty()) {
            factors.stream().filter(factor -> alvfct.contains(CommonMasterUtility
                    .lookUpByLookUpIdAndPrefix(factor.getAssfFactorId(), MainetConstants.Property.propPref.FCT,
                            organisation.getOrgid())
                    .getOtherField()))
                    .forEach(factor -> {// for each Factor in unit
                        FactorMasterModel factorModel = null;
                        try {
                            factorModel = (FactorMasterModel) factorModelInit.clone();
                        } catch (CloneNotSupportedException e) {
                            LOGGER.error(e.getMessage(), e);
                            throw new FrameworkException(e);
                        }
                        populateFactorMasterModel(factorModel, factor, organisation, unitStartDate,
                                unitFactors);
                        
                        if ((Utility.isEnvPrefixAvailable(organisation, "SKDCL"))) {
                			if ((factor.getFactorDate() == null) || (Utility.compareDate(new Date(), factor.getFactorDate())
                					|| (Utility.comapreDates(new Date(), factor.getFactorDate())))) {
                				factList.add(factorModel);
                			}
                		} else {
                			factList.add(factorModel);
                		}
                    });
            factorMap.put(finAndUnitId, factList);
        }
    }

    /*
     * input for this method Map create for rate alv rv map with output factor map
     */

    private Map<String, Map<Date, List<PropertyRateMasterModel>>> callBrmsOfTaxRateForIndependentTaxes(
            Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap, Map<String, TbTaxMas> taxMapWithTaxCode,
            Map<String, AlvFactorCombineResDto> combineAlvRvMapRes, Map<String, ALVMasterModel> alvRvMapRes,
            Map<String, List<FactorMasterModel>> factorMapRes,
            Organisation organisation, Map<String, List<AtomicDouble>> totalRvOfYearWiseUnitWiseExemption) {
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = null;
        LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " callBrmsOfTaxRateForIndependentTaxes() method");
        if (rateMap != null && !rateMap.isEmpty()) {
            rateMap.forEach((yearKey, schMap) -> {
                AlvFactorCombineResDto resDto = combineAlvRvMapRes.get(yearKey);
                schMap.forEach((schDate, rateModelList) -> {
                    if (yearKey.contains(MainetConstants.operator.UNDER_SCORE)) {
                        setunitLevelAlvRv(alvRvMapRes, rateModelList, factorMapRes, null, null, resDto, yearKey);// for unit level
                    } else {
                        rateModelList.forEach(rateModel -> {
                            if (resDto.getArv() != null) {
                                rateModel.setArv(resDto.getArv().doubleValue());
                            }
                            if (resDto.getResidentialRv() != null) {
                            	rateModel.setTotalResidentialRv(resDto.getResidentialRv().doubleValue());
                            }
                            if (resDto.getRv() != null) {
                                rateModel.setRv(resDto.getRv().doubleValue());
                            }
                            if (resDto.getCv() != null) {
                                rateModel.setCv(resDto.getCv().doubleValue());
                            }
                            if (resDto.getFactor() != null) {
                                rateModel.setFactor(resDto.getFactor());
                            }
                            if (resDto.getTaxableArea() != null) {
                                rateModel.setTaxableArea(resDto.getTaxableArea().doubleValue());
                            }
                            /*
                             * task: 30262 calculate property tax based on totalRV as required for rajnandgaon and ambikapur ULB
                             * changes made by Apeksha
                             */
                            if (resDto.getArv() != null) {
                                rateModel.setTotalArv(resDto.getArv().doubleValue());
                            }
                            if (resDto.getRv() != null) {
                                rateModel.setTotalRv(resDto.getRv().doubleValue());
                            }
                            if (resDto.getCv() != null) {
                                rateModel.setTotalCv(resDto.getCv().doubleValue());
                            }

                        });
                    }

                });

            });

            rateMap.forEach((yearKey, schMap) -> {
                String[] keyObj = yearKey.split(MainetConstants.operator.UNDER_SCORE);
                List<AtomicDouble> list = totalRvOfYearWiseUnitWiseExemption.get(keyObj[0]);
                if (CollectionUtils.isNotEmpty(list)) {
                    schMap.forEach((schDate, rateModelList) -> {
                        rateModelList.forEach(rate -> {
                            rate.setTotalRvOfExemption1(list.get(0).doubleValue());
                            rate.setTotalRvOfExemption2(list.get(1).doubleValue());
                            rate.setTotalRvOfExemption3(list.get(2).doubleValue());
                        });
                    });
                }

            });

            WSRequestDTO request = new WSRequestDTO();
            request.setDataModel(rateMap);
            WSResponseDTO taxResult = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_RATE);
            Map<String, Map<Date, List<PropertyRateMasterModel>>> taxMapRes = castRequestToTaxModel(taxResult);

            if (taxResult != null
                    && MainetConstants.WebServiceStatus.SUCCESS
                            .equalsIgnoreCase(taxResult.getWsStatus())) {
                // calculateDependentTaxes(taxMapRes, taxMapWithTaxCode, organisation);
            	LOGGER.info("End--> " + this.getClass().getSimpleName() + " callBrmsOfTaxRateForIndependentTaxes() method");
                return taxMapRes;
            }
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " callBrmsOfTaxRateForIndependentTaxes() method");
        return null;
    }

    private Map<String, ALVMasterModel> callBrmsOfAlvRv(Map<String, ALVMasterModel> alvRvMap,
            Map<String, List<FactorMasterModel>> factorMap,
            Map<String, PropertyTaxDataModel> cvResult) {
        LOGGER.info(
                "********************************************* BRMS Call for ALV RV *****************************************************");
        AtomicDouble multiFactorALV = new AtomicDouble(1d);
        AtomicDouble multiFactorTax = new AtomicDouble(1d);
        Map<String, String> factor = new HashMap<>(0);
        alvRvMap.forEach((key, value) -> {
            List<FactorMasterModel> factorList = factorMap.get(key);
            if (cvResult != null) {
                PropertyTaxDataModel cvRate = cvResult.get(key);
                if (cvRate != null) {
                    value.setCv(cvRate.getSddrRate());
                }
            }
            multiFactorALV.set(1d);
            multiFactorTax.set(1d);
            if (factorList != null && !factorList.isEmpty()) {
                factorList.forEach(fact -> {
                    if (fact.getFactApplicable().equals(MainetConstants.Property.ASSESS) ||
                            fact.getFactApplicable().equals(MainetConstants.Property.ASS_AND_TAX)) {
                        multiFactorALV.set(multiFactorALV.doubleValue() * fact.getFlatRate());
                        setFactorValueForALVModel(factor, fact);
                    }
                    if (fact.getFactApplicable().equals(MainetConstants.Property.TAX) ||
                            fact.getFactApplicable().equals(MainetConstants.Property.ASS_AND_TAX)) {
                        multiFactorTax.set(multiFactorTax.doubleValue() * fact.getFlatRate());
                        setFactorValueForALVModel(factor, fact);
                    }
                    value.setFactor(factor);
                });
            }
            value.setMultiplicationFactor(multiFactorALV.get());
            value.setMultiplicationFactorTax(multiFactorTax.get());

        });

        WSRequestDTO alvRvRequest = new WSRequestDTO();
        alvRvRequest.setDataModel(alvRvMap);
        WSResponseDTO alvRvResult = RestClient.callBRMS(alvRvRequest, ServiceEndpoints.Property.PROP_ALV);
        return castRequestToAlvCvModel(alvRvResult);
    }

    private void setFactorValueForALVModel(Map<String, String> factor, FactorMasterModel fact) {
        String factValue = factor.get(fact.getFactor());
        if (factValue == null) {
            factor.put(fact.getFactor(), fact.getFactorValue());
        } else {
            factor.put(fact.getFactor(), factValue + "," + fact.getFactorValue());
        }
    }

    private void setCalculationMethod(final Organisation organisation, AtomicReference<String> calculationType) {
        List<LookUp> calculationValue = CommonMasterUtility.getLookUps(MainetConstants.Property.propPref.ASS, organisation);
        if ((calculationValue != null) && !calculationValue.isEmpty()) {
            for (final LookUp lookUp : calculationValue) {
                if (PrefixConstants.IsLookUp.STATUS.YES.equals(lookUp.getDefaultVal())) {
                    calculationType.set(lookUp.getLookUpCode());
                    break;
                }
            }
        }
    }

    /*
     * private Map<Date, List<PropertyRateMasterModel>> calculatePropertyLevelCharges( Map<Date, List<PropertyRateMasterModel>>
     * rateMap, Organisation organisation) { rateMap.forEach((key, value) -> { value.forEach(tax -> { value.stream().filter(oldTax
     * -> oldTax.getTaxCode().equals(tax.getParentTaxCode())).forEach(eachTax -> { tax.setParentTaxValue(eachTax.getTaxValue());
     * }); tax.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
     * tax.setUsageSubtype1(MainetConstants.CommonConstants.NA); // tax.setParentTaxCode(MainetConstants.CommonConstants.NA); });
     * }); WSRequestDTO propLevelTaxRequest = new WSRequestDTO(); propLevelTaxRequest.setDataModel(rateMap); WSResponseDTO
     * propLevelTaxResult = RestClient.callBRMS(propLevelTaxRequest, ServiceEndpoints.Property.PROP_LEVEL_RATE); return
     * castRequestToTaxModelPropertyLevel(propLevelTaxResult); }
     */

    private Map<Date, List<PropertyRateMasterModel>> callBrmsOfTaxRateForDependentTaxes(
            Map<Date, List<PropertyRateMasterModel>> independentTaxMap,
            Map<String, Map<Date, List<PropertyRateMasterModel>>> unitWiseinDependentTaxMap,
            Map<String, Map<Date, List<PropertyRateMasterModel>>> dependentTaxMap, Map<String, TbTaxMas> taxMapWithTaxCode,
            Map<String, AlvFactorCombineResDto> resDtoMap, Map<String, ALVMasterModel> alvRvMapRes,
            Map<String, List<FactorMasterModel>> factorMapRes, Organisation organisation,
            Map<String, List<AtomicDouble>> totalRvOfYearWiseUnitWiseExemption, FinancialYear currentFinYear,
            int arreatBillPtCount, Date penaltyDateExtended, FinancialYear previousFinYear,
            TbServiceReceiptMasEntity manualReceiptMas, String skipFirstYearPenalty, FinancialYear firstYearAssesment,
            Date manualDate, Map<String, Long> taxCodeWithTaxDescId) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " callBrmsOfTaxRateForDependentTaxes() method");
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = null;
        AtomicInteger aarearPtCount = new AtomicInteger(0);
        AtomicLong daysExtendPenallty = new AtomicLong(0);
        LookUp arrearCountConf = null;
        AtomicBoolean attearCount = new AtomicBoolean(false);
		try {
			arrearCountConf = CommonMasterUtility.getValueFromPrefixLookUp("CPY", "ARC", organisation);
		}catch (Exception exception) {
			
		}
		if(arrearCountConf != null && StringUtils.equals(MainetConstants.FlagY, arrearCountConf.getOtherField())) {
			attearCount.set(true);
        }
        LookUp daysExtdPenaltyLookUp = null;
        try {
            daysExtdPenaltyLookUp = CommonMasterUtility.getValueFromPrefixLookUp("DEP", "DEC", organisation);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for DEC(DEP)");
        }
        if (daysExtdPenaltyLookUp != null && daysExtdPenaltyLookUp.getOtherField() != null) {
            daysExtendPenallty.addAndGet(Long.valueOf(daysExtdPenaltyLookUp.getOtherField()));
        }
        LookUp ptLookup = CommonMasterUtility.getHieLookupByLookupCode(PrefixConstants.LookUpPrefix.PRT,
                PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());// sub category of demand(property tax)
        dependentTaxMap.forEach((yearKey, schMap) -> {
            AlvFactorCombineResDto resDto = resDtoMap.get(yearKey);
            // Map<Date, List<PropertyRateMasterModel>> scheinDependentTaxMap=unitWiseinDependentTaxMap.get(yearKey);
            schMap.forEach((key, value) -> {
                if (yearKey.contains(MainetConstants.operator.UNDER_SCORE)) {// for ex. 31_1(year_unit)
                    setunitLevelAlvRv(alvRvMapRes, value, factorMapRes, null, null, resDto, yearKey);// unit level
                } else {/// Property level alv rv
                    value.forEach(tax -> {
                        if (resDto != null) {
                            if (resDto.getArv() != null) {
                                tax.setArv(resDto.getArv().doubleValue());
                            }
                            if (resDto.getResidentialRv() != null) {
                            	tax.setTotalResidentialRv(resDto.getResidentialRv().doubleValue());
                            }
                            if (resDto.getRv() != null) {
                                tax.setRv(resDto.getRv().doubleValue());
                            }
                            if (resDto.getCv() != null) {
                                tax.setCv(resDto.getCv().doubleValue());
                            }
                            if (resDto.getFactor() != null) {
                                tax.setFactor(resDto.getFactor());
                            }
                            /*
                             * task: 30262 calculate property tax based on totalRV as required for rajnandgaon and ambikapur ULB
                             * changes made by Apeksha
                             */
                            if (resDto.getArv() != null) {
                                tax.setTotalArv(resDto.getArv().doubleValue());
                            }
                            if (resDto.getRv() != null) {
                                tax.setTotalRv(resDto.getRv().doubleValue());
                            }
                            if (resDto.getCv() != null) {
                                tax.setTotalCv(resDto.getCv().doubleValue());
                            }

                        }
                    });
                }
                setParentTaxValueForDependentTaxes(unitWiseinDependentTaxMap, value, key, yearKey, ptLookup,
                        currentFinYear, aarearPtCount, organisation, daysExtendPenallty, penaltyDateExtended, previousFinYear,
                        manualReceiptMas, skipFirstYearPenalty, firstYearAssesment, manualDate,taxCodeWithTaxDescId,arreatBillPtCount);
            });
        });
        aarearPtCount.addAndGet(arreatBillPtCount);
        dependentTaxMap.forEach((yearKey, schMap) -> {
            String[] keyObj = yearKey.split(MainetConstants.operator.UNDER_SCORE);
            List<AtomicDouble> list = totalRvOfYearWiseUnitWiseExemption.get(keyObj[0]);
            if (CollectionUtils.isNotEmpty(list)) {
                schMap.forEach((schDate, rateModelList) -> {
                    rateModelList.forEach(rate -> {
                        rate.setTotalRvOfExemption1(list.get(0).doubleValue());
                        rate.setTotalRvOfExemption2(list.get(1).doubleValue());
                        rate.setTotalRvOfExemption3(list.get(2).doubleValue());
                        if(!attearCount.get()) {
                        	rate.setArrearsPTCount(aarearPtCount.intValue());
                        }
                    });
                });
            }

        });

        WSRequestDTO propLevelTaxRequest = new WSRequestDTO();
        propLevelTaxRequest.setDataModel(dependentTaxMap);
        WSResponseDTO propLevelTaxResult = RestClient.callBRMS(propLevelTaxRequest, ServiceEndpoints.Property.PROP_RATE);

        Map<String, Map<Date, List<PropertyRateMasterModel>>> taxMapRes = castRequestToTaxModel(propLevelTaxResult);

        if (propLevelTaxResult != null
                && MainetConstants.WebServiceStatus.SUCCESS
                        .equalsIgnoreCase(propLevelTaxResult.getWsStatus())) {
            // calculateDependentTaxes(taxMapRes, taxMapWithTaxCode, organisation);
            schWiseMap = prepareScheduleWiseChargeMap(taxMapRes);
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " callBrmsOfTaxRateForDependentTaxes() method");
        return schWiseMap;
    }

    private void setParentTaxValueForDependentTaxes(
            Map<String, Map<Date, List<PropertyRateMasterModel>>> unitWiseinDependentTaxMap,
            List<PropertyRateMasterModel> value, Date schDate, String yearKeyOfDepentTax, LookUp PTlookup,
            FinancialYear currentFinYear, AtomicInteger aarearPtCount, Organisation organisation,
            AtomicLong daysExtendPenallty, Date penaltyDateExtended, FinancialYear previousFinYear,
            TbServiceReceiptMasEntity manualReceiptMas, String skipFirstYearPenalty, FinancialYear firstYearAssesment,
            Date manualDate, Map<String, Long> taxCodeWithTaxDescId,int arreatBillPtCount) {
        AtomicBoolean ptChecked = new AtomicBoolean(false);
        LookUp arrearCountConf = null;
        AtomicBoolean attearCount = new AtomicBoolean(false);
		try {
			arrearCountConf = CommonMasterUtility.getValueFromPrefixLookUp("CPY", "ARC", organisation);
		}catch (Exception exception) {
			
		}
		if(arrearCountConf != null && StringUtils.equals(MainetConstants.FlagY, arrearCountConf.getOtherField())) {
			attearCount.set(true);
        }
        value.forEach(tax -> {
            LOGGER.info("tax " + tax.getTaxCode() + "tax cal level " + tax.getTaxCalculationLevel() + " P tax "
                    + tax.getParentTaxCode() + " P tax " + tax.getParentTaxCode());
            unitWiseinDependentTaxMap.forEach((yearKey, scheInDependentTaxMap) -> {
                scheInDependentTaxMap.forEach((yearDate, indepentListUnitWise) -> {

                    if (indepentListUnitWise != null && !indepentListUnitWise.isEmpty()
                            && Utility.comapreDates(yearDate, schDate)) {
                        indepentListUnitWise.stream()
                                .filter(oldTax -> (oldTax.getTaxCode().equals(tax.getParentTaxCode())
                                        && ((yearKeyOfDepentTax.contains(MainetConstants.operator.UNDER_SCORE)
                                                && yearKey.equals(yearKeyOfDepentTax)) ||
                                                !yearKeyOfDepentTax.contains(MainetConstants.operator.UNDER_SCORE))))
                                .forEach(eachTax -> {
                                    LOGGER.info(
                                            "Tax " + eachTax.getTaxCode() + "tax cal level " + eachTax.getTaxCalculationLevel()
                                                    + "tax value " + eachTax.getTaxValue()

                                                    + "tax sub cat " + eachTax.getTaxSubCategory());

                                    tax.setParentTaxValue(eachTax.getTaxValue() + tax.getParentTaxValue());
                                    if(attearCount.get()) {
                                    	if(!Utility.comapreDates(schDate, firstYearAssesment.getFaFromDate())) {
                                        	tax.setArrearsPTCount(1);
                                        }else {
                                        	tax.setArrearsPTCount(arreatBillPtCount);
                                        }
                                    }
                                });
                    }
                    
                    if (indepentListUnitWise != null && !indepentListUnitWise.isEmpty()
                            && Utility.comapreDates(yearDate, schDate)) {
                        indepentListUnitWise.stream()
                                .filter(oldTax -> (((yearKeyOfDepentTax.contains(MainetConstants.operator.UNDER_SCORE)
                                                && yearKey.equals(yearKeyOfDepentTax)) ||
                                                !yearKeyOfDepentTax.contains(MainetConstants.operator.UNDER_SCORE))))
								.forEach(eachTax -> {
									LOGGER.info("Tax " + eachTax.getTaxCode() + "tax cal level "
											+ eachTax.getTaxCalculationLevel() + "tax value " + eachTax.getTaxValue()

											+ "tax sub cat " + eachTax.getTaxSubCategory());
									String lookUpCode = CommonMasterUtility
											.getNonHierarchicalLookUpObject(taxCodeWithTaxDescId.get(eachTax.getTaxCode()), organisation)
											.getLookUpCode();
									if (StringUtils.equals(lookUpCode, "PTR") || StringUtils.equals(lookUpCode, "RTX")
											|| StringUtils.equals(lookUpCode, "CTX")
											|| StringUtils.equals(lookUpCode, "WSBT")
											|| StringUtils.equals(lookUpCode, "CBT")
											|| StringUtils.equals(lookUpCode, "KET")) {
										tax.setSumOfPercentageOfTaxes(eachTax.getPercentageRate() + tax.getSumOfPercentageOfTaxes());
									}
									
									if (StringUtils.equals(lookUpCode, "TRT")) {
										tax.setTreeTax(eachTax.getTaxValue());
									} else if (StringUtils.equals(lookUpCode, "EGC")) {
										tax.setEmployeeCess(eachTax.getTaxValue());
									} else if (StringUtils.equals(lookUpCode, "EDUR")) {
										tax.setEducationCess(eachTax.getTaxValue());
									}
								});
                    }
                    
                    // Added by Apurva as setting total property tax
                    // we are setting total Property tax for all dependent tax
                    // in case when sub cat of demand is missing for property tax then else part will execute
                    if (indepentListUnitWise != null && !indepentListUnitWise.isEmpty()
                            && Utility.comapreDates(yearDate, schDate)) {

                        LOGGER.info("Tax lookup " + PTlookup.getDescLangFirst());

                        for (PropertyRateMasterModel oldTax : indepentListUnitWise) {
                            if (PTlookup != null && oldTax.getTaxSubCategory().equals(PTlookup.getDescLangFirst())) {
                                tax.setTotalPropertyTax(oldTax.getTaxValue() + tax.getTotalPropertyTax());
                                break;
                            } else if (oldTax.getTaxCode().equals(tax.getParentTaxCode())) {
                                tax.setTotalPropertyTax(oldTax.getTaxValue() + tax.getTotalPropertyTax());
                                break;
                            }
                        }
                      
                        /*
                         * //we are setting total Property tax for all dependent tax //in case when sub cat of demand is missing
                         * for property tax then else part will execute if(PTlookup!=null && PTlookup.getDescLangFirst()!=null) {
                         * indepentListUnitWise.stream() .filter(oldTax ->
                         * (oldTax.getTaxSubCategory().equals(PTlookup.getDescLangFirst()))) .forEach(eachTax -> {
                         * tax.setTotalPropertyTax(eachTax.getTaxValue() + tax.getTotalPropertyTax()); }); }else { //parent tax is
                         * property tax indepentListUnitWise.stream() .filter(oldTax ->
                         * (oldTax.getTaxCode().equals(tax.getParentTaxCode()))) .forEach(eachTax -> {
                         * tax.setTotalPropertyTax(eachTax.getTaxValue() + tax.getTotalPropertyTax()); }); }
                         */
                    }

                    // This code is to get count of PT > 0 of asll years added by srikanth
                    if (!ptChecked.get() && !yearKeyOfDepentTax.contains(MainetConstants.operator.UNDER_SCORE)) {
                        Date currDate = new Date();
                        if (manualReceiptMas != null && manualReceiptMas.getRmDate() != null) {
                            currDate = manualReceiptMas.getRmDate();
                        } else if (manualDate != null) {
                            currDate = manualDate;
                        }
                        for (PropertyRateMasterModel oldTax : indepentListUnitWise) {
                            if (oldTax.getTaxCode().equals(tax.getParentTaxCode())) {
                                TbTaxMas taxDetail = tbTaxMasService.findTaxDetails(organisation.getOrgid(), oldTax.getTaxCode());
                                String lookUpCode = CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(taxDetail.getTaxDescId(), organisation).getLookUpCode();
                                String[] keyObj = yearKey.split(MainetConstants.operator.UNDER_SCORE);
                                
                                if ((!attearCount.get()) && (((!Utility.comapreDates(schDate, previousFinYear.getFaFromDate()))
                                        || (penaltyDateExtended != null
                                        && Utility.compareDate(penaltyDateExtended, currDate)
                                        && !Utility.comapreDates(penaltyDateExtended, currDate)))
                                && lookUpCode.equals("PTR") && !ptChecked.get()
                                && keyObj[0].contains(yearKeyOfDepentTax)
                                && !(schDate.compareTo(currentFinYear.getFaFromDate()) >= 0
                                        && schDate.compareTo(currentFinYear.getFaToDate()) <= 0)
                                && !(StringUtils.isNotBlank(skipFirstYearPenalty)
                                        && StringUtils.equals(skipFirstYearPenalty, MainetConstants.FlagY)
                                        && (schDate.compareTo(firstYearAssesment.getFaFromDate()) >= 0
                                                && schDate.compareTo(firstYearAssesment.getFaToDate()) <= 0))
                                && oldTax.getTaxValue() > 0)) {
                                    aarearPtCount.getAndIncrement();
                                    ptChecked.set(true);
                                }
                            }
                        }
                    }
                });
            });
            LOGGER.info("^^^^^^^^^^^^^^^^^^Parent tax code : " + tax.getParentTaxCode() + "^^^^^^^^^^^^^^^^^^ Tax code : "
                    + tax.getTaxCode() +
                    "^^^^^^^^^^^^^^^^^^ Tax Calculation level : " + tax.getTaxCalculationLevel()
                    + "^^^^^^^^^^^^^^^^^^ Tax value : " + tax.getTaxValue() +
                    "^^^^^^^^^^^^^^^^^^ Flat rate : " + tax.getFlatRate() + "^^^^^^^^^^^^^^^^^^ Parcentage Rate : "
                    + tax.getPercentageRate() +
                    "^^^^^^^^^^^^^^^^^^ Rule ID : " + tax.getRuleId());

        });
    }

    /*
     * Not required as mixed type changes has done
     */
    /*
     * private void calculateDependentTaxes(Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap, Map<String, TbTaxMas>
     * taxMapWithTaxCode, Organisation organisation) { rateMap.forEach((key, schMap) -> { schMap.forEach((schDate, taxList) -> {
     * taxList.forEach(tax -> { if (tax.getTaxValue() <= 0) { taxList.stream().filter(oldTax ->
     * oldTax.getTaxCode().equals(tax.getParentTaxCode())).forEach(eachTax -> { TbTaxMas taxMas =
     * taxMapWithTaxCode.get(tax.getTaxCode()); LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
     * Long.valueOf(taxMas.getTaxMethod()), organisation); if
     * (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE)) { tax.setTaxValue(eachTax.getTaxValue() *
     * (tax.getPercentageRate() / 100)); } }); } }); }); }); }
     */

    private Map<Date, List<PropertyRateMasterModel>> prepareScheduleWiseChargeMap(
            Map<String, Map<Date, List<PropertyRateMasterModel>>> rateMap) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " prepareScheduleWiseChargeMap() method");
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
        rateMap.forEach((key, schMap) -> {
            schMap.forEach((schDate, taxList) -> {
                List<PropertyRateMasterModel> addTaxList = schWiseMap.get(schDate);
                if (addTaxList != null) {// if list is present for particular schedule

                    taxList.forEach(eachTax -> {
                        boolean isexist = addTaxList.stream()
                                .filter(tax -> (tax.getTaxCode().equals(eachTax.getTaxCode())
                                        && tax.getTaxCalculationLevel().equals(eachTax.getTaxCalculationLevel())))
                                .findFirst()
                                .isPresent();
                        if (isexist) {
                            addTaxList.stream()
                                    .filter(chargeDetailDtoFromList -> chargeDetailDtoFromList.getTaxCode()
                                            .equals(eachTax.getTaxCode()))
                                    .forEach(tax -> {
                                        if (tax.getTaxCalculationLevel().equals(eachTax.getTaxCalculationLevel())) {
                                            // missing condition Defect #13810
                                            tax.setTaxValue(tax.getTaxValue() + eachTax.getTaxValue());
                                            tax.setRuleId(tax.getRuleId() + MainetConstants.operator.COMMA + eachTax.getRuleId());
                                        }
                                    });
                        } else {
                            addTaxList.add(eachTax);// for new tax
                        }

                    });
                } else {
                    List<PropertyRateMasterModel> addTaxListNew = new ArrayList<>();// create list only once for each schedule
                    taxList.forEach(tax -> {
                        addTaxListNew.add(tax);
                    });
                    schWiseMap.put(schDate, addTaxListNew);
                }

            });
        });
        LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " prepareScheduleWiseChargeMap() method");
        return schWiseMap;
    }

    private Map<Date, List<BillPresentAndCalculationDto>> prepareScheduleWisePreCalMap(
            Map<Date, List<PropertyRateMasterModel>> schMap, Organisation organisation,
            Map<Long, BillDisplayDto> demandLevelRebate) {
       LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " prepareScheduleWisePreCalMap() method");
        Map<Date, List<BillPresentAndCalculationDto>> schWiseMap = new TreeMap<>();
        Map<String, TbTaxMas> taxMap = new HashMap<>();
        schMap.forEach((schDate, taxList) -> {
            List<BillPresentAndCalculationDto> dtoList = new ArrayList<>();
            taxList.forEach(tax -> {
                TbTaxMas tbTax = taxMap.get(tax.getTaxCode());
                if (tbTax == null) {
                		 tbTax = tbTaxMasService.findTaxDetails(tax.getOrgId(), tax.getTaxCode());
                         taxMap.put(tbTax.getTaxCode(), tbTax);
                }
                BillPresentAndCalculationDto dto = new BillPresentAndCalculationDto();
                setBillCalDto(tax.getTaxValue(), tbTax, dto, tax, organisation, dtoList, demandLevelRebate);
            });
            schWiseMap.put(schDate, dtoList);
            dtoList.sort(Comparator.comparing(BillPresentAndCalculationDto::getTaxSequenceId));// Sorting List by collection
                                                                                               // sequence
        });
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " prepareScheduleWisePreCalMap() method");
        return schWiseMap;
    }

    private void setAlvRvInDto(Map<String, ALVMasterModel> alvRvMap, ProvisionalAssesmentMstDto assDto) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setAlvRvInDto() method");
        assDto.getProvisionalAssesmentDetailDtoList().forEach(unit -> {
            LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                    unit.getAssdConstruType(), assDto.getOrgId(), MainetConstants.Property.propPref.CSC);

            LOGGER.info("^^^^^^^^^^^^ Usage Type:" + unit.getProAssdUsagetypeDesc() + "^^^^^^^^^^^^^Taxable Area:"
                    + unit.getAssdBuildupArea() +
                    "^^^^^^^^^^^^Standard Rate: " + unit.getAssdStdRate() + "^^^^^^^^^^^^^RV :" + unit.getAssdRv()
                    + "^^^^^^^^^^^ARV : " + unit.getAssdAlv() +
                    "^^^^^^^^^Rule ID :" + unit.getRuleId());
            String finAndUnitId = unit.getFaYearId() + MainetConstants.operator.UNDER_SCORE + unit.getAssdUnitNo();
            ALVMasterModel model = alvRvMap.get(finAndUnitId);
            // D#18545
            unit.setErrorMsg(model.getErrorMsg());
            Long arv = Math.round(model.getArv());
            unit.setAssdAlv(arv.doubleValue());
            Long rv = Math.round(model.getRv());
            unit.setAssdRv(rv.doubleValue());
            unit.setAssdCv(Utility.round(model.getCv(), 2));

            unit.setAssdStdRate(model.getArvStdRate());

            if (model.getAdditionalALVRate() >= 0.0d && !StringUtils.isEmpty(model.getFactor1())
                    && !model.getFactor1().equals("NA")) { // task : 30262 for showing standard rate on ui based on
                                                           // additionalALVRate
                                                           // and arvStdRate By apeksha
                unit.setAssdStdRate(model.getAdditionalALVRate());
            }

            /*
             * if (model.getAdditionalALVRate() >= 0.0d && model.getPropertyFactor() >= 0.0d) { // task : 30262 for showing
             * standard rate on ui based on additionalALVRate and arvStdRate By apeksha
             * unit.setAssdStdRate(model.getPropertyFactor()); }
             */
            unit.setRuleId(model.getRuleId());

            // Changes suggested by rajesh sir, Setting maintainace charges: it is calculated based on 10 % of ARV & in case of
            // land it is set as 0 and Again suggested by Rajesh sir then i have changed from Lnad to VaccantLand it is set as 0
            // by sharvan(defect 38734)
            /*
             * if (lookup.getLookUpCode() != null && lookup.getLookUpCode().equals(MainetConstants.Property.LAND)) {
             * unit.setMaintainceCharge(MainetConstants.Property.MAINTAINCE_CHARGE_ZERO); }
             */
            
			// 110144 By Arun - Calculate Mainetenance charge for tenated property
			String lookUpOtherVal = null;
			try {
				Organisation org = orgService.getOrganisationById(assDto.getOrgId());
				lookUpOtherVal = CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.CMC, MainetConstants.ENV, org).getOtherField();
			} catch (Exception e) {
				LOGGER.error("No prefix found" + e);
			}
			if (StringUtils.isNotEmpty(lookUpOtherVal) && MainetConstants.FlagY.equals(lookUpOtherVal)
					&& ((unit.getAssdAlv() - unit.getAssdRv()) >= 0)) {
				unit.setMaintainceCharge(unit.getAssdAlv() - unit.getAssdRv());
			} else {
				unit.setMaintainceCharge(Utility.round((model.getArv() * model.getMaintenancePercentage()), 2));
			}          		           

        });
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setAlvRvInDto() method");
    }

    private void setBillCalDto(Double charges, TbTaxMas tax,
            BillPresentAndCalculationDto billCalDto, PropertyRateMasterModel rateTaxModel, Organisation organisation,
            List<BillPresentAndCalculationDto> dtoList, Map<Long, BillDisplayDto> demandLevelRebate) {
        List<String> taxCat = Arrays.asList(PrefixConstants.TAX_CATEGORY.INTERST, PrefixConstants.TAX_CATEGORY.REBATE,
                PrefixConstants.TAX_CATEGORY.EXEMPTION);
        final String taxCode = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), organisation)
                .getLookUpCode();
        double baseRate = 0d;
        if (charges > 0
                || (taxCat.contains(taxCode) && (rateTaxModel.getFlatRate() > 0 || rateTaxModel.getPercentageRate() > 0))) {
            LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    Long.valueOf(tax.getTaxMethod()), organisation);
            if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE)) {
                baseRate = rateTaxModel.getPercentageRate() / 100;
            } else if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.FLAT)) {
                baseRate = rateTaxModel.getFlatRate();
            }
            billCalDto.setTaxId(tax.getTaxId());
            if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
            	if(taxCat.contains(taxCode)) {
            		billCalDto.setChargeAmount(charges);
            	}else {
            		billCalDto.setChargeAmount(Math.ceil(charges));
            	}
            }else {
            	billCalDto.setChargeAmount(Math.round(charges));
            }
            billCalDto.setTaxCode(tax.getTaxCode());
            billCalDto.setTaxId(tax.getTaxId());
            billCalDto.setChargeDescEng(tax.getTaxDesc());
            billCalDto.setTaxCategoryId(tax.getTaxCategory1());
            billCalDto.setTaxSubCategoryId(tax.getTaxCategory2());
            billCalDto.setDisplaySeq(tax.getTaxDisplaySeq());
            billCalDto.setTaxSequenceId(tax.getCollSeq());
            billCalDto.setParentCode(tax.getParentCode());
            billCalDto.setTaxMethod(tax.getTaxMethod());
            billCalDto.setRate(baseRate);
            billCalDto.setRuleId(rateTaxModel.getRuleId());
            billCalDto.setPercentageRate(rateTaxModel.getPercentageRate());
            dtoList.add(billCalDto);

            Long count = dtoList.stream().filter(o -> o.getTaxCode().equals(tax.getTaxCode())).count();
            if (count > 1) {
                throw new FrameworkException("configuration issue same tax at property and unit level in BRMS sheet");
            }
            setDemandLevelRebateMap(charges, tax, demandLevelRebate, taxCode);
        }

    }

    private void setDemandLevelRebateMap(Double charges, TbTaxMas tax, Map<Long, BillDisplayDto> demandLevelRebate,
            final String taxCode) {
        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE) || taxCode.equals(PrefixConstants.TAX_CATEGORY.EXEMPTION)) {
            double charge = Math.round(charges);
            if (demandLevelRebate.containsKey(tax.getTaxId())) {
                BillDisplayDto existDto = demandLevelRebate.get(tax.getTaxId());
                existDto.setTotalTaxAmt(BigDecimal.valueOf(existDto.getTotalTaxAmt().doubleValue() + charge));
                existDto.setCurrentYearTaxAmt(BigDecimal.valueOf(existDto.getCurrentYearTaxAmt().doubleValue() + charge));
            } else {
                BillDisplayDto dto = new BillDisplayDto();
                dto.setTaxId(tax.getTaxId());
                dto.setTotalTaxAmt(BigDecimal.valueOf(charge));
                dto.setCurrentYearTaxAmt(BigDecimal.valueOf(charge));
                dto.setTaxDesc(tax.getTaxDesc());
                dto.setTaxCategoryId(tax.getTaxCategory1());
                dto.setDisplaySeq(tax.getTaxDisplaySeq());
                dto.setParentTaxId(tax.getParentCode());
                demandLevelRebate.put(tax.getTaxId(), dto);
            }
        }
    }

    private PropertyRateMasterModel populateChargeModel(
            PropertyRateMasterModel propertyRateMasterModel, Organisation organisation,
            String calculationMethod, List<String> wardZone, Date scheduleStartDate, Date scheduleEndDate,
            Map<Long, TbTaxMas> taxMap, TbTaxMas tax, List<ProvisionalAssesmentDetailDto> unitDetails, boolean isPropertyMixed,
            ProvisionalAssesmentMstDto sdto, int lastAssesmentYear, int currentYear,List<String> propertyType) {
        ProvisionalAssesmentDetailDto unit = new ProvisionalAssesmentDetailDto();
		if (CollectionUtils.isNotEmpty(unitDetails)) {
			unit = unitDetails.get(0);
		}
        propertyRateMasterModel.setOrgId(organisation.getOrgid());
        propertyRateMasterModel.setRateStartDate(scheduleStartDate.getTime());
        propertyRateMasterModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                organisation).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            propertyRateMasterModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    organisation).getDescLangFirst());
        }
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        propertyRateMasterModel.setTaxCode(tax.getTaxCode());
        propertyRateMasterModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());

        if (tax.getParentCode() != null && tax.getParentCode() > 0) {
            TbTaxMas tbTax = taxMap.get(tax.getParentCode());
            if (tbTax == null) {
                tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), organisation.getOrgid());
            }
            propertyRateMasterModel.setParentTaxCode(tbTax.getTaxCode());
        }
        propertyRateMasterModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
        propertyRateMasterModel.setWardZoneLevel1(wardZone.get(0));
        propertyRateMasterModel.setWardZoneLevel3(wardZone.get(2));
        propertyRateMasterModel.setWardZoneLevel2(wardZone.get(1));
        propertyRateMasterModel.setWardZoneLevel4(wardZone.get(3));
        propertyRateMasterModel.setWardZoneLevel5(wardZone.get(4));
        if (!isPropertyMixed) {
            if (unit.getAssdUsagetype1() != null) {
                propertyRateMasterModel
                        .setUsageSubtype1(CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype1(), organisation)
                                .getDescLangFirst());
            }
        }
        LookUp ownerLookup = null;
        if (sdto.getAssOwnerType() != null) {
            ownerLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    sdto.getAssOwnerType(), organisation);
        }
        if (ownerLookup != null) {
            propertyRateMasterModel.setOwnershipType(ownerLookup.getDescLangFirst());
        }
        if (sdto.getAssAcqDate() != null) {
            propertyRateMasterModel.setYearOfAcquisition(sdto.getAssAcqDate().getTime());
        }

        if (sdto.getAssPlotArea() != null) {
            propertyRateMasterModel.setPlotArea(sdto.getAssPlotArea());
        }
        // This block is added to set last assesment year and current assesment year

        propertyRateMasterModel.setLastAssessmentYear(lastAssesmentYear);
        propertyRateMasterModel.setCurrentAssessmentYear(currentYear);
        propertyRateMasterModel.setIsGroup(sdto.getIsGroup());
        if (CollectionUtils.isNotEmpty(propertyType)) {
        propertyRateMasterModel.setPropertyTypeLevel1(propertyType.get(0));
        propertyRateMasterModel.setPropertyTypeLevel2(propertyType.get(1));
        propertyRateMasterModel.setPropertyTypeLevel3(propertyType.get(2));
        propertyRateMasterModel.setPropertyTypeLevel4(propertyType.get(3));
        propertyRateMasterModel.setPropertyTypeLevel5(propertyType.get(4));
        }
        if (sdto.getIsGroup() != null) {
        	propertyRateMasterModel.setIsGroupProperty(sdto.getIsGroup());
		}
        return propertyRateMasterModel;
    }

    public double getBillingPeriod(Date scheduleStartDate, Date scheduleEndDate,
            Date consDate) {
        Date comapareDate = (consDate.after(scheduleStartDate) ? consDate
                : scheduleStartDate);
        LocalDate frmDateLoc = comapareDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDateLoc = scheduleEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(frmDateLoc, toDateLoc) + 1;
        if (toDateLoc.isLeapYear()) {
            return (days / 366.0);
        } else {
            return (days / 365.0);
        }
    }

    private void setUsageTypeAndPropertyType(ProvisionalAssesmentDetailDto unit, Organisation organisation,
            List<String> usageType, List<String> propertyType) {
        if (null != unit.getAssdUsagetype1()) {
            usageType.add(CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype1(), organisation)
                    .getDescLangFirst());
        } else {
            usageType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdUsagetype2()) {
            usageType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype2(), organisation).getDescLangFirst());
        } else {
            usageType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdUsagetype3()) {
            usageType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype3(), organisation).getDescLangFirst());
        } else {
            usageType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdUsagetype4()) {
            usageType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype4(), organisation).getDescLangFirst());
        } else {
            usageType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdUsagetype5()) {
            usageType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdUsagetype5(), organisation).getDescLangFirst());
        } else {
            usageType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdNatureOfproperty1()) {
            propertyType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdNatureOfproperty1(), organisation).getDescLangFirst());
        } else {
            propertyType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdNatureOfproperty2()) {
            propertyType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdNatureOfproperty2(), organisation).getDescLangFirst());
        } else {
            propertyType.add(MainetConstants.CommonConstants.NA);
        }

        if (null != unit.getAssdNatureOfproperty3()) {
            propertyType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdNatureOfproperty3(), organisation).getDescLangFirst());
        } else {
            propertyType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdNatureOfproperty4()) {
            propertyType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdNatureOfproperty4(), organisation).getDescLangFirst());
        } else {
            propertyType.add(MainetConstants.CommonConstants.NA);
        }
        if (null != unit.getAssdNatureOfproperty5()) {
            propertyType.add(
                    CommonMasterUtility.getHierarchicalLookUp(unit.getAssdNatureOfproperty5(), organisation).getDescLangFirst());
        } else {
            propertyType.add(MainetConstants.CommonConstants.NA);
        }

    }

    private void populateSddrRateModel(PropertyTaxDataModel sddrRateCv, List<String> wardZone, Organisation organisation,
            Date scheduleStartDate, List<String> usageType) {
        sddrRateCv.setOrgId(organisation.getOrgid());
        sddrRateCv.setWardZoneLevel1(wardZone.get(0));
        sddrRateCv.setWardZoneLevel3(wardZone.get(2));
        sddrRateCv.setWardZoneLevel2(wardZone.get(1));
        sddrRateCv.setWardZoneLevel4(wardZone.get(3));
        sddrRateCv.setWardZoneLevel5(wardZone.get(4));
        sddrRateCv.setRateStartDate(scheduleStartDate.getTime());
        sddrRateCv.setUsageSubtype1(usageType.get(0));
        sddrRateCv.setUsageSubtype2(usageType.get(1));
        sddrRateCv.setUsageSubtype3(usageType.get(2));
        sddrRateCv.setUsageSubtype4(usageType.get(3));
        sddrRateCv.setUsageSubtype5(usageType.get(0));
    }

    private void populateFactorMasterModel(FactorMasterModel factorMasterModel,
            ProvisionalAssesmentFactorDtlDto factorDto, Organisation organisation, Date startDate,
            Map<String, String> unitFactors) {
        LookUp factorLookup = null;
        if (factorDto.getAssfFactorId() != null) {
            factorLookup = CommonMasterUtility.lookUpByLookUpIdAndPrefix(factorDto.getAssfFactorId(),
                    MainetConstants.Property.propPref.FCT, organisation.getOrgid());
            factorMasterModel.setFactor(factorLookup.getDescLangFirst());
            factorMasterModel.setFactApplicable(factorLookup.getOtherField());
        }

        factorMasterModel.setOrgId(organisation.getOrgid());
        if (factorDto.getAssfFactorValueId() != null)
            factorMasterModel.setFactorValue(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(factorDto.getAssfFactorValueId(), organisation).getDescLangFirst());
        factorMasterModel.setRateStartDate(startDate.getTime());
			unitFactors.put(factorMasterModel.getFactor(), factorMasterModel.getFactorValue());
		
    }

    private List<String> setDeptWardZone(ProvisionalAssesmentMstDto sdto, final Organisation organisation,
            Long deptId) {
        List<String> wardZone = new ArrayList<>(0);
        /*
         * LocationOperationWZMapping locOperationWZ = locationMasJpaRepository.findbyLocationAndDepartment(sdto.getLocId(),
         * deptId); if (locOperationWZ != null) {
         */
        if (sdto.getAssWard1() != null) {
            wardZone.add(CommonMasterUtility.getHierarchicalLookUp(sdto.getAssWard1(), organisation)
                    .getDescLangFirst());
        } else {
            wardZone.add(MainetConstants.CommonConstants.NA);
        }
        if (sdto.getAssWard2() != null) {
            wardZone.add(CommonMasterUtility.getHierarchicalLookUp(sdto.getAssWard2(), organisation)
                    .getDescLangFirst());
        } else {
            wardZone.add(MainetConstants.CommonConstants.NA);
        }
        if (sdto.getAssWard3() != null) {
            wardZone.add(CommonMasterUtility.getHierarchicalLookUp(sdto.getAssWard3(), organisation)
                    .getDescLangFirst());
        } else {
            wardZone.add(MainetConstants.CommonConstants.NA);
        }
        if (sdto.getAssWard4() != null) {
            wardZone.add(CommonMasterUtility.getHierarchicalLookUp(sdto.getAssWard4(), organisation)
                    .getDescLangFirst());
        } else {
            wardZone.add(MainetConstants.CommonConstants.NA);
        }
        if (sdto.getAssWard5() != null) {
            wardZone.add(CommonMasterUtility.getHierarchicalLookUp(sdto.getAssWard5(), organisation)
                    .getDescLangFirst());
        } else {
            wardZone.add(MainetConstants.CommonConstants.NA);
        }
        /* } */
        return wardZone;
    }

    @Override
    public void fetchInterstRate(List<TbBillMas> billMasList, Organisation org, Long deptId) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "fetchInterstRate() method");
        LookUp lookup = CommonMasterUtility.getHieLookupByLookupCode(PrefixConstants.TAX_CATEGORY.INTERST,
                PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid());
         LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL_RECEIPT, MainetConstants.Property.propPref.CAA,
                org);
        if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
        	taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                    org);
        }
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
        List<TbTaxMasEntity> taxList = tbTaxMasService.getTaxMasterByTaxCategoryId(deptId, lookup.getLookUpId(), org.getOrgid(),
                taxAppAtBill.getLookUpId());
        WSRequestDTO dto = new WSRequestDTO();
        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class, 0);
            WSRequestDTO request = new WSRequestDTO();
            PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList
                    .get(0);

            for (TbBillMas billMas : billMasList) {
				
                List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
                for (TbTaxMasEntity tax : taxList) {
                    PropertyRateMasterModel rateModel = null;
                    try {
                        rateModel = (PropertyRateMasterModel) propRateModel.clone();
                    } catch (CloneNotSupportedException e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new FrameworkException(e);
                    }
                    rateModel.setOrgId(org.getOrgid());
                    rateModel.setRateStartDate(billMas.getBmFromdt().getTime());
                    rateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                            org).getDescLangFirst());
                    if (tax.getTaxCategory2() != null) {
                        rateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                                org).getDescLangFirst());
                    }
                    if (tax.getParentCode() != null && tax.getParentCode() > 0) {
                        TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), org.getOrgid());
                        rateModel.setParentTaxCode(tbTax.getTaxCode());
                    }
                    rateModel.setTaxCode(tax.getTaxCode());
                    rateModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());
                    rateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
                    rateMasterList.add(rateModel);
                }
                schWiseMap.put(billMas.getBmFromdt(), rateMasterList);
            }

            request.setDataModel(schWiseMap);
            WSResponseDTO interestCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
            if (interestCharges != null
                    && MainetConstants.WebServiceStatus.SUCCESS
                            .equalsIgnoreCase(interestCharges.getWsStatus())) {
                Map<Date, List<PropertyRateMasterModel>> interestResponse = castRequestToTaxModelPropertyLevel(interestCharges);

                billMasList.forEach(bill -> {
                    // List<PropertyRateMasterModel> rateList = interestResponse.get(bill.getBmFromdt());
                    interestResponse.forEach((key, value) -> {
                        if (key.equals(bill.getBmFromdt())) {
                            for (PropertyRateMasterModel propRateMaster : value) {
                                TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(org.getOrgid(), deptId,
                                        propRateMaster.getTaxCode());
                                double baseRate = 0d;
                                LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                                        Long.valueOf(tax.getTaxMethod()), org);
                                if (taxType != null) {
                                    if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE)) {
                                        baseRate = propRateMaster.getPercentageRate() / 100;
                                    } else if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.FLAT)) {
                                        baseRate = propRateMaster.getFlatRate();
                                    }
                                }
                                if (baseRate > 0) {
                                    LookUp taxSubCat = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org);
                                    bill.setInterstCalMethod(taxSubCat.getLookUpCode());
                                    bill.setBmIntValue(baseRate);
                                    bill.setTaxCode(propRateMaster.getTaxCode());
                                    break;
                                }
                            }
                        }
                    });

                });

            }

        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "fetchInterstRate() method");
    }

    @Override
    @Transactional
    public void fetchPenaltyRate(TbBillMas billMas, Organisation org, Long deptId, PropertyRateMasterModel propRateModel,
            double diffAmount, AtomicDouble prvBalance, AtomicDouble prvtotal, AtomicDouble totalDemandWithoutRebate) {
        LookUp penaltyLookup = CommonMasterUtility.getHieLookupByLookupCode(
                MainetConstants.Property.PENLTY_AGAINST_MISLEADING_INFROMATION,
                PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                org);
        if (penaltyLookup != null && taxAppAtBill != null) {
            List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                    taxAppAtBill.getLookUpId(), penaltyLookup.getLookUpId());
            if (taxList != null && !taxList.isEmpty()) {
                TbTaxMasEntity tax = taxList.get(0);
                WSRequestDTO request = new WSRequestDTO();
                propRateModel.setOrgId(org.getOrgid());
                propRateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                        org).getDescLangFirst());
                propRateModel.setTaxSubCategory(penaltyLookup.getDescLangFirst());
                propRateModel.setRateStartDate(billMas.getBmFromdt().getTime());
                if (tax.getTaxCategory2() != null) {
                    propRateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                            org).getDescLangFirst());
                }
                if (tax.getParentCode() != null && tax.getParentCode() > 0) {
                    TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), org.getOrgid());
                    propRateModel.setParentTaxCode(tbTax.getTaxCode());
                    if (billMas != null) {
                        for (TbBillDet det : billMas.getTbWtBillDet()) {
                            if (det.getTaxId().equals(tax.getParentCode())) {
                                propRateModel.setTotalPropertyTax(det.getBdCurBalTaxamt()); // withRebate
                                propRateModel.setTotalPropertyTaxWithoutRebate(det.getBdCurTaxamt()); // without rebate
                                propRateModel.setTotalArrearPropertyTaxWithoutRebate(det.getBdPrvArramt()); // arrear without
                                                                                                            // rebate
                                propRateModel.setTotalArrearPropertyTaxWithRebate(det.getBdPrvBalArramt());// arrear with rebate
                            }
                        }
                    }
                }
                propRateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
                propRateModel.setDifferenceAmount(diffAmount);
                propRateModel.setTaxCode(tax.getTaxCode());
                propRateModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());
                propRateModel.setTotalDemandWithoutRebate(totalDemandWithoutRebate.doubleValue());
                propRateModel.setTotalDemandWithRebate(billMas.getBmTotalOutstanding());

                request.setDataModel(propRateModel);
                Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
                List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
                rateMasterList.add(propRateModel);
                schWiseMap.put(billMas.getBmFromdt(), rateMasterList);
                request.setDataModel(schWiseMap);
                WSResponseDTO penaltyCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                if (penaltyCharges != null
                        && MainetConstants.WebServiceStatus.SUCCESS
                                .equalsIgnoreCase(penaltyCharges.getWsStatus())) {
                    Map<Date, List<PropertyRateMasterModel>> penaltyResponse = castRequestToTaxModelPropertyLevel(
                            penaltyCharges);
                    Entry<Date, List<PropertyRateMasterModel>> entry = penaltyResponse.entrySet().iterator().next();
                    PropertyRateMasterModel penaltyResult = entry.getValue().get(0);
                    if (penaltyResult.getTaxValue() > 0) {
                        billMas.setTaxCode(penaltyResult.getTaxCode());
                        setbillDetails(tax, penaltyResult.getTaxValue(), billMas, prvBalance, prvtotal,
                                penaltyLookup.getLookUpId());

                    }
                }
            }
        } else {
            LOGGER.info("PENLTY_AGAINST_MISLEADING_INFROMATION with PMI value is not defined");
        }
    }

    private TbBillDet setbillDetails(TbTaxMasEntity tax, double baseRate, TbBillMas billMas, AtomicDouble prvBalance,
            AtomicDouble prvtotal, Long penaltyId) {
        TbBillDet billDet = null;
        double penalty = 0d;
        for (TbBillDet billdet : billMas.getTbWtBillDet()) {
            if (billdet.getTaxId().equals(tax.getTaxId())) {
                billDet = billdet;
                break;
            }

        }
        if (billDet == null) {
            billDet = new TbBillDet();
            billDet.setBmIdno(billMas.getBmIdno());
            billMas.getTbWtBillDet().add(billDet);
        }
        double amount = Math.round(baseRate - (billDet.getBdCurTaxamt() - billDet.getBdCurBalTaxamt()));

        billDet.setTaxCategory(tax.getTaxCategory1());
        billDet.setTaxSubCategory(tax.getTaxCategory2());
        billDet.setTaxId(tax.getTaxId());
        billDet.setTaxDesc(tax.getTaxDesc());
        billDet.setCollSeq(tax.getCollSeq());
        billDet.setDisplaySeq(tax.getTaxDisplaySeq());
        billDet.setBdCurTaxamt(baseRate);
        billDet.setBdCurBalTaxamt(amount);
        billDet.setBdPrvArramt(prvtotal.doubleValue());
        billDet.setBdPrvBalArramt(prvBalance.doubleValue());
        if (prvtotal.doubleValue() > 0d) {
            prvtotal.addAndGet(billDet.getBdPrvArramt());
            prvBalance.addAndGet(billDet.getBdPrvBalArramt());
        } else {
            prvtotal.addAndGet(billDet.getBdCurTaxamt());
            prvBalance.addAndGet(billDet.getBdCurBalTaxamt());
        }
        for (TbBillDet billdet : billMas.getTbWtBillDet()) {
            if (billdet.getTaxCategory().equals(penaltyId)) {
                penalty += billdet.getBdCurBalTaxamt() + billdet.getBdPrvBalArramt();
            }
        }
        billMas.setTotalPenalty(penalty);
        return billDet;
    }

    @Override
    @Transactional
    public List<BillDisplayDto> fetchEarlyPayRebateRate(List<TbBillMas> billMasList, Organisation org, Long deptId,
            Date manualDate,String payableAmountMethod) {
        List<BillDisplayDto> billDtoList = new ArrayList<BillDisplayDto>();
        if (billMasList != null && !billMasList.isEmpty()) {
            Date tillDate = null;
            if (manualDate == null) {
                tillDate = new Date();
            } else {
                tillDate = manualDate;
            }
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            LookUp rebate = null;
            try {
            	rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", org);

            } catch (Exception e) {
            	LOGGER.error("No prefix found for ENV(RBT)");
            }
			if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
					&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY))
					|| (billMas.getBmDuedate() != null && tillDate.before(billMas.getBmDuedate())
							|| tillDate.equals(billMas.getBmDuedate()))) {

                WSRequestDTO dto = new WSRequestDTO();
                LookUp taxSubCat = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.EARLY_PAY_DISCOUNT,
                        PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
                final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                        org);

                List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                        taxAppAtBill.getLookUpId(), taxSubCat.getLookUpId());

                if (taxList != null && !taxList.isEmpty()) {
                    for (TbTaxMasEntity tax : taxList) {
                        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
                        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
                        if (response != null
                                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
                                    0);
                            WSRequestDTO request = new WSRequestDTO();
                            setRateMasterForRebate(billMasList, org, billMas, taxAppAtBill, tax,
                                    propertyRateMasterList, request, manualDate,payableAmountMethod);
                            WSResponseDTO rabateCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                            if (rabateCharges != null
                                    && MainetConstants.WebServiceStatus.SUCCESS
                                            .equalsIgnoreCase(rabateCharges.getWsStatus())) {
                                Map<Date, List<PropertyRateMasterModel>> rebateResponse = castRequestToTaxModelPropertyLevel(
                                        rabateCharges);
                                Entry<Date, List<PropertyRateMasterModel>> entry = rebateResponse.entrySet().iterator().next();

                                PropertyRateMasterModel rebateResult = entry.getValue().get(0);
                                if (rebateResult.getTaxValue() > 0) {
                                    BillDisplayDto billdto = new BillDisplayDto();
                                    setBillDisplayDto(rebateResult, tax, billdto);
                                    billDtoList.add(billdto);
                                }
                            }
                        }
                    }
                }
            }
        }
        return billDtoList;
    }

    private void setRateMasterForRebate(List<TbBillMas> billMasList, Organisation org, TbBillMas billMas,
            final LookUp taxAppAtBill, TbTaxMasEntity tax, List<Object> propertyRateMasterList, WSRequestDTO request,
            Date manualDate,String payableAmountMethod) {
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
        List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
        PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList
                .get(0);
        propRateModel.setOrgId(org.getOrgid());
        // propRateModel.setRateStartDate(billMas.getBmFromdt().getTime());
        /////#84422 By Arun
        Date date=Utility.removeTimeFromDatestatic(new Date());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			propRateModel.setRateStartDate(todayWithZeroTime.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        if (manualDate != null) {
        	try {
				Date todayWithZeroTimeForManual = formatter.parse(formatter.format(manualDate));
				propRateModel.setRateStartDate(todayWithZeroTimeForManual.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	
        }
        /////
        propRateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                org).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            propRateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    org).getDescLangFirst());
        }
        propRateModel.setTaxCode(tax.getTaxCode());
        propRateModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());
        propRateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
        propRateModel.setUsageSubtype1(payableAmountMethod);
        if (tax.getParentCode() != null && tax.getParentCode() > 0) {
            TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), tax.getOrgid());
            propRateModel.setParentTaxCode(tbTax.getTaxCode());
            if (billMasList != null && !billMasList.isEmpty()) {
                // Early payment discount is applied on self occupied rebate deducted amount and set it in totalPropertyTax by
                // apeksha
                List<TbBillDet> billDet = billMasList.get(billMasList.size() - 1).getTbWtBillDet();
                billDet.forEach(det -> {
                    if (det.getTaxId().equals(tax.getParentCode())) {
                    	if(Utility.isEnvPrefixAvailable(org, "HDA") && StringUtils.isNotBlank(payableAmountMethod) && StringUtils.equals(payableAmountMethod, "Half")) {
                    		 propRateModel.setTotalPropertyTax(det.getBdCurBalTaxamt()/2);
                             propRateModel.setTotalPropertyTaxWithoutRebate(det.getBdCurTaxamt()/2); // without rebate
                             propRateModel.setTotalArrearPropertyTaxWithoutRebate(det.getBdPrvArramt()/2); // arrear without rebate
                             propRateModel.setTotalArrearPropertyTaxWithRebate(det.getBdPrvBalArramt()/2);// arrear with rebate
                    	}else {
                    		 propRateModel.setTotalPropertyTax(det.getBdCurBalTaxamt());
                             propRateModel.setTotalPropertyTaxWithoutRebate(det.getBdCurTaxamt()); // without rebate
                             propRateModel.setTotalArrearPropertyTaxWithoutRebate(det.getBdPrvArramt()); // arrear without rebate
                             propRateModel.setTotalArrearPropertyTaxWithRebate(det.getBdPrvBalArramt());// arrear with rebate
                    	}
                    }
                });
            }
        }
        double totalDemandAmount = 0;
        for (TbBillMas bill : billMasList) {
            totalDemandAmount = totalDemandAmount + bill.getBmTotalAmount();
        }
        TbBillMas bill = billMasList.get(billMasList.size() - 1);
        propRateModel.setTotalDemandWithRebate(bill.getBmTotalOutstanding());
        propRateModel.setTotalDemandWithoutRebate(totalDemandAmount);
        double demandTax = 0;

        for (TbBillDet billdet : billMas.getTbWtBillDet()) {
            if (billdet.getTaxCategory() != null) {
                if (PrefixConstants.TAX_CATEGORY.DEMAND.equals(CommonMasterUtility
                        .getHierarchicalLookUp(billdet.getTaxCategory(), org).getLookUpCode())) {
                    demandTax = demandTax + billdet.getBdCurBalTaxamt();
                }
            }

        }
        propRateModel.setParentTaxValue(demandTax);
        rateMasterList.add(propRateModel);
        schWiseMap.put(billMas.getBmFromdt(), rateMasterList);
        request.setDataModel(schWiseMap);
    }

    private void setRateMasterForSurCharge(Organisation org,
            final LookUp taxAppAtBill, TbTaxMasEntity tax, List<Object> propertyRateMasterList, WSRequestDTO request,
            List<TbBillMas> billMasList, double arrearAmt, Date manualReceiptDate) {
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
        List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
        PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList
                .get(0);
        propRateModel.setOrgId(org.getOrgid());
        propRateModel.setRateStartDate(new Date().getTime());
        if (manualReceiptDate != null) {
            propRateModel.setRateStartDate(manualReceiptDate.getTime());
        }
        propRateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                org).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            propRateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    org).getDescLangFirst());
        }
        propRateModel.setTaxCode(tax.getTaxCode());
        propRateModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());
        propRateModel.setArrearAmountWithoutRebate(arrearAmt);
        propRateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
        if (tax.getParentCode() != null && tax.getParentCode() > 0) {
            TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), tax.getOrgid());
            propRateModel.setParentTaxCode(tbTax.getTaxCode());
            // changes related to surcharge which is applied on arrears property tax with rebate amount by Apeksha
            if (billMasList != null && !billMasList.isEmpty()) {
                List<TbBillDet> billDet = billMasList.get(billMasList.size() - 1).getTbWtBillDet();
                for (TbBillDet det : billDet) {
                    if (det.getTaxId().equals(tax.getParentCode())) {
                        LookUp showAsArrearLookUp = null;
                        try {
                            showAsArrearLookUp = CommonMasterUtility.getValueFromPrefixLookUp("ANA", "SAA", org);
                        } catch (Exception exception) {
                            LOGGER.error("No Prefix found for SAA(ANA)");
                        }
                        Date currDate = new Date();
                        if (manualReceiptDate != null) {
                            currDate = manualReceiptDate;
                        }
                        Long currFinYearId = iFinancialYearService.getFinanceYearId(currDate);
                        propRateModel.setTotalPropertyTax(det.getBdCurBalTaxamt()); // withRebate
                        propRateModel.setTotalPropertyTaxWithoutRebate(det.getBdCurTaxamt()); // without rebate
                        propRateModel.setTotalArrearPropertyTaxWithoutRebate(det.getBdPrvArramt()); // arrear without rebate
                        propRateModel.setTotalArrearPropertyTaxWithRebate(det.getBdPrvBalArramt());// arrear with rebate
                        if ((!billMasList.get(billMasList.size() - 1).getBmYear().equals(currFinYearId))
                                && (showAsArrearLookUp != null && StringUtils.equals(showAsArrearLookUp.getOtherField(),
                                        MainetConstants.FlagY))) {
                            propRateModel.setTotalPropertyTax(0); // withRebate
                            propRateModel.setTotalPropertyTaxWithoutRebate(0); // without rebate
                            propRateModel.setTotalArrearPropertyTaxWithoutRebate(det.getBdPrvArramt() + det.getBdCurTaxamt()); // arrear
                                                                                                                               // without
                                                                                                                               // rebate
                            propRateModel.setTotalArrearPropertyTaxWithRebate(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());// arrear
                                                                                                                                 // with
                                                                                                                                 // rebate
                        }
                    }
                }
            }
        }
        double totalDemandAmount = 0;
        for (TbBillMas bill : billMasList) {
            totalDemandAmount = totalDemandAmount + bill.getBmTotalAmount();
        }
        TbBillMas bill = billMasList.get(billMasList.size() - 1);
        propRateModel.setTotalDemandWithRebate(bill.getBmTotalOutstanding());
        propRateModel.setTotalDemandWithoutRebate(totalDemandAmount);
        rateMasterList.add(propRateModel);
        schWiseMap.put(new Date(), rateMasterList);
        request.setDataModel(schWiseMap);
    }
   
    @Override
    @Transactional
    public BillDisplayDto fetchSurCharge(Organisation org, Long deptId, double arrearAmt, String taxSubCat,
            List<TbBillMas> billMasList, Long finYearId, Date manualReceiptDate) {
        BillDisplayDto billdto = null;
        WSRequestDTO dto = new WSRequestDTO();
        LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(taxSubCat,
                PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                org);

        List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());

        if (taxList != null && !taxList.isEmpty()) {
            TbTaxMasEntity tax = taxList.get(0);
            dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
            WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
            if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class, 0);
                WSRequestDTO request = new WSRequestDTO();
                setRateMasterForSurCharge(org, taxAppAtBill, tax, propertyRateMasterList, request, billMasList, arrearAmt,
                        manualReceiptDate);
                WSResponseDTO rabateCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                if (rabateCharges != null
                        && MainetConstants.WebServiceStatus.SUCCESS
                                .equalsIgnoreCase(rabateCharges.getWsStatus())) {
                    Map<Date, List<PropertyRateMasterModel>> surchargeResponse = castRequestToTaxModelPropertyLevel(
                            rabateCharges);
                    Entry<Date, List<PropertyRateMasterModel>> entry = surchargeResponse.entrySet().iterator().next();

                    PropertyRateMasterModel surchargeResult = entry.getValue().get(0);
                    double baseRate = 0d;

                    LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            Long.valueOf(tax.getTaxMethod()), org);

                    if (tax.getTaxMethod() != null) {
                        if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE)) {
                            baseRate = surchargeResult.getPercentageRate() / 100;
                        } else if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.FLAT)) {
                            baseRate = surchargeResult.getFlatRate();
                        }
                    }
                    if (baseRate > 0) {
                        // double totalSurCharge = baseRate * arrearAmt;
						billdto = new BillDisplayDto();
						billdto.setTaxDesc(tax.getTaxDesc());
						billdto.setDisplaySeq(tax.getTaxDisplaySeq());
						billdto.setTaxId(tax.getTaxId());
						billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(surchargeResult.getTaxValue())));
						billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(surchargeResult.getTaxValue())));
						billdto.setArrearsTaxAmt(BigDecimal.valueOf(0));
						billdto.setTaxCategoryId(tax.getTaxCategory1());
						billdto.setFinYearId(finYearId);
                    }
                }
            }
        }
        return billdto;
    }

    @Override
    @Transactional
    public List<BillDisplayDto> fetchApplicationOrScurtinyCharges(Organisation org, Long deptId,
            String serviceCode, String chargeAppAt, PropertyTransferMasterDto transferDto, ProvisionalAssesmentMstDto assMstDto) {

        List<BillDisplayDto> charges = new ArrayList<>();

        /*
         * for testing loi BillDisplayDto billdto1 = new BillDisplayDto(); billdto1.setTaxDesc("property tax");
         * billdto1.setDisplaySeq(1L); billdto1.setTaxId(53l); billdto1.setTotalTaxAmt(BigDecimal.valueOf(500d));
         * billdto1.setCurrentYearTaxAmt(BigDecimal.valueOf(500d)); charges.add(billdto1);
         */

        WSRequestDTO dto = new WSRequestDTO();
        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
            LookUp lookup = null;
            if (chargeAppAt.equals(MainetConstants.ChargeApplicableAt.APPLICATION)) {

                lookup = CommonMasterUtility.getHieLookupByLookupCode("AC",
                        PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
            }
            if (chargeAppAt.equals(MainetConstants.ChargeApplicableAt.SCRUTINY)) {

                lookup = CommonMasterUtility.getHieLookupByLookupCode("MAC",
                        PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
            }
            LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    chargeAppAt, MainetConstants.Property.propPref.CAA,
                    org);
            List<TbTaxMasEntity> taxList = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasJpaRepository.class)
                    .findAllActiveTaxList(org.getOrgid(), deptId,
                            taxAppAtBill.getLookUpId());
            /*
             * List<TbTaxMasEntity> taxList = tbTaxMasJpaRepository.findAllActiveTaxList(org.getOrgid(), deptId,
             * taxAppAtBill.getLookUpId());
             */

            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class, 0);
            WSRequestDTO request = new WSRequestDTO();
            PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList
                    .get(0);
            List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();

            List<TbTaxMasEntity> dependentTaxList = taxList.stream().filter(tax -> tax.getParentCode() != null)
                    .collect(Collectors.toList());
            List<TbTaxMasEntity> independentTaxList = taxList.stream().filter(tax -> tax.getParentCode() == null)
                    .collect(Collectors.toList());
            AtomicDouble totalRv = new AtomicDouble(0);
            AtomicDouble totalArv = new AtomicDouble(0);
            if(assMstDto != null && CollectionUtils.isNotEmpty(assMstDto.getProvisionalAssesmentDetailDtoList())) {
            	assMstDto.getProvisionalAssesmentDetailDtoList().forEach(detailDto ->{
            		if(detailDto.getAssdRv() != null) {
            			totalRv.addAndGet(detailDto.getAssdRv());
            		}
            		if(detailDto.getAssdAlv() != null) {
            			totalArv.addAndGet(detailDto.getAssdAlv());
            		}
            	});
            }
            if (CollectionUtils.isNotEmpty(independentTaxList) ) {
            	if(assMstDto !=null) {
                assMstDto.getProvisionalAssesmentDetailDtoList().forEach(detailDto -> {
                    independentTaxList.forEach(tax -> {
                        populateRateModelForPropertyLevel(org, serviceCode, taxAppAtBill, propRateModel, rateMasterList, tax,
                                transferDto, detailDto, charges,totalRv.doubleValue(),totalArv.doubleValue());
                    });
                });
            	}else {
            		 independentTaxList.forEach(tax -> {
                         populateRateModelForPropertyLevel(org, serviceCode, taxAppAtBill, propRateModel, rateMasterList, tax,
                                 transferDto, null, charges,totalRv.doubleValue(),totalArv.doubleValue());
                     });
            	}

                schWiseMap.put(new Date(), rateMasterList);
                request.setDataModel(schWiseMap);
                WSResponseDTO interestCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                if (interestCharges != null

                        && MainetConstants.WebServiceStatus.SUCCESS
                                .equalsIgnoreCase(interestCharges.getWsStatus())) {
                    Map<Date, List<PropertyRateMasterModel>> serviceChargeResponse = castRequestToTaxModelPropertyLevel(
                            interestCharges);
                    Entry<Date, List<PropertyRateMasterModel>> entry = serviceChargeResponse.entrySet().iterator().next();
                    List<PropertyRateMasterModel> serviceChargeList = entry.getValue();

                    serviceChargeList.forEach(serviceCharge -> {
                        independentTaxList.stream()
                                .filter(tax -> tax.getTaxCode().equals(serviceCharge.getTaxCode())).forEach(tax -> {
                                    if (tax != null && serviceCharge.getTaxValue() > 0) {
                                        BillDisplayDto billdto = new BillDisplayDto();
                                        billdto.setTaxDesc(tax.getTaxDesc());
                                        billdto.setDisplaySeq(tax.getTaxDisplaySeq());
                                        billdto.setTaxId(tax.getTaxId());
                                        billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setTaxCategoryId(tax.getTaxCategory1());
                                        billdto.setParentTaxCode(serviceCharge.getTaxCode());
                                        // Defect #90164 If TCL other vale is "Y" then Tax Calculation Level is based on
                                        // "Property" otherwise "Unit"
                                        LookUp checkListVerificationyFlag = null;
                                        try {
                                            checkListVerificationyFlag = CommonMasterUtility.getValueFromPrefixLookUp("TCL",
                                                    "ENV", org);
                                        } catch (Exception e) {
                                            LOGGER.error(e.getMessage(), e);
                                        }
                                        if ((checkListVerificationyFlag != null) && (StringUtils.equals(
                                                checkListVerificationyFlag.getOtherField(),
                                                MainetConstants.FlagY))) {
                                            // adding unique Parent Tax Code for "Property" based Tax Calculation Level
                                            if (!charges.stream().anyMatch(
                                                    ptTax -> ptTax.getParentTaxCode() != null
                                                            && ptTax.getParentTaxCode().equals(billdto.getParentTaxCode()))) {
                                                charges.add(billdto);
                                            }
                                        } else {
                                            charges.add(billdto);
                                        }
                                    }
                                });
                    });
                }
            }// if

            //

            if (CollectionUtils.isNotEmpty(dependentTaxList)) {
                assMstDto.getProvisionalAssesmentDetailDtoList().forEach(detailDto -> {
                    dependentTaxList.forEach(tax -> {
                        populateRateModelForPropertyLevel(org, serviceCode, taxAppAtBill, propRateModel, rateMasterList, tax,
                                transferDto, detailDto, charges,totalRv.doubleValue(),totalArv.doubleValue());
                    });
                });
                List<PropertyRateMasterModel> dependentRateList = rateMasterList.stream()
                        .filter(parentCode -> !parentCode.getParentTaxCode().equals(MainetConstants.CommonConstants.NA))
                        .collect(Collectors.toList());
                schWiseMap.clear();
                if (CollectionUtils.isNotEmpty(dependentRateList)) {
                    schWiseMap.put(new Date(), dependentRateList);
                    request.setDataModel(schWiseMap);
                }
                WSResponseDTO interestCharges2 = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                if (interestCharges2 != null

                        && MainetConstants.WebServiceStatus.SUCCESS
                                .equalsIgnoreCase(interestCharges2.getWsStatus())) {
                    Map<Date, List<PropertyRateMasterModel>> serviceChargeResponse = castRequestToTaxModelPropertyLevel(
                            interestCharges2);
                    Entry<Date, List<PropertyRateMasterModel>> entry = serviceChargeResponse.entrySet().iterator().next();
                    List<PropertyRateMasterModel> serviceChargeList2 = entry.getValue();

                    serviceChargeList2.forEach(serviceCharge -> {
                        dependentTaxList.stream()
                                .filter(tax -> tax.getTaxCode().equals(serviceCharge.getTaxCode())).forEach(tax -> {
                                    if (tax != null && serviceCharge.getTaxValue() > 0) {
                                        BillDisplayDto billdto = new BillDisplayDto();
                                        billdto.setTaxDesc(tax.getTaxDesc());
                                        billdto.setDisplaySeq(tax.getTaxDisplaySeq());
                                        billdto.setTaxId(tax.getTaxId());
                                        billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setTaxCategoryId(tax.getTaxCategory1());
                                        // Defect #90164 If TCL other vale is "Y" then Tax Calculation Level is based on
                                        // "Property" otherwise "Unit"
                                        LookUp checkListVerificationyFlag = null;
                                        try {
                                            checkListVerificationyFlag = CommonMasterUtility.getValueFromPrefixLookUp("TCL",
                                                    "ENV", org);
                                        } catch (Exception e) {
                                            LOGGER.error(e.getMessage(), e);
                                        }
                                        if ((checkListVerificationyFlag != null) && (StringUtils.equals(
                                                checkListVerificationyFlag.getOtherField(),
                                                MainetConstants.FlagY))) {
                                            // adding unique Parent Tax Code for "Property" based Tax Calculation Level
                                            if (!charges.stream().anyMatch(
                                                    ptTax -> ptTax.getParentTaxCode() != null
                                                            && ptTax.getParentTaxCode().equals(billdto.getParentTaxCode()))) {
                                                charges.add(billdto);
                                            }
                                        } else {
                                            charges.add(billdto);
                                        }
                                    }
                                });
                    });
                }
            }// if
        }
        return charges;
    }

    private void populateRateModelForPropertyLevel(Organisation org, String serviceCode, LookUp taxAppAt,
            PropertyRateMasterModel propRateModel, List<PropertyRateMasterModel> rateMasterList, TbTaxMasEntity tax,
            PropertyTransferMasterDto transferDto, ProvisionalAssesmentDetailDto assDetDto,
            List<BillDisplayDto> billDisplayDtoList,double totalRv,double totalArv) {
        PropertyRateMasterModel rateModel = null;
        try {
            rateModel = (PropertyRateMasterModel) propRateModel.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }
        List<String> propertyType = new ArrayList<>(0);
        List<String> usageType = new ArrayList<>(0);
        String floorLookup = null;

        /*
         * task: 26817 UsageType set value by Sharvan
         */
        rateModel.setTotalRv(totalRv);
        rateModel.setTotalArv(totalArv);
        if (assDetDto != null) {
            setUsageTypeAndPropertyType(assDetDto, org, usageType, propertyType);
            rateModel.setUsageSubtype1(usageType.get(0));
            rateModel.setUsageSubtype2(usageType.get(1));
            rateModel.setUsageSubtype3(usageType.get(2));
            rateModel.setUsageSubtype4(usageType.get(3));
            rateModel.setUsageSubtype5(usageType.get(4));
        }
        rateModel.setOrgId(org.getOrgid());
        rateModel.setRateStartDate(new Date().getTime());
        rateModel.setServiceCode(serviceCode);
        if (assDetDto != null && assDetDto.getAssdFloorNo() != null) {
            floorLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assDetDto.getAssdFloorNo(), org).getDescLangFirst();
        }
        rateModel.setFloorNo(floorLookup);
        // rateModel.setAssdUsagetype1(CommonMasterUtility.getHierarchicalLookUp(assMstDto.getAssdUsa));
        // rateModel.setAssdFloorNo(Long assdFloorNo)
        rateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                org).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            rateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    org).getDescLangFirst());
        }
        rateModel.setTaxCode(tax.getTaxCode());
        // Defect #82917
        if (tax.getParentCode() != null) {
            TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), tax.getOrgid());
            rateModel.setParentTaxCode(tbTax.getTaxCode());
            for (BillDisplayDto dto : billDisplayDtoList) {
                if (tbTax.getTaxCode().equals(dto.getParentTaxCode())) {
                    rateModel.setParentTaxValue(dto.getTotalTaxAmt().doubleValue());
                }
            }
        }
        rateModel.setChargeApplicableAt(taxAppAt.getDescLangFirst());
        if (assDetDto != null && assDetDto.getAssdAlv() != null) {
            rateModel.setArv(assDetDto.getAssdAlv());
        }
        if (assDetDto != null && assDetDto.getAssdRv() != null) {
            rateModel.setRv(assDetDto.getAssdRv());
        }
        // Defect #82866
        if (transferDto != null && transferDto.getTransferType() != null && transferDto.getTransferType() > 0) {
            rateModel.setMutationType(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(transferDto.getTransferType(), org).getDescLangFirst());
            rateModel.setFactor4(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(transferDto.getTransferType(), org).getDescLangFirst());
        } else if(transferDto!=null && transferDto.getProAssNo()!=null){
            // Defect #111401        	
            PropertyTransferMasterDto transferMasDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(MutationService.class).getMutationByPropNo(transferDto.getProAssNo(),
                            org.getOrgid());
            if (transferMasDto != null && transferMasDto.getTransferType() != null && transferMasDto.getTransferType() > 0) {
                rateModel.setMutationType(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(transferMasDto.getTransferType(), org)
                                .getDescLangFirst());
                rateModel.setFactor4(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(transferMasDto.getTransferType(), org)
                                .getDescLangFirst());
            }
        }

        rateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.UNIT);
        // rateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
        if (transferDto != null && transferDto.getSalesDeedValue() != null) {
            rateModel.setSaleDeadValue(transferDto.getSalesDeedValue().doubleValue());
        }
        Date currentDate = new Date();
        Date transferDate = new Date();
        List<Date> transferDateList = new ArrayList<>();
        // D#90817 NULL handle for Transfer Date
        if (transferDto != null && transferDto.getActualTransferDate() != null) {
            transferDate = transferDto.getActualTransferDate();
        } else {
            if (transferDto != null) {
                transferDateList = propertyTransferService.getActualTransferDateByPropNo(transferDto.getOrgId(),
                        transferDto.getProAssNo());
            }
        }
        if (CollectionUtils.isNotEmpty(transferDateList)) {
            transferDate = transferDateList.get(transferDateList.size() - 1);
        }

        long diff = currentDate.getTime() - transferDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        try {
            if (transferDto != null) {
                List<LookUp> noOfDaysList = CommonMasterUtility.lookUpListByPrefix("MPD", transferDto.getOrgId());
                for (LookUp noOfDays : noOfDaysList) {
                    if (days > Long.valueOf(noOfDays.getLookUpCode())) {
                        rateModel.setIsDueDatePassOrNot(noOfDays.getOtherField());
                    } else {
                        rateModel.setIsDueDatePassOrNot(MainetConstants.CommonConstants.NA);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        rateMasterList.add(rateModel);
        /*
         * if (tax.getTaxCategory1()!=null) { propUnit.forEach(propUnit -> {
         * propRateModel.setUsageSubtype1(propUnit.setUsageSubtype1(usageType.get(0))); rateMasterList.
         */

    }

    private void setBillDisplayDto(PropertyRateMasterModel serviceCharge, TbTaxMasEntity tax, BillDisplayDto billdto) {
        Organisation organisation = new Organisation(tax.getOrgid());
        billdto.setTaxDesc(tax.getTaxDesc());
        billdto.setDisplaySeq(tax.getTaxDisplaySeq());
        billdto.setTaxId(tax.getTaxId());

        LookUp roundOff = null;
        try {
            roundOff = CommonMasterUtility.getValueFromPrefixLookUp("NR", "RND", organisation);

        } catch (Exception e) {

        }
        if (roundOff != null && roundOff.getOtherField() != null
                && org.apache.commons.lang.StringUtils.equals(roundOff.getOtherField(), "RF")) {
            billdto.setTotalTaxAmt(BigDecimal.valueOf(serviceCharge.getTaxValue()));
            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(serviceCharge.getTaxValue()));
        } else if (roundOff != null && roundOff.getOtherField() != null
                && org.apache.commons.lang.StringUtils.equals(roundOff.getOtherField(), "NF")) {
            billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
        } else {
            billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
        }

        billdto.setTaxCategoryId(tax.getTaxCategory1());
        if (org.apache.commons.lang.StringUtils.isNotBlank(serviceCharge.getParentTaxCode())) {
            billdto.setParentTaxCode(serviceCharge.getParentTaxCode());
        }
        billdto.setPercentageRate(serviceCharge.getPercentageRate());
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<FactorMasterModel>> castRequestToDataModelMapRate(WSResponseDTO response) {
        Map<String, List<FactorMasterModel>> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, List<FactorMasterModel>>> typeRef = new TypeReference<Map<String, List<FactorMasterModel>>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public Map<String, ALVMasterModel> castRequestToAlvCvModel(WSResponseDTO response) {
        Map<String, ALVMasterModel> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, ALVMasterModel>> typeRef = new TypeReference<Map<String, ALVMasterModel>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }
        }
        LOGGER.info(
                "********************************************* BRMS Call End for ALV RV *****************************************************");
       
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<Date, List<PropertyRateMasterModel>>> castRequestToTaxModel(WSResponseDTO response) {
        Map<String, Map<Date, List<PropertyRateMasterModel>>> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            System.out.println(jsonString);
            try {
                TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>> typeRef = new TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public Map<Date, List<PropertyRateMasterModel>> castRequestToTaxModelPropertyLevel(WSResponseDTO response) {
        Map<Date, List<PropertyRateMasterModel>> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<Date, List<PropertyRateMasterModel>>> typeRef = new TypeReference<Map<Date, List<PropertyRateMasterModel>>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public Map<String, PropertyTaxDataModel> castRequestToSddrRate(WSResponseDTO response) {
        Map<String, PropertyTaxDataModel> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<String, Object> requestMap = (LinkedHashMap<String, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, PropertyTaxDataModel>> typeRef = new TypeReference<Map<String, PropertyTaxDataModel>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }
        }
        return dataModel;
    }

    @Override
    @Transactional
    public List<BillDisplayDto> fetchApplicationForNoDues(Organisation org, Long deptId,
            String serviceCode, String chargeAppAt, Long noOfCopies, Long serviceId) {
        List<BillDisplayDto> charges = new ArrayList<>();
        WSRequestDTO dto = new WSRequestDTO();
        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();

            LookUp lookup = CommonMasterUtility.getHieLookupByLookupCode("COPY",
                    PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
            LookUp taxAppAtAppli = CommonMasterUtility.getValueFromPrefixLookUp(
                    chargeAppAt, MainetConstants.Property.propPref.CAA,
                    org);

            Long chargeAppliAt = CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                            PrefixConstants.LookUpPrefix.CAA, org)
                    .getLookUpId();

            List<TbTaxMasEntity> taxList = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(serviceId,
                            org.getOrgid(), chargeAppliAt);

            if (CollectionUtils.isNotEmpty(taxList)) {
                List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
                        0);
                WSRequestDTO request = new WSRequestDTO();
                PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList.get(0);
                List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
                taxList.forEach(tax -> {
                    populateRateModelForPropertyLevel(org, serviceCode, taxAppAtAppli, propRateModel, rateMasterList,
                            tax, null, null, null,0,0);
                });
                if (CollectionUtils.isNotEmpty(rateMasterList)) {
                    rateMasterList.forEach(propModel -> {
                        propModel.setNoOfDays(Long.valueOf(noOfCopies));
                    });
                }
                schWiseMap.put(new Date(), rateMasterList);
                request.setDataModel(schWiseMap);
                WSResponseDTO interestCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                if (interestCharges != null

                        && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(interestCharges.getWsStatus())) {
                    Map<Date, List<PropertyRateMasterModel>> serviceChargeResponse = castRequestToTaxModelPropertyLevel(
                            interestCharges);
                    Entry<Date, List<PropertyRateMasterModel>> entry = serviceChargeResponse.entrySet().iterator()
                            .next();
                    List<PropertyRateMasterModel> serviceChargeList = entry.getValue();

                    serviceChargeList.forEach(serviceCharge -> {
                        taxList.stream().filter(tax -> tax.getTaxCode().equals(serviceCharge.getTaxCode()))
                                .forEach(tax -> {
                                    if (tax != null && serviceCharge.getTaxValue() > 0) {
                                        BillDisplayDto billdto = new BillDisplayDto();
                                        billdto.setTaxDesc(tax.getTaxDesc());
                                        billdto.setDisplaySeq(tax.getTaxDisplaySeq());
                                        billdto.setTaxId(tax.getTaxId());
                                        billdto.setTotalTaxAmt(
                                                BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setCurrentYearTaxAmt(
                                                BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
                                        billdto.setTaxCategoryId(tax.getTaxCategory1());
                                        charges.add(billdto);
                                    }
                                });
                    });
                }
            }

        }
        return charges;
    }

    private Map<String, AlvFactorCombineResDto> setAlvFactorOutputInRateModel(Map<String, ALVMasterModel> alvRvMapRes,
            Map<String, List<FactorMasterModel>> factorMap) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setAlvFactorOutputInRateModel() method");
        Map<String, AlvFactorCombineResDto> resMap = new HashMap<>();
        if (alvRvMapRes != null && !alvRvMapRes.isEmpty()) {

            alvRvMapRes.forEach((key, schMap) -> {
                String[] keyObj = key.split(MainetConstants.operator.UNDER_SCORE);
                AlvFactorCombineResDto resDto = resMap.get(keyObj[0]);
                if (resDto == null) {
                    resDto = new AlvFactorCombineResDto();
                }
                resDto.getArv().addAndGet(schMap.getArv());
                resDto.getRv().addAndGet(schMap.getRv());
                resDto.getCv().addAndGet(schMap.getCv());
                resDto.getTaxableArea().addAndGet(schMap.getTaxableArea());
                LookUp ResidentialLookUp = CommonMasterUtility.getHieLookupByLookupCode("RES", "USA", 1, schMap.getOrgId());
                if(ResidentialLookUp != null && StringUtils.isNotBlank(ResidentialLookUp.getDescLangFirst()) && StringUtils.equals(ResidentialLookUp.getDescLangFirst(), schMap.getUsageSubtype1())) {
                	 resDto.getResidentialRv().addAndGet(schMap.getRv());
                }
                resMap.put(keyObj[0], resDto);
                resMap.put(key, resDto);

            });
            if (factorMap != null && !factorMap.isEmpty()) {
                factorMap.forEach((factKey, factorList) -> {
                    if (factorList != null && !factorList.isEmpty()) {
                        String[] keyObj = factKey.split(MainetConstants.operator.UNDER_SCORE);
                        AlvFactorCombineResDto resDto = resMap.get(keyObj[0]);
                        factorList.forEach(factor -> {
                            String factValue = resDto.getFactor().get(factor.getFactor());
                            if (factValue == null) {
                                resDto.getFactor().put(factor.getFactor(), factor.getFactorValue());
                            } else {
                                resDto.getFactor().put(factor.getFactor(), factValue + "," + factor.getFactorValue());
                            }
                        });
                    }
                });
            }

        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setAlvFactorOutputInRateModel() method");
        return resMap;
    }

    private Map<Date, List<PropertyRateMasterModel>> combineDependentAndIndependentTaxes(
            Map<Date, List<PropertyRateMasterModel>> schWiseIndependntMap,
            Map<Date, List<PropertyRateMasterModel>> schWiseDependentMap) {
        schWiseIndependntMap.forEach((key, value) -> {
            schWiseDependentMap.get(key);
            value.addAll(schWiseDependentMap.get(key));
        });
        return schWiseIndependntMap;
    }

    private Map<Long, Boolean> getYearWisePropertyMixedMap(List<ProvisionalAssesmentDetailDto> unitDetails,
            List<Long> finYearList) {
        Map<Long, Boolean> propMixedMap = new HashMap<>();
        finYearList.forEach(year -> {
            List<ProvisionalAssesmentDetailDto> listOutput = unitDetails.stream().filter(e -> e.getFaYearId().equals(year))
                    .collect(Collectors.toList());
            boolean propMixed = isPropertyMixed(listOutput);
            propMixedMap.put(year, propMixed);
        });
        return propMixedMap;

    }

    private boolean isPropertyMixed(List<ProvisionalAssesmentDetailDto> unitDetails) {
        Long uasgeType = 0l;
        // Long occupType = 0l;
        boolean usageType = false;
        for (ProvisionalAssesmentDetailDto dto : unitDetails) {
            Logger.getLogger(this.getClass()).error(" inside isPropertyMixed and year is :" + dto.getFaYearId());
            if (uasgeType == 0) {
                uasgeType = dto.getAssdUsagetype1();
            } else {
                if (!uasgeType.equals(dto.getAssdUsagetype1())) {
                    return true;
                }
            }
        }
        /*
         * if (!usageType) { for (ProvisionalAssesmentDetailDto dto : unitDetails) { if (occupType == 0) { occupType =
         * dto.getAssdOccupancyType(); } else { if (!occupType.equals(dto.getAssdOccupancyType())) { return true; } } } } else {
         * return usageType; }
         */

        return usageType;
    }

    private Map<Long, Boolean> getYearWiseConstruTypeMixedMap(List<ProvisionalAssesmentDetailDto> unitDetails,
            List<Long> finYearList) {
        Map<Long, Boolean> construtionTypeMixedMap = new HashMap<>();
        finYearList.forEach(year -> {
            List<ProvisionalAssesmentDetailDto> listOutput = unitDetails.stream().filter(e -> e.getFaYearId().equals(year))
                    .collect(Collectors.toList());
            boolean propMixed = isConstructionTypeMixed(listOutput);
            construtionTypeMixedMap.put(year, propMixed);
        });
        return construtionTypeMixedMap;
    }

    private boolean isConstructionTypeMixed(List<ProvisionalAssesmentDetailDto> unitDetails) {
        Long constructionType = 0l;
        boolean construType = false;
        for (ProvisionalAssesmentDetailDto dto : unitDetails) {
            if (constructionType == 0) {
                constructionType = dto.getAssdConstruType();
            } else {
                if (!constructionType.equals(dto.getAssdConstruType())) {
                    return true;
                }
            }
        }
        return construType;
    }

    private Map<Long, AtomicDouble> yearWiseTotalBuiltUpArea(List<ProvisionalAssesmentDetailDto> unitDetails,
            List<Long> finYearList) {
        Map<Long, AtomicDouble> totalBuiltUpAreaMap = new HashMap<>();
        finYearList.forEach(year -> {
            List<ProvisionalAssesmentDetailDto> listOutput = unitDetails.stream().filter(e -> e.getFaYearId().equals(year))
                    .collect(Collectors.toList());
            AtomicDouble totalBuiltUpArea = totalBuiltUpArea(listOutput);
            totalBuiltUpAreaMap.put(year, totalBuiltUpArea);

        });
        return totalBuiltUpAreaMap;
    }

    private AtomicDouble totalBuiltUpArea(List<ProvisionalAssesmentDetailDto> listOutput) {
        AtomicDouble totalBuiltUpArea = new AtomicDouble(0);
        listOutput.forEach(det -> {
            if (det.getAssdBuildupArea() != null) {
                totalBuiltUpArea.addAndGet(det.getAssdBuildupArea());
            }
        });
        return totalBuiltUpArea;
    }

    private Map<Long, Boolean> getYearWiseOccupancyTypeMixedMap(List<ProvisionalAssesmentDetailDto> unitDetails,
            List<Long> finYearList) {
        Map<Long, Boolean> OccupancyTypeMixedMap = new HashMap<>();
        finYearList.forEach(year -> {
            List<ProvisionalAssesmentDetailDto> listOutput = unitDetails.stream().filter(e -> e.getFaYearId().equals(year))
                    .collect(Collectors.toList());
            boolean propMixed = isOccupancyTypeMixed(listOutput);
            OccupancyTypeMixedMap.put(year, propMixed);
        });
        return OccupancyTypeMixedMap;
    }

    private boolean isOccupancyTypeMixed(List<ProvisionalAssesmentDetailDto> listOutput) {
        Long occupancyType = 0l;
        boolean occuType = false;
        for (ProvisionalAssesmentDetailDto dto : listOutput) {
            if (occupancyType == 0) {
                occupancyType = dto.getAssdOccupancyType();
            } else {
                if (!occupancyType.equals(dto.getAssdOccupancyType())) {
                    return true;
                }
            }
        }
        return occuType;
    }

    private Map<String, List<AtomicDouble>> setTotalRvOfExemptionWise(
            List<ProvisionalAssesmentDetailDto> unitDetailsList, Map<String, ALVMasterModel> combineAlvRvMapRes,
            Organisation organisation, List<Long> finYearList) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setTotalRvOfExemptionWise() method");
        Map<String, List<AtomicDouble>> totalRvOfExemptionWise = new HashMap<String, List<AtomicDouble>>();

        for (Long finYearId : finYearList) {

            AtomicDouble exemption1 = new AtomicDouble(0);
            AtomicDouble exemption2 = new AtomicDouble(0);
            AtomicDouble exemption3 = new AtomicDouble(0);
            List<AtomicDouble> unitWiseExemption = new ArrayList<>(0);
            for (Entry<String, ALVMasterModel> alvMasterOutput : combineAlvRvMapRes.entrySet()) {
                ALVMasterModel alvRvResponse = alvMasterOutput.getValue();
                if (alvMasterOutput.getKey().contains(finYearId.toString())) {
                    for (ProvisionalAssesmentDetailDto unitDetail : unitDetailsList) {

                        if (unitDetail.getAssdUnitNo() != null && org.apache.commons.lang.StringUtils.equals(alvMasterOutput.getKey(),
                                unitDetail.getFaYearId().toString().concat(MainetConstants.operator.UNDER_SCORE)
                                        .concat(unitDetail.getAssdUnitNo().toString()))) {
                            for (ProvisionalAssesmentFactorDtlDto factorDto : unitDetail
                                    .getProvisionalAssesmentFactorDtlDtoList()) {
                                LookUp exemptionLookUp = CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(factorDto.getAssfFactorValueId(), organisation);
                                if (org.apache.commons.lang.StringUtils.equals(exemptionLookUp.getLookUpCode(),
                                        "WWM")) {
                                    exemption1.addAndGet(alvRvResponse.getRv());
                                } else if (org.apache.commons.lang.StringUtils.equals(exemptionLookUp.getLookUpCode(),
                                        "ABM")) {
                                    exemption2.addAndGet(alvRvResponse.getRv());
                                } else if (org.apache.commons.lang.StringUtils.equals(exemptionLookUp.getLookUpCode(),
                                        "FF")) {
                                    exemption3.addAndGet(alvRvResponse.getRv());
                                }

                            }
                        }
                    }
                }

            }
            unitWiseExemption.add(exemption1);
            unitWiseExemption.add(exemption2);
            unitWiseExemption.add(exemption3);

            totalRvOfExemptionWise.put(finYearId.toString(), unitWiseExemption);

        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setTotalRvOfExemptionWise() method");
        return totalRvOfExemptionWise;

    }

    private int getNoOfMonthsToCalculatePT(Date dateOfAcquisition, FinancialYear finYear, Long orgId) {
        // D#89360
        if (dateOfAcquisition == null) {
            return 0;
        }
        int noOfMonths = 12;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateOfAcquisition);
        cal1.add(Calendar.MONTH, 12);
        Date dateForpartialYear = cal1.getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(dateOfAcquisition);
        int dayOfWeek = c.get(Calendar.DATE);

        if (dateOfAcquisition.compareTo(finYear.getFaFromDate()) >= 0
                && dateOfAcquisition.compareTo(finYear.getFaToDate()) <= 0) {
            noOfMonths = 0;
        } else if (dateForpartialYear != null && dateForpartialYear.compareTo(finYear.getFaFromDate()) >= 0
                && dateForpartialYear.compareTo(finYear.getFaToDate()) <= 0) {
            if (dayOfWeek > 1) {
                noOfMonths = Utility.monthsBetweenDates(dateForpartialYear, finYear.getFaToDate());
            } else {
                noOfMonths = Utility.monthsBetweenDates(dateForpartialYear, finYear.getFaToDate()) + 1;
            }
        }

        return noOfMonths;
    }

    private double setArrearsExist(String propertyNo, List<Long> finYearList, Long orgId, List<TbBillMas> unpaidBillMas,
            int count, TbServiceReceiptMasEntity manualReceiptMas, List<TbBillMas> paidBillMas) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setArrearsExist() method");
        double arrear = 0;
        if (propertyNo != null) {
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            if (CollectionUtils.isNotEmpty(unpaidBillMas)) {
                TbBillMas lastBillMas = unpaidBillMas.get(unpaidBillMas.size() - 1);
                arrear = lastBillMas.getBmTotalBalAmount();
            } else {
                int finYearSize = finYearList.size();
                if (finYearSize > 1) {
                    arrear = 100;
                }
                if (arrear <= 0) {
                    LookUp manualPenalty = null;
                    try {
                        manualPenalty = CommonMasterUtility.getValueFromPrefixLookUp("DPE", "PME", org);
                    } catch (Exception exception) {
                        LOGGER.error("No Prefix found for PME");
                    }
                    if (manualPenalty != null) {
                        arrear = setArrearsExistForManual(propertyNo, orgId, count, manualPenalty, manualReceiptMas, paidBillMas);
                    }
                }
            }
        } else {
            int finYearSize = finYearList.size();
            if (finYearSize > 1) {
                arrear = 100;
            }
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setArrearsExist() method");
        return arrear;
    }

    private double setArrearsExistForManual(String propertyNo, Long orgId, int count, LookUp manualPenalty,
            TbServiceReceiptMasEntity manualReceiptMas, List<TbBillMas> lastbillMasData) {
        double arrearAmount = 0;

        Date billcreateDate = null;
        if (CollectionUtils.isNotEmpty(lastbillMasData)) {
            TbBillMas lastBill = lastbillMasData.get(lastbillMasData.size() - 1);
            if (manualReceiptMas != null) {
                if (CollectionUtils.isNotEmpty(manualReceiptMas.getReceiptFeeDetail())) {
                    List<TbSrcptFeesDetEntity> lastYearDemandBillCollection = manualReceiptMas.getReceiptFeeDetail().stream()
                            .filter(recDet -> recDet.getBmIdNo() != null)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(lastYearDemandBillCollection)) {
                        List<TbSrcptFeesDetEntity> lastYearBillCollection = lastYearDemandBillCollection.stream()
                                .filter(recDetDto -> recDetDto.getBmIdNo().equals(lastBill.getBmIdno()))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(lastYearBillCollection)) {
                            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate localDate = LocalDate.parse(manualPenalty.getOtherField(), dateFormat);
                            billcreateDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            if (billcreateDate != null) {
                                if (Utility.compareDate(billcreateDate, manualReceiptMas.getRmDate())
                                        || Utility.comapreDates(billcreateDate, manualReceiptMas.getRmDate())) {
                                    arrearAmount = 100;
                                }
                            }
                        }
                    }

                }
            }

        }

        return arrearAmount;
    }

    private int setArrerPtCountForExistBills(List<TbBillMas> unPaidBillMasData, Organisation organisation,
            Date penaltyDateExtended, FinancialYear prevFinYear, TbServiceReceiptMasEntity manualReceiptMas,
            List<TbBillMas> PaidBillMasData, Long deptId, String propNo, Date manualDate) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setArrerPtCountForExistBills() method");
        AtomicInteger arrrearPtCount = new AtomicInteger(0);
        LookUp delayedPenaltyConfg = null;
        AtomicBoolean skipFirstYearPenalty = new AtomicBoolean(false);
        try {
            delayedPenaltyConfg = CommonMasterUtility.getValueFromPrefixLookUp("SPF", "DCP", organisation);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for DCP(SPF)");
        }
        if (delayedPenaltyConfg != null && StringUtils.isNotBlank(delayedPenaltyConfg.getOtherField())
                && StringUtils.equals(delayedPenaltyConfg.getOtherField(), MainetConstants.FlagY)) {
            skipFirstYearPenalty.set(true);
        }
        if (CollectionUtils.isNotEmpty(unPaidBillMasData)) {
        	final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, organisation);
        	LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
					PrefixConstants.LookUpPrefix.TAC, 1, organisation.getOrgid());
			LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("PT",
					PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());
			Long shastiPenaltyId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), organisation.getOrgid(),
					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
			AtomicLong bmIdno = new AtomicLong(0);
			
			unPaidBillMasData.forEach(billMasDto ->{
				billMasDto.getTbWtBillDet().forEach( billDetDto ->{
					if(billDetDto.getTaxId().equals(shastiPenaltyId) && billDetDto.getBdCurTaxamt() > 0) {
						bmIdno.getAndSet(billMasDto.getBmIdno());
					}
				});
			});
            unPaidBillMasData.forEach(billMas -> {
                billMas.getTbWtBillDet().forEach(billDet -> {
                    TbBillMas firstBillMas = null;
                    if (CollectionUtils.isNotEmpty(PaidBillMasData)) {
                        firstBillMas = PaidBillMasData.get(0);
                    }
                    TbTaxMas taxDetail = tbTaxMasService.findById(billDet.getTaxId(), organisation.getOrgid());
                    String lookUpCode = CommonMasterUtility
                            .getNonHierarchicalLookUpObject(taxDetail.getTaxDescId(), organisation).getLookUpCode();

                    Date currDate = new Date();
                    if (manualDate != null) {
                        currDate = manualDate;
                    }
                    if ((billMas.getBmIdno() >= bmIdno.longValue()) && (!(firstBillMas != null && skipFirstYearPenalty.get()
                            && (firstBillMas.getBmFromdt().compareTo(billMas.getBmFromdt()) >= 0
                                    && firstBillMas.getBmFromdt().compareTo(billMas.getBmTodt()) <= 0))
                            && ((!Utility.comapreDates(billMas.getBmFromdt(), prevFinYear.getFaFromDate()))
                                    || (penaltyDateExtended != null
                                            && Utility.compareDate(penaltyDateExtended, currDate)
                                            && !Utility.comapreDates(penaltyDateExtended, currDate)))
                            && lookUpCode.equals("PTR") && billDet.getBdCurBalTaxamt() > 0)) {
                        arrrearPtCount.getAndIncrement();
                    }
                });
            });
        } else if (manualReceiptMas != null) {
            if (CollectionUtils.isNotEmpty(PaidBillMasData)) {
                if (CollectionUtils.isNotEmpty(manualReceiptMas.getReceiptFeeDetail())) {
                    List<TbSrcptFeesDetEntity> lastYearDemandBillCollection = manualReceiptMas.getReceiptFeeDetail().stream()
                            .filter(recDet -> recDet.getBmIdNo() != null)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(lastYearDemandBillCollection)) {
                        PaidBillMasData.forEach(billMas -> {
                            billMas.getTbWtBillDet().forEach(billDet -> {
                                TbBillMas firstBillMas = null;
                                if (CollectionUtils.isNotEmpty(PaidBillMasData)) {
                                    firstBillMas = PaidBillMasData.get(0);
                                }
                                TbTaxMas taxDetail = tbTaxMasService.findById(billDet.getTaxId(), organisation.getOrgid());
                                String lookUpCode = CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(taxDetail.getTaxDescId(), organisation).getLookUpCode();
                                if (lookUpCode.equals("PTR")) {
                                    List<TbSrcptFeesDetEntity> lastYearBillCollection = lastYearDemandBillCollection.stream()
                                            .filter(recDetDto -> recDetDto.getBmIdNo().equals(billMas.getBmIdno()))
                                            .collect(Collectors.toList());
                                    List<TbSrcptFeesDetEntity> propBillRec = lastYearBillCollection.stream()
                                            .filter(recDet -> recDet.getBilldetId() != null
                                                    && recDet.getBilldetId().equals(billDet.getBdBilldetid()))
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(propBillRec)) {
                                        if (!(firstBillMas != null && skipFirstYearPenalty.get()
                                                && (firstBillMas.getBmFromdt().compareTo(billMas.getBmFromdt()) >= 0
                                                        && firstBillMas.getBmFromdt()
                                                                .compareTo(billMas.getBmTodt()) <= 0))
                                                && ((!Utility.comapreDates(billMas.getBmFromdt(),
                                                        prevFinYear.getFaFromDate()))
                                                        || (penaltyDateExtended != null
                                                                && Utility.compareDate(penaltyDateExtended,
                                                                        manualReceiptMas.getRmDate())
                                                                && !Utility.comapreDates(penaltyDateExtended,
                                                                        manualReceiptMas.getRmDate())))
                                                && lookUpCode.equals("PTR")) {
                                            arrrearPtCount.getAndIncrement();
                                        }
                                    }
                                }
                            });
                        });
                    }
                }
            }
        }
        LOGGER.info("End --> " + this.getClass().getSimpleName() + " setArrerPtCountForExistBills() method");
        return arrrearPtCount.intValue();
    }

    private FinancialYear getPreviousFinYear(FinancialYear currentFinYear) {
        FinancialYear previousFinYear = null;
        Timestamp currFinYearTimeStamp = new Timestamp(currentFinYear.getFaFromDate().getTime());
        Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime());
        LocalDate convertFinFromDateToLocalDate = currentFinYearDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        currentFinYear.getFaToDate().getTime();
        Date afterSubMonths = Date
                .from(convertFinFromDateToLocalDate.minusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
        previousFinYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
                .getFinanciaYearByDate(afterSubMonths);
        return previousFinYear;
    }

    private Date getPenaltyDateAfterAddedExtendedDaysToPrevFinYear(FinancialYear prevFinYear,
            Organisation organisation) {
        Date dateAfterExtendedDays = null;
        LookUp daysExtdPenaltyLookUp = null;
        try {
            daysExtdPenaltyLookUp = CommonMasterUtility.getValueFromPrefixLookUp("DEP", "DEC", organisation);
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for PME(DEP)");
        }
        if (daysExtdPenaltyLookUp != null && daysExtdPenaltyLookUp.getOtherField() != null) {
            Timestamp prevFinYearTimeStamp = new Timestamp(prevFinYear.getFaToDate().getTime());
            Date prevFinYearDate = new Date(prevFinYearTimeStamp.getTime());

            LocalDate convertFinToDateToLocalDate = prevFinYearDate.toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dateAfterExtendedDays = Date
                    .from(convertFinToDateToLocalDate.plusDays(Long.valueOf(daysExtdPenaltyLookUp.getOtherField()))
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return dateAfterExtendedDays;
    }

    private List<TbBillMas> getUnPaidBills(String propNo, Organisation organisation, int count) {
        List<TbBillMas> billMasData = null;
        if (count > 0) {
            // Form Main Bill table
            billMasData = ApplicationContextProvider.getApplicationContext().getBean(PropertyMainBillService.class)
                    .fetchNotPaidBillForAssessment(propNo, organisation.getOrgid());
        } else {
            // From Provisional Bill Table
            billMasData = ApplicationContextProvider.getApplicationContext().getBean(IProvisionalBillService.class)
                    .fetchNotPaidBillForProAssessment(propNo, organisation.getOrgid());
        }

        return billMasData;
    }

    private List<TbBillMas> getPaidBills(String propNo, Organisation organisation, int count) {
        List<TbBillMas> lastbillMasData = null;
        if (count > 0) {
            lastbillMasData = ApplicationContextProvider.getApplicationContext().getBean(PropertyMainBillService.class)
                    .fetchAllBillByPropNo(propNo, organisation.getOrgid());
        } else {
            lastbillMasData = ApplicationContextProvider.getApplicationContext().getBean(IProvisionalBillService.class)
                    .getProvisionalBillMasByPropertyNo(propNo, organisation.getOrgid());
        }
        return lastbillMasData;
    }
    
    @Override
	public List<BillDisplayDto> fetchAllChargesApplAtReceipt(String propNo,List<TbBillMas> billMasList, Organisation organisation,
			Long deptId, String noticeGenFlag,Long userId, String demandNoticeGenFlag) {
		Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
		List<BillDisplayDto> billDtoList = new ArrayList<BillDisplayDto>();
		Long currFinId = iFinancialYearService.getFinanceYearId(new Date());
		FinancialYear currFinYear = iFinancialYearService.getFinincialYearsById(currFinId,
				organisation.getOrgid());
		TbBillMas lastBillMas = billMasList.get(billMasList.size() - 1);
		boolean demandChargeCalcuCurrYear = false;
    	for (final TbBillDet billDet : lastBillMas.getTbWtBillDet()) {
				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), organisation)
						.getLookUpCode();
				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
					if (billDet.getLmoddate().compareTo(currFinYear.getFaFromDate()) >= 0
							&& billDet.getLmoddate().compareTo(currFinYear.getFaToDate()) <= 0) {
						demandChargeCalcuCurrYear = true;
					}
				}
			}
    	
			if(StringUtils.isNotBlank(demandNoticeGenFlag) && StringUtils.equals(MainetConstants.FlagY, demandNoticeGenFlag)) {
				demandChargeCalcuCurrYear = false;
			}
		WSRequestDTO dto = new WSRequestDTO();
		WSRequestDTO request = new WSRequestDTO();
		List<TbTaxMas> appliAtReceiptTaxList = new ArrayList<TbTaxMas>();
		final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.propPref.RCPT,
				MainetConstants.Property.propPref.CAA, organisation);
		final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
				MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
				organisation);
		List<TbTaxMas> indepenTaxList = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
				taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
		List<TbTaxMas> depenTaxList = tbTaxMasService.fetchAllDepenentTaxes(organisation.getOrgid(), deptId,
				taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
		


		 final LookUp chargeApplicableAtDemand = CommonMasterUtility.getValueFromPrefixLookUp(
		                "DN", PrefixConstants.NewWaterServiceConstants.CAA,
		                organisation);
		List<TbTaxMas> indepenTaxListOfDemand = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
		                taxAppAtBill.getLookUpId(), null, chargeApplicableAtDemand.getLookUpId());
		
		appliAtReceiptTaxList.addAll(indepenTaxList);
		appliAtReceiptTaxList.addAll(depenTaxList);
		appliAtReceiptTaxList.addAll(indepenTaxListOfDemand);
		if (CollectionUtils.isNotEmpty(appliAtReceiptTaxList)) {
			List<AssessNoticeMasterEntity> noticeMasList = ApplicationContextProvider.getApplicationContext()
					.getBean(AssessNoticeMasterRepository.class)
					.getAllNoticeMasterByPropNo(propNo, organisation.getOrgid());
			PropertyRateMasterModel propRateModel = null;
			dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
			WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
			if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
						0);
				propRateModel = (PropertyRateMasterModel) propertyRateMasterList.get(0);
			}
			List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
			for (TbTaxMas tbTaxMas : appliAtReceiptTaxList) {
				LookUp taxApplAt = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxApplicable(), organisation);
				LookUp taxCategory = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory1(),
						organisation);
				LookUp taxSubCategory = null;
				if (tbTaxMas.getTaxCategory2() != null) {
					taxSubCategory = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(),
							organisation);
				}
				if ((noticeGenFlag == null && !StringUtils.equals(taxCategory.getLookUpCode(), PrefixConstants.TAX_CATEGORY.REBATE)
						&& !StringUtils.equals(taxCategory.getLookUpCode(), PrefixConstants.TAX_CATEGORY.INTERST)
						&& ((taxSubCategory == null) || (taxSubCategory != null && !StringUtils
								.equals(taxSubCategory.getLookUpCode(), PrefixConstants.TAX_CATEGORY.SURCHARGE)))) || (StringUtils.equals(noticeGenFlag, MainetConstants.FlagY) && StringUtils.equals(taxCategory.getLookUpCode(), PrefixConstants.TAX_CATEGORY.NOTICE))) {
					TbTaxMasEntity tbMsEntity = new TbTaxMasEntity();
					BeanUtils.copyProperties(tbTaxMas, tbMsEntity);
					
					String demandNoticeGen = "No";
					if (CollectionUtils.isNotEmpty(noticeMasList)
							&& noticeMasList.get(noticeMasList.size() - 1).getMnNotno() != null
							&& noticeMasList.get(noticeMasList.size() - 1).getMnNotno() > 0) {
						demandNoticeGen = "Yes";
					}
					if(StringUtils.isNotBlank(demandNoticeGenFlag) && StringUtils.equals(MainetConstants.FlagY, demandNoticeGenFlag)) {
						demandNoticeGen = "Yes";
					}
					populateRateModelForPropertyLevelReceiptCharges(organisation, taxApplAt, propRateModel, rateMasterList, tbMsEntity, demandNoticeGen, null);
				}
			}
			schWiseMap.put(new Date(), rateMasterList);
			request.setDataModel(schWiseMap);
			WSResponseDTO interestCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
			if (interestCharges != null

					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(interestCharges.getWsStatus())) {
				Map<Date, List<PropertyRateMasterModel>> serviceChargeResponse = castRequestToTaxModelPropertyLevel(
						interestCharges);
				Entry<Date, List<PropertyRateMasterModel>> entry = serviceChargeResponse.entrySet().iterator().next();
				List<PropertyRateMasterModel> serviceChargeList = entry.getValue();

				serviceChargeList.forEach(serviceCharge -> {
					appliAtReceiptTaxList.stream().filter(tax -> tax.getTaxCode().equals(serviceCharge.getTaxCode()))
							.forEach(tax -> {
								if (tax != null && serviceCharge.getTaxValue() > 0) {
									BillDisplayDto billdto = new BillDisplayDto();
									billdto.setTaxDesc(tax.getTaxDesc());
									billdto.setDisplaySeq(tax.getTaxDisplaySeq());
									billdto.setTaxId(tax.getTaxId());
									billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
									billdto.setCurrentYearTaxAmt(
											BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
									billdto.setTaxCategoryId(tax.getTaxCategory1());
									billdto.setParentTaxCode(serviceCharge.getTaxCode());
									billDtoList.add(billdto);
								}
							});
				});
			}
		}
		boolean demandNoticeTaxExist = false;
		if(CollectionUtils.isNotEmpty(billDtoList)) {
    		BillDisplayDto demandNoticeCharges = billDtoList.get(0);
        	for (final TbBillDet billDet : lastBillMas.getTbWtBillDet()) {
    				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), organisation)
    						.getLookUpCode();
    				if (!demandChargeCalcuCurrYear && taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
    					billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					demandNoticeTaxExist = true;
    				}
    			}
        	
    		if(!demandChargeCalcuCurrYear && !demandNoticeTaxExist) {
    			TbBillDet demandTaxDet = new TbBillDet();
    			demandTaxDet.setBmIdno(lastBillMas.getBmIdno());
    			 final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
    	                    "DN", PrefixConstants.NewWaterServiceConstants.CAA,
    	                    organisation);
    			 LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("D",
    						PrefixConstants.LookUpPrefix.TAC, 1, organisation.getOrgid());
    				LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("DN",
    						PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());
    			Long intTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), organisation.getOrgid(),
    					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
    			TbTaxMas demandTax = tbTaxMasService.findById(intTaxId, organisation.getOrgid());
    			boolean demandNoticeExist = false;
    			for (final TbBillDet billDet : lastBillMas.getTbWtBillDet()) {
    				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), organisation)
    						.getLookUpCode();
    				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
    					billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					billMasList.get(billMasList.size() - 1).setBmTotalOutstanding(billMasList.get(billMasList.size() - 1).getBmTotalOutstanding() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					billMasList.get(billMasList.size() - 1).setBmTotalBalAmount(billMasList.get(billMasList.size() - 1).getBmTotalBalAmount() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    	    			billMasList.get(billMasList.size() - 1).setBmTotalAmount(billMasList.get(billMasList.size() - 1).getBmTotalAmount() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
    					demandNoticeExist = true;
    				}
    			}
    			
    			if(!demandNoticeExist) {
    				final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                            MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                            null);
        			demandTaxDet.setTaxId(intTaxId);
        			demandTaxDet.setBdCurTaxamt(demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
        			demandTaxDet.setBdCurBalTaxamt(demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
        			demandTaxDet.setTaxCategory(demandTax.getTaxCategory1());
        			demandTaxDet.setCollSeq(demandTax.getCollSeq());
        			demandTaxDet.setLmoddate(new Date());
        			demandTaxDet.setBdBilldetid(bdBilldetid);
        			demandTaxDet.setUserId(userId);
        			billMasList.get(billMasList.size() - 1).setBmTotalBalAmount(billMasList.get(billMasList.size() - 1).getBmTotalBalAmount() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
        			billMasList.get(billMasList.size() - 1).setBmTotalAmount(billMasList.get(billMasList.size() - 1).getBmTotalAmount() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
        			billMasList.get(billMasList.size() - 1).setBmTotalOutstanding(billMasList.get(billMasList.size() - 1).getBmTotalOutstanding() + demandNoticeCharges.getCurrentYearTaxAmt().doubleValue());
        			billMasList.get(billMasList.size() - 1).getTbWtBillDet().add(demandTaxDet);
    			}
    		}
		}
		return billDtoList;
	}

	private void populateRateModelForPropertyLevelReceiptCharges(Organisation org, LookUp taxAppAt,
			PropertyRateMasterModel propRateModel, List<PropertyRateMasterModel> rateMasterList, TbTaxMasEntity tax,
			String demandNoticeGenFlag, Date manualReceiptDate) {

		PropertyRateMasterModel rateModel = null;
		try {
			rateModel = (PropertyRateMasterModel) propRateModel.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.error(e.getMessage(), e);
			throw new FrameworkException(e);
		}
		
		rateModel.setOrgId(org.getOrgid());
		rateModel.setRateStartDate(new Date().getTime());
		if (manualReceiptDate != null) {
			rateModel.setRateStartDate(manualReceiptDate.getTime());
		}
		rateModel.setTaxCategory(
				CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org).getDescLangFirst());
		if (tax.getTaxCategory2() != null) {
			rateModel.setTaxSubCategory(
					CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org).getDescLangFirst());
		}
		rateModel.setTaxCode(tax.getTaxCode());
		rateModel.setChargeApplicableAt("Demand_Notice");
		rateModel.setDemandNoticeGen(demandNoticeGenFlag);
		rateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
		rateMasterList.add(rateModel);

	}

	@Override
	@Transactional
	public TbSrcptModesDetBean fetchDishonorCharge(TbSrcptModesDetBean recMode,Organisation org, Long deptId) {
		//List<BillDisplayDto> charges = new ArrayList<>();
		LOGGER.info(".......fetchDishonorCharge method start for getting dishonor charge....");
		WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
		WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();

			LookUp penaltyLookup = CommonMasterUtility.getHieLookupByLookupCode(
					MainetConstants.Property.CHEQUE_DISHONR_CHARGES, PrefixConstants.LookUpPrefix.TAC, 2,
					org.getOrgid());
			final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.Property.CHEQUE_DISHONR_CHARGES, MainetConstants.Property.propPref.CAA, org);

			List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
					taxAppAtBill.getLookUpId(), penaltyLookup.getLookUpId());

			if (CollectionUtils.isNotEmpty(taxList)) {
				List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
						0);
				WSRequestDTO request = new WSRequestDTO();
				PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList.get(0);
				List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
				taxList.forEach(tax -> {
					setRateModelForDishonorcharge(org, taxAppAtBill, propRateModel, rateMasterList, tax);
				});

				schWiseMap.put(new Date(), rateMasterList);
				request.setDataModel(schWiseMap);
				WSResponseDTO dishonorCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
				if (dishonorCharges != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(dishonorCharges.getWsStatus())) {
					Map<Date, List<PropertyRateMasterModel>> serviceChargeResponse = castRequestToTaxModelPropertyLevel(
							dishonorCharges);
					Entry<Date, List<PropertyRateMasterModel>> entry = serviceChargeResponse.entrySet().iterator()
							.next();
					List<PropertyRateMasterModel> serviceChargeList = entry.getValue();

					serviceChargeList.forEach(serviceCharge -> {
						taxList.stream().filter(tax -> tax.getTaxCode().equals(serviceCharge.getTaxCode()))
								.forEach(tax -> {
									if (tax != null && serviceCharge.getTaxValue() > 0) {
										recMode.setRdSrChkDisChg(serviceCharge.getTaxValue());
									} else {
										recMode.setRdSrChkDisChg(0d);
									}
								});
					});
				}
			}

		}
		LOGGER.info(".......fetchDishonorCharge method End for getting dishonor charge....");
		return recMode;
	}

	private void setRateModelForDishonorcharge(Organisation org, LookUp taxAppAt, PropertyRateMasterModel propRateModel,
			List<PropertyRateMasterModel> rateMasterList, TbTaxMasEntity tax) {
		PropertyRateMasterModel rateModel = null;
		try {
			rateModel = (PropertyRateMasterModel) propRateModel.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.error(e.getMessage(), e);
			throw new FrameworkException(e);
		}

		rateModel.setOrgId(org.getOrgid());
		rateModel.setRateStartDate(new Date().getTime());
		rateModel.setTaxCategory(
				CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org).getDescLangFirst());
		if (tax.getTaxCategory2() != null) {
			rateModel.setTaxSubCategory(
					CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org).getDescLangFirst());
		}
		rateModel.setTaxCode(tax.getTaxCode());
		rateModel.setChargeApplicableAt(taxAppAt.getDescLangFirst());
		rateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
		rateMasterList.add(rateModel);
	}
	
	private void calculateAlvRvTelescopic(Map<String, ALVMasterModel> alvRvMapRes,ProvisionalAssesmentMstDto sdto){
		AtomicDouble landRate = new AtomicDouble(0);
		double lndrate = 0;
		
		if(sdto.getTppSurveyNumber() != null &&  sdto.getAreaName() != null) {
			Double fetchLandRate = ApplicationContextProvider.getApplicationContext().getBean(AssesmentMstRepository.class).fetchLandRate(sdto.getTppSurveyNumber(), sdto.getAreaName());
			if(fetchLandRate != null) {
				lndrate =fetchLandRate;
			}
			 LOGGER.info("Land Rate " + lndrate);
		}
		landRate.addAndGet(lndrate);
		alvRvMapRes.forEach((yearKey, alvModel) -> {
			if(alvModel.getPropertyTypeLevel1().equals("Land")) {
				telescopicMethodForAlvRvCalculation(alvModel, landRate.doubleValue());
			}
		});
	}
	
	private void telescopicMethodForAlvRvCalculation(ALVMasterModel alvModel, double landRate) {
		double actualArea = alvModel.getTaxableArea();
		double arv = 0;
		arv = calculateArv(actualArea, alvModel.getSlab1(), 0, alvModel.getSlabRate1()/100, landRate, alvModel.getMultiplicationFactor()/100, arv);
		actualArea = actualArea - alvModel.getSlab1();
		arv =  calculateArv(actualArea, alvModel.getSlab2(), alvModel.getSlab1(), alvModel.getSlabRate2()/100, landRate, alvModel.getMultiplicationFactor()/100, arv);
		actualArea = actualArea - (alvModel.getSlab2() - alvModel.getSlab1());
		arv = calculateArv(actualArea, alvModel.getSlab3(), alvModel.getSlab2(), alvModel.getSlabRate3()/100, landRate, alvModel.getMultiplicationFactor()/100, arv);
		actualArea = actualArea - (alvModel.getSlab3() - alvModel.getSlab2());
		arv =  calculateArv(actualArea, alvModel.getSlab4(), alvModel.getSlab3(), alvModel.getSlabRate4()/100, landRate, alvModel.getMultiplicationFactor()/100, arv);
		actualArea = actualArea - (alvModel.getSlab4() - alvModel.getSlab3());
		arv = calculateArv(actualArea, alvModel.getSlab5(), alvModel.getSlab4(), alvModel.getSlabRate5()/100, landRate, alvModel.getMultiplicationFactor()/100, arv);
		alvModel.setArv(arv);
		double tenPerOfArv = (arv * 10) / 100;
		alvModel.setRv(Math.round(arv - tenPerOfArv));
		//alvModel.setSddrRate(landRate);
		alvModel.setArvStdRate(landRate);
	}
	
	private double calculateArv(double actualArea, double currSlab, double previousSlab, double slabRate, double landRate, double multiplicationFactor,double arv) {
		double slab = currSlab - previousSlab;
		if(actualArea > 0 && actualArea >= slab) {
			arv = arv + (slab * slabRate * landRate * multiplicationFactor);
		}else if(actualArea > 0) {
			arv = arv + (actualArea * slabRate * landRate * multiplicationFactor);
		}
		return arv;
	}
	
	@Override
    @Transactional
    public List<BillDisplayDto> fetchEarlyPayRebateRateForGoupProperty(List<TbBillMas> billMasList, Organisation org, Long deptId,
            Date manualDate,String payableAmountMethod,double currentBalanceAmount, double currentAmount, double arrearBalanceAmount, double arrearAmount) {
        List<BillDisplayDto> billDtoList = new ArrayList<BillDisplayDto>();
        if (billMasList != null && !billMasList.isEmpty()) {
            Date tillDate = null;
            if (manualDate == null) {
                tillDate = new Date();
            } else {
                tillDate = manualDate;
            }
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            LookUp rebate = null;
            try {
            	rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", org);

            } catch (Exception e) {
            	LOGGER.error("No prefix found for ENV(RBT)");
            }
			if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
					&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY))
					|| (billMas.getBmDuedate() != null && tillDate.before(billMas.getBmDuedate())
							|| tillDate.equals(billMas.getBmDuedate()))) {

                WSRequestDTO dto = new WSRequestDTO();
                LookUp taxSubCat = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.EARLY_PAY_DISCOUNT,
                        PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
                final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                        org);

                List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                        taxAppAtBill.getLookUpId(), taxSubCat.getLookUpId());

                if (taxList != null && !taxList.isEmpty()) {
                    for (TbTaxMasEntity tax : taxList) {
                        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
                        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
                        if (response != null
                                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
                                    0);
                            WSRequestDTO request = new WSRequestDTO();
                            setRateMasterForRebateForGroupProperty(billMasList, org, billMas, taxAppAtBill, tax,
                                    propertyRateMasterList, request, manualDate,payableAmountMethod,currentBalanceAmount,currentAmount,arrearBalanceAmount,arrearAmount);
                            WSResponseDTO rabateCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
                            if (rabateCharges != null
                                    && MainetConstants.WebServiceStatus.SUCCESS
                                            .equalsIgnoreCase(rabateCharges.getWsStatus())) {
                                Map<Date, List<PropertyRateMasterModel>> rebateResponse = castRequestToTaxModelPropertyLevel(
                                        rabateCharges);
                                Entry<Date, List<PropertyRateMasterModel>> entry = rebateResponse.entrySet().iterator().next();

                                PropertyRateMasterModel rebateResult = entry.getValue().get(0);
                                if (rebateResult.getTaxValue() > 0) {
                                    BillDisplayDto billdto = new BillDisplayDto();
                                    setBillDisplayDto(rebateResult, tax, billdto);
                                    billDtoList.add(billdto);
                                }
                            }
                        }
                    }
                }
            }
        }
        return billDtoList;
    }
	
	private void setRateMasterForRebateForGroupProperty(List<TbBillMas> billMasList, Organisation org, TbBillMas billMas,
            final LookUp taxAppAtBill, TbTaxMasEntity tax, List<Object> propertyRateMasterList, WSRequestDTO request,
            Date manualDate,String payableAmountMethod,double currentBalanceAmount, double currentAmount, double arrearBalanceAmount, double arrearAmount) {
        Map<Date, List<PropertyRateMasterModel>> schWiseMap = new TreeMap<>();
        List<PropertyRateMasterModel> rateMasterList = new ArrayList<>();
        PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList
                .get(0);
        propRateModel.setOrgId(org.getOrgid());
        // propRateModel.setRateStartDate(billMas.getBmFromdt().getTime());
        /////#84422 By Arun
        Date date=Utility.removeTimeFromDatestatic(new Date());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        try {
			Date todayWithZeroTime = formatter.parse(formatter.format(today));
			propRateModel.setRateStartDate(todayWithZeroTime.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        if (manualDate != null) {
        	try {
				Date todayWithZeroTimeForManual = formatter.parse(formatter.format(manualDate));
				propRateModel.setRateStartDate(todayWithZeroTimeForManual.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	
        }
        /////
        propRateModel.setTaxCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(),
                org).getDescLangFirst());
        if (tax.getTaxCategory2() != null) {
            propRateModel.setTaxSubCategory(CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(),
                    org).getDescLangFirst());
        }
        propRateModel.setTaxCode(tax.getTaxCode());
        propRateModel.setChargeApplicableAt(taxAppAtBill.getDescLangFirst());
        propRateModel.setTaxCalculationLevel(MainetConstants.Property.TaxCalculationLevel.PROPERTY);
        propRateModel.setUsageSubtype1(payableAmountMethod);
        if (tax.getParentCode() != null && tax.getParentCode() > 0) {
            TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(tax.getParentCode(), tax.getOrgid());
            propRateModel.setParentTaxCode(tbTax.getTaxCode());
            if (billMasList != null && !billMasList.isEmpty()) {
                // Early payment discount is applied on self occupied rebate deducted amount and set it in totalPropertyTax by
                // apeksha
                List<TbBillDet> billDet = billMasList.get(billMasList.size() - 1).getTbWtBillDet();
                billDet.forEach(det -> {
                    if (det.getTaxId().equals(tax.getParentCode())) {
                    	if(Utility.isEnvPrefixAvailable(org, "HDA") && StringUtils.isNotBlank(payableAmountMethod) && StringUtils.equals(payableAmountMethod, "Half")) {
                    		 propRateModel.setTotalPropertyTax(currentBalanceAmount/2);
                             propRateModel.setTotalPropertyTaxWithoutRebate(currentAmount/2); // without rebate
                             propRateModel.setTotalArrearPropertyTaxWithoutRebate(arrearAmount/2); // arrear without rebate
                             propRateModel.setTotalArrearPropertyTaxWithRebate(arrearBalanceAmount/2);// arrear with rebate
                    	}else {
                    		 propRateModel.setTotalPropertyTax(currentBalanceAmount);
                             propRateModel.setTotalPropertyTaxWithoutRebate(currentAmount); // without rebate
                             propRateModel.setTotalArrearPropertyTaxWithoutRebate(arrearAmount); // arrear without rebate
                             propRateModel.setTotalArrearPropertyTaxWithRebate(arrearBalanceAmount);// arrear with rebate
                    	}
                    }
                });
            }
        }
        double totalDemandAmount = 0;
        for (TbBillMas bill : billMasList) {
            totalDemandAmount = totalDemandAmount + bill.getBmTotalAmount();
        }
        TbBillMas bill = billMasList.get(billMasList.size() - 1);
        propRateModel.setTotalDemandWithRebate(bill.getBmTotalOutstanding());
        propRateModel.setTotalDemandWithoutRebate(totalDemandAmount);
        double demandTax = 0;

        for (TbBillDet billdet : billMas.getTbWtBillDet()) {
            if (billdet.getTaxCategory() != null) {
                if (PrefixConstants.TAX_CATEGORY.DEMAND.equals(CommonMasterUtility
                        .getHierarchicalLookUp(billdet.getTaxCategory(), org).getLookUpCode())) {
                    demandTax = demandTax + billdet.getBdCurBalTaxamt();
                }
            }

        }
        propRateModel.setParentTaxValue(currentBalanceAmount);
        rateMasterList.add(propRateModel);
        schWiseMap.put(billMas.getBmFromdt(), rateMasterList);
        request.setDataModel(schWiseMap);
    }
	
	 private Map<Date, List<BillPresentAndCalculationDto>> prepareScheduleWisePreCalMapForSkdcl(
	            Map<Date, List<PropertyRateMasterModel>> schMap, Organisation organisation,
	            Map<Long, BillDisplayDto> demandLevelRebate,ProvisionalAssesmentMstDto sdto, List<Long> finYearList) {
	       LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " prepareScheduleWisePreCalMap() method");
	       FinancialYear financialYear = iFinancialYearService.getFinincialYearsById(finYearList.get(0),
                   organisation.getOrgid());
	        Map<Date, List<BillPresentAndCalculationDto>> schWiseMap = new TreeMap<>();
	        Map<String, TbTaxMas> taxMap = new HashMap<>();
	        schMap.forEach((schDate, taxList) -> {
	            List<BillPresentAndCalculationDto> dtoList = new ArrayList<>();
	            taxList.forEach(tax -> {
	                TbTaxMas tbTax = taxMap.get(tax.getTaxCode());
	                if (tbTax == null) {
	                		 tbTax = tbTaxMasService.findTaxDetails(tax.getOrgId(), tax.getTaxCode());
	                         taxMap.put(tbTax.getTaxCode(), tbTax);
	                }
	                BillPresentAndCalculationDto dto = new BillPresentAndCalculationDto();
	                if(Utility.comapreDates(schDate, financialYear.getFaFromDate()) && !Utility.comapreDates(sdto.getAssAcqDate(), financialYear.getFaFromDate())) {
	                	if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
	                		LocalDate localDate = sdto.getAssAcqDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		                	int acquisitionMonth = localDate.getMonthValue();
		                	if(acquisitionMonth >= 4 && acquisitionMonth <=9) {
		                		setBillCalDto(tax.getTaxValue(), tbTax, dto, tax, organisation, dtoList, demandLevelRebate);
		                	}else {
		                		setBillCalDtoForSkdcl(tax.getTaxValue(), tbTax, dto, tax, organisation, dtoList, demandLevelRebate,sdto, financialYear,schDate);
		                	}
	                	}else {
	                		setBillCalDtoForSkdcl(tax.getTaxValue(), tbTax, dto, tax, organisation, dtoList, demandLevelRebate,sdto, financialYear,schDate);
	                	}
	                }else {
	                	setBillCalDto(tax.getTaxValue(), tbTax, dto, tax, organisation, dtoList, demandLevelRebate);
	                }
	            });
	            schWiseMap.put(schDate, dtoList);
	            dtoList.sort(Comparator.comparing(BillPresentAndCalculationDto::getTaxSequenceId));// Sorting List by collection
	                                                                                               // sequence
	        });
	        LOGGER.info("End--> " + this.getClass().getSimpleName() + " prepareScheduleWisePreCalMap() method");
	        return schWiseMap;
	    }
	 
	 private void setBillCalDtoForSkdcl(Double charges, TbTaxMas tax,
	            BillPresentAndCalculationDto billCalDto, PropertyRateMasterModel rateTaxModel, Organisation organisation,
	            List<BillPresentAndCalculationDto> dtoList, Map<Long, BillDisplayDto> demandLevelRebate,ProvisionalAssesmentMstDto sdto,FinancialYear financialYear,Date schDate) {
	        List<String> taxCat = Arrays.asList(PrefixConstants.TAX_CATEGORY.INTERST, PrefixConstants.TAX_CATEGORY.REBATE,
	                PrefixConstants.TAX_CATEGORY.EXEMPTION);
	        final String taxCode = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), organisation)
	                .getLookUpCode();
	        Long diffBetweenAcqDateAndFinToDate = Long.valueOf(Utility.getDaysBetweenDates(sdto.getAssAcqDate(),
	        		financialYear.getFaToDate()));
	        
	        if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
	        	Date addMonths = DateUtils.addMonths(schDate, 6);
	        	diffBetweenAcqDateAndFinToDate = Long.valueOf(Utility.getDaysBetweenDates(addMonths,
		        		financialYear.getFaToDate()));
	        }
	        		
	        double baseRate = 0d;
	        if (charges > 0
	                || (taxCat.contains(taxCode) && (rateTaxModel.getFlatRate() > 0 || rateTaxModel.getPercentageRate() > 0))) {
	            LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
	                    Long.valueOf(tax.getTaxMethod()), organisation);
	            if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE)) {
	                baseRate = rateTaxModel.getPercentageRate() / 100;
	            } else if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.FLAT)) {
	                baseRate = rateTaxModel.getFlatRate();
	            }
	            double perDayChages = charges / 365;
	            double finalCharges = perDayChages * diffBetweenAcqDateAndFinToDate;
	            billCalDto.setTaxId(tax.getTaxId());
	            if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
	            	if(taxCat.contains(taxCode)) {
	            		billCalDto.setChargeAmount(finalCharges);
	            	}else {
	            		billCalDto.setChargeAmount(Math.ceil(finalCharges));
	            	}
	            }else {
	            	billCalDto.setChargeAmount(Math.round(finalCharges));
	            }
	            billCalDto.setTaxCode(tax.getTaxCode());
	            billCalDto.setTaxId(tax.getTaxId());
	            billCalDto.setChargeDescEng(tax.getTaxDesc());
	            billCalDto.setTaxCategoryId(tax.getTaxCategory1());
	            billCalDto.setTaxSubCategoryId(tax.getTaxCategory2());
	            billCalDto.setDisplaySeq(tax.getTaxDisplaySeq());
	            billCalDto.setTaxSequenceId(tax.getCollSeq());
	            billCalDto.setParentCode(tax.getParentCode());
	            billCalDto.setTaxMethod(tax.getTaxMethod());
	            billCalDto.setRate(baseRate);
	            billCalDto.setRuleId(rateTaxModel.getRuleId());
	            billCalDto.setPercentageRate(rateTaxModel.getPercentageRate());
	            dtoList.add(billCalDto);

	            Long count = dtoList.stream().filter(o -> o.getTaxCode().equals(tax.getTaxCode())).count();
	            if (count > 1) {
	                throw new FrameworkException("configuration issue same tax at property and unit level in BRMS sheet");
	            }
	            setDemandLevelRebateMap(finalCharges, tax, demandLevelRebate, taxCode);
	        }

	    }

	 private Map<String, Double>  calculateFatorValueForApartment(final Organisation org, List<String> wardZone, ALVMasterModel alvModelInit,
	             List<String> usageType, String finAndUnitId,
	             ProvisionalAssesmentMstDto sdto, boolean isPropertyMixed, List<String> propertyType,
	            boolean isConstruTypeMixed, boolean isDueDatePassOrNot, boolean isOccupancyTypeMixed,
	            FinancialYear financialYear, String demandGenFlag,boolean apartmentLevelCalculationAppl,Long finYear, Long deptId, List<Long> finYearList) {
		 Map<String, Double> totalAlvRvMap = new HashMap<String, Double>();
		 AtomicDouble totalArv = new AtomicDouble(0);
		 AtomicDouble totalRv = new AtomicDouble(0);
		 AtomicDouble totalArea = new AtomicDouble(0);
		 Map<String, ALVMasterModel> alvRvMap = new HashMap<>();// <propNo_fin_unitId, ALVModel>
		 if(StringUtils.isNotBlank(sdto.getParentPropNo())) {
			 List<ProvisionalAssesmentMstDto> assementMastByParentPropNo = assesmentMastService.fetchAssessmentMasterByParentPropNo(sdto.getParentPropNo());
			 if(CollectionUtils.isNotEmpty(assementMastByParentPropNo)) {
				 
					 assementMastByParentPropNo.forEach(assesmentDto ->{
						 
						  List<String> wardZoneLevel = setDeptWardZone(assesmentDto, org, deptId);
						  Map<Long, AtomicDouble> yearWiseTotalBuiltUpArea = yearWiseTotalBuiltUpArea(assesmentDto.getProvisionalAssesmentDetailDtoList(), finYearList);
						  AtomicDouble totalBuiltUpArea = yearWiseTotalBuiltUpArea.get(finYear);
						 assesmentDto.getProvisionalAssesmentDetailDtoList().forEach(unitDetail->{
							 usageType.clear();
							 propertyType.clear();
							 setUsageTypeAndPropertyType(unitDetail, org, usageType, propertyType);
							 Date unitStartDate = null;
							 if (unitDetail.getAssdYearConstruction() != null) {
				                    unitStartDate = (unitDetail.getAssdYearConstruction().after(financialYear.getFaFromDate())
				                            ? unitDetail.getAssdYearConstruction()
				                            : financialYear.getFaFromDate());
				                } else {
				                    unitStartDate = financialYear.getFaFromDate();
				                }
							 String propFinYearId = assesmentDto.getAssNo() + finYear+ MainetConstants.operator.UNDER_SCORE + unitDetail.getAssdUnitNo();
							 totalArea.addAndGet(unitDetail.getAssdBuildupArea());
							createAlvRvModel(org, wardZoneLevel, alvModelInit, alvRvMap, unitDetail, usageType,
									propFinYearId, unitStartDate, assesmentDto, isPropertyMixed, propertyType,
									isConstruTypeMixed, isDueDatePassOrNot, totalBuiltUpArea, isOccupancyTypeMixed,
									financialYear, demandGenFlag, false,null);
						 });
					 });
				 
					WSRequestDTO alvRvRequest = new WSRequestDTO();
			        alvRvRequest.setDataModel(alvRvMap);
			        WSResponseDTO alvRvResult = RestClient.callBRMS(alvRvRequest, ServiceEndpoints.Property.PROP_ALV);
			        Map<String, ALVMasterModel> alvModelResponse = castRequestToAlvCvModel(alvRvResult);
			        if (alvRvResult.getResponseObj() != null) {
			        alvModelResponse.forEach((propNo, AlvModel)->{
			        	totalArv.addAndGet(AlvModel.getArv());
			        	totalRv.addAndGet(AlvModel.getRv());
			        });
			        	totalAlvRvMap.put("ARV", totalArv.get());
			        	totalAlvRvMap.put("RV", totalArv.get()*0.9);
			        	totalAlvRvMap.put("TotalArea", totalArea.get());
			        
				 }
			 }
		 }
		 return totalAlvRvMap;
	 }
	 
	 @Override
	    @Transactional
	    public List<BillDisplayDto> fetchEarlyPayRebateRateForBackPosted(List<TbBillMas> billMasList, Organisation org, Long deptId,
	            Date manualDate,String payableAmountMethod) {
	        List<BillDisplayDto> billDtoList = new ArrayList<BillDisplayDto>();
	        if (billMasList != null && !billMasList.isEmpty()) {
	            Date tillDate = null;
	            if (manualDate == null) {
	                tillDate = new Date();
	            } else {
	                tillDate = manualDate;
	            }
	            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
	            LookUp rebate = null;
	            try {
	            	rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", org);

	            } catch (Exception e) {
	            	LOGGER.error("No prefix found for ENV(RBT)");
	            }
				if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
						&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY))
						|| (billMas.getBmDuedate() != null && tillDate.before(billMas.getBmDuedate())
								|| tillDate.equals(billMas.getBmDuedate()))) {

	                WSRequestDTO dto = new WSRequestDTO();
	                LookUp taxSubCat = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.EARLY_PAY_DISCOUNT,
	                        PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
	                final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
	                        MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
	                        org);

	                List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
	                        taxAppAtBill.getLookUpId(), taxSubCat.getLookUpId());

	                if (taxList != null && !taxList.isEmpty()) {
	                    for (TbTaxMasEntity tax : taxList) {
	                        dto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
	                        WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
	                        if (response != null
	                                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
	                            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class,
	                                    0);
	                            WSRequestDTO request = new WSRequestDTO();
	                            setRateMasterForRebate(billMasList, org, billMas, taxAppAtBill, tax,
	                                    propertyRateMasterList, request, manualDate,payableAmountMethod);
	                            WSResponseDTO rabateCharges = RestClient.callBRMS(request, ServiceEndpoints.Property.PROP_LEVEL_RATE);
	                            if (rabateCharges != null
	                                    && MainetConstants.WebServiceStatus.SUCCESS
	                                            .equalsIgnoreCase(rabateCharges.getWsStatus())) {
	                                Map<Date, List<PropertyRateMasterModel>> rebateResponse = castRequestToTaxModelPropertyLevel(
	                                        rabateCharges);
	                                Entry<Date, List<PropertyRateMasterModel>> entry = rebateResponse.entrySet().iterator().next();

	                                PropertyRateMasterModel rebateResult = entry.getValue().get(0);
	                                if (rebateResult.getPercentageRate() > 0) {
	                                    BillDisplayDto billdto = new BillDisplayDto();
	                                    setBillDisplayDtoForBackPosted(rebateResult, tax, billdto);
	                                    billDtoList.add(billdto);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        return billDtoList;
	    }
	 
	 private void setBillDisplayDtoForBackPosted(PropertyRateMasterModel serviceCharge, TbTaxMasEntity tax, BillDisplayDto billdto) {
	        Organisation organisation = new Organisation(tax.getOrgid());
	        billdto.setTaxDesc(tax.getTaxDesc());
	        billdto.setDisplaySeq(tax.getTaxDisplaySeq());
	        billdto.setTaxId(tax.getTaxId());
	        billdto.setParentTaxId(tax.getParentCode());
	        serviceCharge.setTaxValue(serviceCharge.getTotalPropertyTaxWithoutRebate() * (serviceCharge.getPercentageRate()/100));
	        LookUp roundOff = null;
	        try {
	            roundOff = CommonMasterUtility.getValueFromPrefixLookUp("NR", "RND", organisation);

	        } catch (Exception e) {

	        }
	        if (roundOff != null && roundOff.getOtherField() != null
	                && org.apache.commons.lang.StringUtils.equals(roundOff.getOtherField(), "RF")) {
	            billdto.setTotalTaxAmt(BigDecimal.valueOf(serviceCharge.getTaxValue()));
	            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(serviceCharge.getTaxValue()));
	        } else if (roundOff != null && roundOff.getOtherField() != null
	                && org.apache.commons.lang.StringUtils.equals(roundOff.getOtherField(), "NF")) {
	            billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
	            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
	        } else {
	            billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
	            billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(serviceCharge.getTaxValue())));
	        }

	        billdto.setTaxCategoryId(tax.getTaxCategory1());
	        if (org.apache.commons.lang.StringUtils.isNotBlank(serviceCharge.getParentTaxCode())) {
	            billdto.setParentTaxCode(serviceCharge.getParentTaxCode());
	        }
	        billdto.setPercentageRate(serviceCharge.getPercentageRate());
	    }

}
