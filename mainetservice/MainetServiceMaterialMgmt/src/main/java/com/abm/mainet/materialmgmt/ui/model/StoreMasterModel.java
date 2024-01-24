package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.StoreGroupMappingDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;

@Component
@Scope("session")
public class StoreMasterModel extends AbstractFormModel{
	
	private static final long serialVersionUID = 578932568657570829L;
	
	@Autowired
    private IStoreMasterService storeMasterService;

	private StoreMasterDTO storeMasterDTO = new StoreMasterDTO();
	
	private List<TbLocationMas> location = new ArrayList<>();
		
	private List<TbLocationMas> locList = new ArrayList<>();
	
	private List<LookUp> lookupList=new ArrayList<>();
	
	private List<StoreMasterDTO> storeMasterSummmaryDataList=new ArrayList<>();
	
	private List<StoreGroupMappingDto> storeGroupMappingDto = new ArrayList<>();
		
	private String excelFilePath;
	
	private String successFlag;

    private String saveMode;
   
    private List<Object[]> inchargeList;
    
    final ApplicationSession session = ApplicationSession.getInstance();
    
	@Override
	public boolean saveForm() {
		if (storeMasterDTO.getStoreId() == null) {
			storeMasterDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			storeMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			storeMasterDTO.setUpdatedDate(new Date());
			storeMasterDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			storeMasterDTO.setLangId(UserSession.getCurrent().getOrganisation().getOrgid());
			storeMasterDTO.setUserId(UserSession.getCurrent().getOrganisation().getOrgid());
			storeMasterDTO.setStatus(MainetConstants.CommonConstants.CHAR_Y);
			if (storeMasterDTO.getStoreGrMappingDtoList() != null) {
				for (StoreGroupMappingDto storeDetails : storeMasterDTO.getStoreGrMappingDtoList()) {
					storeDetails.setStatus(MainetConstants.CommonConstants.CHAR_Y);
					storeDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					storeDetails.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					storeDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					storeDetails.setUpdatedDate(new Date());
					storeDetails.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					storeDetails.setLangId(UserSession.getCurrent().getOrganisation().getOrgid());
				}
			}
		}
		StoreMasterDTO dto = storeMasterService.saveStore(storeMasterDTO);
		if(null == storeMasterDTO.getStoreId()) {
			setSuccessMessage(session.getMessage("store.master.code") + MainetConstants.WHITE_SPACE + dto.getStoreCode()
				+ MainetConstants.WHITE_SPACE + session.getMessage("store.master.submitt.success"));			
		} else {
			if (MainetConstants.CommonConstants.CHAR_Y == storeMasterDTO.getStatus())
				setSuccessMessage(session.getMessage("store.master.code") + MainetConstants.WHITE_SPACE + dto.getStoreCode()
					+ MainetConstants.WHITE_SPACE + session.getMessage("store.master.edit.success"));
			else
				setSuccessMessage(session.getMessage("store.master.code") + MainetConstants.WHITE_SPACE + dto.getStoreCode()
					+ MainetConstants.WHITE_SPACE + session.getMessage("store.master.inactive.success"));			
		}
		return true;
	}

	public StoreMasterDTO getStoreMasterDTO() {
		return storeMasterDTO;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setStoreMasterDTO(StoreMasterDTO storeMasterDTO) {
		this.storeMasterDTO = storeMasterDTO;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<TbLocationMas> getLocation() {
		return location;
	}

	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}

	public List<TbLocationMas> getLocList() {
		return locList;
	}

	public void setLocList(List<TbLocationMas> locList) {
		this.locList = locList;
	}

	public List<LookUp> getLookupList() {
		return lookupList;
	}

	public void setLookupList(List<LookUp> lookupList) {
		this.lookupList = lookupList;
	}

	public List<StoreGroupMappingDto> getStoreGroupMappingDto() {
		return storeGroupMappingDto;
	}

	public void setStoreGroupMappingDto(List<StoreGroupMappingDto> storeGroupMappingDto) {
		this.storeGroupMappingDto = storeGroupMappingDto;
	}

	public List<StoreMasterDTO> getStoreMasterSummmaryDataList() {
		return storeMasterSummmaryDataList;
	}

	public void setStoreMasterSummmaryDataList(List<StoreMasterDTO> storeMasterSummmaryDataList) {
		this.storeMasterSummmaryDataList = storeMasterSummmaryDataList;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}

	public List<Object[]> getInchargeList() {
		return inchargeList;
	}

	public void setInchargeList(List<Object[]> inchargeList) {
		this.inchargeList = inchargeList;
	}	
	
}
