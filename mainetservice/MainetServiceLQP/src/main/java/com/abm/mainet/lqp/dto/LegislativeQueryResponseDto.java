package com.abm.mainet.lqp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LegislativeQueryResponseDto implements Serializable {

    private static final long serialVersionUID = -1058114763873073475L;

    private List<QueryRegistrationMasterDto> queryRegMasrDtoList = new ArrayList<>();

    public List<QueryRegistrationMasterDto> getQueryRegMasrDtoList() {
        return queryRegMasrDtoList;
    }

    public void setQueryRegMasrDtoList(List<QueryRegistrationMasterDto> queryRegMasrDtoList) {
        this.queryRegMasrDtoList = queryRegMasrDtoList;
    }

}
