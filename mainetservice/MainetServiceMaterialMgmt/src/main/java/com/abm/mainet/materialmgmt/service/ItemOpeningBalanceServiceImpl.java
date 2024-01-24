package com.abm.mainet.materialmgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.materialmgmt.dao.ItemOpeningBalanceDao;
import com.abm.mainet.materialmgmt.domain.BinLocMasEntity;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;
import com.abm.mainet.materialmgmt.domain.ItemOpeningBalanceDetEntity;
import com.abm.mainet.materialmgmt.domain.ItemOpeningBalanceEntity;
import com.abm.mainet.materialmgmt.domain.StoreMaster;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDetDto;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.ItemOpeningBalanceRepository;


@Service
public class ItemOpeningBalanceServiceImpl implements ItemOpeningBalanceService{

	
	@Autowired
	private ItemOpeningBalanceRepository itemOpeningBalanceRepository;
	
	@Autowired
	private ItemOpeningBalanceDao itemOpeningBalanceDao;

	@Resource
	private ICommonTransactionRepository transactionRepository;

	@Override
	@Transactional(rollbackFor = Exception.class,readOnly=false)
	public void save(ItemOpeningBalanceDto itemOpeningBalanceDto,List<Long> removeChildIds) {
		
		ItemOpeningBalanceEntity entity =new ItemOpeningBalanceEntity();
		BeanUtils.copyProperties(itemOpeningBalanceDto, entity);
		ItemMasterEntity itemMasterEntity =new ItemMasterEntity();
		StoreMaster storeMaster = new StoreMaster();
		itemMasterEntity.setItemId(itemOpeningBalanceDto.getItemId());
		storeMaster.setStoreId(itemOpeningBalanceDto.getStoreId());
		entity.setItemMasterEntity(itemMasterEntity);
		entity.setStoreMaster(storeMaster);
		entity.setStatus(true);
		ItemOpeningBalanceDetEntity detEntity;
		BinLocMasEntity binLocMasEntity =null;
		List<ItemOpeningBalanceDetEntity> detList = new ArrayList<ItemOpeningBalanceDetEntity>();
		for (ItemOpeningBalanceDetDto itemOpeningBalanceDetDto : itemOpeningBalanceDto.getItemOpeningBalanceDetDto()) {
		  if (!removeChildIds.contains(itemOpeningBalanceDetDto.getOpenBalDetId())) {
			detEntity= new ItemOpeningBalanceDetEntity();
			BeanUtils.copyProperties(itemOpeningBalanceDetDto, detEntity);
			detEntity.setOrgId(itemOpeningBalanceDto.getOrgId());
			detEntity.setUserId(itemOpeningBalanceDto.getUserId());
			detEntity.setLgIpMac(itemOpeningBalanceDto.getLgIpMac());
			detEntity.setLangId(itemOpeningBalanceDto.getLangId());
			detEntity.setUpdatedBy(itemOpeningBalanceDto.getUpdatedBy());
			detEntity.setUpdatedDate(new Date());
			detEntity.setStatus("A");
			detEntity.setItemId(itemOpeningBalanceDto.getItemId());
			binLocMasEntity =new BinLocMasEntity();
			binLocMasEntity.setBinLocId(itemOpeningBalanceDetDto.getBinLocId());
			detEntity.setBinLocMasEntity(binLocMasEntity);
			detEntity.setOpeningBalanceEntity(entity);
			//detEntity.setCreatedBy(itemOpeningBalanceDto.getUpdatedBy());
			//detEntity.setCreatedDate(new Date());
			detEntity.setActive(true);
			detList.add(detEntity);
		  }
		}
		entity.setItemOpeningBalanceDetEntities(detList);
		itemOpeningBalanceRepository.save(entity);
		 if (!removeChildIds.isEmpty()) {
		     itemOpeningBalanceRepository.updateStatusForDetails(entity.getUpdatedBy(),removeChildIds);
		 }
		 
		 /** Save Opening Balance Details to Transaction Table */
		 transactionRepository.save(saveOpeningBalanceDataToTransactionEntity(itemOpeningBalanceDto));
	}


	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent Inward-S
		Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O, Returned-R */
	private List<CommonTransactionEntity> saveOpeningBalanceDataToTransactionEntity(
			ItemOpeningBalanceDto itemOpeningBalanceDto) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		itemOpeningBalanceDto.getItemOpeningBalanceDetDto().forEach(itemDetailDto -> {
			CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
			transactionEntity.setTransactionDate(new Date());
			transactionEntity.setTransactionType(MainetConstants.WorksManagement.OPEN);
			transactionEntity.setReferenceNo("OPENINGBAL");
			transactionEntity.setStoreId(itemOpeningBalanceDto.getStoreId());
			transactionEntity.setItemId(itemOpeningBalanceDto.getItemId());
			transactionEntity.setBinLocation(itemDetailDto.getBinLocId());
			transactionEntity.setItemNo(itemDetailDto.getItemNo());
			transactionEntity.setCreditQuantity(itemDetailDto.getQuantity());
			transactionEntity.setMfgDate(itemDetailDto.getMfgDate());
			transactionEntity.setExpiryDate(itemDetailDto.getExpiryDate());
			transactionEntity.setStatus(MainetConstants.FlagY);
			transactionEntity.setDisposalStatus(MainetConstants.FlagN);
			transactionEntity.setOrgId(itemOpeningBalanceDto.getOrgId());
			transactionEntity.setUserId(itemOpeningBalanceDto.getCreatedBy());
			transactionEntity.setLangId(itemOpeningBalanceDto.getLangId());
			transactionEntity.setCreatedDate(itemOpeningBalanceDto.getCreatedDate());
			transactionEntity.setLgIpMac(itemOpeningBalanceDto.getLgIpMac());
			transactionEntityList.add(transactionEntity);
		});
		return transactionEntityList;
	}


	@Override
	@Transactional(readOnly=true)
	public List<ItemOpeningBalanceDto> findByOrgId(Long orgId) {
		List<ItemOpeningBalanceEntity> list = itemOpeningBalanceRepository.findByOrgId(orgId);
		List<ItemOpeningBalanceDto> dtoList =new ArrayList<>();
		ItemOpeningBalanceDto itemOpeningBalanceDto;
		for (ItemOpeningBalanceEntity itemOpeningBalanceEntity : list) {
			itemOpeningBalanceDto =new ItemOpeningBalanceDto();
			itemOpeningBalanceDto.setItemCode(itemOpeningBalanceEntity.getItemMasterEntity().getItemCode());
			itemOpeningBalanceDto.setItemName(itemOpeningBalanceEntity.getItemMasterEntity().getName());
			itemOpeningBalanceDto.setStoreName(itemOpeningBalanceEntity.getStoreMaster().getStoreName());
			itemOpeningBalanceDto.setOpeningBalance(itemOpeningBalanceEntity.getOpeningBalance());
			itemOpeningBalanceDto.setStatus(itemOpeningBalanceEntity.isStatus());
			itemOpeningBalanceDto.setOpenBalId(itemOpeningBalanceEntity.getOpenBalId());
			dtoList.add(itemOpeningBalanceDto);
			}
		return dtoList;
	}

	@Override
	@Transactional
	public void updateStatus(Long openBalId,Long updatedBy) {
		itemOpeningBalanceRepository.updateStatus(updatedBy,openBalId);
		
	}

	@Override
	@Transactional(readOnly=true)
	public ItemOpeningBalanceDto findById(Long openBalId) {
		ItemOpeningBalanceEntity entity = itemOpeningBalanceRepository.findOne(openBalId);
		ItemOpeningBalanceDto dto =new ItemOpeningBalanceDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setStoreId(entity.getStoreMaster().getStoreId());
		dto.setItemId(entity.getItemMasterEntity().getItemId());
		dto.setItemCode(entity.getItemMasterEntity().getItemCode());
		dto.setItemName(entity.getItemMasterEntity().getName());
		dto.setItemGroup1(entity.getItemMasterEntity().getItemGroup());
		dto.setItemGroup2(entity.getItemMasterEntity().getItemSubGroup());
		dto.setIsExpiry(entity.getItemMasterEntity().getIsExpiry());
		dto.setMethodId(Long.parseLong(entity.getItemMasterEntity().getManagement()));
		dto.setUomId(entity.getItemMasterEntity().getUom());
		List<ItemOpeningBalanceDetEntity> details = entity.getItemOpeningBalanceDetEntities();
		ItemOpeningBalanceDetDto detailsDto;
		List<ItemOpeningBalanceDetDto> detailsList = new ArrayList<>();
		for (ItemOpeningBalanceDetEntity itemOpeningBalanceDetEntity : details) {
			detailsDto =new ItemOpeningBalanceDetDto();
			BeanUtils.copyProperties(itemOpeningBalanceDetEntity, detailsDto);
			detailsDto.setBinLocId(itemOpeningBalanceDetEntity.getBinLocMasEntity().getBinLocId());
			detailsDto.setOpenBalId(itemOpeningBalanceDetEntity.getOpeningBalanceEntity().getOpenBalId());
			detailsList.add(detailsDto);
		}
		dto.setItemOpeningBalanceDetDto(detailsList);
		return dto;
	}


	@Override
	public List<ItemOpeningBalanceDto> serchByItemIdAndStoreId(Long storeId, Long itemId, Long orgId) {
		
		List<ItemOpeningBalanceEntity> entities = itemOpeningBalanceDao.searchBalaneData(storeId, itemId, orgId);
		List<ItemOpeningBalanceDto> dtoList =new ArrayList<>();
		ItemOpeningBalanceDto itemOpeningBalanceDto;
		for (ItemOpeningBalanceEntity itemOpeningBalanceEntity : entities) {
			itemOpeningBalanceDto =new ItemOpeningBalanceDto();
			itemOpeningBalanceDto.setItemCode(itemOpeningBalanceEntity.getItemMasterEntity().getItemCode());
			itemOpeningBalanceDto.setItemName(itemOpeningBalanceEntity.getItemMasterEntity().getName());
			itemOpeningBalanceDto.setStoreName(itemOpeningBalanceEntity.getStoreMaster().getStoreName());
			itemOpeningBalanceDto.setOpeningBalance(itemOpeningBalanceEntity.getOpeningBalance());
			itemOpeningBalanceDto.setStatus(itemOpeningBalanceEntity.isStatus());
			itemOpeningBalanceDto.setOpenBalId(itemOpeningBalanceEntity.getOpenBalId());
			dtoList.add(itemOpeningBalanceDto);
			}
		return dtoList;
	}


	@Override
	public List<String> checkDuplicateItemNos(Long itemId, List<String> itemNumberList, Long orgId) {
		return itemOpeningBalanceRepository.checkDuplicateItemNos(itemId, itemNumberList, orgId);
	}
}
