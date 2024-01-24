package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.BirthRegistrationEntity;

public interface InclusionOfChildDao {
	public List<BirthRegistrationEntity> getBirthRegisteredApplicantList(String certNo, String regNo, String regDate,Date brDob,
			String applicnId, Long smServiceId,Long orgId);

	public void updateBirthRegEntity(Long brId, String childName, String childNameMar, Long orgId);

	public void updateBirthRegEntityOnApproval(Long brId, String brChildName, String brChildNameMar,
			String birthWFStatus, String brStatus, String brCorrectionFlg, Date brCorrnDate, Long orgId);
	
}
