package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.rnl.dto.ReportDTO;

@Component
@Scope("session")
public class ReportModel extends AbstractFormModel {

    private static final long serialVersionUID = -8313728297358185037L;
    private ReportDTO reportDTO;
    private Long estateId;
    private Long propId;
    private List<Object[]> estateMasters = Collections.emptyList();
    private List<ReportDTO> summaryReportDetails = new ArrayList<>();
    private Map<Long, String> financialYearMap = new HashMap<>(); 


    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public void setReportDTO(ReportDTO reportDTO) {
        this.reportDTO = reportDTO;
    }

    public List<ReportDTO> getSummaryReportDetails() {
        return summaryReportDetails;
    }

    public void setSummaryReportDetails(List<ReportDTO> summaryReportDetails) {
        this.summaryReportDetails = summaryReportDetails;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public void setFinancialYearMap(Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

	public Long getEstateId() {
		return estateId;
	}

	public void setEstateId(Long estateId) {
		this.estateId = estateId;
	}

	public List<Object[]> getEstateMasters() {
		return estateMasters;
	}

	public void setEstateMasters(List<Object[]> estateMasters) {
		this.estateMasters = estateMasters;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}
    
    

}
