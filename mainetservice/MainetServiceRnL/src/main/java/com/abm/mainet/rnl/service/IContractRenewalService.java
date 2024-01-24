package com.abm.mainet.rnl.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;

public interface IContractRenewalService {

    ContractMastDTO saveContractRenewal(ContractMastDTO contractMastDTO, EstateContMappingDTO estateContMappingDTO,
            Map<String, File> UploadMap, String removeFieldId, List<DocumentDetailsVO> commonFileAttachment,
            RequestDTO requestDTO, Employee employee, Organisation organisation, int langId);
}
