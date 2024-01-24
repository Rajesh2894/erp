package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountTransCntDayWiseEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "ONLINE_COUNT")
	private Long onlineCount;

	@Column(name = "CASH_COUNT")
	private Long cashCount;

	@Column(name = "BUDGET_AMT")
	private Double budgetAmount;

	@Column(name = "EXPENDITURE_AMT")
	private Double expenditureAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(Long onlineCount) {
		this.onlineCount = onlineCount;
	}

	public Long getCashCount() {
		return cashCount;
	}

	public void setCashCount(Long cashCount) {
		this.cashCount = cashCount;
	}

	public Double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public Double getExpenditureAmount() {
		return expenditureAmount;
	}

	public void setExpenditureAmount(Double expenditureAmount) {
		this.expenditureAmount = expenditureAmount;
	}

	@Override
	public String toString() {
		return "AccountTransCntDayWiseEntity [id=" + id + ", onlineCount=" + onlineCount + ", cashCount=" + cashCount
				+ ", budgetAmount=" + budgetAmount + ", expenditureAmount=" + expenditureAmount + "]";
	}

}
