package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.workflow.dto.EventDTO;
import com.abm.mainet.common.workflow.dto.ServicesEventDTO;

/**
 * This class provides CRUD services for Event Master data management.
 * 
 *
 */
public interface EventMasterService {

    /**
     * This method will save or update event master data.
     * 
     * @param servicesEventDTOList
     * @param emp
     * @param org
     * @return
     */
    public boolean saveOrUpdateEventMaster(ServicesEventDTO servicesEventDTOList, Employee emp, Organisation org);

    /**
     * This method will find Event master data by using given smfAction as flag and active status
     * 
     * @param flag
     * @param activeStatus
     * @return
     * @throws RuntimeException
     */
    public List<Object[]> findEventList(String flag, String activeStatus) throws RuntimeException;

    /**
     * This method will find event by departmentID, orgId, and serviceId
     * 
     * @param deptId
     * @param orgId
     * @param serviceId
     * @return
     */
    public List<EventDTO> findEventsByDeptOrgService(Long deptId, Long orgId, Long serviceId);

    /**
     * This method will find events mapped with departmentID, orgId, serviceId, smfAction as flag and active status
     * 
     * @param flag
     * @param activeStatus
     * @param deptId
     * @param serviceId
     * @param orgId
     * @return
     * @throws RuntimeException
     */
    public List<Object[]> findMappedEventList(String flag, String activeStatus, Long deptId, Long serviceId, Long orgId)
            throws RuntimeException;

    /**
     * This method will find events not mapped with departmentID, orgId, serviceId, smfAction as flag and active status.
     * 
     * @param flag
     * @param activeStatus
     * @param deptId
     * @param serviceId
     * @param orgId
     * @return
     * @throws RuntimeException
     */
    public List<Object[]> findNonMappedEventList(String flag, String activeStatus, Long deptId, Long serviceId, Long orgId)
            throws RuntimeException;

    /**
     * This method will update event and department service mapping
     * 
     * @param serviceEventDto
     * @param newList
     * @param deletedList
     * @param org
     * @param employee
     * @return
     */
    public boolean updateEventsMapping(ServicesEventDTO serviceEventDto, List<String> newList, List<String> deletedList,
            Organisation org, Employee employee);

    /**
     * This method will soft delete event of give Id.
     * 
     * @param flag
     * @param id
     */
    void delete(String flag, Long id);

    /**
     * This method will check if events are already exist or not.
     * 
     * @param serviceEventDto
     * @param isDeleted
     * @return
     */
    boolean checkEventsForServiceIdExist(ServicesEventDTO serviceEventDto, String isDeleted);
}
