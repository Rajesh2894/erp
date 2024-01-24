package com.abm.mainet.materialmgmt.ui.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.BinDefMasDto;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class BinMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(BinMasterModel.class);

	private BinDefMasDto binDefMasDto = new BinDefMasDto();

	private BinLocMasDto binLocMasDto  =new BinLocMasDto();
	
	private String modeType;
	
	private String formType;
	
	private List<BinDefMasDto> defList;
	
	private List<BinLocMasDto> locList;
	
	private List<Object[]> storeObjectList;
	
	@Autowired
	private IBinMasService iBinMasService;
	
	@Override
	public boolean saveForm() {
    	boolean status=false;
    	if(MainetMMConstants.BIN_MASTER.BD.equalsIgnoreCase(this.getFormType())) {
    		LOG.info("Start execution of Bin def Master saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
	    	validateBinDefForm();
			if (hasValidationErrors()) {
				return false;
			}
			final UserSession userSession = UserSession.getCurrent();
			Employee emp = UserSession.getCurrent().getEmployee();
			binDefMasDto.setOrgId(userSession.getOrganisation().getOrgid());
			binDefMasDto.setUserId(userSession.getEmployee().getEmpId());
			binDefMasDto.setLmodDate(new Date());
			binDefMasDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			binDefMasDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			binDefMasDto.setUpdatedBy(emp.getEmpId());
			binDefMasDto.setUpdatedDate(new Date());
			binDefMasDto.setCreatedBy(emp.getEmpId());
			binDefMasDto.setCreatedDate(new Date());
			iBinMasService.save(binDefMasDto);
			this.setSuccessMessage(getAppSession().getMessage("bin.def.master.form.submit.success"));
			LOG.info("End execution of Bin def Master saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
			status = true;
	 	 }else {
	 		LOG.info("Start execution of Bin Location Master saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
	 		validateBinLocForm();
			if (hasValidationErrors()) {
				return false;
			}
			final UserSession userSession = UserSession.getCurrent();
			Employee emp = UserSession.getCurrent().getEmployee();
			binLocMasDto.setOrgId(userSession.getOrganisation().getOrgid());
			binLocMasDto.setUserId(userSession.getEmployee().getEmpId());
			binLocMasDto.setLmodDate(new Date());
			binLocMasDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			binLocMasDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			binLocMasDto.setUpdatedBy(emp.getEmpId());
			binLocMasDto.setUpdatedDate(new Date());
			binLocMasDto.setCreatedBy(emp.getEmpId());
			binLocMasDto.setCreatedDate(new Date());
			iBinMasService.saveBinLocation(binLocMasDto);
			iBinMasService.deleteEmptyLocId();
			this.setSuccessMessage(getAppSession().getMessage("bin.loc.master.form.submit.success"));
			LOG.info("End execution of Bin Location Master saveForm"+UserSession.getCurrent().getOrganisation().getOrgid());
	 		status = true;
	 	 }
      return status;
	}

	private void validateBinLocForm() {		
		if(binLocMasDto.getStoreId() == null || binLocMasDto.getStoreId() == 0)
			 addValidationError(getAppSession().getMessage("error.binloc.storeName"));
		if(binLocMasDto.getStoreLocation() == null || binLocMasDto.getStoreLocation().isEmpty())
			addValidationError(getAppSession().getMessage("error.binloc.storeLocation"));
		if(binLocMasDto.getStoreAdd() == null || binLocMasDto.getStoreAdd().isEmpty())
			addValidationError(getAppSession().getMessage("error.binloc.storeAdd"));
		if(binLocMasDto.getBinLocation() == null || binLocMasDto.getBinLocation().isEmpty())
			 addValidationError(getAppSession().getMessage("error.binloc.locationName"));
		if(binLocMasDto.getDefinitions() == null || binLocMasDto.getDefinitions().isEmpty())
			addValidationError(getAppSession().getMessage("error.binloc.definitions"));	
	}

	private void validateBinDefForm() {
		if (binDefMasDto.getDefName() == null || binDefMasDto.getDefName().isEmpty())
			 addValidationError(getAppSession().getMessage("error.bindef.defName"));
		if (binDefMasDto.getDescription() == null || binDefMasDto.getDescription().isEmpty())
			addValidationError(getAppSession().getMessage("error.bindef.description"));
		if (binDefMasDto.getPriority() == null)
			addValidationError(getAppSession().getMessage("error.bindef.priority"));
		else if(binDefMasDto.getPriority().toString().length() > 4)
			addValidationError(getAppSession().getMessage("error.bindef.priority.length"));
	}

	public BinDefMasDto getBinDefMasDto() {
		return binDefMasDto;
	}

	public void setBinDefMasDto(BinDefMasDto binDefMasDto) {
		this.binDefMasDto = binDefMasDto;
	}

	public BinLocMasDto getBinLocMasDto() {
		return binLocMasDto;
	}

	public void setBinLocMasDto(BinLocMasDto binLocMasDto) {
		this.binLocMasDto = binLocMasDto;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public List<BinDefMasDto> getDefList() {
		return defList;
	}

	public void setDefList(List<BinDefMasDto> defList) {
		this.defList = defList;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public List<BinLocMasDto> getLocList() {
		return locList;
	}

	public void setLocList(List<BinLocMasDto> locList) {
		this.locList = locList;
	}

	public List<Object[]> getStoreObjectList() {
		return storeObjectList;
	}

	public void setStoreObjectList(List<Object[]> storeObjectList) {
		this.storeObjectList = storeObjectList;
	}

}
