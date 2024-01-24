package com.abm.mainet.swm.ui.model;

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
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.PumpFuelDetailsDTO;
import com.abm.mainet.swm.dto.PumpMasterDTO;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IVehicleFuellingService;
import com.abm.mainet.swm.ui.validator.VehicleFuellingValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleFuellingModel extends AbstractFormModel {

    private static final long serialVersionUID = -2710014942516062984L;

    @Autowired
    private IVehicleFuellingService vehicleFuellingService;

    VehicleFuellingDTO vehicleFuellingDTO = new VehicleFuellingDTO();
    private VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    private List<PumpMasterDTO> pumps;

    List<TbAcVendormaster> vendorList;
    private List<VehicleFuellingDTO> vehicleFuellingList;

    private List<PumpFuelDetailsDTO> pumpFuelDetails;

    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    @Autowired
    IFileUploadService fileUpload;

    private List<AttachDocs> attachDocsList = new ArrayList<>();
    
    private String removeAttachedDocIds;

    @Autowired
    private VehicleFuellingValidator vehicleFuellingValidator;

    private String saveMode;

    private List<LookUp> lookUps = new ArrayList<>();

    private List<DocumentDetailsVO> documents = new ArrayList<>();

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

    public String getRemoveAttachedDocIds() {
		return removeAttachedDocIds;
	}

	public void setRemoveAttachedDocIds(String removeAttachedDocIds) {
		this.removeAttachedDocIds = removeAttachedDocIds;
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

    public List<PumpMasterDTO> getPumps() {
        return pumps;
    }

    public void setPumps(List<PumpMasterDTO> pumps) {
        this.pumps = pumps;
    }

    @Override
    public boolean saveForm() {
        boolean status = false;
        validateBean(vehicleFuellingDTO, VehicleFuellingValidator.class);
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

            vehicleFuellingDTO = vehicleFuellingService.saveVehicle(vehicleFuellingDTO);

            FileUploadDTO requestDTO = new FileUploadDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus(MainetConstants.FlagA);
            requestDTO.setIdfId(vehicleFuellingDTO.getVefId().toString());
            requestDTO.setDepartmentName(MainetConstants.SolidWasteManagement.SHORT_CODE);
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            List<DocumentDetailsVO> dto = getDocuments();
            setDocuments(fileUpload.setFileUploadMethod(getDocuments()));

            setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
            fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
            
            List<Long> removeDocIdList = getRemoveDocIds();
            if (removeDocIdList != null && !removeDocIdList.isEmpty()) {
                ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class).deleteDocFileById(
                		removeDocIdList,
                        UserSession.getCurrent().getEmployee().getEmpId());
            }
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
    
    private List<Long> getRemoveDocIds() {
        String docsIdStr = getRemoveAttachedDocIds();
        List<Long> removeDocIdList = null;
        if (docsIdStr != null && !docsIdStr.isEmpty()) {
        	removeDocIdList = new ArrayList<>();
            String docIds[] = docsIdStr.split(MainetConstants.operator.COMMA);
            for (String docId : docIds) {
            	removeDocIdList.add(Long.valueOf(docId));
            }
        }
        return removeDocIdList;
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

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
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

    public VehicleFuellingValidator getVehicleFuellingValidator() {
        return vehicleFuellingValidator;
    }

    public void setVehicleFuellingValidator(VehicleFuellingValidator vehicleFuellingValidator) {
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

}
