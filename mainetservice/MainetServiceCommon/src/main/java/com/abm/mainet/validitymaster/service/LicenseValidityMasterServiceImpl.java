package com.abm.mainet.validitymaster.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.validitymaster.dao.ILicenseValidityMasterDao;
import com.abm.mainet.validitymaster.domain.LicenseValidityMasterEntity;
import com.abm.mainet.validitymaster.domain.LicenseValidityMasterHistoryEntity;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.repository.LicenseValidityMasterRepository;

/**
 * @author cherupelli.srikanth
 *
 */
@Service
public class LicenseValidityMasterServiceImpl implements ILicenseValidityMasterService {

    @Autowired
    private LicenseValidityMasterRepository licenseValidityMasterRepository;

    @Resource
    private AuditService auditService;

    @Override
    @Transactional
    public void saveLicenseValidityData(LicenseValidityMasterDto masterDto) {
        try {
            LicenseValidityMasterEntity entity = new LicenseValidityMasterEntity();
            BeanUtils.copyProperties(masterDto, entity);
            licenseValidityMasterRepository.save(entity);
            LicenseValidityMasterHistoryEntity historyEntity = new LicenseValidityMasterHistoryEntity();
            historyEntity.sethStatus(MainetConstants.FlagA);
            auditService.createHistory(entity, historyEntity);
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while saving the license validity data", exception);
        }

    }

    @Override
    @Transactional
    public void updateLicenseValidityData(LicenseValidityMasterDto masterDto) {
        try {
            LicenseValidityMasterEntity entity = new LicenseValidityMasterEntity();
            BeanUtils.copyProperties(masterDto, entity);
            licenseValidityMasterRepository.save(entity);
            LicenseValidityMasterHistoryEntity historyEntity = new LicenseValidityMasterHistoryEntity();
            historyEntity.sethStatus(MainetConstants.FlagA);
            auditService.createHistory(entity, historyEntity);
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while updating the license validity data", exception);
        }

    }

    @Override
    @Transactional
    public List<LicenseValidityMasterDto> searchLicenseValidityData(Long orgId, Long deptId, Long serviceId,Long triCod1,Long licType) {
        List<LicenseValidityMasterDto> masterDtoList = new ArrayList<>();
        try {
            List<LicenseValidityMasterEntity> entityList = ApplicationContextProvider.getApplicationContext()
                    .getBean(ILicenseValidityMasterDao.class).searchLicenseValidtyData(orgId, deptId, serviceId,triCod1,licType);
            if (CollectionUtils.isNotEmpty(entityList)) {
                entityList.forEach(entity -> {
                    LicenseValidityMasterDto masterDto = new LicenseValidityMasterDto();
                    BeanUtils.copyProperties(entity, masterDto);
                    masterDtoList.add(masterDto);
                });
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while fetching the license validity data", exception);
        }

        return masterDtoList;
    }

    @Override
    @Transactional
    public LicenseValidityMasterDto searchLicenseValidityByLicIdAndOrgId(Long licId, Long orgId) {
        LicenseValidityMasterDto masterDto = new LicenseValidityMasterDto();
        try {
            LicenseValidityMasterEntity entity = licenseValidityMasterRepository.findByLicIdAndOrgId(licId, orgId);
            if (entity != null) {
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while fetching the license validity data", exception);
        }

        return masterDto;
    }

    @Override
    @Transactional
    public LicenseValidityMasterDto getLicValDataByDeptIdAndServiceId(Long orgId, Long deptId, Long serviceId, Long licenseTypeId) {
        LicenseValidityMasterDto masterDto = null;
        try {
            LicenseValidityMasterEntity entity = licenseValidityMasterRepository.findByDeptIdAndServiceId(deptId,
                    serviceId, orgId,licenseTypeId);
            if (entity != null) {
                masterDto = new LicenseValidityMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while fetching the license validity data", exception);
        }

        return masterDto;
    }
    
    @Override
    @Transactional
    public LicenseValidityMasterDto getLicValDataByCategoryAndSubCategory(Long orgId, Long deptId, Long serviceId, Long licenseTypeId,Long licCatgry,Long licSubCtgry) {
        LicenseValidityMasterDto masterDto = null;
        try {
            LicenseValidityMasterEntity entity = licenseValidityMasterRepository.findByCategoryAndSubCategory(deptId,
                    serviceId, orgId,licenseTypeId,licCatgry,licSubCtgry);
            if (entity != null) {
                masterDto = new LicenseValidityMasterDto();
                BeanUtils.copyProperties(entity, masterDto);
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception occured while fetching the license validity data", exception);
        }

        return masterDto;
    }

}
