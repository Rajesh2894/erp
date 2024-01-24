package com.abm.mainet.property.service;

import com.abm.mainet.property.domain.TbAsTryEntity;

public interface ITryService {

    TbAsTryEntity findTryDataByRecordNoAndLandType(String recordcode, String landTypeId);

    TbAsTryEntity findTryDataByVsrNo(String vsrNo);

}
