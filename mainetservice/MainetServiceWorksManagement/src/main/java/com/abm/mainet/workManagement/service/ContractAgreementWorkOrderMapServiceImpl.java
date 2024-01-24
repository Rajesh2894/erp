package com.abm.mainet.workManagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.repository.TenderInitiationRepository;

@Service
public class ContractAgreementWorkOrderMapServiceImpl implements ContractAgreementWorkOrderMapService{
	@Autowired
	private TenderInitiationService tenderInitiationService;

	@Autowired
	private TenderInitiationRepository tenderRepo;

	@Autowired
	private WorkEstimateService workEstimateService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractAgreementWorkOrderMapServiceImpl.class);

	@Override
	public void updateContractFlag(Long orgId, String loaNo, Long empId, Long contId) {

		Long workId = tenderRepo.getWorkIdByLoaNumber(loaNo, orgId);

		tenderInitiationService.updateContractId(contId, workId, empId);
		
		workEstimateService.updateContractId(workId, contId,empId);
	}
}
