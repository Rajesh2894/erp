package com.abm.mainet.securitymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dao.IContractualStaffMasterDao;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.repository.ContractualStaffMasterRepository;
/**
 * 
 * @author arunshinde
 *
 */
@Service
public class ContractualStaffMasterService implements IContractualStaffMasterService {

	@Autowired
	private ContractualStaffMasterRepository repository;

	@Autowired 
	private IContractualStaffMasterDao iContractualStaffMasterDao;
	
	@Resource
	private TbAcVendormasterService tbVendormasterService;

	@Override
	@Transactional
	public ContractualStaffMasterDTO saveOrUpdate(ContractualStaffMasterDTO dto) {
		ContractualStaffMaster master = new ContractualStaffMaster();
		BeanUtils.copyProperties(dto, master);
		repository.save(master);
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public ContractualStaffMasterDTO findById(Long staffId) {
		ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();
		ContractualStaffMaster master = repository.findOne(staffId);
		BeanUtils.copyProperties(master, dto);
		return dto;
	}


	@Override
	@Transactional(readOnly = true)
	public List<ContractualStaffMasterDTO> findStaffDetails(String contStaffName, Long vendorId, Long locId,
			Long cpdShiftId,Long dayPrefixId, Long orgId) {
		
		List<ContractualStaffMaster> list =iContractualStaffMasterDao.findStaffDetails(contStaffName, vendorId, locId,cpdShiftId,dayPrefixId, orgId);
		List<ContractualStaffMasterDTO> contractualStaffMasterDTO = new ArrayList<ContractualStaffMasterDTO>();
		
		list.forEach(entity -> {
		ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO(); 
		BeanUtils.copyProperties(entity, dto); 
		contractualStaffMasterDTO.add(dto); 
		});
		return contractualStaffMasterDTO;
	}

	  @Override 
	  @Transactional(readOnly = true)
	  public List<Designation> findDesignNameById(Long orgId) {
	  List<Designation> list =repository.findDesignNameById();
	  return list; 
	  }

	  public List<ContractualStaffMasterDTO> getList( List<ContractualStaffMasterDTO> list, List<Designation> desglist,List<TbLocationMas> localist,List<TbAcVendormaster> loadVendor){
		  
		  for(ContractualStaffMasterDTO dto:list){
			  for(TbAcVendormaster vendorDTO:loadVendor) {
				  if(vendorDTO.getVmVendorid().equals(dto.getVendorId())) {
					  dto.setVendorDesc(vendorDTO.getVmVendorcode()+"-"+vendorDTO.getVmVendorname()); 
				  }
			  }
		  }
		 
		  for(ContractualStaffMasterDTO dto:list){
			  for(TbLocationMas LocationDTO:localist) {
				  if(LocationDTO.getLocId().equals(dto.getLocId())) {
					  dto.setLocDesc(LocationDTO.getLocNameEng()+"-"+LocationDTO.getLocArea()); 
				  }
			  }
		  }
		  for(ContractualStaffMasterDTO dto:list){
			  for(Designation desgDTO:desglist) {
				  if(desgDTO.getDsgid().equals(dto.getDsgId())) {
					  dto.setDesiDesc(desgDTO.getDsgname()); 
				  }
			  }
		  }
		  
		  return list;
	  }

	@Override
	@Transactional(readOnly = true)
	public List<ContractualStaffMasterDTO> findAll(Long orgId) {
	List<ContractualStaffMasterDTO> contractualStaffMasters = StreamSupport
					.stream(repository.findByOrgid(orgId).spliterator(), false).map(entity -> {
						ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();
						BeanUtils.copyProperties(entity, dto);
						return dto;
					}).collect(Collectors.toList());
			return contractualStaffMasters;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean checkDuplicateEmployeeId(Long vendorId, String contStaffIdNo, Long orgId) {
		Long count=repository.checkDuplicateEmployeeId(vendorId,contStaffIdNo,orgId);
		if (count!=0)
		{
			return true;
		}
		else {
		   return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean checkDuplicateMobileNo(Long contStsffId, String contStaffMob, Long orgId) {
		
		List<ContractualStaffMaster> entities = repository.checkDuplicateMobileNo( contStaffMob, orgId);
		
		
		if(entities != null  && contStsffId != null) {
			if(entities.size() > 1)
				return true;
			if(entities.get(0).getContStsffId() != contStsffId) {
				return true;
			}
		}
		else if(!entities.isEmpty()) {
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public List<TbAcVendormaster> findAgencyBasedOnStaffMaster(final Long orgid) {
		List<ContractualStaffMasterDTO>  staffList = findAll(orgid);
		List<TbAcVendormaster> agencyList = tbVendormasterService.findAll(orgid);
		List<TbAcVendormaster> filteredAgencyList = null;
		try {
			Set<Long> uniqueAgencyIds = staffList.stream()
				    .map(ContractualStaffMasterDTO::getVendorId)
				    .collect(Collectors.toSet());

			// Filter agencyList to get only the agencies whose ID exists in staffList
			filteredAgencyList = agencyList.stream()
			    .filter(agency -> uniqueAgencyIds.contains(agency.getVmVendorid()))
			    .collect(Collectors.toList());
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return filteredAgencyList;
	}
		
}
