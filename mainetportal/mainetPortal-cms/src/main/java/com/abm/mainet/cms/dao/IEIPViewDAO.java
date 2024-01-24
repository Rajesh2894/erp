package com.abm.mainet.cms.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.cms.domain.EIPEmpRightView;
import com.abm.mainet.cms.domain.EIPOnlineServicesView;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public interface IEIPViewDAO {

    public abstract List<EIPOnlineServicesView> getEIPOnlineServiceViewForCurrentOrg(Organisation org);

    public abstract List<EIPEmpRightView> getEIPEMpRightsViewForEmployee(Employee employee, Organisation org);

    public abstract List<EIPOnlineServicesView> getEIPOnlineServiceBySmfId(Long smfid);

    public abstract Map<Long, String> getGroupList();

}