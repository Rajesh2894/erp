package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDao;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.PortalServiceDTO;
import com.abm.mainet.common.exception.FrameworkException;

@Service
public class ServiceMasterService implements IServiceMasterService {

    @Autowired
    private IServiceMasterDao iServiceMasterDao;

    @Override
    @Transactional(readOnly = true)
    public Long getServiceId(final String shortCode, final Long orgId) {
        return iServiceMasterDao.getSeviceId(shortCode, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public PortalService getPortalServiceMaster(final String shortCode, final Long orgId) {

        return iServiceMasterDao.getPortalServiceMaster(shortCode, orgId);
    }

    @Override
    public void create(ServiceMaster serviceMaster) {
        iServiceMasterDao.create(serviceMaster);
    }

    @Override
    public void createPortalService(PortalServiceDTO portalserviceDto) {
        PortalService portalService = new PortalService();
        BeanUtils.copyProperties(portalserviceDto, portalService);
        iServiceMasterDao.createPortalService(portalService);
    }

    /**
     * used for create portal service 
     * @param portalService
     */
    
    @Override
    @Transactional
    public PortalService createRestPortalService(PortalService portalService) {
        if (portalService.getIsDeleted().equals(MainetConstants.Common_Constant.ACTIVE_FLAG)) {
            portalService.setIsDeleted(MainetConstants.Common_Constant.NUMBER.ZERO);
        } else {
            portalService.setIsDeleted(MainetConstants.Common_Constant.NUMBER.ONE);
        }
        return iServiceMasterDao.createRestPortalService(portalService);
    }

    /**
     * used for get short code on the basis of
     * @param orgid
     * @param shortName
     */
    
    @Override
    @Transactional
    public PortalService findShortCodeByOrgId(String shortName, Long orgid) {

        return iServiceMasterDao.getRestPortalService(shortName, orgid);
    }

    @Override
    @Transactional(readOnly=true)
	public List<Object[]> findAllActiveServicesByDepartment(Long orgid, Long deptId, String activeStatus) {
		return iServiceMasterDao.findAllActiveServicesByDepartment(orgid, deptId, activeStatus);
		
	}



}
