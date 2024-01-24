package com.abm.mainet.council.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.council.domain.CouncilActionTakenEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;
import com.abm.mainet.council.domain.CouncilProposalMasterEntity;
import com.abm.mainet.council.dto.CouncilActionTakenDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.repository.CouncilActionTakenRepository;
import com.abm.mainet.council.repository.CouncilMeetingMasterRepository;
import com.abm.mainet.council.repository.CouncilProposalMasterRepository;

@Service
public class CouncilActionTakenServiceImpl implements ICouncilActionTakenService {

	@Autowired
	CouncilActionTakenRepository actionTakenRepository;
	@Autowired
	CouncilMeetingMasterRepository councilMeetingRepository;

	@Autowired
	CouncilProposalMasterRepository proposalMaterRepository;

	@Transactional
	public void saveAction(List<CouncilActionTakenEntity> councilActionTakenEntity) {
		try {
			actionTakenRepository.save(councilActionTakenEntity);
		} catch (Exception exception) {
			throw new FrameworkException("Error occured while saving the CouncilActionTakenEntity data", exception);
		}
	}

	@Transactional
	public List<CouncilActionTakenDto> getActionTakenDetailsByPropsalId(Long proposalId, Long orgId) {
		List<CouncilActionTakenDto> actionTakenDtos = new ArrayList<>();
		List<CouncilActionTakenEntity> actionTakenentity = actionTakenRepository.findByProposalIdAndOrgId(proposalId,
				orgId);
		if (actionTakenentity != null) {
			actionTakenentity.forEach(actionEntity -> {
				CouncilActionTakenDto councilActionTakenDto = new CouncilActionTakenDto();
				BeanUtils.copyProperties(actionEntity, councilActionTakenDto);
				actionTakenDtos.add(councilActionTakenDto);

			});
		}
		return actionTakenDtos;

	}

	// fetch agendaIds from Meeting master table
	@Transactional
	public List<CouncilProposalMasterDto> fetchProposalsByAgendaId(Long orgId) {
		List<CouncilProposalMasterDto> proposalList = new ArrayList<>();
		List<CouncilMeetingMasterEntity> meetingEntities = councilMeetingRepository.findAllByOrgIdOrderByMeetingIdDesc(orgId);
		
		meetingEntities.forEach(meeting -> {
			List<CouncilProposalMasterEntity> proposalMasterList = proposalMaterRepository
					.findAllByAgendaId(meeting.getAgendaId().getAgendaId());
			proposalMasterList.forEach(proposalEntity -> {
				CouncilProposalMasterDto proposalMasterDto = new CouncilProposalMasterDto();
				BeanUtils.copyProperties(proposalEntity, proposalMasterDto);
				proposalList.add(proposalMasterDto);
			});
		});
		Comparator<CouncilProposalMasterDto> comparing = Comparator.comparing(CouncilProposalMasterDto::getProposalId, 
                Comparator.nullsLast(Comparator.naturalOrder())
              );
        Collections.sort(proposalList,comparing);
		return proposalList;

	}

}
