package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.abm.mainet.common.dto.TbBillMas;

public class ExcelDownloadDTO implements Serializable {

    private static final long serialVersionUID = -5375385052479513071L;
    private Long orgId;
    private List<TbBillMas> billMasList = null;
    List<String> taxAmountList = new LinkedList<>();

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<TbBillMas> getBillMasList() {
        return billMasList;
    }

    public void setBillMasList(List<TbBillMas> billMasList) {
        this.billMasList = billMasList;
    }

    public List<String> getTaxAmountList() {
        return taxAmountList;
    }

    public void setTaxAmountList(List<String> taxAmountList) {
        this.taxAmountList = taxAmountList;
    }

}
