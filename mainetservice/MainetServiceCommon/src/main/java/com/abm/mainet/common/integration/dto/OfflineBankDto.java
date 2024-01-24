package com.abm.mainet.common.integration.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OfflineBankDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7510017748121557772L;

    Map<Long, String> bankData = new HashMap<>(0);
    long orgId;
    String status;

    public Map<Long, String> getBankData() {
        return bankData;
    }

    public void setBankData(final Map<Long, String> bankData) {
        this.bankData = bankData;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
