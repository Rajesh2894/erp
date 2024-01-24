package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;

public interface IServiceMasterDAO {

    public abstract String getServiceNameByServiceId(long smServiceId);

    public String getServiceShortDescByServiceId(long smServiceId);

    public ServiceMaster getServiceMasterByServiceId(long serviceId, Long orgId);

    public ServiceMaster getServiceMasterByShortCode(String shortCode, Long orgId);
    
	public  List<ServiceMaster> getServicesByShortCodes(List<String> serviceShortCodeList, Long orgId, Long deptId);
	
	String getServiceNameByServiceIdLangId(final long smServiceId, int langId);

}