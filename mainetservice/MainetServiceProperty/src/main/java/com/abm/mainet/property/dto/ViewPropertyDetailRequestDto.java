package com.abm.mainet.property.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;

public class ViewPropertyDetailRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProperySearchDto propSearchDto;

    private PagingDTO pagingDTO;

    private GridSearchDTO gridSearchDTO;

    public ProperySearchDto getPropSearchDto() {
        return propSearchDto;
    }

    public void setPropSearchDto(ProperySearchDto propSearchDto) {
        this.propSearchDto = propSearchDto;
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
