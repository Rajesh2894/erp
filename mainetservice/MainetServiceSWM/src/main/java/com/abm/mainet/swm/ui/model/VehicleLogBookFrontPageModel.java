package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.vehicleLogBookDTO;

@Component
@Scope("session")
public class VehicleLogBookFrontPageModel extends AbstractFormModel{

 
    private static final long serialVersionUID = 1L;
    
    private List<MRFMasterDto> mrfMasterList = new ArrayList<>();
    MRFMasterDto MRFMasterDto;
    List<Object[]> vehicleNo;
    List<Object[]> beatCount;
    List<Object[]> mrfdetails;
    List<Object[]> wardCount;    
    vehicleLogBookDTO vehicleLogBookDTO = new vehicleLogBookDTO();

    public List<MRFMasterDto> getMrfMasterList() {
        return mrfMasterList;
    }

    public void setMrfMasterList(List<MRFMasterDto> mrfMasterList) {
        this.mrfMasterList = mrfMasterList;
    }

    public MRFMasterDto getMRFMasterDto() {
        return MRFMasterDto;
    }

    public void setMRFMasterDto(MRFMasterDto mRFMasterDto) {
        MRFMasterDto = mRFMasterDto;
    }

    public vehicleLogBookDTO getVehicleLogBookDTO() {
        return vehicleLogBookDTO;
    }

    public void setVehicleLogBookDTO(vehicleLogBookDTO vehicleLogBookDTO) {
        this.vehicleLogBookDTO = vehicleLogBookDTO;
    }

    public List<Object[]> getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(List<Object[]> vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public List<Object[]> getBeatCount() {
        return beatCount;
    }

    public void setBeatCount(List<Object[]> beatCount) {
        this.beatCount = beatCount;
    }

    public List<Object[]> getMrfdetails() {
        return mrfdetails;
    }

    public void setMrfdetails(List<Object[]> mrfdetails) {
        this.mrfdetails = mrfdetails;
    }

    public List<Object[]> getWardCount() {
        return wardCount;
    }

    public void setWardCount(List<Object[]> wardCount) {
        this.wardCount = wardCount;
    }

}
