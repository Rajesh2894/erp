package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IPublicNoticesDAO {

    public abstract List<PublicNotices> getPublicNotices(Organisation organisation);

    public abstract List<PublicNoticesHistory> getGuestPublicNotices(Organisation organisation);

    public abstract List<PublicNotices> getPublicNoticesByDeptId(long deptId, Organisation organisation);

    public abstract boolean saveOrUpdate(PublicNotices publicNotices);

    public abstract PublicNotices getpublicNoticesById(long pnId);

    List<PublicNotices> getAdminPublicNotices(Organisation organisation, String flag);

	boolean checkSequence(long sequenceNo, Long orgId);


}