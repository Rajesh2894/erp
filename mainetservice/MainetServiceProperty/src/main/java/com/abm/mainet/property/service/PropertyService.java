package com.abm.mainet.property.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.google.common.util.concurrent.AtomicDouble;

public interface PropertyService {

    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    void setWardZoneDetailByLocId(ProvisionalAssesmentMstDto dto, Long deptId, Long locId);

    Map<String, List<BillDisplayDto>> getTaxMapForDisplayCategoryWise(List<BillDisplayDto> list,
            Organisation organisation, Long deptId);

    List<BillDisplayDto> getTaxListForDisplay(TbBillMas billMas, Organisation organisation, Long deptId);

    boolean isInterestRecalculationRequire(Organisation org, Long deptId, TbBillMas billMas, Date manualDate);

    /*
     * boolean saveAdvancePayment(Long orgId, Double amount, Long csIdn, Long userId, Long receiptId); List<BillReceiptPostingDTO>
     * updateBillMasterAmountPaid(Long propertyNo, Double amount, Long orgId, Long userId);
     */

    List<TbBillMas> generateNewBill(Map<Date, List<BillPresentAndCalculationDto>> scheduleWiseMap, Organisation org, Long deptId,
            String propNo,String flatNo);

    void interestCalculation(Organisation org, Long deptId, List<TbBillMas> billMasDtoList, String intRecaculateFlag);

    void setUnitDetailsForNextYears(ProvisionalAssesmentMstDto assMst, List<Long> finYearList,
            List<FinancialYear> financialYearList) throws IllegalAccessException, InvocationTargetException;

    void taxCarryForward(List<TbBillMas> billMasDtoList, Long orgId);

    void calculatePenaltyTax(Organisation organisation, Long deptId, TbBillMas authBill, double diffAmount,
            AtomicDouble prvBalance, AtomicDouble prvtotal, AtomicDouble totalDemandWithoutRebate);

    List<BillDisplayDto> getTaxListForBillPayDisplay(TbBillMas billMas, Organisation organisation, Long deptId);

    List<Long> propertyBillGeneration(List<NoticeGenSearchDto> list, long orgId, Long empId, String ipAddress, Long logLocId,int langId, Long parentBmNumber);

    void interestCalculationWithoutBRMSCall(Organisation org, Long deptId, List<TbBillMas> billMasDtoList,
            String intRecaculateFlag);

    double getTotalActualAmount(Map<String, List<BillDisplayDto>> presentMap, Long orgId);

    double getTotalPaybaleAmountForBillPay(List<TbBillMas> billMasList, BillDisplayDto rebateDto);

    //method signature modified by @Sadik.shaikh to generate  custom sequence number
    String getPropertyNo(Long orgId,ProvisionalAssesmentMstDto mastDto);

    void updateArrearInCurrentBills(List<TbBillMas> billMasList);

    void updateArrearInCurrentBills(List<TbBillMas> billMasList, TbBillMas lastBillMas);

    List<TbBillMas> getBillListWithMergingOfOldAndNewBill(List<TbBillMas> oldbillMasList, List<TbBillMas> newBillMasList);

    BillDisplayDto calculateSurcharge(Organisation organisation, Long deptId, List<TbBillMas> billMasList, String propNo,
            String taxSubcat, Long finYearId,Date manualReceiptDate);

    double getTotalActualAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate,
            BillDisplayDto surCharge);

    double getTotalPaybaleAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList, BillDisplayDto subCharge);

    double getTotalActualAmount(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate,
            BillDisplayDto surCharge, BillDisplayDto advanceAmt);

    Map<String, Long> getApplicationNumberByRefNo(String propNo, Long serviceId, Long orgId, Long empId);

    BillDisplayDto calculateSurchargeAtAuthorizationEdit(Organisation organisation, Long deptId, List<TbBillMas> billMasList,
            String propNo, String taxSubcat, Long finYearId);

    BillDisplayDto getSurChargetoView(Organisation organisation, Long deptId, String propNo, String taxSubcat, Long finYearId,
            Long applicationNo, BigDecimal billPaidAmt);

    boolean isValidBillNoForObjection(String refno, String billNo, Date billDueDate, Long serviceId, Long orgId);

    List<Long> generateProvisionalBillForReport(PropertyReportRequestDto propertyDto, Long orgId, Long empId, String ipAddress);

    void generateProvisionalBillForReportBySchedular(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	public double knockOffPreviousPaidBillTaxWise(List<TbServiceReceiptMasEntity> feedetailDtoList,
			List<TbBillMas> billMasList, Organisation org , double editableLastAmountPaid);

	TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean recMode);

	String createApplicationNumberForSKDCL(ApplicantDetailDTO applicationDto);
	
	void saveExceptionDetails(Long orgId, Long empId,String ipAddress,String exceptionReason,String billType,String propNo);
	
	List<Long> generateProvisionalBillForReportForMissingPropNos(Organisation org, Long empId, String ipAddress,List<String> propNoList, Long loggedLocId);
	
	void setAdnavcePayDetail(List<BillReceiptPostingDTO> result, final Organisation org, final double amount,
            final Long advanceTaxId);
	Map<String, Object> checkHalfPaymentRebateAppl(List<TbBillMas> billMasList, Organisation org, Long deptId,
			Date manualDate, String payableAmountMethod,double paidAmount,double receiptAmount);

	List<String> getFlatListByRefNo(String refNo, Long orgId);
	
	boolean checkBifurcationMethodApplicable(Organisation organisation);

	List<String> getOwnerInfoByApplId(Long applicationId, Long orgId);
	
	
	List<BillDisplayDto> getTaxListForDisplayForRevision(TbBillMas billMas, Organisation organisation, Long deptId, String interstWaiveOff);
	
	double getTotalActualAmountForRevision(List<TbBillMas> billMasList, List<BillDisplayDto> rebateDtoList,
            Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge);
	
	void updateARVRVInBill(ProvisionalAssesmentMstDto dto, List<TbBillMas> billMasList);
	
	boolean checkOldPropNoExist(String oldPropNo, Long orgId);
	
	void updateArrearInCurrentBillsForBillRevise(List<TbBillMas> billMasList, TbBillMas lastBillMas);
	
	void createBillDetWhereFirstBillHaveArrearAmount(List<TbBillMas> billMasList);
	
	void reArrangeTheDataWhereFirstBillHaveArrearAmount(List<TbBillMas> billMasList);
	
	void rvertBill(List<TbServiceReceiptMasBean> feedetailDtoList, List<BillReceiptPostingDTO> billPosDtoList,
            List<TbBillMas> billMasList, Organisation org, Long userId, String ipAddress);
	
	boolean updatingPendingReceiptData(List<TbBillMas> billMasList,long orgId, Long empId, int languageId, final Long deptId,
			Organisation organisation, ProvisionalAssesmentMstDto assMst, List<TbBillMas> billMasArrears,
			List<ProvisionalBillMasEntity> provBillList, boolean previousReceiptNotAdjFlag,String saveFlag);
	
	void createBillDetWhereFirstBillHaveArrearAmountForReceiptReversal(List<TbBillMas> billMasList);
}
