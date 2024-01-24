package com.abm.mainet.cms.dao;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.common.dao.IAbstractDAO;

public interface IAdminHomeDAO extends IAbstractDAO<EIPHome> {

    public abstract EIPHome findObject(String activeStatus);

    public abstract void persist(EIPHome entity);

}