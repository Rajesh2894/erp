package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

@Component
@Scope("session")
public class ListOfClosingConnectionModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;

	private TbCsmrInfoDTO csmrInfoDTO = new TbCsmrInfoDTO();

	private List<TbCsmrInfoDTO> csmrInfoList = new ArrayList<>();

	private TBWaterDisconnectionDTO disconnectionDTO = new TBWaterDisconnectionDTO();

	private List<TBWaterDisconnectionDTO> diconnectionList = new ArrayList<>();

	private List<FinYearDTO> financialYearList = new ArrayList<>();

	private String reportType;

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

	public TBWaterDisconnectionDTO getDisconnectionDTO() {
		return disconnectionDTO;
	}

	public void setDisconnectionDTO(TBWaterDisconnectionDTO disconnectionDTO) {
		this.disconnectionDTO = disconnectionDTO;
	}

	public List<TBWaterDisconnectionDTO> getDiconnectionList() {
		return diconnectionList;
	}

	public void setDiconnectionList(List<TBWaterDisconnectionDTO> diconnectionList) {
		this.diconnectionList = diconnectionList;
	}

	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

}
