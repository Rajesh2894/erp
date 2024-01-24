/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEIPViewDAO;
import com.abm.mainet.cms.domain.EIPEmpRightView;
import com.abm.mainet.cms.domain.EIPOnlineServicesView;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author rajendra.bhujbal
 * @since 06 May 2014
 */
@Service
public class EIPViewService implements IEIPViewService {

    @Autowired
    private IEIPViewDAO eipViewDAO;

    @Override
    @Transactional
    public List<EIPOnlineServicesView> getEIPOnlineServiceViewForCurrentOrg(final Organisation org) {

        List<EIPOnlineServicesView> eipOnlineServicesList = null;
        if (org != null) {
            eipOnlineServicesList = eipViewDAO.getEIPOnlineServiceViewForCurrentOrg(org);
        }
        if (eipOnlineServicesList == null) {
            eipOnlineServicesList = new ArrayList<>();
        }
        return eipOnlineServicesList;
    }

    @Override
    @Transactional
    public List<EIPEmpRightView> getEIPEMpRightsViewForEmployee(final Employee employee, final Organisation org) {

        List<EIPEmpRightView> eipEmpRightList = null;
        if ((employee != null) && (org != null)) {
            eipEmpRightList = eipViewDAO.getEIPEMpRightsViewForEmployee(employee, org);
        }
        if (eipEmpRightList == null) {
            eipEmpRightList = new ArrayList<>();
        }
        return eipEmpRightList;
    }

    @Override
    @Transactional
    public List<EIPOnlineServicesView> getEIPOnlineServiceBySmfId(final Long smfid) {

        return eipViewDAO.getEIPOnlineServiceBySmfId(smfid);
    }

    @Override
    public Map<Long, String> getGroupList() {

        return eipViewDAO.getGroupList();
    }

}
