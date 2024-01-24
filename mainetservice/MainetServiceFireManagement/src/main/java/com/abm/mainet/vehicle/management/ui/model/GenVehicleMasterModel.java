/*package com.abm.mainet.vehicle.management.ui.model;

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
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDetDTO;
import com.abm.mainet.vehicle.management.service.IGenVehicleMasterService;

*//**
 * @author Ajay.Kumar
 *
 *//*
@Component
@Scope("session")
public class GenVehicleMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -5231739886203518846L;
    @Autowired
    IGenVehicleMasterService vehicleMasterService;

     GenVehicleMasterDTO vehicleMasterDTO = new GenVehicleMasterDTO();
    GenVehicleMasterDetDTO vehicleMasterDetDTO = new GenVehicleMasterDetDTO();
    List<ContractMappingDTO> contractlist;
    List<GenVehicleMasterDTO> vehicleMasterList;
    List<GenVehicleMasterDetDTO> vehicleMasterdetList;
    List<TbAcVendormaster> vendorList;

    private String saveMode;

    
    @SuppressWarnings("unchecked")
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
    



}
*/