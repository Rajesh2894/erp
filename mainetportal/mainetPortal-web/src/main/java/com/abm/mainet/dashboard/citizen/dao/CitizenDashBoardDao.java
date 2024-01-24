package com.abm.mainet.dashboard.citizen.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

@Repository
public class CitizenDashBoardDao extends AbstractDAO<Object> implements ICitizenDashBoardDao {

    @Override
    public List<Object[]> getApplicationList(final String flag, final Employee employee, final Organisation organisation) {

        final StringBuilder builder = new StringBuilder(
                "SELECT a.pamId,a.pamApplicationDate,a.pamApplicationId ,a.smServiceId,a.orgId,a.pamSlaDate,a.userId,a.pamApplicationStatus,ps.shortName,ps.serviceName,ps.serviceNameReg FROM ApplicationPortalMaster a, PortalService ps "
                        + "WHERE a.smServiceId=:ps.serviceId AND a.orgId.orgid=ps.serviceOrgId and a.userId = ?1 and a.orgId=?2 and ");
        if (flag.equals(MainetConstants.DASHBOARD.P)) {
            builder.append(" (a.pamPaymentStatus is null or a.pamPaymentStatus in ?3 )");
        } else {
            builder.append(" (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?3)");
        }
        builder.append("order by a.pamId desc");
        final Query query = createQuery(builder.toString());
        query.setParameter(1, employee);
        query.setParameter(2, organisation);
        query.setParameter(3, MainetConstants.DASHBOARD.N);
        @SuppressWarnings("unchecked")
		final List<Object[]> applicationPortalMasters = query.getResultList();
        if ((applicationPortalMasters != null) && !applicationPortalMasters.isEmpty()) {
            for (final Object[] obj : applicationPortalMasters) {
                if (obj[6] != null) {
                    Hibernate.initialize(obj[6]);
                }
            }
        }
        return applicationPortalMasters;
    }

    @Override
    public int countForApplicationStatus(final String flag, final Employee employee, final Organisation organisation) {
        final StringBuilder builder = new StringBuilder(
                "select count(a.pamId) from ApplicationPortalMaster a where  a.userId = ?1 and a.orgId=?2 and ");
        if (flag.equals(MainetConstants.DASHBOARD.P)) {
            builder.append(" (a.pamPaymentStatus is null or a.pamPaymentStatus in ?3 )");
        } else {
            builder.append(" (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?3 )");
        }
        final Query query = createQuery(builder.toString());
        query.setParameter(1, employee);
        query.setParameter(2, organisation);
        query.setParameter(3, MainetConstants.DASHBOARD.N);
        final Long count = (Long) query.getSingleResult();
        return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getApplicationListForAdmin(final String flag, final Organisation organisation) {

        final StringBuilder builder = new StringBuilder(
                "SELECT a.pamId,a.pamApplicationDate,a.pamApplicationId ,a.smServiceId,a.orgId,a.pamSlaDate,a.userId,a.pamApplicationStatus,ps.shortName,ps.serviceName,ps.serviceNameReg FROM ApplicationPortalMaster a, PortalService ps "
                        + "WHERE a.smServiceId=ps.serviceId AND a.orgId.orgid=ps.serviceOrgId and  a.orgId=?1 and ");
        if (flag.equalsIgnoreCase(MainetConstants.DASHBOARD.PP)) {
            builder.append(" (a.pamPaymentStatus is null or a.pamPaymentStatus in ?2 ) order by a.pamId desc");
        } else if (flag.equalsIgnoreCase(MainetConstants.DASHBOARD.AC)) {
            builder.append(
                    " (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?2) and UPPER(a.pamApplicationStatus) in (?3) order by a.pamId desc");
        } else {
            builder.append(
                    " (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?2) and  UPPER(a.pamApplicationStatus) not in (?3) ORDER BY a.pamSlaDate ASC NULLS LAST ");
        }
        final Query query = createQuery(builder.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.DASHBOARD.N);
        if (!flag.equalsIgnoreCase(MainetConstants.DASHBOARD.PP)) {
            query.setParameter(3,
                    Arrays.asList(new String[] { MainetConstants.DASHBOARD.COMPLETED, MainetConstants.DASHBOARD.CL_REJECTED }));
        }
        final List<Object[]> applicationPortalMasters = query.getResultList();
        if ((applicationPortalMasters != null) && !applicationPortalMasters.isEmpty()) {
            for (final Object[] obj : applicationPortalMasters) {
                if (obj[6] != null) {
                    Hibernate.initialize(obj[6]);
                }
            }
        }
        return applicationPortalMasters;
    }

    @Override
    public int countForApplicationStatusForAdmin(final String flag, final Organisation organisation) {
        final StringBuilder builder = new StringBuilder(
                "select count(a.pamId) from ApplicationPortalMaster a where  a.orgId=?1 and ");
        if (flag.equalsIgnoreCase(MainetConstants.DASHBOARD.PP)) {
            builder.append(" (a.pamPaymentStatus is null or a.pamPaymentStatus in ?2 ) ");
        } else if (flag.equalsIgnoreCase(MainetConstants.DASHBOARD.AC)) {
            builder.append(
                    " (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?2) and UPPER(a.pamApplicationStatus) in ( ?3 ) ");
        } else {
            builder.append(
                    " (a.pamPaymentStatus is not null and a.pamPaymentStatus not in ?2) and  UPPER(a.pamApplicationStatus) not in (?3) ");
        }
        final Query query = createQuery(builder.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.DASHBOARD.N);
        if (!flag.equalsIgnoreCase(MainetConstants.DASHBOARD.PP)) {
            query.setParameter(3,
                    Arrays.asList(new String[] { MainetConstants.DASHBOARD.COMPLETED, MainetConstants.DASHBOARD.CL_REJECTED }));
        }
        final Long count = (Long) query.getSingleResult();
        return count.intValue();
    }
}
