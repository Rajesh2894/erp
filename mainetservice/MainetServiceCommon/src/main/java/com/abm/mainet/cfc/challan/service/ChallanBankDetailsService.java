package com.abm.mainet.cfc.challan.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.challan.dao.IChallanBankDetailDAO;
import com.abm.mainet.common.domain.Organisation;

@Service
public class ChallanBankDetailsService implements IChallanBankDetailsService {

    private static final long serialVersionUID = 4803208157484548458L;

    @Autowired
    IChallanBankDetailDAO IChallanBankDetailDAO;

    @Override
    public Map<Long, String> getBankList(final Organisation organisation) {
        return IChallanBankDetailDAO.getBankList(organisation);
    }

}
