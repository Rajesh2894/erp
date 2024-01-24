package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.dto.PumpFuelDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.ui.validator.PumpMasterValidator;

/**
 * @author Niraj.Kumar
 *
 */
@Component
@Scope("session")
public class PumpMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = 4863396193144575039L;

    private PumpMasterDTO pumpMasterDTO = new PumpMasterDTO();
    private List<PumpFuelDetailsDTO> pumpFuelDetailsList;
    private List<PumpMasterDTO> pumpMasterList;
    private String saveMode;
    private List<LookUp> lookUps = new ArrayList<>();
    @Autowired
    private IPumpMasterService pumpMasterService;
    
	private String mode;
    private String removeChildIds;

    @Override
    public boolean saveForm() {
    	List<Long> removeIds = null;
        //boolean status = false;
        validateBean(pumpMasterDTO,PumpMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        if (pumpMasterDTO.getPuId() == null) {
            pumpMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            pumpMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            pumpMasterDTO.setCreatedDate(new Date());
            pumpMasterDTO.setLgIpMac(Utility.getMacAddress());
            pumpMasterDTO.setPuActive(MainetConstants.FlagY);

            setSuccessMessage(ApplicationSession.getInstance().getMessage("fuel.pump.master.add.success"));
        } else {
            pumpMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            pumpMasterDTO.setUpdatedDate(new Date());
            pumpMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
          
            //to remove items sold
			removeIds = new ArrayList<>();
			final String ids = getRemoveChildIds();
			if (!ids.isEmpty()) {
			    final String array[] = ids.split(",");
			    for (final String string : array) {
			        removeIds.add(Long.valueOf(string));
			    }
			}
            setSuccessMessage(ApplicationSession.getInstance().getMessage("fuel.pump.master.edit.success"));
        }
        
        /*
        status = pumpMasterService.validatePumpMaster(pumpMasterDTO);

        if (status) {
            pumpMasterService.savePumpMaster(pumpMasterDTO);
        } else {
            addValidationError(ApplicationSession.getInstance().getMessage("fuel.pump.master.exists"));
        }
        return status;
        */
		for (PumpFuelDetailsDTO pumpMasterDTO : pumpMasterDTO.getTbSwPumpFuldets()) {
        	if(pumpMasterDTO.getPuActive() == null)
        		pumpMasterDTO.setPuActive("N");
        }
        
        pumpMasterService.savePumpMaster(pumpMasterDTO, removeIds);

		return true;
    }

    public IPumpMasterService getPumpMasterService() {
        return pumpMasterService;
    }

    public void setPumpMasterService(IPumpMasterService pumpMasterService) {
        this.pumpMasterService = pumpMasterService;
    }

    public List<PumpFuelDetailsDTO> getPumpFuelDetailsList() {
        return pumpFuelDetailsList;
    }

    public void setPumpFuelDetailsList(List<PumpFuelDetailsDTO> pumpFuelDetailsList) {
        this.pumpFuelDetailsList = pumpFuelDetailsList;
    }

    public PumpMasterDTO getPumpMasterDTO() {
        return pumpMasterDTO;
    }

    public void setPumpMasterDTO(PumpMasterDTO pumpMasterDTO) {
        this.pumpMasterDTO = pumpMasterDTO;
    }

    public List<PumpMasterDTO> getPumpMasterList() {
        return pumpMasterList;
    }

    public void setPumpMasterList(List<PumpMasterDTO> pumpMasterList) {
        this.pumpMasterList = pumpMasterList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<LookUp> getLookUps() {
        return lookUps;
    }

    public void setLookUps(List<LookUp> lookUps) {
        this.lookUps = lookUps;
    }
    
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRemoveChildIds() {
		return removeChildIds;
	}

	public void setRemoveChildIds(String removeChildIds) {
		this.removeChildIds = removeChildIds;
	}
	

}
