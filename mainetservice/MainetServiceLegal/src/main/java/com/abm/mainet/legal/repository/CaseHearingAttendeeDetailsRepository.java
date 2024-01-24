package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CaseHearingAttendeeDetails;
import com.abm.mainet.legal.dto.CaseHearingDTO;

@Repository
public interface CaseHearingAttendeeDetailsRepository extends JpaRepository<CaseHearingAttendeeDetails, Long> {

    @Modifying
    @Query("delete from CaseHearingAttendeeDetails cha where cha.hraId in(:attendeeId)")
    void deleteHearingAttendeeDetailByIds(@Param("attendeeId") List<Long> attendeeIds);

    List<CaseHearingAttendeeDetails> findByCseIdAndOrgid(Long cseId, Long orgid);

    List<CaseHearingAttendeeDetails> findByCseId(Long cseId);

	List<CaseHearingAttendeeDetails> findByHrId(Long hrId);

	List<CaseHearingAttendeeDetails> findByHrIdAndOrgid(Long id, Long orgId);
}
