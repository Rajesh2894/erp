package com.abm.mainet.quartz;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

@Component
public class QuartzApplicationStatus {

    @Autowired
    private IPortalServiceMasterService portalServiceMasterService;

    public void invokeQuartz_For_ApplicationStatus(final QuartzSchedulerMaster runtimeBean, final List<Object> parameterList)
            throws FrameworkException, SQLException {

        final Organisation organisation = runtimeBean.getOrgId();

        portalServiceMasterService.getApplicationStatusOpen(organisation);

    }

}
