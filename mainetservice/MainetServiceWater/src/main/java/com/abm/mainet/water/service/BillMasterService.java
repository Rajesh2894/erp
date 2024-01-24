package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.BillDetailsResponse;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.TbRcptDet;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtExcessAmt;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.rest.dto.KDMCWaterDetailsRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillResponseDTO;

/**
 * @author Rahul.Yadav
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface BillMasterService {
    /**
     * method is used to generate bill based on tax calculation map
     * @param taxCalMap
     * @param readingMap
     * @param waterDTO
     * @param interestValue
     * @param remarks
     * @param orgnisation
     * @param langId
     * @param empId
     * @param excessAmount
     * @param penaltytaxDto
     * @param dueDate
     * @param notPaidBills
     * @param adjustmentEntry
     * @param finYearId
     * @param ipAddress
     * @param billNoList
     */
    List<TbWtBillMasEntity> billGeneartion(List<BillTaxDTO> taxList, BillTaxDTO readingMap, TbCsmrInfoDTO waterDTO,
            double interestValue, String remarks, Organisation orgnisation, int langId, Long empId,
            TbWtExcessAmt excessAmount, BillTaxDTO interestTaxDto, Date dueDate, List<TbBillMas> notPaidBills,
            List<AdjustmentMasterEntity> adjustmentEntry, Long finYearId, String ipAddress);

    /**
     * Create bills in table
     * @param tbWtBillMas
     * @param adjustedTaxData
     * @param ajustedAmount
     * @return
     */
    TbWtBillMasEntity create(TbBillMas tbWtBillMas);

    Map<Long, List<TbBillMas>> fetchAllUnpaidBillsForBilling(List<Long> csIdn, long orgid);

    /**
     * Fetching all not paid bills while challan update for interest calculation
     * @param csIdn
     * @param orgid
     * @return
     */
    // change to unique Identifier
    List<TbBillMas> getBillMasterListByUniqueIdentifier(long uniqueKey, long orgid);

    Map<Long, Boolean> checkBillsForNonMeter(List<Long> csIdn, long orgid);

    WaterBillResponseDTO fetchBillPaymentData(WaterBillRequestDTO requestDTO);

	public List<TbWtBillMasEntity> saveAndUpdateMainBill(List<TbBillMas> billMasList, Long orgId, Long empId,
			String macAddress);
	
	List<TbBillMas> getBillMasListByCsidn(Long csIdn, Long orgId);
	
	double getTotalOutstandingOfConnNosAssocWithPropNo(List<Long> csIdnList);
	
	 TbBillMas getOuststandingByCsNo(String conNo, Long orgId);
	 
	 List<TbBillMas> fetchAllBillByCsIdn(Long csIdno, long orgId);
	 
	 List<TbBillMas> getBilltHistoryByCsNo(String conNo, Long orgId);
	 
	 List<TbRcptDet> getPaymentHistoryByCsCcn(String conNo, Long orgId);
	 
	 Double getAdjustmentAmountForWaterSkdcl(Long deptId, Long csIdn, Long orgid, List<TbBillMas> billMasList);

	List<TbRcptDet> getPaymentHistorySKDCLByCsCcn(String conNo, Long rcptNo, Long orgId);

	List<BillDetailsResponse> getBillHistoryDetailsSKDCLByCsNo(String conNo, Long orgId);

}
