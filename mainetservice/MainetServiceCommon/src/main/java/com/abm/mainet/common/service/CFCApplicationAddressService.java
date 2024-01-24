package com.abm.mainet.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.ICFCApplicationAddressDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;

/**
 * @author Rahul.Yadav
 *
 */
@Service
public class CFCApplicationAddressService implements ICFCApplicationAddressService {

    @Autowired
    private ICFCApplicationAddressDAO iCFCApplicationAddressDAO;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.service.ICFCApplicationAddressService#getApplicationAddressByAppId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public CFCApplicationAddressEntity getApplicationAddressByAppId(
            final Long loiApplicationId, final Long orgId) {
        return iCFCApplicationAddressDAO.getApplicationAddressByAppId(loiApplicationId, orgId);
    }
}
