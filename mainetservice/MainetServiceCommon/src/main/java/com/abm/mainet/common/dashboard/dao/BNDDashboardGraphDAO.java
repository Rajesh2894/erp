package com.abm.mainet.common.dashboard.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dashboard.domain.YearWiseGrievanceGraphEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.BndApplicationDtlEntity;

@Repository
public class BNDDashboardGraphDAO extends AbstractDAO<Integer> implements IBNDDashboardGraphDAO{
	
	private static final Logger LOG = Logger.getLogger(BNDDashboardGraphDAO.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphEntityList(){
		List<YearWiseGrievanceGraphEntity> yearWiseGraphEntity = null;
		
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+"select years as FY_Year, sum(Closed) as closed_query,sum(Pending) as Pending_query,\r\n"
						+"sum(Rejected) as expired_query, sum(Hold) as Hold, count(1) as recieved_query from\r\n"
						+" (select year(b.DATE_OF_REQUEST) as years,\r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION <> 'REJECTED')  then 1 else 0 END)  Closed, \r\n"
						+"(CASE WHEN (b.STATUS = 'PENDING'  AND b.LAST_DECISION <> 'HOLD')  then 1 else 0 END)  Pending, \r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION = 'REJECTED')  then 1 else 0 END)  Rejected, \r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION = 'HOLD')  then 1 else 0 END)  Hold \r\n"
						+"from tb_workflow_request b, tb_bd_cfc_interface c \r\n"
						+"where c.apm_application_id=b.apm_application_id ) x  \r\n"
						+"group by x.years ) as t, (SELECT @row_number\\:=0) AS rn");
		Query hqlQuery = entityManager.createNativeQuery(queryBuilder.toString(), YearWiseGrievanceGraphEntity.class);
		yearWiseGraphEntity = (List<YearWiseGrievanceGraphEntity>)hqlQuery.getResultList();
		return yearWiseGraphEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<YearWiseGrievanceGraphEntity> getBNDDataStatusByYearAndType(String type,Integer noOfDays){
		List<YearWiseGrievanceGraphEntity> yearWiseGraphEntity = null;
		
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS num, t.* from (\r\n"
				+"select years as FY_Year, sum(Closed) as closed_query,sum(Pending) as Pending_query,\r\n"
						+"sum(Rejected) as expired_query, sum(Hold) as Hold, count(1) as recieved_query from\r\n"
						+" (select year(b.DATE_OF_REQUEST) as years,\r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION <> 'REJECTED')  then 1 else 0 END)  Closed, \r\n"
						+"(CASE WHEN (b.STATUS = 'PENDING'  AND b.LAST_DECISION <> 'HOLD')  then 1 else 0 END)  Pending, \r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION = 'REJECTED')  then 1 else 0 END)  Rejected, \r\n"
						+"(CASE WHEN (b.STATUS = 'CLOSED'  AND b.LAST_DECISION = 'HOLD')  then 1 else 0 END)  Hold \r\n"
						+"from tb_workflow_request b, tb_bd_cfc_interface c ,tb_bd_cert_copy t\r\n"
						+"where c.apm_application_id=b.apm_application_id "
						+ "and c.apm_application_id = t.APMApplicationId "
						+ "and t.COPY_NO = 1 \r\n");
						if(type.equals("B"))
								queryBuilder.append("and t.br_id is not null\r\n");
						else if(type.equals("D"))
							queryBuilder.append("and t.dr_id is not null\r\n");
					if(noOfDays!= null && noOfDays!= 0)
						queryBuilder.append("and b.date_of_request between DATE_SUB(CURDATE(), "
								+ " INTERVAL "+noOfDays +" day ) and now()");
						queryBuilder.append( ") x  \r\n"
						+"group by x.years ) as t, (SELECT @row_number\\:=0) AS rn");
		Query hqlQuery = entityManager.createNativeQuery(queryBuilder.toString(), YearWiseGrievanceGraphEntity.class);
		yearWiseGraphEntity = (List<YearWiseGrievanceGraphEntity>)hqlQuery.getResultList();
		return yearWiseGraphEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BndApplicationDtlEntity> getBNDDataListByStatusAndType(String type,Integer noOfDays,String status){
		List<BndApplicationDtlEntity> bndApplicationDtlEntity = null;
		
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("SELECT @row_number\\:=@row_number+1 AS NUM, t.* from (\r\n" + "SELECT x .* FROM\r\n"
				+ " (select concat_ws(cfc.APM_FNAME ,' ' ,CASE WHEN cfc.APM_LNAME IS NOT NULL THEN cfc.APM_LNAME ELSE '' END) AS APPLICANT_NAME,cfc.APM_APPLICATION_ID, \r\n"
				+ " cfc.APM_APPLICATION_DATE ,b.STATUS,(SELECT SM_SERVICE_NAME FROM tb_services_mst s WHERE s.sm_service_id = cfc.sm_service_id) as SERVICE_NAME\r\n"
				+ "from tb_workflow_request b, tb_bd_cfc_interface c ,tb_cfc_application_mst cfc\r\n"
				+ "where c.apm_application_id=b.apm_application_id \r\n" + "and b.STATUS =:status \r\n"
				+ "and cfc.APM_APPLICATION_ID = c.apm_application_id \r\n"
				+ "and cfc.APM_FNAME is not null\r\n");

		/*if (type.equals("B"))
			queryBuilder.append("and t.br_id is not null\r\n");
		else if (type.equals("D"))
			queryBuilder.append("and t.dr_id is not null\r\n");*/
		if (noOfDays != null && noOfDays != 0)
			queryBuilder.append("and b.date_of_request between DATE_SUB(CURDATE(), " + " INTERVAL " + noOfDays
					+ " day ) and now()");

		queryBuilder.append(") as x) as t, (SELECT @row_number\\:=0) AS rn");
		Query hqlQuery = entityManager.createNativeQuery(queryBuilder.toString(), BndApplicationDtlEntity.class);
		hqlQuery.setParameter("status", status);
		bndApplicationDtlEntity = (List<BndApplicationDtlEntity>)hqlQuery.getResultList();
		return bndApplicationDtlEntity;
	}
}
