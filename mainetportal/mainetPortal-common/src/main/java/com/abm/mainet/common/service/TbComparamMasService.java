package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.TbComparamMasEntity;

public interface TbComparamMasService {

    List<TbComparamMasEntity> findAllByCpmReplicateFlag(String cpmReplicateFlag, String cpmType);

}
