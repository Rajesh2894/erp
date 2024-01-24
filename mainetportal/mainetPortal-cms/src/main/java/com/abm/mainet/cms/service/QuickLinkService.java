
package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.ILinkMasterDAO;
import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
@Service
public class QuickLinkService implements IQuickLinkService {

    @Autowired
    private ILinkMasterDAO linkMasterDAO;

    @Override
    @Transactional
    public List<LinksMaster> getAllLinkMasterByCPDSection(final LookUp lookUp, final Organisation organisation, String flag) {
        return linkMasterDAO.getAllLinkMasterBySectionId(lookUp, organisation, flag);
    }

    @Override
    @Transactional
    public LinksMaster getLinkMaster(final long linkid) {
        return linkMasterDAO.getLinkMasterByLinkId(linkid);
    }

    @Override
    @Transactional
    public boolean delete(final long quickLinkId) {
        final LinksMaster linksMaster = getLinkMaster(quickLinkId);
        linksMaster.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        linksMaster.updateAuditFields();
        return linkMasterDAO.saveOrUpdate(linksMaster);

    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final LinksMaster linksMaster) {
        linksMaster.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        linksMaster.updateAuditFields();
        return linkMasterDAO.saveOrUpdate(linksMaster);

    }

    @Override
    @Transactional
    public List<LinksMaster> getLinkMasterByLinkOrder(final Double linkOrder, final LinksMaster entity,
            final Organisation organisation) {
        return linkMasterDAO.getLinkMasterByLinkOrder(linkOrder, entity, organisation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinksMaster> getSerchContentList(final String searchText, final Organisation organisation, final int langId) {
        return linkMasterDAO.getSerchContentList(searchText, organisation, langId);
    }
}
