/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Component
@Scope("session")
public class AdvertisementDetailsModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private AdvertiserMasterDto advertiserMasterDto = new AdvertiserMasterDto();
    private HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();
    private List<AdvertiserMasterDto> advertiserMasterDtoList = new ArrayList<>();
    private List<TbLocationMas> locationList = new ArrayList<>();
    private Map<Long, String> listOfinalcialyear = null;

    public Map<Long, String> getListOfinalcialyear() {
        return listOfinalcialyear;
    }

    public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
        this.listOfinalcialyear = listOfinalcialyear;
    }

    public AdvertiserMasterDto getAdvertiserMasterDto() {
        return advertiserMasterDto;
    }

    public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
        this.advertiserMasterDto = advertiserMasterDto;
    }

    public HoardingMasterDto getHoardingMasterDto() {
        return hoardingMasterDto;
    }

    public void setHoardingMasterDto(HoardingMasterDto hoardingMasterDto) {
        this.hoardingMasterDto = hoardingMasterDto;
    }

    public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
        return advertiserMasterDtoList;
    }

    public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
        this.advertiserMasterDtoList = advertiserMasterDtoList;
    }

    public List<TbLocationMas> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<TbLocationMas> locationList) {
        this.locationList = locationList;
    }

}
