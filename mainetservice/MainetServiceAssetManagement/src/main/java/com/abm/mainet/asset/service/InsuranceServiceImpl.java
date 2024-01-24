/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInsuranceDetails;
import com.abm.mainet.asset.domain.AssetInsuranceDetailsRev;
import com.abm.mainet.asset.domain.InsuranceDetailsHistory;
import com.abm.mainet.asset.mapper.InsuranceServiceMapper;
import com.abm.mainet.asset.repository.AssetInsuranceRepo;
import com.abm.mainet.asset.repository.AssetInsuranceRevRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;

/**
 * @author satish.rathore
 *
 */
@Service
public class InsuranceServiceImpl implements IInsuranceService {

	private static final Logger LOGGER = Logger.getLogger(InsuranceServiceImpl.class);

	@Resource
	private AssetInsuranceRepo assetInsuranceRepo;
	
	@Resource
	private AssetInsuranceRevRepo assetInsuranceRevRepo;
	
	@Autowired
	private AuditService auditService;

	@Override
	public void addInsuranceInfo(AssetInsuranceDetailsDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public Long updateInsuranceInfo(final Long id, final AssetInsuranceDetailsDTO dto) {
		Long assetInsurId	=	null;
		try {
			InsuranceDetailsHistory entityHistory = new InsuranceDetailsHistory();
			if (dto.getAssetInsuranceId() != null) {
				AssetInsuranceDetails astInsurance = InsuranceServiceMapper.mapToEntity(dto);
				assetInsuranceRepo.updateInsurancesInfo(astInsurance);
				entityHistory.setHistoryFlag(MainetConstants.InsertMode.UPDATE.getStatus());
				try {
					auditService.createHistory(astInsurance, entityHistory);
				} catch (Exception ex) {
					LOGGER.error("Could not make audit entry while updating Asset insurance Details ", ex);
				}
			} else {
				dto.setAssetId(id);
				assetInsurId	=	saveInsurance(dto);

			}
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating service insurance  ", exception);
			throw new FrameworkException("Exception occurs while updating Asset insurance Details ", exception);
		}
		
		return assetInsurId;
	}
	
	@Override
	@Transactional
	public Long updateInsuranceInfoRev(final Long id, final AssetInsuranceDetailsDTO dto) {
		Long assetInsurId	=	null;
		try {
			if (dto.getAssetInsuranceId() != null) {
				AssetInsuranceDetailsRev astInsuranceRev = InsuranceServiceMapper.mapToEntityRev(dto);
				assetInsuranceRevRepo.insuranceInfo(astInsuranceRev);
			} 
		} catch (Exception exception) {
			LOGGER.error("Exception occur while updating service insurance  ", exception);
			throw new FrameworkException("Exception occurs while updating Asset insurance Details ", exception);
		}
		
		return assetInsurId;
	}
	

	@Override
	@Transactional
	public Long saveInsurance( final AssetInsuranceDetailsDTO dto) {
		Long astInsuId	=	null;

		InsuranceDetailsHistory entityHistory = new InsuranceDetailsHistory();
		AssetInsuranceDetails astInsurance = InsuranceServiceMapper.mapToEntity(dto);
		try {
			astInsurance	=	assetInsuranceRepo.save(astInsurance);
			astInsuId		=	astInsurance.getAssetInsuranceId();
			entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
			try {
				auditService.createHistory(astInsurance, entityHistory);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry while saving asset insurance detail ", ex);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving asset insurance detail ", exception);

		}
		
		return astInsuId;

	}

	@Override
	@Transactional
	public Long saveInsuranceRev(final List<AssetInsuranceDetailsDTO> dtoList) {
		Long groupIdRev	=	null;
		
		for(int i=0;i<dtoList.size();i++)
		{
			AssetInsuranceDetailsDTO	astInsuDTO	=	dtoList.get(i);
			AssetInsuranceDetailsRev astInsuranceRev = InsuranceServiceMapper.mapToEntityRev(astInsuDTO);
			try 
			{
				astInsuranceRev	=	assetInsuranceRevRepo.save(astInsuranceRev);
				groupIdRev	=	astInsuranceRev.getRevGrpId();
	
			} catch (Exception exception) {
				throw new FrameworkException("Exception occur while saving asset insurance detail ", exception);
	
			}
		}
		return groupIdRev;
		
	}
	
	@Transactional
    public List<AssetInsuranceDetailsDTO> getAllInsuranceDetailsList(final Long assetInsuranceRevId,final AssetInsuranceDetailsDTO dto)
    {
		List<AssetInsuranceDetailsRev> astInsuRev 		=	null;
		List<AssetInsuranceDetailsDTO> astInsuDTOList	=	null;
		
		try {
//			astInsuRev		=	assetInsuranceRevRepo.getAllInsuranceDetailsList(assetInsuranceRevId);
			astInsuDTOList	=	InsuranceServiceMapper.mapToDTOListRev(astInsuRev);
		} catch (Exception exception) {
			throw new FrameworkException("Exception occur while saving asset insurance detail ", exception);
		}
		return astInsuDTOList;
    }

	
	@Override
	@Transactional(readOnly=true)
	public AssetInsuranceDetailsDTO getInsuranceByAssetId(Long assetId) {
		AssetInsuranceDetailsDTO dto = null;
		final AssetInsuranceDetails entity = assetInsuranceRepo.findInsuranceByAssetId(assetId);
		if (entity != null) {
			dto = InsuranceServiceMapper.mapToDTO(entity);
		}
		return dto;
	}

	@Override
	@Transactional
    public List<AssetInsuranceDetailsDTO> getInsuranceListByAssetId(Long assetId)
    {
		List<AssetInsuranceDetailsDTO> dtoList = null;
		final List<AssetInsuranceDetails> entityList = assetInsuranceRepo.getAllInsurnaceListByAssetId(assetId);
		if (entityList != null && entityList.size()>0) {
			dtoList = InsuranceServiceMapper.mapToDTOList(entityList);
		}
		return dtoList;
    }

	
	@Override
	@Transactional(readOnly=true)
	public AssetInsuranceDetailsDTO getInsuranceRevByAssetId(Long assetId) {
		AssetInsuranceDetailsDTO dto = null;
		final AssetInsuranceDetailsRev entity = assetInsuranceRevRepo.findInsuranceRevByAssetId(assetId);
		if (entity != null) {
			dto = InsuranceServiceMapper.mapToDTORev(entity);
		}
		return dto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<AssetInsuranceDetailsDTO> getInsuranceListRevByGroupId(Long groupId) {
		List<AssetInsuranceDetailsDTO> dtoList = new ArrayList<AssetInsuranceDetailsDTO>();
		final List<AssetInsuranceDetailsRev> entityList = assetInsuranceRevRepo.findInsuranceListRevByAssetId(groupId);
		if (entityList != null) {
			dtoList = InsuranceServiceMapper.mapToDTOListRev(entityList);
		}
		return dtoList;
	}
	

}
