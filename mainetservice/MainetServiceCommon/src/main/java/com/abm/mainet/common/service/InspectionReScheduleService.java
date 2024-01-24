package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.dto.TbVisitorSchedule;
@Service
public interface InspectionReScheduleService {
	void updateAppointmentResc(TbVisitorSchedule marriageDTO, List<Long> appointmentIds);
}
