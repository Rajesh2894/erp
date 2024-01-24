package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ActivityManagementRepository;
import com.abm.mainet.common.domain.ActivityManagement;
import com.abm.mainet.common.domain.ActivityManagementHistory;
import com.abm.mainet.common.dto.ActivityManagementDTO;
import com.abm.mainet.common.master.mapper.ActivityManagementMapper;

@Service
public class ActivityManagementService implements IActivityManagementService {

    @Autowired
    private ActivityManagementRepository activityManagement;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ActivityManagementMapper mapper;

    @Override
    public List<ActivityManagementDTO> getAllIndividualAndTeamActivity(Long orgId, Long empId) {
        List<ActivityManagement> activities = activityManagement.findAllIndividualAndTeamActivity(orgId, empId);
        return mapper.mapEntityListtoDtoList(activities);
    }

    @Override
    public List<ActivityManagementDTO> findAllActivityByOrgid(Long orgId) {
        List<ActivityManagement> activities = activityManagement.findAllByOrgid(orgId);
        return mapper.mapEntityListtoDtoList(activities);
    }

    @Override
    public ActivityManagementDTO saveOrUpdateActivity(ActivityManagementDTO activity) {
        ActivityManagement entity = mapper.mapDtoToEntity(activity);
        ActivityManagementHistory entityHist = new ActivityManagementHistory();

        if (null == entity.getActId()) {
            entityHist.setStatus(MainetConstants.Transaction.Mode.ADD);
        } else {
            entityHist.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        }

        entity = activityManagement.save(entity);
        auditService.createHistory(entity, entityHist);

        return mapper.mapEntitytoDto(entity);
    }

    @Override
    public ActivityManagementDTO getActivity(Long activityId) {
        ActivityManagement activity = activityManagement.findOne(activityId);
        return mapper.mapEntitytoDto(activity);
    }

    @Override
    public boolean deleteActivity(Long activityId) {
        // TODO: set delete flag activity.setacti
        ActivityManagement activity = activityManagement.findOne(activityId);
        ActivityManagementHistory entityHist = new ActivityManagementHistory();
        activityManagement.delete(activity);
        entityHist.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(activity, entityHist);
        return true;
    }

}
