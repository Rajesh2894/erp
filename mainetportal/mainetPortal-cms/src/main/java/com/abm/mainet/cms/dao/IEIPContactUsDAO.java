package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.common.domain.Organisation;

public interface IEIPContactUsDAO {

    public abstract void saveOrUpdateContactUs(EIPContactUs contactUs);

    public List<EIPContactUsHistory> getContactUsorg(Organisation organisation);

    public abstract void saveEIPUserContactus(EipUserContactUs eipUserContactUs);

    public List<EIPContactUs> getContactList(Organisation organisation, String flag);

    public List<EIPContactUs> getContactListchekker(Organisation organisation);

    public EIPContactUs editContactInfoDetails(long rowId, Organisation organisation);

    public boolean hasSequenceExist(String flag, Long sequenceNo, Organisation organisation);

	public boolean hasSequenceExist(String flag, Double sequenceNo, Organisation organisation);

    public abstract List<EIPContactUsHistory> getContactUsListBy(Organisation organisation);

    List<EIPContactUsHistory> getAllContactUsListByOrganisation(Organisation organisation);

	List<EIPContactUs> getContactListByOrganisation(Organisation organisation);

}