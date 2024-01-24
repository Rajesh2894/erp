package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dao.GrnInspectionDao;
import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;
import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;
import com.abm.mainet.materialmgmt.domain.GoodsreceivedNotesitemEntity;
import com.abm.mainet.materialmgmt.domain.GrnInspectionItemDetEntity;
import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.GoodsreceivedNotesitemDto;
import com.abm.mainet.materialmgmt.dto.GrnInspectionItemDetDTO;
import com.abm.mainet.materialmgmt.repository.GoodsReceivedNotesRepository;
import com.abm.mainet.materialmgmt.repository.ICommonTransactionRepository;
import com.abm.mainet.materialmgmt.repository.PurchaseOrderRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Service
public class GoodsReceivedNotesServiceImpl implements Igoodsrecievednoteservice {

	@Autowired
	private GoodsReceivedNotesRepository goodsReceivedNotesRepository;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;

	@Resource
	private ItemMasterService itemMasterService;
	
	@Resource
	private GrnInspectionDao inspectionDao;
	
	@Resource
	private ICommonTransactionRepository transactionRepository;

	
	@Override
	public void saveGoodsReceivedNotes(GoodsReceivedNotesDto goodsReceivedNotesDto) {
		GoodsReceivedNotesEntity receivedNotesEntity = new GoodsReceivedNotesEntity();
		BeanUtils.copyProperties(goodsReceivedNotesDto, receivedNotesEntity);
		goodsReceivedNotesDto.getGoodsreceivedNotesItemList().forEach(grnItemDto->{
			if(null != grnItemDto.getReceivedqty() && grnItemDto.getReceivedqty() > MainetConstants.NUMBERS.ZERO) {
				GoodsreceivedNotesitemEntity notesItemEntity = new GoodsreceivedNotesitemEntity();
				BeanUtils.copyProperties(grnItemDto, notesItemEntity);
				notesItemEntity.setGoodsReceivedNote(receivedNotesEntity);
				ItemMasterEntity entity = new ItemMasterEntity();
				entity.setItemId(grnItemDto.getItemid());
				notesItemEntity.setItemMasterEntity(entity);
				notesItemEntity.setStoreid(receivedNotesEntity.getStoreid());
				receivedNotesEntity.getGoodsItemDetail().add(notesItemEntity);
			}
		});
		goodsReceivedNotesRepository.save(receivedNotesEntity);
	}

	@Override
	@Transactional
	public GoodsReceivedNotesDto saveGrnInspectionData(GoodsReceivedNotesDto goodsReceivedNotesDto,
			List<Long> removeInBatchIdList, List<Long> removeSerialIdList, List<Long> removeNotInBatchList) {
		GoodsReceivedNotesEntity goodsReceivedNotesEntity = new GoodsReceivedNotesEntity();
		BeanUtils.copyProperties(goodsReceivedNotesDto, goodsReceivedNotesEntity);
		ItemMasterEntity itemMasterEntity = new ItemMasterEntity();
		GoodsreceivedNotesitemEntity notesitemEntity = new GoodsreceivedNotesitemEntity();
		List<GoodsreceivedNotesitemEntity> goodsreceivedNotesitemEntities = new ArrayList<>();
		List<GrnInspectionItemDetEntity> grnInspectionItemDetEntities = new ArrayList<>();

		for (GoodsreceivedNotesitemDto notesitemDto : goodsReceivedNotesDto.getGoodsreceivedNotesItemList()) {
			notesitemEntity = new GoodsreceivedNotesitemEntity();
			itemMasterEntity = new ItemMasterEntity();
			itemMasterEntity.setItemId(notesitemDto.getItemid());
			BeanUtils.copyProperties(notesitemDto, notesitemEntity);
			notesitemEntity.setGoodsReceivedNote(goodsReceivedNotesEntity);
			notesitemEntity.setItemMasterEntity(itemMasterEntity);

			GrnInspectionItemDetEntity inspectionItemDetEntity;
			for (GrnInspectionItemDetDTO ispectionItemsDetDto : notesitemDto.getIspectionItemsList()) {
				if (notesitemEntity.getGrnitemid().equals(ispectionItemsDetDto.getGrnitemid())) {
					inspectionItemDetEntity = new GrnInspectionItemDetEntity();
					BeanUtils.copyProperties(ispectionItemsDetDto, inspectionItemDetEntity);
					inspectionItemDetEntity.setGoodsReceivedNote(goodsReceivedNotesEntity);
					inspectionItemDetEntity.setItemEntity(notesitemEntity);
					inspectionItemDetEntity.setItemMasterEntity(itemMasterEntity);
					inspectionItemDetEntity.setStoreId(notesitemDto.getStoreid());
					grnInspectionItemDetEntities.add(inspectionItemDetEntity);
				}
			}
			notesitemEntity.setGrnItemEntities(grnInspectionItemDetEntities);
			goodsreceivedNotesitemEntities.add(notesitemEntity);
		}
		goodsReceivedNotesEntity.setGoodsItemDetail(goodsreceivedNotesitemEntities);
		goodsReceivedNotesRepository.save(goodsReceivedNotesEntity);

		if (removeInBatchIdList != null && removeInBatchIdList.size() != 0)
			goodsReceivedNotesRepository.removeInBatchsById(goodsReceivedNotesEntity.getUserId(), removeInBatchIdList);
		if (removeSerialIdList != null && removeSerialIdList.size() != 0)
			goodsReceivedNotesRepository.removeSerialsById(goodsReceivedNotesEntity.getUserId(), removeSerialIdList);
		if (removeNotInBatchList != null && removeNotInBatchList.size() != 0)
			goodsReceivedNotesRepository.removeNotInBatchByIds(goodsReceivedNotesEntity.getUserId(), removeNotInBatchList);

		 /** Save GRN Details to Transaction Table */
		if(MainetConstants.FlagS.equalsIgnoreCase(goodsReceivedNotesEntity.getStatus())) {
			transactionRepository.save(saveGrnStoreEntryDataToTransactionEntity(goodsReceivedNotesDto));
		}		
		
		return goodsReceivedNotesDto;
	}
	
	/** Transaction Type : Purchase-P, Indent-I, Store Indent Outward-M, Store Indent Inward-S
		Disposal-D, Adjustment-A, Transfer-T, Expired-E, Opening Balance-O, Returned-R */
	private List<CommonTransactionEntity> saveGrnStoreEntryDataToTransactionEntity(GoodsReceivedNotesDto goodsReceivedNotesDto) {
		List<CommonTransactionEntity> transactionEntityList = new ArrayList<>();
		goodsReceivedNotesDto.getGoodsreceivedNotesItemList().forEach(grnNotesItem -> {
			grnNotesItem.getIspectionItemsList().forEach(ispectionItemDetail -> {
				if(MainetConstants.CommonConstants.CHAR_Y == ispectionItemDetail.getDecision()) {
					CommonTransactionEntity transactionEntity = new CommonTransactionEntity();
					transactionEntity.setTransactionDate(new Date());
					transactionEntity.setTransactionType(MainetConstants.FlagP);
					transactionEntity.setReferenceNo(goodsReceivedNotesDto.getGrnno());
					transactionEntity.setStoreId(goodsReceivedNotesDto.getStoreid());
					transactionEntity.setItemId(grnNotesItem.getItemid());
					transactionEntity.setBinLocation(ispectionItemDetail.getBinLocation());
					transactionEntity.setItemNo(ispectionItemDetail.getItemNo());
					transactionEntity.setCreditQuantity(new BigDecimal(ispectionItemDetail.getQuantity()));
					transactionEntity.setMfgDate(ispectionItemDetail.getMfgDate());
					transactionEntity.setExpiryDate(ispectionItemDetail.getExpiryDate());
					transactionEntity.setStatus(MainetConstants.FlagY);
					transactionEntity.setDisposalStatus(MainetConstants.FlagN);
					transactionEntity.setOrgId(ispectionItemDetail.getOrgId());
					transactionEntity.setUserId(ispectionItemDetail.getUserId());
					transactionEntity.setLangId(ispectionItemDetail.getLangId());
					transactionEntity.setCreatedDate(new Date());
					transactionEntity.setLgIpMac(ispectionItemDetail.getLgIpMacUpd());
					transactionEntityList.add(transactionEntity);
				}
			});
		});
		return transactionEntityList;
	}
	

	@Override
	public List<String> checkExsistingNumbers(List<String> itemNumberList, Long storeId, Long orgId) {
		return goodsReceivedNotesRepository.getDuplicateList(itemNumberList, storeId, orgId);
	}

	@Override
	@Transactional
	public GoodsReceivedNotesDto getDetailsByGrnNo(Long grnid, Long orgId) {

		GoodsReceivedNotesEntity entitie = goodsReceivedNotesRepository.findByGrnidAndOrgIdOrderByGrnid(grnid, orgId);

		GoodsReceivedNotesDto receivedNotesDto = new GoodsReceivedNotesDto();
		BeanUtils.copyProperties(entitie, receivedNotesDto);
		
		Object[] poObject = (Object[]) purchaseOrderRepo.getPurcahseOrderDateAndStore(receivedNotesDto.getPoid(), orgId)[0];
		if(null != poObject) {
			receivedNotesDto.setPoNumber(poObject[0].toString());
			String[] poDate = poObject[1].toString().split(MainetConstants.WHITE_SPACE);
			receivedNotesDto.setPoDate(Utility.stringToDate(LocalDate.parse(poDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			receivedNotesDto.setStoreid(Long.parseLong(poObject[2].toString()));
			receivedNotesDto.setStoreName(poObject[3].toString());
		}
		
		List<GoodsreceivedNotesitemDto> dtoItemList = new ArrayList<>();
		GoodsreceivedNotesitemDto goodsReceivedNotesitemDto = null;
		for (GoodsreceivedNotesitemEntity goodsreceivedNotesitemEntity : entitie.getGoodsItemDetail()) {
			goodsReceivedNotesitemDto = new GoodsreceivedNotesitemDto();
			BeanUtils.copyProperties(goodsreceivedNotesitemEntity, goodsReceivedNotesitemDto);

			goodsReceivedNotesitemDto.setIsExpiry(goodsreceivedNotesitemEntity.getItemMasterEntity().getIsExpiry());
			goodsReceivedNotesitemDto.setItemid(goodsreceivedNotesitemEntity.getItemMasterEntity().getItemId());
			goodsReceivedNotesitemDto.setItemDesc(goodsreceivedNotesitemEntity.getItemMasterEntity().getName());
			goodsReceivedNotesitemDto.setUomDesc(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(goodsreceivedNotesitemEntity.getItemMasterEntity().getUom(),
							orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());

			LookUp managementId = (CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					Long.parseLong(goodsreceivedNotesitemEntity.getItemMasterEntity().getManagement()), orgId,
					MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX));
			goodsReceivedNotesitemDto.setManagementCode(managementId.getLookUpDesc());
			goodsReceivedNotesitemDto.setManagement(managementId.getLookUpCode());

			List<GrnInspectionItemDetDTO> inspectionItemDetDTOList = new ArrayList<>();

			GrnInspectionItemDetDTO inspectionItemDetDTO = new GrnInspectionItemDetDTO();
			for (GrnInspectionItemDetEntity itemDetEntity : goodsreceivedNotesitemEntity.getGrnItemEntities()) {
				if (itemDetEntity.getItemEntity().getGrnitemid().equals(goodsreceivedNotesitemEntity.getGrnitemid())) {
					inspectionItemDetDTO = new GrnInspectionItemDetDTO();
					inspectionItemDetDTO.setGrnitemid(itemDetEntity.getItemEntity().getGrnitemid());
					BeanUtils.copyProperties(itemDetEntity, inspectionItemDetDTO);
					inspectionItemDetDTOList.add(inspectionItemDetDTO);
				}
			}
			goodsReceivedNotesitemDto.setIspectionItemsList(inspectionItemDetDTOList);
			dtoItemList.add(goodsReceivedNotesitemDto);
		}
		receivedNotesDto.setGoodsreceivedNotesItemList(dtoItemList);
		return receivedNotesDto;
	}

	@Override
	@Transactional
	public List<GoodsReceivedNotesDto> searchGoodsReceivedNotes(Long storeId, Long grnid, Date fromDate, Date toDate,
			Long poid, Long orgId, String status) {
		List<Object[]> receivedNotesObjectList = null;

		if(MainetConstants.FlagY.equalsIgnoreCase(status))  /** GRN Summary + Search **/
			receivedNotesObjectList = goodsReceivedNotesRepository.searchGoodsReceivedNotes(storeId, grnid,
					fromDate, toDate, poid, orgId);
		else if(MainetConstants.FlagI.equalsIgnoreCase(status)) /** GRN Inspection Summary +  Search  **/
			receivedNotesObjectList = inspectionDao.searchGRNInspectionData(storeId, grnid, fromDate, toDate, poid, orgId);
		else										/** GRN Inspection Add && Store Entry Add + Summary + Search **/
			receivedNotesObjectList = goodsReceivedNotesRepository.searchGRNInspectionSearchData(storeId, grnid,
					fromDate, toDate, poid, orgId, status);

		List<GoodsReceivedNotesDto> receivedNotesDtoList = new ArrayList<>();
		String[] date;
		for (Object[] receivedNotesObject : receivedNotesObjectList) {
			GoodsReceivedNotesDto receivedNotesDto = new GoodsReceivedNotesDto();
			receivedNotesDto.setGrnid(Long.valueOf(receivedNotesObject[0].toString()));
			receivedNotesDto.setGrnno(receivedNotesObject[1].toString());
			if(null != receivedNotesObject[2]) {
				date = receivedNotesObject[2].toString().split(MainetConstants.WHITE_SPACE);
				
				if(MainetConstants.FlagY.equalsIgnoreCase(status))
					receivedNotesDto.setReceiveddate(Utility.stringToDate(
						LocalDate.parse(date[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
				else
					receivedNotesDto.setInspectiondate(Utility.stringToDate(
						LocalDate.parse(date[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));				
			}			
			receivedNotesDto.setPoid(Long.valueOf(receivedNotesObject[3].toString()));
			receivedNotesDto.setStoreid(Long.valueOf(receivedNotesObject[4].toString()));
			receivedNotesDto.setStatus(receivedNotesObject[5].toString());
			receivedNotesDtoList.add(receivedNotesDto);
		}
		return receivedNotesDtoList;
	}

	
	@Override
	public List<Object[]> grnIdNoListByStatus(Long orgId, String status) {
		return goodsReceivedNotesRepository.grnIdNoListByStatus(orgId, status);
	}

	@Override
	@Transactional
	public GoodsReceivedNotesDto getDataById(Long grnid) {
		GoodsReceivedNotesEntity entity = goodsReceivedNotesRepository.findOne(grnid);
		GoodsReceivedNotesDto dto = new GoodsReceivedNotesDto();
		List<GoodsreceivedNotesitemDto> GoodsreceivedNotesitemDto = new ArrayList<GoodsreceivedNotesitemDto>();
		BeanUtils.copyProperties(entity, dto);
		entity.getGoodsItemDetail().forEach(entity1 -> {
			GoodsreceivedNotesitemDto dto1 = new GoodsreceivedNotesitemDto();
			BeanUtils.copyProperties(entity1, dto1);
			GoodsreceivedNotesitemDto.add(dto1);
		});
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public GoodsReceivedNotesDto goodsReceivedNoteEditAndView(Long grnid, Long orgId) {
		GoodsReceivedNotesEntity grnEntity = goodsReceivedNotesRepository.findByGrnidAndOrgIdOrderByGrnid(grnid, orgId);
		GoodsReceivedNotesDto receivedNotesDto = new GoodsReceivedNotesDto();
		BeanUtils.copyProperties(grnEntity, receivedNotesDto);

		Object[] poObject = (Object[]) purchaseOrderRepo.getPurcahseOrderDateAndStore(receivedNotesDto.getPoid(), orgId)[0];
		if(null != poObject) {
			receivedNotesDto.setPoNumber(poObject[0].toString());
			String[] poDate = poObject[1].toString().split(MainetConstants.WHITE_SPACE);
			receivedNotesDto.setPoDate(Utility.stringToDate(LocalDate.parse(poDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			receivedNotesDto.setStoreid(Long.parseLong(poObject[2].toString()));
			receivedNotesDto.setStoreName(poObject[3].toString());
		}
		
		grnEntity.getGoodsItemDetail().forEach(itemDetailEntity->{
			GoodsreceivedNotesitemDto notesItemDto = new GoodsreceivedNotesitemDto();
			BeanUtils.copyProperties(itemDetailEntity, notesItemDto);	
			
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(itemDetailEntity.getItemMasterEntity().getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			notesItemDto.setItemDesc(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : MainetConstants.BLANK);
			notesItemDto.setUomDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());				
			notesItemDto.setManagementDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(
					tbMGItemMaster.getManagement(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
			receivedNotesDto.getGoodsreceivedNotesItemList().add(notesItemDto);
		});
		return receivedNotesDto;
	}

	@Override
	public List<Object[]> getPurchaseOrderListForGRN(Long orgId) {
		List<Object[]> purchaseOrderObjects = goodsReceivedNotesRepository.getPurchaseOrderListForGRN(orgId, MainetConstants.FlagY);
		Map<Long, String> purchaseOrderMap = new HashMap<>();
		purchaseOrderObjects.forEach(object -> purchaseOrderMap.put(Long.valueOf(object[0].toString()), object[1].toString()));
		purchaseOrderObjects.clear();
		purchaseOrderObjects = purchaseOrderMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(entry -> new Object[] { entry.getKey(), entry.getValue() }).collect(Collectors.toList());
		return purchaseOrderObjects;
	}
		
	@Override
	public GoodsReceivedNotesDto getPurchaseOrderData(GoodsReceivedNotesDto goodsReceivedNotesDto, Long orgId) {
		goodsReceivedNotesDto.getGoodsreceivedNotesItemList().clear();
		Object[] poObject = (Object[]) purchaseOrderRepo.getPurcahseOrderDateAndStore(goodsReceivedNotesDto.getPoid(), orgId)[0];
		if(null != poObject) {
			String[] poDate = poObject[1].toString().split(MainetConstants.WHITE_SPACE);
			goodsReceivedNotesDto.setPoDate(Utility.stringToDate(LocalDate.parse(poDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			goodsReceivedNotesDto.setStoreid(Long.parseLong(poObject[2].toString()));
			goodsReceivedNotesDto.setStoreName(poObject[3].toString());
		}
		
		List<Object[]> entitiesList = goodsReceivedNotesRepository.getDataByPoId(goodsReceivedNotesDto.getPoid(), orgId);
		GoodsreceivedNotesitemDto itemDetailsDto;
		for (Object[] poDetailList : entitiesList) {
			if(Double.valueOf(poDetailList[3].toString()) > Double.valueOf(poDetailList[4].toString())) {
				itemDetailsDto = new GoodsreceivedNotesitemDto();
				itemDetailsDto.setItemid(Long.valueOf(poDetailList[2].toString()));
				Object[] itemObj = (Object[]) itemMasterService.getItemDetailObjectByItemId(itemDetailsDto.getItemid())[0];
				itemDetailsDto.setItemDesc(itemObj[1].toString());
				itemDetailsDto.setUom(Long.valueOf(itemObj[2].toString()));
				itemDetailsDto.setUomDesc(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(itemDetailsDto.getUom(), 
						orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
				itemDetailsDto.setManagementDesc((CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						Long.valueOf(itemObj[3].toString()), orgId, MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX)).getLookUpDesc());
				if (poDetailList[3] != null)
					itemDetailsDto.setOrederqty(Double.valueOf(poDetailList[3].toString()));
				if (poDetailList[4] != null)
					itemDetailsDto.setPrevrecqt(Double.valueOf(poDetailList[4].toString()));
				if (poDetailList[3] != null && poDetailList[4] != null)
					itemDetailsDto.setRemainingQty(itemDetailsDto.getOrederqty() - itemDetailsDto.getPrevrecqt());			
				goodsReceivedNotesDto.getGoodsreceivedNotesItemList().add(itemDetailsDto);
			}
		}
		return goodsReceivedNotesDto;
	}

}
