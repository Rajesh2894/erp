package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.ui.validator.ScheduleOfRateValidator;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ScheduleOfRateModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    // Schedule of rate master DTO
    private ScheduleOfRateMstDto mstDto;

    // used to identify form mode whether it is CREATE, UPDATE or VIEW or Excel UPLOAD
    private String modeType;

    // Unit lookup List fetch by using 'WUT' prefix
    private List<LookUp> unitLookUpList = new ArrayList<>();

    // Unit lookup List fetch by using 'WKC' prefix
    private List<LookUp> sorCategoryList = new ArrayList<>();

    // used to store deleted SOR details id as comma separated Array
    private String removeChildIds;

    // give exported excel file name.
    private String excelFileName;

    // store list of error details while uploading SOR through excel
    private List<WmsErrorDetails> errDetails;

    // flag for successful create or update SOR records.
    private String successFlag;

    // flag for Sub Category Flag is active or not.
    private String subCatMode;

    // used to edit data by chapter type
    private Long chapterId;

    private String UADstatus;

    private Long sorId;

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Override
    public boolean saveForm() {
        ApplicationSession appSession = ApplicationSession.getInstance();
        boolean invalidDate = false;
        mstDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        validateBean(mstDto, ScheduleOfRateValidator.class);
        if (getModeType().equals(MainetConstants.MODE_CREATE))
            invalidDate = checkDateValidation(mstDto.getSorFromDate(), mstDto.getSorCpdId());
        if (invalidDate) {
            addValidationError(getAppSession().getMessage("sor.date.validation"));
        }
        if (invalidDate || hasValidationErrors()) {
            return false;
        }
        if (getModeType().equals(MainetConstants.MODE_CREATE) || getModeType().equals(MainetConstants.MODE_UPLOAD)) {
            mstDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            mstDto.setIgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            ScheduleOfRateMstDto dto = scheduleOfRateService.createSchedule(mstDto);
            setSorId(dto.getSorId());
            setSuccessMessage(appSession.getMessage("sor.create.success"));
        } else {
            List<Long> removeIds = null;
            String ids = getRemoveChildIds();
            if (ids != null && !ids.isEmpty()) {
                removeIds = new ArrayList<>();
                String array[] = ids.split(MainetConstants.operator.COMMA);
                for (String id : array) {
                    removeIds.add(Long.valueOf(id));
                }
            }
            mstDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            mstDto.setIgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            scheduleOfRateService.updateSchedule(mstDto, removeIds);
            setSuccessMessage(appSession.getMessage("sor.update.success"));
        }
        return true;
    }

    // validate SOR Start date for new created SOR
    public boolean checkDateValidation(Date sorFromDate, Long sorCpdId) {
        boolean status = false;
        ScheduleOfRateMstEntity entity = scheduleOfRateService.findExistingActiveSorType(sorCpdId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (entity != null) {
            if (entity.getSorFromDate().getTime() >= sorFromDate.getTime()) {
                status = true;
            }
        }

        return status;
    }

    public ScheduleOfRateMstDto getMstDto() {
        if (mstDto == null) {
            mstDto = new ScheduleOfRateMstDto();
        }
        return mstDto;
    }

    public void setMstDto(ScheduleOfRateMstDto mstDto) {
        this.mstDto = mstDto;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<LookUp> getUnitLookUpList() {
        return unitLookUpList;
    }

    public void setUnitLookUpList(List<LookUp> unitLookUpList) {
        this.unitLookUpList = unitLookUpList;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public List<LookUp> getSorCategoryList() {
        return sorCategoryList;
    }

    public void setSorCategoryList(List<LookUp> sorCategoryList) {
        this.sorCategoryList = sorCategoryList;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public List<WmsErrorDetails> getErrDetails() {
        return errDetails;
    }

    public void setErrDetails(List<WmsErrorDetails> errDetails) {
        this.errDetails = errDetails;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getSubCatMode() {
        return subCatMode;
    }

    public void setSubCatMode(String subCatMode) {
        this.subCatMode = subCatMode;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getUADstatus() {
        return UADstatus;
    }

    public void setUADstatus(String uADstatus) {
        UADstatus = uADstatus;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

}
