package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

@Component
@Scope("session")
public class OutstandingRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();

	private List<FinYearDTO> financialYrList = new ArrayList<>();

	private List<TbCsmrInfoDTO> csmrInfoList = new ArrayList<>();

	
	private Date maxFineDate;
	
	private Date minFinDate;
	
	
	/* private List<LookUp> wardZoneLookUplist = new ArrayList<LookUp>(); */

	public List<TbCsmrInfoDTO> getCsmrInfoList() {
		return csmrInfoList;
	}

	public void setCsmrInfoList(List<TbCsmrInfoDTO> csmrInfoList) {
		this.csmrInfoList = csmrInfoList;
	}

	public TbCsmrInfoDTO getCsmrInfoDTO() {

		return csmrInfoDTO;
	}

	public void setCsmrInfoDTO(TbCsmrInfoDTO csmrInfoDTO) {
		this.csmrInfoDTO = csmrInfoDTO;
	}

	public List<FinYearDTO> getFinancialYrList() {
		return financialYrList;
	}

	public void setFinancialYrList(List<FinYearDTO> financialYrList) {
		this.financialYrList = financialYrList;
	}

	public Date getMaxFineDate() {
		return maxFineDate;
	}

	public Date getMinFinDate() {
		return minFinDate;
	}

	public void setMaxFineDate(Date maxFineDate) {
		this.maxFineDate = maxFineDate;
	}

	public void setMinFinDate(Date minFinDate) {
		this.minFinDate = minFinDate;
	}

}
