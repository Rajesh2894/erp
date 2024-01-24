package com.abm.mainet.intranet.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.intranet.dao.PollViewDao;
import com.abm.mainet.intranet.domain.PollView;
import com.abm.mainet.intranet.dto.report.IntranetPollDTO;
import com.abm.mainet.intranet.dto.report.PollRequest;
import com.abm.mainet.intranet.repository.PollViewRepository;

@Service
public class PollViewServiceImpl implements PollViewService {

    @Autowired
    private PollViewRepository pollViewRepository;
    
    @Autowired
    private PollViewDao pollViewDao;
   
	@Override
	public PollView createPollViewDetails(PollRequest pollRequest) {
		PollView pollView = new PollView();
		pollView.setOrgid(pollRequest.getOrgId());
		pollView.setLangId(pollRequest.getLangId());
		pollView.setUserId(pollRequest.getUserId());
		pollView.setLmoddate(new Date());
		pollView.setLgIpMac(pollRequest.getLgIpMac());
        pollView.setLgIpMacUpd(pollRequest.getLgIpMacUpd());
        pollView.setPollId(pollRequest.getPollid());
        pollView.setPollQue(pollRequest.getPollQue());
        pollView.setChoiceId(pollRequest.getChoiceid());
		pollView.setChoiceDescVal(pollRequest.getChoiceDescVal());
		pollView.setLoggedinEmpId(pollRequest.getLoggedInEmp());
		pollView.setChoiceDesc(pollRequest.getChoiceDesc());
		return pollViewRepository.save(pollView);
	}

	@Override
	@Transactional
	public List<PollView> getPollViewData(Long orgId) {
		List<PollView> pollViewObj = pollViewDao.getPollViewData(orgId);
		return pollViewObj;
	}

	@Override
	@Transactional
	public List<Object> getPollViewCount(Long orgId) {
		//List<PollView> pollViewObjCount = pollViewDao.getPollViewCount(orgId);
		List<Object> pollViewObjCount = pollViewRepository.getPollViewCount(orgId);
		return pollViewObjCount;
	}	
	
	@Override
	@Transactional
	public List<Object> getPollViewCountQue(Long orgId) {
		List<Object> pollViewObjCountQue = pollViewRepository.getPollViewCountQue();
		return pollViewObjCountQue;
	}	
	
	/*
	@Override
	@Transactional
	public List<IntranetPollDTO> getPollViewCountDto(Long orgId) {
		//List<PollView> pollViewObjCount = pollViewDao.getPollViewCount(orgId);
		List<IntranetPollDTO> pollViewObjCountDto = pollViewRepository.getPollViewCountDto();
		return pollViewObjCountDto;
	}
	*/	
	
	@Override
	@Transactional
	public List<IntranetPollDTO> getPollViewCountDto(Long orgId) {
		List<IntranetPollDTO> listDTO = pollViewRepository.getPollViewCountDto();
		return listDTO;
	}

	@Override
	@Transactional
	public List<PollView> getPollViewDet(Long pollid, Long userId) {
		List<PollView> PollViewEntity = pollViewDao.getPollViewDet(pollid, userId);
		return PollViewEntity;
	}
	
	
	
	
}
