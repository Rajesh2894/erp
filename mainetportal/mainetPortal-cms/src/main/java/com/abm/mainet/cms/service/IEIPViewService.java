/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.domain.EIPEmpRightView;
import com.abm.mainet.cms.domain.EIPOnlineServicesView;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author rajendra.bhujbal
 * @since 06 May 2014
 */
@Transactional(readOnly = true)
public interface IEIPViewService {

    public List<EIPOnlineServicesView> getEIPOnlineServiceViewForCurrentOrg(Organisation org);

    public List<EIPEmpRightView> getEIPEMpRightsViewForEmployee(Employee employee, Organisation org);

    public List<EIPOnlineServicesView> getEIPOnlineServiceBySmfId(Long smfid);

    public Map<Long, String> getGroupList();

}
