package com.abm.mainet.common.dashboard.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dashboard.domain.skdcl.AccountAmountTotalEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountClassifDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountCollectionEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountFundStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountRatioDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountReceiptsAndPaymentsEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransactionCntEntity;

@Repository
public class AccountDashboardGraphDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	public List<AccountTransactionCntEntity> getYearWiseOnlineTransactionCount() {
		List<AccountTransactionCntEntity> onlineTransCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select year(a.PAYMENT_DATE) as FA_YEAR,count(1) as CNT_OR_SUM from (select cpd_id_payment_mode,PAYMENT_DATE from tb_ac_payment_mas union all\r\n"
				+ " select CPD_FEEMODE,RM_DATE from tb_receipt_mas a,tb_receipt_mode b where a.RM_RCPTID=b.RM_RCPTID) a ,(select cpd_id,a.cpd_value from tb_comparam_det a,tb_comparam_mas b \r\n"
				+ " where a.cpd_value in ('W','RT','N','B') and a.CPM_ID = b.CPM_ID and CPM_PREFIX='PAY') b ,(sELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE \r\n"
				+ " AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) c where a.cpd_id_payment_mode=b.cpd_id and a.PAYMENT_DATE between c.FA_FROMDATE and c.FA_TODATE\r\n"
				+ " group by year(a.PAYMENT_DATE)\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		onlineTransCntList = (List<AccountTransactionCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountTransactionCntEntity.class).getResultList();

		return onlineTransCntList;
	}

	public List<AccountTransactionCntEntity> getYearWiseCashTransactionCount() {
		List<AccountTransactionCntEntity> onlineTransCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select year(a.PAYMENT_DATE) as FA_YEAR,count(1) as CNT_OR_SUM from (select cpd_id_payment_mode,PAYMENT_DATE from tb_ac_payment_mas union all\r\n"
				+ " select CPD_FEEMODE,RM_DATE from tb_receipt_mas a,tb_receipt_mode b where a.RM_RCPTID=b.RM_RCPTID) a ,(select cpd_id,a.cpd_value from tb_comparam_det a,tb_comparam_mas b \r\n"
				+ " where a.cpd_value in ('C') and a.CPM_ID = b.CPM_ID and CPM_PREFIX='PAY') b ,(sELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE \r\n"
				+ " AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) c where a.cpd_id_payment_mode=b.cpd_id and a.PAYMENT_DATE between c.FA_FROMDATE and c.FA_TODATE\r\n"
				+ " group by year(a.PAYMENT_DATE)\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		onlineTransCntList = (List<AccountTransactionCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountTransactionCntEntity.class).getResultList();

		return onlineTransCntList;
	}

	public List<AccountAmountTotalEntity> getYearWiseEstimatedBudget() {
		List<AccountAmountTotalEntity> onlineTransCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select year(a.RM_DATE) as FA_YEAR,sum(RM_AMOUNT) as CNT_OR_SUM from tb_receipt_mas a,(sELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE \r\n"
				+ " AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) b where a.RM_DATE between b.FA_FROMDATE and b.FA_TODATE\r\n"
				+ " group by year(a.RM_DATE)\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		onlineTransCntList = (List<AccountAmountTotalEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountAmountTotalEntity.class).getResultList();

		return onlineTransCntList;
	}

	public List<AccountAmountTotalEntity> getYearWiseExpenditureAmt() {
		List<AccountAmountTotalEntity> onlineTransCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select year(a.PAYMENT_DATE) as FA_YEAR,sum(PAYMENT_AMOUNT) as CNT_OR_SUM from tb_ac_payment_mas a,(sELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE \r\n"
				+ " AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) b where a.PAYMENT_DATE between b.FA_FROMDATE and b.FA_TODATE\r\n"
				+ " group by year(a.PAYMENT_DATE)\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		onlineTransCntList = (List<AccountAmountTotalEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountAmountTotalEntity.class).getResultList();

		return onlineTransCntList;
	}

	public List<AccountCollectionEntity> getDeptWiseCollectionForModes() {
		List<AccountCollectionEntity> collectionList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + "SELECT DP_DEPTDESC,\r\n"
				+ "	CASH_MODE,\r\n" + "	CHEQUE_MODE,\r\n" + "	DEMAND_DRAFT_MODE,\r\n" + "	BANK_MODE,\r\n"
				+ "	TRANSFER_MODE,\r\n" + "	PAY_ORDER_MODE,\r\n" + "	ADJUSTMENT_MODE,\r\n" + "	REBATE_MODE,\r\n"
				+ "	USER_ADJUSTMENT_MODE,\r\n" + "	USER_DISCOUNT_MODE,\r\n" + "	WEB_MODE,\r\n" + "	RTGS_MODE,\r\n"
				+ "	NEFT_MODE,\r\n" + "	FDR_MODE,\r\n" + "	POS_MODE,\r\n" + "	PETTY_CASH_MODE,\r\n"
				+ "	ALL_MODE,\r\n" + "	MONEY_RECEIPT_MODE\r\n" + "	FROM\r\n" + "	(\r\n" + "	SELECT DP_DEPTDESC,\r\n"
				+ "	IFNULL(SUM(CASH_MODE),0) AS CASH_MODE,\r\n" + "	IFNULL(SUM(CHEQUE_MODE),0) AS CHEQUE_MODE,\r\n"
				+ "	IFNULL(SUM(DEMAND_DRAFT_MODE),0) AS DEMAND_DRAFT_MODE,\r\n"
				+ "	IFNULL(SUM(BANK_MODE),0) AS BANK_MODE,\r\n" + "	IFNULL(SUM(TRANSFER_MODE),0) AS TRANSFER_MODE,\r\n"
				+ "	IFNULL(SUM(PAY_ORDER_MODE),0) AS PAY_ORDER_MODE,\r\n"
				+ "	IFNULL(SUM(ADJUSTMENT_MODE),0) AS ADJUSTMENT_MODE,\r\n"
				+ "	IFNULL(SUM(REBATE_MODE),0) AS REBATE_MODE,\r\n"
				+ "	IFNULL(SUM(USER_ADJUSTMENT_MODE),0) AS USER_ADJUSTMENT_MODE,\r\n"
				+ "	IFNULL(SUM(USER_DISCOUNT_MODE),0) AS USER_DISCOUNT_MODE,\r\n"
				+ "	IFNULL(SUM(WEB_MODE),0) AS WEB_MODE,\r\n" + "	IFNULL(SUM(RTGS_MODE),0) AS RTGS_MODE,\r\n"
				+ "	IFNULL(SUM(NEFT_MODE),0) AS NEFT_MODE,\r\n" + "	IFNULL(SUM(FDR_MODE),0) AS FDR_MODE,\r\n"
				+ "	IFNULL(SUM(POS_MODE),0) AS POS_MODE,\r\n"
				+ "	IFNULL(SUM(PETTY_CASH_MODE),0) AS PETTY_CASH_MODE,\r\n"
				+ "	IFNULL(SUM(ALL_MODE),0) AS ALL_MODE,\r\n"
				+ "	IFNULL(SUM(MONEY_RECEIPT_MODE),0) AS MONEY_RECEIPT_MODE\r\n" + "	FROM\r\n" + "	(\r\n" + "\r\n"
				+ "	SELECT DEPT.DP_DEPTDESC AS DP_DEPTDESC,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'C' THEN RM.RM_AMOUNT END AS CASH_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'Q' THEN RM.RM_AMOUNT END AS CHEQUE_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'D' THEN RM.RM_AMOUNT END AS DEMAND_DRAFT_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'B' THEN RM.RM_AMOUNT END AS BANK_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'T' THEN RM.RM_AMOUNT END AS TRANSFER_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'P' THEN RM.RM_AMOUNT END AS PAY_ORDER_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'A' THEN RM.RM_AMOUNT END AS ADJUSTMENT_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'R' THEN RM.RM_AMOUNT END AS REBATE_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'U' THEN RM.RM_AMOUNT END AS USER_ADJUSTMENT_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'UD' THEN RM.RM_AMOUNT END AS USER_DISCOUNT_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'W' THEN RM.RM_AMOUNT END AS WEB_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'RT' THEN RM.RM_AMOUNT END AS RTGS_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'N' THEN RM.RM_AMOUNT END AS NEFT_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'F' THEN RM.RM_AMOUNT END AS FDR_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'POS' THEN RM.RM_AMOUNT END AS POS_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'PCA' THEN RM.RM_AMOUNT END AS PETTY_CASH_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'UD' THEN RM.RM_AMOUNT END AS ALL_MODE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'MR' THEN RM.RM_AMOUNT END AS MONEY_RECEIPT_MODE\r\n" + "\r\n" + "\r\n"
				+ "	FROM tb_receipt_mas RM\r\n" + "	INNER JOIN tb_receipt_mode RMD ON RM.RM_RCPTID = RMD.RM_RCPTID\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = RMD.CPD_FEEMODE\r\n"
				+ "	INNER JOIN tb_services_mst SM ON SM.SM_SERVICE_ID = RM.SM_SERVICE_ID\r\n"
				+ "	INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = SM.CDM_DEPT_ID\r\n"
				+ "	WHERE RM.RECEIPT_DEL_FLAG IS NULL\r\n" + "	AND D1.CPD_STATUS = 'A'\r\n"
				+ "	AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE current_date() BETWEEN FA_FROMDATE AND FA_TODATE) AND current_date()\r\n"
				+ "	-- AND RM.ORGID = @x\r\n" + "	) A\r\n" + "	GROUP BY DP_DEPTDESC\r\n" + "	) B\r\n"
				+ "	WHERE (CASH_MODE != 0 OR\r\n" + "	CHEQUE_MODE != 0 OR\r\n" + "	DEMAND_DRAFT_MODE != 0 OR\r\n"
				+ "	BANK_MODE != 0 OR\r\n" + "	TRANSFER_MODE != 0 OR\r\n" + "	PAY_ORDER_MODE != 0 OR\r\n"
				+ "	ADJUSTMENT_MODE != 0 OR\r\n" + "	REBATE_MODE != 0 OR\r\n"
				+ "	USER_ADJUSTMENT_MODE != 0 OR\r\n" + "	USER_DISCOUNT_MODE != 0 OR\r\n" + "	WEB_MODE != 0 OR\r\n"
				+ "	RTGS_MODE != 0 OR\r\n" + "	NEFT_MODE != 0 OR\r\n" + "	FDR_MODE != 0 OR\r\n"
				+ "	POS_MODE != 0 OR\r\n" + "	PETTY_CASH_MODE != 0 OR\r\n" + "	ALL_MODE != 0 OR\r\n"
				+ "	MONEY_RECEIPT_MODE != 0)\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		collectionList = (List<AccountCollectionEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountCollectionEntity.class).getResultList();

		return collectionList;
	}

	public List<AccountReceiptsAndPaymentsEntity> getfunctionWiseReceiptsAndPaymentCount() {
		List<AccountReceiptsAndPaymentsEntity> receiptAndPaymentCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT FM.FUNCTION_DESC AS FUNCTION_OR_ZONE,\r\n"
				+ "	IFNULL(OPERATING_RECEIPTS, 0) AS OPERATING_RECEIPTS,\r\n"
				+ "	IFNULL(NON_OPERATING_RECEIPTS, 0) AS NON_OPERATING_RECEIPTS,\r\n"
				+ "	IFNULL(OPERATING_RECEIPTS, 0) + IFNULL(NON_OPERATING_RECEIPTS, 0) AS TOTAL_RECEIPTS,\r\n"
				+ "	IFNULL(OPERATING_PAYMENTS, 0) AS OPERATING_PAYMENTS,\r\n"
				+ "	IFNULL(NON_OPERATING_PAYMENTS, 0) AS NON_OPERATING_PAYMENTS,\r\n"
				+ "	IFNULL(OPERATING_PAYMENTS, 0) + IFNULL(NON_OPERATING_PAYMENTS, 0) AS TOTAL_PAYMENTS\r\n" + "\r\n"
				+ "	FROM tb_ac_function_master FM\r\n" + "	LEFT JOIN\r\n" + "	(\r\n"
				+ "	SELECT A.FUNCTION_ID AS FUNCTION_ID,\r\n" + "	SUM(OPERATING_RECEIPTS) AS OPERATING_RECEIPTS,\r\n"
				+ "	SUM(NON_OPERATING_RECEIPTS) AS NON_OPERATING_RECEIPTS\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT FM.FUNCTION_ID AS FUNCTION_ID,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'I' THEN RD.RF_FEEAMOUNT END AS OPERATING_RECEIPTS,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE != 'I' THEN RD.RF_FEEAMOUNT END AS NON_OPERATING_RECEIPTS\r\n"
				+ "	FROM tb_ac_secondaryhead_master SHM\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES\r\n"
				+ "	INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID AND D2.CPD_VALUE = 'A'\r\n"
				+ "	INNER JOIN tb_receipt_det RD ON RD.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_receipt_mas RM ON RD.RM_RCPTID = RM.RM_RCPTID\r\n"
				+ "	INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = SHM.FUNCTION_ID\r\n"
				+ "	WHERE RM.RECEIPT_DEL_FLAG IS NULL\r\n"
				+ "	AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE current_date() BETWEEN FA_FROMDATE AND FA_TODATE) AND current_date()\r\n"
				+ "	-- AND SHM.ORGID = @x\r\n" + "	) A\r\n" + "	GROUP BY A.FUNCTION_ID\r\n"
				+ "	) RCPT ON RCPT.FUNCTION_ID = FM.FUNCTION_ID\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "	(\r\n"
				+ "	SELECT B.FUNCTION_ID AS FUNCTION_ID,\r\n"
				+ "	SUM(B.OPERATING_PAYMENTS) AS OPERATING_PAYMENTS,\r\n"
				+ "	SUM(B.NON_OPERATING_PAYMENTS) AS NON_OPERATING_PAYMENTS\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT FM.FUNCTION_ID AS FUNCTION_ID,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'E' THEN PD.PAYMENT_AMT END AS OPERATING_PAYMENTS,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE != 'E' THEN PD.PAYMENT_AMT END AS NON_OPERATING_PAYMENTS\r\n"
				+ "	FROM tb_ac_secondaryhead_master SHM\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES\r\n"
				+ "	INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID AND D2.CPD_VALUE = 'A'\r\n"
				+ "	INNER JOIN (SELECT DISTINCT BM_ID, SAC_HEAD_ID FROM tb_ac_bill_exp_detail)BED ON BED.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = BED.BM_ID AND BM.BM_DEL_FLAG IS NULL\r\n"
				+ "	INNER JOIN tb_ac_payment_det PD ON BM.BM_ID = PD.BM_ID\r\n"
				+ "	INNER JOIN tb_ac_payment_mas PM ON PD.PAYMENT_ID = PM.PAYMENT_ID\r\n"
				+ "	INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = SHM.FUNCTION_ID\r\n"
				+ "	WHERE PM.PAYMENT_DEL_FLAG IS NULL\r\n"
				+ "	AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE current_date() BETWEEN FA_FROMDATE AND FA_TODATE) AND current_date()\r\n"
				+ "	-- AND SHM.ORGID = @x\r\n" + "	) B\r\n" + "	GROUP BY B.FUNCTION_ID\r\n"
				+ "	) PYMT ON PYMT.FUNCTION_ID = FM.FUNCTION_ID\r\n"
				+ "	WHERE ( IFNULL(OPERATING_RECEIPTS, 0) != 0 OR IFNULL(NON_OPERATING_RECEIPTS, 0) != 0 OR IFNULL(OPERATING_PAYMENTS, 0) != 0 OR IFNULL(NON_OPERATING_PAYMENTS, 0) != 0 )\r\n"
				+ "	) as t, (SELECT @row_number\\:=0) AS rn");

		receiptAndPaymentCntList = (List<AccountReceiptsAndPaymentsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountReceiptsAndPaymentsEntity.class).getResultList();

		return receiptAndPaymentCntList;
	}

	public List<AccountReceiptsAndPaymentsEntity> getZoneWiseReceiptsAndPaymentCount() {
		List<AccountReceiptsAndPaymentsEntity> receiptAndPaymentCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT FM.FIELD_DESC AS FUNCTION_OR_ZONE,\r\n"
				+ "	IFNULL(RCPT.OPERATING_RECEIPTS,0) AS OPERATING_RECEIPTS,\r\n"
				+ "	IFNULL(RCPT.NON_OPERATING_RECEIPTS,0) AS NON_OPERATING_RECEIPTS,\r\n"
				+ "	IFNULL(RCPT.OPERATING_RECEIPTS,0) + IFNULL(RCPT.NON_OPERATING_RECEIPTS,0) AS TOTAL_RECEIPTS,\r\n"
				+ "	IFNULL(PMT.OPERATING_PAYMENTS,0) AS OPERATING_PAYMENTS,\r\n"
				+ "	IFNULL(PMT.NON_OPERATING_PAYMENTS,0) AS NON_OPERATING_PAYMENTS,\r\n"
				+ "	IFNULL(PMT.OPERATING_PAYMENTS,0) + IFNULL(PMT.NON_OPERATING_PAYMENTS,0) AS TOTAL_PAYMENTS\r\n"
				+ "\r\n" + "	FROM tb_ac_field_master FM\r\n" + "	LEFT JOIN\r\n" + "	(\r\n"
				+ "	SELECT A.FIELD_ID AS FIELD_ID,\r\n" + "		SUM(OPERATING_RECEIPTS) AS OPERATING_RECEIPTS,\r\n"
				+ "		SUM(NON_OPERATING_RECEIPTS) AS NON_OPERATING_RECEIPTS\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT FM.FIELD_ID AS FIELD_ID,\r\n"
				+ "			CASE WHEN D1.CPD_VALUE = 'I' THEN RD.RF_FEEAMOUNT END AS OPERATING_RECEIPTS,\r\n"
				+ "			CASE WHEN D1.CPD_VALUE != 'I' THEN RD.RF_FEEAMOUNT END AS NON_OPERATING_RECEIPTS\r\n"
				+ "	FROM tb_ac_field_master FM\r\n" + "	INNER JOIN tb_receipt_mas RM ON RM.FIELD_ID = FM.FIELD_ID\r\n"
				+ "	INNER JOIN tb_receipt_det RD ON RD.RM_RCPTID = RM.RM_RCPTID\r\n"
				+ "	INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = RD.SAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES\r\n"
				+ "	INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID AND D2.CPD_VALUE = 'A'\r\n"
				+ "	WHERE RM.RECEIPT_DEL_FLAG IS NULL\r\n"
				+ "	AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE current_date() BETWEEN FA_FROMDATE AND FA_TODATE) AND current_date()\r\n"
				+ "	-- AND RM.ORGID = @x\r\n" + "	) A\r\n" + "	GROUP BY A.FIELD_ID\r\n"
				+ "	) RCPT ON RCPT.FIELD_ID = FM.FIELD_ID\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "	(\r\n"
				+ "	SELECT B.FIELD_ID AS FIELD_ID,\r\n" + "		SUM(B.OPERATING_PAYMENTS) AS OPERATING_PAYMENTS,\r\n"
				+ "		SUM(B.NON_OPERATING_PAYMENTS) AS NON_OPERATING_PAYMENTS\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT FM.FIELD_ID AS FIELD_ID,\r\n"
				+ "			CASE WHEN D1.CPD_VALUE = 'E' THEN PD.PAYMENT_AMT END AS OPERATING_PAYMENTS,\r\n"
				+ "			CASE WHEN D1.CPD_VALUE != 'E' THEN PD.PAYMENT_AMT END AS NON_OPERATING_PAYMENTS\r\n"
				+ "	FROM tb_ac_field_master FM\r\n" + "	INNER JOIN tb_ac_payment_mas PM ON PM.FIELDID = FM.FIELD_ID\r\n"
				+ "	INNER JOIN tb_ac_payment_det PD ON PD.PAYMENT_ID = PM.PAYMENT_ID\r\n"
				+ "	INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = PD.BM_ID AND BM.BM_DEL_FLAG IS NULL\r\n"
				+ "	INNER JOIN (SELECT DISTINCT BM_ID, SAC_HEAD_ID FROM tb_ac_bill_exp_detail)BED ON BED.BM_ID = BM.BM_ID\r\n"
				+ "	INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = BED.SAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES\r\n"
				+ "	INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID AND D2.CPD_VALUE = 'A'\r\n"
				+ "	WHERE PM.PAYMENT_DEL_FLAG IS NULL\r\n"
				+ "	AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE current_date() BETWEEN FA_FROMDATE AND FA_TODATE) AND current_date()\r\n"
				+ "	-- AND PM.ORGID = @x\r\n" + "	) B\r\n" + "	GROUP BY B.FIELD_ID\r\n" + "	) PMT\r\n"
				+ "	ON PMT.FIELD_ID = FM.FIELD_ID\r\n"
				+ "	WHERE IFNULL(RCPT.OPERATING_RECEIPTS,0) != 0 OR IFNULL(RCPT.NON_OPERATING_RECEIPTS,0) != 0 OR IFNULL(PMT.OPERATING_PAYMENTS,0) != 0 OR IFNULL(PMT.NON_OPERATING_PAYMENTS,0) != 0\r\n"
				+ "	-- AND FM.ORGID = @x\r\n" + "	) as t, (SELECT @row_number\\:=0) AS rn");

		receiptAndPaymentCntList = (List<AccountReceiptsAndPaymentsEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountReceiptsAndPaymentsEntity.class).getResultList();

		return receiptAndPaymentCntList;
	}

	public List<AccountClassifDashboardCntEntity> getDashboardCountByIncomeClassificationType() {
		List<AccountClassifDashboardCntEntity> dashboardCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + " SELECT A.*,\r\n"
				+ "	COALESCE(B.AMT,0) AS 2Y_OLD,\r\n" + "	COALESCE(C.AMT,0) AS 1Y_OLD,\r\n"
				+ "	(COALESCE(C.AMT,0) - COALESCE(B.AMT,0)) AS DIFF_INC_OR_DEC,\r\n"
				+ "	COALESCE(D.AMT,0) AS CURRENT_YEAR,\r\n" + "	COALESCE(E.AMT,0) AS AS_ON_2Y,\r\n"
				+ "	COALESCE(F.AMT,0) AS AS_ON_1Y,\r\n"
				+ "	COALESCE(D.AMT,0) - COALESCE(F.AMT,0) AS DIFF_INC_OR_DEC_ASON\r\n"
				+ "	-- (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE(@x, 0) ELSE COALESCE(@x, 0) END) ORGID\r\n"
				+ "	FROM (\r\n" + "	SELECT PAC_HEAD_DESC\r\n" + "	FROM TB_AC_PRIMARYHEAD_MASTER\r\n"
				+ "	WHERE CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND CODCOFDET_ID = 1\r\n" + "	) A\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n" + "	(\r\n"
				+ "	SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(),INTERVAL 2 YEAR) > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 2 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(), INTERVAL 2 YEAR) > FA_FROMDATE AND DATE_SUB(CURDATE(),INTERVAL 2 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE(VD.ORGID,0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) B ON A.PAC_HEAD_DESC = B.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n" + "	(\r\n"
				+ "	SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(), INTERVAL 1 YEAR) > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(), INTERVAL 1 YEAR) > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) C ON A.PAC_HEAD_DESC = C.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n" + "	(\r\n"
				+ "	SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(), INTERVAL 0 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 0 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 0 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 0 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES =(SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) D ON A.PAC_HEAD_DESC = D.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n" + "	(\r\n"
				+ "	SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE date_sub(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND date_sub(CURRENT_DATE(), INTERVAL 2 YEAR)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) E ON A.PAC_HEAD_DESC = E.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE date_sub(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND date_sub(CURRENT_DATE(), INTERVAL 1 YEAR)\r\n"
				+ "	AND VD.ORGID =(CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('I')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) F ON A.PAC_HEAD_DESC = F.PAC_HEAD_DESC\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		dashboardCnts = (List<AccountClassifDashboardCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountClassifDashboardCntEntity.class).getResultList();

		return dashboardCnts;
	}

	public List<AccountClassifDashboardCntEntity> getDashboardCountByExpenseClassificationType() {
		List<AccountClassifDashboardCntEntity> dashboardCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n" + " SELECT A.*,\r\n"
				+ "	COALESCE(B.AMT,0) AS 2Y_OLD,\r\n" + "	COALESCE(C.AMT,0) AS 1Y_OLD,\r\n"
				+ "	(COALESCE(C.AMT,0) - COALESCE(B.AMT,0)) AS DIFF_INC_OR_DEC,\r\n"
				+ "	COALESCE(D.AMT,0) AS CURRENT_YEAR,\r\n" + "	COALESCE(E.AMT,0) AS AS_ON_2Y,\r\n"
				+ "	COALESCE(F.AMT,0) AS AS_ON_1Y,\r\n"
				+ "	COALESCE(D.AMT,0) - COALESCE(F.AMT,0) AS DIFF_INC_OR_DEC_ASON\r\n"
				+ "	FROM (SELECT PAC_HEAD_DESC\r\n" + "	FROM TB_AC_PRIMARYHEAD_MASTER\r\n"
				+ "	WHERE CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND CODCOFDET_ID = 1\r\n" + "	) A\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_DR) - SUM(P.VAMT_CR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 2 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 2 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 2 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 2 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) B ON A.PAC_HEAD_DESC = B.PAC_HEAD_DESC\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_DR) - SUM(P.VAMT_CR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 1 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 1 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 1 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 1 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) C ON A.PAC_HEAD_DESC = C.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_DR) - SUM(P.VAMT_CR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM TB_FINANCIALYEAR WHERE DATE_SUB(CURDATE(), INTERVAL 0 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 0 YEAR) < FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM TB_FINANCIALYEAR WHERE DATE_SUB( CURDATE(), INTERVAL 0 YEAR) > FA_FROMDATE AND DATE_SUB( CURDATE(), INTERVAL 0 YEAR) < FA_TODATE)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) D ON A.PAC_HEAD_DESC = D.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_CR) - SUM(P.VAMT_DR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE date_sub(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND DATE_SUB( CURDATE(), INTERVAL 2 YEAR)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) E ON A.PAC_HEAD_DESC = E.PAC_HEAD_DESC\r\n" + "\r\n" + "	LEFT JOIN\r\n" + "\r\n"
				+ "	(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + "	R.PAC_HEAD_DESC,\r\n"
				+ "	SUM(P.VAMT_DR) - SUM(P.VAMT_CR) AS AMT\r\n" + "	FROM (SELECT C.PAC_HEAD_COMPO_CODE,\r\n"
				+ "	C.PAC_HEAD_DESC,\r\n" + "	C.PAC_HEAD_ID AS LEVEL2,\r\n" + "	D.PAC_HEAD_ID AS LEVEL1,\r\n"
				+ "	SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + "	SUM(AAA.VAMT_CR) AS VAMT_CR\r\n"
				+ "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	SM.PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER PM\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE date_sub(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND DATE_SUB( CURDATE(), INTERVAL 1 YEAR)\r\n"
				+ "	AND VD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( VD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.CPD_ID_ACHEADTYPES = (SELECT CPD_ID\r\n" + "	FROM TB_COMPARAM_DET D\r\n"
				+ "	WHERE D.CPM_ID IN (SELECT CPM_ID\r\n" + "	FROM TB_COMPARAM_MAS M\r\n"
				+ "	WHERE M.CPM_PREFIX = 'COA'\r\n" + "	)\r\n" + "	AND D.CPD_VALUE = ('E')\r\n" + "	)\r\n"
				+ "	AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + "	AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n"
				+ "	GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, SM.PAC_HEAD_ID\r\n" + "	) A\r\n" + "	) AAA,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER C,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER D\r\n"
				+ "	WHERE AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + "	AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n"
				+ "	GROUP BY C.PAC_HEAD_ID, C.PAC_HEAD_DESC, D.PAC_HEAD_ID, C.PAC_HEAD_COMPO_CODE\r\n" + "	) P,\r\n"
				+ "	TB_AC_PRIMARYHEAD_MASTER R\r\n" + "	WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n"
				+ "	GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n"
				+ "	) F ON A.PAC_HEAD_DESC = F.PAC_HEAD_DESC\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		dashboardCnts = (List<AccountClassifDashboardCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountClassifDashboardCntEntity.class).getResultList();

		return dashboardCnts;
	}

	public List<AccountRatioDashboardCntEntity> getDashboardCountByIncomeRatioType() {
		List<AccountRatioDashboardCntEntity> dashboardCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " SELECT PHM.PAC_HEAD_DESC,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_CURRENT_YEAR,0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	IFNULL(X.INCOM_AMT_CURRENT_YEAR,0) AS INCOM_EXPENSE_AMT_CURRENT_YEAR,\r\n"
				+ "	IFNULL(((X.INCOM_AMT_CURRENT_YEAR / A.ORGINAL_ESTAMT_CURRENT_YEAR) * 100 ),0) AS CURRENT_YEAR_RATIO,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_1Y,0) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	IFNULL(X.INCOM_AMT_1Y,0) AS INCOM_EXPENSE_AMT_1Y,\r\n"
				+ "	IFNULL(((X.INCOM_AMT_1Y / A.ORGINAL_ESTAMT_1Y) * 100),0) AS RATIO_1Y,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_2Y,0) AS ORGINAL_ESTAMT_2Y,\r\n"
				+ "	IFNULL(X.INCOM_AMT_2Y,0) AS INCOM_EXPENSE_AMT_2Y,\r\n"
				+ "	IFNULL(((X.INCOM_AMT_2Y / A.ORGINAL_ESTAMT_2Y) * 100), 0) AS RATIO_2Y        \r\n" + "\r\n"
				+ "	FROM TB_AC_PRIMARYHEAD_MASTER PHM\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('I')\r\n"
				+ "	INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n"
				+ "	LEFT JOIN\r\n" + "	(\r\n" + "	SELECT B.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(AAA.INCOM_AMT_CURRENT_YEAR) AS INCOM_AMT_CURRENT_YEAR,\r\n"
				+ "	SUM(AAA.INCOM_AMT_1Y) AS INCOM_AMT_1Y,\r\n" + "	SUM(AAA.INCOM_AMT_2Y) AS INCOM_AMT_2Y\r\n"
				+ "	FROM\r\n" + "	(\r\n" + "	SELECT SHM.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(CASE WHEN RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) THEN RD.RF_FEEAMOUNT END) AS INCOM_AMT_CURRENT_YEAR,\r\n"
				+ "	SUM(CASE WHEN RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN RD.RF_FEEAMOUNT END) AS INCOM_AMT_1Y,\r\n"
				+ "	SUM(CASE WHEN RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN RD.RF_FEEAMOUNT END) AS INCOM_AMT_2Y\r\n"
				+ "	FROM tb_receipt_mas RM\r\n" + "	INNER JOIN tb_receipt_det RD ON RM.RM_RCPTID = RD.RM_RCPTID\r\n"
				+ "	INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = RD.SAC_HEAD_ID    \r\n"
				+ "	WHERE RM.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( RM.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND RM.RECEIPT_DEL_FLAG IS NULL\r\n"
				+ "	AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	GROUP BY SHM.PAC_HEAD_ID\r\n" + "	) AAA\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = AAA.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n"
				+ "	GROUP BY B.PAC_HEAD_ID\r\n" + "	) X ON X.PAC_HEAD_ID = PHM.PAC_HEAD_ID\r\n" + "\r\n"
				+ "	LEFT JOIN\r\n" + "	(\r\n" + "	SELECT B.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_1Y) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_2Y) AS ORGINAL_ESTAMT_2Y\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT BCD.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_2Y\r\n"
				+ "	FROM tb_ac_budgetcode_mas BCD\r\n"
				+ "	INNER JOIN tb_ac_projectedrevenue PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n"
				+ "	AND PRV.FA_YEARID IN (\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE\r\n"
				+ "	UNION ALL\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE\r\n"
				+ "	UNION ALL\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE        \r\n"
				+ "	)\r\n"
				+ "	WHERE BCD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( BCD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	GROUP BY BCD.PAC_HEAD_ID\r\n" + "	)AAA\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = AAA.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n"
				+ "	GROUP BY B.PAC_HEAD_ID\r\n" + "	) A ON A.PAC_HEAD_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	WHERE PHM.CODCOFDET_ID = 1\r\n" + "	ORDER BY PHM.PAC_HEAD_DESC\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");

		dashboardCnts = (List<AccountRatioDashboardCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountRatioDashboardCntEntity.class).getResultList();

		return dashboardCnts;
	}

	public List<AccountRatioDashboardCntEntity> getDashboardCountByExpenseRatioType() {
		List<AccountRatioDashboardCntEntity> dashboardCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " SELECT PHM.PAC_HEAD_DESC,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_CURRENT_YEAR, 0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	IFNULL(X.PAYMENT_AMT_CURRENT_YEAR, 0) AS INCOM_EXPENSE_AMT_CURRENT_YEAR,\r\n"
				+ "	IFNULL(((X.PAYMENT_AMT_CURRENT_YEAR / A.ORGINAL_ESTAMT_CURRENT_YEAR) * 100 ), 0) AS CURRENT_YEAR_RATIO,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_1Y, 0) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	IFNULL(X.PAYMENT_AMT_1Y, 0) AS INCOM_EXPENSE_AMT_1Y,\r\n"
				+ "	IFNULL(((X.PAYMENT_AMT_1Y / A.ORGINAL_ESTAMT_1Y) * 100), 0) AS RATIO_1Y,\r\n"
				+ "	IFNULL(A.ORGINAL_ESTAMT_2Y, 0) AS ORGINAL_ESTAMT_2Y,\r\n"
				+ "	IFNULL(X.PAYMENT_AMT_2Y, 0) AS INCOM_EXPENSE_AMT_2Y,\r\n"
				+ "	IFNULL(((X.PAYMENT_AMT_2Y / A.ORGINAL_ESTAMT_2Y) * 100), 0) AS RATIO_2Y        \r\n" + "\r\n"
				+ "	FROM TB_AC_PRIMARYHEAD_MASTER PHM\r\n"
				+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('E')\r\n"
				+ "	INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n"
				+ "	LEFT JOIN\r\n" + "	(\r\n" + "	SELECT B.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(AAA.PAYMENT_AMT_CURRENT_YEAR) AS PAYMENT_AMT_CURRENT_YEAR,\r\n"
				+ "	SUM(AAA.PAYMENT_AMT_1Y) AS PAYMENT_AMT_1Y,\r\n" + "	SUM(AAA.PAYMENT_AMT_2Y) AS PAYMENT_AMT_2Y\r\n"
				+ "	FROM\r\n" + "	(\r\n" + "	SELECT SHM.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(CASE WHEN PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) THEN PD.PAYMENT_AMT END) AS PAYMENT_AMT_CURRENT_YEAR,\r\n"
				+ "	SUM(CASE WHEN PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PD.PAYMENT_AMT END) AS PAYMENT_AMT_1Y,\r\n"
				+ "	SUM(CASE WHEN PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) AND (SELECT FA_TODATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PD.PAYMENT_AMT END) AS PAYMENT_AMT_2Y\r\n"
				+ "	FROM tb_ac_payment_mas PM\r\n"
				+ "	INNER JOIN tb_ac_payment_det PD ON PM.PAYMENT_ID = PD.PAYMENT_ID\r\n"
				+ "	INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = PD.BM_ID\r\n"
				+ "	INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = PD.BUDGETCODE_ID    \r\n"
				+ "	WHERE PM.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( PM.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	AND PM.PAYMENT_DEL_FLAG IS NULL\r\n" + "	AND BM.CHECKER_AUTHO = 'Y'\r\n"
				+ "	AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND (SELECT FA_TODATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	GROUP BY SHM.PAC_HEAD_ID\r\n" + "	)AAA\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = AAA.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n"
				+ "	GROUP BY B.PAC_HEAD_ID\r\n" + "	) X ON X.PAC_HEAD_ID = PHM.PAC_HEAD_ID\r\n" + "\r\n"
				+ "	LEFT JOIN\r\n" + "	(\r\n" + "	SELECT B.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_1Y) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	SUM(AAA.ORGINAL_ESTAMT_2Y) AS ORGINAL_ESTAMT_2Y\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT BCD.PAC_HEAD_ID AS PAC_HEAD_ID,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y,\r\n"
				+ "	SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_2Y\r\n"
				+ "	FROM tb_ac_budgetcode_mas BCD\r\n"
				+ "	INNER JOIN tb_ac_projected_expenditure PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n"
				+ "	AND PRV.FA_YEARID IN (\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE\r\n"
				+ "	UNION ALL\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE\r\n"
				+ "	UNION ALL\r\n"
				+ "	SELECT FA_YEARID FROM tb_financialyear WHERE DATE_SUB(CURRENT_DATE(), INTERVAL 2 YEAR) BETWEEN FA_FROMDATE AND FA_TODATE        \r\n"
				+ "	)\r\n"
				+ "	WHERE BCD.ORGID = (CASE WHEN COALESCE(@x, 0) = 0 THEN COALESCE( BCD.ORGID, 0) ELSE COALESCE(@x, 0) END)\r\n"
				+ "	GROUP BY BCD.PAC_HEAD_ID\r\n" + "	)AAA\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = AAA.PAC_HEAD_ID\r\n"
				+ "	INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n"
				+ "	GROUP BY B.PAC_HEAD_ID\r\n" + "	) A ON A.PAC_HEAD_ID = PHM.PAC_HEAD_ID\r\n"
				+ "	WHERE PHM.CODCOFDET_ID = 1\r\n" + "	ORDER BY PHM.PAC_HEAD_DESC\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");

		dashboardCnts = (List<AccountRatioDashboardCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountRatioDashboardCntEntity.class).getResultList();

		return dashboardCnts;
	}

	public List<AccountFundStatusEntity> getDashboardCountByFundStatus() {
		List<AccountFundStatusEntity> dashboardCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " SELECT MFM.FUND_DESC AS FUND_DESC,\r\n"
				+ "	IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0) AS OPENING_BALANCE,\r\n"
				+ "	IFNULL(X.VAMT_DR,0) AS RECEIPT,\r\n" + "	IFNULL(X.VAMT_CR,0) AS PAYMENT,\r\n"
				+ "	IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0) + IFNULL(X.VAMT_DR,0) - IFNULL(X.VAMT_CR,0) AS CLOSING_BALANCE,\r\n"
				+ "	IFNULL(INV.FDR,0) AS FDR,\r\n"
				+ "	IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0) + IFNULL(X.VAMT_DR,0) - IFNULL(X.VAMT_CR,0) + IFNULL(INV.FDR,0) AS TOTAL_FUND_BAL\r\n"
				+ "\r\n" + "	FROM TB_AC_FUND_MASTER MFM\r\n" + "	LEFT JOIN\r\n" + "	(\r\n"
				+ "	SELECT FUND_ID, SUM(IN_INVAMT) FDR\r\n" + "	FROM tb_ac_invmst\r\n" + "	WHERE IN_STATUS = 'A'\r\n"
				+ "	-- AND ORGID = @x\r\n" + "	GROUP BY FUND_ID\r\n" + "	) INV ON INV.FUND_ID = MFM.FUND_ID\r\n"
				+ "	LEFT JOIN\r\n" + "	(\r\n" + "	SELECT FM.FUND_ID AS FUND_ID,\r\n"
				+ "	FM.FUND_DESC AS FUND_DESC,\r\n" + "	SUM(BAL.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n"
				+ "	SUM(BAL.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" + "	SUM(VAMT_CR) AS VAMT_CR,\r\n"
				+ "	SUM(VAMT_DR) AS VAMT_DR\r\n" + "\r\n" + "	FROM TB_AC_FUND_MASTER FM\r\n"
				+ "	INNER JOIN TB_BANK_ACCOUNT BA ON BA.FUND_ID = FM.FUND_ID\r\n"
				+ "	INNER JOIN TB_AC_SECONDARYHEAD_MASTER SHM ON SHM.BA_ACCOUNTID = BA.BA_ACCOUNTID\r\n"
				+ "	INNER JOIN\r\n" + "	(\r\n" + "	SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n"
				+ "	OPENBAL_AMT_DR,\r\n" + "	OPENBAL_AMT_CR,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT AC_HEAD_CODE,\r\n" + "	SAC_HEAD_ID,\r\n"
				+ "	OPENBAL_AMT_DR,\r\n" + "	OPENBAL_AMT_CR,\r\n" + "	VAMT_CR,\r\n" + "	VAMT_DR,\r\n"
				+ "	A.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "	PAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE )\r\n"
				+ "	AND CURRENT_DATE()\r\n" + "	-- AND VD.ORGID = @x\r\n" + "	GROUP BY VD.AC_HEAD_CODE,\r\n"
				+ "	VD.SAC_HEAD_ID,\r\n" + "	PAC_HEAD_ID) A\r\n" + "	LEFT JOIN\r\n"
				+ "	(SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" + "	B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + "	(CASE\r\n"
				+ "	WHEN CD.CPD_VALUE IN\r\n" + "	('DR')\r\n" + "	THEN\r\n" + "	BG.OPENBAL_AMT\r\n" + "	ELSE\r\n"
				+ "	0\r\n" + "	END)\r\n" + "	AS OPENBAL_AMT_DR,\r\n" + "	(CASE\r\n" + "	WHEN CD.CPD_VALUE IN\r\n"
				+ "	('CR')\r\n" + "	THEN\r\n" + "	BG.OPENBAL_AMT\r\n" + "	ELSE\r\n" + "	0\r\n" + "	END)\r\n"
				+ "	AS OPENBAL_AMT_CR,\r\n" + "	B.PAC_HEAD_ID\r\n" + "	FROM TB_AC_BUGOPEN_BALANCE BG,\r\n"
				+ "	TB_COMPARAM_DET CD,\r\n" + "	TB_AC_SECONDARYHEAD_MASTER B\r\n"
				+ "	WHERE BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + "	-- AND BG.ORGID = @x\r\n"
				+ "	AND BG.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND B.SAC_HEAD_ID =\r\n" + "	BG.SAC_HEAD_ID\r\n" + "	AND B.ORGID = BG.ORGID) E\r\n"
				+ "	ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" + "	UNION\r\n" + "	SELECT E.AC_HEAD_CODE,\r\n"
				+ "	E.SAC_HEAD_ID,\r\n" + "	OPENBAL_AMT_DR,\r\n" + "	OPENBAL_AMT_CR,\r\n" + "	VAMT_CR,\r\n"
				+ "	VAMT_DR,\r\n" + "	E.PAC_HEAD_ID\r\n" + "	FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n"
				+ "	SUM(VD.VAMT_CR) VAMT_CR,\r\n" + "	SUM(VD.VAMT_DR) VAMT_DR,\r\n"
				+ "	VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" + "	FROM VW_VOUCHER_DETAIL VD\r\n"
				+ "	WHERE VD.VOU_POSTING_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE )\r\n"
				+ "	AND CURRENT_DATE()\r\n" + "	-- AND VD.ORGID = @x\r\n" + "	GROUP BY VD.AC_HEAD_CODE,\r\n"
				+ "	VD.SAC_HEAD_ID) A\r\n" + "	RIGHT JOIN\r\n" + "	(SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n"
				+ "	B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + "	(CASE\r\n" + "	WHEN CD.CPD_VALUE IN\r\n" + "	('DR')\r\n"
				+ "	THEN\r\n" + "	BG.OPENBAL_AMT\r\n" + "	ELSE\r\n" + "	0\r\n" + "	END)\r\n"
				+ "	AS OPENBAL_AMT_DR,\r\n" + "	(CASE\r\n" + "	WHEN CD.CPD_VALUE IN\r\n" + "	('CR')\r\n"
				+ "	THEN\r\n" + "	BG.OPENBAL_AMT\r\n" + "	ELSE\r\n" + "	0\r\n" + "	END)\r\n"
				+ "	AS OPENBAL_AMT_CR,\r\n" + "	B.AC_HEAD_CODE,\r\n" + "	B.SAC_HEAD_ID,\r\n" + "	B.PAC_HEAD_ID\r\n"
				+ "	FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + "	TB_COMPARAM_DET CD,\r\n"
				+ "	TB_AC_SECONDARYHEAD_MASTER B\r\n" + "	WHERE BG.CPD_ID_DRCR = CD.CPD_ID\r\n"
				+ "	-- AND BG.ORGID = @x\r\n"
				+ "	AND BG.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "	AND B.SAC_HEAD_ID =\r\n" + "	BG.SAC_HEAD_ID\r\n" + "	AND B.ORGID = BG.ORGID) E\r\n"
				+ "	ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1)\r\n" + "	A,\r\n" + "	TB_AC_PRIMARYHEAD_MASTER B\r\n"
				+ "	WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID\r\n" + "	) BAL ON BAL.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n"
				+ "	GROUP BY FM.FUND_ID, FM.FUND_DESC\r\n" + "	) X ON X.FUND_ID = MFM.FUND_ID\r\n"
				+ "	WHERE (IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0)) != 0\r\n"
				+ "	OR IFNULL(X.VAMT_DR,0) != 0\r\n" + "	OR IFNULL(X.VAMT_CR,0) != 0\r\n"
				+ "	OR (IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0) + IFNULL(X.VAMT_DR,0) - IFNULL(X.VAMT_CR,0)) != 0\r\n"
				+ "	OR IFNULL(INV.FDR,0) != 0\r\n"
				+ "	OR (IFNULL(X.OPENBAL_AMT_DR,0) - IFNULL(X.OPENBAL_AMT_CR,0) + IFNULL(X.VAMT_DR,0) - IFNULL(X.VAMT_CR,0) + IFNULL(INV.FDR,0)) != 0\r\n"
				+ "	ORDER BY MFM.FUND_DESC\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		dashboardCnts = (List<AccountFundStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountFundStatusEntity.class).getResultList();

		return dashboardCnts;
	}

	public List<AccountTransCntDayWiseEntity> getTransactionCountByDays(int noOfDays) {
		List<AccountTransCntDayWiseEntity> transactionCnts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select o.ONLINE_COUNT, c.CASH_COUNT, b.BUDGET_AMT, e.EXPENDITURE_AMT from\r\n"
				+ " (select count(1) as ONLINE_COUNT from (select cpd_id_payment_mode,PAYMENT_DATE from tb_ac_payment_mas union \r\n"
				+ " select CPD_FEEMODE,RM_DATE from tb_receipt_mas a,tb_receipt_mode b where a.RM_RCPTID=b.RM_RCPTID) a ,(select cpd_id,a.cpd_value from tb_comparam_det a,tb_comparam_mas b \r\n"
				+ " where a.cpd_value in ('W','RT','N','B') and a.CPM_ID = b.CPM_ID and CPM_PREFIX='PAY') b where a.cpd_id_payment_mode=b.cpd_id \r\n"
				+ " and a.PAYMENT_DATE between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()) as o,\r\n"
				+ " (select count(1) as CASH_COUNT from (select cpd_id_payment_mode,PAYMENT_DATE from tb_ac_payment_mas union \r\n"
				+ " select CPD_FEEMODE,RM_DATE from tb_receipt_mas a,tb_receipt_mode b where a.RM_RCPTID=b.RM_RCPTID) a ,(select cpd_id,a.cpd_value from tb_comparam_det a,tb_comparam_mas b \r\n"
				+ " where a.cpd_value in ('C') and a.CPM_ID = b.CPM_ID and CPM_PREFIX='PAY') b where a.cpd_id_payment_mode=b.cpd_id \r\n"
				+ " and a.PAYMENT_DATE between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()) as c,\r\n"
				+ " (select sum(RM_AMOUNT) as BUDGET_AMT from tb_receipt_mas a where a.RM_DATE between DATE_SUB(CURDATE(), INTERVAL "
				+ noOfDays + " day) and now()) as b,\r\n"
				+ " (select sum(PAYMENT_AMOUNT) as EXPENDITURE_AMT from tb_ac_payment_mas a where a.PAYMENT_DATE between DATE_SUB(CURDATE(), INTERVAL "
				+ noOfDays + " day) and now()) as e\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		transactionCnts = (List<AccountTransCntDayWiseEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), AccountTransCntDayWiseEntity.class).getResultList();

		return transactionCnts;
	}
}
