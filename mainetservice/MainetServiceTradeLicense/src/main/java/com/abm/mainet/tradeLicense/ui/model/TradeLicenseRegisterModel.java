package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@Component
@Scope("session")
public class TradeLicenseRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private TradeMasterDetailDTO tradeMaster = new TradeMasterDetailDTO();

	private List<TradeMasterDetailDTO> tradeMasterReport = new ArrayList<>();

	private String reportType;

	public TradeMasterDetailDTO getTradeMaster() {
		return tradeMaster;
	}

	public void setTradeMaster(TradeMasterDetailDTO tradeMaster) {
		this.tradeMaster = tradeMaster;
	}

	public List<TradeMasterDetailDTO> getTradeMasterReport() {
		return tradeMasterReport;
	}

	public void setTradeMasterReport(List<TradeMasterDetailDTO> tradeMasterReport) {
		this.tradeMasterReport = tradeMasterReport;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
