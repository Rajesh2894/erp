package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.AssessNoticeMasterEntity;

public interface PropertyNoticeDeletionRepository extends CrudRepository<AssessNoticeMasterEntity, Long> {

    @Query("select count(1) from AssessNoticeMasterEntity a, AssessNoticeMasterEntity b where a.mnAssNo = b.mnAssNo and a.cpdNottyp =:dmNoticeType and b.cpdNottyp =:wnNoticeType and a.mnNotno =:dmNoticeNo and a.orgid=b.orgid and  a.orgid=:orgId ")
    int propertyNoticeDeletion(@Param("dmNoticeType") Long dmNoticeType, @Param("wnNoticeType") Long wnNoticeType,
            @Param("dmNoticeNo") Long dmNoticeNo, @Param("orgId") Long orgId);

    @Query("select count(1) from TbObjectionEntity a where a.noticeNo=:spNoticeNo and a.orgId=:orgId ")
    int specialNoticeValidation(@Param("spNoticeNo") String spNoticeNo,
            @Param("orgId") long orgId);

    @Query("select count(1) from AssessNoticeMasterEntity n where  n.mnNotno=:spNoticeNo and n.cpdNottyp=:spNoticeType and n.orgid=:orgId and n.mnAssNo in (select a.assNo from AssesmentMastEntity a where a.assStatus=:assStatus and a.faYearId=:faYearId and a.orgId=:orgId)")
    int noticeValidationForCurrentYear(@Param("spNoticeNo") Long spNoticeNo, @Param("spNoticeType") Long spNoticeType,
            @Param("faYearId") Long faYearId, @Param("assStatus") String assStatus,
            @Param("orgId") long orgId);

    @Transactional
    @Modifying
    int deleteByMnNotidAndOrgid(long mnNotid, long orgid);

}
