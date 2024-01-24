package com.abm.mainet.property.dto;

public class BlockChainResponseDto {
    
    private String returnCode;
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getTxid() {
        return txid;
    }
    public void setTxid(String txid) {
        this.txid = txid;
    }
    public BlockChainPayloadDto getResult() {
        return result;
    }
    public void setResult(BlockChainPayloadDto result) {
        this.result = result;
    }
    private String txid;
    private BlockChainPayloadDto result;

}
