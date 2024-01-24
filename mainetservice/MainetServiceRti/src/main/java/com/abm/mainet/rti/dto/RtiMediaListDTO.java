package com.abm.mainet.rti.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class RtiMediaListDTO implements Serializable {

    private static final long serialVersionUID = 2572164722660120107L;
    private Long mediaSerialNo;
    private int mediaType;
    private Long quantity;
    private String mediaDesc;
    private List<DocumentDetailsVO> documentList;
    private String mediaTypeDesc;
    private Double mediaAmount;
    private String mediastatus;

    public Long getMediaSerialNo() {
        return mediaSerialNo;
    }

    public void setMediaSerialNo(Long mediaSerialNo) {
        this.mediaSerialNo = mediaSerialNo;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getMediaTypeDesc() {
        return mediaTypeDesc;
    }

    public void setMediaTypeDesc(String mediaTypeDesc) {
        this.mediaTypeDesc = mediaTypeDesc;
    }

    public String getMediaDesc() {
        return mediaDesc;
    }

    public void setMediaDesc(String mediaDesc) {
        this.mediaDesc = mediaDesc;
    }

    public Double getMediaAmount() {
        return mediaAmount;
    }

    public void setMediaAmount(Double mediaAmount) {
        this.mediaAmount = mediaAmount;
    }

    public String getMediastatus() {
        return mediastatus;
    }

    public void setMediastatus(String mediastatus) {
        this.mediastatus = mediastatus;
    }

}
