package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.PropertyMastRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.google.common.util.concurrent.AtomicDouble;

@Service(value = "PropertyBillPayment")
public class PropertyBillPaymentServiceImpl implements PropertyBillPaymentService, BillDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyBillPaymentServiceImpl.class);
    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Resource
    private IChallanService iChallanService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyMastRepository propertyMastRepository;

    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private IOrganisationService organisationService;

    @Resource
    private DepartmentService departmentService;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
	@Autowired
	private PropertyPenltyService propertyPenltyService;
	
	@Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;
	
	@Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Transactional
    @Override
    public BillPaymentDetailDto getBillPaymentDetail(String oldPropNo, String propNo, Long orgId, Long userId,
            Date manualRcptDate, String billingMethod, String flatNo) {
        BillPaymentDetailDto billPayDto = null;
        List<ProvisionalAssesmentMstDto> assMstList = null;
        String logicalPropNo = null;
        // Organisation organisation = new Organisation(orgId);
        if (orgId == null) {
            assMstList = iProvisionalAssesmentMstService.getPropDetailByPropNoOnly(propNo);
            if (assMstList == null || assMstList.isEmpty()) {
                assMstList = assesmentMastService.getPropDetailByPropNoOnly(propNo);
            }
            if (assMstList != null && !assMstList.isEmpty()) {
                ProvisionalAssesmentMstDto assMst = assMstList.get(assMstList.size() - 1);
                orgId = assMst.getOrgId();
            }
        }
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        if (assMstList == null || assMstList.isEmpty()) {
            assMstList = iProvisionalAssesmentMstService.getPropDetailFromProvAssByPropNoOrOldPropNo(orgId,
                    propNo, oldPropNo);// Fetching latest Detail
            if (assMstList == null || assMstList.isEmpty()) {
                LookUp billDeletionInactive = null;
                try {
                    billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
                } catch (Exception e) {
                    LOGGER.error("No Prefix found for ENV - BDI");
                }
                if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
                    StringBuilder createLogicalPropNo = new StringBuilder();
                    createLogicalPropNo.append(propNo);
                    createLogicalPropNo.append("_");
                    createLogicalPropNo.append(flatNo);
                    logicalPropNo = createLogicalPropNo.toString();
                    assMstList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNoByFlatNo(orgId, propNo,
                            oldPropNo, logicalPropNo);
                } else {
                    assMstList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, propNo, oldPropNo);
                }
                if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL) && assMstList == null || assMstList.isEmpty() && StringUtils.isNotBlank(flatNo)) {
                	 assMstList = assesmentMastService.getPropDetailFromHouseNo(orgId, flatNo);
                }
                if (CollectionUtils.isEmpty(assMstList) && billDeletionInactive != null
                        && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                        && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
                    assMstList = assesmentMastService.getPropDetailByPropNoWithoutActiveCon(propNo);
                }
            }
        }

        if (assMstList != null && !assMstList.isEmpty()) {
        	
        	boolean checkWorkFlowInProcess = checkWorkFlowInProcess(assMstList);
            LookUp paymentRoundOffNotReq = null;
            try {
                paymentRoundOffNotReq = CommonMasterUtility.getValueFromPrefixLookUp("PRN", "ENV", organisation);
            } catch (Exception e) {
                LOGGER.error("No Prefix found for ENV - PNR");
            }
            LookUp skdclEnv = null;
            try {
                skdclEnv = CommonMasterUtility.getValueFromPrefixLookUp("SKDCL", "ENV", organisation);
            } catch (Exception e) {
                LOGGER.error("No Prefix found for ENV - SKDCL");
            }

            String assCheck = CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.SVP, organisation)
                    .getLookUpCode();
            billPayDto = new BillPaymentDetailDto();
            ProvisionalAssesmentMstDto assMst = assMstList.get(assMstList.size() - 1);
            if (checkWorkFlowInProcess) {
				billPayDto.setErrorMsg("Change In Assessment against property number " + assMst.getAssNo()
				+ " is already in progress ");
			}
            ServiceMaster service = serviceMaster.getServiceMaster(assMst.getSmServiceId(), orgId);
            // Defect# 128914 Property Bill payment In payment receipt service name show data entry suit
            if (service != null && service.getSmShortdesc() != null) {
                assMst.setServiceShortCode(service.getSmShortdesc());
            }
            billPayDto.setAssmtDto(assMst);
            // T#109280
            setWardZoneDesc(assMst, organisation);
            setTotalARV(assMst);
            // 97303 - To check special notice due date is passed or not
            if (Utility.isEnvPrefixAvailable(organisation, "SND") && service != null
                    && MainetConstants.Property.SAS.equals(service.getSmShortdesc())) {
                if (assMst.getSplNotDueDate() == null) {
                    billPayDto.setErrorMsg(
                            ApplicationSession.getInstance().getMessage("property.specialNotDueDaysNotPresentValidn"));
                    return billPayDto;
                } else if (!new Date().after(assMst.getSplNotDueDate())) {
                    billPayDto.setErrorMsg(
                            ApplicationSession.getInstance().getMessage("property.specialNotDueDaysValidn"));
                    return billPayDto;
                }               
            }
            //

            List<TbBillMas> billMasList = null;
            LookUp receiptForSkdcl = null;
            try {
                receiptForSkdcl = CommonMasterUtility.getValueFromPrefixLookUp("RPF", "ENV", organisation);

            } catch (Exception e) {
                LOGGER.error("No prefix found for ENV(RNR)");
            }

            // int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(assMst.getAssNo(), orgId);

            if (assCheck.equals(MainetConstants.Y_FLAG)) {
                Long finYearId = iFinancialYear.getFinanceYearId(new Date());

                boolean assessment = selfAssessmentService.CheckForAssesmentFiledForCurrYear(orgId,
                        propNo, oldPropNo, "A", finYearId);
				if (manualRcptDate == null && !assessment) {
					billPayDto.setRedirectCheck(MainetConstants.Y_FLAG);
					billPayDto.setErrorMsg("Assessment is not done for current financial year");
					return billPayDto;
				}
            }
            String lookUpCode = CommonMasterUtility
                    .getDefaultValueByOrg(MainetConstants.Property.propPref.APP, organisation)
                    .getLookUpCode();
            Long deptId=null;
            if(service!=null && service.getTbDepartment()!=null){
             deptId = service.getTbDepartment().getDpDeptid();
               billPayDto.setServiceId(service.getSmServiceId());
          }

            billPayDto.setPropNo(assMst.getAssNo());
            billPayDto.setOldpropno(assMst.getAssOldpropno());
            billPayDto.setApplNo(assMst.getApmApplicationId());
            billPayDto.setDeptId(deptId);
            billPayDto.setOrgId(orgId);
           
            billPayDto.setAddress(assMst.getAssAddress());
            billPayDto.setPinCode(assMst.getAssPincode());
            billPayDto.setLocation(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
            billPayDto.setPartialAdvancePayStatus(lookUpCode);
            billPayDto.setPlotNo(assMst.getTppPlotNo());
            billPayDto.setUniquePropertyId(assMst.getUniquePropertyId());
            billPayDto.setNewHouseNo(assMst.getNewHouseNo());
            if (((!propNo.equals(MainetConstants.BLANK) && propNo != null)
                    || (!oldPropNo.equals(MainetConstants.BLANK) && oldPropNo != null))) {

                List<ProvisionalAssesmentDetailDto> proDetList = null;
                BigDecimal rvAmt = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
                if (assMst != null) {
                    proDetList = assMst.getProvisionalAssesmentDetailDtoList();
                    if (proDetList != null && !proDetList.isEmpty()) {
                        for (ProvisionalAssesmentDetailDto det : proDetList) {
                            if (det.getAssdRv() != null) {
                                rvAmt = rvAmt.add(new BigDecimal(det.getAssdRv()));
                            }
                        }
                    }
                }
                billPayDto.setPdRv(Double.valueOf(rvAmt.toString()));

            }

            setWardZoneId(billPayDto, assMst);
            List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMst.getProvisionalAssesmentOwnerDtlDtoList();
            getAllUniqueUsageType(billPayDto, assMst, organisation);// to print all usage in revenue receipt on revenue receipt
            List<String> ownerNameList = new ArrayList<>();
            StringBuilder ownerFullName = new StringBuilder();
            String mobileNo = null;
            String emailId = null;
            int ownerSize = 1;
            for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
                if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                    billPayDto.setPrimaryOwnerName(owner.getAssoOwnerName());
                    billPayDto.setPrimaryOwnerMobNo(owner.getAssoMobileno());
                    billPayDto.setOwnerDtlDto(owner);
                    if (StringUtils.isEmpty(ownerFullName.toString())) {
                        ownerFullName.append(owner.getAssoOwnerName());
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                            LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    organisation);
                            ownerFullName.append(reltaionLookUp.getDescLangFirst());
                        } else if (receiptForSkdcl == null) {
                            ownerFullName.append("Contact person - ");
                        }
                        if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            ownerFullName.append(owner.getAssoGuardianName());
                        }
                    } else {
                        ownerFullName.append(owner.getAssoOwnerName());
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                            LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    organisation);
                            ownerFullName.append(reltaionLookUp.getDescLangFirst());
                        } else if (receiptForSkdcl == null) {
                            ownerFullName.append("Contact person - ");
                        }
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        ownerFullName.append(owner.getAssoGuardianName());
                    }
                    if (ownerSize < ownerList.size()) {
                        ownerFullName.append("," + " ");
                    }
                    mobileNo = owner.getAssoMobileno();
                    emailId = owner.geteMail();
                    ownerSize = ownerSize + 1;
                } else {
                    ownerNameList.add(owner.getAssoOwnerName());
                    ownerFullName.append(owner.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    } else {
                        ownerFullName.append("Contact person - ");
                    }
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    ownerFullName.append(owner.getAssoGuardianName());
                    if (ownerSize < ownerList.size()) {
                        ownerFullName.append("," + " ");
                    }
                }
                owner.getAssoOwnerName();
            }
            billPayDto.setOwnerFullName(ownerFullName.toString());  // 95103
            if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
                mobileNo = assMst.getProvisionalAssesmentDetailDtoList().get(0).getOccupierMobNo();
                emailId = assMst.getProvisionalAssesmentDetailDtoList().get(0).getOccupierEmail();
            }
            billPayDto.setMobileNo(mobileNo);
            billPayDto.setEmailId(emailId);

            if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {

                // Form Main Bill table
                billMasList = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(assMst.getAssNo(), orgId,
                        flatNo);
                if (CollectionUtils.isEmpty(billMasList)) {
                    // From Provisional Bill Table
                    billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(assMst.getAssNo(), orgId);
                }
            } else {

                // Form Main Bill table
                billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(assMst.getAssNo(), orgId);
                if (CollectionUtils.isEmpty(billMasList) && !Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
                    // From Provisional Bill Table
                    billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(assMst.getAssNo(), orgId);
                }
            }

            if(CollectionUtils.isEmpty(billMasList)) {
            	Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            	int billExistByPropNoAndYearId = propertyMainBillService.getBillExistByPropNoAndYearId(assMst.getAssNo(), orgId, finYearId);
            	if(billExistByPropNoAndYearId > 0) {
                	billPayDto.setCurrentBillGenFlag(MainetConstants.FlagY);
                }else {
                	billPayDto.setCurrentBillGenFlag(MainetConstants.FlagN);
                }
            }
            if (billMasList != null && !billMasList.isEmpty()) {
            	String validateBillData = propertyMainBillService.ValidateBillData(billMasList);
            	if(StringUtils.isNotBlank(validateBillData)) {
            		LOGGER.error("Bill data issue for property no "+assMst.getAssNo()+" "+" Issues -> "+ validateBillData);
            		billPayDto = new BillPaymentDetailDto();
                	billPayDto.setErrorMsg("Bill data correction is required");
            		return billPayDto;
            	}
            	billPayDto.setBillMasList(billMasList);
                AtomicDouble totalArrearAmt = new AtomicDouble(0);
                AtomicDouble totalCurrentAmt = new AtomicDouble(0);
                AtomicDouble totalRebateAmt = new AtomicDouble(0);

                double totalPenalty = 0;
                LookUp penalIntLookUp = null;
                try {
                    penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", organisation);
                } catch (Exception e) {
                    LOGGER.error("No Prefix found for ENV(PIT)");
                }
                if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
                        && StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
                    ApplicationContextProvider.getApplicationContext().getBean(PropertyBRMSService.class)
                            .fetchInterstRate(billMasList, organisation, deptId);
                    totalPenalty = billMasterCommonService.calculatePenaltyInterest(billMasList, organisation, deptId, "Y",
                            manualRcptDate, "N", null, userId);
                }else if(Utility.isEnvPrefixAvailable(organisation, "PSCL")) {
                	List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
                	ApplicationContextProvider.getApplicationContext().getBean(PropertyBRMSService.class)
                    .fetchInterstRate(billMasList, organisation, deptId);
                	propertyService.updatingPendingReceiptData(null, orgId, userId, 0, deptId, organisation, assMst, billMasList, provBillList, false,null);
                	billMasterCommonService.calculateInterestForPrayagRaj(billMasList, organisation, deptId, "Y", manualRcptDate, userId,billMasList,null);
                }

                billPayDto.setTotalPenalty(new java.math.BigDecimal(totalPenalty).setScale(2, RoundingMode.HALF_DOWN));
                boolean validate = true;
                TbBillMas billMas = billMasList.get(billMasList.size() - 1);// Fetching latest Bill
                if (manualRcptDate != null) {
                    TbServiceReceiptMasEntity receipt = null;
                    if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
                        receipt = iReceiptEntryService.getLatestReceiptDetailByAddRefNoAndFlatNo(orgId,
                                assMst.getAssNo(), flatNo);
                    } else {
                        receipt = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(orgId,
                                assMst.getAssNo());
                    }

                    if ((Utility.compareDate(manualRcptDate, billMas.getBmBilldt()))
                            || (receipt != null && (receipt.getRmDate().after(manualRcptDate)))) {
                        validate = false;
                        billPayDto = null;
                    }
                }
                if (validate) {
                	Date currDate = new Date();
                    if (manualRcptDate != null) {
                        currDate = manualRcptDate;
                    }
                    
                	Long finYearId = iFinancialYear.getFinanceYearId(currDate);
                    billPayDto.setBillNo(billMas.getBmIdno());
                    billPayDto.setBillDate(billMas.getBmBilldt());
                    billPayDto.setLastBillYear(Utility.getFinancialYear(billMas.getBmFromdt(), billMas.getBmTodt()));
                    if(billMas.getBmYear().equals(finYearId)) {
                    	billPayDto.setCurrentBillGenFlag(MainetConstants.FlagY);
                    }else {
                    	billPayDto.setCurrentBillGenFlag(MainetConstants.FlagN);
                    }
                    if (billMas.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {

                        // propertyBRMSService.fetchInterstRate(billMasList, org, deptId);// calculating interest rate through
                        // BRMS
                        // billMasterCommonService.calculateInterest(billMasList, org, deptId, MainetConstants.Y_FLAG,
                        // manualRcptDate);
                        
                        FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
                                .getBean(IFinancialYearService.class).getFinincialYearsById(finYearId, orgId);
                        Long noOfBillsPaidInCurYear = receiptRepository
                                .getCountOfbillPaidBetweenCurFinYear(propNo, currentFinYearDto.getFaFromDate(),
                                        currentFinYearDto.getFaToDate(), orgId);
                        if (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear <= 0) {
                            LookUp billDeletionInactive = null;
                            try {
                                billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",
                                        organisation);
                            } catch (Exception e) {
                                LOGGER.error("No prefix found for ENV - BDI");
                            }
                            if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                                    && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
                                noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYearAdvance(propNo,
                                        currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
                            }
                        }
                        BillDisplayDto surChargeDto = propertyService.calculateSurcharge(organisation, deptId, billMasList,
                                propNo,
                                MainetConstants.Property.SURCHARGE,
                                finYearId, manualRcptDate);
                        propertyBRMSService.fetchAllChargesApplAtReceipt(assMst.getAssNo(), billMasList, organisation, deptId,
                                null, userId,null);

                        List<BillDisplayDto> rebateDtoList = null;
                        List<BillDisplayDto> rebateDtoListNew = new ArrayList<BillDisplayDto>();
                        double totalAmt = 0d;
                        LookUp roundOff = null;
                        try {
                            roundOff = CommonMasterUtility.getValueFromPrefixLookUp("NR", "RND", organisation);

                        } catch (Exception e) {

                        }
                        LookUp rebate = null;
                        try {
                            rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", organisation);

                        } catch (Exception e) {
                            LOGGER.error("No prefix found for ENV(RBT)");
                        }
                        String paymentMethodType = null;
                        Long rebateReceivedCount = 0L;
                        if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
                                && StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
                            paymentMethodType = billMasterCommonService.getFullPaymentOrHalfPayRebate(billMasList, organisation);
                            rebateReceivedCount = getRebateReceivedCount(propNo, flatNo, currDate, currentFinYearDto,
                                    organisation, deptId);
                        }
                        if (roundOff != null && roundOff.getOtherField() != null
                                && StringUtils.equals(roundOff.getOtherField(), "RF")) {
                            if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
                                    && StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
                                    && billMasterCommonService.checkRebateAppl(billMasList, organisation)
                                    && rebateReceivedCount <= 0
                                    && billMasList.get(billMasList.size() - 1).getBmYear().equals(finYearId))
                                    || (rebate == null && (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear == 0)
                                            && billMasList.get(billMasList.size() - 1).getBmLastRcptamt() == 0) || (rebate == null && Utility.isEnvPrefixAvailable(organisation, "PSCL"))) {
                                rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                                        organisation, billPayDto.getDeptId(), manualRcptDate, paymentMethodType);
                                Long iterator = 1l;
                                double checkAmt = 0d;
                                double rebateAmount = 0.0;
                                for (BillDisplayDto billDisplayDto : rebateDtoList) {
                                    rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
                                }
                                for (BillDisplayDto rebateDto : rebateDtoList) {
                                    BillDisplayDto newRebateDto = new BillDisplayDto();
                                    double roundedRebateAmt = Math.round(rebateAmount);
                                    double correctTax = 0;
                                    if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
    									correctTax = rebateDto.getTotalTaxAmt().doubleValue();
    								}else {
    									correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
    								}
                                    double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
                                    checkAmt += roundedTax;
                                    if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && rebateDtoList.size() == iterator) {
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
                                    newRebateDto.setTotalTaxAmt(java.math.BigDecimal.valueOf(correctTax));
                                    rebateDtoListNew.add(newRebateDto);
                                }
                            }

                            totalAmt = propertyService.getTotalPaybaleAmount(billMasList, rebateDtoListNew, surChargeDto);
                        } else if (roundOff != null && roundOff.getOtherField() != null
                                && StringUtils.equals(roundOff.getOtherField(), "NF")) {
                            if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
                                    && StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
                                    && billMasterCommonService.checkRebateAppl(billMasList, organisation)
                                    && rebateReceivedCount <= 0
                                    && billMasList.get(billMasList.size() - 1).getBmYear().equals(finYearId))
                                    || (rebate == null && (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear == 0)
                                            && billMasList.get(billMasList.size() - 1).getBmLastRcptamt() == 0) || (rebate == null && Utility.isEnvPrefixAvailable(organisation, "PSCL"))) {
                                rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                                        organisation, billPayDto.getDeptId(), manualRcptDate, paymentMethodType);
                            }

                            totalAmt = propertyService.getTotalPaybaleAmount(billMasList, rebateDtoList, surChargeDto);
                        } else {
                            if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
                                    && StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
                                    && billMasterCommonService.checkRebateAppl(billMasList, organisation)
                                    && rebateReceivedCount <= 0
                                    && billMasList.get(billMasList.size() - 1).getBmYear().equals(finYearId))
                                    || (rebate == null && (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear == 0)
                                            && billMasList.get(billMasList.size() - 1).getBmLastRcptamt() == 0) || (rebate == null && Utility.isEnvPrefixAvailable(organisation, "PSCL"))) {
                                rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                                        organisation, billPayDto.getDeptId(), manualRcptDate, paymentMethodType);
                            }

                            totalAmt = propertyService.getTotalPaybaleAmount(billMasList, rebateDtoList, surChargeDto);
                        }

                        Date secondSemStartDate = getSecondSemStartDate(propNo, flatNo, currDate, currentFinYearDto, organisation,
                                deptId);
                        if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
                                && StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
                                && billMasterCommonService.checkRebateAppl(billMasList, organisation)
                                && StringUtils.isNotBlank(paymentMethodType)
                                && StringUtils.equals(paymentMethodType, "Full") && rebateReceivedCount <= 0
                                && Utility.compareDate(currDate, secondSemStartDate)
                                && billMasList.get(billMasList.size() - 1).getBmYear().equals(finYearId)) {

                            List<BillDisplayDto> halfRebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                                    organisation, billPayDto.getDeptId(), manualRcptDate, "Half");
                            double rebateAmount = 0;
                            if (CollectionUtils.isNotEmpty(halfRebateDtoList)) {
                                for (BillDisplayDto billDisplayDto : halfRebateDtoList) {
                                    rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
                                }
                                billPayDto.setHalfPaymentRebate(Math.ceil(rebateAmount));
                            }
                        }
                        // Defect #34189

                        if (paymentRoundOffNotReq != null
                                && StringUtils.equals(MainetConstants.FlagY, paymentRoundOffNotReq.getOtherField())) {
                            billPayDto.setTotalPayableAmt(Math.ceil(totalAmt + totalPenalty));
                        } else {
                            billPayDto.setTotalPayableAmt(Math.round(totalAmt + totalPenalty));
                        }

						setTaxDetailInBillDetail(billMas, organisation, deptId, MainetConstants.FlagP);
                        List<BillDisplayDto> billDisList = propertyService.getTaxListForBillPayDisplay(billMas, organisation,
                                deptId);
                        billDisList.forEach(billDis -> {
                            totalArrearAmt.addAndGet(billDis.getArrearsTaxAmt().doubleValue());
                            totalCurrentAmt.addAndGet(billDis.getCurrentYearTaxAmt().doubleValue());
                        });
                        if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                            rebateDtoList.forEach(rebateDto -> {
                                totalRebateAmt.addAndGet(rebateDto.getCurrentYearTaxAmt().doubleValue());
                            });
                        }
                        // Defect #34189
                        // if (rebateDto != null) {
                        // billDisList.add(rebateDto);
                        // }
                        if (surChargeDto != null) {
                            billPayDto.setTotalSubcharge(surChargeDto.getCurrentYearTaxAmt().doubleValue());
                            billDisList.add(surChargeDto);
                        }
                        if (rebate == null && CollectionUtils.isNotEmpty(rebateDtoList)) {
                            billDisList.addAll(rebateDtoList);
                        }
                        billPayDto.setBillDisList(billDisList);
                    }

                    if(StringUtils.isNotBlank(flatNo)) {
                    	billPayDto.setAdvanceAmt(asExecssAmtService.getAdvanceAmountByFlatNo(propNo, flatNo, orgId));
                    }else {
                    	billPayDto.setAdvanceAmt(asExecssAmtService.getAdvanceAmount(propNo, orgId));
                    }
                    billPayDto.setTotalArrearAmt(
                            new java.math.BigDecimal(Math.round(totalArrearAmt.doubleValue())));
                    billPayDto.setTotalCurrentAmt(
                            new java.math.BigDecimal(Math.round(totalCurrentAmt.doubleValue())));
                    billPayDto
                            .setTotalBalAmt(new java.math.BigDecimal(Math.round(totalArrearAmt.doubleValue() + totalCurrentAmt.doubleValue()))
                                    );
                    if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
                    	 billPayDto.setTotalRebate(
                                 new java.math.BigDecimal(Math.ceil(totalRebateAmt.doubleValue())));
                    }else {
                    	 billPayDto.setTotalRebate(
                                 new java.math.BigDecimal(totalRebateAmt.doubleValue()).setScale(2, RoundingMode.HALF_DOWN));
                    }
                    billPayDto.setHalfOutstandingAmount(Math.round(
                            billMasterCommonService.getHalfPayableOutstanding(billMasList, organisation,MainetConstants.FlagY) + (totalPenalty / 2)));
                    billPayDto.setActualRebate(Math.ceil(billPayDto.getTotalRebate().doubleValue()));
                    billPayDto.setFullOutstandingAmount(Math.round(
                            billMasterCommonService.getFullPayableOutstanding(billMasList, organisation,MainetConstants.FlagY) + (totalPenalty)));
                    billPayDto.setBalanceOutstandingAmount(Math.round(
                            billMasterCommonService.getBalanceOutstanding(billMasList, organisation) + (totalPenalty)));
                    
                    billPayDto.setActualFullOutstandingAmount(Math.ceil(
                            billMasterCommonService.getFullPayableOutstanding(billMasList, organisation,null) + (totalPenalty)));
                    billPayDto.setActualHalfOutstandingAmount(Math.ceil(
                            billMasterCommonService.getHalfPayableOutstanding(billMasList, organisation,null) + (totalPenalty / 2)));
                }
            } else if (manualRcptDate != null) {
                TbServiceReceiptMasEntity receipt = null;
                if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
                    receipt = iReceiptEntryService.getLatestReceiptDetailByAddRefNoAndFlatNo(orgId,
                            assMst.getAssNo(), flatNo);
                } else {
                    receipt = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(orgId,
                            assMst.getAssNo());
                }
                if ((receipt != null && (receipt.getRmDate().after(manualRcptDate)))) {
                    billPayDto = null;
                }
            }
        }else {
        	//D#153751
        	billPayDto = new BillPaymentDetailDto();
        	billPayDto.setErrorMsg("Please enter valid property no");
        }
        return billPayDto;

    }

    // Task #109280
    private void setTotalARV(ProvisionalAssesmentMstDto assMst) {
        List<Double> totalARV = new ArrayList<Double>();
        List<ProvisionalAssesmentDetailDto> detailDtoList = assMst.getProvisionalAssesmentDetailDtoList();
        for (ProvisionalAssesmentDetailDto dto : detailDtoList) {
            if (dto.getAssdRv() != null) {
                totalARV.add(dto.getAssdRv());
            }
        }
        if (CollectionUtils.isNotEmpty(totalARV)) {
            Double arv = totalARV.stream().mapToDouble(Double::doubleValue).sum();
            assMst.setTotalArv(arv);
        } else {
            assMst.setTotalArv(0.0);
        }
    }

    // Task #109280
    private void setWardZoneDesc(ProvisionalAssesmentMstDto assMst, Organisation org) {
        if (assMst.getAssWard1() != null) {
            assMst.setAssWardDesc1(CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard1(), org).getDescLangFirst());
        }

        if (assMst.getAssWard2() != null) {
            assMst.setAssWardDesc2(CommonMasterUtility
                    .getHierarchicalLookUp(assMst.getAssWard2(), org).getDescLangFirst());
        }

        if (assMst.getAssWard3() != null) {
            assMst.setAssWardDesc3(CommonMasterUtility
                    .getHierarchicalLookUp(assMst.getAssWard3(), org).getDescLangFirst());
        }

        if (assMst.getAssWard4() != null) {
            assMst.setAssWardDesc4(CommonMasterUtility
                    .getHierarchicalLookUp(assMst.getAssWard4(), org).getDescLangFirst());
        }

        if (assMst.getAssWard5() != null) {
            assMst.setAssWardDesc5(CommonMasterUtility
                    .getHierarchicalLookUp(assMst.getAssWard5(), org).getDescLangFirst());
        }

    }

    private void getAllUniqueUsageType(BillPaymentDetailDto billPayDto, ProvisionalAssesmentMstDto assMst, Organisation org) {
        assMst.getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.setProAssdUsagetypeDesc(
                    CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1(), org).getDescLangFirst());
            if (billPayDto.getUsageType1() == null) {
                billPayDto.setUsageType1(det.getProAssdUsagetypeDesc());
            } else if (!billPayDto.getUsageType1().contains(det.getProAssdUsagetypeDesc())) {
                billPayDto.setUsageType1(billPayDto.getUsageType1() + "," + det.getProAssdUsagetypeDesc());
            }
        });
    }

    private void setWardZoneId(BillPaymentDetailDto detailDto, ProvisionalAssesmentMstDto popMas) {
        detailDto.setWard1(popMas.getAssWard1());
        detailDto.setWard2(popMas.getAssWard2());
        detailDto.setWard3(popMas.getAssWard3());
        detailDto.setWard4(popMas.getAssWard4());
        detailDto.setWard5(popMas.getAssWard5());
    }

    /*
     * @Override
     * @Transactional public Long updateBillMasterAmountPaidMobilePayment(final Long propNo, final Double amount, final Long
     * orgId, final CommonChallanDTO offline) { final Organisation org = new Organisation(); TbServiceReceiptMasEntity receipt =
     * null; org.setOrgid(orgId); final List<TbBillMas> billMasList =
     * iProvisionalBillService.getProvisionalBillMasByPropertyNo(propNo.toString(), orgId); if (billMasList != null &&
     * !billMasList.isEmpty()) { TbBillMas billMas = billMasList.get(billMasList.size() - 1); if
     * (billMas.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) { boolean intRecal =
     * propertyService.isInterestRecalculationRequire(org, offline.getDeptId(), billMas); if (intRecal) {
     * propertyBRMSService.fetchInterstRate(billMasList, org, offline.getDeptId());// calculating interest rate // through BRMS
     * propertyService.calculateInterest(billMasList, org, offline.getDeptId(), MainetConstants.Y_FLAG); } final Map<Long, Double>
     * details = new HashMap<>(0); final Map<Long, Long> billDetails = new HashMap<>(0);
     * billMasterCommonService.updateBillData(billMasList, amount, details, billDetails, org);
     * iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, offline.getUserId(), propNo.toString(), null);
     * offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED); offline.setDocumentUploaded(false);
     * offline.setBillDetIds(billDetails); offline.setFeeIds(details); offline.setAmountToPay(amount.toString()); receipt =
     * iChallanService.insertInReceiptMaster(offline); if (billMas.getExcessAmount() > 0) {// Excess Payment
     * asExecssAmtService.addEntryInExcessAmt(orgId, offline.getDeptId(), propNo.toString(), billMas.getExcessAmount(),
     * receipt.getRmRcptid(), offline.getUserId()); } } else {// Payment is pure Advance Payment(No dues are pending)
     * offline.setChallanServiceType("A"); offline.setDocumentUploaded(false); offline.setAmountToPay(amount.toString()); receipt
     * = iChallanService.insertInReceiptMaster(offline); asExecssAmtService.addEntryInExcessAmt(orgId, offline.getDeptId(),
     * propNo.toString(), amount, receipt.getRmRcptid(), offline.getUserId()); } return receipt.getRmRcptno(); } return null; }
     */

    @Override
    @Transactional
	public void setTaxDetailInBillDetail(TbBillMas billMas, Organisation org, Long deptId, String flag) {
        final HashMap<Long, TbTaxMas> taxDetail = new HashMap<>(0);
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, org);
        final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT,
                PrefixConstants.NewWaterServiceConstants.CAA, org);
        final List<TbTaxMas> taxesMaster = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
                chargeApplicableAt.getLookUpId());
        final List<TbTaxMas> taxesMasterBillReceipt = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
                chargeApplicableAtBillReceipt.getLookUpId());
        taxesMaster.addAll(taxesMasterBillReceipt);
        
        try {
			LookUp demandNotice = null;
			try {
				demandNotice = CommonMasterUtility.getValueFromPrefixLookUp(
		                "DN",
		                PrefixConstants.NewWaterServiceConstants.CAA, org);
			}catch (Exception e) {
				LOGGER.error("No prefix founf for CAA(CDC)");
			}
			if (demandNotice != null) {
				final List<TbTaxMas> taxesMasterBillDemand = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
						demandNotice.getLookUpId());
				if (CollectionUtils.isNotEmpty(taxesMasterBillDemand)) {
					taxesMaster.addAll(taxesMasterBillDemand);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred while fetching prefix" + e.getMessage());
		}
        
		if (StringUtils.equals(flag, MainetConstants.FlagP)) {
			try {
				LookUp disHonor = null;
				try {
					disHonor = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.CDC,
							PrefixConstants.NewWaterServiceConstants.CAA, org);
				}catch (Exception e) {
					LOGGER.error("No prefix founf for CAA(CDC)");
				}
				if (disHonor != null) {
					List<TbTaxMas> taxMasDishonorCharges = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(),
							deptId, disHonor.getLookUpId());
					if (CollectionUtils.isNotEmpty(taxMasDishonorCharges)) {
						taxesMaster.addAll(taxMasDishonorCharges);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception occurred while fetching prefix" + e.getMessage());
			}
		}
        if ((taxesMaster != null) && !taxesMaster.isEmpty()) {
            for (final TbTaxMas tax : taxesMaster) {
                taxDetail.put(tax.getTaxId(), tax);
            }
        }
        billMas.getTbWtBillDet().forEach(billDet -> {
            TbTaxMas tax = taxDetail.get(billDet.getTaxId());
            if(tax != null) {
            	if(tax.getTaxDesc() != null)
            		billDet.setTaxDesc(tax.getTaxDesc());
            	if(tax.getTaxDisplaySeq() != null)
            		billDet.setDisplaySeq(tax.getTaxDisplaySeq());            	            	
            }
        });
    }

    @Override
    public CommonChallanDTO getBillDetails(CommonChallanDTO offline) {
        BillPaymentDetailDto billPayDto = getBillPaymentDetail(offline.getPropNoConnNoEstateNoL(), offline.getUniquePrimaryId(),
                offline.getOrgId(),
                offline.getUserId(), null, null, null);
        offline.setAmountToPay(Double.toString(billPayDto.getTotalPayableAmt()));
        offline.setUserId(MainetConstants.Property.UserId);
        offline.setOrgId(billPayDto.getOrgId());
        offline.setLangId(1);
        offline.setFeeIds(new HashMap<>(0));
        offline.setBillDetIds(new HashMap<>(0));
        offline.setApplicantName(billPayDto.getOwnerFullName());
        offline.setApplNo(billPayDto.getApplNo());
        offline.setApplicantAddress(billPayDto.getAddress());
        offline.setUniquePrimaryId(billPayDto.getPropNo());
        offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
        offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
        if (billPayDto.getWard3() != null)
            offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
        offline.setPlotNo(billPayDto.getPlotNo());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        offline.setPdRv(billPayDto.getPdRv());
        offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
        offline.setPropNoConnNoEstateNoL(ApplicationSession.getInstance().getMessage("propertydetails.PropertyNo."));
        offline.setEmpType(null);
        offline.setUsageType(billPayDto.getUsageType1());
        offline.setReferenceNo(billPayDto.getOldpropno());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        return offline;
    }

    @Override
    public Long getCountByApplNoOrLegacyNo(String applNo, String legacyApplNo, Long orgId) {
        Long count = 0L;
        if (StringUtils.isNotEmpty(applNo)) {
            count = provisionalAssesmentMstRepository.getCountBypropNo(applNo, orgId);
            if (count == 0) {
                count = assesmentMstRepository.getCountBypropNo(applNo, orgId);
            }
        } else {
            count = provisionalAssesmentMstRepository.getCountByOldPropNo(legacyApplNo, orgId);
            if (count == 0) {
                count = assesmentMstRepository.getCountByOldPropNo(legacyApplNo, orgId);
            }
        }
        return count;
    }

    @Override
    public Long getRebateReceivedCount(String propNo, String flatNo, Date paymentDate, FinancialYear currentYear,
            Organisation org, Long deptId) {
        Long rebateReceivedCount = 0L;
        List<LookUp> rebateSemPeriods = null;
        String narration = ApplicationSession.getInstance().getMessage("receipt.message.rmNarration3") +
                departmentService.fetchDepartmentDescById(deptId);
        try {
            rebateSemPeriods = CommonMasterUtility.getLookUps("RSP", org);
        } catch (Exception e) {
            LOGGER.error("No Prefix found for RSP");
        }

        if (CollectionUtils.isNotEmpty(rebateSemPeriods)) {
            List<LookUp> firstSemPeriod = rebateSemPeriods.stream()
                    .filter(searchDto -> StringUtils.isNotBlank(searchDto.getLookUpCode())
                            && searchDto.getLookUpCode().equals("FSP"))
                    .collect(Collectors.toList());
            List<LookUp> secondSemPeriod = rebateSemPeriods.stream()
                    .filter(searchDto -> StringUtils.isNotBlank(searchDto.getLookUpCode())
                            && searchDto.getLookUpCode().equals("SSP"))
                    .collect(Collectors.toList());
            Timestamp currFinYearTimeStamp = new Timestamp(currentYear.getFaFromDate().getTime());
            Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime());
            LocalDate convertFinStartDateToLocalDate = currentFinYearDate.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            Date firstSemEndDate = Date
                    .from(convertFinStartDateToLocalDate.plusDays(Long.valueOf(firstSemPeriod.get(0).getOtherField()))
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date secondSemStartDate = Date.from(
                    convertFinStartDateToLocalDate.plusDays(Long.valueOf(firstSemPeriod.get(0).getOtherField()) + 1)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date secondSemEndDate = Date
                    .from(convertFinStartDateToLocalDate.plusDays(Long.valueOf(secondSemPeriod.get(0).getOtherField()))
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (paymentDate.compareTo(currentYear.getFaFromDate()) >= 0
                    && paymentDate.compareTo(firstSemEndDate) <= 0) {
                rebateReceivedCount = getRebareReceiptCount(propNo, currentYear.getFaFromDate(), firstSemEndDate,
                        org.getOrgid(), narration, flatNo);
            } else if (paymentDate.compareTo(secondSemStartDate) >= 0 && paymentDate.compareTo(secondSemEndDate) <= 0) {
                rebateReceivedCount = getRebareReceiptCount(propNo, secondSemStartDate, secondSemEndDate,
                        org.getOrgid(), narration, flatNo);
            } else {
                rebateReceivedCount = 1L;
            }
        }
        return rebateReceivedCount;
    }

    private Long getRebareReceiptCount(String propNo, Date fromDate, Date toDate, Long orgId, String narration, String flatNo) {
        if (StringUtils.isNotBlank(flatNo)) {
            return receiptRepository.getCountOfbillPaidBetweenSelectedPeriodByFlatNo(propNo,
                    fromDate, toDate, orgId, narration, flatNo);
        } else {
            return receiptRepository.getCountOfbillPaidBetweenSelectedPeriod(propNo, fromDate,
                    toDate, orgId, narration);
        }
    }

    @Override
    public Date getSecondSemStartDate(String propNo, String flatNo, Date paymentDate, FinancialYear currentYear,
            Organisation org, Long deptId) {
        List<LookUp> rebateSemPeriods = null;
        Date secondSemStartDate = null;
        try {
            rebateSemPeriods = CommonMasterUtility.getLookUps("RSP", org);
        } catch (Exception e) {
            LOGGER.error("No Prefix found for RSP");
        }

        if (CollectionUtils.isNotEmpty(rebateSemPeriods)) {
            List<LookUp> firstSemPeriod = rebateSemPeriods.stream()
                    .filter(searchDto -> StringUtils.isNotBlank(searchDto.getLookUpCode())
                            && searchDto.getLookUpCode().equals("FSP"))
                    .collect(Collectors.toList());
            Timestamp currFinYearTimeStamp = new Timestamp(currentYear.getFaFromDate().getTime());
            Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime());
            LocalDate convertFinStartDateToLocalDate = currentFinYearDate.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            secondSemStartDate = Date.from(
                    convertFinStartDateToLocalDate.plusDays(Long.valueOf(firstSemPeriod.get(0).getOtherField()) + 1)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());

        }
        return secondSemStartDate;
    }
	    	
	@Transactional
	@Override
	public BillPaymentDetailDto getBillPaymentDetailForGrp(PropertyBillPaymentDto dto, List<String> propertyNoList,
			Date manualRcptDate, Long userId, Organisation organisation) {
		BillPaymentDetailDto billPayDto = new BillPaymentDetailDto();
		billPayDto.setOrgId(organisation.getOrgid());
		List<BillDisplayDto> billDisList = new ArrayList<>();
		double totalPayableAmt = 0;
		double currentBalanceAmount = 0;
		double arrearBalanceAmount = 0;
		LookUp GipLookup = CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(dto.getParentPropNo()),
				organisation.getOrgid());
		Long finId = iFinancialYearService.getFinanceYearId(new Date());
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
				MainetConstants.FlagA);
		List<Object[]> consolidatedBillForGrp = iAssessmentMastDao.getConsolidatedBillsForGrpProperty(finId,
				organisation.getOrgid(), dto.getParentPropNo());
		BillDisplayDto billDisplayDto;
		 String lookUpCode = CommonMasterUtility
                 .getDefaultValueByOrg(MainetConstants.Property.propPref.APP, organisation)
                 .getLookUpCode();
		 ServiceMaster service = serviceMaster.getServiceByShortName("SAS", organisation.getOrgid());
		 billPayDto.setPartialAdvancePayStatus(lookUpCode);
		 billPayDto.setDeptId(deptId);
		 billPayDto.setServiceId(service.getSmServiceId());
		for (Object[] obj : consolidatedBillForGrp) {
			billDisplayDto = new BillDisplayDto();
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(((Integer) obj[13]).longValue(),
					organisation);
			if (lookUp != null && "PTR".equals(lookUp.getLookUpCode())) {
				currentBalanceAmount = ((BigDecimal) obj[7]).doubleValue();
				arrearBalanceAmount = ((BigDecimal) obj[8]).doubleValue();
			}
			billDisplayDto.setTaxDesc((String) obj[10]);
			billDisplayDto.setDisplaySeq(((Double) obj[9]).longValue());
			billDisplayDto.setArrearsTaxAmt((BigDecimal) obj[8]);
			billDisplayDto.setCurrentYearTaxAmt((BigDecimal) obj[7]);
			billDisplayDto.setTotalTaxAmt(((BigDecimal) obj[8]).add((BigDecimal) obj[7]));
			billDisList.add(billDisplayDto);
			totalPayableAmt += billDisplayDto.getTotalTaxAmt().doubleValue();
		}
				
		// Calculate Surcharge separately
		Double penalty = propertyPenltyService.getPropertyPenltyByGroupPropNos(propertyNoList, finId,
				organisation.getOrgid());
		if (penalty != null) {
			LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.propPref.RCPT,
					MainetConstants.Property.propPref.CAA, organisation);
			LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(MainetConstants.Property.SURCHARGE,
					PrefixConstants.LookUpPrefix.TAC, 2, organisation.getOrgid());

			List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(
					organisation.getOrgid(), deptId, taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
			TbTaxMasEntity tax = null;
			if (taxList != null && !taxList.isEmpty()) {
				tax = taxList.get(0);
				BillDisplayDto billdto = new BillDisplayDto();
				billdto.setTaxDesc(tax.getTaxDesc());
				billdto.setDisplaySeq(tax.getTaxDisplaySeq());
				billdto.setTaxId(tax.getTaxId());
				billdto.setTaxCategoryId(tax.getTaxCategory1());
				billdto.setTotalTaxAmt(BigDecimal.valueOf(Math.round(penalty)));
				billdto.setCurrentYearTaxAmt(BigDecimal.valueOf(Math.round(penalty)));
				billdto.setArrearsTaxAmt(BigDecimal.valueOf(0));
				billdto.setFinYearId(finId);
				billPayDto.setTotalSubcharge(penalty);
				billDisList.add(billdto);
				totalPayableAmt += billdto.getTotalTaxAmt().doubleValue();
			}
		}		
		//Rebate calculation passing any one bill master list from child property list
		List<TbBillMas> billMasList = propertyMainBillService
				.fetchNotPaidBillForAssessment(propertyNoList.get(propertyNoList.size() - 1), organisation.getOrgid());
		if (CollectionUtils.isEmpty(billMasList)) {
			billMasList = iProvisionalBillService.fetchNotPaidBillForProAssessment(
					propertyNoList.get(propertyNoList.size() - 1), organisation.getOrgid());
		}
		Long finYearId = iFinancialYear.getFinanceYearId(new Date());
		FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
                .getBean(IFinancialYearService.class).getFinincialYearsById(finYearId, organisation.getOrgid());
		Long noOfBillsPaidInCurYear = receiptRepository
                .getCountOfbillPaidBetweenCurFinYear(String.valueOf(GipLookup.getLookUpId()), currentFinYearDto.getFaFromDate(),
                        currentFinYearDto.getFaToDate(), organisation.getOrgid());
		List<BillDisplayDto> billDisRebateList  = null;
		if((noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear == 0)) {
			billDisRebateList = propertyBRMSService.fetchEarlyPayRebateRateForGoupProperty(billMasList,
					organisation, deptId, null, null, currentBalanceAmount, 0, arrearBalanceAmount, 0);
		}
		if (CollectionUtils.isNotEmpty(billDisRebateList)) {
			billDisList.addAll(billDisRebateList);
			BillDisplayDto rebateDto = billDisRebateList.get(billDisRebateList.size() - 1);
			billPayDto.setTotalPayableAmt(totalPayableAmt - rebateDto.getTotalTaxAmt().doubleValue());
		} else {
			billPayDto.setTotalPayableAmt(totalPayableAmt);
		}
		billPayDto.setBillDisList(billDisList);
		
		
		billPayDto.setParentPropName(GipLookup.getLookUpDesc());
		billPayDto.setParentPropNo(String.valueOf(GipLookup.getLookUpId()));
		billPayDto.setOwnerFullName(GipLookup.getLookUpDesc());
		return billPayDto;		
	}
		
	private boolean checkWorkFlowInProcess(List<ProvisionalAssesmentMstDto> assMstList) {
		boolean workflowProcess = false;
		Organisation org = new Organisation();
		org.setOrgid(assMstList.get(0).getOrgId());
		if (CollectionUtils.isNotEmpty(assMstList)
				&& Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)
				&& Utility.isEnvPrefixAvailable(org, "BPA")) {
			List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
			if (!StringUtils.isEmpty(assMstList.get(0).getAssNo())) {
				provAssesMastList = provisionalAssesmentMstService
						.getPropDetailByPropNoOnly(assMstList.get(0).getAssNo());
			}
			if (CollectionUtils.isNotEmpty(provAssesMastList)) {
				ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
				if (provAssesMstDto != null && provAssesMstDto.getApmApplicationId() != null) {
					WorkflowRequest workflowRequest = workflowReqService.getWorkflowRequestByAppIdOrRefId(
							provAssesMstDto.getApmApplicationId(), null,
							org.getOrgid());
					if (workflowRequest != null
							&& MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
						workflowProcess = true;
					}
				}
			}

		}
		return workflowProcess;
	}
}
