package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountBillRegisterBean implements Serializable {

    private static final long serialVersionUID = 2531558680358823445L;

    private String billNo;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    private List<AccountBillEntryMasterBean> listOfBillRegister = new ArrayList<>();

    public List<AccountBillEntryMasterBean> getListOfBillRegister() {
        return listOfBillRegister;
    }

    public void setListOfBillRegister(List<AccountBillEntryMasterBean> listOfBillRegister) {
        this.listOfBillRegister = listOfBillRegister;
    }

}
