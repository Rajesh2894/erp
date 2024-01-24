package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.WorkOrder;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public class WorkOrderRepositoryImpl extends AbstractDAO<Long> implements WorkOrderRepositoryCustoms {

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkOrder> getFilterWorkOrderGenerationList(String workOrderNo, Date workStipulatedDate,
            Date contractFromDate, Date contractToDate, String vendorName, Long orgId) {

        List<WorkOrder> entity = null;

        StringBuilder hql = new StringBuilder("SELECT wo FROM WorkOrder wo  where wo.orgId = :orgId");

        if (contractFromDate != null && contractToDate != null) {
            hql.append(" and wo.contractFromDate >= :contractFromDate and wo.contractToDate <= :contractToDate ");
        }
        if (contractFromDate != null && contractToDate == null) {
            hql.append(" and wo.contractFromDate >= :contractFromDate ");
        }
        if (contractFromDate == null && contractToDate != null) {
            hql.append(" and wo.contractToDate <= :contractToDate ");
        }
        if (workStipulatedDate != null) {
            hql.append(" and wo.startDate <= :workStipulatedDate ");
        }
        if (workOrderNo != null && !workOrderNo.isEmpty()) {
            hql.append(" and wo.workOrderNo like :workOrderNo");
        }
        /*
         * if (vendorName != null && !vendorName.isEmpty()) {
         * hql.append(" and wo.tenderMasterEntity.vendorMaster.vmVendorname like :vendorName"); }
         */

        final Query query = entityManager.createQuery(hql.toString());
        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

        if (contractFromDate != null && contractToDate != null) {
            query.setParameter("contractFromDate", contractFromDate);
            query.setParameter("contractToDate", contractToDate);
        }
        if (contractFromDate != null && contractToDate == null) {
            query.setParameter("contractFromDate", contractFromDate);
        }
        if (contractFromDate == null && contractToDate != null) {
            query.setParameter("contractToDate", contractToDate);
        }
        if (workStipulatedDate != null) {
            query.setParameter("workStipulatedDate", workStipulatedDate);
        }
        if (workOrderNo != null && !workOrderNo.isEmpty()) {
            query.setParameter("workOrderNo",
                    MainetConstants.operator.PERCENTILE + workOrderNo + MainetConstants.operator.PERCENTILE);
        }
        /*
         * if (vendorName != null && !vendorName.isEmpty()) { query.setParameter("vendorName", MainetConstants.operator.PERCENTILE
         * + vendorName + MainetConstants.operator.PERCENTILE); }
         */

        entity = query.getResultList();

        return entity;
    }

}
