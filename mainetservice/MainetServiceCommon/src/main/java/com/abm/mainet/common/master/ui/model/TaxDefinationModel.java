package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.master.service.ITaxDefinationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Component
@Scope("session")
public class TaxDefinationModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7044455272677778460L;
	
	@Autowired
	private ITaxDefinationService iTaxDefinationService;
	
	List<TaxDefinationDto> taxDefinationList=new ArrayList<>();
	private String removeTaxesId;
    private List<LookUp> taxList = new ArrayList<LookUp>();
    private List<LookUp> valueTypeList = new ArrayList<LookUp>();
    private String saveMode;
    private Long parenetTaxId;
    
	@Override
	public boolean saveForm() {
		Date todayDate = new Date();
		boolean status=true;
		
		for (TaxDefinationDto dto : taxDefinationList) {

			dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			if (dto.getCreatedBy() == null) {
				dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setCreatedDate(todayDate);
				dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			} else {
				dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				dto.setUpdatedDate(todayDate);
				dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}

		List<Long> removeIds = null;
		String ids = getRemoveTaxesId();
		if (ids != null && !ids.isEmpty()) {
			removeIds = new ArrayList<>();
			String array[] = ids.split(MainetConstants.operator.COMMA);
			for (String id : array) {
				removeIds.add(Long.valueOf(id));
			}
		}
		
		iTaxDefinationService.saveUpdateTaxDefinationList(taxDefinationList, removeIds);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("Tax Deduction Saved sucessfully"));
		return status;
	}

	public List<TaxDefinationDto> getTaxDefinationList() {
		return taxDefinationList;
	}

	public void setTaxDefinationList(List<TaxDefinationDto> taxDefinationList) {
		this.taxDefinationList = taxDefinationList;
	}

	public String getRemoveTaxesId() {
		return removeTaxesId;
	}

	public void setRemoveTaxesId(String removeTaxesId) {
		this.removeTaxesId = removeTaxesId;
	}

	public List<LookUp> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<LookUp> taxList) {
		this.taxList = taxList;
	}

	public List<LookUp> getValueTypeList() {
		return valueTypeList;
	}

	public void setValueTypeList(List<LookUp> valueTypeList) {
		this.valueTypeList = valueTypeList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getParenetTaxId() {
		return parenetTaxId;
	}

	public void setParenetTaxId(Long parenetTaxId) {
		this.parenetTaxId = parenetTaxId;
	}


}
