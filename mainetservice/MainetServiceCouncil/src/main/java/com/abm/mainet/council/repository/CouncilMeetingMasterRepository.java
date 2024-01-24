package com.abm.mainet.council.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterHistoryEntity;

@Repository
public interface CouncilMeetingMasterRepository extends JpaRepository<CouncilMeetingMasterEntity, Long> {

    Long countByOrgId(Long orgId);

    List<CouncilMeetingMasterEntity> findMeetingByAgendaId(CouncilAgendaMasterEntity agendaId);

    List<CouncilMeetingMasterEntity> findAllBymeetingId(Long meetingId);

    List<CouncilMeetingMasterEntity> findAllByOrgIdOrderByMeetingIdDesc(@Param("orgId") Long orgId);

    @Query("select case when count(m)>0 THEN true ELSE false END from CouncilMeetingMasterEntity m where m.meetingId !=:meetingId AND m.agendaId.agendaId =:agendaId")
    Boolean checkMeetingPresent(@Param("meetingId") Long meetingId, @Param("agendaId") Long agendaId);

    @Query("select new java.lang.Boolean(count(*) > 0) from CouncilMeetingMasterEntity where meetingTypeId = :meetingTypeId AND meetingDate >= :currentDate AND agendaId.agendaId = :agendaId AND orgId=:orgId")
    Boolean checkMeetingExist(@Param("meetingTypeId") Long meetingTypeId, @Param("currentDate") Date currentDate,
            @Param("agendaId") Long agendaId, @Param("orgId") Long orgId);

    @Query("select mm from CouncilMeetingMasterEntity mm where mm.meetingTypeId = :meetingTypeId and mm.meetingStatus = :meetingStatus and mm.orgId=:orgId")
    CouncilMeetingMasterEntity getMeetingDetails(@Param("meetingTypeId") Long meetingTypeId,
            @Param("meetingStatus") String meetingStatus, @Param("orgId") Long orgId);

    List<CouncilMeetingMasterEntity> findAllMeetingsByMeetingTypeIdAndMeetingStatusAndOrgIdOrderByMeetingIdDesc(Long meetingTypeId,
            String meetingStatus,
            Long orgId);
    
    List<CouncilMeetingMasterEntity> findAllMeetingsByMeetingTypeIdAndOrgId(Long meetingTypeId, Long orgId);

    @Query("select case when count(m)>0 THEN true ELSE false END from CouncilMeetingMasterEntity m where  m.agendaId.agendaId =:agendaId AND m.meetingStatus = :meetingStatus and m.orgId=:orgId")
    Boolean checkAgendaPresentInMeeting(@Param("agendaId") Long agendaId, @Param("meetingStatus") String meetingStatus,
            @Param("orgId") Long orgId);
    
    @Query("Select hd from CouncilMeetingMasterHistoryEntity hd where hd.meetingId =:meetingId AND hd.orgId =:orgId")
    List<CouncilMeetingMasterHistoryEntity> findAllMeetingDetFromHist(@Param("meetingId") Long meetingId, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("UPDATE CouncilMeetingMasterEntity p SET p.meetingStatus =:flag where p.meetingId =:meetingId")
    void updateMeetingStatusWithMeetingID(@Param("meetingId") Long meetingId, @Param("flag") String flag);

}
