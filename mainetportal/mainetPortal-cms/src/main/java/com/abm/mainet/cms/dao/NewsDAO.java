package com.abm.mainet.cms.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
@Repository
public class NewsDAO extends AbstractDAO<News> implements INewsDAO {
    private static final Logger LOG = Logger.getLogger(NewsDAO.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.INewsDAO#getAllNewsBySectionId(com.abm.mainet.ao2.smart.util.LookUp)
     */
    @Override
    public List<News> getAllNewsBySectionId(final LookUp lookUp, final Organisation organisation) {

        final Query query = createQuery(
                "select n from News n where n.cpdSection = ?1 and n.isDeleted = ?2 and n.orgId = ?3 order by newsId asc");
        query.setParameter(1, lookUp.getLookUpId());
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, organisation);
        @SuppressWarnings("unchecked")
        final List<News> news = query.getResultList();
        return news;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.INewsDAO#getNewsByNewsId(long)
     */
    @Override
    public News getNewsByNewsId(final long newsId) {
        final Query query = createQuery("select n from News n where n.newsId = ?1 ");
        query.setParameter(1, newsId);
        @SuppressWarnings("unchecked")
        final List<News> news = query.getResultList();
        if ((news == null) || news.isEmpty()) {
            return null;
        } else {
            return news.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.INewsDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.News)
     */
    @Override
    public boolean saveOrUpdate(final News news) {
        try {
            // this.persist(news);
            entityManager.persist(news);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }
}
