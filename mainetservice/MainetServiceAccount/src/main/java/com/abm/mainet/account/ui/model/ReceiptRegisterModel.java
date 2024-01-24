package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.ReceiptRegisterdto;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class ReceiptRegisterModel extends AbstractFormModel {

    private static final long serialVersionUID = 8434940700617770549L;
    private ReceiptRegisterdto oReceiptRegisterdto = null;
    private TbServiceReceiptMasEntity oTbServiceReceiptMasEntity = null;

    public ReceiptRegisterdto getoReceiptRegisterdto() {
        return oReceiptRegisterdto;
    }

    public void setoReceiptRegisterdto(final ReceiptRegisterdto oReceiptRegisterdto) {
        this.oReceiptRegisterdto = oReceiptRegisterdto;
    }

    public TbServiceReceiptMasEntity getoTbServiceReceiptMasEntity() {
        return oTbServiceReceiptMasEntity;
    }

    public void setoTbServiceReceiptMasEntity(final TbServiceReceiptMasEntity oTbServiceReceiptMasEntity) {
        this.oTbServiceReceiptMasEntity = oTbServiceReceiptMasEntity;
    }

    public void setEntity(final TbServiceReceiptMasEntity recieptList) {

    }

}
