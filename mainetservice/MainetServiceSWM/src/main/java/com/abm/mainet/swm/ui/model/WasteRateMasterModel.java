package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.WasteRateMasterDTO;
import com.abm.mainet.swm.service.IWasteRateMasterService;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class WasteRateMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    IWasteRateMasterService wasteRateMasterService;

    private String saveMode;
    private WasteRateMasterDTO wasteRateMasterDto = new WasteRateMasterDTO();
    private List<WasteRateMasterDTO> wasteRateList = new ArrayList<>();
    private static boolean status;
    private Long prefixLevel;

    List<LookUp> wasteTypeList = new ArrayList<>();

    @Override
    public boolean saveForm() {
        status = false;

        if (getSaveMode().equals(MainetConstants.Legal.SaveMode.EDIT)) {
            wasteRateMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            wasteRateMasterDto.setUpdatedDate(new Date());
            wasteRateMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            wasteRateMasterService.update(wasteRateMasterDto);
            this.setSuccessMessage(getAppSession().getMessage("swm.wasteRate.edit"));
            status = true;
            /*
             * status = wasteRateMasterService.validateEntryWasteType(wasteRateMasterDto); if (status) {
             * wasteRateMasterService.update(wasteRateMasterDto); } else {
             * addValidationError(ApplicationSession.getInstance().getMessage("Item Already Present")); status = false; }
             */

        } else {
            wasteRateMasterDto.getWasteRateList().forEach(wstList -> {
                // boolean status = false;
                if (wstList.getwRateId() == null) {
                    wstList.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    wstList.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    wstList.setCreatedDate(new Date());
                    wstList.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    wstList.setwFromDate(wasteRateMasterDto.getwFromDate());
                    wstList.setwToDate(wasteRateMasterDto.getwToDate());
                    status = wasteRateMasterService.validateEntryWasteType(wstList);
                    if (status) {
                        wasteRateMasterService.save(wstList);
                        this.setSuccessMessage(getAppSession().getMessage("swm.wasteRate.save"));
                        status = true;
                    } else {
                        addValidationError(ApplicationSession.getInstance().getMessage("swm.wasteRate.validation"));
                        status = false;
                    }
                }
            });
        }
        return status;

    }

    public List<WasteRateMasterDTO> getWasteRateList() {
        return wasteRateList;
    }

    public void setWasteRateList(List<WasteRateMasterDTO> wasteRateList) {
        this.wasteRateList = wasteRateList;
    }

    public WasteRateMasterDTO getWasteRateMasterDto() {
        return wasteRateMasterDto;
    }

    public void setWasteRateMasterDto(WasteRateMasterDTO wasteRateMasterDto) {
        this.wasteRateMasterDto = wasteRateMasterDto;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<LookUp> getWasteTypeList() {
        return wasteTypeList;
    }

    public void setWasteTypeList(List<LookUp> wasteTypeList) {
        this.wasteTypeList = wasteTypeList;
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        WasteRateMasterModel.status = status;
    }

    public Long getPrefixLevel() {
        return prefixLevel;
    }

    public void setPrefixLevel(Long prefixLevel) {
        this.prefixLevel = prefixLevel;
    }

}
