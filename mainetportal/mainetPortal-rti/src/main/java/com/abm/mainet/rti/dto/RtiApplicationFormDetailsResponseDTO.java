package com.abm.mainet.rti.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RtiApplicationFormDetailsResponseDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7754055168607533188L;
    private Map<Long, String> mapData;
    private Long applicationNo;
    private Double charges;
    private String errorCode;
    private String errorCause;
    private String errorMsg;
    private List<String> errorList = new ArrayList<>();

    public Map<Long, String> getMapData() {
        return mapData;
    }

    public void setMapData(Map<Long, String> mapData) {
        this.mapData = mapData;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

}
