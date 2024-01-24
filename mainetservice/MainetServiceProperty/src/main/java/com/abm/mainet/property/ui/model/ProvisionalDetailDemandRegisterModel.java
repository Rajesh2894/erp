package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class ProvisionalDetailDemandRegisterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private PropertyReportRequestDto propertyDto = new PropertyReportRequestDto();

    private List<PropertyReportRequestDto> propertyDtoList = new ArrayList<>();

    /*
     * private FinYearDTO financialYearDto=new FinYearDTO(); private List<FinYearDTO> FinancialYDtoList =new ArrayList<>();
     */
    private ProperySearchDto propertySearchdto = new ProperySearchDto();
    private List<ProperySearchDto> propertySearchDtoList = new ArrayList<>();

    private Date maxFinDate;
    private Date minFinDate;

    private List<Long> bmIdNoList;

    public PropertyReportRequestDto getPropertyDto() {
        return propertyDto;
    }

    public void setPropertyDto(PropertyReportRequestDto propertyDto) {
        this.propertyDto = propertyDto;
    }

    public List<PropertyReportRequestDto> getPropertyDtoList() {
        return propertyDtoList;
    }

    public void setPropertyDtoList(List<PropertyReportRequestDto> propertyDtoList) {
        this.propertyDtoList = propertyDtoList;
    }

    public ProperySearchDto getPropertySearchdto() {
        return propertySearchdto;
    }

    public List<ProperySearchDto> getPropertySearchDtoList() {
        return propertySearchDtoList;
    }

    public void setPropertySearchdto(ProperySearchDto propertySearchdto) {
        this.propertySearchdto = propertySearchdto;
    }

    public void setPropertySearchDtoList(List<ProperySearchDto> propertySearchDtoList) {
        this.propertySearchDtoList = propertySearchDtoList;
    }

    public Date getMaxFinDate() {
        return maxFinDate;
    }

    public Date getMinFinDate() {
        return minFinDate;
    }

    public void setMaxFinDate(Date maxFinDate) {
        this.maxFinDate = maxFinDate;
    }

    public void setMinFinDate(Date minFinDate) {
        this.minFinDate = minFinDate;
    }

    public List<Long> getBmIdNoList() {
        return bmIdNoList;
    }

    public void setBmIdNoList(List<Long> bmIdNoList) {
        this.bmIdNoList = bmIdNoList;
    }

    /*
     * public FinYearDTO getFinancialYearDto() { return financialYearDto; } public List<FinYearDTO> getFinancialYDtoList() {
     * return FinancialYDtoList; } public void setFinancialYearDto(FinYearDTO financialYearDto) { this.financialYearDto =
     * financialYearDto; } public void setFinancialYDtoList(List<FinYearDTO> financialYDtoList) { FinancialYDtoList =
     * financialYDtoList; }
     */

}
