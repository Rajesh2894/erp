package com.abm.mainet.adh.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.InspectionEntryDetEntity;
import com.abm.mainet.adh.domain.InspectionEntryEntity;
import com.abm.mainet.adh.domain.NewAdvertisementApplication;
import com.abm.mainet.adh.domain.NewAdvertisementApplicationDet;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.InspectionEntryDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.repository.AdvertiserMasterRepository;
import com.abm.mainet.adh.repository.InspectionEntryRepository;
import com.abm.mainet.adh.repository.NewAdvertisementApplicationRepository;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * @author Anwarul.Hassan
 * @since 08-Nov-2019
 */
@Service
public class InspectionEntryServiceImpl implements IInspectionEntryService {

    private static final Logger LOGGER = Logger.getLogger(InspectionEntryServiceImpl.class);
    @Autowired
    private InspectionEntryRepository inspectionEntryRepository;
    @Autowired
    private NewAdvertisementApplicationRepository newAdvertAppRepository;
    @Autowired
    private AdvertiserMasterRepository advertiserMasterRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IInspectionEntryService#getLicenseNoByOrgId(java.lang.Long)
     */
    @Override
    @Transactional
    public List<String> getLicenseNoByOrgId(Long orgId) {
        return newAdvertAppRepository.findLicenseNoByOrgId(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IInspectionEntryService#getAdvertisementDetails(java.lang.String)
     */
    @Override
    @Transactional
    public NewAdvertisementApplicationDto getAdvertisementDetails(String licenseNo, Long orgId) {
        NewAdvertisementApplicationDto dto = null;
        List<NewAdvertisementApplicationDetDto> newAdvertDetDtos = new ArrayList<>();
        NewAdvertisementApplication entity = newAdvertAppRepository.findAdvertisementDetailsByLicNoAndOrgId(licenseNo, orgId);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        if (entity != null) {
            dto = new NewAdvertisementApplicationDto();
            BeanUtils.copyProperties(entity, dto);

            dto.setAdhZoneDesc1(CommonMasterUtility.getHierarchicalLookUp(entity.getAdhZone1(), org).getDescLangFirst());
            dto.setAdhZoneDesc2(CommonMasterUtility.getHierarchicalLookUp(entity.getAdhZone2(), org).getDescLangFirst());
            List<NewAdvertisementApplicationDet> newAdvertisetDetails = entity.getNewAdvertisetDetails();
            for (NewAdvertisementApplicationDet detail : newAdvertisetDetails) {
                NewAdvertisementApplicationDetDto detailDto = new NewAdvertisementApplicationDetDto();
                BeanUtils.copyProperties(detail, detailDto);
                detailDto.setAdhTypeIdDesc1(
                        CommonMasterUtility.getHierarchicalLookUp(detail.getAdhTypeId1(), org).getDescLangFirst());
                detailDto.setAdhTypeIdDesc2(
                        CommonMasterUtility.getHierarchicalLookUp(detail.getAdhTypeId2(), org).getDescLangFirst());
                newAdvertDetDtos.add(detailDto);
            }
            dto.setNewAdvertDetDtos(newAdvertDetDtos);
        }
        return dto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IInspectionEntryService#findAgencyByAgencyLicNoAndOrgId(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional
    public AdvertiserMasterDto getAgencyByAgencyLicNoAndOrgId(String agencyLicNo, Long orgId) {
        AdvertiserMasterDto masterDto = null;
        AdvertiserMasterEntity entity = advertiserMasterRepository.findAgencyByAgencyLicNoAndOrgId(agencyLicNo, orgId);
        if (entity != null) {
            masterDto = new AdvertiserMasterDto();
            BeanUtils.copyProperties(entity, masterDto);
        }
        return masterDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.service.IInspectionEntryService#saveInspectionEntryData(com.abm.mainet.adh.dto.InspectionEntryDto)
     */
    @Override
    @Transactional
    public InspectionEntryDto saveInspectionEntryData(@RequestBody InspectionEntryDto inspectionEntryDto) {
        InspectionEntryEntity entryEntity = mapDtoToEntity(inspectionEntryDto);
        try {
            inspectionEntryRepository.save(entryEntity);
        } catch (Exception exception) {
            LOGGER.error("Exception Occured While saving the InspectionEntry Data", exception);
            throw new FrameworkException("Exception Occured While saving the InspectionEntry Data", exception);
        }
        return inspectionEntryDto;
    }

    private InspectionEntryEntity mapDtoToEntity(InspectionEntryDto entryDto) {
        InspectionEntryEntity entryEntity = new InspectionEntryEntity();
        List<InspectionEntryDetEntity> detEntities = new ArrayList<>();
        BeanUtils.copyProperties(entryDto, entryEntity);
        if (CollectionUtils.isNotEmpty(entryDto.getInspectionEntryDetDto())) {
            entryDto.getInspectionEntryDetDto().forEach(det -> {
                InspectionEntryDetEntity detDto = new InspectionEntryDetEntity();
                BeanUtils.copyProperties(det, detDto);
                detDto.setInspectionEntryEntity(entryEntity);
                detEntities.add(detDto);
            });
        }
        entryEntity.setInspectionEntryDetEntity(detEntities);
        return entryEntity;
    }
}
