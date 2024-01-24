package com.abm.mainet.materialmgmt.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionDetEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionYearDetEntity;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;

@Component
public class PurchaseRequisitionMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public PurchaseRequisitionMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'PurchaseRequistionDto' to 'PurchaseRequistionEntity'
	 */

	@SuppressWarnings("null")
	public PurchaseRequistionEntity mapPurchaseRequistionDtoToPurchaseRequistionEntity(
			PurchaseRequistionDto purchaseRequistionDto,List<Long> removeDetailsIds) {
		if (purchaseRequistionDto == null) {
			return null;
		}
		PurchaseRequistionEntity entity=map(purchaseRequistionDto, PurchaseRequistionEntity.class);
		PurchaseRequistionDetEntity purchaseRequistionDetEntity=null;
		List<PurchaseRequistionDetEntity> details=new ArrayList<>();
		for(PurchaseRequistionDetDto purchaseReq:purchaseRequistionDto.getPurchaseRequistionDetDtoList())
		{
		 if (!removeDetailsIds.contains(purchaseReq.getPrdetId())) {
			purchaseRequistionDetEntity=new PurchaseRequistionDetEntity();
			purchaseRequistionDetEntity.setPrdetId(purchaseReq.getPrdetId());
			purchaseRequistionDetEntity.setItemId(purchaseReq.getItemId());
			purchaseRequistionDetEntity.setPodetRef(purchaseReq.getPodetRef());
			purchaseRequistionDetEntity.setQuantity(purchaseReq.getQuantity());
			purchaseRequistionDetEntity.setStatus(purchaseReq.getStatus());
			purchaseRequistionDetEntity.setOrgId(purchaseReq.getOrgId());
			purchaseRequistionDetEntity.setUserId(purchaseReq.getUserId());
			purchaseRequistionDetEntity.setLangId(Long.valueOf(purchaseReq.getLangId()));
			purchaseRequistionDetEntity.setLmoDate(purchaseReq.getLmoDate());
			purchaseRequistionDetEntity.setUpdatedBy(purchaseReq.getUpdatedBy());
			purchaseRequistionDetEntity.setLgIpMac(purchaseReq.getLgIpMac());
			purchaseRequistionDetEntity.setStatus('0');
			entity.addStoreMasterMapping(purchaseRequistionDetEntity);
			//details.add(purchaseRequistionDetEntity);
		  }
		}
		//entity.setPurchaseRequistionDetEntity(details);
		return entity;
	}

	/**
	 * Mapping from 'PurchaseRequistionEntity' to 'PurchaseRequistionDto'
	 */

	public PurchaseRequistionDto mapPurchaseRequistionEntityToPurchaseRequistionDto(
			PurchaseRequistionEntity purchaseRequistionEntity) {
		if (purchaseRequistionEntity == null) {
			return null;
		}
		final PurchaseRequistionDto purchaseRequistionDto=map(purchaseRequistionEntity, PurchaseRequistionDto.class);
	     List<PurchaseRequistionDetDto> purchaseRequistionDtoList = new ArrayList<>();
	     PurchaseRequistionDetDto purReqDetDto=null;
	     if (purchaseRequistionDto.getPurchaseRequistionDetDtoList() != null && purchaseRequistionDto.getPurchaseRequistionDetDtoList().size() > 0) {
				for (final PurchaseRequistionDetDto PurRequistionDetDto : purchaseRequistionDto.getPurchaseRequistionDetDtoList()) {
					purReqDetDto = new PurchaseRequistionDetDto();
					purReqDetDto.setItemId(PurRequistionDetDto.getItemId());
					purReqDetDto.setPodetRef(PurRequistionDetDto.getPodetRef());
					purReqDetDto.setQuantity(PurRequistionDetDto.getQuantity());
					purReqDetDto.setStatus(PurRequistionDetDto.getStatus());
					purReqDetDto.setOrgId(PurRequistionDetDto.getOrgId());
					purReqDetDto.setUserId(PurRequistionDetDto.getUserId());
					purReqDetDto.setLangId(PurRequistionDetDto.getLangId());
					purReqDetDto.setLmoDate(PurRequistionDetDto.getLmoDate());
					purReqDetDto.setUpdatedBy(PurRequistionDetDto.getUpdatedBy());
					purReqDetDto.setLgIpMac(PurRequistionDetDto.getLgIpMac());
					purchaseRequistionDtoList.add(purReqDetDto);
				}
				purchaseRequistionDto.setPurchaseRequistionDetDtoList(purchaseRequistionDtoList);
			}
	     
		 if ( purchaseRequistionEntity.getPurchaseRequistionYearDetEntity() != null ) {
			 Double totalYeBugAmount = 0.00;
		     for( PurchaseRequistionYearDetEntity purchaseReqYearDetEntity : purchaseRequistionEntity.getPurchaseRequistionYearDetEntity()) {
		    	 if(null !=purchaseReqYearDetEntity.getYeBugAmount())
		    		 totalYeBugAmount +=purchaseReqYearDetEntity.getYeBugAmount().doubleValue();
		     }
		     purchaseRequistionDto.setTotalYeBugAmount(new BigDecimal(totalYeBugAmount).setScale(2, RoundingMode.HALF_UP));
		 }
     
         return purchaseRequistionDto;
	}
	
	public List<PurchaseRequistionDto> mappurchaseRequistionEntityListToPurchaseRequistionDtoList(
			List<PurchaseRequistionEntity> purchaseRequistionEntity) {
		if (purchaseRequistionEntity == null) {
			return null;
		}
		return map(purchaseRequistionEntity, PurchaseRequistionDto.class);
	}

	public List<PurchaseRequistionEntity> mapPurchaseRequistionDtoListToPurchaseRequistionEntityList(
			List<PurchaseRequistionDto> purchaseRequistionDtoList) {

		if (purchaseRequistionDtoList == null) {
			return null;
		}
		return map(purchaseRequistionDtoList, PurchaseRequistionEntity.class);
	}

}
