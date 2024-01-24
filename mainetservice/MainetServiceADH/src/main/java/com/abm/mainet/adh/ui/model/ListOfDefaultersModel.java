/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Component
@Scope("session")
public class ListOfDefaultersModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();

    public HoardingMasterDto getHoardingMasterDto() {
        return hoardingMasterDto;
    }

    public void setHoardingMasterDto(HoardingMasterDto hoardingMasterDto) {
        this.hoardingMasterDto = hoardingMasterDto;
    }
}
