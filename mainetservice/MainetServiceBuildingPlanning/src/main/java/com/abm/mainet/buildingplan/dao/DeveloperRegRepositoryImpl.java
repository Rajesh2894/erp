package com.abm.mainet.buildingplan.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.TbAuthUserMasEntity;
import com.abm.mainet.buildingplan.domain.TbDevPerGrntMasEntity;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.buildingplan.domain.TbDirectorMasEntity;
import com.abm.mainet.buildingplan.domain.TbStkhldrMasEntity;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UserSession;

@Repository
public class DeveloperRegRepositoryImpl extends AbstractDAO<TbDeveloperRegistrationEntity> implements DeveloperRegRepository {
	
	@Override
	public TbDeveloperRegistrationEntity saveDeveloperRegForm(final TbDeveloperRegistrationEntity devRegEntity) {
		entityManager.persist(devRegEntity);
		return devRegEntity;
	}	
	
	@Override
	public List<TbStkhldrMasEntity> saveStkhldrMasData(final List<TbStkhldrMasEntity> stkhldrMasEntity) {
		List<TbStkhldrMasEntity> stkhldrMasEntityList = new ArrayList<>();
		for (final TbStkhldrMasEntity stkhldrMasEntityData : stkhldrMasEntity) {
			if (stkhldrMasEntityData.getStakeholderId() == 0l) {				
				entityManager.persist(stkhldrMasEntityData);
				stkhldrMasEntityList.add(stkhldrMasEntityData);
			} else {
				stkhldrMasEntityData.setUpdatedDate(new Date());
				stkhldrMasEntityData.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				entityManager.merge(stkhldrMasEntityData);
				stkhldrMasEntityList.add(stkhldrMasEntityData);
			}
		}
		return stkhldrMasEntityList;
	}
	
	@Override
	public List<TbDirectorMasEntity> saveDirectorMasData(final List<TbDirectorMasEntity> directorMasEntityList) {
		List<TbDirectorMasEntity> directorMasEntityDataList= new ArrayList<>();
		for (final TbDirectorMasEntity directorMasEntityData : directorMasEntityList) {
			if (directorMasEntityData.getDirectorId() == 0l) {
				entityManager.persist(directorMasEntityData);
				directorMasEntityDataList.add(directorMasEntityData);
			} else {
				directorMasEntityData.setUpdatedDate(new Date());
				directorMasEntityData.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				entityManager.merge(directorMasEntityData);
				directorMasEntityDataList.add(directorMasEntityData);
			}
		}
		return directorMasEntityList;
	}
	
	@Override
	public List<TbAuthUserMasEntity> saveAuthUserMasData(final List<TbAuthUserMasEntity> authUserMasEntityList) {
		List<TbAuthUserMasEntity> authUserMasEntityDataList= new ArrayList<>();
		for (final TbAuthUserMasEntity authUserMasEntityData : authUserMasEntityList) {
			if (authUserMasEntityData.getAuthUserId() == 0l) {
				entityManager.persist(authUserMasEntityData);
				authUserMasEntityDataList.add(authUserMasEntityData);
			} else {
				authUserMasEntityData.setUpdatedDate(new Date());
				authUserMasEntityData.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				entityManager.merge(authUserMasEntityData);
				authUserMasEntityDataList.add(authUserMasEntityData);
			}
		}
		return authUserMasEntityDataList;
	}
	
	@Override
	public void saveDevPerGrntMasData(final List<TbDevPerGrntMasEntity> devPerGrntMasEntityList) {
		for (final TbDevPerGrntMasEntity devPerGrntMasEntityData : devPerGrntMasEntityList) {
			if (devPerGrntMasEntityData.gethDRULicenseId() == 0l) {
				entityManager.persist(devPerGrntMasEntityData);
			} else {
				//devPerGrntMasEntityData.setModified_date(new Date());
				//devPerGrntMasEntityData.setModified_by(UserSession.getCurrent().getEmployee().getEmpId());
				entityManager.merge(devPerGrntMasEntityData);
			}
		}
	}
	
	@Override
	public List<TbStkhldrMasEntity> getStkhldrMasEntitiesByDevRegNo(Long tcpDevRegNo) {
	    String jpql = "SELECT s FROM TbStkhldrMasEntity s WHERE s.tcp_dev_reg_no = :tcpDevRegNo";
	    return entityManager.createQuery(jpql, TbStkhldrMasEntity.class)
	            .setParameter("tcpDevRegNo", tcpDevRegNo)
	            .getResultList();
	}
	
	@Override
	public List<TbDirectorMasEntity> getDirectorMasEntitiesByDevRegNo(Long tcpDevRegNo) {
	    String jpql = "SELECT d FROM TbDirectorMasEntity d WHERE d.tcp_dev_reg_no = :tcpDevRegNo";
	    return entityManager.createQuery(jpql, TbDirectorMasEntity.class)
	            .setParameter("tcpDevRegNo", tcpDevRegNo)
	            .getResultList();
	}

	@Override
	public List<TbAuthUserMasEntity> getAuthUserMasEntitiesByDevRegNo(Long tcpDevRegNo) {
	    String jpql = "SELECT a FROM TbAuthUserMasEntity a WHERE a.tcp_dev_reg_no = :tcpDevRegNo";
	    return entityManager.createQuery(jpql, TbAuthUserMasEntity.class)
	            .setParameter("tcpDevRegNo", tcpDevRegNo)
	            .getResultList();
	}
	
	@Override
	public List<TbDeveloperRegistrationEntity> getDeveloperRegistrationByCreatedById(Long created_by) {
	    String jpql = "SELECT a FROM TbDeveloperRegistrationEntity a WHERE a.created_by = :created_by ORDER BY a.id DESC";
	    return entityManager.createQuery(jpql, TbDeveloperRegistrationEntity.class)
	            .setParameter("created_by", created_by)
	            .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LicenseApplicationLandAcquisitionDetEntity> getLandListByAppId(final Long applicationId,
			final Long orgId) {
		List<LicenseApplicationLandAcquisitionDetEntity> landList = null;
		final Query query = entityManager.createQuery("SELECT r FROM LicenseApplicationLandAcquisitionDetEntity r "
				+ "WHERE  r.licenseApplicationMaster.tcpLicMstrId in (select l.tcpLicMstrId from LicenseApplicationMaster l where l.applicationNo=?)");
		query.setParameter(1, applicationId);
		// query.setParameter(2, orgId);
		landList = query.getResultList();

		return landList;
	}
	
	
}
