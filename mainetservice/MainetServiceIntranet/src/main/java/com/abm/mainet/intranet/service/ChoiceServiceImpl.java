package com.abm.mainet.intranet.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.intranet.dao.ChoiceDao;
import com.abm.mainet.intranet.domain.Choice;
import com.abm.mainet.intranet.repository.ChoiceRepository;

@Service
public class ChoiceServiceImpl implements ChoiceService {

	@Resource
	private ChoiceRepository choiceRepository;
	
    @Autowired
    private ChoiceDao choiceDao;
	
	@Override
	public Choice getPollByIdFromChoice(Long orgid) {
		Choice choice = choiceRepository.getPollByIdFromChoice(orgid);
		return choice;
	}

	@Transactional
	public List<Choice> getChoices(Long orgId, Long pollID) {
		List<Choice> choiceObj = choiceDao.getChoiceData(orgId, pollID);
		return choiceObj;
	}

	@Override
	public Choice getChoiceDesc(Long orgId, Long pollAnsId) {
		Choice choiceDesc = choiceDao.getChoiceDescData(orgId, pollAnsId);
		return choiceDesc;
	}

	

}
