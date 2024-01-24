package com.abm.mainet.asset.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.asset.domain.AssetPurchaseInformationRev;
import com.abm.mainet.asset.domain.PurchaseInformationHistory;
import com.abm.mainet.asset.mapper.PurchaseInfoMapper;
import com.abm.mainet.asset.repository.AssetPurchaseInformationRepo;
import com.abm.mainet.asset.repository.AssetPurchaseInformationRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;

@Service
public class PurchaseInfoServiceImpl implements IPurchaseInfoService {

	private static final Logger LOGGER = Logger.getLogger(PurchaseInfoServiceImpl.class);

	@Autowired
	private AssetPurchaseInformationRepo purchaseRepo;
	
	@Autowired
	private AssetPurchaseInformationRevRepo purchaseRevRepo;
	
	@Autowired
	private AuditService auditService;

	@Override
	@Transactional
	public Long savePurchaseInfo( final AssetPurchaseInformationDTO dto) {
		Long purchaseId	=	null;
		
		final PurchaseInformationHistory entityHistory = new PurchaseInformationHistory();
		AssetPurchaseInformation purInfo = PurchaseInfoMapper.mapToEntity(dto);
		try {
			purInfo	=	purchaseRepo.save(purInfo);
			purchaseId	=	purInfo.getAssetPurchaserId();
			
			
			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
			try {
				auditService.createHistory(purInfo, entityHistory);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry while saving Purchase Information ", ex);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while saving Purchase Information ", exception);
			throw new FrameworkException("Exception occur while saving Purchase Information ", exception);
		}

		return purchaseId;
	}

	@Override
	@Transactional
	public Long updatePurchaseInfoByAssetId(final Long assetId, final AssetPurchaseInformationDTO dto) {
		Long purchaserId	=	null;
		try {
			if (dto.getAssetPurchaserId() != null) {
				final PurchaseInformationHistory entityHistory = new PurchaseInformationHistory();
				final AssetPurchaseInformation entity = PurchaseInfoMapper.mapToEntity(dto);
				purchaseRepo.updateByAssetId(dto.getAssetPurchaserId(), entity);
				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
				try {
					auditService.createHistory(entity, entityHistory);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry while updating Purchase Information ", ex);
				}
			} else {
				dto.setAssetId(assetId);
				purchaserId	=	savePurchaseInfo(dto);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating Purchase Information ", exception);
			throw new FrameworkException("Exception occur while updating Purchase Information ", exception);
		}
		
		return purchaserId;

	}

	@Override
	@Transactional
	public Long savePurchaseInfoRev(Long assetId, AssetPurchaseInformationDTO dto) {
		Long purchId	=	null;
		try {
//				final PurchaseInformationHistory entityHistory = new PurchaseInformationHistory();
				dto.setAssetId(assetId);
				final AssetPurchaseInformationRev entity = PurchaseInfoMapper.mapToEntityRev(dto);
				AssetPurchaseInformationRev purchRev	=	purchaseRevRepo.save(entity);
				
				purchId	=	 purchRev.getAssetPurchaserRevId();
//				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
//				auditService.createHistory(entity, entityHistory);
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating Purchase Information ", exception);
			throw new FrameworkException("Exception occur while updating Purchase Information ",
					exception);
		}
		return purchId;
	}
	
	@Override
	@Transactional(readOnly=true)
	public AssetPurchaseInformationDTO getPurchaseByAssetId(final Long assetId) {
		AssetPurchaseInformationDTO dto = null;
		try {
			final AssetPurchaseInformation entity = purchaseRepo.findPurchaseByAssetId(assetId);
			if (entity != null) {
				dto = PurchaseInfoMapper.mapToDTO(entity);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Purchase Information ", exception);
			throw new FrameworkException("Exception occur while fetching Purchase Information ", exception);
		}
		return dto;
	}
	
	@Override
	@Transactional(readOnly=true)
	public AssetPurchaseInformationDTO getPurchaseRevByAssetId(final Long assetIdRev) {
		AssetPurchaseInformationDTO dto = null;
		try {
			final AssetPurchaseInformationRev entity = purchaseRevRepo.findPurchaseRevByAssetId(assetIdRev);
			if (entity != null) {
				dto = PurchaseInfoMapper.mapToDTORev(entity);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while fetching Purchase Information ", exception);
			throw new FrameworkException("Exception occur while fetching Purchase Information ",
					exception);
		}
		return dto;
	}

	

}
