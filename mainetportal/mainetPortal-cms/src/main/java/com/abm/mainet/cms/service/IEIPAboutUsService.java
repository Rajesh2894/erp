/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author bhavesh.gadhe
 *
 */
public interface IEIPAboutUsService extends Serializable {
    public EIPAboutUs getAboutUs(Organisation organisation, String isDeleted);

    public EIPAboutUsHistory getGuestAboutUs(Organisation organisation, String isDeleted);

    public void saveAboutUs(EIPAboutUs aboutUs, String chekkerflag);
}
