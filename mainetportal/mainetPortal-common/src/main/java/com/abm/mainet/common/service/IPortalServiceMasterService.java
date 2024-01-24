package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalPrifixMappingMaster;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.PortalDepartmentDTO;

public interface IPortalServiceMasterService {

    Long getServiceId(String shortCode, Long orgId);

    public void saveApplicationMaster(ApplicationPortalMaster applicationMaster, Double paymentAmount, int docListSize);

    String findServiceNameForServiceId(Long serviceId);

    public PortalPrifixMappingMaster getMappedPrefix(String prefixType, Long portalPrefix);

    PortalService getPortalService(Long serviceId);

    String getServiceURL(Long psmSmfid);

    boolean saveCFCCitizenId(String cfcCitizenId, Long long1);

    String getServiceShortName(Long psmDpDeptid);

    Long getServiceDeptIdId(Long serviceId);

    String checkListAvailable(long applicationId);

    public void getApplicationStatusOpen(Organisation organization);

    List<Object[]> findApplicationInfo(long applicationId, long orgId);

    public List<ApplicationPortalMaster> getCertificateList(List<String> serviceShortCodeList, Organisation organisation);

    public void updateDigitalSignFlag(Long applicationId, Long serviceId, Organisation organisation);

    public PortalService getService(Long serviceId, Long orgId);

    Date getLastUpdated();

    List<PortalDepartmentDTO> getAllDeptAndService(Long orgid, Long groupid, int langId);

	List<String> getSmfaction(Long orgid, Long groupid, int languageId);

}
