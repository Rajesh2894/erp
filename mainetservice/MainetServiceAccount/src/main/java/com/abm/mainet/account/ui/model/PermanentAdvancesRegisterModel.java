package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class PermanentAdvancesRegisterModel extends AbstractFormModel {
    private static final long serialVersionUID = 1L;
    private AdvanceEntryDTO advanceEntryDTO;

    public AdvanceEntryDTO getAdvanceEntryDTO() {
        return advanceEntryDTO;
    }

    public void setAdvanceEntryDTO(AdvanceEntryDTO advanceEntryDTO) {
        this.advanceEntryDTO = advanceEntryDTO;
    }

    @Override
    public boolean saveForm() {
        return super.saveForm();
    }

}
