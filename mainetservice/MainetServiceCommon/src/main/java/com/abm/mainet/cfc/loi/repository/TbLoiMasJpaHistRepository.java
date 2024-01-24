package com.abm.mainet.cfc.loi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntityKey;
import com.abm.mainet.cfc.loi.domain.TbLoiMasHistEntity;

/**
 * Repository : TbLoiMasJpaHistRepository.
 */
public interface TbLoiMasJpaHistRepository extends PagingAndSortingRepository<TbLoiMasHistEntity, Long> {

 

}
