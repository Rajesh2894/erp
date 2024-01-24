package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

@Component
@Scope("session")
public class ListOfNewWaterConnReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private TbCsmrInfoDTO tbCsmrInfoDTO = new TbCsmrInfoDTO();

	private List<TbCsmrInfoDTO> csmrInfoDtoList = new ArrayList<>();
	private List<FinYearDTO> financialYearList = new ArrayList<>();

	public TbCsmrInfoDTO getTbCsmrInfoDTO() {
		return tbCsmrInfoDTO;
	}

	public void setTbCsmrInfoDTO(TbCsmrInfoDTO tbCsmrInfoDTO) {
		this.tbCsmrInfoDTO = tbCsmrInfoDTO;
	}

	public List<TbCsmrInfoDTO> getCsmrInfoDtoList() {
		return csmrInfoDtoList;
	}

	public void setCsmrInfoDtoList(List<TbCsmrInfoDTO> csmrInfoDtoList) {
		this.csmrInfoDtoList = csmrInfoDtoList;
	}

	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	
}