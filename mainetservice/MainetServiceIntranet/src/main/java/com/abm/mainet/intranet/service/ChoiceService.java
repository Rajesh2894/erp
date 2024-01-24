package com.abm.mainet.intranet.service;


import java.util.List;

import com.abm.mainet.intranet.domain.Choice;

public interface ChoiceService {
	
	public Choice getPollByIdFromChoice(Long Orgid);

	public List<Choice> getChoices(Long orgId,Long pollID);

	public Choice getChoiceDesc(Long orgId, Long pollAnsId);

	
	 
}
