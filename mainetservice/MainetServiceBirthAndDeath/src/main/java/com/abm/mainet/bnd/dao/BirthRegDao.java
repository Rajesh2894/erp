package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.BirthRegistrationCorrection;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.ParentDetail;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;

public interface BirthRegDao {

	public BirthRegistrationEntity getBirthRegApplnDetails(String certno, Long regNo, String year,
			String brApplicationId, Long orgId);

	public List<BirthRegistrationEntity> getBirthRegisteredApplicantList(String certNo, String regNo, String year,Date brDob,String brChildName,Long smServiceId,
			String applicnId, Long orgId);

	List<BirthRegistrationCorrection> getBirthRegisteredAppliDetailFromApplnId(Long applnId, Long orgId);

	List<BirthRegistrationEntity> getBirthRegApplnData(Long brId, Long orgId);

	List<ParentDetail> getParentDtlApplnData(Long brId, Long orgId);

	public List<BirthRegistrationEntity> getBirthRegCorrDetList(BirthRegistrationDTO birthRegDto);

	public List<Object[]> getApplicantDetailsByApplNoAndOrgId(Long applnNO, Long orgId);

}
