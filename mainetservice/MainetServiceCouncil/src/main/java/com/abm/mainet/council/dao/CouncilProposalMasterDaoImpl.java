package com.abm.mainet.council.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.council.domain.CouncilProposalMasterEntity;
import com.abm.mainet.council.repository.CouncilMeetingMemberRepository;

@Repository
public class CouncilProposalMasterDaoImpl extends AbstractDAO<Long> implements ICouncilProposalMasterDao {

    @Autowired
    CouncilMeetingMemberRepository meetingMemberRepository;

    @Transactional
    public void updateTheAgendaIdByProposalIds(List<Long> proposalIds, Long agendaId) {
        final Query query = createQuery(
                "UPDATE CouncilProposalMasterEntity p SET p.agendaId =?1  where p.proposalId in ?2");
        query.setParameter(1, agendaId);
        query.setParameter(2, proposalIds);
        query.executeUpdate();

    }

    @Transactional
    public void updateTheAgendaIdOfProposalHistoryByProposalIds(List<Long> proposalIds, Long agendaId) {
        final Query query = createQuery(
                "UPDATE CouncilProposalMasHistoryEntity p SET p.agendaId =?1  where p.proposalId in ?2");
        query.setParameter(1, agendaId);
        query.setParameter(2, proposalIds);
        query.executeUpdate();
    }

    @Transactional
    public void updateNullOfAgendaIdInProposalByAgendaId(Long agendaId) {
        final Query query = createQuery(
                "UPDATE CouncilProposalMasterEntity p SET p.agendaId = null  where p.agendaId =?1");
        query.setParameter(1, agendaId);
        query.executeUpdate();
    }

    @Transactional
    public void updateNullOfAgendaIdInProposalHistoryByAgendaId(Long agendaId) {
        final Query query = createQuery(
                "UPDATE CouncilProposalMasHistoryEntity p SET p.agendaId =null  where p.agendaId in ?1");
        query.setParameter(1, agendaId);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<CouncilProposalMasterEntity> fetchProposalListByStatus(String proposalStatus, Long orgId) {
        List<CouncilProposalMasterEntity> proposalList = new ArrayList<>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT PM FROM CouncilProposalMasterEntity PM where   PM.agendaId is null and PM.orgId = :orgId ");
            if (proposalStatus != null && !proposalStatus.isEmpty()) {
                jpaQuery.append(" and PM.proposalStatus like :proposalStatus");
            }
            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
            if (proposalStatus != null) {
                hqlQuery.setParameter("proposalStatus", proposalStatus);
            }
            proposalList = hqlQuery.getResultList();

        } catch (Exception e) {
            throw new FrameworkException(e);
        }
        return proposalList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilProposalMasterEntity> searchCouncilProposalData(Long proposalDepId, String proposalNo,
            Date fromDate, Date toDate, String proposalStatus, Long orgid, List<Long> proposalIds,String type) {
        List<CouncilProposalMasterEntity> listProposalMaster = null;
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT pm FROM CouncilProposalMasterEntity pm  where pm.orgId = :orgid ");

            if (Optional.ofNullable(proposalDepId).orElse(0L) != 0) {
                jpaQuery.append(" and pm.proposalDepId = :proposalDepId ");

            }

            if (StringUtils.isNotEmpty(proposalNo)) {
                jpaQuery.append(" and pm.proposalNo like :proposalNo");

            }
            
            if (StringUtils.isNotEmpty(type)) {
                jpaQuery.append(" and pm.proposalType like :type");

            }

            /*
             * if (StringUtils.isNotEmpty(proposalStatus)) { jpaQuery.append(" and pm.proposalStatus like :proposalStatus"); }
             */

            // if getting than first find list of id from CouncilProposalWardEntity
            if (!proposalIds.isEmpty()) {
                jpaQuery.append(" and pm.proposalId in (:proposalIds)");
            }

            if (fromDate != null) {
                jpaQuery.append(" and pm.proposalDate >= :fromDate ");
            }
            if (toDate != null) {
                jpaQuery.append(" and pm.proposalDate <= :toDate ");
            }

            // on load Search record order by latest Entry
            jpaQuery.append(" order by proposalId desc");

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);

            if (Optional.ofNullable(proposalDepId).orElse(0L) != 0) {
                hqlQuery.setParameter("proposalDepId", proposalDepId);
            }

            if (StringUtils.isNotEmpty(proposalNo)) {
                hqlQuery.setParameter("proposalNo", proposalNo);

            }
            
            if (StringUtils.isNotEmpty(type)) {
                hqlQuery.setParameter("type", type);

            }

            /*
             * if (StringUtils.isNotEmpty(proposalStatus)) { hqlQuery.setParameter("proposalStatus", proposalStatus); }
             */

            if (!proposalIds.isEmpty()) {
                hqlQuery.setParameter("proposalIds", proposalIds);
            }

            if (fromDate != null) {
                hqlQuery.setParameter("fromDate", fromDate);
            }

            if (toDate != null) {
                hqlQuery.setParameter("toDate", toDate);
            }

            listProposalMaster = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Reord", exception);
        }
        return listProposalMaster;
    }

}
