package com.abm.mainet.eip.dao;

import com.abm.mainet.domain.PageMaster;

public interface IHitCounterDao {

	public abstract PageMaster getPageMaster(Long pageId);

	public abstract void updateCount(PageMaster counter);

	public abstract Long getFinalCountOfHits(Long pageId);

	public abstract boolean pageIdExist(Long pageId);

	public boolean updateMyMarathiCount(Long orgId);

	public Long getMarathiCount(Long orgId);
}
