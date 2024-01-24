package com.abm.mainet.common.master.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TrasactionReversalDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;

@Component
@Scope(value ="session")
public class CommonReversalEntryModel extends AbstractFormModel
{

	private static final long serialVersionUID = -2990265867932798981L;
	
	
	List<LookUp> transactionType =new ArrayList<>();
	
	LookUp dept = new LookUp();
	
	
	TrasactionReversalDTO trasactionReversalDTO =new  TrasactionReversalDTO();
	
	List<TbServiceReceiptMasBean> ReceiptMasList = new ArrayList<>();
	
	TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
	
	private List<TbDepartment> deparatmentList = new ArrayList<>();

	private Long depId;
	
	public TbServiceReceiptMasBean getTbServiceReceiptMasBean() {
		return tbServiceReceiptMasBean;
	}

	public void setTbServiceReceiptMasBean(TbServiceReceiptMasBean tbServiceReceiptMasBean) {
		this.tbServiceReceiptMasBean = tbServiceReceiptMasBean;
	}

	public List<TbServiceReceiptMasBean> getReceiptMasList() {
		return ReceiptMasList;
	}

	public void setReceiptMasList(List<TbServiceReceiptMasBean> receiptMasList) {
		ReceiptMasList = receiptMasList;
	}

	public TrasactionReversalDTO getTrasactionReversalDTO() {
		return trasactionReversalDTO;
	}

	public void setTrasactionReversalDTO(TrasactionReversalDTO trasactionReversalDTO) {
		this.trasactionReversalDTO = trasactionReversalDTO;
	}

	public LookUp getDept() {
		return dept;
	}

	public void setDept(LookUp dept) {
		this.dept = dept;
	}

	public List<LookUp> getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(List<LookUp> transactionType) {
		this.transactionType = transactionType;
	}


	protected void initializeModel() {
		super.initializeModel();
	}

	public List<TbDepartment> getDeparatmentList() {
		return deparatmentList;
	}

	public void setDeparatmentList(List<TbDepartment> deparatmentList) {
		this.deparatmentList = deparatmentList;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

}
