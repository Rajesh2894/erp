/**
 * 
 */
package com.abm.mainet.asset.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetLinear;
import com.abm.mainet.asset.domain.AssetLinearRev;
import com.abm.mainet.asset.domain.LinearHistory;
import com.abm.mainet.asset.mapper.LinearAssetMapper;
import com.abm.mainet.asset.repository.AssetLinearRepo;
import com.abm.mainet.asset.repository.AssetLinearRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class LinearAssetServiceImpl implements ILinearAssetService {

	private static final Logger LOGGER = Logger.getLogger(LinearAssetServiceImpl.class);

	@Autowired
	private AssetLinearRepo assetLinearRepo;
	
	@Autowired
	private AssetLinearRevRepo assetLinearRevRepo;
	
	@Autowired
	private AuditService auditService;

	@Override
	@Transactional

	public Long saveLinearInfo( final AssetLinearDTO dto) {
		Long linearInfoId	=	null;

		LinearHistory entityHistory = new LinearHistory();
		AssetLinear astLinearEntity = LinearAssetMapper.mapToEntity(dto);
		try {
			astLinearEntity	=	assetLinearRepo.save(astLinearEntity);
			linearInfoId	=	astLinearEntity.getAssetLinearId();
			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
			try {
				auditService.createHistory(astLinearEntity, entityHistory);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry while saving linear asset ", ex);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving  linear asset  ", exception);

		}

		return linearInfoId;
	}
	
	@Override
	public Long saveLinearInfoRev(AssetLinearDTO dto) {
		Long idRef	=	null;
//		LinearHistory entityHistory = new LinearHistory();
		AssetLinearRev astLinearEntityRev = LinearAssetMapper.mapToEntityRev(dto);
		try {
			astLinearEntityRev	=	assetLinearRevRepo.save(astLinearEntityRev);
			idRef	=	astLinearEntityRev.getAssetLinearRevId();
//			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
//			auditService.createHistory(astLinearEntity, entityHistory);
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving  linear asset  ", exception);

		}
		return idRef;
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLinearInfo(AssetLinearDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public AssetLinearDTO getLinearByAssetId(final Long assetId) {
		AssetLinearDTO dto = null;
		final AssetLinear entity = assetLinearRepo.findLinearByAssetId(assetId);
		if (entity != null) {
			dto = LinearAssetMapper.mapToDTO(entity);
		}
		return dto;
	}
	
	@Override
	@Transactional(readOnly=true)
	public AssetLinearDTO getLinearRevByAssetId(final Long assetId) {
		AssetLinearDTO dto = null;
		final AssetLinearRev entity = assetLinearRevRepo.findLinearRevByAssetId(assetId);
		if (entity != null) {
			dto = LinearAssetMapper.mapToDTORev(entity);
		}
		return dto;
	}

	@Override
	@Transactional
	public Long updateLinearInfoByAssetId(final Long assetId, final AssetLinearDTO dto) {
    	Long linearInfoId	=	null;

		try {
			LinearHistory entityHistory = new LinearHistory();
			if (dto.getAssetLinearId() != null) {
				AssetLinear astLinearEntity = LinearAssetMapper.mapToEntity(dto);
				assetLinearRepo.updateLinearAsset(astLinearEntity);
				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
				try {
					auditService.createHistory(astLinearEntity, entityHistory);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry while updating Asset Service linear Details ", ex);
				}
			} else {
				dto.setAssetId(assetId);
				linearInfoId	=	saveLinearInfo(dto);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating service linear  ", exception);
			throw new FrameworkException("Exception occurs while updating Asset Service linear Details ", exception);
		}
		return linearInfoId;

	}

}
