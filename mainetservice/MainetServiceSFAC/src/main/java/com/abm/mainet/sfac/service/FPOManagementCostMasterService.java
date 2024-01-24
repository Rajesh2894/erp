package com.abm.mainet.sfac.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.sfac.dto.FPOManagementCostMasterDTO;
import com.abm.mainet.sfac.dto.FPOMasterDto;

public interface FPOManagementCostMasterService {

	List<FPOManagementCostMasterDTO> getAppliacationDetails(Long fpoId, Long cbboid, Long iaId, Long fyId);

	FPOManagementCostMasterDTO saveAndUpdateApplication(FPOManagementCostMasterDTO mastDto, List<Long> removedIds);
	
	 Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient);

	FPOManagementCostMasterDTO getDetailById(Long fmcId);

	FPOMasterDto getFPODetails(Long masId);

	FPOManagementCostMasterDTO updateApplication(FPOManagementCostMasterDTO mastDto, List<Long> removedIds);

	FPOManagementCostMasterDTO fetchFPOManagementCostbyAppId(Long valueOf);

	void updateApprovalStatusAndRemark(FPOManagementCostMasterDTO oldMasDto, String lastDecision, String status);

}
