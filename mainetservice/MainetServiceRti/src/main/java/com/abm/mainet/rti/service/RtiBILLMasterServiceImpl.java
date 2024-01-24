package com.abm.mainet.rti.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillGenerationService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;

@Service(value = "RTIBillService")
@Repository
public class RtiBILLMasterServiceImpl
        implements IRtiBILLMasterService, BillPaymentService, BillGenerationService, BillDetailsService {

  

	@Override
	public CommonChallanDTO getBillDetails(CommonChallanDTO commonchlnDto) {
		
		return commonchlnDto;
	}

	@Override
	public boolean revertBills(TbServiceReceiptMasBean feedetailDto, Long userId, String ipAdress) {
		
		return false;
	}

	@Override
	public List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> uniquePrimaryKey, Long orgid) {
		
		return null;
	}

	@Override
	public boolean updateAccountPostingFlag(List<Long> bmIdNo, String flag) {
		
		return false;
	}

	@Override
	public List<TbBillMas> fetchCurrentBill(String uniqueSearchNumber, Long orgId) {
		
		return null;
	}

	@Override
	public List<TbBillMas> updateAdjustedCurrentBill(List<TbBillMas> bill) {
		
		return null;
	}

	@Override
	public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto) {
		
		return null;
	}

	@Override
	public boolean revertBills(TbSrcptModesDetBean feedetailDto, Long userId, String ipAdress) {
		
		return false;
	}

	@Override
	public List<BillReceiptPostingDTO> updateBillMasterAmountPaid(String uniqueId, Double amount, Long orgid,
			Long userId, String ipAddress, Date manualReceptDate, String flatNo) {
		
		return null;
	}

	@Override
	public boolean saveAdvancePayment(Long orgId, Double amount, String uniqueId, Long userId, Long receiptId) {
		
		return false;
	}

	@Override
	public String getApplicantUserNameModuleWise(long orgId, String uniqueKey) {
		
		return null;
	}

	@Override
	public VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress) {
		
		return null;
	}

	@Override
	public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation) {
		
		return null;
	}

	@Override
	public List<TbBillMas> fetchBillsListByBmId(List<Long> uniquePrimaryKey, Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
