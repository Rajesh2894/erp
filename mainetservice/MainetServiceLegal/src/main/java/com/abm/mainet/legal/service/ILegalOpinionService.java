package com.abm.mainet.legal.service;

import java.util.List;

import com.abm.mainet.legal.dto.LegalOpinionDetailDTO;

public interface ILegalOpinionService {

	LegalOpinionDetailDTO saveLegalOpinionEntry(LegalOpinionDetailDTO legalOpinionDetailDTO);

	void saveLegalOpinionApplication(LegalOpinionDetailDTO legalOpinionDetailDTO);

	LegalOpinionDetailDTO findLegalOpinionApplicationByApmId(Long orgId, Long apmApplicationId);

	List<LegalOpinionDetailDTO> findAllByOrgId(Long orgId);

	List<LegalOpinionDetailDTO> findAllByOrgIdAndDeptId(Long orgId, Long DeptId);

	LegalOpinionDetailDTO findByIds(Long id, Long orgId);

}
