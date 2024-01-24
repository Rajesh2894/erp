package com.abm.mainet.securitymanagement.service;

import java.util.List;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;

public interface IContractualStaffMasterService {

	public ContractualStaffMasterDTO saveOrUpdate(ContractualStaffMasterDTO dto);

	public ContractualStaffMasterDTO findById(Long staffId);

	public List<ContractualStaffMasterDTO> findAll(Long orgId);

	public List<ContractualStaffMasterDTO> findStaffDetails(String contStaffName, Long vendorId, Long locId, Long cpdShiftId,Long dayPrefixId,Long orgId);

	public List<Designation> findDesignNameById( Long orgId);
	
	public List<ContractualStaffMasterDTO>  getList( List<ContractualStaffMasterDTO> list, List<Designation> desgList,List<TbLocationMas> locList, List<TbAcVendormaster> vendorList);

	public Boolean checkDuplicateEmployeeId(Long vendorId, String contStaffIdNo,Long orgId);

	public Boolean checkDuplicateMobileNo(Long contStaffIdNo, String contStaffMob, Long orgId);

	public List<TbAcVendormaster> findAgencyBasedOnStaffMaster(Long orgid);
	
	
}
