package com.abm.mainet.rnl.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.rnl.domain.EstateBooking;
import com.abm.mainet.rnl.dto.ReportDTO;

@Repository
public class ReportDAOImpl extends AbstractDAO<Long> implements IReportDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<ReportDTO> findRevenueReportByDateAndServiceIdAndOrgId(Date toDates, Date fromDates, Long orgId, Long serviceId) {
        List<ReportDTO> summaryReportList = new ArrayList<>();
        String queryString = "select NEW com.abm.mainet.rnl.dto.ReportDTO(r.rmRcptid,r.rmRcptno,r.rmDate,m.cpdFeemode,r.rmReceivedfrom,m.rdChequeddno,SUM(d.rfFeeamount) AS rfFeeamount,r.rmNarration,r.apmApplicationId ) from "
                + "TbServiceReceiptMasEntity r, TbSrcptFeesDetEntity d,TbSrcptModesDetEntity m,TbComparamMasEntity cm, TbComparamDetEntity cd where "
                + "r.rmRcptid = d.rmRcptid AND r.rmRcptid = m.rmRcptid AND d.rmRcptid = m.rmRcptid AND cm.cpmId = cd.tbComparamMas.cpmId AND cd.cpdId = m.cpdFeemode AND r.receiptDelFlag is null "
                + "AND r.receiptTypeFlag in ('M','R','A','P') AND r.smServiceId =:serviceId AND r.orgId =:orgId AND r.rmDate BETWEEN :fromDates AND :toDates "
                + "GROUP BY r.rmRcptid , r.rmRcptno , r.rmDate , m.cpdFeemode , r.rmReceivedfrom , m.rdChequeddno,r.rmNarration "
                + "order by r.rmDate,r.rmRcptno asc ";
        TypedQuery<ReportDTO> query = entityManager.createQuery(queryString, ReportDTO.class);
        // Query query = createQuery(queryString);
        query.setParameter("serviceId", serviceId);
        query.setParameter("orgId", orgId);
        query.setParameter("fromDates", fromDates);
        query.setParameter("toDates", toDates);

        summaryReportList = query.getResultList();
        return summaryReportList;
    }

    @Override
    public Boolean checkEstateRegNoExist(Long esId, String regNo, Long orgId) {
        StringBuilder jpaQuery = new StringBuilder(
                "SELECT COUNT(e) from EstateEntity e WHERE  e.regNo=:regNo AND e.orgId=:orgId AND e.isActive = 'Y'");

        if (esId != null) {
            jpaQuery.append(" AND e.esId != :esId");
        }
        final Query hqlQuery = createQuery(jpaQuery.toString());
        hqlQuery.setParameter("regNo", regNo);
        hqlQuery.setParameter("orgId", orgId);
        if (esId != null) {
            hqlQuery.setParameter("esId", esId);
        }
        Long count = (Long) hqlQuery.getSingleResult();
        if (count == 1)
            return true;
        else
            return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EstateBooking> findFreezePropertyByDate(Date fromDate, Date toDate, Long propId, Long orgId) {
        // select eb from EstateBooking eb where eb.fromDate >= ?1 AND eb.toDate <= ?2 AND eb.estatePropertyEntity.propId = ?3 AND
        // eb.orgId = ?4 AND eb.bookingStatus='F'

        List<EstateBooking> ebookings = new ArrayList<EstateBooking>();
        // compare two date with two DIFF column
        String dateCompareQuery = "("
                + "((eb.fromDate <= :fromDate AND eb.toDate >= :fromDate ) AND (eb.toDate  > :toDate or eb.toDate <= :toDate)) "
                + "or"
                + "((eb.fromDate <= :toDate and  eb.toDate >= :toDate) AND (eb.toDate  > :toDate or eb.toDate <= :toDate)) "
                + "or"
                + "(eb.fromDate >= :fromDate and eb.toDate <= :toDate)"
                + ")";

        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "FROM EstateBooking eb where " + dateCompareQuery
                            + " AND eb.estatePropertyEntity.propId = :propId  AND eb.orgId = :orgId AND eb.bookingStatus='F' ");

            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("fromDate", fromDate);
            hqlQuery.setParameter("toDate", toDate);
            hqlQuery.setParameter("propId", propId);
            hqlQuery.setParameter("orgId", orgId);

            ebookings = (List<EstateBooking>) hqlQuery.getResultList();
            if (ebookings.isEmpty()) {
                return ebookings;
            }
        } catch (Exception e) {
            throw new FrameworkException("Exception occured when check property is freeze on particular date", e);
        }
        return ebookings;
    }

}
