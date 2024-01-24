package com.abm.mainet.integration.ws.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.ChargeDetailDTO;

/**
 * super class DTO made for WS call, to hold Web Service call result whether WS success or fail, this class must be inherit by
 * every module specific sub class Response DTO.
 * @author Vivek.Kumar
 * @since 26-Feb-2016
 */
public class WSResponseDTO implements Serializable {

    private static final long serialVersionUID = 5356393443659148061L;

    /**
     * below fields are being used for BRMS response Ex - Applicable Document group will be set to {documentGroup} field
     * applicable all charges for Service will be set to {chargeList} field water consumption will be set to {waterConsumption}
     * field no. of days will be set to {noOfDays} field water Tax will be set to {waterTax} field water rates will be set to
     * {waterRateMaster} field
     */
    private String documentGroup;
    private Object responseObj;
    private boolean free;
    private List<ChargeDetailDTO> charges;
    // field, to identify whether WS Request fail or success
    private String wsStatus;

    // field ,in case if any problem occurred during Web Service call
    private String errorMessage;

    // list of result for checkListgroup/applicable charges
    private List<String> ruleResults;

    public String getWsStatus() {
        return wsStatus;
    }

    public void setWsStatus(final String wsStatus) {
        this.wsStatus = wsStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getRuleResults() {
        return ruleResults;
    }

    public void setRuleResults(final List<String> ruleResults) {
        this.ruleResults = ruleResults;
    }

    public String getDocumentGroup() {
        return documentGroup;
    }

    public void setDocumentGroup(final String documentGroup) {
        this.documentGroup = documentGroup;
    }

    public Object getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(final Object responseObj) {
        this.responseObj = responseObj;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(final boolean free) {
        this.free = free;
    }

    public List<ChargeDetailDTO> getCharges() {
        return charges;
    }

    public void setCharges(final List<ChargeDetailDTO> charges) {
        this.charges = charges;
    }

}
