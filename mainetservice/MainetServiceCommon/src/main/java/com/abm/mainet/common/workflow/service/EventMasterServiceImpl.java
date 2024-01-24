package com.abm.mainet.common.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.master.repository.TbServicesMstJpaRepository;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.dto.EventDTO;
import com.abm.mainet.common.workflow.dto.ServicesEventDTO;
import com.abm.mainet.common.workflow.repository.IServicesEventEntityRepository;

/**
 * This class provides CRUD services for Event Master data management.
 * 
 *
 */
@Service
public class EventMasterServiceImpl implements EventMasterService {

    @Autowired
    private IServicesEventEntityRepository servicesEventEntityRepository;

    @Autowired
    private TbServicesMstJpaRepository servicesMstJpaRepository;

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public boolean saveOrUpdateEventMaster(final ServicesEventDTO serviceEventDto, final Employee employee,
            final Organisation org) {

        ServicesEventEntity servicesEventEntity = null;

        boolean status = false;

        final List<String> eventList = serviceEventDto.getEventMapId();
        for (final String string : eventList) {
            servicesEventEntity = new ServicesEventEntity();
            final SystemModuleFunction systemModuleFunction = new SystemModuleFunction();
            systemModuleFunction.setSmfid(Long.valueOf(string));
            servicesEventEntity.setSmServiceId(serviceEventDto.getSmServiceId());
            servicesEventEntity.setCreatedDate(new Date());
            servicesEventEntity.setIsDeleted(MainetConstants.MENU.N);
            servicesEventEntity.setOrgId(serviceEventDto.getOrgId());
            servicesEventEntity.setSystemModuleFunction(systemModuleFunction);
            servicesEventEntity.setCreatedBy(employee);
            servicesEventEntity.setLgIpMac(Utility.getMacAddress());
            servicesEventEntity.setDeptId(serviceEventDto.getDeptId());
            servicesEventEntity.setOrgId(org);
            servicesEventEntityRepository.save(servicesEventEntity);
            status = true;
        }

        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findEventList(final String flag, final String activeStatus) {

        return servicesEventEntityRepository.findAllEvents(flag, activeStatus);
    }

    @Override
    @Transactional
    public List<EventDTO> findEventsByDeptOrgService(final Long deptId, final Long orgId, final Long serviceId) {

        List<Object[]> list = null;

        final Organisation org = new Organisation();
        org.setOrgid(orgId);

        if (serviceId == 0) {
            list = servicesEventEntityRepository.findEventsByDeptOrg(deptId, org);
        } else {
            list = servicesEventEntityRepository.findEventsByDeptOrgService(deptId, org, serviceId);
        }

        final List<EventDTO> eventResponseGrids = new ArrayList<>();
        EventDTO eventResponseGrid = null;
        List<Object[]> serviceMaster = null;

        for (final Object[] objects : list) {
            eventResponseGrid = new EventDTO();
            eventResponseGrid.setEventId(Long.valueOf(objects[0].toString()));
            eventResponseGrid.setEventName(objects[1] != null ? objects[1].toString() : null);
            eventResponseGrid.setEventNameReg(objects[2] != null ? objects[2].toString() : null);
            eventResponseGrid.setEventDesc(objects[3] != null ? objects[3].toString() : null);
            eventResponseGrid.setServiceUrl(objects[4] != null ? objects[4].toString() : null);
            eventResponseGrid.setServiceEventId(Long.valueOf(objects[5].toString()));
            eventResponseGrid.setServiceId(Long.valueOf(objects[6].toString()));
            eventResponseGrid.setIsdeleted(objects[7] != null ? objects[7].toString() : null);

            serviceMaster = servicesMstJpaRepository.findService(Long.valueOf(objects[6].toString()), orgId);
            for (final Object[] master : serviceMaster) {
                eventResponseGrid.setServiceName(master[1] != null ? master[1].toString() : null);
                eventResponseGrid.setServiceNameReg(master[2] != null ? master[2].toString() : null);
            }
            eventResponseGrids.add(eventResponseGrid);
        }

        return eventResponseGrids;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findMappedEventList(final String flag, final String activeStatus, final Long deptId,
            final Long serviceId, final Long orgId)
            throws RuntimeException {
        return servicesEventEntityRepository.findMappedEventList(flag, activeStatus, deptId, serviceId, orgId);
    }

    @Override
    public List<Object[]> findNonMappedEventList(final String flag, final String activeStatus, final Long deptId,
            final Long serviceId, final Long orgId)
            throws RuntimeException {
        return servicesEventEntityRepository.findNonMappedEventList(flag, activeStatus, deptId, serviceId, orgId);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public boolean updateEventsMapping(final ServicesEventDTO serviceEventDto, final List<String> newList,
            final List<String> deletedList,
            final Organisation org, final Employee employee) {

        ServicesEventEntity servicesEventEntity;
        ServicesEventEntity toBeDeleted = null;

        for (final String newLst : newList) {
            servicesEventEntity = new ServicesEventEntity();
            final SystemModuleFunction systemModuleFunction = new SystemModuleFunction();
            systemModuleFunction.setSmfid(Long.valueOf(newLst));
            servicesEventEntity.setSmServiceId(serviceEventDto.getSmServiceId());
            servicesEventEntity.setCreatedDate(new Date());
            servicesEventEntity.setIsDeleted(MainetConstants.MENU.N);
            servicesEventEntity.setOrgId(serviceEventDto.getOrgId());
            servicesEventEntity.setSystemModuleFunction(systemModuleFunction);
            servicesEventEntity.setCreatedBy(employee);
            servicesEventEntity.setLgIpMac(Utility.getMacAddress());
            servicesEventEntity.setDeptId(serviceEventDto.getDeptId());
            servicesEventEntity.setOrgId(org);
            servicesEventEntityRepository.save(servicesEventEntity);
        }

        for (final String deleteEvnt : deletedList) {
            toBeDeleted = new ServicesEventEntity();
            if (deleteEvnt != null) {
                toBeDeleted = servicesEventEntityRepository.findEventByEventId(Long.valueOf(deleteEvnt),
                        serviceEventDto.getDeptId().getDpDeptid(), org, serviceEventDto.getSmServiceId());
                servicesEventEntityRepository.delete(toBeDeleted.getServiceEventId());
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void delete(final String flag, final Long id) {

        ServicesEventEntity entity = new ServicesEventEntity();
        entity = servicesEventEntityRepository.findOne(id);
        if (flag.equals(MainetConstants.IsDeleted.DELETE)) {
            entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        } else {
            entity.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        }
        servicesEventEntityRepository.save(entity);
    }

    @Override
    public boolean checkEventsForServiceIdExist(final ServicesEventDTO serviceEventDto, final String isDeleted) {

        final List<String> eventList = serviceEventDto.getEventMapId();
        final List<Long> eventsIdList = new ArrayList<>();

        for (final String string : eventList) {
            eventsIdList.add(Long.valueOf(string));
        }

        boolean flag = false;
        final Long count = servicesEventEntityRepository.checkEventsForServiceIdExist(serviceEventDto.getSmServiceId(),
                serviceEventDto.getOrgId(), serviceEventDto.getDeptId(), eventsIdList, isDeleted);
        if (count == 0) {
            flag = true;
        }

        return flag;
    }

}
