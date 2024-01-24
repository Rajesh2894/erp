package com.abm.mainet.common.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicationStatusResponseVO;
import com.abm.mainet.common.dto.PortalDepartmentDTO;

public interface IPortalServiceMasterDao {

    PortalService getService(Long serviceId, Long orgId);

    Long getSeviceId(String shortCode, Long orgId);

    void saveApplicationMaster(ApplicationPortalMaster applicationMaster);

    String findServiceNameForServiceId(Long serviceId);

    PortalService getPortalService(Long serviceId);

    String getServiceURL(Long psmSmfid);

    boolean saveCfcCitizenId(String cfcCitizenId, Long userId);

    String getServiceShortName(Long psmDpDeptid);

    Long getServiceDeptIdId(Long serviceId);

    String checkListAvailable(long applicationId);

    public void updateApplicationStatus(ApplicationStatusResponseVO appStsResponseVO);

    public List<ApplicationPortalMaster> getApplicationStatusOpen(Organisation organization);

    public List<ApplicationPortalMaster> getApplicationStatusListOpenForUser(Organisation organisation);

    List<Object[]> findApplicationInfo(long applicationId, long orgId);

    public List<ApplicationPortalMaster> getCertificateList(List<String> serviceShortCodeList, Organisation organisation);

    void updateDigitalSignFlag(Long applicationId, Long serviceId, Organisation organisation);

    List<Object[]> getAllServicewithUrl(Long orgId, long gmid);

    List<Department> getAllDepartment(Long orgId);

    public abstract ApplicationPortalMaster getServiceApplicationStatus(Long appId);

    Date getLastUpdated();

    List<PortalDepartmentDTO> getAllDeptAndService(Long orgid, Long groupid, int langId);

	List<String> getSmfaction(Long orgid, Long groupid, int languageId);

}
