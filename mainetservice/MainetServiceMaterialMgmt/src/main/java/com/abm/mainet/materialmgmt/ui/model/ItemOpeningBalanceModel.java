package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.ItemOpeningBalanceService;
import com.abm.mainet.materialmgmt.ui.validator.ItemOpeningBalanceValidator;

@Component
@Scope("session")
public class ItemOpeningBalanceModel extends AbstractFormModel{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(ItemOpeningBalanceModel.class);
	
	private List<TbLocationMas> locList;
	private List<StoreMasterDTO> storeMasterList;
	private List<ItemMasterDTO> itemList;
	private ItemOpeningBalanceDto itemOpeningBalanceDto = new ItemOpeningBalanceDto();
	private List<BinLocMasDto> binLocList;
	private List<ItemOpeningBalanceDto> openingBalList;
	private String modeType;
	private String removeChildIds;
	private List<Object[]> storeObjectList;
	
	@Autowired
	private ItemOpeningBalanceService itemOpeningBalanceService;
	
	@Override
	public boolean saveForm() {
    	    LOG.info("Start execution of Item Opening Balance saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
    		validateBean(this.getItemOpeningBalanceDto(), ItemOpeningBalanceValidator.class);
			if (hasValidationErrors()) {
				return false;
			}
			ItemOpeningBalanceDto itemOpeningBalanceDto = this.getItemOpeningBalanceDto();
			final UserSession userSession = UserSession.getCurrent();
			Employee emp = UserSession.getCurrent().getEmployee();
			itemOpeningBalanceDto.setOrgId(userSession.getOrganisation().getOrgid());
			itemOpeningBalanceDto.setUserId(userSession.getEmployee().getEmpId());
			itemOpeningBalanceDto.setLmodDate(new Date());
			itemOpeningBalanceDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			itemOpeningBalanceDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			itemOpeningBalanceDto.setUpdatedBy(emp.getEmpId());
			itemOpeningBalanceDto.setUpdatedDate(new Date());
			itemOpeningBalanceDto.setCreatedBy(emp.getEmpId());
			itemOpeningBalanceDto.setCreatedDate(new Date());
			final List<Long> removeIds = new ArrayList<>();
			final String ids = getRemoveChildIds();
            if (null != ids && !ids.isEmpty()) {
                final String array[] = ids.split(MainetConstants.operator.COMMA);
                for (final String string : array) {
                    removeIds.add(Long.valueOf(string));
                }
            }
			itemOpeningBalanceService.save(itemOpeningBalanceDto,removeIds);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("material.item.openingBalanceFormSavedSucess"));
			LOG.info("End execution of Item Opening Balance saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
			return true;
	}

	public List<TbLocationMas> getLocList() {
		return locList;
	}

	public void setLocList(List<TbLocationMas> locList) {
		this.locList = locList;
	}

	public List<StoreMasterDTO> getStoreMasterList() {
		return storeMasterList;
	}

	public void setStoreMasterList(List<StoreMasterDTO> storeMasterList) {
		this.storeMasterList = storeMasterList;
	}

	public ItemOpeningBalanceDto getItemOpeningBalanceDto() {
		return itemOpeningBalanceDto;
	}

	public void setItemOpeningBalanceDto(ItemOpeningBalanceDto itemOpeningBalanceDto) {
		this.itemOpeningBalanceDto = itemOpeningBalanceDto;
	}

	public List<ItemMasterDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemMasterDTO> itemList) {
		this.itemList = itemList;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public List<ItemOpeningBalanceDto> getOpeningBalList() {
		return openingBalList;
	}

	public void setOpeningBalList(List<ItemOpeningBalanceDto> openingBalList) {
		this.openingBalList = openingBalList;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getRemoveChildIds() {
		return removeChildIds;
	}

	public void setRemoveChildIds(String removeChildIds) {
		this.removeChildIds = removeChildIds;
	}

	public List<Object[]> getStoreObjectList() {
		return storeObjectList;
	}

	public void setStoreObjectList(List<Object[]> storeObjectList) {
		this.storeObjectList = storeObjectList;
	}

}
