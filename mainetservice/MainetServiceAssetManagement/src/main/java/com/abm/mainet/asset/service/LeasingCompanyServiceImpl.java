/**
 * 
 */
package com.abm.mainet.asset.service;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetLeasingCompany;
import com.abm.mainet.asset.domain.AssetLeasingCompanyRev;
import com.abm.mainet.asset.domain.LeasingCompanyHistory;
import com.abm.mainet.asset.mapper.LeasingCompanyMapper;
import com.abm.mainet.asset.repository.AssetLeasingCompanyRepo;
import com.abm.mainet.asset.repository.AssetLeasingCompanyRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class LeasingCompanyServiceImpl implements ILeasingCompanyService {

	private static final Logger LOGGER = Logger.getLogger(LeasingCompanyServiceImpl.class);

	@Autowired
	private AssetLeasingCompanyRepo assetLeasingCompanyRepo;
	
	@Autowired
	private AssetLeasingCompanyRevRepo assetLeasingCompanyRevRepo;
	
	@Autowired
	private AuditService auditService;

	@Override
	@Transactional
	public Long saveLeasingInfo( final AssetLeasingCompanyDTO dto) {
		Long leaseId	=	null;
		LeasingCompanyHistory entityHistory = new LeasingCompanyHistory();
		AssetLeasingCompany leasingEntity = LeasingCompanyMapper.mapToEntity(dto);
		try {
			leasingEntity	=	assetLeasingCompanyRepo.save(leasingEntity);
			leaseId	=	leasingEntity.getAssetLeasingID();
			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
			try {
				auditService.createHistory(leasingEntity, entityHistory);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry while saving asset leasing company ", ex);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving asset leasing company ", exception);

		}
		return leaseId;
	}

	@Override
	public void updateLeasingInfo(AssetLeasingCompanyDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public Long saveLeasingInfoRev(Long assetId, AssetLeasingCompanyDTO dto) {
		Long idRev	=	null;
//		LeasingCompanyHistory entityHistory = new LeasingCompanyHistory();
		AssetLeasingCompanyRev leasingEntityRev = LeasingCompanyMapper.mapToEntityRev(dto);
		try {
			leasingEntityRev	=	assetLeasingCompanyRevRepo.save(leasingEntityRev);
			idRev	=	leasingEntityRev.getAssetLeasingRevID();
//			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
//			auditService.createHistory(leasingEntity, entityHistory);
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving asset leasing company ", exception);
		}
		
		return idRev;
	}
	
	@Override
	@Transactional
	public AssetLeasingCompanyDTO getLeasingByAssetId(final Long assetId) {
		AssetLeasingCompanyDTO dto = null;
		final AssetLeasingCompany entity = assetLeasingCompanyRepo.findLeasingByAssetId(assetId);
		if (entity != null) {
			dto = LeasingCompanyMapper.mapToDTO(entity);
		}
		return dto;
	}
	
	@Override
	@Transactional(readOnly=true)
	public AssetLeasingCompanyDTO getLeasingRevByAssetId(final Long assetId) {
		AssetLeasingCompanyDTO dto = null;
		final AssetLeasingCompanyRev entity = assetLeasingCompanyRevRepo.findLeasingByAssetId(assetId);
		if (entity != null) {
			dto = LeasingCompanyMapper.mapToDTORev(entity);
		}
		return dto;
	}

	@Override
	@Transactional
	public Long updateLeasingInfoByAssetId(final Long assetId, final AssetLeasingCompanyDTO dto) {
		Long leaseId	=	null;
		
		try {
			LeasingCompanyHistory entityHistory = new LeasingCompanyHistory();
			if (dto.getAssetLeasingId() != null) {
				AssetLeasingCompany leasingEntity = LeasingCompanyMapper.mapToEntity(dto);
				assetLeasingCompanyRepo.updateLeasingComp(leasingEntity);
				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
				try {
					auditService.createHistory(leasingEntity, entityHistory);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry while updating service leasing ", ex);
				}
			} else {
				dto.setAssetId(assetId);
				leaseId	=	saveLeasingInfo(dto);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating service leasing ", exception);
			throw new FrameworkException("Exception occurs while updating Asset Service leasing Details ",
					exception);

		}
		
		return leaseId;

	}



}
