package com.abm.mainet.rnl.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;

@Service
public class ContractRenewalServiceImpl implements IContractRenewalService {

    @Autowired
    private IContractAgreementService ContractAgreementService;

    @Autowired
    private IEstateContractMappingService iEstateContractMappingService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    TbDepartmentService tbDepartmentService;

    @Transactional
    public ContractMastDTO saveContractRenewal(ContractMastDTO contractMastDTO, EstateContMappingDTO estateContMappingDTO,
            Map<String, File> UploadMap, String removeFieldId, List<DocumentDetailsVO> commonFileAttachment,
            RequestDTO requestDTO, Employee employee, Organisation organisation, int langId) {
        // update old contract with contCloseFlag : R
        iEstateContractMappingService.updateContractCloseFlag("R", contractMastDTO.getContId(), employee.getEmpId());

        // generate contract no inside common method
        contractMastDTO.setContNo(null);
        contractMastDTO.setContId(0);// doing this because we need to insert record if id present than automatic it update old
                                     // contract which is not a requirement
        ContractMastDTO mastDTO = ContractAgreementService.saveContractAgreement(contractMastDTO,
                organisation.getOrgid(), langId, employee.getEmpId(), MainetConstants.MODE_CREATE /* getModeType() */, UploadMap);
        // insert in TB_RL_EST_CONTRACT_MAPPING and tb_rl_bill_mast

        estateContMappingDTO.setContractId(mastDTO.getContId());// set new contract Id
        iEstateContractMappingService.save(estateContMappingDTO);

        // document code setup
        requestDTO.setDepartmentName(
                tbDepartmentService.findDepartmentShortCodeByDeptId(contractMastDTO.getContDept(), organisation.getOrgid()));
        requestDTO.setIdfId(MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE + contractMastDTO.getContId());
        fileUpload.doMasterFileUpload(commonFileAttachment, requestDTO);
        List<Long> enclosureRemoveById = null;
        if (removeFieldId != null && !removeFieldId.isEmpty()) {
            enclosureRemoveById = new ArrayList<>();
            String fileArray[] = removeFieldId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                enclosureRemoveById.add(Long.valueOf(fields));
            }
        }
        if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
            ContractAgreementService.deleteContractDocFileById(enclosureRemoveById,
                    UserSession.getCurrent().getEmployee().getEmpId());
        return mastDTO;
    }

}
