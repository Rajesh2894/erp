package com.abm.mainet.intranet.dao;

import java.util.List;

import com.abm.mainet.intranet.domain.Choice;

public interface ChoiceDao {

	List<Choice> getChoiceData(Long orgId, Long pollID);

	Choice getChoiceDescData(Long orgId, Long pollAnsId);

	
		
		
}
