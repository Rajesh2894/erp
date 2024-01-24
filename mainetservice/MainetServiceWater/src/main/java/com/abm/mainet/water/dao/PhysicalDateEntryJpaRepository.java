package com.abm.mainet.water.dao;

import java.util.List;

import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

/**
 * Repository : TbCsmrInfo.
 */
public interface PhysicalDateEntryJpaRepository {

    /**
     * @param entity
     */
	TbKCsmrInfoMH updateFormData(TbKCsmrInfoMH entity);

    TbKCsmrInfoMH queryDataAgainstAppliNum(Long applicationNumber);

    /**
     * @param gridSearchDTO
     * @param pagingDTO
     * @return
     */
    List<TbWorkOrderEntity> queryDataFromWorkOrderTab(long orgid, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO);

    int queryDataFromWorkOrderTotalCount(long orgid, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO);
    
    List<TbWorkOrderEntity> queryDataFromWorkOrderTabForDisconnection(final long orgId, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO);
    
    List<TbWorkOrderEntity> queryDataFromWorkOrderTabForReconnection(final long orgId, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO);

}