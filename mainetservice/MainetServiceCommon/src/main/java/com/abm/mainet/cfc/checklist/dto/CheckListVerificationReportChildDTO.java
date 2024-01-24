package com.abm.mainet.cfc.checklist.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.utility.LookUp;

public class CheckListVerificationReportChildDTO implements Serializable {

    private static final long serialVersionUID = -8712080396238783657L;

    private String tSrNo;

    private String tDocName;

    private String tObsDetail;

    private List<LookUp> lookUps;

    public String gettDocName() {
        return tDocName;
    }

    public void settDocName(final String tDocName) {
        this.tDocName = tDocName;
    }

    public String gettObsDetail() {
        return tObsDetail;
    }

    public void settObsDetail(final String tObsDetail) {
        this.tObsDetail = tObsDetail;
    }

    public String gettSrNo() {
        return tSrNo;
    }

    public void settSrNo(final String tSrNo) {
        this.tSrNo = tSrNo;
    }

    public List<LookUp> getLookUps() {
        return lookUps;
    }

    public void setLookUps(final List<LookUp> lookUps) {
        this.lookUps = lookUps;
    }

}
