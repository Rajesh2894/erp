package com.abm.mainet.workManagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.workManagement.dao.SchemeMasterDao;
import com.abm.mainet.workManagement.domain.SchemeMastDetails;
import com.abm.mainet.workManagement.domain.SchemeMastDetailsHistory;
import com.abm.mainet.workManagement.domain.SchemeMaster;
import com.abm.mainet.workManagement.domain.SchemeMasterHistory;
import com.abm.mainet.workManagement.dto.SchemeMastDatailsDTO;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.repository.SchemeMasterRepository;

@Service
public class SchemeMasterServiceImpl implements SchemeMasterService {

    @Autowired
    private SchemeMasterDao schemeMasterDao;

    @Autowired
    private SchemeMasterRepository schemeMasterRepository;

    @Autowired
    private AuditService auditService;

    private static final Logger LOGGER = Logger.getLogger(SchemeMasterServiceImpl.class);

    /**
     * create Scheme Master Details
     */

    @Override
    @Transactional
    public void saveSchemeMaster(SchemeMasterDTO schemeMasterDTO) {

        SchemeMaster masterEntity = new SchemeMaster();
        SchemeMastDetails masterDetailsEntity = null;
        List<SchemeMastDetails> listMasterDetails = new ArrayList<>();
        schemeMasterDTO.setSchemeActive(MainetConstants.Common_Constant.YES);
        for (SchemeMastDatailsDTO schemeDetailsDto : schemeMasterDTO.getMastDetailsDTO()) {
            masterDetailsEntity = new SchemeMastDetails();
            BeanUtils.copyProperties(schemeDetailsDto, masterDetailsEntity);

            masterDetailsEntity.setOrgid(schemeMasterDTO.getOrgId());
            masterDetailsEntity.setCreatedBy(schemeMasterDTO.getCreatedBy());
            masterDetailsEntity.setCreatedDate(schemeMasterDTO.getCreatedDate());
            masterDetailsEntity.setLgIpMac(schemeMasterDTO.getLgIpMac());
            masterDetailsEntity.setSchDActive(MainetConstants.Common_Constant.YES);
            masterDetailsEntity.setWmsSchemeMaster(masterEntity);
            listMasterDetails.add(masterDetailsEntity);
        }

        masterEntity.setMastDetailsEntity(listMasterDetails);
        BeanUtils.copyProperties(schemeMasterDTO, masterEntity);
        SchemeMaster savedSchemeMaster = schemeMasterRepository.save(masterEntity);

        try {
            SchemeMasterHistory history = new SchemeMasterHistory();
            history.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(savedSchemeMaster, history);
            List<Object> schemeHistoryList = new ArrayList<>();
            savedSchemeMaster.getMastDetailsEntity().forEach(masDet -> {
                SchemeMastDetailsHistory detailsHistory = new SchemeMastDetailsHistory();
                BeanUtils.copyProperties(masDet, detailsHistory);
                detailsHistory.setWmsSchmId(masDet.getWmsSchemeMaster().getWmSchId());
                detailsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                schemeHistoryList.add(detailsHistory);
            });

            auditService.createHistoryForListObj(schemeHistoryList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when calling audit service  ", exception);
        }

    }

    /**
     * Update Scheme Master
     * 
     * @param schemeMasterDTO
     */
    @Override
    @Transactional
    public void updateSchemeMaster(SchemeMasterDTO schemeMasterDTO, List<Long> removeChildIds,
            List<Long> removeFileById) {

        SchemeMaster masterEntity = new SchemeMaster();
        BeanUtils.copyProperties(schemeMasterDTO, masterEntity);
        masterEntity.setUpdatedDate(new Date());
        SchemeMastDetails masterDetailsEntity = null;
        List<SchemeMastDetails> listMasterDetails = new ArrayList<>();

        for (SchemeMastDatailsDTO mastDetailsDTO : schemeMasterDTO.getMastDetailsDTO()) {
            masterDetailsEntity = new SchemeMastDetails();
            if (mastDetailsDTO.getSchActiveFlag() != null) {
                BeanUtils.copyProperties(mastDetailsDTO, masterDetailsEntity);
                if (mastDetailsDTO.getSchDetId() == null) {
                    masterDetailsEntity.setSchDActive(MainetConstants.Common_Constant.YES);
                    masterDetailsEntity.setOrgid(schemeMasterDTO.getOrgId());
                    masterDetailsEntity.setLgIpMac(schemeMasterDTO.getLgIpMacUpd());
                    masterDetailsEntity.setCreatedBy(schemeMasterDTO.getUpdatedBy());
                    masterDetailsEntity.setCreatedDate(schemeMasterDTO.getUpdatedDate());

                } else {
                    masterDetailsEntity.setLgIpMacUpd(schemeMasterDTO.getLgIpMacUpd());
                    masterDetailsEntity.setUpdatedBy(schemeMasterDTO.getUpdatedBy());
                    masterDetailsEntity.setUpdatedDate(new Date());
                }
                masterDetailsEntity.setWmsSchemeMaster(masterEntity);

                listMasterDetails.add(masterDetailsEntity);
            }
        }
        masterEntity.setMastDetailsEntity(listMasterDetails);

        SchemeMaster updateSchemeMaster = schemeMasterRepository.save(masterEntity);
        if (removeChildIds != null && !removeChildIds.isEmpty()) {

            schemeMasterRepository.inactiveSchemeMasterChildRecords(schemeMasterDTO.getUpdatedBy(), removeChildIds);
        }

        if (removeFileById != null && !removeFileById.isEmpty()) {
            ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(removeFileById,
                    schemeMasterDTO.getUpdatedBy(), MainetConstants.FlagD);
        }
        try {
            SchemeMasterHistory history = new SchemeMasterHistory();
            history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            auditService.createHistory(updateSchemeMaster, history);
            List<Object> schemeHistoryList = new ArrayList<>();
            updateSchemeMaster.getMastDetailsEntity().forEach(masDet -> {
                SchemeMastDetailsHistory detailsHistory = new SchemeMastDetailsHistory();
                BeanUtils.copyProperties(masDet, detailsHistory);
                detailsHistory.setWmsSchmId(masDet.getWmsSchemeMaster().getWmSchId());
                detailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                schemeHistoryList.add(detailsHistory);
            });
            auditService.createHistoryForListObj(schemeHistoryList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when calling audit service  ", exception);
        }

    }

    /**
     * used for get data on the basis of schemId
     * 
     * @param wmSchId
     */

    @Override
    @Transactional(readOnly = true)
    public SchemeMasterDTO getSchemeMasterBySchemeId(Long wmSchId) {
        SchemeMasterDTO schemeMasterDto = null;

        SchemeMaster schemeMasterEntity = schemeMasterRepository.findOne(wmSchId);

        if (schemeMasterEntity != null) {
            schemeMasterEntity.getMastDetailsEntity();
            schemeMasterDto = new SchemeMasterDTO();
            List<SchemeMastDatailsDTO> listDetailsDTOs = new ArrayList<>();
            BeanUtils.copyProperties(schemeMasterEntity, schemeMasterDto);
            schemeMasterEntity.getMastDetailsEntity().forEach(detailsEntity -> {
                SchemeMastDatailsDTO mastDatailsDTO = new SchemeMastDatailsDTO();
                BeanUtils.copyProperties(detailsEntity, mastDatailsDTO);
                listDetailsDTOs.add(mastDatailsDTO);
            });
            schemeMasterDto.setMastDetailsDTO(listDetailsDTOs);
        }
        return schemeMasterDto;

    }

    /**
     * used to get Scheme master list
     */

    @Override
    @Transactional(readOnly = true)
    public List<SchemeMasterDTO> getSchemeMasterList(Long sourceCode, Long sourceName, String wmSchNameEng,
            long orgId) {
        List<SchemeMasterDTO> schemeMasterDtoList = new ArrayList<SchemeMasterDTO>();
        Long newOrgId = populateService(orgId);
        List<SchemeMaster> schemeMasterEntityList = schemeMasterDao.getSchemeMasterList(sourceCode, sourceName,
                wmSchNameEng, newOrgId);

        for (SchemeMaster tbWmsSchemeMaster : schemeMasterEntityList) {
            SchemeMasterDTO masterDto = new SchemeMasterDTO();
            Long fund = tbWmsSchemeMaster.getWmSchFund();
            if (fund != null) {

                String fundCode = ApplicationContextProvider.getApplicationContext().getBean(AccountFundMasterService.class)
                        .getFundCodeDesc(fund);
                masterDto.setSchFundDesc(fundCode);
            }
            BeanUtils.copyProperties(tbWmsSchemeMaster, masterDto);
            /*
             * if (tbWmsSchemeMaster.getWmSchStrDate() != null) { masterDto.setSchStrDateDesc( new
             * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tbWmsSchemeMaster. getWmSchStrDate())); } if
             * (tbWmsSchemeMaster.getWmSchEndDate() != null) { masterDto.setSchEndDateDesc( new
             * SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tbWmsSchemeMaster. getWmSchEndDate())); }
             */
            schemeMasterDtoList.add(masterDto);
        }
        return schemeMasterDtoList;
    }

    /**
     * Method is used for scheme Soft Delete only flag set "Y" or "N"
     * 
     * @param wmSchId
     */

    @Override
    @Transactional
    public void deleteSchemeMasterById(Long wmSchId) {
        SchemeMaster master = schemeMasterRepository.findOne(wmSchId);
        master.setSchemeActive(MainetConstants.Common_Constant.NO);
        for (SchemeMastDetails schemeMastDetails : master.getMastDetailsEntity()) {
            schemeMastDetails.setSchDActive(MainetConstants.Common_Constant.NO);
        }
        schemeMasterRepository.save(master);

        try {
            SchemeMasterHistory history = new SchemeMasterHistory();
            history.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
            auditService.createHistory(master, history);
            List<Object> schemeHistoryList = new ArrayList<>();
            master.getMastDetailsEntity().forEach(masDet -> {
                SchemeMastDetailsHistory detailsHistory = new SchemeMastDetailsHistory();
                BeanUtils.copyProperties(masDet, detailsHistory);
                detailsHistory.setWmsSchmId(masDet.getWmsSchemeMaster().getWmSchId());
                detailsHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                schemeHistoryList.add(detailsHistory);
            });
            auditService.createHistoryForListObj(schemeHistoryList);
        } catch (Exception exception) {
            LOGGER.error("Exception occured when calling audit service  ", exception);
        }

    }

    /**
     * used to check duplicate SchemeCode
     */

    @Override
    @Transactional
    public String checkDuplicateSchemeCode(String wmSchCode, Long orgId) {
        Long newOrgId = populateService(orgId);
        List<SchemeMaster> list = schemeMasterRepository.checkDuplicateSchemeCode(wmSchCode, newOrgId);
        String flag = MainetConstants.Common_Constant.NO;
        if (list != null && !list.isEmpty()) {
            flag = MainetConstants.Common_Constant.YES;
        }
        return flag;
    }

    /**
     * used for get data on the basis of schemId association with project master
     * 
     * @param wmSchId
     */

    @Override
    @Transactional(readOnly = true)
    public SchemeMasterDTO getSchmMastToProject(Long wmSchId) {
        SchemeMasterDTO schemeMasterDto = null;
        SchemeMaster schemeMasterEntity = schemeMasterRepository.findOne(wmSchId);
        if (schemeMasterEntity != null) {
            schemeMasterEntity.getSchemeToProjectlist();
            schemeMasterDto = new SchemeMasterDTO();
            List<WmsProjectMasterDto> listProjectMastDTOs = new ArrayList<>();
            BeanUtils.copyProperties(schemeMasterEntity, schemeMasterDto);
            schemeMasterEntity.getSchemeToProjectlist().forEach(projectEntity -> {
                WmsProjectMasterDto projectMasterDto = new WmsProjectMasterDto();
                BeanUtils.copyProperties(projectEntity, projectMasterDto);
                if(projectEntity.getProjStartDate() != null)
	                projectMasterDto.setStartDateDesc(
	                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectEntity.getProjStartDate()));
                if(projectEntity.getProjEndDate() != null)
	                projectMasterDto.setEndDateDesc(
	                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(projectEntity.getProjEndDate()));
                listProjectMastDTOs.add(projectMasterDto);

            });
            schemeMasterDto.setSchemeProjectlist(listProjectMastDTOs);
        }
        return schemeMasterDto;

    }

    @Override
    public String generateSchemeCode(Long orgId) {

        final Long schSequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(
                        MainetConstants.WorksManagement.WORKS_MANAGEMENT, "TB_WMS_SCHEME_MAST", "SCH_CODE", orgId,
                        MainetConstants.FlagC, orgId);
        return "SCH" + MainetConstants.WINDOWS_SLASH
                + String.format(MainetConstants.WorksManagement.THREE_PERCENTILE, schSequence);
    }

    /**
     * It's Used for check whether oganisation is, default organisation or not
     * @param orgId
     * @return
     */
    private Long populateService(Long orgId) {
        Organisation organisation = new Organisation();

        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                .findDefaultOrganisation();
        organisation.setOrgid(orgId);
        List<LookUp> ups = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation).stream()
                .filter(c -> c.getDefaultVal().equals(MainetConstants.FlagY)).collect(Collectors.toList());
        if (ups.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            orgId = org.getOrgid();
        }
        return orgId;
    }
}
