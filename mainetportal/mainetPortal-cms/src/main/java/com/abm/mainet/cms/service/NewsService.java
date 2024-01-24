/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.INewsDAO;
import com.abm.mainet.cms.domain.News;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
@Service
public class NewsService implements Serializable, INewsService {

    private static final long serialVersionUID = -4396609753000978294L;

    @Autowired
    private INewsDAO newsDAO;

    @Override
    @Transactional
    public List<News> getAllNewsByCPDSection(final LookUp lookUp, final Organisation organisation) {
        return newsDAO.getAllNewsBySectionId(lookUp, organisation);
    }

    @Override
    @Transactional
    public News getNewsById(final long newsId) {
        return newsDAO.getNewsByNewsId(newsId);
    }

    @Override
    @Transactional
    public boolean delete(final long newsId) {
        final News news = getNewsById(newsId);
        news.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        news.updateAuditFields();
        return newsDAO.saveOrUpdate(news);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final News newsObj) {
        newsObj.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        newsObj.updateAuditFields();
        return newsDAO.saveOrUpdate(newsObj);
    }

}
