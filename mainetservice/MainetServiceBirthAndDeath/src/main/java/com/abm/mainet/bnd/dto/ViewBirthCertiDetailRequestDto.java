package com.abm.mainet.bnd.dto;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;

public class ViewBirthCertiDetailRequestDto {
	
private static final long serialVersionUID = 1L;
	
	private BirthCertificateDTO birthSearchDto;
	
	private PagingDTO pagingDTO;
	   
    private GridSearchDTO gridSearchDTO;

	public BirthCertificateDTO getBirthSearchDto() {
		return birthSearchDto;
	}

	public void setBirthSearchDto(BirthCertificateDTO birthSearchDto) {
		this.birthSearchDto = birthSearchDto;
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
