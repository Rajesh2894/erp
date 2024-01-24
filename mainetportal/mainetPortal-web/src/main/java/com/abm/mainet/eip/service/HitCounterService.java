package com.abm.mainet.eip.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.domain.PageMaster;
import com.abm.mainet.eip.dao.IHitCounterDao;
import com.abm.mainet.ui.controller.HitCounterCookiesController;

@Service
@Scope("prototype")
public class HitCounterService implements IHitCounterService {
	private static final Logger LOGGER = Logger.getLogger(HitCounterService.class);
    @Autowired
    IHitCounterDao iHitCounterDao;

    @Override
    @Transactional
    public PageMaster getPageMaster(final Long pageId) {
        return iHitCounterDao.getPageMaster(pageId);
    }

    @Override
    @Transactional
    public void updateCount(final Long pageId, final PageMaster counter) {
        final long totalCount = counter.getTotalCount() == null ? 0 : counter.getTotalCount();
        LOGGER.info("Counter Update Method Start :"+totalCount);
        counter.setTotalCount(totalCount + 1);
        iHitCounterDao.updateCount(counter);
        LOGGER.info("Counter Update Method End :"+totalCount);
    }

    @Override
    @Transactional
    public Long getFinalCountOfHits(final Long pageId) {
        return iHitCounterDao.getFinalCountOfHits(pageId);
    }

    @Override
    @Transactional
    public boolean pageIdExist(final Long pageId) {
        return iHitCounterDao.pageIdExist(pageId);
    }

	@Override
	 @Transactional
	public boolean updateMyMarathiCount(Long orgid) {
		return iHitCounterDao.updateMyMarathiCount(orgid);
		
	}

	@Override
	 @Transactional
	public Long getMyMarathiCount(Long orgid) {
		
		return iHitCounterDao.getMarathiCount(orgid);
	}
}
