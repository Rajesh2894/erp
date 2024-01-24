package com.abm.mainet.property.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.TbAsTryEntity;
import com.abm.mainet.property.repository.TbAsTryRepository;

@Service
public class TryServiceImpl implements ITryService {

    @Autowired
    private TbAsTryRepository tbAsTryRepository;

    @Transactional
    @Override
    public TbAsTryEntity findTryDataByRecordNoAndLandType(String recordcode, String landTypeId) {
        return tbAsTryRepository.findTryDataByRecordNoAndLandType(recordcode, landTypeId);
    }

    @Transactional
    @Override
    public TbAsTryEntity findTryDataByVsrNo(String vsrNo) {
        return tbAsTryRepository.findTryDataByVsrNo(vsrNo);
    }

}
