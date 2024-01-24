package com.abm.mainet.bnd.dao;

import com.abm.mainet.bnd.domain.BirthRegistrationEntity;

public interface IssuenceOfBirthCertificateDao {
	public BirthRegistrationEntity getBirthRegisteredApplicantList(String certNo, String regNo, String regDate,
			String applicnId, Long orgId);
	
	  Long  getNoOfRequestCopies(String applicnId,Long orgId);
}
