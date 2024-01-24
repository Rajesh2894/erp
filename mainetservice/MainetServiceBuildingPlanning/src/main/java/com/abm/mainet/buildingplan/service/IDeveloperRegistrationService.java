package com.abm.mainet.buildingplan.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import com.abm.mainet.buildingplan.dto.DeveloperRegistrationDTO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

@WebService
public interface IDeveloperRegistrationService {

	DeveloperRegistrationDTO saveDeveloperRegForm(DeveloperRegistrationDTO requestDTO, Employee loggedInUser, String lgIpMacUpd, Long orgId, Long serviceId, String deptCode);
	
	DeveloperRegistrationDTO getDeveloperRegistrationDtoById(Long id, Long orgId);

	void saveApplicantInfoDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode);
	
	void saveAuthUserDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode);

	void saveCapacityFormDocuments(DeveloperRegistrationDTO developerRegistrationDTO, Long empId, Long orgId,
			Long serviceId, String deptCode, List<DocumentDetailsVO> checkListDoc);

	Map<Long, Set<File>> getUploadedFileList(List<CFCAttachment> newMap,
			FileNetApplicationClient fileNetApplicationClient, Map<Long, Set<File>> fileMap);

	Map<Long, Set<File>> getDocUploadedFileList(List<AttachDocs> newMap,
			FileNetApplicationClient fileNetApplicationClient, Map<Long, Set<File>> fileMap, Long docCount);	
}
