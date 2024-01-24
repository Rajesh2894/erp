package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WastageSegregationDTO;
import com.abm.mainet.swm.dto.WastageSegregationDetailsDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class WasteWiseSegregationModel extends AbstractFormModel {
    private static final long serialVersionUID = 1162796817137475965L;

    private List<MRFMasterDto> mRFMasterDtoList = new ArrayList<>();

    private List<WastageSegregationDTO> wastageSegregationList = new ArrayList<>();

    WastageSegregationDTO wastageSegregationDto;

    private WastageSegregationDTO wastageSeg;

    private WastageSegregationDetailsDTO wastageSegregationDet = new WastageSegregationDetailsDTO();

    List<WastageSegregationDetailsDTO> wastageSegregationDetList;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public List<MRFMasterDto> getmRFMasterDtoList() {
        return mRFMasterDtoList;
    }

    public void setmRFMasterDtoList(List<MRFMasterDto> mRFMasterDtoList) {
        this.mRFMasterDtoList = mRFMasterDtoList;
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        return MainetConstants.SOLID_WATSE_FLOWTYPE.CONTRACT_MAPPING_WASTE_LEVEL;
    }

    public List<WastageSegregationDTO> getWastageSegregationList() {
        return wastageSegregationList;
    }

    public void setWastageSegregationList(List<WastageSegregationDTO> wastageSegregationList) {
        this.wastageSegregationList = wastageSegregationList;
    }

    public WastageSegregationDTO getWastageSegregationDto() {
        return wastageSegregationDto;
    }

    public void setWastageSegregationDto(WastageSegregationDTO wastageSegregationDto) {
        this.wastageSegregationDto = wastageSegregationDto;
    }

    public WastageSegregationDetailsDTO getWastageSegregationDet() {
        return wastageSegregationDet;
    }

    public void setWastageSegregationDet(WastageSegregationDetailsDTO wastageSegregationDet) {
        this.wastageSegregationDet = wastageSegregationDet;
    }

    public List<WastageSegregationDetailsDTO> getWastageSegregationDetList() {
        return wastageSegregationDetList;
    }

    public void setWastageSegregationDetList(List<WastageSegregationDetailsDTO> wastageSegregationDetList) {
        this.wastageSegregationDetList = wastageSegregationDetList;
    }

    public WastageSegregationDTO getWastageSeg() {
        return wastageSeg;
    }

    public void setWastageSeg(WastageSegregationDTO wastageSeg) {
        this.wastageSeg = wastageSeg;
    }

}
