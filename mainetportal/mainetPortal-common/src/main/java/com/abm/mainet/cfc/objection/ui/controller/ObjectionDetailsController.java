package com.abm.mainet.cfc.objection.ui.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

@Controller
@RequestMapping("/ObjectionDetails.html")
public class ObjectionDetailsController extends AbstractFormController<ObjectionDetailsModel> {

	@Autowired
	private IObjectionDetailService iObjectionDetailsService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().bind(httpServletRequest);
        final ObjectionDetailsModel model = getModel();
        setCommonFields(model);
        ModelAndView mv = null;
        mv = new ModelAndView("ObjectionDetails", MainetConstants.FORM_NAME, getModel());
        return mv;
    }

    private void setCommonFields(ObjectionDetailsModel model) {
        final ObjectionDetailsDto dto = model.getObjectionDetailsDto();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        dto.setOrgId(orgId);
        model.setOrgId(orgId);
        Set<LookUp> depts = iObjectionDetailsService.getDepartmentList(dto);
        model.setDepartments(depts);
    }

    /**
     * used to get Objection service by Department 
     * @param request
     * @param objectionDeptId
     * @return Set<LookUp>  
     */
   
    @RequestMapping(params = "getObjectionServiceByDepartment", method = RequestMethod.POST)
    public @ResponseBody Set<LookUp> getObjectionServiceByDept(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("objectionDeptId") Long objectionDeptId) {
        final ObjectionDetailsDto dto = this.getModel().getObjectionDetailsDto();
        dto.setObjectionDeptId(objectionDeptId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DSCL")) {
        	orgId=getModel().getOrgId();
        }
        Set<LookUp> serviceList = iObjectionDetailsService.findALlActiveServiceByDeptId(dto, orgId);
        this.getModel().setServiceList(serviceList);
        return serviceList;

    }
    
    /**
     * used to get Location by Department Name
     * @param objectionDeptId
     * @return Set<LookUp>  
     */
    
    @RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Set<LookUp> getLocationByDepartment(
            @RequestParam(value = "objectionDeptId", required = true) Long deptId) {
        final ObjectionDetailsDto dto = this.getModel().getObjectionDetailsDto();
         Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DSCL")) {
        	orgId=getModel().getOrgId();
        }
        dto.setOrgId(orgId);
        dto.setObjectionDeptId(deptId);
        Set<LookUp> locList = iObjectionDetailsService.getLocationByDepartment(dto);
        this.getModel().setLocations(locList);
        return locList;

    }
    
    /**
     * used to Save Objection Filed
     * @param httpServletRequest
     * @return 
     */
    
    @RequestMapping(params = "saveObjectionDetails", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveObjectiondetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        getModel().bind(httpServletRequest);
        ModelAndView mv = null;
        ObjectionDetailsModel model = this.getModel();
        if (model.saveForm()) {
            return jsonResult(JsonViewObject
                    .successResult(model.getSuccessMessage()));
        } else {
            mv = new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME,
                    getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
    }
    
    /* Method to get Objection charges */
    @RequestMapping(params = "saveObjectionOrGetCharges", method = RequestMethod.POST)
    public ModelAndView saveObjectionOrGetCharges(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Organisation org) {
        this.getModel().bind(httpServletRequest);
        ObjectionDetailsModel model = this.getModel();
        setCommonFields(model);
        ObjectionDetailsDto objectionDetailsDto = model.getObjectionDetailsDto();
        objectionDetailsDto.setOrgId(model.getOrgId());
        CommonChallanDTO commonChallanDTO = iObjectionDetailsService.getCharges(objectionDetailsDto);
        objectionDetailsDto.setCharges(commonChallanDTO.getAmountToShow());
        model.setOfflineDTO(commonChallanDTO);
        if (commonChallanDTO.getAmountToShow()!=null&&commonChallanDTO.getAmountToShow() != 0.0) {
            model.setPaymentCharge(MainetConstants.FLAGY);
        }
        Set<LookUp> serviceList = iObjectionDetailsService.findALlActiveServiceByDeptId(objectionDetailsDto, objectionDetailsDto.getOrgId());
        this.getModel().setServiceList(serviceList);
        Set<LookUp> locList = iObjectionDetailsService.getLocationByDepartment(objectionDetailsDto);
        this.getModel().setLocations(locList);
        
        return new ModelAndView(MainetConstants.OBJECTION_COMMON.OBJECTION_VALIDATION_URL, MainetConstants.FORM_NAME, getModel());

    }
    /* End */

	@RequestMapping(params = "getDispatchDetails", method = RequestMethod.POST)
	@ResponseBody
	public ObjectionDetailsDto getApplicationDetails(
			@RequestParam(value = "objectionReferenceNumber", required = true) String objectionReferenceNumber,
			@RequestParam(value = "deptCode") String deptCode) throws Exception {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ObjectionDetailsDto dto = new ObjectionDetailsDto();
		dto.setObjectionReferenceNumber(objectionReferenceNumber);
		dto.setOrgId(orgId);
		
		/*
		 * Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(deptCode);
		 * dto.setObjectionDeptId(deptId);
		 */
		ObjectionDetailsDto dtos=iObjectionDetailsService.fetchRtiAppDetailByRefNo(dto);
		getModel().getObjectionDetailsDto().setErrorList(dtos.getErrorList());
		getModel().setOrgId(dtos.getOrgId());
		getModel().getObjectionDetailsDto().setOrgId(dtos.getOrgId());
		return dtos;

	}
}
