/**
 * 
 */
package com.abm.mainet.rnl.dao;

import java.util.List;

/**
 * @author divya.marshettiwar
 *
 */
public interface EstateContractMappingDao {

	List<Object[]> searchData(String contNo, String propertyContractNo, String estateName, String mobileNo, Long orgId);

}
