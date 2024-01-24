
package com.abm.mainet.water.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.DemandNotice;
import com.abm.mainet.water.domain.DemandNoticeHistory;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;

/**
 * @author Lalit.Mohan
 *
 */
@Repository
public class DemandNoticeGenarationRepositoryImpl extends
        AbstractDAO<DemandNotice> implements
        DemandNoticeGenarationRepository {

    @Resource
    private AuditService auditService;
    private static final Logger LOG = LoggerFactory
            .getLogger(DemandNoticeGenarationRepositoryImpl.class);

    @Override
    public List<DemandNoticeResponseDTO> searchAllDefaulter(
            final DemandNoticeRequestDTO request, final long demandId) {

        StringBuilder demandSearchQuery = null;

        if (request.getWdn() == request.getFinalNoticeType()) {
            demandSearchQuery = new StringBuilder(
                    QueryConstants.DemandNoticeQuery.FINAL_DEMAND_SEARCH);
        } else {
            demandSearchQuery = new StringBuilder(
                    QueryConstants.DemandNoticeQuery.DEMAND_SEARCH);
        }

        if (request.getTrf1() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP1);
        }
        if (request.getTrf2() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP2);
        }
        if (request.getTrf3() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP3);
        }
        if (request.getTrf4() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP4);
        }
        if (request.getTrf5() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP5);
        }

        if (request.getCsz() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.CONNECTION_SIZE);
        }
        if (request.getWmn() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.METER_TYPE);
        }

        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.CONNECTION_BETWEEN);
        }

        if (request.getWwz1() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ1);
        }
        if (request.getWwz2() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ2);
        }
        if (request.getWwz3() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ3);
        }
        if (request.getWwz4() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ4);
        }
        if (request.getWwz5() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ5);
        }

        final Query query = createQuery(demandSearchQuery.toString());
        query.setParameter(
                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                request.getOrgid());
        if (request.getWdn() == request.getFinalNoticeType()) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.FINAL_NOTICE_TYPE,
                    demandId);
        }
        query.setParameter("currDate", new Date(), TemporalType.DATE);
        if (request.getTrf1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP1,
                    request.getTrf1());
        }
        if (request.getTrf2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP2,
                    request.getTrf2());
        }
        if (request.getTrf3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP3,
                    request.getTrf3());
        }
        if (request.getTrf4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP4,
                    request.getTrf4());
        }
        if (request.getTrf5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP5,
                    request.getTrf5());
        }

        if (request.getWwz1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID1,
                    request.getWwz1());
        }
        if (request.getWwz2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID2,
                    request.getWwz2());
        }
        if (request.getWwz3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID3,
                    request.getWwz3());
        }
        if (request.getWwz4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID4,
                    request.getWwz4());
        }
        if (request.getWwz5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID5,
                    request.getWwz5());
        }
        if (request.getCsz() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CONNECTION_SIZE,
                    request.getCsz());
        }
        if (request.getWmn() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.METER_TYPE,
                    request.getWmn());
        }

        if (request.getConnFrom().isEmpty()) {
            request.setConnFrom(request.getConnTo());
        }
        if (request.getConnTo().isEmpty()) {
            request.setConnTo(request.getConnFrom());
        }
        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_FROM,
                    request.getConnFrom());
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_TO,
                    request.getConnTo());
        }

        final List<Object[]> result = query.getResultList();

        final List<DemandNoticeResponseDTO> noticeList = new ArrayList<>(
                result.size());

        if (!result.isEmpty()) {
            DemandNoticeResponseDTO dto = null;
            for (final Object[] objectRow : result) {
                dto = new DemandNoticeResponseDTO();
                dto.setConnectionId((Long) objectRow[0]);
                dto.setConnectionNo((String) objectRow[1]);
                dto.setBillId((Long) objectRow[2]);
                dto.setBillDueDate((Date) objectRow[3]);
                dto.setBillAmount((Double) objectRow[4]);
                dto.setCustName((String) objectRow[5]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[6]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[7]);
                noticeList.add(dto);

            }
        }
        return noticeList;
    }

    @Override
    public List<DemandNoticeResponseDTO> searchAllDemand(
            final DemandNoticeRequestDTO request) {

        final StringBuilder demandPrintQuery = new StringBuilder(
                QueryConstants.DemandNoticeQuery.DEMAND_PRINT);

        if (request.getTrf1() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP1);
        }
        if (request.getTrf2() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP2);
        }
        if (request.getTrf3() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP3);
        }
        if (request.getTrf4() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP4);
        }
        if (request.getTrf5() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP5);
        }

        if (request.getWwz1() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.WWZ1);
        }
        if (request.getWwz2() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.WWZ2);
        }
        if (request.getWwz3() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.WWZ3);
        }
        if (request.getWwz4() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.WWZ4);
        }
        if (request.getWwz5() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.WWZ5);
        }
        if (request.getWdn() > 0) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.NOTICE_TYPE);
        }
        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            demandPrintQuery.append(QueryConstants.DemandNoticeQuery.CONNECTION_BETWEEN);
        }

        final Query query = createQuery(demandPrintQuery.toString());
        query.setParameter(
                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                request.getOrgid());

        if (request.getTrf1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP1,
                    request.getTrf1());
        }
        if (request.getTrf2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP2,
                    request.getTrf2());
        }
        if (request.getTrf3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP3,
                    request.getTrf3());
        }
        if (request.getTrf4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP4,
                    request.getTrf4());
        }
        if (request.getTrf5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP5,
                    request.getTrf5());
        }

        if (request.getWwz1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID1,
                    request.getWwz1());
        }
        if (request.getWwz2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID2,
                    request.getWwz2());
        }
        if (request.getWwz3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID3,
                    request.getWwz3());
        }
        if (request.getWwz4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID4,
                    request.getWwz4());
        }
        if (request.getWwz5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID5,
                    request.getWwz5());
        }
        if (request.getWdn() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.NOTICE_TYPE,
                    request.getWdn());
        }

        if (request.getConnFrom().isEmpty()) {
            request.setConnFrom(request.getConnTo());
        }
        if (request.getConnTo().isEmpty()) {
            request.setConnTo(request.getConnFrom());
        }
        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_FROM,
                    request.getConnFrom());
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_TO,
                    request.getConnTo());
        }
        final List<Object[]> result = query.getResultList();

        final List<DemandNoticeResponseDTO> noticeList = new ArrayList<>();

        if (!result.isEmpty()) {
            DemandNoticeResponseDTO dto = null;
            for (final Object[] objectRow : result) {
                dto = new DemandNoticeResponseDTO();
                dto.setNoticeId((Long) objectRow[0]);
                dto.setConnectionId((Long) objectRow[1]);
                dto.setConnectionNo((String) objectRow[2]);
                dto.setBillId((Long) objectRow[3]);
                dto.setBillDueDate((Date) objectRow[4]);
                dto.setBillAmount((Double) objectRow[5]);
                dto.setCustName((String) objectRow[6]
                        + MainetConstants.WHITE_SPACE
                        + (objectRow[7] != null ? (String) objectRow[7] : MainetConstants.BLANK)
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[8]);
                dto.setCustAddress((String) objectRow[9]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[10]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[11]
                        + (String) objectRow[12]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[13]
                        + MainetConstants.WHITE_SPACE
                        + (String) objectRow[14]);
                dto.setOutstandangFrom((Date) objectRow[15]);
                dto.setOutstandangTo((Date) objectRow[16]);
                dto.setNoticeType((Long) objectRow[17]);
                dto.setNoticeNo((Long) objectRow[18]);
                dto.setNoticeDate((Date) objectRow[19]);
                dto.setNoticeTypeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject((Long) objectRow[17]).getLookUpDesc());
                noticeList.add(dto);

            }
        }
        return noticeList;
    }

    @Override
    public void generateDemandNotice(
            final Map<Long, DemandNotice> demands) {

        DemandNotice updatedEntity = null;
        DemandNoticeHistory demandNoticeHistory = null;

        for (final Entry<Long, DemandNotice> demandNotice : demands
                .entrySet()) {
            demandNoticeHistory = new DemandNoticeHistory();
            if (demandNotice.getValue().getNbNoticeid() == 0) {

                entityManager.persist(demandNotice
                        .getValue());
                demandNoticeHistory
                        .sethStatus(MainetConstants.Transaction.Mode.ADD);// add

            } else {

                updatedEntity = entityManager
                        .merge(demandNotice.getValue());
                demandNoticeHistory
                        .sethStatus(MainetConstants.Transaction.Mode.UPDATE);// update
            }

            updatedEntity = entityManager.find(
                    DemandNotice.class, demandNotice
                            .getValue().getNbNoticeid());
            // AUDIT CODE START
            if (updatedEntity != null) {
                auditService.createHistory(updatedEntity,
                        demandNoticeHistory);
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.DemandNoticeGenarationRepository# findAllPreviousDemand(long)
     */
    @Override
    public Map<Long, DemandNotice> findAllPreviousDemand(
            final long orgId) {

        @SuppressWarnings("unchecked")
        final List<DemandNotice> result = createQuery(
                QueryConstants.DemandNoticeQuery.SELECT_DEMAND_NOTICE)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                                orgId)
                        .getResultList();

        final Map<Long, DemandNotice> notices = new HashMap<>();

        if (!result.isEmpty()) {
            for (final DemandNotice demandNotice : result) {
                notices.put(demandNotice.getCsIdn(),
                        demandNotice);
            }
        }
        return notices;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DemandNotice> getTaxCodeByConnectionId(
            final List<Long> csIdn, final long orgId) {
        List<DemandNotice> result = null;
        result = createQuery(
                QueryConstants.DemandNoticeQuery.SELECT_TAX_CODE)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                                orgId)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.CONNECTION_ID,
                                csIdn)
                        .getResultList();
        return result;
    }

    @Override
    public void deleteDemandNotice(final long connectionId,
            final long orgId) {

        createQuery(
                QueryConstants.DemandNoticeQuery.UPDATE_DEMAND_NOTICE)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                                orgId)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.CONNECTION_ID,
                                connectionId)
                        .setParameter(
                                QueryConstants.DemandNoticeQuery.Parameter.STATUS_FLAG,
                                MainetConstants.Common_Constant.YES)
                        .executeUpdate();

    }

    @Override
    public void updateNoticeDueDate(final Date dueDate, final Date distDate, final long orgid, final long dnId) {
        createQuery("UPDATE DemandNotice d SET d.nbNotduedt = :nbNotduedt,d.nbNotacceptdt=:nbNotacceptdt "
                + " WHERE d.orgid = :orgId AND d.nbNoticeid=:nbNoticeid")
                        .setParameter("nbNotduedt", dueDate)
                        .setParameter("nbNotacceptdt", distDate)
                        .setParameter("orgId", orgid)
                        .setParameter("nbNoticeid", dnId)
                        .executeUpdate();

    }

	@Override
    public List<DemandNoticeResponseDTO> searchAllDefaulterForAscl(
            final DemandNoticeRequestDTO request, final long demandId, final Long csIdn) {

        StringBuilder demandSearchQuery = null;

        if (request.getWdn() == request.getFinalNoticeType()) {
            demandSearchQuery = new StringBuilder(
                    QueryConstants.DemandNoticeQuery.FINAL_DEMAND_SEARCH);
        } else if(csIdn!=null){
        	demandSearchQuery = new StringBuilder(
                    QueryConstants.DemandNoticeQuery.DEMAND_NOTICE_PER_CONN_ID);
        } else{
        	if(!request.getAmountFrom().isEmpty() && !request.getAmountTo().isEmpty()) {
        		demandSearchQuery = new StringBuilder(
                        QueryConstants.DemandNoticeQuery.DEMAND_SEARCH_OUTSTANDING_RANGE);
        	}else {
        		demandSearchQuery = new StringBuilder(
                        QueryConstants.DemandNoticeQuery.DEMAND_SEARCH_ASCL);        		
        	}
        }

        if (request.getTrf1() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP1);
        }
        if (request.getTrf2() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP2);
        }
        if (request.getTrf3() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP3);
        }
        if (request.getTrf4() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP4);
        }
        if (request.getTrf5() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.TRM_GROUP5);
        }

        if (request.getCsz() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.CONNECTION_SIZE);
        }
        if (request.getWmn() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.METER_TYPE);
        }

        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.CONNECTION_BETWEEN);
        }

        if (request.getWwz1() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ1);
        }
        if (request.getWwz2() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ2);
        }
        if (request.getWwz3() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ3);
        }
        if (request.getWwz4() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ4);
        }
        if (request.getWwz5() > 0) {
            demandSearchQuery.append(QueryConstants.DemandNoticeQuery.WWZ5);
        }

        final Query query = createQuery(demandSearchQuery.toString());
        query.setParameter(
                QueryConstants.DemandNoticeQuery.Parameter.ORG_ID,
                request.getOrgid());
        if (request.getWdn() == request.getFinalNoticeType()) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.FINAL_NOTICE_TYPE,
                    demandId);
        }
        //query.setParameter("currDate", new Date(), TemporalType.DATE);
        if (request.getTrf1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP1,
                    request.getTrf1());
        }
        if (request.getTrf2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP2,
                    request.getTrf2());
        }
        if (request.getTrf3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP3,
                    request.getTrf3());
        }
        if (request.getTrf4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP4,
                    request.getTrf4());
        }
        if (request.getTrf5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.TRM_GROUP5,
                    request.getTrf5());
        }

        if (request.getWwz1() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID1,
                    request.getWwz1());
        }
        if (request.getWwz2() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID2,
                    request.getWwz2());
        }
        if (request.getWwz3() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID3,
                    request.getWwz3());
        }
        if (request.getWwz4() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID4,
                    request.getWwz4());
        }
        if (request.getWwz5() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.COD_DWZID5,
                    request.getWwz5());
        }
        if (request.getCsz() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CONNECTION_SIZE,
                    request.getCsz());
        }
        if (request.getWmn() > 0) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.METER_TYPE,
                    request.getWmn());
        }

        if (request.getConnFrom().isEmpty()) {
            request.setConnFrom(request.getConnTo());
        }
        if (request.getConnTo().isEmpty()) {
            request.setConnTo(request.getConnFrom());
        }
        if (!request.getConnFrom().isEmpty()
                || !request.getConnTo().isEmpty()) {
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_FROM,
                    request.getConnFrom());
            query.setParameter(
                    QueryConstants.DemandNoticeQuery.Parameter.CON_TO,
                    request.getConnTo());
        }
        if(!request.getAmountFrom().isEmpty() && !request.getAmountTo().isEmpty()) {
        	request.setAmountFrom(request.getAmountFrom());
        	query.setParameter(QueryConstants.DemandNoticeQuery.Parameter.AMT_FROM, Double.valueOf(request.getAmountFrom()));
            request.setAmountTo(request.getAmountTo());
            query.setParameter(QueryConstants.DemandNoticeQuery.Parameter.AMT_TO, Double.valueOf(request.getAmountTo()));

        }
        if(csIdn!=null) {
        	query.setParameter(QueryConstants.DemandNoticeQuery.Parameter.CS_IDN, csIdn);
        }
        final List<Object[]> result = query.getResultList();

        final List<DemandNoticeResponseDTO> noticeList = new ArrayList<>(
                result.size());

        if (!result.isEmpty()) {
            DemandNoticeResponseDTO dto = null;
            for (final Object[] objectRow : result) {
                dto = new DemandNoticeResponseDTO();
                dto.setConnectionId((Long) objectRow[0]);
                dto.setConnectionNo((String) objectRow[1]);
                dto.setBillId((Long) objectRow[2]);
                dto.setBillDueDate((Date) objectRow[3]);
                dto.setBillAmount((Double) objectRow[4]);
                dto.setCustName((objectRow[5]!=null? (String) objectRow[5]: "")
                        + MainetConstants.WHITE_SPACE
                        + (objectRow[6]!=null? (String) objectRow[6] : "")
                        + MainetConstants.WHITE_SPACE
                        + (objectRow[7]!=null? (String) objectRow[7]:""));
                noticeList.add(dto);

            }
        }
        return noticeList;
    }
    
}
