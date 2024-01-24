package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.ui.validator.ReceiptFormValidator;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class ReceiptFormModel extends AbstractFormModel {
	private static final long serialVersionUID = -9168437509402366083L;

	private List<TbDepartment> departmentList;
	private List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<>();
	
	TbServiceReceiptMasBean receiptMasBean = new TbServiceReceiptMasBean();
	
	private int langId;

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public List<TbServiceReceiptMasBean> getReceiptMasBeanList() {
		return receiptMasBeanList;
	}

	public void setReceiptMasBeanList(List<TbServiceReceiptMasBean> receiptMasBeanList) {
		this.receiptMasBeanList = receiptMasBeanList;
	}

	public TbServiceReceiptMasBean getReceiptMasBean() {
		return receiptMasBean;
	}

	public void setReceiptMasBean(TbServiceReceiptMasBean receiptMasBean) {
		this.receiptMasBean = receiptMasBean;
	}


	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public boolean validateInputs() {
		validateBean(receiptMasBean, ReceiptFormValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
}
