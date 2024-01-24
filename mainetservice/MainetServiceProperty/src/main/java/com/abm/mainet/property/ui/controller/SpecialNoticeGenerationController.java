package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;
import com.abm.mainet.property.service.PropertyNoticeService;
import com.abm.mainet.property.ui.model.SpecialNoticeGenerationModel;

@Controller
@RequestMapping("/SpecialNoticeGeneration.html")
public class SpecialNoticeGenerationController extends AbstractFormController<SpecialNoticeGenerationModel> {

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private IObjectionDetailsService objectionDetailsService;



    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        SpecialNoticeGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(request);
        model.setCommonHelpDocs("SpecialNoticeGeneration.html");
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        model.setDeptId(deptId);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        return defaultResult();
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView getPropetryForSpecNotGen(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SpecialNoticeGenerationModel model = this.getModel();
        if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("SM")) {
            List<NoticeGenSearchDto> notGenShowList = propertyNoticeService.getAllPropWithAuthChangeByPropNo(
                    model.getSpecialNotGenSearchDto(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            model.setNotGenSearchDtoList(notGenShowList);
        } else if (model.getSpecialNotGenSearchDto().getSpecNotSearchType().equals("AL"
                + "")) {
            List<NoticeGenSearchDto> notGenShowList = propertyNoticeService.fetchAssDetailBySearchCriteria(
                    model.getSpecialNotGenSearchDto(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            model.setNotGenSearchDtoList(notGenShowList);
        }
        if (model.getNotGenSearchDtoList().isEmpty()) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("property.NoDataFound"));
        }
        return defaultResult();
    }

    @RequestMapping(params = "generateSpecialNotice", method = RequestMethod.POST)
    public ModelAndView generateSpecialNotice(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SpecialNoticeGenerationModel model = this.getModel();
        propertyNoticeService.saveListOfNoticeApplicableForObjection(model.getNotGenSearchDtoList(),
                UserSession.getCurrent().getOrganisation().getOrgid(),
                UserSession.getCurrent().getEmployee(), "SP",
                MainetConstants.Property.propPref.SNC,
                model.getDeptId());
        
        //#97303 By Arun
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = Utility.getDefaultLanguageId(org);
		Calendar cal = Calendar.getInstance();
		LookUp lookUp = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.SNC, org);
		if (lookUp.getLookUpCode().equals(MainetConstants.Property.SPC_NOT_DUE_DATE.GENERATION_DATE)) {
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(lookUp.getOtherField()));
		}		
		Long notTypeId = CommonMasterUtility.getValueFromPrefixLookUp("SP", MainetConstants.Property.propPref.NTY, org)
				.getLookUpId();
		LookUp notice = CommonMasterUtility.getNonHierarchicalLookUpObject(notTypeId, org);
		if (model.getNotGenSearchDtoList() != null && !model.getNotGenSearchDtoList().isEmpty()) {
			for (NoticeGenSearchDto notDto : model.getNotGenSearchDtoList()) {
				List<NoticeGenSearchDto> dtoList = new ArrayList<>();
				dtoList.add(notDto);
				List<SpecialNoticeReportDto> specNotDtoList = propertyNoticeService.setDtoForSpecialNotPrinting(dtoList,
						UserSession.getCurrent().getOrganisation());

				Map<String, Object> map = new HashMap<>();
				String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
						+ MainetConstants.FILE_PATH_SEPARATOR;
				String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;
				map.put("SUBREPORT_DIR", subReportSource);
				map.put("imgPath", imgpath);
				String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
				String jrxmlFileLocation = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
						+ MainetConstants.FILE_PATH_SEPARATOR + jrxmlName;
				String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest,
						httpServletResponse, jrxmlFileLocation, map, specNotDtoList,
						UserSession.getCurrent().getEmployee().getEmpId());
				
				List<File> filesForAttach = new ArrayList<File>();
				if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
					if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
					} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE,
								MainetConstants.QUAD_FORWARD_SLACE);
					}				
					final File file = new File(Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR + fileName);	
					
					// T#89750 code for DMS push when DMS is Y
					if (MainetConstants.Common_Constant.YES
				                .equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {					
	                Set<File> fileDetails = new LinkedHashSet<>();	                
	                fileDetails.add(file);
	                Map<Long, Set<File>> fileMap = new HashMap<>();
	                fileMap.put(0L, fileDetails);
	                FileUploadUtility.getCurrent().setFileMap(fileMap);
	                FileUploadDTO uploadDTO = new FileUploadDTO();
	                uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	                uploadDTO.setStatus(MainetConstants.FlagA);
	                uploadDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
	                uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	                getModel()
	                        .setCommonFileAttachment(
	                                ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
	                                        .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
	                uploadDTO.setIdfId("SP" + MainetConstants.FILE_PATH_SEPARATOR + new Date());
	                fileUpload.doMasterFileUpload(getModel().getCommonFileAttachment(), uploadDTO);
					}
	
					// 97303 By Arun - To set document in mail-KDMC					
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.DWE)) {
						filesForAttach.add(file);
					}								        
				}				
				
				if (StringUtils.isNotEmpty(notDto.getEmailId()) && StringUtils.isNotEmpty(notDto.getMobileNo())
						&& StringUtils.isNotEmpty(notDto.getPropertyNo()) && notDto.getNoticeNo() != null) {
					propertyNoticeService.sendSmsAndMail(org, langId, notice, notDto, cal.getTime(),
							UserSession.getCurrent().getEmployee().getEmpId(),filesForAttach);
				}
			}
		}
		//end

        return jsonResult(JsonViewObject.successResult(
                ApplicationSession.getInstance().getMessage("prop.spec.not.save")));
    }

	@RequestMapping(params = "resetFormData", method = RequestMethod.POST)
    public ModelAndView resetFormDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        SpecialNoticeGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.bind(httpServletRequest);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        model.setDeptId(deptId);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        return defaultMyResult();
    }
	
	@RequestMapping(params = "get-applicant-details", method = RequestMethod.POST)
	@ResponseBody
	public ObjectionDetailsDto getApplicantDetails(
			@RequestParam(value = "objectionReferenceNumber", required = true) String objectionReferenceNumber
			, @RequestParam(value = "deptCode", required = true) String deptCode) throws Exception {
		ObjectionDetailsDto objection = null;
		objection = objectionDetailsService
                .getObjectionDetailByRefNo(UserSession.getCurrent().getOrganisation().getOrgid(), objectionReferenceNumber);
		
		if(objection == null) {
			objection = propertyNoticeService
					.getObjectionDetailByRefNo(UserSession.getCurrent().getOrganisation().getOrgid(), objectionReferenceNumber, deptCode);
		}
		
		
		return objection;
		
	}


}
