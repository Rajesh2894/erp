/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Bhavesh.Gadhe
 */
public interface IEIPContactUsService {
    public void saveContactUs(EIPContactUs contactUs);

    public List<EIPContactUsHistory> getContactUsorg(Organisation organisation);

    public void saveEIPuserContactus(EipUserContactUs eipUserContactUs);

    public List<EIPContactUs> getContactList(Organisation organisation, String flag);

    public EIPContactUs editContactInfoDetails(long rowId, Organisation organisation);

    public boolean hasSequenceExist(String flag, Double sequenceNo, Organisation organisation);

    public boolean delete(long rowId, Organisation organisation);

    public List<EIPContactUsHistory> getContactUslistBy(Organisation organisation);

    List<EIPContactUsHistory> getAllContactUsListByOrganisation(Organisation organisation);

	List<EIPContactUs> getContactListByOrganisation(Organisation organisation);
}
