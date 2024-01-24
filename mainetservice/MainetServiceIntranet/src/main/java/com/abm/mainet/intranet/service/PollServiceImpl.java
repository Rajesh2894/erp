package com.abm.mainet.intranet.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.intranet.dao.PollDao;
import com.abm.mainet.intranet.dao.PollDetailDao;
import com.abm.mainet.intranet.domain.Choice;
import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollDetails;
import com.abm.mainet.intranet.dto.report.PollRequest;
import com.abm.mainet.intranet.repository.PollDetailsRepository;
import com.abm.mainet.intranet.repository.PollRepository;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollRepository pollRepository;
    
    @Autowired
    private PollDetailsRepository pollDetailsRepository;
	   
    @Autowired
    private PollDao pollDao;
    
    @Autowired
    private PollDetailDao pollDetailDao;
    
	@Override
	@Transactional
	public Poll createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();

        pollRequest.getChoices().forEach(choiceRequest -> {
        	if(!choiceRequest.getText().trim().equals("")){
        	Choice choice = new Choice();
        	
        	choice.setLmoddate(pollRequest.getLmodDate());
        	choice.setLangId(Long.valueOf(pollRequest.getLangId()));
        	choice.setUserId(pollRequest.getUserId());
        	choice.setOrgid(Long.valueOf(pollRequest.getOrgId()));
        	choice.setText(choiceRequest.getText());
        	Long pollId1 = pollRequest.getPollid();
    		if(pollId1 != null) {
    			List<Poll> pollId22 = pollDao.getPollData1(pollRequest.getOrgId(),pollId1);
    			choice.setId(pollId22.get(0).getPollid());
        	}
        	poll.addChoice(choice);
        	}
        });
        
        poll.setQuestion(pollRequest.getQuestion()); 
        
        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(5));
        //.plus(Duration.ofHours(pollRequest.getPollLength().getHours()));
        poll.setExpirationDateTime(null);
        //poll.setExpirationDateTime(expirationDateTime);
        poll.setOrgid(pollRequest.getOrgId());
        poll.setLangId(pollRequest.getLangId());
        poll.setUserId(pollRequest.getUserId());
        poll.setLmoddate(new Date());
        poll.setLgIpMac(pollRequest.getLgIpMac());
        poll.setLgIpMacUpd(pollRequest.getLgIpMacUpd());
        poll.setPollStatus(pollRequest.getPollStatus());
        
        List<PollDetails> pollDetListId = pollDetailDao.getPollDetailData(pollRequest.getOrgId());
        poll.setId(pollDetListId.get(0));
       
        //if (!(pollRequest.getMode().isEmpty()) && (pollRequest.getMode().equals("E"))) {
        	List<Poll> pollIds = pollDao.getPollIdByPollDetId(pollDetListId.get(0).getId(), pollRequest.getOrgId());
        	if(pollIds != null && !(pollIds.isEmpty())) {
        		poll.setPollid(pollIds.get(0).getPollid());
        	}
        //}
        
        return pollRepository.save(poll);
	}

	@Override
	public Poll getPollById(Long pollId) {
		Poll poll = pollRepository.findById(pollId);
		return poll;
	}
	
	@Override
	@Transactional
	public PollDetails createPollDetails(PollRequest pollRequest) {
		PollDetails pollDet = new PollDetails();
		pollDet.setOrgid(pollRequest.getOrgId());
		pollDet.setLangId(pollRequest.getLangId());
        pollDet.setUserId(pollRequest.getUserId());
        pollDet.setLmoddate(new Date());
        pollDet.setLgIpMac(pollRequest.getLgIpMac());
        pollDet.setLgIpMacUpd(pollRequest.getLgIpMacUpd());
        pollDet.setPollDeptId(pollRequest.getDeptId());
        pollDet.setPollName(pollRequest.getPollName());
        pollDet.setPollFromDate(pollRequest.getFromDate());
        pollDet.setPollToDate(pollRequest.getToDate());
        
        if(pollRequest.getPollid() != null) {
        	pollDet.setId(pollRequest.getPollid());
        }
        return pollDetailsRepository.save(pollDet);
	}

	@Override
	@Transactional
	public void updatePollStatus(String pollStatus, Long pollid, Long orgid) {
		pollRepository.updatePollStatus(pollStatus, pollid, orgid);
	}

	@Override
	@Transactional
	public List<Poll> getPollData(Long orgId) {
		List<Poll> pollObj = pollDao.getPollDataByStatus(orgId);
		return pollObj;
	}

	
	
}
