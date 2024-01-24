package com.abm.mainet.mrm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.mrm.dto.MarriageDTO;

@Component
@Scope("session")
public class MarriageDetailSummaryReportModel extends AbstractFormModel {

	private static final long serialVersionUID = -1389643737605530111L;

	private MarriageDTO marriageDTO = new MarriageDTO();
	private List<MarriageDTO> marriageDTOs = new ArrayList<MarriageDTO>();
	private String reportType;

	public MarriageDTO getMarriageDTO() {
		return marriageDTO;
	}

	public List<MarriageDTO> getMarriageDTOs() {
		return marriageDTOs;
	}

	public String getReportType() {
		return reportType;
	}

	public void setMarriageDTO(MarriageDTO marriageDTO) {
		this.marriageDTO = marriageDTO;
	}

	public void setMarriageDTOs(List<MarriageDTO> marriageDTOs) {
		this.marriageDTOs = marriageDTOs;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

}
