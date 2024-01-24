package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.council.domain.MOMSumotoResolution;

public interface MOMSumotoRepository extends JpaRepository<MOMSumotoResolution, Long> {

	/*
	 * @Query("select m from MOMSumotoResolution m where m.meetingMomId=:meetingMomId AND m.proposalId = :proposalId"
	 * ) List<CouncilMeetingMomSumotoResolution>
	 * findAllByMeetingMomIdAndProposalId(@Param("meetingMomId") Long
	 * meetingMomId,@Param("proposalId") Long proposalId);
	 */
	
	@Query("select m from MOMSumotoResolution m where m.meetingMomId=:meetingMomId")
    List<MOMSumotoResolution> findAllByMeetingMomId(@Param("meetingMomId") Long meetingMomId);
	
}
