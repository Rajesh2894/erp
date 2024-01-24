package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.List;

public class AccountVoucherUploadListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    List<AccountVoucherMasterUploadDTO> voucherdto = null;

    public List<AccountVoucherMasterUploadDTO> getVoucherdto() {
        return voucherdto;
    }

    public void setVoucherdto(List<AccountVoucherMasterUploadDTO> voucherdto) {
        this.voucherdto = voucherdto;
    }

}
