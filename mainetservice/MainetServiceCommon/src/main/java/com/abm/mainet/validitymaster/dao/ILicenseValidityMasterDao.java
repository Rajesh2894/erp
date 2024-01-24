package com.abm.mainet.validitymaster.dao;

import java.util.List;

import com.abm.mainet.validitymaster.domain.LicenseValidityMasterEntity;

/**
 * @author cherupelli.srikanth
 * @since 17 september 2019
 */
public interface ILicenseValidityMasterDao {

    List<LicenseValidityMasterEntity> searchLicenseValidtyData(Long orgId, Long deptId, Long serviceId, Long triCod1, Long licType);
}
