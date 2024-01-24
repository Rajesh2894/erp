package com.abm.mainet.property.dto;


public class BlockChainPayloadDto {
    private String payload;
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public String getEncode() {
        return encode;
    }
    public void setEncode(String encode) {
        this.encode = encode;
    }
    private String encode;
}
