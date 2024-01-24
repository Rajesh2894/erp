package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.TbComparentMasJpaRepository;
import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.dto.TbComparentMas;
import com.abm.mainet.common.master.mapper.TbComparentMasServiceMapper;

@Service
public class TbComparentMasServiceImpl implements TbComparentMasService {

    @Autowired
    private TbComparentMasJpaRepository tbComparentMasJpaRepository;

    @Autowired
    private TbComparentMasServiceMapper tbComparentMasServiceMapper;

    @Override
    @Transactional
    public List<TbComparentMas> findComparentMasDataByCpmId(Long cpmId) {

        List<TbComparentMasEntity> tbComparentMasEntity = tbComparentMasJpaRepository.findComparentMasDataByCpmId(cpmId);
        List<TbComparentMas> tbComparentMasList = new ArrayList<TbComparentMas>();
        for (TbComparentMasEntity tbComparentMas : tbComparentMasEntity) {
            tbComparentMasList.add(tbComparentMasServiceMapper.mapTbComparentMasEntityToTbComparentMas(tbComparentMas));
        }
        return tbComparentMasList;
    }

    @Override
    @Transactional
    public TbComparentMasEntity create(TbComparentMasEntity entity) {
        tbComparentMasJpaRepository.save(entity);
        return entity;
    }

}
