package com.abm.mainet.legal.service;

import java.util.List;

import com.abm.mainet.legal.dto.AdvocateMasterDTO;

public interface AdvocateMasterService {

	AdvocateMasterDTO saveAdvocateMaster(AdvocateMasterDTO advocateMasterDTO);

	List<AdvocateMasterDTO> validateAdvocateMaster(AdvocateMasterDTO advocateMasterDTO);

	AdvocateMasterDTO getAdvocateDetailsById(Long appId, Long orgId);

}
