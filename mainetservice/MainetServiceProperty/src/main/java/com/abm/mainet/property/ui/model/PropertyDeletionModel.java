package com.abm.mainet.property.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class PropertyDeletionModel extends AbstractFormModel

{

    private static final long serialVersionUID = 1L;

    private ProperySearchDto searchDto = new ProperySearchDto();

    @Override
    protected void initializeModel() {

    }

    public ProperySearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(ProperySearchDto searchDto) {
        this.searchDto = searchDto;
    }

}
