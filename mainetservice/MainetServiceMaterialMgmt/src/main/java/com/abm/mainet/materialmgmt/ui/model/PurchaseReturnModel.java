package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDetailDto;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.IPurchaseReturnService;
import com.abm.mainet.materialmgmt.ui.validator.PurchaseReturnValidator;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class PurchaseReturnModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private List<TbAcVendormaster> vendors = new ArrayList<>();

	private List<ItemMasterDTO> itemNameList = new ArrayList<>();

	private List<StoreMasterDTO> storeNameList = new ArrayList<StoreMasterDTO>();

	private List<BinLocMasDto> binLocList = new ArrayList<>();

	private PurchaseReturnDto purchaseReturnDto = new PurchaseReturnDto();

	private List<PurchaseReturnDto> purchaseReturnDtoList = new ArrayList<>();

	private List<PurchaseReturnDetailDto> purchaseReturnDetailDtoList = new ArrayList<>();

	private List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList = new ArrayList<>();

	private String saveMode;

	private static Logger LOGGER = Logger.getLogger(PurchaseReturnModel.class);

	@Autowired
	private IPurchaseReturnService purchaseReturnService;
	
	
	@Override
	public boolean saveForm() {
		boolean status = false;

		validateBean(purchaseReturnDto, PurchaseReturnValidator.class);
		if (hasValidationErrors())
			return false;
		else {
			if (purchaseReturnDto.getReturnId() == null) {
				purchaseReturnDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				purchaseReturnDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				purchaseReturnDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				purchaseReturnDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				purchaseReturnDto.setLmodDate(new Date());
				purchaseReturnDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
				purchaseReturnDto.getPurchaseReturnDetDtoList().forEach(returnDetailDto -> {
					returnDetailDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
					returnDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					returnDetailDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
					returnDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					returnDetailDto.setLmodDate(new Date());
					returnDetailDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				});
			}
			genratePurchaseReturnNo(purchaseReturnDto);
			purchaseReturnService.savePurchaseReturnData(purchaseReturnDto);
			status = true;
		}
		return status;
	}
	
		
	private void genratePurchaseReturnNo(PurchaseReturnDto purchaseReturnDto) {		
		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo("MMM", "mm_purchasereturn", "returnid", UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String returnNo = "PRN" + MainetMMConstants.MmItemMaster.STR + finacialYear + String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		purchaseReturnDto.setReturnNo(returnNo);	
		setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.purchase.return.saved.success") +
				MainetConstants.WHITE_SPACE + purchaseReturnDto.getReturnNo());
	}
		

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}

	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}

	public List<ItemMasterDTO> getItemNameList() {
		return itemNameList;
	}

	public void setItemNameList(List<ItemMasterDTO> itemNameList) {
		this.itemNameList = itemNameList;
	}

	public List<StoreMasterDTO> getStoreNameList() {
		return storeNameList;
	}

	public void setStoreNameList(List<StoreMasterDTO> storeNameList) {
		this.storeNameList = storeNameList;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public PurchaseReturnDto getPurchaseReturnDto() {
		return purchaseReturnDto;
	}

	public void setPurchaseReturnDto(PurchaseReturnDto purchaseReturnDto) {
		this.purchaseReturnDto = purchaseReturnDto;
	}

	public List<PurchaseReturnDto> getPurchaseReturnDtoList() {
		return purchaseReturnDtoList;
	}

	public void setPurchaseReturnDtoList(List<PurchaseReturnDto> purchaseReturnDtoList) {
		this.purchaseReturnDtoList = purchaseReturnDtoList;
	}

	public List<PurchaseReturnDetailDto> getPurchaseReturnDetailDtoList() {
		return purchaseReturnDetailDtoList;
	}

	public void setPurchaseReturnDetailDtoList(List<PurchaseReturnDetailDto> purchaseReturnDetailDtoList) {
		this.purchaseReturnDetailDtoList = purchaseReturnDetailDtoList;
	}

	public List<GoodsReceivedNotesDto> getGoodsReceivedNotesDtoList() {
		return goodsReceivedNotesDtoList;
	}

	public void setGoodsReceivedNotesDtoList(List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList) {
		this.goodsReceivedNotesDtoList = goodsReceivedNotesDtoList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

}
