package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseReferenceAndLegalOpinionDTO implements Serializable {

    private static final long serialVersionUID = 4938281675903771382L;

    private List<LegalOpinionDTO> legalOpinion = new ArrayList<>();

    public List<LegalOpinionDTO> getLegalOpinion() {
        return legalOpinion;
    }

    public void setLegalOpinion(List<LegalOpinionDTO> legalOpinion) {
        this.legalOpinion = legalOpinion;
    }

    public List<LegalReferenceDTO> getLegalReference() {
        return legalReference;
    }

    public void setLegalReference(List<LegalReferenceDTO> legalReference) {
        this.legalReference = legalReference;
    }

    private List<LegalReferenceDTO> legalReference = new ArrayList<>();

}
