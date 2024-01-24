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
public class IllegalConnectionRegisterdModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	
	private TbCsmrInfoDTO tbCsmrInfoDTO = new TbCsmrInfoDTO();

	private List<TbCsmrInfoDTO> tbCsmrInfoDTOList = new ArrayList<>();

	
	private List<FinYearDTO> financialYearList = new ArrayList<>();
	
	public TbCsmrInfoDTO getTbCsmrInfoDTO() {
		return tbCsmrInfoDTO;
	}

	public void setTbCsmrInfoDTO(TbCsmrInfoDTO tbCsmrInfoDTO) {
		this.tbCsmrInfoDTO = tbCsmrInfoDTO;
	}

	public List<TbCsmrInfoDTO> getTbCsmrInfoDTOList() {
		return tbCsmrInfoDTOList;
	}

	public void setTbCsmrInfoDTOList(List<TbCsmrInfoDTO> tbCsmrInfoDTOList) {
		this.tbCsmrInfoDTOList = tbCsmrInfoDTOList;
	}

	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}
	
	
	
}
