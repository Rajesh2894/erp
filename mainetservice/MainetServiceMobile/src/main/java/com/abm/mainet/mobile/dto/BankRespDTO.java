package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.payment.dto.BankDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public class BankRespDTO extends CommonAppResponseDTO implements Serializable {

    private static final long serialVersionUID = -5770170209721789745L;
    private List<BankDTO> list;

    /**
     * @return the list
     */
    public List<BankDTO> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(final List<BankDTO> list) {
        this.list = list;
    }

}
