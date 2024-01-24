package com.abm.mainet.common.dashboard.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDaysWiseDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedCntAndRevenueEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LocAndCategoryWiseLicenseCntEntity;

@Repository
public class LicenseDashboardGraphDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	public List<LocAndCategoryWiseLicenseCntEntity> getZoneAndCategoryWiseLicenseCount() {
		List<LocAndCategoryWiseLicenseCntEntity> licenseCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT TRD_WARD1, ZONE, LICENSE_CATAGORY, COUNT(TRD_ID) CNT\r\n" + "	FROM\r\n" + "	(\r\n"
				+ "	SELECT TM.TRD_WARD1,\r\n"
				+ "	(SELECT COD_DESC FROM tb_comparent_det WHERE COD_ID = TM.TRD_WARD1) ZONE,\r\n"
				+ "	(SELECT COD_DESC FROM tb_comparent_det WHERE COD_ID = ID.TRI_COD1) LICENSE_CATAGORY,\r\n"
				+ "	TM.TRD_ID\r\n"
				+ "	FROM (SELECT * FROM tb_ml_trade_mast) TM, tb_comparam_mas M1, tb_comparam_det D1, tb_ml_item_detail ID\r\n"
				+ "	WHERE M1.CPM_ID	= D1.CPM_ID	\r\n"
				+ "	-- AND TM.ORGID = (case when COALESCE(@x,0)=0 then COALESCE(TM.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "	AND TM.TRD_WARD1 = (CASE WHEN COALESCE(0, 0) = 0 THEN COALESCE(TM.TRD_WARD1, 0) ELSE COALESCE(0, 0) END)	\r\n"
				+ "	AND TM.TRD_STATUS = D1.CPD_ID\r\n" + "	AND TM.TRD_ID = ID.TRD_ID\r\n" + "	)A\r\n"
				+ "	GROUP BY TRD_WARD1, ZONE, LICENSE_CATAGORY\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		licenseCntList = (List<LocAndCategoryWiseLicenseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LocAndCategoryWiseLicenseCntEntity.class).getResultList();

		return licenseCntList;
	}

	public List<LicenseCntEntity> getYearWiseLicenseCount() {
		List<LicenseCntEntity> licenseCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT YEAR(TRD_LICFROM_DATE) AS HDATE,\r\n"
				+ "	COUNT(CASE WHEN D.COD_VALUE = 'STR' THEN A.TRD_ID END) AS STORAGE_LICENSE,\r\n"
				+ "	COUNT(CASE WHEN D.COD_VALUE = 'TL' THEN A.TRD_ID END) AS TRADE_LICENSE\r\n"
				+ "	FROM (SELECT * FROM tb_ml_trade_mast) A ,\r\n"
				+ "	(SELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) B,\r\n"
				+ "	(SELECT * FROM tb_ml_item_detail) C,\r\n" + "	(SELECT * FROM tb_comparent_det) D,\r\n"
				+ "	(SELECT * FROM tb_comparam_det WHERE CPD_VALUE = 'I') E,\r\n"
				+ "	(SELECT * FROM tb_cfc_application_mst) H,\r\n" + "	(SELECT * FROM tb_services_mst) F,\r\n"
				+ "	(SELECT * FROM tb_department) G\r\n" + "\r\n"
				+ "	WHERE (TRD_LICFROM_DATE BETWEEN B.FA_FROMDATE AND B.FA_TODATE)\r\n" + "	AND A.TRD_ID = C.TRD_ID\r\n"
				+ "	AND C.TRI_COD1 = D.COD_ID\r\n"
				+ "	AND H.APM_APPLICATION_ID = A.APM_APPLICATION_ID AND H.ORGID = A.ORGID\r\n"
				+ "	AND F.SM_SERVICE_ID = H.SM_SERVICE_ID AND F.ORGID = H.ORGID\r\n"
				+ "	AND F.CDM_DEPT_ID = G.DP_DEPTID\r\n" + "	AND G.DP_DEPTCODE = 'ML'\r\n"
				+ "	AND E.CPD_ID = A.TRD_STATUS\r\n"
				+ "	-- AND A.ORGID = (case when COALESCE(3,0)=0 then COALESCE(A.orgid,0) else COALESCE(3,0) end)\r\n"
				+ "    GROUP BY YEAR(TRD_LICFROM_DATE)\r\n" + ") as t, (SELECT @row_number\\:=0) AS rn");

		licenseCntList = (List<LicenseCntEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseCntEntity.class).getResultList();

		return licenseCntList;
	}

	public List<LicenseCntDayWiseEntity> getLicenseCountByDays(int noOfDays) {
		List<LicenseCntDayWiseEntity> licenseCntList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT COUNT(CASE WHEN D.COD_VALUE = 'STR' THEN A.TRD_ID END) AS STORAGE_LICENSE,\r\n"
				+ "	COUNT(CASE WHEN D.COD_VALUE = 'TL' THEN A.TRD_ID END) AS TRADE_LICENSE\r\n"
				+ "	FROM (SELECT * FROM tb_ml_trade_mast) A ,\r\n" + "	(SELECT * FROM tb_ml_item_detail) C,\r\n"
				+ "	(SELECT * FROM tb_comparent_det) D,\r\n"
				+ "	(SELECT * FROM tb_comparam_det WHERE CPD_VALUE = 'I') E,\r\n"
				+ "	(SELECT * FROM tb_cfc_application_mst) H,\r\n" + "	(SELECT * FROM tb_services_mst) F,\r\n"
				+ "	(SELECT * FROM tb_department) G\r\n" + "\r\n"
				+ "	WHERE H.APM_APPLICATION_ID = A.APM_APPLICATION_ID AND H.ORGID = A.ORGID\r\n"
				+ "	AND F.SM_SERVICE_ID = H.SM_SERVICE_ID AND F.ORGID = H.ORGID\r\n"
				+ "	AND F.CDM_DEPT_ID = G.DP_DEPTID\r\n" + "	AND G.DP_DEPTCODE = 'ML'\r\n"
				+ "	AND E.CPD_ID = A.TRD_STATUS\r\n" + "	AND A.TRD_ID = C.TRD_ID\r\n"
				+ "	AND C.TRI_COD1 = D.COD_ID\r\n"
				+ "	-- AND A.ORGID = (case when COALESCE(3,0)=0 then COALESCE(A.orgid,0) else COALESCE(3,0) end)\r\n"
				+ "    AND TRD_LICFROM_DATE between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n"
				+ ") as t, (SELECT @row_number\\:=0) AS rn");

		licenseCntList = (List<LicenseCntDayWiseEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseCntDayWiseEntity.class).getResultList();

		return licenseCntList;
	}

	public List<LicenseDataEntity> getLicenseDataByDaysAndType(int noOfDays, String type) {
		List<LicenseDataEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "SELECT D.COD_DESC as LICENSE_TYPE, H.APM_APPLICATION_ID as APPLICATION_NO, W.DATE_OF_REQUEST as APPLICATION_DATE,\r\n"
				+ "TRIM(concat(IFNULL(H.APM_FNAME, ''), ' ', IFNULL(H.APM_MNAME, ''), ' ', IFNULL(H.APM_LNAME, ''))) as APPLICANT_NAME,\r\n"
				+ "G.DP_DEPTDESC as Department, W.STATUS as STATUS, F.SM_SERVICE_NAME as SERVICE_NAME\r\n"
				+ "	FROM (SELECT * FROM tb_ml_trade_mast) A ,\r\n" + "	(SELECT * FROM tb_ml_item_detail) C,\r\n"
				+ "	(SELECT * FROM tb_comparent_det) D,\r\n"
				+ "	(SELECT * FROM tb_comparam_det WHERE CPD_VALUE = 'I') E,\r\n"
				+ "	(SELECT * FROM tb_cfc_application_mst) H,\r\n" + "	(SELECT * FROM tb_services_mst) F,\r\n"
				+ "	(SELECT * FROM tb_department) G,\r\n" + "    (SELECT * FROM tb_workflow_request) W\r\n" + "\r\n"
				+ "	WHERE H.APM_APPLICATION_ID = A.APM_APPLICATION_ID AND H.ORGID = A.ORGID\r\n"
				+ "	AND F.SM_SERVICE_ID = H.SM_SERVICE_ID AND F.ORGID = H.ORGID\r\n"
				+ "	AND F.CDM_DEPT_ID = G.DP_DEPTID\r\n" + "	AND G.DP_DEPTCODE = 'ML'\r\n"
				+ "	AND E.CPD_ID = A.TRD_STATUS\r\n" + "	AND A.TRD_ID = C.TRD_ID\r\n"
				+ "	AND C.TRI_COD1 = D.COD_ID\r\n" + "    AND H.APM_APPLICATION_ID = W.APM_APPLICATION_ID\r\n"
				+ "	-- AND A.ORGID = (case when COALESCE(3,0)=0 then COALESCE(A.orgid,0) else COALESCE(3,0) end)\r\n");
		if (noOfDays != 0) {
			queryBuilder.append(
					" AND TRD_LICFROM_DATE between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		}
		if (type != null) {
			queryBuilder.append("AND D.COD_VALUE = '" + type + "'");
		}
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");

		licenseDataList = (List<LicenseDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseDataEntity.class).getResultList();

		return licenseDataList;
	}

	public List<LicenseIssuedDetEntity> getIssuedLicenseDetByOrgId(Long orgId) {
		List<LicenseIssuedDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT @a\\:=@a+1 as num , COD_DESC AS LICENSE_CATEGORY, IFNULL(UP_TO_LAST_MONTH_FEE_AMT, 0)\r\n"
						+ "AS UPTO_LAST_MONTH, IFNULL(CURRENT_MONTH_FEE_AMT, 0) AS CURRENT_MONTH,IFNULL(TILL_MONTH_FEE_AMT, 0) AS TILL_MONTH \r\n"
						+ "FROM tb_comparent_det D1 INNER JOIN tb_comparent_mas M1 ON M1.COM_ID = D1.COM_ID AND M1.COM_VALUE = 'C' \r\n"
						+ "INNER JOIN tb_comparam_mas M2 ON M2.CPM_ID = M1.CPM_ID AND M2.CPM_PREFIX = 'ITC' LEFT JOIN \r\n"
						+ "	( SELECT ID.TRI_COD1 AS LIC_CAT_CODE, SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(CURRENT_DATE()), INTERVAL 1 DAY), INTERVAL - 1 MONTH) AND CURRENT_DATE()) \r\n"
						+ "	AND (DATE_FORMAT(RD.CREATED_DATE, '%Y-%m-%d') BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(CURRENT_DATE()), INTERVAL 1 DAY), INTERVAL - 1 MONTH) AND CURRENT_DATE())\r\n"
						+ "	THEN RD.RF_FEEAMOUNT END) CURRENT_MONTH_FEE_AMT, \r\n"
						+ "	SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d') FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (LAST_DAY(DATE_ADD(CURRENT_DATE(), INTERVAL - 1 MONTH))))\r\n"
						+ "	AND (DATE_FORMAT(RD.CREATED_DATE, '%Y-%m-%d') BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d') FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (LAST_DAY(DATE_ADD(CURRENT_DATE(), INTERVAL - 1 MONTH))))\r\n"
						+ "	THEN RD.RF_FEEAMOUNT END) UP_TO_LAST_MONTH_FEE_AMT,\r\n"
						+ "	SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d') FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
						+ "	AND (DATE_FORMAT(RD.CREATED_DATE, '%Y-%m-%d') BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d') FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
						+ "	THEN RD.RF_FEEAMOUNT END) TILL_MONTH_FEE_AMT\r\n" + "	FROM tb_ml_trade_mast TM \r\n"
						+ "	INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = TM.TRD_STATUS AND CPD_VALUE = 'I' \r\n"
						+ "INNER JOIN tb_ml_item_detail ID ON ID.TRD_ID = TM.TRD_ID AND TM.ORGID = ID.ORGID \r\n"
						+ "INNER JOIN tb_cfc_application_mst AM ON AM.APM_APPLICATION_ID = TM.APM_APPLICATION_ID \r\n"
						+ "INNER JOIN tb_services_mst SM ON SM.SM_SERVICE_ID = AM.SM_SERVICE_ID \r\n"
						+ "INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = SM.CDM_DEPT_ID AND DEPT.DP_DEPTCODE = 'ML' \r\n"
						+ "INNER JOIN tb_receipt_mas RM ON RM.APM_APPLICATION_ID = TM.APM_APPLICATION_ID AND RM.ORGID = TM.ORGID \r\n"
						+ "INNER JOIN tb_receipt_det RD ON RD.RM_RCPTID = RM.RM_RCPTID AND RD.ORGID = RM.ORGID,(select @a\\:=0) as a");
		if (orgId != null) {
			queryBuilder.append(" WHERE TM.ORGID ='" + orgId + "' ");
		}
			queryBuilder.append(" AND TM.TRD_LICNO IS NOT NULL\r\n"
				+ "AND TM.TRD_LICFROM_DATE BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d') FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE)\r\n"
				+ "AND CURRENT_DATE() AND DATE_FORMAT(RD.CREATED_DATE, '%Y-%m-%d') BETWEEN (SELECT DATE_FORMAT(FA_FROMDATE, '%Y-%m-%d')\r\n"
				+ "FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE()\r\n"
				+ "GROUP BY LIC_CAT_CODE) A ON A.LIC_CAT_CODE = D1.COD_ID\r\n" + "WHERE COD_STATUS = 'Y'");

		licenseDataList = (List<LicenseIssuedDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseIssuedDetEntity.class).getResultList();

		return licenseDataList;
	}

	public List<LicenseDaysWiseDetEntity> getYearWiseLicenseData(long orgId) {
		List<LicenseDaysWiseDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				" SELECT @al\\:=@al+1 as num,CONCAT(DATE_FORMAT(FA_FROMDATE,'%Y'),'-',DATE_FORMAT(FA_TODATE,'%Y')) AS HDATE,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'O' THEN A.TRD_ID END) AS OTHER,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'HR' THEN A.TRD_ID END) AS Hotel_Restaurant\r\n"
						+ "FROM (SELECT TRD_ID, TRD_LICISDATE, ORGID FROM tb_ml_trade_mast) A ,\r\n"
						+ "(SELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR)   < FA_FROMDATE) B,\r\n"
						+ "(SELECT TRD_ID,TRI_COD1 FROM tb_ml_item_detail) C,\r\n"
						+ "(SELECT COD_ID, COD_DESC, COD_VALUE FROM tb_comparent_det) D ,(select @al\\:=0) as al\r\n"
						+ "WHERE (TRD_LICISDATE BETWEEN B.FA_FROMDATE AND B.FA_TODATE)\r\n");
		if (orgId != 0) {
			queryBuilder.append(" AND A.ORGID = (case when COALESCE('" + orgId
					+ "',0)=0 then COALESCE(A.orgid,0) else COALESCE('" + orgId + "',0) end)\r\n");
		}
		queryBuilder.append(" AND A.TRD_ID = C.TRD_ID\r\n" + "AND C.TRI_COD1 = D.COD_ID\r\n"
				+ "GROUP BY CONCAT(DATE_FORMAT(FA_FROMDATE,'%Y'),'-',DATE_FORMAT(FA_TODATE,'%Y')),DATE_FORMAT(FA_FROMDATE,'%Y')\r\n"
				+ "ORDER BY DATE_FORMAT(FA_FROMDATE,'%Y') DESC LIMIT 5\r\n");

		licenseDataList = (List<LicenseDaysWiseDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseDaysWiseDetEntity.class).getResultList();

		return licenseDataList;
	}

	public List<LicenseDaysWiseDetEntity> getHalfYearWiseLicenseData(long orgId) {
		// TODO Auto-generated method stub
		List<LicenseDaysWiseDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				" SELECT @ad\\:= @ad+1 num , CONCAT(DATE_FORMAT(FA_FROMDATE,'%b%Y'),'-',DATE_FORMAT(FA_TODATE,'%b%Y')) AS HDATE,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'O' THEN A.TRD_ID END) AS OTHER,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'HR' THEN A.TRD_ID END) AS Hotel_Restaurant\r\n"
						+ "FROM (SELECT TRD_ID, TRD_LICISDATE, ORGID FROM tb_ml_trade_mast) A ,\r\n"
						+ "(SELECT * FROM tb_financialhalfy WHERE CURDATE() > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR)   < FA_FROMDATE) B,\r\n"
						+ "(SELECT TRD_ID,TRI_COD1 FROM tb_ml_item_detail) C,\r\n"
						+ "(SELECT COD_ID, COD_DESC, COD_VALUE FROM tb_comparent_det) D,(select @ad\\:= 0) as ad\r\n");
		if (orgId != 0) {
			queryBuilder.append(" WHERE A.ORGID = (case when COALESCE('" + orgId
					+ "',0)=0 then COALESCE(A.orgid,0) else COALESCE('" + orgId + "',0) end)\r\n");
		}
		queryBuilder.append("AND (TRD_LICISDATE BETWEEN B.FA_FROMDATE AND B.FA_TODATE)\r\n"
				+ "AND A.TRD_ID = C.TRD_ID\r\n" + "AND C.TRI_COD1 = D.COD_ID\r\n"
				+ "GROUP BY CONCAT(DATE_FORMAT(FA_FROMDATE,'%b%Y'),'-',DATE_FORMAT(FA_TODATE,'%b%Y')),DATE_FORMAT(FA_FROMDATE,'%Y')\r\n"
				+ "ORDER BY DATE_FORMAT(FA_FROMDATE,'%Y') DESC LIMIT 2");

		licenseDataList = (List<LicenseDaysWiseDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseDaysWiseDetEntity.class).getResultList();
		return licenseDataList;
	}

	public List<LicenseDaysWiseDetEntity> getQuarterWiseLicenseData(long orgId) {
		List<LicenseDaysWiseDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				" SELECT @ad\\:= @ad+1 num ,CONCAT(DATE_FORMAT(FA_FROMDATE,'%b%Y'),'-',DATE_FORMAT(FA_TODATE,'%b%Y')) AS HDATE,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'O' THEN 1 END) OTHER,\r\n"
						+ "COUNT(CASE WHEN D.COD_VALUE = 'HR' THEN 1 END) Hotel_Restaurant\r\n"
						+ "FROM (SELECT * FROM tb_ml_trade_mast) A ,\r\n"
						+ "(SELECT * FROM TB_FINANCIALQUARTY WHERE CURDATE() > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR)   < FA_FROMDATE) B,\r\n"
						+ "(SELECT * FROM tb_ml_item_detail) C,\r\n"
						+ "(SELECT * FROM tb_comparent_det) D,(SELECT * FROM tb_cfc_application_mst) E,(SELECT * FROM tb_services_mst) F,(SELECT * FROM tb_department) G ,(select @ad\\:= 0) as ad WHERE (TRD_LICISDATE BETWEEN B.FA_FROMDATE AND B.FA_TODATE) \r\n");
		if (orgId != 0) {
			queryBuilder.append(" AND A.ORGID = (case when COALESCE('" + orgId
					+ "',0)=0 then COALESCE(A.orgid,0) else COALESCE('" + orgId + "',0) end)\r\n");
		}
		queryBuilder.append("AND A.TRD_ID = C.TRD_ID AND A.ORGID = C.ORGID\r\n" + "		AND C.TRI_COD1 = D.COD_ID\r\n"
				+ "		AND E.APM_APPLICATION_ID = A.APM_APPLICATION_ID AND E.ORGID = A.ORGID\r\n"
				+ "		AND F.SM_SERVICE_ID = E.SM_SERVICE_ID AND F.ORGID = E.ORGID\r\n"
				+ "		AND F.CDM_DEPT_ID = G.DP_DEPTID\r\n" + "		AND G.DP_DEPTCODE = 'ML'\r\n"
				+ "		GROUP BY CONCAT(DATE_FORMAT(FA_FROMDATE,'%b%Y'),'-',DATE_FORMAT(FA_TODATE,'%b%Y')),DATE_FORMAT(FA_FROMDATE,'%Y')\r\n"
				+ "		ORDER BY DATE_FORMAT(FA_FROMDATE,'%Y') DESC LIMIT 4");

		licenseDataList = (List<LicenseDaysWiseDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseDaysWiseDetEntity.class).getResultList();
		return licenseDataList;
	}

	public List<LicenseDaysWiseDetEntity> getsevenDayrWiseLicenseData(long orgId) {
		List<LicenseDaysWiseDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT @al\\:= @al+1 num , date_format(A.TRD_LICISDATE,'%d-%m-%Y') AS HDATE,\r\n"
				+ "COUNT(CASE WHEN C.COD_VALUE = 'O' THEN 1 END) OTHER,\r\n"
				+ "COUNT(CASE WHEN C.COD_VALUE = 'HR' THEN 1 END) Hotel_Restaurant\r\n"
				+ "FROM (SELECT * FROM tb_ml_trade_mast) A ,(SELECT * FROM tb_ml_item_detail) B,(SELECT * FROM tb_comparent_det) C,	(SELECT * FROM tb_cfc_application_mst) E,(SELECT * FROM tb_services_mst) F,(SELECT * FROM tb_department) G,(select @ad\\:= 0) as al\r\n"
				+ " WHERE A.TRD_LICISDATE between DATE_SUB(CURDATE(), INTERVAL 6 MONTH) and CURDATE()\r\n");
		if (orgId != 0) {
			queryBuilder.append(" AND A.ORGID = (case when COALESCE('" + orgId
					+ "',0)=0 then COALESCE(A.orgid,0) else COALESCE('" + orgId + "',0) end)\r\n");
		}
		queryBuilder.append("AND A.TRD_ID = B.TRD_ID AND A.ORGID = B.ORGID\r\n" + "	AND B.TRI_COD1 = C.COD_ID\r\n"
				+ "	AND E.APM_APPLICATION_ID = A.APM_APPLICATION_ID AND E.ORGID = A.ORGID\r\n"
				+ "	AND F.SM_SERVICE_ID = E.SM_SERVICE_ID AND F.ORGID = E.ORGID\r\n"
				+ "	AND F.CDM_DEPT_ID = G.DP_DEPTID\r\n" + "	AND G.DP_DEPTCODE = 'ML'\r\n"
				+ "	group by date_format(A.TRD_LICISDATE,'%d-%m-%Y'),date_format(A.TRD_LICISDATE,'%Y%m%d')\r\n"
				+ "	order by date_format(A.TRD_LICISDATE,'%Y%m%d') desc limit 7");

		licenseDataList = (List<LicenseDaysWiseDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseDaysWiseDetEntity.class).getResultList();
		return licenseDataList;

	}

	public List<LicenseIssuedDetEntity> getLicenceULBReport(long orgId) {
		List<LicenseIssuedDetEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT  @al\\:= @al+1 num ,COD_DESC AS LICENSE_CATEGORY,\r\n"
				+ "    IFNULL(UP_TO_LAST_MONTH_FEE_AMT, 0) AS UPTO_LAST_MONTH,\r\n"
				+ "    IFNULL(CURRENT_MONTH_FEE_AMT, 0) AS CURRENT_MONTH,\r\n"
				+ "    IFNULL(TILL_MONTH_FEE_AMT, 0) AS TILL_MONTH\r\n" + "    FROM tb_comparent_det D1\r\n"
				+ "    INNER JOIN tb_comparent_mas M1 ON M1.COM_ID = D1.COM_ID AND M1.COM_VALUE = 'C'\r\n"
				+ "    INNER JOIN tb_comparam_mas M2 ON M2.CPM_ID = M1.CPM_ID AND M2.CPM_PREFIX = 'ITC'\r\n"
				+ "    LEFT JOIN\r\n" + "    ( SELECT ID.TRI_COD1 AS LIC_CAT_CODE,\r\n"
				+ "    SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(CURRENT_DATE()), INTERVAL 1 DAY), INTERVAL - 1 MONTH) AND CURRENT_DATE())\r\n"
				+ "    AND (RD.CREATED_DATE BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(CURRENT_DATE()), INTERVAL 1 DAY), INTERVAL - 1 MONTH) AND CURRENT_DATE())\r\n"
				+ "    THEN RD.RF_FEEAMOUNT END) CURRENT_MONTH_FEE_AMT,\r\n" + "\r\n"
				+ "    SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (LAST_DAY(DATE_ADD(CURRENT_DATE(), INTERVAL - 1 MONTH))))\r\n"
				+ "    AND (RD.CREATED_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND (LAST_DAY(DATE_ADD(CURRENT_DATE(), INTERVAL - 1 MONTH))))\r\n"
				+ "    THEN RD.RF_FEEAMOUNT END) UP_TO_LAST_MONTH_FEE_AMT,\r\n" + "\r\n"
				+ "    SUM(CASE WHEN (TM.TRD_LICFROM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
				+ "    AND (RD.CREATED_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
				+ "    THEN RD.RF_FEEAMOUNT END) TILL_MONTH_FEE_AMT\r\n" + "\r\n" + "    FROM tb_ml_trade_mast TM\r\n"
				+ "    INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = TM.TRD_STATUS AND CPD_VALUE = 'I'\r\n"
				+ "    INNER JOIN tb_ml_item_detail ID ON ID.TRD_ID = TM.TRD_ID AND TM.ORGID = ID.ORGID\r\n"
				+ "    INNER JOIN tb_receipt_mas RM ON RM.APM_APPLICATION_ID = TM.APM_APPLICATION_ID AND RM.ORGID = TM.ORGID\r\n"
				+ "    INNER JOIN tb_receipt_det RD ON RD.RM_RCPTID = RM.RM_RCPTID AND RD.ORGID = RM.ORGID\r\n"
				+ "    INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = RM.DP_DEPTID AND DEPT.DP_DEPTCODE = 'ML' ,(select @al\\:= 0) as al ");
		if (orgId != 0) {
			queryBuilder.append("  WHERE TM.ORGID = '" + orgId + "'");
		}
		queryBuilder.append(" AND TM.TRD_LICNO IS NOT NULL\r\n"
				+ "    AND (TM.TRD_LICFROM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
				+ "    AND (RD.CREATED_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE())\r\n"
				+ "    GROUP BY LIC_CAT_CODE) A ON A.LIC_CAT_CODE = D1.COD_ID\r\n" + "    WHERE COD_STATUS = 'Y'");

		licenseDataList = (List<LicenseIssuedDetEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseIssuedDetEntity.class).getResultList();
		return licenseDataList;
	}

	public List<LicenseIssuedCntAndRevenueEntity> getTotalIssuedlicenseTotalrevenue(long orgId) {
		List<LicenseIssuedCntAndRevenueEntity> licenseDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT  @al\\:= @al+1 num ,COD_DESC AS LICENSE_CATEGORY,\r\n"
				+ "                COUNT(TRD_ID) AS LICENCE_ISSUED_CNT,        \r\n"
				+ "                SUM(IFNULL(TILL_MONTH_FEE_AMT, 0)) AS REVENUE\r\n"
				+ "                FROM tb_comparent_det D1\r\n"
				+ "                INNER JOIN tb_comparent_mas M1 ON M1.COM_ID = D1.COM_ID AND M1.COM_VALUE = 'C'\r\n"
				+ "                INNER JOIN tb_comparam_mas M2 ON M2.CPM_ID = M1.CPM_ID AND M2.CPM_PREFIX = 'ITC'\r\n"
				+ "                LEFT JOIN\r\n" + "                (\r\n"
				+ "                    SELECT ID.TRI_COD1 AS LIC_CAT_CODE, TM.TRD_ID AS TRD_ID, RM.TILL_MONTH_FEE_AMT AS TILL_MONTH_FEE_AMT\r\n"
				+ "                    FROM tb_ml_trade_mast TM\r\n"
				+ "                    INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = TM.TRD_STATUS AND CPD_VALUE = 'I'\r\n"
				+ "                    INNER JOIN tb_ml_item_detail ID ON ID.TRD_ID = TM.TRD_ID AND TM.ORGID = ID.ORGID\r\n"
				+ "                    LEFT JOIN\r\n" + "                    (            \r\n"
				+ "                        SELECT  RM.ADDITIONAL_REF_NO,  SUM(IFNULL(RD.RD_AMOUNT,0)) AS TILL_MONTH_FEE_AMT            \r\n"
				+ "                        FROM tb_receipt_mas RM\r\n"
				+ "                        INNER JOIN tb_receipt_mode RD ON RD.RM_RCPTID = RM.RM_RCPTID AND RD.ORGID = RM.ORGID\r\n"
				+ "                        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = RM.DP_DEPTID AND DEPT.DP_DEPTCODE = 'ML'\r\n"
				+ "                        WHERE RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE()\r\n"
				+ "                        AND RECEIPT_DEL_FLAG IS NULL AND RECEIPT_TYPE_FLAG <> 'RB'\r\n"
				+ "                        GROUP BY RM.ADDITIONAL_REF_NO\r\n"
				+ "                    ) RM ON RM.ADDITIONAL_REF_NO = TM.TRD_LICNO,(select @al\\:= 0) as al ");
		if (orgId != 0) {
			queryBuilder.append("  WHERE TM.ORGID = '" + orgId + "'");
		}
		queryBuilder.append(" AND TM.TRD_LICNO IS NOT NULL\r\n"
				+ "                    AND TM.TRD_LICFROM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE CURRENT_DATE() BETWEEN FA_FROMDATE AND FA_TODATE) AND CURRENT_DATE()\r\n"
				+ "                ) A ON A.LIC_CAT_CODE = D1.COD_ID\r\n" + "                WHERE COD_STATUS = 'Y'\r\n"
				+ "                GROUP BY COD_DESC");

		licenseDataList = (List<LicenseIssuedCntAndRevenueEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), LicenseIssuedCntAndRevenueEntity.class).getResultList();
		return licenseDataList;
	}
}
