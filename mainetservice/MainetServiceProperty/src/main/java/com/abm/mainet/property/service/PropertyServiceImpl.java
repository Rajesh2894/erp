package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.dto.PropertyBillGenerationMap;
import com.abm.mainet.bill.service.BillGenerationService;
import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.bill.service.ChequeDishonorService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dao.IEmployeeDAO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.datamodel.PropertyRateMasterModel;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PropertyBillExceptionDto;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.property.repository.ProvisionalBillRepository;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@Service(value = "PropertyMasterService")
public class PropertyServiceImpl implements PropertyService, BillPaymentService, BillGenerationService {
    private static final Logger LOGGER = Logger.getLogger(PropertyServiceImpl.class);
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Autowired
    private TbFinancialyearService tbFinancialyearService;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Resource
    private IChallanService iChallanService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private PropertyTransferService propertyTransferService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private ChequeDishonorService chequeDishonorService;

    @Autowired
    private PropertyPenltyService propertyPenltyService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;
   
    @Autowired
    private PropertyBillPaymentService PropertyBillPaymentService;

    @Autowired
    GroupMasterService groupMasterService;

    @Autowired
    IEmployeeDAO empService;

    @Autowired
    AssesmentMastService assMstService;
    
    @Autowired
    private  ReceiptRepository receiptRepository;
    
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private PropertyBillExceptionService propertyBillExceptionService;
    
    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;
    
    @Autowired
    private AssesmentMstRepository assesmentMstRepository;
   
    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;
    
    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;
    
    @Autowired
    private ProvisionalBillRepository provisionalBillRepository;
      
    @Override
    public String getPropertyNo(Long orgId, ProvisionalAssesmentMstDto mastDto) {

        SequenceConfigMasterDTO configMasterDTO = null;

        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.PropMaster.PropShortType,
                PrefixConstants.STATUS_ACTIVE_PREFIX);

        configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
                MainetConstants.Property.TB_AS_PRO_ASSE_MAST, MainetConstants.Property.PRO_PROP_NO);
        
        Organisation organization = iOrganisationService.getOrganisationById(orgId);
        LookUp lookUp=null;
        	try {
        		lookUp =CommonMasterUtility.getValueFromPrefixLookUp("PNG", "ENV", organization);
		    } catch (Exception e) {
		        LOGGER.error("No prefix found for ENV - PNG ", e);
		    }
        if(lookUp != null && lookUp.getOtherField()!=null && StringUtils.isNotBlank(lookUp.getOtherField()) &&
        		StringUtils.equals(lookUp.getOtherField(), MainetConstants.FlagY)) {
        		
			final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
					MainetConstants.Property.TB_AS_PRO_ASSE_MAST, MainetConstants.Property.PRO_PROP_NO, orgId,
					MainetConstants.FlagC, null);
			final String propNo = sequence.toString();
			final String paddingPropNo = String.format("%06d", Integer.parseInt(propNo));
			
			String Code1 = CommonMasterUtility.getHierarchicalLookUp(mastDto.getAssWard1(), orgId).getLookUpCode();
			String Code2 = CommonMasterUtility.getHierarchicalLookUp(mastDto.getAssWard2(), orgId).getLookUpCode();
			StringBuffer sequenceNo=new StringBuffer();
			sequenceNo.append(Code1);
			sequenceNo.append(Code2);
			sequenceNo.append("0");
			sequenceNo.append(paddingPropNo);
			sequenceNo.append("00");
			return sequenceNo.toString();
        }else {
        
	        if (configMasterDTO.getSeqConfigId() == null) {
	        	Organisation org = iOrganisationService.getOrganisationById(orgId);
	            final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
	                    MainetConstants.Property.TB_AS_PRO_ASSE_MAST, MainetConstants.Property.PRO_PROP_NO, orgId,
	                    MainetConstants.FlagC, null);
	            final String propNo = sequence.toString();
	            final String paddingPropNo = String.format(MainetConstants.CommonMasterUi.CD, Integer.parseInt(propNo));
	            return MainetConstants.Property.PROP_NO_CHAR.concat(org.getOrgShortNm()).concat(paddingPropNo)
	                    .concat(MainetConstants.Property.Tripe_Zero);
	        	
	        } else {
	            CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
	            if ((mastDto.getProvisionalAssesmentDetailDtoList()).size() <= 1) {
	                for (ProvisionalAssesmentDetailDto assesmentDetailDto : mastDto
	                        .getProvisionalAssesmentDetailDtoList()) {
	                    commonDto.setUsageId1(assesmentDetailDto.getAssdUsagetype1());
	                    commonDto.setUsageCtrlId(1L);
	                    break;
	                }
	            } else {
	                List<Long> usageIds = new ArrayList<>();
	                for (ProvisionalAssesmentDetailDto assesmentDetailDto : mastDto
	                        .getProvisionalAssesmentDetailDtoList()) {
	                    usageIds.add(assesmentDetailDto.getAssdUsagetype1());
	                    commonDto.setUsageId1(assesmentDetailDto.getAssdUsagetype1());
	                }	
	                commonDto.setUsageCtrlId(0L);
	                commonDto.setUsageIds(usageIds);
	            }
	            commonDto.setLevel1Id(mastDto.getAssWard1());
	            commonDto.setLevel2Id(mastDto.getAssWard2());
	            if (!Utility.isEnvPrefixAvailable(organization, MainetConstants.ENV_PSCL)) {
	            	commonDto.setLevel3Id(mastDto.getAssWard3());
	            	commonDto.setLevel4Id(mastDto.getAssWard4());
		            commonDto.setLevel5Id(mastDto.getAssWard5());
	            }
	            commonDto.setServiceCode(MainetConstants.Property.DES);
	            return seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
	        }
        }
    }

    @Override
    @Transactional
    public List<BillReceiptPostingDTO> updateBillMasterAmountPaid(final String propertyNo, Double amount,
            final Long orgId, final Long userId, String ipAddress, Date manualReceptDate,String flatNo) {// Advance Amount Pending
    	LOGGER.info("Method parameters of updateBillMasterAmountPaid"+" "+"propno"+" "+propertyNo +" "+"orgid"+ orgId);
    	List<BillReceiptPostingDTO> result = new ArrayList<>();
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		final Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		final Organisation org = new Organisation();
		final double actualPayAmt = amount;
		org.setOrgid(orgId);
		List<TbBillMas> billMasData = null;
		LookUp billMethod = null;
		LookUp billingMethodLookUp = null;
		try {
			billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
		}
		int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
		if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
				&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
			Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propertyNo, orgId);
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
					UserSession.getCurrent().getOrganisation());
		}
		if (billingMethodLookUp != null
				&& StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
			if (count > 0) {
				// Form Main Bill table
				billMasData = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(propertyNo, orgId, flatNo);
			} else {
				// From Provisional Bill Table
				billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessment(propertyNo, orgId);
			}
		} else {
			if (count > 0) {
				// Form Main Bill table
				billMasData = propertyMainBillService.fetchNotPaidBillForAssessment(propertyNo, orgId);
			} else {
				// From Provisional Bill Table
				if (!Utility.isEnvPrefixAvailable(org, "PSCL")) {
					billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessment(propertyNo, orgId);
				}
			}
		}

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
				MainetConstants.STATUS.ACTIVE);
		if ((billMasData != null) && !billMasData.isEmpty()) {
			propertyMainBillService.copyDataFromMainBillDetailToHistory(billMasData, "P",userId,ipAddress);
			propertyBRMSService.fetchInterstRate(billMasData, org, deptId);
			LookUp penalIntLookUp = null;
			try {
				penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", org);
			} catch (Exception e) {
				LOGGER.error("No Prefix found for ENV(PIT)");
			}
			if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
					&& StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
				
				billMasterCommonService.calculatePenaltyInterest(billMasData, org, deptId, MainetConstants.FlagY,
						manualReceptDate, MainetConstants.FlagN, MainetConstants.FlagY, userId);
				 
			}else if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
				List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
				List<ProvisionalAssesmentMstDto> assMst = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, propertyNo, null);
				updatingPendingReceiptData(null, orgId, userId, 0, deptId, org, assMst.get(assMst.size() -1), billMasData, provBillList, false,MainetConstants.FlagY);
				billMasterCommonService.calculateInterestForPrayagRaj(billMasData, org, deptId, MainetConstants.FlagY, manualReceptDate, userId,billMasData,null);
			}
			else {
				billMasterCommonService.calculateMultipleInterest(billMasData, org, deptId, MainetConstants.Y_FLAG,
						null);
			}
			
			claculateDemandNoticeGenCharges(billMasData, org, deptId, userId);
			Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
			PropertyPenltyDto currentFinYearPenaltyDto = propertyPenltyService.calculateExistingSurcharge(propertyNo,
					finYearId, orgId);
			BillDisplayDto surCharge = calculateSurcharge(org, deptId, billMasData, propertyNo,
					MainetConstants.Property.SURCHARGE, finYearId, manualReceptDate);
			if (currentFinYearPenaltyDto == null && surCharge != null && actualPayAmt > 0
					&& surCharge.getTotalTaxAmt().doubleValue() > 0) {
				double pendingSurcharge = surCharge.getTotalTaxAmt().doubleValue();
				amount -= surCharge.getTotalTaxAmt().doubleValue();
				double amountPaidSurcharge = actualPayAmt - amount;
				pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
				if (amount >= 0) {
					propertyPenltyService.savePropertyPenlty(propertyNo, surCharge.getFinYearId(), orgId, userId,
							ipAddress, surCharge.getTotalTaxAmt().doubleValue(), pendingSurcharge,null);
				}
			} else {
				if (currentFinYearPenaltyDto != null && currentFinYearPenaltyDto.getPendingAmount() > 0) {
					surCharge = new BillDisplayDto();
					LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(
							MainetConstants.Property.SURCHARGE, PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
					final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
							MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA, org);

					List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(),
							deptId, taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
					surCharge.setTotalTaxAmt(new BigDecimal(currentFinYearPenaltyDto.getPendingAmount()));
					surCharge.setTaxId(taxList.get(0).getTaxId());
					surCharge.setTaxCategoryId(taxList.get(0).getTaxCategory1());
					double pendingSurcharge = currentFinYearPenaltyDto.getPendingAmount();
					amount -= currentFinYearPenaltyDto.getPendingAmount();
					double amountPaidSurcharge = actualPayAmt - amount;
					pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
					if (pendingSurcharge < 0) {
						pendingSurcharge = 0.0;
					}
					currentFinYearPenaltyDto.setPendingAmount(pendingSurcharge);
					propertyPenltyService.updatePropertyPenalty(currentFinYearPenaltyDto, ipAddress, userId);
				}
			}

			final TbBillMas last = billMasData.get(billMasData.size() - 1);
			List<BillReceiptPostingDTO> rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
			List<BillDisplayDto> rebateDtoList = null;
			if (last.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {

				Date currDate = new Date();
				if(manualReceptDate != null) {
					currDate = manualReceptDate;
				}
				Long payFinYearId = iFinancialYearService.getFinanceYearId(currDate);
				FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
						.getBean(IFinancialYearService.class).getFinincialYearsById(payFinYearId, orgId);
				Long noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYear(propertyNo,
						currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
				Organisation organisation = new Organisation();
				organisation.setOrgid(orgId);
				LookUp rebate = null;
				try {
					rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", organisation);

				} catch (Exception e) {
					LOGGER.error("No prefix found for ENV(RBT)");
				}
				String paymentMethodType = null;
				double receiptAmount = 0.0;
				Long rebateReceivedCount = 0L;
				if(rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
						&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
					paymentMethodType = billMasterCommonService.getFullPaymentOrHalfPayRebate(billMasData,org);
					BigDecimal totalReceiptAmount = iReceiptEntryService.getReceiptAmountPaidByPropNoOrFlatNo(propertyNo, flatNo, org, deptId);
					if(totalReceiptAmount != null) {
						receiptAmount = totalReceiptAmount.doubleValue();
					}
					rebateReceivedCount = PropertyBillPaymentService.getRebateReceivedCount(propertyNo, flatNo,
							currDate, currentFinYearDto, organisation, deptId);
					last.setBmToatlRebate(0.0);
				}
				if (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear <= 0) {
					LookUp billDeletionInactive = null;
					try {
						billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
					} catch (Exception e) {
						LOGGER.error("No prefix found for ENV - BDI");
					}
					if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
							&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
						noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYearAdvance(
								propertyNo, currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
					}
				}
				if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
						&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
						&& billMasterCommonService.checkRebateAppl(billMasData,org) && rebateReceivedCount <= 0 && last.getBmYear().equals(finYearId))
						|| (rebate == null && ((last.getBmToatlRebate() > 0)
								|| (last.getBmToatlRebate() == 0 && noOfBillsPaidInCurYear == null
										|| noOfBillsPaidInCurYear == 0))
								&& last.getBmLastRcptamt() == 0.0)  || (rebate == null && Utility.isEnvPrefixAvailable(organisation, "PSCL"))) {

					rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasData, org, deptId,
							manualReceptDate, paymentMethodType);
					double rebateAmount = 0.0;

					// #107759 By Arun
					Double bmTotalOutStandingAmt = 0.0;
					LookUp userChargesLookUp = null;
					try {
						userChargesLookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.EUC,
								MainetConstants.ENV, organisation);
					} catch (Exception e) {
						LOGGER.error("No prefix found for ENV - EUC");
					}
					for (BillDisplayDto billDisplayDto : rebateDtoList) {
						rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
					}

					if (CollectionUtils.isNotEmpty(rebateDtoList)) {
						double roundedRebateAmt = 0;
						if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
							roundedRebateAmt = Math.ceil(rebateAmount);
						}else {
							roundedRebateAmt = Math.round(rebateAmount);
						}
						if (last.getBmToatlRebate() > 0) {
							roundedRebateAmt = last.getBmToatlRebate();
						}
						// Exclude user charges
						if (userChargesLookUp != null
								&& MainetConstants.Y_FLAG.equals(userChargesLookUp.getOtherField())) {
							for (TbBillDet billDet : last.getTbWtBillDet()) {
								LookUp taxCat = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(),
										organisation);
								if (StringUtils.equals(taxCat.getLookUpCode(), MainetConstants.FlagN)) {
									for (BillDisplayDto billDisplayDto : rebateDtoList) {
										TbTaxMas taxMas = tbTaxMasService.findTaxDetails(orgId,
												billDisplayDto.getParentTaxCode());
										if (billDet.getTaxId().equals(taxMas.getTaxId())) {
											bmTotalOutStandingAmt = bmTotalOutStandingAmt
													+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
										}
									}
								} else {
									bmTotalOutStandingAmt = bmTotalOutStandingAmt
											+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
								}
							}
							bmTotalOutStandingAmt = bmTotalOutStandingAmt - roundedRebateAmt;
						} else {
							bmTotalOutStandingAmt = last.getBmTotalOutstanding() - roundedRebateAmt;
						}
						double paidAmount = amount.doubleValue();
						if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
								&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
							paidAmount = paidAmount + receiptAmount;
							bmTotalOutStandingAmt = billMasterCommonService.getFullPayableOutstanding(billMasData, organisation,null) - roundedRebateAmt;
						}
						if ((paidAmount >= bmTotalOutStandingAmt)) {
							Long iterator = 1l;
							double checkAmt = 0d;
							for (BillDisplayDto rebateDto : rebateDtoList) {
								BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
								// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

								billDetails.put(rebateDto.getTaxId(), null);
								rebateTax.setBillMasId(last.getBmIdno());
								double correctTax = 0;
								if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
									correctTax = rebateDto.getTotalTaxAmt().doubleValue();
								}else {
									correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
								}
								double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
								checkAmt += roundedTax;
								if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && (rebateDtoList.size() == iterator)) {
									// if equal than check rebateAmt is equal to checkAmt
									if (roundedRebateAmt != checkAmt) {
										double testAmt = roundedRebateAmt - checkAmt;
										if (testAmt > 0) {
											// + testAmt
											correctTax += testAmt;
										} else {
											// -testAmt
											correctTax += testAmt;
										}
									}
								}
								iterator++;
								rebateTax.setTaxAmount(correctTax);
								details.put(rebateDto.getTaxId(), correctTax);
								// rebateTax.setTaxAmount(rebateDto.getTotalTaxAmt().doubleValue());
								rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
								rebateTax.setTaxId(rebateDto.getTaxId());
								rebateTax.setYearId(last.getBmYear());
								if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
									last.setBmToatlRebate(Math.ceil(rebateAmount));
								}else {
									last.setBmToatlRebate(Math.round(rebateAmount));
								}
								if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
									ArrayList<Double> taxValueIdList = new ArrayList<Double>();
									// taxValueIdList.add(rebateDto.getTotalTaxAmt().doubleValue());
									taxValueIdList.add(correctTax);
									taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
									last.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
								}
								rebateTaxList.add(rebateTax);
							}

						}
						else if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
								&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY) && last.getBmYear().equals(finYearId)) {
							Map<String, Object> checkHalfPaymentRebateAppl = checkHalfPaymentRebateAppl(billMasData,
									org, deptId, manualReceptDate, paymentMethodType, amount.doubleValue(),receiptAmount);
							Date secondSemStartDate = PropertyBillPaymentService.getSecondSemStartDate(propertyNo,
									flatNo, currDate, currentFinYearDto, organisation, deptId);
							if((boolean) checkHalfPaymentRebateAppl.get("halfPayApplicable") && Utility.compareDate(currDate,secondSemStartDate)) {
								checkHalfPaymentRebateAppl.get("rebateList");
								@SuppressWarnings("unchecked")
								List<BillDisplayDto> halfRebateDtoList = (List<BillDisplayDto>) checkHalfPaymentRebateAppl.put("rebateList", rebateDtoList);
								double totalRebateAmount = (double) checkHalfPaymentRebateAppl.get("totalRebate");
								roundedRebateAmt = totalRebateAmount;
								Long iterator = 1l;
								double checkAmt = 0d;
								rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
								for (BillDisplayDto rebateDto : halfRebateDtoList) {
									BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
									// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

									billDetails.put(rebateDto.getTaxId(), null);
									rebateTax.setBillMasId(last.getBmIdno());
									double correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
									double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
									checkAmt += roundedTax;
									if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && (rebateDtoList.size() == iterator)) {
										if (roundedRebateAmt != checkAmt) {
											double testAmt = roundedRebateAmt - checkAmt;
											if (testAmt > 0) {
												correctTax += testAmt;
											} else {
												correctTax += testAmt;
											}
										}
									}
									iterator++;
									rebateTax.setTaxAmount(correctTax);
									details.put(rebateDto.getTaxId(), correctTax);
									rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
									rebateTax.setTaxId(rebateDto.getTaxId());
									rebateTax.setYearId(last.getBmYear());
									last.setBmToatlRebate(Math.round(totalRebateAmount));
									if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
										ArrayList<Double> taxValueIdList = new ArrayList<Double>();
										taxValueIdList.add(correctTax);
										taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
										last.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
									}
									rebateTaxList.add(rebateTax);
								}
							}
						}else {
							last.setBmToatlRebate(0.0);
						}
					}

				}

				double totalDemand = last.getBmTotalOutstanding();
				if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
					createBillDetWhereFirstBillHaveArrearAmount(billMasData);
				}
				billMasData.forEach(billMas -> {
					billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
				});
				if (checkBifurcationMethodApplicable(org)) {
					result = billMasterCommonService.updateBifurcationMethodBillData(billMasData, amount.doubleValue(),
							details, billDetails, org, rebateDetails, manualReceptDate);
				}else if(Utility.isEnvPrefixAvailable(organisation, "KIF")) {
					List<BillReceiptPostingDTO> interestResult = new ArrayList<>();
					interestResult = billMasterCommonService.updateBillDataForInterest(billMasData, amount.doubleValue(), details,
							billDetails, org, rebateDetails, manualReceptDate);
						result = billMasterCommonService.updateBillDataWithoutInterest(billMasData, last.getExcessAmount(), details,
								billDetails, org, rebateDetails, manualReceptDate);
					result.addAll(interestResult);
				}
				else {
					result = billMasterCommonService.updateBillData(billMasData, amount.doubleValue(), details,
							billDetails, org, rebateDetails, manualReceptDate);
				}
				billMasData.get(billMasData.size() - 1).setBmLastRcptamt(actualPayAmt);
				if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
					reArrangeTheDataWhereFirstBillHaveArrearAmount(billMasData);
				}
				updateOutStanding(billMasData);
				if (CollectionUtils.isNotEmpty(rebateTaxList)) {
					for (BillReceiptPostingDTO rebateTax : rebateTaxList) {
						rebateTax.setRebateDetails(rebateDetails);
						result.add(rebateTax);
					}

				}
				if (!result.isEmpty()) { // for total demand in receipt master
					result.get(0).setTotalDemand(totalDemand);
				}
				if (surCharge != null && actualPayAmt > 0 && surCharge.getTotalTaxAmt().doubleValue() > 0) {
					BillReceiptPostingDTO surchargeRecDto = new BillReceiptPostingDTO();
					surchargeRecDto.setPayableAmount(surCharge.getTotalTaxAmt().doubleValue());
					surchargeRecDto.setTaxId(surCharge.getTaxId());
					surchargeRecDto.setTaxAmount(surCharge.getTotalTaxAmt().doubleValue());
					surchargeRecDto.setTaxCategory(surCharge.getTaxCategoryId());
					final String taxCode = CommonMasterUtility.getHierarchicalLookUp(surCharge.getTaxCategoryId(), org)
							.getLookUpCode();
					surchargeRecDto.setTaxCategoryCode(taxCode);
					surchargeRecDto.setTotalDetAmount(surCharge.getTotalTaxAmt().doubleValue());
					result.add(surchargeRecDto);
				}
				if (count > 0) {
					// Form Main Bill table
					propertyMainBillService.saveAndUpdateMainBill(billMasData, orgId, userId, null, ipAddress);
				} else {
					// From Provisional Bill Table
					iProvisionalBillService.saveAndUpdateProvisionalBill(billMasData, orgId, userId,
							propertyNo.toString(), null, null, null);
				}
				if (last.getExcessAmount() > 0) {// Excess Payment
					final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
					details.put(advanceTaxId, last.getExcessAmount());// to add advance amt into receipt det
					setAdnavcePayDetail(result, org, last.getExcessAmount(), advanceTaxId);
				}
			} else {// Payment is pure Advance Payment(No dues are pending)
				final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
				details.put(advanceTaxId, amount);
				setAdnavcePayDetail(result, org, amount, advanceTaxId);
			}
		} else {// Payment is pure Advance Payment(No dues are pending)
			LOGGER.info("no bills found");
			final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
			details.put(advanceTaxId, amount);
			setAdnavcePayDetail(result, org, amount, advanceTaxId);
		}
		LOGGER.info("updateBillMasterAmountPaid method executrd succesfully");
		return result;
    }

	private void updateOutStanding(List<TbBillMas> billMasData) {
		billMasterCommonService.updateArrearInCurrentBills(billMasData);
		billMasData.forEach(billMaster ->{
			AtomicDouble totalAmnt = new AtomicDouble(0.00);
			billMaster.getTbWtBillDet().forEach(detail ->{
				totalAmnt.addAndGet(detail.getBdCurBalTaxamt() + detail.getBdPrvBalArramt());
			});
			if(totalAmnt.get() > 0) {
				billMaster.setBmPaidFlag(MainetConstants.FlagN);
				billMaster.setBmTotalOutstanding(totalAmnt.get());
			}else {
				billMaster.setBmPaidFlag(MainetConstants.FlagY);
				billMaster.setBmTotalOutstanding(0.00);
				billMaster.setBmTotalArrearsWithoutInt(0.00);
				billMaster.setBmTotalArrears(0.00);
			}
		});
	}

    @Override
    public void setAdnavcePayDetail(List<BillReceiptPostingDTO> result, final Organisation org, final double amount,
            final Long advanceTaxId) {
        final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC,
                MainetConstants.NUMBERS.ONE, org);
        Long advanceId = null;
        if ((taxCategory != null) && !taxCategory.isEmpty()) {
            for (final LookUp lookupid : taxCategory) {
                if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                    advanceId = lookupid.getLookUpId();
                    break;
                }
            }
        }
        BillReceiptPostingDTO advance = new BillReceiptPostingDTO();
        advance.setTaxAmount(amount);
        advance.setTaxId(advanceTaxId);
        advance.setTaxCategory(advanceId);
        advance.setTaxCategoryCode(PrefixConstants.TAX_CATEGORY.ADVANCE);
        result.add(advance);
    }

    @Override
    @Transactional
    public boolean saveAdvancePayment(final Long orgId, final Double amount, final String propNo, final Long userId,
            final Long receiptId) {
        asExecssAmtService.addEntryInExcessAmt(orgId, propNo, amount, receiptId, userId);
        return true;
    }

    /*
     * complete bill generation based upon ScheduleWisemap which create by charges from BRMS for each unit and schedule
     */
    @Override
    @Transactional
    public List<TbBillMas> generateNewBill(Map<Date, List<BillPresentAndCalculationDto>> scheduleWiseMap,
            Organisation org, Long deptId, String propNo,String flatNo) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "generateNewBill() method");
        List<TbBillMas> billMasDtoList = new ArrayList<>();
        List<TbBillDet> billdetListForArr = new ArrayList<>();
        Double totalCurrent = null;
        Random rand = new Random();
        long dummyId = 0;// for condition to calculate interest after Demand level rebate knock off
        Double totalArrear = null;
        totalArrear = 0d;
        LookUp arrearCountConf = null;
        AtomicBoolean attearCount = new AtomicBoolean(false);
		try {
			arrearCountConf = CommonMasterUtility.getValueFromPrefixLookUp("CPY", "ARC", org);
		}catch (Exception exception) {
			
		}
		if(arrearCountConf != null && StringUtils.equals(MainetConstants.FlagY, arrearCountConf.getOtherField())) {
			attearCount.set(true);
        }
        if (scheduleWiseMap != null && !scheduleWiseMap.isEmpty()) {

            for (Map.Entry<Date, List<BillPresentAndCalculationDto>> entry : scheduleWiseMap.entrySet()) {
                Date billDate = entry.getKey();
                dummyId = rand.nextInt(1000) + 1;
                TbBillMas billMas = new TbBillMas();
                billMas.setDummyMasId(dummyId);
                billMas.setPropNo(propNo);
                billMas.setFlatNo(flatNo);
                StringBuilder logcalPropNo = new StringBuilder();
                logcalPropNo.append(propNo);
                if(StringUtils.isNotBlank(flatNo)) {
                	 logcalPropNo.append(MainetConstants.operator.UNDER_SCORE);
                	 logcalPropNo.append(flatNo);
                }
                billMas.setLogicalPropNo(logcalPropNo.toString());
                List<TbBillDet> billDetDtoList = new ArrayList<>();
                totalCurrent = 0d;
                Object[] currentFinDate = iFinancialYearService.getFinacialYearByDate(new Date());
                Date currentFinYear = (Date) currentFinDate[2];
                int currentYear = Utility.getYearByDate(currentFinYear);

                for (BillPresentAndCalculationDto eachTax : entry.getValue()) {
                    List<String> taxCat = Arrays.asList(PrefixConstants.TAX_CATEGORY.REBATE,
                            PrefixConstants.TAX_CATEGORY.EXEMPTION);
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(eachTax.getTaxCategoryId(), org)
                            .getLookUpCode();

                    String taxSubCatCode = CommonMasterUtility.getHierarchicalLookUp(eachTax.getTaxSubCategoryId(), org)
                            .getLookUpCode();

                    if (!taxCat.contains(taxCode)) {
                        // This condition is for in case of penalty we need to show under current year
                        // we are restricted to add in previous fin year by srikanth defect no 34077
                        Object[] billFinYear = iFinancialYearService.getFinacialYearByDate(billDate);
                        Date billYear = (Date) billFinYear[2];
                        int billAssesYear = Utility.getYearByDate(billYear);
                        if ((attearCount.get()) || (!StringUtils.equals("PT", taxSubCatCode)
                                || (StringUtils.equals("PT", taxSubCatCode) && billAssesYear == currentYear))) {
                            TbBillDet billDet = new TbBillDet();
                            dummyId = rand.nextInt(1000) + 1;
                            billDet.setDummyDetId(dummyId);
                            billDet.setTaxCategory(eachTax.getTaxCategoryId());
                            billDet.setTaxSubCategory(eachTax.getTaxSubCategoryId());
                            billDet.setTaxId(eachTax.getTaxId());
                            billDet.setBaseRate(eachTax.getRate());
                            billDet.setTaxDesc(eachTax.getChargeDescEng());
                            billDet.setCollSeq(eachTax.getTaxSequenceId());
                            billDet.setDisplaySeq(eachTax.getDisplaySeq());
                            if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
                            	billDet.setBdCurTaxamt(Math.ceil(eachTax.getChargeAmount()));
                                billDet.setBdCurBalTaxamt(Math.ceil(eachTax.getChargeAmount()));
                            }else {
                            	billDet.setBdCurTaxamt(Math.round(eachTax.getChargeAmount()));
                                billDet.setBdCurBalTaxamt(Math.round(eachTax.getChargeAmount()));
                            }
                            billDet.setPercentageRate(eachTax.getPercentageRate());
                            billDet.setRuleId(eachTax.getRuleId());
                            	totalCurrent = (billDet.getBdCurTaxamt() + totalCurrent);
                            
                            if (!billdetListForArr.isEmpty()) {
                                if (billdetListForArr.stream()
                                        .filter(eachSaveTax -> eachTax.getTaxId().equals(eachSaveTax.getTaxId()))
                                        .findFirst().isPresent()) {
                                    for (TbBillDet eachSaveTax : billdetListForArr) {
                                        if (eachTax.getTaxId().equals(eachSaveTax.getTaxId())) {
                                            billDet.setBdPrvArramt(Math.round(
                                                    eachSaveTax.getBdPrvArramt() + eachSaveTax.getBdCurTaxamt()));
                                            billDet.setBdPrvBalArramt(Math.round(
                                                    eachSaveTax.getBdPrvArramt() + eachSaveTax.getBdCurTaxamt()));
                                            totalArrear = totalArrear + eachSaveTax.getBdCurTaxamt();
                                            break;
                                        }
                                    }
                                } else {
                                    billDet.setBdPrvArramt(0d);
                                }
                            } else {
                                billDet.setBdPrvArramt(0d);
                            }
                            billDetDtoList.add(billDet);
                            billdetListForArr.add(billDet);

                        }

                    }
                }
                setBillMasDto(billMas, totalCurrent, totalArrear, entry.getKey(), org.getOrgid());
                billdetListForArr.clear();// There is problem if in between any tax is not applied.
                billdetListForArr.addAll(billDetDtoList);
                billMas.setTbWtBillDet(billDetDtoList);
                billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
                billMasDtoList.add(billMas);
            }
            billMasDtoList.get(billMasDtoList.size() - 1).setBmBilldt(new Date());// setting current_Bill billGrenDate
                                                                                  // is today
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "generateNewBill() method");
        return billMasDtoList;
    }
    private void setBillMasDto(TbBillMas billMas, Double totalCurrent, Double totalArrear, Date schFromDate,
            Long orgId) {
    	Organisation organisation = new Organisation();
       	organisation.setOrgid(orgId);
    	LookUp skdclEnv  = null;
     	try {
     		skdclEnv = CommonMasterUtility.getValueFromPrefixLookUp("SKDCL", "ENV", organisation);
     	}catch (Exception e) {
     		LOGGER.error("No Prefix found for ENV(SKDCL)");
 		}
        BillingScheduleDetailEntity billSchDet = billingScheduleService.getSchedulebySchFromDate(orgId, schFromDate);
        Long finYear = iFinancialYearService.getFinanceYearId(schFromDate);
        Long currentFinYear = iFinancialYearService.getFinanceYearId(new Date());
        FinancialYear curFinYear = iFinancialYearService.getFinincialYearsById(currentFinYear, orgId);
        Date dueDate = getDueDateForSch(billSchDet.getBillFromDate(), billSchDet.getCalFromDate(),
                billSchDet.getNoOfDays(), billSchDet.getBillFromDate(), billSchDet.getBillToDate(), orgId);
        billMas.setBmYear(finYear);
        billMas.setOrgid(orgId);
        billMas.setBmTotalAmount(totalCurrent);
        billMas.setBmDuedate(dueDate);
        billMas.setBmBilldt(new Date());
        billMas.setBmTotalBalAmount(totalCurrent);
        billMas.setBmPaidFlag(MainetConstants.PAY_STATUS.NOT_PAID);
        billMas.setBmTotalArrearsWithoutInt(totalArrear);
        billMas.setBmTotalArrears(totalArrear);
        billMas.setBmActualArrearsAmt(totalArrear);
        billMas.setBmTotalOutstanding(billMas.getBmTotalBalAmount() + billMas.getBmTotalArrears());
        billMas.setBmFromdt(billSchDet.getBillFromDate());
        billMas.setBmTodt(billSchDet.getBillToDate());
        billMas.setIntFrom(new Date());
        if(skdclEnv == null) {
        	billMas.setIntTo(dueDate);
        	if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
        		billMas.setIntTo(curFinYear.getFaToDate());
        	}
        }else {
        	 billMas.setIntTo(new Date());
        }
        billMas.setCurrentBillFlag(MainetConstants.Y_FLAG);
        if (billMas.getBmBilldt().after(billMas.getBmFromdt()) && billMas.getBmBilldt().before(billMas.getBmTodt())) {
            billMas.setGenFlag(MainetConstants.Y_FLAG);
        } else {
            billMas.setGenFlag(MainetConstants.N_FLAG);
        }
    }

    /*
     * oldbillMasList=unpaid bill list from main table. newBillMasList=new Bill list of assessment from BRMS. merging both list,
     * if same bill(same year bill) is present in both the list, have to take new bill in merge list with tax amount of new bill
     * and primary key of old bill
     */

    @Override
    public List<TbBillMas> getBillListWithMergingOfOldAndNewBill(List<TbBillMas> oldbillMasList,
            List<TbBillMas> newBillMasList) {
        List<TbBillMas> newBills = new LinkedList<>();
        Long firstBmIdNo = provisionalBillRepository.getFirstBmIdNoByPropNo(oldbillMasList.get(0).getPropNo());
        oldbillMasList.forEach(oldBill -> {
            boolean exist = newBillMasList.stream().filter(newBill -> oldBill.getBmYear().equals(newBill.getBmYear()))
                    .findFirst().isPresent();
            if (exist) {
                newBillMasList.stream().filter(newBill -> oldBill.getBmYear().equals(newBill.getBmYear()))
                        .forEach(newBill -> {
                            newBill.setBmIdno(oldBill.getBmIdno());
                            newBill.setBmNo(oldBill.getBmNo());
                            newBill.setUserId(oldBill.getUserId());
                            newBill.setLgIpMac(oldBill.getLgIpMac());
                            newBill.setLmoddate(oldBill.getLmoddate());
                            oldBill.getTbWtBillDet().forEach(oldBillDet -> {
                                newBill.getTbWtBillDet().stream()
                                        .filter(newBillDet -> newBillDet.getTaxId().equals(oldBillDet.getTaxId()))
                                        .forEach(newBillDet -> {
                                            newBillDet.setBdBilldetid(oldBillDet.getBdBilldetid());
                                            newBillDet.setUserId(oldBillDet.getUserId());
                                            newBillDet.setLgIpMac(oldBillDet.getLgIpMac());
                                            newBillDet.setLmoddate(oldBillDet.getLmoddate());
                                            if(firstBmIdNo != null && oldBill.getBmIdno() == firstBmIdNo) {
                                            	newBillDet.setBdPrvArramt(oldBillDet.getBdPrvArramt());
                                            	newBillDet.setBdPrvBalArramt(oldBillDet.getBdPrvBalArramt());
                                            }
                                        });
                            });
                            newBills.add(newBill);
                        });
            } else {
                newBills.add(oldBill);
            }
        });
        newBillMasList.forEach(newBill -> {
            boolean exist = oldbillMasList.stream().filter(oldBill -> oldBill.getBmYear().equals(newBill.getBmYear()))
                    .findFirst().isPresent();
            if (!exist) {
                newBills.add(newBill);
            }
        });

        return newBills;
    }

    /*
     * if one tax is applicable for 16-17 and not applicable for other year then that tax should carry forward for all following
     * year with current tax amount zero
     */
    @Override
    public void taxCarryForward(List<TbBillMas> billMasDtoList, Long orgId) {
        // ex. if bill size is 5 then loop will flow for 4 times
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "taxCarryForward() method");
        TbBillMas lastBillMas = null;
        for (int i = 1; i < billMasDtoList.size(); i++) {
            List<TbBillDet> firstTaxList = billMasDtoList.get(i - 1).getTbWtBillDet();// from which tax is copy
            List<TbBillDet> secondTaxList = billMasDtoList.get(i).getTbWtBillDet();// where tax will add
            for (TbBillDet firDet : firstTaxList) {
                boolean isexist = secondTaxList.stream()
                        .filter(s -> s.getTaxId().toString().equals(firDet.getTaxId().toString())).findFirst()
                        .isPresent();
                if (!isexist) {
                    TbBillDet billDet = new TbBillDet();
                    BeanUtils.copyProperties(firDet, billDet);
                    if (billDet.getDisplaySeq() == null) {
                        TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(firDet.getTaxId(), orgId);
                        billDet.setDisplaySeq(tax.getTaxDisplaySeq());
                        billDet.setTaxDesc(tax.getTaxDesc());
                    }
                    billDet.setBdCurTaxamt(0.0);
                    billDet.setBdBilldetid(0);
                    billDet.setBdCurBalTaxamt(0.0);
                    // billDet.setBdPrvArramt(firDet.getBdCurTaxamt() + firDet.getBdPrvArramt());
                    // #12562
                    billDet.setBdPrvBalArramt(firDet.getBdCurBalTaxamt() + firDet.getBdPrvBalArramt());
                    // because on revenue receipt payable amt and paid amt must be same without
                    // rebate
                    // and any tax is applicable to first year and zero for next year ex. property
                    // tax
                    if ((billMasDtoList.get(i - 1).getCurrentBillFlag() != null
                            && billMasDtoList.get(i - 1).getCurrentBillFlag().equals(MainetConstants.Y_FLAG))) {
                        billDet.setBdPrvArramt(firDet.getBdCurTaxamt() + firDet.getBdPrvBalArramt());
                    } else {
                        billDet.setBdPrvArramt(firDet.getBdCurBalTaxamt() + firDet.getBdPrvBalArramt());
                    }

                    secondTaxList.add(billDet);
                }
            }
            LOGGER.info("End--> " + this.getClass().getSimpleName() + "taxCarryForward() method");
        }
        
        billMasterCommonService.updateArrearInCurrentBills(billMasDtoList);
        for (TbBillMas billMas : billMasDtoList) {
        	
        	//120052 - To reset previousBillMas in case of flat wise individual billing
			if (lastBillMas != null && StringUtils.isNotBlank(lastBillMas.getFlatNo())
					&& StringUtils.isNotBlank(billMas.getFlatNo()) && (!lastBillMas.getFlatNo().equals(billMas.getFlatNo()))) {
				lastBillMas=null;
			}
			
            if (lastBillMas != null) {
                billMas.setBmTotalArrearsWithoutInt(
                        lastBillMas.getBmTotalBalAmount() + lastBillMas.getBmTotalArrearsWithoutInt());
                billMas.setArrearsTotal(billMas.getBmTotalArrearsWithoutInt() + billMas.getBmTotalCumIntArrears());
                billMas.setBmTotalOutstanding(billMas.getBmTotalArrears() + billMas.getBmTotalBalAmount());
            }
            /*
             * for (TbBillDet billDet : billMas.getTbWtBillDet()) { totalArrear = totalArrear + billDet.getBdCurTaxamt(); }
             */
            lastBillMas = billMas;
        }

    }

    @Override
    @Transactional
    public void interestCalculation(Organisation org, Long deptId, List<TbBillMas> billMasDtoList,
            String intRecaculateFlag) {
        propertyBRMSService.fetchInterstRate(billMasDtoList, org, deptId);// calculating interest rate through BRMS
        setBillDetForInterst(billMasDtoList, org.getOrgid(), deptId);// also adding interest tax in bill details
        billMasterCommonService.calculateInterest(billMasDtoList, org, deptId, intRecaculateFlag, null);
    }

    @Override
    @Transactional
    public void interestCalculationWithoutBRMSCall(Organisation org, Long deptId, List<TbBillMas> billMasDtoList,
            String intRecaculateFlag) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "interestCalculationWithoutBRMSCall() method");
        setInterstMethodInBillMas(billMasDtoList, org, deptId);
        //billMasterCommonService.calculateInterest(billMasDtoList, org, deptId, intRecaculateFlag, null);
        if(!Utility.isEnvPrefixAvailable(org, "PSCL")) {
        	billMasterCommonService.calculateMultipleInterest(billMasDtoList, org, deptId, intRecaculateFlag, null);
            LOGGER.info("End--> " + this.getClass().getSimpleName() + "interestCalculationWithoutBRMSCall() method");
        }
    }

    private Date getDueDateForSch(Date finYearStrDate, Long calFrom, Long noOfDay, Date schStartDate, Date schEndDate,
            Long orgId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        String lookCode = CommonMasterUtility.getNonHierarchicalLookUpObject(calFrom, org).getLookUpCode();
        Calendar cal = Calendar.getInstance();
        if (lookCode.equals(MainetConstants.Property.DueDatePerf.BGD)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.FYS)) {
            cal.setTime(finYearStrDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SSD)) {
            cal.setTime(schStartDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SED)) {
            cal.setTime(schEndDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        }
        return new Date();
    }

    private void setBillDetForInterst(List<TbBillMas> billMasDtoList, Long orgId, Long deptId) {
        billMasDtoList.forEach(billMas -> {
            final TbTaxMas tax = tbTaxMasService.getTaxMasterByTaxCode(orgId, deptId, billMas.getTaxCode());
            TbBillDet billDet = null;
            if (tax != null) {
                boolean result = billMas.getTbWtBillDet().stream()
                        .anyMatch(billDetail -> billDetail.getTaxCategory().equals(tax.getTaxCategory1()));
                if (!result) {
                    billDet = new TbBillDet();
                    billDet.setTaxCategory(tax.getTaxCategory1());
                    billDet.setTaxSubCategory(tax.getTaxCategory2());
                    billDet.setTaxDesc(tax.getTaxDesc());
                    billDet.setTaxId(tax.getTaxId());
                    billDet.setCollSeq(tax.getCollSeq());
                    billDet.setDisplaySeq(tax.getTaxDisplaySeq());
                    billMas.getTbWtBillDet().add(billDet);
                }
            }
        });

    }

    private void setInterstMethodInBillMas(List<TbBillMas> billMasDtoList, Organisation org, Long deptId) {
        billMasDtoList.forEach(billMas -> {

        	billMasDtoList.get(billMasDtoList.size() - 1).getTbWtBillDet().stream()
                    .filter(billDetail -> CommonMasterUtility.getHierarchicalLookUp(billDetail.getTaxCategory(), org)
                            .getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.INTERST))
                    .forEach(billDet -> {
                        LookUp taxSubCat = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxSubCategory(), org);
                        billMas.setInterstCalMethod(taxSubCat.getLookUpCode());
                        billMas.setBmIntValue(billDet.getBaseRate());
                        // billMas.setTaxCode(propRateMaster.getTaxCode());
                    });

        });

    }

    /*
     * Calculate Month Between Two dates if fromDate is last date of that Month then that month does't count for interest
     * Calculation otherwise count, And months till toDate including toDate Month is added for interest Calculation Month For
     * Example. (30/9/2016,31/3/2017) return=6 (29/9/2016,31/3/2017) return=7
     */
    public int calculateMonths(Date fromDate, Date toDate) {
        int noOfMonth = 0;
        LocalDate frmDateLoc = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDateLoc = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (frmDateLoc.getDayOfMonth() != frmDateLoc.lengthOfMonth()) {
            noOfMonth++;
            frmDateLoc = frmDateLoc.withDayOfMonth(frmDateLoc.lengthOfMonth());
        }
        Period tillPayDay = Period.between(frmDateLoc, toDateLoc);
        if (tillPayDay.getYears() > 0 || tillPayDay.getMonths() > 0 || tillPayDay.getDays() > 0) {
            if (tillPayDay.getDays() == 30) {
                noOfMonth += 1;
            }
            noOfMonth += (tillPayDay.getYears() * 12) + tillPayDay.getMonths();
            if (tillPayDay.getDays() > 0 && (frmDateLoc.getDayOfMonth() != frmDateLoc.lengthOfMonth())
                    || (toDateLoc.getDayOfMonth() < toDateLoc.lengthOfMonth() && tillPayDay.getDays() > 0)) {
                noOfMonth++;
            }
        } else {
            noOfMonth = 0;
        }
        return noOfMonth;
    }

    public int calculateYears(Date fromDate, Date toDate, Long orgId) {
        return tbFinancialyearService.getCountOfFinYearBetwDates(fromDate, toDate, orgId);
    }

    @Override
    public boolean isInterestRecalculationRequire(Organisation org, Long deptId, TbBillMas billMas, Date manualDate) {
        int period = 0;
        final Long taxCat = CommonMasterUtility.getHieLookupByLookupCode(PrefixConstants.TAX_CATEGORY.INTERST,
                PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid()).getLookUpId();
        billMas.getTbWtBillDet().stream()

                .filter(billDet -> taxCat.toString().equals(billDet.getTaxCategory().toString()))// enter in loop if tax
                                                                                                 // cat is
                                                                                                 // Interest
                .forEach(billDet -> {
                    TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(), org.getOrgid());
                    billMas.setInterstCalMethod(
                            CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org).getLookUpCode());
                });

        if (billMas.getIntTo() != null) {
            Date tillDate = manualDate;
            if (manualDate == null) {
                tillDate = new Date();
            } else {
                tillDate = manualDate;
            }
            if (billMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                period = calculateYears(billMas.getIntTo(), tillDate, org.getOrgid());

            } else if (billMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                period = calculateMonths(billMas.getIntTo(), tillDate);
            }
        }
        if (period > 0) {
            return true;
        }
        return false;
    }

    @Override
    public double getTotalPaybaleAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList,
            BillDisplayDto subCharge) {
        AtomicDouble totAmt = new AtomicDouble(0);
        AtomicDouble rebateAmount = new AtomicDouble(0);
        Organisation org = new Organisation();
        org.setOrgid(billMasList.get(0).getOrgid());
        if (billMasList != null && !billMasList.isEmpty()) {// will be add extra check for rebate and notice or any
                                                            // other extra
                                                            // charge
            billMasList.get(billMasList.size() - 1).getTbWtBillDet().forEach(det -> {
                totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
            });
            if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                rebateDtoList.forEach(rebateDto -> {
                    if (rebateDto != null) {
                        if (rebateDto.getTotalTaxAmt().doubleValue() > 0) {
                        	rebateAmount.addAndGet(rebateDto.getTotalTaxAmt().doubleValue());
                        	if(!Utility.isEnvPrefixAvailable(org, "SKDCL")) {
                        		totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue());
                        	}
                        }
                    }
                });
            }
            if (subCharge != null) {
                totAmt.addAndGet(subCharge.getCurrentYearTaxAmt().doubleValue());
            }
        }
        if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
    		totAmt.getAndSet(totAmt.doubleValue() - Math.ceil(rebateAmount.doubleValue()));
    	}
        
        return totAmt.doubleValue();
    }

    @Override
    public double getTotalPaybaleAmountForBillPay(List<TbBillMas> billMasList, BillDisplayDto rebateDto) {
        AtomicDouble totAmt = new AtomicDouble(0);
        if (billMasList != null && !billMasList.isEmpty()) {// will be add extra check for rebate and notice or any
                                                            // other extra
                                                            // charge
            totAmt.addAndGet(billMasList.get(billMasList.size() - 1).getBmTotalOutstanding());
            if (rebateDto != null) {
                if (rebateDto.getTotalTaxAmt().doubleValue() > 0) {
                    totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue());
                }
            }
        }
        return totAmt.doubleValue();
    }

    // for Authorizer as he want to see complete bill
    @Override
    public double getTotalActualAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList,
            Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge) {
        AtomicDouble totAmt = new AtomicDouble(0);
        if (billMasList != null && !billMasList.isEmpty()) {// will be add extra check for notice or any other extra
                                                            // charges
            for (TbBillDet det : billMasList.get(billMasList.size() - 1).getTbWtBillDet()) {
                totAmt.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
            }
            if (CollectionUtils.isNotEmpty(rebateDtoList)) { // Early Payment Rebate
                rebateDtoList.forEach(rebateDto -> {
                    if (rebateDto != null) {
                        totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue());
                    }
                });
            }
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {// Demand level Rebate
                taxWiseRebate.entrySet().forEach(demandRebet -> {
                    totAmt.getAndSet(totAmt.doubleValue() - demandRebet.getValue().getTotalTaxAmt().doubleValue());
                });
            }
            if (surCharge != null) {
                totAmt.addAndGet(surCharge.getTotalTaxAmt().doubleValue());
            }
            /*
             * if (rebateDto != null && rebateDto.getTotalTaxAmt() != null) { // Early Payment Rebate
             * totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue()); }
             */
        }
        return totAmt.doubleValue() > 0 ? Math.round(totAmt.doubleValue()) : 0;
    }

    @Override
    public double getTotalActualAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList,
            Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge, BillDisplayDto advanceAmt) {
        AtomicDouble totAmt = new AtomicDouble(0);
        Organisation organisation = new Organisation();
        organisation.setOrgid(billMasList.get(0).getOrgid());
        if (billMasList != null && !billMasList.isEmpty()) {// will be add extra check for notice or any other extra
                                                            // charges
            for (TbBillDet det : billMasList.get(billMasList.size() - 1).getTbWtBillDet()) {
                if (det.getArrAmtOfNewBill() > 0) {
                    totAmt.addAndGet(det.getBdCurTaxamt() + det.getArrAmtOfNewBill());
				} else {
					if (Utility.isEnvPrefixAvailable(organisation, "ASCL")
							&& Utility.isEnvPrefixAvailable(organisation, "DBA")) {
						if (MapUtils.isNotEmpty(taxWiseRebate)) {
							taxWiseRebate.entrySet().forEach(entry -> {
								BillDisplayDto value = entry.getValue();
								if (value.getParentTaxId().equals(det.getTaxId())) {
									totAmt.addAndGet(det.getBdCurTaxamt() + det.getBdPrvBalArramt());
								}else {
									totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
								}
							});
						} else {
							totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
						}
					} else {
						totAmt.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
					}
				}
            }
            if (CollectionUtils.isNotEmpty(rebateDtoList)) { // Early Payment Rebate
                rebateDtoList.forEach(rebateDto -> {
                    if (rebateDto != null && rebateDto.getTotalTaxAmt().doubleValue() > 0) {
                        totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue());
                    }
                });
            }
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {// Demand level Rebate
                taxWiseRebate.entrySet().forEach(demandRebet -> {
                    totAmt.getAndSet(totAmt.doubleValue() - demandRebet.getValue().getTotalTaxAmt().doubleValue());
                });
            }
            if (surCharge != null) {
                totAmt.addAndGet(surCharge.getTotalTaxAmt().doubleValue());
            }
            if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) { // Early Payment Rebate
                totAmt.getAndSet(totAmt.doubleValue() - advanceAmt.getTotalTaxAmt().doubleValue());
            }
        }
        return totAmt.doubleValue() > 0 ? totAmt.doubleValue() : 0;
    }

    @Override
    public double getTotalActualAmount(Map<String, List<BillDisplayDto>> presentMap, Long orgId) {
        AtomicDouble totAmt = new AtomicDouble(0);
        if (presentMap != null && !presentMap.isEmpty()) {// will be add extra check for notice or any other extra
                                                          // charges
            List<String> taxCat = Arrays.asList(PrefixConstants.TAX_CATEGORY.ADVANCE,
                    PrefixConstants.TAX_CATEGORY.REBATE);
            presentMap.forEach((key, value) -> {
                value.forEach(tax -> {
                    final String catCode = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategoryId(), orgId)
                            .getLookUpCode();
                    if (taxCat.contains(catCode)) {
                        totAmt.getAndSet(totAmt.doubleValue() - tax.getTotalTaxAmt().doubleValue());
                    } else {
                        totAmt.addAndGet(tax.getTotalTaxAmt().doubleValue());
                    }

                });
            });
        }
        return totAmt.doubleValue();
    }

    @Override
    public List<BillDisplayDto> getTaxListForDisplay(TbBillMas billMas, final Organisation organisation, Long deptId) {
        List<BillDisplayDto> billDisDtoList = new ArrayList<>();
        LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.SURCHARGE,
                PrefixConstants.LookUpPrefix.TAC, MainetConstants.Property.SECOND_LEVEL, organisation.getOrgid());
        if (billMas != null) {
            billMas.getTbWtBillDet().forEach(billDet -> {
                BillDisplayDto billDisDto = new BillDisplayDto();
                billDisDto.setTaxCategoryId(billDet.getTaxCategory());
                billDisDto.setTaxDesc(billDet.getTaxDesc());
                if (billDet.getDisplaySeq() != null) {
                    billDisDto.setDisplaySeq(billDet.getDisplaySeq());
                } else {
                    TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(),
                            organisation.getOrgid());

                    billDisDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
                    billDisDto.setTaxDesc(taxMas.getTaxDesc());
                }
                if(Utility.isEnvPrefixAvailable(organisation, "ASCL") && Utility.isEnvPrefixAvailable(organisation, "DBA")) {
                	billDisDto.setCurrentYearTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()));
                }else {
                	billDisDto.setCurrentYearTaxAmt(BigDecimal.valueOf(billDet.getBdCurTaxamt()));
                }
                
                /*
                 * if (billDet.getArrAmtOfNewBill() > 0) {
                 * billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getArrAmtOfNewBill())) ;
                 * billDisDto.setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurTaxamt() + billDet.getArrAmtOfNewBill())); } else
                 * {
                 */
                if(Utility.isEnvPrefixAvailable(organisation, "ASCL") && Utility.isEnvPrefixAvailable(organisation, "DBA")) {
                	billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getBdPrvBalArramt()));
                }else {
                	billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getBdPrvArramt()));
                }

                // For surcharge call Arrears+current will be display in current tax column
                if (billDet.getTaxSubCategory() != null
                        && billDet.getTaxSubCategory().equals(taxSubCatLookup.getLookUpId())) {
                    billDisDto.setCurrentYearTaxAmt(
                            BigDecimal.valueOf(billDet.getBdCurTaxamt() + billDet.getBdPrvArramt()));
                    billDisDto.setArrearsTaxAmt(new BigDecimal(0));
                }
                
                if(Utility.isEnvPrefixAvailable(organisation, "ASCL") && Utility.isEnvPrefixAvailable(organisation, "DBA")) {
                	billDisDto.setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt()));
                }else {
                	billDisDto.setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurTaxamt() + billDet.getBdPrvArramt()));
                }
                // }
                billDisDto.setTaxId(billDet.getTaxId());
                billDisDtoList.add(billDisDto);
            });

            billDisDtoList.sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));
        }
        return billDisDtoList;

    }

    @Override
    public List<BillDisplayDto> getTaxListForBillPayDisplay(TbBillMas billMas, final Organisation organisation,
            Long deptId) {
        List<BillDisplayDto> billDisDtoList = new ArrayList<>();
        if (billMas != null) {
            billMas.getTbWtBillDet().forEach(billDet -> {
                BillDisplayDto billDisDto = new BillDisplayDto();
                billDisDto.setTaxId(billDet.getTaxId());
                billDisDto.setTaxCategoryId(billDet.getTaxCategory());
                billDisDto.setTaxDesc(billDet.getTaxDesc());
                if (billDet.getDisplaySeq() != null) {
                    billDisDto.setDisplaySeq(billDet.getDisplaySeq());
                } else {
                    billDisDto.setDisplaySeq(tbTaxMasService
                            .findTaxByTaxIdAndOrgId(billDet.getTaxId(), organisation.getOrgid()).getTaxDisplaySeq());
                }
                billDisDto.setCurrentYearTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()).setScale(2, RoundingMode.HALF_DOWN));
                billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getBdPrvBalArramt()).setScale(2, RoundingMode.HALF_DOWN));
                
                LookUp penalIntLookUp  = null;
            	try {
            		penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", organisation);
            	}catch (Exception e) {
            		LOGGER.error("No Prefix found for ENV(PIT)");
        		}
				if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
						&& StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
					
					 double totalBalance = billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt();
		                billDisDto
		                        .setTotalTaxAmt(BigDecimal.valueOf(Utility.round(totalBalance, 2)).setScale(2, RoundingMode.HALF_DOWN));
				}else {
					billDisDto
                    .setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt()).setScale(2, RoundingMode.HALF_DOWN));
				}
                billDisDtoList.add(billDisDto);
            });

            billDisDtoList.sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));
        }
        return billDisDtoList;
    }

    @Override
    public Map<String, List<BillDisplayDto>> getTaxMapForDisplayCategoryWise(List<BillDisplayDto> list,
            final Organisation organisation, Long deptId) {
        Map<String, List<BillDisplayDto>> taxCategoryWiseChargeMap = new HashMap<>(0);
        List<Long> taxAppList = new ArrayList<>();
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.propPref.BILL,
                MainetConstants.Property.propPref.CAA, organisation);
        final LookUp taxAppAtRecp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.propPref.RCPT,
                MainetConstants.Property.propPref.CAA, organisation);
        taxAppList.add(taxAppAtBill.getLookUpId());
        taxAppList.add(taxAppAtRecp.getLookUpId());
        List<Long> taxCatList = tbTaxMasService.getDistinctTaxCatByDept(organisation.getOrgid(), deptId, taxAppList);
        taxCatList.forEach(tacCat -> {
            String taxCatDesc = CommonMasterUtility.getHierarchicalLookUp(tacCat, organisation).getDescLangFirst();
            List<BillDisplayDto> billDisDtoList = new ArrayList<>();
            list.forEach(billPresent -> {
                if (billPresent.getTaxCategoryId().equals(tacCat)) {
                    billDisDtoList.add(billPresent);
                }
            });
            if (!billDisDtoList.isEmpty()) {
                billDisDtoList.sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));// Sorting List by collection
                                                                                         // sequence
                taxCategoryWiseChargeMap.put(taxCatDesc, billDisDtoList);
            }
        });
        return taxCategoryWiseChargeMap;
    }

    @Override
    @Transactional
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
            final Long org) {
        final List<ProvisionalAssesmentMstEntity> assMstList = iProvisionalAssesmentMstService
                .getProAssMasterByApplicationId(org, applicationId);
        WardZoneBlockDTO wardZoneDTO = new WardZoneBlockDTO();
        if (assMstList != null && !assMstList.isEmpty()) {
            ProvisionalAssesmentMstEntity assMst = assMstList.get(0);
            if (assMst.getAssWard1() != null) {
                wardZoneDTO.setAreaDivision1(assMst.getAssWard1());
            }
            if (assMst.getAssWard2() != null) {
                wardZoneDTO.setAreaDivision2(assMst.getAssWard2());
            }
            if (assMst.getAssWard3() != null) {
                wardZoneDTO.setAreaDivision3(assMst.getAssWard3());
            }
            if (assMst.getAssWard4() != null) {
                wardZoneDTO.setAreaDivision4(assMst.getAssWard4());
            }
            if (assMst.getAssWard4() != null) {
                wardZoneDTO.setAreaDivision5(assMst.getAssWard4());
            }
        } else {
            final List<AssesmentMastEntity> mainAssMstList = assesmentMastService
                    .getAssesmentMstEntListByAppId(applicationId, org);
            if (mainAssMstList != null && !mainAssMstList.isEmpty()) {
                AssesmentMastEntity assMst = mainAssMstList.get(0);
                if (assMst.getAssWard1() != null) {
                    wardZoneDTO.setAreaDivision1(assMst.getAssWard1());
                }
                if (assMst.getAssWard2() != null) {
                    wardZoneDTO.setAreaDivision2(assMst.getAssWard2());
                }
                if (assMst.getAssWard3() != null) {
                    wardZoneDTO.setAreaDivision3(assMst.getAssWard3());
                }
                if (assMst.getAssWard4() != null) {
                    wardZoneDTO.setAreaDivision4(assMst.getAssWard4());
                }
                if (assMst.getAssWard4() != null) {
                    wardZoneDTO.setAreaDivision5(assMst.getAssWard4());
                }
            }
        }
        return wardZoneDTO;
    }

    @Override
    @Transactional
    public void setWardZoneDetailByLocId(ProvisionalAssesmentMstDto assDto, final Long deptId, final Long locId) {
        LocOperationWZMappingDto operWZDto = iLocationMasService.findOperLocationAndDeptId(locId, deptId);
        if (operWZDto != null) {
            if (operWZDto.getCodIdOperLevel1() != null) {
                assDto.setAssWard1(operWZDto.getCodIdOperLevel1());
            }
            if (operWZDto.getCodIdOperLevel2() != null) {
                assDto.setAssWard2(operWZDto.getCodIdOperLevel2());
            }
            if (operWZDto.getCodIdOperLevel3() != null) {
                assDto.setAssWard3(operWZDto.getCodIdOperLevel3());
            }
            if (operWZDto.getCodIdOperLevel4() != null) {
                assDto.setAssWard4(operWZDto.getCodIdOperLevel4());
            }
            if (operWZDto.getCodIdOperLevel5() != null) {
                assDto.setAssWard5(operWZDto.getCodIdOperLevel5());
            }
        }
    }

    // For Account Posting
    @Override
    @Transactional
    public List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> uniquePrimaryKey, Long orgid) {
        List<TbBillMas> yearWiseBill = new ArrayList<>();
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        AtomicInteger yearCount = new AtomicInteger(0);
        List<TbBillMas> bills = iProvisionalBillService.fetchListOfBillsByPrimaryKey(uniquePrimaryKey, orgid);

        final Long govtTaxGrp = CommonMasterUtility.getValueFromPrefixLookUp("GVT", "TAG", org).getLookUpId();
        final Department dept = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.STATUS.ACTIVE);
        List<Long> govtTaxId = tbTaxMasService.fetchTaxIdByDeptIdForTaxGroup(govtTaxGrp, orgid, dept.getDpDeptid());

        final Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", org).getLookUpId();
        final Long previousIncomeId = CommonMasterUtility.getValueFromPrefixLookUp("PPI", "DMC", org).getLookUpId();

        final Long PreviousExpenditureId = CommonMasterUtility.getValueFromPrefixLookUp("PPE", "DMC", org)
                .getLookUpId();
		/*
		 * Handling exception for other  project  where RMS value not required to
		 * Configure
		 */
        Long currentExpenditureIdTemp=null;
		  try {
			  currentExpenditureIdTemp = CommonMasterUtility.getValueFromPrefixLookUp("RMS", "DMC", org).getLookUpId();
		} catch (Exception e) {
			LOGGER.error("No Prefix found for DMC Prefix code value -->RMS"+e);
		}
		final Long currentExpenditureId=currentExpenditureIdTemp;
        /* coding yet to be done for expenditure */

        final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC,
                MainetConstants.NUMBERS.ONE, org);
        Long demandId = null;
        if ((taxCategory != null) && !taxCategory.isEmpty()) {
            for (final LookUp lookupid : taxCategory) {
                if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.DEMAND)) {
                    demandId = lookupid.getLookUpId();
                    break;
                }
            }
        }
        final Long demandCategoryId = demandId;
        Map<Long, List<TbBillMas>> mapSorted = new LinkedHashMap<>(0);
        final Long finYear = iFinancialYearService.getFinanceYearId(new Date());
        Map<Long, List<TbBillMas>> map = bills.stream().collect(Collectors.groupingBy(TbBillMas::getBmYear));

        final List<FinancialYear> finYearAll = iFinancialYearService.getAllFinincialYear();// Collections.reverse(finYearAll);

        finYearAll.forEach(finyear -> {
            map.forEach((yearId, value) -> {
                if (finyear.getFaYear() == yearId.longValue()) {
                    mapSorted.put(yearId, value);
                }
            });
        });
        TbBillMas consolidateBillMas = new TbBillMas();
        AtomicDouble consolidateDemandExpd = new AtomicDouble(0);
        Map<Long, TbBillDet> consolidateDetails = new HashMap<>();
        mapSorted.forEach((k, v) -> {
            Long tppTaxId = currDemandId;
            if (!finYear.equals(k)) {
                tppTaxId = previousIncomeId;
            }
            final Long tddTaxId = tppTaxId;
            if (yearCount.intValue() < MainetConstants.NUMBERS.FIVE) {
                AtomicDouble totalDemandExpd = new AtomicDouble(0);
                TbBillMas billMas = new TbBillMas();
                Map<Long, TbBillDet> details = new HashMap<>();
                billMas.setBmYear(k);
                v.forEach(bill -> {
                    billMas.setBmTotalOutstanding(billMas.getBmTotalOutstanding() + bill.getBmTotalAmount());
                    billMas.setBmNo(bill.getPropNo());
                    billMas.setLgIpMac(bill.getLgIpMac());
                    billMas.setBmIdno(bill.getBmIdno());
                    bill.getTbWtBillDet().stream().filter(billdet -> demandCategoryId.equals(billdet.getTaxCategory()))
                            .forEach(billdet -> {
                                TbBillDet billdetails = details.get(billdet.getTaxId());
                                double expenditureAmount = 0d;
                                if (billdet.getBdCurTaxamtAuth() > 0d) {
                                    billMas.setBmTotalOutstanding(0d);
                                    expenditureAmount = billdet.getBdCurTaxamt() - billdet.getBdCurTaxamtAuth();
                                    totalDemandExpd.addAndGet(expenditureAmount);
                                }
                                if (billdetails != null) {
                                    if (expenditureAmount > 0d) {
                                        billdetails.setBdCurTaxamt(expenditureAmount + billdetails.getBdCurTaxamt());
                                    } else {
                                        billdetails.setBdCurTaxamt(
                                                billdet.getBdCurTaxamt() + billdetails.getBdCurTaxamt());
                                    }
                                } else {
                                    billdetails = new TbBillDet();
                                    if (expenditureAmount != 0d) {
                                        billdetails.setBdCurTaxamt(expenditureAmount);
                                        billdetails.setTaxId(billdet.getTaxId());
                                        details.put(billdet.getTaxId(), billdetails);
                                        if (expenditureAmount < 0d) {
											
											if (billMas.getBmYear().equals(finYear) && currentExpenditureId != null) {
												billdetails.setTddTaxid(currentExpenditureId);
											} else {
												billdetails.setTddTaxid(PreviousExpenditureId);
											}
											 
                                        } else {
                                            if (govtTaxId.contains(billdet.getTaxId())) {
                                                billdetails.setTddTaxid(currDemandId);
                                            } else {
                                                billdetails.setTddTaxid(tddTaxId);
                                            }
                                        }
                                    } else {
                                        billdetails.setBdCurTaxamt(billdet.getBdCurTaxamt());
                                        billdetails.setTaxId(billdet.getTaxId());
                                        details.put(billdet.getTaxId(), billdetails);
                                        if (govtTaxId.contains(billdet.getTaxId())) {
                                            billdetails.setTddTaxid(currDemandId);
                                        } else {
                                            billdetails.setTddTaxid(tddTaxId);
                                        }
                                    }
                                }
                            });
                });
                if (totalDemandExpd.doubleValue() != 0d) {
                    billMas.setGrandTotal(totalDemandExpd.doubleValue());
                    billMas.setBmTotalOutstanding(totalDemandExpd.doubleValue());
                }
                yearCount.incrementAndGet();
                billMas.getTbWtBillDet().addAll(details.values().stream().collect(Collectors.toList()));
                yearWiseBill.add(billMas);
            } else {
                v.forEach(bill -> {
                    consolidateBillMas.setBmTotalOutstanding(
                            consolidateBillMas.getBmTotalOutstanding() + bill.getBmTotalAmount());
                    consolidateBillMas.setBmNo(bill.getPropNo());
                    consolidateBillMas.setLgIpMac(bill.getLgIpMac());
                    consolidateBillMas.setBmIdno(bill.getBmIdno());
                    bill.getTbWtBillDet().stream().filter(billdet -> demandCategoryId.equals(billdet.getTaxCategory()))
                            .forEach(billdet -> {
                                TbBillDet billdetails = consolidateDetails.get(billdet.getTaxId());
                                double expenditureAmount = 0d;
                                if (billdet.getBdCurTaxamtAuth() > 0d) {
                                    consolidateBillMas.setBmTotalOutstanding(0d);
                                    expenditureAmount = billdet.getBdCurTaxamt() - billdet.getBdCurTaxamtAuth();
                                    consolidateDemandExpd.addAndGet(expenditureAmount);
                                }
                                if (billdetails != null) {
                                    if (expenditureAmount > 0d) {
                                        billdetails.setBdCurTaxamt(expenditureAmount + billdetails.getBdCurTaxamt());
                                    } else {
                                        billdetails.setBdCurTaxamt(
                                                billdet.getBdCurTaxamt() + billdetails.getBdCurTaxamt());
                                    }
                                } else {
                                    billdetails = new TbBillDet();
                                    if (expenditureAmount != 0d) {
                                        billdetails.setBdCurTaxamt(expenditureAmount);
                                        billdetails.setTaxId(billdet.getTaxId());
                                        consolidateDetails.put(billdet.getTaxId(), billdetails);
                                        if (expenditureAmount < 0d) {
                                            billdetails.setTddTaxid(PreviousExpenditureId);
                                        } else {
                                            if (govtTaxId.contains(billdet.getTaxId())) {
                                                billdetails.setTddTaxid(currDemandId);
                                            } else {
                                                billdetails.setTddTaxid(tddTaxId);
                                            }
                                        }
                                    } else {
                                        billdetails.setBdCurTaxamt(billdet.getBdCurTaxamt());
                                        billdetails.setTaxId(billdet.getTaxId());
                                        consolidateDetails.put(billdet.getTaxId(), billdetails);
                                        if (govtTaxId.contains(billdet.getTaxId())) {
                                            billdetails.setTddTaxid(currDemandId);
                                        } else {
                                            billdetails.setTddTaxid(tddTaxId);
                                        }
                                    }
                                }
                            });
                });
                yearCount.incrementAndGet();
                if (consolidateDemandExpd.doubleValue() != 0d) {
                    consolidateBillMas.setGrandTotal(consolidateDemandExpd.doubleValue());
                    consolidateBillMas.setBmTotalOutstanding(consolidateDemandExpd.doubleValue());
                }
                consolidateBillMas.getTbWtBillDet()
                        .addAll(consolidateDetails.values().stream().collect(Collectors.toList()));
            }
        });
        if (yearCount.intValue() > MainetConstants.NUMBERS.FIVE) {
            yearWiseBill.add(consolidateBillMas);
        }
        return yearWiseBill;
    }

    @Override
    public void setUnitDetailsForNextYears(ProvisionalAssesmentMstDto assMst, List<Long> finYearList,
            List<FinancialYear> financialYearList) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "setUnitDetailsForNextYears() method finYearList :"+finYearList);
        List<ProvisionalAssesmentDetailDto> provisionalDetailDtoFinYearList = new ArrayList<>(0);
        for (FinancialYear financialYearEach : financialYearList) {
            ProvisionalAssesmentDetailDto dto = null;
            List<ProvisionalAssesmentDetailDto> provisionalDetailDtoFinYear = new ArrayList<>(0);
            finYearList.add(financialYearEach.getFaYear());
            for (ProvisionalAssesmentDetailDto assDetailDto : assMst.getProvisionalAssesmentDetailDtoList()) {
                dto = new ProvisionalAssesmentDetailDto();
                BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
                BeanUtils.copyProperties(assDetailDto, dto);
                dto.setFaYearId(financialYearEach.getFaYear());
                dto.setProvisionalAssesmentFactorDtlDtoList(assDetailDto.getProvisionalAssesmentFactorDtlDtoList());
                dto.setLastAssesmentStringDate(
                        new SimpleDateFormat(MainetConstants.DATE_FRMAT).format(dto.getAssdAssesmentDate()));
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date todayWithZeroTime = null;
                try {
                    todayWithZeroTime = formatter.parse(formatter.format(dto.getAssdAssesmentDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dto.setLastAssesmentDate(todayWithZeroTime);
                provisionalDetailDtoFinYear.add(dto);
            }
            provisionalDetailDtoFinYearList.addAll(provisionalDetailDtoFinYear);
        }
        assMst.setProvisionalAssesmentDetailDtoList(provisionalDetailDtoFinYearList);
        
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "setUnitDetailsForNextYears() method");
    }

    @Override
    @Transactional
    public void updateArrearInCurrentBills(List<TbBillMas> billMasList) {
        billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(billMasList);
    }

    /*
     * @Override public void updateArrearInCurrentBills(List<TbBillMas> billMasList) { TbBillMas previousBillMas = null; for
     * (final TbBillMas currBillMas : billMasList) { if (previousBillMas != null) { currBillMas.setBmTotalArrearsWithoutInt(
     * previousBillMas.getBmTotalBalAmount() + previousBillMas.getBmTotalArrearsWithoutInt()); } if (previousBillMas != null) {
     * currBillMas.setBmTotalCumIntArrears( previousBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt()); } else {
     * currBillMas.setBmTotalCumIntArrears( currBillMas.getBmToatlInt()); } currBillMas.setBmTotalArrears(
     * currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt()); currBillMas.setBmTotalOutstanding(
     * currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount()); if ((currBillMas.getTbWtBillDet() != null) &&
     * !currBillMas.getTbWtBillDet().isEmpty() && (previousBillMas != null)) { for (final TbBillDet det :
     * currBillMas.getTbWtBillDet()) { for (final TbBillDet lastDet : previousBillMas.getTbWtBillDet()) { if
     * (det.getTaxId().equals(lastDet.getTaxId())) { det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() +
     * lastDet.getBdPrvBalArramt()); det.setBdPrvBalArramt(det.getBdPrvArramt()); } } } } previousBillMas = currBillMas; } }
     */

    @Override
    public void updateArrearInCurrentBills(List<TbBillMas> billMasList, TbBillMas lastBillMas) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
        List<TbBillMas> previousBillMass = null;
        for (final TbBillMas currBillMas : billMasList) {
            if (previousBillMass == null) {
                previousBillMass = new ArrayList<>();
            }
            double amount = 0d;
            previousBillMass.add(lastBillMas);
            if (!previousBillMass.isEmpty()) {
                for (final TbBillMas prevBillMas : previousBillMass) {
                    amount += prevBillMas.getBmTotalBalAmount() + prevBillMas.getBmTotalArrearsWithoutInt();
                }
                currBillMas.setBmTotalArrearsWithoutInt(amount);
            }

            if (lastBillMas != null) {

                currBillMas
                        .setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
            } else {
                currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
            }

            currBillMas.setBmTotalArrears(
                    currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt());

            currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());
            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
                    && (lastBillMas != null)) {
                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
                    for (final TbBillDet lastDet : lastBillMas.getTbWtBillDet()) {
                        if (det.getTaxId().equals(lastDet.getTaxId())) {
                            det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                            det.setBdPrvBalArramt(det.getBdPrvArramt());
                        }
                    }
                }
            }
            lastBillMas = currBillMas;
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
    }

    @Override
    @Transactional
    public void calculatePenaltyTax(Organisation organisation, Long deptId, TbBillMas authBill, double diffAmount,
            AtomicDouble prvBalance, AtomicDouble prvtotal, AtomicDouble totalDemandWithoutRebate) {
        WSRequestDTO reqdto = new WSRequestDTO();
        reqdto.setModelName(MainetConstants.Property.PROP_BRMS_MODEL_FOR_INT_CAL);
        WSResponseDTO response = RestClient.callBRMS(reqdto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> propertyRateMasterList = RestClient.castResponse(response, PropertyRateMasterModel.class, 0);
            PropertyRateMasterModel propRateModel = (PropertyRateMasterModel) propertyRateMasterList.get(0);
            propertyBRMSService.fetchPenaltyRate(authBill, organisation, deptId, propRateModel, diffAmount, prvBalance,
                    prvtotal, totalDemandWithoutRebate);
        }
    }

    @Override
    @Transactional
    public BillDisplayDto calculateSurcharge(Organisation organisation, Long deptId, List<TbBillMas> billMasList,
            String propNo, String taxSubcat, Long finYearId,Date manualReceiptDate) {
        PropertyPenltyDto penltyDto = null;
        LookUp showAsArrearLookUp = null;
		try {
			showAsArrearLookUp = CommonMasterUtility.getValueFromPrefixLookUp("ANA", "SAA", organisation);
		} catch (Exception exception) {
			LOGGER.error("No Prefix found for SAA(ANA)");
		}
        // PropertyPenltyDto lastSurchargeCalculated = null;
        if (propNo != null && !propNo.isEmpty()) {
            penltyDto = propertyPenltyService.calculateExistingSurcharge(propNo, finYearId, organisation.getOrgid());
        }
        if (penltyDto == null) {
            int size = billMasList.size() - 1;
            double totalArrear = 0;
            boolean covidActiveFlag = checkDaysExtendedCovidActive(organisation);
            if(!covidActiveFlag) {
            	LookUp manualAssesment = null;
                try {
                	manualAssesment = CommonMasterUtility.getValueFromPrefixLookUp("MRA", "PAS", organisation);
                } catch (Exception exception) {
                    LOGGER.error("No Prefix found for PAS(MRA)");
                }
				if (manualReceiptDate != null
						&& (manualAssesment == null || StringUtils.isBlank(manualAssesment.getOtherField())
								|| StringUtils.equals(manualAssesment.getOtherField(), MainetConstants.FlagN))) {
            	    Date dueDateCrossed = new Date();
					if ((showAsArrearLookUp == null || StringUtils.isBlank(showAsArrearLookUp.getOtherField())
							|| StringUtils.equals(showAsArrearLookUp.getOtherField(), MainetConstants.FlagN))
							&& manualReceiptDate != null) {
						dueDateCrossed = manualReceiptDate;
					}
            		if(CollectionUtils.isNotEmpty(billMasList)) {
                		for (TbBillMas billMas : billMasList) {
                			if(Utility.compareDate(billMas.getBmDuedate(), dueDateCrossed) && !Utility.comapreDates(billMas.getBmDuedate(), dueDateCrossed)) {
                				totalArrear = totalArrear + billMas.getBmTotalBalAmount();
                				finYearId = billMas.getBmYear();
                    		}
    					}
                	}
            	}else {
            		if (finYearId.equals(billMasList.get(size).getBmYear())) {
                        totalArrear = billMasList.get(size).getBmTotalArrearsWithoutInt();
                    } else {
                        totalArrear = billMasList.get(size).getBmTotalArrearsWithoutInt()
                                + billMasList.get(size).getBmTotalBalAmount();
                    }
            	}
            }else {

				List<Double> totalArrearFinYear = arrearAmountIfCovidActive(organisation, billMasList, finYearId,
						manualReceiptDate);
				totalArrear = totalArrearFinYear.get(0);
				finYearId = totalArrearFinYear.get(1).longValue();
				 
            }
            BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(propNo, organisation.getOrgid(), null);
            if(advanceAmt != null && advanceAmt.getCurrentYearTaxAmt() != null) {
            	totalArrear = totalArrear - advanceAmt.getCurrentYearTaxAmt().doubleValue();
            }
            if (totalArrear > 0) {
            	double lastSurchargeCalAmount = excludeSurchargeCalculatedAmount(organisation, billMasList, propNo, manualReceiptDate);
            	totalArrear = totalArrear - lastSurchargeCalAmount;
                return propertyBRMSService.fetchSurCharge(organisation, deptId, totalArrear, taxSubcat, billMasList,finYearId,manualReceiptDate);
            }
        }
        return null;
    }

    /*
     * Difference between calculateSurcharge and calculateSurchargeAtAuthorizationEdit is calculateSurcharge for first time when
     * there is no entry in tb_as_penalty for that fin year, and calculateSurchargeAtAuthorizationEdit for even though for current
     * fin Year entry is available again calculate the surcharge as we editing the property
     */

    @Override
    @Transactional
    public BillDisplayDto calculateSurchargeAtAuthorizationEdit(Organisation organisation, Long deptId,
            List<TbBillMas> billMasList, String propNo, String taxSubcat, Long finYearId) {
        int size = billMasList.size() - 1;
        double totalArrear;
        if (finYearId.equals(billMasList.get(size).getBmYear())) {
            totalArrear = billMasList.get(size).getBmTotalArrearsWithoutInt();
        } else {
            totalArrear = billMasList.get(size).getBmTotalArrearsWithoutInt()
                    + billMasList.get(size).getBmTotalBalAmount();
        }
        if (totalArrear > 0) {
            return propertyBRMSService.fetchSurCharge(organisation, deptId, totalArrear, taxSubcat, billMasList,finYearId,null);
        }
        return null;
    }

    @Override
    @Transactional
    public BillDisplayDto getSurChargetoView(Organisation organisation, Long deptId, String propNo, String taxSubcat,
            Long finYearId, Long applicationNo, BigDecimal billPaidAmt) {
        BillDisplayDto billdto = null;
        PropertyPenltyDto penltyDto = propertyPenltyService.calculateExistingSurcharge(propNo, finYearId,
                organisation.getOrgid());
        if (penltyDto != null) {

            LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(taxSubcat,
                    PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());
            final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA, organisation);

            List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(
                    organisation.getOrgid(), deptId, taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());

            if (taxList != null && !taxList.isEmpty()) {
                TbTaxMasEntity tax = taxList.get(0);
                int count = iReceiptEntryService.getcountOfTaxExistAgainstAppId(organisation.getOrgid(), applicationNo,
                        deptId, tax.getTaxId());// #12562
                if (count > 0) {
                    billdto = new BillDisplayDto();
                    billdto.setTaxDesc(tax.getTaxDesc());
                    billdto.setDisplaySeq(tax.getTaxDisplaySeq());
                    billdto.setTaxId(tax.getTaxId());
                    billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(penltyDto.getActualAmount())));
                    billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(penltyDto.getActualAmount())));
                    billdto.setArrearsTaxAmt(BigDecimal.valueOf(0));
                    billdto.setTaxCategoryId(tax.getTaxCategory1());
                }

            }
        }

        return billdto;
    }

    @Override
    @Transactional
	public List<Long> propertyBillGeneration(List<NoticeGenSearchDto> list, long orgId, Long empId, String ipAddress,
			Long logLocId, int languageId, Long parentBmNumber) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " propertyBillGeneration() method & orgId : "+orgId);
    	final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.STATUS.ACTIVE);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<Long> billIdsGen = new ArrayList<>(0);
        int langId = Utility.getDefaultLanguageId(organisation);
        int count = list.size();
        AtomicLong selectedCount = new AtomicLong(count);
        AtomicLong errorCount = new AtomicLong(0);
        AtomicLong successfulCount = new AtomicLong(0);
        PropertyBillGenerationMap propBillGenerationMap = new PropertyBillGenerationMap();
        ApplicationSession.getInstance().getPropBillGenerationMapOrgId().put(orgId, propBillGenerationMap);
        Long currentYearId = iFinancialYearService.getFinanceYearId(new Date());
     	final ExecutorService executorService = Executors.newFixedThreadPool(Integer.valueOf(ApplicationSession.getInstance().getMessage("bill.thread.pool.size")));
     	LOGGER.info("Bill Generation process is started total size is : "+count);
     	if(CollectionUtils.isNotEmpty(list)) {
     		LOGGER.info("bill gen check"+list.get(0).getGenNotCheck());
     	}else {
     		LOGGER.info("List not found");
     	}
     	list.stream().filter(dto -> MainetConstants.NewWaterServiceConstants.YES.equals(dto.getGenNotCheck()))
                .forEach(dto -> {
                	executorService.execute(new Runnable() {
    					public void run() {
    						 LOGGER.info(String.format("starting thread before task thread %s", Thread.currentThread().getName()));
    						 propertyBillGeneration(orgId, empId, ipAddress, languageId, deptId, organisation, billIdsGen,
    									langId, count, selectedCount, errorCount, successfulCount, dto,propBillGenerationMap,parentBmNumber,currentYearId);
    						 LOGGER.info(String.format("starting thread after task thread %s after", Thread.currentThread().getName()));
    					}
    				});
                });
        executorService.shutdown(); 
        while (!executorService.isTerminated()) {
        	 
		}
		LOGGER.info("\nFinished all threads");
        billMasterCommonService.doVoucherPosting(billIdsGen, organisation, MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                empId, logLocId);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " propertyBillGeneration() method");
        return billIdsGen;
    }

    private void propertyBillGeneration(long orgId, Long empId, String ipAddress, int languageId, final Long deptId,
			Organisation organisation, List<Long> billIdsGen, int langId, int count, AtomicLong selectedCount,
			AtomicLong errorCount, AtomicLong successfulCount, NoticeGenSearchDto dto, PropertyBillGenerationMap propBillGenerationMap, Long parentBmNumber,Long currentYearId) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " propertyBillGeneration() method & propertyId : "+dto.getPropertyNo());
		String assType = null;
		LookUp skdclEnv  = null;
		boolean billNotExist = true;
		boolean billDataCorrect = true;
		try {
			skdclEnv = CommonMasterUtility.getValueFromPrefixLookUp("SKDCL", "ENV", organisation);
		}catch (Exception e) {
			LOGGER.error("No Prefix found for ENV(SKDCL)");
		}
		long startTime = System.currentTimeMillis();
		try {
		 	String logicalPropNo  = null;
		     LOGGER.info("Bill Generation started for Property  >>" + dto.getPropertyNo());
		     LOGGER.info("Bill Generation started for Property Count >>" + selectedCount);
		     ProvisionalAssesmentMstDto assMst = null;
		     if(dto.getFlatNo() != null) {
		    	LOGGER.info("Flat no is present propert no & flat no "+dto.getPropertyNo()+"  "+dto.getFlatNo()); 
		     	StringBuilder createLogicalPropNo = new StringBuilder();
		     	createLogicalPropNo.append(dto.getPropertyNo());
		     	createLogicalPropNo.append("_");
		     	createLogicalPropNo.append(dto.getFlatNo());
		     	logicalPropNo = createLogicalPropNo.toString();
		     	assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(orgId, dto.getPropertyNo(), dto.getOldPropertyNo(), "A",logicalPropNo);
		     }else {
		     	assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(orgId, dto.getPropertyNo(), dto.getOldPropertyNo(), "A",null);
		     }
		     if(assMst != null && checkSplNotDueDAtePassed(organisation, assMst)) {
		    	 
		    	 List<TbBillMas> billMasArrears = null;
		    	 Long billExistCount = 0L;
	             if(dto.getFlatNo() != null) {
	             	billMasArrears = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(assMst.getAssNo(), orgId, dto.getFlatNo());
	             }else {
	             	billMasArrears = propertyMainBillService
	                         .fetchNotPaidBillForAssessment(assMst.getAssNo(), orgId);
	             }
	             
		    	 LOGGER.info("Assesment Found for : "+dto.getPropertyNo()); 
		    	 List<Long> finYearList = new ArrayList<>();
		    	 List<FinancialYear> financialYearList = new ArrayList<FinancialYear>();
		    	 if ((Utility.isEnvPrefixAvailable(organisation, "ASCL") || Utility.isEnvPrefixAvailable(organisation, "PSCL")) && StringUtils.isBlank(dto.getFinYear())) {
		    		 Long maxYearId = ApplicationContextProvider.getApplicationContext().getBean(PropertyMainBillRepository.class).getMaxFinYearIdByPropNo(assMst.getAssNo(), orgId);
						if (maxYearId != null && maxYearId >0) {
							financialYearList = iFinancialYearService.getFinanceYearListAfterGivenDate(
									organisation.getOrgid(), maxYearId,
									new Date());
						} else {
							Long financeYearId = iFinancialYearService.getFinanceYearId(new Date());
							FinancialYear currentYear = iFinancialYearService.getFinincialYearsById(financeYearId, orgId);
							financialYearList.add(currentYear);
							if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
								assType = MainetConstants.Property.NEW_ASESS;
								Long acquistionYear = iFinancialYearService.getFinanceYearId(assMst.getAssAcqDate());
								if(!acquistionYear.equals(currentYearId)) {
									financialYearList = new ArrayList<FinancialYear>();
									financialYearList = iFinancialYearService.getFinanceYearListAfterGivenDateForNewBills(
											organisation.getOrgid(), acquistionYear,
											new Date());
								}
							}

						}
					} else if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")){
						FinancialYear selectedFinYearForSkdcl = iFinancialYearService
								.getFinincialYearsById(Long.valueOf(dto.getFinYear()), orgId);
						financialYearList.add(selectedFinYearForSkdcl);
					}else {
						financialYearList = iFinancialYearService.getFinanceYearListAfterGivenDate(
								organisation.getOrgid(), assMst.getFaYearId(),
								new Date());
					}
		        
		         if (!financialYearList.isEmpty()) {
		        	 LOGGER.info("financialYearList is not empty : "+dto.getPropertyNo()); 
                     setUnitDetailsForNextYears(assMst, finYearList, financialYearList);

		         }
		         if(CollectionUtils.isNotEmpty(finYearList)) {
		        	 if (dto.getFlatNo() != null) {
							billExistCount = propertyMainBillRepository
									.getCountOfBillExistYearByPropNoAndFlatNo(assMst.getAssNo(), dto.getFlatNo(), finYearList);
						} else {
							billExistCount = propertyMainBillRepository.getCountOfBillExistYearByPropNo(assMst.getAssNo(),
									finYearList);
						}
		         }else {
		        	 LOGGER.error("Fin year list empty or duplicate bill" + dto.getPropertyNo());
			    	 saveExceptionDetails(orgId, empId, ipAddress, "Fin year list empty or duplicate bill", "BG",dto.getPropertyNo());
			    	 errorCount.getAndIncrement();
			    	 billExistCount = 1L;
		         }
		         
		         if(CollectionUtils.isNotEmpty(billMasArrears)) {
		        	 propertyMainBillService.copyDataFromMainBillDetailToHistory(billMasArrears, "G",empId,ipAddress);
	            	 String validateBillData = propertyMainBillService.ValidateBillData(billMasArrears);
	            	 if(StringUtils.isNotBlank(validateBillData)) {
	            		 billDataCorrect = false;
	            		 LOGGER.error("Bill data is not proper "+dto.getPropertyNo() +" issue -> "+ validateBillData);
	            	 }
	             }
				
				if(billExistCount > 0) {
	            	 billNotExist = false;
	             }
		        if(billNotExist && billDataCorrect) {
		        	 Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
			         Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService
			                 .fetchCharges(assMst, deptId, taxWiseRebate, finYearList,null,assType,null,MainetConstants.FlagY);
			         if (!schWiseChargeMap.isEmpty()) {
			        	 LOGGER.info("Charges are found");
			        	 List<TbBillMas> billMasList = generateNewBill(schWiseChargeMap,
			              		organisation, deptId, assMst.getAssNo(),dto.getFlatNo());

			              List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService
			                      .knowkOffDemandLevelRebateAndExemption(billMasList, schWiseChargeMap,
			                      		organisation, taxWiseRebate);
			              
						Map<Long, Double> finAlvMap = new HashMap<>();
						Map<Long, Double> finRvMap = new HashMap<>();
						Map<Long, Double> finCvmap = new HashMap<>();
						Map<Long, Double> finStdRate = new HashMap<>();
						assMst.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
							if (!finAlvMap.containsKey(detail.getFaYearId())) {
								finAlvMap.put(detail.getFaYearId(), detail.getAssdAlv());
								finRvMap.put(detail.getFaYearId(), detail.getAssdRv());
								finCvmap.put(detail.getFaYearId(), detail.getAssdCv());
								finStdRate.put(detail.getFaYearId(), detail.getAssdStdRate());
							} else {
								finAlvMap.put(detail.getFaYearId(),
										finAlvMap.get(detail.getFaYearId()) + detail.getAssdAlv());
								finRvMap.put(detail.getFaYearId(), finRvMap.get(detail.getFaYearId()) + detail.getAssdRv());
								finCvmap.put(detail.getFaYearId(), finCvmap.get(detail.getFaYearId()) + detail.getAssdCv());
							}
						});
						for (TbBillMas bill : billMasList) {
							bill.setAssdStdRate(finStdRate.get(bill.getBmYear()));
							bill.setAssdAlv(finAlvMap.get(bill.getBmYear()));
							bill.setAssdRv(finRvMap.get(bill.getBmYear()));
							bill.setAssdCv(finCvmap.get(bill.getBmYear()));
						}
						List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
						List<AsExcessAmtEntity> excessAmtEntByPropNo = null;
			            boolean previousReceiptNotAdjFlag = false;
			              							            					
			              if (billMasArrears != null && !billMasArrears.isEmpty()) {
			                  TbBillMas lastBillMas = billMasArrears.get(billMasArrears.size() - 1);
			                  if (skdclEnv != null && StringUtils.isNotBlank(skdclEnv.getOtherField())
			          				&& StringUtils.equals(skdclEnv.getOtherField(), MainetConstants.FlagY)) {
			                  	billMasList.forEach(billMas ->{
			                  		billMas.setIntFrom(lastBillMas.getIntFrom());
			                  		billMas.setIntTo(lastBillMas.getIntTo());
			                  	});
			                  }
			                  updateArrearInCurrentBills(billMasList, lastBillMas);
			                  if (Utility.isEnvPrefixAvailable(organisation, "PSCL")
										&& CollectionUtils.isNotEmpty(billMasArrears)) {
									previousReceiptNotAdjFlag = updatingPendingReceiptData(billMasList,orgId, empId, languageId, deptId,
											organisation, assMst, billMasArrears, provBillList, previousReceiptNotAdjFlag,MainetConstants.FlagY);
								}
			                  
			                	  billMasList.addAll(billMasArrears);
				                  billMasList.sort(Comparator.comparing(TbBillMas::getBmFromdt)); 
				                  
			                  if (skdclEnv != null && StringUtils.isNotBlank(skdclEnv.getOtherField())
			          				&& StringUtils.equals(skdclEnv.getOtherField(), MainetConstants.FlagY)) {
			              		ApplicationContextProvider.getApplicationContext().getBean(PropertyBRMSService.class).fetchInterstRate(billMasList, organisation, deptId);
			              		taxCarryForward(billMasList, organisation.getOrgid());
			  					billMasterCommonService.calculatePenaltyInterestForBillGen(billMasArrears, organisation, deptId, "Y", null, "N", "Y",empId,billMasList);
			              	}else {
			              		interestCalculationWithoutBRMSCall(organisation, deptId, billMasList,
			                              MainetConstants.Property.INT_RECAL_NO);
			              	}
			              }
			              taxCarryForward(billMasList, orgId);
			              
			              
			              BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(assMst.getAssNo(), orgId,dto.getFlatNo());
			              if(previousReceiptNotAdjFlag) {
			            	  advanceAmt = null;
			              }
			              List<BillReceiptPostingDTO> billRecePstingDto = null;
			              double ajustedAmt = 0;
			              if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
			                  billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(billMasList, advanceAmt, organisation,new Date(),null);
			                  if (billMasList.get(billMasList.size() - 1).getExcessAmount() > 0) {
			                      ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue()
			                              - billMasList.get(billMasList.size() - 1).getExcessAmount();
			                  } else {
			                      ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue();
			                  }
			              }
			              
			              if(CollectionUtils.isNotEmpty(billMasArrears)) {
			            	  if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
			            		  if(advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
			            			  List<TbBillMas> arrearList = new ArrayList<TbBillMas>();
			            			  billMasList.forEach(billMas ->{
			            				  if(billMas.getBmIdno() > 0) {
			            					  arrearList.add(billMas);
			            				  }
			            			  });
			            			  billMasArrears = new ArrayList<TbBillMas>();
			            			  billMasArrears.addAll(arrearList);
			            		  }
			            		  billMasterCommonService.calculateInterestForPrayagRaj(billMasList, organisation, deptId, "Y", null, empId,billMasArrears,MainetConstants.FlagY);
			            		  updateArrearInCurrentBillsForAdvanceAdjustment(billMasList, billMasArrears.get(billMasArrears.size() -1));
			            	  }
			              }
			              updateOutStanding(billMasList);
			                            
						// #37183
						String parentPropNo = null;
						if (StringUtils.isNotBlank(dto.getParentPropNo())) {
							parentPropNo = dto.getParentPropNo();
						}					
						if (parentBmNumber != null && parentBmNumber != 0) {
							Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
							for (TbBillMas bill : billMasList) {
								bill.setParentMnNo(String.valueOf(parentBmNumber));
								bill.setParentPropNo(parentPropNo);
							}
							BillDisplayDto surChargeDto = calculateSurcharge(organisation, deptId, billMasList,
									assMst.getAssNo(), MainetConstants.Property.SURCHARGE, finYearId, null);
							if (surChargeDto != null)
								propertyPenltyService.savePropertyPenlty(assMst.getAssNo(), finYearId, orgId, empId,
										ipAddress, surChargeDto.getCurrentYearTaxAmt().doubleValue(),
										surChargeDto.getCurrentYearTaxAmt().doubleValue(),parentPropNo);
						}					
						//
			              LOGGER.info("Transaction query execution starts for ..."+dto.getPropertyNo());
			              selfAssessmentService.saveAdvanceAmt(assMst, empId, deptId, advanceAmt, organisation, billMasList, assMst.getLgIpMac(),
			                      provBillList, billRecePstingDto, ajustedAmt,dto.getFlatNo(),languageId);
			              List<Long> bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, empId,
			                      assMst.getAssNo(), null, provBillList, ipAddress);
			              selfAssessmentService.saveDemandLevelRebate(assMst, empId, deptId, billMasList, reabteRecDtoList,
			                      organisation, provBillList);
						  List<MainBillMasEntity> mainBillMasList  = propertyMainBillService.saveAndUpdateMainBillFromProvisionalBill(provBillList, orgId, empId,
								MainetConstants.Property.AuthStatus.AUTH, ipAddress);
						  
						  List<MainBillMasEntity> mailBillMasForDupBill = new ArrayList<MainBillMasEntity>();
						  finYearList.forEach(finYear ->{
							  mainBillMasList.forEach(mainBillMas ->{
								  if(finYear.equals(mainBillMas.getBmYear())) {
									  mailBillMasForDupBill.add(mainBillMas);
								  }
							  });
						  });
						  
						  propertyBillGenerationService.saveDuplicateBill(mailBillMasForDupBill, assMst, langId, orgId);
						  
			              iProvisionalBillService.deleteProvisionalBillsWithEntityById(provBillList);
			              billIdsGen.addAll(bmIds);
			              sendSmsAndMail(assMst, organisation, langId, provBillList.get(provBillList.size() - 1), empId);
			              successfulCount.getAndIncrement();
			              LOGGER.info("Transaction query execution ends for ..."+dto.getPropertyNo());
			         }else {
			        	 LOGGER.error("fetchCharges is found null :  " + assMst.getAssNo());
			        	 saveExceptionDetails(orgId, empId, ipAddress, "financialYearList is found null", "BG",assMst.getAssNo());
			        	 errorCount.getAndIncrement();
			         }
		        }else {
		        	if(!billDataCorrect) {
	            		 LOGGER.error("fetchCharges is found null :  " + assMst.getAssNo());
			        	 saveExceptionDetails(orgId, empId, ipAddress, "Bill data correction is required", "BG",assMst.getAssNo());
	            	 }
	            	 errorCount.getAndIncrement();
		        }
		     }else {
		    	 if(assMst == null) {
		    		 LOGGER.error("No entity found:  " + dto.getPropertyNo());
			    	 saveExceptionDetails(orgId, empId, ipAddress, "No entity found", "BG",dto.getPropertyNo());
		    	 }else {
		    		 LOGGER.error("Special notice due date is not completed" + dto.getPropertyNo());
			    	 saveExceptionDetails(orgId, empId, ipAddress, "Special notice due date is not completed", "BG",dto.getPropertyNo());
		    	 }
		    	 errorCount.getAndIncrement(); 
		     }
		    
		     selectedCount.getAndDecrement();
		}catch (Exception e) {
			 LOGGER.error("Prvisional Demand not generated for Property :" + dto.getPropertyNo());
		     LOGGER.error("Exception occured due to this "+e);
			 errorCount.getAndIncrement();
			 selectedCount.getAndDecrement();
			 if(StringUtils.isBlank(e.toString())) {
				 saveExceptionDetails(orgId, empId, ipAddress, "unable to genearte billUnable to find exception log", "BG",dto.getPropertyNo());
			 }else {
				 saveExceptionDetails(orgId, empId, ipAddress, e.toString(), "BG",dto.getPropertyNo());
			 }
		}
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);  // Total execution time in milli seconds
		LOGGER.info("Time taken for one bill generation : " + duration + " MiliSecond");
		setPropertyBillGenerationMap(count, successfulCount, errorCount, duration, orgId, propBillGenerationMap);
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " propertyBillGeneration() method & propertyId : "+dto.getPropertyNo());
	}

	private boolean checkSplNotDueDAtePassed(Organisation organisation, ProvisionalAssesmentMstDto assMst) {
		boolean splDueDatePassed = true;
		 if(Utility.isEnvPrefixAvailable(organisation, "PSCL") && assMst.getSplNotDueDate() != null && (Utility.comapreDates(assMst.getSplNotDueDate(), new Date()) || !new Date().after(assMst.getSplNotDueDate()))) {
			 splDueDatePassed = false;
		 }
		 return splDueDatePassed;
	}

    @Override
	public boolean updatingPendingReceiptData(List<TbBillMas> billMasList,long orgId, Long empId, int languageId, final Long deptId,
			Organisation organisation, ProvisionalAssesmentMstDto assMst, List<TbBillMas> billMasArrears,
			List<ProvisionalBillMasEntity> provBillList, boolean previousReceiptNotAdjFlag,String saveFlag) {
		List<AsExcessAmtEntity> excessAmtEntByPropNo;
		excessAmtEntByPropNo = asExecssAmtService.getExcessAmtEntByPropNo(assMst.getAssNo(), orgId);
		if (CollectionUtils.isNotEmpty(excessAmtEntByPropNo)) {
			List<AsExcessAmtEntity> previousReceiptNotAdjustedList = excessAmtEntByPropNo.stream()
					.filter(excess -> StringUtils.isNotBlank(excess.getExtraCol2())
							&& StringUtils.equals("Y", excess.getExtraCol2()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(previousReceiptNotAdjustedList)) {
				previousReceiptNotAdjFlag = true;
				updatePreviousYearReceipts(billMasList,excessAmtEntByPropNo, organisation,
						billMasArrears, deptId, empId, provBillList, assMst, languageId,saveFlag);

			}
		}
		return previousReceiptNotAdjFlag;
	}

    @Override
    public Map<String, Long> getApplicationNumberByRefNo(String propNo, Long serviceId, Long orgId, Long empId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        String serviceShortcode = serviceMasterService.fetchServiceShortCode(serviceId, orgId);
        Map<String, Long> outputMap = new HashMap<>();
        if (serviceShortcode.equals("MUT")) {
            outputMap.put(MainetConstants.Objection.APPLICTION_NO,
                    propertyTransferService.getApplicationIdByPropNo(propNo, orgId));
            outputMap
                    .put(MainetConstants.Objection.PERIOD,
                            TimeUnit.MILLISECONDS.convert(
                                    Long.valueOf(CommonMasterUtility
                                            .getDefaultValueByOrg(MainetConstants.Property.PNC, org).getOtherField()),
                                    TimeUnit.DAYS));

        } else {
            outputMap.put(MainetConstants.Objection.APPLICTION_NO,
                    assesmentMastService.getApplicationNoByPropNoForObjection(orgId, propNo, serviceId));
            outputMap.put(MainetConstants.Objection.PERIOD,
                    TimeUnit.MILLISECONDS.convert(
                            Long.valueOf(CommonMasterUtility
                                    .getDefaultValueByOrg(MainetConstants.Property.propPref.SNC, org).getOtherField()),
                            TimeUnit.DAYS));

        }
        return outputMap;
    }

    private void sendSmsAndMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId,
            ProvisionalBillMasEntity provisionalBillMasEntity, Long userId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setUserId(userId);
        dto.setEmail(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
        dto.setMobnumber(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
        if (provAsseMstDto.getApmApplicationId() != null) {
            dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        }
        dto.setPropertyNo(provAsseMstDto.getAssNo());
        dto.setBillNo(provisionalBillMasEntity.getBmNo());
        dto.setDueDt(Utility.dateToString(provisionalBillMasEntity.getBmDuedate()));
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "PropertyBillGeneration.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, organisation, langId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getApplicantUserNameModuleWise(long orgId, String propNo) {
        String name = null;
        List<ProvisionalAssesmentMstDto> assMstList = null;
        assMstList = iProvisionalAssesmentMstService.getPropDetailFromProvAssByPropNoOrOldPropNo(orgId, propNo, null);// Fetching
                                                                                                                      // latest
                                                                                                                      // Detail
        if (assMstList == null || assMstList.isEmpty()) {

            assMstList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, propNo, null);
        }
        if (assMstList != null && !assMstList.isEmpty()) {
            name = assMstList.get(assMstList.size() - 1).getProvisionalAssesmentOwnerDtlDtoList().get(0)
                    .getAssoOwnerName();
        }
        return name;
    }

    @Override
    @Transactional
    public boolean revertBills(TbServiceReceiptMasBean feedetailDto, Long userId, String ipAddress) {
        Long orgId = feedetailDto.getOrgId();
        String propertyNo = feedetailDto.getAdditionalRefNo();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);

        List<TbBillMas> billMasList = null;
        int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
        if (count > 0) {
            billMasList = propertyMainBillService.fetchAllBillByPropNo(propertyNo, orgId);
        } else {
            billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(propertyNo, orgId);
        }
        AsExcessAmtEntity excessAmtEnt = asExecssAmtService.getAdvanceEntryByRecptId(feedetailDto.getRmRcptid(),
                propertyNo, orgId);
        BillReceiptPostingDTO excessReceiptDto = null;
        if (excessAmtEnt != null) {
            asExecssAmtService.inactiveAdvPayEnrtyByExcessId(excessAmtEnt.getExcessId(), orgId);
            excessReceiptDto = new BillReceiptPostingDTO();
            excessReceiptDto.setBillDetId(excessAmtEnt.getExcessId());
            excessReceiptDto.setTaxId(excessAmtEnt.getTaxId());
            TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(excessAmtEnt.getTaxId(), org.getOrgid());
            excessReceiptDto.setTaxCategory(tax.getTaxCategory1());
            excessReceiptDto.setTaxAmount(excessAmtEnt.getExcAmt());
            Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
            excessReceiptDto.setYearId(finYearId);
        }

        if (billMasList != null && !billMasList.isEmpty()) {
            List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();

            List<TbServiceReceiptMasBean> feedetailDtoList = new ArrayList<TbServiceReceiptMasBean>();
            feedetailDtoList.add(feedetailDto);
            rvertBill(feedetailDtoList, billPosDtoList, billMasList, org,userId,ipAddress);
            if(excessReceiptDto != null) {
            	billPosDtoList.add(excessReceiptDto);
            }
            LookUp penaltyLookup = CommonMasterUtility.getHieLookupByLookupCode(
                    MainetConstants.Property.CHEQUE_DISHONR_CHARGES, PrefixConstants.LookUpPrefix.TAC, 2,
                    org.getOrgid());
            final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
            if (penaltyLookup != null && taxAppAtBill != null) {
                List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(),
                        deptId, taxAppAtBill.getLookUpId(), penaltyLookup.getLookUpId());
                if (taxList != null && !taxList.isEmpty()) {
                    TbTaxMasEntity det = taxList.get(0);

                    boolean isexist = billMasList.get(0).getTbWtBillDet().stream()
                            .filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString())).findFirst()
                            .isPresent();
                    if (isexist) {
                        billMasList.get(billMasList.size() - 1).getTbWtBillDet().stream()
                                .filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString()))
                                .forEach(taxDet -> {
                                    taxDet.setBdCurTaxamt(taxDet.getBdCurTaxamt()
                                            + feedetailDto.getReceiptModeDetailList().getRdSrChkDisChg());
                                    taxDet.setBdCurBalTaxamt(taxDet.getBdCurBalTaxamt()
                                            + feedetailDto.getReceiptModeDetailList().getRdSrChkDisChg());
                                });
                    } else {
                        TbBillDet taxDet = new TbBillDet();
                        taxDet.setTaxId(det.getTaxId());
                        taxDet.setTaxCategory(det.getTaxCategory1());
                        taxDet.setCollSeq(det.getCollSeq());
                        taxDet.setBdCurTaxamt(feedetailDto.getReceiptModeDetailList().getRdSrChkDisChg());
                        taxDet.setBdCurBalTaxamt(feedetailDto.getReceiptModeDetailList().getRdSrChkDisChg());
                        billMasList.get(billMasList.size() - 1).getTbWtBillDet().add(taxDet);
                    }
                }
            }

            if (!billMasList.isEmpty()) {
                billMasterCommonService.updateBillData(billMasList, 0d, null, null, org, null,null);
            }
            if (count > 0) {
                // Form Main Bill table
				propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId, userId, null, ipAddress);
            } else {
                // From Provisional Bill Table
                iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, userId, propertyNo, null, null,
                        null);
            }
        }

        TbSrcptModesDetBean modesId = feedetailDto.getReceiptModeDetailList();
        final TbSrcptModesDetEntity feeDet = chequeDishonorService.fetchReceiptFeeDetails(modesId.getRdModesid(),
                modesId.getOrgid());
        feeDet.setRdSrChkDate(modesId.getRdSrChkDate());
        feeDet.setRdSrChkDisChg(modesId.getRdSrChkDisChg());
        feeDet.setRdSrChkDis(modesId.getRdSrChkDis());
        feeDet.setRdV1(modesId.getRdV1());
        chequeDishonorService.updateFeeDet(feeDet);
        return true;
    }

    @Override
    public void rvertBill(List<TbServiceReceiptMasBean> feedetailDtoList, List<BillReceiptPostingDTO> billPosDtoList,
            List<TbBillMas> billMasList, Organisation org, Long userId, String ipAddress) {
        AtomicDouble totalCurBalAmt = new AtomicDouble(0);
        AtomicDouble totInt = new AtomicDouble(0);
        AtomicDouble totPenlty = new AtomicDouble(0);
        feedetailDtoList.forEach(feedetailDto ->{
        feedetailDto.getReceiptFeeDetail().forEach(det -> {
            billMasList.stream().filter(billMas -> Long.valueOf(billMas.getBmIdno()).equals(det.getBmIdNo()))
                    .forEach(billMas -> {
                        billMas.getTbWtBillDet().forEach(billDet -> {
                            if (det.getBilldetId() != null && det.getBilldetId().equals(Long.valueOf(billDet.getBdBilldetid()))) {
                                BillReceiptPostingDTO billPosDto = new BillReceiptPostingDTO();
                                if (billDet.getBdCurTaxamt() > billDet.getBdCurBalTaxamt()) {
                                    billDet.setBdCurBalTaxamt(
                                            billDet.getBdCurBalTaxamt() + det.getRfFeeamount().doubleValue());
                                }
                                billPosDto.setBillDetId(billDet.getBdBilldetid());
                                billPosDto.setTaxId(billDet.getTaxId());
                                billPosDto.setTaxCategory(billDet.getTaxCategory());
                                billPosDto.setTaxAmount(det.getRfFeeamount().doubleValue());
                                billPosDto.setYearId(billMas.getBmYear());
                                billPosDtoList.add(billPosDto);
                                final String taxCode = CommonMasterUtility
                                        .getHierarchicalLookUp(billDet.getTaxCategory(), org).getLookUpCode();
                                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                                    totInt.addAndGet(det.getRfFeeamount().doubleValue());

                                } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                                    totPenlty.addAndGet(det.getRfFeeamount().doubleValue());
                                } else if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                                    totalCurBalAmt.addAndGet(det.getRfFeeamount().doubleValue());
                                }
                            }
                        });

                        billMas.setBmTotalBalAmount(billMas.getBmTotalBalAmount() + totalCurBalAmt.doubleValue());
                        billMas.setBmToatlInt(billMas.getBmToatlInt() + totInt.doubleValue());
                        billMas.setTotalPenalty(billMas.getTotalPenalty() + totPenlty.doubleValue());
                        billMas.setBmPaidFlag(MainetConstants.FlagN);
                        billMas.setBmLastRcptamt(0);
                        billMas.setBmLastRcptdt(null);
                        totalCurBalAmt.set(0);
                        totInt.set(0);
                        totPenlty.set(0);
                    });
            billMasList.get(billMasList.size() - 1).setBmLastRcptamt(0);
            TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), org.getOrgid());
            final String lookupCode = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org)
                    .getLookUpCode();
            if (StringUtils.isNotBlank(lookupCode) && lookupCode.equals(MainetConstants.Property.SURCHARGE)) {
                Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
					
                BillReceiptPostingDTO billPosDto = new BillReceiptPostingDTO();
                PropertyPenltyDto penaltyDto = propertyPenltyService.calculateExistingSurcharge(feedetailDto.getAdditionalRefNo(), finYearId, org.getOrgid());
                if(penaltyDto != null) {
                	 penaltyDto.setPendingAmount(penaltyDto.getPendingAmount() + det.getRfFeeamount().doubleValue());
                     penaltyDto.setActiveFlag(MainetConstants.FlagI);
                     propertyPenltyService.updatePropertyPenalty(penaltyDto, ipAddress, userId);
                     billPosDto.setBillDetId(penaltyDto.getPenaltyId());
                     billPosDto.setTaxId(tax.getTaxId());
                     billPosDto.setTaxCategory(tax.getTaxCategory1());
                     billPosDto.setTaxAmount(det.getRfFeeamount().doubleValue());
                     billPosDto.setYearId(penaltyDto.getFinYearId());
                     billPosDtoList.add(billPosDto);
                }
            }
            if (det.getBilldetId() == null && det.getBmIdNo() == null) {
                BillReceiptPostingDTO billPosDto = new BillReceiptPostingDTO();
                billPosDto.setTaxId(det.getTaxId());
                TbTaxMas tbTax = tbTaxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), org.getOrgid());
                billPosDto.setTaxCategory(tbTax.getTaxCategory1());
                billPosDto.setTaxAmount(det.getRfFeeamount().doubleValue());
            }
        });
    });
    }

    @Override
    public VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress) {
        String propertyNo = feedetailDto.getAdditionalRefNo();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<TbBillMas> billMasList = null;
        int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
        if (count > 0) {
            billMasList = propertyMainBillService.fetchAllBillByPropNo(propertyNo, orgId);
        } else {
            billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(propertyNo, orgId);
        }

        if (billMasList != null && !billMasList.isEmpty()) {
            if ((Utility.isEnvPrefixAvailable(org, "SDV")) || (Utility.comapreDates(feedetailDto.getRmDate(), billMasList.get(billMasList.size() - 1).getBmBilldt())
                    || feedetailDto.getRmDate().after(billMasList.get(billMasList.size() - 1).getBmBilldt()))) {
            	 BillReceiptPostingDTO excessReceiptDto = null;
                List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();
                if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
                	createBillDetWhereFirstBillHaveArrearAmountForReceiptReversal(billMasList);
                }
                AsExcessAmtEntity excessAmtEnt = asExecssAmtService.getAdvanceEntryByRecptId(feedetailDto.getRmRcptid(),
                        propertyNo, orgId);
                if (excessAmtEnt != null) {
                    asExecssAmtService.inactiveAdvPayEnrtyByExcessId(excessAmtEnt.getExcessId(), orgId);
                    excessReceiptDto = new BillReceiptPostingDTO();
                    excessReceiptDto.setBillDetId(excessAmtEnt.getExcessId());
                    excessReceiptDto.setTaxId(excessAmtEnt.getTaxId());
                    TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(excessAmtEnt.getTaxId(), org.getOrgid());
                    excessReceiptDto.setTaxCategory(tax.getTaxCategory1());
                    excessReceiptDto.setTaxAmount(excessAmtEnt.getExcAmt());
                    Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
                    excessReceiptDto.setYearId(finYearId);
                }
			
                List<TbServiceReceiptMasBean> feedetailDtoList = new ArrayList<TbServiceReceiptMasBean>();
                List<TbServiceReceiptMasBean> rebateReceiptList = iReceiptEntryService.findReceiptByReceiptDateType(feedetailDto.getRmRcptid(), orgId, feedetailDto.getRmDate(), feedetailDto.getDpDeptId(), "RB");
                feedetailDtoList.add(feedetailDto);
                if(CollectionUtils.isNotEmpty(rebateReceiptList)) {
                	feedetailDtoList.addAll(rebateReceiptList);
                }
                if(excessReceiptDto != null) {
                	billPosDtoList.add(excessReceiptDto);
                }
                rvertBill(feedetailDtoList, billPosDtoList, billMasList, org,userId,ipAddress);
                
                final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
                billMasList.get(billMasList.size() - 1).setBmToatlRebate(0);
                billMasterCommonService.updateBillData(billMasList, 0d, null, null, org, rebateDetails,null);
                billMasList.get(billMasList.size() - 1).setBmLastRcptdt(null);
                if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
                	reArrangeTheDataWhereFirstBillHaveArrearAmount(billMasList);
                }
                if (count > 0) {
                    // Form Main Bill table
                    propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId, userId, null, ipAddress);
                } else {
                    // From Provisional Bill Table
                    iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, userId, propertyNo, null,
                            null, null);
                }
			
                return iReceiptEntryService.getAccountPostingDtoForBillReversal(billPosDtoList, org);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean updateAccountPostingFlag(List<Long> bmIdNo, String flag) {
        iProvisionalBillService.updateAccountPostingFlag(bmIdNo, flag);
        return true;
    }

    @Override
    public List<TbBillMas> fetchCurrentBill(String propertyNo, Long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>();
		String checkActiveFlag = null;
		//128051 - Active properties check is added
		List<String> checkActiveFlagList = assMstService.checkActiveFlag(propertyNo, orgId);
		if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
			checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
		}
		if (StringUtils.equals(MainetConstants.FlagA, checkActiveFlag)) {
			int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
			if (count > 0) {
				billMasList = propertyMainBillService.fetchAllBillByPropNo(propertyNo, orgId);
			} else {
				//billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(propertyNo, orgId);
			}
		} else {
			LOGGER.info("<-----------------This property with property no. : " + propertyNo + " is inactive-------------->");
		}
        return billMasList;
    }

    @Override
    public List<TbBillMas> updateAdjustedCurrentBill(List<TbBillMas> bill) {
    	List<TbBillMas> billMasList = propertyMainBillService.fetchAllBillByPropNo(bill.get(0).getPropNo(), bill.get(0).getOrgid());
    	billMasList.forEach(billMas ->{
        	billMas.setRevisedBillDate(new Date());
        	billMas.setRevisedBillType(bill.get(0).getRevisedBillType());
        	billMas.getTbWtBillDet().forEach(detailDto ->{
        		detailDto.setRevisedBillDate(new Date());
        		detailDto.setRevisedBillType(bill.get(0).getRevisedBillType());
        	});
        });
    	propertyMainBillService.copyDataFromMainBillDetailToHistory(billMasList,
				MainetConstants.FlagA,null,null);
    	billMasterCommonService.updateArrearInCurrentBills(bill);
    	
    	bill.forEach(billMas ->{
    		AtomicBoolean balanceFlag = new AtomicBoolean(false);
    		billMas.getTbWtBillDet().forEach(det ->{
    			if(det.getBdCurBalTaxamt() > 0 || det.getBdPrvBalArramt() > 0) {
    				balanceFlag.getAndSet(true);
    			}
    		});
    		if(balanceFlag.get()) {
    			billMas.setBmPaidFlag(MainetConstants.FlagN);
    		}else {
    			billMas.setBmPaidFlag(MainetConstants.FlagY);
    			billMas.setBmTotalOutstanding(0.00);
    		}
    	});
    	propertyMainBillService.saveAndUpdateMainBill(bill,
				UserSession.getCurrent().getOrganisation().getOrgid(),
				UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.Property.AuthStatus.AUTH,
				Utility.getMacAddress());
        return bill;
    }

    @Override
    public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster,
            final Organisation orgnisation) {
        final List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(offlineMaster.getApplNo(), offlineMaster.getOrgId());
        final ProvisionalAssesmentMstDto assMst = propertyAuthorizationService
                .getAssesmentMstDtoForDisplay(provAssDtoList);
        offlineMaster.setReferenceNo(assMst.getAssOldpropno());
        offlineMaster
                .setPropNoConnNoEstateNoL(ApplicationSession.getInstance().getMessage("propertydetails.PropertyNo."));
        offlineMaster.setPropNoConnNoEstateNoV(offlineMaster.getUniquePrimaryId());
        assMst.getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.setProAssdUsagetypeDesc(
                    CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1(), orgnisation).getDescLangFirst());
            if (offlineMaster.getUsageType() == null) {
                offlineMaster.setUsageType(det.getProAssdUsagetypeDesc());
            } else if (!offlineMaster.getUsageType().contains(det.getProAssdUsagetypeDesc())) {
                offlineMaster.setUsageType(offlineMaster.getUsageType() + "," + det.getProAssdUsagetypeDesc());
            }
        });
        List<TbServiceReceiptMasEntity> rebeteList = iReceiptEntryService.getRebateByAppNo(offlineMaster.getOrgId(),
                offlineMaster.getApplNo(), offlineMaster.getDeptId());
        if (rebeteList != null && !rebeteList.isEmpty()) {
            offlineMaster
                    .setDemandLevelRebate(rebeteList.stream().mapToDouble(re -> re.getRmAmount().doubleValue()).sum());
        }
        return offlineMaster;
    }

    @Override
    public boolean isValidBillNoForObjection(String refno, String billNo, Date billDueDate, Long serviceId,
            Long orgId) {
        List<TbBillMas> billMas = propertyMainBillService.fetchBillByBillNoAndPropertyNo(refno, billNo, orgId);
        return billMas != null ? true : false;
    }

    @Transactional
    @Override
    public List<Long> generateProvisionalBillForReport(PropertyReportRequestDto propertyDto, Long orgId, Long empId,
            String ipAddress) {
        List<Long> billIdsGen = new ArrayList<>(0);
        final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.STATUS.ACTIVE);
        List<ProvisionalAssesmentMstDto> assDtoList = assesmentMastService.getAssMstListForProvisionalDemand(orgId);
        List<TbBillMas> totalBillListforSave = new ArrayList<>();
        long size = assDtoList.size();
        LOGGER.info("assDtoList size for generating demand : " + size);
        assDtoList.forEach(assmaster->{
        	LOGGER.info(assmaster.getAssNo() + " ,");
        });
        AtomicLong successfulCount = new AtomicLong(0);
        AtomicLong errorCount = new AtomicLong(0);
        AtomicInteger count = new AtomicInteger(assDtoList.size());
        AtomicInteger batchCount = new AtomicInteger(assDtoList.size());
        assDtoList.forEach(assmaster->{
        	LOGGER.info(assmaster.getAssNo()+" ,");
        	
        });
        PropertyBillGenerationMap propBillGenerationMap = new PropertyBillGenerationMap();
        ApplicationSession.getInstance().getPropBillGenerationMapOrgId().put(orgId, propBillGenerationMap);
		final ExecutorService executorService = Executors.newFixedThreadPool(
				Integer.valueOf(ApplicationSession.getInstance().getMessage("bill.thread.pool.size")));
		assDtoList.forEach(assMst -> {

			executorService.execute(new Runnable() {
				public void run() {
					LOGGER.info(
							String.format("starting thread before task thread %s", Thread.currentThread().getName()));
					generateProvDemand(orgId, empId, ipAddress, deptId, totalBillListforSave, size, successfulCount,
							errorCount, count, batchCount, assMst,propBillGenerationMap);
					LOGGER.info(String.format("starting thread after task thread %s after",
							Thread.currentThread().getName()));
				}
			});

		});
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		LOGGER.info("\nFinished all threads");
        return billIdsGen;
    }

	private void generateProvDemand(Long orgId, Long empId, String ipAddress, final Long deptId,
			List<TbBillMas> totalBillListforSave, long size, AtomicLong successfulCount, AtomicLong errorCount,
			AtomicInteger count, AtomicInteger batchCount, ProvisionalAssesmentMstDto assMst, PropertyBillGenerationMap propBillGenerationMap) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Provisional Demand generation started for Property :  " + assMst.getAssNo());
		LOGGER.info("Provisional Demand generation remainning count : " + count.intValue());
		try {
		    Organisation organisation = new Organisation();
		    organisation.setOrgid(orgId);
		    List<Long> finYearList = new ArrayList<>();
		    List<FinancialYear> financialYearList = iFinancialYearService.getFinanceYearListAfterGivenDate(orgId,
		            assMst.getFaYearId(), new Date());
		    LOGGER.info("Check Financial Year is empty for Property :  " +financialYearList.isEmpty()+" for "+ assMst.getAssNo());
		    if (!financialYearList.isEmpty()) {
		    	setUnitDetailsForNextYears(assMst, finYearList, financialYearList);

		        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
		        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService
		                .fetchCharges(assMst, deptId, taxWiseRebate, finYearList,null,null,null,null);
		        LOGGER.info("Check SCH Wise ChargeMap is empty for Property :  " + schWiseChargeMap.isEmpty()+" for "+assMst.getAssNo());
		        if (!schWiseChargeMap.isEmpty()) {
		        	List<TbBillMas> billMasList = generateNewBill(schWiseChargeMap, organisation, deptId,assMst.getAssNo(),assMst.getFlatNo());
		            selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList, schWiseChargeMap,
		                    organisation, taxWiseRebate);
		            interestCalculationWithoutBRMSCall(organisation, deptId, billMasList,
		                    MainetConstants.Property.INT_RECAL_NO);
		            List<TbBillMas> billMasArrears = propertyMainBillService
		                    .fetchNotPaidBillForAssessment(assMst.getAssNo(), orgId);
		            List<TbBillMas> billList = billMasList;

		            if (billMasArrears != null && !billMasArrears.isEmpty()) {
		                interestCalculation(organisation, deptId, billMasArrears,
		                        MainetConstants.Property.INT_RECAL_YES);
		                billList = getBillListWithMergingOfOldAndNewBill(billMasArrears, billMasList);
		                updateArrearInCurrentBills(billList);
		                billList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
		            }
		            // Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
		            // calculateSurcharge(organisation, deptId, billList, null,
		            // MainetConstants.Property.SURCHARGE,
		            // finYearId);
		            taxCarryForward(billList, orgId);
		            // propertyBRMSService.fetchEarlyPayRebateRate(billList, organisation, deptId,
		            // null);
		                iProvisionalBillService.saveAndUpdateTemporaryProvisionalBill(billList, orgId,
		                        empId, ipAddress);
		                batchCount.set(count.intValue());
		                successfulCount.getAndIncrement();
		        } else {
		            LOGGER.error("fetchCharges is found null :  " + assMst.getAssNo());
		            errorCount.getAndIncrement();
		            saveExceptionDetails(orgId, empId, ipAddress, "fetchCharges is found null :  ", "PD",assMst.getAssNo());
		        }

		    } else {
		        LOGGER.error("financialYearList is found null");
		        errorCount.getAndIncrement();
		        saveExceptionDetails(orgId, empId, ipAddress, "financialYearList is found null", "PD",assMst.getAssNo());
		    }
		    count.getAndDecrement();		    
		} catch (Exception e) {
		    LOGGER.error("Prvisional Demand not generated for Property :" + assMst.getAssNo());
		    LOGGER.error("Exception occured due to this "+e);
		    count.getAndDecrement();
		    errorCount.getAndIncrement();
		    if(StringUtils.isBlank(e.getMessage())) {
		    	saveExceptionDetails(orgId, empId, ipAddress, "unable to genearte billUnable to find exception log", "PD",assMst.getAssNo());
		    }else {
		    	saveExceptionDetails(orgId, empId, ipAddress, e.getMessage(), "PD",assMst.getAssNo());
		    }
		}
		long endTime = System.currentTimeMillis();
		long timeTaken = (endTime - startTime);  // Total execution time in milli seconds
		LOGGER.info("Time taken for one bill generation : " + timeTaken + " MiliSecond");
		setPropertyBillGenerationMap(size, successfulCount, errorCount, timeTaken, orgId,propBillGenerationMap);
		LOGGER.info("Provisional Demand generation ended for Property :  " + assMst.getAssNo());
	}
   
	private void setPropertyBillGenerationMap(long size, AtomicLong successfulCount, AtomicLong errorCount,
			long timeTaken, long orgId,PropertyBillGenerationMap billGenerationMap) {		
		billGenerationMap.setNoOfBillsForGeneration(size);
		billGenerationMap.setNoOfBillsGeneratedSuccessfull(successfulCount.longValue());
		billGenerationMap.setNoOfBillsGotErrors(errorCount.longValue());
		billGenerationMap.setNoOfBillsPending((size) - (successfulCount.longValue() + errorCount.longValue()));
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL)) {
			billGenerationMap.setApproximatelyTakenTimeForBillGeneration((long) (timeTaken * 0.001)*size);
		}
		else {
			billGenerationMap.setApproximatelyTakenTimeForBillGeneration((long) (size * (timeTaken * 0.001))* size);
		}
		billGenerationMap.setOrgId(orgId);
		ApplicationSession.getInstance().getPropBillGenerationMapOrgId().put(orgId, billGenerationMap);
	}

    /*
     * Task #30569 This method is called from scheduler: Added By Apeksha
     */
    @Override
    @Transactional
    public void generateProvisionalBillForReportBySchedular(QuartzSchedulerMaster runtimeBean,
            List<Object> parameterList) {
    	Long orgId = runtimeBean.getOrgId().getOrgid();
    	LOGGER.info("Schedular method execution generateProvisionalBillForReportBySchedular statrted for : "+orgId);
    	List<Long> userIdList = empService.getEmpId(orgId, MainetConstants.MENU._0);
        Long userId = userIdList.get(0);
        iProvisionalBillService.deleteTemporaryProvisionalBillsWithEntityById(userId, orgId);
        propertyBillExceptionService.deleteEceptionDetailsByOrgIdAndUserId(userId, orgId);
        PropertyReportRequestDto propertyDto = new PropertyReportRequestDto();
        propertyDto.setMnassward1(MainetConstants.Property.All);
        propertyDto.setMnFromdt(new Date());
        generateProvisionalBillForReport(propertyDto, orgId, userId, null);
        LOGGER.info("Schedular method execution generateProvisionalBillForReportBySchedular ended for :  "+orgId);
    }
    
    private boolean checkDaysExtendedCovidActive(Organisation organisation) {

		boolean covidActiveFlag = false;
		long lookUpId = 0;
		try {
			lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("DES", "DEC", organisation.getOrgid());
		} catch (Exception exception) {
			
		}

		if (lookUpId > 0) {
			covidActiveFlag = true;
		}
		return covidActiveFlag;

	}
	
	private List<Double> arrearAmountIfCovidActive(Organisation organisation, List<TbBillMas> billMasList, Long finYearId,Date manualReceiptDate) {
		List<Double>  arraerAmtAndFinYear = new ArrayList<Double>();
		double arrearAmount = 0;
		Date currentDate = new Date();
		if(manualReceiptDate != null) {
			currentDate = manualReceiptDate;
		}
		FinancialYear currentFinYear = iFinancialYearService.getFinincialYearsById(finYearId, organisation.getOrgid());
		int size = billMasList.size() - 1;
		
		  Timestamp currFinYearTimeStamp =new Timestamp(currentFinYear.getFaFromDate().getTime());
		  Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime()); 
		  
		LocalDate convertFinFromDateToLocalDate = currentFinYearDate.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate();

		currentFinYear.getFaToDate().getTime();
		Date afterSubMonths = Date
				.from(convertFinFromDateToLocalDate.minusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
		FinancialYear previousFinYear = tbFinancialyearService.getFinanciaYearByDate(afterSubMonths);
		LOGGER.error("previous fin year id" +" " + previousFinYear.getFaYear());
		if (manualReceiptDate != null) {
			Date dueDateCrossed = new Date();
			if (manualReceiptDate != null) {
				dueDateCrossed = manualReceiptDate;
			}
			
			LookUp manualAssesment = null;
            try {
            	manualAssesment = CommonMasterUtility.getValueFromPrefixLookUp("MRA", "PAS", organisation);
            } catch (Exception exception) {
                LOGGER.error("No Prefix found for PAS(MRA)");
            }
				if (CollectionUtils.isNotEmpty(billMasList)) {
					for (TbBillMas billMas : billMasList) {
						if (billMas.getBmDuedate() != null && Utility.compareDate(billMas.getBmDuedate(), dueDateCrossed)
								&& !Utility.comapreDates(billMas.getBmDuedate(), dueDateCrossed)) {
							arrearAmount = arrearAmount + billMas.getBmTotalBalAmount();
							if (manualAssesment == null || StringUtils.isBlank(manualAssesment.getOtherField())
									|| StringUtils.equals(manualAssesment.getOtherField(), MainetConstants.FlagN)) {
								finYearId = billMas.getBmYear();
							}
						}
					}
				}
			
		} else {
			if (finYearId.equals(billMasList.get(size).getBmYear())) {
				arrearAmount = billMasList.get(size).getBmTotalArrearsWithoutInt();
			} else {
				arrearAmount = billMasList.get(size).getBmTotalArrearsWithoutInt()
						+ billMasList.get(size).getBmTotalBalAmount();
			}
		}
		LookUp covidActivePrefix = CommonMasterUtility.getValueFromPrefixLookUp("DES", "DEC", organisation);
		if (arrearAmount > 0 && covidActivePrefix != null) {
			Timestamp prevFinYearTimeStamp =new Timestamp(previousFinYear.getFaToDate().getTime());
			  Date prevFinYearDate = new Date(prevFinYearTimeStamp.getTime()); 
			LocalDate convertFinToDateToLocalDate = prevFinYearDate.toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			Date afterAddingDaysToFinToDate = Date
					.from(convertFinToDateToLocalDate.plusDays(Long.valueOf(covidActivePrefix.getOtherField()))
							.atStartOfDay(ZoneId.systemDefault()).toInstant());
			if (Utility.compareDate(currentDate, afterAddingDaysToFinToDate)
					|| Utility.comapreDates(currentDate, afterAddingDaysToFinToDate)) {
				for (TbBillMas billMas : billMasList) {
					if (billMas.getBmYear().equals(previousFinYear.getFaYear())) {
						arrearAmount = arrearAmount - billMas.getBmTotalBalAmount();
					}
				}

			}

		}
		arraerAmtAndFinYear.add(arrearAmount);
		arraerAmtAndFinYear.add(finYearId.doubleValue());
		return arraerAmtAndFinYear;
	}
	
	private double excludeSurchargeCalculatedAmount(Organisation organisation,List<TbBillMas> billMasList,
			String propNo, Date manualReceiptDate) {
		double lastSurchargeArrearAmount = 0;
		PropertyPenltyDto lastSurchargeCalculated = propertyPenltyService.getLastClaculatedSurcharge(propNo,
				organisation.getOrgid());
		if (lastSurchargeCalculated != null) {
			FinancialYear lastSurChargeFinYear = tbFinancialyearService.getFinanciaYearByDate(new Date());
			if (lastSurchargeCalculated.getCreatedDate().compareTo(lastSurChargeFinYear.getFaFromDate()) >= 0
					&& lastSurchargeCalculated.getCreatedDate().compareTo(lastSurChargeFinYear.getFaToDate()) <= 0) {
				List<TbBillMas> lastSurchargeBillMasList = billMasList.stream()
						.filter(billMas -> billMas.getBmYear().equals(lastSurchargeCalculated.getFinYearId()))
						.collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(lastSurchargeBillMasList)) {
					TbBillMas lastSurchargeBillMas = lastSurchargeBillMasList.get(lastSurchargeBillMasList.size() - 1);
					lastSurchargeArrearAmount = lastSurchargeBillMas.getBmTotalArrearsWithoutInt()
							+ lastSurchargeBillMas.getBmTotalBalAmount();
				}
			}
		}
		return lastSurchargeArrearAmount;
	}
	
    @Override
	public double knockOffPreviousPaidBillTaxWise(List<TbServiceReceiptMasEntity> feedetailDtoList,
			List<TbBillMas> billMasList, Organisation org, double editableLastAmountPaid) {

    	AtomicDouble advanceAmount = new AtomicDouble(0);
         AtomicDouble totInt = new AtomicDouble(0);
         AtomicDouble totPenlty = new AtomicDouble(0);
		feedetailDtoList.forEach(feedetailDto -> {
			feedetailDto.getReceiptFeeDetail().forEach(det -> {
				billMasList.stream().filter(billMas -> Long.valueOf(billMas.getBmIdno()).equals(det.getBmIdNo()))
						.forEach(billMas -> {
							 AtomicDouble totalCurBalAmt = new AtomicDouble(0);
							billMas.getTbWtBillDet().forEach(billDet -> {
								if (det.getBilldetId() != null
										&& det.getBilldetId().equals(Long.valueOf(billDet.getBdBilldetid()))) {
									
									billDet.setBdCurBalTaxamt(
											billDet.getBdCurBalTaxamt() - det.getRfFeeamount().doubleValue());
									if(billDet.getBdCurBalTaxamt() < 0) {
										advanceAmount.addAndGet(Math.abs(billDet.getBdCurBalTaxamt()));
										billDet.setBdCurBalTaxamt(0);
									}
									
									final String taxCode = CommonMasterUtility
											.getHierarchicalLookUp(billDet.getTaxCategory(), org).getLookUpCode();
									if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
										totInt.addAndGet(det.getRfFeeamount().doubleValue());

									} else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
										totPenlty.addAndGet(det.getRfFeeamount().doubleValue());
									} else if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
										totalCurBalAmt.addAndGet(det.getRfFeeamount().doubleValue());
									}
								}
							});

							billMas.setBmTotalBalAmount(billMas.getBmTotalBalAmount() - totalCurBalAmt.doubleValue());
							billMas.setBmTotalOutstanding(billMas.getBmTotalOutstanding() - totalCurBalAmt.doubleValue());
							billMas.setBmToatlInt(billMas.getBmToatlInt() - totInt.doubleValue());
							billMas.setTotalPenalty(billMas.getTotalPenalty() - totPenlty.doubleValue());
							if (billMas.getBmTotalBalAmount() > 0) {
								billMas.setBmPaidFlag(MainetConstants.FlagN);
							} else {
								billMas.setBmPaidFlag(MainetConstants.FlagY);
							}
						});
			});
		});
		billMasList.get(billMasList.size() - 1).setBmLastRcptamt(editableLastAmountPaid);
		return advanceAmount.doubleValue();
	}
    
    
    @Override
    public boolean checkBifurcationMethodApplicable(Organisation organisation) {
		boolean bifurcationApplicable = false;
		LookUp bifurcationMethod = null;
		try {
			bifurcationMethod = CommonMasterUtility.getValueFromPrefixLookUp("B", "RBA", organisation);
		} catch (Exception exception) {
			
		}

		if (bifurcationMethod != null && StringUtils.isNotBlank(bifurcationMethod.getOtherField())
				&& StringUtils.equals(bifurcationMethod.getOtherField(), MainetConstants.FlagY)) {
			bifurcationApplicable = true;
		}
		return bifurcationApplicable;

	}
    
    private void claculateDemandNoticeGenCharges(List<TbBillMas> billMasData, Organisation org, Long deptId,Long userId) {
    	propertyBRMSService.fetchAllChargesApplAtReceipt(billMasData.get(0).getPropNo(), billMasData, org, deptId, MainetConstants.FlagY,userId,null);
    }
    
    @Override
    public  Map<String, Object> checkHalfPaymentRebateAppl(List<TbBillMas> billMasList, Organisation org, Long deptId,
			Date manualDate, String payableAmountMethod,double paidAmount,double receiptAmount) {
		HashMap<String, Object> halfPayDetails = new HashMap<String, Object>();
		boolean halfPaymentRebateAppl = false;
		List<BillDisplayDto> rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList, org, deptId, manualDate, "Half");
		double rebateAmount = 0.0;
		double halfPayableOutstanding = billMasterCommonService.getHalfPayableOutstanding(billMasList,org,null);
		for (BillDisplayDto billDisplayDto : rebateDtoList) {
			rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
		}
		paidAmount = paidAmount+receiptAmount;
		if(paidAmount >= (halfPayableOutstanding)) {
			halfPaymentRebateAppl = true;
		}
		halfPayDetails.put("halfPayApplicable", halfPaymentRebateAppl);
		halfPayDetails.put("rebateList", rebateDtoList);
		halfPayDetails.put("totalRebate", rebateAmount);
		return halfPayDetails;
	}

	@Override
	@Transactional(readOnly = true)
	public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean recMode) {
		TbSrcptModesDetBean dto = propertyBRMSService.fetchDishonorCharge(recMode, org, deptId);
		return dto;
	}

	@Override
	@Transactional
	public boolean revertBills(TbSrcptModesDetBean feedetailDto, Long userId, String ipAddress) {
		LOGGER.info(".....Revert Bill methods start For Mode Id : " + feedetailDto.getRdModesid() + ".......");
		Long orgId = feedetailDto.getOrgid();
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);

		TbServiceReceiptMasEntity receiptMas = iReceiptEntryService.findByRmRcptidAndOrgId(feedetailDto.getReceiptId(),
				orgId);
		String propertyNo = receiptMas.getAdditionalRefNo();
		String flatNo = receiptMas.getFlatNo();

		LookUp billMethod = null;
		LookUp billingMethodLookUp = null;
		ProvisionalAssesmentMstDto mastDto = null;
		boolean isIndividualBillingMethod = false;
		try {
			billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV",
					org);
		} catch (Exception e) {
		}

		if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
				&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
			Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propertyNo, orgId);
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
					org);
		}
		if (billingMethodLookUp != null
				&& StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
			mastDto = assesmentMastService.fetchLatestAssessmentByPropNoAndFlatNo(orgId, propertyNo, flatNo);
			isIndividualBillingMethod = true;
		} else {
			mastDto = assesmentMastService.fetchLatestAssessmentByPropNo(orgId, propertyNo);
		}

		if (mastDto == null) {
			throw new FrameworkException(
					"No Property Number Found In Assessment Master for Property Number :" + propertyNo
							+ " and Flat No :" + flatNo
							+ " and OrgId :" + orgId);
		}

		List<TbBillMas> billMasList = null;

		// revert advanced amount if present
		BillReceiptPostingDTO excessReceiptDto = revertAndUpdateAdvanceAmtIfExist(feedetailDto, userId, ipAddress,
				orgId, propertyNo);

		List<Long> billIds = null;
		Integer count = null;
		if (!feedetailDto.getBillNoAndAmountMap().isEmpty()) {
			billIds = new ArrayList<>(feedetailDto.getBillNoAndAmountMap().keySet());// Receipt Mode bill Ids
			if (!billIds.isEmpty()) {// Defect #119808
				count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
				if (count > 0) {
					billMasList = propertyMainBillService.fetchBillFromBmIdNos(billIds);
				} else {
					billMasList = iProvisionalBillService.fetchListOfBillsByPrimaryKey(billIds, orgId);
				}
			}
		}

		if (count == null) {
			count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
		}
		// in case of purely advanced payment & for adding dishonor charges in latest
		// bill
		final TbBillMas latestBill = findLatestBill(org, propertyNo, flatNo, count, isIndividualBillingMethod);

		if (billMasList != null && !billMasList.isEmpty()) { // reverse receipt mode amount in bill mas and bill det
			List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();
			List<TbBillMas> billList = billMasList;
			// revert dishonor amount
			updateBillDetData(feedetailDto, billList, receiptMas);
			if (excessReceiptDto != null) {
				billPosDtoList.add(excessReceiptDto);
			}
		}
		// add Dishonor charge in latest bill det
		billMasList = addDishonorCharges(feedetailDto, org, deptId, billMasList, latestBill);

		if (!billMasList.isEmpty()) {
			updateBillMasData(org, billMasList);
			final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
			double rebateAmount = billMasList.get(billMasList.size() - 1).getBmToatlRebate();
			billMasList.get(billMasList.size() - 1).setBmToatlRebate(0.0);
			billMasterCommonService.updateBillData(billMasList, 0d, null, null, org, rebateDetails, null);
			billMasList.get(billMasList.size() - 1).setBmToatlRebate(rebateAmount);
			if (count > 0) {
				// Form Main Bill table
				propertyMainBillService.saveAndUpdateMainBillWithKeyGen(billMasList, orgId, userId, propertyNo, null,
						ipAddress);
			} else {
				// From Provisional Bill Table
				iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, userId, propertyNo, null, null,
						null);
			}

			// update Receipt Mode
			try {
				final List<TbSrcptModesDetEntity> feeDetList = chequeDishonorService
						.fetchReceiptFeeDetails(feedetailDto.getModeKeyList(), feedetailDto.getOrgid());
				if (feeDetList != null && !feeDetList.isEmpty()) {
					for (TbSrcptModesDetEntity feeDet : feeDetList) {
						feeDet.setRdSrChkDate(feedetailDto.getRdSrChkDate());
						feeDet.setRdSrChkDisChg(feedetailDto.getRdSrChkDisChg());
						feeDet.setRdSrChkDis(feedetailDto.getRdSrChkDis());
						// Defect #119702
						feeDet.setRd_dishonor_remark(feedetailDto.getRd_dishonor_remark());
						LookUp CLRPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
								MainetConstants.Property.DISHONOUR, MainetConstants.Property.CLR, org);
						if (CLRPrefix != null)
							feeDet.setCheckStatus(CLRPrefix.getLookUpId());
					}
					chequeDishonorService.saveReceiptModeDetails(feeDetList);
				}
				sendSMSEmail(feedetailDto, userId, org, mastDto);
			} catch (Exception e) {
				throw new FrameworkException("Exception occured while update receipt mode details.");
			}
		}

		LOGGER.info(".....Revert Bill methods End For Mode Id : " + feedetailDto.getRdModesid() + ".......");
		return true;
	}


	private void sendSMSEmail(TbSrcptModesDetBean det, Long userId, Organisation org,
			ProvisionalAssesmentMstDto assMastDto) {
		try {
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();
			ProvisionalAssesmentOwnerDtlDto ownerDto = assMastDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
			if (ownerDto != null) {
			dto.setEmail(ownerDto.geteMail());
			dto.setMobnumber(ownerDto.getAssoMobileno());
			dto.setAppName(ownerDto.getAssoOwnerName());
			dto.setReferenceNo(ownerDto.getAssNo());
			dto.setRegReason(det.getRd_dishonor_remark());
			dto.setNoticeDate(det.getRdChequedddate().toString());
			dto.setChallanNo(det.getRdChequeddno().toString());
			if (det.getRdSrChkDisChg() != null)
				dto.setAmount(det.getRdSrChkDisChg());

			dto.setDeptShortCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
			dto.setLangId(MainetConstants.DEFAULT_LANGUAGE_ID);
			dto.setUserId(userId);

				ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.PROPERTY, "ChequeDishonor.html",
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED, dto, org, dto.getLangId());
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured while sending SMS EMail For Cheque Dishonour at Department : "
					+ MainetConstants.DEPT_SHORT_NAME.PROPERTY + ".......", e);
		}
	}

	private BillReceiptPostingDTO revertAndUpdateAdvanceAmtIfExist(TbSrcptModesDetBean feedetailDto, Long userId,
			String ipAddress, Long orgId, String propertyNo) {
		LOGGER.info(".....Advanced Amount revert methods start.......");
		AsExcessAmtEntity excessAmtEnt = asExecssAmtService.getAdvanceEntryByRecptId(feedetailDto.getReceiptId(),
				propertyNo, orgId);

		// removing advance amt from bill wise amount map (key is null because in
		// advance amt bill no is null)
		Double advanceAmt = feedetailDto.getBillNoAndAmountMap().remove(null);
		if (excessAmtEnt != null && excessAmtEnt.getExcAmt() > 0 && advanceAmt != null) {
			// final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
			// auditService.createHistory(accessAmtEnt, excessAmtHist);// add to history
			double actualAmt = excessAmtEnt.getExcAmt();
			// Knock off advance amount first
			if (excessAmtEnt.getExcAmt() >= advanceAmt) {
				excessAmtEnt.setExcAmt(excessAmtEnt.getExcAmt() - advanceAmt);
			} else {
				LOGGER.error("Problem while reverting advance amount in cheque dishonor,"
						+ "advance amt in receipt is more then amount from advnce table");
			}

			excessAmtEnt.setAdjAmt(excessAmtEnt.getAdjAmt() + (actualAmt - excessAmtEnt.getExcAmt()));
			excessAmtEnt.setUpdatedBy(userId);
			excessAmtEnt.setUpdatedDate(new Date());
			excessAmtEnt.setLgIpMacUpd(ipAddress);
			asExecssAmtService.saveAndUpdateAsExecssAmt(excessAmtEnt, orgId, userId);

		}
		BillReceiptPostingDTO excessReceiptDto = null;
		/*
		 * if (excessAmtEnt != null) {
		 * asExecssAmtService.inactiveAdvPayEnrtyByExcessId(excessAmtEnt.getExcessId(),
		 * orgId); excessReceiptDto = new BillReceiptPostingDTO();
		 * excessReceiptDto.setBillDetId(excessAmtEnt.getExcessId());
		 * excessReceiptDto.setTaxId(excessAmtEnt.getTaxId()); TbTaxMas tax =
		 * tbTaxMasService.findTaxByTaxIdAndOrgId(excessAmtEnt.getTaxId(),
		 * org.getOrgid()); excessReceiptDto.setTaxCategory(tax.getTaxCategory1());
		 * excessReceiptDto.setTaxAmount(excessAmtEnt.getExcAmt()); Long finYearId =
		 * iFinancialYearService.getFinanceYearId(new Date());
		 * excessReceiptDto.setYearId(finYearId); }
		 */
		LOGGER.info(".....Advanced Amount revert methods End.......");
		return excessReceiptDto;
	}

	private List<TbBillMas> addDishonorCharges(TbSrcptModesDetBean feedetailDto, Organisation org, Long deptId,
			List<TbBillMas> billMasList, TbBillMas latestBill) {
		LOGGER.info(".....addDishonorCharges methods start.......");

		LookUp penaltyLookup = CommonMasterUtility.getHieLookupByLookupCode(
				MainetConstants.Property.CHEQUE_DISHONR_CHARGES, PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
		final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.CHEQUE_DISHONR_CHARGES,
				MainetConstants.Property.propPref.CAA, org);// Defect #132569
		if (penaltyLookup != null && taxAppAtBill != null) {
			List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
					taxAppAtBill.getLookUpId(), penaltyLookup.getLookUpId());
			if (taxList != null && !taxList.isEmpty()) {
				TbTaxMasEntity det = taxList.get(0);
				List<TbBillMas> bill = null;
				TbBillMas billMas = null;
				if (CollectionUtils.isNotEmpty(billMasList)) {
					bill = billMasList.stream().filter(detail -> detail.getBmIdno() == latestBill.getBmIdno())
							.collect(Collectors.toList()); // latest bill present in billmasList or not
				} else {
					billMasList = new ArrayList<>();// In case of Purely advanced payment
				}

				if (CollectionUtils.isNotEmpty(bill)) {
					billMas = bill.get(0);// if latest bill present in billmasList then use as for adding dishonour
											// charges
				} else {
					billMas = latestBill;
				}

				boolean isexist = billMas.getTbWtBillDet().stream()
						.filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString())).findFirst().isPresent();
				if (isexist) {
					billMas.getTbWtBillDet().stream()
							.filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString())).forEach(taxDet -> {
								taxDet.setBdCurTaxamt(taxDet.getBdCurTaxamt() + feedetailDto.getRdSrChkDisChg());
								taxDet.setBdCurBalTaxamt(taxDet.getBdCurBalTaxamt() + feedetailDto.getRdSrChkDisChg());
							});
				} else {
					TbBillDet taxDet = new TbBillDet();
					taxDet.setTaxId(det.getTaxId());
					taxDet.setTaxCategory(det.getTaxCategory1());
					taxDet.setCollSeq(det.getCollSeq());
					taxDet.setBdCurTaxamt(feedetailDto.getRdSrChkDisChg());
					taxDet.setBdCurBalTaxamt(feedetailDto.getRdSrChkDisChg());
					billMas.getTbWtBillDet().add(taxDet);
				}
				if (CollectionUtils.isEmpty(bill)) {
					billMasList.add(billMas);
				}

			} else {// Defect #120765
				LOGGER.error("No Tax Found For Cheque Dishonor Charges For OrgId --->" + org.getOrgid() + " DeptId -->"
						+ deptId + " TaxApplicabtleAt -->" + taxAppAtBill.getLookUpId() + " TaxSubCategory -->"
						+ penaltyLookup.getLookUpId());
				throw new FrameworkException("No Tax Found For Cheque Dishonor Charges For OrgId --->" + org.getOrgid()
						+ " DeptId -->" + deptId + " TaxApplicabtleAt -->" + taxAppAtBill.getLookUpId()
						+ " TaxSubCategory -->" + penaltyLookup.getLookUpId());
			}
		} else {// Defect #120765
			LOGGER.error("PrefixLookUpCode Not Found  lookUpCode --->" + MainetConstants.Property.CHEQUE_DISHONR_CHARGES
					+ " prefix -->" + PrefixConstants.LookUpPrefix.TAC);
			throw new FrameworkException("PrefixLookUpCode Not Found for: prefix -->" + PrefixConstants.LookUpPrefix.TAC
					+ " and lookUpCode-->" + MainetConstants.Property.CHEQUE_DISHONR_CHARGES);
		}
		LOGGER.info(".....addDishonorCharges methods End.......");
		return billMasList;
	}

	private TbBillMas findLatestBill(Organisation org, String propNo, String flatNo, int count,
			boolean isIndividualBillingMethod) {
		List<TbBillMas> billMasterList;
		TbBillMas latestBill = null;
		if (isIndividualBillingMethod) {
			if (count > 0) {
				// Form Main Bill table
				billMasterList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(propNo, flatNo, org.getOrgid());
			} else {
				// From Provisional Bill Table
				billMasterList = iProvisionalBillService.getProvisionalBillMasByPropertyNoAndFlatNo(propNo, flatNo,
						org.getOrgid());
			}
		} else {
			if (count > 0) {
				// Form Main Bill table
				billMasterList = propertyMainBillService.fetchAllBillByPropNo(propNo, org.getOrgid());
			} else {
				// From Provisional Bill Table
				billMasterList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(propNo, org.getOrgid());
			}
		}
		if (!billMasterList.isEmpty())
			latestBill = billMasterList.get(billMasterList.size() - 1);
		else {
			throw new FrameworkException("No Bill Found For Property No :" + propNo + " and flat No :" + flatNo
					+ " and OrgId :" + org.getOrgid());
		}
		return latestBill;
	}

	private void updateBillDetData(TbSrcptModesDetBean feedetailDto, List<TbBillMas> billList,
			TbServiceReceiptMasEntity receiptMas) {
		AtomicDouble modeAmt = new AtomicDouble(0);
		AtomicDouble receiptDetAmt = new AtomicDouble(0);
		final Map<Long, BigDecimal> receiptDetMap = receiptMas.getReceiptFeeDetail().stream()
				.collect(Collectors.toMap(TbSrcptFeesDetEntity::getBilldetId, TbSrcptFeesDetEntity::getRfFeeamount));

		feedetailDto.getBillNoAndAmountMap().forEach((key, value) -> {
			modeAmt.getAndSet(value.doubleValue());
			billList.stream().filter(bill -> bill.getBmIdno() == key).forEach(bill -> {
				bill.getTbWtBillDet().forEach(billDet -> {
					if (receiptDetMap.get(billDet.getBdBilldetid()) != null) {
						receiptDetAmt.getAndSet(receiptDetMap.get(billDet.getBdBilldetid()).doubleValue());// receiptDet
																											// Amount

						if (billDet.getBdCurBalTaxamt() < billDet.getBdCurTaxamt()) {
							double diffAmt = modeAmt.get() - receiptDetAmt.get();
							if (diffAmt < 0) {
								billDet.setBdCurBalTaxamt(modeAmt.get() + billDet.getBdCurBalTaxamt());
								modeAmt.getAndSet(0);
							} else {
								billDet.setBdCurBalTaxamt(receiptDetAmt.get() + billDet.getBdCurBalTaxamt());
								modeAmt.getAndSet(diffAmt);
							}
						}
					}
				});
			});

		});
	}

	private void updateBillMasData(Organisation org, List<TbBillMas> billMasList) {
		AtomicDouble totalCurBalAmt = new AtomicDouble(0);
		AtomicDouble totInt = new AtomicDouble(0);
		AtomicDouble totPenlty = new AtomicDouble(0);

		billMasList.forEach(billMas -> {
			billMas.getTbWtBillDet().forEach(billDet -> {
				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
						.getLookUpCode();
				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
					totInt.addAndGet(billDet.getBdCurBalTaxamt());
				} else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
					totPenlty.addAndGet(billDet.getBdCurBalTaxamt());
				} else if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
					totalCurBalAmt.addAndGet(billDet.getBdCurBalTaxamt());
				}
			});

			billMas.setBmTotalBalAmount(billMas.getBmTotalBalAmount() + totalCurBalAmt.doubleValue());
			billMas.setBmToatlInt(billMas.getBmToatlInt() + totInt.doubleValue());
			billMas.setTotalPenalty(billMas.getTotalPenalty() + totPenlty.doubleValue());
			billMas.setBmPaidFlag(MainetConstants.FlagN);
			billMas.setBmLastRcptamt(0);
			billMas.setBmLastRcptdt(null);
			totalCurBalAmt.set(0);
			totInt.set(0);
			totPenlty.set(0);
		});
		billMasList.get(billMasList.size() - 1).setBmLastRcptamt(0);
	}

	@Override
	@Transactional(readOnly = true)
	public String createApplicationNumberForSKDCL(ApplicantDetailDTO applicationDto) {
		final Date sysDate = UtilityService.getSQLDate(new Date());
		final String date = sysDate.toString();
		final String[] dateParts = date.split("-");
		final String year = dateParts[0];
		final String month = dateParts[1];
		final String day = dateParts[2];
		final String YYYYMMDDDate = year.concat(month).concat(day);

		Long number = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				MainetConstants.CommonMasterUi.TB_CFC_APP_MST, MainetConstants.TABLE_COLUMN.REF_NO,
				applicationDto.getOrgId(), MainetConstants.FlagF, null);
		final String paddingAppNo = String.format(MainetConstants.LOI.LOI_NO_FORMAT, Long.parseLong(number.toString()));
		String wardId = "";

		if (applicationDto.getDwzid5() != null)
			wardId = CommonMasterUtility.getHierarchicalLookUp(applicationDto.getDwzid5(), applicationDto.getOrgId())
					.getLookUpCode();
		else if (applicationDto.getDwzid4() != null)
			wardId = CommonMasterUtility.getHierarchicalLookUp(applicationDto.getDwzid4(), applicationDto.getOrgId())
					.getLookUpCode();
		else if (applicationDto.getDwzid3() != null)
			wardId = CommonMasterUtility.getHierarchicalLookUp(applicationDto.getDwzid3(), applicationDto.getOrgId())
					.getLookUpCode();
		else if (applicationDto.getDwzid2() != null)
			wardId = CommonMasterUtility.getHierarchicalLookUp(applicationDto.getDwzid2(), applicationDto.getOrgId())
					.getLookUpCode();
		else if (applicationDto.getDwzid1() != null)
			wardId = CommonMasterUtility.getHierarchicalLookUp(applicationDto.getDwzid1(), applicationDto.getOrgId())
					.getLookUpCode();

		final String appNumber = String.valueOf(applicationDto.getOrgId()).concat(wardId).concat(YYYYMMDDDate)
				.concat(paddingAppNo);
		return appNumber;
	}

	@Override
	public List<TbBillMas> fetchBillsListByBmId(List<Long> uniquePrimaryKey, Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void saveExceptionDetails(Long orgId, Long empId, String ipAddress, String exceptionReason,String billType,String propNo) {
		PropertyBillExceptionDto exceptionDto = new PropertyBillExceptionDto();
		exceptionDto.setOrgId(orgId);
		exceptionDto.setCreatedBy(empId);
		exceptionDto.setCreatedDate(new Date());
		exceptionDto.setLgIpMac(ipAddress);
		exceptionDto.setBillType(billType);
		exceptionDto.setStatus(MainetConstants.FlagA);
		exceptionDto.setExceptionReason(exceptionReason);
		exceptionDto.setLgIpMac(ipAddress);
		exceptionDto.setPropNo(propNo);
		exceptionDto.setUpdatedBy(empId);
		try {
			propertyBillExceptionService.saveExceptionDetails(exceptionDto);
		}catch (Exception exception) {
		LOGGER.error("Error occured while saving exception details" + exception);
		}
	}

	@Override
	@Transactional
	public List<Long> generateProvisionalBillForReportForMissingPropNos(Organisation org, Long empId, String ipAddress,
			List<String> propNoList, Long loggedLocId) {
        List<Long> billIdsGen = new ArrayList<>(0);
        final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.STATUS.ACTIVE);
        List<ProvisionalAssesmentMstDto> assDtoList = assesmentMastService.getAssMstListForProvisionalDemandbyPropNoList(org.getOrgid(), propNoList);
        long size = assDtoList.size();
        LOGGER.info("assDtoList size for generating demand : " + size);
        AtomicLong successfulCount = new AtomicLong(0);
        AtomicLong errorCount = new AtomicLong(0);
        AtomicInteger count = new AtomicInteger(assDtoList.size());
        AtomicInteger batchCount = new AtomicInteger(assDtoList.size());
        PropertyBillGenerationMap propBillGenerationMap = new PropertyBillGenerationMap();
        ApplicationSession.getInstance().getPropBillGenerationMapOrgId().put(org.getOrgid(), propBillGenerationMap);
        final ExecutorService executorService = Executors.newFixedThreadPool(
				Integer.valueOf(ApplicationSession.getInstance().getMessage("bill.thread.pool.size")));
		assDtoList.forEach(assMst -> {

			executorService.execute(new Runnable() {
				public void run() {
					LOGGER.info(
							String.format("starting thread before task thread %s", Thread.currentThread().getName()));
					 generateProvDemandForMissingPropNos(org.getOrgid(), empId, ipAddress, deptId, size, successfulCount, errorCount,
								count, batchCount, assMst,propBillGenerationMap);
					LOGGER.info(String.format("starting thread after task thread %s after",
							Thread.currentThread().getName()));
				}
			});

		});
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		 billMasterCommonService.doVoucherPosting(billIdsGen, org, MainetConstants.Property.PROP_DEPT_SHORT_CODE,
		            empId, loggedLocId);
        return billIdsGen;
    }

	private void generateProvDemandForMissingPropNos(Long orgId, Long empId, String ipAddress, final Long deptId,
			long size, AtomicLong successfulCount, AtomicLong errorCount, AtomicInteger count, AtomicInteger batchCount,
			ProvisionalAssesmentMstDto assMst, PropertyBillGenerationMap propBillGenerationMap) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Provisional Demand generation started for Property :  " + assMst.getAssNo());
		LOGGER.info("Provisional Demand generation remainning count : " + count.intValue());
		try {
		    Organisation organisation = new Organisation();
		    organisation.setOrgid(orgId);
		    List<Long> finYearList = new ArrayList<>();
		    List<FinancialYear> financialYearList = iFinancialYearService.getFinanceYearListAfterGivenDate(orgId,
		            assMst.getFaYearId(), new Date());
		    LOGGER.info("Check Financial Year is empty for Property :  " +financialYearList.isEmpty()+" for "+ assMst.getAssNo());
		    if (!financialYearList.isEmpty()) {
		    	setUnitDetailsForNextYears(assMst, finYearList, financialYearList);

		        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
		        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService
		                .fetchCharges(assMst, deptId, taxWiseRebate, finYearList,null,null,null,null);
		        LOGGER.info("Check SCH Wise ChargeMap is empty for Property :  " + schWiseChargeMap.isEmpty()+" for "+assMst.getAssNo());
		        if (!schWiseChargeMap.isEmpty()) {
		        	List<TbBillMas> billMasList = generateNewBill(schWiseChargeMap, organisation, deptId,assMst.getAssNo(),assMst.getFlatNo());
		            selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList, schWiseChargeMap,
		                    organisation, taxWiseRebate);
		            interestCalculationWithoutBRMSCall(organisation, deptId, billMasList,
		                    MainetConstants.Property.INT_RECAL_NO);
		            List<TbBillMas> billMasArrears = propertyMainBillService
		                    .fetchNotPaidBillForAssessment(assMst.getAssNo(), orgId);
		            List<TbBillMas> billList = billMasList;

		            if (billMasArrears != null && !billMasArrears.isEmpty()) {
		                interestCalculation(organisation, deptId, billMasArrears,
		                        MainetConstants.Property.INT_RECAL_YES);
		                billList = getBillListWithMergingOfOldAndNewBill(billMasArrears, billMasList);
		                updateArrearInCurrentBills(billList);
		                billList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
		            }
		            // Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
		            // calculateSurcharge(organisation, deptId, billList, null,
		            // MainetConstants.Property.SURCHARGE,
		            // finYearId);
		            taxCarryForward(billList, orgId);
		            // propertyBRMSService.fetchEarlyPayRebateRate(billList, organisation, deptId,
		            // null);

		                iProvisionalBillService.saveAndUpdateTemporaryProvisionalBill(billList, orgId,
		                        empId, ipAddress);
		                batchCount.set(count.intValue());
		            propertyBillExceptionService.updateBillExceptionDataByPropNo(assMst.getAssNo(), "I", null, ipAddress);
		            successfulCount.getAndIncrement();
		        } else {
		            LOGGER.error("fetchCharges is found null :  " + assMst.getAssNo());
		            errorCount.getAndIncrement();
		            propertyBillExceptionService.updateBillExceptionDataByPropNo(assMst.getAssNo(), "A", "fetchCharges is found null :  ", ipAddress);
		        }

		    } else {
		        LOGGER.error("financialYearList is found null");
		        errorCount.getAndIncrement();
		        propertyBillExceptionService.updateBillExceptionDataByPropNo(assMst.getAssNo(), "A", "financialYearList is found null", ipAddress);
		    }
		    count.getAndDecrement();
		    
		} catch (Exception e) {
		    LOGGER.error("Prvisional Demand not generated for Property :" + assMst.getAssNo());
		    count.getAndDecrement();
		    errorCount.getAndIncrement();
		    if(StringUtils.isBlank(e.getMessage())) {
		    	
		    	propertyBillExceptionService.updateBillExceptionDataByPropNo(assMst.getAssNo(), "A", "unable to genearte billUnable to find exception log", ipAddress);
		    }else {
		    	propertyBillExceptionService.updateBillExceptionDataByPropNo(assMst.getAssNo(), "A", e.getMessage(), ipAddress);
		    }
		}
		long endTime = System.currentTimeMillis();
		long timeTaken = (endTime - startTime);  // Total execution time in milli seconds
		LOGGER.info("Time taken for one bill generation : " + timeTaken + " MiliSecond");
		setPropertyBillGenerationMap(size, successfulCount, errorCount, timeTaken, orgId, propBillGenerationMap);
		LOGGER.info("Provisional Demand generation ended for Property :  " + assMst.getAssNo());
	}
	
	@Override
	public List<String> getFlatListByRefNo(String refNo, Long orgId) {
		List<String> flatNoList = new ArrayList<String>();
		String billingMethod = null;
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(refNo, orgId);
		Organisation org=UserSession.getCurrent().getOrganisation();
		if(orgId!=null ) {
			org=iOrganisationService.getOrganisationById(orgId);
		}
		try {
			billingMethod = CommonMasterUtility
					.getNonHierarchicalLookUpObject(billingMethodId,org )
					.getLookUpCode();
		} catch (Exception e) {

		}
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(refNo, orgId);
		}
		return flatNoList;
	}

	@Override
	public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
		return assesmentMastService.getPropNoByOldPropNo(oldPropNo, orgId);
	}
	
	@Override
	public List<String> getOwnerInfoByApplId(Long applicationId, Long orgId) {
		List<String> ownerInfoList = new ArrayList<>();
		List<String> ownerName = new ArrayList<>();
		List<ProvisionalAssesmentMstDto> assessmentMasterList = assesmentMastService
				.getAssesmentMstDtoListByAppId(applicationId, orgId);
		if (CollectionUtils.isNotEmpty(assessmentMasterList)) {
			ProvisionalAssesmentMstDto assMaster = assessmentMasterList.get(assessmentMasterList.size() - 1);
			if (assMaster.getBillMethod() != null && MainetConstants.FlagI
					.equals(CommonMasterUtility.getNonHierarchicalLookUpObject(assMaster.getBillMethod(),
							UserSession.getCurrent().getOrganisation()).getLookUpCode())) {
				List<ProvisionalAssesmentDetailDto> assDetail = assMaster.getProvisionalAssesmentDetailDtoList();
				ownerInfoList.add(assDetail.get(0).getOccupierName() != null ? assDetail.get(0).getOccupierName()
						: MainetConstants.BLANK);
				ownerInfoList.add(assDetail.get(0).getOccupierMobNo() != null ? assDetail.get(0).getOccupierMobNo()
						: MainetConstants.BLANK);
				ownerInfoList.add(assDetail.get(0).getOccupierEmail() != null ? assDetail.get(0).getOccupierEmail()
						: MainetConstants.BLANK);
			} else {
				List<ProvisionalAssesmentOwnerDtlDto> ownerDetail = assMaster.getProvisionalAssesmentOwnerDtlDtoList();
				ownerDetail.forEach(owner -> {
					ownerName.add(owner.getAssoOwnerName());
				});
				ownerInfoList.add(String.join(MainetConstants.BLANK_WITH_SPACE, ownerName));
				ownerInfoList.add(ownerDetail.get(0).getAssoMobileno() != null ? ownerDetail.get(0).getAssoMobileno()
						: MainetConstants.BLANK);
				ownerInfoList.add(
						ownerDetail.get(0).geteMail() != null ? ownerDetail.get(0).geteMail() : MainetConstants.BLANK);
			}
		}
		return ownerInfoList;
	}

	@Override
	public List<BillDisplayDto> getTaxListForDisplayForRevision(TbBillMas billMas, Organisation organisation,
			Long deptId, String interstWaiveOff) {

        List<BillDisplayDto> billDisDtoList = new ArrayList<>();
        LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.SURCHARGE,
                PrefixConstants.LookUpPrefix.TAC, MainetConstants.Property.SECOND_LEVEL, organisation.getOrgid());
        LookUp interestTaxCat = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.FlagI,
                PrefixConstants.LookUpPrefix.TAC, 1, organisation.getOrgid());
        if (billMas != null) {
            billMas.getTbWtBillDet().forEach(billDet -> {
                BillDisplayDto billDisDto = new BillDisplayDto();
                billDisDto.setTaxCategoryId(billDet.getTaxCategory());
                billDisDto.setTaxDesc(billDet.getTaxDesc());
                if (billDet.getDisplaySeq() != null) {
                    billDisDto.setDisplaySeq(billDet.getDisplaySeq());
                } else {
                    TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(),
                            organisation.getOrgid());

                    billDisDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
                    billDisDto.setTaxDesc(taxMas.getTaxDesc());
                }
				if (StringUtils.isNotBlank(interstWaiveOff)
						&& StringUtils.equals(MainetConstants.FlagY, interstWaiveOff)
						&& billDet.getTaxCategory().equals(interestTaxCat.getLookUpId())) {
					billDisDto.setCurrentYearTaxAmt(BigDecimal.valueOf(billDet.getBdCurTaxamt()));
	                billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getBdPrvArramt()));
	                billDisDto.setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurTaxamt() + billDet.getBdPrvArramt()));
				}else {
					billDisDto.setCurrentYearTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()));
	                billDisDto.setArrearsTaxAmt(BigDecimal.valueOf(billDet.getBdPrvBalArramt()));
	                billDisDto.setTotalTaxAmt(BigDecimal.valueOf(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt()));
				}

                // For surcharge call Arrears+current will be display in current tax column
                if (billDet.getTaxSubCategory() != null
                        && billDet.getTaxSubCategory().equals(taxSubCatLookup.getLookUpId())) {
                    billDisDto.setCurrentYearTaxAmt(
                            BigDecimal.valueOf(billDet.getBdCurTaxamt() + billDet.getBdPrvArramt()));
                    billDisDto.setArrearsTaxAmt(new BigDecimal(0));
                }
                // }
                billDisDto.setTaxId(billDet.getTaxId());
                billDisDtoList.add(billDisDto);
            });

            billDisDtoList.sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));
        }
        return billDisDtoList;

    
	}
	
	   @Override
	    public double getTotalActualAmountForRevision(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList,
	            Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge) {
	        AtomicDouble totAmt = new AtomicDouble(0);
	        if (billMasList != null && !billMasList.isEmpty()) {// will be add extra check for notice or any other extra
	                                                            // charges
	            for (TbBillDet det : billMasList.get(billMasList.size() - 1).getTbWtBillDet()) {
	                totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
	            }
	            if (CollectionUtils.isNotEmpty(rebateDtoList)) { // Early Payment Rebate
	                rebateDtoList.forEach(rebateDto -> {
	                    if (rebateDto != null) {
	                        totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue());
	                    }
	                });
	            }
	            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {// Demand level Rebate
	                taxWiseRebate.entrySet().forEach(demandRebet -> {
	                    totAmt.getAndSet(totAmt.doubleValue() - demandRebet.getValue().getTotalTaxAmt().doubleValue());
	                });
	            }
	            if (surCharge != null) {
	                totAmt.addAndGet(surCharge.getTotalTaxAmt().doubleValue());
	            }
	            /*
	             * if (rebateDto != null && rebateDto.getTotalTaxAmt() != null) { // Early Payment Rebate
	             * totAmt.getAndSet(totAmt.doubleValue() - rebateDto.getTotalTaxAmt().doubleValue()); }
	             */
	        }
	        return totAmt.doubleValue() > 0 ? Math.round(totAmt.doubleValue()) : 0;
	    }


	   @Override
	    @Transactional
	    public List<BillReceiptPostingDTO> updateBillMasterAmountPaidForGroupProperty(final String propertyNo, Double amount,
	            final Long orgId, final Long userId, String ipAddress, Date manualReceptDate,String flatNo,String parentNo) {// Advance Amount Pending
	    	LOGGER.info("Method parameters of updateBillMasterAmountPaid"+" "+"propno"+" "+propertyNo +" "+"orgid"+ orgId);
	    	List<BillReceiptPostingDTO> result = new ArrayList<>();
			final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
			final Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			final Organisation org = new Organisation();
			final double actualPayAmt = amount;
			org.setOrgid(orgId);
			List<TbBillMas> billMasData = null;
			LookUp billMethod = null;
			LookUp billingMethodLookUp = null;
			try {
				billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV",
						UserSession.getCurrent().getOrganisation());
			} catch (Exception e) {
			}
			int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
			count = 1;
			
			if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
					&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
				Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propertyNo, orgId);
				billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
						UserSession.getCurrent().getOrganisation());
			}
			
			List<String> propNoList = assesmentMstRepository.fetchPropertyNosByParentPropNo(parentNo);
				if (count > 0) {
					// Form Main Bill table
						billMasData = propertyMainBillService.fetchNotPaidBillForAssessmentByParentPropNo(parentNo, orgId);
						
				} else {
						billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessmentByParentPropNo(parentNo, orgId);
					// From Provisional Bill Table
				}
			

			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					MainetConstants.STATUS.ACTIVE);
			if ((billMasData != null) && !billMasData.isEmpty()) {
				propertyBRMSService.fetchInterstRate(billMasData, org, deptId);
				LookUp penalIntLookUp = null;
				try {
					penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", org);
				} catch (Exception e) {
					LOGGER.error("No Prefix found for ENV(PIT)");
				}
				if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
						&& StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
					
					billMasterCommonService.calculatePenaltyInterest(billMasData, org, deptId, MainetConstants.FlagY,
							manualReceptDate, MainetConstants.FlagN, MainetConstants.FlagY, userId);
					 
				} else {
					billMasterCommonService.calculateMultipleInterest(billMasData, org, deptId, MainetConstants.Y_FLAG,
							null);
				}
				//claculateDemandNoticeGenCharges(billMasData, org, deptId, userId);
				Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
				
				List<PropertyPenltyDto> currentFinYearPenaltyDtoList = null;
				
				if(StringUtils.isNotBlank(parentNo)) {
					currentFinYearPenaltyDtoList = propertyPenltyService.calculateExistingSurchargeByParentPropNo(parentNo,
							finYearId, orgId);
				}
				
				List<BillDisplayDto> surChargeList = new ArrayList<BillDisplayDto>();
				BillDisplayDto surCharge = calculateSurcharge(org, deptId, billMasData, propertyNo,
						MainetConstants.Property.SURCHARGE, finYearId, manualReceptDate);
				if (CollectionUtils.isEmpty(currentFinYearPenaltyDtoList) && surCharge != null && actualPayAmt > 0
						&& surCharge.getTotalTaxAmt().doubleValue() > 0) {
					double pendingSurcharge = surCharge.getTotalTaxAmt().doubleValue();
					amount -= surCharge.getTotalTaxAmt().doubleValue();
					double amountPaidSurcharge = actualPayAmt - amount;
					pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
					if (amount >= 0) {
						propertyPenltyService.savePropertyPenlty(propertyNo, surCharge.getFinYearId(), orgId, userId,
								ipAddress, surCharge.getTotalTaxAmt().doubleValue(), pendingSurcharge,parentNo);
					}
					surChargeList.add(surCharge);
				} else {
					if (CollectionUtils.isNotEmpty(currentFinYearPenaltyDtoList)) {
						for (PropertyPenltyDto currentFinYearPenaltyDto : currentFinYearPenaltyDtoList) {
							if (currentFinYearPenaltyDto != null && currentFinYearPenaltyDto.getPendingAmount() > 0) {
								surCharge = new BillDisplayDto();
								LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(
										MainetConstants.Property.SURCHARGE, PrefixConstants.LookUpPrefix.TAC, 2,
										org.getOrgid());
								final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
										MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA, org);
								List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(
										org.getOrgid(), deptId, taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
								surCharge.setTotalTaxAmt(new BigDecimal(currentFinYearPenaltyDto.getPendingAmount()));
								surCharge.setTaxId(taxList.get(0).getTaxId());
								surCharge.setTaxCategoryId(taxList.get(0).getTaxCategory1());
								double pendingSurcharge = currentFinYearPenaltyDto.getPendingAmount();
								if(amount > 0) {
								if(amount >= pendingSurcharge) {
									amount -= currentFinYearPenaltyDto.getPendingAmount();
									double amountPaidSurcharge = actualPayAmt - amount;
									surCharge.setTaxAmt(pendingSurcharge);
									pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
									if (pendingSurcharge < 0) {
										pendingSurcharge = 0.0;
									}
								}else {
									pendingSurcharge = currentFinYearPenaltyDto.getPendingAmount() - amount;
									surCharge.setTaxAmt(amount);
									amount -= currentFinYearPenaltyDto.getPendingAmount();
								}
							}
								currentFinYearPenaltyDto.setPendingAmount(pendingSurcharge);
								propertyPenltyService.updatePropertyPenalty(currentFinYearPenaltyDto, ipAddress, userId);
								surChargeList.add(surCharge);
							}
						}

					}
				}

				final TbBillMas last = billMasData.get(billMasData.size() - 1);
				List<BillReceiptPostingDTO> rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
				List<BillDisplayDto> rebateDtoList = null;
				if (last.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {

					Date currDate = new Date();
					if(manualReceptDate != null) {
						currDate = manualReceptDate;
					}
					Long payFinYearId = iFinancialYearService.getFinanceYearId(currDate);
					FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
							.getBean(IFinancialYearService.class).getFinincialYearsById(payFinYearId, orgId);
					Long noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYear(parentNo,
							currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
					Organisation organisation = new Organisation();
					organisation.setOrgid(orgId);
					LookUp rebate = null;
					try {
						rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", organisation);

					} catch (Exception e) {
						LOGGER.error("No prefix found for ENV(RBT)");
					}
					String paymentMethodType = null;
					double receiptAmount = 0.0;
					Long rebateReceivedCount = 0L;
					if(rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
							&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
						paymentMethodType = billMasterCommonService.getFullPaymentOrHalfPayRebate(billMasData,org);
						BigDecimal totalReceiptAmount = iReceiptEntryService.getReceiptAmountPaidByPropNoOrFlatNo(propertyNo, flatNo, org, deptId);
						if(totalReceiptAmount != null) {
							receiptAmount = totalReceiptAmount.doubleValue();
						}
						rebateReceivedCount = PropertyBillPaymentService.getRebateReceivedCount(propertyNo, flatNo,
								currDate, currentFinYearDto, organisation, deptId);
						last.setBmToatlRebate(0.0);
					}
					if (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear <= 0) {
						LookUp billDeletionInactive = null;
						try {
							billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
						} catch (Exception e) {
							LOGGER.error("No prefix found for ENV - BDI");
						}
						if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
								&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
							noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYearAdvance(
									parentNo, currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
						}
					}
					if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
							&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
							&& billMasterCommonService.checkRebateAppl(billMasData,org) && rebateReceivedCount <= 0 && last.getBmYear().equals(finYearId))
							|| (rebate == null && ((last.getBmToatlRebate() > 0)
									|| (last.getBmToatlRebate() == 0 && noOfBillsPaidInCurYear == null
											|| noOfBillsPaidInCurYear == 0))
									&& last.getBmLastRcptamt() == 0.0)) {
						AtomicLong propertyTaxId = new AtomicLong(0);
						   final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
					                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
					                organisation);
					        final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
					                MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
					                organisation);
					        List<TbTaxMas> indepenTaxList = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
					                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
					        
						indepenTaxList.forEach(taxMas -> {
							String lookUpCode = CommonMasterUtility.getNonHierarchicalLookUpObject(taxMas.getTaxDescId(), organisation)
									.getLookUpCode();

							if (StringUtils.equals(lookUpCode, "PTR")) {
								propertyTaxId.getAndSet(taxMas.getTaxId());
							}
						});
						List<Double> propertyTax = getCurrentPropertyTaxForGroupProperty(billMasData, finYearId,organisation,propertyTaxId.longValue());
						
						rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRateForGoupProperty(billMasData, org, deptId,
								manualReceptDate, paymentMethodType,propertyTax.get(0),propertyTax.get(1),propertyTax.get(2),propertyTax.get(3));

						double rebateAmount = 0.0;

						// #107759 By Arun
						Double bmTotalOutStandingAmt = 0.0;
						LookUp userChargesLookUp = null;
						try {
							userChargesLookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.EUC,
									MainetConstants.ENV, organisation);
						} catch (Exception e) {
							LOGGER.error("No prefix found for ENV - EUC");
						}
						for (BillDisplayDto billDisplayDto : rebateDtoList) {
							rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
						}

						if (CollectionUtils.isNotEmpty(rebateDtoList)) {
							double roundedRebateAmt = Math.round(rebateAmount);
							if (last.getBmToatlRebate() > 0) {
								roundedRebateAmt = last.getBmToatlRebate();
							}
							// Exclude user charges
							if (userChargesLookUp != null
									&& MainetConstants.Y_FLAG.equals(userChargesLookUp.getOtherField())) {
								for (TbBillDet billDet : last.getTbWtBillDet()) {
									LookUp taxCat = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(),
											organisation);
									if (StringUtils.equals(taxCat.getLookUpCode(), MainetConstants.FlagN)) {
										for (BillDisplayDto billDisplayDto : rebateDtoList) {
											TbTaxMas taxMas = tbTaxMasService.findTaxDetails(orgId,
													billDisplayDto.getParentTaxCode());
											if (billDet.getTaxId().equals(taxMas.getTaxId())) {
												bmTotalOutStandingAmt = bmTotalOutStandingAmt
														+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
											}
										}
									} else {
										bmTotalOutStandingAmt = bmTotalOutStandingAmt
												+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
									}
								}
								bmTotalOutStandingAmt = bmTotalOutStandingAmt - roundedRebateAmt;
							} else {
								bmTotalOutStandingAmt = propertyTax.get(4) - roundedRebateAmt;
							}
							double paidAmount = amount.doubleValue();
							if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
									&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
								paidAmount = paidAmount + receiptAmount;
								bmTotalOutStandingAmt = billMasterCommonService.getFullPayableOutstanding(billMasData, organisation,null) - roundedRebateAmt;
							}
						if ((paidAmount >= bmTotalOutStandingAmt)) {
							for (TbBillMas billMas : billMasData) {
								if (billMas.getBmYear().equals(finYearId)) {

									for (TbBillDet billDet : billMas.getTbWtBillDet()) {
										if(billDet.getTaxId().equals(propertyTaxId.longValue())) {
									
									Long iterator = 1l;
									double checkAmt = 0d;
									for (BillDisplayDto rebateDto : rebateDtoList) {
										BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
										double propertyWiseRebate = (billDet.getBdCurBalTaxamt() * rebateDto.getPercentageRate()) / 100;
										roundedRebateAmt = propertyWiseRebate;
										rebateDto.setTotalTaxAmt(new BigDecimal(propertyWiseRebate));
										// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());
										billDetails.put(rebateDto.getTaxId(), null);
										rebateTax.setBillMasId(billMas.getBmIdno());
										double correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
										double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
										checkAmt += roundedTax;
										if (rebateDtoList.size() == iterator) {
											// if equal than check rebateAmt is equal to checkAmt
											if (roundedRebateAmt != checkAmt) {
												double testAmt = roundedRebateAmt - checkAmt;
												if (testAmt > 0) {
													correctTax += testAmt;
												} else {
													correctTax += testAmt;
												}
											}
										}
										iterator++;
										rebateTax.setTaxAmount(correctTax);
										details.put(rebateDto.getTaxId(), correctTax);
										rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
										rebateTax.setTaxId(rebateDto.getTaxId());
										rebateTax.setYearId(billMas.getBmYear());
										billMas.setBmToatlRebate(Math.round(rebateAmount));
										if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
											ArrayList<Double> taxValueIdList = new ArrayList<Double>();
											taxValueIdList.add(correctTax);
											taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
											billMas.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
										}
										rebateTaxList.add(rebateTax);
									}
									
										}
									
									}

								}

							}

						}
							else if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
									&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY) && last.getBmYear().equals(finYearId)) {
								Map<String, Object> checkHalfPaymentRebateAppl = checkHalfPaymentRebateAppl(billMasData,
										org, deptId, manualReceptDate, paymentMethodType, amount.doubleValue(),receiptAmount);
								Date secondSemStartDate = PropertyBillPaymentService.getSecondSemStartDate(propertyNo,
										flatNo, currDate, currentFinYearDto, organisation, deptId);
								if((boolean) checkHalfPaymentRebateAppl.get("halfPayApplicable") && Utility.compareDate(currDate,secondSemStartDate)) {
									checkHalfPaymentRebateAppl.get("rebateList");
									@SuppressWarnings("unchecked")
									List<BillDisplayDto> halfRebateDtoList = (List<BillDisplayDto>) checkHalfPaymentRebateAppl.put("rebateList", rebateDtoList);
									double totalRebateAmount = (double) checkHalfPaymentRebateAppl.get("totalRebate");
									roundedRebateAmt = Math.ceil(totalRebateAmount);
									Long iterator = 1l;
									double checkAmt = 0d;
									rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
									for (BillDisplayDto rebateDto : halfRebateDtoList) {
										BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
										// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

										billDetails.put(rebateDto.getTaxId(), null);
										rebateTax.setBillMasId(last.getBmIdno());
										double correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
										double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
										checkAmt += roundedTax;
										if (rebateDtoList.size() == iterator) {
											if (roundedRebateAmt != checkAmt) {
												double testAmt = roundedRebateAmt - checkAmt;
												if (testAmt > 0) {
													correctTax += testAmt;
												} else {
													correctTax += testAmt;
												}
											}
										}
										iterator++;
										rebateTax.setTaxAmount(correctTax);
										details.put(rebateDto.getTaxId(), correctTax);
										rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
										rebateTax.setTaxId(rebateDto.getTaxId());
										rebateTax.setYearId(last.getBmYear());
										last.setBmToatlRebate(roundedRebateAmt);
										if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
											ArrayList<Double> taxValueIdList = new ArrayList<Double>();
											taxValueIdList.add(correctTax);
											taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
											last.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
										}
										rebateTaxList.add(rebateTax);
									}
								}
							}else {
								last.setBmToatlRebate(0.0);
							}
						}

					}

					double totalDemand = last.getBmTotalOutstanding();

					billMasData.forEach(billMas -> {
						billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
					});
					if (checkBifurcationMethodApplicable(org)) {
						result = billMasterCommonService.updateBifurcationMethodBillData(billMasData, amount.doubleValue(),
								details, billDetails, org, rebateDetails, manualReceptDate);
					}else if(Utility.isEnvPrefixAvailable(organisation, "KIF")) {
						List<BillReceiptPostingDTO> interestResult = new ArrayList<>();
						interestResult = billMasterCommonService.updateBillDataForInterest(billMasData, amount.doubleValue(), details,
								billDetails, org, rebateDetails, manualReceptDate);
							result = billMasterCommonService.updateBillDataForGroupPropNo(billMasData, last.getExcessAmount(), details,
									billDetails, org, rebateDetails, manualReceptDate,propNoList);
						result.addAll(interestResult);
					}
					else {
						result = billMasterCommonService.updateBillDataForGroupPropNo(billMasData, amount.doubleValue(), details,
								billDetails, org, rebateDetails, manualReceptDate,propNoList);
					}
					billMasData.get(billMasData.size() - 1).setBmLastRcptamt(actualPayAmt);

					if (CollectionUtils.isNotEmpty(rebateTaxList)) {
						for (BillReceiptPostingDTO rebateTax : rebateTaxList) {
							rebateTax.setRebateDetails(rebateDetails);
							result.add(rebateTax);
						}

					}
					if (!result.isEmpty()) { // for total demand in receipt master
						result.get(0).setTotalDemand(totalDemand);
					}
					
					
					if(StringUtils.isNotBlank(parentNo)) {
						for (BillDisplayDto surChargeDto : surChargeList) {
							if (surChargeDto != null && actualPayAmt > 0 && surChargeDto.getTotalTaxAmt().doubleValue() > 0) {
								BillReceiptPostingDTO surchargeRecDto = new BillReceiptPostingDTO();
								surchargeRecDto.setPayableAmount(surChargeDto.getTotalTaxAmt().doubleValue());
								surchargeRecDto.setTaxId(surChargeDto.getTaxId());
								surchargeRecDto.setTaxAmount(surChargeDto.getTaxAmt());
								surchargeRecDto.setTaxCategory(surChargeDto.getTaxCategoryId());
								final String taxCode = CommonMasterUtility.getHierarchicalLookUp(surChargeDto.getTaxCategoryId(), org)
										.getLookUpCode();
								surchargeRecDto.setTaxCategoryCode(taxCode);
								surchargeRecDto.setTotalDetAmount(surChargeDto.getTotalTaxAmt().doubleValue());
								result.add(surchargeRecDto);
							}
						}
					}else {
						if (surCharge != null && actualPayAmt > 0 && surCharge.getTotalTaxAmt().doubleValue() > 0) {
							BillReceiptPostingDTO surchargeRecDto = new BillReceiptPostingDTO();
							surchargeRecDto.setPayableAmount(surCharge.getTotalTaxAmt().doubleValue());
							surchargeRecDto.setTaxId(surCharge.getTaxId());
							surchargeRecDto.setTaxAmount(surCharge.getTaxAmt());
							surchargeRecDto.setTaxCategory(surCharge.getTaxCategoryId());
							final String taxCode = CommonMasterUtility.getHierarchicalLookUp(surCharge.getTaxCategoryId(), org)
									.getLookUpCode();
							surchargeRecDto.setTaxCategoryCode(taxCode);
							surchargeRecDto.setTotalDetAmount(surCharge.getTotalTaxAmt().doubleValue());
							result.add(surchargeRecDto);
						}
					}
					if (count > 0) {
						// Form Main Bill table
						 for (String propNo : propNoList) {
					        	List<TbBillMas> billMasList = billMasData.stream().filter(list ->list.getPropNo().equals(propNo)).collect(Collectors.toList());
					        	if(CollectionUtils.isNotEmpty(billMasList)) {
					        		billMasterCommonService.updateArrearInCurrentBills(billMasList);
					        		propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId, userId, null, ipAddress);
					        	}
							
						}
					} else {
						// From Provisional Bill Table
						iProvisionalBillService.saveAndUpdateProvisionalBill(billMasData, orgId, userId,
								propertyNo.toString(), null, null, null);
					}
					if (last.getExcessAmount() > 0) {// Excess Payment
						final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
						details.put(advanceTaxId, last.getExcessAmount());// to add advance amt into receipt det
						setAdnavcePayDetail(result, org, last.getExcessAmount(), advanceTaxId);
					}
				} else {// Payment is pure Advance Payment(No dues are pending)
					final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
					details.put(advanceTaxId, amount);
					setAdnavcePayDetail(result, org, amount, advanceTaxId);
				}
			} else {// Payment is pure Advance Payment(No dues are pending)
				LOGGER.info("no bills found");
				final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
				details.put(advanceTaxId, amount);
				setAdnavcePayDetail(result, org, amount, advanceTaxId);
			}
			LOGGER.info("updateBillMasterAmountPaid method executrd succesfully");

			return result;
	    }
	   
	   private List<Double> getCurrentPropertyTaxForGroupProperty(List<TbBillMas> billMasList, Long finId,Organisation organisation, Long propertyTaxId){
		   List<Double> propertyTaxList = new ArrayList<Double>();
		   AtomicDouble currentAmount = new AtomicDouble(0);
		   AtomicDouble currentBalanceAmount = new AtomicDouble(0);
		   AtomicDouble arrearAmount = new AtomicDouble(0);
		   AtomicDouble arrearBalanceAmount = new AtomicDouble(0);
		   AtomicDouble totalOutStanding = new AtomicDouble(0);
	        
		   billMasList.forEach(billMas ->{
			   if(billMas.getBmYear().equals(finId)) {
				   billMas.getTbWtBillDet().forEach(billDet ->{
					   if(billDet.getTaxId().equals(propertyTaxId.longValue())) {
						   currentAmount.addAndGet(billDet.getBdCurTaxamt());
						   currentBalanceAmount.addAndGet(billDet.getBdCurBalTaxamt());
						   arrearAmount.addAndGet(billDet.getBdPrvArramt());
						   arrearBalanceAmount.addAndGet(billDet.getBdPrvBalArramt());
						   
					   }
				   }); 
			   }
		   });
		   
		   billMasList.forEach(billMas ->{
				   billMas.getTbWtBillDet().forEach(billDet ->{
						   totalOutStanding.addAndGet(billDet.getBdCurBalTaxamt());
						   
				   }); 
		   });
		   
		   propertyTaxList.add(currentBalanceAmount.doubleValue());
		   propertyTaxList.add(currentAmount.doubleValue());
		   propertyTaxList.add(arrearBalanceAmount.doubleValue());
		   propertyTaxList.add(arrearAmount.doubleValue());
		   propertyTaxList.add(totalOutStanding.doubleValue());
		   
		   return propertyTaxList;
	   }

	   @Override
	   @Transactional
	   public TbBillMas getLatestYearBill(String propNoOrConnNo, Long orgId){
		   Long financeYearId = iFinancialYearService.getFinanceYearId(new Date());
		List<MainBillMasEntity> latestBill = ApplicationContextProvider.getApplicationContext()
				.getBean(PropertyMainBillRepository.class)
				.fetchAllBillByPropNoAndCurrentFinId(propNoOrConnNo, orgId, financeYearId);
		   if(CollectionUtils.isNotEmpty(latestBill)) {
			   TbBillMas billMasdto = new TbBillMas();
			   BeanUtils.copyProperties(latestBill.get(0), billMasdto);
			   return billMasdto;
		   }else {
			   return null;
		   }
		  
	   }
	   
	   @Override   
	public void updateARVRVInBill(ProvisionalAssesmentMstDto dto, List<TbBillMas> billMasList) {

		   Map<Long, Double> finAlvMap = new HashMap<>();
			Map<Long, Double> finRvMap = new HashMap<>();
			Map<Long, Double> finCvmap = new HashMap<>();
			Map<Long, Double> finStdRate = new HashMap<>();
			dto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
				if (!finAlvMap.containsKey(detail.getFaYearId())) {
					finAlvMap.put(detail.getFaYearId(), detail.getAssdAlv());
					finRvMap.put(detail.getFaYearId(), detail.getAssdRv());
					finCvmap.put(detail.getFaYearId(), detail.getAssdCv());
					finStdRate.put(detail.getFaYearId(), detail.getAssdStdRate());
				} else {
					finAlvMap.put(detail.getFaYearId(),
							finAlvMap.get(detail.getFaYearId()) + detail.getAssdAlv());
					finRvMap.put(detail.getFaYearId(), finRvMap.get(detail.getFaYearId()) + detail.getAssdRv());
					finCvmap.put(detail.getFaYearId(), finCvmap.get(detail.getFaYearId()) + detail.getAssdCv());
				}
			});
			for (TbBillMas bill : billMasList) {
				if(finStdRate.get(bill.getBmYear()) != null && finStdRate.get(bill.getBmYear()) > 0) {
					bill.setAssdStdRate(finStdRate.get(bill.getBmYear()));
				}
				if(finAlvMap.get(bill.getBmYear()) != null && finAlvMap.get(bill.getBmYear()) > 0) {
					bill.setAssdAlv(finAlvMap.get(bill.getBmYear()));
				}
				if(finRvMap.get(bill.getBmYear()) != null && finRvMap.get(bill.getBmYear()) > 0) {
					bill.setAssdRv(finRvMap.get(bill.getBmYear()));
				}
				if(finCvmap.get(bill.getBmYear()) != null && finCvmap.get(bill.getBmYear()) > 0) {
					bill.setAssdCv(finCvmap.get(bill.getBmYear()));
				}
				
			}
	}
	   
	   @Override
	   @Transactional
	   public boolean isChequeClearDishonour(TbServiceReceiptMasBean receiptMasBean, Long orgId){
		    try {
		    	for(TbSrcptModesDetBean mode : receiptMasBean.getReceiptModeList()) {
			    	 String payMode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(mode.getCpdFeemode(), orgId, "PAY")
							  .getLookUpCode();
			    	 
			    	  //cheque or DD 
					  if(("D".equals(payMode) || "Q".equals(payMode)) ) {
						  if( mode.getCheckStatus() == null) {
							  return false;
						  } else {
							  String chequeStatus =  CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(mode.getCheckStatus(), orgId, "CLR")
									  .getLookUpCode();
							  if(MainetConstants.AccountConstants.CHEQUE.CLEARED.getValue().equals(chequeStatus)) {
								  return true;
							  }else if(MainetConstants.AccountConstants.CHEQUE.DISHONORED.getValue().equals(chequeStatus)){
								  return false;
							  }
						  }
					  } 
			    }
		    } catch (Exception e) {
		    	LOGGER.info("Error occured while fetching check dishonour details: " + e);
		    	return false;
			}
		    
		   return true;
	   }

	@Override
	public boolean checkOldPropNoExist(String oldPropNo, Long orgId) {
		boolean oldPropExist = false;
		Long countByOldPropNo = provisionalAssesmentMstRepository.getCountByOldPropNo(oldPropNo, orgId);
		if(countByOldPropNo != null && countByOldPropNo > 1) {
			oldPropExist = true;
		}
		if(!oldPropExist) {
			Long oldPropNoCount = assesmentMstRepository.getOldPropNoCount(oldPropNo);
			if(oldPropNoCount != null && oldPropNoCount > 1) {
				oldPropExist = true;
			}
		}
		return oldPropExist;
	}
	
	@Override
    public void updateArrearInCurrentBillsForBillRevise(List<TbBillMas> billMasList, TbBillMas lastBillMas) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
        List<TbBillMas> previousBillMass = null;
        for (final TbBillMas currBillMas : billMasList) {
        	
        	if(StringUtils.isNotBlank(currBillMas.getWtLo1()) && StringUtils.equals("Y", currBillMas.getWtLo1())) {
        		 if (previousBillMass == null) {
                     previousBillMass = new ArrayList<>();
                 }
                 double amount = 0d;
                 previousBillMass.add(lastBillMas);
                 if (!previousBillMass.isEmpty()) {
                     for (final TbBillMas prevBillMas : previousBillMass) {
                         amount += prevBillMas.getBmTotalBalAmount() + prevBillMas.getBmTotalArrearsWithoutInt();
                     }
                     currBillMas.setBmTotalArrearsWithoutInt(amount);
                 }

                 if (lastBillMas != null) {

                     currBillMas
                             .setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
                 } else {
                     currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
                 }

                 currBillMas.setBmTotalArrears(
                         currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt());

                 currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());
                 if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
                         && (lastBillMas != null)) {
                     for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
                         for (final TbBillDet lastDet : lastBillMas.getTbWtBillDet()) {
                             if (det.getTaxId().equals(lastDet.getTaxId())) {
                                 det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                                 det.setBdPrvBalArramt(det.getBdPrvArramt());
                             }
                         }
                     }
                 }
                 lastBillMas = currBillMas;
        	}
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
    }
	
	@Override
	public void createBillDetWhereFirstBillHaveArrearAmount(List<TbBillMas> billMasList) {
		Long firstBillBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(billMasList.get(0).getPropNo());
		billMasList.forEach(billMas ->{
			if(billMas.getBmIdno() == firstBillBmIdNo) {
				billMas.getTbWtBillDet().forEach(det ->{
					if(det.getBdPrvBalArramt() > 0) {
						det.setCalculatePenalAmountAsonDate(det.getBdCurTaxamt());
						det.setBdCurTaxamt(det.getBdCurTaxamt() + det.getBdPrvArramt());
						det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
					}
				});
			}
		});
	}
	
	@Override
	public void createBillDetWhereFirstBillHaveArrearAmountForReceiptReversal(List<TbBillMas> billMasList) {
		Long firstBillBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(billMasList.get(0).getPropNo());
		billMasList.forEach(billMas ->{
			if(billMas.getBmIdno() == firstBillBmIdNo) {
				billMas.getTbWtBillDet().forEach(det ->{
					if(det.getBdPrvArramt() > 0) {
						det.setCalculatePenalAmountAsonDate(det.getBdCurTaxamt());
						det.setBdCurTaxamt(det.getBdCurTaxamt() + det.getBdPrvArramt());
						det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
						det.setBdPrvBalArramt(0.00);
					}
				});
			}
		});
	}
	
	@Override
	public void reArrangeTheDataWhereFirstBillHaveArrearAmount(List<TbBillMas> billMasList) {
		Long firstBillBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(billMasList.get(0).getPropNo());
		billMasList.forEach(billMas ->{
			if(billMas.getBmIdno() == firstBillBmIdNo) {
				billMas.getTbWtBillDet().forEach(det ->{
					if(det.getCalculatePenalAmountAsonDate() > 0) {
						if(det.getBdCurBalTaxamt() > det.getCalculatePenalAmountAsonDate()) {
							double arrearBalance = det.getBdCurBalTaxamt() - det.getCalculatePenalAmountAsonDate();
							det.setBdCurTaxamt(det.getCalculatePenalAmountAsonDate());
							det.setBdCurBalTaxamt(det.getCalculatePenalAmountAsonDate());
							det.setBdPrvBalArramt(arrearBalance);
						}else {
							det.setBdCurTaxamt(det.getCalculatePenalAmountAsonDate());
							det.setBdPrvBalArramt(0.00);
						}
						det.setCalculatePenalAmountAsonDate(0.00);
					}
				});
			}
		});
	}
	
	 public void updateArrearInCurrentBillsForAdvanceAdjustment(List<TbBillMas> billMasList, TbBillMas lastBillMas) {
	    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
	        List<TbBillMas> previousBillMass = null;
	        for (final TbBillMas currBillMas : billMasList) {
	        	
	        	if(currBillMas.getBmIdno() <= 0) {
	        		 if (previousBillMass == null) {
	 	                previousBillMass = new ArrayList<>();
	 	            }
	 	            double amount = 0d;
	 	            previousBillMass.add(lastBillMas);
	 	            if (!previousBillMass.isEmpty()) {
	 	                for (final TbBillMas prevBillMas : previousBillMass) {
	 	                    amount += prevBillMas.getBmTotalBalAmount() + prevBillMas.getBmTotalArrearsWithoutInt();
	 	                }
	 	                currBillMas.setBmTotalArrearsWithoutInt(amount);
	 	            }

	 	            if (lastBillMas != null) {

	 	                currBillMas
	 	                        .setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
	 	            } else {
	 	                currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
	 	            }

	 	            currBillMas.setBmTotalArrears(
	 	                    currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt());

	 	            currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());
	 	            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
	 	                    && (lastBillMas != null)) {
	 	                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
	 	                    for (final TbBillDet lastDet : lastBillMas.getTbWtBillDet()) {
	 	                        if (det.getTaxId().equals(lastDet.getTaxId())) {
	 	                            det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
	 	                            det.setBdPrvBalArramt(det.getBdPrvArramt());
	 	                        }
	 	                    }
	 	                }
	 	            }
	 	            lastBillMas = currBillMas;
	        	}
	           
	        }
	        LOGGER.info("End--> " + this.getClass().getSimpleName() + "updateArrearInCurrentBills() method");
	    }
	 
	 private void updatePreviousYearReceipts(List<TbBillMas>  billMasList, List<AsExcessAmtEntity> excessAmtEntByPropNo,Organisation org,
			 List<TbBillMas> billMasArrears,Long deptId,Long userId,List<ProvisionalBillMasEntity> provBillList,
			 ProvisionalAssesmentMstDto assMst,int languageId,String saveFlag) {
		 propertyBRMSService.fetchInterstRate(billMasArrears, org, deptId);
		 excessAmtEntByPropNo.sort(Comparator.comparing(AsExcessAmtEntity::getExcessId));
		 double ajustedAmt = 0;
		 long lastExcessId = excessAmtEntByPropNo.get(excessAmtEntByPropNo.size() -1).getExcessId();
		 boolean knockOffCurrentBill = false;
		 Double howMuchAmountToAdjust = 0.00;
		 AtomicDouble totAmt = new AtomicDouble(0);
		 for (AsExcessAmtEntity exAmt : excessAmtEntByPropNo) {
             totAmt.addAndGet(exAmt.getExcAmt() - exAmt.getAdjAmt());
         }
		 for (AsExcessAmtEntity excessDto : excessAmtEntByPropNo) {
			 double totalAmt = excessDto.getExcAmt() - excessDto.getAdjAmt();
			 BillDisplayDto advanceAmt = createAdvanceDisplayDto(org.getOrgid(), totalAmt, excessDto);
			 billMasterCommonService.calculateInterestForPrayagRaj(billMasArrears, org, deptId, "Y", excessDto.getCreatedDate(), userId, billMasArrears,null);
			 List<BillReceiptPostingDTO> billRecePstingDto = null;
             
             if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
            	 double finalAdjustAmount = 0;
                 billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(billMasArrears, advanceAmt, org,new Date(),null);
                 billMasterCommonService.updateArrearInCurrentBills(billMasArrears);
                 if (billMasArrears.get(billMasArrears.size() - 1).getExcessAmount() > 0) {
                     ajustedAmt = ajustedAmt + (advanceAmt.getCurrentYearTaxAmt().doubleValue()
                             - billMasArrears.get(billMasArrears.size() - 1).getExcessAmount());
                 } else {
                     ajustedAmt = ajustedAmt + (advanceAmt.getCurrentYearTaxAmt().doubleValue());
                 }
                 
                 if(lastExcessId == excessDto.getExcessId() && totAmt.doubleValue() > ajustedAmt) {
                	 knockOffCurrentBill = true;
                	 howMuchAmountToAdjust = totAmt.doubleValue() - ajustedAmt;
                 }
                 if(lastExcessId == excessDto.getExcessId() && !knockOffCurrentBill) {
                	 finalAdjustAmount = ajustedAmt;
                 }
                 if(StringUtils.equals(MainetConstants.FlagY, saveFlag)) {
                	 selfAssessmentService.saveAdvanceAmt(assMst, userId, deptId, advanceAmt, org, billMasArrears, assMst.getLgIpMac(),
   	                      provBillList, billRecePstingDto, finalAdjustAmount,null,languageId);
                 }
                 
             }
		}
		 
		 if(knockOffCurrentBill && CollectionUtils.isNotEmpty(billMasList)) {
			 BillDisplayDto advanceAmt = createAdvanceDisplayDto(org.getOrgid(), howMuchAmountToAdjust, excessAmtEntByPropNo.get(excessAmtEntByPropNo.size() -1));
			 List<BillReceiptPostingDTO> billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(billMasList, advanceAmt, org,new Date(),null);
             billMasterCommonService.updateArrearInCurrentBills(billMasList);
             if (billMasList.get(billMasList.size() - 1).getExcessAmount() > 0) {
                 ajustedAmt = ajustedAmt + (advanceAmt.getCurrentYearTaxAmt().doubleValue()
                         - billMasList.get(billMasList.size() - 1).getExcessAmount());
             } else {
                 ajustedAmt = ajustedAmt + (advanceAmt.getCurrentYearTaxAmt().doubleValue());
             }
             
             selfAssessmentService.saveAdvanceAmt(assMst, userId, deptId, advanceAmt, org, billMasList, assMst.getLgIpMac(),
                     provBillList, billRecePstingDto, ajustedAmt,null,languageId);
		 }
	 }
	 private BillDisplayDto createAdvanceDisplayDto(Long orgId, Double totAmt,
				AsExcessAmtEntity advEnt) {
		 BillDisplayDto advanceAmtDto = null;
			if (totAmt.doubleValue() > 0) {
				 advanceAmtDto = new BillDisplayDto();
			    TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(advEnt.getTaxId(), orgId);
			    advanceAmtDto.setCurrentYearTaxAmt(BigDecimal.valueOf(totAmt.doubleValue()));
			    advanceAmtDto.setTotalTaxAmt(BigDecimal.valueOf(totAmt.doubleValue()));
			    advanceAmtDto.setTaxId(taxMas.getTaxId());
			    advanceAmtDto.setTaxCategoryId(taxMas.getTaxCategory1());
			    advanceAmtDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
			    advanceAmtDto.setTaxDesc(taxMas.getTaxDesc());
			}
			return advanceAmtDto;
		}
}