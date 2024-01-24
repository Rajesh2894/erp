package com.abm.mainet.vehiclemanagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDetDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuelReconciationService;
import com.abm.mainet.vehiclemanagement.ui.validator.RefuelingAdviceReconcilValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class RefuelingAdviceReconciliationModel extends AbstractFormModel {

    private static final long serialVersionUID = 1567368066198298711L;

    @Autowired
    private IVehicleFuelReconciationService vehicleFuelReconciationService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ILocationMasService locMasService;

    @Resource
    private DepartmentService departmentService;

    private VehicleFuelReconciationDTO vehicleFuelReconciationDTO = new VehicleFuelReconciationDTO();

    VehicleFuellingDTO vehicleFuellingDTO = new VehicleFuellingDTO();

    private GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();

    private List<VehicleFuellingDTO> vehicleFuellingList;

    private List<VehicleFuelReconciationDTO> vehicleFuelReconcilationList;

    private List<VehicleFuelReconciationDetDTO> vehicleFuelReconciationDetDTOList;

    private List<VehicleFuelReconciationDTO> deductionHeadList;

    private String saveMode;

    private List<LookUp> lookUps = new ArrayList<>();

    private List<DocumentDetailsVO> documents = new ArrayList<>();

    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();

    private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();

    private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();
    
    List<TbAcVendormaster> vendorList = new ArrayList<>();

    private Map<Long, String> listExpenditureHead = null;

    private List<LookUp> valueTypeList = new ArrayList<LookUp>();

    @Override
    public boolean saveForm() {
        boolean status = false;
        validateBean(vehicleFuelReconciationDTO, RefuelingAdviceReconcilValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleFuelReconciationDTO.getInrecId() == null) {  // vefId
                vehicleFuelReconciationDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleFuelReconciationDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleFuelReconciationDTO.setCreatedDate(new Date());
                vehicleFuelReconciationDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("fueling.advice.add.success"));
            } else {
                vehicleFuelReconciationDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleFuelReconciationDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                // vehicleFuelReconciationDTO.setUpdatedDate(new Date());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("fueling.advice.edit.success"));
            }

            if (!vehicleFuelReconciationDTO.getTbSwVehiclefuelInrecDets().isEmpty()) {
                vehicleFuelReconciationDetDTOList = new ArrayList<>();
                vehicleFuelReconciationDTO.getTbSwVehiclefuelInrecDets().forEach(item -> {

                    if (null != item.getInrecdActive() && item.getInrecdActive().equalsIgnoreCase(MainetConstants.FlagY)) {
                        if (item.getInrecdId() == null) {
                            item.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                            item.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                            item.setCreatedDate(new Date());
                            item.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                            item.setTbSwVehiclefuelInrec(vehicleFuelReconciationDTO);
                        } else {
                            item.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                            item.setUpdatedDate(new Date());
                            item.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                            item.setTbSwVehiclefuelInrec(vehicleFuelReconciationDTO);
                        }
                        vehicleFuelReconciationDetDTOList.add(item);
                    }
                });
                vehicleFuelReconciationDTO.setTbSwVehiclefuelInrecDets(vehicleFuelReconciationDetDTOList);
            }
            vehicleFuelReconciationDTO = vehicleFuelReconciationService.save(vehicleFuelReconciationDTO);
            Long sliLiveStatus = CommonMasterUtility
                    .getLookUpFromPrefixLookUpValue("L", "SLI",
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                    .getLookUpId();
            if (sliLiveStatus != null && sliLiveStatus != 0) {
                // saveBillApprval(vehicleFuelReconciationDTO);
            }
            // vehicleFuelReconciationDTO = vehicleFuelReconciationService.save(vehicleFuelReconciationDTO);
            FileUploadDTO requestDTO = new FileUploadDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            requestDTO.setIdfId("ADV_RECON/" + vehicleFuelReconciationDTO.getInrecId());
            requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            List<DocumentDetailsVO> dto = getDocuments();
            setDocuments(fileUpload.setFileUploadMethod(getDocuments()));

            setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
            fileUpload.doMasterFileUpload(getAttachments(), requestDTO);

            int i = 0;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility
                    .getCurrent().getFileMap().entrySet()) {
                getDocuments().get(i).setDoc_DESC_ENGL(
                        dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
                i++;
            }

            // vehicleFuelReconciationService.save(vehicleFuelReconciationDTO);
            status = true;
        }
        return status;
    }

    private void saveBillApprval(VehicleFuelReconciationDTO vehicleFuelReconciationDTO) {
        ResponseEntity<?> responseEntity = null;
        List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
        VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
        // deductionDetList.add(billDedDetailDTO);
        /*
         * workOrder = workOrderService.getWorkOredrByOrderId(masterDto.getWorkOrId()); tenderWorkDto =
         * tenderInitiationService.findWorkByWorkId(workOrder.getContractMastDTO().getContId()); definitionDto =
         * definitionService.findAllWorkDefinitionById(tenderWorkDto.getWorkId()); MeasurementBookMasterDto bookMasterDto =
         * bookService.getMBByWorkOrderId(masterDto.getWorkOrId(), MainetConstants.FlagA); List<MeasurementBookTaxDetailsDto>
         * bookTaxDetailsDtos = mbTaxdetailsService.getMbTaxDetails(definitionDto.getWorkId(), getParentOrgId());
         */

        approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));
        approvalDTO.setBillTypeId(
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MI", "ABT", vehicleFuelReconciationDTO.getOrgid()));
        approvalDTO.setOrgId(vehicleFuelReconciationDTO.getOrgid());
        approvalDTO.setNarration("Bill Against MB no -" + "Fuel Charges Tax");
        approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
        approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
        approvalDTO.setVendorId(vehicleFuelReconciationDTO.getVendorId());
        approvalDTO.setInvoiceNumber(vehicleFuelReconciationDTO.getInrecdInvno().toString());
        approvalDTO.setInvoiceDate(vehicleFuelReconciationDTO.getInrecdInvdate().toString());
        approvalDTO.setInvoiceAmount(vehicleFuelReconciationDTO.getInrecdInvamt());
        // WorkDefinationYearDetDto dto = definitionDto.getYearDtos().get(0);
        billExpDetailDTO.setBudgetCodeId(vehicleFuelReconciationDTO.getExpenditureId());
        billExpDetailDTO.setAmount(vehicleFuelReconciationDTO.getInrecdInvamt());
        billExpDetailDTO.setSanctionedAmount(vehicleFuelReconciationDTO.getInrecdInvamt());
        expDetListDto.add(billExpDetailDTO);
        approvalDTO.setExpDetListDto(expDetListDto);
        billDedDetailDTO.setBchId(vehicleFuelReconciationDTO.getExpenditureId());
        billDedDetailDTO.setBudgetCodeId(vehicleFuelReconciationDTO.getDedAcHeadId());
        billDedDetailDTO.setDeductionAmount(vehicleFuelReconciationDTO.getDedAmt());
        deductionDetList.add(billDedDetailDTO);
        approvalDTO.setDedDetListDto(deductionDetList);

        /*
         * billDedDetailDTO.setBudgetCodeId(dto.getSacHeadId()); billDedDetailDTO.setDeductionAmount(new BigDecimal("500"));
         * deductionDetList.add(billDedDetailDTO); approvalDTO.setDedDetListDto(deductionDetList);
         */
        long fieldId = 0;
        if (UserSession.getCurrent().getLoggedLocId() != null) {
            final TbLocationMas locMas = locMasService
                    .findById(UserSession.getCurrent().getLoggedLocId());
            if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
            }
        }
        if (fieldId == 0) {
            throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
        }
        approvalDTO.setFieldId(fieldId);
        Department entities = departmentService.getDepartment(MainetConstants.SolidWasteManagement.SHORT_CODE,
                MainetConstants.CommonConstants.ACTIVE);
        approvalDTO.setDepartmentId(entities.getDpDeptid());
        try {
            responseEntity = RestClient.callRestTemplateClient(approvalDTO,
                    ServiceEndpoints.SALARY_POSTING);
            if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                // bookService.updateBillNumberByMbId(responseEntity.getBody().toString(), workMbId);
                // setWorkBillNumber(responseEntity.getBody().toString());
            }
        } catch (Exception exception) {

            throw new FrameworkException("error occured while bill posting to account module ", exception);
        }
    }

    public List<VehicleFuelReconciationDTO> getVehicleFuelReconcilationList() {
        return vehicleFuelReconcilationList;
    }

    public void setVehicleFuelReconcilationList(List<VehicleFuelReconciationDTO> vehicleFuelReconcilationList) {
        this.vehicleFuelReconcilationList = vehicleFuelReconcilationList;
    }

    public List<LookUp> getLookUps() {
        return lookUps;
    }

    public void setLookUps(List<LookUp> lookUps) {
        this.lookUps = lookUps;
    }

    public IVehicleFuelReconciationService getVehicleFuelReconciationService() {
        return vehicleFuelReconciationService;
    }

    public void setVehicleFuelReconciationService(IVehicleFuelReconciationService vehicleFuelReconciationService) {
        this.vehicleFuelReconciationService = vehicleFuelReconciationService;
    }

    public VehicleFuelReconciationDTO getVehicleFuelReconciationDTO() {
        return vehicleFuelReconciationDTO;
    }

    public void setVehicleFuelReconciationDTO(VehicleFuelReconciationDTO vehicleFuelReconciationDTO) {
        this.vehicleFuelReconciationDTO = vehicleFuelReconciationDTO;
    }

    public VehicleFuellingDTO getVehicleFuellingDTO() {
        return vehicleFuellingDTO;
    }

    public void setVehicleFuellingDTO(VehicleFuellingDTO vehicleFuellingDTO) {
        this.vehicleFuellingDTO = vehicleFuellingDTO;
    }

    public GenVehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(GenVehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

    public List<VehicleFuellingDTO> getVehicleFuellingList() {
        return vehicleFuellingList;
    }

    public void setVehicleFuellingList(List<VehicleFuellingDTO> vehicleFuellingList) {
        this.vehicleFuellingList = vehicleFuellingList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<VehicleFuelReconciationDetDTO> getVehicleFuelReconciationDetDTOList() {
        return vehicleFuelReconciationDetDTOList;
    }

    public void setVehicleFuelReconciationDetDTOList(List<VehicleFuelReconciationDetDTO> vehicleFuelReconciationDetDTOList) {
        this.vehicleFuelReconciationDetDTOList = vehicleFuelReconciationDetDTOList;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public IFileUploadService getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(IFileUploadService fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<VehicleFuelReconciationDTO> getDeductionHeadList() {
        return deductionHeadList;
    }

    public void setDeductionHeadList(List<VehicleFuelReconciationDTO> deductionHeadList) {
        this.deductionHeadList = deductionHeadList;
    }

    public Map<Long, String> getListExpenditureHead() {
        return listExpenditureHead;
    }

    public void setListExpenditureHead(Map<Long, String> listExpenditureHead) {
        this.listExpenditureHead = listExpenditureHead;
    }

    public List<LookUp> getValueTypeList() {
        return valueTypeList;
    }

    public void setValueTypeList(List<LookUp> valueTypeList) {
        this.valueTypeList = valueTypeList;
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }
    
    

}
