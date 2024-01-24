package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;
import com.abm.mainet.water.dto.TbMeterMas;
import com.abm.mainet.water.service.MeterCutOffRestorationService;

/**
 * @author Arun.Chavda
 *
 */
@Component
@Scope("session")
public class MeterRestorationModel extends AbstractFormModel {

    private static final long serialVersionUID = 6916041868854055078L;

    @Autowired
    private MeterCutOffRestorationService meterCutOffRestorationService;

    private TbMeterMas meterMasPreviousDetailDTO = new TbMeterMas();
    private TbMeterMas newMeterMasDTO = new TbMeterMas();
    private MeterCutOffRestorationDTO meterCutOffResDTO = new MeterCutOffRestorationDTO();
    private List<MeterCutOffRestorationDTO> cutOffRestorationDTO = new ArrayList<>(0);
    private String meteredFlag;
    private String hasError = MainetConstants.MENU.N;
    private String premiseType;
    private String tarrifCategory;
    private String connectionSize;
    private String consumerName;
    private String newMeterResFlag;
    private String newMeterValidation;

    @Override
    public boolean saveForm() {
        final boolean result = validateFormData(getMeterCutOffResDTO(), getNewMeterMasDTO(), meteredFlag, newMeterResFlag);
        if (result || hasValidationErrors()) {
            hasError = MainetConstants.MENU.Y;
            return false;
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        meterCutOffResDTO.setOrgId(orgId);
        meterCutOffResDTO.setLangId(UserSession.getCurrent().getLanguageId());
        meterCutOffResDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        meterCutOffResDTO.setLmodDate(new Date());
        meterCutOffResDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        meterCutOffResDTO.setMmMtnid(getMeterMasPreviousDetailDTO().getMmMtnid());
        meterCutOffResDTO.setMeteredFlag(getMeteredFlag());
        meterCutOffResDTO.setMeterBillingFlag(MainetConstants.MeterCutOffRestoration.BILLING_STATUS_ACTIVE);
        meterCutOffRestorationService.saveCutOffRestorationDetails(meterCutOffResDTO, newMeterMasDTO, newMeterResFlag,
                MainetConstants.MeterCutOffRestoration.METER_RESTORATION,
                MainetConstants.MeterCutOffRestoration.METER_STATUS_ACTIVE);
        return true;
    }

    private boolean validateFormData(final MeterCutOffRestorationDTO cutOffRestorationDTO, final TbMeterMas newMeterMasDTO,
            final String meteredFlag, final String newMeterResFlag) {
        boolean status = false;
        if ((cutOffRestorationDTO.getConnectionNo() == null)
                || cutOffRestorationDTO.getConnectionNo().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.connectionNo"));
            status = true;
        }

        if ((cutOffRestorationDTO.getCutResRemark() == null)
                || cutOffRestorationDTO.getCutResRemark().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.remark"));
            status = true;
        }

        if ((cutOffRestorationDTO.getCutResDate() == null)
                || cutOffRestorationDTO.getCutResDate().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.resDate"));
            status = true;
        }
        if (MainetConstants.MENU.Y.equals(meteredFlag)) {
            if ((cutOffRestorationDTO.getCutResRead() == null) || (cutOffRestorationDTO.getCutResRead().longValue() == 0l)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.resReading"));
                status = true;
            }
        }
        if (MainetConstants.MENU.Y.equals(newMeterValidation)) {
            if ((newMeterMasDTO.getMmOwnership() == null) || newMeterMasDTO.getMmOwnership().equals(MainetConstants.BLANK)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.meterOwnership"));
                status = true;
            }
            if ((newMeterMasDTO.getMmMtrno() == null) || newMeterMasDTO.getMmMtrno().equals(MainetConstants.BLANK)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.newMeterNo"));
                status = true;
            }

            if ((newMeterMasDTO.getMmMtrcost() == null) || (newMeterMasDTO.getMmMtrcost().longValue() == 0l)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.newMeterCost"));
                status = true;
            }
            if ((newMeterMasDTO.getMmMtrmake() == null) || newMeterMasDTO.getMmMtrmake().equals(MainetConstants.BLANK)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.newMeterMake"));
                status = true;
            }
            if ((newMeterMasDTO.getMaxMeterRead() == null) || (newMeterMasDTO.getMaxMeterRead().longValue() == 0l)) {
                addValidationError(getAppSession().getMessage("water.meterRes.validationMSG.maxReading"));
                status = true;
            }
        }
        return status;
    }

    public TbMeterMas getMeterMasPreviousDetailDTO() {
        return meterMasPreviousDetailDTO;
    }

    public void setMeterMasPreviousDetailDTO(final TbMeterMas meterMasPreviousDetailDTO) {
        this.meterMasPreviousDetailDTO = meterMasPreviousDetailDTO;
    }

    public MeterCutOffRestorationDTO getMeterCutOffResDTO() {
        return meterCutOffResDTO;
    }

    public void setMeterCutOffResDTO(final MeterCutOffRestorationDTO meterCutOffResDTO) {
        this.meterCutOffResDTO = meterCutOffResDTO;
    }

    public List<MeterCutOffRestorationDTO> getCutOffRestorationDTO() {
        return cutOffRestorationDTO;
    }

    public void setCutOffRestorationDTO(
            final List<MeterCutOffRestorationDTO> cutOffRestorationDTO) {
        this.cutOffRestorationDTO = cutOffRestorationDTO;
    }

    public String getMeteredFlag() {
        return meteredFlag;
    }

    public void setMeteredFlag(final String meteredFlag) {
        this.meteredFlag = meteredFlag;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getPremiseType() {
        return premiseType;
    }

    public void setPremiseType(final String premiseType) {
        this.premiseType = premiseType;
    }

    public String getTarrifCategory() {
        return tarrifCategory;
    }

    public void setTarrifCategory(final String tarrifCategory) {
        this.tarrifCategory = tarrifCategory;
    }

    public String getConnectionSize() {
        return connectionSize;
    }

    public void setConnectionSize(final String connectionSize) {
        this.connectionSize = connectionSize;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(final String consumerName) {
        this.consumerName = consumerName;
    }

    public String getNewMeterResFlag() {
        return newMeterResFlag;
    }

    public void setNewMeterResFlag(final String newMeterResFlag) {
        this.newMeterResFlag = newMeterResFlag;
    }

    /**
     * @return the newMeterValidation
     */
    public String getNewMeterValidation() {
        return newMeterValidation;
    }

    /**
     * @param newMeterValidation the newMeterValidation to set
     */
    public void setNewMeterValidation(final String newMeterValidation) {
        this.newMeterValidation = newMeterValidation;
    }

    /**
     * @return the newMeterMasDTO
     */
    public TbMeterMas getNewMeterMasDTO() {
        return newMeterMasDTO;
    }

    /**
     * @param newMeterMasDTO the newMeterMasDTO to set
     */
    public void setNewMeterMasDTO(final TbMeterMas newMeterMasDTO) {
        this.newMeterMasDTO = newMeterMasDTO;
    }

}
