package com.abm.mainet.vehiclemanagement.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.vehiclemanagement.dao.ISLRMEmployeeDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMaster;
import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMasterHistory;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.mapper.SLRMEmployeeMap;
import com.abm.mainet.vehiclemanagement.repository.SLRMEmployeeMastRepository;

@Service
public class SLRMEmployeeMastService implements ISLRMEmployeeMasterService {

    private static Logger log = Logger.getLogger(SLRMEmployeeMastService.class);

    @Autowired
    SLRMEmployeeMastRepository VehicleEmployeeMasterRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    ISLRMEmployeeDAO sLRMEmployeeDAO;

    @Autowired
    private SLRMEmployeeMap sLRMEmployeeMapper;
    
    @Resource
    private EmployeeJpaRepository employeeMasterRepository;
    
    @Resource
    private DepartmentJpaRepository departmentJpaRepository;
 
    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleEmployeeMasterService#saveEmployeeDetails(com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO)
     */
    @Override
    @Transactional
    public void saveEmployeeDetails(SLRMEmployeeMasterDTO SLRMEmployeeMasterDTO) {
        VehicleEmployeeMaster VehicleEmployeeMaster = new VehicleEmployeeMaster();
        BeanUtils.copyProperties(SLRMEmployeeMasterDTO, VehicleEmployeeMaster);
        VehicleEmployeeMasterRepository.save(VehicleEmployeeMaster);

        VehicleEmployeeMasterHistory VehicleEmployeeMasterHistory = new VehicleEmployeeMasterHistory();
        VehicleEmployeeMasterHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());

        try {
            auditService.createHistory(VehicleEmployeeMaster, VehicleEmployeeMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + VehicleEmployeeMaster, e);
        }

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleEmployeeMasterService#updateEmployeeDetails(com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO)
     */
    @Override
    @Transactional
    public void updateEmployeeDetails(SLRMEmployeeMasterDTO SLRMEmployeeMasterDTO) {
        VehicleEmployeeMaster VehicleEmployeeMaster = new VehicleEmployeeMaster();
        BeanUtils.copyProperties(SLRMEmployeeMasterDTO, VehicleEmployeeMaster);
        VehicleEmployeeMasterRepository.save(VehicleEmployeeMaster);
        VehicleEmployeeMasterHistory VehicleEmployeeMasterHistory = new VehicleEmployeeMasterHistory();
        VehicleEmployeeMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());

        try {
            auditService.createHistory(VehicleEmployeeMaster, VehicleEmployeeMasterHistory);
        } catch (Exception e) {
            log.error("Could not make audit entry for " + VehicleEmployeeMaster, e);
        }

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleEmployeeMasterService#searchEmployeeDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId) {
        SLRMEmployeeMasterDTO SLRMEmployeeMasterDTO = new SLRMEmployeeMasterDTO();
        VehicleEmployeeMaster VehicleEmployeeMaster = VehicleEmployeeMasterRepository.getEmployeeDetails(orgId, empId);
        if (VehicleEmployeeMaster != null)
            BeanUtils.copyProperties(VehicleEmployeeMaster, SLRMEmployeeMasterDTO);
        return SLRMEmployeeMasterDTO;
    }

    
    @Override
    @Transactional(readOnly = true)
    public SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId, long languageId) {
        SLRMEmployeeMasterDTO SLRMEmployeeMasterDTO = new SLRMEmployeeMasterDTO();
        VehicleEmployeeMaster VehicleEmployeeMaster = VehicleEmployeeMasterRepository.getEmployeeDetails(orgId, empId);
        if (VehicleEmployeeMaster != null) {
            BeanUtils.copyProperties(VehicleEmployeeMaster, SLRMEmployeeMasterDTO);
			Object[] employeeObject = (Object[]) departmentJpaRepository.getDeptAndDesgById(SLRMEmployeeMasterDTO.getMrfId(), SLRMEmployeeMasterDTO.getDesgId())[0];			
			if(1L == languageId) {
				SLRMEmployeeMasterDTO.setDeptName(employeeObject[0].toString());
				SLRMEmployeeMasterDTO.setDesigName(employeeObject[1].toString());
			} else {
				SLRMEmployeeMasterDTO.setDeptName(employeeObject[2].toString());
				SLRMEmployeeMasterDTO.setDesigName(employeeObject[3].toString());
			}
        }
        return SLRMEmployeeMasterDTO;
    }
    
    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleEmployeeMasterService#searchEmployeeList(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SLRMEmployeeMasterDTO> searchEmployeeList(Long empId, String empUId, Long mrfId, Long orgId) {
        return sLRMEmployeeMapper.mapVehicleEmployeeMasterListToSLRMEmployeeMasterDTOList(
                sLRMEmployeeDAO.searchEmployeeList(empId, empUId, mrfId, orgId));

    }

    /* (non-Javadoc)
     * @see com.abm.mainet.swm.service.IVehicleEmployeeMasterService#checkDuplicateMobileNo(java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Long checkDuplicateMobileNo(Long orgId, String empMobNo) {
        return (long) VehicleEmployeeMasterRepository.checkDuplicateMobileNo(orgId, empMobNo);
    }

    
    
	 @Override
	 public boolean checkEmpCodeByEmpCode(String empUId, Long orgid) {
     boolean result;
     Long count;
    	  count=VehicleEmployeeMasterRepository.checkDuplicateEmpCodeByEmpCode(empUId, orgid);
    	  if (count.toString().equals("1")) {
    			result = false;
    		} else {
    			result = true;
    		}

    		return result;
      }

	@Override
	public boolean checkDuplicateMob(Long orgId, String empMobNo, Long empId) {
		boolean flag = true;
		List<VehicleEmployeeMaster> emplList = VehicleEmployeeMasterRepository.checkDuplicate(orgId, empMobNo);
		List<SLRMEmployeeMasterDTO> list = sLRMEmployeeMapper
				.mapVehicleEmployeeMasterListToSLRMEmployeeMasterDTOList(emplList);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmpId() != empId) {
				flag = false;  //duplicate found
				break;
			}
			else {
				flag = true;
			}
		}
		return flag;
	}
	
		 
	@Override
	public SLRMEmployeeMasterDTO getEmpDetails(Long empUId, long languageId) {
		Object[] employeeObject = (Object[]) employeeMasterRepository.getEmployeeDetailsByEmpId(empUId)[0];
		SLRMEmployeeMasterDTO driverDetailsDto = new SLRMEmployeeMasterDTO();
		if(null != employeeObject[0])
			driverDetailsDto.setEmpUId(employeeObject[0].toString());
		if(null != employeeObject[1])
			driverDetailsDto.setEmpName(employeeObject[1].toString());
		if(null != employeeObject[2])
			driverDetailsDto.setEmpMName(employeeObject[2].toString());
		if(null != employeeObject[3])
			driverDetailsDto.setEmpLName(employeeObject[3].toString());
		if(null != employeeObject[4])
			driverDetailsDto.setMrfId(Long.valueOf(employeeObject[4].toString()));
		if(null != employeeObject[6])
			driverDetailsDto.setDesgId(Long.valueOf(employeeObject[6].toString()));
		
		if(1L == languageId) {
			if(null != employeeObject[5])
				driverDetailsDto.setDeptName(employeeObject[5].toString());
			if(null != employeeObject[7])
				driverDetailsDto.setDesigName(employeeObject[7].toString());
		} else {
			if(null != employeeObject[15])
				driverDetailsDto.setDeptName(employeeObject[15].toString());
			if(null != employeeObject[16])
				driverDetailsDto.setDesigName(employeeObject[16].toString());
		}

		if(null != employeeObject[8])
			driverDetailsDto.setTtlId(Long.valueOf(employeeObject[8].toString()));
		if(null != employeeObject[9])
			driverDetailsDto.setGender(employeeObject[9].toString());
		if(null != employeeObject[10])
			driverDetailsDto.setEmpMobNo(employeeObject[10].toString());
		if(null != employeeObject[11])
			driverDetailsDto.setEmpEmailId(employeeObject[11].toString());
		if(null != employeeObject[12])
			driverDetailsDto.setEmpAddress(employeeObject[12].toString());
		if(null != employeeObject[13])
			driverDetailsDto.setEmpAddress1(employeeObject[13].toString());
		if(null != employeeObject[14])
			driverDetailsDto.setEmpPincode(employeeObject[14].toString());
		return driverDetailsDto;
	}

	@Override
	public String getDriverFullNameById(Long empId) {
		final Object[] empObject = VehicleEmployeeMasterRepository.getEmpFullNameByEmpId(empId);
		if (empObject.length == 0)
			return MainetConstants.BLANK;
		else {
			Object[] employee = (Object[]) empObject[0];
			String fName = employee[0].toString();
			String lName = employee[1].toString();
			if (employee[0].toString() == null)
				fName = MainetConstants.BLANK;
			if (employee[1].toString() == null)
				fName = MainetConstants.BLANK;
			return fName + MainetConstants.WHITE_SPACE + lName;
		}
	}
	
	
	@Override
	public List<Object[]> getEmployeesForVehicleDriverMas(Long orgId, String desgDriver) {
		return VehicleEmployeeMasterRepository.getEmployeesForVehicleDriverMas(orgId, desgDriver);
	}

}
