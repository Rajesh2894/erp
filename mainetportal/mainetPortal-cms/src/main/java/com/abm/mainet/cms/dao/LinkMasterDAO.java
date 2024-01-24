package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.domain.LinksMasterHistory;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Repository
public class LinkMasterDAO extends AbstractDAO<LinksMaster> implements ILinkMasterDAO {

    private static final Logger LOG = Logger.getLogger(LinkMasterDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ILinkMasterDAO#getAllLinkMasterBySectionId(com.abm.mainet.ao2.smart.util.LookUp)
     */
    @Override
    public List<LinksMaster> getAllLinkMasterBySectionId(final LookUp lookUp, final Organisation organisation, String flag) {
        final StringBuilder queryAppender = new StringBuilder(
                "select l from LinksMaster l where l.cpdSection = ?1 and l.isDeleted = ?2 and l.orgId = ?3 ");
        if (flag != null) {
            queryAppender
                    .append("and l.chekkerflag =?4");
        } else {
            queryAppender
                    .append("and l.chekkerflag is null");
        }
        queryAppender
                .append(" order by l.linkId asc, l.linkOrder asc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, lookUp.getLookUpId());
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, organisation);
        if (flag != null) {
            query.setParameter(4, flag);
        }
        final List<LinksMaster> linksMaster = query.getResultList();

        return linksMaster;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ILinkMasterDAO#getLinkMasterByLinkId(long)
     */
    @Override
    public LinksMaster getLinkMasterByLinkId(final long linkid) {
        final LinksMaster linksMaster = findById(linkid);
        return linksMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ILinkMasterDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.LinksMaster)
     */
    @Override
    public boolean saveOrUpdate(LinksMaster linksMaster) {
        try {

            LinksMasterHistory linksMasterHistory = new LinksMasterHistory();
            if (linksMaster.getLinkId() == 0L) {
                linksMasterHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.persist(linksMaster);

            } else {
                if (linksMaster.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    linksMasterHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    linksMasterHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                if (linksMaster.getCheckerFlag1() != null && linksMaster.getCheckerFlag1().equalsIgnoreCase("Y")) {
                    linksMaster = entityManager.merge(linksMaster);
                } else {
                    linksMaster.setChekkerflag(null);
                    linksMaster = entityManager.merge(linksMaster);
                }
            }
            auditService.createHistory(linksMaster, linksMasterHistory);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.ILinkMasterDAO#getLinkMasterByLinkOrder(java.lang.Double,
     * com.abm.mainet.eip.domain.core.LinksMaster)
     */
    @Override
    public List<LinksMaster> getLinkMasterByLinkOrder(final Double linkOrder, final LinksMaster entity,
            final Organisation organisation) {
        final Query query = createQuery(
                "select l from LinksMaster l where l.isDeleted = ?1 and l.orgId = ?2 and l.linkId <> ?3 and l.linkOrder = ?4 ");
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);
        query.setParameter(3, entity.getId());
        query.setParameter(4, linkOrder.doubleValue());

        @SuppressWarnings("unchecked")
        final List<LinksMaster> linksMaster = query.getResultList();
        return linksMaster;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LinksMaster> getSerchContentList(final String searchText, final Organisation organisation, final int langId) {

        final StringBuilder str = new StringBuilder(
                "select l from  LinksMaster l where l.orgId=:orgId and l.isDeleted = :isDeleted");
        if (langId == 1) {
            str.append(" and UPPER(l.linkTitleEg) like CONCAT('%',UPPER(:text),'%')");
        } else {
            str.append(" and UPPER(l.linkTitleReg) like CONCAT('%',UPPER(:text),'%')");
        }
        final Query query = createQuery(str.toString());
        query.setParameter("orgId", organisation);
        query.setParameter("isDeleted", MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter("text", searchText);
        final List<LinksMaster> linkList = query.getResultList();
        for (final LinksMaster link : linkList) {
            link.setSubLinkMasters(getSubLink(link.getLinkId()));
        }
        return linkList;
    }

    private List<SubLinkMaster> getSubLink(final long linkId) {

        final StringBuilder builder = new StringBuilder(
                "select s.id, s.subLinkNameEn, s.subLinkNameRg from SubLinkMaster s where s.linksMaster.linkId=?1 and s.isDeleted = ?2  "
                        + "and s.orgId = ?3 ");

        builder.append(
                " and s.id not in (select sm.subLinkMaster.id from SubLinkMaster sm where sm.isDeleted = ?2 and sm.orgId = ?3 and sm.subLinkMaster.id is not null)");

        final Query query = createQuery(builder.toString());
        query.setParameter(1, linkId);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, UserSession.getCurrent().getOrganisation());
        @SuppressWarnings("unchecked")
        final List<Object[]> lisObject = query.getResultList();
        final List<SubLinkMaster> subList = new ArrayList<>();
        SubLinkMaster linkMaster = null;

        for (final Object object[] : lisObject) {

            linkMaster = new SubLinkMaster();
            linkMaster.setId((Long) object[0]);
            linkMaster.setSubLinkNameEn(object[1] + MainetConstants.BLANK);
            linkMaster.setSubLinkNameRg(object[2] + MainetConstants.BLANK);
            subList.add(linkMaster);
        }
        return subList;
    }
}
