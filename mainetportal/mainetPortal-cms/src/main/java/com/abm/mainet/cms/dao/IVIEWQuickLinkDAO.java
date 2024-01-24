package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.abm.mainet.common.domain.Organisation;

public interface IVIEWQuickLinkDAO {

    public abstract List<VIEWQuickLink> getallQuicklink(Organisation org);

	List<Object[]> getallQuicklinkSuda(Organisation org);

}