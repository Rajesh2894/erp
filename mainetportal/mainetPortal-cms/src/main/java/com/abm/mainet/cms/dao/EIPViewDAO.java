package com.abm.mainet.cms.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPEmpRightView;
import com.abm.mainet.cms.domain.EIPOnlineServicesView;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.UserSession;

/**
 * @author rajendra.bhujbal
 * @since 06 May 2014
 */
@Repository
@SuppressWarnings("unchecked")
public class EIPViewDAO extends AbstractDAO<EIPOnlineServicesView> implements IEIPViewDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPViewDAO#getEIPOnlineServiceViewForCurrentOrg()
     */

    @Override
    public List<EIPOnlineServicesView> getEIPOnlineServiceViewForCurrentOrg(final Organisation org) {

        final Query query = createQuery(
                "select ep from EIPOnlineServicesView ep where ep.orgId =?1 or ep.orgId is null order by ep.mntparentid asc ");
        query.setParameter(1, org);
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPViewDAO#getEIPEMpRightsViewForEmployee(com.abm.mainet.domain.core.Employee)
     */
    @Override
    public List<EIPEmpRightView> getEIPEMpRightsViewForEmployee(final Employee employee, final Organisation org) {
        final Query query = createQuery(
                "Select ev.orgId,ev.menuprm2,ev.menuprm1,ev.dpDeptid,ev.smServiceId,ev.mntrootid,ev.mntparentid,ev.smfsrno,ev.smfcategory,ev.smfid,ev.smfname,ev.smfnameMar,ev.smfaction,ev.smfdescription,ev.smfflag,ev.depth from EIPEmpRightView ev where ev.empid = ?1 and ev.orgId = ?2 ");
        query.setParameter(1, employee.getEmpId());
        query.setParameter(2, org.getOrgid());
        return query.getResultList();
    }

    @Override
    public List<EIPOnlineServicesView> getEIPOnlineServiceBySmfId(final Long smfid) {

        final Query query = createQuery("select ep from EIPOnlineServicesView ep where ep.smfid =?1 ");
        query.setParameter(1, smfid);
        return query.getResultList();

    }

    @Override
    public Map<Long, String> getGroupList() {
        final Map<Long, String> list = new LinkedHashMap<>();
        final Query query = createQuery("select g from GroupMaster g where g.orgId =?1  and g.grStatus = ?2");
        query.setParameter(1, UserSession.getCurrent().getOrganisation());
        query.setParameter(2, "A");
        final List<GroupMaster> master = query.getResultList();
        if ((master != null) && !master.isEmpty()) {
            for (final GroupMaster mas : master) {
                list.put(mas.getGmId(), mas.getGrCode());
            }

        }
        return list;
    }

}
