package com.abm.mainet.council.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.council.dao.ICouncilMemberCommitteeDao;
import com.abm.mainet.council.domain.CouncilMemberCommitteeMasterEntity;
import com.abm.mainet.council.domain.CouncilMemberCommitteeMasterHistoryEntity;
import com.abm.mainet.council.domain.CouncilMemberMasterEntity;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.repository.CouncilMemberCommitteMasterRepository;

@Service
public class CouncilMemberCommitteeMasterServiceImpl implements ICouncilMemberCommitteeMasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMemberCommitteeMasterServiceImpl.class);

    @Autowired
    private CouncilMemberCommitteMasterRepository councilCommiteeRepository;

    @Autowired
    private ICouncilMemberCommitteeDao committeeDao;

    @Autowired
    private AuditService auditService;

    @Transactional
    public boolean saveCouncilMemberCommitteeMaster(CouncilMemberCommitteeMasterDto councilDto,
            List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList,
            List<Long> removeIds) {
        // logic set for update and save
        Long memberCommmitteeId = councilDto.getMemberCommmitteeId();
        List<Object> historyList = new ArrayList<Object>();
        if (memberCommmitteeId != null) {

            for (int i = 0; i < councilMemberCommitteeMasterDtoList.size(); i++) {
                long memberId = councilMemberCommitteeMasterDtoList.get(i).getMemberId();
                long comDsgId = councilMemberCommitteeMasterDtoList.get(i).getComDsgId();
                Date expiryDate = councilMemberCommitteeMasterDtoList.get(i).getExpiryDate();
                String expiryReason = councilMemberCommitteeMasterDtoList.get(i).getExpiryReason();
                CouncilMemberCommitteeMasterEntity councilCommitteeEntity = new CouncilMemberCommitteeMasterEntity();
                BeanUtils.copyProperties(councilDto, councilCommitteeEntity);
                CouncilMemberMasterEntity member = new CouncilMemberMasterEntity();
                member.setCouId(memberId);
                councilCommitteeEntity.setMembers(member);
                // check primary key id present or not
                Long memberCommitteeId = councilMemberCommitteeMasterDtoList.get(i).getMemberCommmitteeId();
                councilCommitteeEntity.setMemberCommmitteeId(memberCommitteeId);
                if (memberCommitteeId == null) {
                    councilCommitteeEntity.setCreatedDate(new Date());
                }
                councilCommitteeEntity.setComDsgId(comDsgId);
                councilCommitteeEntity.setExpiryDate(expiryDate);
                councilCommitteeEntity.setExpiryReason(expiryReason);
                Date currentDate = new Date();
                if (expiryDate != null && expiryDate.before(currentDate)) {
                    councilCommitteeEntity.setMemberStatus(MainetConstants.STATUS.INACTIVE);
                } else {
                    councilCommitteeEntity.setMemberStatus(MainetConstants.STATUS.ACTIVE);
                }
                councilCommiteeRepository.save(councilCommitteeEntity);
                CouncilMemberCommitteeMasterHistoryEntity history = new CouncilMemberCommitteeMasterHistoryEntity();
                BeanUtils.copyProperties(councilCommitteeEntity, history);
                history.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                historyList.add(history);

            }
            if (removeIds != null && !removeIds.isEmpty()) {
                // delete old memberIds by using committeeTypeId
                councilCommiteeRepository.deleteEntityRecords(removeIds);
            }

        } else {
            // insertion
            /*
             * for (Long memberId : councilDto.getMembers()) { CouncilMemberCommitteeMasterEntity councilCommitteeEntity = new
             * CouncilMemberCommitteeMasterEntity(); CouncilMemberMasterEntity member = new CouncilMemberMasterEntity();
             * member.setCouId(memberId); councilCommitteeEntity.setMembers(member); BeanUtils.copyProperties(councilDto,
             * councilCommitteeEntity); councilCommiteeRepository.save(councilCommitteeEntity);
             * CouncilMemberCommitteeMasterHistoryEntity history = new CouncilMemberCommitteeMasterHistoryEntity();
             * BeanUtils.copyProperties(councilCommitteeEntity, history);
             * history.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus()); historyList.add(history); }
             */

            // update the old commiitteeType with INACTIVE STATUS by orgId
            updateInactiveStatusOfOldCommitteeType(MainetConstants.Council.INACTIVE_STATUS,
                    councilDto.getCommitteeTypeId(), councilDto.getOrgId());

            for (int j = 0; j < councilMemberCommitteeMasterDtoList.size(); j++) {
                CouncilMemberCommitteeMasterEntity councilCommitteeEntity = new CouncilMemberCommitteeMasterEntity();
                Long memberId = councilMemberCommitteeMasterDtoList.get(j).getMemberId();
                Long comDsgId = councilMemberCommitteeMasterDtoList.get(j).getComDsgId();
                CouncilMemberMasterEntity member = new CouncilMemberMasterEntity();
                member.setCouId(memberId);
                councilDto.setComDsgId(comDsgId);
                councilCommitteeEntity.setMembers(member);
                BeanUtils.copyProperties(councilDto, councilCommitteeEntity);
                councilCommiteeRepository.save(councilCommitteeEntity);
                CouncilMemberCommitteeMasterHistoryEntity history = new CouncilMemberCommitteeMasterHistoryEntity();
                BeanUtils.copyProperties(councilCommitteeEntity, history);
                history.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                history.setComDsgId(comDsgId);
                historyList.add(history);

            }
        }
        // create and update in Committee master history table
        try {
            auditService.createHistoryForListObj(historyList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when creating history of committeeType : ", exception);
        }
        return MainetConstants.SUCCESS;
    }

    @Transactional
    public List<CouncilMemberCommitteeMasterDto> searchCouncilMemberCommitteeMasterData(Long memberId, Long committeeTypeId,
            Long orgId, String status) {
        List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
        List<CouncilMemberCommitteeMasterEntity> committeeMasterEntities = committeeDao.searchCouncilMemberCommitteeData(memberId,
                committeeTypeId, orgId, status);
        committeeMasterEntities.forEach(committee -> {
            CouncilMemberCommitteeMasterDto dto = new CouncilMemberCommitteeMasterDto();
            BeanUtils.copyProperties(committee, dto);
            dto.setMemberName(committee.getMembers().getCouMemName());
            dto.setElecWardDesc(CommonMasterUtility
                    .getHierarchicalLookUp(committee.getMembers().getCouEleWZId1(), committee.getOrgId()).getLookUpDesc());
             //D#139057
             if (committee.getMembers().getCouEleWZId2() != null)
             dto.setElecZoneDesc(CommonMasterUtility.getHierarchicalLookUp(committee.getMembers().getCouEleWZId2(), committee.getOrgId()).getLookUpDesc());
            dto.setCouMemberTypeDesc(CommonMasterUtility.getCPDDescription(committee.getMembers().getCouMemberType().longValue(),
                    MainetConstants.MENU.E));
            dto.setPartyAFFDesc(CommonMasterUtility.getCPDDescription(committee.getMembers().getCouPartyAffilation().longValue(),
                    MainetConstants.MENU.E));
            dto.setCommitteeType(CommonMasterUtility.getCPDDescription(committee.getCommitteeTypeId().longValue(),
                    MainetConstants.MENU.E));

            councilMemberCommitteeMasterDtoList.add(dto);
        });
        return councilMemberCommitteeMasterDtoList;
    }

    @Transactional
    public List<CouncilMemberCommitteeMasterDto> fetchMappingMemberListByCommitteeTypeId(Long committeeTypeId,
            Boolean dataForMeetingCommitteeMember, String status, String memberStatus, Long orgId) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<CouncilMemberCommitteeMasterDto> committeMemberDtos = new ArrayList<>();
        List<CouncilMemberCommitteeMasterEntity> committeMemberEntities = new ArrayList<>();
        // based on dissolveDate fetch result from daoImpl
        if (dataForMeetingCommitteeMember) {
            // finding records based on Dissolve date
            committeMemberEntities = committeeDao.fetchCommitteeMemberByIdAndDissolveDate(committeeTypeId, orgId, status,
                    memberStatus);
        } else {
            // finding all the Records
            committeMemberEntities = councilCommiteeRepository.findAllByCommitteeTypeIdAndStatusAndOrgId(committeeTypeId, status,
                    orgId);
        }
        committeMemberEntities.forEach((committeMember) -> {
            CouncilMemberCommitteeMasterDto dto = new CouncilMemberCommitteeMasterDto();
            BeanUtils.copyProperties(committeMember, dto);
            Long memberId = committeMember.getMembers().getCouId();
            String memberName = committeMember.getMembers().getCouMemName();
            String elecWardDesc = CommonMasterUtility.getHierarchicalLookUp(committeMember.getMembers().getCouEleWZId1(), orgId)
                    .getLookUpDesc();
            /* String designation = designationService.findById(committeMember.getMembers().getCouDesgId()).getDsgname(); */
            String couMemberTypeDesc = CommonMasterUtility
                    .getCPDDescription(committeMember.getMembers().getCouMemberType().longValue(), MainetConstants.MENU.E);
            String partyAFFDesc = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(committeMember.getMembers().getCouPartyAffilation(),
                            organisation)
                    .getLookUpDesc();
            dto.setMemberName(memberName);
            dto.setMemberId(memberId);
            dto.setElecWardDesc(elecWardDesc);
            /* dto.setDesignation(designation); */
            dto.setCouMemberTypeDesc(couMemberTypeDesc);
            dto.setPartyAFFDesc(partyAFFDesc);
            /*
             * LookUp lookupMET = CommonMasterUtility.getNonHierarchicalLookUpObject(
             * committeMember.getMembers().getCouMemberType(), UserSession.getCurrent().getOrganisation());
             * dto.setOtherField(lookupMET.getOtherField());
             */

            LookUp lookupCDS = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    dto.getComDsgId(), organisation);
            dto.setSeqCDS(lookupCDS.getOtherField());
            committeMemberDtos.add(dto);
        });
        /*
         * Comparator<CouncilMemberCommitteeMasterDto> comparing = Comparator.comparing(
         * CouncilMemberCommitteeMasterDto::getOtherField, Comparator.nullsLast(Comparator.naturalOrder()));
         */
        Comparator<CouncilMemberCommitteeMasterDto> comparing = Comparator.comparing(CouncilMemberCommitteeMasterDto::getSeqCDS,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(committeMemberDtos, comparing);

        return committeMemberDtos;
    }

    @Transactional
    public CouncilMemberCommitteeMasterDto getCommitteeMemberData(Long committeeTypeId, Long orgId, String status) {
        CouncilMemberCommitteeMasterDto dto = new CouncilMemberCommitteeMasterDto();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        List<CouncilMemberCommitteeMasterEntity> entites = councilCommiteeRepository
                .findAllByCommitteeTypeIdAndStatusAndOrgId(committeeTypeId, status, orgId);
        CouncilMemberCommitteeMasterEntity entity = entites.isEmpty() ? null : entites.get(0);
        // check null because getting error like source must not null (BeanUtils)
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
            dto.setCommitteeType(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(entity.getCommitteeTypeId(), organisation)
                    .getLookUpDesc());
            LookUp lookupCPT = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    dto.getCommitteeTypeId(), organisation);
            dto.setOtherField(lookupCPT.getOtherField());
        } else {
            dto = null;
        }
        return dto;
    }

    @Transactional
    public List<String> fetchMemberNotExistInDissolveDate(List<Long> memberIds, Long committeeTypeId, String status, Long orgId) {
        List<String> messages = new ArrayList<>();
        memberIds.forEach(memberId -> {
            CouncilMemberCommitteeMasterEntity result = committeeDao.checkMemberExistInDissolveDate(memberId, committeeTypeId,
                    orgId, status);
            if (result != null) {
                String memberName = result.getMembers().getCouMemName();
                String message = "";
                // message ask to sir/madam
                message += memberName + MainetConstants.Council.CommitteeMapping.VALIDITY_ERROR_MSG;
                messages.add(message);
            }
        });
        return messages;
    }

    @Override
    public Boolean checkCommitteeTypeInDissolveDateByOrg(Long committeeTypeId, Long orgId, String status) {
        return councilCommiteeRepository.checkCommitteeTypeInDissolveDate(committeeTypeId, orgId,
                status);

    }

    @Transactional
    public void updateInactiveStatusOfOldCommitteeType(String status, Long committeeTypeId, Long orgId) {
        councilCommiteeRepository.updateInactiveStatusOfOldCommiitteeType(status, orgId, committeeTypeId);
    }

    // Defect #34123 ->this is to check member present against that committee or not
    @Override
    public Boolean checkMemberPresentInCommitteeByOrg(Long committeeTypeId, Long orgId, String status) {
        return councilCommiteeRepository.checkMemberPresentInCommitteeType(committeeTypeId, orgId, status);
    }

}
