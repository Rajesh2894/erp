package com.abm.mainet.council.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.council.domain.CouncilMemberCommitteeMasterEntity;

@Repository
public class CouncilMemberCommitteeDaoImpl extends AbstractDAO<Long> implements ICouncilMemberCommitteeDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilMemberCommitteeMasterEntity> searchCouncilMemberCommitteeData(Long memberId,
            Long committeeTypeId, Long orgid, String status) {
        List<CouncilMemberCommitteeMasterEntity> committeeMasterEntities = new ArrayList<>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT cm  FROM CouncilMemberCommitteeMasterEntity cm  where cm.orgId = :orgid AND status = :status");

            if (Optional.ofNullable(memberId).orElse(0L) != 0) {
                jpaQuery.append(" and cm.members.couId =:memberId");
            }

            if (Optional.ofNullable(committeeTypeId).orElse(0L) != 0) {
                jpaQuery.append(" and cm.committeeTypeId = :committeeTypeId");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);
            hqlQuery.setParameter("status", status);

            if (Optional.ofNullable(memberId).orElse(0L) != 0) {
                hqlQuery.setParameter("memberId", memberId);
            }

            if (Optional.ofNullable(committeeTypeId).orElse(0L) != 0) {
                hqlQuery.setParameter("committeeTypeId", committeeTypeId);
            }

            committeeMasterEntities = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Record", exception);
        }
        return committeeMasterEntities;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilMemberCommitteeMasterEntity> fetchCommitteeMemberByIdAndDissolveDate(Long committeeTypeId, Long orgId,
            String status, String memberStatus) {
        List<CouncilMemberCommitteeMasterEntity> entities = new ArrayList<>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "FROM CouncilMemberCommitteeMasterEntity where committeeTypeId = :committeeTypeId and dissolveDate >= CURRENT_DATE and orgId = :orgId AND status = :status AND memberStatus = :memberStatus");
            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("committeeTypeId", committeeTypeId);
            hqlQuery.setParameter("orgId", orgId);
            hqlQuery.setParameter("status", status);
            hqlQuery.setParameter("memberStatus", memberStatus);
            entities = (List<CouncilMemberCommitteeMasterEntity>) hqlQuery.getResultList();
        } catch (Exception e) {
            throw new FrameworkException("Exception occured when fetch committeeMember by id and dissolve date", e);
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CouncilMemberCommitteeMasterEntity checkMemberExistInDissolveDate(Long memberId, Long committeeTypeId, Long orgId,
            String status) {
        List<CouncilMemberCommitteeMasterEntity> results = new ArrayList<>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "FROM CouncilMemberCommitteeMasterEntity where members.couId = :memberId AND committeeTypeId = :committeeTypeId AND dissolveDate <= CURRENT_DATE  AND orgId = :orgId AND status = :status");
            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("memberId", memberId);
            hqlQuery.setParameter("committeeTypeId", committeeTypeId);
            hqlQuery.setParameter("orgId", orgId);
            hqlQuery.setParameter("status", status);
            results = (List<CouncilMemberCommitteeMasterEntity>) hqlQuery.getResultList();
            if (results.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            throw new FrameworkException("Exception occured when check exist member based on dissolve date or not", e);
        }
        return results.get(0);
    }

}
