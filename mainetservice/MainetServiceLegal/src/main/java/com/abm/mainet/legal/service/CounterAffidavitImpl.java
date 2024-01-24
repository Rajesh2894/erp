package com.abm.mainet.legal.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.legal.domain.CounterAffidavitEntity;
import com.abm.mainet.legal.dto.CounterAffidavitDTO;
import com.abm.mainet.legal.repository.CounterAffidavitRepository;

@Service
public class CounterAffidavitImpl implements ICounterAffidavit
{
	
	@Resource
	CounterAffidavitRepository counterAffidavitRepository;
	
	@Override
	public void saveEntry(CounterAffidavitDTO counterAffidavitDTO) 
	{
		CounterAffidavitEntity counterAffidavitEntity = new CounterAffidavitEntity();
		
		BeanUtils.copyProperties(counterAffidavitDTO, counterAffidavitEntity);
		
		counterAffidavitRepository.save(counterAffidavitEntity);

	}

	@Override
	public void updateEntry(CounterAffidavitDTO counterAffidavitDTO) 
	{
       CounterAffidavitEntity counterAffidavitEntity = new CounterAffidavitEntity();
		
		BeanUtils.copyProperties(counterAffidavitDTO, counterAffidavitEntity);
		
	   counterAffidavitRepository.save(counterAffidavitEntity);
		
	}

	@Override
	@Transactional(readOnly=true)
	public CounterAffidavitDTO getCounterAffidavitBysceId(Long cseId,Long orgId) 
	{
		
		CounterAffidavitEntity counterAffidavitEntity = counterAffidavitRepository.findByOrgIdAndCaseId(orgId, cseId);
		
		CounterAffidavitDTO counterAffidavitDTO = new CounterAffidavitDTO();
		if(counterAffidavitEntity!=null)
			
		BeanUtils.copyProperties(counterAffidavitEntity, counterAffidavitDTO);
		
		return counterAffidavitDTO;
		
	
	}


}
