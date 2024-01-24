/*
 * Created on 19 Aug 2015 ( Time 17:12:01 ) Generated by Telosys Tools Generator
 * ( version 2.1.1 )
 */
package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;

/**
 * Business Service Interface for entity TbServicesMst.
 */
public interface TbServicesMstService {

    /**
     * Loads an entity from the database using its Primary Key
     *
     * @param smServiceId
     * @return entity
     */
    TbServicesMst findById(Long smServiceId);

    /**
     * Loads all entities.
     *
     * @return all entities
     */
    List<TbServicesMst> findAll();

    /**
     * Updates the given entity in the database
     *
     * @param entity
     * @return
     */
    TbServicesMst update(TbServicesMst entity);

    /**
     * Creates the given entity in the database
     *
     * @param entity
     * @return
     */
    TbServicesMst create(TbServicesMst entity);

    /**
     * Deletes an entity using its Primary Key
     *
     * @param smServiceId
     */
    void delete(Long smServiceId);

    /**
     *
     * @param orgId
     * @return
     */
    List<TbServicesMst> findAllServiceListByOrgId(Long orgId);

    /**
     *
     * @param deptId
     * @param serviceId
     * @param organisation
     * @return
     */
    List<TbServicesMst> findByDeptServiceId(Long deptId, Long serviceId, Long organisation);

    /**
     *
     * @param deptId
     * @param orgId
     * @return
     */
    List<TbServicesMst> findByDeptId(Long deptId, Long orgId);

    /**
     *
     * @param prefix
     * @param cpdCode
     * @param organisation
     * @return
     */
    Long getCpdId(String prefix, String cpdCode, Organisation organisation);

    /**
     *
     * @param smServiceName
     * @param valueOf
     * @return
     */
    Long checkForDuplicateService(String smServiceName, Long orgId);

    /**
     * @param orgId
     * @param serviceId
     * @param groupName
     * @return
     */
    // List<ServiceChecklistDTO> getSearchData(Long orgId, Long serviceId, String groupName);

    Long findDepartmentIdByserviceid(long Serviceid, long orgid);

    /**
     * @param deptId
     * @param orgId
     * @return
     */
    List<TbServicesMst> findALlActiveServiceByDeptId(Long deptId, Long orgId);

    /**
     * @param serviceId
     * @param orgId
     * @return
     */
    String findServiceNameById(Long serviceId, Long orgId);

    /**
     * @param TbServiceMst
     * @return
     */
    ServiceMaster findShortCodeByOrgId(String smShortdesc, Long orgId);

    /**
     * @param orgId
     * @param groupId
     * @return
     */
    // List<ServiceChecklistDTO> getSearchDocumentData(Long orgId, Long groupId);

    /**
     * @param orgId
     * @param serviceId
     * @return
     */
    // List<ServiceChecklistDTO> getSearchChecklistData(Long orgId, Long serviceId);

    List<TbDepartment> findDeptByScrutinyFlag(Long orgId);

    List<TbServicesMst> findServiceByDeptScrutiny(Long deptId, Long orgId, Long servActive);

    List<ServiceMaster> findAllServicesByOrgId(Long orgId);

    String getServiceShortDescByServiceId(long smServiceId);

    public String getServiceNameByServiceId(final long smServiceId);

    List<TbServicesMst> findListOfServiceByDepartment(List<String> approvalList, Long orgId, Long deptId);

    /**
     * this service is used to push service to created organization
     * @param serviceList
     */
    void createOrganisationServices(List<TbServicesMst> serviceList);
    
    String getServiceNameByServiceIdLangId(final long smServiceId, int langId);

	List<TbServicesMst> findActiveServiceByDeptIdAndNotActualSer(Long deptId, Long orgId);

}
