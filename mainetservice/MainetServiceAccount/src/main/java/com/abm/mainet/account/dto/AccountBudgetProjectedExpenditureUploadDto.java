package com.abm.mainet.account.dto;

import java.util.Date;

import javax.validation.constraints.Size;

public class AccountBudgetProjectedExpenditureUploadDto {

    private String budgetYear;
    private String department;
    private String budgetHead;
    private String budgetSubType;
    private String originalBudget;
    private String field;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    @Size(max = 100)
    private String lgIpMac;

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBudgetHead() {
        return budgetHead;
    }

    public void setBudgetHead(String budgetHead) {
        this.budgetHead = budgetHead;
    }

    public String getOriginalBudget() {
        return originalBudget;
    }

    public void setOriginalBudget(String originalBudget) {
        this.originalBudget = originalBudget;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getBudgetSubType() {
        return budgetSubType;
    }

    public void setBudgetSubType(String budgetSubType) {
        this.budgetSubType = budgetSubType;
    }

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((budgetHead == null) ? 0 : budgetHead.hashCode());
        result = prime * result + ((budgetSubType == null) ? 0 : budgetSubType.hashCode());
        result = prime * result + ((budgetYear == null) ? 0 : budgetYear.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((originalBudget == null) ? 0 : originalBudget.hashCode());
        result = prime * result + ((field == null) ? 0 : field.hashCode());
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
        AccountBudgetProjectedExpenditureUploadDto other = (AccountBudgetProjectedExpenditureUploadDto) obj;
        if (budgetHead == null) {
            if (other.budgetHead != null)
                return false;
        } else if (!budgetHead.equals(other.budgetHead))
            return false;
        if (budgetSubType == null) {
            if (other.budgetSubType != null)
                return false;
        } else if (!budgetSubType.equals(other.budgetSubType))
            return false;
        if (budgetYear == null) {
            if (other.budgetYear != null)
                return false;
        } else if (!budgetYear.equals(other.budgetYear))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (originalBudget == null) {
            if (other.originalBudget != null)
                return false;
        } else if (!originalBudget.equals(other.originalBudget))
            return false;
        if(field==null) {
        	if(other.field!=null)
        		return false;
        }else if(!field.equals(other.field)){
        	return false;
        }
        return true;
    }

}
