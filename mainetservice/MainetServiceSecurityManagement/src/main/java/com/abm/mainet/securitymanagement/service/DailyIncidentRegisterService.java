package com.abm.mainet.securitymanagement.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.securitymanagement.domain.DailyIncidentRegister;
import com.abm.mainet.securitymanagement.dto.DailyIncidentRegisterDTO;
import com.abm.mainet.securitymanagement.repository.DailyIncidentRegisterRepository;

@Service
public class DailyIncidentRegisterService implements IDailyIncidentRegisterService {

	@Autowired
	private DailyIncidentRegisterRepository dailyIncidentRegisterRepository;
	
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;

	// save ---------
	
	
	@Override
	public DailyIncidentRegisterDTO save(DailyIncidentRegisterDTO dailyIncidentRegisterDTO) {
		
		//String[] empName=dailyIncidentRegisterDTO.getNameVisitingId().split(",");
		/*
		 * for(String id:empName) { DailyIncidentRegister dailyIncidentRegister=new
		 * DailyIncidentRegister(); BeanUtils.copyProperties(dailyIncidentRegisterDTO,
		 * dailyIncidentRegister);
		 * dailyIncidentRegister.setOrgid(dailyIncidentRegisterDTO.getOrgId());
		 * dailyIncidentRegister.setNameVisitingId(Long.valueOf(id));
		 * dailyIncidentRegisterRepository.save(dailyIncidentRegister); }
		 */
		DailyIncidentRegister dailyIncidentRegister=new DailyIncidentRegister();
		BeanUtils.copyProperties(dailyIncidentRegisterDTO, dailyIncidentRegister);
		dailyIncidentRegister.setOrgid(dailyIncidentRegisterDTO.getOrgId());
		String nameVisitingId = "";
		for(int i =0; i<dailyIncidentRegisterDTO.getVisitingOfficerIds().size();i++) {
			//Long to String like '1-2-3'
			nameVisitingId+=dailyIncidentRegisterDTO.getVisitingOfficerIds().get(i) +",";
		}
		nameVisitingId = nameVisitingId.substring(0, nameVisitingId.length() - 1);
		
		dailyIncidentRegister.setNameVisitingId(nameVisitingId);
		dailyIncidentRegisterRepository.save(dailyIncidentRegister);
		BeanUtils.copyProperties(dailyIncidentRegister, dailyIncidentRegisterDTO);
		return dailyIncidentRegisterDTO;
		
	}
	
	@Override
	public DailyIncidentRegisterDTO getIncidentById(Long incidentId) {
		DailyIncidentRegister incidentRegister = dailyIncidentRegisterRepository.findOne(incidentId);
		DailyIncidentRegisterDTO incidentRegisterDTO = new DailyIncidentRegisterDTO();
		BeanUtils.copyProperties(incidentRegister, incidentRegisterDTO);
		//incidentRegisterDTO.setNameVisitingId(incidentRegister.getNameVisitingId().toString());
		String nameVisitingId = incidentRegister.getNameVisitingId();
		String[] splitIds = nameVisitingId.split(",");
		List<Long> visitingOfficerIds = new ArrayList<>();
		for (int i = 0; i < splitIds.length; i++) {
			visitingOfficerIds.add(Long.valueOf(splitIds[i]));
        }
		incidentRegisterDTO.setVisitingOfficerIds(visitingOfficerIds);
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 String strDate= formatter.format(incidentRegisterDTO.getDate());  
		 incidentRegisterDTO.setDate(Utility.stringToDate(strDate));
		 incidentRegisterDTO.setTime(new SimpleDateFormat("HH:mm a").format(stringToTimeConvet(incidentRegisterDTO.getTime())));
		return incidentRegisterDTO;
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
	
	@Override
	public List<DailyIncidentRegisterDTO> getAllRecords(Long orgId) {
		List<DailyIncidentRegister> listDailyIncidentRegisters = dailyIncidentRegisterRepository.findByOrgidOrderByIncidentIdDesc(orgId);
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		List<EmployeeBean>  empList =employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid() , null);
		List<DailyIncidentRegisterDTO> dtoList = new ArrayList<DailyIncidentRegisterDTO>();
		
		listDailyIncidentRegisters.forEach(entity -> {
			DailyIncidentRegisterDTO dto = new DailyIncidentRegisterDTO();
			String nameVisitingId = entity.getNameVisitingId();
			String[] splitIds = nameVisitingId.split(",");
			List<Long> visitingOfficerIds = new ArrayList<>();
			List<String> officerList = new ArrayList<>();
			for (int i = 0; i < splitIds.length; i++) {
				visitingOfficerIds.add(Long.valueOf(splitIds[i]));
				String lookUpdec = employeeService.findById(Long.valueOf(splitIds[i])).getFullName();
				officerList.add(lookUpdec);
            }
			BeanUtils.copyProperties(entity, dto);
			dto.setVisitingOfficerIds(visitingOfficerIds);
			// join officer name
            String joinedString = "NA";
            if (officerList.size() > 0) {
                joinedString = String.join(",", officerList);
            }
            dto.setNameVisitingOffJoin(joinedString);
			/*
			 * empList.stream().filter(emp->emp.getEmpId().equals(entity.getNameVisitingId()
			 * )).forEach(emp->{ dto.setNameVisitingOff(emp.getFullName()); });
			 */
			dtoList.add(dto);

		});

		return dtoList;

	}

	@Override
	public List<DailyIncidentRegisterDTO> searchIncidentRegister(Date fromDate, Date toDate, Long orgId) {
		List<DailyIncidentRegisterDTO> dtoList = new ArrayList<DailyIncidentRegisterDTO>();
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		List<EmployeeBean>  empList =employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid() , null);
		List<DailyIncidentRegister> listDailyIncidentRegisterDTOs = dailyIncidentRegisterRepository.searchIncidentRegister(fromDate, toDate, orgId);		
			
		listDailyIncidentRegisterDTOs.forEach(entity -> {
			DailyIncidentRegisterDTO dto = new DailyIncidentRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			
			String nameVisitingId = entity.getNameVisitingId();
			String[] splitIds = nameVisitingId.split(",");
			List<Long> visitingOfficerIds = new ArrayList<>();
			List<String> officerList = new ArrayList<>();
			for (int i = 0; i < splitIds.length; i++) {
				visitingOfficerIds.add(Long.valueOf(splitIds[i]));
				String lookUpdec = employeeService.findById(Long.valueOf(splitIds[i])).getFullName();
				officerList.add(lookUpdec);
            }
			dto.setVisitingOfficerIds(visitingOfficerIds);
			// join officer name
            String joinedString = "NA";
            if (officerList.size() > 0) {
                joinedString = String.join(",", officerList);
            }
            dto.setNameVisitingOffJoin(joinedString);
			dtoList.add(dto);

		});
		

        return dtoList;

	}

	

	/*
	 * 
	 * @Override
	 * 
	 * @Transactional(readOnly = true)
	 * 
	 * @GET
	 * 
	 * @Path(value = "/get/{Id}")
	 * 
	 * @ApiOperation(value = "Get Complain by Id", notes =
	 * "Get Complain by Complain Id", response = FireCallRegisterDTO.class)
	 */
/*public DailyIncidentRegisterDTO findOne(@PathParam("Id") Long id) {
	DailyIncidentRegister master = dailyIncidentRegisterRepository.findOne(id);
	DailyIncidentRegisterDTO dto = new DailyIncidentRegisterDTO();
	BeanUtils.copyProperties(master, dto);
	dto.setVisitingOfficer(mapToList(dto.getNameVisitingOff()));
	//dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
	//dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
	return dto;
}

private List<String> mapToList(String nameVisitingOff) {
	// TODO Auto-generated method stub
	return null;*/
	
	

}