/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
public interface IQuickLinkService {

    public List<LinksMaster> getAllLinkMasterByCPDSection(LookUp lookUp, Organisation organisation, String flag);

    public LinksMaster getLinkMaster(long linkid);

    public boolean delete(long quickLinkId);

    public boolean saveOrUpdate(LinksMaster linksMaster);

    public List<LinksMaster> getLinkMasterByLinkOrder(Double linkOrder, LinksMaster entity, Organisation organisation);

    public List<LinksMaster> getSerchContentList(String searchText, Organisation organisation, int langId);
}
