package com.abm.mainet.rnl.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.dto.EstatePropMaster;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;
import com.abm.mainet.rnl.service.IEstateService;
import com.abm.mainet.rnl.ui.model.EstatePropMasModel;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping("/EstatePropMas.html")
public class EstatePropMasController extends AbstractFormController<EstatePropMasModel> {

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IEstateService iEstateService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private IEstateContractMappingService mappingService;

    private static final List EMPTY_LIST = new ArrayList<>();

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public String index(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        uiModel.addAttribute("locationList",
                iLocationMasService.getLocationNameByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        clearFileSession();
        return MainetConstants.EstateProMaster.ESTATE_PROP_MAS;
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbEstateMaster
     */
    private void populateModel(final Long propId, final EstatePropMasModel estatePropMasModel, final String modeType) {

        Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        estatePropMasModel.setUsageType(CommonMasterUtility.getLevelData("USA", 1, organisation));
        if (propId == null) {

            estatePropMasModel.setEstatePropMaster(new EstatePropMaster());
            estatePropMasModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);

        } else {
            final EstatePropMaster estatePropMaster = iEstatePropertyService.findEstatePropWithDetailsById(propId);
            estatePropMaster.setEstatecode(getModel().getCodeMap().get(estatePropMaster.getEstateId()));

            estatePropMasModel.setEstatePropMaster(estatePropMaster);
            final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    estatePropMasModel.getEstatePropMaster().getCode());
            estatePropMasModel.setDocumentList(attachDocs);
            if (MainetConstants.RnLCommon.MODE_VIEW.equals(modeType)) {
                estatePropMasModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
                estatePropMasModel.getEstatePropMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_VIEW);
            } else {
                estatePropMasModel.setModeType(MainetConstants.RnLCommon.MODE_EDIT);
                estatePropMasModel.getEstatePropMaster().setHiddeValue(MainetConstants.RnLCommon.MODE_EDIT);
                addInMap(attachDocs);
            }
        }
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

    /**
     * Shows a form page in order to create a new Property
     * @param model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public ModelAndView formForCreate(@RequestParam(value = "propId", required = false) final Long esId,
            @RequestParam(value = "type", required = false) final String modeType, HttpServletRequest request) {

        final EstatePropMasModel estatePropMasModel = getModel();
        final List<Object[]> list = iEstateService
                .findEstateRecordsForProperty(UserSession.getCurrent().getOrganisation().getOrgid());
        estatePropMasModel.setEstateMasters(list);
        

        if ((list != null) && !list.isEmpty()) {
            for (final Object[] obj : list) {
                estatePropMasModel.getCodeMap().put(Long.valueOf(String.valueOf(obj[0])), String.valueOf(obj[1]));
            }
        }

        populateModel(esId, estatePropMasModel, modeType);
        // D#74609 Production Issue
        String VP = (String) request.getSession().getAttribute("viewProperties");
		/*
		 * if (!StringUtils.isEmpty(VP)) { estatePropMasModel.setModeType("VP"); }
		 */
        return new ModelAndView(MainetConstants.EstateProMaster.ESTATE_PROP_MAS_FORM, MainetConstants.FORM_NAME,
                estatePropMasModel);
    }

    @RequestMapping(params = "code", method = RequestMethod.POST)
    public @ResponseBody String getCode(final String id) {
        final String code = getModel().getCodeMap().get(Long.valueOf(id));
        
        return code;
    }

    @ResponseBody
    @RequestMapping(params = "deleteEstate", method = RequestMethod.POST)
    public boolean deActiveTenantId(@RequestParam("propId") final Long propId) {
        iEstatePropertyService.deleteRecord(propId, UserSession.getCurrent().getEmployee().getEmpId());
        return true;
    }

    private List<EstatePropGrid> gridData(final HttpServletRequest request) {
        final String locationId = request.getParameter("locationId");
        final String estateId = request.getParameter("estateId");
        List<EstatePropGrid> list;
        if (locationId.isEmpty() || estateId.isEmpty()) {
            list = iEstatePropertyService.findAllRecords(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = iEstatePropertyService.findGridFilterRecords(UserSession.getCurrent().getOrganisation().getOrgid(),
                    Long.valueOf(estateId));
        }
        if (list != null) {
            for (final EstatePropGrid estatePropGrid : list) {
                estatePropGrid
                        .setUsageValue(CommonMasterUtility
                                .getHierarchicalLookUp(estatePropGrid.getUsage(), UserSession.getCurrent().getOrganisation())
                                .getLookUpDesc());
                if (estatePropGrid.getFloor() != null) {
                    estatePropGrid.setFloorValue(
                            CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(estatePropGrid.getFloor(),
                                            UserSession.getCurrent().getOrganisation())
                                    .getLookUpDesc());
                }
                estatePropGrid.setOccValue(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropGrid.getOccupancy(), UserSession.getCurrent().getOrganisation())
                        .getLookUpDesc());
            }

        } else {
            list = EMPTY_LIST;
        }

        return list;
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

    private void clearFileSession() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
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

    @RequestMapping(method = RequestMethod.POST, params = "estatePropGrid")
    public String indexProp(final Model uiModel, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        clearFileSession();
        final String estateId = httpServletRequest.getParameter("esId");
        getModel().setEstatePropGridId(Long.valueOf(estateId));
        // D#74609
        httpServletRequest.getSession().setAttribute("viewProperties", "VP");
        httpServletRequest.getSession().setAttribute("addProperties", "AP");
        return "showPropertyGrid";
    }

    @RequestMapping(params = "getGridPropData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridPropResults(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {

        List<EstatePropGrid> list = iEstatePropertyService.findGridFilterRecordWithActiveStatus(
                UserSession.getCurrent().getOrganisation().getOrgid(), getModel().getEstatePropGridId());
        if (list != null) {
            for (final EstatePropGrid estatePropGrid : list) {
                // D#77263
                estatePropGrid
                        .setUsageValue(CommonMasterUtility
                                .getHierarchicalLookUp(estatePropGrid.getUsage(), UserSession.getCurrent().getOrganisation())
                                .getLookUpDesc());
                if (estatePropGrid.getFloor() != null) {
                    estatePropGrid.setFloorValue(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(estatePropGrid.getFloor(),
                                    UserSession.getCurrent().getOrganisation()).getLookUpDesc());
                }
                estatePropGrid.setOccValue(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(estatePropGrid.getOccupancy(), UserSession.getCurrent().getOrganisation())
                        .getLookUpDesc());
            }

        } else {
            list = new ArrayList<>();
        }
        return getModel().paginate(httpServletRequest, page, rows, list);
    }

    @RequestMapping(method = RequestMethod.POST, params = "checkAddProperty")
    public @ResponseBody boolean hasPropertyForEstate(@RequestParam final Long esId) {
        final List<Character> list = iEstatePropertyService.checkProperty(esId);
        boolean flag = true;
        if ((list != null) && !list.isEmpty()) {
            if (list.size() == 1) {
                if (list.get(0).equals(MainetConstants.EstateMaster.Single)) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.POST, params = "checkPropertyAvailable")
    public @ResponseBody boolean hasPropertyExist(@RequestParam final Long esId) {
        final List<Character> list = iEstatePropertyService.checkProperty(esId);
        final boolean flag = true;
        if ((list != null) && !list.isEmpty()) {
            return false;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.POST, params = "isFloorMandatoty")
    public @ResponseBody boolean isFloorMandatotyForProperty(@RequestParam final Long id) {
        final EstateMaster estateMaster = iEstateService.findById(id);
        final List<LookUp> lookUps = CommonMasterUtility.getNextLevelData(PrefixConstants.CATEGORY_PREFIX_NAME, 1,
                UserSession.getCurrent().getOrganisation().getOrgid());
        boolean check = true;
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if (lookUp.getLookUpId() == estateMaster.getType1()) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.RnLCommon.L_FLAG)) {
                        check = false;
                        break;
                    }
                }
            }
        }
        getModel().setFloorCheck(check);
        return check;

    }

    @ResponseBody
    @RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
    public EstatePropMaster getPropertyMasterDetails(@RequestParam("propNo") String propNo) {
        EstatePropMaster estatePropMaster = new EstatePropMaster();
        // asked to tester this validation put or not if property tax live than get details or not
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue("AS", "MLI",
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
            estatePropMaster = iEstatePropertyService.getPropertyDetailsByPropNumber(propNo,
                    UserSession.getCurrent().getOrganisation());
        }
        return estatePropMaster;
    }

    // D#90757
    @RequestMapping(method = RequestMethod.POST, params = "checkPropertyExistInContract")
    public @ResponseBody boolean checkPropertyExistInContract(@RequestParam final Long propId) {
        return mappingService.propertyExistInContractPeriosd(UserSession.getCurrent().getOrganisation().getOrgid(), propId);
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "getPropertyAvailable")
    public @ResponseBody Long getPropertyAvailable(@RequestParam final Long esId) {
    	
    	 List<EstatePropGrid> list;
    	 Long id=null;
        
    	 list = iEstatePropertyService.findGridFilterRecords(UserSession.getCurrent().getOrganisation().getOrgid(), esId);
   
        if ((list != null) && !list.isEmpty()) {
        	id=list.get(0).getPropId();
            return id;
        }
        return id;
    }
    
    
    @RequestMapping(params = "newAddFrom", method = RequestMethod.POST)
    public ModelAndView newAddFrom(@RequestParam(value="esId", required = false) final Long esId,
    @RequestParam(value="escode", required = false) final String escode, final HttpServletRequest request) {

    final EstatePropMasModel estatePropMasModel = getModel();
    final List<Object[]> list = iEstateService
    .findEstateRecordsForProperty(UserSession.getCurrent().getOrganisation().getOrgid());
    estatePropMasModel.setEstateMasters(list);

    if ((list != null) && !list.isEmpty()) {
    for (final Object[] obj : list) {
    estatePropMasModel.getCodeMap().put(Long.valueOf(String.valueOf(obj[0])), String.valueOf(obj[1]));
    }
    }
    this.getModel().getEstatePropMaster().setEstatecode(escode);
    this.getModel().getEstatePropMaster().setEstateId(esId);
    estatePropMasModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
    Organisation organisation = UserSession.getCurrent().getOrganisation();
	estatePropMasModel.setUsageType(CommonMasterUtility.getLevelData("USA", 1, organisation));
    return new ModelAndView(MainetConstants.EstateProMaster.ESTATE_PROP_MAS_FORM, MainetConstants.FORM_NAME,
    this.getModel());
    }
    
    @RequestMapping(params = "propertyDetail", method=RequestMethod.POST) 
	  public @ResponseBody String propertyDetail(@RequestParam ("propertyNo")String propertyNo)
	  {
		if (StringUtils.isNotBlank(propertyNo)) {
			Long currentOrgId =UserSession.getCurrent().getOrganisation().getOrgid();
			
	        return ServiceEndpoints.BIRT_REPORT_URL +"=RentSummaryReport_New.rptdesign&propertyNo="+ propertyNo+"&Orgid="
					+ currentOrgId;
	    }
	  else 
	     { 
		  return "f"; 
		 }
   }

}
