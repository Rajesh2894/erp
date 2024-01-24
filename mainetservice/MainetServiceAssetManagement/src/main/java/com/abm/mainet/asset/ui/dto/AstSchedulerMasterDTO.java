/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author satish.rathore
 *
 */
public class AstSchedulerMasterDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5360934178080790486L;

    String calculationType;
    String assetCode;
    Long assetClass;
    Date assetDateField;

    

    AssetErrorDTO asetErrorDto;
    AssetValuationDetailsDTO valuationDto;
    Set<AssetErrorDTO> errorSet = new HashSet<>();
    Set<AssetValuationDetailsDTO> valuationSet = new HashSet<>();

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public AssetErrorDTO getAsetErrorDto() {
        return asetErrorDto;
    }

    public void setAsetErrorDto(AssetErrorDTO asetErrorDto) {
        this.asetErrorDto = asetErrorDto;
    }

    public AssetValuationDetailsDTO getValuationDto() {
        return valuationDto;
    }

    public void setValuationDto(AssetValuationDetailsDTO valuationDto) {
        this.valuationDto = valuationDto;
    }

    public Long getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(Long assetClass) {
        this.assetClass = assetClass;
    }

    public Set<AssetErrorDTO> getErrorSet() {
        return errorSet;
    }

    public void setErrorSet(Set<AssetErrorDTO> errorSet) {
        this.errorSet = errorSet;
    }

    public Set<AssetValuationDetailsDTO> getValuationSet() {
        return valuationSet;
    }

    public void setValuationSet(Set<AssetValuationDetailsDTO> valuationSet) {
        this.valuationSet = valuationSet;
    }

    /**
     * @return the assetDateField
     */
    public Date getAssetDateField() {
        return assetDateField;
    }

    /**
     * @param assetDateField the assetDateField to set
     */
    public void setAssetDateField(Date assetDateField) {
        this.assetDateField = assetDateField;
    }
    
    

}
