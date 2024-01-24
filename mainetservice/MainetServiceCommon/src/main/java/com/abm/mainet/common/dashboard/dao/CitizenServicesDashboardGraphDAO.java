package com.abm.mainet.common.dashboard.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.FinancialDurationWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.ServiceStatusWiseCountEntity;

@Repository
public class CitizenServicesDashboardGraphDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	public List<ServiceStatusWiseCountEntity> getServiceStatusWiseCounts() {
		List<ServiceStatusWiseCountEntity> serviceStatusWiseCounts = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select (select O_NLS_ORGNAME from tb_organisation where orgid=a.orgid) as orgName,(select O_NLS_ORGNAME_MAR from tb_organisation where orgid=a.orgid) as orgName_Reg,\r\n"
				+ " a.orgid,sum(b.EXPIRED) as EXPIRED,sum(b.PENDING) as PENDING,sum(b.CLOSED) as CLOSED,sum(b.Received) as RECEIVED from tb_organisation a join \r\n"
				+ " (select a.* from (\r\n" + " SELECT  D.DP_DEPTDESC as Department, c.ORGID as orgid,\r\n"
				+ " C.STATUS,\r\n" + " case when status='EXPIRED' then 1 else 0 end as EXPIRED,\r\n"
				+ " case when status='PENDING' then 1 else 0 end as PENDING,\r\n"
				+ " case when status='CLOSED' then 1 else 0 end as CLOSED,\r\n" + " 1 as Received,\r\n" + " CASE\r\n"
				+ " WHEN (C.APPLICATION_SLA_DURATION >= (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN\r\n" + " 'W'\r\n"
				+ " WHEN (C.APPLICATION_SLA_DURATION < (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN\r\n" + " 'B'\r\n" + " else\r\n" + " 'N'\r\n"
				+ " END AS SLA\r\n"
				+ " FROM (SELECT A.APM_APPLICATION_ID,a.APM_LNAME,a.APM_FNAME,a.APM_MNAME,a.ORGID,\r\n"
				+ " A.SM_SERVICE_ID,\r\n" + " (B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n"
				+ " B.DATE_OF_REQUEST,\r\n" + " B.LAST_DATE_OF_ACTION,\r\n" + " case \r\n"
				+ " when B.LAST_DECISION not in\r\n" + " ('APPROVED', 'REJECTED') then\r\n" + " 'IN-PROCESS'\r\n"
				+ " else\r\n" + " B.LAST_DECISION\r\n" + " end as LAST_DECISION,\r\n" + " B.STATUS\r\n"
				+ " FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ " JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B\r\n"
				+ " ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID) C\r\n" + " LEFT JOIN (SELECT A.SM_SERVICE_ID,\r\n"
				+ " A.SM_SERVICE_NAME,\r\n" + " A.CPD_DESC        AS SERVICE_STATUS,\r\n" + " A.DP_DEPTDESC,\r\n"
				+ " B.CPD_DESC        AS SM_SERV_DESC2\r\n" + " FROM (SELECT A.*, B.DP_DEPTDESC\r\n"
				+ " FROM (SELECT SM_SERVICE_ID,\r\n" + " SM_SERVICE_NAME,\r\n" + " CPD_DESC,\r\n" + " SM_SERV_TYPE,\r\n"
				+ " CDM_DEPT_ID\r\n" + " FROM TB_SERVICES_MST A,\r\n" + " TB_COMPARAM_DET B\r\n"
				+ " WHERE SM_SERV_ACTIVE = CPD_ID) A\r\n" + " LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC\r\n"
				+ " FROM TB_DEPARTMENT) B\r\n" + " ON CDM_DEPT_ID = DP_DEPTID) A\r\n"
				+ " LEFT JOIN (SELECT CPD_ID, CPD_DESC\r\n" + " FROM TB_COMPARAM_DET) B\r\n"
				+ " ON A.SM_SERV_TYPE = B.CPD_ID) D\r\n" + " ON C.SM_SERVICE_ID = D.SM_SERVICE_ID) a\r\n"
				+ " where upper(a.Department)<>'CFC') b on a.orgid=b.orgid\r\n"
				+ " where a.orgid=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " a.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end) \r\n"
				+ " group by a.orgid\r\n" + " ) as t, (SELECT @row_number\\:=0) AS rn");

		serviceStatusWiseCounts = (List<ServiceStatusWiseCountEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ServiceStatusWiseCountEntity.class).getResultList();

		return serviceStatusWiseCounts;
	}

	public List<DeptOrSLAWiseServiceStatusEntity> getSLAWiseServiceStatus(int noOfDays) {
		List<DeptOrSLAWiseServiceStatusEntity> serviceStatusList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select SLA as DEPT_OR_SLA,\r\n" + "	SUM(PENDING) AS PENDING,\r\n" + "	SUM(CLOSED) AS CLOSED,\r\n"
				+ "	SUM(EXPIRED) AS EXPIRED,\r\n" + "	SUM(Received) AS RECEIVED\r\n" + "\r\n"
				+ "	from tb_organisation a\r\n" + "	join\r\n" + "	(select a.*\r\n"
				+ "	from (SELECT D.DP_DEPTDESC as Department,\r\n" + "	c.ORGID as orgid,\r\n" + "	C.STATUS,\r\n"
				+ "    C.DATE_OF_REQUEST,\r\n" + "	CASE WHEN status='PENDING' THEN 1 ELSE 0 END AS PENDING,\r\n"
				+ "	CASE WHEN status='CLOSED' THEN 1 ELSE 0 END AS CLOSED,\r\n"
				+ "	CASE WHEN status='EXPIRED' THEN 1 ELSE 0 END AS EXPIRED,\r\n" + "\r\n"
				+ "	case when status='PENDING' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > current_date() then 'B'\r\n"
				+ "	when status='PENDING' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= current_date() then 'W'\r\n"
				+ "	when status='CLOSED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > LAST_DATE_OF_ACTION then 'B'\r\n"
				+ "	when status='CLOSED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= LAST_DATE_OF_ACTION then 'W'\r\n"
				+ "	when status='EXPIRED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > LAST_DATE_OF_ACTION then 'B'\r\n"
				+ "	when status='EXPIRED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= LAST_DATE_OF_ACTION then 'W'\r\n"
				+ "	ELSE 'N'\r\n" + "	END AS SLA,\r\n" + "	1 as Received\r\n" + "\r\n"
				+ "	FROM (SELECT A.APM_APPLICATION_ID,\r\n" + "	a.APM_LNAME,\r\n" + "	a.APM_FNAME,\r\n"
				+ "	a.APM_MNAME,\r\n" + "	a.ORGID,\r\n" + "	A.SM_SERVICE_ID,\r\n"
				+ "	(B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n" + "	B.DATE_OF_REQUEST,\r\n"
				+ "	B.LAST_DATE_OF_ACTION,\r\n"
				+ "	case when B.LAST_DECISION not in ('APPROVED', 'REJECTED') then 'IN-PROCESS' else B.LAST_DECISION end as LAST_DECISION,\r\n"
				+ "	B.STATUS,\r\n" + "	SM.SM_SERDUR,\r\n" + "	D1.CPD_DESC,\r\n" + "	D1.CPD_VALUE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'H' THEN SM.SM_SERDUR / 24\r\n"
				+ "	WHEN D1.CPD_VALUE = 'Y' THEN SM.SM_SERDUR * 365\r\n" + "	ELSE SM.SM_SERDUR\r\n"
				+ "	END AS DURATION_IN_DAYS,\r\n" + "	B.LAST_DECISION AS LAST_DECISION1,\r\n"
				+ "	A.APM_APPLICATION_DATE\r\n" + "\r\n" + "	FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ "	JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID\r\n"
				+ "	LEFT JOIN (SELECT SM_SERVICE_ID, SM_SERDUR, SM_SERDURUNI from tb_services_mst) SM ON SM.SM_SERVICE_ID = A.SM_SERVICE_ID\r\n"
				+ "	LEFT JOIN tb_comparam_det D1 ON D1.CPD_ID = SM.SM_SERDURUNI\r\n" + "	) C\r\n"
				+ "	LEFT JOIN (SELECT A.SM_SERVICE_ID,\r\n" + "	A.SM_SERVICE_NAME,\r\n" + "\r\n"
				+ "	A.CPD_DESC AS SERVICE_STATUS,\r\n" + "	A.DP_DEPTDESC,\r\n" + "	B.CPD_DESC AS SM_SERV_DESC2\r\n"
				+ "\r\n" + "	FROM (SELECT A.*,\r\n" + "	B.DP_DEPTDESC\r\n" + "	FROM (SELECT SM_SERVICE_ID,\r\n"
				+ "	SM_SERVICE_NAME,\r\n" + "	CPD_DESC,\r\n" + "	SM_SERV_TYPE,\r\n" + "	CDM_DEPT_ID\r\n" + "\r\n"
				+ "	FROM TB_SERVICES_MST A,\r\n" + "	TB_COMPARAM_DET B\r\n" + "	WHERE SM_SERV_ACTIVE = CPD_ID\r\n"
				+ "	) A\r\n"
				+ "	LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC FROM TB_DEPARTMENT) B ON CDM_DEPT_ID = DP_DEPTID\r\n"
				+ "	) A\r\n"
				+ "	LEFT JOIN (SELECT CPD_ID, CPD_DESC FROM TB_COMPARAM_DET) B ON A.SM_SERV_TYPE = B.CPD_ID\r\n"
				+ "	) D ON C.SM_SERVICE_ID = D.SM_SERVICE_ID\r\n" + "	) a where upper(a.Department)<>'CFC'\r\n"
				+ "	) b on a.orgid=b.orgid\r\n"
				+ "	where a.orgid=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end)\r\n");
		if (noOfDays != 0)
			queryBuilder.append(
					"and b.DATE_OF_REQUEST between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		queryBuilder.append("group by SLA\r\n" + "    ) as t, (SELECT @row_number\\:=0) AS rn");
		serviceStatusList = (List<DeptOrSLAWiseServiceStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptOrSLAWiseServiceStatusEntity.class).getResultList();

		return serviceStatusList;
	}

	public List<DeptOrSLAWiseServiceStatusEntity> getDeptWiseServiceStatus(int noOfDays) {
		List<DeptOrSLAWiseServiceStatusEntity> serviceStatusList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select b.Department as DEPT_OR_SLA,sum(b.EXPIRED) as EXPIRED,sum(b.PENDING) as PENDING,sum(b.CLOSED) as CLOSED,sum(b.RECEIVED) as RECEIVED from tb_organisation a join \r\n"
				+ " (select a.* from (\r\n" + " SELECT  D.DP_DEPTDESC as Department, c.ORGID as orgid,\r\n"
				+ " C.STATUS,\r\n" + " C.DATE_OF_REQUEST,\r\n"
				+ " case when status='EXPIRED' then 1 else 0 end as EXPIRED,\r\n"
				+ " case when status='PENDING' then 1 else 0 end as PENDING,\r\n"
				+ " case when status='CLOSED' then 1 else 0 end as CLOSED,\r\n" + " 1 as Received,\r\n"
				+ " CASE WHEN (C.APPLICATION_SLA_DURATION >= (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN 'W'\r\n"
				+ " WHEN (C.APPLICATION_SLA_DURATION < (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN 'B'\r\n" + " else 'N'\r\n" + " END AS SLA\r\n"
				+ " FROM (SELECT A.APM_APPLICATION_ID,a.APM_LNAME,a.APM_FNAME,a.APM_MNAME,a.ORGID,\r\n"
				+ " A.SM_SERVICE_ID,\r\n" + " (B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n"
				+ " B.DATE_OF_REQUEST,\r\n" + " B.LAST_DATE_OF_ACTION,\r\n"
				+ " case when B.LAST_DECISION not in ('APPROVED', 'REJECTED') then 'IN-PROCESS'\r\n"
				+ " else B.LAST_DECISION\r\n" + " end as LAST_DECISION,\r\n" + " B.STATUS\r\n"
				+ " FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ " JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B\r\n"
				+ " ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID) C\r\n" + " LEFT JOIN (SELECT A.SM_SERVICE_ID,\r\n"
				+ " A.SM_SERVICE_NAME,\r\n" + " A.CPD_DESC AS SERVICE_STATUS,\r\n" + " A.DP_DEPTDESC,\r\n"
				+ " B.CPD_DESC AS SM_SERV_DESC2\r\n" + " FROM (SELECT A.*, B.DP_DEPTDESC\r\n"
				+ " FROM (SELECT SM_SERVICE_ID,\r\n" + " SM_SERVICE_NAME, CPD_DESC, SM_SERV_TYPE, CDM_DEPT_ID\r\n"
				+ " FROM TB_SERVICES_MST A,\r\n" + " TB_COMPARAM_DET B\r\n" + " WHERE SM_SERV_ACTIVE = CPD_ID) A\r\n"
				+ " LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC\r\n" + " FROM TB_DEPARTMENT) B\r\n"
				+ " ON CDM_DEPT_ID = DP_DEPTID) A\r\n" + " LEFT JOIN (SELECT CPD_ID, CPD_DESC\r\n"
				+ " FROM TB_COMPARAM_DET) B\r\n" + " ON A.SM_SERV_TYPE = B.CPD_ID) D\r\n"
				+ " ON C.SM_SERVICE_ID = D.SM_SERVICE_ID) a\r\n"
				+ " where upper(a.Department)<>'CFC') b on a.orgid=b.orgid\r\n"
				+ " where a.orgid=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " a.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end)\r\n");
		if (noOfDays != 0)
			queryBuilder.append(
					"and b.DATE_OF_REQUEST between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		queryBuilder.append("group by b.Department\r\n" + "    ) as t, (SELECT @row_number\\:=0) AS rn");

		serviceStatusList = (List<DeptOrSLAWiseServiceStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptOrSLAWiseServiceStatusEntity.class).getResultList();

		return serviceStatusList;
	}

	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndSLA(int noOfDays, String sla) {
		List<DeptOrSLAWiseDataEntity> serviceDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select SLA as DEPT_OR_SLA,\r\n"
				+ "	b.APM_APPLICATION_ID as APPLICATION_NO, b.APPLICANT_NAME, b.DATE_OF_REQUEST as APPLICATION_DATE, b.status as STATUS, b.Department,\r\n"
				+ "    (select SM_SERVICE_NAME from tb_services_mst where SM_SERVICE_ID = b.SM_SERVICE_ID) as SERVICE_NAME\r\n"
				+ "	from tb_organisation a\r\n" + "	join\r\n" + "	(select a.*\r\n"
				+ "	from (SELECT c.APM_APPLICATION_ID, c.APM_FNAME as APPLICANT_NAME, c.DATE_OF_REQUEST, c.SM_SERVICE_ID,\r\n"
				+ "    D.DP_DEPTDESC as Department,\r\n" + "	c.ORGID as orgid,\r\n" + "	C.STATUS,\r\n"
				+ "	CASE WHEN status='PENDING' THEN 1 ELSE 0 END AS PENDING,\r\n"
				+ "	CASE WHEN status='CLOSED' THEN 1 ELSE 0 END AS CLOSED,\r\n"
				+ "	CASE WHEN status='EXPIRED' THEN 1 ELSE 0 END AS EXPIRED,\r\n" + "\r\n"
				+ "	case when status='PENDING' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > current_date() then 'B'\r\n"
				+ "	when status='PENDING' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= current_date() then 'W'\r\n"
				+ "	when status='CLOSED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > LAST_DATE_OF_ACTION then 'B'\r\n"
				+ "	when status='CLOSED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= LAST_DATE_OF_ACTION then 'W'\r\n"
				+ "	when status='EXPIRED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) > LAST_DATE_OF_ACTION then 'B'\r\n"
				+ "	when status='EXPIRED' AND DATE_ADD(APM_APPLICATION_DATE, INTERVAL IFNULL(DURATION_IN_DAYS,7) DAY) <= LAST_DATE_OF_ACTION then 'W'\r\n"
				+ "	ELSE 'N'\r\n" + "	END AS SLA,\r\n" + "	1 as Received\r\n" + "\r\n"
				+ "	FROM (SELECT A.APM_APPLICATION_ID,\r\n" + "	a.APM_LNAME,\r\n" + "	a.APM_FNAME,\r\n"
				+ "	a.APM_MNAME,\r\n" + "	a.ORGID,\r\n" + "	A.SM_SERVICE_ID,\r\n"
				+ "	(B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n" + "	B.DATE_OF_REQUEST,\r\n"
				+ "	B.LAST_DATE_OF_ACTION,\r\n"
				+ "	case when B.LAST_DECISION not in ('APPROVED', 'REJECTED') then 'IN-PROCESS' else B.LAST_DECISION end as LAST_DECISION,\r\n"
				+ "	B.STATUS,\r\n" + "	SM.SM_SERDUR,\r\n" + "	D1.CPD_DESC,\r\n" + "	D1.CPD_VALUE,\r\n"
				+ "	CASE WHEN D1.CPD_VALUE = 'H' THEN SM.SM_SERDUR / 24\r\n"
				+ "	WHEN D1.CPD_VALUE = 'Y' THEN SM.SM_SERDUR * 365\r\n" + "	ELSE SM.SM_SERDUR\r\n"
				+ "	END AS DURATION_IN_DAYS,\r\n" + "	B.LAST_DECISION AS LAST_DECISION1,\r\n"
				+ "	A.APM_APPLICATION_DATE\r\n" + "\r\n" + "	FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ "	JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID\r\n"
				+ "	LEFT JOIN (SELECT SM_SERVICE_ID, SM_SERDUR, SM_SERDURUNI from tb_services_mst) SM ON SM.SM_SERVICE_ID = A.SM_SERVICE_ID\r\n"
				+ "	LEFT JOIN tb_comparam_det D1 ON D1.CPD_ID = SM.SM_SERDURUNI\r\n" + "	) C\r\n" + "    \r\n"
				+ "	LEFT JOIN (SELECT A.SM_SERVICE_ID,\r\n" + "	A.SM_SERVICE_NAME,\r\n" + "\r\n"
				+ "	A.CPD_DESC AS SERVICE_STATUS,\r\n" + "	A.DP_DEPTDESC,\r\n" + "	B.CPD_DESC AS SM_SERV_DESC2\r\n"
				+ "\r\n" + "	FROM (SELECT A.*,\r\n" + "	B.DP_DEPTDESC\r\n" + "	FROM (SELECT SM_SERVICE_ID,\r\n"
				+ "	SM_SERVICE_NAME,\r\n" + "	CPD_DESC,\r\n" + "	SM_SERV_TYPE,\r\n" + "	CDM_DEPT_ID\r\n" + "\r\n"
				+ "	FROM TB_SERVICES_MST A,\r\n" + "	TB_COMPARAM_DET B\r\n" + "	WHERE SM_SERV_ACTIVE = CPD_ID\r\n"
				+ "	) A\r\n"
				+ "	LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC FROM TB_DEPARTMENT) B ON CDM_DEPT_ID = DP_DEPTID\r\n"
				+ "	) A\r\n"
				+ "	LEFT JOIN (SELECT CPD_ID, CPD_DESC FROM TB_COMPARAM_DET) B ON A.SM_SERV_TYPE = B.CPD_ID\r\n"
				+ "	) D ON C.SM_SERVICE_ID = D.SM_SERVICE_ID\r\n" + "	) a where upper(a.Department)<>'CFC'\r\n"
				+ "	) b on a.orgid=b.orgid\r\n"
				+ "	where a.orgid=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end)\r\n"
				+ "	and a.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end)\r\n");
		if (noOfDays != 0) {
			queryBuilder.append(
					"and b.DATE_OF_REQUEST between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		}
		if (sla != null) {
			queryBuilder.append("and SLA = '" + sla + "'\r\n");
		}
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		serviceDataList = (List<DeptOrSLAWiseDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptOrSLAWiseDataEntity.class).getResultList();

		return serviceDataList;
	}

	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndDept(int noOfDays, String dept) {
		List<DeptOrSLAWiseDataEntity> serviceDataList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ "select b.Department as DEPT_OR_SLA,\r\n"
				+ " b.APM_APPLICATION_ID as APPLICATION_NO, b.APPLICANT_NAME, b.DATE_OF_REQUEST as APPLICATION_DATE, b.status as STATUS, b.Department,\r\n"
				+ "(select SM_SERVICE_NAME from tb_services_mst where SM_SERVICE_ID = b.SM_SERVICE_ID) as SERVICE_NAME\r\n"
				+ " from tb_organisation a join \r\n" + " (select a.* from (\r\n"
				+ " SELECT c.APM_APPLICATION_ID, c.APM_FNAME as APPLICANT_NAME, c.DATE_OF_REQUEST, c.SM_SERVICE_ID,\r\n"
				+ " D.DP_DEPTDESC as Department, c.ORGID as orgid,\r\n" + " C.STATUS,\r\n"
				+ " case when status='EXPIRED' then 1 else 0 end as EXPIRED,\r\n"
				+ " case when status='PENDING' then 1 else 0 end as PENDING,\r\n"
				+ " case when status='CLOSED' then 1 else 0 end as CLOSED,\r\n" + " 1 as Received,\r\n"
				+ " CASE WHEN (C.APPLICATION_SLA_DURATION >= (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN 'W'\r\n"
				+ " WHEN (C.APPLICATION_SLA_DURATION < (CASE\r\n" + " WHEN (STATUS = 'PENDING') THEN\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(SYSDATE(), C.DATE_OF_REQUEST))\r\n" + " ELSE\r\n"
				+ " TIME_TO_SEC(TIMEDIFF(C.LAST_DATE_OF_ACTION,\r\n" + " C.DATE_OF_REQUEST))\r\n"
				+ " END)) AND C.APPLICATION_SLA_DURATION > 0 THEN 'B'\r\n" + " else 'N'\r\n" + " END AS SLA\r\n"
				+ " FROM (SELECT A.APM_APPLICATION_ID,a.APM_LNAME,a.APM_FNAME,a.APM_MNAME,a.ORGID,\r\n"
				+ " A.SM_SERVICE_ID,\r\n" + " (B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n"
				+ " B.DATE_OF_REQUEST,\r\n" + " B.LAST_DATE_OF_ACTION,\r\n"
				+ " case when B.LAST_DECISION not in ('APPROVED', 'REJECTED') then 'IN-PROCESS'\r\n"
				+ " else B.LAST_DECISION\r\n" + " end as LAST_DECISION,\r\n" + " B.STATUS\r\n"
				+ " FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ " JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B\r\n"
				+ " ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID) C\r\n" + " LEFT JOIN (SELECT A.SM_SERVICE_ID,\r\n"
				+ " A.SM_SERVICE_NAME,\r\n" + " A.CPD_DESC AS SERVICE_STATUS,\r\n" + " A.DP_DEPTDESC,\r\n"
				+ " B.CPD_DESC AS SM_SERV_DESC2\r\n" + " FROM (SELECT A.*, B.DP_DEPTDESC\r\n"
				+ " FROM (SELECT SM_SERVICE_ID,\r\n" + " SM_SERVICE_NAME, CPD_DESC, SM_SERV_TYPE, CDM_DEPT_ID\r\n"
				+ " FROM TB_SERVICES_MST A,\r\n" + " TB_COMPARAM_DET B\r\n" + " WHERE SM_SERV_ACTIVE = CPD_ID) A\r\n"
				+ " LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC\r\n" + " FROM TB_DEPARTMENT) B\r\n"
				+ " ON CDM_DEPT_ID = DP_DEPTID) A\r\n" + " LEFT JOIN (SELECT CPD_ID, CPD_DESC\r\n"
				+ " FROM TB_COMPARAM_DET) B\r\n" + " ON A.SM_SERV_TYPE = B.CPD_ID) D\r\n"
				+ " ON C.SM_SERVICE_ID = D.SM_SERVICE_ID) a\r\n"
				+ " where upper(a.Department)<>'CFC') b on a.orgid=b.orgid\r\n"
				+ " where a.orgid=(case when COALESCE(@x,0)=0 then COALESCE(a.orgid,0) else COALESCE(@x,0) end)  and\r\n"
				+ " a.ORG_CPD_ID_STATE=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_STATE,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIV=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIV,0) else COALESCE(0,0) end) and\r\n"
				+ " a.ORG_CPD_ID_DIS=(case when COALESCE(0,0)=0 then COALESCE(a.ORG_CPD_ID_DIS,0) else COALESCE(0,0) end)\r\n");
		if (noOfDays != 0) {
			queryBuilder.append(
					"and b.DATE_OF_REQUEST between DATE_SUB(CURDATE(), INTERVAL " + noOfDays + " day) and now()\r\n");
		}
		if (dept != null) {
			queryBuilder.append("and b.Department = '" + dept + "'\r\n");
		}
		queryBuilder.append(") as t, (SELECT @row_number\\:=0) AS rn");
		serviceDataList = (List<DeptOrSLAWiseDataEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), DeptOrSLAWiseDataEntity.class).getResultList();

		return serviceDataList;
	}

	public List<FinancialDurationWiseServiceStatusEntity> getYearWiseServiceStatus() {
		List<FinancialDurationWiseServiceStatusEntity> serviceStatusList = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+ " select year(a.Date_of_Registration) as FIN_DURATION,\r\n" + "	sum(EXPIRED) as EXPIRED,\r\n"
				+ "	sum(PENDING) as PENDING,\r\n" + "	sum(CLOSED) as CLOSED,\r\n" + "	sum(Received) as RECEIVED \r\n"
				+ "	from (SELECT C.DATE_OF_REQUEST as Date_of_Registration,\r\n"
				+ "	D.DP_DEPTDESC as Department,\r\n" + "	c.ORGID,\r\n" + "	C.STATUS,\r\n"
				+ "	case when status='EXPIRED' then 1 else 0 end as EXPIRED,\r\n"
				+ "	case when status='PENDING' then 1 else 0 end as PENDING,\r\n"
				+ "	case when status='CLOSED' then 1 else 0 end as CLOSED,1 as Received\r\n"
				+ "	FROM (SELECT A.APM_APPLICATION_ID,\r\n" + "	a.APM_LNAME, a.APM_FNAME, a.APM_MNAME,\r\n"
				+ "	a.ORGID,\r\n" + "	A.SM_SERVICE_ID,\r\n"
				+ "	(B.APPLICATION_SLA_DURATION / 1000) AS APPLICATION_SLA_DURATION,\r\n" + "	B.DATE_OF_REQUEST,\r\n"
				+ "	B.LAST_DATE_OF_ACTION,\r\n"
				+ "	case when B.LAST_DECISION not in ('APPROVED', 'REJECTED') then 'IN-PROCESS' else B.LAST_DECISION end as LAST_DECISION,\r\n"
				+ "	B.STATUS  \r\n" + "	FROM (SELECT * FROM TB_CFC_APPLICATION_MST) A\r\n"
				+ "	JOIN (SELECT * FROM TB_WORKFLOW_REQUEST) B ON A.APM_APPLICATION_ID = B.APM_APPLICATION_ID) C\r\n"
				+ "	LEFT JOIN (SELECT A.SM_SERVICE_ID, \r\n" + "	A.SM_SERVICE_NAME,\r\n"
				+ "	A.CPD_DESC AS SERVICE_STATUS,\r\n" + "	A.DP_DEPTDESC, \r\n"
				+ "	B.CPD_DESC AS SM_SERV_DESC2 \r\n" + "	FROM (SELECT A.*, B.DP_DEPTDESC\r\n"
				+ "	FROM (SELECT SM_SERVICE_ID, \r\n" + "	SM_SERVICE_NAME, \r\n" + "	CPD_DESC,\r\n"
				+ "	SM_SERV_TYPE,\r\n" + "	CDM_DEPT_ID\r\n" + "	FROM TB_SERVICES_MST A,\r\n"
				+ "	TB_COMPARAM_DET B \r\n" + "	WHERE SM_SERV_ACTIVE = CPD_ID) A\r\n"
				+ "	LEFT JOIN (SELECT DP_DEPTID, DP_DEPTDESC FROM TB_DEPARTMENT) B  ON CDM_DEPT_ID = DP_DEPTID) A\r\n"
				+ "	LEFT JOIN (SELECT CPD_ID, CPD_DESC FROM TB_COMPARAM_DET) B ON A.SM_SERV_TYPE = B.CPD_ID) D ON C.SM_SERVICE_ID = D.SM_SERVICE_ID) a,\r\n"
				+ "	(SELECT * FROM TB_FINANCIALYEAR WHERE CURDATE() > FA_FROMDATE AND DATE_SUB(CURDATE(), INTERVAL 5 YEAR) < FA_FROMDATE) b\r\n"
				+ "	WHERE Date_of_Registration BETWEEN FA_FROMDATE AND FA_TODATE \r\n" + "	AND A.ORGID = 3\r\n"
				+ "	and upper(a.Department)<>'CFC'\r\n" + "    group by year(a.Date_of_Registration)\r\n"
				+ "	) as t, (SELECT @row_number\\:=0) AS rn");

		serviceStatusList = (List<FinancialDurationWiseServiceStatusEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), FinancialDurationWiseServiceStatusEntity.class)
				.getResultList();

		return serviceStatusList;
	}
	
	public Date yesterdayDateStartTime() {
		 Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, -1);
	       cal.set(Calendar.HOUR_OF_DAY, 0);  
	       cal.set(Calendar.MINUTE, 0);  
	       cal.set(Calendar.SECOND, 0);  
	       cal.set(Calendar.MILLISECOND, 0); 
	       return cal.getTime();
	}
	public Date yesterdayDateEndTime() {
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.DATE, -1);
		 cal.set(Calendar.HOUR_OF_DAY, 23);  
		 cal.set(Calendar.MINUTE,59);  
		 cal.set(Calendar.SECOND, 59);  
		 cal.set(Calendar.MILLISECOND,59); 	       
	       return cal.getTime();
	}

   public Long getPriviousDayGrievances() {
		String hql = "select count(*) from tb_workflow_request w where (w.date_of_request between :fromDate and :toDate) and w.process_name = 'care'";

        final Query query = entityManager.createNativeQuery(hql.toString());
        query.setParameter("fromDate", yesterdayDateStartTime());
       // query.setParameter("toDate",yesterdayDateEndTime());
        query.setParameter("toDate",yesterdayDateEndTime());
        Object singleResult = query.getSingleResult();      
        Long count  =new Long(singleResult.toString());
		return count;
	}

	public Long getPriviousDayReslovedGrievances() {
		String hql ="select count(*)  from tb_workflow_request w where (w.last_date_of_action between  :fromDate and :toDate) and status ='CLOSED' and w.process_name = 'care'";
		final Query query = entityManager.createNativeQuery(hql.toString());
        query.setParameter("fromDate", yesterdayDateStartTime());
        // query.setParameter("toDate",yesterdayDateEndTime());
        query.setParameter("toDate",yesterdayDateEndTime()); 
        Object singleResult = query.getSingleResult();      
        Long count  =new Long(singleResult.toString());
		return count;
	}
	
	public Long getOpenRtiComplaints() {
		String hql ="select count(*) from tb_workflow_request where PROCESS_NAME = 'rti' and status ='PENDING'";
		final Query query = entityManager.createNativeQuery(hql.toString());
        Object singleResult = query.getSingleResult();      
        Long count  =new Long(singleResult.toString());
		return count;
	}

}
