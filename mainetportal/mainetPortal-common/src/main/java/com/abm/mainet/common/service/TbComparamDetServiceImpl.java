package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.TbComparamDetJpaRepository;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.dto.TbComparamDet;
import com.abm.mainet.common.master.mapper.TbComparamDetServiceMapper;

@Service
public class TbComparamDetServiceImpl implements TbComparamDetService {

    @Autowired
    private TbComparamDetJpaRepository tbComparamDetJpaRepository;

    @Autowired
    private TbComparamDetServiceMapper tbComparamDetServiceMapper;

    @Override
    @Transactional
    public List<TbComparamDetEntity> findCmprmDetDataByCpmId(Long cpmId) {

        return tbComparamDetJpaRepository.findCmprmDetByCpmId(cpmId);
    }

    @Override
    @Transactional
    public TbComparamDet create(TbComparamDet tbComparamDet) {

        TbComparamDetEntity tbComparamDetEntity = null;
        if (tbComparamDet.getCpdId() != null)
            tbComparamDetEntity = tbComparamDetJpaRepository.findOne(tbComparamDet.getCpdId());
        if (tbComparamDetEntity != null) {
            throw new IllegalStateException("already.exists");
        }
        tbComparamDetEntity = new TbComparamDetEntity();
        tbComparamDetServiceMapper.mapTbComparamDetToTbComparamDetEntity(tbComparamDet, tbComparamDetEntity);
        TbComparamDetEntity tbComparamDetEntitySaved = tbComparamDetJpaRepository.save(tbComparamDetEntity);
        return tbComparamDetServiceMapper.mapTbComparamDetEntityToTbComparamDet(tbComparamDetEntitySaved);
    }

}
