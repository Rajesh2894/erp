package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsErrorDetails;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WmsLeadLiftMasterService;

@Component
@Scope("session")
public class WmsLeadLiftMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private WmsLeadLiftMasterDto wmsLeadLiftMasterDto = new WmsLeadLiftMasterDto();
    private List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos = new ArrayList<>();
    private List<LookUp> lookUpList = new ArrayList<LookUp>();
    private List<WmsLeadLiftMasterDto> wmsleadLiftTableDtos = new ArrayList<>();
    public List<ScheduleOfRateMstDto> scheduleOfRateMstDtoList = new ArrayList<>();
    private List<WmsErrorDetails> errDetails;
    private List<WmsLeadLiftMasterDto> errorDtos = new ArrayList<>();;

    private String excelFileName;
    private String rateType;
    private String formMode;
    private String fromDate;
    private String toDate;
    private String removeChildIds;
    private boolean activeChkBox;

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public WmsLeadLiftMasterDto getWmsLeadLiftMasterDto() {
        return wmsLeadLiftMasterDto;
    }

    public void setWmsLeadLiftMasterDto(WmsLeadLiftMasterDto wmsLeadLiftMasterDto) {
        this.wmsLeadLiftMasterDto = wmsLeadLiftMasterDto;
    }

    public List<WmsLeadLiftMasterDto> getWmsLeadLiftMasterDtos() {
        return wmsLeadLiftMasterDtos;
    }

    public void setWmsLeadLiftMasterDtos(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDtos) {
        this.wmsLeadLiftMasterDtos = wmsLeadLiftMasterDtos;
    }

    public List<LookUp> getLookUpList() {
        return lookUpList;
    }

    public void setLookUpList(List<LookUp> lookUpList) {
        this.lookUpList = lookUpList;
    }

    public String getFormMode() {
        return formMode;
    }

    public List<WmsLeadLiftMasterDto> getWmsleadLiftTableDtos() {
        return wmsleadLiftTableDtos;
    }

    public void setWmsleadLiftTableDtos(List<WmsLeadLiftMasterDto> wmsleadLiftTableDtos) {
        this.wmsleadLiftTableDtos = wmsleadLiftTableDtos;
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

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public boolean isActiveChkBox() {
        return activeChkBox;
    }

    public void setActiveChkBox(boolean activeChkBox) {
        this.activeChkBox = activeChkBox;
    }

    public List<ScheduleOfRateMstDto> getScheduleOfRateMstDtoList() {
        return scheduleOfRateMstDtoList;
    }

    public void setScheduleOfRateMstDtoList(List<ScheduleOfRateMstDto> scheduleOfRateMstDtoList) {
        this.scheduleOfRateMstDtoList = scheduleOfRateMstDtoList;
    }

    public List<WmsErrorDetails> getErrDetails() {
        return errDetails;
    }

    public void setErrDetails(List<WmsErrorDetails> errDetails) {
        this.errDetails = errDetails;
    }

    public List<WmsLeadLiftMasterDto> getErrorDtos() {
        return errorDtos;
    }

    public void setErrorDtos(List<WmsLeadLiftMasterDto> errorDtos) {
        this.errorDtos = errorDtos;
    }

    @Override
    public boolean saveForm() {

        if (activeChkBox) {
            this.getWmsLeadLiftMasterDto().setLeLiSlabFlg(MainetConstants.Y_FLAG);
        } else {
            this.getWmsLeadLiftMasterDto().setLeLiSlabFlg(MainetConstants.N_FLAG);
        }
        String isSlab = this.getWmsLeadLiftMasterDto().getLeLiSlabFlg();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Date todayDate = new Date();

        for (WmsLeadLiftMasterDto dto : wmsleadLiftTableDtos) {
            if (dto.getSorId() == null) {
                dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                dto.setCreatedBy(empId);
                dto.setCreatedDate(todayDate);
                dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                dto.setSorId(this.getWmsLeadLiftMasterDto().getSorId());
                dto.setLeLiFlag(this.getWmsLeadLiftMasterDto().getLeLiFlag());
                dto.setLeLiSlabFlg(isSlab);

            } else {
                dto.setLeLiSlabFlg(isSlab);
                dto.setUpdatedBy(empId);
                dto.setUpdatedDate(todayDate);
                dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }

        }
        if (!validateData(wmsleadLiftTableDtos, isSlab)) {
            return false;
        }

        if (formMode.equals(MainetConstants.MODE_CREATE) || formMode.equals(MainetConstants.MODE_UPLOAD)) {
            ApplicationContextProvider.getApplicationContext().getBean(WmsLeadLiftMasterService.class)
                    .addLeadLiftMasterEntry(this.getWmsleadLiftTableDtos());
            setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.save"));
        } else {
            String ids = this.getRemoveChildIds();
            ApplicationContextProvider.getApplicationContext().getBean(WmsLeadLiftMasterService.class)
                    .saveAndUpdateMaster(this.getWmsleadLiftTableDtos(), empId, ids);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.update"));
        }

        return true;
    }

    public void prepareDto(Long sorId, String mode, String flag) {

        ScheduleOfRateMstDto mstDto = ApplicationContextProvider.getApplicationContext().getBean(ScheduleOfRateService.class)
                .findSORMasterWithDetailsBySorId(sorId);
        this.setFormMode(mode);
        this.getWmsLeadLiftMasterDto().setSorId(sorId);
        this.getWmsLeadLiftMasterDto().setLeLiFlag(flag);
        this.getWmsLeadLiftMasterDto().setSorFromDate(mstDto.getSorFromDate());
        this.getWmsLeadLiftMasterDto().setSorToDate(mstDto.getSorToDate());
        this.getWmsLeadLiftMasterDto().setSorName(
                CommonMasterUtility.getNonHierarchicalLookUpObject(mstDto.getSorCpdId()).getLookUpDesc());
    }

    public boolean validateData(List<WmsLeadLiftMasterDto> wmsDtos, String slab) {
        String isSlab = slab;
        boolean isCheck = true;
        if (isSlab.equals(MainetConstants.Y_FLAG)) {
            for (int i = 0; i <= wmsDtos.size() - 1; i++) {
                int rowCount = i + 1;
                if (wmsDtos.get(i).getLeLiFrom().compareTo(new BigDecimal(0)) <= 0
                        || wmsDtos.get(i).getLeLiTo().compareTo(new BigDecimal(0)) <= 0
                        || wmsDtos.get(i).getLeLiRate().compareTo(new BigDecimal(0)) <= 0) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("leadlift.master.select.correctcharges")
                                    + rowCount);
                    wmsDtos.get(i).setErrMessage(
                            ApplicationSession.getInstance().getMessage("leadlift.master.select.correctcharges")
                                    + rowCount);
                    errorDtos.add(wmsDtos.get(i));
                    isCheck = false;
                }
                if (wmsDtos.get(i).getLeLiFrom().compareTo(wmsDtos.get(i).getLeLiTo()) >= 0) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("leadlift.master.enter.fromValue") + rowCount);
                    wmsDtos.get(i).setErrMessage(
                            ApplicationSession.getInstance().getMessage("leadlift.master.enter.fromValue") + rowCount);
                    errorDtos.add(wmsDtos.get(i));
                    isCheck = false;
                }

                if (i != 0) {
                    if (wmsDtos.get(i).getLeLiFrom().compareTo(wmsDtos.get(i - 1).getLeLiTo()) <= 0) {

                        addValidationError(ApplicationSession.getInstance().getMessage("leadlift.master.previousvalue")
                                + rowCount);
                        wmsDtos.get(i).setErrMessage(
                                ApplicationSession.getInstance().getMessage("leadlift.master.previousvalue")
                                        + rowCount);
                        errorDtos.add(wmsDtos.get(i));
                        isCheck = false;
                    }

                }
            }

        } else {
            for (int i = 0; i <= wmsDtos.size() - 1; i++) {
                int rowCount = i + 1;
                if (wmsDtos.get(i).getLeLiTo().compareTo(new BigDecimal(0)) <= 0
                        || wmsDtos.get(i).getLeLiRate().compareTo(new BigDecimal(0)) <= 0) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("leadlift.master.select.correctcharges")
                                    + rowCount);
                    wmsDtos.get(i).setErrMessage(
                            ApplicationSession.getInstance().getMessage("leadlift.master.select.correctcharges")
                                    + rowCount);
                    errorDtos.add(wmsDtos.get(i));
                    isCheck = false;
                }
                if (i != 0) {
                    if (wmsDtos.get(i).getLeLiTo().compareTo(wmsDtos.get(i - 1).getLeLiTo()) <= 0) {

                        addValidationError(
                                ApplicationSession.getInstance().getMessage("leadlift.master.select.toValueRange")
                                        + rowCount);
                        wmsDtos.get(i).setErrMessage(
                                ApplicationSession.getInstance().getMessage("leadlift.master.select.toValueRange")
                                        + rowCount);
                        errorDtos.add(wmsDtos.get(i));
                        isCheck = false;
                    }

                }
            }
        }
        return isCheck;
    }

}
