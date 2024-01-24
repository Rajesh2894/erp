package com.abm.mainet.swm.ui.model;

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
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IVehicleMaintenanceService;
import com.abm.mainet.swm.ui.validator.VehicleMaintenanceValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleMaintenanceModel extends AbstractFormModel {

    private static final long serialVersionUID = 1340684254502225688L;

    @Autowired
    private IVehicleMaintenanceService vehicleMaintenanceService;

    private VehicleMaintenanceDTO vehicleMaintenanceDTO = new VehicleMaintenanceDTO();

    private VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    private List<VehicleMaintenanceDTO> vehicleMaintenanceList;

    private List<DocumentDetailsVO> documents = new ArrayList<>();

    @Autowired
    private VehicleMaintenanceValidator vehicleMaintenanceValidator;

    private String saveMode;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ILocationMasService locMasService;

    @Resource
    private DepartmentService departmentService;

    private List<TbAcVendormaster> vendors;

    private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();

    private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();

    private List<LookUp> valueTypeList = new ArrayList<LookUp>();

    public List<VendorBillExpDetailDTO> getExpDetListDto() {
        return expDetListDto;
    }

    public void setExpDetListDto(List<VendorBillExpDetailDTO> expDetListDto) {
        this.expDetListDto = expDetListDto;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public VehicleMaintenanceValidator getVehicleMaintenanceValidator() {
        return vehicleMaintenanceValidator;
    }

    public void setVehicleMaintenanceValidator(VehicleMaintenanceValidator vehicleMaintenanceValidator) {
        this.vehicleMaintenanceValidator = vehicleMaintenanceValidator;
    }

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

    public VehicleMaintenanceDTO getVehicleMaintenanceDTO() {
        return vehicleMaintenanceDTO;
    }

    public void setVehicleMaintenanceDTO(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
        this.vehicleMaintenanceDTO = vehicleMaintenanceDTO;
    }

    public List<VehicleMaintenanceDTO> getVehicleMaintenanceList() {
        return vehicleMaintenanceList;
    }

    public void setVehicleMaintenanceList(List<VehicleMaintenanceDTO> vehicleMaintenanceList) {
        this.vehicleMaintenanceList = vehicleMaintenanceList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbAcVendormaster> getVendors() {
        return vendors;
    }

    public void setVendors(List<TbAcVendormaster> vendors) {
        this.vendors = vendors;
    }

    public IVehicleMaintenanceService getVehicleMaintenanceService() {
        return vehicleMaintenanceService;
    }

    public void setVehicleMaintenanceService(IVehicleMaintenanceService vehicleMaintenanceService) {
        this.vehicleMaintenanceService = vehicleMaintenanceService;
    }

    public VendorBillExpDetailDTO getBillExpDetailDTO() {
        return billExpDetailDTO;
    }

    public void setBillExpDetailDTO(VendorBillExpDetailDTO billExpDetailDTO) {
        this.billExpDetailDTO = billExpDetailDTO;
    }

    public List<LookUp> getValueTypeList() {
        return valueTypeList;
    }

    public void setValueTypeList(List<LookUp> valueTypeList) {
        this.valueTypeList = valueTypeList;
    }

    @Override
    public boolean saveForm() {
        boolean status = false;
        // validateBean(vehicleMaintenanceDTO, VehicleMaintenanceValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleMaintenanceDTO.getVemId() == null) {
                vehicleMaintenanceDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleMaintenanceDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMaintenanceDTO.setCreatedDate(new Date());
                vehicleMaintenanceDTO.setLgIpMac(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.maintenance.add.success"));
            } else {
                vehicleMaintenanceDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMaintenanceDTO.setLgIpMacUpd(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.maintenance.edit.success"));
            }
            vehicleMaintenanceDTO = vehicleMaintenanceService.saveVehicleMaintenance(vehicleMaintenanceDTO);
            /*
             * Long sliLiveStatus = CommonMasterUtility .getLookUpFromPrefixLookUpValue("L", "SLI",
             * UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation()) .getLookUpId(); if
             * (sliLiveStatus != null && sliLiveStatus != 0) { saveBillApprval(vehicleMaintenanceDTO); }
             */
            FileUploadDTO requestDTO = new FileUploadDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            requestDTO.setIdfId(vehicleMaintenanceDTO.getVemId().toString());
            requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            List<DocumentDetailsVO> dto = getDocuments();
            setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
            setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
            fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
            status = true;
            int i = 0;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility
                    .getCurrent().getFileMap().entrySet()) {
                getDocuments().get(i).setDoc_DESC_ENGL(
                        dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
                i++;
            }

        }
        return status;
    }

    private void saveBillApprval(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
        ResponseEntity<?> responseEntity = null;
        List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
        VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
        deductionDetList.add(billDedDetailDTO);
        approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));
        approvalDTO.setBillTypeId(
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MI", "ABT", vehicleMaintenanceDTO.getOrgid()));
        approvalDTO.setOrgId(vehicleMaintenanceDTO.getOrgid());
        approvalDTO.setNarration("Bill Against MB no -" + "Maintenance Tax");
        approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
        approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
        approvalDTO.setVendorId(vehicleMaintenanceDTO.getVendorId());
        approvalDTO.setInvoiceNumber(vehicleMaintenanceDTO.getVemReceiptno().toString());
        approvalDTO.setInvoiceAmount(vehicleMaintenanceDTO.getVemCostincurred());
        billExpDetailDTO.setBudgetCodeId(vehicleMaintenanceDTO.getExpenditureId());
        billExpDetailDTO.setAmount(vehicleMaintenanceDTO.getVemCostincurred());
        billExpDetailDTO.setSanctionedAmount(vehicleMaintenanceDTO.getVemCostincurred());
        expDetListDto.add(billExpDetailDTO);
        approvalDTO.setExpDetListDto(expDetListDto);
        billDedDetailDTO.setBchId(vehicleMaintenanceDTO.getExpenditureId());
        billDedDetailDTO.setBudgetCodeId(vehicleMaintenanceDTO.getDedAcHeadId());
        billDedDetailDTO.setDeductionAmount(vehicleMaintenanceDTO.getDedAmt());
        deductionDetList.add(billDedDetailDTO);
        approvalDTO.setDedDetListDto(deductionDetList);
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

}
