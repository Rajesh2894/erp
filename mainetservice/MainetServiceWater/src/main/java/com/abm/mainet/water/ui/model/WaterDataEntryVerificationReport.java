package com.abm.mainet.water.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

@Component
@Scope("session")
public class WaterDataEntryVerificationReport extends AbstractFormModel {
    private static final long serialVersionUID = 1L;

    private TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();

    public TbCsmrInfoDTO getCsmrInfoDTO() {
        return csmrInfoDTO;
    }

    public void setCsmrInfoDTO(TbCsmrInfoDTO csmrInfoDTO) {
        this.csmrInfoDTO = csmrInfoDTO;
    }

}
