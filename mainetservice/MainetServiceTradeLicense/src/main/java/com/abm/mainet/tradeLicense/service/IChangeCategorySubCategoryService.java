package com.abm.mainet.tradeLicense.service;

import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.TradeMasterCategoryDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface IChangeCategorySubCategoryService {

    /**
     * used to get change In Category Sub Category charges( from BRMS Rule)
     * 
     * @param masDtoQ
     * @return
     */
    TradeMasterDetailDTO getCategoryChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

    /**
     * save Change in Category Sub category Service Application
     * 
     * @param masDto
     * @return
     */

    TradeMasterDetailDTO saveChangeCategorySubcategoryService(TradeMasterDetailDTO masDto,
            TradeMasterDetailDTO tradeDto);

    /**
     * used to get Word Zone Block By Application Id
     * 
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

    /**
     * get Loi Charges
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     * @throws CloneNotSupportedException
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

    TradeMasterDetailDTO getCategoryWiseLoiChargesFromBrmsRule(TradeMasterDetailDTO masDto);

    TradeMasterDetailDTO saveCategoryServiceFromPortal(TradeMasterCategoryDto dto);

}
