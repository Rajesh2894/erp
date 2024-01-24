package com.abm.mainet.council.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;

@Repository
public class CouncilAgendaMasterDaoImpl extends AbstractDAO<Long> implements ICouncilAgendaMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilAgendaMasterEntity> searchCouncilAgendaMasterData(Long committeeTypeId, String agendaNo,
            String fromDate, String toDate, Long orgid) {
        List<CouncilAgendaMasterEntity> councilAgendaMasterList = new ArrayList<CouncilAgendaMasterEntity>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT ca FROM CouncilAgendaMasterEntity ca  where ca.orgId = :orgid ");

            if (Optional.ofNullable(committeeTypeId).orElse(0L) != 0) {
                jpaQuery.append(" and ca.committeeTypeId like :committeeTypeId");
            }

            String agendaNoStr = Optional.ofNullable(agendaNo).map(Object::toString).orElse("");
            int agendaNoInt = 0;

            if (!agendaNoStr.isEmpty()) {
                try {
                    agendaNoInt = Integer.parseInt(agendaNoStr);
                } catch (NumberFormatException e) {
                    // Handle the case when agendaNo is not a valid integer
                }
            }

            if (agendaNoInt != 0 || !agendaNoStr.isEmpty()) {
                jpaQuery.append(" and ca.agendaNo = :agendaNo ");
            }
            // ask to mam date is compare to created or updated date

            if (StringUtils.isNotEmpty(fromDate)) {
                jpaQuery.append(" and ca.agendaDate >= :fromDate ");
            }
            if (StringUtils.isNotEmpty(toDate)) {
                jpaQuery.append(" and ca.agendaDate <= :toDate ");
            }

            // order by set
            jpaQuery.append(" order by agendaId desc");

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);

            if (Optional.ofNullable(committeeTypeId).orElse(0L) != 0) {
                hqlQuery.setParameter("committeeTypeId", committeeTypeId);
            }

            String agendaNoValue = Optional.ofNullable(agendaNo).map(Object::toString).orElse("");
            int intValue = 0;

            try {
                intValue = Integer.parseInt(agendaNoValue);
            } catch (NumberFormatException e) {
                // Handle the case when agendaNoValue is not a valid integer
            }

            if (!agendaNoValue.isEmpty() || intValue != 0) {
                hqlQuery.setParameter("agendaNo", agendaNoValue);
            }

            if (StringUtils.isNotEmpty(fromDate)) {
                hqlQuery.setParameter("fromDate", UtilityService.convertStringDateToDateFormat(fromDate));
            }

            if (StringUtils.isNotEmpty(toDate)) {
                hqlQuery.setParameter("toDate", UtilityService.convertStringDateToDateFormat(toDate));
            }

            councilAgendaMasterList = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Record", exception);
        }
        return councilAgendaMasterList;
    }

}
