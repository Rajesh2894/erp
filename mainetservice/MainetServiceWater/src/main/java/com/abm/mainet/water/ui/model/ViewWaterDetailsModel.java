package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;

/**
 * @author cherupelli.srikanth
 *@since 29 July 2020
 */

@Component
@Scope("session")
public class ViewWaterDetailsModel extends AbstractFormModel{

	private static final long serialVersionUID = -2460351430110109099L;
	
	private WaterDataEntrySearchDTO entrySearchDto = new WaterDataEntrySearchDTO();

	private List<WaterDataEntrySearchDTO> entrySearchDtoList = new ArrayList<>();

	private ViewCsmrConnectionDTO viewConnectionDto = new ViewCsmrConnectionDTO();
	
	private List<LookUp> collectionDetails = new ArrayList<>(0);
	
	private String showForm;
	
	public WaterDataEntrySearchDTO getEntrySearchDto() {
		return entrySearchDto;
	}

	public void setEntrySearchDto(WaterDataEntrySearchDTO entrySearchDto) {
		this.entrySearchDto = entrySearchDto;
	}

	public List<WaterDataEntrySearchDTO> getEntrySearchDtoList() {
		return entrySearchDtoList;
	}

	public void setEntrySearchDtoList(List<WaterDataEntrySearchDTO> entrySearchDtoList) {
		this.entrySearchDtoList = entrySearchDtoList;
	}

	public ViewCsmrConnectionDTO getViewConnectionDto() {
		return viewConnectionDto;
	}

	public void setViewConnectionDto(ViewCsmrConnectionDTO viewConnectionDto) {
		this.viewConnectionDto = viewConnectionDto;
	}
	
	public List<LookUp> getCollectionDetails() {
		return collectionDetails;
	}

	public void setCollectionDetails(List<LookUp> collectionDetails) {
		this.collectionDetails = collectionDetails;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(String showForm) {
		this.showForm = showForm;
	}
	
}
