package com.abm.mainet.workManagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.workManagement.domain.ScheduleOfRateDetEntity;
import com.abm.mainet.workManagement.domain.ScheduleOfRateDetHistEntity;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstHistEntity;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.repository.ScheduleOfRateRepository;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
@Service
public class ScheduleOfRateServiceImpl implements ScheduleOfRateService {

    @Autowired
    private ScheduleOfRateRepository scheduleOfRateRepository;

    @Autowired
    private AuditService auditService;

    private static final Logger LOGGER = Logger.getLogger(ScheduleOfRateServiceImpl.class);

    /**
     * create Schedule of rate Master Details
     */
    @Override
    @Transactional
    public ScheduleOfRateMstDto createSchedule(ScheduleOfRateMstDto dto) {
        if (dto != null && dto.getDetDto() != null && !dto.getDetDto().isEmpty()) {
            if (dto.getSorCpdId() != null && dto.getSorCpdId() != 0 && dto.getOrgId() != null) {
                // Inactive already active SOR while creating new one.
                updateExistingActiveSorType(dto.getSorCpdId(), dto.getSorFromDate(), dto.getOrgId(), dto.getCreatedBy(),
                        dto.getIgIpMac());
            }

            ScheduleOfRateMstEntity masEntity = new ScheduleOfRateMstEntity();
            List<ScheduleOfRateDetEntity> detEntityList = new ArrayList<>();
            BeanUtils.copyProperties(dto, masEntity);
            Organisation organisation = new Organisation();
            organisation.setOrgid(dto.getOrgId());
            masEntity.setSorName(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getSorCpdId(), organisation).getDescLangFirst());
            masEntity.setCreatedDate(new Date());
            masEntity.setSorActive(MainetConstants.Common_Constant.YES);

            // set SOR child details.
            dto.getDetDto().parallelStream().forEach(detDto -> {
                ScheduleOfRateDetEntity detEntity = new ScheduleOfRateDetEntity();
                BeanUtils.copyProperties(detDto, detEntity);
                detEntity.setCreatedBy(dto.getCreatedBy());
                detEntity.setCreatedDate(new Date());
                detEntity.setSorDActive(MainetConstants.Common_Constant.YES);
                detEntity.setOrgId(dto.getOrgId());
                detEntity.setScheduleOfRateMst(masEntity);
                detEntity.setIgIpMac(dto.getIgIpMac());
                detEntityList.add(detEntity);
            });
            masEntity.setScheduleDetailsList(detEntityList);
            ScheduleOfRateMstEntity savedMstEntity = scheduleOfRateRepository.save(masEntity);
            BeanUtils.copyProperties(savedMstEntity, dto);
            // create history for new created SOR

            createScheduleOfRateHistory(savedMstEntity);
        }
        return dto;
    }

    // create history for new created SOR
    private void createScheduleOfRateHistory(ScheduleOfRateMstEntity savedMstEntity) {

        try {
            ScheduleOfRateMstHistEntity mstHistory = new ScheduleOfRateMstHistEntity();
            mstHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(savedMstEntity, mstHistory);

            // create history for child records.
            List<Object> hisotyList = new ArrayList<>();

            savedMstEntity.getScheduleDetailsList().parallelStream().forEach(savedDetEntity -> {
                ScheduleOfRateDetHistEntity detHistory = new ScheduleOfRateDetHistEntity();
                BeanUtils.copyProperties(savedDetEntity, detHistory);
                detHistory.setSorId(savedMstEntity.getSorId());
                detHistory.setSordId(savedDetEntity.getSordId());
                detHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                hisotyList.add(detHistory);
            });
            auditService.createHistoryForListObj(hisotyList);
        } catch (Exception exception) {
            LOGGER.error("Could not make audit entry for" + savedMstEntity, exception);
        }
    }

    // Inactive already active SOR while creating new one.
    private void updateExistingActiveSorType(Long sorCpdId, Date sorFromDate, Long orgId, Long createdBy, String updatedBy) {
        ScheduleOfRateMstEntity existingSorType = findExistingActiveSorType(sorCpdId, orgId);
        if (existingSorType != null) {
            // Set Yesterday date to previous SOR type and Make it inactive by set active status as 'N'
            Calendar cal = Calendar.getInstance();
            cal.setTime(sorFromDate);
            cal.add(Calendar.DAY_OF_YEAR, -1);
            existingSorType.setSorToDate(cal.getTime());
            existingSorType.setSorActive(MainetConstants.Common_Constant.NO);
            existingSorType.setUpdatedBy(createdBy);
            existingSorType.setUpdatedDate(new Date());
            existingSorType.setIgIpMacUpd(updatedBy);
            scheduleOfRateRepository.save(existingSorType);
        }
    }

    /**
     * update schedule of rate master form details
     */
    @Override
    @Transactional
    public void updateSchedule(ScheduleOfRateMstDto mstDto, List<Long> removeChildIds) {
        if (mstDto != null) {
            ScheduleOfRateMstEntity masterEntity = new ScheduleOfRateMstEntity();
            BeanUtils.copyProperties(mstDto, masterEntity);
            masterEntity.setUpdatedDate(new Date());
            Organisation organisation = new Organisation();
            organisation.setOrgid(mstDto.getOrgId());
            masterEntity.setSorName(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(mstDto.getSorCpdId(), organisation).getDescLangFirst());
            if (mstDto.getDetDto() != null && !mstDto.getDetDto().isEmpty()) {
                ScheduleOfRateDetEntity detailEntity = null;
                List<ScheduleOfRateDetEntity> detailEntityList = new ArrayList<>();
                for (ScheduleOfRateDetDto detDTO : mstDto.getDetDto()) {
                    if (detDTO.getSchActiveFlag() != null) {
                        detailEntity = new ScheduleOfRateDetEntity();
                        BeanUtils.copyProperties(detDTO, detailEntity);
                        if (detDTO.getSordId() == null) {
                            detailEntity.setSorDActive(MainetConstants.Common_Constant.YES);
                            detailEntity.setOrgId(mstDto.getOrgId());
                            detailEntity.setIgIpMac(mstDto.getIgIpMacUpd());
                            detailEntity.setCreatedBy(mstDto.getUpdatedBy());
                            detailEntity.setCreatedDate(new Date());
                        } else {
                            detailEntity.setIgIpMacUpd(mstDto.getIgIpMacUpd());
                            detailEntity.setUpdatedBy(mstDto.getUpdatedBy());
                            detailEntity.setUpdatedDate(new Date());
                        }

                        detailEntity.setScheduleOfRateMst(masterEntity);
                        detailEntityList.add(detailEntity);
                    }
                }
                masterEntity.setScheduleDetailsList(detailEntityList);
                ScheduleOfRateMstEntity updatedMstEntity = scheduleOfRateRepository.save(masterEntity);

                try {
                    ScheduleOfRateMstHistEntity updateMstHistory = new ScheduleOfRateMstHistEntity();
                    updateMstHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                    auditService.createHistory(updatedMstEntity, updateMstHistory);

                    List<Object> hisotyList = new ArrayList<>();
                    updatedMstEntity.getScheduleDetailsList().forEach(updatedDetEntity -> {
                        ScheduleOfRateDetHistEntity updateDetHistory = new ScheduleOfRateDetHistEntity();
                        BeanUtils.copyProperties(updatedDetEntity, updateDetHistory);
                        updateDetHistory.setSorId(updatedMstEntity.getSorId());
                        updateDetHistory.setSordId(updatedDetEntity.getSordId());
                        updateDetHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                        hisotyList.add(updateDetHistory);
                    });
                    auditService.createHistoryForListObj(hisotyList);
                } catch (Exception exception) {
                    LOGGER.error("Could not make audit entry for" + updatedMstEntity, exception);
                }

            }
            if (removeChildIds != null && !removeChildIds.isEmpty()) {
                inactiveDetailsRecords(removeChildIds, mstDto.getUpdatedBy());
            }
        }

    }

    /**
     * Used to inactive multiple SOR items
     * 
     * @param removeChildIds
     * @param updatedBy
     */

    private void inactiveDetailsRecords(List<Long> removeChildIds, Long updatedBy) {
        scheduleOfRateRepository.inactiveSorChildRecords(updatedBy, removeChildIds);
    }

    /**
     * find SOR Master details by sorId(primary key)
     * @param sorId
     * @param organisation
     * @return ScheduleOfRateMstDto with All child details records if record found.
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleOfRateMstDto findSORMasterWithDetailsBySorId(Long sorId) {
        ScheduleOfRateMstDto mastDTO = null;
        ScheduleOfRateMstEntity entity = scheduleOfRateRepository.findOne(sorId);
        if (entity != null) {
            entity.getScheduleDetailsList();
            mastDTO = new ScheduleOfRateMstDto();
            mastDTO.setSorName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(entity.getSorCpdId(), entity.getOrgId(),
                    MainetConstants.WorksManagement.SRM).getLookUpDesc());
            List<ScheduleOfRateDetDto> detDtoList = new ArrayList<>();
            BeanUtils.copyProperties(entity, mastDTO);
            entity.getScheduleDetailsList().forEach(detEntity -> {
                ScheduleOfRateDetDto detDTO = new ScheduleOfRateDetDto();
                BeanUtils.copyProperties(detEntity, detDTO);
                detDTO.setSordCategoryDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(detEntity.getSordCategory(),
                                entity.getOrgId(), MainetConstants.ScheduleOfRate.WKC).getDescLangFirst());
                detDTO.setSorIteamUnitDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(detEntity.getSorIteamUnit(),
                                entity.getOrgId(), MainetConstants.WorksManagement.WUT).getLookUpDesc());
                if (detEntity.getLeadUnit() != null)
                    detDTO.setLeadUnitDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(detEntity.getLeadUnit(),
                                    entity.getOrgId(), MainetConstants.WorksManagement.WUT).getLookUpDesc());
                detDtoList.add(detDTO);
            });
            detDtoList.sort(Comparator.comparing(ScheduleOfRateDetDto::getSordCategory));
            mastDTO.setDetDto(detDtoList);
        }
        return mastDTO;
    }

    /**
     * used to get All Active Schedule of rate master list details. if record found than return ScheduleOfRateMstDto with details
     * else return empty dto list. it will return only master details not sor item details.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateMstDto> getAllActiveScheduleRateMstList(long orgid) {
        List<ScheduleOfRateMstDto> dtoList = new ArrayList<>();
        Long newOrgId = populateService(orgid);
        List<ScheduleOfRateMstEntity> masEntityList = scheduleOfRateRepository.findAllActiveScheduleByOrgId(newOrgId);
        if (masEntityList != null && !masEntityList.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(newOrgId);
            masEntityList.forEach(entity -> {
                ScheduleOfRateMstDto dto = new ScheduleOfRateMstDto();
                BeanUtils.copyProperties(entity, dto);
                dto.setSorName(CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getSorCpdId(), organisation)
                        .getLookUpDesc());
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    /**
     * used to search Active Schedule of rate master list details based on organization id and sor type or sor name or sor start
     * date or sor end date. if record found than return ScheduleOfRateMstDto with details else return empty dto list. it will
     * return only master details not sor item details.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateMstDto> searchSorRecords(long orgId, Long sorNameId, Date sorFromDate,
            Date sorToDate) {
        List<ScheduleOfRateMstDto> dtoSearchList = new ArrayList<>();

        Long newOrgId = populateService(orgId);
        List<ScheduleOfRateMstEntity> masEntitySearchList = scheduleOfRateRepository.searchSorRecords(newOrgId, sorNameId,
                sorFromDate, sorToDate);
        if (masEntitySearchList != null && !masEntitySearchList.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(newOrgId);
            masEntitySearchList.forEach(searchEntity -> {
                ScheduleOfRateMstDto masDto = new ScheduleOfRateMstDto();
                BeanUtils.copyProperties(searchEntity, masDto);
                masDto.setSorName(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(searchEntity.getSorCpdId(), organisation)
                                .getDescLangFirst());
                masDto.setFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(searchEntity.getSorFromDate()));
                if (searchEntity.getSorToDate() != null)
                    masDto.setToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(searchEntity.getSorToDate()));
                dtoSearchList.add(masDto);
            });
        }
        return dtoSearchList;
    }

    /**
     * used to inactive SOR master details and related all child details by master primary key sor id and set active flag to "N"
     * @param empId for updated by details
     * @param sorId primary key
     */
    @Override
    public void inactiveSorMaster(Long sorId, Long empId) {
        scheduleOfRateRepository.inactiveSorMas(sorId, empId);
        scheduleOfRateRepository.inactiveAllChildByMasId(sorId, empId);
    }

    /**
     * used to get All SOR name by organization id
     * @param orgId
     * @return SOR Name Object List
     */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findAllSorNamesByOrgId(long orgId) {
        Long newOrgId = populateService(orgId);
        return scheduleOfRateRepository.findAllSorNamesByOrgId(newOrgId);
    }

    /**
     * used to get All Active and Inactive Schedule of rate master list details. if record found than return ScheduleOfRateMstDto
     * with details else return empty dto list. it will return only master details not sor item details.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateMstDto> getAllScheduleRateMstList(long orgid) {
        List<ScheduleOfRateMstDto> dtoList = new ArrayList<>();
        Long newOrgId = populateService(orgid);
        List<ScheduleOfRateMstEntity> masEntityList = scheduleOfRateRepository.findAllScheduleByOrgId(newOrgId);
        if (masEntityList != null && !masEntityList.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(newOrgId);
            masEntityList.forEach(entity -> {
                ScheduleOfRateMstDto dto = new ScheduleOfRateMstDto();
                BeanUtils.copyProperties(entity, dto);
                dto.setSorName(CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getSorCpdId(), organisation)
                        .getDescLangFirst());
                dto.setFromDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getSorFromDate()));
                if (entity.getSorToDate() != null)
                    dto.setToDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getSorToDate()));
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    /**
     * get Existing Active SOR Type by SOR name Id
     * @param sorCpdId
     * @param orgId
     * @return if records present than return entity else return null;
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleOfRateMstEntity findExistingActiveSorType(Long sorCpdId, Long orgId) {
        Long newOrgId = populateService(orgId);
        return scheduleOfRateRepository.findExistingActiveSorType(sorCpdId, newOrgId);
    }

    /**
     * it is used to get all active Schedule of Rate Item Details List
     * @param orgid
     * @return List<ScheduleOfRateDetDto> if record found else return empty list
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateDetDto> getAllActiveSorDetails(long orgId) {
        List<ScheduleOfRateDetDto> detailsList = new ArrayList<>();
        Long newOrgId = populateService(orgId);

        List<ScheduleOfRateDetEntity> detEntityList = scheduleOfRateRepository.findAllScheduleDetailsByOrgId(newOrgId);
        if (detEntityList != null && !detEntityList.isEmpty()) {
            detEntityList.forEach(entity -> {
                ScheduleOfRateDetDto dto = new ScheduleOfRateDetDto();
                BeanUtils.copyProperties(entity, dto);
                detailsList.add(dto);
            });
        }
        return detailsList;

    }

    /**
     * this service is used to find item details by using SOR details primary key sordId
     * @param sordId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleOfRateDetDto findSorItemDetailsBySordId(long sordId) {
        ScheduleOfRateDetDto detDto = null;
        ScheduleOfRateDetEntity detEntity = scheduleOfRateRepository.findSorItemDetailsBySordId(sordId);
        if (detEntity != null) {
            detDto = new ScheduleOfRateDetDto();
            BeanUtils.copyProperties(detEntity, detDto);
            detDto.setSorId(detEntity.getScheduleOfRateMst().getSorId());
        }
        return detDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleOfRateDetDto getSorDetailsByItemCode(Long orgId, String itemCode, Long sorId) {
        ScheduleOfRateDetDto detDto = null;
        Long newOrgId = populateService(orgId);
        ScheduleOfRateDetEntity detEntity = scheduleOfRateRepository.getSorDetailsByItemCode(newOrgId, itemCode, sorId);
        if (detEntity != null) {
            detDto = new ScheduleOfRateDetDto();
            BeanUtils.copyProperties(detEntity, detDto);
            detDto.setSorId(detEntity.getScheduleOfRateMst().getSorId());
        }
        return detDto;
    }

    /**
     * This method is used to Check Default status of Super Organisation
     * 
     * @param orgId
     * @return orgId
     */
    public Long populateService(Long orgId) {
        Organisation organisation = new Organisation();

        organisation.setOrgid(orgId);
        Organisation org = ApplicationContextProvider.getApplicationContext().getBean(TbOrganisationService.class)
                .findDefaultOrganisation();
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.CSR, organisation).stream()
                .filter(c -> c.getDefaultVal().equals("Y")).collect(Collectors.toList());
        if (lookUps.get(0).getLookUpCode().equals(MainetConstants.YES)) {
            orgId = org.getOrgid();
        }
        return orgId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOfRateDetDto> getAllItemsListByChapterId(Long chapterValue, Long sorId, long orgId) {

        List<ScheduleOfRateDetDto> detDtoList = new ArrayList<>();
        Long newOrgId = populateService(orgId);
        List<ScheduleOfRateDetEntity> entity = scheduleOfRateRepository.getAllItemsListByChapterId(chapterValue, sorId,
                newOrgId);
        if (entity != null && !(entity.isEmpty())) {
            entity.forEach(detEntity -> {
                ScheduleOfRateDetDto detDTO = new ScheduleOfRateDetDto();
                BeanUtils.copyProperties(detEntity, detDTO);
                detDTO.setSordCategoryDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        detEntity.getSordCategory(), orgId, MainetConstants.ScheduleOfRate.WKC).getDescLangFirst());
                detDTO.setSorIteamUnitDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        detEntity.getSorIteamUnit(), orgId, MainetConstants.WorksManagement.WUT).getDescLangFirst());
                if (detEntity.getLeadUnit() != null)
                    detDTO.setLeadUnitDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                            detEntity.getLeadUnit(), orgId, MainetConstants.WorksManagement.WUT).getDescLangFirst());
                detDtoList.add(detDTO);
            });
            detDtoList.sort(Comparator.comparing(ScheduleOfRateDetDto::getSordCategory));
        }
        return detDtoList;
    }
    
    //Defect#119121
	@Override
	public ArrayList<Object> getChapterList(Long orgId,Long sorId) {
		
		ArrayList<Object> chapterList = new ArrayList<>();
		Organisation organisation = new Organisation(); 
		Long newOrgId = populateService(orgId); 
		organisation.setOrgid(newOrgId); 
		List<LookUp> lookUpWKC = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.WKC,organisation);
		lookUpWKC.forEach(lookUp -> {
			  List<ScheduleOfRateDetEntity> entity = scheduleOfRateRepository.getAllItemsListByChapterId(lookUp.getLookUpId(), sorId,
		                newOrgId);
			  if(entity != null && !(entity.isEmpty())) {
				  chapterList.add(lookUp);
			  }
		 });
		
		return chapterList;
	}
	 
}
