package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface ILinkMasterDAO {

    /**
     * @param lookUp
     * @param flag
     * @return
     */
    public abstract List<LinksMaster> getAllLinkMasterBySectionId(LookUp lookUp, Organisation organisation, String flag);

    /**
     * @param linkid
     * @return
     */
    public abstract LinksMaster getLinkMasterByLinkId(long linkid);

    /**
     * @param linksMaster
     * @return
     */
    public abstract boolean saveOrUpdate(LinksMaster linksMaster);

    public abstract List<LinksMaster> getLinkMasterByLinkOrder(Double linkOrder, LinksMaster entity, Organisation organisation);

    List<LinksMaster> getSerchContentList(String searchText, Organisation organisation, int langId);

}