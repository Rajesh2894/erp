package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.council.domain.MOMResolutionEntity;

public interface MOMRepository extends JpaRepository<MOMResolutionEntity, Long> {

    @Query("select mm from MOMResolutionEntity mm where mm.proposalId = :proposalId  and mm.orgId=:orgId")
    MOMResolutionEntity getMeetingMOMData(@Param("proposalId") Long proposalId, @Param("orgId") Long orgId);

    @Query("select case when count(mm)>0 THEN true ELSE false END from MOMResolutionEntity mm where mm.meetingId = :meetingId AND mm.orgId = :orgId")
    Boolean checkMOMCreated(@Param("meetingId") Long meetingId, @Param("orgId") Long orgId);

    @Query("SELECT DISTINCT mm.meetingId FROM MOMResolutionEntity mm WHERE mm.meetingId IN ?1")
    List<Long> findMOMByMeetingIdIn(List<Long> meetingIds);

    @Query("select mm from MOMResolutionEntity mm where mm.meetingId = :meetingId order by mm.resoId desc")
    List<MOMResolutionEntity> findMOMByMeetingId(@Param("meetingId") Long meetingId);

    @Query("SELECT mm.proposalId FROM MOMResolutionEntity mm WHERE mm.status = :status and mm.orgId=:orgId")
    List<Long> findProposalsByStatus(@Param("status") String status, @Param("orgId") Long orgId);
}
