
package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.WorksDepositRegisterReportDto;

@Component
@Scope("session")
public class WorkDepositRegisterReportModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<WorksDepositRegisterReportDto> worksDepositRegisterReportDto;
	private String fromDate;
	private String toDate;
	private BigDecimal totalValue;
	
	
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public List<WorksDepositRegisterReportDto> getWorksDepositRegisterReportDto() {
		return worksDepositRegisterReportDto;
	}
	public void setWorksDepositRegisterReportDto(List<WorksDepositRegisterReportDto> worksDepositRegisterReportDto) {
		this.worksDepositRegisterReportDto = worksDepositRegisterReportDto;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}