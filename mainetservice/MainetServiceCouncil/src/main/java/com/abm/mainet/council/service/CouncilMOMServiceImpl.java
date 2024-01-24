package com.abm.mainet.council.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dao.ICouncilMeetingMasterDao;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;
import com.abm.mainet.council.domain.MOMResolutionEntity;
import com.abm.mainet.council.domain.MOMResolutionHistoryEntity;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.MOMResolutionDto;
import com.abm.mainet.council.repository.MOMRepository;

@Service
public class CouncilMOMServiceImpl implements ICouncilMOMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMOMServiceImpl.class);

    @Autowired
    MOMRepository momRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ICouncilProposalMasterService proposalMasterService;

    @Autowired
    ICouncilMeetingMasterService meetingMasterService;

    @Autowired
    ICouncilMeetingMasterDao councilMeetingMasterDao;

    @Autowired
    private AuditService auditService;

    @Autowired
    private IFileUploadService fileUpload;

    @Transactional
    public Boolean saveMeetingMOM(List<MOMResolutionDto> meetingMOMList, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO uploadDTO, Long deleteFileId, Employee ee) {
        Boolean createMOMBoolean = true;
        Organisation organisation = new Organisation();
        organisation.setOrgid(ee.getOrganisation().getOrgid());
        try {
            List<Object> historyList = new ArrayList<Object>();
            List<MOMResolutionEntity> resolutionEntities = new ArrayList<>();
            for (MOMResolutionDto momDto : meetingMOMList) {
                momDto.setOrgId(organisation.getOrgid());
                // check momId is null or not in case of update the mom data
                MOMResolutionHistoryEntity history = new MOMResolutionHistoryEntity();
                if (momDto.getResoId() != null) {
                    momDto.setUpdatedBy(ee.getUpdatedBy());
                    momDto.setUpdatedDate(new Date());
                    momDto.setLgIpMacUpd(ee.getEmppiservername());
                    history.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());

                } else {
                    momDto.setCreatedBy(ee.getEmpId());
                    momDto.setCreatedDate(new Date());
                    momDto.setLgIpMac(ee.getEmppiservername());
                    history.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                    Date meetingDate = momDto.getMeetingDate();
                    // create resolutionNo dynamically
                    int year = Utility.getYearByDate(meetingDate);
                    String monthName = new SimpleDateFormat(MainetConstants.Council.MONTH_FORMAT).format(new Date())
                            .toUpperCase();
                    final Long sequence = ApplicationContextProvider.getApplicationContext()
                            .getBean(SeqGenFunctionUtility.class).generateSequenceNo(
                                    MainetConstants.Council.COUNCIL_MANAGEMENT, MainetConstants.Council.TB_COU_MEETING_MASTER,
                                    MainetConstants.Council.MEETING_NO, momDto.getOrgId(), MainetConstants.FlagC,
                                    momDto.getOrgId());
                    // format given by NILIMA Madam for res. no
                    String resolutionNo = year + MainetConstants.WINDOWS_SLASH + monthName + MainetConstants.WINDOWS_SLASH
                            + sequence;
                    momDto.setResolutionNo(resolutionNo);
                }

                MOMResolutionEntity momEntity = new MOMResolutionEntity();
                BeanUtils.copyProperties(momDto, momEntity);
                BeanUtils.copyProperties(momDto, history);
                resolutionEntities.add(momEntity);
                historyList.add(history);
            }
            try {
                momRepository.save(resolutionEntities);

                for (int i = 0; i < historyList.size(); i++) {
                    MOMResolutionHistoryEntity historyEntity = (MOMResolutionHistoryEntity) historyList.get(i);
                    historyEntity.setResoId(resolutionEntities.get(i).getResoId());
                }

                // file upload code set here
                Long meetingId = resolutionEntities.get(0).getMeetingId();
                if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
                    uploadDTO.setIdfId("MOM" + MainetConstants.WINDOWS_SLASH + meetingId);
                    fileUpload.doMasterFileUpload(attachmentList, uploadDTO);
                } else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
                    uploadDTO.setIdfId("MOM" + MainetConstants.WINDOWS_SLASH + meetingId);
                    fileUpload.doMasterFileUpload(attachmentList, uploadDTO);
                    List<Long> deletedDocFiles = new ArrayList<>();
                    deletedDocFiles.add(deleteFileId);
                    ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(deletedDocFiles,
                            resolutionEntities.get(0).getUpdatedBy(), MainetConstants.FlagD);
                }
                auditService.createHistoryForListObj(historyList);
            } catch (Exception exception) {
                LOGGER.error("Exception occured when creating history of resolution: ", exception);
            }

        } catch (FrameworkException e) {
            createMOMBoolean = false;
            throw new FrameworkException("Error when create MOM", e);
        }
        return createMOMBoolean;

    }

    @Override
    public MOMResolutionDto getMeetingMOMDataById(Long proposalId, Long orgId) {
        MOMResolutionEntity momEntity = momRepository.getMeetingMOMData(proposalId, orgId);
        MOMResolutionDto dto = new MOMResolutionDto();
        if (momEntity != null) {
            BeanUtils.copyProperties(momEntity, dto);
        } else {
            dto = null;
        }
        return dto;
    }

    @Transactional
    public List<CouncilMeetingMasterDto> searchCouncilMOMMasterData(Long meetingTypeId, String meetingNo,
            Date fromDate, Date toDate, Long orgId, int langId) {
        List<CouncilMeetingMasterDto> meetingMasterDtos = new ArrayList<CouncilMeetingMasterDto>();
        List<CouncilMeetingMasterEntity> meetingMasterEntities = councilMeetingMasterDao
                .searchCouncilMeetingMasterData(meetingTypeId, meetingNo, fromDate, toDate, orgId, null);
        List<Long> meetingIds = new ArrayList<>();
        meetingMasterEntities.forEach(meeting -> {
            meetingIds.add(meeting.getMeetingId());
        });
        // get data from MeetingMOM Table based on meeting ids
        if (!meetingIds.isEmpty()) {
            List<Long> momList = momRepository.findMOMByMeetingIdIn(meetingIds);
            momList.forEach(mom -> {
                CouncilMeetingMasterDto dto = meetingMasterService.getMeetingDataById(mom);
                // set meeting date and meeting time
                Date meetingDateTime = dto.getMeetingDate();
                String meetingDateDesc = Utility.dateToString(meetingDateTime);
                String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
                // dto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getMeetingTypeId(),organisation).getLookUpDesc());
                dto.setMeetingTypeName(CommonMasterUtility.getCPDDescription(dto.getMeetingTypeId(),
                        langId == 1 ? PrefixConstants.D2KFUNCTION.ENGLISH_DESC : PrefixConstants.D2KFUNCTION.REG_DESC, orgId));
                dto.setMeetingDateDesc(meetingDateDesc);
                dto.setMeetingTime(meetingTime);
                // set status of meeting
                String meetingStatus = MainetConstants.Council.STATUS_APPROVED;
                if (StringUtils.equals(dto.getMeetingStatus(), MainetConstants.Council.DB_STATUS_REJECT)) {
                    meetingStatus = MainetConstants.Council.STATUS_REJECTED;
                }
                // set actionBT status for momSummary screen
                // check in TB_CMT_COUNCIL_MEETING_MOM using meeting id record present or not
                dto.setActionBT(momRepository.checkMOMCreated(dto.getMeetingId(), orgId));
                dto.setMeetingStatus(meetingStatus);
                meetingMasterDtos.add(dto);
            });
        }

        return meetingMasterDtos;
    }

    @Override
    public MOMResolutionDto getMOMByMeetingId(Long meetingId) {
        List<MOMResolutionEntity> momEntity = momRepository.findMOMByMeetingId(meetingId);
        MOMResolutionDto dto = new MOMResolutionDto();
        if (momEntity != null) {
            BeanUtils.copyProperties(momEntity.get(0), dto);
        } else {
            dto = null;
        }
        return dto;
    }

    @Override
    public Boolean findMeetingIdForAttendance(Long meetingIdAtted, Long orgId) {
        return momRepository.checkMOMCreated(meetingIdAtted, orgId);
    }

}
