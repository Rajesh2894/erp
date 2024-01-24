package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WastageSegregationDTO;

@Component
@Scope("session")
public class SLRMReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 8940426123575743301L;
    private List<MRFMasterDto> mrfMasterList = new ArrayList<>();

    private WastageSegregationDTO wastageSegregationDTO = new WastageSegregationDTO();

    public List<MRFMasterDto> getMrfMasterList() {
        return mrfMasterList;
    }

    public void setMrfMasterList(List<MRFMasterDto> mrfMasterList) {
        this.mrfMasterList = mrfMasterList;
    }

    public WastageSegregationDTO getWastageSegregationDTO() {
        return wastageSegregationDTO;
    }

    public void setWastageSegregationDTO(WastageSegregationDTO wastageSegregationDTO) {
        this.wastageSegregationDTO = wastageSegregationDTO;
    }

}
