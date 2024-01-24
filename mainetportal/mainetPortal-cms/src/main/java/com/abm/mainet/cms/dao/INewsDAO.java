package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface INewsDAO {

    /**
     * @param lookUp
     * @return To get {@link News} entity object for given lookUp.getLookUpId
     */
    public abstract List<News> getAllNewsBySectionId(LookUp lookUp, Organisation organisation);

    /**
     * @param newsId
     * @return To get {@link News} entity object for given news id
     */
    public abstract News getNewsByNewsId(long newsId);

    /**
     * @param news
     * @return
     */
    public abstract boolean saveOrUpdate(News news);

}