package com.abm.mainet.council.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dao.ICouncilMeetingMasterDao;
import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterHistoryEntity;
import com.abm.mainet.council.domain.CouncilMeetingMemberEntity;
import com.abm.mainet.council.domain.CouncilMeetingMemberHistoryEntity;
import com.abm.mainet.council.domain.CouncilMemberMasterEntity;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.repository.CouncilMeetingMasterRepository;
import com.abm.mainet.council.repository.CouncilMeetingMemberRepository;
import com.abm.mainet.council.repository.CouncilMemberMasterRepository;
import com.abm.mainet.council.repository.MOMRepository;
import com.abm.mainet.council.ui.model.CouncilMeetingMasterModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class CouncilMeetingMasterServiceImpl implements ICouncilMeetingMasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMeetingMasterServiceImpl.class);

    @Autowired
    ICouncilMeetingMasterDao councilMeetingMasterDao;

    @Autowired
    CouncilMeetingMasterRepository councilMeetingMasterRepository;

    @Autowired
    CouncilMeetingMemberRepository councilMeetingMemberRepository;

    @Autowired
    CouncilMemberMasterRepository councilMemberMasterRepository;

    @Autowired
    MOMRepository momRepository;

    @Autowired
    DesignationService designationService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ICouncilAgendaMasterService agendaMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;
    
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;
    
    @Autowired
	IOrganisationService organisationService;

    @Transactional
    public boolean saveCouncilMeeting(CouncilMeetingMasterDto meetingDto) {
        try {
            CouncilMeetingMasterEntity councilMeetingMasterEntity = new CouncilMeetingMasterEntity();
            BeanUtils.copyProperties(meetingDto, councilMeetingMasterEntity);

            CouncilAgendaMasterEntity agendaEntity = new CouncilAgendaMasterEntity();
            agendaEntity.setAgendaId(meetingDto.getAgendaId());
            councilMeetingMasterEntity.setAgendaId(agendaEntity);
			if (meetingDto.getMeetingId() == null) {
				Organisation org = new Organisation();
				org.setOrgid(meetingDto.getOrgId());
				if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {

					String year = Utility.getCurrentFinancialYear();
					String fromYear = year.substring(2, 4);
					String toYear = year.substring(7);

					String finacialYear = fromYear + MainetConstants.operator.HIPHEN + toYear;

					LookUp lookupComittee = null;
					Long sequence = null;
					try {
						lookupComittee = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
								meetingDto.getMeetingTypeId(), meetingDto.getOrgId(), "MPT");
					} catch (Exception e) {
						LOGGER.error("Prefix MPT not found " + e);
					}
					sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.Council.COUNCIL_MANAGEMENT,
									MainetConstants.Council.TB_COU_MEETING_MASTER, MainetConstants.Council.MEETING_NO,
									meetingDto.getOrgId(), MainetConstants.FlagF, meetingDto.getMeetingTypeId());
					String meetingNo = finacialYear + MainetConstants.WINDOWS_SLASH + lookupComittee.getLookUpCode()
							+ MainetConstants.WINDOWS_SLASH + String.format("%05d", sequence);
					councilMeetingMasterEntity.setMeetingNo(meetingNo);
					meetingDto.setMeetingNo(meetingNo);
				} else {
					// 1st get year and month name using meetingDate
					int year = Utility.getYearByDate(meetingDto.getMeetingDate());
					String monthName = new SimpleDateFormat("MMM").format(new Date()).toUpperCase();
					final Long sequence = ApplicationContextProvider.getApplicationContext()
							.getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(MainetConstants.Council.COUNCIL_MANAGEMENT,
									MainetConstants.Council.TB_COU_MEETING_MASTER, MainetConstants.Council.MEETING_NO,
									meetingDto.getOrgId(), MainetConstants.FlagC, meetingDto.getOrgId());
					String meetingNo = year + MainetConstants.WINDOWS_SLASH + monthName + MainetConstants.WINDOWS_SLASH
							+ sequence;
					councilMeetingMasterEntity.setMeetingNo(meetingNo);
					meetingDto.setMeetingNo(meetingNo);
				}
			}
            String memberIdByCommitteeType = meetingDto.getMemberIdByCommitteeType();
            String splitIds[] = memberIdByCommitteeType.split(MainetConstants.operator.COMMA);
            // make List of memberId long array
            List<Long> memberIds = new ArrayList<>();
            for (int i = 0; i < splitIds.length; i++) {
                memberIds.add(Long.valueOf(splitIds[i]));
            }
            Boolean insertionInMeetingMemberTable = true;
            // fetch meeting id related memberId from existing DB
            List<Long> memberIdsDB = new ArrayList<>();
            List<CouncilMeetingMemberEntity> meetingMembers = councilMeetingMemberRepository
                    .findAllByMeetingId(meetingDto.getMeetingId());
            // push in long memberIds Array
            meetingMembers.forEach(meetingMember -> {
                memberIdsDB.add(meetingMember.getCouncilMemberId());
            });

            if (!meetingMembers.isEmpty()) {
                if (memberIds.containsAll(memberIdsDB) && memberIds.size() == memberIdsDB.size()) {
                    // not delete from table and nor insert in meeting member table because its a
                    // same member
                    insertionInMeetingMemberTable = false;
                } else {
                    // delete query based on meeting id in meeting member table
                    councilMeetingMemberRepository.deleteMeetingMember(meetingDto.getMeetingId());
                }
            }

            CouncilMeetingMasterEntity meetingMasterEntity = null;
            // check case is save or update
            if (meetingDto.getMeetingId() != null) {
                // update the meeting and meeting member
                councilMeetingMasterRepository.save(councilMeetingMasterEntity);
                CouncilMeetingMasterModel.setPrintMeetingId(councilMeetingMasterEntity.getMeetingId());

            } else {
                meetingMasterEntity = councilMeetingMasterRepository.save(councilMeetingMasterEntity);
                CouncilMeetingMasterModel.setPrintMeetingId(councilMeetingMasterEntity.getMeetingId());
            }
            // set meetingId at run time
            Long meetingId = meetingDto.getMeetingId();
            if (meetingMasterEntity != null) {
                meetingId = meetingMasterEntity.getMeetingId();
            }

            if (insertionInMeetingMemberTable) {
                for (int i = 0; i < splitIds.length; i++) {
                    long councilMemberId = Long.valueOf(splitIds[i]);
                    CouncilMeetingMemberEntity memberEntity = new CouncilMeetingMemberEntity();
                    // memberEntity.setMeetingId(meetingDto.getMeetingId());
                    memberEntity.setMeetingId(meetingId);
                    memberEntity.setAttendanceStatus(0);
                    memberEntity.setCouncilMemberId(councilMemberId);
                    memberEntity.setCreatedBy(meetingDto.getCreatedBy());
                    memberEntity.setCreatedDate(meetingDto.getCreatedDate());
                    memberEntity.setUpdatedBy(meetingDto.getUpdatedBy());
                    memberEntity.setUpdatedDate(memberEntity.getUpdatedDate());
                    memberEntity.setLgIpMac(meetingDto.getLgIpMac());
                    memberEntity.setOrgId(meetingDto.getOrgId());
                    councilMeetingMemberRepository.save(memberEntity);
                }
            } else {
                // updated meeting member by meetingId
                councilMeetingMemberRepository.updateMeetingMember(meetingDto.getUpdatedBy(),
                        meetingDto.getLgIpMacUpd(), meetingDto.getMeetingId());
            }

            // create and update in Meeting Master history table using meeting_Id
            try {
                CouncilMeetingMasterHistoryEntity meetingHistory = new CouncilMeetingMasterHistoryEntity();
                if (meetingDto.getReason()!=null && !meetingDto.getReason().isEmpty())
                    meetingHistory.setReason(meetingDto.getReason());
                if (meetingDto.getUpdatedBy() == null) {
                    meetingHistory.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                } else {
                    meetingHistory.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                auditService.createHistory(councilMeetingMasterEntity, meetingHistory);
            } catch (Exception exception) {
                LOGGER.error("Exception occured when creating history : ", exception);
            }
        } catch (FrameworkException ex) {
            throw new FrameworkException("Error when save council meeting ", ex);
        }
        sendSmsEmail(meetingDto);
        return MainetConstants.SUCCESS;
    }

    @Transactional
    public List<CouncilMeetingMasterDto> searchCouncilMeetingMasterData(Long meetingTypeId, String meetingNo,
            Date fromDate, Date toDate, Long orgid, String orderBy) {
        List<CouncilMeetingMasterDto> meetingMasterDtos = new ArrayList<CouncilMeetingMasterDto>();
        List<CouncilMeetingMasterEntity> meetingMasterEntities = councilMeetingMasterDao
                .searchCouncilMeetingMasterData(meetingTypeId, meetingNo, fromDate, toDate, orgid, orderBy);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        meetingMasterEntities.forEach(mastEntity -> {
            CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
            BeanUtils.copyProperties(mastEntity, dto);
            // set meeting date and meeting time
            Date meetingDateTime = mastEntity.getMeetingDate();
            String meetingDateDesc = Utility.dateToString(meetingDateTime);
            String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
            dto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(mastEntity.getMeetingTypeId(),
                    organisation).getLookUpDesc());
            dto.setMeetingDateDesc(meetingDateDesc);
            dto.setMeetingTime(meetingTime);
            // set status of meeting
            String meetingStatus = MainetConstants.Council.STATUS_APPROVED;
            if (StringUtils.equals(mastEntity.getMeetingStatus(), MainetConstants.Council.DB_STATUS_REJECT)) {
                meetingStatus = MainetConstants.Council.STATUS_REJECTED;
            }
            // based on actionBT hide message icon from summary screen of meeting
            int compareDate = Utility.compareDates(meetingDateTime, new Date());
            Boolean actionBT = true;
            if (compareDate < 0) {
                actionBT = false; // hide Message Icon from summary screen
            }
            dto.setActionBT(actionBT);
            dto.setMeetingStatus(meetingStatus);
            meetingMasterDtos.add(dto);
        });
        return meetingMasterDtos;
    }

    @Transactional
    public CouncilMeetingMasterDto getMeetingDataById(Long meetingId) {
        CouncilMeetingMasterDto meetingMasterDto = new CouncilMeetingMasterDto();

        // get record using JPA repository
        CouncilMeetingMasterEntity meeting = councilMeetingMasterRepository.findOne(meetingId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(meeting.getOrgId());
        CouncilAgendaMasterDto agendaMasterDto = new CouncilAgendaMasterDto();
        CouncilAgendaMasterEntity agendaMasterEntity = new CouncilAgendaMasterEntity();
        // agendaId is nothing but object here if any doubt check in meetingMasterEntity
        agendaMasterEntity = meeting.getAgendaId();
        // set agenda object
        agendaMasterDto = agendaMasterService.getCouncilAgendaMasterByAgendaId(agendaMasterEntity.getAgendaId());
        BeanUtils.copyProperties(meeting, meetingMasterDto);
        agendaMasterDto.setCommitteeType(CommonMasterUtility
                .getNonHierarchicalLookUpObject(meeting.getAgendaId().getCommitteeTypeId(),
                        organisation)
                .getLookUpDesc());
        meetingMasterDto.setAgendaMasterDto(agendaMasterDto);
        meetingMasterDto.setAgendaId(meeting.getAgendaId().getAgendaId());
        // Date meetingdDate = meeting.getMeetingDate();
        // String meetingDateDesc = null;
        // if (meetingdDate != null) {
        // DateFormat sdf = new SimpleDateFormat(MainetConstants.Council.Meeting.MEETING_DATE_FORMATE);
        // meetingDateDesc = sdf.format(meetingdDate);
        // }
        Date meetingDateTime = meeting.getMeetingDate();
        String meetingDateDesc = Utility.dateToString(meetingDateTime);
        String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
        meetingMasterDto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(meeting.getMeetingTypeId(),
                organisation).getLookUpDesc());
        meetingMasterDto.setMeetingDateDesc(meetingDateDesc);
        meetingMasterDto.setMeetingTime(meetingTime);
        meetingMasterDto.setMeetingDateDesc(meetingDateDesc);
        return meetingMasterDto;
    }

    @Transactional
    public List<CouncilMemberCommitteeMasterDto> fetchMeetingMemberListByMeetingId(Long meetingId, Long orgid) {
        List<CouncilMemberCommitteeMasterDto> meetingMembers = new ArrayList<>();
        List<CouncilMeetingMemberEntity> meetingMemberEntities = councilMeetingMemberRepository
                .findAllByMeetingId(meetingId);
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        meetingMemberEntities.forEach((meetingMember) -> {
            CouncilMemberCommitteeMasterDto dto = new CouncilMemberCommitteeMasterDto();
            Long memberId = meetingMember.getCouncilMemberId();
            dto.setMemberId(memberId);
           
            BeanUtils.copyProperties(meetingMember, dto);
            dto.setAttendStatus(String.valueOf(dto.getAttendanceStatus()));
            // set data for attenders member in addAttendanceMaster.jsp page
            CouncilMemberMasterEntity memberMaster = councilMemberMasterRepository.findOne(memberId);
            dto.setMemberName(memberMaster.getCouMemName());
            dto.setElecWardDesc(CommonMasterUtility.getHierarchicalLookUp(memberMaster.getCouEleWZId1(), memberMaster.getOrgId())
                    .getLookUpDesc());
            /* dto.setDesignation(designationService.findById(memberMaster.getCouDesgId()).getDsgname()); */
            dto.setCouMemberTypeDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(memberMaster.getCouMemberType(),
                            organisation).getLookUpDesc());
            dto.setQualificationDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(memberMaster.getCouEduId()),
                    organisation).getLookUpDesc());
            /*
             * As per samadhan sir instruction removed party AFF
             * dto.setPartyAFFDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(memberMaster.getCouPartyAffilation(),
             * UserSession.getCurrent().getOrganisation()).getLookUpDesc());
             */
            meetingMembers.add(dto);
        });
        return meetingMembers;
    }

    @Transactional
    public List<CouncilMeetingMasterDto> searchAttendanceMasterData(Long meetingTypeId, String meetingNo, Date fromDate,
            Date toDate, Long orgId) {
        List<CouncilMeetingMasterDto> dtoList = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        Long meetingId = null, totalMember = 0L, memberPresent = 0L, memberAbsent = 0L, memberLeave=0L;
        if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0 || StringUtils.isNotEmpty(meetingNo)) {
            // here problem occur if more than one row present for meetingType so neglect this we need to update the meeting if
            // completed with status 'C' and orgId
            CouncilMeetingMasterEntity masterEntity = new CouncilMeetingMasterEntity();
            // masterEntity = councilMeetingMasterRepository.getMeetingDetails(meetingTypeId, "A", orgId);
            // if getting meetingType search only than 1st find out meetingIds
            List<CouncilMeetingMasterEntity> meetingList = new ArrayList<>();
            if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0 && StringUtils.isEmpty(meetingNo)) {
                meetingList = councilMeetingMasterRepository
                        .findAllMeetingsByMeetingTypeIdAndMeetingStatusAndOrgIdOrderByMeetingIdDesc(meetingTypeId,
                                MainetConstants.Common_Constant.ACTIVE_FLAG, orgId);
                makeDataBasedOnMemberAttend(dtoList, meetingList, fromDate, toDate, orgId);
            } else {
                masterEntity = councilMeetingMasterDao.getMeetingDetailsByCondition(null, meetingTypeId, meetingNo, orgId);
                CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
                if (masterEntity != null) {
                    meetingId = masterEntity.getMeetingId();
                    totalMember = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                            MainetConstants.Council.Meeting.ATTENDANCE_ALL);
                    memberPresent = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                            MainetConstants.Council.Meeting.ATTENDANCE_STATUS_TRUE);
                    memberAbsent = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                            MainetConstants.Council.Meeting.ATTENDANCE_STATUS_FALSE);
                    memberLeave=(totalMember-(memberPresent+memberAbsent));
                    dto.setMeetingId(masterEntity.getMeetingId());
                    // set meeting date and meeting time
                    Date meetingDateTime = masterEntity.getMeetingDate();
                    String meetingDateDesc = Utility.dateToString(meetingDateTime);
                    dto.setMeetingDateDesc(meetingDateDesc);
                    // dto.setMeetingDate(masterEntity.getMeetingDate());
                    dto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(masterEntity.getMeetingTypeId(),
                            organisation).getLookUpDesc());
                    dto.setTotalMember(totalMember);
                    dto.setMemberPresent(memberPresent);
                    dto.setMemberAbsent(memberAbsent);
                    dto.setMemberLeave(memberLeave);
                    String meetingStatus = masterEntity.getMeetingStatus();
                    if (StringUtils.equals(meetingStatus, MainetConstants.Council.DB_STATUS_APPROVED)) {
                        meetingStatus = ApplicationSession.getInstance().getMessage("council.approved");
                    } else if (StringUtils.equals(meetingStatus, MainetConstants.Council.DB_STATUS_REJECT)) {
                        meetingStatus = ApplicationSession.getInstance().getMessage("council.rejected");
                    } else {
                        meetingStatus = ApplicationSession.getInstance().getMessage("council.complete");

                    }
                    dto.setMeetingStatus(meetingStatus);
                    dtoList.add(dto);
                }
            }
        } else {
            // this will execute when summary page load
            // first find all meetingIds by oggId from TB_CMT_COUNCIL_MEETING_MAST
            List<CouncilMeetingMasterEntity> meetingList = councilMeetingMasterRepository.findAllByOrgIdOrderByMeetingIdDesc(orgId);
            makeDataBasedOnMemberAttend(dtoList, meetingList, fromDate, toDate, orgId);
        }
        return dtoList;
    }

    // create common method for search based on search filter
    void makeDataBasedOnMemberAttend(List<CouncilMeetingMasterDto> dtoList, List<CouncilMeetingMasterEntity> meetingList,
            Date fromDate,
            Date toDate, Long orgId) {
        Long meetingId = null, totalMember = 0L, memberPresent = 0L, memberAbsent = 0L,memberLeave=0L;
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        for (CouncilMeetingMasterEntity entity : meetingList) {
            meetingId = entity.getMeetingId();
            boolean meetingAttend = councilMeetingMemberRepository.checkMemberAttendMeeting(meetingId,
                    MainetConstants.Council.PRESENT_STATUS);
            if (meetingAttend) {
                totalMember = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                        MainetConstants.Council.Meeting.ATTENDANCE_ALL);
                if (totalMember != 0) {
                    memberPresent = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                            MainetConstants.Council.Meeting.ATTENDANCE_STATUS_TRUE);
                    memberAbsent = councilMeetingMasterDao.getMemberCountByCondition(meetingId, fromDate, toDate, orgId,
                            MainetConstants.Council.Meeting.ATTENDANCE_STATUS_FALSE);
                    memberLeave=(totalMember-(memberPresent+memberAbsent));
                    CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
                    dto.setMeetingId(entity.getMeetingId());
                    // set meeting date and meeting time
                    Date meetingDateTime = entity.getMeetingDate();
                    String meetingDateDesc = Utility.dateToString(meetingDateTime);
                    dto.setMeetingDateDesc(meetingDateDesc);
                    dto.setMeetingTypeName(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(entity.getMeetingTypeId(),
                                    organisation)
                            .getLookUpDesc());
                    dto.setTotalMember(totalMember);
                    dto.setMemberPresent(memberPresent);
                    dto.setMemberAbsent(memberAbsent);
                    dto.setMemberLeave(memberLeave);
                    String meetingStatus = entity.getMeetingStatus();
                    if (StringUtils.equals(meetingStatus, MainetConstants.Council.DB_STATUS_APPROVED)) {
                        meetingStatus = MainetConstants.Council.STATUS_APPROVED;
                    } else if (StringUtils.equals(meetingStatus, MainetConstants.Council.DB_STATUS_REJECT)) {
                        meetingStatus = MainetConstants.Council.STATUS_REJECTED;
                    } else {
                        meetingStatus = MainetConstants.Council.STATUS_COMPLETED;
                    }
                    dto.setMeetingStatus(meetingStatus);
                    dtoList.add(dto);
                }
            }
        }
    }

    @Transactional
    public void updateAttendanceStatusInMeetingMember(List<Long> memberIds, Long meetingId,
            List<DocumentDetailsVO> attachmentList,
            FileUploadDTO fileUploadDTO, Long deleteFileId, Long orgId, String lgIpMac) {
        try {
            // insert and update history table
            List<Long> updateIds = new ArrayList<>();
            // 1st find list of meeting member who attend meeting

            List<CouncilMeetingMemberEntity> meetingMembers = councilMeetingMemberRepository
                    .findMembersWithAttendanceStatusByMeetingId(meetingId, MainetConstants.Council.PRESENT_STATUS);

            List<Object> historyList = new ArrayList<Object>();
            if (meetingMembers.isEmpty()) {
                // Execute when 1st Time Attendance Taken
                for (int i = 0; i < memberIds.size(); i++) {
                    // history table insert
                    CouncilMeetingMemberHistoryEntity history = new CouncilMeetingMemberHistoryEntity();
                    // find out record from TB_CMT_COUNCIL_MEETING_MEMBER using meetingId and memberId
                    CouncilMeetingMemberEntity entity = councilMeetingMemberRepository.getMeetingMemberByIds(meetingId,
                            memberIds.get(i));
                    BeanUtils.copyProperties(entity, history);
                    // data setting because DTO name mismatch and created updated field set (because in meeting member table only
                    // update attendance status)
                    history.setUpdatedBy(null);
                    history.setUpdatedDate(null);
                    history.setLgIpMacUpd(null);
                    history.setMeetingMemberId(entity.getId());
                    history.setMemberId(entity.getCouncilMemberId());
                    history.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                    history.setAttendanceStatus(MainetConstants.Council.PRESENT_STATUS);
                    history.setCreatedBy(entity.getCreatedBy());
                    history.setCreatedDate(new Date());
                    history.setLgIpMac(entity.getLgIpMac());
                    historyList.add(history);
                }
            } else {
                // hit when 2nd time attendance update
                // logic for history table data set like Attendance status 0
                List<Long> dbMemberIds = new ArrayList<>();// present member
                meetingMembers.forEach(member -> {
                    dbMemberIds.add(member.getCouncilMemberId());
                });

                // Prepare a union
                List<Long> union = new ArrayList<Long>(dbMemberIds);
                union.addAll(memberIds);
                // Prepare an intersection
                List<Long> intersection = new ArrayList<Long>(dbMemberIds);
                intersection.retainAll(memberIds);
                // Subtract the intersection from the union
                union.removeAll(intersection);
                for (Long n : union) {
                    updateIds.add(n);
                }

                for (int i = 0; i < memberIds.size(); i++) {
                    // history table insert
                    CouncilMeetingMemberHistoryEntity history = new CouncilMeetingMemberHistoryEntity();
                    // find out record from TB_CMT_COUNCIL_MEETING_MEMBER using meetingId and memberId
                    CouncilMeetingMemberEntity entity = councilMeetingMemberRepository.getMeetingMemberByIds(meetingId,
                            memberIds.get(i));
                    BeanUtils.copyProperties(entity, history);
                    history.setUpdatedBy(null);
                    history.setUpdatedDate(null);
                    history.setLgIpMacUpd(null);
                    history.setMeetingMemberId(entity.getId());
                    history.setMemberId(entity.getCouncilMemberId());
                    history.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                    history.setAttendanceStatus(MainetConstants.Council.PRESENT_STATUS);
                    history.setCreatedBy(entity.getCreatedBy());
                    history.setCreatedDate(new Date());
                    history.setLgIpMac(entity.getLgIpMac());
                    historyList.add(history);
                }

            }

            // file upload code set here
            if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
                fileUploadDTO.setIdfId("ATD" + MainetConstants.WINDOWS_SLASH + meetingId);
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
            } else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
                fileUploadDTO.setIdfId("ATD" + MainetConstants.WINDOWS_SLASH + meetingId);
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
                List<Long> deletedDocFiles = new ArrayList<>();
                deletedDocFiles.add(deleteFileId);
                ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(deletedDocFiles,
                        null, MainetConstants.FlagD);
            }

            // 1st update the attendance status with status = 0 by meetingId than after update based on memberIds and meetingId
            councilMeetingMemberRepository.updateAttendanceStatusByMeetingId(MainetConstants.Council.ABSENT_STATUS, meetingId);
            councilMeetingMemberRepository.updateAttendanceStatusByIds(MainetConstants.Council.PRESENT_STATUS, memberIds,
                    meetingId, orgId, lgIpMac);

            for (int i = 0; i < updateIds.size(); i++) {
                // history table insert
                CouncilMeetingMemberHistoryEntity history = new CouncilMeetingMemberHistoryEntity();
                // find out record from TB_CMT_COUNCIL_MEETING_MEMBER using meetingId and memberId with present status
                CouncilMeetingMemberEntity entity = councilMeetingMemberRepository.getMeetingMemberByIdsByStatus(meetingId,
                        updateIds.get(i), MainetConstants.Council.ABSENT_STATUS);
                if (entity != null) {
                    BeanUtils.copyProperties(entity, history);
                    // data setting because DTO name mismatch and created updated field set (because in meeting member table only
                    // update attendance status)
                    history.setMeetingMemberId(entity.getId());
                    history.setMemberId(entity.getCouncilMemberId());
                    history.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    history.setAttendanceStatus(MainetConstants.Council.ABSENT_STATUS);
                    history.setCreatedBy(entity.getCreatedBy());
                    history.setCreatedDate(new Date());
                    history.setLgIpMac(entity.getLgIpMac());
                    history.setUpdatedBy(entity.getUpdatedBy());
                    history.setUpdatedDate(new Date());
                    history.setLgIpMacUpd(entity.getLgIpMacUpd());
                    historyList.add(history);
                }
            }
            try {
                auditService.createHistoryForListObj(historyList);
            } catch (Exception exception) {
                LOGGER.error("Exception occured when create history of meeting member attendance ", exception);
            }
        } catch (Exception e) {
            throw new FrameworkException("exception when member attendance for meeting ", e);
        }
    }

    @Override
    public List<CouncilMeetingMasterDto> fetchMeetingDetailsById(Long meetingId, Long orgId) {
        List<CouncilMeetingMasterEntity> masterEntities = councilMeetingMasterRepository.findAllBymeetingId(meetingId);
        List<CouncilMeetingMasterDto> dtoEntities = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        masterEntities.forEach(mastEntity -> {
            CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
            BeanUtils.copyProperties(mastEntity, dto);
            // set meeting date and meeting time
            Date meetingDateTime = mastEntity.getMeetingDate();
            String meetingDateDesc = Utility.dateToString(meetingDateTime);
            String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
            dto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(mastEntity.getMeetingTypeId(),
                    organisation).getLookUpDesc());
            dto.setMeetingDateDesc(meetingDateDesc);
            dto.setMeetingTime(meetingTime);
            dtoEntities.add(dto);
        });
        return dtoEntities;
    }

    @Override
    public boolean checkMemberAttendMeeting(Long meetingId, Integer attendanceStatus) {
        return councilMeetingMemberRepository.checkMemberAttendMeeting(meetingId, attendanceStatus);
    }

    @Override
    public List<CouncilMeetingMasterDto> fetchMeetingsByMeetingTypeId(Long meetingTypeId, Long orgId) {
        List<CouncilMeetingMasterDto> meetingDtos = new ArrayList<CouncilMeetingMasterDto>();
        try {
            // 1st find out meetingId using meetingTypeId in TB_CMT_COUNCIL_MEETING_MEMBER
            List<CouncilMeetingMasterEntity> meetings = councilMeetingMasterRepository
                    .findAllMeetingsByMeetingTypeIdAndMeetingStatusAndOrgIdOrderByMeetingIdDesc(
                            meetingTypeId, MainetConstants.Common_Constant.ACTIVE_FLAG,
                            orgId);
            meetings.forEach(meeting -> {
                Long meetingId = meeting.getMeetingId();
                boolean meetingAttend = councilMeetingMemberRepository.checkMemberAttendMeeting(meetingId,
                        MainetConstants.Council.PRESENT_STATUS);
                if (meetingAttend) {
                    // CHECK in TB_CMT_COUNCIL_MEETING_MOM
                    boolean momPresent = momRepository.checkMOMCreated(meetingId, meeting.getOrgId());
                    if (!momPresent) {
                        CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
                        BeanUtils.copyProperties(meeting, dto);
                        meetingDtos.add(dto);
                    }
                }
            });

        } catch (Exception e) {
            throw new FrameworkException("Exception occured when fetching meeting no : ", e);
        }
        return meetingDtos;
    }

    @Override
    public boolean checkAgendaPresentInMeeting(Long agendaId, String meetingStatus, Long orgId) {
        return councilMeetingMasterRepository.checkAgendaPresentInMeeting(agendaId, meetingStatus,
                orgId);

    }

    @Override
    public List<CouncilMeetingMasterDto> fetchPendingMeetingsMeetingTypeId(Long meetingTypeId, Long orgId) {
        List<CouncilMeetingMasterDto> meetingDtos = new ArrayList<CouncilMeetingMasterDto>();
        try {
            // 1st find out meetingId using meetingTypeId in TB_CMT_COUNCIL_MEETING_MEMBER
        	Organisation org = organisationService.getOrganisationById(orgId);
        	List<CouncilMeetingMasterEntity> meetings = null;
        	if(Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.TSCL)) {
        			meetings = councilMeetingMasterRepository
	                    .findAllMeetingsByMeetingTypeIdAndOrgId(meetingTypeId, orgId);
        	}
        	else {
	            	meetings = councilMeetingMasterRepository
	                    .findAllMeetingsByMeetingTypeIdAndMeetingStatusAndOrgIdOrderByMeetingIdDesc(
	                            meetingTypeId, MainetConstants.Common_Constant.ACTIVE_FLAG,
	                            orgId);
        	}
            meetings.forEach(meeting -> {
                Long meetingId = meeting.getMeetingId();
                // CHECK pending attendance in TB_CMT_COUNCIL_MEETING_MEMBER
                boolean memberPresent = councilMeetingMemberRepository.checkMemberAttendMeeting(meetingId,
                        MainetConstants.Council.PRESENT_STATUS);
                if (!memberPresent) {
                    CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
                    BeanUtils.copyProperties(meeting, dto);
                    meetingDtos.add(dto);
                }
            });
        } catch (Exception e) {
            throw new FrameworkException("Exception occured when fetching pending meetings : ", e);
        }
        return meetingDtos;
    }

    @Override
    public List<CouncilMemberCommitteeMasterDto> fetchMeetingPresentMemberListByMeetingId(Long meetingId,
            Integer attendanceStatus, Boolean printReport, Long orgId) {
        List<CouncilMemberCommitteeMasterDto> meetingMembers = new ArrayList<>();
        List<CouncilMeetingMemberEntity> meetingMemberEntities = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        // doing this because at the time of print report of agenda than find members based on meetingId only not by attendance
        // Status
        if (printReport) {
            meetingMemberEntities = councilMeetingMemberRepository.findMembersByMeetingId(meetingId);
        } else {
            meetingMemberEntities = councilMeetingMemberRepository
                    .findMembersWithAttendanceStatusByMeetingId(meetingId, attendanceStatus);
        }
        meetingMemberEntities.forEach((meetingMember) -> {
            CouncilMemberCommitteeMasterDto dto = new CouncilMemberCommitteeMasterDto();
            Long memberId = meetingMember.getCouncilMemberId();
            dto.setMemberId(memberId);
            BeanUtils.copyProperties(meetingMember, dto);
            // set data for attenders member in addAttendanceMaster.jsp page
            CouncilMemberMasterEntity memberMaster = councilMemberMasterRepository.findOne(memberId);
            dto.setMemberName(memberMaster.getCouMemName());
            dto.setElecWardDesc(CommonMasterUtility.getHierarchicalLookUp(memberMaster.getCouEleWZId1(), memberMaster.getOrgId())
                    .getLookUpDesc());
            dto.setCouMemberTypeDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(memberMaster.getCouMemberType(),
                            organisation).getLookUpDesc());
            dto.setQualificationDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(memberMaster.getCouEduId()),
                    organisation).getLookUpDesc());
            dto.setPartyAFFDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(memberMaster.getCouPartyAffilation(),
                    organisation).getLookUpDesc());
            dto.setMemberAddress(memberMaster.getCouAddress());
            LookUp lookupMET = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    memberMaster.getCouMemberType(), organisation);
            dto.setOtherField(lookupMET.getOtherField());
            meetingMembers.add(dto);
        });

        Comparator<CouncilMemberCommitteeMasterDto> comparing = Comparator.comparing(
                CouncilMemberCommitteeMasterDto::getOtherField,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(meetingMembers, comparing);
        return meetingMembers;
    }

    @Override
    public List<CouncilMeetingMasterDto> getMeetingDetFromHistById(Long meetingId, Long orgId) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<CouncilMeetingMasterDto> meetingDtos = new ArrayList<CouncilMeetingMasterDto>();
        List<CouncilMeetingMasterHistoryEntity> meetingEntity = councilMeetingMasterRepository
                .findAllMeetingDetFromHist(meetingId, orgId);
        meetingEntity.forEach(meetingHist -> {
            CouncilMeetingMasterDto dto = new CouncilMeetingMasterDto();
            BeanUtils.copyProperties(meetingHist, dto);
            Date meetingDateTime = meetingHist.getMeetingDate();
            String meetingDateDesc = Utility.dateToString(meetingDateTime);
            String meetingTime = new SimpleDateFormat(MainetConstants.HOUR_FORMAT).format(meetingDateTime);
            dto.setMeetingTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(meetingHist.getMeetingTypeId(),
                    organisation).getLookUpDesc());
            dto.setMeetingDateDesc(meetingDateDesc);
            dto.setMeetingTime(meetingTime);
            dto.setReason(meetingHist.getReason());
            meetingDtos.add(dto);
        });
        return meetingDtos;
    }
    
    @Override
    @Transactional
    public void updateStatus(List<CouncilMemberCommitteeMasterDto> dto) {
    	 for (int i = 0; i < dto.size(); i++) {
    		 if(dto.get(i).getAttendStatus()!=null)
    			 
    	councilMeetingMemberRepository.updateAttendanceStatusByMemberId(Integer.valueOf(dto.get(i).getAttendStatus()),dto.get(i).getId());
    	 }
    }
    
    public void sendSmsEmail(CouncilMeetingMasterDto meetingDto) {
    	List<CouncilMemberCommitteeMasterDto> members = fetchMeetingPresentMemberListByMeetingId(meetingDto.getMeetingId(), MainetConstants.Council.ABSENT_STATUS, true,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        for (CouncilMemberCommitteeMasterDto member : members) {
            CouncilMemberMasterDto memberDto = councilMemberMasterService.getCouncilMemberMasterByCouId(member.getMemberId());
            // Send Email and SMS
            final SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setMobnumber(String.valueOf(memberDto.getCouMobNo()));
            dto.setAppNo(meetingDto.getMeetingNo());
            dto.setServName(MainetConstants.Council.Meeting.MEETING_SERVICE_NAME);
            dto.setAppName(MainetConstants.Council.Meeting.APPLICATION_MEETING_INVITATION);
            dto.setDate(meetingDto.getMeetingDate());
            dto.setPlace(meetingDto.getMeetingPlace());
            dto.setType(meetingDto.getMeetingTypeName());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setEmail(memberDto.getCouEmail());
            String menuUrl = MainetConstants.Council.MEETING_MASTER_URL;
            Organisation org = new Organisation();
            org.setOrgid(meetingDto.getOrgId());
            int langId = UserSession.getCurrent().getLanguageId();
            iSMSAndEmailService.sendEmailSMS(MainetConstants.Council.COUNCIL_MANAGEMENT, menuUrl,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
        }
    }
    
    @Transactional
	public void updateMeetingStatusWithMeetingID(Long meetingId, String flag) {			
    	councilMeetingMasterRepository.updateMeetingStatusWithMeetingID(meetingId, flag);
	}

}
