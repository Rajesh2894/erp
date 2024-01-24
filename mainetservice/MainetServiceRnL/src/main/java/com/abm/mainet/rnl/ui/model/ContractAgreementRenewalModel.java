package com.abm.mainet.rnl.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractDetailDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.ContractAgreementRenewalDto;
import com.abm.mainet.rnl.dto.ContractPropListDTO;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.service.IContractRenewalService;
import com.abm.mainet.rnl.service.IEstateContractMappingService;

@Component
@Scope("session")
public class ContractAgreementRenewalModel extends AbstractFormModel {
    /**
     * 
     */
    private static final long serialVersionUID = 7808311072862480742L;

    @Autowired
    TbDepartmentService tbDepartmentService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    TbFinancialyearService financialyearService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IEstateContractMappingService iEstateContractMappingService;

    @Autowired
    private IContractRenewalService contractRenewalService;

    private List<ContractAgreementSummaryDTO> summaryDTOList = new ArrayList<>();
    private List<ContractAgreementSummaryDTO> contractNoList = null;
    private List<Object[]> vendorList = null;
    ContractAgreementRenewalDto contactAgreementdto = null;
    ContractMastDTO contractMastDTO = null;
    private String saveMode;
    private String modeType;
    private String removeCommonFileById;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Map<String, File> UploadMap = new HashMap<>();
    private List<TbDepartment> departmentList;
    // D#138342
    private List<Object[]> estateMasters = Collections.emptyList();
    private EstatePropMaster estatePropMaster;
    private Map<Long, String> codeMap = new HashMap<>();
    private List<AttachDocs> documentList;
    private String removeChildIds;
    private String removeEventIds;
    private String removeShiftIds;
    private String removeFacilityIds;
    private String removeAminities;
    List<LookUp> usageType = new ArrayList<LookUp>();

    public TbDepartmentService getTbDepartmentService() {
        return tbDepartmentService;
    }

    public void setTbDepartmentService(TbDepartmentService tbDepartmentService) {
        this.tbDepartmentService = tbDepartmentService;
    }

    public IFileUploadService getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(IFileUploadService fileUpload) {
        this.fileUpload = fileUpload;
    }

    public TbFinancialyearService getFinancialyearService() {
        return financialyearService;
    }

    public void setFinancialyearService(TbFinancialyearService financialyearService) {
        this.financialyearService = financialyearService;
    }

    public IAttachDocsService getAttachDocsService() {
        return attachDocsService;
    }

    public void setAttachDocsService(IAttachDocsService attachDocsService) {
        this.attachDocsService = attachDocsService;
    }

    public IEstateContractMappingService getiEstateContractMappingService() {
        return iEstateContractMappingService;
    }

    public void setiEstateContractMappingService(IEstateContractMappingService iEstateContractMappingService) {
        this.iEstateContractMappingService = iEstateContractMappingService;
    }

    public List<ContractAgreementSummaryDTO> getSummaryDTOList() {
        return summaryDTOList;
    }

    public void setSummaryDTOList(List<ContractAgreementSummaryDTO> summaryDTOList) {
        this.summaryDTOList = summaryDTOList;
    }

    public List<ContractAgreementSummaryDTO> getContractNoList() {
        return contractNoList;
    }

    public void setContractNoList(List<ContractAgreementSummaryDTO> contractNoList) {
        this.contractNoList = contractNoList;
    }

    public List<Object[]> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<Object[]> vendorList) {
        this.vendorList = vendorList;
    }

    public ContractAgreementRenewalDto getContactAgreementdto() {
        return contactAgreementdto;
    }

    public void setContactAgreementdto(ContractAgreementRenewalDto contactAgreementdto) {
        this.contactAgreementdto = contactAgreementdto;
    }

    public ContractMastDTO getContractMastDTO() {
        return contractMastDTO;
    }

    public void setContractMastDTO(ContractMastDTO contractMastDTO) {
        this.contractMastDTO = contractMastDTO;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getRemoveCommonFileById() {
        return removeCommonFileById;
    }

    public void setRemoveCommonFileById(String removeCommonFileById) {
        this.removeCommonFileById = removeCommonFileById;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public Map<String, File> getUploadMap() {
        return UploadMap;
    }

    public void setUploadMap(Map<String, File> uploadMap) {
        UploadMap = uploadMap;
    }

    public List<TbDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<TbDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Object[]> getEstateMasters() {
        return estateMasters;
    }

    public void setEstateMasters(List<Object[]> estateMasters) {
        this.estateMasters = estateMasters;
    }

    public EstatePropMaster getEstatePropMaster() {
        return estatePropMaster;
    }

    public void setEstatePropMaster(EstatePropMaster estatePropMaster) {
        this.estatePropMaster = estatePropMaster;
    }

    public Map<Long, String> getCodeMap() {
        return codeMap;
    }

    public void setCodeMap(Map<Long, String> codeMap) {
        this.codeMap = codeMap;
    }

    public List<AttachDocs> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<AttachDocs> documentList) {
        this.documentList = documentList;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public String getRemoveEventIds() {
        return removeEventIds;
    }

    public void setRemoveEventIds(String removeEventIds) {
        this.removeEventIds = removeEventIds;
    }

    public String getRemoveShiftIds() {
        return removeShiftIds;
    }

    public void setRemoveShiftIds(String removeShiftIds) {
        this.removeShiftIds = removeShiftIds;
    }

    public String getRemoveFacilityIds() {
        return removeFacilityIds;
    }

    public void setRemoveFacilityIds(String removeFacilityIds) {
        this.removeFacilityIds = removeFacilityIds;
    }

    public String getRemoveAminities() {
        return removeAminities;
    }

    public void setRemoveAminities(String removeAminities) {
        this.removeAminities = removeAminities;
    }

    public List<LookUp> getUsageType() {
        return usageType;
    }

    public void setUsageType(List<LookUp> usageType) {
        this.usageType = usageType;
    }

    @Override
    public boolean saveForm() {
        final ContractMastDTO contractMastDTO = getContractMastDTO();
        List<ContractDetailDTO> contractDetailList = contractMastDTO.getContractDetailList();
        ContractAgreementRenewalDto agreementRenewalDto = getContactAgreementdto();
        // fromDate and toDate Set here
        // at the time of renewal toDate become fromDate
        contractDetailList.get(0).setContFromDate(contractDetailList.get(0).getContToDate());
        contractDetailList.get(0).setContToDate(agreementRenewalDto.getToDate());
        long noOfDays = Utility.getDaysBetweenDates(contractDetailList.get(0).getContFromDate(),
                contractDetailList.get(0).getContToDate());
        contractDetailList.get(0).setContToPeriod(noOfDays);
        contractMastDTO.setContractDetailList(contractDetailList);

        /*
         * contractMastDTO.getContractPart1List().clear(); contractMastDTO.getContractPart2List().clear();
         */
        // set id only empty because at the time of search of contract Agreement common method query need this data
        // ContractAgreementDaoImpl file method is getContractAgreementSummary
        contractMastDTO.getContractPart1List().forEach(obj -> {
            obj.setContp1Id(0);
        });
        contractMastDTO.getContractPart2List().forEach(obj -> {
            obj.setContp2Id(0);
        });

        if (contractMastDTO.getContractDetailList().get(0).getContractAmt() != null)
            contractMastDTO.getContractDetailList().get(0)
                    .setContAmount(contractMastDTO.getContractDetailList().get(0).getContractAmt().doubleValue());
        // fetch documents of contract old id
        List<AttachDocs> attachDocs = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE + contractMastDTO.getContId());

        Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();
        if (!attachDocs.isEmpty()) {
            fileMap.put((long) (fileMap.size()), makeCopyOfOldDoc(attachDocs, FileNetApplicationClient.getInstance()));
            FileUploadUtility.getCurrent().setFileMap(fileMap);
        }

        EstateContMappingDTO contratcMappingDto = iEstateContractMappingService.findByContractId(contractMastDTO.getContId());
        List<ContractPropListDTO> contractPropListDTO = new ArrayList<>();
        if (contratcMappingDto != null) {
            ContractPropListDTO dto = new ContractPropListDTO();
            // propId
            dto.setPropId(contratcMappingDto.getContractPropListDTO().get(0).getPropId());
            contractPropListDTO.add(dto);
        } else {
            // return error MSG for contract mapping not getting for old Contract Id
        }

        // insert in TB_RL_EST_CONTRACT_MAPPING and tb_rl_bill_mast
        EstateContMappingDTO estateContMappingDTO = new EstateContMappingDTO();
        estateContMappingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        estateContMappingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        estateContMappingDTO.setCreatedDate(new Date());
        estateContMappingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        estateContMappingDTO.setContractPropListDTO(contractPropListDTO);
        estateContMappingDTO.setEsId(contratcMappingDto.getEsId());// set esId

        // document code setup
        RequestDTO requestDTO = new RequestDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        requestDTO.setOrgId(orgId);
        requestDTO.setStatus(MainetConstants.FlagA);
        /*
         * requestDTO.setDepartmentName( tbDepartmentService.findDepartmentShortCodeByDeptId(contractMastDTO.getContDept(),
         * orgId)); requestDTO.setIdfId(MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE +
         * contractMastDTO.getContId());
         */
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        // List<DocumentDetailsVO> dtoDocName = getCommonFileAttachment();
        List<DocumentDetailsVO> dto = getCommonFileAttachment();
        setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        // below code is only for set name of document
        for (int i = 0; i < dto.size(); i++) {
            if (getCommonFileAttachment().size() - 1 >= i) {
                getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dto.get(i).getDoc_DESC_ENGL());
            }

        }
        int j = attachDocs.size() - 1;
        for (int i = 0; i < getCommonFileAttachment().size(); i++) {
            if (getCommonFileAttachment().get(i).getDoc_DESC_ENGL() == null) {
                getCommonFileAttachment().get(i).setDoc_DESC_ENGL(attachDocs.get(j).getDmsDocName());
                j--;
            }
        }

        /*
         * int i = 0; for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) { if
         * (attachDocs.size() > i) {
         * getCommonFileAttachment().get(i).setDoc_DESC_ENGL(attachDocs.get(entry.getKey().intValue()).getDmsDocName()); } else {
         * getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dtoDocName.get(entry.getKey().intValue()).getDoc_DESC_ENGL()); } i++;
         * }
         */
        contractMastDTO.setUpdatedBy(null);
        contractMastDTO.setUpdatedDate(null);

        contractRenewalService.saveContractRenewal(contractMastDTO, estateContMappingDTO, UploadMap, getRemoveCommonFileById(),
                getCommonFileAttachment(), requestDTO, UserSession.getCurrent().getEmployee(),
                UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

        return true;
    }

    public Set<File> makeCopyOfOldDoc(List<AttachDocs> attachDocs,
            FileNetApplicationClient fileNetApplicationClient) {
        Set<File> fileList = new HashSet<>();
        for (AttachDocs doc : attachDocs) {
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
            String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
            final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
            FileOutputStream fos = null;
            File file = null;
            try {
                final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);
                Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);
                file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);
                fos = new FileOutputStream(file);
                fos.write(image);
                fos.close();
            } catch (final Exception e) {
                throw new FrameworkException(e);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (final IOException e) {
                    throw new FrameworkException(e);
                }
            }
            fileList.add(file);
        }
        return fileList;
    }

}
