package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountCollectionSummaryDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class AccountCollectionSummaryReportModel extends AbstractFormModel {

    private static final long serialVersionUID = -9126339890405680022L;
    private TbServiceReceiptMasEntity oTbServiceReceiptMasEntity = null;

    public TbServiceReceiptMasEntity getoTbServiceReceiptMasEntity() {
        return oTbServiceReceiptMasEntity;
    }

    public void setoTbServiceReceiptMasEntity(TbServiceReceiptMasEntity oTbServiceReceiptMasEntity) {
        this.oTbServiceReceiptMasEntity = oTbServiceReceiptMasEntity;
    }

    public AccountCollectionSummaryDTO getAccountCollectionSummaryDTO() {
        return accountCollectionSummaryDTO;
    }

    public void setAccountCollectionSummaryDTO(AccountCollectionSummaryDTO accountCollectionSummaryDTO) {
        this.accountCollectionSummaryDTO = accountCollectionSummaryDTO;
    }

    private AccountCollectionSummaryDTO accountCollectionSummaryDTO = null;

}
