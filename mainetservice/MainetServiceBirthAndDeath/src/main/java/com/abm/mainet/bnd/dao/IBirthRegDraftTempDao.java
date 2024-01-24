package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.BirthRegistrationEntityTemp;

public interface IBirthRegDraftTempDao {

	List<BirthRegistrationEntityTemp> getBirthRegisteredApplicantListTemp(String certNo, String brRegNo, String year,
			Date brDob, String brChildName, Long smServiceId, String applnId, Long orgId);

}
