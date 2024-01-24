package com.abm.mainet.council.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.council.domain.CouncilMemberMasterEntity;

@Repository
public class CouncilMemberMasterDaoImpl extends AbstractDAO<Long> implements ICouncilMemberMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilMemberMasterEntity> searchCouncilMasterData(String couMemName, Long couMemberType, Long couPartyAffilation,
            Long orgid, Long couEleWZId1, Long couEleWZId2) {

        List<CouncilMemberMasterEntity> listCouncilMaster = null;
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT cm FROM CouncilMemberMasterEntity cm  where cm.orgId = :orgid ");

            if (StringUtils.isNotEmpty(couMemName)) {
                jpaQuery.append(" and cm.couMemName like concat('%',:couMemName,'%')");
               
            }

            if (Optional.ofNullable(couMemberType).orElse(0L) != 0) {
                jpaQuery.append(" and cm.couMemberType = :couMemberType ");

            }

            if (Optional.ofNullable(couPartyAffilation).orElse(0L) != 0) {
                jpaQuery.append(" and cm.couPartyAffilation = :couPartyAffilation ");

            }

            if (Optional.ofNullable(couEleWZId1).orElse(0L) != 0) {
                jpaQuery.append(" and cm.couEleWZId1 = :couEleWZId1 ");

            }

            if (Optional.ofNullable(couEleWZId2).orElse(0L) != 0) {
                jpaQuery.append(" and cm.couEleWZId2 = :couEleWZId2 ");

            }

            // on load Search record order by latest Entry
            jpaQuery.append(" order by couId desc");

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);

            if (StringUtils.isNotEmpty(couMemName)) {
                hqlQuery.setParameter("couMemName", couMemName);

            }
            if (Optional.ofNullable(couMemberType).orElse(0L) != 0) {
                hqlQuery.setParameter("couMemberType", couMemberType);

            }
            if (Optional.ofNullable(couPartyAffilation).orElse(0L) != 0) {
                hqlQuery.setParameter("couPartyAffilation", couPartyAffilation);

            }

            if (Optional.ofNullable(couEleWZId1).orElse(0L) != 0) {
                hqlQuery.setParameter("couEleWZId1", couEleWZId1);

            }
            if (Optional.ofNullable(couEleWZId2).orElse(0L) != 0) {
                hqlQuery.setParameter("couEleWZId2", couEleWZId2);

            }
            listCouncilMaster = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Reord", exception);
        }
        return listCouncilMaster;
    }

}
