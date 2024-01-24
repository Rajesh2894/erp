package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.MRFMasterDto;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class DayMonthWiseDumpingModel extends AbstractFormModel {

    private static final long serialVersionUID = 3373555382330277014L;

    MRFMasterDto mRFMasterDto = new MRFMasterDto();
    MRFMasterDto mRFMaster;
    private List<MRFMasterDto> mRFMasterDtoList = new ArrayList<>();

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public List<MRFMasterDto> getmRFMasterDtoList() {
        return mRFMasterDtoList;
    }

    public void setmRFMasterDtoList(List<MRFMasterDto> mRFMasterDtoList) {
        this.mRFMasterDtoList = mRFMasterDtoList;
    }

    public MRFMasterDto getmRFMasterDto() {
        return mRFMasterDto;
    }

    public void setmRFMasterDto(MRFMasterDto mRFMasterDto) {
        this.mRFMasterDto = mRFMasterDto;
    }

    public MRFMasterDto getmRFMaster() {
        return mRFMaster;
    }

    public void setmRFMaster(MRFMasterDto mRFMaster) {
        this.mRFMaster = mRFMaster;
    }
}
