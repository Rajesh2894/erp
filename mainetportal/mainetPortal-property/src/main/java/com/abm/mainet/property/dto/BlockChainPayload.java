package com.abm.mainet.property.dto;

import java.util.Date;

public class BlockChainPayload {
    private String TxId;
    private BlockChainPayloadDtl Value;
    public String getTxId() {
        return TxId;
    }
    public void setTxId(String txId) {
        TxId = txId;
    }
    public BlockChainPayloadDtl getValue() {
        return Value;
    }
    public void setValue(BlockChainPayloadDtl value) {
        Value = value;
    }
    public Date getTimestamp() {
        return Timestamp;
    }
    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
    public boolean isIsDelete() {
        return IsDelete;
    }
    public void setIsDelete(boolean isDelete) {
        IsDelete = isDelete;
    }
    private Date Timestamp;
    private boolean IsDelete;
}
