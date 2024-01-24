package com.abm.mainet.mrm.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.mrm.dao.IMarriageRegistrationDAO;
import com.abm.mainet.mrm.domain.Appointment;
import com.abm.mainet.mrm.domain.Marriage;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.repository.AppointmentRepository;

import io.swagger.annotations.Api;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.mrm.service.IAppointmentService")
@Api(value = "/appointment")
@Path("/appointment")
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    IMarriageRegistrationDAO marriageRegistrationDAO;

    @Override
    public List<AppointmentDTO> findByAppointmentDateAndOrg(Date date, Long orgId) {
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        List<Appointment> appointments = appointmentRepo.findByAppointmentDateAndOrg(date, orgId);
        appointments.forEach(appointment -> {
            AppointmentDTO appoitDTO = new AppointmentDTO();
            BeanUtils.copyProperties(appointment, appoitDTO);
            // set applicant Name and application id
            TbCfcApplicationMstEntity cfcMaster = ApplicationContextProvider.getApplicationContext()
                    .getBean(ICFCApplicationMasterService.class)
                    .getCFCApplicationByApplicationId(appointment.getMarId().getApplicationId(), appointment.getOrgId());
            if (cfcMaster != null) {
                cfcMaster.getApmFname();
                cfcMaster.getApmLname();
                String middleName = cfcMaster.getApmMname() != null ? cfcMaster.getApmMname() : "";
                appoitDTO.getMarId().setApplicantName(cfcMaster.getApmFname() + " " + middleName + " " + cfcMaster.getApmLname());
            }
            appoitDTO.setAppointmentTime(new SimpleDateFormat("HH:mm").format(appointment.getAppointmentTime()));
            appointmentDTOs.add(appoitDTO);
        });

        return appointmentDTOs;
    }

    @Override
    public List<AppointmentDTO> searchAppointments(Date appointmentDate, Long orgId) {
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        List<Object[]> objList = marriageRegistrationDAO.searchAppointmentData(appointmentDate, orgId);
        objList.forEach(appointment -> {
            AppointmentDTO appoitDTO = new AppointmentDTO();
            MarriageDTO dto = new MarriageDTO();
            appoitDTO.setAppointmentDate((Date) appointment[0]);
            appoitDTO.setAppointmentTime(Utility.dateToString((Date) appointment[0]));
            dto.setMarId(convertBigIntTOLong(appointment[1]));
            dto.setApplicationId(convertBigIntTOLong(appointment[2]));

            appoitDTO.setAppointmentId(convertBigIntTOLong(appointment[4]));
            appoitDTO.setMarId(dto);
            // set applicant Name and application id
            TbCfcApplicationMstEntity cfcMaster = ApplicationContextProvider.getApplicationContext()
                    .getBean(ICFCApplicationMasterService.class)
                    .getCFCApplicationByApplicationId(dto.getApplicationId(), orgId);
            if (cfcMaster != null) {
                cfcMaster.getApmFname();
                cfcMaster.getApmLname();
                String middleName = cfcMaster.getApmMname() != null ? cfcMaster.getApmMname() : "";
                appoitDTO.getMarId().setApplicantName(cfcMaster.getApmFname() + " " + middleName + " " + cfcMaster.getApmLname());
            }

            appointmentDTOs.add(appoitDTO);
        });

        return appointmentDTOs;
    }

    private Long convertBigIntTOLong(Object obj) {
        BigInteger bigInt = (BigInteger) obj;
        return bigInt != null ? bigInt.longValue() : null;
    }

    @Override
    @Transactional
    public void updateAppointmentResc(MarriageDTO marriageDTO, List<Long> appointmentIds) {
        appointmentRepo.updateAppointmentRescByIds(marriageDTO.getAppointmentDTO().getAppointmentDate(),
                stringToTimeConvet(marriageDTO.getAppointmentDTO().getAppointmentTime()), appointmentIds,
                marriageDTO.getAppointmentDTO().getOrgId(), marriageDTO.getAppointmentDTO().getUpdatedBy(),
                marriageDTO.getAppointmentDTO().getLgIpMacUpd());
    }

    public Date stringToTimeConvet(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;
        if (time != null)
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {
            }
        return timeValue;
    }

    @Override
    public AppointmentDTO getAppointmentData(Long appointmentId) {
        AppointmentDTO appDTO = new AppointmentDTO();
        Appointment appointment = appointmentRepo.findByAppointmentId(appointmentId);
        if (appointment != null) {
            BeanUtils.copyProperties(appointment, appDTO);
            Marriage mar = appointment.getMarId();
            MarriageDTO marDTO = new MarriageDTO();
            BeanUtils.copyProperties(mar, marDTO);
            appDTO.setMarId(marDTO);
        }
        return appDTO;
    }

}
