package com.abm.mainet.sfac.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.FPOProfileMasterDto;

public interface FPOProfileMasterService {

	FPOProfileMasterDto saveAndUpdateApplication(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto getDetailById(Long fpoId);

	FPOProfileMasterDto saveAndUpdateLicenseInfo(FPOProfileMasterDto mastDto, List<Long> removeId);

	FPOProfileMasterDto saveAndUpdateCreditInfo(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateEquityInfo(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateFarmerSummary(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateCreditGrand(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateTraningInfo(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateManagementInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdateStorageInfo(FPOProfileMasterDto mastDto);

	FPOProfileMasterDto saveAndUpdateCustomInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdatePNSInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdateSubsidiesInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdatePreharveshInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdatePostharvestInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdateTransportVehicleInfo(FPOProfileMasterDto mastDto);
	
	FPOProfileMasterDto saveAndUpdateMLInfo(FPOProfileMasterDto mastDto, FPOMasterDto fpoMasterDto);
	
	FPOProfileMasterDto saveAndUpdateABSInfo(FPOProfileMasterDto mastDto, List<Long> removeId);
	
	FPOProfileMasterDto saveAndUpdateBPInfo(FPOProfileMasterDto mastDto, List<Long> removeId);

	FPOProfileMasterDto saveAndUpdateDPRInfo(FPOProfileMasterDto mastDto);

	Map<Long, Set<File>> getUploadedFileList(Map<Long, List<AttachDocs>> newMap, FileNetApplicationClient instance);

	FPOMasterDto getFPODetails(Long masId);

}
