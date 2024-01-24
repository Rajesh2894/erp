package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilMeetingMemberEntity;

@Repository
public interface CouncilMeetingMemberRepository extends JpaRepository<CouncilMeetingMemberEntity, Long> {

    List<CouncilMeetingMemberEntity> findAllByMeetingId(Long meetingId);

    @Modifying
    @Query("DELETE FROM CouncilMeetingMemberEntity mm WHERE mm.meetingId  =:meetingId")
    void deleteMeetingMember(@Param("meetingId") Long meetingId);

    @Modifying
    @Query("UPDATE CouncilMeetingMemberEntity mm SET mm.updatedBy =:updatedBy, mm.lgIpMacUpd =:lgIpMacUpd, mm.updatedDate = CURRENT_TIMESTAMP "
            + "WHERE mm.meetingId = :meetingId ")
    void updateMeetingMember(@Param("updatedBy") Long updatedBy, @Param("lgIpMacUpd") String lgIpMacUpd,
            @Param("meetingId") Long meetingId);

    @Modifying
    @Query("UPDATE CouncilMeetingMemberEntity mm SET mm.attendanceStatus = :attendanceStatus where mm.meetingId =:meetingId ")
    void updateAttendanceStatusByMeetingId(@Param("attendanceStatus") int attendanceStatus, @Param("meetingId") Long meetingId);

    @Modifying
    @Query("UPDATE CouncilMeetingMemberEntity mm SET mm.attendanceStatus = :attendanceStatus, mm.updatedBy =:updatedBy, mm.lgIpMacUpd =:lgIpMacUpd, mm.updatedDate = CURRENT_TIMESTAMP where mm.meetingId =:meetingId AND mm.councilMemberId in (:memberIds)")
    void updateAttendanceStatusByIds(@Param("attendanceStatus") int attendanceStatus, @Param("memberIds") List<Long> memberIds,
            @Param("meetingId") Long meetingId, @Param("updatedBy") Long updatedBy, @Param("lgIpMacUpd") String lgIpMacUpd);

    @Query("select case when count(mm)>0 THEN true ELSE false END from CouncilMeetingMemberEntity mm where mm.meetingId = :meetingId AND mm.attendanceStatus =:attendanceStatus")
    Boolean checkMemberAttendMeeting(@Param("meetingId") Long meetingId, @Param("attendanceStatus") Integer attendanceStatus);

    @Query("select mm from CouncilMeetingMemberEntity mm where mm.meetingId = :meetingId AND mm.attendanceStatus =:attendanceStatus")
    List<CouncilMeetingMemberEntity> findMembersWithAttendanceStatusByMeetingId(@Param("meetingId") Long meetingId,
            @Param("attendanceStatus") Integer attendanceStatus);

    @Query("select mm from CouncilMeetingMemberEntity mm where mm.meetingId = :meetingId ")
    List<CouncilMeetingMemberEntity> findMembersByMeetingId(@Param("meetingId") Long meetingId);

    @Query("select mm from CouncilMeetingMemberEntity mm where mm.meetingId = :meetingId AND mm.councilMemberId =:councilMemberId")
    CouncilMeetingMemberEntity getMeetingMemberByIds(@Param("meetingId") Long meetingId,
            @Param("councilMemberId") Long councilMemberId);

    @Query("select mm from CouncilMeetingMemberEntity mm where mm.meetingId = :meetingId AND mm.councilMemberId =:councilMemberId AND mm.attendanceStatus =:attendanceStatus")
    CouncilMeetingMemberEntity getMeetingMemberByIdsByStatus(@Param("meetingId") Long meetingId,
            @Param("councilMemberId") Long councilMemberId, @Param("attendanceStatus") Integer attendanceStatus);
    
    @Modifying
    @Query("UPDATE CouncilMeetingMemberEntity mm SET mm.attendanceStatus = :attendanceStatus where mm.id =:id ")
    void updateAttendanceStatusByMemberId(@Param("attendanceStatus") int attendanceStatus, @Param("id") Long id);

}
