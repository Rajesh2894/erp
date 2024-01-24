/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;

/**
 * @author cherupelli.srikanth
 *
 */	
public interface IHomeCompostingService {

    public SolidWasteConsumerMasterDTO saveHomeComposting(SolidWasteConsumerMasterDTO masterDto);

    public List<SolidWasteConsumerMasterDTO> searchHomeCompost(Long zone, Long ward, Long block, Long route, Long house,
	    Long mobileNo, String name, Long orgId);

    public SolidWasteConsumerMasterDTO getCitizenByRegistrationId(Long registrationId);

    public SolidWasteConsumerMasterDTO updateHomeComposting(SolidWasteConsumerMasterDTO masterDto , List<Long> removeWasteIds);

    
}
