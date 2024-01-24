package com.abm.mainet.buildingplan.dao;

import java.util.List;

import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.TbAuthUserMasEntity;
import com.abm.mainet.buildingplan.domain.TbDevPerGrntMasEntity;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.buildingplan.domain.TbDirectorMasEntity;
import com.abm.mainet.buildingplan.domain.TbStkhldrMasEntity;

public interface DeveloperRegRepository {

	TbDeveloperRegistrationEntity saveDeveloperRegForm(TbDeveloperRegistrationEntity devRegEntity);

	List<TbStkhldrMasEntity> saveStkhldrMasData(List<TbStkhldrMasEntity> stkhldrMasEntity);

	List<TbDirectorMasEntity> saveDirectorMasData(List<TbDirectorMasEntity> directorMasEntityList);

	List<TbAuthUserMasEntity> saveAuthUserMasData(List<TbAuthUserMasEntity> authUserMasEntityList);

	void saveDevPerGrntMasData(List<TbDevPerGrntMasEntity> devPerGrntMasEntityList);

	List<TbStkhldrMasEntity> getStkhldrMasEntitiesByDevRegNo(Long tcpDevRegNo);

	List<TbDirectorMasEntity> getDirectorMasEntitiesByDevRegNo(Long tcpDevRegNo);

	List<TbAuthUserMasEntity> getAuthUserMasEntitiesByDevRegNo(Long tcpDevRegNo);

	List<TbDeveloperRegistrationEntity> getDeveloperRegistrationByCreatedById(Long created_by);

	List<LicenseApplicationLandAcquisitionDetEntity> getLandListByAppId(Long applicationId, Long orgId);
}
