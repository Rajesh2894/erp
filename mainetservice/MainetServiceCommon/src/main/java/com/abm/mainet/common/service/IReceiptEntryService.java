package com.abm.mainet.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;

public interface IReceiptEntryService {

    TbServiceReceiptMasEntity insertInReceiptMaster(CommonChallanDTO requestDTO, List<BillReceiptPostingDTO> billTaxes);

    TbServiceReceiptMasEntity findByRmRcptidAndOrgId(Long refId, long orgid);

    TbServiceReceiptMasEntity getLatestReceiptDetailByAddRefNo(Long orgId, String additionalRefNo);

    BigDecimal getPaidAmountByAdditionalRefNo(Long orgId, String additionalRefNo, Long deptId);

    BigDecimal getPaidAmountByAppNo(Long orgId, Long applicationNo, Long deptId);

    List<TbServiceReceiptMasEntity> getRebateByAppNo(Long orgId, Long applicationNo, Long deptId);

    Long saveDemandLevelRebateAndAccountPosting(CommonChallanDTO requestDTO, Organisation org,
            List<BillReceiptPostingDTO> billRebateDto);

    List<TbServiceReceiptMasEntity> getCollectionDetails(String propNo, Long deptId, long orgid);

    void inActiveAllRebetReceiptByAppNo(Long orgId, Long applicationNo, Long deptId, String deleteRemark, Long empId);

    public void doAccountVoucherPosting(final String narration,
            final List<VoucherPostDTO> accountPostingList, final VoucherPostDTO accountPosting, String activeFlag);

    /**
     * find All data
     * @param orgId
     * @param rmAmount
     * @param rmRcptno
     * @param rmReceivedfrom
     * @param rmDate
     * @param deptId
     * @return
     */
    List<TbServiceReceiptMasBean> findAll(Long orgId, BigDecimal rmAmount, Long rmRcptno, String rmReceivedfrom, Date rmDate,
            Long deptId);

    List<String> getPayeeNames(Long orgId, Long deptId);

    /**
     * get all Receipt By orgId And Department Id
     * @param deptId
     * @param orgId
     * @return
     */
    List<TbServiceReceiptMasBean> getAllReceiptsByOrgId(Long deptId, Long orgId);

    /**
     * To get Receipt By Receipt Id
     * @param rmRcptid
     * @param orgId
     * @return
     */
    TbServiceReceiptMasBean findReceiptById(Long rmRcptid, Long orgId);

    TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndOrgId(long rcptNo, Long orgId);

    int getCountOfReceiptByRefNo(String additionalRefNo, Long orgId);

    TbServiceReceiptMasEntity getReceiptNoByLoiNoAndOrgId(String rmLoiNo, Long orgId);

    BigDecimal getAjustedAdvancedAmountByAppNo(Long orgId, Long applicationNo, Long deptId);

    VoucherPostDTO getAccountPostingDtoForBillReversal(List<BillReceiptPostingDTO> billTaxes, Organisation org);

    int getcountOfTaxExistAgainstAppId(Long orgId, Long applicationNo, Long deptId, Long taxId);
    
    TbServiceReceiptMasEntity getReceiptDetailsByAppId(Long applicationNo,Long orgId);

    Long getTotalReceiptAmountByRefIdAndReceiptType(Long refId, String receiptType, Long orgId);
    
    BigDecimal getActicePaidAmountByAppNo(Long orgId, Long applicationNo, Long deptId);
    
    void inActiveAllDemadReceiptByAppNo(Long orgId, Long applicationNo, Long deptId, String deleteRemark, Long empId);

	public List<TbServiceReceiptMasBean> findReceiptByReceiptDateType(Long refId, Long orgId,
			Date receiptDate, Long deptId, String receiptType);
	
	public List<TbServiceReceiptMasBean> findReceiptByReceiptDateAddRefNo(String additionalRefNo, Long orgId,
			Date receiptDate, Long deptId);
	
	void inActiveAllRebetReceiptByAdditionalRefNo(Long orgId, String applicationNo, Long deptId, String deleteRemark, Long empId);
	
	 BigDecimal getPaidAmountByAdditionalRefNoIncRebate(Long orgId, String additionalRefNo, Long deptId);
	 
	 TbServiceReceiptMasEntity getLatestReceiptDetailByAddRefNoAndFlatNo(Long orgId, String additionalRefNo,String flatNo);
	 
	 TbServiceReceiptMasEntity getReceiptDetByAppIdAndServiceId(Long applicationId, Long smServiceId, Long orgId);
	 
	 BigDecimal getReceiptAmountPaidByPropNoOrFlatNo(String propNo,String flatNo,Organisation org, Long deptId);
	 
	 Long getDuplicateChequeNoCount(Long bankId, Long chequeNo);
	 
	 void inActiveReceiptByReceiptList(Long orgId, List<TbServiceReceiptMasEntity> rebateReceiptList,
				String deleteRemark, Long empId);

	List<TbServiceReceiptMasBean> findReceiptDet(TbServiceReceiptMasBean receiptMasBean);

	ChallanReceiptPrintDTO setValuesAndPrintReport(Long rmRcptid, Long orgId, int langId);

	List<TbServiceReceiptMasEntity> getCollectionDetailsInactive(String additionalRefNo, Long deptId, long orgid);
   
    //Defect #145245
	TbServiceReceiptMasEntity getReceiptDetailByIds(Long rcptNo, Long orgId, Long deptId,String loiNo,Date rmDate, String rmAmount, Long appNo, Long refNo,String rmReceivedfrom, String formAndToDt);

	String getCustomReceiptNo(Long deptId, Long rmRcptno);

	List<TbServiceReceiptMasEntity> getCollectionDetailsWithFlatNo(String additionalRefNo, String flatNo, Long deptId,
			Long orgid);
	
	TbServiceReceiptMasEntity getMaxReceiptIdByAdditinalRefNo(String additionalRefNo);
	
	List<TbServiceReceiptMasEntity> getPaymentHistoryByAdditinalRefNo(String additionalRefNo , Long orgId);

	TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndDeptId(Long rcptNo, Long orgId, Long deptId);
	
	TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndDeptIdAndRmDate(Long rcptNo, Long orgId, Long deptId ,Date rmDate);

	TbServiceReceiptMasEntity getReceiptDetailByCsCcnRcptNoAndOrgId(String conNo, Long rcptNo, Long orgId);

	TbServiceReceiptMasEntity getMaxReceiptIdByAdditinalRefNoAndDeptId(String raidNo, Long deptId);

	TbServiceReceiptMasEntity saveRebateReceipt(final CommonChallanDTO requestDTO,
            final TbServiceReceiptMasEntity receiptMasEntity,
            Organisation org,
            Map<Long, Long> taxHeadId, BillReceiptPostingDTO billDef, Long currDemandId,double totalEarlyDiscAmnt);

	List<TbServiceReceiptMasBean> getAllReceiptsByOrgIdAndPropNo(String additionalRefNo, Long deptId, long orgid);

	Long countnoOfPropertyPaidToday(Long orgid, Long deptId, String dateSet);

	List<Object[]> getTaxWiseAmountCollectnProp(Long orgid, Long deptId, String taxDesc, String dateSet);

	String getTSCLCustomReceiptNo(Long fieldId, Long serviceId, Long rmRcptno, Date rmDate, Long orgId);

	boolean findDuplicateManualReceiptExist(String manualReceiptNo, Long smServiceId, Long dpDeptid, Long orgId);

}
