package com.abm.mainet.mrm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;

@WebService
public interface IAppointmentService {

    List<AppointmentDTO> findByAppointmentDateAndOrg(Date date, Long orgId);

    List<AppointmentDTO> searchAppointments(Date appointmentDate, Long orgId);

    void updateAppointmentResc(MarriageDTO marriageDTO, List<Long> appointmentIds);

    AppointmentDTO getAppointmentData(Long appointmentId);
}
