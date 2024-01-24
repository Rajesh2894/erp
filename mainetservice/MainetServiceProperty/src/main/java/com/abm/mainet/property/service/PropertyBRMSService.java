package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.datamodel.PropertyRateMasterModel;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.google.common.util.concurrent.AtomicDouble;

public interface PropertyBRMSService {

    Map<Date, List<BillPresentAndCalculationDto>> fetchCharges(ProvisionalAssesmentMstDto sdto, Long deptId,
            Map<Long, BillDisplayDto> taxWiseRebate, List<Long> finYearList,TbServiceReceiptMasEntity manualReceiptMas,String assType,Date manualDate,String demandGenFlag);

    void fetchInterstRate(List<TbBillMas> billMasList, Organisation org, Long deptId);

    void fetchPenaltyRate(TbBillMas authBill, Organisation org, Long deptId, PropertyRateMasterModel propRateModel,
            double diffAmount, AtomicDouble prvBalance, AtomicDouble prvtotal, AtomicDouble totalDemandWithoutRebate);

    List<BillDisplayDto> fetchEarlyPayRebateRate(List<TbBillMas> billMasList, Organisation org, Long deptId, Date manualDate,String payableAmountMethod);
    /*
     * task: 26817 pass dto here
     * by Sharvan
     */
    List<BillDisplayDto> fetchApplicationOrScurtinyCharges(Organisation org, Long deptId, String serviceCode, String chargeAppAt,
            PropertyTransferMasterDto transferDto,ProvisionalAssesmentMstDto assMstDto);

    List<BillDisplayDto> fetchApplicationForNoDues(Organisation org, Long deptId, String serviceCode,
            String chargeAppAt,Long noOfCopies,Long serviceId);

    BillDisplayDto fetchSurCharge(Organisation org, Long deptId, double arrearAmt, String taxSubCat, List<TbBillMas> billMasList,Long finYearId,Date manualReceiptDate);

    List<BillDisplayDto> fetchAllChargesApplAtReceipt(String propNo, List<TbBillMas> billMasList,Organisation organisation,Long deptId,String noticeGenFlag,Long userId, String demandNoticeGenFlag);

	/**
	 * @param recMode
	 * @param org
	 * @param deptId
	 * @return dishonor charge added in TbSrcptModesDetBean
	 */
	TbSrcptModesDetBean fetchDishonorCharge(TbSrcptModesDetBean recMode, Organisation org, Long deptId);

	List<BillDisplayDto> fetchEarlyPayRebateRateForGoupProperty(List<TbBillMas> billMasList, Organisation org,
			Long deptId, Date manualDate, String payableAmountMethod, double currentBalanceAmount, double currentAmount,
			double arrearBalanceAmount, double arrearAmount);
	
	List<BillDisplayDto> fetchEarlyPayRebateRateForBackPosted(List<TbBillMas> billMasList, Organisation org, Long deptId,
            Date manualDate,String payableAmountMethod);

}
