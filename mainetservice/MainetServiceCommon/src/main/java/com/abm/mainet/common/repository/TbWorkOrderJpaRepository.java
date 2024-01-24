package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.TbWorkOrderEntity;

/**
 * Repository : TbWorkOrder.
 */
@Repository
public interface TbWorkOrderJpaRepository extends PagingAndSortingRepository<TbWorkOrderEntity, Long> {

    @Query("select tbWorkOrderEntity.woApplicationId, tbCfcApplicationMstEntity.apmFname , tbWorkOrderEntity.woPrintFlg from TbWorkOrderEntity tbWorkOrderEntity, TbCfcApplicationMstEntity tbCfcApplicationMstEntity "
            + " where tbWorkOrderEntity.woServiceId = :artServiceId and tbWorkOrderEntity.woApplicationId = tbCfcApplicationMstEntity.apmApplicationId and tbWorkOrderEntity.orgid= :orgid  and  tbWorkOrderEntity.woApplicationId =  tbCfcApplicationMstEntity.apmApplicationId and tbCfcApplicationMstEntity.tbOrganisation.orgid = :orgid order by tbWorkOrderEntity.woApplicationId desc")
    List<Object[]> findWorkOrderPrintList(@Param("artServiceId") Long artServiceId, @Param("orgid") Long orgid);

    @Query("select tbWorkOrderEntity.woApplicationId, tbCfcApplicationMstEntity.apmFname ,tbWorkOrderEntity.PlumId ,tbWorkOrderEntity.woPrintFlg from TbWorkOrderEntity tbWorkOrderEntity,TbCfcApplicationMstEntity tbCfcApplicationMstEntity "
            + " where tbWorkOrderEntity.woServiceId = :artServiceId and tbWorkOrderEntity.woApplicationId = tbCfcApplicationMstEntity.apmApplicationId and  tbWorkOrderEntity.orgid= :orgid and tbWorkOrderEntity.PlumId is null ")
    List<Object[]> WorkOrderPrintListwithAndWithoutPlumber(@Param("artServiceId") Long artServiceId, @Param("orgid") Long orgid);

    @Query("select tbWorkOrderEntity.woApplicationId, tbCfcApplicationMstEntity.apmFname ,tbWorkOrderEntity.PlumId ,tbWorkOrderEntity.woPrintFlg from TbWorkOrderEntity tbWorkOrderEntity,TbCfcApplicationMstEntity tbCfcApplicationMstEntity "
            + " where tbWorkOrderEntity.woServiceId = :artServiceId and tbWorkOrderEntity.woApplicationId = tbCfcApplicationMstEntity.apmApplicationId and  tbWorkOrderEntity.orgid= :orgid")
    List<Object[]> WorkOrderPrintListwithAndWithPlumber(@Param("artServiceId") Long artServiceId, @Param("orgid") Long orgid);
    
    @Query("select tbWorkOrderEntity from TbWorkOrderEntity tbWorkOrderEntity "
            + " where tbWorkOrderEntity.woOrderNo = :workOrderNo and  tbWorkOrderEntity.orgid= :orgid")
    TbWorkOrderEntity ForPrintflagupdate(@Param("workOrderNo") String workOrderNo, @Param("orgid") Long orgid);

    @Modifying
    @Transactional
	@Query("UPDATE TbWorkOrderEntity c SET  c.issuedDate=CURRENT_TIMESTAMP, c.issuedBy=:issuedBy  where c.woApplicationId=:applicationId and c.orgid=:orgid")
	void updateIssuanceDataInWorkOrder(@Param("applicationId") Long applicationId, @Param("issuedBy") Long issuedBy, @Param("orgid") Long orgid);

    @Query("select tbWorkOrderEntity from TbWorkOrderEntity tbWorkOrderEntity "
            + " where tbWorkOrderEntity.woApplicationId = :woApplicationId and  tbWorkOrderEntity.orgid= :orgid")
	TbWorkOrderEntity findByApplicationId(@Param("woApplicationId") Long woApplicationId,@Param("orgid") Long orgid);
}
