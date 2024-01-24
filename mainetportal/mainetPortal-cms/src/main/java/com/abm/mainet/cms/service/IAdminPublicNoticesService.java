/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 *
 */
public interface IAdminPublicNoticesService {
    public List<PublicNotices> getAllPublicNotices(Organisation organisation, String flag);

    public List<PublicNotices> getCitizenPublicNotices(Organisation organisation);

    public List<PublicNoticesHistory> getGuestCitizenPublicNotices(Organisation organisation);

    public PublicNotices getPublicNotices(long pnId);

    public boolean delete(long pnId);

    public boolean saveOrUpdate(PublicNotices publicNotices);

    public List<PublicNotices> getPublicNoticesByDeptId(long deptId, Organisation organisation);

	boolean checkSequence(long sequenceNo, Long orgId);

}
