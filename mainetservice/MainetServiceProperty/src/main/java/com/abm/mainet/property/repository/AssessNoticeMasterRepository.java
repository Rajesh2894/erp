package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.AssessNoticeMasterEntity;

public interface AssessNoticeMasterRepository extends JpaRepository<AssessNoticeMasterEntity, Long> {

    @Query("select a from AssessNoticeMasterEntity a  where a.mnAssNo=:propNo and a.orgid=:orgId")
    AssessNoticeMasterEntity getNoticeMasterByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);

    @Query("select a from AssessNoticeMasterEntity a  where a.mnNotid in(select max(b.mnNotid) from AssessNoticeMasterEntity b  "
    		+ "where b.mnAssNo=:propNo and b.orgid=:orgId and b.cpdNottyp=:cpdNottyp order by 1 desc)")
    AssessNoticeMasterEntity getNoticesDetailsByPropNo(@Param("orgId") long orgid, @Param("propNo") String propNo,
            @Param("cpdNottyp") long noticeType);

    @Query("select a from AssessNoticeMasterEntity a  where a.mnNotno=:mnNotno and a.orgid=:orgId and a.cpdNottyp=:cpdNottyp")
    AssessNoticeMasterEntity getNoticesDetailsByNoticeNo(@Param("orgId") long orgid, @Param("mnNotno") long mnNotno,
            @Param("cpdNottyp") long noticeType);
    
    @Query("select a from AssessNoticeMasterEntity a  where a.mnAssNo=:propNo and a.orgid=:orgId")
    List<AssessNoticeMasterEntity> getAllNoticeMasterByPropNo(@Param("propNo") String propNo, @Param("orgId") Long orgId);
    
    @Query("select a.mnAssNo from AssessNoticeMasterEntity a  where a.cpdNottyp=:noticeType and a.orgid=:orgid and a.creationDate >= :fromDate and a.creationDate <= :toDate")
    List<String> fetchPropertyDemandNoticeofCurrentYear(@Param("noticeType") Long noticeType, @Param("orgid") Long orgid, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
