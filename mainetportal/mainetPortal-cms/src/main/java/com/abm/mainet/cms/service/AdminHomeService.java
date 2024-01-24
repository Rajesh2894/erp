package com.abm.mainet.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IAdminHomeDAO;
import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.LookUp;

/**
 * @author Pranit.Mhatre
 */
@Service
public class AdminHomeService implements IAdminHomeService {

    @Autowired
    private IAdminHomeDAO dao;

    @Override
    @Transactional
    public EIPHome getObject() {
        return dao.findObject(MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    @Transactional
    public void saveObject(final EIPHome entity) {
        entity.updateAuditFields();
        entity.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        dao.persist(entity);
    }

    @Override
    @Transactional
    public LookUp getHomeContent() {
        final LookUp lookUp = new LookUp();

        final EIPHome eipHome = dao.findObject(MainetConstants.IsDeleted.NOT_DELETE);

        if (eipHome != null) {
            lookUp.setLookUpId(eipHome.getId());
            lookUp.setDescLangFirst(eipHome.getDescriptionEn());
            lookUp.setDescLangSecond(eipHome.getDescriptionReg());
        }

        return lookUp;
    }

}
