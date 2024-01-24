package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.PropertyTransferMasterEntity;
import com.abm.mainet.property.domain.PropertyTransferOwnerEntity;

@Repository
public interface PropertyTransferRepository extends JpaRepository<PropertyTransferMasterEntity, Long> {

    @Query("SELECT m FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and m.apmApplicationId=:applicationId "
            + "and m.status='A'")
    PropertyTransferMasterEntity getPropTransferMstByAppId(@Param("orgId") Long orgId,
            @Param("applicationId") Long applicationId);

    @Query("SELECT m.apmApplicationId FROM  PropertyTransferMasterEntity m  WHERE (m.proAssNo,m.transferMstId) in (select t.proAssNo,max(t.transferMstId) from PropertyTransferMasterEntity t"
            + " where t.orgId=:orgId  and  t.proAssNo =:propNo group by t.proAssNo  ) order by m.transferMstId")
    Long getApplicationIdByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT m.proAssNo FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and m.apmApplicationId=:applicationId and m.smServiceId=:serviceId "
            + "and m.status='A'")
    String getPropertyNoByAppId(@Param("orgId") Long orgId,
            @Param("applicationId") Long applicationId, @Param("serviceId") Long serviceId);

    @Query("SELECT m.actualTransferDate FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and m.proAssNo=:proAssNo "
            + "and m.status='A'")
    List<Date> getActualTransferDateByPropNo(@Param("orgId") Long orgId,
            @Param("proAssNo") String proAssNo);

    @Modifying
    @Query("Update PropertyTransferMasterEntity pm set pm.certificateNo=:certificateNo, pm.updatedBy=:empId, pm.updatedDate = CURRENT_DATE where pm.apmApplicationId=:apmApplicationId  and pm.orgId=:orgId ")
    void updateCertificateNo(@Param("certificateNo") String certificateNo, @Param("empId") Long empId,
            @Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);

    @Query("SELECT m FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and (m.apmApplicationId=:applicationId or m.proAssNo=:propNo) order by m.transferMstId desc")
    List<PropertyTransferMasterEntity> getPropTransferMst(@Param("orgId") Long orgId,
            @Param("applicationId") Long applicationId, @Param("propNo") String propNo);

    @Query("SELECT m FROM PropertyTransferMasterEntity m  WHERE (m.proAssNo,m.transferMstId) in (select t.proAssNo,max(t.transferMstId) from PropertyTransferMasterEntity t"
            + " where t.orgId=:orgId  and  t.proAssNo =:propNo group by t.proAssNo  ) order by m.transferMstId")
    PropertyTransferMasterEntity fetchPropTransferMstByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("SELECT m FROM PropertyTransferOwnerEntity m WHERE m.tbAsTransferrMast =:transferMaster and m.orgId=:orgId")
    List<PropertyTransferOwnerEntity> fetchTransferOwnerByTransferMaster(
            @Param("transferMaster") PropertyTransferMasterEntity transferMaster, @Param("orgId") Long orgId);
    
	@Query(value = "SELECT wr.STATUS from tb_workflow_request wr where wr.APM_APPLICATION_ID =:apmApplicationId and  wr.ORGID =:orgId", nativeQuery = true)
	String getWorkflowRequestByAppId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);
	
	@Query("SELECT m FROM PropertyTransferMasterEntity m  WHERE (m.proAssNo,m.transferMstId) in (select t.proAssNo,max(t.transferMstId) from PropertyTransferMasterEntity t"
			+ " where t.orgId=:orgId  and  t.proAssNo =:propNo and  t.flatNo =:flatNo group by t.proAssNo ) ")
	PropertyTransferMasterEntity fetchPropTransferMstByPropNoNFlatNo(@Param("propNo") String propNo,@Param("flatNo") String flatNo,
			@Param("orgId") Long orgId);
	
	@Query("SELECT m FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and (m.apmApplicationId=:applicationId or m.proAssNo=:propNo) "
			+ " and m.authoStatus='A' order by m.transferMstId desc")
	List<PropertyTransferMasterEntity> getPropTransferMaster(@Param("orgId") Long orgId,
			@Param("applicationId") Long applicationId, @Param("propNo") String propNo);

	@Query("SELECT m FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId and (m.apmApplicationId=:applicationId or m.proAssNo=:propNo) "
			+ " and m.flatNo=:flatNo and m.authoStatus='A' order by m.transferMstId desc")
	List<PropertyTransferMasterEntity> getPropTransferMstWithFlat(@Param("orgId") Long orgId,
			@Param("applicationId") Long applicationId, @Param("propNo") String propNo, @Param("flatNo") String flatNo);

	@Modifying
	@Query("Update PropertyTransferMasterEntity pm set pm.authoStatus=:authoStatus, pm.updatedBy=:empId, pm.updatedDate = CURRENT_DATE where "
			+ " pm.apmApplicationId=:apmApplicationId and pm.proAssNo=:proAssNo and pm.orgId=:orgId ")
	void updatePropTransferMstStatus(@Param("orgId") Long orgId, @Param("apmApplicationId") Long apmApplicationId,
			@Param("proAssNo") String proAssNo, @Param("authoStatus") String authoStatus, @Param("empId") Long empId);
	
	@Query("SELECT m.apmApplicationId FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId  and  m.proAssNo =:propNo  ")
	List<Long> getAllApplicationIdsByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

	@Query("SELECT m.apmApplicationId FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId  and  m.proAssNo =:propNo and m.flatNo=:flatNo")
	List<Long> getAllApplicationIdsByPropNoNFlat(@Param("propNo") String propNo, @Param("flatNo") String flatNo,
			@Param("orgId") Long orgId);
	 
	@Query("SELECT m.transferType FROM  PropertyTransferMasterEntity m  WHERE m.orgId=:orgId  and  m.apmApplicationId =:appNo")
	Long getTransferTypeByApplicationId(@Param("appNo") Long applicationId, @Param("orgId") Long orgId);
	
	@Modifying
    @Query("Update PropertyTransferMasterEntity pm set pm.autBy=:empId, pm.updatedBy=:empId where pm.apmApplicationId=:apmApplicationId  and pm.orgId=:orgId ")
    void updateAuthorizeBy(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId,@Param("empId") Long empId);
}
