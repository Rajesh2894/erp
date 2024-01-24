package com.abm.mainet.bnd.dto;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
/**
 * @author Bhagyashri.dongardive
 *
 */
public class ViewDeathCertiDetailRequestDto {
	
	private static final long serialVersionUID = 1L;
	
	private DeathCertificateDTO  deathSearchDto;
	
	private PagingDTO pagingDTO;
	   
    private GridSearchDTO gridSearchDTO;

	



	public DeathCertificateDTO getDeathSearchDto() {
		return deathSearchDto;
	}

	public void setDeathSearchDto(DeathCertificateDTO deathSearchDto) {
		this.deathSearchDto = deathSearchDto;
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
