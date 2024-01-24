package com.abm.mainet.care.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

public class ActionResponseDTO {

    private String response;

    private List<ObjectError> errorList;

    private String error;

    private Map<String, String> responseData;

    private List<? extends Object> dataList;

    private Date actionDate;

    public ActionResponseDTO() {
        this.responseData = new HashMap<String, String>();
        this.dataList = new ArrayList<Object>();
        this.errorList = new ArrayList<ObjectError>();
    }

    public ActionResponseDTO(String response) {
        this.response = response;
        this.responseData = new HashMap<String, String>();
        this.errorList = new ArrayList<ObjectError>();
    }

    public Map<String, String> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, String> responseData) {
        this.responseData = responseData;
    }

    public void addResponseData(String key, String data) {
        responseData.put(key, data);
    }

    public String getResponseData(String key) {
        return responseData.get(key);
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ObjectError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ObjectError> errorList) {
        this.errorList = errorList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<? extends Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<? extends Object> dataList) {
        this.dataList = dataList;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

}
