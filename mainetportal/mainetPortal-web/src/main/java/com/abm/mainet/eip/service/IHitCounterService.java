package com.abm.mainet.eip.service;

import com.abm.mainet.domain.PageMaster;

public interface IHitCounterService {

    public PageMaster getPageMaster(Long pageId);

    public void updateCount(Long pageId, PageMaster counter);

    public Long getFinalCountOfHits(Long pageId);

    public boolean pageIdExist(Long pageId);


    public boolean updateMyMarathiCount(Long orgid);

	public Long getMyMarathiCount(Long orgid);

}
