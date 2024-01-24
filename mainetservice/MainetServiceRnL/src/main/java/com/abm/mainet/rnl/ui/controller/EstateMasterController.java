package com.abm.mainet.rnl.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.Prefix;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.dto.EstateMasterGrid;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.ui.model.EstateMasterModel;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("EstateMaster.html")
public class EstateMasterController extends AbstractFormController<EstateMasterModel> {

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IEstateService iEstateService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IFileUploadService fileUploadService;

    private static final List EMPTY_LIST = new ArrayList<>();

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        clearFileSession();
        uiModel.addAttribute(MainetConstants.EstateMasters.LOCATION_LIST, getLocations());
		/* return MainetConstants.EstateMasters.ESTATE_MASTER_LIST; */
        return new ModelAndView(MainetConstants.EstateMasters.ESTATE_MASTER_LIST, MainetConstants.FORM_NAME, getModel());
    }

    /**
     * Shows a form page in order to create a new Estate
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "esId", required = false) final Long esId,
            @RequestParam(value = "type", required = false) final String modeType) {

        final EstateMasterModel estateMasterModel = getModel();
        estateMasterModel.setLocationList(getLocations());
        final HashMap<Character, String> hashMap = new HashMap<>();
        hashMap.put(MainetConstants.EstateMaster.Single,
                ApplicationSession.getInstance().getMessage(MainetConstants.EstateMasters.ESTATE_CAT_SINGLE));
        hashMap.put(MainetConstants.EstateMaster.Group,
                ApplicationSession.getInstance().getMessage(MainetConstants.EstateMasters.ESTATE_CAT_GROUP));
        estateMasterModel.setCategoryType(hashMap);
        populateModel(esId, estateMasterModel, modeType);
        // DUMMY TEST FOR ASSET COE
        // T#139716
        List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
                UserSession.getCurrent().getOrganisation());
        Boolean tsclENVPresent = envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.TSCL)
                        && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        if (tsclENVPresent) {
            getModel().setApplicableENV(MainetConstants.APP_NAME.TSCL);
        }
        return new ModelAndView(MainetConstants.EstateMasters.ESTATE_MASTER_FORM, MainetConstants.FORM_NAME, estateMasterModel);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbEstateMaster
     */
    private void populateModel(final Long esId, final EstateMasterModel estateMasterModel, final String modeType) {

        if (esId == null) {
            estateMasterModel.setEstateMaster(new EstateMaster());
            estateMasterModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);

        } else {
            estateMasterModel.setEstateMaster(iEstateService.findById(esId));
            final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    estateMasterModel.getEstateMaster().getCode());
            estateMasterModel.setDocumentList(attachDocs);
            if (MainetConstants.RnLCommon.MODE_VIEW.equals(modeType)) {
                estateMasterModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
                estateMasterModel.getEstateMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_VIEW);
            } else {
                estateMasterModel.setModeType(MainetConstants.RnLCommon.MODE_EDIT);
                estateMasterModel.getEstateMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_EDIT);
                addInMap(attachDocs);
            }
        }
        // make asset code list when asset module live......
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.LandEstate.ASSET_CODE_VALUE,
                MainetConstants.LandEstate.MODULE_LIVE_INTEGRATION, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        if (lookUp != null && lookUp.getOtherField() != null &&
                lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
            List<Long> assetClassLookupIds = new ArrayList<>();
            List<LookUp> assetClassLookups = CommonMasterUtility.getLookUps(MainetConstants.ASSET_CLASS_PREFIX,
                    UserSession.getCurrent().getOrganisation());
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            	assetClassLookups.forEach(lookup -> {
                    if (lookup.getLookUpCode().equals(MainetConstants.FlagL)
                            || lookup.getLookUpCode().equals(MainetConstants.FlagB)
                            || lookup.getLookUpCode().equals(MainetConstants.FlagR))
                        assetClassLookupIds.add(lookup.getLookUpId());
                });
            }else {
            	assetClassLookups.forEach(lookup -> {
                    if (lookup.getLookUpCode().equals(MainetConstants.FlagL)
                            || lookup.getLookUpCode().equals(MainetConstants.FlagB))
                        assetClassLookupIds.add(lookup.getLookUpId());
                });
            }
            
            // D#100288
            if (!assetClassLookupIds.isEmpty()) {
            	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            		estateMasterModel.setAssetNoAndNameList(iEstateService.fetchAssetCodesAndAssetNameByAssetClassIds(assetClassLookupIds,
                            UserSession.getCurrent().getOrganisation().getOrgid(), modeType));
            	}else {
            		estateMasterModel.setAssetCodeList(iEstateService.fetchAssetCodesByLookupIds(assetClassLookupIds,
                            UserSession.getCurrent().getOrganisation().getOrgid(), modeType));
            	}
                
            }
        }
    }

    @ResponseBody
    @RequestMapping(params = "getEstate", method = RequestMethod.POST)
    public Map<Long, String> getEstateList(@RequestParam("locId") final long locationId) {

        final Map<Long, String> map = new HashMap<>();
        final List<EstateMaster> estateMasters = iEstateService.findEstateByLocId(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                locationId, UserSession.getCurrent().getLanguageId());
        if (estateMasters != null) {
            for (final EstateMaster estateMaster : estateMasters) {
                map.put(estateMaster.getEsId(), estateMaster.getNameEng());
            }
        }
        return map;
    }

    /**
     * Get Estate Grid data
     * @param request
     * @return
     */
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        return getModel().paginate(httpServletRequest, page, rows, gridData(httpServletRequest));
    }

	private List<EstateMasterGrid> gridData(final HttpServletRequest request) {
        final String locationId = request.getParameter(MainetConstants.EstateMasters.LOCATION_ID);
        final String estateId = request.getParameter(MainetConstants.EstateMasters.ESTATE_ID);
        String purpose = null;
        String estateType = null;
        String subType = null;
        String acqType = null;
        
        List<EstateMasterGrid> estateMasterGrids = null;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
      
        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        	if (locationId.isEmpty() && estateId.isEmpty()) {
                estateMasterGrids = iEstateService.findGridRecords(orgId);
            } else {
                estateMasterGrids = iEstateService.findGridFilterRecords(orgId,
                        locationId.isEmpty() ? null : Long.valueOf(locationId), estateId.isEmpty() ? null : Long.valueOf(estateId));
            }
        }else {
        	
        	purpose = request.getParameter(MainetConstants.EstateMasters.PURPOSE);
            estateType = request.getParameter(MainetConstants.EstateMasters.ESTATE_TYPE);
            subType = request.getParameter(MainetConstants.EstateMasters.SUB_TYPE);
            acqType = request.getParameter(MainetConstants.EstateMasters.ACQTYPE);
            
            Integer estateTypeId = 0; 
            Integer subTypeId = 0;
            Long  purposeId = 0L;
            Long acqTypeId = 0L;
            
            if(purpose!=null && !purpose.equals("0")) {
            	purposeId = Long.parseLong(purpose);
            }            
            if(acqType!=null && !acqType.equals("0")) {
            	acqTypeId = Long.parseLong(acqType);
            }
            if(estateType != null && !estateType.equals("0")) {
            	estateTypeId = Integer.parseInt(estateType);
            	subTypeId = Integer.parseInt(subType);
            }
            
        	//for all
        	if (locationId.isEmpty() && estateId.isEmpty() && purposeId==0L && estateType.equals("0") && subType==null && acqType.equals("0")) {
                estateMasterGrids = iEstateService.findGridRecords(orgId);
            }
        	// for locationId and estateId
        	else if(!locationId.isEmpty() && !estateId.isEmpty() && purposeId==0L && !estateType.equals("0") && subType==null && acqType==null) {
            	estateMasterGrids = iEstateService.findGridFilterRecords(orgId,
                        locationId.isEmpty() ? null : Long.valueOf(locationId), estateId.isEmpty() ? null : Long.valueOf(estateId));
            }else if(!locationId.isEmpty() || !estateId.isEmpty() || purposeId != 0L || !estateType.equals("0") || subType!=null || !acqType.equals("0")) {
            	estateMasterGrids = iEstateService.searchRecordsByParameters(orgId, locationId.isEmpty() ? null : Long.valueOf(locationId), 
            			estateId.isEmpty() ? null : Long.valueOf(estateId), purposeId, estateTypeId, subTypeId, acqTypeId);
            }
        	
        }
        
        if (estateMasterGrids != null) {
            for (final EstateMasterGrid estateMasterGrid : estateMasterGrids) {
                if (UserSession.getCurrent().getLanguageId() != MainetConstants.ENGLISH) {
                    estateMasterGrid.setLocationEng(estateMasterGrid.getLocationReg());
                    estateMasterGrid.setNameEng(estateMasterGrid.getNameReg());
                }
                
                if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
                	if (estateMasterGrid.getPurpose() != null && estateMasterGrid.getPurpose() != 0L ) {
                		estateMasterGrid
                        .setPurposeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(estateMasterGrid.getPurpose(), 
                        		UserSession.getCurrent().getOrganisation()).getLookUpDesc());
                	}
                	
                	if (estateMasterGrid.getAcquisitionType() != null && estateMasterGrid.getAcquisitionType() != 0L ) {
                		estateMasterGrid
                        .setAcquisitionTypeName(CommonMasterUtility.getNonHierarchicalLookUpObject(estateMasterGrid.getAcquisitionType(), 
                        		UserSession.getCurrent().getOrganisation()).getLookUpDesc());
                	}
                }
                // D#100283
                if (estateMasterGrid.getCat() != null && estateMasterGrid.getCat().equals(MainetConstants.EstateMaster.Single)) {
                    estateMasterGrid.setCategoryName(ApplicationSession.getInstance().getMessage("estate.category.single"));
                } else {
                    estateMasterGrid.setCategoryName(
                            ApplicationSession.getInstance().getMessage(MainetConstants.EstateMasters.ESTATE_CAT_GROUP));
                }
                if (estateMasterGrid.getType() != null)
                    estateMasterGrid
                            .setTypeName(CommonMasterUtility.getHierarchicalLookUp(estateMasterGrid.getType(),
                                    UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc());

                if (estateMasterGrid.getSubType() != null)
                    estateMasterGrid
                            .setSubTypeName(CommonMasterUtility.getHierarchicalLookUp(estateMasterGrid.getSubType(),
                                    UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpDesc());
                
            }
        } else {
            estateMasterGrids = EMPTY_LIST;
        }
        return estateMasterGrids;
    }

    @ResponseBody
    @RequestMapping(params = "deleteEstate", method = RequestMethod.POST)
    public boolean deActiveEstateId(@RequestParam("esId") final Long esId) {
        iEstateService.deleteRecord(esId, UserSession.getCurrent().getEmployee().getEmpId());
        return true;
    }

    @Override
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response,
            final String fileCode, @RequestParam final String browserType) {
        final JsonViewObject jsonViewObject = super.uploadDocument(httpServletRequest, response, fileCode, browserType);
        if ((getModel().getModeType() == MainetConstants.RnLCommon.MODE_EDIT) && jsonViewObject.isStatus()) {
            jsonViewObject.setMessage(getUploadedMessgeString(jsonViewObject.getMessage()));
        }
        return jsonViewObject;
    }

    @Override
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        List<JsonViewObject> result = new ArrayList<>();
        ;
        if (getModel().getDocumentList() != null) {
            final Map<Long, String> fileNames = new LinkedHashMap<>();
            for (final AttachDocs attachDocs : getModel().getDocumentList()) {
                fileNames.put(attachDocs.getAttId(), attachDocs.getAttFname());
            }
            result = FileUploadUtility.getCurrent().getFileUploadListWithUniqueId(fileNames);
        }
        return result;
    }

    @Override
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType, @RequestParam final Long uniqueId) {
        if (uniqueId != null) {
            getModel().getIds().add(uniqueId);
        }
        final JsonViewObject jsonViewObject = super.doFileDeletion(fileId, httpServletRequest, browserType, uniqueId);
        jsonViewObject.setMessage(getUploadedMessgeString(jsonViewObject.getMessage()));
        return jsonViewObject;
    }

    private void clearFileSession() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
    }

    private void addInMap(final List<AttachDocs> attachDocs) {

        final String guidRanDNum = Utility.getGUIDNumber();
        if (null != FileNetApplicationClient.getInstance()) {
            for (final AttachDocs singleAttachment : attachDocs) {
                fileUploadService.downLoadFilesFromServer(singleAttachment, guidRanDNum, FileNetApplicationClient.getInstance());

            }
            FileUploadUtility.getCurrent().setExistingFolderPath(
                    Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                            + MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR);
        }

    }

    private List<Object[]> getLocations() {
    	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
    		return iLocationMasService.getLocationNameAndLocCodeByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	}else {
    		return iLocationMasService.getLocationNameByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	}
        
    }

    private String getUploadedMessgeString(final String message) {

        final Map<Long, String> fileNames = new LinkedHashMap<>();
        final List<String> existDocs = new ArrayList<>();
        if (getModel().getDocumentList() != null) {
            for (final AttachDocs attachDocs : getModel().getDocumentList()) {
                fileNames.put(attachDocs.getAttId(), attachDocs.getAttFname());
                existDocs.add(FileUploadUtility.getCurrent().generateValidString(attachDocs.getAttFname()));
            }
        }

        File file = null;
        Iterator<File> setFilesItr = null;
        final List<String> allDocs = new ArrayList<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            new ArrayList<>(entry.getValue());
            setFilesItr = entry.getValue().iterator();
            while (setFilesItr.hasNext()) {
                file = setFilesItr.next();
                allDocs.add(FileUploadUtility.getCurrent().generateValidString(file.getName()));
            }
        }

        final StringBuilder b = new StringBuilder("<ul>");
        String text = message;
        text = text.replaceAll("</li>", "").replaceAll("</ul>", "");
        final String[] results = text.split("<li>");
        boolean flag = true;

        for (final String string : existDocs) {
            final Set<Entry<Long, String>> ent = fileNames.entrySet();
            for (final Entry<Long, String> entry : ent) {
                // The first one is empty, remove it
                for (int i = 1; i < results.length; i++) {
                    if (string.equals(FileUploadUtility.getCurrent().generateValidString(entry.getValue()))) {
                        if (flag) {
                            b.append("<li>" + results[i] + "<li>");
                            flag = false;
                        }
                        if (results[i].contains(string)) {
                            b.append("<li>" + results[i] + "<input type='hidden' value='" + entry.getKey() + "'></li>");
                            break;
                        }
                    }
                }
            }
        }
        allDocs.removeAll(existDocs);
        for (final String string : allDocs) {
            for (int i = 1; i < results.length; i++) {
                if (results[i].contains(string)) {
                    b.append("<li>" + results[i] + "<input type='hidden' value=''></li>");
                    break;
                }
            }
        }
        b.append("</ul>");
        return b.toString();
    }

    @ResponseBody
    @RequestMapping(params = "getAssetDetails", method = RequestMethod.POST)
    public EstateMaster getPropertyMasterDetails(@RequestParam("assetCode") String assetCode) {
        EstateMaster estateMaster = new EstateMaster();
        // asked to tester this validation put or not if asset module live than get details or not

        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.LandEstate.ASSET_CODE_VALUE,
                MainetConstants.LandEstate.MODULE_LIVE_INTEGRATION, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        if (lookUp != null && lookUp.getOtherField() != null &&
                lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
            estateMaster = iEstateService.getAssetDetailsByAssetCodeAndOrgId(assetCode,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        return estateMaster;

    }

}
