package com.abm.mainet.property.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.MutationIntimationDto;

@Component
@Scope("session")
public class MutationIntimationModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -187204405947626950L;

    MutationIntimationDto mutationIntimationDto = new MutationIntimationDto();

    MutationIntimationDto mutationIntimationViewDto = new MutationIntimationDto();

    public MutationIntimationDto getMutationIntimationDto() {
        return mutationIntimationDto;
    }

    public void setMutationIntimationDto(MutationIntimationDto mutationIntimationDto) {
        this.mutationIntimationDto = mutationIntimationDto;
    }

    public MutationIntimationDto getMutationIntimationViewDto() {
        return mutationIntimationViewDto;
    }

    public void setMutationIntimationViewDto(MutationIntimationDto mutationIntimationViewDto) {
        this.mutationIntimationViewDto = mutationIntimationViewDto;
    }

}
