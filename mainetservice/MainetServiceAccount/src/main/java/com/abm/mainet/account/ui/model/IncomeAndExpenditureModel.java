package com.abm.mainet.account.ui.model;

import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.account.dto.AccountIncomeAndExpenditureDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class IncomeAndExpenditureModel extends AbstractFormModel {
    private static final long serialVersionUID = 1L;
    private AccountIncomeAndExpenditureDto accountIEDto = new AccountIncomeAndExpenditureDto();

    private Map<Long, String> listOfinalcialyear = null;

    private Map<Long, String> listOfprimaryHead = null;
    
    private String scheduleNo;

    public Map<Long, String> getListOfinalcialyear() {
        return listOfinalcialyear;
    }

    public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
        this.listOfinalcialyear = listOfinalcialyear;
    }

    public AccountIncomeAndExpenditureDto getAccountIEDto() {
        return accountIEDto;
    }

    public void setAccountIEDto(AccountIncomeAndExpenditureDto accountIEDto) {
        this.accountIEDto = accountIEDto;
    }

    public Map<Long, String> getListOfprimaryHead() {
        return listOfprimaryHead;
    }

    public void setListOfprimaryHead(Map<Long, String> listOfprimaryHead) {
        this.listOfprimaryHead = listOfprimaryHead;
    }

    public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	@Override
    public boolean saveForm() {
        return super.saveForm();
    }

}
