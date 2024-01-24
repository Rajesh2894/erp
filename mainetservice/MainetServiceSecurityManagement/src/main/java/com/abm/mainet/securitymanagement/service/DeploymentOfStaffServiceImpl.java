package com.abm.mainet.securitymanagement.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.securitymanagement.dao.IDeploymentOfStaffDao;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.DeploymentOfStaff;
import com.abm.mainet.securitymanagement.domain.DeploymentOfStaffHist;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingHist;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.dto.DeploymentOfStaffDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.repository.ContractualStaffMasterRepository;
import com.abm.mainet.securitymanagement.repository.DeploymentOfStaffHistoryRepository;
import com.abm.mainet.securitymanagement.repository.DeploymentOfStaffRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingHistoryRepository;
import com.abm.mainet.securitymanagement.repository.EmployeeSchedulingRepository;
import com.abm.mainet.securitymanagement.repository.TransferAndDutySchRepository;

@Service
public class DeploymentOfStaffServiceImpl implements IDeploymentOfStaffService {

	@Autowired
	private DeploymentOfStaffRepository repo;
	@Autowired
	private DeploymentOfStaffHistoryRepository hisRepo;
	@Autowired
	private EmployeeSchedulingRepository emplRepo;
	@Autowired
	private EmployeeSchedulingHistoryRepository emplHisRepo;
	@Autowired
	private IDeploymentOfStaffDao dao;
	@Autowired
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	@Autowired
	private TransferAndDutySchRepository transRepo;
	@Autowired
	private IEmployeeSchedulingService employeeSchedulingService;
	@Autowired
	private ContractualStaffMasterRepository contractRepo;

	@Override
	public List<ContractualStaffMasterDTO> findEmployeeNameList(Long orgId) {

		List<ContractualStaffMaster> staffList = repo.findEmpNameList(orgId);
		List<ContractualStaffMasterDTO> staffDto = new ArrayList<ContractualStaffMasterDTO>();
		staffList.forEach(entity -> {
			ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();
			BeanUtils.copyProperties(entity, dto);
			staffDto.add(dto);
		});
		return staffDto;
	}

	@Override
	public ContractualStaffMasterDTO findEmpByEmpId(String contStaffIdNo, Long vendorId, Long orgId) {
		ContractualStaffMaster staffDetails = emplRepo.findByEmpIdAndVendor(contStaffIdNo, vendorId, orgId);
		ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();
		BeanUtils.copyProperties(staffDetails, dto);
		return dto;
	}

	@Override
	public DeploymentOfStaffDTO saveOrUpdate(DeploymentOfStaffDTO dto) {
		Long count = 0l;
		dto.setCount(count);
		List<EmployeeScheduling> staffDetail = repo.findStaffListByEmpId(dto.getContStaffIdNo(), dto.getVendorId(),
				dto.getOrgid());

		for (int i = 0; i < staffDetail.size(); i++) {
			if ((staffDetail != null && staffDetail.get(i).getLocId().equals(dto.getLocId())
					&& staffDetail.get(i).getCpdShiftId().equals(dto.getCpdShiftId())
					&& (dto.getContStaffSchFrom().compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0 && dto
							.getContStaffSchFrom().compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true)
					|| (dto.getContStaffSchTo().compareTo(staffDetail.get(i).getContStaffSchFrom()) >= 0 && dto
							.getContStaffSchTo().compareTo(staffDetail.get(i).getContStaffSchTo()) <= 0) == true) {
				count++;
				dto.setCount(count);
			}
		}
		ContractualStaffMaster staffData = emplRepo.findByEmpIdAndVendor(dto.getContStaffIdNo(), dto.getVendorId(),
				dto.getOrgid());
		if (staffData != null) {
			dto.setDayDesc(CommonMasterUtility.getCPDDescription(staffData.getDayPrefixId(), MainetConstants.BLANK));
			if ((staffData.getContStaffAppointDate().after(dto.getContStaffSchFrom()))
					|| (staffData.getContStaffAppointDate().after(dto.getContStaffSchTo()))) {
				dto.setMessageDate("true");
			}
		}
		if (count == 0 && dto.getMessageDate() != "true") {
			DeploymentOfStaff deploymentOfStaff = new DeploymentOfStaff();
			BeanUtils.copyProperties(dto, deploymentOfStaff);
			repo.save(deploymentOfStaff);
			DeploymentOfStaffHist deploymentOfStaffHist = new DeploymentOfStaffHist();
			BeanUtils.copyProperties(dto, deploymentOfStaffHist);
			deploymentOfStaffHist.setDeplId(deploymentOfStaff.getDeplId());
			hisRepo.save(deploymentOfStaffHist);
		}
		return dto;
	}

	@Override
	public void saveOrUpdateAfterWfApproval(Long deplId, Long orgId) {

		List<EmployeeSchedulingDet> emplDetList = new ArrayList<EmployeeSchedulingDet>();
		DeploymentOfStaff deploymentOfStaff = new DeploymentOfStaff();
		EmployeeScheduling empsch = new EmployeeScheduling();
		DeploymentOfStaffDTO deplDto = new DeploymentOfStaffDTO();
		DeploymentOfStaff staffEntity = repo.findByDeplId(deplId, orgId);
		BeanUtils.copyProperties(staffEntity, deplDto);

		DeploymentOfStaffDTO depDto = checkStaffWeekoffDay(deplDto);
		BeanUtils.copyProperties(depDto, empsch);
		for (int i = 0; i < depDto.getEmplDetDtoList().size(); i++) {
			EmployeeSchedulingDet emplEnt = new EmployeeSchedulingDet();
			emplEnt.setEmployeeScheduling(empsch);
			BeanUtils.copyProperties(depDto.getEmplDetDtoList().get(i), emplEnt);
			emplDetList.add(emplEnt);
		}
		BeanUtils.copyProperties(depDto, deploymentOfStaff);
		empsch.setEmployeeSchedulingdets(emplDetList);
		deploymentOfStaff.setEmployeeScheduling(empsch);
		empsch.setDeplId(deploymentOfStaff);
		repo.save(deploymentOfStaff);

		DeploymentOfStaffHist deploymentOfStaffHist = new DeploymentOfStaffHist();
		BeanUtils.copyProperties(depDto, deploymentOfStaffHist);
		deploymentOfStaffHist.setDeplId(deploymentOfStaff.getDeplId());
		hisRepo.save(deploymentOfStaffHist);

		Long emplScdlId = emplRepo.getBydeplId(deplDto.getDeplId(), deplDto.getOrgid());
		empsch.setEmplScdlId(emplScdlId);

		depDto.setDeplId(deploymentOfStaff.getDeplId());
		EmployeeSchedulingHist employeeSchedulingHist = new EmployeeSchedulingHist();
		BeanUtils.copyProperties(empsch, employeeSchedulingHist);
		employeeSchedulingHist.setEmplScdlId(empsch.getEmplScdlId());
		emplHisRepo.save(employeeSchedulingHist);

	}

	private DeploymentOfStaffDTO checkStaffWeekoffDay(DeploymentOfStaffDTO deploymentOfStaffDTO) {

		Instant instantFrom = deploymentOfStaffDTO.getContStaffSchFrom().toInstant();
		ZonedDateTime zdtFrom = instantFrom.atZone(ZoneId.systemDefault());
		java.time.LocalDate dateFrom = zdtFrom.toLocalDate();
		Instant instantTo = deploymentOfStaffDTO.getContStaffSchTo().toInstant();
		ZonedDateTime zdtTo = instantTo.atZone(ZoneId.systemDefault());
		java.time.LocalDate dateTo = zdtTo.toLocalDate();
		long diffInDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
		ContractualStaffMaster staffData = emplRepo.findByEmpIdAndVendor(deploymentOfStaffDTO.getContStaffIdNo(),
				deploymentOfStaffDTO.getVendorId(), deploymentOfStaffDTO.getOrgid());
		if (staffData != null) {
			deploymentOfStaffDTO.setDayDesc(
					CommonMasterUtility.getCPDDescription(staffData.getDayPrefixId(), MainetConstants.BLANK));
		}
		List<HolidayMasterDto> dtoList = employeeSchedulingService.findHolidaysByYear(
				deploymentOfStaffDTO.getContStaffSchFrom(), deploymentOfStaffDTO.getContStaffSchTo(),
				deploymentOfStaffDTO.getOrgid());
		List<EmployeeSchedulingDetDTO> detDtoList = new ArrayList<EmployeeSchedulingDetDTO>();
		for (int i = 0; i <= diffInDays; i++) {
			EmployeeSchedulingDetDTO employeeDetDTO = new EmployeeSchedulingDetDTO();
			BeanUtils.copyProperties(deploymentOfStaffDTO, employeeDetDTO);
			LocalDate d = null;
			String[] neWweekDay = null;
			String weekDays = deploymentOfStaffDTO.getDayDesc().toUpperCase();
			if (weekDays != null) {
				neWweekDay = weekDays.split(",");
			}
			d = dateFrom.plus(i, ChronoUnit.DAYS);
			DayOfWeek dayOfWeek = d.getDayOfWeek();
			String DayOfWeekName = dayOfWeek.name();

			if (weekDays != null) {
				for (int j = 0; j < neWweekDay.length; j++) {
					String str = neWweekDay[j];
					Long checkValue = 0l;
					for (int k = 0; k < dtoList.size(); k++) {
						LocalDate localDate = dateToLocaldate(dtoList.get(k).getHoDate());
						if (localDate.equals(d)) {
							checkValue++;
						}
					}
					if (str.compareTo(DayOfWeekName) != 0 && checkValue == 0) {
						Date date1 = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
						employeeDetDTO.setShiftDate(date1);
						ShiftMaster master = employeeSchedulingService
								.findShiftById(deploymentOfStaffDTO.getCpdShiftId(), deploymentOfStaffDTO.getOrgid());
						if(master !=null) {
						Date fromTime = stringToTimeConvet(master.getFromTime());
						Date toTime = stringToTimeConvet(master.getToTime());
						employeeDetDTO.setEmpTypeId(deploymentOfStaffDTO.getEmpTypeId());
						employeeDetDTO.setStartimeShift(fromTime);
						employeeDetDTO.setEndtimeShift(toTime);
						employeeDetDTO.setShiftDay(DayOfWeekName);
						}
						detDtoList.add(employeeDetDTO);
					}
				}
			}
		}
		deploymentOfStaffDTO.setEmplDetDtoList(detDtoList);
		return deploymentOfStaffDTO;
	}

	public Date stringToTimeConvet(String time) {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		Date timeValue = null;
		if (time != null)
			try {
				timeValue = new Date(formatter.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return timeValue;
	}

	public LocalDate dateToLocaldate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}

	@Override
	public List<DeploymentOfStaffDTO> findStaffDetails(Long empTypeId, Long vendorId, Long cpdShiftId, Long locId,
			Long orgId) {
		List<DeploymentOfStaff> staffDetails = dao.buildQuery(empTypeId, vendorId, cpdShiftId, locId, orgId);
		List<DeploymentOfStaffDTO> dto = new ArrayList<DeploymentOfStaffDTO>();
		staffDetails.forEach(entity -> {
			DeploymentOfStaffDTO staffDto = new DeploymentOfStaffDTO();
			BeanUtils.copyProperties(entity, staffDto);

			if (staffDto.getVendorId() != null) {
				ContractualStaffMaster staffNames = emplRepo.findByEmpIdAndVendor(staffDto.getContStaffIdNo(),
						staffDto.getVendorId(), staffDto.getOrgid());
				if(staffNames!=null)
				staffDto.setContStaffName(staffNames.getContStaffName());
			}
			dto.add(staffDto);
		});
		return dto;
	}

	@Override
	public List<DeploymentOfStaffDTO> getList(List<DeploymentOfStaffDTO> list, List<TbLocationMas> localist,
			List<TbAcVendormaster> loadVendor) {
		for (DeploymentOfStaffDTO dto : list) {
			for (TbAcVendormaster vendorDTO : loadVendor) {
				if (vendorDTO.getVmVendorid().equals(dto.getVendorId())) {
					dto.setVendorDesc(vendorDTO.getVmVendorcode() + "-" + vendorDTO.getVmVendorname());
				}
			}
		}
		for (DeploymentOfStaffDTO dto : list) {
			for (TbLocationMas LocationDTO : localist) {
				if (LocationDTO.getLocId().equals(dto.getLocId())) {
					dto.setLocDesc(LocationDTO.getLocNameEng() + "-" + LocationDTO.getLocArea());
				}
			}
		}
		return list;
	}

	@Override
	public DeploymentOfStaffDTO findById(Long id) {
		DeploymentOfStaffDTO dto = new DeploymentOfStaffDTO();
		DeploymentOfStaff entity = repo.findOne(id);
		BeanUtils.copyProperties(entity, dto);

		if (entity.getVendorId() != null) {
			ContractualStaffMaster staffName = emplRepo.findByEmpIdAndVendor(entity.getContStaffIdNo(),
					entity.getVendorId(), entity.getOrgid());
			dto.setFromLocId(staffName.getLocId());
			dto.setFromCpdShiftId(staffName.getCpdShiftId());
		}
		return dto;
	}

	@Override
	public List<DeploymentOfStaffDTO> findAll(Long orgId) {
		List<DeploymentOfStaff> findStaffs = repo.findByOrgid(orgId);
		List<DeploymentOfStaffDTO> listDto = new ArrayList<DeploymentOfStaffDTO>();
		findStaffs.forEach(entity -> {
			DeploymentOfStaffDTO dto = new DeploymentOfStaffDTO();
			BeanUtils.copyProperties(entity, dto);
			if (dto.getVendorId() != null) {
				ContractualStaffMaster staffNames = emplRepo.findByEmpIdAndVendor(dto.getContStaffIdNo(),
						dto.getVendorId(), dto.getOrgid());
				if(staffNames!=null)
				dto.setContStaffName(staffNames.getContStaffName());
			}
			listDto.add(dto);
		});
		return listDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas,
			String url, String workFlowFlag) {
		try {
			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			ApplicationMetadata applicationMetadata = new ApplicationMetadata();
			applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setApplicationId(workflowActionDto.getApplicationId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowMas.getWfId());
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);
			ApplicationSession appSession = ApplicationSession.getInstance();

			/*
			 * Task manager assignment is depends no LDAP integration his check added in
			 * BRm/BPM layer
			 */
			TaskAssignment assignment = new TaskAssignment();

			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setServiceEventId(-1L);
			String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
			assignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

			assignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

			assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
			assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
			assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
			assignment.setServiceId(workFlowMas.getService().getSmServiceId());
			assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
			assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
			assignment.setUrl(url);

			/*
			 * Reviewer TaskAssignment has been removed from here,because it will be fetch
			 * on the fly by BPM to Service callback.
			 */

			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.initiateWorkflow(workflowProcessParameter);

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
		}
		return null;

	}

	@Override
	@Transactional
	public String updateWorkFlowSecurityService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	@Transactional
	public DeploymentOfStaffDTO findByDeplSeq(String complainNo, Long orgid) {
		DeploymentOfStaffDTO dto = new DeploymentOfStaffDTO();
		DeploymentOfStaff entity = repo.findByDeplSeq(complainNo, orgid);
		BeanUtils.copyProperties(entity, dto);

		if (dto.getVendorId() != null) {
			ContractualStaffMaster staffNames = emplRepo.findByEmpIdAndVendor(entity.getContStaffIdNo(),
					entity.getVendorId(), entity.getOrgid());
			dto.setFromLocId(staffNames.getLocId());
			dto.setContStaffIdNo(staffNames.getContStaffIdNo());
			dto.setFromCpdShiftId(staffNames.getCpdShiftId());
		}
		return dto;
	}

	@Override
	@Transactional
	public void updateWfStatus(String complainNo, String status) {
		repo.updateWfStatus(complainNo, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeploymentStaffApproveStatus(DeploymentOfStaffDTO entity, String status, String lastDecision) {
		TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
		DeploymentOfStaff DeployStaffEntity = new DeploymentOfStaff();

		cfcApplEntiry.setApmApplicationId(entity.getDeplId());
		cfcApplEntiry.setRefNo(entity.getDeplSeq());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
		DeployStaffEntity.setUpdatedDate(new Date());

		if (lastDecision.equals("REJECTED")) {
			cfcApplEntiry.setApmAppRejFlag("R");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("C");
			cfcApplEntiry.setApmApplicationDate(new Date());
			DeployStaffEntity.setWfStatus("REJECTED");
		}

//		else if (status.equals("APPROVED") && lastDecision.equals("PENDING")) {
//			cfcApplEntiry.setApmApplSuccessFlag("P");
//			cfcApplEntiry.setRejectionDt(new Date());
//			cfcApplEntiry.setApmApplClosedFlag("O");
//			cfcApplEntiry.setApmApplicationDate(new Date());
//			DeployStaffEntity.setWfStatus("PENDING");
//		}

		else if (lastDecision.equals("APPROVED")) {
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(entity.getCreatedDate());
			cfcApplEntiry.setApmApplicationDate(new Date());
			DeployStaffEntity.setWfStatus("APPROVED");
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional
	public void updateWfStatusDepl(Long deplId, String pending, Long orgId) {
		repo.updateWfStatusDepl(deplId, pending, orgId);
	}

	@Override
	public List<DeploymentOfStaffDTO> getStaffNameByVendorId(Long vendorId,String empType, Long orgId) {
		List<ContractualStaffMaster> staffList = contractRepo.getStaffNameByVendorIdAndType(vendorId,empType, orgId);
		List<DeploymentOfStaffDTO> dtoList = new ArrayList<DeploymentOfStaffDTO>();
		staffList.forEach(data -> {
			DeploymentOfStaffDTO depDto = new DeploymentOfStaffDTO();
			BeanUtils.copyProperties(data, depDto);
			dtoList.add(depDto);
		});
		return dtoList;
	}

}
