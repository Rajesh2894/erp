package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.ui.validator.WmsMaterialMasterValidator;

@Component
@Scope("session")
public class WmsMaterialMasterModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -4863929718785580345L;

    private WmsMaterialMasterDto materialMasterDto;

    private List<WmsMaterialMasterDto> materialMasterListDto;

    private List<ScheduleOfRateMstDto> ScheduleOfRateListDto;

    private List<ScheduleOfRateMstDto> activeScheduleRateList;

    private ScheduleOfRateMstDto scheduleOfRateMstDto = new ScheduleOfRateMstDto();

    private List<WmsErrorDetails> errDetails;

    private Long sorType;
    private String sorName;
    private String fromDate;
    private String toDate;

    private String removeChildIds;
    private String modeType;
    private String excelFilePath;

    public List<WmsErrorDetails> getErrDetails() {
        return errDetails;
    }

    public void setErrDetails(List<WmsErrorDetails> errDetails) {
        this.errDetails = errDetails;
    }

    public WmsMaterialMasterDto getMaterialMasterDto() {
        return materialMasterDto;
    }

    public void setMaterialMasterDto(WmsMaterialMasterDto materialMasterDto) {
        this.materialMasterDto = materialMasterDto;
    }

    public ScheduleOfRateMstDto getScheduleOfRateMstDto() {
        return scheduleOfRateMstDto;
    }

    public void setScheduleOfRateMstDto(ScheduleOfRateMstDto scheduleOfRateMstDto) {
        this.scheduleOfRateMstDto = scheduleOfRateMstDto;
    }

    public List<WmsMaterialMasterDto> getMaterialMasterListDto() {
        return materialMasterListDto;
    }

    public void setMaterialMasterListDto(List<WmsMaterialMasterDto> materialMasterListDto) {
        this.materialMasterListDto = materialMasterListDto;
    }

    @Override
    public boolean saveForm() {
        Date todayDate = new Date();
        boolean status = true;

        for (WmsMaterialMasterDto wmsMaterialMasterDto : materialMasterListDto) {

            validateBean(wmsMaterialMasterDto, WmsMaterialMasterValidator.class);
            if (hasValidationErrors()) {
                return false;
            }
            wmsMaterialMasterDto.setSorId(getScheduleOfRateMstDto().getSorId());

            wmsMaterialMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            if (wmsMaterialMasterDto.getCreatedBy() == null) {
                wmsMaterialMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                wmsMaterialMasterDto.setCreatedDate(todayDate);
                wmsMaterialMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                wmsMaterialMasterDto.setMaActive(MainetConstants.IsDeleted.DELETE);

            } else {
                wmsMaterialMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                wmsMaterialMasterDto.setUpdatedDate(todayDate);
                wmsMaterialMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }

        List<Long> removeIds = null;
        String ids = getRemoveChildIds();
        if (ids != null && !ids.isEmpty()) {
            removeIds = new ArrayList<>();
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }

        if (status) {
            ApplicationContextProvider.getApplicationContext().getBean(WmsMaterialMasterService.class)
                    .saveUpdateMaterialList(getMaterialMasterListDto(), removeIds);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("material.master.saveList"));
        }
        return status;
    }

    public List<ScheduleOfRateMstDto> getScheduleOfRateListDto() {
        return ScheduleOfRateListDto;
    }

    public void setScheduleOfRateListDto(List<ScheduleOfRateMstDto> scheduleOfRateListDto) {
        ScheduleOfRateListDto = scheduleOfRateListDto;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
    }

    public Long getSorType() {
        return sorType;
    }

    public void setSorType(Long sorType) {
        this.sorType = sorType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<ScheduleOfRateMstDto> getActiveScheduleRateList() {
        return activeScheduleRateList;
    }

    public void setActiveScheduleRateList(List<ScheduleOfRateMstDto> activeScheduleRateList) {
        this.activeScheduleRateList = activeScheduleRateList;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }

    public void setExcelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

}
