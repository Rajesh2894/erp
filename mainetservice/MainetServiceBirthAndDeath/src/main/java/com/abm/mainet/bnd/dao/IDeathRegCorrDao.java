package com.abm.mainet.bnd.dao;
import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.CemeteryMaster;
import com.abm.mainet.bnd.domain.DeceasedMasterCorrection;
import com.abm.mainet.bnd.domain.MedicalMaster;
import com.abm.mainet.bnd.domain.MedicalMasterCorrection;
import com.abm.mainet.bnd.domain.TbBdDeathregCorr;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.domain.TbDeathregTemp;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;

public interface IDeathRegCorrDao {

	public List<TbDeathreg> getDeathRegisteredAppliDetail(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod,String drDeceasedname,Long orgId, Long smServiceId, String DeathWFStatus);
	public List<TbDeathreg> getDeathRegisteredCorrAppliDetail(Long drId, Long orgId);

	public List<TbBdDeathregCorr> getDeathRegisteredAppliDetailFromApplnId(Long applnId, Long orgId);

	public List<MedicalMaster> getDeathRegApplnDataFromMedicalCorr(Long drId, Long orgId);

	public List<MedicalMasterCorrection> getDeathRegApplnDataFromMedicalMasCorr(Long drCorrId, Long orgId);

	public List<DeceasedMasterCorrection> getDeathRegApplnDataFromDecaseCorr(Long drCorrId, Long orgId);
	
	public List<TbDeathreg> getDeathDataForCorr(TbDeathregDTO tbDeathregDTO);
	
	List<TbDeathregTemp> getDeathRegisteredAppliDetailTemp(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod, String drDeceasedname, Long orgId, Long smServiceId, String DeathWFStatus);

	
}
