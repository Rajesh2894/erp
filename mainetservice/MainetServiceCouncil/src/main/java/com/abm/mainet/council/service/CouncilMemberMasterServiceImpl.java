package com.abm.mainet.council.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.council.dao.ICouncilMemberMasterDao;
import com.abm.mainet.council.domain.CouncilMemberMasHistoryEntity;
import com.abm.mainet.council.domain.CouncilMemberMasterEntity;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.repository.CouncilMemberMasterRepository;

@Service
public class CouncilMemberMasterServiceImpl implements ICouncilMemberMasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMemberMasterServiceImpl.class);

    @Autowired
    private CouncilMemberMasterRepository councilMemberMasterRepository;

    @Autowired
    private ICouncilMemberMasterDao councilMemberMasterDao;

    @Autowired
    private AuditService auditService;

    @Autowired
    private IFileUploadService fileUpload;

    @Transactional
    public boolean saveCouncil(CouncilMemberMasterDto councilDto, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO fileUploadDTO, Long deleteFileId) {
        try {

            CouncilMemberMasterEntity counilMasterEntity = new CouncilMemberMasterEntity();
            BeanUtils.copyProperties(councilDto, counilMasterEntity);

            CouncilMemberMasterEntity master = councilMemberMasterRepository.save(counilMasterEntity);

            // file upload code set here
            if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId == null) {
                fileUploadDTO.setIdfId(MainetConstants.Council.MemberMaster.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                        + master.getCouId());
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
            } else if (attachmentList != null && !attachmentList.isEmpty() && deleteFileId != null) {
                fileUploadDTO.setIdfId(MainetConstants.Council.MemberMaster.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                        + master.getCouId());
                fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
                List<Long> deletedDocFiles = new ArrayList<>();
                deletedDocFiles.add(deleteFileId);
                ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(deletedDocFiles,
                        councilDto.getUpdatedBy(), MainetConstants.FlagD);
            }

            // create and update in member master history table using councilId
            try {
                CouncilMemberMasHistoryEntity memMasHistory = new CouncilMemberMasHistoryEntity();
                if (councilDto.getUpdatedBy() == null) {
                    memMasHistory.setHistoryStatus(MainetConstants.InsertMode.ADD.getStatus());
                } else {
                    memMasHistory.setHistoryStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                auditService.createHistory(master, memMasHistory);
            } catch (Exception exception) {
                LOGGER.error("Exception occured when creating history : ", exception);
            }

        } catch (Exception e) {
            throw new FrameworkException("exception occured when create member master", e);
        }
        return MainetConstants.SUCCESS;
    }

    public List<CouncilMemberMasterDto> fetchAll(Long orgid) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        List<CouncilMemberMasterEntity> memberEntityList = councilMemberMasterRepository.fetchAll(orgid);
        List<CouncilMemberMasterDto> memberDtoList = new ArrayList<>();
        for (CouncilMemberMasterEntity entity : memberEntityList) {
            CouncilMemberMasterDto dto = new CouncilMemberMasterDto();
            BeanUtils.copyProperties(entity, dto);
            LookUp lookupMET = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    dto.getCouMemberType(), organisation);
            dto.setOtherField(lookupMET.getOtherField());
            memberDtoList.add(dto);
        }
        return memberDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouncilMemberMasterDto> searchCouncilMasterData(String couMemName, Long couMemberType, Long couPartyAffilation,
            Long orgid, Long couEleWZId1, Long couEleWZId2, int langId) {
        List<CouncilMemberMasterDto> councilMastDtosList = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        List<CouncilMemberMasterEntity> masterEntitiesList = councilMemberMasterDao.searchCouncilMasterData(couMemName,
                couMemberType, couPartyAffilation, orgid, couEleWZId1, couEleWZId2);

        masterEntitiesList.forEach(mastEntity -> {
            CouncilMemberMasterDto dto = new CouncilMemberMasterDto();
            BeanUtils.copyProperties(mastEntity, dto);

            // Getting Hierarchical and Non Hierarchical Prefix Description
            dto.setCouPartyAffDesc(
                    CommonMasterUtility.getCPDDescription(dto.getCouPartyAffilation().longValue(),
                            langId == 1 ? MainetConstants.MENU.E : MainetConstants.MENU.R));
            dto.setCouMemberTypeDesc(
                    CommonMasterUtility.getCPDDescription(dto.getCouMemberType().longValue(),
                            langId == 1 ? MainetConstants.MENU.E : MainetConstants.MENU.R));

            final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Council.ELECTION_WARD_PREFIX,
                    organisation);
            int levelSize = lookupList.size();
            List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
            List<LookUp> lookupListLevel2 = new ArrayList<LookUp>();
            List<LookUp> lookupListLevel3 = new ArrayList<LookUp>();
            List<LookUp> lookupListLevel4 = new ArrayList<LookUp>();
            List<LookUp> lookupListLevel5 = new ArrayList<LookUp>();
            /*
             * try { if (levelSize == 1) { lookupListLevel1 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, dto.getOrgId().longValue());
             * } if (levelSize == 2) { lookupListLevel1 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, dto.getOrgId().longValue());
             * lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 2,
             * dto.getOrgId().longValue()); } if (levelSize == 3) { lookupListLevel1 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, dto.getOrgId().longValue());
             * lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 2,
             * dto.getOrgId().longValue()); lookupListLevel3 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 3, dto.getOrgId().longValue());
             * } if (levelSize == 4) { lookupListLevel1 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, dto.getOrgId().longValue());
             * lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 2,
             * dto.getOrgId().longValue()); lookupListLevel3 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 3, dto.getOrgId().longValue());
             * lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 4,
             * dto.getOrgId().longValue()); } if (levelSize == 5) { lookupListLevel1 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, dto.getOrgId().longValue());
             * lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 2,
             * dto.getOrgId().longValue()); lookupListLevel3 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 3, dto.getOrgId().longValue());
             * lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 4,
             * dto.getOrgId().longValue()); lookupListLevel5 =
             * CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 5, dto.getOrgId().longValue());
             * } } catch (Exception e) { throw new FrameworkException("prefix level not found"); }
             */
            if (dto.getCouEleWZId1() != null && dto.getCouEleWZId1() != 0) {
                if (levelSize >= 1) {
                    lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1,
                            dto.getOrgId().longValue());
                }
                List<LookUp> level1 = lookupListLevel1.parallelStream()
                        .filter(clList -> clList != null
                                && clList.getLookUpId() == dto.getCouEleWZId1().longValue())
                        .collect(Collectors.toList());
                if (level1 != null && !level1.isEmpty()) {

                    dto.setCouEleWZ1Desc(level1.get(0).getDescLangFirst());
                }
            }
            if (dto.getCouEleWZId2() != null && dto.getCouEleWZId2() != 0) {
                if (levelSize >= 2) {
                    lookupListLevel2 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 2,
                            dto.getOrgId().longValue());
                }
                List<LookUp> level2 = lookupListLevel2.parallelStream()
                        .filter(clList -> clList != null
                                && clList.getLookUpId() == dto.getCouEleWZId2().longValue())
                        .collect(Collectors.toList());
                if (level2 != null && !level2.isEmpty()) {

                    dto.setCouEleWZ2Desc(level2.get(0).getDescLangFirst());
                }
            }
            if (dto.getCouEleWZId3() != null && dto.getCouEleWZId3() != 0) {
                if (levelSize >= 3) {
                    lookupListLevel3 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 3,
                            dto.getOrgId().longValue());
                }
                List<LookUp> level3 = lookupListLevel3.parallelStream()
                        .filter(clList -> clList != null
                                && clList.getLookUpId() == dto.getCouEleWZId3().longValue())
                        .collect(Collectors.toList());
                if (level3 != null && !level3.isEmpty()) {

                    dto.setCouEleWZ3Desc(level3.get(0).getDescLangFirst());
                }
            }
            if (dto.getCouEleWZId4() != null && dto.getCouEleWZId4() != 0) {
                if (levelSize >= 4) {
                    lookupListLevel4 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 4,
                            dto.getOrgId().longValue());
                }

                List<LookUp> level4 = lookupListLevel4.parallelStream()
                        .filter(clList -> clList != null
                                && clList.getLookUpId() == dto.getCouEleWZId4().longValue())
                        .collect(Collectors.toList());
                if (level4 != null && !level4.isEmpty()) {

                    dto.setCouEleWZ4Desc(level4.get(0).getDescLangFirst());
                }
            }
            if (dto.getCouEleWZId5() != null && dto.getCouEleWZId5() != 0) {
                if (levelSize >= 5) {
                    lookupListLevel5 = CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 5,
                            dto.getOrgId().longValue());
                }
                List<LookUp> level5 = lookupListLevel5.parallelStream()
                        .filter(clList -> clList != null
                                && clList.getLookUpId() == dto.getCouEleWZId5().longValue())
                        .collect(Collectors.toList());
                if (level5 != null && !level5.isEmpty()) {

                    dto.setCouEleWZ5Desc(level5.get(0).getDescLangFirst());
                }
            }
            councilMastDtosList.add(dto);
        });
        return councilMastDtosList;

    }

    @Override
    @Transactional(readOnly = true)
    public CouncilMemberMasterDto getCouncilMemberMasterByCouId(Long couId) {
        CouncilMemberMasterDto memberMatserDto = new CouncilMemberMasterDto();
        CouncilMemberMasterEntity counilMasterEntity = councilMemberMasterRepository.findOne(couId);
        BeanUtils.copyProperties(counilMasterEntity, memberMatserDto);
        return memberMatserDto;
    }

}
