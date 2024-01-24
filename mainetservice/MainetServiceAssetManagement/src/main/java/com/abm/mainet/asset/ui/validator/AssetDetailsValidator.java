/**
 * 
 */
package com.abm.mainet.asset.ui.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.service.IChartOfDepreciationMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author satish.rathore
 *
 */
@Component
public class AssetDetailsValidator extends BaseEntityValidator<AssetDetailsDTO> {
    final ApplicationSession session = ApplicationSession.getInstance();

    @Autowired
    private IChartOfDepreciationMasterService chartOfDepreciationMasterService;

    @Override
    protected void performValidations(AssetDetailsDTO dto, EntityValidationContext<AssetDetailsDTO> entityValidationContext) {
        final AssetPurchaseInformationDTO purDto = dto.getAssetPurchaseInformationDTO();
        final AssetDepreciationChartDTO depDto = dto.getAstDepreChartDTO();
        if (purDto == null) {
            if (depDto != null && depDto.getInitialAccumDepreAmount() != null) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.purchase.dto"));
            }
        } else if (depDto != null) {
            if (purDto.getCostOfAcquisition() != null && depDto.getInitialAccumDepreAmount() != null) {
                int check = depDto.getInitialAccumDepreAmount().compareTo(purDto.getCostOfAcquisition());
                if (check == 1) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.dep.amount"));
                }
            } else if (purDto.getCostOfAcquisition() == null && depDto.getInitialAccumDepreAmount() != null) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.purchase.costacqui"));
            }

            if (purDto.getDateOfAcquisition() != null && depDto.getInitialAccumulDeprDate() != null) {
                if (purDto.getDateOfAcquisition().after(depDto.getInitialAccumulDeprDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.dep.date"));
                }

            } else if (purDto.getDateOfAcquisition() == null && depDto.getInitialAccumulDeprDate() != null) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.purchase.acquidate"));
            }

            if (depDto.getChartOfDepre() != null && !depDto.getChartOfDepre().equals(0l)) {
                Long count = chartOfDepreciationMasterService.getAssetClassByGroupIdAndAssetClass(depDto.getChartOfDepre(),
                        dto.getAssetInformationDTO().getAssetClass2());
                if (count.equals(0L) || count == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.dep.category"));
                }
            }
            /* Task #5318 */
            if (purDto.getInitialBookDate() == null && depDto.getInitialAccumulDeprDate() != null) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.purchase.initialbookdaterequired"));
            } else if (purDto.getInitialBookDate() != null && depDto.getInitialAccumulDeprDate() != null) {

                if (purDto.getInitialBookDate().before(depDto.getInitialAccumulDeprDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.accumulated.depr.date"));
                }

            }

        }
    }

    // T#92467
    public static String getModuleDeptCode(String smShortDesc, String requestURI, String compareURI) {
        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String moduleDeptCode = MainetConstants.AssetManagement.ASSETCODE;
        if (StringUtils.isNotEmpty(smShortDesc)) {
            // check which module click based on smShortDesc
            String deptCode = MainetConstants.ITAssetManagement.IT_ASSET_CODE;
            moduleDeptCode = (smShortDesc.equals(deptCode) ? deptCode : MainetConstants.AssetManagement.ASSETCODE);
        } else if (requestURI.substring(requestURI.lastIndexOf("/") + 1).equals(compareURI)) {
            moduleDeptCode = (MainetConstants.ITAssetManagement.IT_ASSET_CODE);
        }
        return moduleDeptCode;
    }

    public static Map<String, String> getModuleDeptAndServiceCode(String moduelDeptCode, String astServiceCode,
            String ITServiceCode) {
        Map<String, String> map = new HashMap<String, String>();
        String moduleDeptCode = moduelDeptCode.equals(MainetConstants.AssetManagement.ASSETCODE)
                ? MainetConstants.AssetManagement.ASSETCODE
                : MainetConstants.ITAssetManagement.IT_ASSET_CODE;

        String serviceCodeModuleWise = moduelDeptCode
                .equals(MainetConstants.AssetManagement.ASSETCODE)
                        ? astServiceCode
                        : ITServiceCode;
        map.put(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY, moduleDeptCode);
        map.put(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY, serviceCodeModuleWise);
        return map;
    }

}
