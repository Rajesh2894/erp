package com.abm.mainet.cfc.objection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;

public interface ObjectionMastRepository extends JpaRepository<TbObjectionEntity, Long> {

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.apmApplicationId=:appId AND o.serviceId=:serviceId")
    TbObjectionEntity getObjectionDetailByAppId(@Param("orgId") Long orgId, @Param("appId") Long appId,
            @Param("serviceId") Long serviceId);

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.apmApplicationId=:appId AND o.serviceId=:serviceId")
    List<TbObjectionEntity> getObjectionDetailListByAppId(@Param("orgId") Long orgId, @Param("appId") Long appId,
            @Param("serviceId") Long serviceId);

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.objectionNumber=:objNo")
    TbObjectionEntity getObjectionDetailByObjNo(@Param("orgId") Long orgId, @Param("objNo") String objNo);

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.objectionId=:objectionId")
    TbObjectionEntity getObjectionDetailByObjId(@Param("orgId") Long orgId, @Param("objectionId") Long objectionId);

    @Query("SELECT o from  TbObjectionEntity o WHERE o.objectionStatus='I'")
    List<TbObjectionEntity> getAllInactiveObjectionList();

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.apmApplicationId=:appId AND o.serviceId=:serviceId AND o.objectionOn=:objectionOn")
    TbObjectionEntity getObjectionDetailByIds(@Param("orgId") Long orgId, @Param("appId") Long appId,
            @Param("serviceId") Long serviceId, @Param("objectionOn") Long objectionOn);

    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId AND o.serviceId=:serviceId AND o.objectionOn=:objectionOn AND o.mobileNo =:mobileNo")
    List<TbObjectionEntity> fetchObjectionsByMobileNoAndIds(@Param("orgId") Long orgId,
            @Param("serviceId") Long serviceId, @Param("objectionOn") Long objectionOn, @Param("mobileNo") String mobileNo);
    @Modifying
    @Query("UPDATE TbObjectionEntity o SET  o.objectionStatus =:objectionStatus, o.updatedBy =:updatedBy, o.lgIpMacUpd =:lgIpMacUpd, o.updatedDate = CURRENT_TIMESTAMP "
            + "WHERE o.objectionId = :objectionId ")
    void updateObjectionStatus(@Param("updatedBy") Long updatedBy, @Param("lgIpMacUpd") String lgIpMacUpd,
            @Param("objectionStatus") String objectionStatus,
            @Param("objectionId") Long objectionId);

    @Query("SELECT o.objectionId from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.objectionReferenceNumber=:objectionReferenceNumber")
    List<Long> getObjectionIdListByRefNo(@Param("orgId") Long orgId,
            @Param("objectionReferenceNumber") String objectionReferenceNumber);

    @Query("select case when count(m)>0 THEN true ELSE false END from TbObjectionEntity m where m.objectionReferenceNumber =:refNo AND m.orgId =:orgId AND m.objectionStatus='Pending' ")
	Boolean checkLicenseNoExist(@Param("refNo") String refNo, @Param("orgId") Long orgId);
    
    @Query("SELECT o from  TbObjectionEntity o WHERE o.orgId=:orgId"
            + " AND o.objectionReferenceNumber=:objectionReferenceNumber"
            + " ORDER BY o.objectionId desc")
    List<TbObjectionEntity> getObjectionEntityByRefNo(@Param("orgId") Long orgId,
            @Param("objectionReferenceNumber") String objectionReferenceNumber);
}
