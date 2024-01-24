package com.abm.mainet.common.master.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.InformationDeskDto;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.ui.model.InformationHelpDeskModel;
import com.abm.mainet.common.service.InformationHelpDeskService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/InformationHelpDesk.html")
public class InformationHelpDeskController extends AbstractFormController<InformationHelpDeskModel>{
	
	private static final Logger LOGGER = Logger.getLogger(InformationHelpDeskController.class);

	@Autowired
	InformationHelpDeskService infoHelpDeskService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ServiceMasterService serviceMaster;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setDepartmentList(infoHelpDeskService.getDeptarmetnList());
		this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
		return index();
	}
	
	@ResponseBody
	@RequestMapping(params = "getServiceList", method = RequestMethod.POST)
	public List<TbServicesMst> getServiceList(final HttpServletRequest httpServletRequest, @RequestParam Long deptId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final List<TbServicesMst> serviceMstList = infoHelpDeskService.findServiceListByDeptId(deptId, orgId);
		this.getModel().setTbServicesMsts(serviceMstList);
		this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
		return serviceMstList;
	}

	@ResponseBody
	@RequestMapping(params = "getServiceInfo", method = RequestMethod.POST)
	public ModelAndView getServiceInfo(final HttpServletRequest request, @RequestParam Long deptId,
			@RequestParam Long serviceId,@RequestParam Long categoryId) {
		this.getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		InformationHelpDeskModel model = this.getModel();
		model.getInformationDeskDto().setServiceId(serviceId);
		model.getInformationDeskDto().setDeptId(deptId);
		if (model.validateInputs()) {
		InformationDeskDto dto = infoHelpDeskService.getServiceInfo(deptId, serviceId, orgId,categoryId);
		if (dto != null && StringUtils.isNotEmpty(dto.getChargeApplicable())) {
		this.getModel().setMode(MainetConstants.Y_FLAG);
		dto.setDeptId(deptId);
		dto.setServiceId(serviceId);
		dto.setCategoryId(categoryId);
		this.getModel().setInformationDeskDto(dto);
		if (dto.getCheckList().isEmpty()) 
			this.getModel().setCheckListApplicable(MainetConstants.FlagN);
		else
			this.getModel().setCheckListApplicable(MainetConstants.FlagY);
		  } 
		else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("search.noRecords"));
		 }
		}
		ModelAndView mv = null;
		mv = new ModelAndView("InformationHelpDeskValidn", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(params = "getCateoryList", method = RequestMethod.POST)
	public List<LookUp> getCateoryList(final HttpServletRequest request, @RequestParam Long deptId,
			@RequestParam Long serviceId) {
		List<LookUp> categoryList = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String deptCode = departmentService.getDeptCode(deptId);
		String serviceShortCode = serviceMaster.fetchServiceShortCode(serviceId, orgId);
		this.getModel().setServiceCode(serviceShortCode);
		this.getModel().setDeptCode(deptCode);
		try {
		if (MainetConstants.TradeLicense.MARKET_LICENSE.equals(deptCode) && MainetConstants.TradeLicense.SERVICE_CODE.equals(serviceShortCode)) {	
			categoryList = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
					orgId);
	     }else if(MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(deptCode) && MainetConstants.Property.MUT.equals(serviceShortCode)) {
	    	 categoryList = CommonMasterUtility.lookUpListByPrefix("TFT", orgId);
	     }else if(MainetConstants.DEPT_SHORT_NAME.WATER.equals(deptCode) && MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(serviceShortCode)) {
	    		List<LookUp> categoryList1 = CommonMasterUtility.getNextLevelData(MainetConstants.NewWaterServiceConstants.CCG, 2,orgId);
	    		for (LookUp list : categoryList1) {
	    			if(!list.getLookUpCode().equals("NR")) {
	    				categoryList.add(list);
	    			}
				}
	     }
		this.getModel().setCategoryList(categoryList);
		return categoryList;
		}catch (Exception e) {
		     LOGGER.info("getCateoryList level 1 prefix list method");
				return categoryList;
		}
		
		
}

	
	@ResponseBody
	@RequestMapping(params = "getServiceDetails", method = {RequestMethod.POST })
	public List<Object> getServiceDetails(final HttpServletRequest httpServletRequest, @RequestParam Long deptId,@RequestParam Long serviceId){
		List<Object> objList = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		try {
		String deptCode = departmentService.getDeptCode(deptId);
		String serviceShortCode = serviceMaster.fetchServiceShortCode(serviceId, orgId);
		if(StringUtils.isNotEmpty(deptCode))
		objList.add(deptCode);
		if(StringUtils.isNotEmpty(serviceShortCode))
		objList.add(serviceShortCode);
		this.getModel().setDeptCode(deptCode);
		this.getModel().setServiceCode(serviceShortCode);
		return objList;
		} catch (Exception e) {
			  LOGGER.info("Service and dept code not found inside getServiceDetails method");
			return objList;
		}

	}

}
