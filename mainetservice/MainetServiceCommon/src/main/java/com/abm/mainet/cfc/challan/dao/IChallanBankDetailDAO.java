package com.abm.mainet.cfc.challan.dao;

import java.util.Map;

import com.abm.mainet.common.domain.Organisation;

public interface IChallanBankDetailDAO {
    public Map<Long, String> getBankList(Organisation organisation);
}
