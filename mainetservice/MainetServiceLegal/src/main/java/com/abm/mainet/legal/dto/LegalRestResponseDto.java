package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LegalRestResponseDto implements Serializable {

    private static final long serialVersionUID = 8136749277045023610L;

    private List<CaseEntryDTO> caseEntryDtoList = new ArrayList<>();

    public List<CaseEntryDTO> getCaseEntryDtoList() {
        return caseEntryDtoList;
    }

    public void setCaseEntryDtoList(List<CaseEntryDTO> caseEntryDtoList) {
        this.caseEntryDtoList = caseEntryDtoList;
    }

}
