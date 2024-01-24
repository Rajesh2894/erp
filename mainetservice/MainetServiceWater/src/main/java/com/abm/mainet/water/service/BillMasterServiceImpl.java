package com.abm.mainet.water.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bill.repository.AdjustmentBillDetailMappingRepository;
import com.abm.mainet.bill.repository.AdjustmentEntryRepository;
import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillGenerationService;
import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.bill.service.ChequeDishonorService;
import com.abm.mainet.cfc.challan.domain.AdjustmentBillDetailMappingEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentDetailEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dto.BillDetailsResponse;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.BillTaxDetailsResponse;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.TbRcptDet;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
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
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.dao.MeterDetailEntryJpaRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.dao.WaterNoDuesCertificateDao;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWtBIllDetHist;
import com.abm.mainet.water.domain.TbWtBIllMasHist;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtBillScheduleDetailEntity;
import com.abm.mainet.water.domain.TbWtBillScheduleEntity;
import com.abm.mainet.water.domain.TbWtExcessAmt;
import com.abm.mainet.water.domain.TbWtExcessAmtHist;
import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillTaxDTO;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.repository.BillDetailJpaRepository;
import com.abm.mainet.water.repository.BillMasterJpaRepository;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtBillScheduleDetailJpaRepository;
import com.abm.mainet.water.repository.TbWtBillScheduleJpaRepository;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.repository.WaterPenaltyRepository;
import com.abm.mainet.water.rest.dto.WaterBillRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillResponseDTO;
import com.google.common.util.concurrent.AtomicDouble;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Rahul.Yadav
 *
 */
@Service(value = "WaterBillService")
@WebService(endpointInterface = "com.abm.mainet.water.service.BillMasterService")
@Api(value = "/waterBillService")
@Path("/waterBillService")
public class BillMasterServiceImpl implements BillMasterService, BillPaymentService, BillGenerationService, BillDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillMasterServiceImpl.class);

    @Resource
    private BillMasterJpaRepository billMasterJpaRepository;

    @Resource
    private BillDetailJpaRepository billDetailJpaRepository;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private AuditService auditService;

    @Autowired
    private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;

    @Autowired
    private ChequeDishonorService chequeDishonorService;

    @Resource
    private IChallanService iChallanService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Resource
    private BillMasterCommonService billMasterCommonService;

    @Resource
    private TbWtBillMasService tbWtBillMasService;

    @Resource
    private NewWaterRepository waterRepository;
    @Resource
    private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Resource
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IFinancialYearService financialYearService;

    @Autowired
    IWaterPenaltyService waterPenaltyService;

    @Autowired
    IOrganisationService organisationService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private BRMSWaterService brmsWaterService;

    @Autowired
    ISMSAndEmailService ismsAndEmailService;

    @Resource
    private ServiceMasterService serviceMasterService;
    @Autowired
    private TbWtBillScheduleJpaRepository billScheduleJpaRepository;
    @Autowired
    private TbCsmrInfoRepository tbCsmrInfoRepository;
    @Autowired
    private TbWtBillScheduleDetailJpaRepository tbWtBillScheduleDetailJpaRepository;
    
    @Resource
    private WaterPenaltyRepository waterPenaltyRepository;
    
    @Autowired
    private AdjustmentEntryRepository adjustmentEntryRepository;
    
    @Resource
    private AdjustmentBillDetailMappingRepository adjustmentBillDetailMappingRepository;
    
    @Resource
    private MeterDetailEntryJpaRepository meterDetailEntryJpaRepository;

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

    /**
     * @param meterReadingMap
     * @param waterDTO
     * @param lookUpVal
     * @param meterTypeLookup
     * @param dueDateDays
     * @param errorDto
     * @param interestValue
     * @param orgnisation
     * @param empId
     * @param langId
     * @param excessAmount
     * @param penaltytaxDto
     * @param taxCalMap2
     * @return
     */
    @Override
    @WebMethod(exclude = true)
    public List<TbWtBillMasEntity> billGeneartion(final List<BillTaxDTO> taxList, final BillTaxDTO billDateData,
            final TbCsmrInfoDTO waterDTO, final double interestValue, final String remarks,
            final Organisation orgnisation, final int langId, final Long empId, final TbWtExcessAmt excessAmount,
            final BillTaxDTO interestTaxDto, final Date dueDate, List<TbBillMas> listBill,
            final List<AdjustmentMasterEntity> adjustmentEntity, final Long finYearId, final String ipAddress) {

        taxList.sort(Comparator.comparing(BillTaxDTO::getTaxSequence));
        Date date = new Date();
        TbBillMas billMas = new TbBillMas();
        billMas.setCsIdn(waterDTO.getCsIdn());
        billMas.setBmYear(finYearId);
        billMas.setBmBilldt(date);
        billMas.setBmRemarks(remarks);
        billMas.setBmFromdt(billDateData.getMrdFrom());
        billMas.setBmTodt(billDateData.getMrdTo());
        billMas.setBmDuedate(dueDate);
        double totalAmount = 0d;
        double totalBillAmount = 0d;
        BillTaxDTO advanceTaxDto = null;
        final List<Long> adjustedTaxData = new ArrayList<>(0);
        Double adjustedAmount = 0d;
        double totalPenalty = 0d;
        // below for loop calculates total tax amount and total rebate amount
        // this will become micro service
        // Apply Rebate & Advance Adjustment - Interface
        Double rebateTaxAmount = 0d;

        for (final BillTaxDTO tax : taxList) {
            double taxamount = Math.round(tax.getTax());
            TbBillDet billDet = new TbBillDet();
            billDet.setBaseRate(tax.getBaseRate());
            billDet.setRuleId(tax.getRuleId());
            billDet.setBdCurBalTaxamt(taxamount);
            billDet.setBdCurTaxamt(taxamount);

            // Advance and Rebate not consider in Demand only Demand tax category should
            // have been use
            // Changed this to Demand rather checking Rebate and Advance
            if (PrefixConstants.TAX_CATEGORY.DEMAND.equals(tax.getTaxCategoryCodeValue())) {
                totalBillAmount += taxamount;
                totalAmount += taxamount;
                billMasterCommonService.setBillDetails(orgnisation, langId, empId, tax, billDet, ipAddress);
                billMas.getTbWtBillDet().add(billDet);
            } else if (PrefixConstants.TAX_CATEGORY.REBATE.equals(tax.getTaxCategoryCodeValue())) { // need to change
                // for rebate
                rebateTaxAmount += taxamount;
                if ((rebateTaxAmount != null) && (rebateTaxAmount > 0D)) {
                    adjustedTaxData.add(tax.getTaxId());
                }
            } else if (PrefixConstants.TAX_CATEGORY.ADVANCE.equals(tax.getTaxCategoryCodeValue())) {
                advanceTaxDto = tax;
                if ((excessAmount != null) && (excessAmount.getExcAmt() > 0d)) {
                    adjustedTaxData.add(tax.getTaxId());

                }

            }
            // Added more tax under category Penalty one would be surcharge but applicable
            // at
            // payment
            else if (PrefixConstants.TAX_CATEGORY.PENALTY.equals(tax.getTaxCategoryCodeValue())) {
                totalPenalty += taxamount;
                billMasterCommonService.setBillDetails(orgnisation, langId, empId, tax, billDet, ipAddress);
                billMas.getTbWtBillDet().add(billDet);
            }

        }
        // Apply Rebate if any at a time of bill generation
        if (rebateTaxAmount > 0d) {
            adjustedAmount += rebateTaxAmount;
            totalAmount = totalAmount - rebateTaxAmount;
        }
        // In case complete advance amount is not exhaust apply Advance Adjustment
        if ((excessAmount != null) && (excessAmount.getExcAmt() > 0d)) {
            TbBillDet billDet = new TbBillDet();
            billDet.setTaxCategory(advanceTaxDto.getTaxCategory());
            // here only total amount is getting adjusted and creating bill det for excess
            // amount
            totalAmount = adjustAdvancePayAmount(excessAmount, totalAmount, billDet);
            billMasterCommonService.setBillDetails(orgnisation, langId, empId, advanceTaxDto, billDet, ipAddress);
            billMas.getTbWtBillDet().add(billDet);
            // rebate + advance amount getting sum billDet.getBdCurTaxAmt is giving advance
            // amount
            adjustedAmount = adjustedAmount + billDet.getBdCurTaxamt();

        }

        if (adjustedAmount > 0) {
            // To Knock Off from details table
            // updateBillData
            List<TbBillMas> bills = new ArrayList<>();
            bills.add(billMas);
            billMasterCommonService.updateBillData(bills, adjustedAmount, null, null, orgnisation, null, null);
        }

        billMas.setBmTotalAmount(totalBillAmount);
        billMas.setBmTotalBalAmount(totalAmount);
        billMas.setBmTotalOutstanding(totalAmount);
        billMas.setBmIntValue(interestValue);
        if (totalPenalty != 0) {
            billMas.setTotalPenalty(totalPenalty);
        } else {
            billMas.setTotalPenalty(0);
        }

        // penalty column to be set
        if (totalAmount <= 0)
            billMas.setBmPaidFlag(MainetConstants.CommonConstants.Y);
        else
            billMas.setBmPaidFlag(MainetConstants.CommonConstants.N);
        billMas.setGenFlag(MainetConstants.CommonConstants.Y);
        billMas.setUserId(empId);
        billMas.setOrgid(orgnisation.getOrgid());
        billMas.setLmoddate(date);
        billMas.setLangId(langId);

        billMas.setLgIpMac(ipAddress);
        billMas.setIntFrom(date);
        billMas.setIntTo(dueDate);
        if (interestTaxDto != null) {
            TbBillDet billDet = new TbBillDet();
            billMasterCommonService.setBillDetails(orgnisation, langId, empId, interestTaxDto, billDet, ipAddress);
            // setting Interest Calculation Method
            LookUp taxSubCat = CommonMasterUtility.getHierarchicalLookUp(interestTaxDto.getTaxSubCategory(),
                    orgnisation);
            billMas.setInterstCalMethod(taxSubCat.getLookUpCode());
            billMas.getTbWtBillDet().add(billDet);
        }
        if (listBill != null && !listBill.isEmpty()) {
            listBill.add(billMas);
            if(billMas.getBmFromdt()!=null) {
              listBill.sort(Comparator.comparing(TbBillMas::getBmFromdt));
            }
            // Setting Interest Rate in each bill
               listBill.forEach(bill -> {
                bill.setBmIntValue(interestValue);
            });
            // Arrears and interest calculation
            billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(listBill);
            updateArrearsInbillDetailForNewTax(listBill, orgnisation.getOrgid());
            if (Utility.isEnvPrefixAvailable(orgnisation, "OTI")) {
                billMasterCommonService.calculateMultipleInterestForWater(listBill, orgnisation, null, "Y", null);
            } else {
                billMasterCommonService.calculateInterest(listBill, orgnisation, null, "Y", null);
            }
        } else {
            listBill = new ArrayList<>(0);
            listBill.add(billMas);
        }
        // Adjustment (+tive or -tive)
        List<AdjustmentBillDetailMappingEntity> mappingEntity = null;
        if (adjustmentEntity != null && !adjustmentEntity.isEmpty()) {
            
            if(!Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_SKDCL)) {
            	 mappingEntity = billMasterCommonService.doAdjustmentForNewBill(listBill, adjustmentEntity);
            	 if ((mappingEntity != null) && !mappingEntity.isEmpty()) {
                     billMasterCommonService.saveAndUpdateMappingTable(billMas.getTbWtBillDet(), mappingEntity);
                 }
            }
            
        }
        // Apply rebate if total outStanding is Zero after all adjustments and advance
        // knock off
        if (billMas != null && billMas.getBmTotalOutstanding() == 0d) {
            LookUp waterRebateAdvancePayment = null;
            try {
                waterRebateAdvancePayment = CommonMasterUtility.getValueFromPrefixLookUp("APR", "WRB", orgnisation);
            } catch (Exception exception) {
                LOGGER.error("No prefix found for WRB");
            }
            if (waterRebateAdvancePayment == null || waterRebateAdvancePayment.getOtherField().equals("0")) {
                applyRebateOnFullPay(orgnisation, empId, langId, waterDTO, billMas, ipAddress, excessAmount, dueDate);
            }
        }
        // create list of bill for updated interest and new bills created for save
        final List<TbWtBillMasEntity> tbWtBillMasEntityList = new ArrayList<>(0);
        for (TbBillMas billMasUpdated : listBill) {
            tbWtBillMasEntityList.add(create(billMasUpdated));
        }
        return tbWtBillMasEntityList;
    }

    private void applyRebateOnFullPay(final Organisation orgnisation, final Long empId, final int langId,
            final TbCsmrInfoDTO waterDTO, final TbBillMas billGenerated, String ipAddress, TbWtExcessAmt excessAmount,
            Date dueDate) {
         BillTaxDTO rebate = null ;
        if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_ASCL))
        {
        	rebate = tbWtBillMasService.getRebateAmountForPaymentByCnTyp(waterDTO, billGenerated, orgnisation, dueDate);
        }
        else
        {
        	rebate= tbWtBillMasService.getRebateAmountForPayment(waterDTO, billGenerated, orgnisation, dueDate);
        }
        
        if ((rebate != null) && (rebate.getTax() > 0d)) {
            final double rebateAmount = Math.round(rebate.getTax());
            billGenerated.setBmToatlRebate(rebateAmount);
            excessAmount.setExcAmt(rebateAmount + excessAmount.getExcAmt());
            excessAmount.setExcadjFlag(MainetConstants.RnLCommon.MODE_EDIT);
        }
    }

    private void updateArrearsInbillDetailForNewTax(final List<TbBillMas> billMasDtoList, long orgid) {
        // ex. if bill size is 5 then loop will flow for 4 times
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
                    billDet.setBdCurTaxamt(0.0);
                    billDet.setBdBilldetid(0);
                    billDet.setBdCurBalTaxamt(0.0);
                    // billDet.setBdPrvArramt(firDet.getBdCurTaxamt() + firDet.getBdPrvArramt()); //
                    // #12562 defect in property
                    // same in water
                    billDet.setBdPrvArramt(Math.round(firDet.getBdCurBalTaxamt() + firDet.getBdPrvBalArramt()));
                    billDet.setBdPrvBalArramt(Math.round(firDet.getBdCurBalTaxamt() + firDet.getBdPrvBalArramt()));
                    secondTaxList.add(billDet);
                }

            }

        }
        for (TbBillMas billMas : billMasDtoList) {
            if (lastBillMas != null) {
                billMas.setBmTotalArrearsWithoutInt(Math.round(
                        lastBillMas.getBmTotalBalAmount() + lastBillMas.getBmTotalArrearsWithoutInt()));
                billMas.setArrearsTotal(Math.round(billMas.getBmTotalArrearsWithoutInt() + billMas.getBmTotalCumIntArrears()));
                billMas.setBmTotalOutstanding(Math.round(billMas.getBmTotalArrears() + billMas.getBmTotalBalAmount()));
            }
            /*
             * for (TbBillDet billDet : billMas.getTbWtBillDet()) { totalArrear = totalArrear + billDet.getBdCurTaxamt(); }
             */
            lastBillMas = billMas;
        }

    }

    /**
     * @param excessAmount
     * @param totalAmount
     * @param billDet
     * @param empId
     * @param langId
     * @param orgnisation
     * @param billMas
     * @param advanceTaxDto
     * @return
     */
    private double adjustAdvancePayAmount(final TbWtExcessAmt excessAmount, double totalAmount,
            final TbBillDet billDet) {
        final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
        excessAmtHist.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
        excessAmtHist.setExcessId(excessAmount.getExcessId());
        auditService.createHistory(excessAmount, excessAmtHist);
        final double amount = totalAmount;
        totalAmount = totalAmount - excessAmount.getExcAmt();
        if (totalAmount < 0) {
            billDet.setBdCurTaxamt(amount);
            excessAmount.setAdjAmt(excessAmount.getAdjAmt() + amount);
            excessAmount.setExcAmt(Math.abs(totalAmount));
            totalAmount = 0d;
        } else {
            final double adjustedAmount = excessAmount.getExcAmt();
            billDet.setBdCurTaxamt(adjustedAmount);
            if(excessAmount.getAdjAmt()!=null)
                excessAmount.setAdjAmt(excessAmount.getAdjAmt() + adjustedAmount);
            excessAmount.setExcAmt(0d);
            excessAmount.setExcadjFlag(MainetConstants.RnLCommon.Flag_A);
        }
        return totalAmount;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService# getBillMasterListByCsidn(long, long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<TbBillMas> getBillMasterListByUniqueIdentifier(final long csIdn, final long orgid) {
        final List<TbWtBillMasEntity> entities = billMasterJpaRepository.getBillMasterListByCsidn(csIdn, orgid);
        final List<TbBillMas> beans = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            Organisation org = new Organisation();
            org.setOrgid(orgid);
            LookUp taxSubCat = null;
            TbTaxMasEntity taxMas = billMasterCommonService.getInterestTax(orgid,
                    MainetConstants.WATER_DEPARTMENT_CODE);
            if (taxMas != null) {
                taxSubCat = CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory2(), org);
            }
            for (final TbWtBillMasEntity tbWtBillMasEntity : entities) {
                final List<TbBillDet> detList = new ArrayList<>(0);
                if ((tbWtBillMasEntity.getBillDetEntity() != null) && !tbWtBillMasEntity.getBillDetEntity().isEmpty()) {
                    for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                    	TbBillDet det = new TbBillDet();
                        BeanUtils.copyProperties(detail, det);
                        det.setBmIdno(tbWtBillMasEntity.getBmIdno());
                        detList.add(det);		
                    }
                }
                final TbBillMas mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                if (taxSubCat != null) {
                    mas.setInterstCalMethod(taxSubCat.getLookUpCode());
                }
                detList.sort(Comparator.comparing(TbBillDet::getCollSeq));
                mas.setTbWtBillDet(detList);
                beans.add(mas);
            }
        }
        return beans;
    }

    @Override
    @WebMethod(exclude = true)
    public TbWtBillMasEntity create(final TbBillMas tbWtBillMas) {
        List<TbWtBillDetEntity> detList = new ArrayList<>(0);
        final TbWtBillMasEntity tbWtBillMasEntity = new TbWtBillMasEntity();
        BeanUtils.copyProperties(tbWtBillMas, tbWtBillMasEntity);
        if (tbWtBillMasEntity.getBmIdno() <= 0) {
            final Long bmNumber = seqGenFunctionUtility.generateSequenceNo(MainetConstants.BILL_TABLE.Module,
                    MainetConstants.BILL_TABLE.Table, MainetConstants.BILL_TABLE.Column, tbWtBillMas.getOrgid(),
                    MainetConstants.RECEIPT_MASTER.Reset_Type, null);
            tbWtBillMas.setBmNo(bmNumber.toString());
            tbWtBillMasEntity.setBmNo(bmNumber.toString());
        }
        for (final TbBillDet det : tbWtBillMas.getTbWtBillDet()) {
            final TbWtBillDetEntity tbWtBillDetEntity = new TbWtBillDetEntity();
            det.setBmIdno(tbWtBillMasEntity.getBmIdno());
            BeanUtils.copyProperties(det, tbWtBillDetEntity);

            det.setBdBilldetid(tbWtBillDetEntity.getBdBilldetid());
            tbWtBillDetEntity.setUserId(tbWtBillMas.getUserId());
            tbWtBillDetEntity.setOrgid(tbWtBillMas.getOrgid());
            tbWtBillDetEntity.setLmoddate(tbWtBillMas.getLmoddate());
            tbWtBillDetEntity.setLgIpMac(tbWtBillMas.getLgIpMac());
            tbWtBillDetEntity.setBmIdNo(tbWtBillMasEntity);
            detList.add(tbWtBillDetEntity);
        }
        tbWtBillMasEntity.setBillDetEntity(detList);
        return tbWtBillMasEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.TbWtBillMasService#saveAdvancePayment( java.lang.String,
     * com.abm.mainetservice.web.common.entity.Organisation, java.util.Map)
     */
    @Override
    @WebMethod(exclude = true)
    public boolean saveAdvancePayment(final Long orgId, final Double amount, final String csIdn, final Long userId,
            final Long receiptId) {
        final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, MainetConstants.WATER_DEPARTMENT_CODE,
                null);
        TbWtExcessAmt excessAmt = tbWtExcessAmtJpaRepository.findExcessAmountByCsIdnAndOrgId(Long.valueOf(csIdn),
                orgId);
        if (excessAmt == null) {
            excessAmt = new TbWtExcessAmt();
        }
        // reverted for Defect #141292
        //excessAmt.setExcAmt(amount + excessAmt.getExcAmt());
        excessAmt.setExcAmt(amount + (excessAmt.getExcAmt()!=null? excessAmt.getExcAmt() : 0d));
        excessAmt.setCsIdn(Long.valueOf(csIdn));
        excessAmt.setExcadjFlag(MainetConstants.RnLCommon.MODE_EDIT);
        excessAmt.setUserId(userId);
        excessAmt.setOrgId(orgId);
        excessAmt.setLangId(MainetConstants.NUMBERS.ONE);
        excessAmt.setLmodDate(new Date());
        excessAmt.setTaxId(advanceTaxId);
        excessAmt.setRmRcptid(receiptId);
        if (excessAmt.getExcessId() > 0) {
            final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
            excessAmtHist.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
            excessAmtHist.setExcessId(excessAmt.getExcessId());
            final TbWtExcessAmt excessAmtOld = tbWtExcessAmtJpaRepository.findOne(excessAmt.getExcessId());
            auditService.createHistory(excessAmtOld, excessAmtHist);
        }
        tbWtExcessAmtJpaRepository.save(excessAmt);
        return true;
    }

    private TbWtExcessAmt getAdvancePaymentData(final Long csIdn, final long orgId) {
        return tbWtExcessAmtJpaRepository.findExcessAmountByCsIdnAndOrgId(csIdn, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.water.service.BillMasterService# fetchAdditinalRefNumberByBmIdNo(java.lang.Long, java.lang.Long)
     */

    private TbWtBillDetEntity fetchAdditinalRefNumberByBmIdNo(final Long receiptId, final Long orgid) {
        return billMasterJpaRepository.fetchAdditinalRefNumberByBmIdNo(receiptId, orgid);
    }

    private TbBillMas fetchBillsByBmIdno(final Long bmIdNo, final Long orgid) {
        final TbWtBillMasEntity entities = billMasterJpaRepository.getBillPaymentDataByBmno(bmIdNo, orgid);
        TbBillMas beans = null;
        boolean advance = false;
        if (entities != null) {
            beans = new TbBillMas();
            final List<TbBillDet> detList = new ArrayList<>(0);
            for (final TbWtBillDetEntity detail : entities.getBillDetEntity()) {
                final String taxCode = CommonMasterUtility
                        .getHierarchicalLookUp(detail.getTaxCategory(), UserSession.getCurrent().getOrganisation())
                        .getLookUpCode();
                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                    advance = true;
                }
                final TbBillDet det = new TbBillDet();
                BeanUtils.copyProperties(detail, det);
                detList.add(det);
            }
            BeanUtils.copyProperties(entities, beans);
            if (advance) {
                beans.setWtV1(MainetConstants.RnLCommon.Y_FLAG);
            }
            beans.setTbWtBillDet(detList);
        }
        return beans;
    }

    private TbWtBIllMasHist fetchBillHistoryData(final TbBillMas bills, final Long orgid) {
        if (bills != null) {
            final List<TbWtBIllMasHist> billHistory = billMasterJpaRepository.fetchBillHistoryData(bills.getBmIdno(),
                    orgid);
            if ((billHistory != null) && !billHistory.isEmpty()) {
                return billHistory.get(0);
            }
        }
        return null;
    }

    private List<TbWtBIllDetHist> fetBillDetailsHistory(final Long bmIdNo, final List<TbBillDet> tbWtBillDet,
            final Long orgid) {
        final List<TbWtBIllDetHist> billdetHist = new ArrayList<>(0);
        for (final TbBillDet detail : tbWtBillDet) {
            final List<TbWtBIllDetHist> detHistory = billMasterJpaRepository.fetchBillDetailByBmidAndBillDetId(bmIdNo,
                    detail.getBdBilldetid(), orgid);
            if ((detHistory != null) && !detHistory.isEmpty()) {
                billdetHist.add(detHistory.get(0));
            }
        }
        return billdetHist;
    }

    private void revertBillPaymentDataByBmno(final TbBillMas bills, final TbWtExcessAmt advanceAmt,
            final TbWtBIllMasHist billHistory, final List<TbWtBIllDetHist> detHistory,
            final TbWtExcessAmtHist advanceAmtHist, final TbSrcptModesDetBean modesId) {
        final Map<Long, Long> billdetDelete = new HashMap<>(0);
        final TbWtBillMasEntity entity = new TbWtBillMasEntity();
        if (billHistory != null) {
            BeanUtils.copyProperties(billHistory, entity);
            billMasterJpaRepository.save(entity);
        }
        if ((detHistory != null) && !detHistory.isEmpty()) {
            TbWtBillDetEntity detEntityCurr = null;
            for (final TbWtBIllDetHist detHist : detHistory) {
                detEntityCurr = new TbWtBillDetEntity();
                BeanUtils.copyProperties(detHist, detEntityCurr);
                detEntityCurr.setBmIdNo(entity);
                billDetailJpaRepository.save(detEntityCurr);
                billdetDelete.put(detHist.getTaxId(), detHist.getBdBilldetid());
            }
        }
        if ((billdetDelete != null) && !billdetDelete.isEmpty() && (bills != null) && (bills.getTbWtBillDet() != null)
                && !bills.getTbWtBillDet().isEmpty()) {
            for (final TbBillDet detDelete : bills.getTbWtBillDet()) {
                if (!billdetDelete.containsKey(detDelete.getTaxId())) {
                    billDetailJpaRepository.delete(detDelete.getBdBilldetid());
                }
            }
        }
        if (advanceAmtHist != null) {
            final TbWtExcessAmt advanceAmtCurr = new TbWtExcessAmt();
            BeanUtils.copyProperties(advanceAmtHist, advanceAmtCurr);
            tbWtExcessAmtJpaRepository.save(advanceAmtCurr);
        } else if ((advanceAmtHist == null) && (advanceAmt != null)) {
            tbWtExcessAmtJpaRepository.delete(advanceAmt.getExcessId());
        }
        final TbSrcptModesDetEntity feeDet = chequeDishonorService.fetchReceiptFeeDetails(modesId.getRdModesid(),
                modesId.getOrgid());
        feeDet.setRdSrChkDate(modesId.getRdSrChkDate());
        feeDet.setRdSrChkDisChg(modesId.getRdSrChkDisChg());
        feeDet.setRdSrChkDis(modesId.getRdSrChkDis());
        feeDet.setRdV1(modesId.getRdV1());
        chequeDishonorService.updateFeeDet(feeDet);
    }

    private void updateBillsHistoryTable(final TbBillMas bills, final TbWtExcessAmt advanceAmt) {
        if (bills != null) {
            final TbWtBIllMasHist billHistoryCurrent = new TbWtBIllMasHist();
            billHistoryCurrent.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
            auditService.createHistory(bills, billHistoryCurrent);
            if ((bills.getTbWtBillDet() != null) && !bills.getTbWtBillDet().isEmpty()) {
                TbWtBIllDetHist detHistoryDataCurr = null;
                for (final TbBillDet billDet : bills.getTbWtBillDet()) {
                    detHistoryDataCurr = new TbWtBIllDetHist();
                    detHistoryDataCurr.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
                    auditService.createHistory(billDet, detHistoryDataCurr);
                }
            }
        }
        if (advanceAmt != null) {
            final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
            excessAmtHist.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
            excessAmtHist.setExcessId(advanceAmt.getExcessId());
            auditService.createHistory(advanceAmt, excessAmtHist);
        }
    }

    private TbWtExcessAmtHist fetchAdvancePaymentHistoryData(final long excessId, final Long orgid, final Long csIdn) {
        final List<TbWtExcessAmtHist> advancePayHist = tbWtExcessAmtJpaRepository.fetchAdvancePaymentHistory(excessId,
                orgid, csIdn);
        if ((advancePayHist != null) && !advancePayHist.isEmpty()) {
            advancePayHist.get(0);
        }
        return null;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Map<Long, List<TbBillMas>> fetchAllUnpaidBillsForBilling(final List<Long> csIdn, final long orgid) {
        final List<TbWtBillMasEntity> entities = billMasterJpaRepository.getBillMasterListByCsidnListForBilling(csIdn,
                orgid);
        TbBillMas mas = null;
        List<TbBillDet> detList = null;
        List<TbBillMas> beans = null;
        final Map<Long, List<TbBillMas>> bills = new HashMap<>(0);
        if (entities != null && !entities.isEmpty()) {
            Organisation org = new Organisation();
            org.setOrgid(orgid);
            TbTaxMasEntity taxMas = billMasterCommonService.getInterestTax(orgid,
                    MainetConstants.WATER_DEPARTMENT_CODE);
            LookUp taxSubCat = null;
            if (taxMas != null) {
                taxSubCat = CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory2(), org);
            }
            for (final TbWtBillMasEntity tbWtBillMasEntity : entities) {
                detList = new ArrayList<>(0);
                if ((tbWtBillMasEntity.getBillDetEntity() != null) && !tbWtBillMasEntity.getBillDetEntity().isEmpty()) {
                    for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                        TbBillDet det = new TbBillDet();
                        BeanUtils.copyProperties(detail, det);
                        det.setBmIdno(tbWtBillMasEntity.getBmIdno());
                        detList.add(det);
                    }
                }
                mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                if (taxSubCat != null) {
                    mas.setInterstCalMethod(taxSubCat.getLookUpCode());
                }
                mas.setTbWtBillDet(detList);
                beans = bills.get(tbWtBillMasEntity.getCsIdn());
                if (beans == null) {
                    beans = new ArrayList<>(0);
                }
                beans.add(mas);
                bills.put(tbWtBillMasEntity.getCsIdn(), beans);
            }
        }
        return bills;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<BillReceiptPostingDTO> updateBillMasterAmountPaid(final String csIdn, Double amount, final Long orgid,
            final Long userId, String ipAddress, Date manualReceptDate, String flatNo) {
        List<BillReceiptPostingDTO> result = new ArrayList<>();
        final Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
        final double actualPayAmt = amount;
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        TbKCsmrInfoMH csmrInfoMH = null;
        List<TbBillMas> billMasData = getBillMasterListByUniqueIdentifier(Long.valueOf(csIdn), orgid);
        // from Mobile csIdn coming as connecton no. so get csIdn from connection no
        if (CollectionUtils.isEmpty(billMasData)) {
        	if((Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL))
        			|| (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) ) {
        		csmrInfoMH = waterRepository.fetchConnectionByCsIdn(Long.valueOf(csIdn), orgid);
        	}
        	else {
        		csmrInfoMH = waterRepository.fetchConnectionByCsIdn(Long.valueOf(csIdn), orgid);
        	}
            
            if (csmrInfoMH != null && csmrInfoMH.getCsIdn() > 0) {
                billMasData = getBillMasterListByUniqueIdentifier(csmrInfoMH.getCsIdn(), orgid);
            }
        }// end
        if ((billMasData != null) && !billMasData.isEmpty()) {

            // billMasterCommonService.calculateInterest(billMasData, org, null, "Y", null);
            billMasterCommonService.updateArrearInCurrentBills(billMasData);

            final int size = billMasData.size();
            final TbBillMas resultBill = billMasData.get(size - 1);
            BillReceiptPostingDTO rebateTax = null;
            BillReceiptPostingDTO surChargeTax = null;
            double amountPaidSurcharge = 0;
            TbKCsmrInfoMH waterEntity = waterRepository.fetchConnectionByCsIdn(Long.valueOf(csIdn), orgid);
            // from Mobile csIdn coming as connecton no. so get csIdn from connection no
            if (waterEntity == null && csmrInfoMH.getCsIdn() > 0) {
                waterEntity = waterRepository.fetchConnectionByCsIdn(csmrInfoMH.getCsIdn(), orgid);
            }// end
            if (waterEntity != null) {
                final TbCsmrInfoDTO dto = new TbCsmrInfoDTO();
                BeanUtils.copyProperties(waterEntity, dto);
                final LookUp chargeApplicableAtBillReceipt;
                Long finYearId = financialYearService.getFinanceYearId(new Date());
                WaterPenaltyDto waterPenaltyDto = null;
                final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
                        MainetConstants.STATUS.ACTIVE);
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                	 chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);
                }else {
                	 chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA, org);
                }
                // Here fetching all taxes applicable at the time of payment
                List<TbTaxMas> taxList = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
                        chargeApplicableAtBillReceipt.getLookUpId());
                // Here calculating surcharge which is applicable at the time of payment
                List<WaterPenaltyDto> waterPenaltyDtoList = new ArrayList<WaterPenaltyDto>();
                if (CollectionUtils.isNotEmpty(taxList)) {
                    for (TbTaxMas tbTaxMas : taxList) {
                        if (StringUtils.equalsIgnoreCase(tbTaxMas.getTaxActive(), MainetConstants.FlagY)) {
                            LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(),
                                    orgid);
                            if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.ReceiptForm.SC)) {
                            	  LOGGER.info("calculate surcharge update bill paid amount ");
                                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                                	  LOGGER.info("update bill amount data calculate surcharge for skdcl");
                                 waterPenaltyDtoList=null;
                                  waterPenaltyDtoList = tbWtBillMasService.calculateSurcharge(organisationService.getOrganisationById(orgid), 
												 deptId, billMasData, tbTaxMas,finYearId, dto, ipAddress, userId, 
												 null, manualReceptDate, waterPenaltyDtoList); 
									 
                               }else if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                                 List<WaterPenaltyEntity> waterPenaltyList= waterPenaltyRepository.getWaterPenaltyByCCnNo(String.valueOf(waterEntity.getCsIdn()), orgid);
                                  for (WaterPenaltyEntity entity : waterPenaltyList) {
                                     WaterPenaltyDto dtonew = new WaterPenaltyDto();
                                     BeanUtils.copyProperties(entity, dtonew);
                                     waterPenaltyDtoList.add(dtonew);
                                 }

                            }else  {
                            	   LOGGER.info("update bill amount data calculate surcharge");
                            	   waterPenaltyDto = tbWtBillMasService.calculateSurcharge(
	                                           organisationService.getOrganisationById(orgid), deptId,
	                                           billMasData, tbTaxMas, finYearId, dto, ipAddress,
	                                           userId, null, manualReceptDate);
                               }
                               
                            }
                        }
                    }
                }
                
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                	  LOGGER.info("update bill paid amount for skdcl");
                	int count = 0;
                	TbBillMas prevBill = null;
                	Double totalSurcharge = 0d;
                	for(TbBillMas tbBillMas : billMasData) { 
                		count++;
                		
                		Optional<WaterPenaltyDto> waterPenalty = waterPenaltyDtoList.stream().filter(penaltyObj->penaltyObj.getBmIdNo() != null &&	penaltyObj.getBmIdNo() == tbBillMas.getBmIdno()).findFirst();
                		if (waterPenalty != null && waterPenalty.isPresent() && waterPenalty.get().getPendingAmount() > 0 && amount != null && amount > 0d) {
                			WaterPenaltyDto waterPenaltyObj = waterPenalty.get();
                			totalSurcharge = waterPenalty.get().getPendingAmount();
                            double pendingSurcharge = waterPenaltyObj.getPendingAmount();
                            Double billGenAmount = waterPenaltyObj.getBillGenAmount();
                            pendingSurcharge -= amount;
                            if (billGenAmount != null) {
                                billGenAmount -= amount;
                            }
                            if (pendingSurcharge <= 0) {
                                pendingSurcharge = 0;
                            }
                            if (billGenAmount == null || billGenAmount <= 0) {
                                billGenAmount = 0.0;
                            }
                            amount -= waterPenaltyObj.getPendingAmount();
                            amountPaidSurcharge = waterPenaltyObj.getPendingAmount() - pendingSurcharge;

                            // Here Surcharge Details are setting to display on Receipt
                            setSurchargeDataForReceipt(tbBillMas, amountPaidSurcharge, waterPenaltyObj, totalSurcharge, result, org);
                           
                            waterPenaltyObj.setPendingAmount(pendingSurcharge);
                            waterPenaltyObj.setLgIpMacUpd(ipAddress);
                            waterPenaltyObj.setUpdatedBy(userId);
                            waterPenaltyObj.setBillGenAmount(billGenAmount);
                            waterPenaltyService.updateWaterPenalty(waterPenaltyObj);
                            LOGGER.info("SurCharge Details Updated Successfully");
                            
                            
                        }
                		else {
                        	if (waterPenalty != null && waterPenalty.isPresent()) {
                        		totalSurcharge = waterPenalty.get().getPendingAmount();
                        		setSurchargeDataForReceipt(tbBillMas, amount,  waterPenalty.get(),  waterPenalty.get().getPendingAmount(), result, org);
                        	}
                        }
                		
                		billMasData.forEach(billMas -> {
        					billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
        				});

                		List<TbBillMas> billMasDataList = new ArrayList<>();
                		boolean isLastBill = billMasData.size() == count ? true : false;
                		double balBeforeUpdate = tbBillMas.getBmTotalBalAmount();
                		List<BillReceiptPostingDTO> billReceiptPostingDTO  = billMasterCommonService.updateSingleBillData(tbBillMas, prevBill, amount.doubleValue(), details,
                                									 		billDetails, org, rebateDetails, manualReceptDate, isLastBill);
                		
                		result.addAll(billReceiptPostingDTO);
                		billMasDataList.add(tbBillMas);
                		
                		if(isLastBill)
                			billMasDataList.get(billMasDataList.size() - 1).setBmLastRcptamt(actualPayAmt);
                        
                		prevBill = tbBillMas;
                        
   					/* check after adding rebate logic for each bill
   					 * if (rebateTax != null) { rebateTax.setRebateDetails(rebateDetails);
   					 * result.add(rebateTax); }
   					 */
                        savePaidBillAmount(billMasDataList);
                        amount = amount > balBeforeUpdate ? amount - balBeforeUpdate : 0d;
                	}
					/*
					 * if (surChargeTax != null && surChargeTax.getTaxId() != null) {
					 * result.add(surChargeTax); }
					 */
                	final TbBillMas last = billMasData.get(billMasData.size() - 1);
                    if (last.getExcessAmount() > 0) {
                        result.add(addAdvanceAmountDetails(orgid, details, billDetails, last.getExcessAmount()));
                    }
                    return result;
                }
                else {
                	  LOGGER.info("update bill paid amount method ");
                   	  if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
                	  int count = 0;
                  	TbBillMas prevBill = null;
                  	Double totalSurcharge = 0d;
                  	for(TbBillMas tbBillMas : billMasData) { 
                  		count++;
                  		
                  		Optional<WaterPenaltyDto> waterPenalty = waterPenaltyDtoList.stream().filter(penaltyObj->penaltyObj.getBmIdNo() != null &&	penaltyObj.getBmIdNo() == tbBillMas.getBmIdno()).findFirst();
                  		if (waterPenalty != null && waterPenalty.isPresent() && waterPenalty.get().getPendingAmount() > 0 && amount != null && amount > 0d) {
                  			WaterPenaltyDto waterPenaltyObj = waterPenalty.get();
                  			totalSurcharge = waterPenalty.get().getPendingAmount();
                              double pendingSurcharge = waterPenaltyObj.getPendingAmount();
                              Double billGenAmount = waterPenaltyObj.getBillGenAmount();
                              pendingSurcharge -= amount;
                              if (billGenAmount != null) {
                                  billGenAmount -= amount;
                              }
                              if (pendingSurcharge <= 0) {
                                  pendingSurcharge = 0;
                              }
                              if (billGenAmount == null || billGenAmount <= 0) {
                                  billGenAmount = 0.0;
                              }
                              amount -= waterPenaltyObj.getPendingAmount();
                              amountPaidSurcharge = waterPenaltyObj.getPendingAmount() - pendingSurcharge;

                              // Here Surcharge Details are setting to display on Receipt
                              setSurchargeDataForReceipt(tbBillMas, amountPaidSurcharge, waterPenaltyObj, totalSurcharge, result, org);
                             
                              waterPenaltyObj.setPendingAmount(pendingSurcharge);
                              waterPenaltyObj.setLgIpMacUpd(ipAddress);
                              waterPenaltyObj.setUpdatedBy(userId);
                              waterPenaltyObj.setBillGenAmount(billGenAmount);
                              waterPenaltyService.updateWaterPenalty(waterPenaltyObj);
                              LOGGER.info("SurCharge Details Updated Successfully");
                              
                              
                          }
                  		else {
                          	if (waterPenalty != null && waterPenalty.isPresent()) {
                          		totalSurcharge = waterPenalty.get().getPendingAmount();
                          		setSurchargeDataForReceipt(tbBillMas, amount,  waterPenalty.get(),  waterPenalty.get().getPendingAmount(), result, org);
                          	}
                          }
                  	
                  	}  
                }else {
                	if (waterPenaltyDto != null && waterPenaltyDto.getPendingAmount() > 0) {

                    double pendingSurcharge = waterPenaltyDto.getPendingAmount();
                    Double billGenAmount = waterPenaltyDto.getBillGenAmount();
                    pendingSurcharge -= amount;
                    if (billGenAmount != null) {
                        billGenAmount -= amount;
                    }
                    if (pendingSurcharge <= 0) {
                        pendingSurcharge = 0;
                    }
                    if (billGenAmount == null || billGenAmount <= 0) {
                        billGenAmount = 0.0;
                    }
                    amount -= waterPenaltyDto.getPendingAmount();
                    amountPaidSurcharge = waterPenaltyDto.getPendingAmount() - pendingSurcharge;

                    // Here Surcharge Details are setting to display on Receipt
                    surChargeTax = new BillReceiptPostingDTO();
                    surChargeTax.setBillMasId(resultBill.getBmIdno());
                    surChargeTax.setTaxAmount(amountPaidSurcharge);
                    surChargeTax.setTaxId(waterPenaltyDto.getTaxId());
                    surChargeTax.setTotalDetAmount(waterPenaltyDto.getPendingAmount());
                    surChargeTax.setYearId(waterPenaltyDto.getFinYearId());
                    TbTaxMas taxMasSurcharge = tbTaxMasService.findById(waterPenaltyDto.getTaxId(), waterPenaltyDto.getOrgId());
                    final String taxCode = CommonMasterUtility.getHierarchicalLookUp(taxMasSurcharge.getTaxCategory1(), org)
                            .getLookUpCode();
                    surChargeTax.setTaxCategoryCode(taxCode);
                    surChargeTax.setTaxCategory(taxMasSurcharge.getTaxCategory1());
                    waterPenaltyDto.setPendingAmount(pendingSurcharge);
                    waterPenaltyDto.setLgIpMacUpd(ipAddress);
                    waterPenaltyDto.setUpdatedBy(userId);
                    waterPenaltyDto.setBillGenAmount(billGenAmount);

                    waterPenaltyService.updateWaterPenalty(waterPenaltyDto);
                    LOGGER.info("SurCharge Details Updated Successfully");
                }
               }
                BillTaxDTO rebate = null;
                double rebateAmount = 0;
                Date lastBillDueDate = resultBill.getBmDuedate();
                LookUp waterRebateAdvancePayment = null;
                try {
                    waterRebateAdvancePayment = CommonMasterUtility.getValueFromPrefixLookUp("APR", "WRB", org);
                } catch (Exception exception) {
                    LOGGER.error("No prefix found for WRB");
                }
                List<TbBillMas> currentYearBillList = billMasData.stream()
                        .filter(billMas -> billMas.getBmYear().equals(finYearId)).collect(Collectors.toList());
                if (waterRebateAdvancePayment != null) {
                    if (CollectionUtils.isNotEmpty(currentYearBillList)) {
                        lastBillDueDate = currentYearBillList.get(0).getBmTodt();
                    }
                }
                final LocalDate date = LocalDate.now();
                Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (manualReceptDate != null) {
                    duedate = manualReceptDate;
                }
                if (UtilityService.compareDate(duedate, lastBillDueDate) && resultBill.getBmLastRcptamt() == 0) {
                    //rebate = tbWtBillMasService.getRebateAmountForPaymentByCnTyp(dto, resultBill, org, manualReceptDate);
                    
                    if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL))
                    {
                    	rebate = tbWtBillMasService.getRebateAmountForPaymentByCnTyp(dto, resultBill, org, manualReceptDate);
                    }
                    else
                    {
                    	rebate = tbWtBillMasService.getRebateAmountForPayment(dto, resultBill, org, manualReceptDate);
                    }
                    
                    if ((rebate != null) && (rebate.getTax() > 0d)) {
                        if (waterRebateAdvancePayment != null
                                && StringUtils.isNotBlank(waterRebateAdvancePayment.getOtherField())) {
                            double currentYearBill = (rebate.getTax())
                                    / (Double.valueOf(waterRebateAdvancePayment.getOtherField()));
                            double totalCurrentYearBill = 0;
                            if (CollectionUtils.isNotEmpty(currentYearBillList)) {
                                totalCurrentYearBill = currentYearBill * (12 - currentYearBillList.size());
                            } else {
                                totalCurrentYearBill = currentYearBill * 11;
                            }
                            resultBill.setBmTotalOutstanding(resultBill.getBmTotalOutstanding() + totalCurrentYearBill);
                        }
                        rebateAmount = Math.round(rebate.getTax());
                        if (amount.doubleValue() >= (resultBill.getBmTotalOutstanding() - rebateAmount)) {
                            rebateTax = new BillReceiptPostingDTO();
                            resultBill.setBmToatlRebate(Math.round(rebate.getTax()));
                            details.put(rebate.getTaxId(), resultBill.getBmToatlRebate());
                            billDetails.put(rebate.getTaxId(), null);
                            rebateTax.setBillMasId(resultBill.getBmIdno());
                            rebateTax.setTaxAmount(resultBill.getBmToatlRebate());
                            rebateTax.setTaxCategory(rebate.getTaxCategory());
                            rebateTax.setTaxId(rebate.getTaxId());
                            rebateTax.setYearId(resultBill.getBmYear());
                            if (StringUtils.isNotBlank(rebate.getParentTaxCode())) {
                                ArrayList<Double> taxValueIdList = new ArrayList<Double>();
                                taxValueIdList.add(rebateAmount);
                                taxValueIdList.add(Double.valueOf(rebate.getTaxId()));
                                resultBill.getTaxWiseRebate().put(rebate.getParentTaxCode(), taxValueIdList);
                            }
                        }
                    }
                }

                billMasData.forEach(billMas -> {
                    billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
                });

            	List<BillReceiptPostingDTO> billReceiptPostingDTO = billMasterCommonService.updateBillData(billMasData, amount.doubleValue(), details,
                            billDetails, org, rebateDetails, manualReceptDate);
                    billMasData.get(billMasData.size() - 1).setBmLastRcptamt(actualPayAmt);
                	result.addAll(billReceiptPostingDTO);
                }

            }
            if (surChargeTax != null) {
                result.add(surChargeTax);
            }
            if (rebateTax != null) {
                rebateTax.setRebateDetails(rebateDetails);
                result.add(rebateTax);
            }
            savePaidBillAmount(billMasData);
            final TbBillMas last = billMasData.get(billMasData.size() - 1);
            if (last.getExcessAmount() > 0) {
                result.add(addAdvanceAmountDetails(orgid, details, billDetails, last.getExcessAmount()));
            }
        } else {
            result.add(addAdvanceAmountDetails(orgid, details, billDetails, amount));
        }

        return result;
    }

	private BillReceiptPostingDTO addAdvanceAmountDetails(final Long orgid, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, final double amount) {
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgid, MainetConstants.WATER_DEPARTMENT_CODE,
                null);
        details.put(advanceTaxId, amount);
        billDetails.put(advanceTaxId, null);

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
        BillReceiptPostingDTO dto = new BillReceiptPostingDTO();
        dto.setTaxAmount(amount);
        dto.setTaxId(advanceTaxId);
        dto.setTaxCategory(advanceId);
        // Defect #12813 Advance payment details not get displayed on bill/receipt of
        // Data entry suite
        dto.setTaxCategoryCode(PrefixConstants.TAX_CATEGORY.ADVANCE);
        return dto;
    }

    /**
     * @param rmReceiptId
     * @param advance
     * @param rmRcptid
     * @param orgid
     * @param modesId
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean revertBills(final TbServiceReceiptMasBean feedetailDto, Long userId, String ipAddress) {
        Long csIdn = null;
        final long orgid = feedetailDto.getOrgId();
        TbWtBIllMasHist billHistory = null;
        TbBillMas bills = null;
        final TbWtBillDetEntity bmIdNo = fetchAdditinalRefNumberByBmIdNo(feedetailDto.getRmRcptid(), orgid);
        if (bmIdNo != null) {
            bills = fetchBillsByBmIdno(bmIdNo.getBmIdNo().getBmIdno(), orgid);
        }
        if (bills != null) {
            billHistory = fetchBillHistoryData(bills, orgid);
        }
        List<TbWtBIllDetHist> detHistory = null;
        TbWtExcessAmt advanceAmt = null;
        TbWtExcessAmtHist advanceAmtHist = null;
        if (billHistory != null) {
            detHistory = fetBillDetailsHistory(bmIdNo.getBmIdNo().getBmIdno(), bills.getTbWtBillDet(), orgid);
        }
        if (PrefixConstants.TAX_CATEGORY.ADVANCE.equals(feedetailDto.getReceiptTypeFlag())) {
            csIdn = Long.valueOf(feedetailDto.getAdditionalRefNo());
            advanceAmt = getAdvancePaymentData(csIdn, orgid);
            if (advanceAmt != null) {
                advanceAmtHist = fetchAdvancePaymentHistoryData(advanceAmt.getExcessId(), orgid, csIdn);
            }
        }
        revertBillPaymentDataByBmno(bills, advanceAmt, billHistory, detHistory, advanceAmtHist,
                feedetailDto.getReceiptModeDetailList());
        updateBillsHistoryTable(bills, advanceAmt);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<TbBillMas> fetchListOfBillsByPrimaryKey(final List<Long> bmIdNo, final Long orgid) {
        final List<TbBillMas> bills = new ArrayList<>();
        final List<TbWtBillMasEntity> billmas = billMasterJpaRepository.fetchListOfBillsByPrimaryKey(bmIdNo, orgid);
        TbBillMas mas = null;
        List<TbBillDet> detList = null;
        if (billmas != null && !billmas.isEmpty()) {
            Organisation org = new Organisation();
            org.setOrgid(orgid);
            Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", org).getLookUpId();

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
            for (final TbWtBillMasEntity tbWtBillMasEntity : billmas) {
                Double amount = 0d;
                detList = new ArrayList<>(0);
                for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                    if (demandId.equals(detail.getTaxCategory())) {
                        final TbBillDet det = new TbBillDet();
                        BeanUtils.copyProperties(detail, det);
                        det.setTddTaxid(currDemandId);
                        amount += det.getBdCurTaxamt();
                        detList.add(det);
                    }
                }
                mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                mas.setTbWtBillDet(detList);
                mas.setBmTotalOutstanding(amount);
                bills.add(mas);
            }
        }
        return bills;
    }

    @Override
    @WebMethod(exclude = true)
    public boolean updateAccountPostingFlag(final List<Long> bmIdNo, final String flag) {
        /* List<List<Long>> subSets = ListUtils.partition(bmIdNo, 1000); */
        Map<Long, List<Long>> groups = bmIdNo.stream().collect(Collectors.groupingBy(s -> (s - 1) / 1000));
        List<List<Long>> subSets = new ArrayList<>(groups.values());
        subSets.forEach(billId -> {
            billMasterJpaRepository.updateAccountPostingFlag(billId, flag);
        });

        return true;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchCurrentBill(final String connectionNumber, final Long orgId) {
        List<TbBillMas> beans = null;
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final TbKCsmrInfoMH csIdn = billMasterJpaRepository.fetchCsIdnByConnectionNumber(connectionNumber, orgId);
        if (csIdn != null) {
        	if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        		beans = getWaterChargesListByUniqueIdentifier(csIdn.getCsIdn(), orgId);
        	}else {
                beans = getBillMasterListByUniqueIdentifier(csIdn.getCsIdn(), orgId);
        	}
            if (beans.isEmpty()) {
                final TbBillMas mas = new TbBillMas();
                mas.setCsIdn(csIdn.getCsIdn());
                beans.add(mas);
            }
        }
        return beans;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public List<TbBillMas> updateAdjustedCurrentBill(List<TbBillMas> bill) {
    	List<TbBillMas> billMasList = fetchAllBillByCsIdn(bill.get(0).getCsIdn(), bill.get(0).getOrgid());
    	
    	TbWtBIllMasHist history = new TbWtBIllMasHist();
        history.sethStatus(MainetConstants.FlagA);
        auditService.createHistory(billMasList, history);
    	
		billMasterCommonService.updateArrearInCurrentBills(bill);

		bill.forEach(billMas -> {
			AtomicBoolean balanceFlag = new AtomicBoolean(false);
			billMas.getTbWtBillDet().forEach(det -> {
				if (det.getBdCurBalTaxamt() > 0) {
					balanceFlag.getAndSet(true);
				}
			});
			if (balanceFlag.get()) {
				billMas.setBmPaidFlag(MainetConstants.FlagN);
			}else {
				billMas.setBmPaidFlag(MainetConstants.FlagY);
    			billMas.setBmTotalOutstanding(0.00);
			}
		});
    	savePaidBillAmount(bill);
        return bill;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public Map<Long, Boolean> checkBillsForNonMeter(List<Long> csIdn, long orgid) {
        Map<Long, Boolean> billMap = new HashMap<>(0);
        List<TbWtBillMasEntity> entity = billMasterJpaRepository.checkBillsForNonMeter(csIdn, orgid);
        if (entity != null && !entity.isEmpty()) {
            entity.forEach(bill -> {
                billMap.put(bill.getCsIdn(), true);
            });
        }
        return billMap;
    }

    @Override
    @Transactional
    @POST
    @Path("fetchconnectiondetailforpayment")
    public WaterBillResponseDTO fetchBillPaymentData(@RequestBody WaterBillRequestDTO requestDTO) {
        final WaterBillResponseDTO response = new WaterBillResponseDTO();
        final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        TbKCsmrInfoMH entity = null;
        if ((!requestDTO.getCcnNumber().equals(MainetConstants.BLANK) && requestDTO.getCcnNumber() != null)) {
            entity = tbWtBillMasJpaRepository.getCsIdnByConnectionNumber(requestDTO.getCcnNumber(),
                    requestDTO.getOrgid());
        } else if ((entity == null)
                && (!requestDTO.getOldccNumber().equals(MainetConstants.BLANK) && requestDTO.getOldccNumber() != null)) {
            entity = tbWtBillMasJpaRepository.getCsIdnByOldConnectionNumber(requestDTO.getOldccNumber(),
                    requestDTO.getOrgid());
        }
        if (entity != null) {
            requestDTO.setOrgid(entity.getOrgId());
            final CFCApplicationAddressEntity address = iCFCApplicationAddressService
                    .getApplicationAddressByAppId(entity.getApplicationNo(), requestDTO.getOrgid());
            List<TbBillMas> billMasList = getBillMasterListByUniqueIdentifier(entity.getCsIdn(), requestDTO.getOrgid());
            if ((billMasList != null) && !billMasList.isEmpty()) {
                final Organisation org = new Organisation();
                org.setOrgid(requestDTO.getOrgid());
                // billMasterCommonService.calculateInterest(billMasList, org, null, "Y", null);
                if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                    billMasterCommonService.updateArrearInCurrentBills(billMasList);
                }
                // updateArrearsInbillDetailForNewTax(billMasList, org.getOrgid());

                final int size = billMasList.size();
                final TbBillMas result = billMasList.get(size - 1);
                // final TbBillMas watertax = billMasList.get(size - 2);
                AtomicDouble totAmt = new AtomicDouble(0);
                if (billMasList != null && !billMasList.isEmpty()) {
                    billMasList.get(billMasList.size() - 1).getTbWtBillDet().forEach(det -> {
                        totAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                    });
                }
                // US#116840 point.7 for Product
                LookUp meterTypeDesc = null;
                List<TbWtBillScheduleEntity> billScheduleEntityList = null;
                Long meterType = tbCsmrInfoRepository.fetchMeterTypeByCsidn(entity.getCsIdn(), requestDTO.getOrgid());
                if (meterType != null) {
                    meterTypeDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(meterType, org);
                    if (meterTypeDesc != null && meterTypeDesc.getLookUpCode() != null) {
                        billScheduleEntityList = billScheduleJpaRepository
                                .getBillScheduleByFinYearId(result.getBmYear(), result.getOrgid(), meterTypeDesc.getLookUpCode());
                    }
                    if (CollectionUtils.isNotEmpty(billScheduleEntityList)) {
                        TbWtBillScheduleEntity billScheduleEntity = billScheduleEntityList.get(billScheduleEntityList.size() - 1);
                        List<TbWtBillScheduleDetailEntity> billScheduleDetailEntityList = tbWtBillScheduleDetailJpaRepository
                                .findAllByParentId(billScheduleEntity.getCnsId(), requestDTO.getOrgid());
                        if (CollectionUtils.isNotEmpty(billScheduleDetailEntityList)) {
                            calculatingFrequency(response, billScheduleDetailEntityList);
                        }
                    }
                }
                // End of US#116840 point.7
                /** Code Related to calculating Surcharge Starts From here **/

                // Here getting current financial Year Id
                Long finYearId = financialYearService.getFinanceYearId(new Date());

                TbCsmrInfoDTO csmrInfoDto = new TbCsmrInfoDTO();
                BeanUtils.copyProperties(entity, csmrInfoDto);
                
                final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA, org);


                WaterBillTaxDTO totalSurcharge = null;
                WaterPenaltyDto surcharge = null;
                WaterPenaltyDto surchargeDto = null;

                // Here fetching all taxes applicable at the time of payment
                List<TbTaxMas> taxList = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
                        chargeApplicableAtBillReceipt.getLookUpId());

                // Here calculating surcharge which is applicable at the time of payment
                
                List<WaterPenaltyDto> surChargeList=new ArrayList<>();
                
                boolean surchargeTaxActive = false;
                if (CollectionUtils.isNotEmpty(taxList)) {
                    for (TbTaxMas tbTaxMas : taxList) {
                        if (StringUtils.equalsIgnoreCase(tbTaxMas.getTaxActive(), MainetConstants.FlagY)) {

                            LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(),
                                    requestDTO.getOrgid());
                            if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.ReceiptForm.SC)) {
                                surchargeTaxActive = true;
                                TbServiceReceiptMasEntity receiptOfLatestPaidBill = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(org.getOrgid(),String.valueOf(entity.getCsIdn()));
                                Date manualReceiptDate = receiptOfLatestPaidBill != null ? receiptOfLatestPaidBill.getRmDate(): null;
                             if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SUDA)) {
                            	      manualReceiptDate=requestDTO.getManualReceiptDate();
                               }
                               if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                                	LOGGER.info("fetch bill data  calculate surcharge for skdcl");
                                	 surChargeList = tbWtBillMasService.calculateSurcharge(organisationService.getOrganisationById(requestDTO.getOrgid()), 
													 deptId, billMasList, tbTaxMas,finYearId, csmrInfoDto, requestDTO.getIpAddress(), requestDTO.getUserId(), 
													 "Y", manualReceiptDate, surChargeList);
									 
                                }else {
                                	LOGGER.info("fetch bill paid calculate surcharge");
	                                surcharge = tbWtBillMasService.calculateSurcharge(
	                                        organisationService.getOrganisationById(requestDTO.getOrgid()), deptId,
	                                        billMasList, tbTaxMas, finYearId, csmrInfoDto, requestDTO.getIpAddress(),
	                                        requestDTO.getUserId(), null, manualReceiptDate);
                                }
                            }
                        }
                    }
                }
                
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                    double arrearPenaltyAmount = 0;
                    double currentPenaltyAmout = 0;
                    double pendingAmount=0;
                    double actualAmout=0;
                    Long taxId=0l;
                    Optional<WaterPenaltyDto> surchargeOfCurrentBill = null;
                   // fetch bill wise surchage and return into a list
                    if (!surchargeTaxActive) {
                        if (CollectionUtils.isNotEmpty(billMasList)) {
                            surChargeList = waterPenaltyService.getWaterPenaltyByBmNoIds(billMasList, requestDTO.getOrgid());
                            
                       }
                   }
                    if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                        if (CollectionUtils.isNotEmpty(surChargeList)) {
                     	               	   surchargeDto=new WaterPenaltyDto();
                     	                      totalSurcharge = new WaterBillTaxDTO();

                     	               	   if(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(deptId))) {
                     	               		   actualAmout = actualAmout + surChargeList.stream().filter(sur->!sur.getBmIdNo().equals(
                     	                   			   result.getBmIdno())).mapToDouble(sur -> sur.getActualAmount()).sum();
                     	                   	   pendingAmount = pendingAmount + surChargeList.stream().filter(sur->!sur.getBmIdNo().equals(
                     	                 			   result.getBmIdno())).mapToDouble(sur -> sur.getPendingAmount()).sum(); 

                     	                   	   currentPenaltyAmout = currentPenaltyAmout + surChargeList.stream().filter(sur->!sur.getBmIdNo().equals(
                     	                     			   result.getBmIdno())).mapToDouble(sur -> sur.getCurrentPenalty()).sum(); 
                     	                   	   taxId = surChargeList.get(0).getTaxId();

                     	               		   surchargeOfCurrentBill = surChargeList.stream().filter(
                     	                       		   detail -> detail.getBmIdNo().equals(result.getBmIdno())).findFirst();
                     	                          totalSurcharge.setTaxAmount(surchargeOfCurrentBill!=null && surchargeOfCurrentBill.isPresent() ? 
                     	                       		   surchargeOfCurrentBill.get().getPendingAmount() : 0);
                     	                          totalSurcharge.setTotal(pendingAmount + totalSurcharge.getTaxAmount());
                     	                          surchargeDto.setCurrentPenalty(totalSurcharge.getTotal());
                     	               	   }else {
                     	               		   for (WaterPenaltyDto surDto : surChargeList) {
                     	                            actualAmout=actualAmout+surDto.getActualAmount();
                     	                            pendingAmount=pendingAmount+ surDto.getPendingAmount(); 
                     	                            arrearPenaltyAmount=arrearPenaltyAmount+surDto.getArrearPenalty();
                     	                            currentPenaltyAmout=currentPenaltyAmout+surDto.getCurrentPenalty();
                     	                            taxId=surDto.getTaxId();
                     	                        }
                     	                          surchargeDto.setCurrentPenalty(currentPenaltyAmout);
                     	                          totalSurcharge.setTotal(pendingAmount);
                     	               	   }
                     	                      surchargeDto.setPendingAmount(pendingAmount);
                     	                      surchargeDto.setArrearPenalty(arrearPenaltyAmount);
                     	                      surchargeDto.setTaxId(taxId);
                     	                      surchargeDto.setOrgId(requestDTO.getOrgid());
                     	                       //totalSurcharge.setTaxAmount(currentBalanceAmout);
                     	                      totalSurcharge.setArrearTaxAmount(arrearPenaltyAmount);
                     	                      surchargeOfCurrentBill = surChargeList.stream().filter(
                     	                   		   detail -> detail.getBmIdNo().equals(result.getBmIdno())).findFirst();
                     	                      totalSurcharge.setTaxAmount(surchargeOfCurrentBill!=null && surchargeOfCurrentBill.isPresent() ? 
                     	                   		   surchargeOfCurrentBill.get().getPendingAmount() : 0);
                     	                      totalSurcharge.setTotal(pendingAmount + totalSurcharge.getTaxAmount());
                     	                      surchargeDto.setCurrentPenalty(totalSurcharge.getTotal());

                     	                      totalSurcharge.setBalabceTaxAmount(pendingAmount);
                     	                       if (surchargeDto.getTaxId() != null) {
                     	                           totalSurcharge.setTaxdescription(tbTaxMasService.findTaxDescByTaxIdAndOrgId(surchargeDto.getTaxId(), requestDTO.getOrgid())); 
                     	                       }
                     	             }
            }else {
                   if (CollectionUtils.isNotEmpty(surChargeList)) {
                       for (WaterPenaltyDto surDto : surChargeList) {
                           actualAmout=actualAmout+surDto.getActualAmount();
                           pendingAmount=pendingAmount+ surDto.getPendingAmount(); 
                           arrearPenaltyAmount=arrearPenaltyAmount+surDto.getArrearPenalty();
                           currentPenaltyAmout=currentPenaltyAmout+surDto.getCurrentPenalty();
                           taxId=surDto.getTaxId();
                       }
                       //currentBalanceAmout=actualAmout-pendingAmount;
                       surchargeDto=new WaterPenaltyDto();
                       surchargeDto.setPendingAmount(pendingAmount);
                       surchargeDto.setArrearPenalty(arrearPenaltyAmount);
                       surchargeDto.setCurrentPenalty(currentPenaltyAmout);
                       surchargeDto.setTaxId(taxId);
                       surchargeDto.setOrgId(requestDTO.getOrgid());
                        totalSurcharge = new WaterBillTaxDTO();
                        //totalSurcharge.setTaxAmount(currentBalanceAmout);
                       totalSurcharge.setArrearTaxAmount(arrearPenaltyAmount);
                       totalSurcharge.setTotal(pendingAmount);
                       totalSurcharge.setBalabceTaxAmount(pendingAmount);
                        if (surchargeDto.getTaxId() != null) {
                            totalSurcharge.setTaxdescription(tbTaxMasService.findTaxDescByTaxIdAndOrgId(surchargeDto.getTaxId(), requestDTO.getOrgid()));
                       
                        }
                   }
                 }
                   // This code is added because edited interest as surcharge and we added this interest alias surcharge to actual
                   // surcharge. Defect NO: 37598 by srikanth
                   final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                           PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);

                   LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
                           PrefixConstants.LookUpPrefix.TAC, 1, requestDTO.getOrgid());
                   LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
                           PrefixConstants.LookUpPrefix.TAC, 2, requestDTO.getOrgid());
                   Long taxIds = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), requestDTO.getOrgid(), deptId,
                           taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());

                   Optional<TbBillDet> findFirst = result.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(taxIds))
                           .findFirst();
                   if (findFirst.isPresent()) {
                       for (TbBillDet detail : result.getTbWtBillDet()) {
                           final String taxCode = CommonMasterUtility
                                   .getHierarchicalLookUp(detail.getTaxCategory(), org).getLookUpCode();
                           if (StringUtils.equals(taxCode, "P")) {
                               double curTaxAmnt = detail.getBdCurTaxamt();
                               if (surcharge != null) {
                                   detail.setBdCurTaxamt(detail.getBdCurTaxamt() + surchargeDto.getPendingAmount());
                                   detail.setBdCurBalTaxamt(curTaxAmnt + surchargeDto.getPendingAmount());
                               }
                           }
                       }

                   } else {

                       if ((surchargeDto != null && surchargeDto.getArrearPenalty()>0.0) || (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && surchargeDto != null && (surchargeDto.getPendingAmount() > 0 ||
                    		   surchargeDto.getCurrentPenalty() > 0))) {
                         
                           TbBillDet det = new TbBillDet();
                           det.setTaxId(surchargeDto.getTaxId());
                           //det.setBdCurTaxamt(Math.round(surchargeDto.getPendingAmount()));
                           //det.setBdCurBalTaxamt(Math.round(surchargeDto.getPendingAmount()));
                           if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                    		   det.setBdCurTaxamt(surchargeOfCurrentBill!=null && surchargeOfCurrentBill.isPresent() ? 
                            		   surchargeOfCurrentBill.get().getCurrentPenalty() : 0);
                               det.setBdCurBalTaxamt(surchargeOfCurrentBill!=null && surchargeOfCurrentBill.isPresent() ? 
                            		   surchargeOfCurrentBill.get().getPendingAmount() : 0);
                               det.setBdPrvBalArramt(totalSurcharge.getBalabceTaxAmount());
                               det.setTotal(totalSurcharge.getTotal());
                    	   
                    	   }else {
                               det.setBdPrvBalArramt(surchargeDto.getArrearPenalty());
                    	   }
                           det.setBmIdno(result.getBmIdno());
                           det.setOrgid(surchargeDto.getOrgId());
                           TbTaxMas surchargeTaxMast = tbTaxMasService.findById(surchargeDto.getTaxId(),
                                   org.getOrgid());
                           det.setTaxCategory(surchargeTaxMast.getTaxCategory1());
                           result.getTbWtBillDet().add(det);
                           }
                   }
               }
                else {
                	 if (!surchargeTaxActive) {
                         surcharge = waterPenaltyService.getWaterPenaltyByCCNOByFinId(String.valueOf(csmrInfoDto.getCsIdn()),
                                 finYearId, requestDTO.getOrgid());
                     }

                if (surcharge != null) {
                    totalSurcharge = new WaterBillTaxDTO();
                    totalSurcharge.setTaxAmount(surcharge.getPendingAmount());
                    totalSurcharge.setTotal(surcharge.getPendingAmount());
                    totalSurcharge.setBalabceTaxAmount(surcharge.getPendingAmount());
                    if (surcharge.getTaxId() != null) {
                        totalSurcharge.setTaxdescription(tbTaxMasService
                                .findTaxDescByTaxIdAndOrgId(surcharge.getTaxId(), requestDTO.getOrgid()));
                    }

                }

                // This code is added because edited interest as surcharge and we added this interest alias surcharge to actual
                // surcharge. Defect NO: 37598 by srikanth
                final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                        PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA, org);

                LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("P",
                        PrefixConstants.LookUpPrefix.TAC, 1, requestDTO.getOrgid());
                LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("SC",
                        PrefixConstants.LookUpPrefix.TAC, 2, requestDTO.getOrgid());

                Long taxId = tbTaxMasService.getTaxId(chargeApplicableAt.getLookUpId(), requestDTO.getOrgid(), deptId,
                        taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());

                Optional<TbBillDet> findFirst = result.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(taxId))
                        .findFirst();
                if (findFirst.isPresent()) {
                    for (TbBillDet detail : result.getTbWtBillDet()) {
                        final String taxCode = CommonMasterUtility
                                .getHierarchicalLookUp(detail.getTaxCategory(), org).getLookUpCode();
                        if (StringUtils.equals(taxCode, "P")) {
                            double curTaxAmnt = detail.getBdCurTaxamt();
                            if (surcharge != null) {
                                detail.setBdCurTaxamt(detail.getBdCurTaxamt() + surcharge.getPendingAmount());
                                detail.setBdCurBalTaxamt(curTaxAmnt + surcharge.getPendingAmount());
                            }
                        }
                    }

                } else {

                         if (surcharge != null) {
                             TbBillDet det = new TbBillDet();
                             det.setTaxId(surcharge.getTaxId());
                             det.setBdCurTaxamt(Math.round(surcharge.getPendingAmount()));
                             det.setBdCurBalTaxamt(Math.round(surcharge.getPendingAmount()));
                             det.setBmIdno(result.getBmIdno());
                             det.setOrgid(surcharge.getOrgId());
                             TbTaxMas surchargeTaxMast = tbTaxMasService.findById(surcharge.getTaxId(),
                                     org.getOrgid());
                             det.setTaxCategory(surchargeTaxMast.getTaxCategory1());
                             result.getTbWtBillDet().add(det);
                         }
                     }

                }
                
                
               
                /** Code Related to calulating Surcharge Ends **/

                Date lastBillDueDate = result.getBmDuedate();
                LookUp waterRebateAdvancePayment = null;
                try {
                    waterRebateAdvancePayment = CommonMasterUtility.getValueFromPrefixLookUp("APR", "WRB", org);
                } catch (Exception exception) {
                    LOGGER.error("No prefix found for WRB");
                }
                if (waterRebateAdvancePayment != null) {
                    List<TbBillMas> currentYearBillList = billMasList.stream()
                            .filter(billMas -> billMas.getBmYear().equals(finYearId)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(currentYearBillList)) {
                        lastBillDueDate = currentYearBillList.get(0).getBmTodt();
                    }
                }
                final LocalDate date = LocalDate.now();
                Date duedate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (requestDTO.getManualReceiptDate() != null) {
                    duedate = requestDTO.getManualReceiptDate();
                }
                if (UtilityService.compareDate(duedate, lastBillDueDate) && result.getBmLastRcptamt() == 0) {
                    final TbCsmrInfoDTO dto = new TbCsmrInfoDTO();
                    BeanUtils.copyProperties(entity, dto);
                    //final BillTaxDTO rebate = tbWtBillMasService.getRebateAmountForPayment(dto, result, org, duedate);
                    //final BillTaxDTO rebate = tbWtBillMasService.getRebateAmountForPaymentByCnTyp(dto, result, org, duedate);
                    BillTaxDTO rebate = null;
                    
                    if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL))
                    {
                    	rebate = tbWtBillMasService.getRebateAmountForPaymentByCnTyp(dto, result, org, duedate);
                    }
                    else if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)){
                    	if(result.getBmYear() == finYearId){
                    		try {
								LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("RBW", "RBW", org);
								if(lookUp!= null){
									Integer rebateDays = Integer.valueOf(lookUp.getOtherField());
									Calendar cal = Calendar.getInstance();
									cal.setTime(result.getBmBilldt());
									cal.add(Calendar.DATE, rebateDays);
									Date rebateDueDate = cal.getTime();
									if(new Date().after(result.getBmBilldt()) && new Date().before(rebateDueDate)){
										rebate = tbWtBillMasService.getRebateAmountForPayment(dto, result, org, duedate);
									}
								}
							} catch (Exception e) {
								LOGGER.info("RBW prefix not found");
							}                		
                    	}                   	
                    }
                    else
                    {
                    	rebate = tbWtBillMasService.getRebateAmountForPayment(dto, result, org, duedate);
                    }
                    
                    if ((rebate != null) && (rebate.getTax() > 0d)) {
                        result.setBmTotalOutstanding(result.getBmTotalOutstanding() - Math.round(rebate.getTax()));
                        response.setRebateAmount(Math.round(rebate.getTax()));
                    }
                }
                response.setTotalPayableAmount(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)? Math.round(totAmt.doubleValue()) : Math.round(totAmt.doubleValue()));

                final TbWtExcessAmt excessAmount = tbWtExcessAmtJpaRepository.fetchExcessAmountData(result.getCsIdn(),
                        requestDTO.getOrgid());
                if (excessAmount != null && excessAmount.getExcAmt() != null && excessAmount.getAdjAmt() != null) {
                    response.setBalanceExcessAmount(Math.round(excessAmount.getExcAmt()));
                    response.setExcessAmount(Math.round(excessAmount.getAdjAmt()));
                }
                
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                	setAllArrearsAndCurrentByTaxesRmDpcl(response, org, result);
                	
                	if(MainetConstants.WATER_DEPT.equals(departmentService.getDeptCode(deptId))) {
                		//to set adjustment amount for skdcl
        				Double totalAdjValue = 0.0;
                		totalAdjValue = getAdjustmentAmountForWaterSkdcl(deptId, entity.getCsIdn(), org.getOrgid(), billMasList);
            			response.setAdjustmentEntry(totalAdjValue);
                	}
                	
                }
                else {
                	setAllArrearsAndCurrentByTaxes(response, org, result);
                }

                /*
                 * if (totalSurcharge != null && totalSurcharge.getTaxAmount() > 0) { response.getTaxes().add(totalSurcharge); }
                 */
                // Here setting surcharge data in response to display on screen
                if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
                    if (surchargeDto != null && (surchargeDto.getPendingAmount() > 0 ||
                  		   surchargeDto.getCurrentPenalty() > 0)) {
                        response.setCurrSurchargeAmount(surchargeDto.getCurrentPenalty());
                        response.setTotalPayableAmount(Math.round(totAmt.doubleValue() + surchargeDto.getCurrentPenalty()));
                    	//response.setTotalPayableAmount(Math.round(totAmt.doubleValue() + totalSurcharge.getArrearTaxAmount() + surchargeDto.getCurrentPenalty()));
                        response.setSurchargeAmount(Math.round(totalSurcharge.getTotal()));
                       
                    }
                }else {
                	if (surcharge != null && surcharge.getPendingAmount() > 0) {
                        response.setTotalPayableAmount(Math.round(totAmt.doubleValue() + totalSurcharge.getTotal()));
                        response.setSurchargeAmount(Math.round(totalSurcharge.getTotal()));
                    }
                }

            }
            response.setCsIdn(entity.getCsIdn());
            if (entity.getApplicationNo() != null)
                response.setApplicationNumber(entity.getApplicationNo());
            response.getApplicantDto().setApplicantFirstName(entity.getCsName());
            response.getApplicantDto().setApplicantMiddleName(entity.getCsMname());
            response.getApplicantDto().setApplicantLastName(entity.getCsLname());
            response.getApplicantDto().setOrgId(entity.getOrgId());
            // D#117768
            PropertyDetailDto detailDTO = null;
            PropertyInputDto propInputDTO = new PropertyInputDto();
            propInputDTO.setPropertyNo(entity.getPropertyNo());
            propInputDTO.setOrgId(entity.getOrgId());
            
            ResponseEntity<?> responseEntity = null;
            final Organisation organisation = new Organisation();
            organisation.setOrgid(requestDTO.getOrgid());
            if(propInputDTO.getPropertyNo()!=null && !propInputDTO.getPropertyNo().isEmpty() && !Utility.isEnvPrefixAvailable(organisation, "PIN")) {
            	  responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                        ServiceEndpoints.PROP_BY_PROP_ID);
            }
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

                detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                if (!detailDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.FAIL)) {
                    if (detailDTO.getGardianOwnerName() != null) {
                        response.setGuardianName(detailDTO.getGardianOwnerName());
                    }
                }
            }
            // User Story #97963 Customization for SKDCL-Manish Chaurasiya
            final Organisation organ = new Organisation();
            organ.setOrgid(entity.getOrgId());
            if (Utility.isEnvPrefixAvailable(organ, "RPF")) {
                response.getApplicantDto().setAreaName(entity.getCsLanear());// not confirm
                response.getApplicantDto().setBuildingName(entity.getCsBldplt());
                response.getApplicantDto().setFlatBuildingNo(entity.getCsFlatno());
                response.getApplicantDto().setRoadName(entity.getCsRdcross());
            } else {
                response.getApplicantDto().setAreaName(entity.getCsBadd());
                response.getApplicantDto().setBuildingName(entity.getCsBbldplt());
                response.getApplicantDto().setFlatBuildingNo(entity.getCsBlanear());
                response.getApplicantDto().setRoadName(entity.getCsBrdcross());
            }
            response.getApplicantDto().setDepartmentId(deptId);
            if (entity.getCsCcn() != null && !entity.getCsCcn().equals(MainetConstants.BLANK))
                response.setCcnNumber(entity.getCsCcn());
            if (entity.getCsOldccn() != null && !entity.getCsOldccn().equals(MainetConstants.BLANK))
                response.setOldccnNumber(entity.getCsOldccn());
            if (address != null && address.getApmApplicationId() != 0) {
                response.getApplicantDto().setMobileNo(address.getApaMobilno());
                response.getApplicantDto().setEmailId(address.getApaEmail());
            } else {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(entity.getCsContactno())) {
                    response.getApplicantDto().setMobileNo(entity.getCsContactno());
                } else {
                    response.getApplicantDto().setMobileNo(entity.getCsOcontactno());
                }
                response.getApplicantDto().setEmailId(entity.getCsEmail());
            }
            response.setStatus(MainetConstants.FlagS);
        } else {
            response.setStatus(MainetConstants.MENU.N);
        }
        return response;
    }

    @Override
    public Double getAdjustmentAmountForWaterSkdcl(Long deptId, Long csIdn, Long orgid, List<TbBillMas> billMasList) {

		//to set adjustment amount for skdcl
		Double totalAdjValue = 0.0;
    	List<AdjustmentMasterEntity> adjustmentEntryList = adjustmentEntryRepository.fetchModuleWiseAdjustmentByUniqueIds(deptId, Arrays.asList(String.valueOf(csIdn)), 
    			orgid);
    	if(CollectionUtils.isNotEmpty(adjustmentEntryList)) {
    		AdjustmentMasterEntity adjustmentMasterEntity = adjustmentEntryList.get(adjustmentEntryList.size()-1);
    		if(adjustmentMasterEntity!=null) {
				//when multiple bills exist, adjust amount between latest and last unpaid bill for the connection so it is relevant
				//this is only for displaying - value is added in current bill amount
    			if(billMasList!=null && !billMasList.isEmpty() && billMasList.size() > 1) {
    				TbBillMas lastestBill = billMasList.get(billMasList.size()-1);
    				Optional<TbBillMas> oldestUnpaidBillIsPresent = billMasList.stream().filter(bill->bill.getBmPaidFlag()!= null && bill.getBmPaidFlag().equals(MainetConstants.N_FLAG)).findFirst();
    				TbBillMas oldestUnpaidBill = oldestUnpaidBillIsPresent.isPresent() ? oldestUnpaidBillIsPresent.get() : lastestBill;
            		for(AdjustmentMasterEntity entry :adjustmentEntryList) {
            			if(entry.getAdjDate().after(oldestUnpaidBill.getBmTodt())) {
            				//when multiple adjustments of positive and negative type exist
            				if(entry.getAdjType().equals("P")){
            					totalAdjValue+= entry.getAdjDetail().stream().filter(det->
            						MainetConstants.Common_Constant.YES.equals(det.getAdjAdjustedFlag())).
    							mapToDouble(amt -> amt.getAdjAdjustedAmount()).sum();
                			}else {
                				totalAdjValue-=entry.getAdjDetail().stream().mapToDouble(amt -> amt.getAdjAdjustedAmount()).sum();
                			}
            			}
            		}
    			}else {
    				for(AdjustmentMasterEntity entry :adjustmentEntryList) {
        				if(entry.getAdjType().equals("P")){
            				totalAdjValue+= entry.getAdjDetail().stream().mapToDouble(amt -> amt.getAdjAdjustedAmount()).sum();
            			}else {
            				totalAdjValue-=entry.getAdjDetail().stream().mapToDouble(amt -> amt.getAdjAdjustedAmount()).sum();
            			}
            		}
    			}
    		}
    	}	
	
    	return totalAdjValue;
    }

	/**
     * @param response
     * @param billScheduleDetailEntityList
     */
    private void calculatingFrequency(final WaterBillResponseDTO response,
            List<TbWtBillScheduleDetailEntity> billScheduleDetailEntityList) {
        int count = billScheduleDetailEntityList.size();
        if (count > 0) {
            if (count == 12) {
                response.setBillFrequencyIs(MainetConstants.SocialSecurity.MONTHLY);
            } else if (count == 2) {
                response.setBillFrequencyIs(MainetConstants.SocialSecurity.HALFYEARLY);
            } else if (count == 4) {
                response.setBillFrequencyIs(MainetConstants.SocialSecurity.QUATERLY);
            } else if (count == 6) {
                response.setBillFrequencyIs(MainetConstants.SocialSecurity.BIMONTHLY);
            } else if (count == 1) {
                response.setBillFrequencyIs(MainetConstants.SocialSecurity.YEARLY);
            }
        }
    }

    private void setAllArrearsAndCurrentByTaxesRmDpcl(final WaterBillResponseDTO response, final Organisation org,
            final TbBillMas watertax) {
    	LookUp taxCode;
        String lookString;
        WaterBillTaxDTO taxesDto;
        HashMap<String, WaterBillTaxDTO> taxDtoMap = new HashMap<>();
        for (final TbBillDet billDet : watertax.getTbWtBillDet()) {
            taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org);
            lookString = taxCode.getLookUpCode();
            
            if ((!lookString.equals(PrefixConstants.TAX_CATEGORY.ADVANCE))) {
                TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(), org.getOrgid());
                
                // Defect #133449 water tax is printing multiple times - Pushkar Dike 
                if(!taxDtoMap.containsKey(taxMas.getTaxDesc())) {
                	//put in map new entry
                	taxesDto = new WaterBillTaxDTO();
                    taxesDto.setTaxdescription(taxMas.getTaxDesc());
                    taxesDto.setTaxAmount(Math.round(billDet.getBdCurBalTaxamt()));
                    taxesDto.setBalabceTaxAmount(Math.round(billDet.getBdCurBalTaxamt()));
                    taxesDto.setArrearTaxAmount(Math.round(billDet.getBdPrvBalArramt()));
                    taxesDto.setTotal(Math.round(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt()));
                    if (taxesDto.getTotal() != 0.0) {
                        taxesDto.setTotal(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)?
                        	Math.round((taxesDto.getTotal() * 100.0 / 100.0)) : Math.round(taxesDto.getTotal() * 100.0) / 100.0);
                    }
                    taxesDto.setDispSeq(taxMas.getTaxDisplaySeq());
                	taxDtoMap.put(taxMas.getTaxDesc(), taxesDto);
                	
                } else {
                	//update map old entry
                	taxesDto = taxDtoMap.get(taxMas.getTaxDesc());
                    taxesDto.setTaxAmount(Math.round(billDet.getBdCurBalTaxamt() + taxesDto.getTaxAmount()));
                    taxesDto.setBalabceTaxAmount(Math.round(billDet.getBdCurBalTaxamt() + taxesDto.getBalabceTaxAmount()));
                    taxesDto.setArrearTaxAmount(Math.round(billDet.getBdPrvBalArramt() + taxesDto.getArrearTaxAmount()));
                    taxesDto.setTotal(Math.round(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt() + taxesDto.getTotal()));
                    if (taxesDto.getTotal() != 0.0) {
                        taxesDto.setTotal(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)?
                            	Math.round((taxesDto.getTotal() * 100.0 / 100.0)) : Math.round(taxesDto.getTotal() * 100.0) / 100.0);
                    }
                	taxDtoMap.put(taxMas.getTaxDesc(), taxesDto);
                }
                
            }
            if (watertax.getBmTotalAmount() == watertax.getBmTotalBalAmount()) {
                if (lookString.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                    response.setExcessAmount(Math.round(response.getExcessAmount() + billDet.getBdCurBalTaxamt()));
                }
            }
            
        }
        for(Entry<String, WaterBillTaxDTO> entry : taxDtoMap.entrySet()) {
        	response.getTaxes().add(entry.getValue());
        }

        // This line of coded is added to handle (Null Pointer Exception)if getDispSeq
        // is null in the list
        Comparator<WaterBillTaxDTO> comparing = Comparator.comparing(WaterBillTaxDTO::getDispSeq,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(response.getTaxes(), comparing);

    }
    
    private void setAllArrearsAndCurrentByTaxes(final WaterBillResponseDTO response, final Organisation org,
            final TbBillMas watertax) {
        LookUp taxCode;
        String lookString;
        WaterBillTaxDTO taxesDto;
        for (final TbBillDet billDet : watertax.getTbWtBillDet()) {
            taxCode = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(), org);
            lookString = taxCode.getLookUpCode();
            if ((!lookString.equals(PrefixConstants.TAX_CATEGORY.ADVANCE))) {
                TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(), org.getOrgid());
                taxesDto = new WaterBillTaxDTO();
                taxesDto.setTaxdescription(taxMas.getTaxDesc());
                taxesDto.setTaxAmount(Math.round(billDet.getBdCurTaxamt()));
                taxesDto.setBalabceTaxAmount(Math.round(billDet.getBdCurBalTaxamt()));
                taxesDto.setArrearTaxAmount(Math.round(billDet.getBdPrvBalArramt()));
                taxesDto.setTotal(Math.round(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt()));
                if (taxesDto.getTotal() != 0.0) {
                    taxesDto.setTotal(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)?
                        	Math.round((taxesDto.getTotal() * 100.0 / 100.0)) : Math.round(taxesDto.getTotal() * 100.0) / 100.0);
                }
                taxesDto.setDispSeq(taxMas.getTaxDisplaySeq());
                response.getTaxes().add(taxesDto);
            }
            if (watertax.getBmTotalAmount() == watertax.getBmTotalBalAmount()) {
                if (lookString.equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                    response.setExcessAmount(Math.round(response.getExcessAmount() + billDet.getBdCurBalTaxamt()));
                }
            }
        }

        // This line of coded is added to handle (Null Pointer Exception)if getDispSeq
        // is null in the list
        Comparator<WaterBillTaxDTO> comparing = Comparator.comparing(WaterBillTaxDTO::getDispSeq,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(response.getTaxes(), comparing);
    }


    @Override
    @Transactional(readOnly = true)
    public String getApplicantUserNameModuleWise(long orgId, String csIdn) {
        TbKCsmrInfoMH csmrinfo = waterRepository.fetchConnectionByCsIdn(Long.valueOf(csIdn), orgId);
        if (csmrinfo != null) {
            return csmrinfo.getCsName() + " " + csmrinfo.getCsLname();
        }
        return null;
    }

    @Override
    public VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress) {

        String propertyNo = feedetailDto.getAdditionalRefNo();
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<TbBillMas> billMasList = null;
        Long csIdn = Long.valueOf(feedetailDto.getAdditionalRefNo());
        billMasList = getBillMasListByCsidn(csIdn, orgId);
        if (billMasList != null && !billMasList.isEmpty()) {
            if (Utility.comapreDates(feedetailDto.getRmDate(), billMasList.get(billMasList.size() - 1).getBmBilldt())
                    || feedetailDto.getRmDate().after(billMasList.get(billMasList.size() - 1).getBmBilldt())) {

                List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();
                TbWtExcessAmt waterExcessAmt = tbWtExcessAmtJpaRepository.getAdvanceEntryByRecptId(feedetailDto.getRmRcptid(),
                        Long.valueOf(feedetailDto.getAdditionalRefNo()), orgId);

                if (waterExcessAmt != null) {
                    tbWtExcessAmtJpaRepository.inactiveAdvPayEnrtyByExcessId(waterExcessAmt.getExcessId(), orgId);
                    if (waterExcessAmt.getTaxId() != null) {
                        BillReceiptPostingDTO waterBillPosDto = new BillReceiptPostingDTO();
                        TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(waterExcessAmt.getTaxId(), org.getOrgid());
                        waterBillPosDto.setBillDetId(waterExcessAmt.getExcessId());
                        waterBillPosDto.setTaxId(waterExcessAmt.getTaxId());
                        waterBillPosDto.setTaxCategory(tax.getTaxCategory1());
                        waterBillPosDto.setTaxAmount(waterExcessAmt.getExcAmt());
                        waterBillPosDto.setYearId(Long.valueOf(Utility.getCurrentYear()));
                        billPosDtoList.add(waterBillPosDto);
                    }
                }

                List<TbServiceReceiptMasBean> feedetailDtoList = new ArrayList<TbServiceReceiptMasBean>();
                List<TbServiceReceiptMasBean> rebateReceiptList = iReceiptEntryService.findReceiptByReceiptDateType(
                        feedetailDto.getRmRcptid(), orgId, feedetailDto.getRmDate(), feedetailDto.getDpDeptId(), "RB");
                feedetailDtoList.add(feedetailDto);
                if (CollectionUtils.isNotEmpty(rebateReceiptList)) {
                    feedetailDtoList.addAll(rebateReceiptList);
                }

                rvertBill(feedetailDtoList, billPosDtoList, billMasList, org, userId, ipAddress);

                final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
                billMasList.get(billMasList.size() - 1).setBmToatlRebate(0);
                billMasterCommonService.updateBillData(billMasList, 0d, null, null, org, rebateDetails, null);

                // propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId, userId, null, ipAddress);
                saveAndUpdateMainBill(billMasList, orgId, userId, ipAddress);

                return iReceiptEntryService.getAccountPostingDtoForBillReversal(billPosDtoList, org);
            } else {
                return null;
            }
        }
        return null;

    }

    @Override
    public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TbBillMas> getBillMasListByCsidn(Long csIdn, Long orgId) {
        final List<TbBillMas> bills = new ArrayList<>();
        final List<TbWtBillMasEntity> billmas = billMasterJpaRepository.fetchBillMasData(csIdn, orgId);
        TbBillMas mas = null;
        List<TbBillDet> detList = null;
        if (billmas != null && !billmas.isEmpty()) {

            for (final TbWtBillMasEntity tbWtBillMasEntity : billmas) {
                Double amount = 0d;
                detList = new ArrayList<>(0);
                for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                    final TbBillDet det = new TbBillDet();
                    BeanUtils.copyProperties(detail, det);
                    detList.add(det);
                }
                mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                mas.setTbWtBillDet(detList);
                mas.setBmTotalOutstanding(amount);
                bills.add(mas);
            }
        }
        return bills;
    }

    private void rvertBill(List<TbServiceReceiptMasBean> feedetailDtoList, List<BillReceiptPostingDTO> billPosDtoList,
            List<TbBillMas> billMasList, Organisation org, Long userId, String ipAddress) {
        AtomicDouble totalCurBalAmt = new AtomicDouble(0);
        AtomicDouble totInt = new AtomicDouble(0);
        AtomicDouble totPenlty = new AtomicDouble(0);
        feedetailDtoList.forEach(feedetailDto -> {
            feedetailDto.getReceiptFeeDetail().forEach(det -> {
                billMasList.stream().filter(billMas -> Long.valueOf(billMas.getBmIdno()).equals(det.getBmIdNo()))
                        .forEach(billMas -> {
                            billMas.getTbWtBillDet().forEach(billDet -> {
                                if (det.getBilldetId() != null
                                        && det.getBilldetId().equals(Long.valueOf(billDet.getBdBilldetid()))) {
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

                TbTaxMas tax = tbTaxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), org.getOrgid());
                final String lookupCode = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org)
                        .getLookUpCode();
                if (StringUtils.isNotBlank(lookupCode) && lookupCode.equals(MainetConstants.Property.SURCHARGE)) {
                    BillReceiptPostingDTO billPosDto = new BillReceiptPostingDTO();
                    WaterPenaltyDto penaltyDto = waterPenaltyService
                            .getWaterPenaltyByCCNOByFinId(feedetailDto.getAdditionalRefNo(), 0L, org.getOrgid());
                    if( null != penaltyDto) {
                    if (penaltyDto.getActualAmount() > penaltyDto.getPendingAmount()) {
                        penaltyDto.setPendingAmount(penaltyDto.getPendingAmount() + det.getRfFeeamount().doubleValue());
                    }
                    waterPenaltyService.updateWaterPenalty(penaltyDto);
                    billPosDto.setBillDetId(penaltyDto.getPenaltyId());
                    billPosDto.setTaxId(penaltyDto.getTaxId());
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

    @Transactional
    @Override
    public List<TbWtBillMasEntity> saveAndUpdateMainBill(List<TbBillMas> billMasList, Long orgId, Long empId,
            String macAddress) {
        // Provisional bill mas
        List<TbWtBillMasEntity> mainBillEntList = new ArrayList<>();

        if (billMasList != null && !billMasList.isEmpty()) {
            billMasList.forEach(billMasDto -> {
                final TbWtBillMasEntity billMasEntity = new TbWtBillMasEntity();
                BeanUtils.copyProperties(billMasDto, billMasEntity);
                billMasEntity.setUserId(empId);
                billMasEntity.setOrgid(orgId);
                billMasEntity.setLgIpMac(macAddress);
                billMasEntity.setLmoddate(new Date());
                // Provisional bill detail
                final List<TbWtBillDetEntity> billDetEntityList = new ArrayList<>();
                billMasDto.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));// Sorting List by collection
                                                                                              // sequence
                billMasDto.getTbWtBillDet().forEach(billDetDto -> {
                    TbWtBillDetEntity billDetEntity = new TbWtBillDetEntity();
                    BeanUtils.copyProperties(billDetDto, billDetEntity);
                    billDetEntity.setBmIdNo(billMasEntity);
                    billDetEntity.setOrgid(orgId);
                    billDetEntity.setUserId(empId);
                    billDetEntity.setLgIpMac(macAddress);
                    billDetEntity.setLmoddate(new Date());
                    billDetEntityList.add(billDetEntity);
                });
                billMasEntity.setBillDetEntity(billDetEntityList);
                mainBillEntList.add(billMasEntity);
            });
        }
        billMasterJpaRepository
                .save(mainBillEntList);
        return mainBillEntList;
    }

    @Override
    @Transactional
    public CommonChallanDTO getBillDetails(CommonChallanDTO offline) {
        WaterBillRequestDTO requestDTO = new WaterBillRequestDTO();
        requestDTO.setCcnNumber(offline.getUniquePrimaryId());
        requestDTO.setOldccNumber(offline.getPropNoConnNoEstateNoL());
        requestDTO.setOrgid(offline.getOrgId());
        WaterBillResponseDTO responseDTO = fetchBillPaymentData(requestDTO);
        TbCsmrInfoDTO tbCsmrInfoDTO = null;
        List<TbWtBillMasEntity> billsMas = null;
        if (responseDTO != null && responseDTO.getCsIdn() != null) {
            tbCsmrInfoDTO = ApplicationContextProvider.getApplicationContext()
                    .getBean(NewWaterConnectionService.class).getConnectionDetailsById(responseDTO.getCsIdn());
            List<Long> csIdnList = new ArrayList<>();
            csIdnList.add(responseDTO.getCsIdn());
            billsMas = billMasterJpaRepository.checkBillsForNonMeter(csIdnList, offline.getOrgId());
        }
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(billsMas)) {
            map.put("bmIdno", String.valueOf(billsMas.get(0).getBmIdno()));
            map.put("bmBilldt", String.valueOf(billsMas.get(0).getBmBilldt()));
            map.put("bmDuedate", String.valueOf(billsMas.get(0).getBmDuedate()));
            map.put("billPeriod",
                    String.valueOf(billsMas.get(0).getBmFromdt()) + " to " + String.valueOf(billsMas.get(0).getBmTodt()));
        }
        offline.setBillDetails(map);
        offline.setAmountToPay(String.valueOf(responseDTO.getTotalPayableAmount()));
        offline.setAmountToPay(String.valueOf(Math.floor(responseDTO.getTotalPayableAmount() - responseDTO.getRebateAmount())));
        offline.setUserId(MainetConstants.Property.UserId);
        offline.setOrgId(requestDTO.getOrgid());
        offline.setLangId(1);
        offline.setFeeIds(new HashMap<>(0));
        offline.setBillDetIds(new HashMap<>(0));
        String userName = (responseDTO.getApplicantDto().getApplicantFirstName() == null ? MainetConstants.BLANK
                : responseDTO.getApplicantDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE);
        userName += responseDTO.getApplicantDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
                : responseDTO.getApplicantDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
        userName += responseDTO.getApplicantDto().getApplicantLastName() == null ? MainetConstants.BLANK
                : responseDTO.getApplicantDto().getApplicantLastName();
        offline.setApplicantName(userName);
        offline.setApplNo(responseDTO.getApplicationNumber());
        offline.setApplicantAddress(responseDTO.getApplicantDto().getAreaName());
        offline.setUniquePrimaryId(String.valueOf(responseDTO.getCsIdn()));
        offline.setMobileNumber(responseDTO.getApplicantDto().getMobileNo());
        offline.setEmailId(responseDTO.getApplicantDto().getEmailId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setPropNoConnNoEstateNoV(responseDTO.getCcnNumber());
        offline.setPropNoConnNoEstateNoL(ApplicationSession.getInstance().getMessage("water.ConnectionNo"));
        offline.setEmpType(null);
        offline.setReferenceNo(responseDTO.getOldccnNumber());
        offline.setApplicantFullName(userName);
        if (tbCsmrInfoDTO != null) {
            if (StringUtils.isNotEmpty(tbCsmrInfoDTO.getCsFlatno())) {
                offline.setFlatNo(tbCsmrInfoDTO.getCsFlatno());
            }
            if (tbCsmrInfoDTO.getCsOflatno() != null && !tbCsmrInfoDTO.getCsOflatno().equals(MainetConstants.BLANK)) {
                offline.setPlotNo(tbCsmrInfoDTO.getCsOflatno());
            }
            if(tbCsmrInfoDTO.getArv() != null) {
            	offline.setPdRv(tbCsmrInfoDTO.getArv());
            }
            WardZoneBlockDTO wardDto = new WardZoneBlockDTO();
            if (tbCsmrInfoDTO.getCodDwzid1() != null)
                wardDto.setAreaDivision1(tbCsmrInfoDTO.getCodDwzid1());
            if (tbCsmrInfoDTO.getCodDwzid2() != null)
                wardDto.setAreaDivision2(tbCsmrInfoDTO.getCodDwzid2());
            if (tbCsmrInfoDTO.getCodDwzid3() != null)
                wardDto.setAreaDivision3(tbCsmrInfoDTO.getCodDwzid3());
            if (tbCsmrInfoDTO.getCodDwzid4() != null)
                wardDto.setAreaDivision4(tbCsmrInfoDTO.getCodDwzid4());
            if (tbCsmrInfoDTO.getCodDwzid5() != null)
                wardDto.setAreaDivision5(tbCsmrInfoDTO.getCodDwzid5());
            offline.setDwzDTO(wardDto);
        }
        return offline;
    }

    @Override
    public Long getCountByApplNoOrLegacyNo(String applNo, String legacyApplNo, Long orgId) {
        Long count = null;
        if (StringUtils.isNotEmpty(applNo)) {
            count = tbWtBillMasJpaRepository.getCountByCcnNo(applNo, orgId);
        } else {
            count = tbWtBillMasJpaRepository.getCountByoldConnNo(legacyApplNo, orgId);
        }

        return count;
    }

    @Override
    public double getTotalOutstandingOfConnNosAssocWithPropNo(List<Long> csIdnList) {
        AtomicDouble outstandingAmount = new AtomicDouble(0);
        csIdnList.forEach(csIdn -> {
            List<Long> csIdns = new ArrayList<Long>();
            csIdns.add(csIdn);
            List<TbWtBillMasEntity> billMasEntity = billMasterJpaRepository.getBilMasterByCsIdList(csIdns);
            billMasEntity.forEach(billMas -> {
                billMas.getBillDetEntity().forEach(billDet -> {
                    outstandingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt());
                });
            });
        });
        return outstandingAmount.doubleValue();
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch outstanding By OrgId", notes = "Fetch outstanding By OrgId", response = Object.class)
    @Path("/getOuststandingByCsNo/CsCcn/{CsCcn}/orgId/{orgId}")
    public TbBillMas getOuststandingByCsNo(@PathParam("CsCcn") String conNo, @PathParam("orgId") Long orgId) {
        TbBillMas tbBillMas = new TbBillMas();
        TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
                .fetchConnectionDetailsByConnNo(conNo,
                        orgId);
        if (consumerdto != null) {
            tbBillMas.setCodDwzid1(consumerdto.getCodDwzid1());
            tbBillMas.setCodDwzid2(consumerdto.getCodDwzid2());
            tbBillMas.setCodDwzid3(consumerdto.getCodDwzid3());
            tbBillMas.setCodDwzid4(consumerdto.getCodDwzid4());
            tbBillMas.setCodDwzid5(consumerdto.getCodDwzid5());
            tbBillMas.setMobileNo(consumerdto.getCsContactno());
            
           //new fields added
           tbBillMas.setName(consumerdto.getCsName());
           tbBillMas.setTitle(consumerdto.getCsTitle());
           tbBillMas.setGender(consumerdto.getGenderDesc());
           tbBillMas.setAddress(consumerdto.getCsAdd());
           tbBillMas.setPincode(consumerdto.getCsCpinCode());
           //new fields added
            
        }

        TbWtBillMasEntity waterDues = ApplicationContextProvider.getApplicationContext().getBean(WaterNoDuesCertificateDao.class)
                .getWaterDues(consumerdto.getCsIdn(), orgId);
        if (waterDues != null) {
            double outStandingAmount = 0;
            if (CollectionUtils.isNotEmpty(waterDues.getBillDetEntity())) {
                for (TbWtBillDetEntity det : waterDues.getBillDetEntity()) {
                    outStandingAmount = outStandingAmount + (det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                }
            }
            waterDues.setBmTotalOutstanding(outStandingAmount);
            BeanUtils.copyProperties(waterDues, tbBillMas);
        }

        return tbBillMas;
    }

    @Override
    @Transactional(readOnly = true)
    public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto) {
        LOGGER.info(".......fetchDishonorCharge method start for getting dishonor charge....");
        TbSrcptModesDetBean dto = brmsWaterService.getDishonorCharges(deptId, org, chequeModeDto);
        return dto;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean revertBills(TbSrcptModesDetBean feedetailDto, Long userId, String ipAdress) {
        LOGGER.info(".....Revert Bill methods start.......");
        Long orgid = feedetailDto.getOrgid();
        Organisation org = new Organisation();
        org.setOrgid(orgid);
        String csIdn = feedetailDto.getAddRefNo();
        TbCsmrInfoDTO csmrInfoDto = null;
        if (csIdn != null && !csIdn.isEmpty()) {
            final TbKCsmrInfoMH waterEntity = waterRepository.fetchConnectionByCsIdn(Long.valueOf(csIdn), orgid);
            if (waterEntity != null) {
                csmrInfoDto = new TbCsmrInfoDTO();
                BeanUtils.copyProperties(waterEntity, csmrInfoDto);
            }
        }
        // final List<TbWtBillMasEntity> entities = billMasterJpaRepository.getBillMasterListByCsidn(Long.valueOf(csIdn), orgid);

        // List<TbBillMas> billMasterList=getBillMasterListByUniqueIdentifier(Long.valueOf(csIdn), orgid);
        List<TbBillMas> billMasterList = getBillMasListByCsidn(Long.valueOf(csIdn), orgid);
        /*
         * final TbBillMas latestBillMaster = billMasterList.get(0);
         */

        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE);

        List<Long> billIds = new ArrayList<>(feedetailDto.getBillNoAndAmountMap().keySet());
        List<Long> billId = billIds.stream().filter(str -> str != null).collect(Collectors.toList());
        List<TbBillMas> billMasList = null;
        TbBillMas latestBill = new TbBillMas();
        if (!billMasterList.isEmpty() && billMasterList != null)
            latestBill = billMasterList.get(billMasterList.size() - 1);

        if (billId != null && !billId.isEmpty()) {
            billMasList = fetchBillsListByBmId(billId, orgid);
        }
        
        //logic for reverting penalty
        List<Long> billIdsForPenalty = new ArrayList<>(feedetailDto.getPenaltyWiseBillNoAndAmountMap().keySet());
        List<Long> billIdForPenalty = billIdsForPenalty.stream().filter(str -> str != null).collect(Collectors.toList());
        List<WaterPenaltyDto> billMasForPenaltyList = null;
        if (billIdForPenalty != null && !billIdForPenalty.isEmpty()) {
        	billMasForPenaltyList = fetchPenaltyListByBmId(billIdForPenalty, orgid);
        }

        // revert advanced amount if present
        BillReceiptPostingDTO excessReceiptDto = revertAndUpdateAdvanceAmtIfExist(feedetailDto, userId, ipAdress,
                orgid);
        if (billMasList != null && !billMasList.isEmpty()) {
            List<BillReceiptPostingDTO> billPosDtoList = new ArrayList<>();
            List<TbBillMas> billList = billMasList;
            // revert dishonor amount
            updateBillDetData(feedetailDto, billList);
            updateBillMasData(org, billMasList);
            if (excessReceiptDto != null) {
                billPosDtoList.add(excessReceiptDto);
            }
            // add Dishonor charge in bill det
            addDishonorCharges(feedetailDto, org, deptId, billMasList, latestBill);
            if (!billMasList.isEmpty()) {
                billMasterCommonService.updateBillData(billMasList, 0d, null, null, org, null, null);
            }
            saveAndUpdateMainBill(billMasList, orgid, userId, ipAdress);
            // saveAndUpdateMainBill(latestBill, orgid, userId, ipAdress);
        }
        
        //update penalty amount
        if(billMasForPenaltyList != null && !billMasForPenaltyList.isEmpty()) {
        	List<WaterPenaltyDto> billForPenaltyList = billMasForPenaltyList;
        	updatePenaltyData(feedetailDto, billForPenaltyList);
        	saveAndUpdatePenalty(billForPenaltyList, orgid, userId, ipAdress);
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
                    feeDet.setRd_dishonor_remark(feedetailDto.getRd_dishonor_remark());
                    LookUp CLRPrefix = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.DISHONOUR,
                            MainetConstants.Property.CLR, org);
                    if (CLRPrefix != null)
                        feeDet.setCheckStatus(CLRPrefix.getLookUpId());
                    chequeDishonorService.updateFeeDet(feeDet);
                }
            }
        } catch (Exception e) {
            throw new FrameworkException("Exception occured while update receipt mode details.");
        }

        sendSMSAndEmail(csmrInfoDto, feedetailDto);
        LOGGER.info(".....Revert Bill methods End.......");
        return true;
    }

    private void saveAndUpdatePenalty(List<WaterPenaltyDto> billForPenaltyList, Long orgid, Long userId,
			String ipAdress) {
    	List<WaterPenaltyEntity> penaltyEntList = new ArrayList<>();

        if (billForPenaltyList != null && !billForPenaltyList.isEmpty()) {
        	billForPenaltyList.forEach(penalty -> {
                final WaterPenaltyEntity penaltyEntity = new WaterPenaltyEntity();
                BeanUtils.copyProperties(penalty, penaltyEntity);
                penaltyEntity.setOrgId(orgid);
                penaltyEntity.setLgIpmac(ipAdress);
                penaltyEntity.setUpdatedDate(new Date());
                penaltyEntList.add(penaltyEntity);
            });
        }
        waterPenaltyRepository
                .save(penaltyEntList);
		
	}

	private List<WaterPenaltyDto> fetchPenaltyListByBmId(List<Long> billIdForPenalty, Long orgid) {
    	final List<WaterPenaltyDto> penaltyList = new ArrayList<>();
        final List<WaterPenaltyEntity> penaltyEntityList = waterPenaltyRepository.getWaterPenaltyByBmIdNos(billIdForPenalty, orgid);
        WaterPenaltyDto pen = null;
        if (penaltyEntityList != null && !penaltyEntityList.isEmpty()) {
            for (final WaterPenaltyEntity waterPenaltyEntity : penaltyEntityList) {
                pen = new WaterPenaltyDto();
                BeanUtils.copyProperties(waterPenaltyEntity, pen);
                penaltyList.add(pen);
            }
        }
        return penaltyList;
	}

	private void sendSMSAndEmail(TbCsmrInfoDTO csmrInfoDto, TbSrcptModesDetBean feedetailDto) {
        String url = "ChequeDishonor.html";
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        Organisation org = new Organisation();
        if (csmrInfoDto != null && feedetailDto != null) {
            dto.setMobnumber(csmrInfoDto.getCsOcontactno());
            dto.setEmail(csmrInfoDto.getCsOEmail());
            org.setOrgid(csmrInfoDto.getOrgId());
            if (feedetailDto.getRdChequedddate() != null)
                dto.setAppDate(Utility.dateToString(feedetailDto.getRdChequedddate()));
            if (feedetailDto.getRdChequeddno() != null)
                dto.setReferenceNo(feedetailDto.getRdChequeddno().toString());
            if (feedetailDto.getRdSrChkDisChg() != null)
                dto.setAmount(feedetailDto.getRdSrChkDisChg());
        }
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("WCD", org.getOrgid());
        if (serviceMaster != null) {
            dto.setServName(serviceMaster.getSmServiceName());
        }
        }
        int langId = Utility.getDefaultLanguageId(org);
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.WATER, url,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto, org, langId);
    }

    private void updateBillDetData(TbSrcptModesDetBean feedetailDto, List<TbBillMas> billList) {
        AtomicDouble modeAmt = new AtomicDouble(0);
        feedetailDto.getBillNoAndAmountMap().forEach((key, value) -> {
            modeAmt.getAndSet(value.doubleValue());
            billList.stream().filter(bill -> bill.getBmIdno() == key).forEach(bill -> {
                bill.getTbWtBillDet().forEach(billDet -> {
                    if (billDet.getBdCurBalTaxamt() < billDet.getBdCurTaxamt()) {
                        billDet.setBdCurBalTaxamt(modeAmt.doubleValue() + billDet.getBdCurBalTaxamt());
                        double diffAmt = billDet.getBdCurBalTaxamt() - billDet.getBdCurTaxamt();
                        if (diffAmt < 0) {
                            modeAmt.getAndSet(0);
                        } else {
                            modeAmt.getAndSet(diffAmt);
                            billDet.setBdCurBalTaxamt(billDet.getBdCurTaxamt());
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

    private void addDishonorCharges(TbSrcptModesDetBean feedetailDto, Organisation org, Long deptId,
            List<TbBillMas> billMasList, TbBillMas latestBill) {
        LOGGER.info(".....addDishonorCharges methods start.......");
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
                // Optional<TbBillMas> bill=billMasList.stream().filter(f->f.getBmIdno()==latestBill.getBmIdno()).findFirst();

                List<TbBillMas> bill = billMasList.stream()
                        .filter(detail -> detail.getBmIdno() == latestBill.getBmIdno()).collect(Collectors.toList());

                TbBillMas billMas = null;
                if (CollectionUtils.isNotEmpty(bill)) {
                    billMas = bill.get(0);
                } else {
                    billMas = latestBill;
                }
                boolean isexist = billMas.getTbWtBillDet().stream()
                        .filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString())).findFirst()
                        .isPresent();
                if (isexist) {
                    billMas.getTbWtBillDet().stream()
                            .filter(s -> s.getTaxId().toString().equals(det.getTaxId().toString()))
                            .forEach(taxDet -> {
                                taxDet.setBdCurTaxamt(taxDet.getBdCurTaxamt() + feedetailDto.getRdSrChkDisChg());
                                taxDet.setBdCurBalTaxamt(
                                        taxDet.getBdCurBalTaxamt() + feedetailDto.getRdSrChkDisChg());
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
            }
        }
        LOGGER.info(".....addDishonorCharges methods End.......");
    }

    private BillReceiptPostingDTO revertAndUpdateAdvanceAmtIfExist(TbSrcptModesDetBean feedetailDto, Long userId,
            String ipAddress, Long orgId) {
        LOGGER.info(".....Advanced Amount revert methods start.......");
        TbWtExcessAmt waterExcessAmt = tbWtExcessAmtJpaRepository.getAdvanceEntryByRecptId(feedetailDto.getReceiptId(),
                Long.valueOf(feedetailDto.getAddRefNo()), orgId);
        // removing advance amt from bill wise amount map (key is null because in
        // advance amt bill no is null)
        Double advanceAmt = feedetailDto.getBillNoAndAmountMap().remove(null);
        if (waterExcessAmt != null && waterExcessAmt.getExcAmt() > 0 && advanceAmt != null) {
            // final TbWtExcessAmtHist excessAmtHist = new TbWtExcessAmtHist();
            // auditService.createHistory(accessAmtEnt, excessAmtHist);// add to history
            double actualAmt = waterExcessAmt.getExcAmt();
            // Knock off advance amount first
            if (waterExcessAmt.getExcAmt() >= advanceAmt) {
                waterExcessAmt.setExcAmt(waterExcessAmt.getExcAmt() - advanceAmt);
            } else {
                LOGGER.error("Problem while reverting advance amount in cheque dishonor,"
                        + "advance amt in receipt is more then amount from advnce table");
            }
            // waterExcessAmt.setAdjAmt(waterExcessAmt.getAdjAmt() + (actualAmt - waterExcessAmt.getExcAmt()));
            waterExcessAmt.setUpdatedBy(userId);
            waterExcessAmt.setUpdatedDate(new Date());
            waterExcessAmt.setLgIpMacUpd(ipAddress);
            saveOrUpdateExcessAmt(userId, waterExcessAmt);
        }
        BillReceiptPostingDTO excessReceiptDto = null;
        LOGGER.info(".....Advanced Amount revert methods End.......");
        return excessReceiptDto;
    }

    /**
     * @param userId
     * @param waterExcessAmt
     */
    private void saveOrUpdateExcessAmt(Long userId, TbWtExcessAmt waterExcessAmt) {
        final String ipAddress1 = Utility.getMacAddress();
        if (waterExcessAmt.getExcessId() == 0L) {
            waterExcessAmt.setLgIpMac(ipAddress1);
        } else {
            waterExcessAmt.setUpdatedBy(userId);
            waterExcessAmt.setLgIpMacUpd(ipAddress1);
            waterExcessAmt.setUpdatedDate(new Date());
        }
        tbWtExcessAmtJpaRepository.save(waterExcessAmt);
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public List<TbBillMas> fetchBillsListByBmId(final List<Long> bmIdNo, final Long orgid) {
        final List<TbBillMas> bills = new ArrayList<>();
        final List<TbWtBillMasEntity> billmas = billMasterJpaRepository.fetchBillsListByBmId(bmIdNo, orgid);
        TbBillMas mas = null;
        List<TbBillDet> detList = null;
        if (billmas != null && !billmas.isEmpty()) {
            Organisation org = new Organisation();
            org.setOrgid(orgid);
            Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", org).getLookUpId();

            final List<LookUp> taxCategory = CommonMasterUtility.getLevelData(PrefixConstants.LookUpPrefix.TAC,
                    MainetConstants.NUMBERS.ONE, org);
            Long demandId = null;
            Long penaltyId = null;
            if ((taxCategory != null) && !taxCategory.isEmpty()) {
                for (final LookUp lookupid : taxCategory) {
                    if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.DEMAND)) {
                        demandId = lookupid.getLookUpId();
                    }
                    if (lookupid.getLookUpCode().equals(PrefixConstants.TAX_CATEGORY.PENALTY)) {
                        penaltyId = lookupid.getLookUpId();
                    }
                }
            }
            for (final TbWtBillMasEntity tbWtBillMasEntity : billmas) {
                Double amount = 0d;
                detList = new ArrayList<>(0);
                for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                    if (demandId.equals(detail.getTaxCategory())) {
                        final TbBillDet det = new TbBillDet();
                        BeanUtils.copyProperties(detail, det);
                        det.setTddTaxid(currDemandId);
                        amount += det.getBdCurTaxamt();
                        detList.add(det);
                    }
                    if (penaltyId.equals(detail.getTaxCategory())) {
                        final TbBillDet det = new TbBillDet();
                        BeanUtils.copyProperties(detail, det);
                        det.setTddTaxid(currDemandId);
                        amount += det.getBdCurTaxamt();
                        detList.add(det);
                    }
                }
                mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                mas.setTbWtBillDet(detList);
                mas.setBmTotalOutstanding(amount);
                bills.add(mas);
            }
        }
        return bills;
    }

    @Override
    public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TbBillMas> fetchAllBillByCsIdn(Long csIdno, long orgId) {
        List<TbBillMas> billMasList = new ArrayList<>(0);
        
        List<TbWtBillMasEntity> billMasEntityList = billMasterJpaRepository.fetchAllBillByCsIdn(csIdno, orgId);

        if (billMasEntityList != null && !billMasEntityList.isEmpty()) {
        	billMasEntityList.forEach(billMas -> {
                TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                billMasList.add(billMasdto);
            });
        }
        return billMasList;
    }
    
    private TbBillMas getBillMasDtoByBillMasEnt(TbWtBillMasEntity billMas) {
        TbBillMas billMasdto = new TbBillMas();
        BeanUtils.copyProperties(billMas, billMasdto);
        List<TbBillDet> billDetList = new ArrayList<>();
        billMas.getBillDetEntity().forEach(billDetEntity -> {
            TbBillDet billDet = new TbBillDet();
            billDet.setBmIdno(billMas.getBmIdno());
            BeanUtils.copyProperties(billDetEntity, billDet);
            billDet.setBdCsmp(BigDecimal.valueOf(billDet.getBdCurBalTaxamt()));
            billDetList.add(billDet);
        });
        billMasdto.setTbWtBillDet(billDetList);
        return billMasdto;
    }
    
    private void updatePenaltyData(TbSrcptModesDetBean feedetailDto, List<WaterPenaltyDto> penaltyList) {
    	AtomicDouble modeAmt = new AtomicDouble(0);
    	
    	for(WaterPenaltyDto penaltyDto : penaltyList) {
    		if(penaltyDto.getBmIdNo() != null && feedetailDto.getPenaltyWiseBillNoAndAmountMap() != null 
    				&& feedetailDto.getPenaltyWiseBillNoAndAmountMap().containsKey(penaltyDto.getBmIdNo())) {
    			modeAmt.getAndSet(feedetailDto.getPenaltyWiseBillNoAndAmountMap().get(penaltyDto.getBmIdNo()));
    			penaltyDto.setPendingAmount(modeAmt.doubleValue() + penaltyDto.getPendingAmount());
    			modeAmt.getAndSet(0);
    		}
    	}
    	
//        feedetailDto.getPenaltyWiseBillNoAndAmountMap().forEach((key, value) -> {
//            modeAmt.getAndSet(value.doubleValue());
//            penaltyList.stream().filter(penalty -> penalty.getBmIdNo().equals(key)).forEach(penalty -> {
//            	penalty.setPendingAmount(modeAmt.doubleValue() + penalty.getPendingAmount());
//            	modeAmt.getAndSet(0);
//            });
//
//        });
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch Payment History By CsCCn", notes = "Fetch Payment History By CsCCn", response = Object.class)
    @Path("/getPaymentHistoryByCsNo/CsCcn/{CsCcn}/orgId/{orgId}")
    public List<TbRcptDet> getPaymentHistoryByCsCcn(@PathParam("CsCcn") String conNo, @PathParam("orgId") Long orgId) {
        
    	List<TbRcptDet> listRcptDet = new ArrayList<TbRcptDet>();
    	try {
	    	TbCsmrInfoDTO consumerdto = null;
	    		consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
	                .fetchConnectionDetailsByConnNo(conNo, orgId);
	    	if (consumerdto != null && consumerdto.getCsIdn() != 0) {
     		
     			List<TbServiceReceiptMasEntity> lstRcpt = iReceiptEntryService.getPaymentHistoryByAdditinalRefNo(
     					String.valueOf(consumerdto.getCsIdn()), orgId);
     		
	     		if(lstRcpt != null && !lstRcpt.isEmpty()){
	     			for(TbServiceReceiptMasEntity masEntity: lstRcpt){
	     				TbRcptDet rcptDet = new TbRcptDet();
	     				
	     				if(null != masEntity.getRmRcptno()) {
	     					rcptDet.setRcptNo(masEntity.getRmRcptno().longValue());
	     				}
	     				
	     				if(null != masEntity.getRmDate()){
	     					rcptDet.setRcptDate(masEntity.getRmDate());
	     					rcptDet.setRcptYear(new SimpleDateFormat("yyyy").format(rcptDet.getRcptDate()));
	     				}
	     				
	     				if(null != masEntity.getRmAmount()) {
	     					rcptDet.setRcptAmount(masEntity.getRmAmount().doubleValue());
	     				}
	     				if(null != masEntity.getRmReceivedfrom()) {
	     					rcptDet.setRcptFrom(masEntity.getRmReceivedfrom());
	     				}
	     				
	     				listRcptDet.add(rcptDet);
	     			}
	     		}
     		}else {
 	    		TbRcptDet det = new TbRcptDet();
 	    		det.setErrorMessage("Connection no. is not valid");
 	    		listRcptDet.add(det);
 	    		Log.error("Exception in fetchConnectionDetailsByConnNo for connection no "+ conNo);
     		}
    	}catch(Exception ex) {
 			TbRcptDet det = new TbRcptDet();
 			det.setErrorMessage("Something has gone wrong, please try again");
   			listRcptDet.add(det); 
   			LOGGER.error("Exception occured in payment history api for connection no. " + conNo + " and orgId " + orgId 
   					+ " " + ex.getMessage());
 		}
       return listRcptDet;
    }

    @Transactional(readOnly = true)
    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch Bill History By CsCCn", notes = "Fetch Bill History By CsCCn", response = Object.class)
    @Path("/getBillHistoryByCsCcn/CsCcn/{CsCcn}/orgId/{orgId}")
	public List<TbBillMas> getBilltHistoryByCsNo(@PathParam("CsCcn") String conNo, @PathParam("orgId") Long orgId) {

    	 List<TbBillMas> billMasList = new ArrayList<>(0);

    	 TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
                 .fetchConnectionDetailsByConnNo(conNo,
                         orgId);
    	 
         List<TbWtBillMasEntity> billMasEntityList = null;

         if (consumerdto != null) {
         	
         	if(consumerdto.getCsIdn() != 0)
         	{
         		billMasEntityList = billMasterJpaRepository.fetchAllBillByCsIdn(consumerdto.getCsIdn(), orgId);
         		
         		 if (billMasEntityList != null && !billMasEntityList.isEmpty()) {
                 	billMasEntityList.forEach(billMas -> {
                         TbBillMas billMasdto = getBillMasDtoByBillMasEnt(billMas);
                         billMasList.add(billMasdto);
                     });
                 }
                 return billMasList;
         	}
         }
         
    	return null;
	}
    
    public List<TbBillMas> getWaterChargesListByUniqueIdentifier(final long csIdn, final long orgid) {
        final List<TbWtBillMasEntity> entities = billMasterJpaRepository.getBillMasterListByCsidn(csIdn, orgid);
        final List<TbBillMas> beans = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            Organisation org = new Organisation();
            org.setOrgid(orgid);
            LookUp taxSubCat = null;
            TbTaxMasEntity taxMas = billMasterCommonService.getInterestTax(orgid,
                    MainetConstants.WATER_DEPARTMENT_CODE);
            if (taxMas != null) {
                taxSubCat = CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory2(), org);
            }
            for (final TbWtBillMasEntity tbWtBillMasEntity : entities) {
                final List<TbBillDet> detList = new ArrayList<>(0);
                if ((tbWtBillMasEntity.getBillDetEntity() != null) && !tbWtBillMasEntity.getBillDetEntity().isEmpty()) {
                    for (final TbWtBillDetEntity detail : tbWtBillMasEntity.getBillDetEntity()) {
                    	TbTaxMas tax = tbTaxMasService.findById(detail.getTaxId(), orgid);
                    	if(MainetConstants.ComparamMasterConstants.WATER_CHARGES.equals(tax.getTaxDesc())) {
                    		TbBillDet det = new TbBillDet();
                            BeanUtils.copyProperties(detail, det);
                            det.setBmIdno(tbWtBillMasEntity.getBmIdno());
                            detList.add(det);	
                    	}
                        
                    }
                }
                final TbBillMas mas = new TbBillMas();
                BeanUtils.copyProperties(tbWtBillMasEntity, mas);
                if (taxSubCat != null) {
                    mas.setInterstCalMethod(taxSubCat.getLookUpCode());
                }
                detList.sort(Comparator.comparing(TbBillDet::getCollSeq));
                mas.setTbWtBillDet(detList);
                beans.add(mas);
            }
        }
        return beans;
    }
    
	private void setSurchargeDataForReceipt(TbBillMas tbBillMas, double amountPaidSurcharge,
			WaterPenaltyDto waterPenaltyObj, Double totalSurcharge, List<BillReceiptPostingDTO> result, Organisation org) {
    	 BillReceiptPostingDTO surChargeTax2 = new BillReceiptPostingDTO();
         surChargeTax2.setBillMasId(tbBillMas.getBmIdno()); 
         //surChargeTax.setTaxAmount(amountPaidSurcharge + surChargeTax.getTaxAmount()); 
         surChargeTax2.setTaxAmount(amountPaidSurcharge);
         surChargeTax2.setTaxId(waterPenaltyObj.getTaxId()); 
         //surChargeTax.setTotalDetAmount(waterPenaltyObj.getPendingAmount() + (surChargeTax.getTotalDetAmount() != null ? surChargeTax.getTotalDetAmount() : 0.0));
         surChargeTax2.setTotalDetAmount(waterPenaltyObj.getPendingAmount());
         surChargeTax2.setYearId(waterPenaltyObj.getFinYearId());
         TbTaxMas taxMasSurcharge = tbTaxMasService.findById(waterPenaltyObj.getTaxId(), waterPenaltyObj.getOrgId());
         final String taxCode = CommonMasterUtility.getHierarchicalLookUp(taxMasSurcharge.getTaxCategory1(), org)
                 .getLookUpCode();
         surChargeTax2.setTaxCategoryCode(taxCode);
         surChargeTax2.setTaxCategory(taxMasSurcharge.getTaxCategory1());
         surChargeTax2.setTotalSurcharge(totalSurcharge);
         
         if (surChargeTax2 != null && surChargeTax2.getTaxId() != null) {
             result.add(surChargeTax2);
         }		
	}
		
	@Override
	@Produces( MediaType.APPLICATION_JSON_VALUE)
	@POST
	@ApiOperation(value = "Fetch Payment History By rcptNo", notes = "Fetch Payment History By rcptNo", response = Object.class)
	@Path("/getPaymentHistorySKDCLByCsCcn/CsCcn/{CsCcn}/rcptNo/{rcptNo}/orgId/{orgId}")
	public List<TbRcptDet> getPaymentHistorySKDCLByCsCcn(@PathParam("CsCcn") String conNo, @PathParam("rcptNo") Long rcptNo,
			@PathParam("orgId") Long orgId){
		LOGGER.info("In method : getPaymentHistorySKDCLByCsCcn for ccn " + conNo);

		List<TbRcptDet> listRcptDet = new ArrayList<TbRcptDet>();
		try {
			Map<String, TbSrcptFeesDetEntity> receiptDetailsByTaxDesc = new HashMap<>();
			Map<String, TbWtBillDetEntity> billDetailsByTaxDesc = new HashMap<>();
			Map<String, WaterPenaltyEntity> penaltyByTaxDesc = new HashMap<>();
			try {
				TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
		                .fetchConnectionDetailsByConnNo(conNo, orgId);
		        if (consumerdto != null && consumerdto.getCsIdn()!=0) {
		        	
		        	TbServiceReceiptMasEntity receiptMastList = iReceiptEntryService.getReceiptDetailByCsCcnRcptNoAndOrgId(String.valueOf(consumerdto.getCsIdn()),
		        			rcptNo, orgId);
		    		
		    		//getting tax wise collection 
		        	if(receiptMastList!=null) {
		        		Map<Long, List<TbSrcptFeesDetEntity>> receiptDetailsByTaxId = receiptMastList.getReceiptFeeDetail().stream().filter(
								det->det.getTaxId()!=null).collect(Collectors.groupingBy(det->det.getTaxId()));
				    		for(Entry<Long, List<TbSrcptFeesDetEntity>> taxWiseReceiptDetails : receiptDetailsByTaxId.entrySet()) { 
								TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(taxWiseReceiptDetails.getKey(), orgId);
								if(taxMas!=null) {
									if(receiptDetailsByTaxDesc.containsKey(taxMas.getTaxDesc())) {
										TbSrcptFeesDetEntity existingReceiptDetail = receiptDetailsByTaxDesc.get(taxMas.getTaxDesc());
										existingReceiptDetail.setRfFeeamount(existingReceiptDetail.getRfFeeamount().add(
											taxWiseReceiptDetails.getValue().get(0).getRfFeeamount()));
										receiptDetailsByTaxDesc.put(taxMas.getTaxDesc(), existingReceiptDetail);
									}else {
										if(taxWiseReceiptDetails.getValue().size()>1) {
											TbSrcptFeesDetEntity existingReceiptDetail = new TbSrcptFeesDetEntity();
											BigDecimal totalCollection = taxWiseReceiptDetails.getValue().stream().map(x -> x.getRfFeeamount())   
								                .reduce(BigDecimal.ZERO, BigDecimal::add);
											existingReceiptDetail.setRfFeeamount(totalCollection);
				    						receiptDetailsByTaxDesc.put(taxMas.getTaxDesc(), existingReceiptDetail);
										}else {
				    						receiptDetailsByTaxDesc.put(taxMas.getTaxDesc(), taxWiseReceiptDetails.getValue().get(0));
										}
									}
								}
							}
				    	
		        	}
		    		
		    		List<Long> bmIdNoList = new ArrayList<>();

		    		Map<Long, List<TbSrcptFeesDetEntity>> receiptByBmIdNo = receiptMastList.getReceiptFeeDetail().stream().filter(
						det->det.getBmIdNo()!=null).collect(Collectors.groupingBy(det->det.getBmIdNo()));
		    		
		    		if(receiptByBmIdNo!=null && !receiptByBmIdNo.isEmpty()) {
		    			bmIdNoList.addAll(receiptByBmIdNo.keySet());
			    		List<TbWtBillMasEntity> billMasEntityList = billMasterJpaRepository.fetchBillsListByBmId(bmIdNoList, orgId);
			        	
			        	if (CollectionUtils.isNotEmpty(billMasEntityList)) {
			        		for(TbWtBillMasEntity billMasEntity : billMasEntityList) {
				        		//getting tax wise bill details exclusive of penalty 
			        			Map<Long, List<TbWtBillDetEntity>> taxIdWiseBillDet = billMasEntity.getBillDetEntity().stream().filter(
			    					det->det.getTaxId()!=null).collect(Collectors.groupingBy(det->det.getTaxId()));
			        			
			        			for(Entry<Long, List<TbWtBillDetEntity>> taxWiseBillDetails : taxIdWiseBillDet.entrySet()) { 
			    					TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(taxWiseBillDetails.getKey(), orgId);
			    					if(taxMas!=null) {
			    						if(billDetailsByTaxDesc.containsKey(taxMas.getTaxDesc())) {
			    							TbWtBillDetEntity existingBillDetail = billDetailsByTaxDesc.get(taxMas.getTaxDesc());
			    							existingBillDetail.setBdCurTaxamt(existingBillDetail.getBdCurTaxamt() + 
												taxWiseBillDetails.getValue().get(0).getBdCurTaxamt());
											billDetailsByTaxDesc.put(taxMas.getTaxDesc(), existingBillDetail);
			        					}else {
			        						if(taxWiseBillDetails.getValue().size() > 1) {
			        							TbWtBillDetEntity existingBillDetail = new TbWtBillDetEntity();
			        							double sum = taxWiseBillDetails.getValue().stream().mapToDouble(det->det.getBdCurTaxamt()).sum();
			        							existingBillDetail.setBdCurTaxamt(sum);
			        							billDetailsByTaxDesc.put(taxMas.getTaxDesc(), existingBillDetail);
			        						}else {
			        							billDetailsByTaxDesc.put(taxMas.getTaxDesc(), taxWiseBillDetails.getValue().get(0));
			        						}
			        					}
			    					}
								}
			    			}
			        	}
		        	
			        	List<WaterPenaltyEntity> penaltyList = waterPenaltyRepository.getWaterPenaltyByBmIdNos(bmIdNoList, orgId);
							        	
			        	if (CollectionUtils.isNotEmpty(penaltyList)) {
			        		//getting penalty to be collected
			    			Map<Long, List<WaterPenaltyEntity>> taxIdWisePenalty = penaltyList.stream().filter(
								det->det.getTaxId()!=null).collect(Collectors.groupingBy(det->det.getTaxId()));
			        			
		        			for(Entry<Long, List<WaterPenaltyEntity>> taxWisePenalty : taxIdWisePenalty.entrySet()) { 
		    					TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(taxWisePenalty.getKey(), orgId);
		    					if(taxMas!=null) {
		    						if(billDetailsByTaxDesc.containsKey(taxMas.getTaxDesc())) {
		    							WaterPenaltyEntity existingPenalty = penaltyByTaxDesc.get(taxMas.getTaxDesc());
		    							existingPenalty.setActualAmount(existingPenalty.getActualAmount() + 
		    									taxWisePenalty.getValue().get(0).getActualAmount());
		    							penaltyByTaxDesc.put(taxMas.getTaxDesc(), existingPenalty);
		        					}else {
		        						if(taxWisePenalty.getValue().size()>1) {
		        							WaterPenaltyEntity existingPenalty = new WaterPenaltyEntity();
		        							double sum = taxWisePenalty.getValue().stream().mapToDouble(pen->pen.getActualAmount()).sum();
		        							existingPenalty.setActualAmount(sum);
		        							penaltyByTaxDesc.put(taxMas.getTaxDesc(), existingPenalty);
		        						}else {
			        						penaltyByTaxDesc.put(taxMas.getTaxDesc(), taxWisePenalty.getValue().get(0));
		        						}
		        					}
		    					}
			    			}
			        	}
		    		}
		    		
		        	for(Entry<String, TbSrcptFeesDetEntity> collectionAmount: receiptDetailsByTaxDesc.entrySet()) {
		    			TbRcptDet recDet = new TbRcptDet(); 
		    			recDet.setTaxDescription(collectionAmount.getKey());
		    			recDet.setDueAmount((billDetailsByTaxDesc.get(collectionAmount.getKey())!=null ?
		    					Math.round(billDetailsByTaxDesc.get(collectionAmount.getKey()).getBdCurTaxamt()) : 0d) + 
		    					(penaltyByTaxDesc.get(collectionAmount.getKey())!=null ? 
		    							Math.round(penaltyByTaxDesc.get(collectionAmount.getKey()).getActualAmount()) : 0d));
		    			recDet.setReceivedAmount(collectionAmount.getValue().getRfFeeamount().doubleValue());
		    			listRcptDet.add(recDet);
					}
		        }else {
	    			 TbRcptDet recDet = new TbRcptDet(); 
	    			 recDet.setErrorMessage("Connection no. is not valid");
					 listRcptDet.add(recDet); 
			     }
			}catch(Exception ex) {
	        	LOGGER.error("Exception occured while calling fetchConnectionDetailsByConnNo for connection no: " + conNo + " -> " 
	        			+ ex.getMessage());
	        	 TbRcptDet recDet = new TbRcptDet(); 
    			 recDet.setErrorMessage("Something has wrong, please try again");
				 listRcptDet.add(recDet); 
			}
        }catch(Exception ex) {
        	LOGGER.error("Exception occured while fetching payment history details for connection no: " + conNo + " -> " + ex.getMessage());
        	 TbRcptDet recDet = new TbRcptDet(); 
			 recDet.setErrorMessage("Request is incorrect");
			 listRcptDet.add(recDet); 
        }
		return listRcptDet;
	}


    @Override
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@POST
	@ApiOperation(value = "Fetch Bill History Details SKDCL By CsCCn", notes = "Fetch Bill History Details By CsCCn", response = Object.class)
	@Path("/getBillHistoryDetailsSKDCLByCsNo/CsCcn/{CsCcn}/orgId/{orgId}")
	public List<BillDetailsResponse> getBillHistoryDetailsSKDCLByCsNo(@PathParam("CsCcn") String conNo, @PathParam("orgId") Long orgId) {
		LOGGER.info("In method : getBillHistoryDetailsSKDCLByCsNo for ccn " + conNo);
	
		 List<BillDetailsResponse> billMasList = new ArrayList<>(0);
		 try {
			 TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
	             .fetchConnectionDetailsByConnNo(conNo, orgId);
		     if (consumerdto != null && consumerdto.getCsIdn() != 0) {
		     	Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPT);
		    	List<AdjustmentMasterEntity> adjustmentEntryList = adjustmentEntryRepository.
		     			fetchModuleWiseAdjustmentByUniqueIds(deptId, Arrays.asList(String.valueOf(consumerdto.getCsIdn())), orgId);
		
		    	List<TbWtBillMasEntity> billMasEntityList = billMasterJpaRepository.fetchAllBillByCsIdnDesc(consumerdto.getCsIdn(), orgId);
		    	if(CollectionUtils.isNotEmpty(billMasEntityList)) {
		    		int size = billMasEntityList.size() < 3 ? billMasEntityList.size() : 3;
		    		for(int i = 0; i < size; i++) {
				        Double adjustmentAmount = 0d;
			    		TbWtBillMasEntity billMas = billMasEntityList.get(i);
			 			BillDetailsResponse billResponse = new BillDetailsResponse();
			 			Map<String, TbWtBillDetEntity> taxWiseBillDetails = new HashMap<>();
			 			List<BillTaxDetailsResponse> billTaxList = new ArrayList<>(0);
			 			billResponse.setCsIdn(consumerdto.getCsIdn());
			 			billResponse.setBmBilldt(billMas.getBmBilldt());
			 			billResponse.setBmDuedate(billMas.getBmDuedate());
			 			billResponse.setBmFromdt(billMas.getBmFromdt());
			 			billResponse.setBmIdno(billMas.getBmIdno());
			 			billResponse.setBmPaidFlag(billMas.getGenFlag());
			 			billResponse.setBmPrintdate(billMas.getBmPrintdate());
			 			billResponse.setBmTodt(billMas.getBmTodt());
			 			billResponse.setBmTotalAmount(billMas.getBmTotalAmount());
			 			billResponse.setBmTotalArrears(billMas.getBmTotalArrears());
			 			billResponse.setBmTotalBalAmount(billMas.getBmTotalBalAmount());
			 			billResponse.setBmYear(billMas.getBmYear());
			 			billResponse.setBmPaidFlag(billMas.getBmPaidFlag()==null ? MainetConstants.Y_FLAG : billMas.getBmPaidFlag());
			        	
						WaterPenaltyEntity penalty = waterPenaltyRepository.getWaterPenaltyByBmIdNo(String.valueOf(consumerdto.getCsIdn()),
			    			billMas.getBmIdno(), orgId);
			        	if(penalty!=null) {
			     			billResponse.setActualPenalty(penalty.getActualAmount());
			     			billResponse.setPendingPenalty(penalty.getPendingAmount());
			        	}
			        	
			        	for(TbWtBillDetEntity detEntity: billMas.getBillDetEntity()) {
			
							TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(detEntity.getTaxId(), orgId);
							if(taxMas!=null) {
								if(!taxWiseBillDetails.containsKey(taxMas.getTaxDesc())){
									taxWiseBillDetails.put(taxMas.getTaxDesc(), detEntity);
									BillTaxDetailsResponse taxResponse = new BillTaxDetailsResponse();
					        		taxResponse.setBdBilldetid(detEntity.getBdBilldetid());
					        		taxResponse.setBdCurBalTaxamt(detEntity.getBdCurBalTaxamt());
					        		taxResponse.setBdCurTaxamt(detEntity.getBdCurTaxamt());
					        		taxResponse.setBdPrvBalArramt(detEntity.getBdPrvBalArramt());
					        		taxResponse.setTaxName(taxMas.getTaxDesc());
					        		taxResponse.setTaxId(detEntity.getTaxId());
					        		billTaxList.add(taxResponse);
								}else {
									TbWtBillDetEntity existingDetEntity = taxWiseBillDetails.get(taxMas.getTaxDesc());
									existingDetEntity.setBdCurTaxamt(existingDetEntity.getBdCurTaxamt() + detEntity.getBdCurTaxamt());
									existingDetEntity.setBdCurBalTaxamt(existingDetEntity.getBdCurBalTaxamt() + detEntity.getBdCurBalTaxamt());
									existingDetEntity.setBdPrvBalArramt(existingDetEntity.getBdPrvBalArramt() + detEntity.getBdPrvBalArramt());
									taxWiseBillDetails.put(taxMas.getTaxDesc(), existingDetEntity);
									Optional<BillTaxDetailsResponse> existingTax = billTaxList.stream().filter(bill->bill.getTaxName().equals(taxMas.getTaxDesc())).findFirst();
									if(existingTax!=null && existingTax.isPresent()) {
										existingTax.get().setBdBilldetid(existingDetEntity.getBdBilldetid());
										existingTax.get().setBdCurBalTaxamt(existingDetEntity.getBdCurBalTaxamt());
										existingTax.get().setBdCurTaxamt(existingDetEntity.getBdCurTaxamt());
										existingTax.get().setBdPrvBalArramt(existingDetEntity.getBdPrvBalArramt());
										existingTax.get().setTaxId(existingDetEntity.getTaxId());
										existingTax.get().setTaxName(taxMas.getTaxDesc());
									}
								}
							}
			             for(AdjustmentMasterEntity adjEntry: adjustmentEntryList) {
			         			List<AdjustmentBillDetailMappingEntity> adjustmentBillDetails = adjustmentBillDetailMappingRepository.
			         					getAdjustmentBillDetailsByBillDetId(detEntity.getBdBilldetid(), orgId);
			
				     			if(CollectionUtils.isNotEmpty(adjustmentBillDetails)) {
				     				List<AdjustmentBillDetailMappingEntity> adjustedBillsDetails = adjustmentBillDetails.stream().filter(
			     						adjDet -> adjDet.getAdjbmId().equals(detEntity.getBdBilldetid())).collect(Collectors.toList());
				     				
				     				for(AdjustmentBillDetailMappingEntity adjBillDet: adjustedBillsDetails) {
				     					for(AdjustmentDetailEntity adjDet : adjEntry.getAdjDetail()) {
				     						if(adjDet.getAdjDetId()==adjBillDet.getAdjId() &&  adjDet.getAdjId().getAdjId()==adjEntry.getAdjId()){
				     							if(MainetConstants.FlagP.equals(adjEntry.getAdjType())){
				        	     					adjustmentAmount = adjustmentAmount + adjDet.getAdjAdjustedAmount();
				        	     				}
				        	     				if(MainetConstants.FlagN.equals(adjEntry.getAdjType())) {
				        	     					adjustmentAmount = adjustmentAmount- adjDet.getAdjAdjustedAmount();
				        	     				}
				     						}
				     					}
				     				}
				     			}
			             	}
			    		}
			    		billResponse.setBillTaxDetails(billTaxList);
			    		Double totalPayableAmount = penalty!=null ? (penalty.getPendingAmount() + billResponse.getBmTotalBalAmount() + 
			    				billResponse.getBmTotalArrears() ) :  billResponse.getBmTotalBalAmount() + billResponse.getBmTotalArrears();
			    		billResponse.setTotalBillPaymentAmout(totalPayableAmount);
			    		billResponse.setMeterCost(0d);
			    		 List<TbMeterMasEntity> meterMasEntities = meterDetailEntryJpaRepository.getMeterMasEntities(consumerdto.getCsIdn(), orgId);
			             if(CollectionUtils.isNotEmpty(meterMasEntities)) {
			            	 Optional<TbMeterMasEntity> findFirst = meterMasEntities.stream().filter(
			        			 meter->meter.getMmStatus()!=null && meter.getMmStatus().equals(MainetConstants.Y_FLAG)).findFirst();
			            	 if(findFirst!=null && findFirst.isPresent() && findFirst.get().getMmMtrcost()!=null) {
			            		 billResponse.setMeterCost(findFirst.get().getMmMtrcost().doubleValue());
			            	 }
			             }
			             
			    		billResponse.setAdjustmentAmount(adjustmentAmount);
			    		billMasList.add(billResponse);
			 		
			    	}
		    	}
		    	
		     }else {
		    	 BillDetailsResponse billResponse = new BillDetailsResponse();
				 billResponse.setErrorMessage("Connection no. is not valid");
				 billMasList.add(billResponse); 
		     }
		 }catch(Exception ex) {
			 BillDetailsResponse billResponse = new BillDetailsResponse();
			 billResponse.setErrorMessage("Something has gone wrong, please try again");
			 billMasList.add(billResponse); 
			 LOGGER.error("Exception occured while fetching csmr info entity for connection no. " + conNo + " and orgId " + orgId 
				 + " " + ex.getMessage());
		 }
		return billMasList;
	}
}
