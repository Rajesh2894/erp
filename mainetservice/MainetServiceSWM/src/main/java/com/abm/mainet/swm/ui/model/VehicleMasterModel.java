package com.abm.mainet.swm.ui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDetDTO;
import com.abm.mainet.swm.service.IVehicleMasterService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -5231739886203518846L;
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();
    VehicleMasterDetDTO vehicleMasterDetDTO = new VehicleMasterDetDTO();
    List<ContractMappingDTO> contractlist;
    List<VehicleMasterDTO> vehicleMasterList;
    List<VehicleMasterDetDTO> vehicleMasterdetList;
    List<TbAcVendormaster> vendorList;
    private String saveMode;

    @Override
    public boolean saveForm() {
        BigDecimal total = new BigDecimal(10.00);
        boolean status = true;
        if (hasValidationErrors()) {
            return false;
        } else {
            if (vehicleMasterDTO.getVeId() == null) {
                vehicleMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                vehicleMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMasterDTO.setCreatedDate(new Date());
                vehicleMasterDTO.setLgIpMac(Utility.getMacAddress());
                vehicleMasterDTO.setVeActive(MainetConstants.FlagY);
                vehicleMasterDTO.setVeCapacity(total);
            } else {
                vehicleMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                vehicleMasterDTO.setUpdatedDate(new Date());
                vehicleMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
            }
            for (VehicleMasterDetDTO vehicleMasterdet : vehicleMasterDTO.getTbSwVehicleMasterdets()) {
                if (vehicleMasterdet.getVedId() == null) {
                    vehicleMasterdet.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    vehicleMasterdet.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    vehicleMasterdet.setCreatedDate(new Date());
                    vehicleMasterdet.setLgIpMac(Utility.getMacAddress());
                    vehicleMasterdet.setVeActive(MainetConstants.FlagY);
                    vehicleMasterdet.setTbSwVehicleMaster(vehicleMasterDTO);
                } else {
                    vehicleMasterdet.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    vehicleMasterdet.setUpdatedDate(new Date());
                    vehicleMasterdet.setLgIpMacUpd(Utility.getMacAddress());
                    vehicleMasterdet.setTbSwVehicleMaster(vehicleMasterDTO);
                }
            }            
            if (status) {
                if (vehicleMasterDTO.getVeId() == null) {
                    vehicleMasterService.saveVehicle(vehicleMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMasterDTO.save.add"));
                } else {
                    vehicleMasterService.saveVehicle(vehicleMasterDTO);
                    setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMasterDTO.save.edit"));
                }
                status = true;
            } else {
                addValidationError(ApplicationSession.getInstance().getMessage("vehicle.master.validation.regNo"));
                status = false;
            }
        }
        return status;
    }

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public List<ContractMappingDTO> getContractlist() {
        return contractlist;
    }

    public void setContractlist(List<ContractMappingDTO> contractlist) {
        this.contractlist = contractlist;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    public List<VehicleMasterDetDTO> getVehicleMasterdetList() {
        return vehicleMasterdetList;
    }

    public void setVehicleMasterdetList(List<VehicleMasterDetDTO> vehicleMasterdetList) {
        this.vehicleMasterdetList = vehicleMasterdetList;
    }

    public VehicleMasterDetDTO getVehicleMasterDetDTO() {
        return vehicleMasterDetDTO;
    }

    public void setVehicleMasterDetDTO(VehicleMasterDetDTO vehicleMasterDetDTO) {
        this.vehicleMasterDetDTO = vehicleMasterDetDTO;
    }

}
