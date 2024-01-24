package com.abm.mainet.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.intranet.dao.PollDetailDao;
import com.abm.mainet.intranet.domain.PollDetails;
import com.abm.mainet.intranet.repository.PollDetailsRepository;

@Service
public class PollDetServiceImpl implements PollDetService {

    
    @Autowired
    private PollDetailsRepository pollDetailsRepository;
    
    @Autowired
    private PollDetailDao pollDetailDao;
    
	   
	@Override
	@Transactional
	public PollDetails getPollById(Long pollId) {
		PollDetails pollDet = pollDetailsRepository.findById(pollId);
		return pollDet;
	}

	@Override
	public List<PollDetails> getPollByIdFromPollDet(Long orgId) {
		List<PollDetails> pollDet = pollDetailDao.getPollByIdFromPollDet(orgId);
		return pollDet;
	}

	@Override
	@Transactional
	public List<PollDetails> getPollDet(Long orgId) {
		List<PollDetails> pollDetObj = pollDetailsRepository.findAll();
		return pollDetObj;
	}
	
	
}
