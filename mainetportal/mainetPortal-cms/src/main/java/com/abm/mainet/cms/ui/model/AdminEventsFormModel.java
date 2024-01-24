package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.News;
import com.abm.mainet.cms.service.INewsService;
import com.abm.mainet.cms.ui.validator.AdminEventsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminEventsFormModel extends AbstractEntryFormModel<News> implements Serializable {

    private static final long serialVersionUID = 4571987312802174705L;

    @Autowired
    private INewsService iAdminNewsService;

    private News news;

    private String mode;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#addForm() Open form for Adding records
     */
    @Override
    public void addForm() {
        final News news = new News();
        final LookUp lookUpObj = getQuickLinkSection(PrefixConstants.Prefix.HNS);
        news.setCpdSection(lookUpObj.getLookUpId());
        setMode(MainetConstants.Transaction.Mode.ADD);
        setNews(news);
        setEntity(news);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#editForm(long) Open form for editing records
     */
    @Override
    public void editForm(final long rowId) {

        final News news = iAdminNewsService.getNewsById(rowId);

        setMode(MainetConstants.Transaction.Mode.UPDATE);
        setNews(news);
        setEntity(news);

    }

    /**
     * @return
     * @throws FrameworkException
     * @see Befor saving set values by using this method
     */
    private boolean saveEvents() throws FrameworkException {

        final News newsObj = getNews();
        return iAdminNewsService.saveOrUpdate(newsObj);

    }

    /**
     * @return
     * @throws FrameworkException
     * @see Befor updating set values by using this method
     */
    private boolean updateEvents() throws FrameworkException {
        try {
            final News newsObj = getNews();

            return iAdminNewsService.saveOrUpdate(newsObj);
        } catch (final FrameworkException ex) {
            throw ex;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#saveOrUpdateForm() To save records
     */
    @Override
    public boolean saveOrUpdateForm() {
        validateBean(getNews(), AdminEventsValidator.class);
        if (hasValidationErrors()) {
            return false;
        }

        if (getMode().equals(MainetConstants.Transaction.Mode.ADD)) {
            if (saveEvents()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (updateEvents()) {
                return true;
            } else {
                return false;
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#delete(long) Delete records from Grid
     */
    @Override
    public void delete(final long rowId) {
        iAdminNewsService.delete(rowId);

    }

    /**
     * @param prefix
     * @return
     */
    private LookUp getQuickLinkSection(final String prefix) {
        LookUp event = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(MainetConstants.NEC.EMPLOYEE)) {
                event = lookUp;
                break;
            }

        }
        return event;

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
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

}
