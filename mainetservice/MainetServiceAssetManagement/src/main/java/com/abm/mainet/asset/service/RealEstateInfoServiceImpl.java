package com.abm.mainet.asset.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetRealEstate;
import com.abm.mainet.asset.domain.AssetRealEstateHistory;
import com.abm.mainet.asset.domain.AssetRealEstateRev;
import com.abm.mainet.asset.mapper.RealEstateInfoMapper;
import com.abm.mainet.asset.repository.AssetRealEstateRepo;
import com.abm.mainet.asset.repository.AssetRealEstateRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;

@Service
public class RealEstateInfoServiceImpl implements IRealEstateInfoService {

	private static final Logger LOGGER = Logger.getLogger(RealEstateInfoServiceImpl.class);

	@Autowired
	private AssetRealEstateRepo realEstateRepo;

	@Autowired
	private AssetRealEstateRevRepo realEstateRevRepo;

	@Autowired
	private AuditService auditService;

	@Override
	@Transactional
	public Long saveRealEstate(final AssetRealEstateInformationDTO dto) {
		Long realEstateId = null;

		final AssetRealEstateHistory entityHistory = new AssetRealEstateHistory();
		AssetRealEstate realEstateInfo = RealEstateInfoMapper.mapToEntity(dto);
		try {
			realEstateInfo = realEstateRepo.save(realEstateInfo);
			realEstateId = realEstateInfo.getAssetRealStdId();

			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
			try {
				// later to add
				auditService.createHistory(realEstateInfo, entityHistory);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry while saving Purchase Information ", ex);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Real Estate Information ", exception);
			throw new FrameworkException("Exception occur while saving Real Estate Information ", exception);
		}

		return realEstateId;
	}

	@Override
	@Transactional
	public Long updateRealEstateByAssetId(final Long assetId, final AssetRealEstateInformationDTO dto) {
		Long realEstateId = null;
		try {
			if (dto.getAssetRealEstId() != null) {
				final AssetRealEstateHistory entityHistory = new AssetRealEstateHistory();
				final AssetRealEstate entity = RealEstateInfoMapper.mapToEntity(dto);
				realEstateRepo.updateByAssetId(dto.getAssetRealEstId(), entity);
				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
				try {
					auditService.createHistory(entity, entityHistory);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry while updating Real Estate Information ", ex);
				}
			} else {
				dto.setAssetId(assetId);
				realEstateId = saveRealEstate(dto);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating Real Estate Information ", exception);
			throw new FrameworkException("Exception occur while updating Real Estate Information ", exception);
		}

		return realEstateId;

	}

	@Override
	@Transactional
	public Long saveRealEstateRev(Long assetId, AssetRealEstateInformationDTO dto) {
		Long purchId = null;
		try {
			dto.setAssetId(assetId);
			final AssetRealEstateRev entity = RealEstateInfoMapper.mapToEntityRev(dto);
			AssetRealEstateRev realEstateRev = realEstateRevRepo.save(entity);
			purchId = realEstateRev.getAssetRealStdId();
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating Purchase Information ", exception);
			throw new FrameworkException("Exception occur while updating Purchase Information ", exception);
		}
		return purchId;
	}

	@Override
	@Transactional(readOnly=true)
	public AssetRealEstateInformationDTO getRealEstateInfoByAssetId(final Long assetId) {
		AssetRealEstateInformationDTO dto = null;
		try {
			final AssetRealEstate entity = realEstateRepo.findRealEstateByAssetId(assetId);
			if (entity != null) {
				dto = RealEstateInfoMapper.mapToDTO(entity);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching RealEstate Information ", exception);
			throw new FrameworkException("Exception occur while fetching RealEstate Information ", exception);
		}
		return dto;
	}

	@Override
	@Transactional(readOnly=true)
	public AssetRealEstateInformationDTO getRealEstateInfoRevByAssetId(final Long assetIdRev) {
		AssetRealEstateInformationDTO dto = null;
		try {
			final AssetRealEstateRev entity = realEstateRevRepo.findRealEstateInfoRevByAssetId(assetIdRev);

			if (entity != null) {
				dto = RealEstateInfoMapper.mapToDTORev(entity);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching RealEstate Information ", exception);
			throw new FrameworkException("Exception occur while fetching RealEstate Information ", exception);
		}
		return dto;
	}

}
