package com.abm.mainet.workManagement.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ViewWorkDefinationYearDetDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Long workId;
   
    private String faYear;

    private BigDecimal yearPercntWork;

    private String yeDocRefNo;

    private BigDecimal yeBugAmount;

    private String yeActive;


    private String financeCodeDesc;

    private String finActiveFlag;

    private String faYearFromTo;

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getFaYear() {
		return faYear;
	}

	public void setFaYear(String faYear) {
		this.faYear = faYear;
	}

	public BigDecimal getYearPercntWork() {
        return yearPercntWork;
    }

    public void setYearPercntWork(BigDecimal yearPercntWork) {
        this.yearPercntWork = yearPercntWork;
    }

    public String getYeDocRefNo() {
        return yeDocRefNo;
    }

    public void setYeDocRefNo(String yeDocRefNo) {
        this.yeDocRefNo = yeDocRefNo;
    }

    public BigDecimal getYeBugAmount() {
        return yeBugAmount;
    }

    public void setYeBugAmount(BigDecimal yeBugAmount) {
        this.yeBugAmount = yeBugAmount;
    }

    public String getYeActive() {
        return yeActive;
    }

    public void setYeActive(String yeActive) {
        this.yeActive = yeActive;
    }

    public String getFinanceCodeDesc() {
        return financeCodeDesc;
    }

    public void setFinanceCodeDesc(String financeCodeDesc) {
        this.financeCodeDesc = financeCodeDesc;
    }

    public String getFinActiveFlag() {
        return finActiveFlag;
    }

    public void setFinActiveFlag(String finActiveFlag) {
        this.finActiveFlag = finActiveFlag;
    }

    public String getFaYearFromTo() {
        return faYearFromTo;
    }

    public void setFaYearFromTo(String faYearFromTo) {
        this.faYearFromTo = faYearFromTo;
    }

}
