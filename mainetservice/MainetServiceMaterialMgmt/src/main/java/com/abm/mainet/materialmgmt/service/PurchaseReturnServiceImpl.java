package com.abm.mainet.materialmgmt.service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dao.IPurchaseReturnDao;
import com.abm.mainet.materialmgmt.domain.PurchaseReturnDetailEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseReturnEntity;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDetailDto;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDto;
import com.abm.mainet.materialmgmt.repository.GoodsReceivedNotesRepository;
import com.abm.mainet.materialmgmt.repository.IPurchaseReturnRepository;
import com.abm.mainet.materialmgmt.repository.PurchaseOrderRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;


@Service
public class PurchaseReturnServiceImpl implements IPurchaseReturnService {
	
	@Autowired
	private IPurchaseReturnRepository iPurchaseReturnRepository ;	
		
	@Autowired
	private IStoreMasterService iStoreMasterService;
	
	@Autowired
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private ItemMasterService itemMasterService;
	
	@Autowired
	private GoodsReceivedNotesRepository goodsReceivedNotesRepository;
	
	@Autowired
	private IPurchaseReturnDao iPurchaseReturnDao;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Override
	@Transactional
	public PurchaseReturnDto savePurchaseReturnData(PurchaseReturnDto purchaseReturnDto) {
		PurchaseReturnEntity purchaseReturnEntity = new PurchaseReturnEntity();	
		BeanUtils.copyProperties(purchaseReturnDto, purchaseReturnEntity);
		purchaseReturnDto.getPurchaseReturnDetDtoList().forEach(purchaseReturnDetDto->{
			PurchaseReturnDetailEntity purchaseReturnDetailEntity= new PurchaseReturnDetailEntity();
			BeanUtils.copyProperties(purchaseReturnDetDto, purchaseReturnDetailEntity);
			purchaseReturnDetailEntity.setPurchaseReturnEntity(purchaseReturnEntity);
			purchaseReturnDetailEntity.setStoreId(purchaseReturnDto.getStoreId());
			purchaseReturnEntity.getPurchaseReturnDetList().add(purchaseReturnDetailEntity);		
		});
		iPurchaseReturnRepository.save(purchaseReturnEntity);
		return purchaseReturnDto;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<GoodsReceivedNotesDto> getGrnNumbersForReturn(Long orgId) {
		// Decision == N ~~ Rejected item and Status != 'D' ~~ Not Draft  in query
		List<Object[]> entitiesList = iPurchaseReturnRepository.fetchGrnNumberListForPurchaseReturn(MainetConstants.CommonConstants.CHAR_N, MainetConstants.FlagD, orgId);		
		List<GoodsReceivedNotesDto> receivedNotesDtoList = new ArrayList<>();
		GoodsReceivedNotesDto receivedNotesDto;
		for (Object[] obj : entitiesList) {
			receivedNotesDto = new GoodsReceivedNotesDto();
			if (obj[0] != null)
				receivedNotesDto.setGrnid(Long.valueOf(obj[0].toString()));
			if (obj[1] != null)
				receivedNotesDto.setGrnno(obj[1].toString());
			receivedNotesDtoList.add(receivedNotesDto);
		}
		return receivedNotesDtoList;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PurchaseReturnDto fetchRejectedItemDataByGRNId(Long grnId, Long orgId){
		PurchaseReturnDto purchaseReturnDto = new PurchaseReturnDto();
		
		// to get GoodsReceivedNotesEntity Data >>  Note: Status != D in query
		Object[] grnEntityObject = iPurchaseReturnRepository.getGRNDataByGRNId(grnId, orgId, MainetConstants.FlagD);
		Object[] goodsReceivedNotesEntity = (Object[]) grnEntityObject[0];
		purchaseReturnDto.setGrnId(Long.valueOf(goodsReceivedNotesEntity[0].toString()));
		purchaseReturnDto.setGrnNo(goodsReceivedNotesEntity[1].toString());
		purchaseReturnDto.setStoreId(Long.valueOf(goodsReceivedNotesEntity[2].toString()));
		purchaseReturnDto.setGrnDate(LocalDate.parse(goodsReceivedNotesEntity[3].toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));	
		purchaseReturnDto.setStoreName(iStoreMasterService.getStorenameByStoreId(purchaseReturnDto.getStoreId()));
		purchaseReturnDto.setPoId(Long.valueOf(goodsReceivedNotesEntity[4].toString()));
		purchaseReturnDto.setPoNo(goodsReceivedNotesEntity[5].toString());
		purchaseReturnDto.setVendorId(Long.valueOf(goodsReceivedNotesEntity[6].toString()));
		purchaseReturnDto.setVendorName(tbAcVendormasterService.getVendorNameById(purchaseReturnDto.getVendorId(), orgId));
	
		// to get Item Details Data	 >>  Note: Decision == N ~~ Rejected and Status == 'Y' ~~ Active
		List<Object[]> goodsReceivedNotesItemEntity = iPurchaseReturnRepository.getItemDataByGRNId(grnId, orgId,
														MainetConstants.CommonConstants.CHAR_N, MainetConstants.CommonConstants.CHAR_Y);
		PurchaseReturnDetailDto purReturnDetailDto;
		for(Object[] grnItemEntity : goodsReceivedNotesItemEntity) {
			purReturnDetailDto = new PurchaseReturnDetailDto();
			
			purReturnDetailDto.setGrnId(Long.valueOf(grnItemEntity[0].toString()));
			purReturnDetailDto.setGrnItemEntryId(Long.valueOf(grnItemEntity[1].toString()));
		
			purReturnDetailDto.setItemId(Long.valueOf(grnItemEntity[2].toString()));
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(purReturnDetailDto.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			purReturnDetailDto.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : MainetConstants.BLANK);
			purReturnDetailDto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());				
			purReturnDetailDto.setManagementMethod(CommonMasterUtility.getNonHierarchicalLookUpObject(
					tbMGItemMaster.getManagement(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
			
			if(null != grnItemEntity[3])
				purReturnDetailDto.setItemNo(grnItemEntity[3].toString() != null ? grnItemEntity[3].toString() : MainetConstants.BLANK);
			
			purReturnDetailDto.setQuantity(Double.valueOf(grnItemEntity[4].toString()));
			purReturnDetailDto.setRejectionRemark(CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(grnItemEntity[5].toString()),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());

			purchaseReturnDto.getPurchaseReturnDetDtoList().add(purReturnDetailDto);
		}
		return purchaseReturnDto;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public PurchaseReturnDto fetchRejectedItemDataByReturnId(Long returnId, Long orgId) {
		PurchaseReturnEntity purchaseReturnEntity = iPurchaseReturnRepository.findByReturnIdAndOrgId(returnId, orgId);
		PurchaseReturnDto purchaseReturnDto = new PurchaseReturnDto();
		BeanUtils.copyProperties(purchaseReturnEntity, purchaseReturnDto);
		purchaseReturnDto.setStoreName(iStoreMasterService.getStorenameByStoreId(purchaseReturnDto.getStoreId()));
		purchaseReturnDto.setVendorName(tbAcVendormasterService.getVendorNameById(purchaseReturnDto.getVendorId(), orgId));
		purchaseReturnDto.setGrnDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(goodsReceivedNotesRepository.findGRNDateByOrgIdAndGRNId(orgId, purchaseReturnDto.getGrnId())));
		purchaseReturnDto.setPoNo(purchaseOrderRepository.getAllPurchaseOrderNoById(orgId, purchaseReturnDto.getPoId()));
		purchaseReturnDto.setGrnNo(goodsReceivedNotesRepository.findGRNNumberByOrgIdAndGRNId(orgId, purchaseReturnDto.getGrnId()));

		purchaseReturnEntity.getPurchaseReturnDetList().forEach(purchaseReturnDetail->{
			PurchaseReturnDetailDto returnDetailDto = new PurchaseReturnDetailDto();
			BeanUtils.copyProperties(purchaseReturnDetail, returnDetailDto);
			
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(returnDetailDto.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			returnDetailDto.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : MainetConstants.BLANK);
			returnDetailDto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());				
			returnDetailDto.setManagementMethod(CommonMasterUtility.getNonHierarchicalLookUpObject(
					tbMGItemMaster.getManagement(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());

			Long rejectionRemarkId = goodsReceivedNotesRepository.getRejectionReasonByOrgIdAndGrnitementryid(returnDetailDto.getOrgId(), returnDetailDto.getGrnItemEntryId());
			returnDetailDto.setRejectionRemark(CommonMasterUtility.getNonHierarchicalLookUpObject(rejectionRemarkId,
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());
			
			purchaseReturnDto.getPurchaseReturnDetDtoList().add(returnDetailDto);		
		});
		return purchaseReturnDto;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<PurchaseReturnDto> fetchRejectedItemSummaryData(Long returnId, Long grnId, Date fromDate,
			Date toDate, Long storeId, Long vendorId, Long orgId) {
		
		List<Object[]> purchaseReturnEntitieList = iPurchaseReturnDao.searchPurchaseReturnSummaryData(returnId,
						grnId, fromDate, toDate, storeId, vendorId, orgId);
		
		List<PurchaseReturnDto> purchaseReturnDtoList = new ArrayList<>();
		PurchaseReturnDto purchaseReturnDto;
		String[] returnDate;
		for (Object[] object : purchaseReturnEntitieList) {
			purchaseReturnDto = new PurchaseReturnDto();
			
			purchaseReturnDto.setReturnId(Long.valueOf(object[0].toString()));
			purchaseReturnDto.setReturnNo(object[1].toString());
						
			returnDate = object[2].toString().split(MainetConstants.WHITE_SPACE);
	        purchaseReturnDto.setReturnDate(Utility.stringToDate(LocalDate.parse(returnDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			
			purchaseReturnDto.setGrnId(Long.valueOf(object[3].toString()));
			purchaseReturnDto.setGrnNo(goodsReceivedNotesRepository.findGRNNumberByOrgIdAndGRNId(orgId, purchaseReturnDto.getGrnId()));
			purchaseReturnDto.setPoId(Long.valueOf(object[4].toString()));
			purchaseReturnDto.setPoNo(purchaseOrderRepository.getAllPurchaseOrderNoById(orgId, purchaseReturnDto.getPoId()));
			purchaseReturnDto.setStoreId(Long.valueOf(object[5].toString()));
			purchaseReturnDto.setStoreName(iStoreMasterService.getStorenameByStoreId(purchaseReturnDto.getStoreId()));
			purchaseReturnDto.setVendorId(Long.valueOf(object[6].toString()));
			purchaseReturnDto.setVendorName(tbAcVendormasterService.getVendorNameById(purchaseReturnDto.getVendorId(), orgId));

			purchaseReturnDtoList.add(purchaseReturnDto);
		}
		return purchaseReturnDtoList;	
	}
		
}
