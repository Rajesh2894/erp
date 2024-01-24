package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountIncomeAndExpenditureDto implements Serializable {
    private static final long serialVersionUID = 4255494180253605720L;

    private Long financialYrId;

    private String primaryAcHeadId;

    private String primaryAcHeadDesc;

    private String currentYearAmount;

    private String previousYearAmount;

    private String totalCurrentAmount;

    private String totalPreviousAmount;

    private String sumofPreviousYearAmount;

    private String sumofcurrYearAmount;

    private String repotType;

    private String openingBalannce;

    private String txdramount;

    private String txcramount;

    private String financialYr;
    private String scheduleNo;

    public String getRepotType() {
        return repotType;
    }

    public void setRepotType(String repotType) {
        this.repotType = repotType;
    }

    private List<AccountIncomeAndExpenditureDto> listOfIE = new ArrayList<>();

    public String getPrimaryAcHeadId() {
        return primaryAcHeadId;
    }

    public void setPrimaryAcHeadId(String primaryAcHeadId) {
        this.primaryAcHeadId = primaryAcHeadId;
    }

    public String getPrimaryAcHeadDesc() {
        return primaryAcHeadDesc;
    }

    public void setPrimaryAcHeadDesc(String primaryAcHeadDesc) {
        this.primaryAcHeadDesc = primaryAcHeadDesc;
    }

    public String getCurrentYearAmount() {
        return currentYearAmount;
    }

    public void setCurrentYearAmount(String currentYearAmount) {
        this.currentYearAmount = currentYearAmount;
    }

    public String getPreviousYearAmount() {
        return previousYearAmount;
    }

    public void setPreviousYearAmount(String previousYearAmount) {
        this.previousYearAmount = previousYearAmount;
    }

    public String getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public void setTotalCurrentAmount(String totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
    }

    public String getTotalPreviousAmount() {
        return totalPreviousAmount;
    }

    public void setTotalPreviousAmount(String totalPreviousAmount) {
        this.totalPreviousAmount = totalPreviousAmount;
    }

    public String getSumofPreviousYearAmount() {
        return sumofPreviousYearAmount;
    }

    public void setSumofPreviousYearAmount(String sumofPreviousYearAmount) {
        this.sumofPreviousYearAmount = sumofPreviousYearAmount;
    }

    public String getSumofcurrYearAmount() {
        return sumofcurrYearAmount;
    }

    public void setSumofcurrYearAmount(String sumofcurrYearAmount) {
        this.sumofcurrYearAmount = sumofcurrYearAmount;
    }

    public List<AccountIncomeAndExpenditureDto> getListOfIE() {
        return listOfIE;
    }

    public void setListOfIE(List<AccountIncomeAndExpenditureDto> listOfIE) {
        this.listOfIE = listOfIE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((primaryAcHeadId == null) ? 0 : primaryAcHeadId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountIncomeAndExpenditureDto other = (AccountIncomeAndExpenditureDto) obj;
        if (primaryAcHeadId == null) {
            if (other.primaryAcHeadId != null)
                return false;
        } else if (!primaryAcHeadId.equals(other.primaryAcHeadId))
            return false;
        return true;
    }

    public Long getFinancialYrId() {
        return financialYrId;
    }

    public void setFinancialYrId(Long financialYrId) {
        this.financialYrId = financialYrId;
    }

    public String getOpeningBalannce() {
        return openingBalannce;
    }

    public void setOpeningBalannce(String openingBalannce) {
        this.openingBalannce = openingBalannce;
    }

    public String getTxdramount() {
        return txdramount;
    }

    public void setTxdramount(String txdramount) {
        this.txdramount = txdramount;
    }

    public String getTxcramount() {
        return txcramount;
    }

    public void setTxcramount(String txcramount) {
        this.txcramount = txcramount;
    }

    public String getFinancialYr() {
        return financialYr;
    }

    public void setFinancialYr(String financialYr) {
        this.financialYr = financialYr;
    }

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

}
