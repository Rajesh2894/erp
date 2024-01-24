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
public class MeterCutOFFModel extends AbstractFormModel {

    private static final long serialVersionUID = 1426673231439790393L;

    @Autowired
    private MeterCutOffRestorationService meterCutOffRestorationService;

    private TbMeterMas meterMasPreviousDetailDTO = new TbMeterMas();
    private MeterCutOffRestorationDTO meterCutOffResDTO = new MeterCutOffRestorationDTO();
    private List<MeterCutOffRestorationDTO> cutOffRestorationDTO = new ArrayList<>(0);
    private String meteredFlag;
    private String hasError = MainetConstants.MENU.N;
    private String premiseType;
    private String tarrifCategory;
    private String connectionSize;
    private String consumerName;
    private String billingActiveFlag;

    @Override
    public boolean saveForm() {
        final boolean result = validateFormData(meterCutOffResDTO, meteredFlag);
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
        if (null != billingActiveFlag) {
            meterCutOffResDTO.setMeterBillingFlag(MainetConstants.MeterCutOffRestoration.BILLING_STATUS_ACTIVE);
        } else {
            meterCutOffResDTO.setMeterBillingFlag(MainetConstants.MeterCutOffRestoration.BILLING_STATUS_INACTIVE);
        }

        meterCutOffResDTO.setMeteredFlag(getMeteredFlag());
        meterCutOffRestorationService.saveCutOffRestorationDetails(meterCutOffResDTO, null, MainetConstants.BLANK,
                MainetConstants.MeterCutOffRestoration.METER_CUTOFF,
                MainetConstants.MeterCutOffRestoration.METER_STATUS_INACTIVE);
        return true;
    }

    private boolean validateFormData(final MeterCutOffRestorationDTO cutOffRestorationDTO, final String meteredFlag) {

        boolean status = false;

        if ((cutOffRestorationDTO.getConnectionNo() == null)
                || cutOffRestorationDTO.getConnectionNo().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.connectionNo"));
            status = true;
        }

        if ((cutOffRestorationDTO.getCutResRemark() == null)
                || cutOffRestorationDTO.getCutResRemark().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.reason"));
            status = true;
        }

        if ((cutOffRestorationDTO.getCutResDate() == null)
                || cutOffRestorationDTO.getCutResDate().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.date"));
            status = true;
        }

        if (MainetConstants.MENU.Y.equals(meteredFlag)) {

            if ((cutOffRestorationDTO.getMeterStatus() == null) || (cutOffRestorationDTO.getMeterStatus().longValue() == 0l)) {
                addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.meterStatus"));
                status = true;
            }

            if ((cutOffRestorationDTO.getCutResRead() == null) || (cutOffRestorationDTO.getCutResRead().longValue() == 0l)) {
                addValidationError(getAppSession().getMessage("water.meterCutOff.validationMSG.meterReading"));
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

    /**
     * @return the meteredFlag
     */
    public String getMeteredFlag() {
        return meteredFlag;
    }

    /**
     * @param meteredFlag the meteredFlag to set
     */
    public void setMeteredFlag(final String meteredFlag) {
        this.meteredFlag = meteredFlag;
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

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
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

    public String getBillingActiveFlag() {
        return billingActiveFlag;
    }

    public void setBillingActiveFlag(final String billingActiveFlag) {
        this.billingActiveFlag = billingActiveFlag;
    }

}
