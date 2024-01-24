package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.ui.model.MRFCModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/MRFMaster.html")
public class MRFMasterController extends AbstractFormController<MRFCModel> {

    /**
     * The IMRFMaster Service
     */
    @Autowired
    private IMRFMasterService mRFMasterService;

    /**
     * The IAttachDocs Service
     */
    @Autowired
    private IAttachDocsService attachDocsService;

    /**
     * The ILocationMas Service
     */
    @Autowired
    private ILocationMasService iLocationMasService;

    /**
     * The IFileUpload Service
     */
    @Autowired
    IFileUploadService fileUpload;

    /**
     * The Designation Service
     */
    @Autowired
    DesignationService designationService;

    /**
     * The Department Service
     */
    @Autowired
    DepartmentService departmentService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("MRFMaster.html");
        searchDefault(httpServletRequest);
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocAddress));
        this.getModel().getmRFMasterDtoList().forEach(disp -> {
            disp.setLocAddress(locationMap.get(disp.getLocId()));
        });
     
        return index();
    }

    /**
     * @param httpServletRequest
     */
    private void searchDefault(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setmRFMasterDtoList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    @ResponseBody
    @RequestMapping(params = "checkPropertyNo", method = RequestMethod.POST)
    public boolean checkProperyNo(final HttpServletRequest request, @RequestParam String propertyNo) {
        sessionCleanup(request);
        
        boolean propertyPresent = true;  
    	propertyPresent = mRFMasterService.getPropertyDetailsByPropertyNumber(propertyNo);
        return propertyPresent;
    }

    
    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "Add", method = RequestMethod.POST)
    public ModelAndView materialCenterAdd(final HttpServletRequest request, @RequestParam String propertyNo) {
        sessionCleanup(request);
        
        List<MRFMasterDto> mrfMasterList = null;
        List<AttachDocs> attachDocs = null;
        String formName = null;
        
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());

        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Organisation org = UserSession.getCurrent().getOrganisation();
        LookUp PropertyActiveStatus = null;
        try {
        	PropertyActiveStatus =CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.SolidWasteManagement.PAS,MainetConstants.SolidWasteManagement.SWA,
    				org);
            
            }catch(Exception e) {
            	
            }
        if(PropertyActiveStatus != null) {
        	getModel().setPropertyActiveStatus(PropertyActiveStatus.getOtherField());
        }else {
        	getModel().setPropertyActiveStatus(MainetConstants.Y_FLAG);
        }
        if (StringUtils.isNotEmpty(propertyNo.trim())) {
   		 mrfMasterList = mRFMasterService.serchMrfCenter(
                    UserSession.getCurrent().getOrganisation().getOrgid(),propertyNo);
        	this.getModel().setmRFMasterDtoList(mrfMasterList);
        }else {
       	 mrfMasterList = mRFMasterService.serchMrfCenter(null, null,
 	                UserSession.getCurrent().getOrganisation().getOrgid());
        	this.getModel().setmRFMasterDtoList(mrfMasterList);
        }
             
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);

        this.getModel().setDesignationList(
                designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        
        if(propertyNo.trim().isEmpty()) {
    		formName = "materialCenter/Form";          
    	} else {
    		formName = "editMaterialCenter/Form";
    		
    		if(!mrfMasterList.isEmpty()) {
    			this.getModel().setmRFMasterDto(mRFMasterService.getPlantNameByPlantId(mrfMasterList.get(0).getMrfId()));
    		
    			attachDocs = attachDocsService
                    .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SWM_MRF_" + mrfMasterList.get(0).getMrfId().toString());
    		
    			this.getModel().setAttachDocsList(attachDocs);
    		}
            this.getModel().setmRFMasterDtoList(mRFMasterService.serchMrfCenter(null, null,
                    UserSession.getCurrent().getOrganisation().getOrgid()));
    	}
        return new ModelAndView("materialCenter/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * Edit Mrf Center
     * @param request
     * @param MrfId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editMrfCenter", method = RequestMethod.POST)
    public ModelAndView editMrfCenter(final HttpServletRequest request, @RequestParam Long MrfId) {
        sessionCleanup(request);
        this.getModel().setmRFMasterDto(mRFMasterService.getPlantNameByPlantId(MrfId));
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Organisation org = UserSession.getCurrent().getOrganisation();
        LookUp PropertyActiveStatus = null;
        try {
        	PropertyActiveStatus =CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.SolidWasteManagement.PAS,MainetConstants.SolidWasteManagement.SWA,
    				org);
            
            }catch(Exception e) {
            	
            }
        if(PropertyActiveStatus != null) {
        	getModel().setPropertyActiveStatus(PropertyActiveStatus.getOtherField());
        }else {
        	getModel().setPropertyActiveStatus(MainetConstants.Y_FLAG);
        }
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(
                designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        final List<AttachDocs> attachDocs = attachDocsService
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SWM_MRF_" + MrfId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        this.getModel().setmRFMasterDtoList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("editMaterialCenter/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * view Mrf Center
     * @param request
     * @param MrfId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewMrfCenter", method = RequestMethod.POST)
    public ModelAndView viewMrfCenter(final HttpServletRequest request, @RequestParam Long MrfId) {
        sessionCleanup(request);
        this.getModel().setmRFMasterDto(mRFMasterService.getPlantNameByPlantId(MrfId));
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
        this.getModel().setDesignationList(
                designationService.getDesignByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
        final List<AttachDocs> attachDocs = attachDocsService
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SWM_MRF_" + MrfId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        this.getModel().setmRFMasterDtoList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("viewMaterialCenter/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * material Center Search
     * @param PlantId
     * @param PlantName
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView materialCenterSearch(@RequestParam(required = false) String PlantId,
            @RequestParam(required = false) String PlantName, final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().getmRFMasterDto().setMrfPlantName(PlantName);
        this.getModel().getmRFMasterDto().setMrfPlantId(PlantId);
        this.getModel().setmRFMasterDtoList(mRFMasterService.serchMrfCenter(PlantId, PlantName,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Long LocationCat = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GCC", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(LocationCat,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> locationMap = this.getModel().getLocList().stream()
                .collect(Collectors.toMap(TbLocationMas::getLocId, TbLocationMas::getLocAddress));
        this.getModel().getmRFMasterDtoList().forEach(disp -> {
            disp.setLocAddress(locationMap.get(disp.getLocId()));
        });
        return new ModelAndView("MRFMasterSummary", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * material Center Delete
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView materialCenterDelete(final HttpServletRequest request) {
        return new ModelAndView("MRFMasterSummary", MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * @return
     */
    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public Object[] getMapData() {
        final Object data[] = new Object[] { null, null, null, null };
        List<String[]> position = null;
        TbLocationMas locList;
        List<MRFMasterDto> mRFMstr = mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        mRFMasterService.downloadMrfSiteImages(mRFMstr);
        if (mRFMstr != null && !mRFMstr.isEmpty()) {
            position = new ArrayList<>();
            for (MRFMasterDto mstr : mRFMstr) {
                try {
                    if (mstr.getLocId() != null) {
                        locList = iLocationMasService.findById(mstr.getLocId());
                        if (locList.getLatitude() != null && locList.getLongitude() != null) {
                            final String[] mapData = new String[] {
                                    CommonMasterUtility.getCPDDescription(mstr.getMrfCategory(),
                                            PrefixConstants.D2KFUNCTION.ENGLISH_DESC),
                                    locList.getLatitude(), locList.getLongitude(),
                                    mstr.getMrfPlantName(), mstr.getSiteImage(), mstr.getMrfPlantId(),
                                    mstr.getMrfPlantCap().toPlainString() };
                            position.add(mapData);
                        }
                    }
                } catch (Exception e) {
                    logger.error("error while encoding", e);
                }
            }
        }
        data[0] = position;
        return data;
    }

}
