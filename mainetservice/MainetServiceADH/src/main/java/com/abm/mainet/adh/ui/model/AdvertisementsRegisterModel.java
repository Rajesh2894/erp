/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Component
@Scope("session")
public class AdvertisementsRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	private AdvertiserMasterDto advertiserMasterDto;

	private NewAdvertisementApplicationDto advertisementDto = new NewAdvertisementApplicationDto();

	public AdvertiserMasterDto getAdvertiserMasterDto() {
		return advertiserMasterDto;
	}

	public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
		this.advertiserMasterDto = advertiserMasterDto;
	}

	public NewAdvertisementApplicationDto getAdvertisementDto() {
		return advertisementDto;
	}

	public void setAdvertisementDto(NewAdvertisementApplicationDto advertisementDto) {
		this.advertisementDto = advertisementDto;
	}

}
