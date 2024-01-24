package com.abm.mainet.lqp.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.lqp.domain.QueryRegistrationMaster;

@Repository
public class LegislativeDaoImpl extends AbstractDAO<Long> implements ILegislativeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegislativeDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<QueryRegistrationMaster> searchQueryRegisterMasterData(Long deptId, Long questionTypeId, String questionId,
            Date fromDate, Date toDate, Long orgId) {
        List<QueryRegistrationMaster> councilMeetingMasterEntities = new ArrayList<QueryRegistrationMaster>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT qr FROM QueryRegistrationMaster qr join fetch qr.department  where qr.orgId = :orgId ");

            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                jpaQuery.append(" and qr.department.dpDeptid  = :deptId");
            }

            if (Optional.ofNullable(questionTypeId).orElse(0L) != 0) {
                jpaQuery.append(" and qr.questionTypeId  = :questionTypeId");
            }

            if (StringUtils.isNotEmpty(questionId)) {
                jpaQuery.append(" and qr.questionId like :questionId ");
            }

            if (fromDate != null) {
                jpaQuery.append(" and qr.questionDate >= :fromDate ");
            }
            if (toDate != null) {
                jpaQuery.append(" and qr.questionDate <= :toDate ");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgId", orgId);

            if (Optional.ofNullable(deptId).orElse(0L) != 0) {
                hqlQuery.setParameter("deptId", deptId);
            }

            if (Optional.ofNullable(questionTypeId).orElse(0L) != 0) {
                hqlQuery.setParameter("questionTypeId", questionTypeId);
            }

            if (StringUtils.isNotEmpty(questionId)) {
                hqlQuery.setParameter("questionId", questionId);
            }

            if (fromDate != null) {
                hqlQuery.setParameter("fromDate", fromDate);
            }

            if (toDate != null) {
                hqlQuery.setParameter("toDate", toDate);
            }

            councilMeetingMasterEntities = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Reord", exception);
        }
        return councilMeetingMasterEntities;
    }

    @Override
    public WorkflowMas getServiceWorkFlowForWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT wt FROM WorkflowMas wt"
                + " where wt.organisation.orgid = " + orgId
                + " and wt.department.dpDeptid = " + deptId
                + " and wt.complaint is NULL"
                + " and wt.service.smServiceId = " + serviceId
                + " and wt.type = 'N'"
                + " and wt.status = 'Y' ");
        if (sourceOfFund != null && sourceOfFund != 0)
            builder.append(" and wt.wmSchCodeId1=" + sourceOfFund);
        if (amount != null)
            builder.append(" and wt.fromAmount <= " + amount + " and wt.toAmount >= " + amount);
        if (codIdOperLevel1 != null && codIdOperLevel1 > 0d)
            builder.append(" and wt.codIdOperLevel1 = " + codIdOperLevel1);
        if (codIdOperLevel2 != null && codIdOperLevel2 > 0d)
            builder.append(" and wt.codIdOperLevel2 = " + codIdOperLevel2);
        if (codIdOperLevel3 != null && codIdOperLevel3 > 0d)
            builder.append(" and wt.codIdOperLevel3 = " + codIdOperLevel3);
        if (codIdOperLevel4 != null && codIdOperLevel4 > 0d)
            builder.append(" and wt.codIdOperLevel4 = " + codIdOperLevel4);
        if (codIdOperLevel5 != null && codIdOperLevel5 > 0d)
            builder.append(" and wt.codIdOperLevel5 = " + codIdOperLevel5);

        Query query = this.createQuery(builder.toString());
        @SuppressWarnings("unchecked")
        List<WorkflowMas> list = query.getResultList();
        if (list.isEmpty()) {
            LOGGER.error("Workflow not found for given Parameters : " + builder.toString());
        }
        return list.isEmpty() == true ? null : list.get(0);
    }

}
