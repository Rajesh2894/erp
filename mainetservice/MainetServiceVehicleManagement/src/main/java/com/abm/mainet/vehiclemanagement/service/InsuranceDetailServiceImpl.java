package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.vehiclemanagement.dao.IInsuranceDetaildao;
import com.abm.mainet.vehiclemanagement.dao.IVehicleMasterDAO;
import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.mapper.GeVehicleMasterMapper;
import com.abm.mainet.vehiclemanagement.repository.InsuranceDetailRepository;

@Service
public class InsuranceDetailServiceImpl implements IInsuranceDetailService {
	
	
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private DepartmentService departmentService;

	
	@Autowired
	private InsuranceDetailRepository insuranceDetailRepository;
	
	@Autowired
	private IInsuranceDetaildao insuranceDetailDao;
	
	@Autowired
	private TbAcVendormasterService vendorMasterService;
	
	@Autowired
    private IVehicleMasterDAO vehicleMasterDAO;
	
	@Autowired
    private GeVehicleMasterMapper vehicleMasterMapper;

	@Override
	public InsuranceDetailsDTO save(@RequestBody InsuranceDetailsDTO insuranceDetailsDTO) {
	//	InsuranceDetailsDTO iInsuranceDetailsDTO=new InsuranceDetailsDTO();
		InsuranceDetail entity=new InsuranceDetail();
		BeanUtils.copyProperties(insuranceDetailsDTO, entity);
		insuranceDetailRepository.save(entity);
		return insuranceDetailsDTO;
	}

	@Override
	public List<InsuranceDetailsDTO> getAllRecord(Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InsuranceDetailsDTO> getAllVehicles(Long orgid) {
		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<InsuranceDetailsDTO> listDto = new ArrayList<InsuranceDetailsDTO>();
		for (Object[] obj : list) {
			InsuranceDetailsDTO dto = new InsuranceDetailsDTO();
			// dto.setDeptDesc(obj[0].toString());
			dto.setVeNo(obj[0].toString());
			dto.setVeDesc(obj[1].toString());
			listDto.add(dto);
		}

		return listDto;
	}

	@Override
	public List<InsuranceDetailsDTO> searchInsuranceDetails(Long department, Long vehicleType, Long veid, Long orgid) {
		List<InsuranceDetailsDTO> iInsuranceDetailsDTOs=new ArrayList<InsuranceDetailsDTO>();
		List<InsuranceDetail> list=insuranceDetailDao.searchInsuranceDetails(department, vehicleType, veid, orgid);
		list.forEach(entity -> {
			InsuranceDetailsDTO inDetailsDTO=new InsuranceDetailsDTO();
			BeanUtils.copyProperties(entity, inDetailsDTO);
			inDetailsDTO.setInsuredName(vendorMasterService.getVendorNameById(entity.getInsuredBy(), orgid));
			inDetailsDTO.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			inDetailsDTO.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
			iInsuranceDetailsDTOs.add(inDetailsDTO);
		});
		
		return iInsuranceDetailsDTOs;
	}

	@Override
	public InsuranceDetailsDTO getDetailById(Long insuranceDetId) {
		InsuranceDetail insuranceDetail = insuranceDetailRepository.findOne(insuranceDetId);
		InsuranceDetailsDTO insuranceDetailsDTO = new InsuranceDetailsDTO();
		BeanUtils.copyProperties(insuranceDetail, insuranceDetailsDTO);
		return insuranceDetailsDTO;
	}

	@Override
	@Transactional
	public List<InsuranceDetailsDTO> insuranceDetails(Date issueDate, Date endDate,Long insurDetId, Long veid, Long orgid) {
		List<InsuranceDetailsDTO> iInsuranceDetailsDTOs=new ArrayList<InsuranceDetailsDTO>();
		List<InsuranceDetail> list=insuranceDetailDao.insuranceDetails(issueDate, endDate, veid, orgid);
		
		list.forEach(entity -> {
			Long id=insurDetId;
			if(id!=null && !id.equals(entity.getInsuranceDetId())) {
				InsuranceDetailsDTO inDetailsDTO = new InsuranceDetailsDTO();
				BeanUtils.copyProperties(entity, inDetailsDTO);
				inDetailsDTO.setInsuredName(vendorMasterService.getVendorNameById(entity.getInsuredBy(), orgid));
				inDetailsDTO.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
				inDetailsDTO.setIssueDate(entity.getIssueDate());
				inDetailsDTO.setEndDate(entity.getEndDate());
				iInsuranceDetailsDTOs.add(inDetailsDTO);
			}
			else if(id==null){
				InsuranceDetailsDTO inDetailsDTO = new InsuranceDetailsDTO();
				BeanUtils.copyProperties(entity, inDetailsDTO);
				inDetailsDTO.setInsuredName(vendorMasterService.getVendorNameById(entity.getInsuredBy(), orgid));
				inDetailsDTO.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
				inDetailsDTO.setIssueDate(entity.getIssueDate());
				inDetailsDTO.setEndDate(entity.getEndDate());
				iInsuranceDetailsDTOs.add(inDetailsDTO);
			}
		});
		
		return iInsuranceDetailsDTOs;
	}

	@Override
	@Transactional
	public boolean searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Date issueDate, long orgid) {
		boolean flag=true;
		List<GenVehicleMasterDTO> vehList = vehicleMasterMapper.mapVehicleMasterListToVehicleMasterDTOList(
                vehicleMasterDAO.searchVehicleByVehTypeAndVehRegNo(veId, null, MainetConstants.FlagY, null, null, null, orgid));           
		for (int i = 0; i < vehList.size(); i++) {
			if (vehList.get(i).getVePurDate().after(issueDate)) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	

}
