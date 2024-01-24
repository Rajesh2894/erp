package com.abm.mainet.bill.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;

import com.abm.mainet.cfc.challan.domain.AdjustmentBillDetailMappingEntity;
import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;

/**
 * @author Rahul.Yadav
 *
 */
public interface BillMasterCommonService {

    /**
     * Calculate interest on not paid bills at bill generation
     * @param listBill
     */
    void calculateInterestAndArrears(List<TbBillMas> listBill);

    /**
     * Add and update the interest on not paid bills at a time of bill payment
     * @param billMasData
     * @param ccnNumber
     */
    void processBillNewData(List<TbBillMas> billMasData, String ccnNumber);

    /**
     * Temporary updating the bills with paid amount and adding bill details on challan
     * @param listBill
     * @param paidAmount
     * @param details
     * @param billDetails
     * @param org
     * @param rebateDetails
     * @return
     */
    List<BillReceiptPostingDTO> updateBillData(List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details,
            Map<Long, Long> billDetails, Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate);

    /**
     * fetching advance taxId based on department
     * @param orgid
     * @param module
     * @return
     */
    Long getAdvanceTaxId(long orgid, String module, Long deptId);

    /**
     * preparing bill details based on taxes
     * @param orgnisation
     * @param langId
     * @param empId
     * @param penaltytaxDto
     * @param billDet
     * @param ipAddress
     */
    // pls correct english
    void setBillDetails(Organisation orgnisation, int langId, Long empId,
            BillTaxDTO penaltytaxDto, TbBillDet billDet, String ipAddress);

    /**
     * return interest tax detail for adding interest data in bill details
     * @param orgid
     * @param water
     * @return
     */
    // please change department as input parameter
    TbTaxMasEntity getInterestTax(Long orgid, String deptShortName);

    /**
     * Voucher posting at bill generation
     * @param csIdn
     * @param orgId
     * @param deptShortName
     * @param finYearId
     * @param empId
     * @param logLocId
     */
    void doVoucherPosting(List<Long> uniqueNumbers, Organisation orgId, String deptShortName, Long empId, Long logLocId);

    /**
     * balance +ve or -ve adjustment amount is adjusted in current bill.
     * @param listBill
     * @param adjustmentEntity
     * @return
     */
    List<AdjustmentBillDetailMappingEntity> doAdjustmentAgainstBalanceAmount(List<TbBillMas> listBill,
            List<AdjustmentMasterEntity> adjustmentEntity);

    void saveAndUpdateMappingTable(List<TbBillDet> tbWtBillDet,
            List<AdjustmentBillDetailMappingEntity> mappingEntity);

    void accountVoucherPosting(List<Long> uniqueNumber, Organisation orgId, String deptShortName, Long empId, Long logLocId);

    List<TbBillMas> generateBillForDataEntry(List<TbBillMas> billMasList, Organisation org);

    void calculateInterest(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate, Date manualDate);

    void updateArrearInCurrentBills(List<TbBillMas> billMasList);

    void setBillDetailForDataEntry(List<TbBillMas> listBill, Double paidAmount, Map<Long, Double> details,
            Map<Long, Long> billDetails, Organisation org, List<Map<Long, Double>> rebateDetails);

    void updateArrearInCurrentBillsForNewBillGenertaion(List<TbBillMas> billMasList);
    
    public void calculateMultipleInterest(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
            Date manualDate);
    
    
    public double calculatePenaltyInterest(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
            Date manualDate,String billGen,String paymentFlag, Long userId);
    
    List<BillReceiptPostingDTO> updateBifurcationMethodBillData(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate);
    
    boolean checkRebateAppl(List<TbBillMas> billMasList,Organisation org);
    
    String getFullPaymentOrHalfPayRebate(List<TbBillMas> billMasList,Organisation org);
    
    double getHalfPayableOutstanding(List<TbBillMas> billMasList,Organisation org, String paymentDisplayFlag);
    
    double getFullPayableOutstanding(List<TbBillMas> billMasList,Organisation org, String paymentDisplayFlag);
    
    double getBalanceOutstanding(List<TbBillMas> billMasList,Organisation org);
    
    void calculateMultipleInterestForWater(List<TbBillMas> listBill, Organisation org, Long deptId,
			String interestRecalculate, Date manualDate);
    
    List<BillReceiptPostingDTO> updateBillDataForInterest(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate);

	List<BillReceiptPostingDTO> updateSingleBillData(TbBillMas bill, TbBillMas lastBillMas, Double detAmount, Map<Long, Double> details,
			Map<Long, Long> billDetails, Organisation org, List<Map<Long, List<Double>>> rebateDetails,
			Date manualReceptDate, boolean isLastBill);

	List<BillReceiptPostingDTO> updateBillDataForGroupPropNo(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate, List<String> propNoList);
	
	double calculatePenaltyInterestForBillGen(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
			Date manualDate, String billGen,String paymentFlag, Long userId,List<TbBillMas> totalBillList);

	List<AdjustmentBillDetailMappingEntity> doAdjustmentForNewBill(List<TbBillMas> listBill,
			List<AdjustmentMasterEntity> adjustmentEntity);
	
	CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO, Long empId, Long orgId,
			Long deptId, String amount) throws BeansException, IOException, InterruptedException;

	void updateMappingTable(TbBillDet billDet, AdjustmentBillDetailMappingEntity mapping, Long orgId);
	
	void updateArrearInCurrentBillsForNewBillGenertaionForChangInAss(List<TbBillMas> billMasList);
	
	List<BillReceiptPostingDTO> updateBillDataWithoutInterest(final List<TbBillMas> listBill,
            Double paidAmount, Map<Long, Double> details, Map<Long, Long> billDetails,
            final Organisation org, List<Map<Long, List<Double>>> rebateDetails, Date manualReceptDate);
	
	void taxCarryForward(List<TbBillMas> billMasDtoList, Long orgId);
	
	void calculateInterestForPrayagRaj(List<TbBillMas> listBill, Organisation org, Long deptId, String interestRecalculate,
			Date manualDate, Long userId,List<TbBillMas> billMasArrears,String billGenFlag);
}
