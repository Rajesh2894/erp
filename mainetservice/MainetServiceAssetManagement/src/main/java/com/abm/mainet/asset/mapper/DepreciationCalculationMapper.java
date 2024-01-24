/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.CalculationDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
public class DepreciationCalculationMapper {

    /**
     * @param calculatedto
     * @return valuationDTO
     */
    public static AssetValuationDetailsDTO mapToValuation(CalculationDTO calculatedto) {
        AssetValuationDetailsDTO valuationDTO = new AssetValuationDetailsDTO();
        BeanUtils.copyProperties(calculatedto, valuationDTO);
        valuationDTO.setValuationDetId(calculatedto.getCalcualtionId());
        valuationDTO.setCreatedBy(calculatedto.getCreatedBy());
        valuationDTO.setCreationDate(calculatedto.getCreationDate());
        valuationDTO.setLgIpMac(calculatedto.getLgIpMac());
        valuationDTO.setOrgId(calculatedto.getOrgId());
        valuationDTO.setUpdatedDate(calculatedto.getUpdatedDate());
        valuationDTO.setLgIpMacUpd(calculatedto.getLgIpMacUpd());
        valuationDTO.setAccumDeprValue(calculatedto.getAccumDeprValue());
        valuationDTO.setAssetId(calculatedto.getAssetId());
        valuationDTO.setPreviousBookDate(calculatedto.getPreviousBookDate());
        valuationDTO.setPreviousBookValue(calculatedto.getPreviousBookValue());
        valuationDTO.setCurrentBookValue(calculatedto.getCurrentBookValue());
        valuationDTO.setCurrentBookDate(calculatedto.getCurrentBookDate());
        valuationDTO.setDeprValue(calculatedto.getDeprValue());
        valuationDTO.setBookFinYear(calculatedto.getBookFinYear());
        valuationDTO.setGroupId(calculatedto.getGroupId());
        valuationDTO.setChangetype(calculatedto.getChangetype());
        valuationDTO.setInitialBookValue(calculatedto.getInitialBookValue());
        valuationDTO.setLife(calculatedto.getLife());
        valuationDTO.setChangetype(calculatedto.getChangetype());
        valuationDTO.setAccountCode(calculatedto.getAccountCode());
        return valuationDTO;
    }

    public static CalculationDTO mapToCalculation(AssetValuationDetailsDTO valuationDTO) {
        CalculationDTO calculatedto = new CalculationDTO();
        BeanUtils.copyProperties(valuationDTO, calculatedto);
        calculatedto.setCalcualtionId(valuationDTO.getValuationDetId());
        calculatedto.setCreatedBy(valuationDTO.getCreatedBy());
        calculatedto.setCreationDate(valuationDTO.getCreationDate());
        calculatedto.setLgIpMac(valuationDTO.getLgIpMac());
        calculatedto.setOrgId(valuationDTO.getOrgId());
        calculatedto.setUpdatedDate(valuationDTO.getUpdatedDate());
        calculatedto.setLgIpMacUpd(valuationDTO.getLgIpMacUpd());
        calculatedto.setAccumDeprValue(valuationDTO.getAccumDeprValue());
        calculatedto.setAssetId(valuationDTO.getAssetId());
        calculatedto.setPreviousBookDate(valuationDTO.getPreviousBookDate());
        calculatedto.setPreviousBookValue(valuationDTO.getPreviousBookValue());
        calculatedto.setCurrentBookValue(valuationDTO.getCurrentBookValue());
        calculatedto.setCurrentBookDate(valuationDTO.getCurrentBookDate());
        calculatedto.setDeprValue(valuationDTO.getDeprValue());
        calculatedto.setBookFinYear(valuationDTO.getBookFinYear());
        calculatedto.setGroupId(valuationDTO.getGroupId());
        calculatedto.setChangetype(valuationDTO.getChangetype());
        calculatedto.setInitialBookValue(valuationDTO.getInitialBookValue());
        calculatedto.setLife(valuationDTO.getLife());
        calculatedto.setChangetype(valuationDTO.getChangetype());
        return calculatedto;
    }

}
