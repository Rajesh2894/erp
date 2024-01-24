package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;

public interface TbAcCodingstructureMasDao {

    public List<TbAcCodingstructureMasEntity> findByAllGridSearchData(Long comCpdId, Long orgId);

}
