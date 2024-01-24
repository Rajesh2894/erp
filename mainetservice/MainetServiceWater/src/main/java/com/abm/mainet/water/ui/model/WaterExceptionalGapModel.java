package com.abm.mainet.water.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterExceptionGapDTO;
import com.abm.mainet.water.service.WaterExceptionalGapService;
import com.abm.mainet.water.ui.validator.WaterExceptionalGapValidator;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class WaterExceptionalGapModel extends AbstractFormModel {

    private static final long serialVersionUID = -4597079265460559002L;

    @Autowired
    private WaterExceptionalGapService waterExceptionalGapService;

    private TbCsmrInfoDTO waterDTO = null;

    private List<WaterExceptionGapDTO> gapDto = null;

    private WaterExceptionGapDTO editGap = null;

    private long billingFrequency;

    private String reason;

    private String addEdit;

    private Long meterType;

    private String meterTypeDesc;

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            return "waterDTO.trmGroup";

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "waterDTO.codDwzid";

        default:
            return null;

        }
    }

    /**
     * @return
     */
    public boolean searchWaterBillRecords() {
        setReason(null);
        List<WaterExceptionGapDTO> entityList = null;
        setGapDto(entityList);
        final Organisation orgn = UserSession.getCurrent().getOrganisation();
        if (MainetConstants.Transaction.Mode.ADD.equals(getAddEdit())) {
            validateBean(getWaterDTO(), WaterExceptionalGapValidator.class);
            if (getBillingFrequency() == 0d) {
                addValidationError(ApplicationSession.getInstance().getMessage("water.exception.billFreq"));
            }
        } else {
            validateEditSearch();
        }
        if (hasValidationErrors()) {
            return false;
        }
        if (MainetConstants.Transaction.Mode.ADD.equals(getAddEdit())) {
            getWaterDTO().setCsMeteredccn(getMeterType());
            getWaterDTO().setOrgId(orgn.getOrgid());
            entityList = waterExceptionalGapService.getWaterDataForExceptionGap(getWaterDTO(),
                    getBillingFrequency(), UserSession.getCurrent().getFinYearId(), getMeterTypeDesc());
        } else {
            getEditGap().setOrgId(orgn.getOrgid());
            entityList = waterExceptionalGapService.editExceptionGapEntry(getEditGap(), getMeterType());
        }
        setGapDto(entityList);
        if ((entityList == null) || entityList.isEmpty()) {
            addValidationError(getAppSession().getMessage("wwater.Norecord"));
        }
        return true;
    }

    private void validateEditSearch() {
        final WaterExceptionGapDTO edit = getEditGap();
        if (((edit.getCpdMtrstatus() == null) || (edit.getCpdMtrstatus() == 0d))
                && MainetConstants.NewWaterServiceConstants.METER.equals(getMeterTypeDesc())) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.exception.mrStatus"));
        }

        if (((edit.getCpdGap() == null) || (edit.getCpdGap() == 0d))
                && MainetConstants.NewWaterServiceConstants.METER.equals(getMeterTypeDesc())) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.exception.gapCode"));
        }

        if (((edit.getMgapFrom() == null) && (edit.getMgapTo() == null))
                && MainetConstants.NewWaterServiceConstants.NON_METER.equals(getMeterTypeDesc())) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.exception.date"));
        }
    }

    @Override
    public boolean saveForm() {

        Boolean flag = false;
        final ApplicationSession appSession = ApplicationSession.getInstance();
        if (MainetConstants.Transaction.Mode.ADD.equals(getAddEdit())) {
            for (final WaterExceptionGapDTO dto : getGapDto()) {
                if (MainetConstants.Common_Constant.YES.equals(dto.getMgapActive())) {
                    flag = true;
                    if (((dto.getCpdGap() == null) || (dto.getCpdGap() <= 0d) || (dto.getCpdMtrstatus() == null)
                            || (dto.getCpdMtrstatus() <= 0d))
                            && MainetConstants.NewWaterServiceConstants.METER.equals(getMeterTypeDesc())) {
                        addValidationError(appSession.getMessage("water.exception.mrStatusActive"));
                        break;
                    }
                    if ((dto.getMgapFrom() == null) || (dto.getMgapTo() == null)) {
                        addValidationError(appSession.getMessage("water.exception.dateActive"));
                        break;
                    } else {
                        if (!UtilityService.compareDate(dto.getMgapFrom(), dto.getMgapTo())) {
                            addValidationError(appSession.getMessage("date.valid"));
                            break;
                        }
                    }
                }
            }
        } else {
            flag = true;
            for (final WaterExceptionGapDTO dto : getGapDto()) {
                if (((dto.getCpdGap() == null) || (dto.getCpdGap() <= 0d) || (dto.getCpdMtrstatus() == null)
                        || (dto.getCpdMtrstatus() <= 0d))
                        && MainetConstants.NewWaterServiceConstants.METER.equals(getMeterTypeDesc())) {
                    addValidationError(appSession.getMessage("water.exception.mrStatusActive"));
                    break;
                }
                if ((dto.getMgapFrom() == null) || (dto.getMgapTo() == null)) {
                    addValidationError(appSession.getMessage("water.exception.dateActive"));
                    break;
                } else {
                    if (!UtilityService.compareDate(dto.getMgapFrom(), dto.getMgapTo())) {
                        addValidationError(appSession.getMessage("date.valid"));
                        break;
                    }
                }
            }
        }
        if (!flag) {
            addValidationError(appSession.getMessage("water.exception.atleastOne"));
        }
        if ((getReason() == null) || getReason().isEmpty()) {
            addValidationError(appSession.getMessage("water.exception.reason"));
        }
        if (hasValidationErrors()) {
            setCustomViewName("ExceptionalSearchAndSave");
            return false;
        }
        waterExceptionalGapService.saveAndUpdateExceptionalData(getGapDto(),
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee().getEmpId(), UserSession.getCurrent().getEmployee().getEmppiservername(),
                getReason(), getAddEdit());
        setSuccessMessage(appSession.getMessage("water.exception.successMsg"));
        return true;
    }

    public TbCsmrInfoDTO getWaterDTO() {
        return waterDTO;
    }

    public void setWaterDTO(final TbCsmrInfoDTO waterDTO) {
        this.waterDTO = waterDTO;
    }

    public long getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(final long billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public List<WaterExceptionGapDTO> getGapDto() {
        return gapDto;
    }

    public void setGapDto(final List<WaterExceptionGapDTO> gapDto) {
        this.gapDto = gapDto;
    }

    public WaterExceptionGapDTO getEditGap() {
        return editGap;
    }

    public void setEditGap(final WaterExceptionGapDTO editGap) {
        this.editGap = editGap;
    }

    public String getAddEdit() {
        return addEdit;
    }

    public void setAddEdit(final String addEdit) {
        this.addEdit = addEdit;
    }

    public Long getMeterType() {
        return meterType;
    }

    public void setMeterType(final Long meterType) {
        this.meterType = meterType;
    }

    public String getMeterTypeDesc() {
        return meterTypeDesc;
    }

    public void setMeterTypeDesc(final String meterTypeDesc) {
        this.meterTypeDesc = meterTypeDesc;
    }

}
