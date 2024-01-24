package com.abm.mainet.account.ui.model;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountIncomeAndExpenditureDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class AssetsAndLiabilitiesScheduleReportModel extends AbstractFormModel {

    private static final long serialVersionUID = -6182438529371902927L;

    private AccountIncomeAndExpenditureDto accountIEDto = new AccountIncomeAndExpenditureDto();

    private Map<Long, String> listOfinalcialyear = null;

    private Map<Long, String> listOfprimaryHead = null;

    public Map<Long, String> getListOfinalcialyear() {
        return listOfinalcialyear;
    }

    public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
        this.listOfinalcialyear = listOfinalcialyear;
    }

    public Map<Long, String> getListOfprimaryHead() {
        return listOfprimaryHead;
    }

    public void setListOfprimaryHead(Map<Long, String> listOfprimaryHead) {
        this.listOfprimaryHead = listOfprimaryHead;
    }

    public AccountIncomeAndExpenditureDto getAccountIEDto() {
        return accountIEDto;
    }

    public void setAccountIEDto(AccountIncomeAndExpenditureDto accountIEDto) {
        this.accountIEDto = accountIEDto;
    }

}
