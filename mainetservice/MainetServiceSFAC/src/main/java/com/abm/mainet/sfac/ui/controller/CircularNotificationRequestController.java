package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;
import com.abm.mainet.sfac.service.CircularNotificationService;
import com.abm.mainet.sfac.ui.model.CircularNotificationFormModel;

@Controller
@RequestMapping(MainetConstants.Sfac.CIRCULAR_NOTIFY_FORM_HTML)
public class CircularNotificationRequestController extends AbstractFormController<CircularNotificationFormModel>{
	
	
	
	@Autowired
	private IOrganisationService orgService;
	
	@Autowired
	private TbDepartmentService tbDepartmentService;
	
	@Autowired CircularNotificationService circularNotificationService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return new ModelAndView(MainetConstants.Sfac.CIRCULAR_NOTIFY_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}
	
	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		CircularNotificationFormModel circularNotificationFormModel = this.getModel();
		circularNotificationFormModel.setDeptList(tbDepartmentService.findAllDepartmentByOrganization(currentOrgId, MainetConstants.MENU.A));
		circularNotificationFormModel.setOrgId(currentOrgId);
		circularNotificationFormModel.getDto().setConvenerOfCircular(currentOrgId);
		circularNotificationFormModel.getDto().setConvenerOfCircularName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

		this.getModel().setViewMode(MainetConstants.FlagA);
		
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);

		return new ModelAndView(MainetConstants.Sfac.CIRCULAR_NOTIFY_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(String circularTitle, String circularNo ,final HttpServletRequest httpServletRequest) {

		
		List<CircularNotificationMasterDto> circularNotificationMasterDtos = circularNotificationService.getCircularNotification(circularTitle, circularNo);
		this.getModel().setCircularNotificationMasterDtos(circularNotificationMasterDtos);

		return new ModelAndView(MainetConstants.Sfac.CIRCULAR_NOTIFY_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long cnId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		
		CircularNotificationMasterDto dto = circularNotificationService.getDetailById(cnId);
		dto.setCnId(cnId);
		this.getModel().setDto(dto);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setDeptList(tbDepartmentService.findAllDepartmentByOrganization(currentOrgId, MainetConstants.MENU.A));
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		return new ModelAndView(MainetConstants.Sfac.CIRCULAR_NOTIFY_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("stateId") Long stateId, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == stateId)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			logger.error("SDB Prefix not found");
			return lookUpList1;
		}
	}


}
