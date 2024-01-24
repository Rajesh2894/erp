/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.List;

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
public class HoardingRegisterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private HoardingMasterDto hoardingMasterDto;
    private List<HoardingMasterDto> hoardingMasterDtoList = new ArrayList<>();

    public HoardingMasterDto getHoardingMasterDto() {
        return hoardingMasterDto;
    }

    public void setHoardingMasterDto(HoardingMasterDto hoardingMasterDto) {
        this.hoardingMasterDto = hoardingMasterDto;
    }

    public List<HoardingMasterDto> getHoardingMasterDtoList() {
        return hoardingMasterDtoList;
    }

    public void setHoardingMasterDtoList(List<HoardingMasterDto> hoardingMasterDtoList) {
        this.hoardingMasterDtoList = hoardingMasterDtoList;
    }

}
