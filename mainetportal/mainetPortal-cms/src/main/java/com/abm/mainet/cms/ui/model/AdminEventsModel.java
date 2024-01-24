package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.cms.service.INewsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminEventsModel extends AbstractFormModel {

    private static final long serialVersionUID = -8538499336887227069L;

    @Autowired
    private INewsService iAdminNewsService;

    private News news;
    private List<News> newsList;

    /**
     * @param prefix
     * @return Objects of entity {@link : LookUp}
     */
    private LookUp getEventsSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(MainetConstants.NEC.EMPLOYEE)) {
                quickLink = lookUp;
                break;
            }
        }
        return quickLink;

    }

    /**
     * @return List of entity {@link : News}
     */
    public List<News> generateEventList() {
        final LookUp lookUp = getEventsSection(PrefixConstants.Prefix.HNS);
        final List<News> listNews = iAdminNewsService.getAllNewsByCPDSection(lookUp, UserSession.getCurrent().getOrganisation());
        setNewsList(listNews);
        return listNews;
    }

    /**
     * @return the news
     */
    public News getNews() {
        return news;
    }

    /**
     * @param news the news to set
     */
    public void setNews(final News news) {
        this.news = news;
    }

    /**
     * @return the newsList
     */
    public List<News> getNewsList() {
        return newsList;
    }

    /**
     * @param newsList the newsList to set
     */
    public void setNewsList(final List<News> newsList) {
        this.newsList = newsList;
    }

}
