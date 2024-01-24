package com.abm.mainet.swm.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;

@Component
@Scope("session")
public class AllFineLogBookReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 6121201543620755461L;

    private FineChargeCollectionDTO fineChargeCollectionDTO = new FineChargeCollectionDTO();
    private FineChargeCollectionDTO fineChargeList = new FineChargeCollectionDTO();

    public FineChargeCollectionDTO getFineChargeCollectionDTO() {
        return fineChargeCollectionDTO;
    }

    public void setFineChargeCollectionDTO(FineChargeCollectionDTO fineChargeCollectionDTO) {
        this.fineChargeCollectionDTO = fineChargeCollectionDTO;
    }

    public FineChargeCollectionDTO getFineChargeList() {
        return fineChargeList;
    }

    public void setFineChargeList(FineChargeCollectionDTO fineChargeList) {
        this.fineChargeList = fineChargeList;
    }

}
