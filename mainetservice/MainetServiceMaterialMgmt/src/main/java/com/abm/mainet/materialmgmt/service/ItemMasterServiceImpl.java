
package com.abm.mainet.materialmgmt.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Path;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.materialmgmt.dao.ItemMasterDao;
import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.dto.ItemMasterUploadDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.mapper.ItemMasterServiceMapper;
import com.abm.mainet.materialmgmt.repository.ItemMasterRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

import io.swagger.annotations.Api;
import liquibase.integration.commandline.Main;


@WebService(endpointInterface = "com.abm.mainet.materialmgmt.service.ItemMasterService")
@Api(value = "/itemMaster")
@Path("/itemMaster")
@Service
public class ItemMasterServiceImpl implements ItemMasterService {

   
    @Resource
    private ItemMasterServiceMapper itemMasterServiceMapper; 
    @Resource
    private ItemMasterRepository itemMasterRepository;
    @Resource
    private ItemMasterDao itemMasterDao;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;
   
    private static final String SEQUENCE_NO = "I0000";
   
    @Override
    @Transactional
	public ItemMasterDTO saveItemMasterFormData(final ItemMasterDTO itemMasterDto,final Organisation org,final int langId ,final Employee emp) {
    	if (itemMasterDto.getItemCode() == null || itemMasterDto.getItemCode().isEmpty()) {
    		long itemcodeNumber = 0;
            itemcodeNumber = seqGenFunctionUtility.generateSequenceNo(
          		  MainetMMConstants.MmItemMaster.ITM,
          		  MainetMMConstants.MmItemMaster.MM_ITEMMASTER,
          		  MainetMMConstants.MmItemMaster.MM_ITEMCODE, itemMasterDto.getOrgId(),
                    MainetConstants.MODE_CREATE, null);
            final StringBuilder itemCodeNo = new StringBuilder();
            //itemCodeNo.append(SEQUENCE_NO).append(itemcodeNumber).toString();
            //itemMasterDto.setItemCode("ITM"+ itemCodeNo.toString());
            itemMasterDto.setItemCode(itemCodeNo.append("ITM")
                    .append(org.getOrgShortNm())
                    .append(String.format("%06d", itemcodeNumber)).toString());
           
    	}
		final ItemMasterDTO itemMasterDTo = itemMasterDto;
		ItemMasterEntity itemMasterEntity = new ItemMasterEntity();
		itemMasterServiceMapper.mapItemMasterDtoToItemMasterEntity(itemMasterDTo, itemMasterEntity,org,langId,emp);
		itemMasterRepository.save(itemMasterEntity);
		return itemMasterDto;
	}

  
    @Override
	@Transactional(readOnly = true)
	public List<ItemMasterDTO> findItemMasterDetailsByOrgId(final Long orgId) {
		final List<ItemMasterEntity> entities = itemMasterRepository.findItemMasterDetailsByOrgId(orgId);
		final List<ItemMasterDTO> bean = new ArrayList<>();
		for (final ItemMasterEntity itemMasterEntity : entities) {
			bean.add(itemMasterServiceMapper.mapItemMasterEntityToItemMasterDTO(itemMasterEntity));
		}
		return bean;
	}

	@Override
	@Transactional
	public List<ItemMasterDTO> findByAllGridSearchData(Long category, Long type, Long itemgroup, Long itemsubgroup,
			String name, Long orgId) {
		final List<ItemMasterEntity> entities = itemMasterDao.findByAllGridSearchData(category, type, itemgroup, itemsubgroup, name, orgId);
		final List<ItemMasterDTO> bean = new ArrayList<>();
		for (final ItemMasterEntity itemMasterEntity : entities) {
			bean.add(itemMasterServiceMapper.mapItemMasterEntityToItemMasterDTO(itemMasterEntity));
		}
		return bean;
	}
	
	
	@Override
	@Transactional
	public List<Object[]> getItemIdNameListByOrgId(Long orgId) {
		return itemMasterRepository.getItemIdNameListByOrgId(orgId);
	}	


	@Override
	@Transactional
	public ItemMasterDTO getDetailsByUsingItemId(ItemMasterDTO tbMGItemMaster) {
		final Long itemId = tbMGItemMaster.getItemId();
        final ItemMasterEntity itemMasterEntity = itemMasterRepository.findItemMasterDetailsByItemId(itemId);
        tbMGItemMaster = itemMasterServiceMapper.mapItemMasterEntityToItemMasterDTO(itemMasterEntity);
        return tbMGItemMaster;
	}  
  
	@Override
	@Transactional
	public void saveItemMasterExcelData(ItemMasterUploadDTO itemMasterUploadDto) throws ParseException {
		final ItemMasterEntity itemMasterEntity = new ItemMasterEntity();
		ItemMasterDTO itemMasterDto = new ItemMasterDTO();
		itemMasterDto.setOrgId(itemMasterUploadDto.getOrgId());
		itemMasterDto.setUserId(itemMasterUploadDto.getUserId());
		itemMasterDto.setLgIpMac(itemMasterUploadDto.getLgIpMac());
		itemMasterDto.setLangId(itemMasterUploadDto.getLangId());
		itemMasterDto.setLgIpMacUpd(itemMasterUploadDto.getLgIpMacUpd());
		itemMasterDto.setUpdatedBy(itemMasterUploadDto.getUpdatedBy());
		itemMasterDto.setUpdatedDate(itemMasterUploadDto.getUpdatedDate());
		itemMasterDto.setStatus(itemMasterUploadDto.getStatus());
		itemMasterDto.setEntryFlag(itemMasterUploadDto.getEntryFlag());
		itemMasterDto.setManagement(Long.valueOf(itemMasterUploadDto.getManagement()));
		itemMasterDto.setValueMethod(Long.valueOf(itemMasterUploadDto.getValueMethod()));
		itemMasterDto.setName(itemMasterUploadDto.getName());
		long itemcodeNumber = 0;
		itemcodeNumber = seqGenFunctionUtility.generateSequenceNo(MainetMMConstants.MmItemMaster.ITM,
				MainetMMConstants.MmItemMaster.MM_ITEMMASTER, MainetMMConstants.MmItemMaster.MM_ITEMCODE,
				itemMasterUploadDto.getOrgId(), MainetConstants.MODE_CREATE, null);
		final StringBuilder itemCodeNo = new StringBuilder();
		itemCodeNo.append(SEQUENCE_NO).append(itemcodeNumber).toString();
		itemMasterDto.setItemCode("ITM" + itemCodeNo.toString());
		itemMasterDto.setCategory(Long.valueOf(itemMasterUploadDto.getCategory()));
		itemMasterDto.setClassification(Long.valueOf(itemMasterUploadDto.getClassification()));
		itemMasterDto.setDescription(itemMasterUploadDto.getDescription());
		itemMasterDto.setExpiryType(Long.valueOf(itemMasterUploadDto.getExpiryType()));
		itemMasterDto.setHsnCode(itemMasterUploadDto.getHsnCode());
		itemMasterDto.setIsExpiry(itemMasterUploadDto.getIsExpiry());
		itemMasterDto.setIsAsset(itemMasterUploadDto.getIsAsset());
		itemMasterDto.setItemGroup(Long.valueOf(itemMasterUploadDto.getItemGroup()));
		itemMasterDto.setItemSubGroup(Long.valueOf(itemMasterUploadDto.getItemSubGroup()));
		itemMasterDto.setMinLevel(itemMasterUploadDto.getMinLevel());
		itemMasterDto.setReorderLevel(itemMasterUploadDto.getReorderLevel());
		itemMasterDto.setTaxPercentage(itemMasterUploadDto.getTaxPercentage());
		itemMasterDto.setType(Long.valueOf(itemMasterUploadDto.getType()));
		itemMasterDto.setUom(Long.valueOf(itemMasterUploadDto.getUom()));
		itemMasterDto.setLmodDate(itemMasterUploadDto.getLmodDate());
		BeanUtils.copyProperties(itemMasterDto, itemMasterEntity);
		itemMasterRepository.save(itemMasterEntity);

	}

	public Long ItemNameCount(String name, Long orgId) {
		long id = itemMasterRepository.ItemNameCount(orgId, name);
		return id;

	}

	
	@Override
	@Transactional
	public Object[] getItemDetailObjectByItemId(Long itenId) {
		return itemMasterRepository.getItemDetailObjectByItemId(itenId);
	}


	@Override
	public long getUomByitemCode(Long orgId, Long itemId) {
		return itemMasterRepository.getUomByitemCode(orgId, itemId);
	}


	@Override
	public Map<String, String> getItemIdNameListByItemIdsOrgId(PurchaseRequistionDto dto) {
		List<Object[]> itemIdNameObjects = itemMasterRepository.getItemIdNameListByItemIdsOrgId(dto.getItemIds(), dto.getOrgId());
		Map<String,String> itemIdNameMap=new HashMap<>();
		itemIdNameObjects.forEach(itemIdName -> itemIdNameMap.put(itemIdName[0].toString(), itemIdName[1].toString()));
		return itemIdNameMap;
	}	
	
	@Override
	@Transactional
	public List<Object[]> getActiveItemIdNameListByOrgId(Long orgId) {
		return itemMasterRepository.getActiveItemIdNameListByOrgId(orgId, MainetConstants.FlagA);
	}
}
