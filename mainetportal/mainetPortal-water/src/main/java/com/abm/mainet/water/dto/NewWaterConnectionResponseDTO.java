package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ResponseDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class NewWaterConnectionResponseDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = -6653209424095584576L;
    private Map<Long, String> mapData;

    private List<PlumberDTO> plumberList = new ArrayList<>();

    private Long applicationNo;
    private Double charges;

    private String errorCode;
    private String errorCause;
    private String errorMsg;
    private List<String> errorList = new ArrayList<>();

    public Map<Long, String> getMapData() {
        return mapData;
    }

    public void setMapData(final Map<Long, String> mapData) {
        this.mapData = mapData;
    }

    public List<PlumberDTO> getPlumberList() {
        return plumberList;
    }

    public void setPlumberList(final List<PlumberDTO> plumberList) {
        this.plumberList = plumberList;
    }

    @Override
    public Long getApplicationNo() {
        return applicationNo;
    }

    @Override
    public void setApplicationNo(final Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(final Double charges) {
        this.charges = charges;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(final String errorCause) {
        this.errorCause = errorCause;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(final List<String> errorList) {
        this.errorList = errorList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("NewWaterConnectionResponseDTO [mapData=");
        builder.append(mapData);
        builder.append(", plumberList=");
        builder.append(plumberList);
        builder.append(", applicationNo=");
        builder.append(applicationNo);
        builder.append(", charges=");
        builder.append(charges);
        builder.append(", errorCode=");
        builder.append(errorCode);
        builder.append(", errorCause=");
        builder.append(errorCause);
        builder.append(", errorMsg=");
        builder.append(errorMsg);
        builder.append(", errorList=");
        builder.append(errorList);
        builder.append("]");
        return builder.toString();
    }

}
