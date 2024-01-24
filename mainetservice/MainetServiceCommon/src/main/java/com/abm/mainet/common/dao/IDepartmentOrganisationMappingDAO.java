package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.DeptOrgMap;

public interface IDepartmentOrganisationMappingDAO {

    public abstract List<DeptOrgMap> getaMapping(String activeStatus);

}