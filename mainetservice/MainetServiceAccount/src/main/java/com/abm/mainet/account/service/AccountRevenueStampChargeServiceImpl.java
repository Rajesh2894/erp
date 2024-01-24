package com.abm.mainet.account.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.RevenueStampChargesEntity;
import com.abm.mainet.account.dto.RevenueStampChargeDTO;
import com.abm.mainet.account.repository.AccountRevenueStampRepository;

@Service
public class AccountRevenueStampChargeServiceImpl implements AccountRevanueStampChargeService {

    @Resource
    AccountRevenueStampRepository accountRevenueStampRepository;

    @Override
    @Transactional
    public RevenueStampChargeDTO createRevenueEntry(RevenueStampChargeDTO revenueStampChargeDTO) {
        RevenueStampChargesEntity revenueStampChargesEntity = new RevenueStampChargesEntity();

        BeanUtils.copyProperties(revenueStampChargeDTO, revenueStampChargesEntity);
        accountRevenueStampRepository.save(revenueStampChargesEntity);
        return revenueStampChargeDTO;

    }

}
