package com.abm.mainet.water.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.utility.GridPaginationUtility;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

/**
 * Repository : TbCsmrInfo.
 */
@Repository
public class PhysicalDateEntryJpaRepositoryImpl extends AbstractDAO<TbKCsmrInfoMH> implements PhysicalDateEntryJpaRepository {

    private static final String SELECT_WORK_ORDER = "select workOrd from TbWorkOrderEntity workOrd,TbKCsmrInfoMH mh  WHERE workOrd.orgid=?1 and workOrd.woPrintFlg=?2"
            + " and workOrd.woApplicationId=mh.applicationNo and mh.pcFlg is null and mh.orgId=?3 ";

    private static final String SELECT_CSMR_INFO = "select am from TbKCsmrInfoMH am  WHERE am.applicationNo=?1 ";
    
    private static final String SELECT_WORK_ORDER_DISCONNECTION = "select workOrd from TbWorkOrderEntity workOrd,TBWaterDisconnection mh  WHERE workOrd.orgid=?1 and workOrd.woPrintFlg=?2"
            + " and workOrd.woApplicationId=mh.apmApplicationId and mh.discGrantFlag is null and mh.orgId=?3 ";
    
    private static final String SELECT_WORK_ORDER_RECONNECTION = "select workOrd from TbWorkOrderEntity workOrd,TbWaterReconnection rc  WHERE workOrd.orgid=?1 and workOrd.woPrintFlg=?2"
            + " and workOrd.woApplicationId=rc.apmApplicationId and rc.ccnFlag is null and rc.orgId=?3 ";

   /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.PhysicalDateEntryJpaRepository#updateFormData(com.abm.mainet.water.domain.TbCsmrInfoEntity)
     */
    @Override
    public TbKCsmrInfoMH updateFormData(final TbKCsmrInfoMH entity) {
    	TbKCsmrInfoMH ntity= entityManager.merge(entity);
        return ntity;
    }

    @Override
    public TbKCsmrInfoMH queryDataAgainstAppliNum(final Long applicationNumber) {
        final Query query = createQuery(SELECT_CSMR_INFO);
        query.setParameter(1, applicationNumber);
        final TbKCsmrInfoMH entity = (TbKCsmrInfoMH) query.getSingleResult();
        return entity;
    }

    @Override
    public List<TbWorkOrderEntity> queryDataFromWorkOrderTab(final long orgId, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO) {

        final StringBuilder queryString = new StringBuilder(SELECT_WORK_ORDER);
        String coloumnName = null;
        if (gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.ConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, "workOrd.");
        GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                MainetConstants.ConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "workOrd.");
        final Query query = createQuery(queryString.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.Common_Constant.YES);
        query.setParameter(3, orgId);

        GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);

        @SuppressWarnings("unchecked")
        final List<TbWorkOrderEntity> workOrderEntity = query.getResultList();
        return workOrderEntity;
    }

    @Override
    public int queryDataFromWorkOrderTotalCount(final long orgId, final PagingDTO pagingDTO, final GridSearchDTO gridSearchDTO) {

        final StringBuilder queryString = new StringBuilder(SELECT_WORK_ORDER);
        String coloumnName = null;
        if (gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.ConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, "workOrd.");
        GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                MainetConstants.ConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "workOrd.");
        final Query query = createQuery(queryString.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.Common_Constant.YES);
        query.setParameter(3, orgId);
        @SuppressWarnings("unchecked")
        final List<TbWorkOrderEntity> workOrderEntity = query.getResultList();
        return workOrderEntity.size();
    }

    @Override
    public List<TbWorkOrderEntity> queryDataFromWorkOrderTabForDisconnection(final long orgId, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO) {

        final StringBuilder queryString = new StringBuilder(SELECT_WORK_ORDER_DISCONNECTION);
        String coloumnName = null;
        if (gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.ConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, "workOrd.");
        GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                MainetConstants.ConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "workOrd.");
        final Query query = createQuery(queryString.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.Common_Constant.YES);
        query.setParameter(3, orgId);

        GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);

        @SuppressWarnings("unchecked")
        final List<TbWorkOrderEntity> workOrderEntity = query.getResultList();
        return workOrderEntity;
    }
    
    @Override
    public List<TbWorkOrderEntity> queryDataFromWorkOrderTabForReconnection(final long orgId, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO) {

        final StringBuilder queryString = new StringBuilder(SELECT_WORK_ORDER_RECONNECTION);
        String coloumnName = null;
        if (gridSearchDTO.getSearchOper() != null) {
            coloumnName = MainetConstants.ConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, "workOrd.");
        GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                MainetConstants.ConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "workOrd.");
        final Query query = createQuery(queryString.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, MainetConstants.Common_Constant.YES);
        query.setParameter(3, orgId);

        GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);

        @SuppressWarnings("unchecked")
        final List<TbWorkOrderEntity> workOrderEntity = query.getResultList();
        return workOrderEntity;
    }
}