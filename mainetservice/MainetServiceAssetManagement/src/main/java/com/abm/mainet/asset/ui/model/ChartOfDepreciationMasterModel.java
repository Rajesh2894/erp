/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IChartOfDepreciationMasterService;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author sarojkumar.yadav
 *
 * Model Class for Chart of Depreciation Master
 */
/*
 * Defect #5054 Help document is not displayed after clicking on help icon.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChartOfDepreciationMasterModel extends AbstractFormModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1641214196404464257L;

    @Autowired
    IChartOfDepreciationMasterService cdmService;

    @Resource
    private SecondaryheadMasterService shmService;

    private ChartOfDepreciationMasterDTO assetChartOfDepreciationMasterDTO = new ChartOfDepreciationMasterDTO();
    private String modeType;

    /**
     * @return the assetChartOfDepreciationMasterDTO
     */
    public ChartOfDepreciationMasterDTO getAssetChartOfDepreciationMasterDTO() {
        return assetChartOfDepreciationMasterDTO;
    }

    /**
     * @param assetChartOfDepreciationMasterDTO the assetChartOfDepreciationMasterDTO to set
     */
    public void setAssetChartOfDepreciationMasterDTO(ChartOfDepreciationMasterDTO assetChartOfDepreciationMasterDTO) {
        this.assetChartOfDepreciationMasterDTO = assetChartOfDepreciationMasterDTO;
    }

    /**
     * @return the modeType
     */
    public String getModeType() {
        return modeType;
    }

    /**
     * @param modeType the modeType to set
     */
    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    @Override
    public boolean saveForm() {

        boolean cdmStatus = false;
        ChartOfDepreciationMasterDTO mstDto = getAssetChartOfDepreciationMasterDTO();
        mstDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        validateConstraints(mstDto, ChartOfDepreciationMasterDTO.class, getBindingResult());

        /*
         * if (this.getModeType().equals(MainetConstants.MODE_CREATE)) { duplicateStatus =
         * cdmService.checkDuplicateName(UserSession.getCurrent().getOrganisation(). getOrgid(), mstDto.getName().trim()); if
         * (duplicateStatus) { addValidationError( ApplicationSession.getInstance().getMessage(
         * "assset.depreciationMaster.duplicate.name")); }
         */
        if (hasValidationErrors()) {
            return false;
        }

        if (this.getModeType().equals(MainetConstants.MODE_EDIT)) {
            cdmStatus = cdmService.updateByGroupId(mstDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("assset.depreciationMaster.update.success"));
        } else {
            mstDto.setDeptCode(UserSession.getCurrent().getModuleDeptCode());
            cdmStatus = cdmService.addEntry(mstDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("assset.depreciationMaster.create.success"));
        }

        return cdmStatus;
    }

    /**
     * update chart Of Depreciation Master fields by name or/and by accountCode or/and by assetClass or/and by frequency with
     * primary Key groupId.
     * 
     * @param orgId
     * @param name
     * @param accountCode
     * @param assetClass
     * @param frequency
     * @return List<ChartOfDepreciationMasterDTO> if record found else return empty dto
     */
    public List<ChartOfDepreciationMasterDTO> filterCdmDataList(final String accountCode, final Long assetClass,
            final Long frequency, final String name, final Long orgId) {

        List<ChartOfDepreciationMasterDTO> cdmDTOList = cdmService.findBySearchData(accountCode, assetClass, frequency,
                name, orgId, UserSession.getCurrent().getModuleDeptCode());
        /*
         * cdmDTOList.forEach(cdmDTO -> { cdmDTO.setAccountCode( shmService.findBySacHeadId(cdmDTO.getAccountCode(),
         * orgId).getBaAccountid()); cdmDTO.setAssetClass(CommonMasterUtility.getCPDDescription(Long.valueOf(
         * cdmDTO.getAssetClass()), MainetConstants.MODE_EDIT));
         * cdmDTO.setFrequency(CommonMasterUtility.getCPDDescription(Long.valueOf(cdmDTO .getFrequency()),
         * MainetConstants.MODE_EDIT)); });
         */
        return cdmDTOList;
    }

    public boolean isDuplicateName(final String name) {
        boolean duplicateStatus = false;
        duplicateStatus = cdmService.checkDuplicateName(UserSession.getCurrent().getOrganisation().getOrgid(),
                name.trim());
        return duplicateStatus;
    }

    public boolean isDuplicateAssetClass(final Long assetClass) {
        boolean duplicateStatus = false;
        duplicateStatus = cdmService.checkDuplicateAssetClass(UserSession.getCurrent().getOrganisation().getOrgid(),
                assetClass);
        return duplicateStatus;
    }
}