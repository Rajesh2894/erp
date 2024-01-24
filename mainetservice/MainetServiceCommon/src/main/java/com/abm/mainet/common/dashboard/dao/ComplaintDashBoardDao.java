package com.abm.mainet.common.dashboard.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dashboard.domain.CommonComplaintEntity;
import com.abm.mainet.common.dashboard.domain.ComplaintEntity;
@Repository
public class ComplaintDashBoardDao {
	private static final Logger log = LoggerFactory.getLogger(ComplaintDashBoardDao.class);

	@PersistenceContext
	protected EntityManager entityManager;

	public List<ComplaintEntity> getTotComplaintAndStatus() {

		List<ComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select @row_number\\:=@row_number+1 AS num, a.* from\r\n"
				+ "(select Department,Zone,Ward,category,sub_category,\r\n" + "sum(coalesce(b.PENDING,0)) PENDING,\r\n"
				+ "sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + "sum(coalesce(b.REJECTED,0)) REJECTED,\r\n"
				+ "sum(coalesce(b.HOLD,0)) HOLD,\r\n"
				+ "(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n"
				+ "from\r\n" + "(SELECT\r\n"
				+ "(select DP.DP_DEPTDESC from tb_department DP where DP.DP_DEPTID =b.DEPT_COMP_ID) Department,\r\n"
				+ "(select COD_DESC from tb_comparent_det where cod_id=b.CARE_WARD_NO) Zone,\r\n"
				+ "(select COD_DESC from tb_comparent_det where cod_id=b.CARE_WARD_NO1) Ward,\r\n"
				+ "(select CPD_DESC from tb_comparam_det where CPD_ID=B.REFERENCE_MODE) category,\r\n"
				+ "(select CPD_DESC from tb_comparam_det where CPD_ID=B.REFERENCE_CATEGORY) sub_category,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n"
				+ "FROM\r\n" + "TB_CARE_REQUEST B ,\r\n" + "TB_WORKFLOW_REQUEST a ,\r\n"
				+ "tb_cfc_application_mst c\r\n" + "WHERE\r\n" + "B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n"
				+ "B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n"
				+ "AND B.CARE_WARD_NO is not null\r\n" + "-- AND B.DEPT_COMP_ID =\r\n"
				+ "GROUP BY A.STATUS,a.LAST_DECISION,Department,Zone,Ward,category,sub_category)B\r\n"
				+ "GROUP BY Department,Zone,Ward,category,sub_category) a, (SELECT @row_number\\:=0) as R");
		commonComplaintEntity = (List<ComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), ComplaintEntity.class).getResultList();
		return commonComplaintEntity;

	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByCategory(String category) {

		List<CommonComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select @row_number\\:=@row_number+1 AS num, a.* from\r\n" + "(select Name,\r\n"
				+ "sum(coalesce(b.PENDING,0)) PENDING,\r\n" + "sum(coalesce(b.CLOSED,0)) CLOSED,\r\n"
				+ "sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + "sum(coalesce(b.HOLD,0)) HOLD,\r\n"
				+ "(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n"
				+ "from\r\n" + "(SELECT\r\n"
				+ "(select CPD_DESC from tb_comparam_det where CPD_ID=B.REFERENCE_MODE) Name,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n"
				+ "(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n"
				+ "FROM\r\n" + "TB_CARE_REQUEST B ,\r\n" + "TB_WORKFLOW_REQUEST a ,\r\n"
				+ "tb_cfc_application_mst c\r\n" + "WHERE\r\n" + "B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n"
				+ "B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n"
				+ "AND B.CARE_WARD_NO is not null\r\n" + " AND B.REFERENCE_MODE="+ category+"\r\n"
				+ "GROUP BY A.STATUS,a.LAST_DECISION,Name)B\r\n" 
				+ "GROUP BY Name) a, (SELECT @row_number\\:=0) as R");
		commonComplaintEntity = (List<CommonComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CommonComplaintEntity.class).getResultList();
		return commonComplaintEntity;

	}

	public List<CommonComplaintEntity> getgetTotComplaintAndStatusByWard(String ward) {

		List<CommonComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder("select @row_number\\:=@row_number+1 AS num, a.* from\r\n" + 
				"(select Name,\r\n" + 
				"sum(coalesce(b.PENDING,0)) PENDING,\r\n" + 
				"sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + 
				"sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + 
				"sum(coalesce(b.HOLD,0)) HOLD,\r\n" + 
				"(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n" + 
				"from\r\n" + 
				"(SELECT\r\n" + 
				"(select COD_DESC from tb_comparent_det where cod_id=b.CARE_WARD_NO1) Name,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n" + 
				"FROM\r\n" + 
				"TB_CARE_REQUEST B ,\r\n" + 
				"TB_WORKFLOW_REQUEST a ,\r\n" + 
				"tb_cfc_application_mst c\r\n" + 
				"WHERE\r\n" + 
				"B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n" + 
				"B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n" + 
				"AND B.CARE_WARD_NO1 ="+ward+"\r\n" + 
				"GROUP BY A.STATUS,a.LAST_DECISION,Name)B\r\n" + 
				"GROUP BY Name ) a, (SELECT @row_number\\:=0) as R");
		queryBuilder.append("");
		commonComplaintEntity = (List<CommonComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CommonComplaintEntity.class).getResultList();
		return commonComplaintEntity;

	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByZone(String zone) {
		List<CommonComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select @row_number\\:=@row_number+1 AS num, a.* from\r\n" + 
				"(select Name,\r\n" + 
				"sum(coalesce(b.PENDING,0)) PENDING,\r\n" + 
				"sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + 
				"sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + 
				"sum(coalesce(b.HOLD,0)) HOLD,\r\n" + 
				"(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n" + 
				"from\r\n" + 
				"(SELECT\r\n" + 
				"(select COD_DESC from tb_comparent_det where cod_id=b.CARE_WARD_NO) Name,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n" + 
				"FROM\r\n" + 
				"TB_CARE_REQUEST B ,\r\n" + 
				"TB_WORKFLOW_REQUEST a ,\r\n" + 
				"tb_cfc_application_mst c\r\n" + 
				"WHERE\r\n" + 
				"B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n" + 
				"B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n" + 
				"AND B.CARE_WARD_NO ="+zone+"\r\n" + 
				"GROUP BY A.STATUS,a.LAST_DECISION,Name)B\r\n" + 
				"GROUP BY Name ) a, (select @row_number\\:=0) as R");
		commonComplaintEntity = (List<CommonComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CommonComplaintEntity.class).getResultList();
		return commonComplaintEntity;
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusByDept(String deptId) {
		List<CommonComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select @row_number\\:=@row_number+1 AS num, a.* from\r\n" + 
				"(select Name,\r\n" + 
				"sum(coalesce(b.PENDING,0)) PENDING,\r\n" + 
				"sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + 
				"sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + 
				"sum(coalesce(b.HOLD,0)) HOLD,\r\n" + 
				"(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n" + 
				"from\r\n" + 
				"(SELECT\r\n" + 
				"(select DP.DP_DEPTDESC from tb_department DP where DP.DP_DEPTID =b.DEPT_COMP_ID) Name,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n" + 
				"FROM\r\n" + 
				"TB_CARE_REQUEST B ,\r\n" + 
				"TB_WORKFLOW_REQUEST a ,\r\n" + 
				"tb_cfc_application_mst c\r\n" + 
				"WHERE\r\n" + 
				"B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n" + 
				"B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n" + 
				"AND B.CARE_WARD_NO is not null\r\n" + 
				"AND B.DEPT_COMP_ID ="+deptId+"\r\n" + 
				"GROUP BY A.STATUS,a.LAST_DECISION,Name)B\r\n" + 
				"GROUP BY Name ) a, (SELECT @row_number\\:=0) as R");
		commonComplaintEntity = (List<CommonComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CommonComplaintEntity.class).getResultList();
		return commonComplaintEntity;
	}

	public List<CommonComplaintEntity> getTotComplaintAndStatusBySubCategory(String subCategory) {
		List<CommonComplaintEntity> commonComplaintEntity = null;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select @row_number\\:=@row_number+1 AS num, a.* from\r\n" + 
				"(select Name,\r\n" + 
				"sum(coalesce(b.PENDING,0)) PENDING,\r\n" + 
				"sum(coalesce(b.CLOSED,0)) CLOSED,\r\n" + 
				"sum(coalesce(b.REJECTED,0)) REJECTED,\r\n" + 
				"sum(coalesce(b.HOLD,0)) HOLD,\r\n" + 
				"(SUM(COALESCE(B.CLOSED,0))+SUM(COALESCE(B.PENDING,0))+SUM(COALESCE(B.REJECTED,0))+SUM(COALESCE(B.HOLD,0))) AS TOtalRecieved\r\n" + 
				"from\r\n" + 
				"(SELECT\r\n" + 
				"(select CPD_DESC from tb_comparam_det where CPD_ID=B.REFERENCE_CATEGORY) Name,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION = 'REJECTED')) THEN COUNT(1) END) AS REJECTED ,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (A.LAST_DECISION <> 'REJECTED')) THEN COUNT(1) END) AS CLOSED,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION = 'HOLD')) THEN COUNT(1) END) AS HOLD,\r\n" + 
				"(CASE WHEN ((REPLACE(A.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (A.LAST_DECISION <> 'HOLD')) THEN COUNT(1) END)AS PENDING\r\n" + 
				"FROM\r\n" + 
				"TB_CARE_REQUEST B ,\r\n" + 
				"TB_WORKFLOW_REQUEST a ,\r\n" + 
				"tb_cfc_application_mst c\r\n" + 
				"WHERE\r\n" + 
				"B.APM_APPLICATION_ID = a.APM_APPLICATION_ID and\r\n" + 
				"B.APM_APPLICATION_ID = c.APM_APPLICATION_ID and c.APM_STATUS IS NULL\r\n" + 
				"AND B.CARE_WARD_NO is not null\r\n" + 
				" AND B.REFERENCE_CATEGORY ="+subCategory+"\r\n" + 
				"GROUP BY A.STATUS,a.LAST_DECISION ,Name)B\r\n" + 
				"GROUP BY Name) a, (SELECT @row_number\\:=0) as R");
		commonComplaintEntity = (List<CommonComplaintEntity>) entityManager
				.createNativeQuery(queryBuilder.toString(), CommonComplaintEntity.class).getResultList();
		return commonComplaintEntity;
	}
}
