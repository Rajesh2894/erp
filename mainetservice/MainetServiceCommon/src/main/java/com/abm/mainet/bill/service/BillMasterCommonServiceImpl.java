package com.abm.mainet.bill.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.repository.AdjustmentBillDetailMappingRepository;
import com.abm.mainet.cfc.challan.dao.IChallanDAO;
import com.abm.mainet.cfc.challan.domain.AdjustmentBillDetailMappingEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentDetailEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostListDTO;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class BillMasterCommonServiceImpl implements BillMasterCommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillMasterCommonServiceImpl.class);

    @Resource
    private IChallanDAO iChallanDAO;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private AuditService auditService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private IChallanService iChallanService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AdjustmentEntryService adjustmentEntryService;

    @Resource
    private AdjustmentBillDetailMappingRepository adjustmentBillDetailMappingRepository;
    
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private TbFinancialyearService financialyearService;

    private final String constantNO = MainetConstants.Common_Constant.NO;
    private final String constantYES = MainetConstants.Common_Constant.YES;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService# calculateInterestAndArrears(java.util.LinkedList)
     */
    @Override
    public void calculateInterestAndArrears(final List<TbBillMas> listBill) {
        final Organisation org = new Organisation();
        org.setOrgid(listBill.get(0).getOrgid());

        TbBillMas lastBillMas = null;
        for (final TbBillMas currBillMas : listBill) {
            double amount = 0d;
            if (lastBillMas != null) {
                if (UtilityService.compareDateField(lastBillMas.getBmDuedate(),
                        currBillMas.getBmBilldt())) {// if bill date is greater
                    amount += lastBillMas.getBmTotalBalAmount() + lastBillMas.getBmTotalArrearsWithoutInt();
                    currBillMas.setBmTotalArrearsWithoutInt(amount);
                }
            }
            if (currBillMas.getBmTotalArrearsWithoutInt() == 0d || lastBillMas == null) {
                currBillMas.setBmToatlInt(currBillMas.getBmToatlInt());
            } else {

                final Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(currBillMas.getBmBilldt());

                final Calendar startCalendar1 = Calendar.getInstance();
                startCalendar1.setTime(lastBillMas.getBmBilldt());

                final Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(lastBillMas.getBmDuedate());

                final int diffYear = startCalendar.get(Calendar.YEAR)
                        - endCalendar.get(Calendar.YEAR);
                final int diffMonth = ((diffYear * 12)
                        + startCalendar.get(Calendar.MONTH))
                        - endCalendar.get(Calendar.MONTH);

                final int diffYear1 = endCalendar.get(Calendar.YEAR)
                        - startCalendar1.get(Calendar.YEAR);
                final int diffMonth1 = ((diffYear1 * 12)
                        + endCalendar.get(Calendar.MONTH))
                        - startCalendar1.get(Calendar.MONTH);
                currBillMas.setBmToatlInt(Math.round((currBillMas.getBmTotalArrearsWithoutInt()
                        * diffMonth * currBillMas.getBmIntValue())
                        + (lastBillMas
                                .getBmTotalArrearsWithoutInt()
                                * diffMonth1 * lastBillMas
                                        .getBmIntValue())));
                if (!isLastDayOfMonth(lastBillMas.getBmDuedate())) {
                    currBillMas.setBmToatlInt(Math.round((lastBillMas.getBmTotalBalAmount() * lastBillMas.getBmIntValue()) +
                            currBillMas.getBmToatlInt()));
                }
            }
            if (lastBillMas != null) {// setting CumIntArrears
                currBillMas.setBmTotalCumIntArrears(
                        lastBillMas.getBmTotalCumIntArrears()
                                + currBillMas.getBmToatlInt());
            } else {
                currBillMas.setBmTotalCumIntArrears(
                        currBillMas.getBmToatlInt());
            }

            currBillMas.setBmTotalArrears(  // setting TotalArrears
                    currBillMas.getBmTotalCumIntArrears()
                            + currBillMas.getBmTotalArrearsWithoutInt());
            if (constantYES.equals(currBillMas.getBmPaidFlag())) {
                if (UtilityService.compareDateField(lastBillMas.getBmDuedate(),
                        currBillMas.getBmBilldt())) {
                    currBillMas.setBmTotalOutstanding(
                            currBillMas.getBmTotalArrears());
                } else {
                    currBillMas.setBmTotalOutstanding(
                            lastBillMas.getBmTotalArrears()
                                    + lastBillMas.getBmTotalBalAmount());
                }
            } else {
                currBillMas.setBmTotalOutstanding(
                        currBillMas.getBmTotalArrears()
                                + currBillMas.getBmTotalBalAmount());
            }
            currBillMas.setIntTo(new Date());
            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty() && (lastBillMas != null)) {
                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
                    for (final TbBillDet lastDet : lastBillMas.getTbWtBillDet()) {
                        if (det.getTaxId().equals(lastDet.getTaxId())) {
                            final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org)
                                    .getLookUpCode();
                            if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                                det.setBdPrvBalArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                            }
                        }
                    }
                }
            }
            lastBillMas = currBillMas;
        }
    }

    private boolean isLastDayOfMonth(final Date bmDuedate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(bmDuedate);
        final boolean isLastDay = calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
        return isLastDay;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService#processBillNewData (java.util.LinkedList)
     */
    @Override
    public void processBillNewData(final List<TbBillMas> billMasData,
            final String ccnNumber) {

        final ApplicationSession appSession = ApplicationSession.getInstance();
        if ((billMasData != null) && !billMasData.isEmpty()) {
            final TbBillMas billMasLast = billMasData.get(billMasData.size() - 1);
            final TbBillMas billMas = new TbBillMas();

            billMas.setBmNo(billMasLast.getBmNo());
            billMas.setCsIdn(billMasLast.getCsIdn());
            billMas.setBmYear((long) Utility.getCurrentYear());

            billMas.setBmRemarks(appSession.getMessage("bill.lbl.payment"));
            billMas.setBmFromdt(billMasLast.getBmFromdt());
            billMas.setBmTodt(billMasLast.getBmTodt());
            final LocalDate date = LocalDate.now();
            final Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            billMas.setBmDuedate(duedate);
            billMas.setBmBilldt(duedate);
            billMas.setBmTotalAmount(0d);
            billMas.setBmTotalBalAmount(0d);
            billMas.setBmIntValue(billMasLast.getBmIntValue());
            billMas.setBmPaidFlag(constantYES);
            billMas.setUserId(billMasLast.getUserId());
            billMas.setOrgid(billMasLast.getOrgid());
            billMas.setLmoddate(new Date());
            billMas.setLangId(billMasLast.getLangId());
            billMas.setLgIpMac(billMasLast.getLgIpMac());

            billMasData.add(billMas);

            calculateInterestAndArrears(billMasData);

        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService#updateBillData (java.util.LinkedList, double)
     */
    @Override
    public List<BillReceiptPostingDTO> updateBillData(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate) {
        List<BillReceiptPostingDTO> billPosting = new ArrayList<>();
        final int size = listBill.size(); //3
        final LocalDate date = LocalDate.now();
        Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(manualReceptDate != null) {
        	duedate = manualReceptDate;
        }
        if (details == null) {
            details = new HashMap<>(0);
        }
        if (billDetails == null) {
            billDetails = new HashMap<>(0);
        }
        BillReceiptPostingDTO billUpdate = null;
        final double amount = paidAmount;
        double detAmount = paidAmount;
        double rebateAmount = 0d;
        double totalDemand = 0d;
        double totalInterest = 0d;
        double totalPenlty = 0d;
        int count = 0;

        TbBillMas lastBillMas = null;
        List<TbBillDet> tbBillDet = null;
        // priority
        for (final TbBillMas bill : listBill) {
            count++;
            if (bill.getBmDuedate() != null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
                if (bill.getBmToatlRebate() > 0d) {

                    // This condition is failing in case af arrears. At any how we are checking condition while calling rebate ->
                    // by srikanth 77411

                    // final double totalAmountPayable = detAmount + bill.getBmToatlRebate();
                    rebateAmount = bill.getBmToatlRebate();
                    /*
                     * if (totalAmountPayable >= bill.getBmTotalOutstanding()) { paidAmount += bill.getBmToatlRebate();
                     * rebateAmount = bill.getBmToatlRebate(); }
                     */
                }
            }
            tbBillDet = new ArrayList<>(0);
            if ((bill.getTbWtBillDet() != null)
                    && !bill.getTbWtBillDet().isEmpty()) {
                for (final TbBillDet det : bill.getTbWtBillDet()) {
                    billUpdate = new BillReceiptPostingDTO();
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                    if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                        Double taxAmount = null;
                        Double value = null;
                        Double rebatePaid = null;
                        Double initialBalAmount = null;
                        initialBalAmount = det.getBdCurBalTaxamt();
                        billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
                        billUpdate.setTaxCategory(det.getTaxCategory());
                        billUpdate.setBillDate(bill.getBmBilldt());
                        billUpdate.setBmIdNo(bill.getBmNo());
                        billUpdate.setBillNO(bill.getBmNo());
                        billUpdate.setTaxCategoryCode(taxCode);
                        billUpdate.setTotalDetAmount(initialBalAmount);
                        billUpdate.setTaxId(det.getTaxId());
                        billUpdate.setDisplaySeq(det.getDisplaySeq());
                        billUpdate.setYearId(bill.getBmYear());
                        billUpdate.setBillFromDate(bill.getBmFromdt());
                        billUpdate.setBillToDate(bill.getBmTodt());

                        // billUpdate.setBillDetId(det.getBdBilldetid());
                        if (det.getBdBilldetid() == 0) {
                            billUpdate.setBillDetId(det.getDummyDetId());
                        } else {
                            billUpdate.setBillDetId(det.getBdBilldetid());
                        }
                        billUpdate.setYearId(bill.getBmYear());
                        if (bill.getBmIdno() == 0) {
                            billUpdate.setBillMasId(bill.getDummyMasId());
                        } else {
                            billUpdate.setBillMasId(bill.getBmIdno());

                        }

                        if (count != size) {
                            billUpdate.setArrearAmount(initialBalAmount);
                        }
                        billPosting.add(billUpdate);

                        // Defect Id -> 77411 done by srikanth
                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
                            List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
                            if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
                                rebateAmount = rebateAmount - taxvalueIdList.get(0);
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
                                det.setBdCurBalTaxamt(Math.abs(rebatePaid));
                                List<Double> detTaxIds = new ArrayList<Double>();
                                detTaxIds.add(taxvalueIdList.get(0));
                                detTaxIds.add(taxvalueIdList.get(1));
                                rebateDet.put(det.getBdBilldetid(), detTaxIds);
                                rebateDetails.add(rebateDet);
                            }
                        } else if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            List<Double> detTaxIds = new ArrayList<Double>();
                            detTaxIds.add(rebateAmount);
                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
                            if (rebateAmount >= 0d) {
                                rebatePaid = det.getBdCurBalTaxamt();
                                det.setBdCurBalTaxamt(0);
                            } else {
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
                            }
                            
                            rebateDet.put(det.getBdBilldetid(), detTaxIds);
                            rebateDetails.add(rebateDet);
                        }
                        if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                        	billUpdate.setYearId(bill.getBmYear());
                        	billUpdate.setBillFromDate(bill.getBmFromdt());
                            billUpdate.setBillToDate(bill.getBmTodt());
                            value = details.get(det.getTaxId());
                            detAmount = detAmount - det.getBdCurBalTaxamt();
                            if (value != null) {
                                if (detAmount >= 0d) {
                                    taxAmount = det.getBdCurBalTaxamt();
                                    value += (det.getBdCurBalTaxamt());
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            } else {
                                if (detAmount >= 0d) {
                                    value = det.getBdCurBalTaxamt();
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            }
                            details.put(det.getTaxId(), value);
                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
                            // billUpdate.setBillDetId(det.getBdBilldetid());
                            /*
                             * if (det.getBdBilldetid() == 0) { billUpdate.setBillDetId(det.getDummyDetId()); } else {
                             * billUpdate.setBillDetId(det.getBdBilldetid()); }
                             */
                            // billUpdate.setTaxId(det.getTaxId());
                            if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
                            	billUpdate.setTaxAmount(taxAmount);
                            }else {
                            	billUpdate.setTaxAmount(Math.round(taxAmount));
                            }

                            /*
                             * billUpdate.setYearId(bill.getBmYear()); if (bill.getBmIdno() == 0) {
                             * billUpdate.setBillMasId(bill.getDummyMasId()); } else { billUpdate.setBillMasId(bill.getBmIdno());
                             * }
                             */
                            /*
                             * billUpdate.setTaxCategory(det.getTaxCategory()); billUpdate.setBillDate(bill.getBmBilldt());
                             * billUpdate.setBmIdNo(bill.getBmNo()); billUpdate.setTotalDetAmount(initialBalAmount); if (count !=
                             * size) { billUpdate.setArrearAmount(initialBalAmount); } billPosting.add(billUpdate);
                             */
                        }
                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
                                if (det.getTaxId().equals(lastdet.getTaxId())) {
                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
                                }
                            }
                        }
                        tbBillDet.add(det);
                        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND) || taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
                            totalDemand += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                            totalInterest += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                            totalPenlty += det.getBdCurBalTaxamt();
                        }
                    }
                }
            }
            bill.setBmToatlInt(Math.abs(totalInterest));
            if (totalDemand > 0) {
                bill.setBmTotalBalAmount(Math.abs(totalDemand));
            } else {
                bill.setBmTotalBalAmount(0.0);
            }
            bill.setTotalPenalty(Math.abs(totalPenlty));
            totalDemand = 0d;
            totalInterest = 0d;
            totalPenlty = 0d;

            if (lastBillMas == null) {
                bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
            } else {
                bill.setBmTotalCumIntArrears(lastBillMas
                        .getBmTotalCumIntArrears() + bill.getBmToatlInt());
                TbTaxMas findById = tbTaxMasService.findById(bill.getTbWtBillDet().get(0).getTaxId(), org.getOrgid());
                String deptCode = null;
             	if(findById!=null) {
             		deptCode = departmentService.getDeptCode(findById.getDpDeptId());
             	}
                if (lastBillMas.getBmDuedate() != null && (UtilityService.compareDate(
                        lastBillMas.getBmDuedate(), bill.getBmBilldt()) || (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) 
                        		&& MainetConstants.WATER_DEPT.equals(deptCode)))) {
                    bill.setBmTotalArrearsWithoutInt(
                            lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
                }
            }
            bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
                    + bill.getBmTotalCumIntArrears());
            bill.setBmTotalOutstanding(bill.getBmTotalArrears()
                    + bill.getBmTotalBalAmount());
            if(checkIfWaterDeptAndSkdclOrg(bill, org)) {
            	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
                    bill.setBmPaidFlag(constantYES);
            	}
            }else if (bill.getBmTotalOutstanding() == 0d && bill.getTotalPenalty() == 0d) {// #12562
                bill.setBmPaidFlag(constantYES);
            }

            bill.setTbWtBillDet(tbBillDet);
            lastBillMas = bill;
        }
        // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
        // listBill.get(size - 1).setBmLastRcptamt(amount);
        listBill.get(size - 1).setBmLastRcptdt(new Date());
        listBill.get(size - 1).setExcessAmount(detAmount);
        return billPosting;
    }

    /**
     * @param orgnisation
     * @param langId
     * @param empId
     * @param penaltytaxDto
     * @param billDet
     */
    @Override
    public void setBillDetails(final Organisation orgnisation, final int langId,
            final Long empId, final BillTaxDTO penaltytaxDto, final TbBillDet billDet, String ipAddress) {
        billDet.setTaxId(penaltytaxDto.getTaxId());
        billDet.setTaxCategory(penaltytaxDto.getTaxCategory());
        billDet.setTaxSubCategory(penaltytaxDto.getTaxSubCategory());
        billDet.setCollSeq(penaltytaxDto.getTaxSequence());
        billDet.setOrgid(orgnisation.getOrgid());
        billDet.setUserId(empId);
        billDet.setLmoddate(new Date());
        billDet.setLangId(langId);
        billDet.setLgIpMac(ipAddress);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService#getAdvanceTaxId(long)
     */
    @Override
    public Long getAdvanceTaxId(final long orgid, final String module, final Long depmtId) {
        Long taxId = null;
        Long deptId = null;
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC,
                MainetConstants.NUMBERS.ONE,
                organisation);
        Long advanceId = null;
        if ((taxCategory != null) && !taxCategory.isEmpty()) {
            for (final LookUp lookupid : taxCategory) {
                if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                    advanceId = lookupid.getLookUpId();
                    break;
                }
            }
        }
        if (module != null) {
            deptId = departmentService.getDepartmentIdByDeptCode(
                    module, MainetConstants.STATUS.ACTIVE);
        } else {
            deptId = depmtId;
        }
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, organisation);
        final List<TbTaxMasEntity> taxMas = tbTaxMasService.getTaxMasterByTaxCategoryId(deptId, advanceId, orgid,
                chargeApplicableAt.getLookUpId());
        if ((taxMas != null) && !taxMas.isEmpty()) {
            taxId = taxMas.get(0).getTaxId();
        }
        return taxId;
    }

    @Override
    public void doVoucherPosting(final List<Long> uniqueNumber, final Organisation orgId,
            final String deptShortName, final Long empId, Long logLocId) {
        if (uniqueNumber != null && !uniqueNumber.isEmpty()) {
            taskExecutor.execute(() -> accountVoucherPosting(uniqueNumber, orgId, deptShortName,
                    empId, logLocId));
        }
    }

    /**
     * @param csIdn
     * @param orgId
     * @param deptShortName
     * @param finYearId
     * @param empId
     * @param logLocId
     */
    @Override
    public void accountVoucherPosting(final List<Long> uniqueNumber,
            final Organisation orgId, final String deptShortName,
            final Long empId, Long logLocId) {
        try {
            Long locationId = null;
            List<TbBillMas> bills = null;
            String activeFlag = null;
            final LookUp accountActive = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, orgId);
            if (accountActive != null) {
                activeFlag = accountActive.getDefaultVal();
            }
            final List<Long> billIds = new ArrayList<>(0);
            if (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) {

                final LookUp tdpPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.BILL_MASTER_COMMON.DMD_VALUE,
                        MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgId);

                final LookUp positiveAdjust = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.BILL_MASTER_COMMON.POSITIVE_ADJUST,
                        MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgId);

                final LookUp negativeAdjust = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.BILL_MASTER_COMMON.NEGATIVE_ADJUST,
                        MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgId);

                	bills = getBillMasterListByPrimaryKey(uniqueNumber, orgId.getOrgid(), deptShortName);
                
                if ((bills != null) && !bills.isEmpty()) {
                    List<VoucherPostDTO> accountPostingList = new ArrayList<>(0);
                    if (logLocId != null) {
                        final TbLocationMas locMas = iLocationMasService.findById(logLocId);
                        if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                            locationId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                        }
                    } else {
                        final TbLocationMas locMas = iLocationMasService
                                .findByLocationName(ApplicationSession.getInstance().getMessage("location.LocNameEng"),
                                        orgId.getOrgid());
                        if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                            locationId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                        }
                    }
                    final Department dept = departmentService.getDepartment(deptShortName, MainetConstants.STATUS.ACTIVE);
                    for (TbBillMas billMas : bills) {
                        billIds.add(billMas.getBmIdno());
                        accountPostingList.add(voucherDemandPosting(orgId, empId, locationId, tdpPrefix, dept, billMas,
                                positiveAdjust.getLookUpId(), negativeAdjust.getLookUpId()));
                    }
                    doVoucherPosting(accountPostingList, billIds, deptShortName);
                }
            }
        } catch (Exception e) {
            LOGGER.error("error while account posting for demand bills of department " + deptShortName, e);
        }
    }

    private void doVoucherPosting(List<VoucherPostDTO> accountPostingList, List<Long> billIds, String deptShortName) {
        if (accountPostingList != null && !accountPostingList.isEmpty()) {
            VoucherPostListDTO dto = new VoucherPostListDTO();
            dto.setVoucherdto(accountPostingList);

			final ResponseEntity<?> responseValidate = RestClient.callRestTemplateClient(dto,
                    ServiceEndpoints.ACCOUNT_POSTING_VALIDATE);
            if ((responseValidate != null) && (responseValidate.getStatusCode() == HttpStatus.OK)) {
                LOGGER.info("Account Voucher Posting validated successfully");
                final ResponseEntity<?> response = RestClient.callRestTemplateClient(dto,
                        ServiceEndpoints.ACCOUNT_POSTING);
                if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
                    updateAccountPostingFlag(billIds, constantYES, deptShortName);
                } else {
                    LOGGER.error(ApplicationSession.getInstance().getMessage("account.voucher.posting.failmsg")
                            + "dept " + deptShortName + " for Bill no:" + billIds.toString() + " "
                            + (response != null ? response.getBody() : ""));
                }
            } else {
                LOGGER.error("Account Voucher Posting Validation Failed due to :"
                        + (responseValidate != null ? responseValidate.getBody() : MainetConstants.BLANK));
            }
        }
    }

    private VoucherPostDTO voucherDemandPosting(final Organisation orgId, final Long empId, Long locationId,
            final LookUp tdpPrefix, final Department dept, TbBillMas billMas, long positiveAdjust, long negativeAdjust) {
        return prepareVoucherDTO(orgId, empId, locationId, tdpPrefix, dept, billMas, positiveAdjust, negativeAdjust);

    }

    private VoucherPostDTO prepareVoucherDTO(final Organisation orgId, final Long empId, Long locationId, final LookUp tdpPrefix,
            final Department dept, TbBillMas billMas, long positiveAdjust, long negativeAdjust) {
    	 VoucherPostDTO accountPosting;
         VoucherPostDetailDTO voucherDetail;
         String referenceNo = null;
         accountPosting = new VoucherPostDTO();
         accountPosting.setVoucherDetails(new ArrayList<VoucherPostDetailDTO>(0));
         if (billMas.getGrandTotal() < 0) {
             accountPosting.setVoucherSubTypeId(negativeAdjust);
         } else if (billMas.getGrandTotal() > 0) {
             accountPosting.setVoucherSubTypeId(positiveAdjust);
         }
         if (billMas.getBmYear() != null) {
             accountPosting.setVoucherSubTypeId(tdpPrefix.getLookUpId());
             accountPosting.setFinancialYearId(billMas.getBmYear());
             accountPosting.setTemplateType("FYW");
         } else {
             accountPosting.setTemplateType("PN");
             final LookUp dmpPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
                     MainetConstants.BILL_MASTER_COMMON.DMP,
                     MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgId);
             accountPosting.setVoucherSubTypeId(dmpPrefix.getLookUpId());
         }
         accountPosting.setFieldId(locationId);
         accountPosting.setVoucherDate(new Date());
         accountPosting.setVoucherReferenceDate(new Date());
         accountPosting.setDepartmentId(dept.getDpDeptid());
         referenceNo = billMas.getBmNo();
         accountPosting.setVoucherReferenceNo(referenceNo);// should be numeric
         accountPosting.setNarration(dept.getDpDeptdesc()
                 + ApplicationSession.getInstance().getMessage("bill.lbl.taxes") + referenceNo);
         accountPosting.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
         accountPosting.setOrgId(orgId.getOrgid());
         accountPosting.setCreatedBy(empId);
         accountPosting.setCreatedDate(new Date());
         accountPosting.setLangId(MainetConstants.ENGLISH);
         accountPosting.setLgIpMac(billMas.getLgIpMac());
         accountPosting.setPayModeId(CommonMasterUtility.getValueFromPrefixLookUp("T",
                 MainetConstants.PAY_PREFIX.PREFIX_VALUE, orgId).getLookUpId());
         for (final TbBillDet billDet : billMas.getTbWtBillDet()) {
             voucherDetail = new VoucherPostDetailDTO();
             voucherDetail.setVoucherAmount(BigDecimal.valueOf(billDet.getBdCurTaxamt()));
             final Long sacHeadId = tbTaxMasService.fetchSacHeadIdForReceiptDetByDemandClass(orgId.getOrgid(),
                     billDet.getTaxId(), MainetConstants.STATUS.ACTIVE, billDet.getTddTaxid());
             voucherDetail.setSacHeadId(sacHeadId);
             accountPosting.getVoucherDetails().add(voucherDetail);
         }
         return accountPosting;
    }

    private boolean updateAccountPostingFlag(final List<Long> billIds, final String flag, final String deptShortName) {
        boolean result = false;
        try {
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            final ApplicationSession appSession = ApplicationSession.getInstance();
            serviceClassName = messageSource.getMessage(
                    appSession.getMessage("bill.lbl.Bills") + deptShortName, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.updateAccountPostingFlag(billIds, flag);
        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in updating jv post flag in bill for account posting for department :"
                    + deptShortName + " and bill id's are:" + billIds.toString(), e);
        }
        return result;

    }

    private List<TbBillMas> getBillMasterListByPrimaryKey(final List<Long> uniqueNumber, final long orgid,
            final String deptShortName) {
        List<TbBillMas> result = null;
        try {
            BillGenerationService dynamicServiceInstance = null;
            String serviceClassName = null;
            serviceClassName = messageSource.getMessage(
                    "Bill." + deptShortName, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillGenerationService.class);
            result = dynamicServiceInstance.fetchListOfBillsByPrimaryKey(uniqueNumber, orgid);

        } catch (LinkageError | Exception e) {
            throw new FrameworkException("Exception in fetching bills for account posting for department :" + deptShortName, e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public TbTaxMasEntity getInterestTax(final Long orgid, final String deptShortName) {
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC,
                MainetConstants.NUMBERS.ONE,
                organisation);
        Long interestTax = null;
        if ((taxCategory != null) && !taxCategory.isEmpty()) {
            for (final LookUp lookupid : taxCategory) {
                if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                    interestTax = lookupid.getLookUpId();
                    break;
                }
            }
        }
        final Long deptId = departmentService.getDepartmentIdByDeptCode(
                deptShortName, MainetConstants.STATUS.ACTIVE);
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, organisation);
        final List<TbTaxMasEntity> taxMas = tbTaxMasService.getTaxMasterByTaxCategoryId(deptId, interestTax, orgid,
                chargeApplicableAt.getLookUpId());
        if ((taxMas != null) && !taxMas.isEmpty()) {
            return taxMas.get(0);
        }
        return null;
    }

    @Override
    public List<AdjustmentBillDetailMappingEntity> doAdjustmentAgainstBalanceAmount(final List<TbBillMas> listBill,
            final List<AdjustmentMasterEntity> adjustmentEntity) {
        final List<AdjustmentBillDetailMappingEntity> mappingList = new ArrayList<>(0);
        listBill.forEach(billMas -> {
            List<TbBillDet> previous = null;
            adjustmentEntity.forEach(adjMaster -> {
                final AtomicDouble amountAdjusted = new AtomicDouble();
                adjMaster.getAdjDetail().forEach(detail -> {
                    AdjustmentBillDetailMappingEntity mapping = null;
                    for (final TbBillDet det : billMas.getTbWtBillDet()) {
                        if (det.getTaxId().equals(detail.getTaxId()) && (detail.getAdjBalanceAmount() > 0d)) {
                            double adjusted = 0d;
                            if (constantNO.equals(adjMaster.getAdjType())) {
                                final double adjAmount = det.getBdCurBalTaxamt() - detail.getAdjBalanceAmount();
                                if (adjAmount >= 0d) {
                                    det.setBdCurBalTaxamt(adjAmount);
                                    adjusted += detail.getAdjBalanceAmount();
                                    amountAdjusted.addAndGet(detail.getAdjBalanceAmount());
                                    detail.setAdjAdjustedAmount(detail.getAdjAdjustedAmount() + detail.getAdjBalanceAmount());
                                    detail.setAdjBalanceAmount(0d);
                                    detail.setAdjAdjustedFlag(constantYES);
                                } else {
                                    adjusted += det.getBdCurBalTaxamt();
                                    amountAdjusted.addAndGet(det.getBdCurBalTaxamt());
                                    detail.setAdjAdjustedAmount(detail.getAdjAdjustedAmount() + det.getBdCurBalTaxamt());
                                    det.setBdCurBalTaxamt(0d);
                                    detail.setAdjBalanceAmount(Math.abs(adjAmount));
                                    detail.setAdjAdjustedFlag(constantNO);
                                }
                            } else {
                                adjusted += detail.getAdjBalanceAmount();
                                amountAdjusted.addAndGet(detail.getAdjBalanceAmount());
                                det.setBdCurBalTaxamt(detail.getAdjBalanceAmount() + det.getBdCurBalTaxamt());
                                detail.setAdjAdjustedAmount(detail.getAdjAdjustedAmount() + detail.getAdjBalanceAmount());
                                detail.setAdjBalanceAmount(0d);
                                detail.setAdjAdjustedFlag(constantYES);
                            }
                            mapping = new AdjustmentBillDetailMappingEntity();
                            mapping.setAdjAmount(adjusted);
                            mapping.setAdjId(detail.getAdjDetId());
                            mapping.setAdjbmId(det.getBdBilldetid());
                            mapping.setOrgId(adjMaster.getOrgId());
                            mapping.setDpDeptId(adjMaster.getDpDeptId());
                            mapping.setLgIpMac(adjMaster.getLgIpMac());
                            mapping.setCreatedBy(adjMaster.getCreatedBy());
                            mapping.setCreatedDate(new Date());
                            mapping.setTaxId(det.getTaxId());
                            mappingList.add(mapping);
                        }
                    }
                });
                if (amountAdjusted.doubleValue() > 0d) {
                    if (constantNO.equals(adjMaster.getAdjType())) {
                        final double adjAmt = billMas.getBmTotalBalAmount() - amountAdjusted.doubleValue();
                        if (adjAmt >= 0d) {
                            billMas.setBmTotalBalAmount(adjAmt);
                        } else {
                            billMas.setBmTotalBalAmount(0d);
                        }
                    } else {
                        billMas.setBmTotalBalAmount(billMas.getBmTotalBalAmount() + amountAdjusted.doubleValue());
                    }
                }
            });
            final List<TbBillDet> current = billMas.getTbWtBillDet();
            if ((current != null) && !current.isEmpty()) {
                for (final TbBillDet curdet : current) {
                    if (previous != null) {
                        for (final TbBillDet arrdet : previous) {
                            if (curdet.getTaxId().longValue() == arrdet.getTaxId().longValue()) {
                                curdet.setBdPrvBalArramt(arrdet.getBdPrvBalArramt() + arrdet.getBdCurBalTaxamt());
                                curdet.setBdPrvArramt(curdet.getBdPrvBalArramt());
                            }
                        }
                    }
                }
                previous = current;
            }
        });
        adjustmentEntryService.updateAdjustedAdjustmentData(adjustmentEntity);
        return mappingList;
    }

    @Override
    public void saveAndUpdateMappingTable(final List<TbBillDet> tbWtBillDet,
            final List<AdjustmentBillDetailMappingEntity> mappingEntity) {
        for (final AdjustmentBillDetailMappingEntity mapping : mappingEntity) {
            for (final TbBillDet billDetail : tbWtBillDet) {
                if (mapping.getTaxId().equals(billDetail.getTaxId())) {
                    mapping.setAdjbmId(billDetail.getBdBilldetid());
                }
            }
        }
        adjustmentBillDetailMappingRepository.save(mappingEntity);
    }

    @Override
    public List<TbBillMas> generateBillForDataEntry(List<TbBillMas> billMasList, Organisation org) {
        AtomicDouble totAmt = new AtomicDouble(0);

        AtomicDouble totInt = new AtomicDouble(0);
        AtomicDouble totPenlty = new AtomicDouble(0);
        List<TbBillMas> newBillMasList = new LinkedList<>();
        Long curYearId = financialyearService.getFinanciaYearIdByFromDate(new Date());
        billMasList.forEach(billMas -> {
            // totCurAmt is initialized inside becuse incase of partial month entries. It is taking provious month amount. Always
            // amount is greater than zero even actual amount is zero. Defect Id=33095
            AtomicDouble totCurAmt = new AtomicDouble(0);
            totAmt.getAndSet(0);
            totInt.getAndSet(0);
            totPenlty.getAndSet(0);
            AtomicBoolean isWaterDept = new AtomicBoolean(false);
            TbTaxMas findById = tbTaxMasService.findById(billMas.getTbWtBillDet().get(0).getTaxId(), org.getOrgid());
         	if(findById!=null) {
         		String deptCode = departmentService.getDeptCode(findById.getDpDeptId());
                 boolean flag= Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && MainetConstants.WATER_DEPT.equals(deptCode) ?
                 		true : false;
                 isWaterDept.set(flag);
         	}
            billMas.getTbWtBillDet().forEach(det -> {
                final String taxCode = CommonMasterUtility
                        .getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)
                        && !taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                    if (det.getBdCsmp() != null) {
                        totAmt.addAndGet(det.getBdCsmp().doubleValue());
                        det.setBdCurTaxamt(det.getBdCsmp().doubleValue());
                        det.setBdCurBalTaxamt(det.getBdCsmp().doubleValue());
                    }
                }
                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                    if (det.getBdCsmp() != null) {
                        totInt.addAndGet(det.getBdCsmp().doubleValue());
                        det.setBdCurTaxamt(det.getBdCsmp().doubleValue());
                        det.setBdCurBalTaxamt(det.getBdCsmp().doubleValue());
                    }
                }
              
                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                    if (det.getBdCsmp() != null) {
                        totPenlty.addAndGet(det.getBdCsmp().doubleValue());
                        det.setBdCurTaxamt(det.getBdCsmp().doubleValue());
                        det.setBdCurBalTaxamt(det.getBdCsmp().doubleValue());
                    }
                }
               
                if (det.getBdCsmp() != null) {
                    totCurAmt.addAndGet(det.getBdCsmp().doubleValue());
                }
            });

            if (totCurAmt.doubleValue() > 0) {
                // bill is current bill and total amt of current bill is zero then current bill will no t generate
                billMas.setBmTotalAmount(totAmt.doubleValue());
                billMas.setBmTotalBalAmount(totAmt.doubleValue());
                billMas.setBmToatlInt(totInt.doubleValue());
                billMas.setTotalPenalty(totPenlty.doubleValue());
                newBillMasList.add(billMas);
            }
        });

        setBillDetailForDataEntry(newBillMasList, 0d, null, null, org, null);
        return newBillMasList;
    }

    @Override
    public void setBillDetailForDataEntry(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, Double>> rebateDetails) {

        final LocalDate date = LocalDate.now();
        final Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BillReceiptPostingDTO billUpdate = null;
        final double amount = paidAmount;
        double detAmount = amount;
        double rebateAmount = 0d;
        TbBillMas lastBillMas = null;
        List<TbBillDet> tbWtBillDet = null;

        // priority
        for (final TbBillMas bill : listBill) {
            if (bill.getBmDuedate()!=null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
                if (bill.getBmToatlRebate() > 0d) {
                    final double totalAmountPayable = amount + bill.getBmToatlRebate();
                    if (totalAmountPayable >= bill.getBmTotalOutstanding()) {
                        paidAmount += bill.getBmToatlRebate();
                        rebateAmount = bill.getBmToatlRebate();
                    }
                }
            }
            if ((bill.getBmTotalBalAmount() > 0d)
                    && (paidAmount > 0d)) {
                paidAmount = paidAmount - bill.getBmTotalBalAmount();
                if (paidAmount < 0d) {
                    bill.setBmTotalBalAmount(Math.abs(paidAmount));
                } else {
                    bill.setBmTotalBalAmount(0);
                }
            }
            if ((bill.getBmToatlInt() > 0d) && (paidAmount > 0d)) {
                paidAmount = paidAmount - bill.getBmToatlInt();
                if (paidAmount < 0d) {
                    bill.setBmToatlInt(Math.abs(paidAmount));
                } else {
                    bill.setBmToatlInt(0);
                }
            }

            if ((bill.getTotalPenalty() > 0d)
                    && (paidAmount > 0d)) {
                paidAmount = paidAmount - bill.getTotalPenalty();
                if (paidAmount < 0d) {
                    bill.setTotalPenalty(Math.abs(paidAmount));
                } else {
                    bill.setTotalPenalty(0);
                }
            }
            if (lastBillMas == null) {
                bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
            } else {
                bill.setBmTotalCumIntArrears(lastBillMas
                        .getBmTotalCumIntArrears() + bill.getBmToatlInt());
                if (lastBillMas.getBmDuedate()!=null && UtilityService.compareDate(
                        lastBillMas.getBmDuedate(), bill.getBmBilldt())) {
                    bill.setBmTotalArrearsWithoutInt(
                            lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
                }
                bill.setBmActualArrearsAmt(
                        lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
            }

            bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
                    + bill.getBmTotalCumIntArrears());
            bill.setBmTotalOutstanding(bill.getBmTotalArrears()
                    + bill.getBmTotalBalAmount());

            /*
             * if (bill.getBmTotalOutstanding() == 0d) { bill.setBmPaidFlag(constantYES); }
             */
            tbWtBillDet = new ArrayList<>(0);
            if ((bill.getTbWtBillDet() != null)
                    && !bill.getTbWtBillDet().isEmpty()) {
                for (final TbBillDet det : bill.getTbWtBillDet()) {
                    billUpdate = new BillReceiptPostingDTO();
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                    boolean isWaterDept = false;
                    TbTaxMas findById = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
                 	if(findById!=null) {
                 		String deptCode = departmentService.getDeptCode(findById.getDpDeptId());
                        isWaterDept = Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && MainetConstants.WATER_DEPT.equals(deptCode) ?
                         		true : false;
                 	}
                    if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                        Double taxAmount = null;
                        Double value = null;
                        Double rebatePaid = null;
                        Double initialBalAmount = null;
                        initialBalAmount = det.getBdCurBalTaxamt();
                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            Map<Long, Double> rebateDet = new HashMap<>();
                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
                            if (rebateAmount >= 0d) {
                                rebatePaid = det.getBdCurBalTaxamt();
                                det.setBdCurBalTaxamt(0);
                            } else {
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
                            }
                            rebateDet.put(det.getBdBilldetid(), rebatePaid);
                            rebateDetails.add(rebateDet);
                        }
                        if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            value = details.get(det.getTaxId());
                            detAmount = detAmount - det.getBdCurBalTaxamt();
                            if (value != null) {
                                if (detAmount >= 0d) {
                                    taxAmount = det.getBdCurBalTaxamt();
                                    value += (det.getBdCurBalTaxamt());
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            } else {
                                if (detAmount >= 0d) {
                                    value = det.getBdCurBalTaxamt();
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            }
                            details.put(det.getTaxId(), value);
                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
                            billUpdate.setBillDetId(det.getBdBilldetid());
                            if (det.getBdBilldetid() == 0) {
                                billUpdate.setBillDetId(det.getDummyDetId());
                            } else {
                                billUpdate.setBillDetId(det.getBdBilldetid());
                            }
                            billUpdate.setTaxId(det.getTaxId());
                            billUpdate.setTaxAmount(taxAmount);
                            billUpdate.setYearId(bill.getBmYear());
                            if (bill.getBmIdno() == 0) {
                                billUpdate.setBillMasId(bill.getDummyMasId());
                            } else {
                                billUpdate.setBillMasId(bill.getBmIdno());

                            }
                            billUpdate.setTaxCategory(det.getTaxCategory());
                            billUpdate.setTotalDetAmount(initialBalAmount);
                        }
                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
                                if (det.getTaxId().equals(lastdet.getTaxId())) {
                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
                                    det.setBdPrvArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
                                }
                            }
                        }
                        tbWtBillDet.add(det);
                    }
                }
            }
            bill.setTbWtBillDet(tbWtBillDet);
            lastBillMas = bill;
        }
    }

    /*
     * calculate Interest Method-: Calculating each year interest till current Date on totalBalAmt(currentYearAmt) with different
     * interest rate and different interest calculation method for each schedule
     */
    @Override
    public void calculateInterest(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
            Date manualDate) {
        int billSize = listBill.size() - 1;
        int count = 1;
        int period = 0;
        Date toDate = null;
        Date fromDate = null;
        TbBillMas lastBillMas = null;
        Date tillDate = manualDate;
        if (manualDate == null) {
            tillDate = new Date();
        }
        for (final TbBillMas currBillMas : listBill) {
            if (currBillMas.getInterstCalMethod() != null) {
                if (count == listBill.size() || interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                    // this Condition for current bill to calculate interest till current date
                    toDate = tillDate;
                } else {
                    toDate = (tillDate.after(currBillMas.getBmTodt()) ? currBillMas.getBmTodt() : tillDate);
                    // other then current bill to calculate interest till bill due date
                }
                if (interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                    fromDate = currBillMas.getIntTo();
                } else {
                    fromDate = currBillMas.getBmDuedate();
                }

                // calculate period
                if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                    period = calculateYears(fromDate, toDate, org.getOrgid());

                } else if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                    period = calculateMonths(fromDate, toDate);
                }
                // calculate bill for currBillMas till that year only
                // ex. bill of 2016-17 interest from 14/05/2016(dueDate) to 31/03/2017 (schedule end date)only
                currBillMas.setBmToatlInt(Math.round((currBillMas.getBmTotalBalAmount()
                        * period * currBillMas.getBmIntValue()) + currBillMas.getBmToatlInt()));
                currBillMas.setIntFrom(currBillMas.getBmDuedate());
                currBillMas.setIntTo(tillDate);

                if (interestRecalculate.equals(MainetConstants.Property.INT_RECAL_NO)) {
                    // for loop contain Excluding currBillMas reaming following bill to calculate interest till current date
                    for (int i = count; i <= billSize; i++) {
                        TbBillMas billMas = listBill.get(i);
                        if (i == billSize) { // this Condition for current bill to calculate interest till current date
                            toDate = tillDate;
                        } else {
                            toDate = (tillDate.after(billMas.getBmTodt()) ? billMas.getBmTodt() : tillDate);
                            // other then current bill to calculate interest till bill due date
                            // Example In half yearly New Date= 1/06/2018 then second last schedule is current ongoing schedule
                        }
                        if (billMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                            period = calculateYears(billMas.getBmFromdt(), toDate, org.getOrgid());

                        } else if (billMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                            period = calculateMonths(billMas.getBmFromdt(), toDate);
                        }
                        currBillMas.setBmToatlInt(Math.round((currBillMas.getBmTotalBalAmount()
                                * period * billMas.getBmIntValue()) + currBillMas.getBmToatlInt()));
                    }
                }
                if (lastBillMas != null) {// setting CumIntArrears
                    currBillMas.setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
                } else {
                    currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
                }

                currBillMas.setBmTotalArrears(
                        Math.round(currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt()));
                currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());

                // setting interest into Bill Detail
                for (final TbBillDet billDet : currBillMas.getTbWtBillDet()) {
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
                            .getLookUpCode();
                    if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                        // There can be multiple interest(like monthly and Yearly)in one Bill because of tax carry forward
                        // so Copy interest into correct interest tax this condition will work
                        // code commented as discussed with Rajesh Sir and Apoorva
                        /*
                         * TbTaxMas tax = null; Long taxSubCatId = null; if (billDet.getTaxSubCategory() == null) { tax =
                         * tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(), org.getOrgid()); taxSubCatId =
                         * tax.getTaxCategory2(); } else { taxSubCatId = billDet.getTaxSubCategory(); } LookUp taxSubCat =
                         * CommonMasterUtility.getHierarchicalLookUp(taxSubCatId, org); if
                         * (currBillMas.getInterstCalMethod().equals(taxSubCat.getLookUpCode())) {
                         */
                        billDet.setBdCurTaxamt(currBillMas.getBmToatlInt());// for first bill master
                        billDet.setBdCurBalTaxamt(currBillMas.getBmToatlInt());// for first bill master

                        // }
                        if (lastBillMas != null) {
                            lastBillMas.getTbWtBillDet().parallelStream()
                                    .filter(lastBillDet -> billDet.getTaxId().toString()
                                            .equals(lastBillDet.getTaxId().toString()))
                                    .forEach(lastBillDet -> {
                                        billDet.setBdPrvArramt(lastBillDet.getBdCurTaxamt() + lastBillDet.getBdPrvArramt());
                                        billDet.setBdPrvBalArramt(lastBillDet.getBdCurTaxamt() + lastBillDet.getBdPrvArramt());
                                    });
                            
                        }
                    }
                }
                lastBillMas = currBillMas;
                count++;
            }
        }
    }

    /*
     * Calculate Month Between Two dates if fromDate is last date of that Month then that month does't count for interest
     * Calculation otherwise count, And months till toDate including toDate Month is added for interest Calculation Month For
     * Example. (30/9/2016,31/3/2017) return=6 (29/9/2016,31/3/2017) return=7
     */
    private int calculateMonths(Date fromDate, Date toDate) {
        int noOfMonth = 0;
        String fromdateStr = Utility.dateToString(fromDate);
        fromDate=Utility.stringToDate(fromdateStr);
        String todateStr = Utility.dateToString(toDate);
        toDate=Utility.stringToDate(todateStr);
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
            if (tillPayDay.getDays() > 0 && (frmDateLoc.getDayOfMonth() != frmDateLoc.lengthOfMonth()) ||
                    (toDateLoc.getDayOfMonth() < toDateLoc.lengthOfMonth() && tillPayDay.getDays() > 0)) {
                noOfMonth++;
            }
        }
        return noOfMonth;
    }

    private int calculateYears(Date fromDate, Date toDate, Long orgId) {
        return financialyearService.getCountOfFinYearBetwDates(fromDate, toDate, orgId);
    }

    /*
     * merge arrears of old unpaid bill and new bill of new year. BillMasList=merge list of old bill and new bill with order of
     * billFromDate
     */
    @Override
    public void updateArrearInCurrentBills(List<TbBillMas> billMasList) {
        TbBillMas previousBillMas = null;
        Organisation org = new Organisation();
        if(CollectionUtils.isNotEmpty(billMasList)) {
        	org.setOrgid(billMasList.get(0).getOrgid());
        }
        for (final TbBillMas currBillMas : billMasList) {
        	
        	//120052 - To reset previousBillMas in case of flat wise individual billing
			if (previousBillMas != null && StringUtils.isNotBlank(previousBillMas.getFlatNo())
					&& StringUtils.isNotBlank(currBillMas.getFlatNo()) && (!previousBillMas.getFlatNo().equals(currBillMas.getFlatNo()))) {
				previousBillMas=null;
			}  

            if (previousBillMas != null) {
                currBillMas.setBmTotalArrearsWithoutInt(Math.round(previousBillMas.getBmTotalBalAmount() + 
                		previousBillMas.getBmTotalArrearsWithoutInt()));
                currBillMas.setBmActualArrearsAmt(Math.round(previousBillMas.getBmTotalBalAmount() + 
                		previousBillMas.getBmTotalArrearsWithoutInt()));
            }

            if (previousBillMas != null) {

                currBillMas.setBmTotalCumIntArrears(Math.round(previousBillMas.getBmTotalCumIntArrears()
                	+ currBillMas.getBmToatlInt()));
            } else {
                currBillMas.setBmTotalCumIntArrears(Math.round(currBillMas.getBmToatlInt()));
            }

            currBillMas.setBmTotalArrears(Math.round(currBillMas.getBmTotalCumIntArrears()
                            + currBillMas.getBmTotalArrearsWithoutInt()));

            currBillMas.setBmTotalOutstanding(Math.round(currBillMas.getBmTotalArrears()
            	+ currBillMas.getBmTotalBalAmount()));
            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
                    && (previousBillMas != null)) {
                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
                    for (final TbBillDet lastDet : previousBillMas.getTbWtBillDet()) {
                        if (det.getTaxId().equals(lastDet.getTaxId())) {
                        	// Defect : 93933 -> No need to update actual arrear column at payment time
                            //det.setBdPrvArramt(lastDet.getBdCurTaxamt() + lastDet.getBdPrvArramt());
                        	if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
                        		 det.setBdPrvBalArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                        	}else {
                        		 det.setBdPrvBalArramt(Math.round(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt()));
                        	}
                        }
                    }
                }
            }
            previousBillMas = currBillMas;
        }
    }

    /*
     * merge arrears of old unpaid bill and new bill of new year. BillMasList=merge list of old bill and new bill with order of
     * billFromDate( with new bill detArrAmt and ArrBalAmt is same)
     */
    //// #12562 defect in property and water too
    @Override
    public void updateArrearInCurrentBillsForNewBillGenertaion(List<TbBillMas> billMasList) {
        TbBillMas previousBillMas = null;
        for (final TbBillMas currBillMas : billMasList) {
            if (previousBillMas != null) {
                currBillMas.setBmTotalArrearsWithoutInt(
                        previousBillMas.getBmTotalBalAmount() + previousBillMas.getBmTotalArrearsWithoutInt());
                currBillMas.setBmActualArrearsAmt(
                        previousBillMas.getBmTotalBalAmount() + previousBillMas.getBmTotalArrearsWithoutInt());
            }

            if (previousBillMas != null) {

                currBillMas.setBmTotalCumIntArrears(
                        previousBillMas.getBmTotalCumIntArrears()
                                + currBillMas.getBmToatlInt());
            } else {
                currBillMas.setBmTotalCumIntArrears(
                        currBillMas.getBmToatlInt());
            }

            currBillMas.setBmTotalArrears(
                    currBillMas.getBmTotalCumIntArrears()
                            + currBillMas.getBmTotalArrearsWithoutInt());

            currBillMas.setBmTotalOutstanding(
                    currBillMas.getBmTotalArrears()
                            + currBillMas.getBmTotalBalAmount());
            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
                    && (previousBillMas != null)) {
                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
                    for (final TbBillDet lastDet : previousBillMas.getTbWtBillDet()) {
                        if (det.getTaxId().equals(lastDet.getTaxId())) {
                            det.setBdPrvBalArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                            // previous year bill but generating with current bill
                            /*
                             * if ((previousBillMas.getCurrentBillFlag() != null &&
                             * previousBillMas.getCurrentBillFlag().equals(MainetConstants.Y_FLAG))) {//
                             * det.setArrAmtOfNewBill(lastDet.getArrAmtOfNewBill() + lastDet.getBdCurTaxamt()); } else {
                             * det.setArrAmtOfNewBill(lastDet.getArrAmtOfNewBill() + lastDet.getBdCurBalTaxamt()); }
                             */
                            if (currBillMas.getBmIdno() > 0) {
                                det.setBdPrvArramt(lastDet.getBdCurTaxamt() + lastDet.getBdPrvArramt());
                            } else {
                                // set bill detail in new bill
                                // because on revenue receipt payable amt and paid amt must be same without rebate
                                if ((previousBillMas.getCurrentBillFlag() != null
                                        && previousBillMas.getCurrentBillFlag().equals(MainetConstants.Y_FLAG))) {//
                                    det.setBdPrvArramt(lastDet.getBdCurTaxamt() + lastDet.getBdPrvBalArramt());
                                } else {
                                    det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
                                }
                            }
                        }
                    }
                }
            }
            previousBillMas = currBillMas;
        }
    }

    /**
     * This method is for aligarh specific interst calculation. Here calculating muitiple interest tax
     */
	@Override
	public void calculateMultipleInterest(List<TbBillMas> listBill, Organisation org, Long deptId,
			String interestRecalculate, Date manualDate) {

        int billSize = listBill.size() - 1;
        int count = 1;
        int period = 0;
        Date toDate = null;
        Date fromDate = null;
        TbBillMas lastBillMas = null;
        Date tillDate = manualDate;
        if (manualDate == null) {
            tillDate = new Date();
        }
        LookUp defaultInterest  = null;
     	try {
     		defaultInterest = CommonMasterUtility.getValueFromPrefixLookUp("DIP", "ENV", org);
     	}catch (Exception e) {
     		LOGGER.error("No Prefix found for ENV(DIP)");
 		}
        for (final TbBillMas currBillMas : listBill) {
        	
        	double totalInterest = 0;
        		   if (currBillMas.getInterstCalMethod() != null) {
        			   if (count == listBill.size() || interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                           // this Condition for current bill to calculate interest till current date
                           toDate = tillDate;
                       } else {
                           toDate = (tillDate.after(currBillMas.getBmTodt()) ? currBillMas.getBmTodt() : tillDate);                           
                           // other then current bill to calculate interest till bill due date
                       }
                       if (interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                           fromDate = currBillMas.getIntTo();
                       } else {
                    	   if(currBillMas.getBmDuedate() != null) {
                    		   fromDate = currBillMas.getBmDuedate();
                    	   }else {
                    		   fromDate = currBillMas.getIntTo();
                    	   }
                       }                       

                       // calculate period
                       if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                           period = calculateYears(fromDate, toDate, org.getOrgid());

                       } else if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                           period = calculateMonths(fromDate, toDate);
                       }
                       if(defaultInterest != null) {
                    	   period = Integer.valueOf(defaultInterest.getOtherField());
                       }
                       /**
                        * This condition is to make false if interest calculated already through revise form
                        *   StringUtils.isBlank(currBillMas.getWtLo3())
								|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN)
                        */
				if (((defaultInterest == null) || (Utility.comapreDates(tillDate, currBillMas.getBmBilldt())))
						&& (StringUtils.isBlank(currBillMas.getWtLo3())
								|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN))) {
			
        		   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
        			   final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
                               .getLookUpCode();
                       if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                    	   TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(), org.getOrgid());
                    	   //TbTaxMas intersetDependentTaxMas = tbTaxMasService.findById(intersetTaxMas.getParentCode(), org.getOrgid());
                    	   
                    	   List<TbBillDet> interestDependentTax = currBillMas.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode())).collect(Collectors.toList());
                    	   if(CollectionUtils.isNotEmpty(interestDependentTax)) {
                    		   double balanceAmount = 0;
                    		   if(defaultInterest == null) {
                    			    balanceAmount = interestDependentTax.get(0).getBdCurBalTaxamt() + interestDependentTax.get(0).getBdCurBalArramt();
                    		   }else {
                    			   balanceAmount = interestDependentTax.get(0).getBdPrvBalArramt();
                    		   }
                    		   
                        	   if(billDet.getBaseRate() != null && balanceAmount > 0) {
                        		   if(defaultInterest == null) {
                        			   billDet.setBdCurTaxamt(Math.round((balanceAmount
                                               * period * billDet.getBaseRate()) + billDet.getBdCurTaxamt()));// for first bill master
                                       billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                               * period * billDet.getBaseRate()) + billDet.getBdCurBalTaxamt()));// for first bill master
                        		   }else {
                        			   billDet.setBdCurTaxamt(Math.round((balanceAmount
                                               * period * billDet.getBaseRate())));// for first bill master
                                       billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                               * period * billDet.getBaseRate())));// for first bill master
                        		   }
                                   totalInterest = totalInterest + billDet.getBdCurTaxamt();
                        	   }
                    	   }

                           // }
                       }
                       
        		   }
        		   
        		   if (lastBillMas != null) {
        			   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
                       lastBillMas.getTbWtBillDet().parallelStream()
                               .filter(lastBillDet -> billDet.getTaxId().toString()
                                       .equals(lastBillDet.getTaxId().toString()))
                               .forEach(lastBillDet -> {
                                   billDet.setBdPrvArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                                   billDet.setBdPrvBalArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                               });

                   }
        		   }
                       }
        	
            
                // calculate bill for currBillMas till that year only
                // ex. bill of 2016-17 interest from 14/05/2016(dueDate) to 31/03/2017 (schedule end date)only
               // currBillMas.setBmToatlInt(totalInterest);
                currBillMas.setIntFrom(currBillMas.getBmDuedate());
                currBillMas.setIntTo(tillDate);

                if (StringUtils.isNotBlank(interestRecalculate) && interestRecalculate.equals(MainetConstants.Property.INT_RECAL_NO)) {
                	
                	double totInt = 0.0;
                    // for loop contain Excluding currBillMas reaming following bill to calculate interest till current date
                    for (int i = count; i <= billSize; i++) {
                        TbBillMas billMas = listBill.get(i);
                        if (i == billSize) { // this Condition for current bill to calculate interest till current date
                            toDate = tillDate;
                        } else {
                            toDate = (tillDate.after(billMas.getBmTodt()) ? billMas.getBmTodt() : tillDate);
                            // other then current bill to calculate interest till bill due date
                            // Example In half yearly New Date= 1/06/2018 then second last schedule is current ongoing schedule
                        }
                        if (StringUtils.isNotBlank(billMas.getInterstCalMethod()) && billMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                            period = calculateYears(billMas.getBmFromdt(), toDate, org.getOrgid());

                        } else if (StringUtils.isNotBlank(billMas.getInterstCalMethod()) && billMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                            period = calculateMonths(billMas.getBmFromdt(), toDate);
                        }
                        
                        if(defaultInterest != null) {
                     	   period = Integer.valueOf(defaultInterest.getOtherField());
                        }
						if (((defaultInterest == null) || (Utility.comapreDates(tillDate, currBillMas.getBmBilldt())))
								&& (StringUtils.isBlank(currBillMas.getWtLo3())
										|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN))) {
                        for (TbBillDet billDet : billMas.getTbWtBillDet()) {
              			   final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
                                     .getLookUpCode();
                             if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                          	   TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(), org.getOrgid());
                          	   
                          	   List<TbBillDet> interestDependentTax = currBillMas.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode())).collect(Collectors.toList());
                          	  if(CollectionUtils.isNotEmpty(interestDependentTax)) {
                          		  
                          	   double balanceAmount =0;
                          	 if(defaultInterest == null) {
                          		balanceAmount = interestDependentTax.get(0).getBdCurBalTaxamt() + interestDependentTax.get(0).getBdCurBalArramt();
                 		   }else {
                 			   balanceAmount = interestDependentTax.get(0).getBdPrvBalArramt();
                 		   }
                          	   double intValue = 0;
                          	   if(billDet.getBaseRate() != null) {
                          		 intValue = billDet.getBaseRate();
                          	   }else {
                          		 intValue = billMas.getBmIntValue();
                          	   }
                          	 if(billDet.getBaseRate() != null && balanceAmount > 0) {
                          	 if(defaultInterest == null) {
                          		billDet.setBdCurTaxamt(Math.round((balanceAmount
                                        * period * intValue) + billDet.getBdCurTaxamt()));// for first bill master
                                billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                        * period * intValue) + billDet.getBdCurBalTaxamt()));// for first bill master
                          	 }else {
                          		 billDet.setBdCurTaxamt(Math.round((balanceAmount
                                         * period * intValue)));// for first bill master
                                 billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                         * period * intValue)));// for first bill master
                          	 }
                          	  
                                 totInt = totInt + billDet.getBdCurTaxamt();
                          	  }
                             }
                             }
              		   }
                        
                        if (lastBillMas != null) {
             			   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
                            lastBillMas.getTbWtBillDet().parallelStream()
                                    .filter(lastBillDet -> billDet.getTaxId().toString()
                                            .equals(lastBillDet.getTaxId().toString()))
                                    .forEach(lastBillDet -> {
                                        billDet.setBdPrvArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                                        billDet.setBdPrvBalArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                                    });

                        }
             		   }
                        
                        }
                        
                       
                    }
                }
                currBillMas.setBmToatlInt(Math.round((totalInterest+ currBillMas.getBmToatlInt())));
                if (lastBillMas != null) {// setting CumIntArrears
                    currBillMas.setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
                } else {
                    currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
                }

                currBillMas.setBmTotalArrears(
                        Math.round(currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt()));
                currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());
               
                lastBillMas = currBillMas;
                count++;
        	  }
            }
        }

	@Override
	public double calculatePenaltyInterest(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
			Date manualDate, String billGen,String paymentFlag, Long userId) {
		TbBillMas lastBillMas = listBill.get(listBill.size() - 1);
		double intOnarrear = 0;
		double intOnCur = 0;
		double intOnCurrentYear = 0;
		double totalInt = 0;
		boolean interestTaxexist = false;
		Date currDate = new Date();
		if(manualDate != null) {
			currDate = manualDate;
		}
		AtomicDouble arrearAmount = new AtomicDouble(0);
		AtomicDouble currentAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBill = new AtomicDouble(0);
		AtomicDouble totalOutStanding = new AtomicDouble(0);
		boolean compoundedIntApplicable = false;
		if (lastBillMas != null) {
		if (lastBillMas.getIntTo() != null && lastBillMas.getBmTodt() != null) {
		if (Utility.compareDate(lastBillMas.getIntTo(), lastBillMas.getBmTodt()) && Utility.compareDate(lastBillMas.getBmTodt(), currDate)) {
			compoundedIntApplicable = true;
		  }
		 }
		int arrearPeriod = 0;
		int currentFirstSemPeriod = 0;
		int currentSecondSemPeriod = 0;
		LookUp penalRoundOff  = null;
    	try {
    		penalRoundOff = CommonMasterUtility.getValueFromPrefixLookUp("PMR", "ENV", org);
    	}catch (Exception e) {
    		LOGGER.info("No Prefix found for ENV(PMR)");
		}
    	LookUp calculatePenaltyOnCurrBill  = null;
    	try {
    		calculatePenaltyOnCurrBill = CommonMasterUtility.getValueFromPrefixLookUp("CPC", "ENV", org);
    	}catch (Exception e) {
    		LOGGER.info("No Prefix found for ENV(CPC)");
		}
    	boolean penalRoundOffReq = false;
    	boolean calculatePenaltyOnCurrBillFlag = false;
    	if(calculatePenaltyOnCurrBill != null && StringUtils.equals(MainetConstants.FlagY,calculatePenaltyOnCurrBill.getOtherField())) {
    		calculatePenaltyOnCurrBillFlag = true;
    	}
    	if(penalRoundOff != null && StringUtils.isNotBlank(penalRoundOff.getOtherField()) && StringUtils.equals(penalRoundOff.getOtherField(), MainetConstants.FlagY)) {
    		penalRoundOffReq = true;
    	}
  	
		lastBillMas.getTbWtBillDet().forEach(billDet -> {
			if(lastBillMas.getIntTo() != null && lastBillMas.getBmTodt() != null && Utility.compareDate(lastBillMas.getIntTo(), lastBillMas.getBmTodt())) {
				arrearAmount.addAndGet(billDet.getBdPrvBalArramt());
				currentAmount.addAndGet(billDet.getBdCurBalTaxamt());
				actualCurrentBill.addAndGet(billDet.getBdCurTaxamt());
				totalOutStanding.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
			}else {
				arrearAmount.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
				totalOutStanding.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
			}
		});
		double currentFirstSemAmount = actualCurrentBill.doubleValue() / 2;
		double currentSecondSemAmount = actualCurrentBill.doubleValue();
		double currPaidBill = actualCurrentBill.doubleValue() - currentAmount.doubleValue();
		if(currPaidBill > 0) {
			currentFirstSemAmount = currentFirstSemAmount - currPaidBill;
			if(currentFirstSemAmount < 0) {
				currentFirstSemAmount = 0;
			}
			currentSecondSemAmount = currentSecondSemAmount - currPaidBill;
		}
		if (StringUtils.equals(billGen, MainetConstants.FlagY)) {

		} else {
			if(Utility.comapreDates(lastBillMas.getIntTo(), currDate)) {
				arrearPeriod = 0;
			}else {
				if(compoundedIntApplicable) {
					arrearPeriod = calculateMonths(lastBillMas.getIntTo(), lastBillMas.getBmTodt());
				}else {
					if (lastBillMas.getIntTo() != null)
					arrearPeriod = calculateMonths(lastBillMas.getIntTo(), currDate);
				}
					
			}
		}
		
		intOnarrear = getIntAmount(arrearAmount.doubleValue(), arrearPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
		
		Date firstSemDueDate = getCurrentFirstHalfDueDate(lastBillMas.getBillDistrDate(), org);
		Date secondSemDueDate = getCurrentSecondHalfDueDate(org);
		if(lastBillMas.getBillDistrDate() != null && lastBillMas.getIntTo() != null  && Utility.compareDate(firstSemDueDate, lastBillMas.getIntTo())) {
			firstSemDueDate = lastBillMas.getIntTo();
		}
		if(lastBillMas.getIntTo() != null  && Utility.compareDate(secondSemDueDate, lastBillMas.getIntTo())) {
			secondSemDueDate = lastBillMas.getIntTo();
		}
		double intOnFirstSem = 0;
		if (firstSemDueDate != null && secondSemDueDate != null) {
			if(Utility.comapreDates(firstSemDueDate, new Date())) {
				currentFirstSemPeriod = 0;
			}else {
				if(Utility.compareDate(new Date(), secondSemDueDate)) {
					if(Utility.compareDate(firstSemDueDate, new Date())) {
						currentFirstSemPeriod = calculateMonths(firstSemDueDate, new Date());
					}
				}else {
					currentFirstSemPeriod = calculateMonths(firstSemDueDate, secondSemDueDate);
				}
			}
			
			 intOnFirstSem = getIntAmount(currentFirstSemAmount, currentFirstSemPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
			
		}
		if ((secondSemDueDate != null && Utility.compareDate(secondSemDueDate, currDate))
				&& (calculatePenaltyOnCurrBillFlag
						|| (!calculatePenaltyOnCurrBillFlag && lastBillMas.getBillDistrDate() != null))) {
			if(Utility.comapreDates(secondSemDueDate, new Date())) {
				currentSecondSemPeriod = 0;
			}else {
				if(compoundedIntApplicable) {
					currentSecondSemPeriod = calculateMonths(secondSemDueDate, lastBillMas.getBmTodt());
				}else {
					currentSecondSemPeriod = calculateMonths(secondSemDueDate, currDate);
				}
			}
			double intOnCurrSecondSem = getIntAmount(currentSecondSemAmount, currentSecondSemPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
			intOnCur = intOnFirstSem + intOnCurrSecondSem;
		}else {
			intOnCur = intOnFirstSem;
		}
		
		totalInt = Math.round((intOnCur + intOnarrear));
		if (compoundedIntApplicable) {
			double currentYearPeriod = 0;
			currentYearPeriod = calculateMonths(lastBillMas.getBmTodt(), currDate);
			intOnCurrentYear = getIntAmount(totalOutStanding.doubleValue() + totalInt, currentYearPeriod, lastBillMas.getBmIntValue(),
					penalRoundOffReq);
		}
		
		totalInt = Math.round((totalInt + intOnCurrentYear));
		if(totalInt < 0) {
			totalInt = 0;
		}
		if(totalInt > 0 && StringUtils.isNotBlank(paymentFlag) && StringUtils.equals(paymentFlag, MainetConstants.FlagY)) {
			for (final TbBillDet billDet : lastBillMas.getTbWtBillDet()) {
				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
						.getLookUpCode();
				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
					billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + totalInt);
					billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + totalInt);
					lastBillMas.setBmToatlInt(lastBillMas.getBmToatlInt() + totalInt);
					listBill.get(listBill.size() - 1).setBmTotalOutstanding(listBill.get(listBill.size() - 1).getBmTotalOutstanding() + totalInt);
					interestTaxexist = true;
				}
			}
			listBill.forEach(billMas ->{
				billMas.setIntFrom(billMas.getIntTo());
				Date currentData = new Date();
				if(manualDate != null) {
					currentData = manualDate;
				}
				LocalDate currLocalDate = currentData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
                Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
				billMas.setIntTo(date);
			});
		}
		if(totalInt > 0 && StringUtils.isNotBlank(paymentFlag) && StringUtils.equals(paymentFlag, MainetConstants.FlagY) && !interestTaxexist) {
			TbBillDet intTax = new TbBillDet();
			intTax.setBmIdno(lastBillMas.getBmIdno());
			 final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
	                    PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
	                    org);
			 LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("I",
						PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid());
				LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("MI",
						PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
			Long intTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), org.getOrgid(),
					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
			TbTaxMas intTaxMas = tbTaxMasService.findById(intTaxId, org.getOrgid());
			final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                    MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                    null);
			intTax.setTaxId(intTaxId);
			intTax.setBdCurTaxamt(totalInt);
			intTax.setBdCurBalTaxamt(totalInt);
			intTax.setTaxCategory(intTaxMas.getTaxCategory1());
			intTax.setCollSeq(intTaxMas.getCollSeq());
			intTax.setLmoddate(new Date());
			intTax.setUserId(userId);
			intTax.setBdBilldetid(bdBilldetid);
			listBill.get(listBill.size() - 1).setBmTotalOutstanding(listBill.get(listBill.size() - 1).getBmTotalOutstanding() + totalInt);
			listBill.get(listBill.size() - 1).getTbWtBillDet().add(intTax);
			
		}
	}
		return totalInt;
	}
	
	private Date getCurrentFirstHalfDueDate(Date DistributionDate, Organisation org) {
		Date firstSemDueDate = null;
		if (DistributionDate != null) {
			LookUp distributionFirstSemLookUp = null;

			try {
				distributionFirstSemLookUp = CommonMasterUtility.getValueFromPrefixLookUp("BDD", "BDC", org);
			} catch (Exception exception) {
				LOGGER.error("No Prefix found for BDC(BDD)");
			}
			LocalDate convertDistrDateToLocalDate = DistributionDate.toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			firstSemDueDate = Date
					.from(convertDistrDateToLocalDate.plusDays(Long.valueOf(distributionFirstSemLookUp.getOtherField()))
							.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		return firstSemDueDate;
	}

	private Date getCurrentSecondHalfDueDate(Organisation org) {
		Date secondSemDueDate = null;
		LookUp distributionSecondSemLookUp = null;

		try {
			distributionSecondSemLookUp = CommonMasterUtility.getValueFromPrefixLookUp("SSD", "ENV", org);
		} catch (Exception exception) {
			LOGGER.error("No Prefix found for ENV(SSD)");
		}
		if (distributionSecondSemLookUp != null
				&& StringUtils.isNotBlank(distributionSecondSemLookUp.getOtherField())) {
			String dateInString = distributionSecondSemLookUp.getOtherField();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				secondSemDueDate = formatter.parse(dateInString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return secondSemDueDate;
	}

	@Override
	public List<BillReceiptPostingDTO> updateBifurcationMethodBillData(List<TbBillMas> listBill, Double paidAmount,
			Map<Long, Double> details, Map<Long, Long> billDetails, Organisation org,
			List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate) {
        List<BillReceiptPostingDTO> billPosting = new ArrayList<>();
        final int size = listBill.size();
        final LocalDate date = LocalDate.now();
        Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(manualReceptDate != null) {
        	duedate = manualReceptDate;
        }
        if (details == null) {
            details = new HashMap<>(0);
        }
        if (billDetails == null) {
            billDetails = new HashMap<>(0);
        }
        BillReceiptPostingDTO billUpdate = null;
        final double amount = paidAmount;
        double detAmount = paidAmount;
        double rebateAmount = 0d;
        double totalDemand = 0d;
        double totalInterest = 0d;
        double totalPenlty = 0d;
        int count = 0;

        TbBillMas lastBillMas = null;
        List<TbBillDet> tbBillDet = null;
        // priority
        for (final TbBillMas bill : listBill) {
        	boolean bifurcationMethodAplli = true;
        	double bifurcatePercentage = 0;
        	AtomicDouble totalBalAmount = new AtomicDouble(0);
        	bill.getTbWtBillDet().forEach(billDet ->{
        		totalBalAmount.addAndGet(billDet.getBdCurBalTaxamt());
        	});
            count++;
                if (bill.getBmToatlRebate() > 0d) {
                    rebateAmount = bill.getBmToatlRebate();
                }
          
            
            if(detAmount+rebateAmount >= Math.round(totalBalAmount.doubleValue())) {
        		bifurcationMethodAplli = false;
        	}else {
        		bifurcatePercentage = totalBalAmount.doubleValue()/detAmount;
        	}
            tbBillDet = new ArrayList<>(0);
            if ((bill.getTbWtBillDet() != null)
                    && !bill.getTbWtBillDet().isEmpty()) {
                for (final TbBillDet det : bill.getTbWtBillDet()) {
                    billUpdate = new BillReceiptPostingDTO();
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                    if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                        Double taxAmount = null;
                        Double value = null;
                        Double rebatePaid = null;
                        Double initialBalAmount = null;
                        initialBalAmount = det.getBdCurBalTaxamt();
                        billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
                        billUpdate.setTaxCategory(det.getTaxCategory());
                        billUpdate.setBillDate(bill.getBmBilldt());
                        billUpdate.setBmIdNo(bill.getBmNo());
                        billUpdate.setBillNO(bill.getBmNo());
                        billUpdate.setTaxCategoryCode(taxCode);
                        billUpdate.setTotalDetAmount(initialBalAmount);
                        billUpdate.setTaxId(det.getTaxId());
                        billUpdate.setDisplaySeq(det.getDisplaySeq());
                        // billUpdate.setBillDetId(det.getBdBilldetid());
                        if (det.getBdBilldetid() == 0) {
                            billUpdate.setBillDetId(det.getDummyDetId());
                        } else {
                            billUpdate.setBillDetId(det.getBdBilldetid());
                        }
                        billUpdate.setYearId(bill.getBmYear());
                        if (bill.getBmIdno() == 0) {
                            billUpdate.setBillMasId(bill.getDummyMasId());
                        } else {
                            billUpdate.setBillMasId(bill.getBmIdno());

                        }

                        if (count != size) {
                            billUpdate.setArrearAmount(initialBalAmount);
                        }
                        billPosting.add(billUpdate);

                        double actualBalAmount = det.getBdCurBalTaxamt();
                        // Defect Id -> 77411 done by srikanth
                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
                            List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
                            if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
                                rebateAmount = rebateAmount - taxvalueIdList.get(0);
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
                                det.setBdCurBalTaxamt(Math.abs(rebatePaid));
                                List<Double> detTaxIds = new ArrayList<Double>();
                                detTaxIds.add(taxvalueIdList.get(0));
                                detTaxIds.add(taxvalueIdList.get(1));
                                rebateDet.put(det.getBdBilldetid(), detTaxIds);
                                rebateDetails.add(rebateDet);
                            }
                        } else if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
                            if (rebateAmount >= 0d) {
                                rebatePaid = det.getBdCurBalTaxamt();
                                det.setBdCurBalTaxamt(0);
                            } else {
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
                            }
                            List<Double> detTaxIds = new ArrayList<Double>();
                            detTaxIds.add(rebateAmount);
                            rebateDet.put(det.getBdBilldetid(), detTaxIds);
                            rebateDetails.add(rebateDet);
                        }
                        if (!bifurcationMethodAplli && detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                        	billUpdate.setYearId(bill.getBmYear());
                        	 billUpdate.setBillFromDate(bill.getBmFromdt());
                             billUpdate.setBillToDate(bill.getBmTodt());
                            value = details.get(det.getTaxId());
                            detAmount = detAmount - det.getBdCurBalTaxamt();
                            if (value != null) {
                                if (detAmount >= 0d) {
                                    taxAmount = det.getBdCurBalTaxamt();
                                    value += (det.getBdCurBalTaxamt());
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            } else {
                                if (detAmount >= 0d) {
                                    value = det.getBdCurBalTaxamt();
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    taxAmount = value;
                                    double balanceAmount = Math.abs(detAmount);
                                    if(balanceAmount < 1) {
                                    	value = det.getBdCurBalTaxamt();
                                        taxAmount = value;
                                        det.setBdCurBalTaxamt(0);
                                    }else {
                                    	det.setBdCurBalTaxamt(Math.abs(detAmount));
                                    }
                                }
                            }
                            details.put(det.getTaxId(), value);
                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
                            billUpdate.setTaxAmount(taxAmount);
                        }else if(detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                        	billUpdate.setYearId(bill.getBmYear());
                       	    billUpdate.setBillFromDate(bill.getBmFromdt());
                            billUpdate.setBillToDate(bill.getBmTodt());
                            value = details.get(det.getTaxId());
                            double knockOffAmount =  actualBalAmount/bifurcatePercentage;
                            detAmount = detAmount - actualBalAmount/bifurcatePercentage;
                            if (value != null) {
                                    taxAmount = knockOffAmount;
                                    value += knockOffAmount;
                                    double balanceAmount = det.getBdCurBalTaxamt() - knockOffAmount;
                                    if(balanceAmount > 0) {
                                    	det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() - knockOffAmount);
                                    }else {
                                    	det.setBdCurBalTaxamt(0);
                                    }
                                
                            } else {
                                    value = knockOffAmount;
                                    taxAmount = value;
                                    double balanceAmount = det.getBdCurBalTaxamt() - knockOffAmount;
                                    if(balanceAmount > 0) {
                                    	det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() - knockOffAmount);
                                    }else {
                                    	det.setBdCurBalTaxamt(0);
                                    }
                            }
                            details.put(det.getTaxId(), value);
                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
                            billUpdate.setTaxAmount(taxAmount);
                        
                        }
                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
                                if (det.getTaxId().equals(lastdet.getTaxId())) {
                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
                                }
                            }
                        }
                        tbBillDet.add(det);
                        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND)) {
                            totalDemand += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                            totalInterest += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                            totalPenlty += det.getBdCurBalTaxamt();
                        }
                    }
                }
            }
            bill.setBmToatlInt(Math.abs(totalInterest));
            if (totalDemand > 0) {
                bill.setBmTotalBalAmount(Math.abs(totalDemand));
            } else {
                bill.setBmTotalBalAmount(0.0);
            }
            bill.setTotalPenalty(Math.abs(totalPenlty));
            totalDemand = 0d;
            totalInterest = 0d;
            totalPenlty = 0d;

            if (lastBillMas == null) {
                bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
            } else {
                bill.setBmTotalCumIntArrears(lastBillMas
                        .getBmTotalCumIntArrears() + bill.getBmToatlInt());
                    bill.setBmTotalArrearsWithoutInt(
                            lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
            }
            bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
                    + bill.getBmTotalCumIntArrears());
            bill.setBmTotalOutstanding(bill.getBmTotalArrears()
                    + bill.getBmTotalBalAmount());

            if(checkIfWaterDeptAndSkdclOrg(bill, org)) {
            	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
                    bill.setBmPaidFlag(constantYES);
            	}
            }else if (bill.getBmTotalOutstanding() == 0d && bill.getTotalPenalty() == 0d) {// #12562
                bill.setBmPaidFlag(constantYES);
            }

            bill.setTbWtBillDet(tbBillDet);
            lastBillMas = bill;
        }
        // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
        // listBill.get(size - 1).setBmLastRcptamt(amount);
        listBill.get(size - 1).setBmLastRcptdt(new Date());
        listBill.get(size - 1).setExcessAmount(Math.round(detAmount));
        return billPosting;
    }
	
	@Override
	public boolean checkRebateAppl(List<TbBillMas> billMasList,Organisation org) {
		boolean rebateApplFlag = false;
		LookUp fullRebateAppl = null;
		try {
			fullRebateAppl = CommonMasterUtility.getValueFromPrefixLookUp("FRA", "ENV", org);

		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(RBA)");
		}
		TbBillMas lastBill = billMasList.get(billMasList.size() - 1);
		AtomicDouble actualCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentBillAmount = new AtomicDouble(0);
		lastBill.getTbWtBillDet().forEach(det -> {
			balanceCurrentArrearBillAmount.addAndGet(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());
			actualCurrentArrearBillAmount.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
			actualCurrentBillAmount.addAndGet(det.getBdCurTaxamt());
			balanceCurrentBillAmount.addAndGet(det.getBdCurBalTaxamt());
		});
		double firstSemAmount = actualCurrentBillAmount.doubleValue() / 2;
		if (balanceCurrentBillAmount.doubleValue() >= firstSemAmount) {
			rebateApplFlag = true;
		}
		if (fullRebateAppl != null && StringUtils.isNotBlank(fullRebateAppl.getOtherField())
				&& StringUtils.equals(fullRebateAppl.getOtherField(), MainetConstants.FlagF)) {
			rebateApplFlag = true;
		}
		return rebateApplFlag;
	}

	@Override
	public String getFullPaymentOrHalfPayRebate(List<TbBillMas> billMasList,Organisation org) {
		LookUp fullRebateAppl = null;
		try {
			fullRebateAppl = CommonMasterUtility.getValueFromPrefixLookUp("FRA", "ENV", org);

		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(RBA)");
		}
		String paymentTypeRebate =  null;
		TbBillMas lastBill = billMasList.get(billMasList.size() - 1);
		AtomicDouble actualCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentBillAmount = new AtomicDouble(0);
		lastBill.getTbWtBillDet().forEach(det -> {
			balanceCurrentArrearBillAmount.addAndGet(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());
			actualCurrentArrearBillAmount.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
			actualCurrentBillAmount.addAndGet(det.getBdCurTaxamt());
			balanceCurrentBillAmount.addAndGet(det.getBdCurBalTaxamt());
		});
		if(actualCurrentBillAmount.doubleValue() == balanceCurrentBillAmount.doubleValue()) {
			paymentTypeRebate = "Full";
		}else {
			paymentTypeRebate = "Half";
		}
		if (fullRebateAppl != null && StringUtils.isNotBlank(fullRebateAppl.getOtherField())
				&& StringUtils.equals(fullRebateAppl.getOtherField(), MainetConstants.FlagF)) {
			paymentTypeRebate = "Full";
		}
		return paymentTypeRebate;
	}

	@Override
	public double getHalfPayableOutstanding(List<TbBillMas> billMasList,Organisation org, String paymentDisplayFlag) {
		LookUp rebateOnBalanceOrActual = null;
		try {
			rebateOnBalanceOrActual = CommonMasterUtility.getValueFromPrefixLookUp("RBA", "ENV", org);

		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(RBA)");
		}
		double halfOutstandingAmount = 0;
		TbBillMas lastBill = billMasList.get(billMasList.size() - 1);
		AtomicDouble actualCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualArrearBillAmount = new AtomicDouble(0);
		lastBill.getTbWtBillDet().forEach(det -> {
			balanceCurrentArrearBillAmount.addAndGet(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());
			actualCurrentArrearBillAmount.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
			actualCurrentBillAmount.addAndGet(det.getBdCurTaxamt());
			balanceCurrentBillAmount.addAndGet(det.getBdCurBalTaxamt());
			balanceArrearBillAmount.addAndGet(det.getBdPrvBalArramt());
			actualArrearBillAmount.addAndGet(det.getBdPrvArramt());
		});
		if ((rebateOnBalanceOrActual != null && StringUtils.isNotBlank(rebateOnBalanceOrActual.getOtherField())
				&& StringUtils.equals(rebateOnBalanceOrActual.getOtherField(), MainetConstants.FlagB))
				|| (StringUtils.isNotBlank(paymentDisplayFlag)
						&& StringUtils.equals(paymentDisplayFlag, MainetConstants.FlagY))) {
			halfOutstandingAmount = halfOutstandingAmount + balanceArrearBillAmount.doubleValue() + (balanceCurrentBillAmount.doubleValue()/2);
		}else {
			halfOutstandingAmount = halfOutstandingAmount + actualArrearBillAmount.doubleValue() + (actualCurrentBillAmount.doubleValue()/2);
		}
		return halfOutstandingAmount;
	}
	
	@Override
	public double getFullPayableOutstanding(List<TbBillMas> billMasList,Organisation org, String paymentDisplayFlag) {
		LookUp rebateOnBalanceOrActual = null;
		try {
			rebateOnBalanceOrActual = CommonMasterUtility.getValueFromPrefixLookUp("RBA", "ENV", org);

		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(RBA)");
		}
		double fullOutstandingAmount = 0;
		TbBillMas lastBill = billMasList.get(billMasList.size() - 1);
		AtomicDouble actualCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualArrearBillAmount = new AtomicDouble(0);
		lastBill.getTbWtBillDet().forEach(det -> {
			balanceCurrentArrearBillAmount.addAndGet(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());
			actualCurrentArrearBillAmount.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
			actualCurrentBillAmount.addAndGet(det.getBdCurTaxamt());
			balanceCurrentBillAmount.addAndGet(det.getBdCurBalTaxamt());
			balanceArrearBillAmount.addAndGet(det.getBdPrvBalArramt());
			actualArrearBillAmount.addAndGet(det.getBdPrvArramt());
		});
		
		if ((rebateOnBalanceOrActual != null && StringUtils.isNotBlank(rebateOnBalanceOrActual.getOtherField())
				&& StringUtils.equals(rebateOnBalanceOrActual.getOtherField(), MainetConstants.FlagB))
				|| (StringUtils.isNotBlank(paymentDisplayFlag)
						&& StringUtils.equals(paymentDisplayFlag, MainetConstants.FlagY))) {
			fullOutstandingAmount = fullOutstandingAmount + balanceArrearBillAmount.doubleValue() + (balanceCurrentBillAmount.doubleValue());
		}else {
			fullOutstandingAmount = fullOutstandingAmount + actualArrearBillAmount.doubleValue() + (actualCurrentBillAmount.doubleValue());
		}
		return fullOutstandingAmount;
	}
	
	
	private double getIntAmount(double amount , double period, double intValue,boolean penalRoundOff) {
		double intAmount = 0;
		if(penalRoundOff) {
			intAmount = amount * 1 * intValue;
			intAmount = Math.round(intAmount);
			intAmount = intAmount * period;
		}else {
			intAmount = amount * period * intValue;
		}
		return intAmount;
	}
	
	@Override
	public double getBalanceOutstanding(List<TbBillMas> billMasList,Organisation org) {
		LookUp rebateOnBalanceOrActual = null;
		try {
			rebateOnBalanceOrActual = CommonMasterUtility.getValueFromPrefixLookUp("RBA", "ENV", org);

		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(RBA)");
		}
		double halfOutstandingAmount = 0;
		TbBillMas lastBill = billMasList.get(billMasList.size() - 1);
		AtomicDouble actualCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceCurrentBillAmount = new AtomicDouble(0);
		AtomicDouble balanceArrearBillAmount = new AtomicDouble(0);
		AtomicDouble actualArrearBillAmount = new AtomicDouble(0);
		lastBill.getTbWtBillDet().forEach(det -> {
			balanceCurrentArrearBillAmount.addAndGet(det.getBdPrvBalArramt() + det.getBdCurBalTaxamt());
			actualCurrentArrearBillAmount.addAndGet(det.getBdCurTaxamt() + det.getBdPrvArramt());
			actualCurrentBillAmount.addAndGet(det.getBdCurTaxamt());
			balanceCurrentBillAmount.addAndGet(det.getBdCurBalTaxamt());
			balanceArrearBillAmount.addAndGet(det.getBdPrvBalArramt());
			actualArrearBillAmount.addAndGet(det.getBdPrvArramt());
		});
		
			halfOutstandingAmount = halfOutstandingAmount + balanceArrearBillAmount.doubleValue() + (balanceCurrentBillAmount.doubleValue());
		
		return halfOutstandingAmount;
	}
	
	
	@Override
	public void calculateMultipleInterestForWater(List<TbBillMas> listBill, Organisation org, Long deptId,
			String interestRecalculate, Date manualDate) {

        int billSize = listBill.size() - 1;
        int count = 1;
        int period = 0;
        Date toDate = null;
        Date fromDate = null;
        TbBillMas lastBillMas = null;
        Date tillDate = manualDate;
        if (manualDate == null) {
            tillDate = new Date();
        }
        LookUp defaultInterest  = null;
     	try {
     		defaultInterest = CommonMasterUtility.getValueFromPrefixLookUp("DIP", "ENV", org);
     	}catch (Exception e) {
     		LOGGER.error("No Prefix found for ENV(DIP)");
 		}
        for (final TbBillMas currBillMas : listBill) {
        	
        	double totalInterest = 0;
        		   if (currBillMas.getInterstCalMethod() != null) {
        			   if (count == listBill.size() || interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                           // this Condition for current bill to calculate interest till current date
                           toDate = tillDate;
                       } else {
                           toDate = (tillDate.after(currBillMas.getBmTodt()) ? currBillMas.getBmTodt() : tillDate);                           
                           // other then current bill to calculate interest till bill due date
                       }
                       if (interestRecalculate.equals(MainetConstants.Property.INT_RECAL_YES)) {
                           fromDate = currBillMas.getIntTo();
                       } else {
                           fromDate = currBillMas.getBmDuedate();
                       }                       

                       // calculate period
                       if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                           period = calculateYears(fromDate, toDate, org.getOrgid());

                       } else if (currBillMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                           period = calculateMonths(fromDate, toDate);
                       }
                       if(defaultInterest != null) {
                    	   period = Integer.valueOf(defaultInterest.getOtherField());
                       }
                       /**
                        * This condition is to make false if interest calculated already through revise form
                        *   StringUtils.isBlank(currBillMas.getWtLo3())
								|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN)
                        */
				if (((defaultInterest == null) || (Utility.comapreDates(tillDate, currBillMas.getBmBilldt())))
						&& (StringUtils.isBlank(currBillMas.getWtLo3())
								|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN))) {
			
        		   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
        			   final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
                               .getLookUpCode();
                       if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                    	   TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(), org.getOrgid());
                    	   //TbTaxMas intersetDependentTaxMas = tbTaxMasService.findById(intersetTaxMas.getParentCode(), org.getOrgid());
                    	   
                    	   List<TbBillDet> interestDependentTax = currBillMas.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode())).collect(Collectors.toList());
                    	   if(CollectionUtils.isNotEmpty(interestDependentTax)) {
                    		   double balanceAmount = 0;
                    		   if(defaultInterest == null) {
                    			    balanceAmount = interestDependentTax.get(0).getBdCurBalTaxamt() + interestDependentTax.get(0).getBdCurBalArramt();
                    		   }else {
                    			   balanceAmount = interestDependentTax.get(0).getBdPrvBalArramt();
                    		   }
                    		   
                        	   if(currBillMas.getBmIntValue() >0 && balanceAmount > 0) {
                        		   if(defaultInterest == null) {
                        			   billDet.setBdCurTaxamt(Math.round((balanceAmount
                                               * period * currBillMas.getBmIntValue()) + billDet.getBdCurTaxamt()));// for first bill master
                                       billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                               * period * currBillMas.getBmIntValue()) + billDet.getBdCurBalTaxamt()));// for first bill master
                        		   }else {
                        			   billDet.setBdCurTaxamt(Math.round((balanceAmount
                                               * period * currBillMas.getBmIntValue())));// for first bill master
                                       billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                               * period * currBillMas.getBmIntValue())));// for first bill master
                        		   }
                                   totalInterest = totalInterest + billDet.getBdCurTaxamt();
                        	   }
                    	   }

                           // }
                       }
                       
        		   }
        		   
        		   if (lastBillMas != null) {
        			   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
                       lastBillMas.getTbWtBillDet().parallelStream()
                               .filter(lastBillDet -> billDet.getTaxId().toString()
                                       .equals(lastBillDet.getTaxId().toString()))
                               .forEach(lastBillDet -> {
                                   billDet.setBdPrvArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                                   billDet.setBdPrvBalArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvArramt());
                               });

                   }
        		   }
                       }
        	
            
                // calculate bill for currBillMas till that year only
                // ex. bill of 2016-17 interest from 14/05/2016(dueDate) to 31/03/2017 (schedule end date)only
               // currBillMas.setBmToatlInt(totalInterest);
                currBillMas.setIntFrom(currBillMas.getBmDuedate());
                currBillMas.setIntTo(tillDate);

                if (StringUtils.isNotBlank(interestRecalculate) && interestRecalculate.equals(MainetConstants.Property.INT_RECAL_NO)) {
                	
                	double totInt = 0.0;
                    // for loop contain Excluding currBillMas reaming following bill to calculate interest till current date
                    for (int i = count; i <= billSize; i++) {
                        TbBillMas billMas = listBill.get(i);
                        if (i == billSize) { // this Condition for current bill to calculate interest till current date
                            toDate = tillDate;
                        } else {
                            toDate = (tillDate.after(billMas.getBmTodt()) ? billMas.getBmTodt() : tillDate);
                            // other then current bill to calculate interest till bill due date
                            // Example In half yearly New Date= 1/06/2018 then second last schedule is current ongoing schedule
                        }
                        if (StringUtils.isNotBlank(billMas.getInterstCalMethod()) && billMas.getInterstCalMethod().equals(MainetConstants.Property.YEARLY)) {
                            period = calculateYears(billMas.getBmFromdt(), toDate, org.getOrgid());

                        } else if (StringUtils.isNotBlank(billMas.getInterstCalMethod()) && billMas.getInterstCalMethod().equals(MainetConstants.Property.MONTHLY)) {
                            period = calculateMonths(billMas.getBmFromdt(), toDate);
                        }
                        
                        if(defaultInterest != null) {
                     	   period = Integer.valueOf(defaultInterest.getOtherField());
                        }
						if (((defaultInterest == null) || (Utility.comapreDates(tillDate, currBillMas.getBmBilldt())))
								&& (StringUtils.isBlank(currBillMas.getWtLo3())
										|| StringUtils.equals(currBillMas.getWtLo3(), MainetConstants.FlagN))) {
                        for (TbBillDet billDet : billMas.getTbWtBillDet()) {
              			   final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
                                     .getLookUpCode();
                             if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                          	   TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(), org.getOrgid());
                          	   
                          	   List<TbBillDet> interestDependentTax = currBillMas.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode())).collect(Collectors.toList());
                          	  if(CollectionUtils.isNotEmpty(interestDependentTax)) {
                          		  
                          	   double balanceAmount =0;
                          	 if(defaultInterest == null) {
                          		balanceAmount = interestDependentTax.get(0).getBdCurBalTaxamt() + interestDependentTax.get(0).getBdCurBalArramt();
                 		   }else {
                 			   balanceAmount = interestDependentTax.get(0).getBdPrvBalArramt();
                 		   }
                          	   double intValue = 0;
                          	   if(billDet.getBaseRate() != null) {
                          		 intValue = billDet.getBaseRate();
                          	   }else {
                          		 intValue = billMas.getBmIntValue();
                          	   }
                          	 if(billMas.getBmIntValue() > 0 && balanceAmount > 0) {
                          	 if(defaultInterest == null) {
                          		billDet.setBdCurTaxamt(Math.round((balanceAmount
                                        * period * intValue) + billDet.getBdCurTaxamt()));// for first bill master
                                billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                        * period * intValue) + billDet.getBdCurBalTaxamt()));// for first bill master
                          	 }else {
                          		 billDet.setBdCurTaxamt(Math.round((balanceAmount
                                         * period * intValue)));// for first bill master
                                 billDet.setBdCurBalTaxamt(Math.round((balanceAmount
                                         * period * intValue)));// for first bill master
                          	 }
                          	  
                                 totInt = totInt + billDet.getBdCurTaxamt();
                          	  }
                             }
                             }
              		   }
                        
                        if (lastBillMas != null) {
             			   for (TbBillDet billDet : currBillMas.getTbWtBillDet()) {
                            lastBillMas.getTbWtBillDet().parallelStream()
                                    .filter(lastBillDet -> billDet.getTaxId().toString()
                                            .equals(lastBillDet.getTaxId().toString()))
                                    .forEach(lastBillDet -> {
                                        billDet.setBdPrvArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvBalArramt());
                                        billDet.setBdPrvBalArramt(lastBillDet.getBdCurBalTaxamt() + lastBillDet.getBdPrvArramt());
                                    });

                        }
             		   }
                        
                        }
                        
                       
                    }
                }
                currBillMas.setBmToatlInt(Math.round((totalInterest+ currBillMas.getBmToatlInt())));
                if (lastBillMas != null) {// setting CumIntArrears
                    currBillMas.setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + currBillMas.getBmToatlInt());
                } else {
                    currBillMas.setBmTotalCumIntArrears(currBillMas.getBmToatlInt());
                }

                currBillMas.setBmTotalArrears(
                        Math.round(currBillMas.getBmTotalCumIntArrears() + currBillMas.getBmTotalArrearsWithoutInt()));
                currBillMas.setBmTotalOutstanding(currBillMas.getBmTotalArrears() + currBillMas.getBmTotalBalAmount());
               
                lastBillMas = currBillMas;
                count++;
        	  }
            }
        }
	
	
	 @Override
	    public List<BillReceiptPostingDTO> updateBillDataForInterest(final List<TbBillMas> listBill,
	            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
	            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate) {
	        List<BillReceiptPostingDTO> billPosting = new ArrayList<>();
	        final int size = listBill.size();
	        final LocalDate date = LocalDate.now();
	        Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        if(manualReceptDate != null) {
	        	duedate = manualReceptDate;
	        }
	        if (details == null) {
	            details = new HashMap<>(0);
	        }
	        if (billDetails == null) {
	            billDetails = new HashMap<>(0);
	        }
	        BillReceiptPostingDTO billUpdate = null;
	        final double amount = paidAmount;
	        double detAmount = paidAmount;
	        double rebateAmount = 0d;
	        double totalDemand = 0d;
	        double totalInterest = 0d;
	        double totalPenlty = 0d;
	        double paidTotalInt = 0d;
	        int count = 0;

	        TbBillMas lastBillMas = null;
	        List<TbBillDet> tbBillDet = null;
	        // priority
	        for (final TbBillMas bill : listBill) {
	            count++;
	            if (bill.getBmDuedate() != null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
	                if (bill.getBmToatlRebate() > 0d) {

	                    // This condition is failing in case af arrears. At any how we are checking condition while calling rebate ->
	                    // by srikanth 77411

	                    // final double totalAmountPayable = detAmount + bill.getBmToatlRebate();
	                    rebateAmount = bill.getBmToatlRebate();
	                    /*
	                     * if (totalAmountPayable >= bill.getBmTotalOutstanding()) { paidAmount += bill.getBmToatlRebate();
	                     * rebateAmount = bill.getBmToatlRebate(); }
	                     */
	                }
	            }
	            tbBillDet = new ArrayList<>(0);
	            if ((bill.getTbWtBillDet() != null)
	                    && !bill.getTbWtBillDet().isEmpty()) {
	                for (final TbBillDet det : bill.getTbWtBillDet()) {
	                    billUpdate = new BillReceiptPostingDTO();
	                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
	                    if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
	                        Double taxAmount = null;
	                        Double value = null;
	                        Double rebatePaid = null;
	                        Double initialBalAmount = null;
	                        initialBalAmount = det.getBdCurBalTaxamt();
	                        billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
	                        billUpdate.setTaxCategory(det.getTaxCategory());
	                        billUpdate.setBillDate(bill.getBmBilldt());
	                        billUpdate.setBmIdNo(bill.getBmNo());
	                        billUpdate.setBillNO(bill.getBmNo());
	                        billUpdate.setTaxCategoryCode(taxCode);
	                        billUpdate.setTotalDetAmount(initialBalAmount);
	                        billUpdate.setTaxId(det.getTaxId());
	                        billUpdate.setDisplaySeq(det.getDisplaySeq());
	                        // billUpdate.setBillDetId(det.getBdBilldetid());
	                        if (det.getBdBilldetid() == 0) {
	                            billUpdate.setBillDetId(det.getDummyDetId());
	                        } else {
	                            billUpdate.setBillDetId(det.getBdBilldetid());
	                        }
	                        billUpdate.setYearId(bill.getBmYear());
	                        if (bill.getBmIdno() == 0) {
	                            billUpdate.setBillMasId(bill.getDummyMasId());
	                        } else {
	                            billUpdate.setBillMasId(bill.getBmIdno());

	                        }

	                        if (count != size) {
	                            billUpdate.setArrearAmount(initialBalAmount);
	                        }
	                        billPosting.add(billUpdate);

	                        // Defect Id -> 77411 done by srikanth
	                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
	                            Map<Long, List<Double>> rebateDet = new HashMap<>();
	                            TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
	                            List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
	                            if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
	                                rebateAmount = rebateAmount - taxvalueIdList.get(0);
	                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
	                                det.setBdCurBalTaxamt(Math.abs(rebatePaid));
	                                List<Double> detTaxIds = new ArrayList<Double>();
	                                detTaxIds.add(taxvalueIdList.get(0));
	                                detTaxIds.add(taxvalueIdList.get(1));
	                                rebateDet.put(det.getBdBilldetid(), detTaxIds);
	                                rebateDetails.add(rebateDet);
	                            }
	                        } else if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
	                            Map<Long, List<Double>> rebateDet = new HashMap<>();
	                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
	                            if (rebateAmount >= 0d) {
	                                rebatePaid = det.getBdCurBalTaxamt();
	                                det.setBdCurBalTaxamt(0);
	                            } else {
	                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
	                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
	                            }
	                            List<Double> detTaxIds = new ArrayList<Double>();
	                            detTaxIds.add(rebateAmount);
	                            rebateDet.put(det.getBdBilldetid(), detTaxIds);
	                            rebateDetails.add(rebateDet);
	                        }
	                        if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
	                            value = details.get(det.getTaxId());
	                            detAmount = detAmount - det.getBdCurBalTaxamt();
	                            if (value != null) {
	                                if (detAmount >= 0d) {
	                                    taxAmount = det.getBdCurBalTaxamt();
	                                    value += (det.getBdCurBalTaxamt());
	                                    det.setBdCurBalTaxamt(0);
	                                } else {
	                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
	                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
	                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
	                                }
	                            } else {
	                                if (detAmount >= 0d) {
	                                    value = det.getBdCurBalTaxamt();
	                                    taxAmount = value;
	                                    det.setBdCurBalTaxamt(0);
	                                } else {
	                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
	                                    taxAmount = value;
	                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
	                                }
	                            }
	                            details.put(det.getTaxId(), value);
	                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
	                            // billUpdate.setBillDetId(det.getBdBilldetid());
	                            /*
	                             * if (det.getBdBilldetid() == 0) { billUpdate.setBillDetId(det.getDummyDetId()); } else {
	                             * billUpdate.setBillDetId(det.getBdBilldetid()); }
	                             */
	                            // billUpdate.setTaxId(det.getTaxId());
	                            billUpdate.setTaxAmount(Math.round(taxAmount));

	                            /*
	                             * billUpdate.setYearId(bill.getBmYear()); if (bill.getBmIdno() == 0) {
	                             * billUpdate.setBillMasId(bill.getDummyMasId()); } else { billUpdate.setBillMasId(bill.getBmIdno());
	                             * }
	                             */
	                            /*
	                             * billUpdate.setTaxCategory(det.getTaxCategory()); billUpdate.setBillDate(bill.getBmBilldt());
	                             * billUpdate.setBmIdNo(bill.getBmNo()); billUpdate.setTotalDetAmount(initialBalAmount); if (count !=
	                             * size) { billUpdate.setArrearAmount(initialBalAmount); } billPosting.add(billUpdate);
	                             */
	                        }
	                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
	                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
	                                if (det.getTaxId().equals(lastdet.getTaxId())) {
	                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
	                                }
	                            }
	                        }
	                        tbBillDet.add(det);
	                        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND) || taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
	                            totalDemand += det.getBdCurBalTaxamt();
	                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
	                            totalInterest += det.getBdCurBalTaxamt();
	                            if(taxAmount != null) {
	                            	paidTotalInt +=taxAmount;
	                            }
	                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
	                            totalPenlty += det.getBdCurBalTaxamt();
	                        }
	                    }
	                }
	            }
	            bill.setBmToatlInt(Math.abs(totalInterest));
			/*
			 * if (totalDemand > 0) { bill.setBmTotalBalAmount(Math.abs(totalDemand)); }
			 * else { bill.setBmTotalBalAmount(0.0); }
			 */
	            
	            bill.setBmTotalOutstanding(bill.getBmTotalOutstanding()
	                    - paidTotalInt);
	            bill.setTotalPenalty(Math.abs(totalPenlty));
	            totalDemand = 0d;
	            totalInterest = 0d;
	            totalPenlty = 0d;

			if (lastBillMas == null) {
				bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
			} else {
				bill.setBmTotalCumIntArrears(lastBillMas.getBmTotalCumIntArrears() + bill.getBmToatlInt());
			
					bill.setBmTotalArrearsWithoutInt(
							lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
				
			}
	            //bill.setTbWtBillDet(tbBillDet);
	            lastBillMas = bill;
	        }
	        // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
	        listBill.get(size - 1).setBmLastRcptamt(amount);
	        listBill.get(size - 1).setBmLastRcptdt(new Date());
	        listBill.get(size - 1).setExcessAmount(detAmount);
	        return billPosting;
	    }

	@Override
	public List<BillReceiptPostingDTO> updateSingleBillData(TbBillMas bill,TbBillMas lastBillMas, Double detAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate, boolean isLastBill) {
		
		List<BillReceiptPostingDTO>  billPosting = new ArrayList();
        final LocalDate date = LocalDate.now();
        Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(manualReceptDate != null) {
        	duedate = manualReceptDate;
        }
        if (details == null) {
            details = new HashMap<>(0);
        }
        if (billDetails == null) {
            billDetails = new HashMap<>(0);
        }
        BillReceiptPostingDTO billUpdate = null;
        final double amount = detAmount;
        //double detAmount = paidAmount;
        double rebateAmount = 0d;
        double totalDemand = 0d;
        double totalInterest = 0d;
        double totalPenlty = 0d;
        int count = 0;

        //TbBillMas lastBillMas = null;
        List<TbBillDet> tbBillDet = null;
        // priority
       
            if (bill.getBmDuedate() != null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
                if (bill.getBmToatlRebate() > 0d) {

                    // This condition is failing in case af arrears. At any how we are checking condition while calling rebate ->
                    // by srikanth 77411

                    // final double totalAmountPayable = detAmount + bill.getBmToatlRebate();
                    rebateAmount = bill.getBmToatlRebate();
                    /*
                     * if (totalAmountPayable >= bill.getBmTotalOutstanding()) { paidAmount += bill.getBmToatlRebate();
                     * rebateAmount = bill.getBmToatlRebate(); }
                     */
                }
            }
            tbBillDet = new ArrayList<>(0);
            if ((bill.getTbWtBillDet() != null)
                    && !bill.getTbWtBillDet().isEmpty()) {
                for (final TbBillDet det : bill.getTbWtBillDet()) {
                    billUpdate = new BillReceiptPostingDTO();
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                    if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                        Double taxAmount = null;
                        Double value = null;
                        Double rebatePaid = null;
                        Double initialBalAmount = null;
                        initialBalAmount = det.getBdCurBalTaxamt();
                        billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
                        billUpdate.setTaxCategory(det.getTaxCategory());
                        billUpdate.setBillDate(bill.getBmBilldt());
                        billUpdate.setBmIdNo(bill.getBmNo());
                        billUpdate.setBillNO(bill.getBmNo());
                        billUpdate.setTaxCategoryCode(taxCode);
                        billUpdate.setTotalDetAmount(initialBalAmount);
                        billUpdate.setTaxId(det.getTaxId());
                        billUpdate.setDisplaySeq(det.getDisplaySeq());
                        billUpdate.setBillFromDate(bill.getBmFromdt());
                        billUpdate.setBillToDate(bill.getBmTodt());
                        // billUpdate.setBillDetId(det.getBdBilldetid());
                        if (det.getBdBilldetid() == 0) {
                            billUpdate.setBillDetId(det.getDummyDetId());
                        } else {
                            billUpdate.setBillDetId(det.getBdBilldetid());
                        }
                        billUpdate.setYearId(bill.getBmYear());
                        if (bill.getBmIdno() == 0) {
                            billUpdate.setBillMasId(bill.getDummyMasId());
                        } else {
                            billUpdate.setBillMasId(bill.getBmIdno());

                        }

                        if (!isLastBill) {
                            billUpdate.setArrearAmount(initialBalAmount);
                        }
                        billPosting.add(billUpdate);

                        // Defect Id -> 77411 done by srikanth
                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
                            List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
                            if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
                                rebateAmount = rebateAmount - taxvalueIdList.get(0);
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
                                det.setBdCurBalTaxamt(Math.abs(rebatePaid));
                                List<Double> detTaxIds = new ArrayList<Double>();
                                detTaxIds.add(taxvalueIdList.get(0));
                                detTaxIds.add(taxvalueIdList.get(1));
                                rebateDet.put(det.getBdBilldetid(), detTaxIds);
                                rebateDetails.add(rebateDet);
                            }
                        } else if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            Map<Long, List<Double>> rebateDet = new HashMap<>();
                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
                            if (rebateAmount >= 0d) {
                                rebatePaid = det.getBdCurBalTaxamt();
                                det.setBdCurBalTaxamt(0);
                            } else {
                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
                            }
                            List<Double> detTaxIds = new ArrayList<Double>();
                            detTaxIds.add(rebateAmount);
                            rebateDet.put(det.getBdBilldetid(), detTaxIds);
                            rebateDetails.add(rebateDet);
                        }
                        if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                            value = details.get(det.getTaxId());
                            detAmount = detAmount - det.getBdCurBalTaxamt();
                            if (value != null) {
                                if (detAmount >= 0d) {
                                    taxAmount = det.getBdCurBalTaxamt();
                                    value += (det.getBdCurBalTaxamt());
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            } else {
                                if (detAmount >= 0d) {
                                    value = det.getBdCurBalTaxamt();
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(0);
                                } else {
                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                    taxAmount = value;
                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
                                }
                            }
                            bill.setUpdatedDate(new Date());
                            details.put(det.getTaxId(), value);
                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
                            // billUpdate.setBillDetId(det.getBdBilldetid());
                            /*
                             * if (det.getBdBilldetid() == 0) { billUpdate.setBillDetId(det.getDummyDetId()); } else {
                             * billUpdate.setBillDetId(det.getBdBilldetid()); }
                             */
                            // billUpdate.setTaxId(det.getTaxId());
                            billUpdate.setTaxAmount(Math.round(taxAmount));

                            /*
                             * billUpdate.setYearId(bill.getBmYear()); if (bill.getBmIdno() == 0) {
                             * billUpdate.setBillMasId(bill.getDummyMasId()); } else { billUpdate.setBillMasId(bill.getBmIdno());
                             * }
                             */
                            /*
                             * billUpdate.setTaxCategory(det.getTaxCategory()); billUpdate.setBillDate(bill.getBmBilldt());
                             * billUpdate.setBmIdNo(bill.getBmNo()); billUpdate.setTotalDetAmount(initialBalAmount); if (count !=
                             * size) { billUpdate.setArrearAmount(initialBalAmount); } billPosting.add(billUpdate);
                             */
                        }
                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
                                if (det.getTaxId().equals(lastdet.getTaxId())) {
                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
                                }
                            }
                        }
                        tbBillDet.add(det);
                        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND) || taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
                            totalDemand += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                            totalInterest += det.getBdCurBalTaxamt();
                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                            totalPenlty += det.getBdCurBalTaxamt();
                        }
                    }
                }
            }
            bill.setBmToatlInt(Math.abs(totalInterest));
            if (totalDemand > 0) {
                bill.setBmTotalBalAmount(Math.abs(totalDemand));
            } else {
                bill.setBmTotalBalAmount(0.0);
            }
            bill.setTotalPenalty(Math.abs(totalPenlty));
            totalDemand = 0d;
            totalInterest = 0d;
            totalPenlty = 0d;

            if (lastBillMas == null) {
                bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
            } else {
                bill.setBmTotalCumIntArrears(lastBillMas
                        .getBmTotalCumIntArrears() + bill.getBmToatlInt());
                if (lastBillMas.getBmDuedate() != null && UtilityService.compareDate(
                        lastBillMas.getBmDuedate(), bill.getBmBilldt())) {
                    bill.setBmTotalArrearsWithoutInt(
                            lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
                }
            }
            bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
                    + bill.getBmTotalCumIntArrears());
            bill.setBmTotalOutstanding(bill.getBmTotalArrears()
                    + bill.getBmTotalBalAmount());
            
            if(checkIfWaterDeptAndSkdclOrg(bill, org)) {
            	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
                    bill.setBmPaidFlag(constantYES);
            	}
            }else if (bill.getBmTotalOutstanding() == 0d && bill.getTotalPenalty() == 0d) {// #12562
                bill.setBmPaidFlag(constantYES);
            }

            bill.setTbWtBillDet(tbBillDet);
            //lastBillMas = bill;
	
        
        // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
        // listBill.get(size - 1).setBmLastRcptamt(amount);
            if(isLastBill) {
            	bill.setBmLastRcptdt(new Date());
                bill.setExcessAmount(detAmount);
            }
        
        return billPosting;
	}
	
	
	@Override
    public List<BillReceiptPostingDTO> updateBillDataForGroupPropNo(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate, List<String> propNoList) {
        List<BillReceiptPostingDTO> billPosting = new ArrayList<>();
        
        final double amount = paidAmount;
        double detAmount = paidAmount;
        for (String propNo : propNoList) {
        	List<TbBillMas> billMasList = listBill.stream().filter(list ->list.getPropNo().equals(propNo)).collect(Collectors.toList());
        	if(CollectionUtils.isNotEmpty(billMasList)) {
        		final int size = billMasList.size(); //3
                final LocalDate date = LocalDate.now();
                Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if(manualReceptDate != null) {
                	duedate = manualReceptDate;
                }
                if (details == null) {
                    details = new HashMap<>(0);
                }
                if (billDetails == null) {
                    billDetails = new HashMap<>(0);
                }
                BillReceiptPostingDTO billUpdate = null;
                double rebateAmount = 0d;
                double totalDemand = 0d;
                double totalInterest = 0d;
                double totalPenlty = 0d;
                int count = 0;

                TbBillMas lastBillMas = null;
                List<TbBillDet> tbBillDet = null;
                // priority
                for (final TbBillMas bill : billMasList) {
                    count++;
                    if (bill.getBmDuedate() != null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
                        if (bill.getBmToatlRebate() > 0d) {

                            // This condition is failing in case af arrears. At any how we are checking condition while calling rebate ->
                            // by srikanth 77411

                            // final double totalAmountPayable = detAmount + bill.getBmToatlRebate();
                            rebateAmount = bill.getBmToatlRebate();
                            /*
                             * if (totalAmountPayable >= bill.getBmTotalOutstanding()) { paidAmount += bill.getBmToatlRebate();
                             * rebateAmount = bill.getBmToatlRebate(); }
                             */
                        }
                    }
                    tbBillDet = new ArrayList<>(0);
                    if ((bill.getTbWtBillDet() != null)
                            && !bill.getTbWtBillDet().isEmpty()) {
                        for (final TbBillDet det : bill.getTbWtBillDet()) {
                            billUpdate = new BillReceiptPostingDTO();
                            final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
                            if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                                Double taxAmount = null;
                                Double value = null;
                                Double rebatePaid = null;
                                Double initialBalAmount = null;
                                initialBalAmount = det.getBdCurBalTaxamt();
                                billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
                                billUpdate.setTaxCategory(det.getTaxCategory());
                                billUpdate.setBillDate(bill.getBmBilldt());
                                billUpdate.setBmIdNo(bill.getBmNo());
                                billUpdate.setBillNO(bill.getBmNo());
                                billUpdate.setTaxCategoryCode(taxCode);
                                billUpdate.setTotalDetAmount(initialBalAmount);
                                billUpdate.setTaxId(det.getTaxId());
                                billUpdate.setDisplaySeq(det.getDisplaySeq());
                                // billUpdate.setBillDetId(det.getBdBilldetid());
                                if (det.getBdBilldetid() == 0) {
                                    billUpdate.setBillDetId(det.getDummyDetId());
                                } else {
                                    billUpdate.setBillDetId(det.getBdBilldetid());
                                }
                                billUpdate.setYearId(bill.getBmYear());
                                if (bill.getBmIdno() == 0) {
                                    billUpdate.setBillMasId(bill.getDummyMasId());
                                } else {
                                    billUpdate.setBillMasId(bill.getBmIdno());

                                }

                                if (count != size) {
                                    billUpdate.setArrearAmount(initialBalAmount);
                                }
                                billPosting.add(billUpdate);

                                // Defect Id -> 77411 done by srikanth
                                if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
                                    Map<Long, List<Double>> rebateDet = new HashMap<>();
                                    TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
                                    List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
                                    if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
                                        rebateAmount = rebateAmount - taxvalueIdList.get(0);
                                        rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
                                        det.setBdCurBalTaxamt(Math.abs(rebatePaid));
                                        List<Double> detTaxIds = new ArrayList<Double>();
                                        detTaxIds.add(taxvalueIdList.get(0));
                                        detTaxIds.add(taxvalueIdList.get(1));
                                        rebateDet.put(det.getBdBilldetid(), detTaxIds);
                                        rebateDetails.add(rebateDet);
                                    }
                                } 
                                if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
                                    value = details.get(det.getTaxId());
                                    detAmount = detAmount - det.getBdCurBalTaxamt();
                                    if (value != null) {
                                        if (detAmount >= 0d) {
                                            taxAmount = det.getBdCurBalTaxamt();
                                            value += (det.getBdCurBalTaxamt());
                                            det.setBdCurBalTaxamt(0);
                                        } else {
                                            taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
                                            value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                            det.setBdCurBalTaxamt(Math.abs(detAmount));
                                        }
                                    } else {
                                        if (detAmount >= 0d) {
                                            value = det.getBdCurBalTaxamt();
                                            taxAmount = value;
                                            det.setBdCurBalTaxamt(0);
                                        } else {
                                            value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
                                            taxAmount = value;
                                            det.setBdCurBalTaxamt(Math.abs(detAmount));
                                        }
                                    }
                                    details.put(det.getTaxId(), value);
                                    billDetails.put(det.getTaxId(), det.getBdBilldetid());
                                    // billUpdate.setBillDetId(det.getBdBilldetid());
                                    /*
                                     * if (det.getBdBilldetid() == 0) { billUpdate.setBillDetId(det.getDummyDetId()); } else {
                                     * billUpdate.setBillDetId(det.getBdBilldetid()); }
                                     */
                                    // billUpdate.setTaxId(det.getTaxId());
                                    billUpdate.setTaxAmount(Math.round(taxAmount));

                                    /*
                                     * billUpdate.setYearId(bill.getBmYear()); if (bill.getBmIdno() == 0) {
                                     * billUpdate.setBillMasId(bill.getDummyMasId()); } else { billUpdate.setBillMasId(bill.getBmIdno());
                                     * }
                                     */
                                    /*
                                     * billUpdate.setTaxCategory(det.getTaxCategory()); billUpdate.setBillDate(bill.getBmBilldt());
                                     * billUpdate.setBmIdNo(bill.getBmNo()); billUpdate.setTotalDetAmount(initialBalAmount); if (count !=
                                     * size) { billUpdate.setArrearAmount(initialBalAmount); } billPosting.add(billUpdate);
                                     */
                                }
        						/*
        						 * if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) { for
        						 * (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) { if
        						 * (det.getTaxId().equals(lastdet.getTaxId())) {
        						 * det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() +
        						 * lastdet.getBdPrvBalArramt()); } } }
        						 */
                                tbBillDet.add(det);
                                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND) || taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
                                    totalDemand += det.getBdCurBalTaxamt();
                                } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
                                    totalInterest += det.getBdCurBalTaxamt();
                                } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                                    totalPenlty += det.getBdCurBalTaxamt();
                                }
                            }
                        }
                    }
                    bill.setBmToatlInt(Math.abs(totalInterest));
                    if (totalDemand > 0) {
                        bill.setBmTotalBalAmount(Math.abs(totalDemand));
                    } else {
                        bill.setBmTotalBalAmount(0.0);
                    }
                    bill.setTotalPenalty(Math.abs(totalPenlty));
                    totalDemand = 0d;
                    totalInterest = 0d;
                    totalPenlty = 0d;

                    if (lastBillMas == null) {
                        bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
                    } else {
                        bill.setBmTotalCumIntArrears(lastBillMas
                                .getBmTotalCumIntArrears() + bill.getBmToatlInt());
                        if (lastBillMas.getBmDuedate() != null && UtilityService.compareDate(
                                lastBillMas.getBmDuedate(), bill.getBmBilldt())) {
                            bill.setBmTotalArrearsWithoutInt(
                                    lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
                        }
                    }
                    bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
                            + bill.getBmTotalCumIntArrears());
                    bill.setBmTotalOutstanding(bill.getBmTotalArrears()
                            + bill.getBmTotalBalAmount());

                    if(checkIfWaterDeptAndSkdclOrg(bill, org)) {
                    	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
                            bill.setBmPaidFlag(constantYES);
                    	}
                    }else if (bill.getBmTotalOutstanding() == 0d && bill.getTotalPenalty() == 0d) {// #12562
                        bill.setBmPaidFlag(constantYES);
                    }

                    bill.setTbWtBillDet(tbBillDet);
                    lastBillMas = bill;
                }
                // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
                // listBill.get(size - 1).setBmLastRcptamt(amount);
                billMasList.get(size - 1).setBmLastRcptdt(new Date());
        	}
		}
        listBill.get(listBill.size() - 1).setExcessAmount(detAmount);
        return billPosting;
    }
	
	private Boolean checkIfWaterDeptAndSkdclOrg(TbBillMas bill, Organisation org) {
		 Boolean isWaterDeptSkdclOrg = false;
         if(bill.getTbWtBillDet()!=null && bill.getTbWtBillDet().size()>0 && bill.getTbWtBillDet().get(0).getTaxId()!=null) {
         	TbTaxMas findById = tbTaxMasService.findById(bill.getTbWtBillDet().get(0).getTaxId(), org.getOrgid());
         	if(findById!=null) {
         		String deptCode = departmentService.getDeptCode(findById.getDpDeptId());
                 isWaterDeptSkdclOrg = Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && MainetConstants.WATER_DEPT.equals(deptCode) ?
                 		true : false;
         	}
         }
         if(isWaterDeptSkdclOrg) {
         	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
                 bill.setBmPaidFlag(constantYES);
         	}
         }		
         return isWaterDeptSkdclOrg;
	}

	@Override
	public double calculatePenaltyInterestForBillGen(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
			Date manualDate, String billGen,String paymentFlag, Long userId,List<TbBillMas> totalBillList) {
		TbBillMas lastBillMas = listBill.get(listBill.size() - 1);
		double intOnarrear = 0;
		double intOnCur = 0;
		double intOnCurrentYear = 0;
		double totalInt = 0;
		boolean interestTaxexist = false;
		Date currDate = new Date();
		if(manualDate != null) {
			currDate = manualDate;
		}
		AtomicDouble arrearAmount = new AtomicDouble(0);
		AtomicDouble currentAmount = new AtomicDouble(0);
		AtomicDouble actualCurrentBill = new AtomicDouble(0);
		AtomicDouble totalOutStanding = new AtomicDouble(0);
		boolean compoundedIntApplicable = false;
		if(Utility.compareDate(lastBillMas.getIntTo(), lastBillMas.getBmTodt()) && Utility.compareDate(lastBillMas.getBmTodt(), currDate)) {
			compoundedIntApplicable = true;
		}
		int arrearPeriod = 0;
		int currentFirstSemPeriod = 0;
		int currentSecondSemPeriod = 0;
		LookUp penalRoundOff  = null;
    	try {
    		penalRoundOff = CommonMasterUtility.getValueFromPrefixLookUp("PMR", "ENV", org);
    	}catch (Exception e) {
    		LOGGER.info("No Prefix found for ENV(PMR)");
		}
    	LookUp calculatePenaltyOnCurrBill  = null;
    	try {
    		calculatePenaltyOnCurrBill = CommonMasterUtility.getValueFromPrefixLookUp("CPC", "ENV", org);
    	}catch (Exception e) {
    		LOGGER.info("No Prefix found for ENV(CPC)");
		}
    	boolean penalRoundOffReq = false;
    	boolean calculatePenaltyOnCurrBillFlag = false;
    	if(calculatePenaltyOnCurrBill != null && StringUtils.equals(MainetConstants.FlagY,calculatePenaltyOnCurrBill.getOtherField())) {
    		calculatePenaltyOnCurrBillFlag = true;
    	}
    	if(penalRoundOff != null && StringUtils.isNotBlank(penalRoundOff.getOtherField()) && StringUtils.equals(penalRoundOff.getOtherField(), MainetConstants.FlagY)) {
    		penalRoundOffReq = true;
    	}
		lastBillMas.getTbWtBillDet().forEach(billDet -> {
			if(Utility.compareDate(lastBillMas.getIntTo(), lastBillMas.getBmTodt())) {
				arrearAmount.addAndGet(billDet.getBdPrvBalArramt());
				currentAmount.addAndGet(billDet.getBdCurBalTaxamt());
				actualCurrentBill.addAndGet(billDet.getBdCurTaxamt());
				totalOutStanding.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
			}else {
				arrearAmount.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
				totalOutStanding.addAndGet(billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
			}
		});
		double currentFirstSemAmount = actualCurrentBill.doubleValue() / 2;
		double currentSecondSemAmount = actualCurrentBill.doubleValue();
		double currPaidBill = actualCurrentBill.doubleValue() - currentAmount.doubleValue();
		if(currPaidBill > 0) {
			currentFirstSemAmount = currentFirstSemAmount - currPaidBill;
			if(currentFirstSemAmount < 0) {
				currentFirstSemAmount = 0;
			}
			currentSecondSemAmount = currentSecondSemAmount - currPaidBill;
		}
		if (StringUtils.equals(billGen, MainetConstants.FlagY)) {

		} else {
			if(Utility.comapreDates(lastBillMas.getIntTo(), currDate)) {
				arrearPeriod = 0;
			}else {
				if(compoundedIntApplicable) {
					arrearPeriod = calculateMonths(lastBillMas.getIntTo(), lastBillMas.getBmTodt());
				}else {
					arrearPeriod = calculateMonths(lastBillMas.getIntTo(), currDate);
				}
					
			}
		}
		
		intOnarrear = getIntAmount(arrearAmount.doubleValue(), arrearPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
		
		Date firstSemDueDate = getCurrentFirstHalfDueDate(lastBillMas.getBillDistrDate(), org);
		Date secondSemDueDate = getCurrentSecondHalfDueDate(org);
		if(lastBillMas.getBillDistrDate() != null && Utility.compareDate(firstSemDueDate, lastBillMas.getIntTo())) {
			firstSemDueDate = lastBillMas.getIntTo();
		}
		if(Utility.compareDate(secondSemDueDate, lastBillMas.getIntTo())) {
			secondSemDueDate = lastBillMas.getIntTo();
		}
		double intOnFirstSem = 0;
		if (firstSemDueDate != null && secondSemDueDate != null) {
			if(Utility.comapreDates(firstSemDueDate, new Date())) {
				currentFirstSemPeriod = 0;
			}else {
				if(Utility.compareDate(new Date(), secondSemDueDate)) {
					currentFirstSemPeriod = calculateMonths(firstSemDueDate, new Date());
				}else {
					currentFirstSemPeriod = calculateMonths(firstSemDueDate, secondSemDueDate);
				}
			}
			
			 intOnFirstSem = getIntAmount(currentFirstSemAmount, currentFirstSemPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
			
		}
		if ((secondSemDueDate != null && Utility.compareDate(secondSemDueDate, currDate))
				&& (calculatePenaltyOnCurrBillFlag
						|| (!calculatePenaltyOnCurrBillFlag && lastBillMas.getBillDistrDate() != null))) {
			if(Utility.comapreDates(secondSemDueDate, new Date())) {
				currentSecondSemPeriod = 0;
			}else {
				if(compoundedIntApplicable) {
					currentSecondSemPeriod = calculateMonths(secondSemDueDate, lastBillMas.getBmTodt());
				}else {
					currentSecondSemPeriod = calculateMonths(secondSemDueDate, currDate);
				}
			}
			double intOnCurrSecondSem = getIntAmount(currentSecondSemAmount, currentSecondSemPeriod, lastBillMas.getBmIntValue(), penalRoundOffReq);
			intOnCur = intOnFirstSem + intOnCurrSecondSem;
		}
		totalInt = Math.round((intOnCur + intOnarrear));
		if (compoundedIntApplicable) {
			double currentYearPeriod = 0;
			currentYearPeriod = calculateMonths(lastBillMas.getBmTodt(), currDate);
			intOnCurrentYear = getIntAmount(totalOutStanding.doubleValue() + totalInt, currentYearPeriod, lastBillMas.getBmIntValue(),
					penalRoundOffReq);
		}
		
		totalInt = Math.round((totalInt + intOnCurrentYear));
		if(totalInt < 0) {
			totalInt = 0;
		}
		if(totalInt > 0 && StringUtils.isNotBlank(paymentFlag) && StringUtils.equals(paymentFlag, MainetConstants.FlagY)) {
			TbBillMas totalLastBillMas = totalBillList.get(totalBillList.size() - 1);
			for (final TbBillDet billDet : totalLastBillMas.getTbWtBillDet()) {
				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
						.getLookUpCode();
				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
					billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + totalInt);
					billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + totalInt);
					totalLastBillMas.setBmToatlInt(totalLastBillMas.getBmToatlInt() + totalInt);
					totalBillList.get(totalBillList.size() - 1).setBmTotalOutstanding(totalBillList.get(totalBillList.size() - 1).getBmTotalOutstanding() + totalInt);
					interestTaxexist = true;
				}
			}
			totalBillList.forEach(billMas ->{
				billMas.setIntFrom(billMas.getIntTo());
				Date currentData = new Date();
				if(manualDate != null) {
					currentData = manualDate;
				}
				LocalDate currLocalDate = currentData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
                Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
				billMas.setIntTo(date);
			});
		}
		if(totalInt > 0 && StringUtils.isNotBlank(paymentFlag) && StringUtils.equals(paymentFlag, MainetConstants.FlagY) && !interestTaxexist) {
			TbBillMas totalLastBillMas = totalBillList.get(totalBillList.size() - 1);
			TbBillDet intTax = new TbBillDet();
			intTax.setBmIdno(totalLastBillMas.getBmIdno());
			 final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
	                    PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
	                    org);
			 LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("I",
						PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid());
				LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("MI",
						PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
			Long intTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), org.getOrgid(),
					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
			TbTaxMas intTaxMas = tbTaxMasService.findById(intTaxId, org.getOrgid());
			final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                    MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                    null);
			intTax.setTaxId(intTaxId);
			intTax.setBdCurTaxamt(totalInt);
			intTax.setBdCurBalTaxamt(totalInt);
			intTax.setTaxCategory(intTaxMas.getTaxCategory1());
			intTax.setCollSeq(intTaxMas.getCollSeq());
			intTax.setLmoddate(new Date());
			intTax.setUserId(userId);
			intTax.setBdBilldetid(bdBilldetid);
			totalBillList.get(totalBillList.size() - 1).setBmTotalOutstanding(totalBillList.get(totalBillList.size() - 1).getBmTotalOutstanding() + totalInt);
			totalBillList.get(totalBillList.size() - 1).getTbWtBillDet().add(intTax);
			
		}
		return totalInt;
	}
	
	
	@Override
    public List<AdjustmentBillDetailMappingEntity> doAdjustmentForNewBill(final List<TbBillMas> listBill,
            final List<AdjustmentMasterEntity> adjustmentEntity) {
        final List<AdjustmentBillDetailMappingEntity> mappingList = new ArrayList<>(0);
        TbBillMas billMas = listBill.get(listBill.size()-1);
        List<TbBillDet> tbWtBillDet = billMas.getTbWtBillDet();
        List<TbBillDet> previous = null;
            
        for(AdjustmentMasterEntity adjMaster: adjustmentEntity) {
            final AtomicDouble amountAdjusted = new AtomicDouble();
            for(AdjustmentDetailEntity detail: adjMaster.getAdjDetail()) {
            	//Choose only those entries whose adjustment is done yet
            	if(MainetConstants.MENU.N.equals(detail.getAdjAdjustedFlag())) {
                    AdjustmentBillDetailMappingEntity mapping = null;
                    for (TbBillDet det : tbWtBillDet) {
                      if (det.getTaxId().equals(detail.getTaxId())) {
                          if (MainetConstants.MENU.P.equals(adjMaster.getAdjType())) {
                              det.setBdCurBalTaxamt(detail.getAdjBalanceAmount() + det.getBdCurBalTaxamt());
                              detail.setAdjBalanceAmount(0d);    
                              detail.setAdjAdjustedFlag(constantYES);
                              detail.setAdjAdjustedAmount(detail.getAdjAmount());
                              amountAdjusted.addAndGet(detail.getAdjAdjustedAmount());

                              mapping = new AdjustmentBillDetailMappingEntity();
                              mapping.setAdjAmount(detail.getAdjAdjustedAmount());
                              mapping.setAdjId(detail.getAdjDetId());
                              mapping.setAdjbmId(det.getBdBilldetid());
                              mapping.setOrgId(adjMaster.getOrgId());
                              mapping.setDpDeptId(adjMaster.getDpDeptId());
                              mapping.setLgIpMac(adjMaster.getLgIpMac());
                              mapping.setCreatedBy(adjMaster.getCreatedBy());
                              mapping.setCreatedDate(new Date());
                              mapping.setTaxId(det.getTaxId());
                              mappingList.add(mapping);
                          }
                      }
					}
                }
            }
            if (amountAdjusted.doubleValue() > 0d) {
                if (MainetConstants.MENU.P.equals(adjMaster.getAdjType())) {
                    billMas.setBmTotalBalAmount(billMas.getBmTotalBalAmount() + amountAdjusted.doubleValue());
                    billMas.setBmTotalOutstanding(billMas.getBmTotalOutstanding() + amountAdjusted.doubleValue());
                }
            }
        
        }
        final List<TbBillDet> current = billMas.getTbWtBillDet();
        if ((current != null) && !current.isEmpty()) {
            for (final TbBillDet curdet : current) {
                if (previous != null) {
                    for (final TbBillDet arrdet : previous) {
                        if (curdet.getTaxId().longValue() == arrdet.getTaxId().longValue()) {
                            curdet.setBdPrvBalArramt(arrdet.getBdPrvBalArramt() + arrdet.getBdCurBalTaxamt());
                            curdet.setBdPrvArramt(curdet.getBdPrvBalArramt());
                        }
                    }
                }
            }
            previous = current;
        }
        adjustmentEntryService.updateAdjustedAdjustmentData(adjustmentEntity);
        return mappingList;
    }

	@Override
	public CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO2, Long empId, Long orgId,
			Long deptId, String amount) throws BeansException, IOException, InterruptedException {
		
		offlineDTO2.setOrgId(orgId);
		offlineDTO2.setAmountToPay(amount);
		offlineDTO2.setDeptId(deptId);
		offlineDTO2.setUserId(empId);
		final CommonChallanDTO master = ApplicationContextProvider.getApplicationContext()
				.getBean(IChallanService.class).callPushToPayApiForPayment(offlineDTO2);

		return master;
	}

	@Override
	public void updateMappingTable(TbBillDet billDet, AdjustmentBillDetailMappingEntity mappingEntity, Long orgId) {
		try {
	        adjustmentBillDetailMappingRepository.update(mappingEntity.getAdjId(), billDet.getBdBilldetid(), orgId);    		
		}catch(Exception ex) {
			LOGGER.error("Could not store bill det id for which adjustment was done" + ex.getMessage() );
		}
	}
	
	 @Override
	 public void updateArrearInCurrentBillsForNewBillGenertaionForChangInAss(List<TbBillMas> billMasList) {
	        TbBillMas previousBillMas = null;
	        for (final TbBillMas currBillMas : billMasList) {
	            if (previousBillMas != null) {
	                currBillMas.setBmTotalArrearsWithoutInt(
	                        previousBillMas.getBmTotalBalAmount() + previousBillMas.getBmTotalArrearsWithoutInt());
	                currBillMas.setBmActualArrearsAmt(
	                        previousBillMas.getBmTotalBalAmount() + previousBillMas.getBmTotalArrearsWithoutInt());
	            }

	            if (previousBillMas != null) {

	                currBillMas.setBmTotalCumIntArrears(
	                        previousBillMas.getBmTotalCumIntArrears()
	                                + currBillMas.getBmToatlInt());
	            } else {
	                currBillMas.setBmTotalCumIntArrears(
	                        currBillMas.getBmToatlInt());
	            }

	            currBillMas.setBmTotalArrears(
	                    currBillMas.getBmTotalCumIntArrears()
	                            + currBillMas.getBmTotalArrearsWithoutInt());

	            currBillMas.setBmTotalOutstanding(
	                    currBillMas.getBmTotalArrears()
	                            + currBillMas.getBmTotalBalAmount());
	            if ((currBillMas.getTbWtBillDet() != null) && !currBillMas.getTbWtBillDet().isEmpty()
	                    && (previousBillMas != null)) {
	                for (final TbBillDet det : currBillMas.getTbWtBillDet()) {
	                    for (final TbBillDet lastDet : previousBillMas.getTbWtBillDet()) {
	                        if (det.getTaxId().equals(lastDet.getTaxId())) {
	                            det.setBdPrvBalArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
	                            // previous year bill but generating with current bill
	                            /*
	                             * if ((previousBillMas.getCurrentBillFlag() != null &&
	                             * previousBillMas.getCurrentBillFlag().equals(MainetConstants.Y_FLAG))) {//
	                             * det.setArrAmtOfNewBill(lastDet.getArrAmtOfNewBill() + lastDet.getBdCurTaxamt()); } else {
	                             * det.setArrAmtOfNewBill(lastDet.getArrAmtOfNewBill() + lastDet.getBdCurBalTaxamt()); }
	                             */
	                            if (currBillMas.getBmIdno() > 0 && det.getBdPrvArramt() <= 0) {
	                                det.setBdPrvArramt(lastDet.getBdCurBalTaxamt() + lastDet.getBdPrvBalArramt());
	                            }
	                        }
	                    }
	                }
	            }
	            previousBillMas = currBillMas;
	        }
	    }

	 @Override
	    public List<BillReceiptPostingDTO> updateBillDataWithoutInterest(final List<TbBillMas> listBill,
	            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
	            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate) {
	        List<BillReceiptPostingDTO> billPosting = new ArrayList<>();
	        final int size = listBill.size(); //3
	        final LocalDate date = LocalDate.now();
	        Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        if(manualReceptDate != null) {
	        	duedate = manualReceptDate;
	        }
	        if (details == null) {
	            details = new HashMap<>(0);
	        }
	        if (billDetails == null) {
	            billDetails = new HashMap<>(0);
	        }
	        BillReceiptPostingDTO billUpdate = null;
	        final double amount = paidAmount;
	        double detAmount = paidAmount;
	        double rebateAmount = 0d;
	        double totalDemand = 0d;
	        double totalInterest = 0d;
	        double totalPenlty = 0d;
	        int count = 0;

	        TbBillMas lastBillMas = null;
	        List<TbBillDet> tbBillDet = null;
	        // priority
	        for (final TbBillMas bill : listBill) {
	            count++;
	            if (bill.getBmDuedate() != null && UtilityService.compareDate(duedate, bill.getBmDuedate())) {
	                if (bill.getBmToatlRebate() > 0d) {

	                    // This condition is failing in case af arrears. At any how we are checking condition while calling rebate ->
	                    // by srikanth 77411

	                    // final double totalAmountPayable = detAmount + bill.getBmToatlRebate();
	                    rebateAmount = bill.getBmToatlRebate();
	                    /*
	                     * if (totalAmountPayable >= bill.getBmTotalOutstanding()) { paidAmount += bill.getBmToatlRebate();
	                     * rebateAmount = bill.getBmToatlRebate(); }
	                     */
	                }
	            }
	            tbBillDet = new ArrayList<>(0);
	            if ((bill.getTbWtBillDet() != null)
	                    && !bill.getTbWtBillDet().isEmpty()) {
	                for (final TbBillDet det : bill.getTbWtBillDet()) {
	                    billUpdate = new BillReceiptPostingDTO();
	                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), org).getLookUpCode();
	                    if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE) && !taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
	                        Double taxAmount = null;
	                        Double value = null;
	                        Double rebatePaid = null;
	                        Double initialBalAmount = null;
	                        initialBalAmount = det.getBdCurBalTaxamt();
	                        billUpdate.setPayableAmount(det.getBdCurBalTaxamt());
	                        billUpdate.setTaxCategory(det.getTaxCategory());
	                        billUpdate.setBillDate(bill.getBmBilldt());
	                        billUpdate.setBmIdNo(bill.getBmNo());
	                        billUpdate.setBillNO(bill.getBmNo());
	                        billUpdate.setTaxCategoryCode(taxCode);
	                        billUpdate.setTotalDetAmount(initialBalAmount);
	                        billUpdate.setTaxId(det.getTaxId());
	                        billUpdate.setDisplaySeq(det.getDisplaySeq());
	                        billUpdate.setYearId(bill.getBmYear());
	                        billUpdate.setBillFromDate(bill.getBmFromdt());
	                        billUpdate.setBillToDate(bill.getBmTodt());

	                        // billUpdate.setBillDetId(det.getBdBilldetid());
	                        if (det.getBdBilldetid() == 0) {
	                            billUpdate.setBillDetId(det.getDummyDetId());
	                        } else {
	                            billUpdate.setBillDetId(det.getBdBilldetid());
	                        }
	                        billUpdate.setYearId(bill.getBmYear());
	                        if (bill.getBmIdno() == 0) {
	                            billUpdate.setBillMasId(bill.getDummyMasId());
	                        } else {
	                            billUpdate.setBillMasId(bill.getBmIdno());

	                        }

	                        if (count != size) {
	                            billUpdate.setArrearAmount(initialBalAmount);
	                        }
	                        billPosting.add(billUpdate);

	                        // Defect Id -> 77411 done by srikanth
	                        if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d && MapUtils.isNotEmpty(bill.getTaxWiseRebate())) {
	                            Map<Long, List<Double>> rebateDet = new HashMap<>();
	                            TbTaxMas taxMas = tbTaxMasService.findById(det.getTaxId(), org.getOrgid());
	                            List<Double> taxvalueIdList = bill.getTaxWiseRebate().get(taxMas.getTaxCode());
	                            if (CollectionUtils.isNotEmpty(taxvalueIdList)) {
	                                rebateAmount = rebateAmount - taxvalueIdList.get(0);
	                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(taxvalueIdList.get(0));
	                                det.setBdCurBalTaxamt(Math.abs(rebatePaid));
	                                List<Double> detTaxIds = new ArrayList<Double>();
	                                detTaxIds.add(taxvalueIdList.get(0));
	                                detTaxIds.add(taxvalueIdList.get(1));
	                                rebateDet.put(det.getBdBilldetid(), detTaxIds);
	                                rebateDetails.add(rebateDet);
	                            }
	                        } else if (rebateAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
	                            Map<Long, List<Double>> rebateDet = new HashMap<>();
	                            rebateAmount = rebateAmount - det.getBdCurBalTaxamt();
	                            if (rebateAmount >= 0d) {
	                                rebatePaid = det.getBdCurBalTaxamt();
	                                det.setBdCurBalTaxamt(0);
	                            } else {
	                                rebatePaid = det.getBdCurBalTaxamt() - Math.abs(rebateAmount);
	                                det.setBdCurBalTaxamt(Math.abs(rebateAmount));
	                            }
	                            List<Double> detTaxIds = new ArrayList<Double>();
	                            detTaxIds.add(rebateAmount);
	                            rebateDet.put(det.getBdBilldetid(), detTaxIds);
	                            rebateDetails.add(rebateDet);
	                        }
	                        if (detAmount > 0d && det.getBdCurBalTaxamt() > 0d) {
	                        	billUpdate.setYearId(bill.getBmYear());
	                        	billUpdate.setBillFromDate(bill.getBmFromdt());
	                            billUpdate.setBillToDate(bill.getBmTodt());
	                            value = details.get(det.getTaxId());
	                            detAmount = detAmount - det.getBdCurBalTaxamt();
	                            if (value != null) {
	                                if (detAmount >= 0d) {
	                                    taxAmount = det.getBdCurBalTaxamt();
	                                    value += (det.getBdCurBalTaxamt());
	                                    det.setBdCurBalTaxamt(0);
	                                } else {
	                                    taxAmount = det.getBdCurBalTaxamt() - Math.abs(detAmount);
	                                    value += (det.getBdCurBalTaxamt() - Math.abs(detAmount));
	                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
	                                }
	                            } else {
	                                if (detAmount >= 0d) {
	                                    value = det.getBdCurBalTaxamt();
	                                    taxAmount = value;
	                                    det.setBdCurBalTaxamt(0);
	                                } else {
	                                    value = (det.getBdCurBalTaxamt() - Math.abs(detAmount));
	                                    taxAmount = value;
	                                    det.setBdCurBalTaxamt(Math.abs(detAmount));
	                                }
	                            }
	                            details.put(det.getTaxId(), value);
	                            billDetails.put(det.getTaxId(), det.getBdBilldetid());
	                            // billUpdate.setBillDetId(det.getBdBilldetid());
	                            /*
	                             * if (det.getBdBilldetid() == 0) { billUpdate.setBillDetId(det.getDummyDetId()); } else {
	                             * billUpdate.setBillDetId(det.getBdBilldetid()); }
	                             */
	                            // billUpdate.setTaxId(det.getTaxId());
	                            if(Utility.isEnvPrefixAvailable(org, "SKDCL")) {
	                            	billUpdate.setTaxAmount(taxAmount);
	                            }else {
	                            	billUpdate.setTaxAmount(Math.round(taxAmount));
	                            }

	                            /*
	                             * billUpdate.setYearId(bill.getBmYear()); if (bill.getBmIdno() == 0) {
	                             * billUpdate.setBillMasId(bill.getDummyMasId()); } else { billUpdate.setBillMasId(bill.getBmIdno());
	                             * }
	                             */
	                            /*
	                             * billUpdate.setTaxCategory(det.getTaxCategory()); billUpdate.setBillDate(bill.getBmBilldt());
	                             * billUpdate.setBmIdNo(bill.getBmNo()); billUpdate.setTotalDetAmount(initialBalAmount); if (count !=
	                             * size) { billUpdate.setArrearAmount(initialBalAmount); } billPosting.add(billUpdate);
	                             */
	                        }
	                        if ((lastBillMas != null) && (lastBillMas.getTbWtBillDet() != null)) {
	                            for (final TbBillDet lastdet : lastBillMas.getTbWtBillDet()) {
	                                if (det.getTaxId().equals(lastdet.getTaxId())) {
	                                    det.setBdPrvBalArramt(lastdet.getBdCurBalTaxamt() + lastdet.getBdPrvBalArramt());
	                                }
	                            }
	                        }
	                        tbBillDet.add(det);
	                        if (taxCode.equals(PrefixConstants.TAX_CATEGORY.DEMAND) || taxCode.equals(PrefixConstants.TAX_CATEGORY.NOTICE)) {
	                            totalDemand += det.getBdCurBalTaxamt();
	                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
	                            totalInterest += det.getBdCurBalTaxamt();
	                        } else if (taxCode.equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
	                            totalPenlty += det.getBdCurBalTaxamt();
	                        }
	                    }else if(taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
	                    	tbBillDet.add(det);
	                    }
	                }
	            }
	            bill.setBmToatlInt(Math.abs(totalInterest));
	            if (totalDemand > 0) {
	                bill.setBmTotalBalAmount(Math.abs(totalDemand));
	            } else {
	                bill.setBmTotalBalAmount(0.0);
	            }
	            bill.setTotalPenalty(Math.abs(totalPenlty));
	            totalDemand = 0d;
	            totalInterest = 0d;
	            totalPenlty = 0d;

	            if (lastBillMas == null) {
	                bill.setBmTotalCumIntArrears(bill.getBmToatlInt());
	            } else {
	                bill.setBmTotalCumIntArrears(lastBillMas
	                        .getBmTotalCumIntArrears() + bill.getBmToatlInt());
	                if (lastBillMas.getBmDuedate() != null && UtilityService.compareDate(
	                        lastBillMas.getBmDuedate(), bill.getBmBilldt())) {
	                    bill.setBmTotalArrearsWithoutInt(
	                            lastBillMas.getBmTotalArrearsWithoutInt() + lastBillMas.getBmTotalBalAmount());
	                }
	            }
	            bill.setBmTotalArrears(bill.getBmTotalArrearsWithoutInt()
	                    + bill.getBmTotalCumIntArrears());
	            bill.setBmTotalOutstanding(bill.getBmTotalArrears()
	                    + bill.getBmTotalBalAmount());
	            if(checkIfWaterDeptAndSkdclOrg(bill, org)) {
	            	if (bill.getBmTotalBalAmount() == 0d && bill.getTotalPenalty() == 0d) {
	                    bill.setBmPaidFlag(constantYES);
	            	}
	            }else if (bill.getBmTotalOutstanding() == 0d && bill.getTotalPenalty() == 0d) {// #12562
	                bill.setBmPaidFlag(constantYES);
	            }

	            bill.setTbWtBillDet(tbBillDet);
	            lastBillMas = bill;
	        }
	        // Defect #34189 Korba Early payment discount, below code commented after discussion with Rajesh sir
	        // listBill.get(size - 1).setBmLastRcptamt(amount);
	        listBill.get(size - 1).setBmLastRcptdt(new Date());
	        listBill.get(size - 1).setExcessAmount(detAmount);
	        return billPosting;
	    }

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
	        
	        updateArrearInCurrentBills(billMasDtoList);
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
	public void calculateInterestForPrayagRaj(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
			Date manualDate, Long userId, List<TbBillMas> billMasArrears,String billGenFlag) {
		if (CollectionUtils.isNotEmpty(billMasArrears)) {
			Date toDate = new Date();
			if (manualDate != null) {
				toDate = manualDate;
			}
			TbBillMas lastBillMas = listBill.get(listBill.size() - 1);
			double bmIntValue = lastBillMas.getBmIntValue();
			TbBillMas arrearLastBill = billMasArrears.get(billMasArrears.size() - 1);
			AtomicDouble totalInt = new AtomicDouble(0);
			boolean interestTaxexist = false;
			TbTaxMas intersetTaxMas = getInterestTaxMaster(org, deptId);
			boolean includeCurrAmount = false;
			if (Utility.compareDate(arrearLastBill.getBmDuedate(), arrearLastBill.getIntTo())) {
				includeCurrAmount = true;
			}

			List<TbBillDet> interestDependentTax = arrearLastBill.getTbWtBillDet().stream()
					.filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(interestDependentTax)) {
				double arrearBalAmount = interestDependentTax.get(0).getBdPrvBalArramt();
				double currentBalAmnt = interestDependentTax.get(0).getBdCurBalTaxamt();
				if (includeCurrAmount) {
					arrearBalAmount = arrearBalAmount + interestDependentTax.get(0).getBdCurBalTaxamt();
					int arrearPeriod = getPeriod(arrearLastBill.getIntTo(), toDate);
					double arrearInt = arrearBalAmount * arrearPeriod * bmIntValue;
					if (arrearInt > 0) {
						totalInt.addAndGet(arrearInt);
					}
				} else {
					int arrearPeriod = getPeriod(arrearLastBill.getIntTo(), toDate);
					double arrearInt = arrearBalAmount * arrearPeriod * bmIntValue;
					if (arrearInt > 0) {
						totalInt.addAndGet(arrearInt);
					}
					int currentPeriod = getPeriod(arrearLastBill.getBmDuedate(), toDate);
					double currentInt = currentBalAmnt * currentPeriod * bmIntValue;
					if (currentInt > 0) {
						totalInt.addAndGet(currentInt);
					}
				}
			}

			if (totalInt.doubleValue() > 0) {
				for (TbBillMas billMas : listBill) {
					billMas.setIntFrom(billMas.getIntTo());
					LocalDate currLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate monthEnd = currLocalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);
					Date date = Date.from(monthEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
					billMas.setIntTo(date);
				}
			}
			updateInterestInBills(listBill, org, deptId, manualDate, userId, lastBillMas, totalInt, interestTaxexist,billGenFlag);

		}

	}

	private TbTaxMas getInterestTaxMaster(Organisation org, Long deptId) {
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);
		LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("I", PrefixConstants.LookUpPrefix.TAC,
				1, org.getOrgid());
		LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("MI",
				PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
		Long intTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), org.getOrgid(), deptId,
				taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
		TbTaxMas intersetTaxMas = tbTaxMasService.findById(intTaxId, org.getOrgid());
		return intersetTaxMas;
	}

	private void updateInterestInBills(List<TbBillMas> listBill, Organisation org, Long deptId, Date manualDate,
			Long userId, TbBillMas lastBillMas, AtomicDouble totalInt, boolean interestTaxexist,String billGenFlag) {
		double totalInterest = Math.ceil(totalInt.doubleValue());
		if(totalInterest > 0) {
			for (final TbBillDet billDet : lastBillMas.getTbWtBillDet()) {
				final String taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org)
						.getLookUpCode();
				if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
					billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + totalInterest);
					billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + totalInterest);
					if(StringUtils.equals(MainetConstants.FlagY, billGenFlag)) {
						billDet.setBillGenPenalty(totalInterest);
					}
					lastBillMas.setBmToatlInt(lastBillMas.getBmToatlInt() + totalInterest);
					listBill.get(listBill.size() - 1).setBmTotalOutstanding(listBill.get(listBill.size() - 1).getBmTotalOutstanding() + totalInterest);
					interestTaxexist = true;
				}
			}
			
		}
		
		if(totalInterest > 0  && !interestTaxexist) {
			TbBillDet intTax = new TbBillDet();
			intTax.setBmIdno(lastBillMas.getBmIdno());
			 final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
	                    PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
	                    org);
			 LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("I",
						PrefixConstants.LookUpPrefix.TAC, 1, org.getOrgid());
				LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("MI",
						PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
			Long intTaxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), org.getOrgid(),
					deptId, taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());
			TbTaxMas intTaxMas = tbTaxMasService.findById(intTaxId, org.getOrgid());
			final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
                    MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
                    null);
			intTax.setTaxId(intTaxId);
			intTax.setBdCurTaxamt(totalInterest);
			intTax.setBdCurBalTaxamt(totalInterest);
			intTax.setTaxCategory(intTaxMas.getTaxCategory1());
			intTax.setCollSeq(intTaxMas.getCollSeq());
			intTax.setLmoddate(new Date());
			intTax.setUserId(userId);
			if(StringUtils.equals(MainetConstants.FlagY, billGenFlag)) {
				intTax.setBillGenPenalty(totalInterest);
			}
			intTax.setBdBilldetid(bdBilldetid);
			listBill.get(listBill.size() - 1).setBmTotalOutstanding(listBill.get(listBill.size() - 1).getBmTotalOutstanding() + totalInterest);
			listBill.get(listBill.size() - 1).getTbWtBillDet().add(intTax);
			
		}
	}

	private int getPeriod(Date fromDate, Date toDate) {
		if(fromDate == null) {
			return 0;
		}
		 int period = Utility.calculateMonthsBetweenTwoDates(fromDate, toDate);
		 return period;
	}
}
