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
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpFuelDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuelReconciationService;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuellingService;
import com.abm.mainet.vehiclemanagement.ui.validator.VehicleFuelValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleFuelModel extends AbstractFormModel {

    private static final long serialVersionUID = -2710014942516062984L;

    @Autowired
    private IVehicleFuellingService vehicleFuellingService;

    VehicleFuellingDTO vehicleFuellingDTO = new VehicleFuellingDTO();
    private GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();

    private List<PumpMasterDTO> pumpMasterDTOList = new ArrayList<>();

    private List<SLRMEmployeeMasterDTO> slrmEmployeeDTOs = new ArrayList<>();
    
    private List<GenVehicleMasterDTO> vehicleMasterDTOs = new ArrayList<>();

    List<TbAcVendormaster> vendorList;
    private List<VehicleFuellingDTO> vehicleFuellingList;

    private List<PumpFuelDetailsDTO> pumpFuelDetails;

    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    @Autowired
    IFileUploadService fileUpload;

    private List<AttachDocs> attachDocsList = new ArrayList<>();

    @Autowired
    private VehicleFuelValidator vehicleFuellingValidator;

    private String saveMode;

    private List<LookUp> lookUps = new ArrayList<>();

    private List<DocumentDetailsVO> documents = new ArrayList<>();
    
    List<Employee> employeList = new ArrayList<>();
    
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;
    
    @Resource
    private IVehicleFuelReconciationService reconciationService;

	@Override
    public boolean saveForm() {
		List<Long> removeFileById = null;
        boolean status = false;
        validateBean(vehicleFuellingDTO, VehicleFuelValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleFuellingDTO.getVefId() == null) {
                vehicleFuellingDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleFuellingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleFuellingDTO.setCreatedDate(new Date());
                vehicleFuellingDTO.setLgIpMac(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.fuelling.add.success"));
            } else {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
					if (reconciationService.checkIsFuellingReconciled(vehicleFuellingDTO.getVefId(), vehicleFuellingDTO.getOrgid())) {
						this.addValidationError(ApplicationSession.getInstance().getMessage("Fueling Datails Already Reconciled, Can Not Edit Details..."));
						return false;
					}
				}       	
                vehicleFuellingDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleFuellingDTO.setLgIpMacUpd(Utility.getMacAddress());
                vehicleFuellingDTO.setUpdatedDate(new Date());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("vehicle.fuelling.edit.success"));
            }
            if (!vehicleFuellingDTO.getTbSwVehiclefuelDets().isEmpty()) {
                vehicleFuellingDTO.getTbSwVehiclefuelDets().forEach(item -> {
                    if (item.getVefdId() == null) {
                        item.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                        item.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        item.setCreatedDate(new Date());
                        item.setIsDeleted(MainetConstants.FlagN);
                        item.setLgIpMac(Utility.getMacAddress());
                        item.setTbSwVehiclefuelMast(vehicleFuellingDTO);
                    } else {
                        item.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        item.setUpdatedDate(new Date());
                        item.setLgIpMacUpd(Utility.getMacAddress());
                        item.setTbSwVehiclefuelMast(vehicleFuellingDTO);
                    }
                });
            }
            
            
            String fileId = vehicleFuellingDTO.getRemoveFileById();
    		if (fileId != null && !fileId.isEmpty()) {
    			removeFileById = new ArrayList<>();
    			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
    			for (String fields : fileArray) {
    				removeFileById.add(Long.valueOf(fields));
    			}
    		}
    		if (removeFileById != null && !removeFileById.isEmpty()) {				
    			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, vehicleFuellingDTO.getUpdatedBy());
    		}

            vehicleFuellingDTO = vehicleFuellingService.saveVehicle(vehicleFuellingDTO);

            FileUploadDTO requestDTO = new FileUploadDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + vehicleFuellingDTO.getVefId().toString()+ MainetConstants.WINDOWS_SLASH + vehicleFuellingDTO.getVeId().toString());
            requestDTO.setDepartmentName(Constants.SHORT_CODE);
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

    public List<PumpFuelDetailsDTO> getPumpFuelDetails() {
        return pumpFuelDetails;
    }

    public void setPumpFuelDetails(List<PumpFuelDetailsDTO> pumpFuelDetails) {
        this.pumpFuelDetails = pumpFuelDetails;
    }

    public IVehicleFuellingService getVehicleFuellingService() {
        return vehicleFuellingService;
    }

    public void setVehicleFuellingService(IVehicleFuellingService vehicleFuellingService) {
        this.vehicleFuellingService = vehicleFuellingService;
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

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    public List<VehicleFuellingDTO> getVehicleFuellingList() {
        return vehicleFuellingList;
    }

    public void setVehicleFuellingList(List<VehicleFuellingDTO> vehicleFuellingList) {
        this.vehicleFuellingList = vehicleFuellingList;
    }

    public VehicleFuelValidator getVehicleFuellingValidator() {
        return vehicleFuellingValidator;
    }

    public void setVehicleFuellingValidator(VehicleFuelValidator vehicleFuellingValidator) {
        this.vehicleFuellingValidator = vehicleFuellingValidator;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
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

    public List<LookUp> getLookUps() {
        return lookUps;
    }

    public void setLookUps(List<LookUp> lookUps) {
        this.lookUps = lookUps;
    }
    
    public List<Employee> getEmployeList() {
		return employeList;
	}

	public void setEmployeList(List<Employee> employeList) {
		this.employeList = employeList;
	}

	public List<PumpMasterDTO> getPumpMasterDTOList() {
		return pumpMasterDTOList;
	}

	public void setPumpMasterDTOList(List<PumpMasterDTO> pumpMasterDTOList) {
		this.pumpMasterDTOList = pumpMasterDTOList;
	}

	public List<SLRMEmployeeMasterDTO> getSlrmEmployeeDTOs() {
		return slrmEmployeeDTOs;
	}

	public void setSlrmEmployeeDTOs(List<SLRMEmployeeMasterDTO> slrmEmployeeDTOs) {
		this.slrmEmployeeDTOs = slrmEmployeeDTOs;
	}

	public List<GenVehicleMasterDTO> getVehicleMasterDTOs() {
		return vehicleMasterDTOs;
	}

	public void setVehicleMasterDTOs(List<GenVehicleMasterDTO> vehicleMasterDTOs) {
		this.vehicleMasterDTOs = vehicleMasterDTOs;
	}
	
    

}
