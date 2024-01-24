package com.abm.mainet.swm.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.VehicleMaintenanceMasterDTO;
import com.abm.mainet.swm.service.IVehicleMaintenanceMasterService;
import com.abm.mainet.swm.ui.validator.VehicleMaintenanceMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleMaintenanceMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1340684254502225688L;

    @Autowired
    private IVehicleMaintenanceMasterService vehicleMaintenanceMasterService;

    private VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO = new VehicleMaintenanceMasterDTO();

    private List<VehicleMaintenanceMasterDTO> vehicleMaintenanceList;

    private String saveMode;

    @Override
    public boolean saveForm() {

        boolean status = false;
        validateBean(vehicleMaintenanceMasterDTO, VehicleMaintenanceMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleMaintenanceMasterDTO.getVeMeId() == null) {
                vehicleMaintenanceMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleMaintenanceMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMaintenanceMasterDTO.setCreatedDate(new Date());
                vehicleMaintenanceMasterDTO.setLgIpMac(Utility.getMacAddress());
                vehicleMaintenanceMasterDTO.setVeMeActive(MainetConstants.FlagY);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.add.success"));

            } else {
                vehicleMaintenanceMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMaintenanceMasterDTO.setUpdatedDate(new Date());
                vehicleMaintenanceMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.edit.success"));
            }
            status = vehicleMaintenanceMasterService.validateVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
            if (status) {
                vehicleMaintenanceMasterService.saveVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);

                status = true;
            } else {
                addValidationError(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.exists"));
                status = false;
            }

        }
        return status;
    }

    public List<VehicleMaintenanceMasterDTO> getVehicleMaintenanceList() {
        return vehicleMaintenanceList;
    }

    public void setVehicleMaintenanceList(List<VehicleMaintenanceMasterDTO> vehicleMaintenanceList) {
        this.vehicleMaintenanceList = vehicleMaintenanceList;
    }

    public IVehicleMaintenanceMasterService getVehicleMaintenanceMasterService() {
        return vehicleMaintenanceMasterService;
    }

    public void setVehicleMaintenanceMasterService(IVehicleMaintenanceMasterService vehicleMaintenanceMasterService) {
        this.vehicleMaintenanceMasterService = vehicleMaintenanceMasterService;
    }

    public VehicleMaintenanceMasterDTO getVehicleMaintenanceMasterDTO() {
        return vehicleMaintenanceMasterDTO;
    }

    public void setVehicleMaintenanceMasterDTO(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
        this.vehicleMaintenanceMasterDTO = vehicleMaintenanceMasterDTO;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

}
