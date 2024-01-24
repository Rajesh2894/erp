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
public class WardZoneWiseConReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();

	private List<FinYearDTO> financialYrList = new ArrayList<>();

	private List<TbCsmrInfoDTO> csmrInfoList = new ArrayList<>();

	private Date maxFinDate;
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

	public Date getMaxFinDate() {
		return maxFinDate;
	}

	public Date getMinFinDate() {
		return minFinDate;
	}

	public void setMaxFinDate(Date maxFinDate) {
		this.maxFinDate = maxFinDate;
	}

	public void setMinFinDate(Date minFinDate) {
		this.minFinDate = minFinDate;
	}

	/*
	 * public List<LookUp> getWardZoneLookUplist() { return wardZoneLookUplist; }
	 * 
	 * public void setWardZoneLookUplist(List<LookUp> wardZoneLookUplist) {
	 * this.wardZoneLookUplist = wardZoneLookUplist; }
	 */

}
