/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harsha.Ramachandran
 *
 */
public class AccountBudgetCodeBeanList {

    private List<AccountBudgetCodeBean> accountBudgetCodeList = new ArrayList<>();

    /**
     * @return the accountBudgetCodeList
     */
    public List<AccountBudgetCodeBean> getAccountBudgetCodeList() {
        return accountBudgetCodeList;
    }

    /**
     * @param accountBudgetCodeList the accountBudgetCodeList to set
     */
    public void setAccountBudgetCodeList(
            final List<AccountBudgetCodeBean> accountBudgetCodeList) {
        this.accountBudgetCodeList = accountBudgetCodeList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountBudgetCodeBeanList [accountBudgetCodeList="
                + accountBudgetCodeList + "]";
    }

}
