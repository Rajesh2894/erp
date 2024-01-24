package com.abm.mainet.council.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.MOMResolutionDto;

public interface ICouncilMOMService {

    public List<CouncilMeetingMasterDto> searchCouncilMOMMasterData(Long meetingTypeId, String meetingNo,
            Date fromDate, Date toDate, Long orgid, int langId);

    Boolean saveMeetingMOM(List<MOMResolutionDto> meetingMOMList, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO uploadDTO, Long deleteFileId, Employee ee);

    MOMResolutionDto getMeetingMOMDataById(Long proposalId, Long orgId);

    MOMResolutionDto getMOMByMeetingId(Long meetingId);

    public Boolean findMeetingIdForAttendance(Long meetingIdAtted, Long orgId);

}
