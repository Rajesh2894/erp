package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillGenErrorDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbMrdataService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.WaterDisconnectionService;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class FinalMeterReadingModel extends AbstractFormModel {

    private static final long serialVersionUID = 4143057066191035064L;

    @Autowired
    private WaterDisconnectionService waterDisconnectionService;

    @Autowired
    private TbMrdataService meterReadingService;

    @Autowired
    private TbWtBillMasService waterBillingService;

    @Resource
    private NewWaterConnectionService connectionService;

    private Long applicationId;
    private Long serviceId;
    private String levelValue;
    private Long levelId;
    private Long level;

    private Date applicationDate;
    private String applicanttName;
    private String serviceName;

    private Long finalMeterReading;
    private Date finalReadingDate;
    private boolean metered;

    private List<MeterReadingDTO> meterReadingList = new ArrayList<>();

    private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

    private TbKCsmrInfoMH connectionInfo;

    public void search() {

        final MeterReadingDTO meterReading = new MeterReadingDTO();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        connectionInfo = waterDisconnectionService.getAppDetailsAppliedForDisConnection(applicationId, orgId);
        final Long meteredtype = CommonMasterUtility
                .getValueFromPrefixLookUp(
                        MainetConstants.NewWaterServiceConstants.METER,
                        MainetConstants.NewWaterServiceConstants.WMN)
                .getLookUpId();
        if (meteredtype.equals(connectionInfo.getCsMeteredccn())) {
            setMetered(true);
        } else {
            setMetered(false);
        }
        meterReading.setOrgid(orgId);
        meterReading.setCsCcn(connectionInfo.getCsCcn());
        meterReading.setMeterType(MainetConstants.MENU.S);
        meterReadingList = meterReadingService.findWaterRecords(meterReading, UserSession.getCurrent().getFinYearId(), null, null,
                null);

    }

    @Override
    public boolean saveForm() {
        if (metered) {
            if (null == finalMeterReading) {
                addValidationError(getAppSession().getMessage("water.final.reading.validate"));
            }
            if (null == finalReadingDate) {
                addValidationError(getAppSession().getMessage("water.final.reading.date.validate"));
            }
        }
        if (hasValidationErrors()) {

            return false;
        }
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        if (metered) {
            meterReadingList.get(0).setMrdMtrread(finalMeterReading);
            meterReadingService.saveMeterReadingData(meterReadingList, finalReadingDate,
                    organisation,
                    null, null, UserSession.getCurrent().getEmployee().getEmpId(), null);
        }

        final TbCsmrInfoDTO requestDTO = new TbCsmrInfoDTO();
        requestDTO.setCsCcn(connectionInfo.getCsCcn());
        requestDTO.setOrgId(connectionInfo.getOrgId());
        final List<TbCsmrInfoDTO> connectionInfList = connectionService.getwaterRecordsForBill(requestDTO, MainetConstants.MENU.S,
                null, null);
        connectionInfList.get(0).setPcFlg(MainetConstants.MENU.Y);
        final List<Long> csIdn = new ArrayList<>(0);
        final Map<Long, WaterBillGenErrorDTO> errorListMap = new HashMap<>(0);
        for (final TbCsmrInfoDTO waterDTO : connectionInfList) {
            csIdn.add(waterDTO.getCsIdn());
        }
        Long loggedLocId = UserSession.getCurrent().getLoggedLocId();
        waterBillingService.billCalculationAndGeneration(
                organisation, errorListMap, connectionInfList, requestDTO.getCsRemark(),
                UserSession.getCurrent().getEmployee().getEmpId(),
                1, csIdn, UserSession.getCurrent().getEmployee().getEmppiservername(),loggedLocId);

        lableValueDTO.setApplicationId(applicationId);
        lableValueDTO.setLableId(levelId);
        lableValueDTO.setLableValue(levelValue);
        lableValueDTO.setLevel(level);

        lableValueDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
        lableValueDTO.setOrgId(organisation.getOrgid());
        lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        setSuccessMessage(getAppSession().getMessage("meter.save"));
        return true;
    }

    public ScrutinyLableValueDTO getLableValueDTO() {
        return lableValueDTO;
    }

    public void setLableValueDTO(final ScrutinyLableValueDTO lableValueDTO) {
        this.lableValueDTO = lableValueDTO;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicanttName() {
        return applicanttName;
    }

    public void setApplicanttName(final String applicanttName) {
        this.applicanttName = applicanttName;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(final String levelValue) {
        this.levelValue = levelValue;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(final Long levelId) {
        this.levelId = levelId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(final Long level) {
        this.level = level;
    }

    public List<MeterReadingDTO> getMeterReadingList() {
        return meterReadingList;
    }

    public void setMeterReadingList(final List<MeterReadingDTO> meterReadingList) {
        this.meterReadingList = meterReadingList;
    }

    public Long getFinalMeterReading() {
        return finalMeterReading;
    }

    public void setFinalMeterReading(final Long finalMeterReading) {
        this.finalMeterReading = finalMeterReading;
    }

    public Date getFinalReadingDate() {
        return finalReadingDate;
    }

    public void setFinalReadingDate(final Date finalReadingDate) {
        this.finalReadingDate = finalReadingDate;
    }

    public boolean isMetered() {
        return metered;
    }

    public void setMetered(final boolean metered) {
        this.metered = metered;
    }

}
