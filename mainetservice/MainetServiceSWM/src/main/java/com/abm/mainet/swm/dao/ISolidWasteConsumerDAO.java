package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 20-Jun-2018
 */
public interface ISolidWasteConsumerDAO {

    
    /**
     * search
     * @param registrationId
     * @param custNumber
     * @param propetyNo
     * @param mobileNo
     * @param orgId
     * @return
     */
    SolidWasteConsumerMaster search(Long registrationId, String custNumber, String propetyNo, Long mobileNo, Long orgId);
    
    List<SolidWasteConsumerMaster> searchCitizenData(Long zone, Long ward, Long block, Long route, Long house,
	    Long mobileNo, String name, Long orgId);

}
