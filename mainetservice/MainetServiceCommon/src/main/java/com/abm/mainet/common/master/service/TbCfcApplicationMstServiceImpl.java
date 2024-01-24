/*
 * Created on 19 Aug 2015 ( Time 17:12:00 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.master.dao.IReceiptEntryDao;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.mapper.TbCfcApplicationMstServiceMapper;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * Implementation of TbCfcApplicationMstService
 */
@Component
@Transactional
public class TbCfcApplicationMstServiceImpl implements TbCfcApplicationMstService {
	private static final Logger LOGGER = Logger.getLogger(TbCfcApplicationMstServiceImpl.class);
	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	private TbCfcApplicationMstServiceMapper tbCfcApplicationMstServiceMapper;
	
	@Autowired
	private IReceiptEntryDao iReceiptEntryDao;

	@Override
	@Transactional
	public TbCfcApplicationMst findById(final Long apmApplicationId) {
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = tbCfcApplicationMstJpaRepository
				.findOne(apmApplicationId);
		return tbCfcApplicationMstServiceMapper
				.mapTbCfcApplicationMstEntityToTbCfcApplicationMst(tbCfcApplicationMstEntity);
	}

	@Override
	public List<TbCfcApplicationMst> findAll() {
		final Iterable<TbCfcApplicationMstEntity> entities = tbCfcApplicationMstJpaRepository.findAll();
		final List<TbCfcApplicationMst> beans = new ArrayList<>();
		for (final TbCfcApplicationMstEntity tbCfcApplicationMstEntity : entities) {
			beans.add(tbCfcApplicationMstServiceMapper
					.mapTbCfcApplicationMstEntityToTbCfcApplicationMst(tbCfcApplicationMstEntity));
		}
		return beans;
	}

	@Override
	public TbCfcApplicationMst save(final TbCfcApplicationMst tbCfcApplicationMst) {
		return update(tbCfcApplicationMst);
	}

	@Override
	public TbCfcApplicationMst create(final TbCfcApplicationMst tbCfcApplicationMst) {
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		tbCfcApplicationMstServiceMapper.mapTbCfcApplicationMstToTbCfcApplicationMstEntity(tbCfcApplicationMst,
				tbCfcApplicationMstEntity);
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntitySaved = tbCfcApplicationMstJpaRepository
				.save(tbCfcApplicationMstEntity);
		return tbCfcApplicationMstServiceMapper
				.mapTbCfcApplicationMstEntityToTbCfcApplicationMst(tbCfcApplicationMstEntitySaved);
	}

	@Override
	public TbCfcApplicationMst update(final TbCfcApplicationMst tbCfcApplicationMst) {
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = tbCfcApplicationMstJpaRepository
				.findOne(tbCfcApplicationMst.getApmApplicationId());
		tbCfcApplicationMstServiceMapper.mapTbCfcApplicationMstToTbCfcApplicationMstEntity(tbCfcApplicationMst,
				tbCfcApplicationMstEntity);
		final TbCfcApplicationMstEntity tbCfcApplicationMstEntitySaved = tbCfcApplicationMstJpaRepository
				.save(tbCfcApplicationMstEntity);
		return tbCfcApplicationMstServiceMapper
				.mapTbCfcApplicationMstEntityToTbCfcApplicationMst(tbCfcApplicationMstEntitySaved);
	}

	@Override
	public void delete(final Long apmApplicationId) {
		tbCfcApplicationMstJpaRepository.delete(apmApplicationId);
	}

	public TbCfcApplicationMstJpaRepository getTbCfcApplicationMstJpaRepository() {
		return tbCfcApplicationMstJpaRepository;
	}

	public void setTbCfcApplicationMstJpaRepository(
			final TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository) {
		this.tbCfcApplicationMstJpaRepository = tbCfcApplicationMstJpaRepository;
	}

	public TbCfcApplicationMstServiceMapper getTbCfcApplicationMstServiceMapper() {
		return tbCfcApplicationMstServiceMapper;
	}

	public void setTbCfcApplicationMstServiceMapper(
			final TbCfcApplicationMstServiceMapper tbCfcApplicationMstServiceMapper) {
		this.tbCfcApplicationMstServiceMapper = tbCfcApplicationMstServiceMapper;
	}

	@Override
	public int checkForTransactionExist(final Long serviceId, final Long orgId) {
		final Long counter = tbCfcApplicationMstJpaRepository.checkForTransactionExist(serviceId, orgId);
		return counter.intValue();
	}

	@Override
	public List<String> getApplicationIdsByServiceId(Long serviceId) {
		// List<Long> appIds =
		// tbCfcApplicationMstJpaRepository.getApplicationIdsByServiceId(serviceId);
		List<String> refIds = tbCfcApplicationMstJpaRepository.getrefNosByServiceId(serviceId);
		return refIds;
	}

	@Override
	public List<String> getRefIdsByServiceId(Long serviceId) {
		List<String> refIds = tbCfcApplicationMstJpaRepository.getrefNosByServiceId(serviceId);
		return refIds;
	}

	@Override
	public CFCSchedulingCounterDet getCounterDetByEmpId(Long empId, Long orgId) {

		String deptRestCallKey = ApplicationSession.getInstance().getMessage("GET_SCHDLNG_COUNTR_DET");
		LOGGER.info("Rest URI to getCounterDetByEmpId"+deptRestCallKey);
		CFCSchedulingCounterDet cfcSchedulingCounterDet = null;
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		try {	 
			RestTemplate restTemplate=new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			UriComponentsBuilder buil = UriComponentsBuilder.fromHttpUrl(deptRestCallKey)
			        .queryParam("empId", empId)
			        .queryParam("orgId", orgId);

			HttpEntity<?> entity = new HttpEntity<>(headers);

			HttpEntity<CFCSchedulingCounterDet> response = restTemplate.exchange(
					buil.toUriString(), 
			        HttpMethod.GET, 
			        entity, 
			        CFCSchedulingCounterDet.class);
			
			if(response.getBody() != null) {
				cfcSchedulingCounterDet=response.getBody();
				
				LOGGER.info("Response entity for getCounterDetByEmpId"+response);
			}
			
		} catch (Exception ex) {
			LOGGER.info("exception occure while executing getCounterDetByEmpId :"+ex);
		}
		return cfcSchedulingCounterDet;

	}
    @Override
	public void setRecieptCfcAndCounterCount(CFCSchedulingCounterDet cfcSchedulingCounterDet) {
	if(cfcSchedulingCounterDet.getDwzId1()!=null) {
		int cfcCount=0;
		int counterCount=0;
		 cfcCount=iReceiptEntryDao.getRecieptENTRYcount(cfcSchedulingCounterDet.getOrgId(), null, cfcSchedulingCounterDet.getDwzId1());
		if(cfcSchedulingCounterDet.getEmpId()!=null) {
			counterCount=	iReceiptEntryDao.getRecieptENTRYcount(cfcSchedulingCounterDet.getOrgId(), cfcSchedulingCounterDet.getEmpId(), cfcSchedulingCounterDet.getDwzId1());
		}
		cfcSchedulingCounterDet.setCollcntrno(cfcSchedulingCounterDet.getCollcntrno()+"/"+(cfcCount+1));//to get count start with 1 
		cfcSchedulingCounterDet.setCounterno(cfcSchedulingCounterDet.getCounterno()+"/"+(counterCount+1));
	}
		
	}
}
