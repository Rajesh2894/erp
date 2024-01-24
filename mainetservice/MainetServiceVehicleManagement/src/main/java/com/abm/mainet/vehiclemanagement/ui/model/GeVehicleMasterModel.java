package com.abm.mainet.vehiclemanagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDetDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class GeVehicleMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -5231739886203518846L;
    @Autowired
    IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private IVehicleMaintenanceService vehicleMaintenanceService;
	
    GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();
    GenVehicleMasterDetDTO vehicleMasterDetDTO = new GenVehicleMasterDetDTO();
    List<ContractMappingDTO> contractlist;
    List<GenVehicleMasterDTO> vehicleMasterList;
    List<GenVehicleMasterDetDTO> vehicleMasterdetList;
    List<TbAcVendormaster> vendorList;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String saveMode;
    
    @Resource
	IFileUploadService fileUpload;
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean saveForm() {
        BigDecimal total = new BigDecimal(10.00);
        boolean status = true;
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
	        boolean checkerForVehicle = vehicleMaintenanceService.isForVehicleWorkFlowInProgress(vehicleMasterDTO.getVeId(),UserSession.getCurrent().getOrganisation().getOrgid());
			if(checkerForVehicle) {
				this.addValidationError(ApplicationSession.getInstance().getMessage("Vehicle.number.already.in.progress.wait.till.exist.from.Maintenance"));
			}
        }
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleMasterDTO.getVeId() == null) {
                vehicleMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMasterDTO.setCreatedDate(new Date());
                vehicleMasterDTO.setLgIpMac(Utility.getMacAddress());
                vehicleMasterDTO.setVeActive(MainetConstants.FlagY);
           //     vehicleMasterDTO.setVeCapacity();

            } else {
                vehicleMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMasterDTO.setUpdatedDate(new Date());
                vehicleMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
            }
            if(vehicleMasterDTO.getVeNo()!=null)
            vehicleMasterDTO.setVeNo( vehicleMasterDTO.getVeNo().substring(vehicleMasterDTO.getVeNo().lastIndexOf(",") + 1));
            
			/*
			 * for (GenVehicleMasterDetDTO vehicleMasterdet :
			 * vehicleMasterDTO.getTbSwVehicleMasterdets()) { if
			 * (vehicleMasterdet.getVedId() == null) {
			 * vehicleMasterdet.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid
			 * ());
			 * vehicleMasterdet.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId
			 * ()); vehicleMasterdet.setCreatedDate(new Date());
			 * vehicleMasterdet.setLgIpMac(Utility.getMacAddress());
			 * vehicleMasterdet.setVeActive(MainetConstants.FlagY);
			 * vehicleMasterdet.setTbSwVehicleMaster(vehicleMasterDTO); } else {
			 * vehicleMasterdet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId
			 * ()); vehicleMasterdet.setUpdatedDate(new Date());
			 * vehicleMasterdet.setLgIpMacUpd(Utility.getMacAddress());
			 * vehicleMasterdet.setTbSwVehicleMaster(vehicleMasterDTO); } }
			 */
            if (status) {
                if (vehicleMasterDTO.getVeId() == null) {
                    vehicleMasterService.saveVehicle(vehicleMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMasterDTO.save.add"));
                } else {
                    vehicleMasterService.saveVehicle(vehicleMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMasterDTO.save.edit"));
                }
                FileUploadDTO requestDTO = new FileUploadDTO();
                requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                requestDTO.setStatus(MainetConstants.FlagA);
                requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.VECH_MASTER+vehicleMasterDTO.getVeId()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
                requestDTO.setDepartmentName(Constants.SHORT_CODE);
                requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
                fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
                status = true;
            } else {
                addValidationError(ApplicationSession.getInstance().getMessage("vehicle.master.validation.regNo"));
                status = false;
            }
        }
        return status;
    }


	public IGenVehicleMasterService getVehicleMasterService() {
		return vehicleMasterService;
	}


	public void setVehicleMasterService(IGenVehicleMasterService vehicleMasterService) {
		this.vehicleMasterService = vehicleMasterService;
	}


	public GenVehicleMasterDTO getVehicleMasterDTO() {
		return vehicleMasterDTO;
	}


	public void setVehicleMasterDTO(GenVehicleMasterDTO vehicleMasterDTO) {
		this.vehicleMasterDTO = vehicleMasterDTO;
	}


	public GenVehicleMasterDetDTO getVehicleMasterDetDTO() {
		return vehicleMasterDetDTO;
	}


	public void setVehicleMasterDetDTO(GenVehicleMasterDetDTO vehicleMasterDetDTO) {
		this.vehicleMasterDetDTO = vehicleMasterDetDTO;
	}


	public List<ContractMappingDTO> getContractlist() {
		return contractlist;
	}


	public void setContractlist(List<ContractMappingDTO> contractlist) {
		this.contractlist = contractlist;
	}


	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}


	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}


	public List<GenVehicleMasterDetDTO> getVehicleMasterdetList() {
		return vehicleMasterdetList;
	}


	public void setVehicleMasterdetList(List<GenVehicleMasterDetDTO> vehicleMasterdetList) {
		this.vehicleMasterdetList = vehicleMasterdetList;
	}


	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}


	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}


	public String getSaveMode() {
		return saveMode;
	}


	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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

}
