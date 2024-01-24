package com.abm.mainet.cfc.objection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.objection.domain.NoticeMasterEntity;

public interface NoticeMasterRepository extends JpaRepository<NoticeMasterEntity, Long> {

    @Query("SELECT count(n) from  NoticeMasterEntity n  WHERE n.orgId=:orgId"
            + " AND n.refNo=:refNo AND n.notNo=:notNo "
            + "AND n.notDuedt > CURRENT_DATE")
    int getCountOfNotBeforeDueDateByRefNoAndNotNo(@Param("orgId") Long orgId, @Param("refNo") String refNo,
            @Param("notNo") String notNo);

    @Query("SELECT count(n) from  NoticeMasterEntity n  WHERE n.orgId=:orgId"
            + " AND n.refNo=:refNo AND n.notNo=:notNo")
    int getCountOfNotByRefNoAndNotNo(@Param("orgId") Long orgId, @Param("refNo") String refNo,
            @Param("notNo") String notNo);

    @Query("SELECT n from  NoticeMasterEntity n  WHERE n.orgId=:orgId"
            + " AND n.refNo=:refNo")
    NoticeMasterEntity getNoticeByRefNo(@Param("orgId") Long orgId, @Param("refNo") String refNo);

    @Query("SELECT n from  NoticeMasterEntity n  WHERE n.orgId=:orgId AND n.refNo=:refNo AND dpDeptid=:dpDeptId AND n.notNo in (select max(notNo) from NoticeMasterEntity  where orgId=:orgId and refNo=:refNo and dpDeptid=:dpDeptId )")
    NoticeMasterEntity getMaxNoticeByRefNo(@Param("orgId") Long orgId, @Param("refNo") String refNo,
            @Param("dpDeptId") Long dpDeptId);

    @Query("SELECT n from  NoticeMasterEntity n  WHERE n.orgId=:orgId AND n.refNo=:refNo")
    List<NoticeMasterEntity> getAllNoticeByRefNo(@Param("orgId") Long orgId, @Param("refNo") String refNo);

    @Query("SELECT n from  NoticeMasterEntity n  WHERE n.orgId=:orgId AND n.notNo=:noticeNo")
    List<NoticeMasterEntity> getNoticeByNoticeNo(@Param("orgId") Long orgId, @Param("noticeNo") String noticeNo);

    @Query("SELECT count(n) from  NoticeMasterEntity n  WHERE n.orgId=:orgId"
            + " AND n.apmApplicationId=:apmApplicationId AND n.notNo=:notNo")
	int getCountOfNotByApplNoNoAndNotNo(@Param("orgId") Long orgId,@Param("apmApplicationId") Long apmApplicationId,  @Param("notNo") String notNo);

    @Query("SELECT count(n) from  NoticeMasterEntity n  WHERE n.orgId=:orgId"
            + " AND n.apmApplicationId=:apmApplicationId AND n.notNo=:notNo "
            + "AND n.notDuedt > CURRENT_DATE")
	int getCountOfNotBeforeDueDateByApplNoAndNotNo(@Param("orgId") Long orgId,@Param("apmApplicationId") Long apmApplicationId,  @Param("notNo") String notNo);

	@Query("SELECT n from  NoticeMasterEntity n  WHERE n.orgId=:orgId AND n.refNo=:refNo AND n.flatNo=:flatNo")
	List<NoticeMasterEntity> getAllNoticeByRefNo(@Param("orgId") Long orgId, @Param("refNo") String refNo,
			@Param("flatNo") String flatNo);
	
	@Query("SELECT min(notId) from  NoticeMasterEntity n  WHERE n.apmApplicationId=:apmApplicationId")
	Long getNoticeIdByApplicationId(@Param("apmApplicationId") Long apmApplicationId);

}
