package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.util.LookUp;

public class OrganisationListResDTO implements Serializable {

    private static final long serialVersionUID = -2767246432598772915L;

    private List<LookUp> lookUpList = new ArrayList<>();
    private String status;

    public List<LookUp> getLookUpList() {
        return lookUpList;
    }

    public void setLookUpList(final List<LookUp> lookUpList) {
        this.lookUpList = lookUpList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

}
