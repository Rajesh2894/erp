package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.domain.AssetFunctionalLocation;
import com.abm.mainet.asset.service.IAssetFunctionalLocationMasterService;
import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;
import com.abm.mainet.asset.ui.validator.AssetFunctionalLoacationValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author vishnu.jagdale
 *
 */
/*
 * Defect #5054 Help document is not displayed after clicking on help icon.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetFunctionalLocationModel extends AbstractFormModel {

    private static final long serialVersionUID = 564342699175223868L;

    @Autowired
    private IAssetFunctionalLocationMasterService assetFuncLocMasterService;

    // Used n both
    private List<AssetFunctionalLocationDTO> funcLocDTOList;

    // Used for drop-down value
    private List<AssetFunctionalLocationDTO> parentFuncLocDTOList;

    private AssetFunctionalLocationDTO assetfucnLocDTO;

    // Unit lookup List fetch by using 'WUT' prefix
    private List<LookUp> unitLookUpList = new ArrayList<LookUp>();

    // Retrive details from entity
    List<AssetFunctionalLocation> asstFuncLocList = null;

    private String modeType;

    /**
     * As scope is request so for every call we need to initialize the fucntion Location List
     */
    private final void initializeMasterDetails() {
        // Retrive function code for parent dropdown
        asstFuncLocList = assetFuncLocMasterService
                .retriveFunctionLocationListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        // initializing lookUp list for Unit_Of_Measurement
        unitLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation());

        convertToParentFunctionalDTOList(asstFuncLocList);

    }

    /**
     * used for initializing the functionDTO List Object
     */
    public final void intializeObject() {
        funcLocDTOList = new ArrayList<AssetFunctionalLocationDTO>();

        if (modeType.equalsIgnoreCase("C")) {
            assetfucnLocDTO = new AssetFunctionalLocationDTO();
            funcLocDTOList.add(assetfucnLocDTO);
        }

        initializeMasterDetails();

    }

    @Override
    public boolean saveForm() {
        validateBean(funcLocDTOList, AssetFunctionalLoacationValidator.class);

        if (hasValidationErrors()) {
            // Add a root entry for it and initializes the Object
            // initializeRootEntry();
            initializeMasterDetails();

            return false;
        } else {

            if (modeType != null && modeType.equalsIgnoreCase("E")) {
                assetFuncLocMasterService.updateFunctionLocation(convertDTOtoEntity().get(0));
                setSuccessMessage(
                        ApplicationSession.getInstance().getMessage("functional.location.master.vldn.updatesuccessmsg"));
                return true;
            } else {
                assetFuncLocMasterService.saveFunctionLocationList(convertDTOtoEntity());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("functional.location.master.vldn.savesuccessmsg"));
                return true;
            }
        }
    }

    /**
     * used to convert functional location code DTO to entity object form
     * 
     * @return List<AssetFunctionalLocation>
     */
    public List<AssetFunctionalLocation> convertDTOtoEntity() {
        List<AssetFunctionalLocation> asstFuncLocListEnty = new ArrayList<AssetFunctionalLocation>();

        for (int i = 0; i < funcLocDTOList.size(); i++) {
            AssetFunctionalLocationDTO funcLocDTO = funcLocDTOList.get(i);

            AssetFunctionalLocation assFunLocEnty = new AssetFunctionalLocation();

            assFunLocEnty.setFuncLocationCode(funcLocDTO.getFuncLocationCode());
            assFunLocEnty.setDescription(funcLocDTO.getDescription());
            // assFunLocEnty.setFuncLocParentId(funcLocDTO.getParentId());
            // Used to create and object regarding parentId
            AssetFunctionalLocation tempEnty = new AssetFunctionalLocation();

            if (funcLocDTO.getParentId() == 0) {
                assFunLocEnty.setFlcObject(null);
            } else {
                tempEnty.setFuncLocationId(funcLocDTO.getParentId());
                assFunLocEnty.setFlcObject(tempEnty);
            }

            assFunLocEnty.setStartPoint(funcLocDTO.getStartPoint());
            assFunLocEnty.setEndPoint(funcLocDTO.getEndPoint());
            assFunLocEnty.setUnit(funcLocDTO.getUnit());
            assFunLocEnty.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

            setDefaultEntyConstraints(assFunLocEnty);

            asstFuncLocListEnty.add(assFunLocEnty);
        }

        return asstFuncLocListEnty;
    }

    private void setDefaultEntyConstraints(AssetFunctionalLocation obj) {
        if (modeType.equalsIgnoreCase("E")) {
            obj.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            obj.setUpdatedDate(new Date());
            obj.setLgIpMacUpd(Utility.getMacAddress());
        } else {
            obj.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            obj.setCreatedDate(new Date());
            obj.setLgIpMac(Utility.getMacAddress());
            obj.setLgIpMac(Utility.getMacAddress());
        }
    }

    /**
     * used for convert entity to DTO object form
     * 
     * @param asstFuncLocList
     */
    public void convertToParentFunctionalDTOList(List<AssetFunctionalLocation> asstFuncLocList) {
        // Add a root entry for it and initializes the Object
        initializeRootEntry();

        if (asstFuncLocList != null)
            for (int i = 0; i < asstFuncLocList.size(); i++) {
                AssetFunctionalLocationDTO parentAsstFuncLocDTO = new AssetFunctionalLocationDTO();

                // it is used to display in drop down list
                parentAsstFuncLocDTO.setFuncLocationCode(asstFuncLocList.get(i).getFuncLocationCode());
                parentAsstFuncLocDTO.setFuncLocationId(asstFuncLocList.get(i).getFuncLocationId());
                ;

                parentFuncLocDTOList.add(parentAsstFuncLocDTO);
            }
    }

    /**
     * used for convert entity to DTO object form
     * 
     * @param asstFuncLocList
     * @return List<AssetFunctionalLocationDTO>
     */
    public List<AssetFunctionalLocationDTO> convertEntityToFunctionalDTOList(List<AssetFunctionalLocation> asstFuncLocList) {
        List<AssetFunctionalLocationDTO> funcLocDTOList = new ArrayList<AssetFunctionalLocationDTO>();
        // Need to change once the prefix for functional code is added
        // List<LookUp> unitLookUpList_Temp = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
        // UserSession.getCurrent().getOrganisation());

        for (int i = 0; i < asstFuncLocList.size(); i++) {
            AssetFunctionalLocationDTO funcLocDTO = convertToFuncLocDTO(asstFuncLocList.get(i));
            funcLocDTOList.add(funcLocDTO);
        }
        return funcLocDTOList;
    }

    private AssetFunctionalLocationDTO convertToFuncLocDTO(AssetFunctionalLocation asstFuncLocEnty) {
        AssetFunctionalLocationDTO funcLocDTO = new AssetFunctionalLocationDTO();

        // it is used to display in drop down list
        funcLocDTO.setFuncLocationCode(asstFuncLocEnty.getFuncLocationCode());
        funcLocDTO.setFuncLocationId(asstFuncLocEnty.getFuncLocationId());
        ;
        funcLocDTO.setDescription(asstFuncLocEnty.getDescription());
        if (asstFuncLocEnty.getFlcObject() != null) {
            funcLocDTO.setParentCode(asstFuncLocEnty.getFlcObject().getFuncLocationCode());
            funcLocDTO.setParentId(asstFuncLocEnty.getFlcObject().getFuncLocationId());
        } else {
            funcLocDTO.setParentCode(MainetConstants.AssetManagement.NONE);
            if (modeType != null && modeType.equalsIgnoreCase("E"))
                funcLocDTO.setParentId(0l);
        }
        funcLocDTO.setStartPoint(asstFuncLocEnty.getStartPoint());
        funcLocDTO.setEndPoint(asstFuncLocEnty.getEndPoint());
        funcLocDTO.setUnit(asstFuncLocEnty.getUnit());

        LookUp unitObj = CommonMasterUtility.getNonHierarchicalLookUpObject(asstFuncLocEnty.getUnit());
        funcLocDTO.setUnitDesc(unitObj.getLookUpCode());

        return funcLocDTO;
    }

    /**
     * used add a default root entry inside inside drop down
     * 
     * @param asstFuncLocList
     */
    private final void initializeRootEntry() {
        parentFuncLocDTOList = new ArrayList<AssetFunctionalLocationDTO>();

        AssetFunctionalLocationDTO parentAsstFuncLocDTO = new AssetFunctionalLocationDTO();
        parentAsstFuncLocDTO.setFuncLocationCode(MainetConstants.AssetManagement.NONE);
        parentAsstFuncLocDTO.setFuncLocationId(0l);

        parentFuncLocDTOList.add(parentAsstFuncLocDTO);

    }

    /**
     * used validate the function location code based onblur function
     * 
     * @param funcLocCode
     * @return boolean
     */
    public boolean isDuplicate_FuncLocCode(String funcLocCode, Long orgId) {
        return assetFuncLocMasterService.isDuplicate(funcLocCode, orgId);
    }

    /**
     * used to retrieve object from database based on funcLocId and orgId
     * 
     * @param funcLocId
     * @param orgId
     */
    public void getFuncLocCode(final Long funcLocId, Long orgId) {
        AssetFunctionalLocation funcLocCodeEnty = assetFuncLocMasterService.findByFuncLocId(funcLocId, orgId);
        AssetFunctionalLocationDTO dto = convertToFuncLocDTO(funcLocCodeEnty);
        funcLocDTOList.add(dto);
    }

    /**
     * used to filter search results on basis of below given parameters
     * 
     * @param orgId
     */
    public List<AssetFunctionalLocationDTO> filterFuncLocCodeList(Long orgId) {
        String funcLocCode = null;
        String description = null;
        List<AssetFunctionalLocation> filteredFunLocList = assetFuncLocMasterService
                .searchFunLocByLocCodeAndDescriptionAndOrgId(funcLocCode, description, orgId);
        return convertEntityToFunctionalDTOList(filteredFunLocList);
    }

    /**
     * used to filter search results on basis of below given parameters It is overloaded method
     * 
     * @param funcLocCode
     * @param description
     * @param orgId
     */
    public List<AssetFunctionalLocationDTO> filterFuncLocCodeList(String funcLocCode, String description, Long orgId) {
        List<AssetFunctionalLocation> filteredFunLocList = assetFuncLocMasterService
                .searchFunLocByLocCodeAndDescriptionAndOrgId(funcLocCode, description, orgId);
        return convertEntityToFunctionalDTOList(filteredFunLocList);
    }

    public List<AssetFunctionalLocationDTO> getFuncLocDTOList() {
        return funcLocDTOList;
    }

    public void setFuncLocDTOList(List<AssetFunctionalLocationDTO> funcLocDTOList) {
        this.funcLocDTOList = funcLocDTOList;
    }

    public IAssetFunctionalLocationMasterService getAssetFuncLocMasterService() {
        return assetFuncLocMasterService;
    }

    public void setAssetFuncLocMasterService(IAssetFunctionalLocationMasterService assetFuncLocMasterService) {
        this.assetFuncLocMasterService = assetFuncLocMasterService;
    }

    public List<LookUp> getUnitLookUpList() {
        return unitLookUpList;
    }

    public void setUnitLookUpList(List<LookUp> unitLookUpList) {
        this.unitLookUpList = unitLookUpList;
    }

    public List<AssetFunctionalLocationDTO> getParentFuncLocDTOList() {
        return parentFuncLocDTOList;
    }

    public void setParentFuncLocDTOList(List<AssetFunctionalLocationDTO> parentFuncLocDTOList) {
        this.parentFuncLocDTOList = parentFuncLocDTOList;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public AssetFunctionalLocationDTO getAssetfucnLocDTO() {
        return assetfucnLocDTO;
    }

    public void setAssetfucnLocDTO(AssetFunctionalLocationDTO assetfucnLocDTO) {
        this.assetfucnLocDTO = assetfucnLocDTO;
    }

}
