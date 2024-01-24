
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author dharmendra.chouhan
 *
 */
public interface AccountReceiptEntryJpaRepository extends PagingAndSortingRepository<TbServiceReceiptMasEntity, Long> {

	/**
	 * commented as per instructed by Rajesh Sir need to test by developer on 19 Jan
	 * 2017
	 */
	@Query("select sc.tbBankaccount.baAccountId,sc.tbBankaccount.baAccountNo,sc.tbBankaccount.baAccountName from  AccountHeadSecondaryAccountCodeMasterEntity sc where sc.orgid = :orgId and sc.tbBankaccount IS NOT NULL")
	public List<Object[]> getBankReceiptAcList(@Param("orgId") Long orgId);

	@Query("select distinct rm.rmReceivedfrom from TbServiceReceiptMasEntity rm where rm.orgId =:orgId and rmDate=:newDate")
	List<String> getPayeeNames(@Param("orgId") Long orgId,@Param("newDate")Date newDate);

	@Query(value = "SELECT sum(rd.RF_FEEAMOUNT)\r\n" + "  FROM tb_ac_budgetcode_mas bm,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_ac_function_master fm,\r\n"
			+ "       tb_ac_primaryhead_master pm,\r\n" + "       tb_ac_projectedrevenue pr,\r\n"
			+ "       tb_receipt_mas rm,\r\n" + "       tb_receipt_det rd\r\n"
			+ " WHERE     sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "       AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n"
			+ "       AND sm.SAC_HEAD_ID = rd.SAC_HEAD_ID\r\n" + "       AND rm.RM_RCPTID = rd.RM_RCPTID\r\n"
			+ "       AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" + "       AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n"
			+ "       AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
			+ "       AND rm.RECEIPT_TYPE_FLAG in ('M','R','A','P')\r\n" + "       AND rm.ORGID = rd.ORGID\r\n"
			+ "       AND bm.ORGID = pr.ORGID\r\n" + "       AND sm.ORGID = rd.ORGID\r\n"
			+ "       AND rd.ORGID=pr.ORGID           \r\n" + "       AND rm.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "       AND rm.rm_date between :fromDate and :toDate\r\n"
			+ "       AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" + "       AND pr.FA_YEARID =:faYearid\r\n"
			+ "       AND pr.ORGID =:orgId", nativeQuery = true)
	public BigDecimal getAllCollectedAmount(@Param("faYearid") Long faYearid,
			@Param("prBudgetCodeId") Long prBudgetCodeId, @Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	@Query(value = "SELECT sum(rd.RF_FEEAMOUNT)\r\n" + "  FROM tb_ac_budgetcode_mas bm,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_ac_function_master fm,\r\n"
			+ "       tb_ac_primaryhead_master pm,\r\n" + "       tb_ac_projectedrevenue pr,\r\n"
			+ "       tb_receipt_mas rm,\r\n" + "       tb_receipt_det rd\r\n"
			+ " WHERE     sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "       AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n"
			+ "       AND sm.SAC_HEAD_ID = rd.SAC_HEAD_ID\r\n" + "       AND rm.RM_RCPTID = rd.RM_RCPTID\r\n"
			+ "       AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" + "       AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n"
			+ "       AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
			+ "       AND rm.RECEIPT_TYPE_FLAG in ('M','R','A','P')\r\n" + "       AND rm.ORGID = rd.ORGID\r\n"
			+ "       AND bm.ORGID = pr.ORGID\r\n" + "       AND sm.ORGID = rd.ORGID\r\n"
			+ "       AND rd.ORGID=pr.ORGID           \r\n" + "       AND rm.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "       AND rm.rm_date between :fromDate and :toDate\r\n"
			+ "       AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" + "       AND pr.FA_YEARID =:faYearid\r\n"
			+ "       AND pr.ORGID =:orgId AND rm.DP_DEPTID=:deptId AND rm.DP_DEPTID=pr.DP_DEPTID AND rm.ORGID = pr.ORGID", nativeQuery = true)
	public BigDecimal getAllCollectedAmountByBasedOnDeptId(@Param("faYearid") Long faYearid,
			@Param("prBudgetCodeId") Long prBudgetCodeId, @Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate,@Param("deptId") Long deptId);
	
	@Query(value = "SELECT sum(rd.RF_FEEAMOUNT)\r\n" + "  FROM tb_ac_budgetcode_mas bm,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_ac_function_master fm,\r\n"
			+ "       tb_ac_primaryhead_master pm,\r\n" + "       tb_ac_projectedrevenue pr,\r\n"
			+ "       tb_receipt_mas rm,\r\n" + "       tb_receipt_det rd\r\n"
			+ " WHERE     sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "       AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n"
			+ "       AND sm.SAC_HEAD_ID = rd.SAC_HEAD_ID\r\n" + "       AND rm.RM_RCPTID = rd.RM_RCPTID\r\n"
			+ "       AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" + "       AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n"
			+ "       AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + "AND pr.FIELD_ID=:fieldId \r\n"
			+ "       AND rm.RECEIPT_TYPE_FLAG in ('M','R','A','P')\r\n" + "       AND rm.ORGID = rd.ORGID\r\n"
			+ "       AND bm.ORGID = pr.ORGID\r\n" + "       AND sm.ORGID = rd.ORGID\r\n"
			+ "       AND rd.ORGID=pr.ORGID           \r\n" + "       AND rm.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "       AND rm.rm_date between :fromDate and :toDate\r\n"
			+ "       AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" + "       AND pr.FA_YEARID =:faYearid\r\n"
			+ "       AND pr.ORGID =:orgId AND rm.DP_DEPTID=:deptId AND rm.DP_DEPTID=pr.DP_DEPTID AND rm.FIELD_ID=pr.FIELD_ID AND rm.ORGID = pr.ORGID", nativeQuery = true)
	public BigDecimal getAllCollectedAmountByBasedOnDeptIdFieldId(@Param("faYearid") Long faYearid,
			@Param("prBudgetCodeId") Long prBudgetCodeId, @Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);
	
	
	@Query("SELECT rm " + "FROM TbServiceReceiptMasEntity rm " + "WHERE rm.rmRcptid=:rmRcptid " + "AND rm.orgId=:orgId ")
	TbServiceReceiptMasEntity findAllByReceiptId(@Param("rmRcptid") long rmRcptid, @Param("orgId") long orgId);

	@Query("select distinct E.empId,E.empname,E.empmname,E.emplname " + " from TbServiceReceiptMasEntity M, "
			+ "Employee E where" + " M.orgId=E.organisation.orgid" + " and M.orgId=:orgId and  M.createdBy=E.empId")
	List<Object[]> getRecieptRegisterEmployeeDetails(@Param("orgId") Long orgId);

	@Query("SELECT COUNT(rd.depositeSlipId) FROM TbSrcptFeesDetEntity rd WHERE rd.rmRcptid.rmRcptid=:rmRcptid AND rd.orgId=:orgId")
	Long countDepositSlipAlreadyGenerated(@Param("rmRcptid") Long rmRcptid, @Param("orgId") Long orgId);

	@Query(value = "SELECT r.RM_RCPTID,\r\n" + "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n"
			+ "       r.MANUALRECEIPTNO,\r\n" + "       d.RF_FEEAMOUNT AS Cash_Amount,\r\n"
			+ "       NULL AS Bank_Amount,\r\n" + "       NULL AS Cheque_DD_Amount,\r\n" + "       NULL ChequeNo,\r\n"
			+ "       NULL BankId,\r\n" + "       NULL BankName,\r\n" + "       r.RM_RECEIVEDFROM,\r\n"
			+ "       d.SAC_HEAD_ID,\r\n" + "       sm.AC_HEAD_CODE, r.DP_DEPTID \r\n" + "  FROM tb_receipt_mas r,\r\n"
			+ "       tb_receipt_det d,\r\n" + "       tb_receipt_mode m,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_comparam_mas cm,\r\n"
			+ "       tb_comparam_det cd\r\n" + " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n"
			+ "       AND r.ORGID = d.ORGID\r\n" + "       AND r.RM_RCPTID = m.RM_RCPTID\r\n"
			+ "       AND r.ORGID = m.ORGID\r\n" + "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n"
			+ "       AND d.ORGID = sm.ORGID\r\n" + "       AND r.ORGID = sm.Orgid\r\n"
			+ "       AND m.ORGID = sm.ORGID\r\n" + "       AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "       AND cd.CPD_VALUE = 'C'\r\n" + "       AND cm.CPM_PREFIX = 'PAY'\r\n"
			+ "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n" + "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')" + "       AND r.CREATED_BY = :employeeListid\r\n"
			+ "       AND r.ORGID = :orgid\r\n" + "       AND rm_date BETWEEN :fromDates AND :toDates\r\n" + "UNION\r\n"
			+ "SELECT r.RM_RCPTID,\r\n" + "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n"
			+ "       r.MANUALRECEIPTNO,\r\n" + "       NULL AS Cash_Amount,\r\n"
			+ "       d.RF_FEEAMOUNT AS Bank_Amount,\r\n" + "       NULL AS Cheque_DD_Amount,\r\n"
			+ "       m.RD_CHEQUEDDNO,\r\n" + "       m.BANKID,\r\n" + "       NULL,\r\n"
			+ "       r.RM_RECEIVEDFROM,\r\n" + "       d.SAC_HEAD_ID,\r\n" + "       sm.AC_HEAD_CODE, r.DP_DEPTID\r\n"
			+ "  FROM tb_receipt_mas r,\r\n" + "       tb_receipt_det d,\r\n" + "       tb_receipt_mode m,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_comparam_mas cm,\r\n"
			+ "       tb_comparam_det cd\r\n" + " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n"
			+ "       AND r.ORGID = d.ORGID\r\n" + "       AND r.RM_RCPTID = m.RM_RCPTID\r\n"
			+ "       AND r.ORGID = m.ORGID\r\n" + "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n"
			+ "       AND d.ORGID = sm.ORGID\r\n" + "       AND r.ORGID = sm.Orgid\r\n"
			+ "       AND m.ORGID = sm.ORGID\r\n" + "       AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "       AND cd.CPD_VALUE = 'B'\r\n" + "       AND cm.CPM_PREFIX = 'PAY'\r\n"
			+ "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n" + "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')" + "       AND r.CREATED_BY = :employeeListid\r\n"
			+ "       AND r.ORGID = :orgid\r\n" + "       AND rm_date BETWEEN :fromDates AND :toDates\r\n" + "UNION\r\n"
			+ "SELECT r.RM_RCPTID,\r\n" + "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n"
			+ "       r.MANUALRECEIPTNO,\r\n" + "       NULL AS Cash_Amount,\r\n" + "       NULL AS Bank_Amount,\r\n"
			+ "       d.RF_FEEAMOUNT AS Cheque_DD_Amount,\r\n" + "       m.RD_CHEQUEDDNO,\r\n" + "       m.BANKID,\r\n"
			+ "       bm.BANK,\r\n" + "       r.RM_RECEIVEDFROM,\r\n" + "       d.SAC_HEAD_ID,\r\n"
			+ "       sm.AC_HEAD_CODE, r.DP_DEPTID\r\n" + "  FROM tb_receipt_mas r,\r\n" + "       tb_receipt_det d,\r\n"
			+ "       tb_receipt_mode m,\r\n" + "       tb_bank_master bm,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_comparam_mas cm,\r\n"
			+ "       tb_comparam_det cd\r\n" + " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n"
			+ "       AND r.ORGID = d.ORGID\r\n" + "       AND r.RM_RCPTID = m.RM_RCPTID\r\n"
			+ "       AND r.ORGID = m.ORGID\r\n" + "       AND bm.BANKID = m.BANKID\r\n"
			+ "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + "       AND d.ORGID = sm.ORGID\r\n"
			+ "       AND r.ORGID = sm.Orgid\r\n" + "       AND m.ORGID = sm.ORGID\r\n"
			+ "       AND cm.CPM_ID = cd.CPM_ID\r\n" + "       AND cd.CPD_VALUE NOT IN ('C', 'B')\r\n"
			+ "       AND cm.CPM_PREFIX = 'PAY'\r\n" + "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n"
			+ "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n" + "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
			+ "       AND r.CREATED_BY = :employeeListid\r\n" + "       AND r.ORGID = :orgid\r\n"
			+ "       AND rm_date BETWEEN :fromDates AND :toDates\r\n"
			+ "ORDER BY rm_date, RM_RCPTNO", nativeQuery = true)
	List<Object[]> getRecieptChallanDeatiles(@Param("employeeListid") Long employeeListid,
			@Param("fromDates") Date fromDates, @Param("toDates") Date toDates, @Param("orgid") Long orgid);

	@Query("SELECT tm.rmRcptid.rmRcptid,te.depositeSlipId,tm.rmRcptid.rmRcptno FROM AccountBankDepositeSlipMasterEntity te,TbSrcptModesDetEntity tm,TbSrcptFeesDetEntity td WHERE tm.rdModesid =:receiptModeId and tm.orgId =:orgId and tm.rmRcptid.rmRcptid=td.rmRcptid.rmRcptid and tm.orgId=td.orgId and td.depositeSlipId=te.depositeSlipId and td.orgId=te.orgid")
	public List<Object[]> gettingReceiptMasEntryId(@Param("receiptModeId") Long receiptModeId,
			@Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE TbServiceReceiptMasEntity rm SET rm.receiptTypeFlag =:receiptTypeFlag,rm.receiptDelFlag =:recDelFlag WHERE rm.rmRcptid =:rmRcptid and rm.orgId =:orgId")
	public void updateReceiptEntryFlag(@Param("rmRcptid") Long rmRcptid,
			@Param("receiptTypeFlag") String receiptTypeFlag, @Param("recDelFlag") String recDelFlag,
			@Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE AccountBankDepositeSlipMasterEntity bd SET bd.coTypeFlag =:coTypeFlag WHERE bd.depositeSlipId =:depositeSlipId and bd.orgid =:orgId")
	public void updateDepositSlipEntryFlag(@Param("depositeSlipId") Long depositeSlipId,
			@Param("coTypeFlag") char coTypeFlag, @Param("orgId") Long orgId);
     //D#133658
	@Query("select sm.cpdFeemode from TbSrcptModesDetEntity sm where sm.rmRcptid.rmRcptid=:rmRcptid order by  1 desc")
	public List<Long> findByRmRcptidFromTbSrcptModesDetEntity(@Param("rmRcptid") Long rmRcptid);

	@Query(value = "select sum(a.Cash_Amount), sum(a.Bank_Amount),sum(a.Cheque_DD_Amount),a.SAC_HEAD_ID,a.AC_HEAD_CODE from\r\n"
			+ "(SELECT d.RF_FEEAMOUNT AS Cash_Amount,NULL AS Bank_Amount,NULL AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,\r\n"
			+ "r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_ac_secondaryhead_master sm,\r\n"
			+ "tb_comparam_mas cm,tb_comparam_det cd WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID\r\n"
			+ "AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "AND cd.CPD_VALUE = 'C' AND cm.CPM_PREFIX = 'PAY' AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
			+ "UNION SELECT NULL AS Cash_Amount,d.RF_FEEAMOUNT AS Bank_Amount,\r\n"
			+ "NULL AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date\r\n"
			+ "FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_ac_secondaryhead_master sm,tb_comparam_mas cm,tb_comparam_det cd\r\n"
			+ "WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n"
			+ "AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID AND cd.CPD_VALUE = 'B' AND cm.CPM_PREFIX = 'PAY'\r\n"
			+ "AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL  AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P') "
			+ "UNION SELECT  NULL AS Cash_Amount, NULL AS Bank_Amount,d.RF_FEEAMOUNT AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,\r\n"
			+ "r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_bank_master bm,tb_ac_secondaryhead_master sm,\r\n"
			+ "tb_comparam_mas cm,tb_comparam_det cd WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID\r\n"
			+ "AND bm.BANKID = m.BANKID AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "AND cd.CPD_VALUE NOT IN ('C' , 'B') AND cm.CPM_PREFIX = 'PAY' AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P') ) a where  a.CREATED_BY =:EmployeeListid AND a.ORGID =:orgid \r\n"
			+ "AND a.rm_date between :fromDates and :toDates  group by a.SAC_HEAD_ID,a.AC_HEAD_CODE", nativeQuery = true)
	public List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntity(@Param("fromDates") Date fromDates,
			@Param("toDates") Date toDates, @Param("orgid") Long orgid, @Param("EmployeeListid") Long EmployeeListid);

	@Query("SELECT rm.refId FROM TbServiceReceiptMasEntity rm where rm.rmRcptid=:rmRcptid and rm.dpDeptId=:dpDeptid and rm.orgId=:orgId")
	public Long gettingRecRefIdInPropertyTax(@Param("rmRcptid") Long rmRcptid, @Param("dpDeptid") Long dpDeptid,
			@Param("orgId") Long orgId);

	@Modifying
	@Query(value = "UPDATE TAX_CB.TB_RECEIPTMASTER" + "    SET RECEIPT_DEL_FLAG = 'Y'"
			+ "  WHERE RM_RECEIPTID = :recRefId" + "    AND ORGID = :orgId", nativeQuery = true)
	public void updateRecRefIdInPropertyTax(@Param("recRefId") Long recRefId, @Param("orgId") Long orgId);

	@Query(value = "SELECT r.RM_RCPTID,\r\n" + "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n"
			+ "       r.MANUALRECEIPTNO,\r\n" + "       d.RF_FEEAMOUNT AS Cash_Amount,\r\n"
			+ "       NULL AS Bank_Amount,\r\n" + "       NULL AS Cheque_DD_Amount,\r\n" + "       NULL ChequeNo,\r\n"
			+ "       NULL BankId,\r\n" + "       NULL BankName,\r\n" + "       r.RM_RECEIVEDFROM,\r\n"
			+ "       d.SAC_HEAD_ID,\r\n" + "       sm.AC_HEAD_CODE\r\n" + "  FROM tb_receipt_mas r,\r\n"
			+ "       tb_receipt_det d,\r\n" + "       tb_receipt_mode m,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_comparam_mas cm,\r\n"
			+ "       tb_comparam_det cd\r\n" + " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n"
			+ "       AND r.ORGID = d.ORGID\r\n" + "       AND r.RM_RCPTID = m.RM_RCPTID\r\n"
			+ "       AND r.ORGID = m.ORGID\r\n" + "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n"
			+ "       AND d.ORGID = sm.ORGID\r\n" + "       AND r.ORGID = sm.Orgid\r\n"
			+ "       AND m.ORGID = sm.ORGID\r\n" + "       AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "       AND cd.CPD_VALUE = 'C'\r\n" + "       AND cm.CPM_PREFIX = 'PAY'\r\n"
			+ "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n" + "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n"
			+ "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')" + "       AND r.ORGID = :orgid\r\n"
			+ "       AND rm_date BETWEEN :fromDates AND :toDates\r\n" + "UNION\r\n" + "SELECT r.RM_RCPTID,\r\n"
			+ "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n" + "       r.MANUALRECEIPTNO,\r\n"
			+ "       NULL AS Cash_Amount,\r\n" + "       d.RF_FEEAMOUNT AS Bank_Amount,\r\n"
			+ "       NULL AS Cheque_DD_Amount,\r\n" + "       m.RD_CHEQUEDDNO,\r\n" + "       m.BANKID,\r\n"
			+ "       NULL,\r\n" + "       r.RM_RECEIVEDFROM,\r\n" + "       d.SAC_HEAD_ID,\r\n"
			+ "       sm.AC_HEAD_CODE\r\n" + "  FROM tb_receipt_mas r,\r\n" + "       tb_receipt_det d,\r\n"
			+ "       tb_receipt_mode m,\r\n" + "       tb_ac_secondaryhead_master sm,\r\n"
			+ "       tb_comparam_mas cm,\r\n" + "       tb_comparam_det cd\r\n"
			+ " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n" + "       AND r.ORGID = d.ORGID\r\n"
			+ "       AND r.RM_RCPTID = m.RM_RCPTID\r\n" + "       AND r.ORGID = m.ORGID\r\n"
			+ "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + "       AND d.ORGID = sm.ORGID\r\n"
			+ "       AND r.ORGID = sm.Orgid\r\n" + "       AND m.ORGID = sm.ORGID\r\n"
			+ "       AND cm.CPM_ID = cd.CPM_ID\r\n" + "       AND cd.CPD_VALUE = 'B'\r\n"
			+ "       AND cm.CPM_PREFIX = 'PAY'\r\n" + "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n"
			+ "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n" + "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
			+ "       AND r.ORGID = :orgid\r\n" + "       AND rm_date BETWEEN :fromDates AND :toDates\r\n" + "UNION\r\n"
			+ "SELECT r.RM_RCPTID,\r\n" + "       r.RM_RCPTNO,\r\n" + "       r.rm_date,\r\n"
			+ "       r.MANUALRECEIPTNO,\r\n" + "       NULL AS Cash_Amount,\r\n" + "       NULL AS Bank_Amount,\r\n"
			+ "       d.RF_FEEAMOUNT AS Cheque_DD_Amount,\r\n" + "       m.RD_CHEQUEDDNO,\r\n" + "       m.BANKID,\r\n"
			+ "       bm.BANK,\r\n" + "       r.RM_RECEIVEDFROM,\r\n" + "       d.SAC_HEAD_ID,\r\n"
			+ "       sm.AC_HEAD_CODE\r\n" + "  FROM tb_receipt_mas r,\r\n" + "       tb_receipt_det d,\r\n"
			+ "       tb_receipt_mode m,\r\n" + "       tb_bank_master bm,\r\n"
			+ "       tb_ac_secondaryhead_master sm,\r\n" + "       tb_comparam_mas cm,\r\n"
			+ "       tb_comparam_det cd\r\n" + " WHERE     r.RM_RCPTID = d.RM_RCPTID\r\n"
			+ "       AND r.ORGID = d.ORGID\r\n" + "       AND r.RM_RCPTID = m.RM_RCPTID\r\n"
			+ "       AND r.ORGID = m.ORGID\r\n" + "       AND bm.BANKID = m.BANKID\r\n"
			+ "       AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + "       AND d.ORGID = sm.ORGID\r\n"
			+ "       AND r.ORGID = sm.Orgid\r\n" + "       AND m.ORGID = sm.ORGID\r\n"
			+ "       AND cm.CPM_ID = cd.CPM_ID\r\n" + "       AND cd.CPD_VALUE NOT IN ('C', 'B')\r\n"
			+ "       AND cm.CPM_PREFIX = 'PAY'\r\n" + "       AND cd.CPD_ID = m.CPD_FEEMODE\r\n"
			+ "       AND r.RECEIPT_DEL_FLAG IS NULL\r\n" + "        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
			+ "       AND r.ORGID = :orgid\r\n" + "       AND rm_date BETWEEN :fromDates AND :toDates\r\n"
			+ "ORDER BY rm_date, RM_RCPTNO", nativeQuery = true)
	public List<Object[]> getRecieptChallanDeatilesAll(@Param("fromDates") Date fromDates,
			@Param("toDates") Date toDates, @Param("orgid") Long orgid);

	@Query(value = "select sum(a.Cash_Amount), sum(a.Bank_Amount),sum(a.Cheque_DD_Amount),a.SAC_HEAD_ID,a.AC_HEAD_CODE from\r\n"
			+ "(SELECT d.RF_FEEAMOUNT AS Cash_Amount,NULL AS Bank_Amount,NULL AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,\r\n"
			+ "r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_ac_secondaryhead_master sm,\r\n"
			+ "tb_comparam_mas cm,tb_comparam_det cd WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID\r\n"
			+ "AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "AND cd.CPD_VALUE = 'C' AND cm.CPM_PREFIX = 'PAY' AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
			+ "UNION SELECT NULL AS Cash_Amount,d.RF_FEEAMOUNT AS Bank_Amount,\r\n"
			+ "NULL AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date\r\n"
			+ "FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_ac_secondaryhead_master sm,tb_comparam_mas cm,tb_comparam_det cd\r\n"
			+ "WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n"
			+ "AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID AND cd.CPD_VALUE = 'B' AND cm.CPM_PREFIX = 'PAY'\r\n"
			+ "AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL  AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P') "
			+ "UNION SELECT  NULL AS Cash_Amount, NULL AS Bank_Amount,d.RF_FEEAMOUNT AS Cheque_DD_Amount,d.SAC_HEAD_ID,sm.AC_HEAD_CODE,\r\n"
			+ "r.CREATED_BY,r.CREATED_DATE,r.ORGID,r.rm_Date FROM tb_receipt_mas r,tb_receipt_det d,tb_receipt_mode m,tb_bank_master bm,tb_ac_secondaryhead_master sm,\r\n"
			+ "tb_comparam_mas cm,tb_comparam_det cd WHERE r.RM_RCPTID = d.RM_RCPTID AND r.ORGID = d.ORGID AND r.RM_RCPTID = m.RM_RCPTID AND r.ORGID = m.ORGID\r\n"
			+ "AND bm.BANKID = m.BANKID AND d.SAC_HEAD_ID = sm.SAC_HEAD_ID AND d.ORGID = sm.ORGID AND r.ORGID = sm.Orgid AND m.ORGID = sm.ORGID AND cm.CPM_ID = cd.CPM_ID\r\n"
			+ "AND cd.CPD_VALUE NOT IN ('C' , 'B') AND cm.CPM_PREFIX = 'PAY' AND cd.CPD_ID = m.CPD_FEEMODE  AND r.RECEIPT_DEL_FLAG IS NULL AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P') ) a where  a.ORGID =:orgid \r\n"
			+ "AND a.rm_date between :fromDates and :toDates  group by a.SAC_HEAD_ID,a.AC_HEAD_CODE", nativeQuery = true)
	public List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntityAll(@Param("fromDates") Date fromDates,
			@Param("toDates") Date toDates, @Param("orgid") Long orgid);

	@Query("select sd.sacHeadId, sum(sd.rfFeeamount) from TbSrcptFeesDetEntity sd where sd.rmRcptid.rmRcptid=:rmRcptid and sd.orgId=:orgId group by sd.sacHeadId")
	public List<Object[]> findAllDataByReceiptId(@Param("rmRcptid") long rmRcptid, @Param("orgId") long orgId);

	@Query(value = "select rm.rm_rcptid, rm.rm_rcptno,rm.rm_date,rm.rm_amount,rm.rm_receivedfrom,rm.rm_narration from tb_receipt_mas rm where rm.ref_id=:grantId and rm.receipt_type_flag=:receiptTypeFlag and rm.orgid=:orgId and rm.receipt_del_auth_by is null", nativeQuery = true)
	public List<Object[]> findByOrgIdAndReceiptTypeFlag(@Param("grantId") Long grantId,@Param("receiptTypeFlag") String receiptTypeFlag,@Param("orgId") Long orgId);

	@Query(value = "FROM AccountInvestmentMasterEntity inv where inv.invstId =:receipt_ref_id AND orgId =:orgId")
	public AccountInvestmentMasterEntity findInvestmentbyIdEntity(@Param("receipt_ref_id") Long receipt_ref_id,@Param("orgId") Long orgId);
	
	
	//TB_RECEIPT_MAS
	
	@Query("SELECT SUM(r.rmAmount) from TbServiceReceiptMasEntity r where r.refId=:refId and r.orgId=:orgId and receiptTypeFlag=:receiptFLag and r.receipt_del_auth_by is null")
	public Object getReceiptsAmount(@Param("refId")Long refId, @Param("orgId") Long orgId,@Param("receiptFLag") String receiptFlag );

	@Query("SELECT rm FROM TbServiceReceiptMasEntity rm WHERE rm.rmRcptno=:rmRcptno AND rm.orgId=:orgId AND rm.dpDeptId=:deptId AND rm.rmDate=:rmDate ")
	TbServiceReceiptMasEntity findReceiptData(@Param("rmRcptno") Long rmRcptno, @Param("orgId") Long orgId,@Param("deptId") Long deptId,@Param("rmDate") Date rmDate);
	@Modifying
	@Query(value = "update tb_receipt_det d,TB_RECEIPT_MODE m set RD_CHEQUE_STATUS=:checkStatus   where d.RM_RCPTID=m.RM_RCPTID and d.DPS_SLIPID=:slipId", nativeQuery = true)
	public void updateCheckStatusInReversal(@Param("checkStatus") Long checkStatus, @Param("slipId") Long slipId);

	@Query(value="from TbServiceReceiptMasEntity rm where rm.orgId=:OrgId and rm.dpDeptId=:deptId and rm.rmRcptno=:referenceNo and rm.rmN4=:entryType and rm.rmDate=:rmDate")
	 public TbServiceReceiptMasEntity getReceiptNoByUniquereferenceNumber(@Param("deptId") Long deptId,@Param("OrgId") Long OrgId,@Param("referenceNo") Long referenceNo,@Param("entryType") Long entryType,@Param("rmDate") Date rmDate);
}
