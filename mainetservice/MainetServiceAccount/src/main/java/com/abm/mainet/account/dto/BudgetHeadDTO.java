
package com.abm.mainet.account.dto;

import java.io.Serializable;

/**
 * this DTO should be used where ever Budget Code related data required it contains combination of below: budgetCode+primary
 * composite code + secondary head code+secondary head description Ex : I00068 - 1100101 - 0003 - Service fee
 * @author Vivek.Kumar
 * @since 21 JAN 2017
 */
public class BudgetHeadDTO implements Serializable {

    private static final long serialVersionUID = 8444179057110472865L;
    private long budgetCodeId;
    private String budgetCode;
    private String primaryHeadCompositCode;
    private String secondaryHeadCode;
    private String secondaryHeadDesc;

    /*
     * being used for drop down list in jsp it contains combination of below: budgetCode+primary composite code + secondary head
     * code+secondary head description Ex : I00068 - 1100101 - 0003 - Service fee
     */
    private String combinedBudgetCodeDesc;

    public long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(final String budgetCode) {
        this.budgetCode = budgetCode;
    }

    public String getPrimaryHeadCompositCode() {
        return primaryHeadCompositCode;
    }

    public void setPrimaryHeadCompositCode(final String primaryHeadCompositCode) {
        this.primaryHeadCompositCode = primaryHeadCompositCode;
    }

    public String getSecondaryHeadCode() {
        return secondaryHeadCode;
    }

    public void setSecondaryHeadCode(final String secondaryHeadCode) {
        this.secondaryHeadCode = secondaryHeadCode;
    }

    public String getSecondaryHeadDesc() {
        return secondaryHeadDesc;
    }

    public void setSecondaryHeadDesc(final String secondaryHeadDesc) {
        this.secondaryHeadDesc = secondaryHeadDesc;
    }

    public String getCombinedBudgetCodeDesc() {
        return combinedBudgetCodeDesc;
    }

    public void setCombinedBudgetCodeDesc(final String combinedBudgetCodeDesc) {
        this.combinedBudgetCodeDesc = combinedBudgetCodeDesc;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BudgetCodeDTO [budgetCodeId=");
        builder.append(budgetCodeId);
        builder.append(", budgetCode=");
        builder.append(budgetCode);
        builder.append(", primaryHeadCompositCode=");
        builder.append(primaryHeadCompositCode);
        builder.append(", secondaryHeadCode=");
        builder.append(secondaryHeadCode);
        builder.append(", secondaryHeadDesc=");
        builder.append(secondaryHeadDesc);
        builder.append(", combinedBudgetCodeDesc=");
        builder.append(combinedBudgetCodeDesc);
        builder.append("]");
        return builder.toString();
    }

}
