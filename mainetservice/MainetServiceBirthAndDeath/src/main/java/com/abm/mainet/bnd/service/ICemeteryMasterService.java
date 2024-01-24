package com.abm.mainet.bnd.service;

import java.util.List;

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;



public interface ICemeteryMasterService {
	
	public List<CemeteryMasterDTO> getAllCemetery(Long orgId);

    public CemeteryMasterDTO getCemeteryById(Long ceId);

	public void saveCemetry(CemeteryMasterDTO cemeteryMasterDTO);

	List<CemeteryMasterDTO> findByCemeteryNameAndCemeteryType(String ceName, Long cpdTypeId, Long orgId);

	List<CemeteryMasterDTO> getAllCemeteryList(Long orgId);
	
	
}