package com.abm.mainet.common.integration.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.repository.PgBankParameterDetailRepository;

/**
 * @author cherupelli.srikanth
 *@since 28 september 2020
 */
@Service
public class PgBankParameterServiceImpl implements PgBankParameterService{

	@Autowired
	private PgBankParameterDetailRepository pgBankParameterDetailRepository;
	
	@Override
	@Transactional
	public void savePgBankParameterDetails(PGBankDetail pgBankMas) {
		pgBankParameterDetailRepository.save(pgBankMas);
	}

	
}
