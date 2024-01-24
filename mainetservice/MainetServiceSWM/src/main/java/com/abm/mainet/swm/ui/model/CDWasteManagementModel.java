package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;

@Component
@Scope("session")
public class CDWasteManagementModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8818084950743952130L;

    private VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    private WasteCollectorDTO wasteCollectorDTO = new WasteCollectorDTO();

    private List<MRFMasterDto> mRFMasterDtoList = new ArrayList<>();

    public List<MRFMasterDto> getmRFMasterDtoList() {
        return mRFMasterDtoList;
    }

    public void setmRFMasterDtoList(List<MRFMasterDto> mRFMasterDtoList) {
        this.mRFMasterDtoList = mRFMasterDtoList;
    }

    public WasteCollectorDTO getWasteCollectorDTO() {
        return wasteCollectorDTO;
    }

    public void setWasteCollectorDTO(WasteCollectorDTO wasteCollectorDTO) {
        this.wasteCollectorDTO = wasteCollectorDTO;
    }

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

}
