package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.HelpDeskRepository;
import com.abm.mainet.common.domain.HelpDesk;
import com.abm.mainet.common.domain.HelpDeskHistory;
import com.abm.mainet.common.dto.HelpDeskDTO;
import com.abm.mainet.common.master.mapper.HelpDeskMapper;

@Service
public class HelpDeskService implements IHelpDeskService {

    @Autowired
    private HelpDeskRepository helpDesk;

    @Autowired
    private AuditService auditService;

    @Autowired
    private HelpDeskMapper mapper;

    @Override
    public List<HelpDeskDTO> getAllIndividualAndTeamCallLog(Long orgId, Long empId) {
        List<HelpDesk> activities = helpDesk.findAllIndividualAndTeamCallLog(orgId, empId);
        return mapper.mapEntityListtoDtoList(activities);
    }

    @Override
    public List<HelpDeskDTO> findAllCallLogByOrgid(Long orgId) {
        List<HelpDesk> activities = helpDesk.findAllByOrgid(orgId);
        return mapper.mapEntityListtoDtoList(activities);
    }

    @Override
    public HelpDeskDTO saveOrUpdateCallLog(HelpDeskDTO callLog) {
        HelpDesk entity = mapper.mapDtoToEntity(callLog);
        HelpDeskHistory entityHist = new HelpDeskHistory();

        if (null == entity.getHelpId()) {
            entityHist.setStatus(MainetConstants.Transaction.Mode.ADD);
        } else {
            entityHist.setStatus(MainetConstants.Transaction.Mode.UPDATE);
        }

        entity = helpDesk.save(entity);
        auditService.createHistory(entity, entityHist);

        return mapper.mapEntitytoDto(entity);
    }

    @Override
    public HelpDeskDTO getCallLog(Long callLogId) {
        HelpDesk callLog = helpDesk.findOne(callLogId);
        return mapper.mapEntitytoDto(callLog);
    }

    @Override
    public boolean deleteCallLog(Long callLogId) {
        // TODO: set delete flag callLog.setacti
        HelpDesk callLog = helpDesk.findOne(callLogId);
        HelpDeskHistory entityHist = new HelpDeskHistory();
        helpDesk.delete(callLog);
        entityHist.setStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(callLog, entityHist);
        return true;
    }

	@Override
	public List<HelpDeskDTO> getAllCallLog() {
        List<HelpDesk> activities = helpDesk.findAllCallLog();
        return mapper.mapEntityListtoDtoList(activities);
    }

}
