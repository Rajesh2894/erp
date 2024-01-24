/**
 * 
 */
package com.abm.mainet.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.smsemail.domain.SmsEmailTransaction;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Repository
public interface SmsEmailRepository extends CrudRepository<SmsEmailTransaction, Long> {
	@Query("select sm from  SmsEmailTransaction sm where  sm.orgId.orgid=:orgId   and sm.refId3=:refId3 and sm.sentDt between :formDt and :toDt")
	List<SmsEmailTransaction> getSmsAndEmailHistory(@Param("orgId") Long orgId, @Param("formDt") Date formDt,
			@Param("toDt") Date toDt, @Param("refId3") String refId3);
	
	@Query("select sm from  SmsEmailTransaction sm where  sm.orgId.orgid=:orgId   and  sm.sentDt between :formDt and :toDt")
	List<SmsEmailTransaction> getSmsAndEmailHistoryForBoth(@Param("orgId") Long orgId, @Param("formDt") Date formDt,
			@Param("toDt") Date toDt);
	
	@Query(nativeQuery = true,
		   value="select t1.*,t2.sm_service_name,t2.sm_shortdesc from tb_sms_transaction t1,tb_services_mst t2 where \r\n" + 
			"t1.orgId=:orgId and t1.sent_dt between :formDt and :toDt and t1.service_id = t2.sm_service_id and t2.sm_shortdesc=:serviceShortCode ")
	List<Object[]> getSmsAndEmailHistoryForBothByService(@Param("orgId") Long orgId, @Param("formDt") Date formDt,
			@Param("toDt") Date toDt,@Param("serviceShortCode")String serviceShortCode);
	
	@Query(nativeQuery = true,
			   value="select t1.*,t2.sm_service_name,t2.sm_shortdesc from tb_sms_transaction t1,tb_services_mst t2 where \r\n" + 
				"t1.orgId=:orgId and t1.sent_dt between :formDt and :toDt and t1.service_id = t2.sm_service_id and t2.sm_shortdesc=:serviceShortCode\r\n"
				+ "and t1.ref_id3=:refId3 ")
		List<Object[]> getSmsAndEmailHistoryByService(@Param("orgId") Long orgId, @Param("formDt") Date formDt,
				@Param("toDt") Date toDt,@Param("serviceShortCode")String serviceShortCode,@Param("refId3") String refId3);
	
	

}
