package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.MeterReadingMonthDTO;
import com.abm.mainet.water.dto.TbMeterMas;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.service.TbMrdataService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.ui.validator.MeterReadingSearchValidator;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class MeterReadingModel extends AbstractFormModel {

    private static final long serialVersionUID = 8058627262721324366L;

    @Autowired
    private TbMrdataService tbmeterService;

    @Autowired
    private TbWtBillMasService tbWtBillMasService;

    private MeterReadingDTO entityDTO = new MeterReadingDTO();

    private List<MeterReadingDTO> entityList = null;

    private TbMeterMas meterMas;

    private TbBillMas billMas;

    private MeterReadingDTO viewDTO = new MeterReadingDTO();

    private Date mrdDate;

    private String changeCycle;

    private List<TbWtBillSchedule> billSchedule = null;

    private List<MeterReadingMonthDTO> monthDto = new ArrayList<>(0);

    private String dependsOnType;

    @Override
    protected final String findPropertyPathPrefix(
            final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.TRF:
            return "entityDTO.tariff";

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return "entityDTO.wardZone";

        default:
            return null;

        }
    }

    /**
     * @return
     */
    public boolean getWaterRecords() {
        final UserSession session = UserSession.getCurrent();
        final List<MeterReadingDTO> dto = tbmeterService
                .findWaterRecords(getEntityDTO(),
                        session.getFinYearId(), getBillSchedule(), getDependsOnType(), getMonthDto());
        setEntityList(dto);
        if ((dto != null) && !dto.isEmpty()) {
            setMrdDate(null);
            if ((getEntityDTO().getMeterType() != null)
                    && getEntityDTO().getMeterType().equals(MainetConstants.MENU.S)) {
                setMonthDto(dto.get(0).getMonth());
                setMrdDate(dto.get(0).getMrdMrdate());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param csId
     * @param organisation
     * @return
     */
    public boolean getWaterRecordsForViewDetails(
            final Long meterMasId, final long organisation) {
        final List<Object> object = tbmeterService.setViewPreviousMeterDetails(meterMasId, organisation, getViewDTO());
        if ((object != null) && !object.isEmpty()) {
            if (object.get(0) != null) {
                setMeterMas((TbMeterMas) object.get(0));
            }
            if (object.get(1) != null) {
                setBillMas((TbBillMas) object.get(1));
                getBillMas().setBmRemarks(MainetConstants.TASK_STATUS_COMPLETED);
            } else {
                setBillMas(new TbBillMas());
                getBillMas().setBmRemarks(MainetConstants.TASK_STATUS_PENDING);
            }
        }
        return true;
    }

    @Override
    public boolean saveForm() {
        if (getMrdDate() == null) {
            addValidationError(ApplicationSession
                    .getInstance().getMessage(
                            "water.meterReading.readDate"));
        }
        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		&& !MainetConstants.MENU.Y.equals(getChangeCycle())) {
            addValidationError(getAppSession().getMessage("meter.cycle"));
        }
        if (hasValidationErrors()) {
            return false;
        }
        if (MainetConstants.MENU.S.equals(getEntityDTO().getMeterType())) {
            int to = 0;
            for (final MeterReadingMonthDTO dto : getMonthDto()) {
                if (MainetConstants.MENU.Y.equals(dto.getValueCheck())) {
                    to = dto.getTo();
                }
            }
            final boolean result = tbWtBillMasService.validateBillPresentOrNot(getEntityList().get(0).getMmMtnid(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), to);
            if (result) {
                addValidationError(getAppSession().getMessage("meter.nochange"));
            }
        }
        if (hasValidationErrors()) {
            return false;
        }
        final List<Long> csIdn = getEntityList()
                .parallelStream()
                .filter(waterMeter -> waterMeter.getMrdMtrread() != null)
                .map(MeterReadingDTO::getCsIdn)
                .collect(Collectors.toList());
        if ((csIdn == null) || csIdn.isEmpty()) {
            addValidationError(getAppSession().getMessage("meter.atlest.one"));
            return false;
        }
        tbmeterService.saveMeterReadingData(
                getEntityList(), getMrdDate(),
                UserSession.getCurrent().getOrganisation(), getMonthDto(), getChangeCycle(),
                UserSession.getCurrent().getEmployee().getEmpId(), csIdn);
        setSuccessMessage(getAppSession().getMessage(
                "water.meterReading.readSucess"));
        return true;
    }

    /**
     * @return
     */
    public boolean validateSearchCriteria() {
        boolean result = true;
        final MeterReadingDTO entity = getEntityDTO();
        validateBean(entity,
                MeterReadingSearchValidator.class);
        if ((getEntityDTO().getMeterType() != null)
                && !getEntityDTO().getMeterType().equals(MainetConstants.MENU.S)) {
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
            		MainetConstants.ONE.equals(getDependsOnType()) && !MainetConstants.MENU.Y.equals(getChangeCycle())) {
                addValidationError(getAppSession().getMessage("meter.cycle"));
            }
        }
        if (hasValidationErrors()) {
            result = false;
        }
        return result;
    }

    /**
     * @param index2
     * @param monthIds
     */
    public void assignBillCycle(
            final List<String> monthIds) {
        if (monthIds != null) {
            for (final String id : monthIds) {
                getMonthDto()
                        .get(Integer.valueOf(id.trim()))
                        .setValueCheck(MainetConstants.MENU.Y);
            }
        } else {
            if ((getMonthDto() != null) && !getMonthDto().isEmpty()) {
                for (final MeterReadingMonthDTO dto : getMonthDto()) {
                    dto.setValueCheck(null);
                }
            }
        }
    }

    public MeterReadingDTO getEntityDTO() {
        return entityDTO;
    }

    public void setEntityDTO(final MeterReadingDTO entityDTO) {
        this.entityDTO = entityDTO;
    }

    public List<MeterReadingDTO> getEntityList() {
        return entityList;
    }

    public void setEntityList(
            final List<MeterReadingDTO> entityList) {
        this.entityList = entityList;
    }

    public TbMeterMas getMeterMas() {
        return meterMas;
    }

    public void setMeterMas(final TbMeterMas meterMas) {
        this.meterMas = meterMas;
    }

    public TbBillMas getBillMas() {
        return billMas;
    }

    public void setBillMas(final TbBillMas billMas) {
        this.billMas = billMas;
    }

    public MeterReadingDTO getViewDTO() {
        return viewDTO;
    }

    public void setViewDTO(final MeterReadingDTO viewDTO) {
        this.viewDTO = viewDTO;
    }

    public Date getMrdDate() {
        return mrdDate;
    }

    public void setMrdDate(final Date mrdDate) {
        this.mrdDate = mrdDate;
    }

    public String getChangeCycle() {
        return changeCycle;
    }

    public void setChangeCycle(final String changeCycle) {
        this.changeCycle = changeCycle;
    }

    public List<MeterReadingMonthDTO> getMonthDto() {
        return monthDto;
    }

    public void setMonthDto(final List<MeterReadingMonthDTO> monthDto) {
        this.monthDto = monthDto;
    }

    public List<TbWtBillSchedule> getBillSchedule() {
        return billSchedule;
    }

    public void setBillSchedule(final List<TbWtBillSchedule> billSchedule) {
        this.billSchedule = billSchedule;
    }

    public String getDependsOnType() {
        return dependsOnType;
    }

    public void setDependsOnType(final String dependsOnType) {
        this.dependsOnType = dependsOnType;
    }

}
