package com.abm.mainet.property.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.IPropertyTaxSelfAssessmentService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.ui.model.UpdateDataEntrySuiteModel;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;

/**
 * 
 * @author anwarul.hassan
 *
 * @since 11-Sep-2020
 */
@Controller
@RequestMapping("/UpdateDataEntrySuite.html")
public class UpdateDataEntrySuiteController extends AbstractFormController<UpdateDataEntrySuiteModel> {

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private IFileUploadService fileUploadService;
    
    @Autowired
    private PropertyService propertyService;


    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        UpdateDataEntrySuiteModel model = this.getModel();
        return new ModelAndView("UpdateDataEntrySuite", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getAssessmentDetail", method = RequestMethod.POST)
    public ModelAndView getAssessmentDetail(HttpServletRequest request, HttpServletResponse httpServletResponse,
            @RequestParam(value = "propNo", required = false) String propNo,
            @RequestParam(value = "oldPropNo", required = false) String oldPropNo) {
        sessionCleanup(request);
        getModel().bind(request);
        fileUploadService.sessionCleanUpForFileUpload();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (StringUtils.isEmpty(propNo) && StringUtils.isEmpty(oldPropNo)) {
            getModel().addValidationError(getApplicationSession().getMessage("property.changeInAss"));
            ModelAndView mv = new ModelAndView("UpdateDataEntrySuiteValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }

        List<ProvisionalAssesmentMstDto> assesmentMstDtoList = assesmentMastService
                .getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, propNo, oldPropNo);

        if (CollectionUtils.isEmpty(assesmentMstDtoList)) {
            getModel().addValidationError(getApplicationSession().getMessage("property.changeInAss"));
            ModelAndView mv = new ModelAndView("UpdateDataEntrySuiteValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
                MainetConstants.STATUS.ACTIVE);
        List<LookUp> locList = getModel().getLocation();
        Organisation org = UserSession.getCurrent().getOrganisation();
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
        ProvisionalAssesmentMstDto assesmentMstDto = assesmentMstDtoList.get(assesmentMstDtoList.size() - 1);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "EWZ") && assesmentMstDto != null
				&& !(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeWardZoneMappingService.class)
						.checkWardZoneMappingFlag(UserSession.getCurrent().getEmployee().getEmpId(),
								UserSession.getCurrent().getOrganisation().getOrgid(), assesmentMstDto.getAssWard1(),
								assesmentMstDto.getAssWard2(), assesmentMstDto.getAssWard3(),
								assesmentMstDto.getAssWard4(), assesmentMstDto.getAssWard5()))) {

			 getModel().addValidationError(getApplicationSession().getMessage("Ward Zone not mapped"));
	            ModelAndView mv = new ModelAndView("UpdateDataEntrySuiteValidn", MainetConstants.FORM_NAME, this.getModel());
	            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	            return mv;
		}
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipTypeValue(lookup.getDescLangFirst());
        assesmentMstDto.setProAssOwnerTypeName(lookup.getDescLangFirst());
        getModel().setExistOldPropNo(assesmentMstDto.getAssOldpropno());
        this.getModel().setProvisionalAssesmentMstDto(assesmentMstDto);
        getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        getModel().setModeType(MainetConstants.FlagV);
        this.getModel().setSearchFlag(MainetConstants.SEARCH);
        return new ModelAndView("UpdateDataEntrySuiteForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "updateDataEntry", method = RequestMethod.POST)
    public ModelAndView updateDataEntry(HttpServletRequest request, HttpServletResponse response) {
        boolean updateFlag = false;
        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        UpdateDataEntrySuiteModel model = this.getModel();
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        
		if (StringUtils.isNotBlank(assMstDto.getAssOldpropno())
				&& (StringUtils.isBlank(model.getExistOldPropNo()) || (StringUtils.isNotBlank(model.getExistOldPropNo())
						&& !StringUtils.equals(model.getExistOldPropNo(), assMstDto.getAssOldpropno())))) {
			boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(assMstDto.getAssOldpropno(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if(checkOldPropNoExist) {
				model.addValidationError("Already PTIN exist with "+ assMstDto.getAssOldpropno());
			}
         }
         if (model.hasValidationErrors()) {
             return defaultMyResult();
         }
        //List<String> updateFlagList = assesmentMastService.getUpdateDataEntryFlag(assMstDto.getAssNo(), orgId);
        //String upFlag = updateFlagList.get(updateFlagList.size() - 1);
        if ((FileUploadUtility.getCurrent().getFileMap() == null)
                || FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
        	 getModel().addValidationError(getApplicationSession().getMessage("Please upload document"));
             ModelAndView mv = new ModelAndView("UpdateDataEntrySuiteForm", MainetConstants.FORM_NAME, this.getModel());
             mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
             return mv;
        }
        //D#136457
        /*if (StringUtils.equals(upFlag, MainetConstants.FlagY)) {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("update.not.allowed.more.than.once")));
        }*/
        updateFlag = assesmentMastService.updateDataEntry(this.getModel().getProvisionalAssesmentMstDto(), orgId, empId,
                this.getModel().getClientIpAddress());
        this.getModel().prepareContractDocumentsData(this.getModel().getProvisionalAssesmentMstDto());
        if (updateFlag) {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("update.success")));
        } else {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("update.fail")));
        }
    }

}
