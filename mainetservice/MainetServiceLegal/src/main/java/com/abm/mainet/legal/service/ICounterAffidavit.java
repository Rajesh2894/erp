package com.abm.mainet.legal.service;

import com.abm.mainet.legal.dto.CounterAffidavitDTO;

public interface ICounterAffidavit
{

	
	void saveEntry(CounterAffidavitDTO counterAffidavitDTO);
	
	
	void updateEntry(CounterAffidavitDTO counterAffidavitDTO);
	
	

	CounterAffidavitDTO getCounterAffidavitBysceId(Long cseId, Long orgId);
	
}
