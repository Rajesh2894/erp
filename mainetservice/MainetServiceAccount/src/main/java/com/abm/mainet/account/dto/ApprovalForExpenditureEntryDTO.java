package com.abm.mainet.account.dto;

public class ApprovalForExpenditureEntryDTO {

    private Long prApprForExpid;

    private String hasError;

    private String alreadyExists = "N";

    // the bellow area as generated setters & getters

    public Long getPrApprForExpid() {
        return prApprForExpid;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public void setPrApprForExpid(final Long prApprForExpid) {
        this.prApprForExpid = prApprForExpid;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

}
