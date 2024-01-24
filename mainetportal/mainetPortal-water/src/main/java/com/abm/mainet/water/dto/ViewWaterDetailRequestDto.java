package com.abm.mainet.water.dto;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
/**
 * @author Bhagyashri.dongardive
 *
 */
public class ViewWaterDetailRequestDto {
	
	private static final long serialVersionUID = 1L;
	
	private WaterDataEntrySearchDTO searchDto;
	
	private PagingDTO pagingDTO;
	   
    private GridSearchDTO gridSearchDTO;

	public WaterDataEntrySearchDTO getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(WaterDataEntrySearchDTO searchDto) {
		this.searchDto = searchDto;
	}

	public PagingDTO getPagingDTO() {
		return pagingDTO;
	}

	public void setPagingDTO(PagingDTO pagingDTO) {
		this.pagingDTO = pagingDTO;
	}

	public GridSearchDTO getGridSearchDTO() {
		return gridSearchDTO;
	}

	public void setGridSearchDTO(GridSearchDTO gridSearchDTO) {
		this.gridSearchDTO = gridSearchDTO;
	}
    
    
	
}
