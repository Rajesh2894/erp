package com.abm.mainet.water.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.rest.dto.WaterDashboardConnectionsCreatedDTO;

@Repository
public interface TbCsmrInfoRepository extends
        PagingAndSortingRepository<TbKCsmrInfoMH, Long> {

    @Query(value = "SELECT wr.STATUS from tb_workflow_request wr where wr.APM_APPLICATION_ID =:apmApplicationId and  wr.ORGID =:orgId", nativeQuery = true)
    String getWorkflowRequestByAppId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);

    @Query("SELECT p.csMeteredccn FROM  TbKCsmrInfoMH p  where  p.csIdn=:csIdn and p.orgId=:orgId")
    Long fetchMeterTypeByCsidn(@Param("csIdn") Long csIdn, @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE TbKCsmrInfoMH m set m.mobileNoOTP=:otp, m.updatedDate =:updatedDate WHERE m.csContactno =:mobileNo"
            + " AND m.csCcn =:connectionNo")
    int updateOTPServiceForWater(@Param("mobileNo") String mobileNo, @Param("connectionNo") String connectionNo,
            @Param("otp") String otp, @Param("updatedDate") Date updatedDate);

    @Query("SELECT m FROM  TbKCsmrInfoMH m WHERE m.csContactno =:mobileNo AND m.csCcn =:connectionNo")
    TbKCsmrInfoMH getOTPServiceForWater(@Param("mobileNo") String mobileNo, @Param("connectionNo") String connectionNo);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE TbKCsmrInfoMH m set m.csMeteredccn =:csMeteredccn, m.trmGroup1 =:trmGroup1, m.trmGroup2 =:trmGroup2,"
    		+ " m.trmGroup3 =:trmGroup3 WHERE m.csIdn =:csIdn")
    void updateCsmrEntry(@Param("csIdn") Long csIdn, @Param("csMeteredccn") Long csMeteredccn, @Param("trmGroup1") Long trmGroup1,
    		@Param("trmGroup2") Long trmGroup2, @Param("trmGroup3") Long trmGroup3);

    @Query("SELECT c FROM  TbKCsmrInfoMH c  where  c.csCcn=:csCcn and c.orgId=:orgId")
    TbKCsmrInfoMH getCsmrInfoByCsCcnAndOrgId(@Param("csCcn") String csCcn, @Param("orgId") Long orgId);

    @Modifying
    @Query("update TbKCsmrInfoMH m set m.csCcnstatus=:conStatusId where m.csIdn=:csIdn")
    void updateConnectionStatusByCsIdn(@Param("csIdn") Long csIdn, @Param("conStatusId") Long connectionStatusId);

    @Query(value ="SELECT (select cpd_desc from tb_comparam_det where cpd_id=cs_Meteredccn) mtr_desc,count(1) \r\n" + 
	 		"    FROM  tb_csmr_info c where c.orgId=:orgId and date(Created_date)=:date1\r\n" + 
	 		"    group by cs_Meteredccn,c.orgId",nativeQuery=true)   
    List<Object[]> fetchCountMeterTypeCcn(@Param("orgId") Long orgId, @Param("date1") String date1);
    
    @Query(value ="select (select cod_desc from tb_comparent_det where cod_id=TRM_GROUP1) mtr_desc,sum(rd_amount), count(1) \r\n" + 
    		"from tb_receipt_mode a,tb_receipt_mas b,tb_csmr_info c where a.rm_rcptid=b.rm_rcptid \r\n" + 
    		"and dp_deptid=10 and a.orgid=:orgId and CPD_FEEMODE!=123 \r\n" + 
    		"and date(rm_date)=:dateN\r\n" + 
    		"group by TRM_GROUP1,b.orgid",nativeQuery=true)
    List<Object[]> fetchCountUsageTypeCcn(@Param("orgId") Long orgId, @Param("dateN") String dateN);
    
    @Query(value ="select distinct(a.APM_APPLICATION_ID), c.DATE_OF_REQUEST from tb_cfc_application_mst a,tb_services_mst b,TB_WORKFLOW_REQUEST c\r\n" + 
    		"where a.APM_APPLICATION_ID=c.APM_APPLICATION_ID and date(APM_APPLICATION_DATE)=:dateN and\r\n" + 
    		"CDM_DEPT_ID=10 and a.SM_SERVICE_ID=b.SM_SERVICE_ID and a.orgid=:orgId and c.STATUS = 'PENDING'\r\n" + 
    		"group by a.orgid,a.APM_APPLICATION_ID, c.DATE_OF_REQUEST",nativeQuery=true)
    List<Object[]>totalPendingApplications(@Param("orgId") Long orgId, @Param("dateN") String date1);
    
    @Query(value="select\r\n" + 
    		"sum(coalesce(BeyoundCLOSED,0)) BeyoundCLOSED,\r\n" + 
    		"sum(coalesce(WithinCLOSED,0)) WithinCLOSED,\r\n" + 
    		"sum(coalesce(CLOSED,0)) CLOSED,\r\n" + 
    		"sum(coalesce(PENDING,0)) PENDING,\r\n" + 
    		"(sum(coalesce(BeyoundCLOSED,0))+sum(coalesce(WithinCLOSED,0))+sum(coalesce(PENDING,0))) AS TotalRecieved\r\n" + 
    		"from\r\n" + 
    		"(select\r\n" + 
    		"CASE WHEN STATUS='CLOSED' and SLA='B' THEN COUNT(1) END AS BeyoundCLOSED,\r\n" + 
    		"CASE WHEN STATUS='CLOSED' and SLA='W' THEN COUNT(1) END AS WithinCLOSED,\r\n" + 
    		"CASE WHEN STATUS='CLOSED' THEN COUNT(1) END AS CLOSED,\r\n" + 
    		"CASE WHEN STATUS='PENDING' THEN COUNT(1) END AS PENDING\r\n" + 
    		"from\r\n" + 
    		"(select\r\n" + 
    		"(CASE WHEN\r\n" + 
    		"((a.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,NOW()) >=\r\n" + 
    		"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0) THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det\r\n" + 
    		"WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
    		"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END))) THEN 'B'\r\n" + 
    		"WHEN ((a.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,NOW()) <\r\n" + 
    		"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)\r\n" + 
    		"THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
    		"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))\r\n" + 
    		"THEN 'W'\r\n" + 
    		"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND (TIMESTAMPDIFF(SECOND,\r\n" + 
    		"a.DATE_OF_REQUEST,a.LAST_DATE_OF_ACTION) >\r\n" + 
    		"(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)\r\n" + 
    		"THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
    		"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END))) THEN 'B' WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND\r\n" + 
    		"(TIMESTAMPDIFF(SECOND,a.DATE_OF_REQUEST,a.LAST_DATE_OF_ACTION) <= (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.wf_id)) / 1000) <> 0) THEN\r\n" + 
    		"((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)\r\n" + 
    		"ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))\r\n" + 
    		"THEN 'W' END) AS SLA,\r\n" + 
    		"(CASE \r\n" + 
    		"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (a.LAST_DECISION <> 'REJECTED')) THEN 'CLOSED'\r\n" + 
    		"WHEN ((REPLACE(a.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (a.LAST_DECISION <> 'HOLD'))\r\n" + 
    		"THEN 'PENDING' END) AS STATUS\r\n" + 
    		"from\r\n" + 
    		"tb_csmr_info  b\r\n" + 
    		"inner join tb_workflow_request a on b.apm_application_id = a.APM_APPLICATION_ID \r\n" + 
    		"inner join tb_workflow_mas tb5 on tb5.WF_ID = a.WORFLOW_TYPE_ID \r\n" + 
    		"-- inner join tb_cfc_application_mst  cfc on  cfc.apm_application_id=b.apm_application_id\r\n" + 
    		"-- inner join tb_services_mst sm on sm.SM_SERVICE_ID =cfc.SM_SERVICE_ID\r\n" + 
    		"-- inner join   tb_department dp on dp.DP_DEPTID=sm.CDM_DEPT_ID and DP_DEPTCODE='WT'\r\n" + 
    		"WHERE b.ORGID=:orgId and date(LAST_DATE_OF_ACTION)=:dateN \r\n" + 
    		") tb1\r\n" + 
    		"WHERE \r\n" + 
    		"STATUS IN ('CLOSED','PENDING')\r\n" + 
    		"group by status,sla) tb2\r\n", nativeQuery=true)
    List<Object[]>totalCompletedApplicationsWithinSLA(@Param("orgId") Long orgId, @Param("dateN") String date1);
    
    
    @Modifying
    @Query("update TbKCsmrInfoMH m set m.applicationNo=:apmApplicationId where m.csIdn=:csIdn")
    void updateConnectionAppNoByCsIdn(@Param("csIdn") Long csIdn, @Param("apmApplicationId") Long apmApplicationId);

    @Query(value ="select (select cpd_desc from tb_comparam_det where cpd_id=cs_Meteredccn) mtr_desc,sum(rd_amount),count(1) \r\n" + 
    		"from tb_receipt_mode a,tb_receipt_mas b,tb_csmr_info c where a.rm_rcptid=b.rm_rcptid \r\n" + 
    		"and dp_deptid=10 and a.orgid=:orgId and CPD_FEEMODE!=123 \r\n" + 
    		"-- and ADDITIONAL_REF_NO=CS_idn --\r\n" + 
    		" and date(rm_date)=:dateN \r\n" + 
    		"group by cs_Meteredccn,b.orgid\r\n",nativeQuery=true)
    List<Object[]> fetchCountCcnType(@Param("orgId") Long orgId, @Param("dateN") String dateN);
    
}
