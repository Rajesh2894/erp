package com.abm.mainet.rts.service;


import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.rts.dto.FirstAppealDto;

public interface AppealService {
	
	
	public FirstAppealDto getFirstAppealData (Long applicationId, Long orgId);
	
	//save first appeal data
	ObjectionDetailsDto saveFirstAppealInObjection(ObjectionDetailsDto objectionDetailsDto);
	
	//save second appeal data
	ObjectionDetailsDto saveSecondAppealInObjection(ObjectionDetailsDto objectionDetailsDto);

}
