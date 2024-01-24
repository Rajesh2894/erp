package com.abm.mainet.account.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountBillRegisterBean;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class RTGSPaymentEntryModel extends AbstractFormModel {
    private static final long serialVersionUID = 1L;
    AccountBillRegisterBean AccountBillRegisterBean = new AccountBillRegisterBean();
    private List<TbAcVendormaster> vendorList = new ArrayList<>();
    private Long vendorId;

    @Override
    public boolean saveForm() {
        return super.saveForm();
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

}
