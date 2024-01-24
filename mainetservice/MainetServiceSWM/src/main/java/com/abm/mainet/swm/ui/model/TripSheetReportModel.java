package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.TripSheetDTO;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class TripSheetReportModel extends AbstractFormModel {

    private static final long serialVersionUID = 7894977071246848930L;

    VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();
    List<VehicleMasterDTO> vehicleMasterList;

    private List<VehicleMaintenanceDTO> vehicleMaintenanceList;
    
    private List<String> vendorNameList=new ArrayList<String>();
    private List<String> contractNoList=new ArrayList<String>();

    TripSheetDTO tripSheetDTOList;

    private String saveMode;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
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

    public List<VehicleMaintenanceDTO> getVehicleMaintenanceList() {
        return vehicleMaintenanceList;
    }

    public void setVehicleMaintenanceList(List<VehicleMaintenanceDTO> vehicleMaintenanceList) {
        this.vehicleMaintenanceList = vehicleMaintenanceList;
    }

    public TripSheetDTO getTripSheetDTOList() {
        return tripSheetDTOList;
    }

    public void setTripSheetDTOList(TripSheetDTO tripSheetDTOList) {
        this.tripSheetDTOList = tripSheetDTOList;
    }

	public List<String> getVendorNameList() {
		return vendorNameList;
	}

	public void setVendorNameList(List<String> vendorNameList) {
		this.vendorNameList = vendorNameList;
	}

	public List<String> getContractNoList() {
		return contractNoList;
	}

	public void setContractNoList(List<String> contractNoList) {
		this.contractNoList = contractNoList;
	}

}
