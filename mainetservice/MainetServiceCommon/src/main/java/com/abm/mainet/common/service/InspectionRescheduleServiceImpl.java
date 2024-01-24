package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dto.TbVisitorSchedule;
import com.abm.mainet.common.repository.TbVisitorScheduleJpaRepository;
@Service
public class InspectionRescheduleServiceImpl implements InspectionReScheduleService {
	@Autowired
	private TbVisitorScheduleJpaRepository tbVisitorScheduleJpaRepository;

	@Override
	@Transactional
	public void updateAppointmentResc(TbVisitorSchedule tbVisDto, List<Long> appointmentIds) {
		tbVisitorScheduleJpaRepository.updateAppointmentRescByIds(tbVisDto.getVisD1(), tbVisDto.getVisTime(),
				appointmentIds, tbVisDto.getOrgid(), tbVisDto.getUpdatedBy());

	}

}
