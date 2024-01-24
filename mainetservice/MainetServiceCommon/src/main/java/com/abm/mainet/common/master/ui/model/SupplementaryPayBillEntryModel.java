package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.SupplimentartPayBillEntryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class SupplementaryPayBillEntryModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private String saveMode;

	private List<SupplimentartPayBillEntryDTO> supplimentartPayBillDTOList = new ArrayList<>();

	@Override
	public boolean saveForm() {
		return false;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<SupplimentartPayBillEntryDTO> getSupplimentartPayBillDTOList() {
		return supplimentartPayBillDTOList;
	}

	public void setSupplimentartPayBillDTOList(List<SupplimentartPayBillEntryDTO> supplimentartPayBillDTOList) {
		this.supplimentartPayBillDTOList = supplimentartPayBillDTOList;
	}

}
