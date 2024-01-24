package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;

public interface AdvanceEntryDao {

    List<AdvanceEntryEntity> findByGridAllData(Long advanceNumber, Date advDate, String name, BigDecimal advAmount,
            Long advanceType, String cpdIdStatus, Long orgId,Long deptId);

}
