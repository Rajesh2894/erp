package com.abm.mainet.swm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.swm.dao.IEmployeeScheduleDAO;
import com.abm.mainet.swm.domain.EmployeeSchedule;
import com.abm.mainet.swm.domain.EmployeeScheduleDetailHistory;
import com.abm.mainet.swm.domain.EmployeeScheduleHistory;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.dto.EmployeeScheduleDetailDTO;
import com.abm.mainet.swm.mapper.EmployeeScheduleMapper;
import com.abm.mainet.swm.repository.EmployeeScheduleRepository;

/**
 * The Class EmployeeScheduleServiceImpl.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Service
@Produces(value = MediaType.APPLICATION_JSON_VALUE)
@WebService(endpointInterface = "com.abm.mainet.swm.service.EmployeeScheduleService")
@Path(value = "/employeeScheduleService")
public class EmployeeScheduleService implements IEmployeeScheduleService {

    /** The employeeSchedule repository. */
    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private IEmployeeScheduleDAO employeeScheduleDAO;

    /** The employeeSchedule mapper. */
    @Autowired
    private EmployeeScheduleMapper employeeScheduleMapper;

    /** The audit service. */
    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.EmployeeScheduleService#getEmployeeScheduleByEmployeeScheduleId(java.lang.Long)
     */
    @Override
    @GET
    @Path(value = "/get/{id}")
    @Transactional(readOnly = true)
    public EmployeeScheduleDTO getByEmployeeScheduleId(@PathParam("id") Long employeeScheduleId) {
        return employeeScheduleMapper
                .mapEmployeeScheduleToEmployeeScheduleDTO(employeeScheduleRepository.findOne(employeeScheduleId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.EmployeeScheduleService#saveEmployeeSchedule(com.abm.mainet.swm.dto.EmployeeScheduleDTO)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public EmployeeScheduleDTO save(EmployeeScheduleDTO employeeScheduleDetails) {
        EmployeeSchedule master = employeeScheduleMapper
                .mapEmployeeScheduleDTOToEmployeeSchedule(employeeScheduleDetails);
        master = employeeScheduleRepository.save(master);
        EmployeeScheduleHistory masterHistory = new EmployeeScheduleHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
        auditService.createHistory(master, masterHistory);

        master.getTbSwEmployeeScheddets().forEach(schDet -> {
            EmployeeScheduleDetailHistory detHistory = new EmployeeScheduleDetailHistory();
            detHistory.setEmsId(schDet.getTbSwEmployeeScheduling().getEmsId());
            if (schDet.getEmsdId() == null) {
                detHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
            } else {
                detHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
            }

            auditService.createHistory(schDet, detHistory);
        });

        return employeeScheduleMapper.mapEmployeeScheduleToEmployeeScheduleDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.EmployeeScheduleService#updateEmployeeSchedule(com.abm.mainet.swm.dto.EmployeeScheduleDTO)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public EmployeeScheduleDTO update(EmployeeScheduleDTO employeeScheduleIdDetails) {
        EmployeeSchedule master = employeeScheduleMapper
                .mapEmployeeScheduleDTOToEmployeeSchedule(employeeScheduleIdDetails);
        master = employeeScheduleRepository.save(master);
        EmployeeScheduleHistory masterHistory = new EmployeeScheduleHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
        auditService.createHistory(master, masterHistory);

        master.getTbSwEmployeeScheddets().forEach(schDet -> {
            EmployeeScheduleDetailHistory detHistory = new EmployeeScheduleDetailHistory();
            detHistory.setEmsId(schDet.getTbSwEmployeeScheduling().getEmsId());
            if (schDet.getEmsdId() == null) {
                detHistory.setHStatus(MainetConstants.Transaction.Mode.ADD);
            } else {
                detHistory.setHStatus(MainetConstants.Transaction.Mode.UPDATE);
            }
            auditService.createHistory(schDet, detHistory);
        });

        return employeeScheduleMapper.mapEmployeeScheduleToEmployeeScheduleDTO(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.EmployeeScheduleService#deleteEmployeeSchedule(java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public void delete(Long employeeScheduleId, Long empId, String ipMacAdd) {
        EmployeeSchedule master = employeeScheduleRepository.findOne(employeeScheduleId);
        // TODO: set delete flag master.setDeActive(MainetConstants.IsDeleted.DELETE);
        // master.setIsDeleted(MainetConstants.FlagD);
        master.setUpdatedBy(empId);
        master.setUpdatedDate(new Date());
        master.setLgIpMacUpd(ipMacAdd);
        EmployeeScheduleHistory masterHistory = new EmployeeScheduleHistory();
        masterHistory.setHStatus(MainetConstants.Transaction.Mode.DELETE);
        auditService.createHistory(master, masterHistory);
        employeeScheduleRepository.save(master);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.EmployeeScheduleService#searchEmployeeScheduleByEmployeeTypeAndEmployeeNo(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeScheduleDTO> search(Long employeeId, Date fromDate, Date toDate, Long orgId) {
        return employeeScheduleMapper.mapEmployeeScheduleListToEmployeeScheduleDTOList(
                employeeScheduleDAO.searchEmployeeScheduleByEmployeeName(employeeId, fromDate,
                        toDate, orgId));
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IEmployeeScheduleService#getEmployeeSheduleDetailsForReports(java.lang.Long,
     * java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeScheduleDTO getEmployeeSheduleDetailsForReports(Long OrgId, Long empId, String scheduleType, Date fromDate,
            Date toDate) {
        List<Object[]> employeeScheduleDetails = employeeScheduleRepository.employeeScheduleDetails(OrgId, empId,
                scheduleType, fromDate, toDate);
        EmployeeScheduleDTO employeeScheduleDTO = null;
        EmployeeScheduleDTO empDto = null;
        List<EmployeeScheduleDTO> employeeScheduleList = new ArrayList<>();
        if (employeeScheduleDetails != null && !employeeScheduleDetails.isEmpty()) {
            employeeScheduleDTO = new EmployeeScheduleDTO();
            for (Object[] empDetails : employeeScheduleDetails) {
                empDto = new EmployeeScheduleDTO();
                if (empDetails[1] != null) {
                    empDto.setEmpName(empDetails[1].toString());
                }
                if (empDetails[3] != null) {
                    empDto.setEmsType(empDetails[3].toString());
                }
                if (empDetails[4] != null) {
                    empDto.setEmsReocc(empDetails[4].toString());
                }
                if (empDetails[10] != null) {
                    empDto.setVehicleNo(empDetails[10].toString());
                }
                if (empDetails[21] != null) {
                    empDto.setFromDate(new SimpleDateFormat("HH:mm a").format((Date) empDetails[21]));
                }
                if (empDetails[22] != null) {
                    empDto.setToDate(new SimpleDateFormat("HH:mm a").format((Date) empDetails[22]));
                }
                if (empDetails[23] != null) {
                    empDto.setEmpScheduledate(new SimpleDateFormat("dd/MM/yyyy").format((Date) empDetails[23]));
                }
                if (empDetails[24] != null) {
                    empDto.setShiftId(Long.valueOf(empDetails[24].toString()));
                }
                if (empDetails[25] != null) {
                    empDto.setWasteType(empDetails[25].toString());
                }
                employeeScheduleList.add(empDto);
            }
            employeeScheduleDTO.setEmployeeScheduleList(employeeScheduleList);
        }
        return employeeScheduleDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IEmployeeScheduleService#validateEmpScheduling(com.abm.mainet.swm.dto.EmployeeScheduleDTO)
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> validateEmpScheduling(EmployeeScheduleDTO employeeScheduleDto) {
        List<String> errlist = new ArrayList<>();
        int countError = 1;
        StringBuilder builder = null;
        for (EmployeeScheduleDetailDTO empDet : employeeScheduleDto.getTbSwEmployeeScheddets()) {
            builder = new StringBuilder();
            Long count = employeeScheduleRepository.findEmployeSchedule(empDet.getEmpid(),
                    employeeScheduleDto.getOrgid(), employeeScheduleDto.getSheduleDate(),
                    empDet.getEmsdStarttime(), empDet.getEmsdEndtime());
            if (count == 0) {

            } else {
                builder.append(countError);
                if (!builder.toString().isEmpty()) {
                    errlist.add(builder.toString());
                }
                break;
            }
            countError++;
        }
        return errlist;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IEmployeeScheduleService#allSheduledEmployee(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeScheduleDTO> allSheduledEmployee(Long employeeId, Long orgId) {
        List<EmployeeScheduleDTO> empdet = employeeScheduleRepository.getAllScheduledEmp(employeeId, orgId);
        return empdet;
    }
}
