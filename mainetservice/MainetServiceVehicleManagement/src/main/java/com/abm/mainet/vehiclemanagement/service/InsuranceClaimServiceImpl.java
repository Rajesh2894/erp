package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.vehiclemanagement.dao.IInsuranceClaimdao;
import com.abm.mainet.vehiclemanagement.domain.InsuranceClaim;
import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;
import com.abm.mainet.vehiclemanagement.dto.InsuranceClaimDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.repository.InsuranceClaimRepository;

@Service
public class InsuranceClaimServiceImpl implements IInsuranceClaimService {

	@Autowired
	private InsuranceClaimRepository insuranceClaimRepository;

	@Autowired
	private TbAcVendormasterService vendorMasterService;

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private IInsuranceClaimdao insuranceClaimDao;
	
	@Autowired
	private DepartmentService departmentService;


	@Override
	public InsuranceClaimDTO saveClaim(@RequestBody InsuranceClaimDTO insuranceDetailsDTO) {
		InsuranceClaim entity = new InsuranceClaim();
		BeanUtils.copyProperties(insuranceDetailsDTO, entity);
		insuranceClaimRepository.save(entity);
		BeanUtils.copyProperties(entity, insuranceDetailsDTO);
		return insuranceDetailsDTO;
	}

	@Override
	@Transactional
	public List<InsuranceClaimDTO> insuranceClaim(Date issueDate, Date endDate, Long insurDetId, Long veid,
			Long orgid) {
		List<InsuranceClaimDTO> iInsuranceDetailsDTOs = new ArrayList<InsuranceClaimDTO>();
		List<InsuranceClaim> list = insuranceClaimDao.insuranceClaim(issueDate, endDate, veid, orgid);
		list.forEach(entity -> {
			if (insurDetId != null && insurDetId != entity.getInsuranceClaimId()) {
				InsuranceClaimDTO inDetailsDTO = new InsuranceClaimDTO();
				BeanUtils.copyProperties(entity, inDetailsDTO);
				inDetailsDTO.setInsuredName(vendorMasterService.getVendorNameById(entity.getInsuredBy(), orgid));
				inDetailsDTO.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
				inDetailsDTO.setIssueDate(entity.getIssueDate());
				inDetailsDTO.setEndDate(entity.getEndDate());
				iInsuranceDetailsDTOs.add(inDetailsDTO);
			} else if (insurDetId == null) {
				InsuranceClaimDTO inDetailsDTO = new InsuranceClaimDTO();
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
	public List<InsuranceClaimDTO> searchInsuranceClaim(Long department, Long vehicleType, Long veid, Long orgid) {
		List<InsuranceClaimDTO> iInsuranceDetailsDTOs=new ArrayList<InsuranceClaimDTO>();
		List<InsuranceClaim> list=insuranceClaimDao.searchInsuranceClaim(department, vehicleType, veid, orgid);
		list.forEach(entity -> {
			InsuranceClaimDTO inDetailsDTO=new InsuranceClaimDTO();
			BeanUtils.copyProperties(entity, inDetailsDTO);
			inDetailsDTO.setInsuredName(vendorMasterService.getVendorNameById(entity.getInsuredBy(), orgid));
			inDetailsDTO.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			inDetailsDTO.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
			iInsuranceDetailsDTOs.add(inDetailsDTO);
		});
		
		return iInsuranceDetailsDTOs;
	}
	
	@Override
	public List<InsuranceClaimDTO> getAllVehicles(Long orgid) {
		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<InsuranceClaimDTO> listDto = new ArrayList<InsuranceClaimDTO>();
		for (Object[] obj : list) {
			InsuranceClaimDTO dto = new InsuranceClaimDTO();
			// dto.setDeptDesc(obj[0].toString());
			dto.setVeNo(obj[0].toString());
			dto.setVeDesc(obj[1].toString());
			listDto.add(dto);
		}

		return listDto;
	}
	
	@Override
	public InsuranceClaimDTO getDetailById(Long insuranceDetId) {
		InsuranceClaim insuranceDetail = insuranceClaimRepository.findOne(insuranceDetId);
		InsuranceClaimDTO insuranceDetailsDTO = new InsuranceClaimDTO();
		BeanUtils.copyProperties(insuranceDetail, insuranceDetailsDTO);
		return insuranceDetailsDTO;
	}

}
