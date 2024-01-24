package com.abm.mainet.adh.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.adh.dto.ADHContractMappingDto;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;

/**
 * @author cherupelli.srikanth
 * @since 04 November 2019
 */
public interface IAdvertisementContractMappingService {

    List<String[]> getContNoByDeptIdAndOrgId(Long deptId, Long orgId);

    boolean saveADHContractMapping(ADHContractMappingDto contractMappingDto);

    List<ContractMappingDTO> findContractDeptWise(Long orgId, TbDepartment tbDepartment, String flag);

    List<ContractMappingDTO> findContract(final Long orgId, final String contractNo, final Date contactDate,
	    final TbDepartment tbDepartment);

    List<ContractMappingDTO> getAdhContractsByContractId(final Long orgId, final Long contId,
	    final TbDepartment tbDepartment);

    ADHContractMappingDto findByContractId(Long contId, Long orgId);

    Long findContractByContIdAndOrgId(Long contId, Long orgId);

    Long getContIdByHoardIdAndOrgId(Long hoardingId, Long orgId);

    List<ContractMappingDTO> searchContractMappingData(Long orgId, String contractNo, Date contactDate,
	    TbDepartment tbDepartment);
}
