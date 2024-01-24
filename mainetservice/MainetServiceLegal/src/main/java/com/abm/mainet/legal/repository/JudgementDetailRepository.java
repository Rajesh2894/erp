package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.JudgementDetail;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface JudgementDetailRepository extends JpaRepository<JudgementDetail, Long> {

    List<JudgementDetail> findByOrgidAndCseId(Long orgid, Long cseId);
    
    @Query("select jd from JudgementDetail jd where jd.orgid = :orgId and jd.cseId = :cseId")
    JudgementDetail findByCseId(@Param("orgId") Long orgId, @Param("cseId") Long cseId);
    
    @Query("select jd from JudgementDetail jd where jd.cseId = :cseId")
    JudgementDetail findJudgementDetByCseId( @Param("cseId") Long cseId);
    
    @Modifying
    @Query("delete from AttendeeDetails ad where ad.attendeeId in(:attendeeId)")
    void deleteAttendeeDetailByIds(@Param("attendeeId") List<Long> attendeeIds);

}
