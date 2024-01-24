/*
 * Created on 10 May 2016 ( Time 19:15:26 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.ui.model;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.water.dto.MeterDetailsEntryDTO;
import com.abm.mainet.water.service.MeterDetailEntryService;

@Component
@Scope("session")
public class WaterMeterDetailsEntryModel extends AbstractFormModel {

    private static final String WATER_SAVED_MSG = "water.saved.msg";

    private static final String WATER_VALID_OWN_SHIP = "water.valid.ownShip";

    private static final String WATER_VALID_MET_INS_DT = "water.valid.metInsDt";

    private static final String WATER_VALID_MET_MAX_READ = "water.valid.metMaxRead";

    private static final String WATER_VALID_METER_READ = "water.valid.meterRead";

    private static final String WATER_VALID_METER_NUMBER = "water.valid.meterNumber";

    private static final long serialVersionUID = 1L;

    private MeterDetailsEntryDTO entity = new MeterDetailsEntryDTO();

    @Resource
    private MeterDetailEntryService tbCsmrInfoService;

    @Override
    public void editForm(final long rowId) {
        final MeterDetailsEntryDTO entity = tbCsmrInfoService.getApplicantDetailForMeterDet(rowId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        setEntity(entity);

    }

    @Override
    public boolean saveForm() {

        validateFields();
        if (getBindingResult().hasErrors()) {
            return false;
        }
        getEntity().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        getEntity().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getEntity().setLangId(UserSession.getCurrent().getLanguageId());
        final boolean result = tbCsmrInfoService.saveFormData(getEntity());
        setSuccessMessage(getAppSession().getMessage(WATER_SAVED_MSG));
        return result;
    }

    private void validateFields() {
        if ((getEntity().getMeterNumber() == null) || getEntity().getMeterNumber().isEmpty()) {
            addValidationError(getAppSession().getMessage(WATER_VALID_METER_NUMBER));
        }
        if (getEntity().getInitialMeterReading() == null) {
            addValidationError(getAppSession().getMessage(WATER_VALID_METER_READ));
        }
        if (getEntity().getMeterMaxReading() == null) {
            addValidationError(getAppSession().getMessage(WATER_VALID_MET_MAX_READ));
        }
        if (getEntity().getMeterInstallationDate() == null) {
            addValidationError(getAppSession().getMessage(WATER_VALID_MET_INS_DT));
        } else {
            final Calendar appDate = Calendar.getInstance();
            appDate.setTime(getEntity().getPcDate());
            appDate.set(Calendar.HOUR_OF_DAY, 0);
            appDate.set(Calendar.MINUTE, 0);
            appDate.set(Calendar.SECOND, 0);
            if (!UtilityService.compareDate(appDate.getTime(),
                    getEntity().getMeterInstallationDate())) {
                addValidationError(getAppSession().getMessage("meter.date"));
            } else if (!UtilityService.compareDate(getEntity().getMeterInstallationDate(),
                    new Date())) {
                addValidationError(getAppSession().getMessage("meter.install.date"));
            }

        }
        if (getEntity().getMeterOwnerShip() == null) {
            addValidationError(getAppSession().getMessage(WATER_VALID_OWN_SHIP));
        }
    }

    public MeterDetailsEntryDTO getEntity() {
        return entity;
    }

    public void setEntity(final MeterDetailsEntryDTO entity) {
        this.entity = entity;
    }

}
