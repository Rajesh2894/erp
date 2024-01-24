/**
 * 
 */
package com.abm.mainet.asset.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetClassification;
import com.abm.mainet.asset.domain.AssetClassificationRev;
import com.abm.mainet.asset.domain.ClassificationHistory;
import com.abm.mainet.asset.mapper.ClassificationInfoMapper;
import com.abm.mainet.asset.repository.AssetClassificationRepo;
import com.abm.mainet.asset.repository.AssetClassificationRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.GeoCoordinatesDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class ClassificationInfoServiceImpl implements IClassificationInfoService {

    @Autowired
    private AssetClassificationRepo assetClassificationRepo;

    @Autowired
    private AssetClassificationRevRepo assetClassificationRevRepo;
    @Autowired
    private AuditService auditService;
    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;

    private static final Logger LOGGER = Logger.getLogger(ClassificationInfoServiceImpl.class);

    @Override
    @Transactional
    public Long saveClassInfo(final AssetClassificationDTO dto) {
        Long classfnId = null;

        final ClassificationHistory entityHistory = new ClassificationHistory();
        AssetClassification assetClassification = ClassificationInfoMapper.mapToEntity(dto);
        try {
            assetClassification = assetClassificationRepo.save(assetClassification);
            classfnId = assetClassification.getAssetClassificationId();

            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            try {
                auditService.createHistory(assetClassification, entityHistory);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry while saving asset classification ", ex);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset classification ", exception);
            throw new FrameworkException("Exception occur while saving asset classification ", exception);
        }
        return classfnId;
    }

    @Override
    @Transactional
    public Long saveClassInfoRev(final AssetClassificationDTO dto) {
        Long classfnId = null;
        // final ClassificationHistory entityHistory = new ClassificationHistory();
        AssetClassificationRev assetClassification = ClassificationInfoMapper.mapToEntityRev(dto);
        try {
            assetClassification = assetClassificationRevRepo.save(assetClassification);
            classfnId = assetClassification.getAssetClassfcnIdRev();
            // entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            // auditService.createHistory(assetClassification, entityHistory);
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset classification ", exception);
            throw new FrameworkException("Exception occur while saving asset classification ", exception);
        }
        return classfnId;
    }

    @Override
    @Transactional
    public Long updateClassInfo(final Long assetId, final AssetClassificationDTO dto) {
        Long classfnId = null;

        try {
            if (dto.getAssetClassificationId() != null) {
                final ClassificationHistory entityHistory = new ClassificationHistory();
                AssetClassification entity = ClassificationInfoMapper.mapToEntity(dto);
                assetClassificationRepo.updateByAssetId(dto.getAssetClassificationId(), entity);
                entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
                try {
                    auditService.createHistory(entity, entityHistory);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry while updating asset classification ", ex);
                }
            } else {
                dto.setAssetId(assetId);
                classfnId = saveClassInfo(dto);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while updating asset classification ", exception);
            throw new FrameworkException("Exception occur while updating asset classification ", exception);
        }

        return classfnId;
    }

    @Override
    @Transactional
    public Long saveClassificationRev(Long assetId, AssetClassificationDTO dto, AuditDetailsDTO auditDTO) {

        return saveClassInfoRev(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetClassificationDTO getclassByAssetId(final Long assetId) {
        AssetClassificationDTO dto = null;
        try {
            final AssetClassification entity = assetClassificationRepo.findClassByAssetId(assetId);
            if (entity != null) {
                dto = ClassificationInfoMapper.mapToDTO(entity);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching asset classification ", exception);
            throw new FrameworkException("Exception occur while fetching asset classification ", exception);
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AssetClassificationDTO getclassRevById(final Long assetClassRevId) {
        AssetClassificationDTO dto = null;
        try {
            final AssetClassificationRev entity = assetClassificationRepo.findClassRevByAssetId(assetClassRevId);
            if (entity != null) {
                dto = ClassificationInfoMapper.mapToDTORev(entity);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occur while fetching asset classification ", exception);
            throw new FrameworkException("Exception occur while fetching asset classification ", exception);
        }
        return dto;
    }

    /**
     * Revised Option
     * 
     */
    /*
     * @Override
     * @Transactional public AssetClassificationDTO getclassRevByAssetId(final Long assetId) { AssetClassificationDTO dto = null;
     * try { final AssetClassificationRev entity = assetClassificationRevRepo.findClassRevByAssetId(assetId); if (entity != null)
     * { dto = ClassificationInfoMapper.mapToDTORev(entity); } } catch (Exception exception) {
     * LOGGER.error("Exception occur while fetching asset classification ", exception); throw new
     * FrameworkException("Exception occur while fetching asset classification ", exception); } return dto; }
     */
    /*
     * @Override
     * @Transactional(readOnly=true) public List<LookUp> getCostCenterbyOrgId(final Long orgId, final int langId) { Organisation
     * organisation = new Organisation(); organisation.setOrgid(orgId); final Long statusCpdId =
     * CommonMasterUtility.getLookUpFromPrefixLookUpValue( PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
     * PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation).getLookUpId(); final Long sacLedType =
     * CommonMasterUtility.getLookUpFromPrefixLookUpValue(
     * PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
     * organisation).getLookUpId(); final Long cpdIdAcheadTypes =
     * CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE, AccountPrefix.COA.toString(), langId,
     * organisation).getLookUpId(); final List<Object[]> acHeadList = secondaryheadMasterService
     * .findByLedgerHeadTypeAndAccountHead(orgId, statusCpdId, sacLedType, cpdIdAcheadTypes); // it will check list is null or not
     * and after that it will convert list of object into list of lookUp final List<LookUp> acHeadLookUp =
     * acHeadList.stream().filter(obj -> obj != null).map(obj -> { final LookUp obj2 = new LookUp(); obj2.setLookUpId((long)
     * obj[0]); obj2.setDescLangFirst((String) obj[1]); return obj2; }).collect(Collectors.toList()); return acHeadLookUp; }
     */

    @Override
    @Transactional
    public void updateDepartment(Long assetId, Long assetDepartment) {
        assetClassificationRepo.updateDepartment(assetId, assetDepartment);
    }

    @Override
    @Transactional
    public void updateLocation(Long id, Long locationId) {
        assetClassificationRepo.updateLocation(id, locationId);
    }

    @Override
    @Transactional
    public boolean updateGeoInformation(Long assetId, GeoCoordinatesDTO geoCoordDto) {
        int rowsUpdated = assetClassificationRepo.updateGeoLocation(assetId, geoCoordDto.getLatitude(),
                geoCoordDto.getLongitude());
        if (rowsUpdated == 0) {
            return false;
        }
        return true;
    }

}
