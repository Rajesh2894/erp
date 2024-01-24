package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dao.TbComparamMasJpaRepository;
import com.abm.mainet.common.domain.TbComparamMasEntity;

@Service
public class TbComparamMasServiceImpl implements TbComparamMasService {

    @Autowired
    private TbComparamMasJpaRepository tbComparamMasJpaRepository;

    @Override
    public List<TbComparamMasEntity> findAllByCpmReplicateFlag(String cpmReplicateFlag, String cpmType) {

        return tbComparamMasJpaRepository.findAllByCpmReplicateFlag(cpmReplicateFlag, cpmType);

    }

}
