package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.ActRuleMasterDTO;

@Component
@Scope("session")
public class ActAndRuleMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private ActRuleMasterDTO actRuleMasterDTO;

    private List<ActAndRuleMasterModel> addendumInfo = new ArrayList<>();

    public ActRuleMasterDTO getActRuleMasterDTO() {
        return actRuleMasterDTO;
    }

    public void setActRuleMasterDTO(ActRuleMasterDTO actRuleMasterDTO) {
        this.actRuleMasterDTO = actRuleMasterDTO;
    }

    public List<ActAndRuleMasterModel> getAddendumInfo() {
        return addendumInfo;
    }

    public void setAddendumInfo(List<ActAndRuleMasterModel> addendumInfo) {
        this.addendumInfo = addendumInfo;
    }

}
