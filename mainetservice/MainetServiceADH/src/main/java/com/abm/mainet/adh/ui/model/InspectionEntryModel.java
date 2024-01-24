/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.adh.dto.ADHPublicNoticeDto;
import com.abm.mainet.adh.dto.InspectionEntryDetDto;
import com.abm.mainet.adh.dto.InspectionEntryDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.service.IInspectionEntryService;
import com.abm.mainet.adh.ui.validator.InspectionEntryValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 23-Oct-2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class InspectionEntryModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IInspectionEntryService inspectionEntryService;

    private List<String> licenseNumberList = new ArrayList<>();
    private NewAdvertisementApplicationDto advertisementDto;
    private NewAdvertisementApplicationDetDto advertisementDetailDto;
    private String agencyName;
    private List<Employee> inspectorName = new ArrayList<>();
    private InspectionEntryDto inspectionEntryDto;
    private InspectionEntryDetDto inspectionEntryDetDto;
    private List<InspectionEntryDetDto> inspectionEntryDetDtoList = new ArrayList<>();
    private List<TbApprejMas> remark = new ArrayList<>();
    private ADHPublicNoticeDto publicNoticeDto = new ADHPublicNoticeDto();
    private double amountToPay;
    private List<ChargeDetailDTO> chargesInfo;
    private boolean payable;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
     */
    @Override
    public boolean saveForm() {
        boolean status = true;
        validateBean(inspectionEntryDto, InspectionEntryValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Date newDate = new Date();
        if (inspectionEntryDto.getInesId() == null) {
            inspectionEntryDto.setOrgId(orgId);
            inspectionEntryDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            inspectionEntryDto.setCreatedDate(newDate);
            inspectionEntryDto.setLgIpMac(getClientIpAddress());
            for (InspectionEntryDetDto detailDto : inspectionEntryDto.getInspectionEntryDetDto()) {
                detailDto.setOrgId(orgId);
                detailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                detailDto.setCreatedDate(newDate);
                detailDto.setLgIpMac(getClientIpAddress());
            }
            inspectionEntryService.saveInspectionEntryData(inspectionEntryDto);
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("InspectionEntry data saved successfully"));
        }

        return status;
    }

    public boolean savePublicNotice() {

        boolean status = true;
        validateBean(inspectionEntryDto, InspectionEntryValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Date newDate = new Date();
        if (inspectionEntryDto.getInesId() == null) {
            inspectionEntryDto.setOrgId(orgId);
            inspectionEntryDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            inspectionEntryDto.setCreatedDate(newDate);
            inspectionEntryDto.setLgIpMac(getClientIpAddress());
            inspectionEntryDto.setNoticeGenFlag(MainetConstants.FlagY);
            inspectionEntryDto.setNoOfDays(publicNoticeDto.getNoOfDays());
            for (InspectionEntryDetDto detailDto : inspectionEntryDto.getInspectionEntryDetDto()) {
                detailDto.setOrgId(orgId);
                detailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                detailDto.setCreatedDate(newDate);
                detailDto.setLgIpMac(getClientIpAddress());
            }
            inspectionEntryService.saveInspectionEntryData(inspectionEntryDto);
        }

        return status;

    }

    public List<String> getLicenseNumberList() {
        return licenseNumberList;
    }

    public void setLicenseNumberList(List<String> licenseNumberList) {
        this.licenseNumberList = licenseNumberList;
    }

    public NewAdvertisementApplicationDto getAdvertisementDto() {
        return advertisementDto;
    }

    public void setAdvertisementDto(NewAdvertisementApplicationDto advertisementDto) {
        this.advertisementDto = advertisementDto;
    }

    public NewAdvertisementApplicationDetDto getAdvertisementDetailDto() {
        return advertisementDetailDto;
    }

    public void setAdvertisementDetailDto(NewAdvertisementApplicationDetDto advertisementDetailDto) {
        this.advertisementDetailDto = advertisementDetailDto;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public List<Employee> getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(List<Employee> inspectorName) {
        this.inspectorName = inspectorName;
    }

    public InspectionEntryDto getInspectionEntryDto() {
        return inspectionEntryDto;
    }

    public void setInspectionEntryDto(InspectionEntryDto inspectionEntryDto) {
        this.inspectionEntryDto = inspectionEntryDto;
    }

    public InspectionEntryDetDto getInspectionEntryDetDto() {
        return inspectionEntryDetDto;
    }

    public void setInspectionEntryDetDto(InspectionEntryDetDto inspectionEntryDetDto) {
        this.inspectionEntryDetDto = inspectionEntryDetDto;
    }

    public List<InspectionEntryDetDto> getInspectionEntryDetDtoList() {
        return inspectionEntryDetDtoList;
    }

    public void setInspectionEntryDetDtoList(List<InspectionEntryDetDto> inspectionEntryDetDtoList) {
        this.inspectionEntryDetDtoList = inspectionEntryDetDtoList;
    }

    public List<TbApprejMas> getRemark() {
        return remark;
    }

    public void setRemark(List<TbApprejMas> remark) {
        this.remark = remark;
    }

    public ADHPublicNoticeDto getPublicNoticeDto() {
        return publicNoticeDto;
    }

    public void setPublicNoticeDto(ADHPublicNoticeDto publicNoticeDto) {
        this.publicNoticeDto = publicNoticeDto;
    }

    public IInspectionEntryService getInspectionEntryService() {
        return inspectionEntryService;
    }

    public void setInspectionEntryService(IInspectionEntryService inspectionEntryService) {
        this.inspectionEntryService = inspectionEntryService;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

}
