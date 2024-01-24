/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
public interface INewsService extends Serializable {

    public List<News> getAllNewsByCPDSection(LookUp lookUp, Organisation organisation);

    public News getNewsById(long newsId);

    public boolean delete(long rowId);

    public boolean saveOrUpdate(News newsObj);
}
