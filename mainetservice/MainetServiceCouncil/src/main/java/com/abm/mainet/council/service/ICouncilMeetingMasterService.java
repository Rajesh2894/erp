package com.abm.mainet.council.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;

public interface ICouncilMeetingMasterService {

    public boolean saveCouncilMeeting(CouncilMeetingMasterDto councilDto);

    public List<CouncilMeetingMasterDto> searchCouncilMeetingMasterData(Long meetingTypeId, String meetingNo,
            Date fromDate, Date toDate, Long orgid, String orderBy);

    public CouncilMeetingMasterDto getMeetingDataById(Long meetingId);

    public List<CouncilMemberCommitteeMasterDto> fetchMeetingMemberListByMeetingId(Long meetingId, Long orgid);

    public List<CouncilMeetingMasterDto> searchAttendanceMasterData(Long meetingTypeId, String meetingNo, Date fromDate,
            Date toDate, Long orgId);

    public void updateAttendanceStatusInMeetingMember(List<Long> memberIds, Long meetingId,
            List<DocumentDetailsVO> attachmentList,
            FileUploadDTO uploadDTO, Long deleteFileId, Long orgId, String lgIpMac);

    List<CouncilMeetingMasterDto> fetchMeetingDetailsById(Long meetingId, Long orgId);

    boolean checkMemberAttendMeeting(Long meetingId, Integer attendanceStatus);

    // fetch those result whose 1.meeting is attend by any single person and 2 not created MOM YET
    List<CouncilMeetingMasterDto> fetchMeetingsByMeetingTypeId(Long meetingTypeId, Long orgId);

    boolean checkAgendaPresentInMeeting(Long agendaId, String meetingStatus, Long orgId);

    // THIS METHOD IS USED FOR ATTENDANCE PART
    List<CouncilMeetingMasterDto> fetchPendingMeetingsMeetingTypeId(Long meetingTypeId, Long orgId);

    public List<CouncilMemberCommitteeMasterDto> fetchMeetingPresentMemberListByMeetingId(Long meetingId,
            Integer attendanceStatus, Boolean printReport, Long orgId);

	

	List<CouncilMeetingMasterDto> getMeetingDetFromHistById(Long meetingId, Long orgId);

	void updateStatus(List<CouncilMemberCommitteeMasterDto> dto);
	
	void updateMeetingStatusWithMeetingID(Long meetingId, String flag);

}
