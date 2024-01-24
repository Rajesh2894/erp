package com.abm.mainet.materialmgmt.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderDetEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderOverheadsEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseorderAttachmentEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseorderTncEntity;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderOverheadsDto;
import com.abm.mainet.materialmgmt.dto.PurchaseorderAttachmentDto;
import com.abm.mainet.materialmgmt.dto.PurchaseorderTncDto;

@Component
public class PurchaseOrderMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public PurchaseOrderMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public PurchaseOrderEntity mapPurchaseOrderDTOToPurchaseOrderEntity(PurchaseOrderDto purchaseOrderDto) {
		if (purchaseOrderDto == null) {
			return null;
		}
		PurchaseOrderEntity PurchaseOrderEntity=map(purchaseOrderDto, PurchaseOrderEntity.class);
		PurchaseOrderDetEntity purOrderDetEntity=null;
		PurchaseOrderOverheadsEntity purOrderOverheadsEntity=null;
		PurchaseorderTncEntity purchaseorderTncEntity=null;
		PurchaseorderAttachmentEntity  purorderAttachmentEntity=null;
		if(purchaseOrderDto.getPurchaseOrderDetDto()!=null) {
		for (PurchaseOrderDetDto purOrderDet : purchaseOrderDto.getPurchaseOrderDetDto()) {
			purOrderDetEntity = new PurchaseOrderDetEntity();
			purOrderDetEntity.setPodet(purOrderDet.getPodet());
			purOrderDetEntity.setItemId(purOrderDet.getItemId());
			purOrderDetEntity.setQuantity(purOrderDet.getQuantity());
			purOrderDetEntity.setUnitPrice(purOrderDet.getUnitPrice());
			purOrderDetEntity.setTax(purOrderDet.getTax());
			purOrderDetEntity.setTotalAmt(purOrderDet.getTotalAmt());
			purOrderDetEntity.setPrId(purOrderDet.getPrId());
			purOrderDetEntity.setPrDetId(purOrderDet.getPrDetId());
			purOrderDetEntity.setOrgId(purOrderDet.getOrgId());
			purOrderDetEntity.setUserId(purOrderDet.getUserId());
			purOrderDetEntity.setLangId(purOrderDet.getLangId());
			purOrderDetEntity.setLmoDate(purOrderDet.getLmoDate());
			purOrderDetEntity.setLgIpMac(purOrderDet.getLgIpMac());
			PurchaseOrderEntity.addPurchaseOrderdetMapping(purOrderDetEntity);
		}
	 }
     if(purchaseOrderDto.getPurchaseOrderOverheadsDto()!=null)	{
    	 for(PurchaseOrderOverheadsDto purOrderOverhdsDet: purchaseOrderDto.getPurchaseOrderOverheadsDto() ) {
				purOrderOverheadsEntity=new PurchaseOrderOverheadsEntity();
				purOrderOverheadsEntity.setOverHeadId(purOrderOverhdsDet.getOverHeadId());
				purOrderOverheadsEntity.setDescription(purOrderOverhdsDet.getDescription());
				purOrderOverheadsEntity.setOverHeadType(purOrderOverhdsDet.getOverHeadType());
				purOrderOverheadsEntity.setAmount(purOrderOverhdsDet.getAmount());
				purOrderOverheadsEntity.setOrgId(purOrderOverhdsDet.getOrgId());
				purOrderOverheadsEntity.setUserId(purOrderOverhdsDet.getUserId());
				purOrderOverheadsEntity.setLangId(purOrderOverhdsDet.getLangId());
				purOrderOverheadsEntity.setLmodDate(purOrderOverhdsDet.getLmodDate());
				purOrderOverheadsEntity.setLgIpMac(purOrderOverhdsDet.getLgIpMac());
				PurchaseOrderEntity.addPurchaseOrderOverheadsMapping(purOrderOverheadsEntity);
			}
     }
     
     if(purchaseOrderDto.getPurchaseorderTncDto()!=null) {
    	 for (PurchaseorderTncDto  purchaseorderTncDto:purchaseOrderDto.getPurchaseorderTncDto()) {
				purchaseorderTncEntity=new PurchaseorderTncEntity();
				purchaseorderTncEntity.setTncId(purchaseorderTncDto.getTncId());
				purchaseorderTncEntity.setDescription(purchaseorderTncDto.getDescription());
				purchaseorderTncEntity.setOrgId(purchaseorderTncDto.getOrgId());
				purchaseorderTncEntity.setUserId(purchaseorderTncDto.getUserId());
				purchaseorderTncEntity.setLangId(purchaseorderTncDto.getLangId());
				purchaseorderTncEntity.setLmodDate(purchaseorderTncDto.getLmodDate());
				purchaseorderTncEntity.setLgIpMac(purchaseorderTncDto.getLgIpMac());
				PurchaseOrderEntity.addPurchaseorderTncMapping(purchaseorderTncEntity);
			}
     }
     if(purchaseOrderDto.getPurchaseorderAttachmentDto()!=null) {
    	 for(PurchaseorderAttachmentDto purorderAttachmentDto:purchaseOrderDto.getPurchaseorderAttachmentDto()) {
				purorderAttachmentEntity=new PurchaseorderAttachmentEntity();
				purorderAttachmentEntity.setPodocId(purorderAttachmentDto.getPodocId());
				purorderAttachmentEntity.setDescription(purorderAttachmentDto.getDescription());
				purorderAttachmentEntity.setAtdFname("pdf");
				purorderAttachmentEntity.setAtdFromPath("D:\\27042022Mainet");
				purorderAttachmentEntity.setAtdPath("D:\\\\27042022Mainet");
				purorderAttachmentEntity.setOrgId(purorderAttachmentDto.getOrgId());
				purorderAttachmentEntity.setCreatedBy(purorderAttachmentDto.getCreatedBy());
				purorderAttachmentEntity.setCreatedDate(purorderAttachmentDto.getCreatedDate());
				purorderAttachmentEntity.setLgIpMac(purorderAttachmentDto.getLgIpMac());
				PurchaseOrderEntity.addPurchaseorderAttachmentMapping(purorderAttachmentEntity);
			}
     }
	return PurchaseOrderEntity;
}}
