package com.abm.mainet.cfc.challan.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;

@Transactional(readOnly = true)
public interface IChallanBankDetailsService extends Serializable {
    public Map<Long, String> getBankList(Organisation organisation);
}
