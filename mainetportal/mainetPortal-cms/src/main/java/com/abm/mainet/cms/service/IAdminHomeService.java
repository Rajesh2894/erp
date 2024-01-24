/**
 *
 */
package com.abm.mainet.cms.service;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.common.util.LookUp;

/**
 * @author Pranit.Mhatre
 */
public interface IAdminHomeService {
    public EIPHome getObject();

    public void saveObject(EIPHome entity);

    public LookUp getHomeContent();

}
