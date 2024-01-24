package com.abm.mainet.mobile.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.RestCommonExeception;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.mobile.dao.DeviceRegistrationDAO;
import com.abm.mainet.mobile.domain.DeviceRegistrationEntity;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.DeviceRegistrationReqDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
@Service
public class DeviceRegistrationImpl implements DeviceRegistration {

    private static final Logger LOG = Logger.getLogger(DeviceRegistrationImpl.class);

    @Resource
    private DeviceRegistrationDAO dao;

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.rest.mobile.service.impl.DeviceRegistration#doRegistrationService(com.abm.mainetservice.rest.mobile.
     * dto.DeviceRegistrationReqDTO)
     */
    @Override
    @Transactional
    public CommonAppResponseDTO doDevRegistrationService(final DeviceRegistrationReqDTO reqDTO) {

        LOG.info("Start the doDevRegistrationService in");
        CommonAppResponseDTO appResponseDTO = null;
        Boolean flag = false;
        try {
            appResponseDTO = new CommonAppResponseDTO();

            final DeviceRegistrationEntity entity = new DeviceRegistrationEntity();

            entity.setDevId(reqDTO.getAppDeviceId());
            entity.setRegId(reqDTO.getAppRegisterId());
            entity.setUserId(reqDTO.getUserId());
            entity.setActiveFlag(reqDTO.getActiveFlag());
            entity.setRegDate(reqDTO.getRegisterationDate());
            entity.setOrgId(reqDTO.getOrgId());
            entity.setLangid(reqDTO.getLangId());
            entity.setLmoddate(new Date());
            entity.setLgipmac(Utility.getMacAddress());
            final DeviceRegistrationEntity enntity = dao.getDevRegistrationService(reqDTO.getUserId(), reqDTO.getOrgId());
            if (enntity != null) {
                enntity.setActiveFlag(reqDTO.getActiveFlag());
                enntity.setRegDate(reqDTO.getRegisterationDate());
                flag = dao.doDevRegistrationService(enntity);
            } else {
                flag = dao.doDevRegistrationService(entity);
            }

            if (flag) {
                appResponseDTO.setOrgId(reqDTO.getOrgId());
                appResponseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.dev.regisration.success.msg"));
                appResponseDTO.setStatus(MainetConstants.COMMON_STATUS.SUCCESS);
                appResponseDTO.setUserId(reqDTO.getUserId());

            } else {
                appResponseDTO.setOrgId(reqDTO.getOrgId());
                appResponseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.dev.regisration.fail.msg"));
                appResponseDTO.setStatus(MainetConstants.COMMON_STATUS.FAIL);
                appResponseDTO.setUserId(reqDTO.getUserId());
            }

        } catch (final Exception exception) {
            LOG.error("Exeception occcur in doRegistrationService", exception);
            throw new RestCommonExeception(" Exeception occcur in doRegistrationService", exception);
        }

        return appResponseDTO;
    }

}
