/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.workManagement.service.ContractTimeVariationService;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class ContractTimeVariationModel extends AbstractFormModel {

    /**
     * 
     */
    @Autowired
    ContractTimeVariationService contractTimeVariationService;

    @Autowired
    IFileUploadService fileUpload;

    private static final long serialVersionUID = 1420204527430137644L;

    private List<ContractAgreementSummaryDTO> contractSummaryDTOList = new ArrayList<>();
    private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
    private ContractMastDTO contractMastDTO = new ContractMastDTO();

    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeVariationFileById;
    private String saveMode;

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getRemoveVariationFileById() {
        return removeVariationFileById;
    }

    public void setRemoveVariationFileById(String removeVariationFileById) {
        this.removeVariationFileById = removeVariationFileById;
    }

    public ContractMastDTO getContractMastDTO() {
        return contractMastDTO;
    }

    public void setContractMastDTO(ContractMastDTO contractMastDTO) {
        this.contractMastDTO = contractMastDTO;
    }

    public List<ContractAgreementSummaryDTO> getContractSummaryDTOList() {
        return contractSummaryDTOList;
    }

    public void setContractSummaryDTOList(List<ContractAgreementSummaryDTO> contractSummaryDTOList) {
        this.contractSummaryDTOList = contractSummaryDTOList;
    }

    public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
        return contractAgreementSummaryDTO;
    }

    public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
        this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public void updateContractTimeVariation() {
        ContractDetailDTO contractdetDto = getContractMastDTO().getContractDetailList().get(0);
        ContractMastDTO masDto = ApplicationContextProvider.getApplicationContext().getBean(IContractAgreementService.class)
                .findById(
                        contractAgreementSummaryDTO.getContId(), UserSession.getCurrent().getOrganisation().getOrgid());
        ContractDetailDTO detDto = masDto.getContractDetailList().get(0);
        Date prevConToDate = UtilityService.convertStringDateToDateFormat(contractAgreementSummaryDTO.getContToDate());
        LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(contractdetDto.getContTimeExtUnit(),
                MainetConstants.WorksManagement.UTS, UserSession.getCurrent().getOrganisation().getOrgid());
        if (lookUp.getLookUpCode().equals(MainetConstants.FlagD)) {
            detDto.setContToDate(Utility.getAddedDateBy2(UtilityService.convertStringDateToDateFormat(contractAgreementSummaryDTO.getContToDate()),
                    contractdetDto.getContTimeExtPer().intValue()));
          Long Days =  (long) Utility.getDaysBetDates(prevConToDate, detDto.getContToDate());
          if(detDto.getContToPeriod() != null) {
          Long totalPeriod = (Days + detDto.getContToPeriod());   
          detDto.setContToPeriod(totalPeriod);
          }
        }
        if (lookUp.getLookUpCode().equals(MainetConstants.FlagM)) {
            detDto.setContToDate(Utility.getAddedMonthsBy(UtilityService.convertStringDateToDateFormat(contractAgreementSummaryDTO.getContToDate()),
                    contractdetDto.getContTimeExtPer().intValue()));
            Long Days =  (long) Utility.getDaysBetDates(prevConToDate, detDto.getContToDate());
            if(detDto.getContToPeriod() != null) {
            Long totalPeriod = (Days + detDto.getContToPeriod());   
            detDto.setContToPeriod(totalPeriod);
            }
        }
        if (lookUp.getLookUpCode().equals(MainetConstants.FlagY)) {
            detDto.setContToDate(Utility.getAddedYearsBy(UtilityService.convertStringDateToDateFormat(contractAgreementSummaryDTO.getContToDate()),
                    contractdetDto.getContTimeExtPer().intValue()));
            Long Days =  (long) Utility.getDaysBetDates(prevConToDate, detDto.getContToDate());
            if(detDto.getContToPeriod() != null) {
            Long totalPeriod = (Days + detDto.getContToPeriod());   
            detDto.setContToPeriod(totalPeriod);
            }
        }
        detDto.setContractId(contractAgreementSummaryDTO.getContId());
        detDto.setContTimeExtPer(contractdetDto.getContTimeExtPer());
        detDto.setContTimeExtUnit(contractdetDto.getContTimeExtUnit());
        detDto.setContTimeExtEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        detDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        detDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        detDto.setLmodDate(new Date());
        contractTimeVariationService.updateContractTimeVariation(detDto);
        prepareUploadData();
    }

    public void prepareUploadData() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setIdfId(contractAgreementSummaryDTO.getContNo());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getAttachments();
        setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
        List<Long> variationFileById = null;
        String fileId = getRemoveVariationFileById();
        if (fileId != null && !fileId.isEmpty()) {
            variationFileById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                variationFileById.add(Long.valueOf(fields));
            }
        }
        if (variationFileById != null && !variationFileById.isEmpty()) {
            contractTimeVariationService.deleteVariationFileById(variationFileById,
                    UserSession.getCurrent().getEmployee().getEmpId());
        }
    }

}
