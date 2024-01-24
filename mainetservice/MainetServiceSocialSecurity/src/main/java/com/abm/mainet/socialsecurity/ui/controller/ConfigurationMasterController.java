package com.abm.mainet.socialsecurity.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.socialsecurity.service.ConfigurationMasterService;
import com.abm.mainet.socialsecurity.ui.dto.ConfigurationMasterDto;
import com.abm.mainet.socialsecurity.ui.model.ConfigurationMasterModel;

/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */
@Controller
@RequestMapping(MainetConstants.SocialSecurity.CONFIGURATION_MASTER_URL)
public class ConfigurationMasterController extends AbstractFormController<ConfigurationMasterModel> {
	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	ConfigurationMasterService configurationMasterService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		loadScheme(httpServletRequest);
		return index();
	}

	private void loadScheme(HttpServletRequest httpServletRequest) {
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		this.getModel().setServiceList(serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId,
				activeStatusId, MainetConstants.FlagN));
		
		List<ConfigurationMasterDto> dtoList = configurationMasterService.getData(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		this.getModel().setConfigurationMasterList(dtoList);
	}


	@ResponseBody
	@RequestMapping(params = MainetConstants.MODE_CREATE, method = RequestMethod.POST)
	public ModelAndView configurationMasterAdd(final HttpServletRequest request) {
		sessionCleanup(request);

		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		  this.getModel().setServiceList(configurationMasterService.unconfiguredList(
		  orgId, depId, activeStatusId, MainetConstants.FlagN));
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView(MainetConstants.SocialSecurity.CONFIGURATION_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.SocialSecurity.SEARCH, method = RequestMethod.POST)
	public ModelAndView ConfigurationMasterSearch(final HttpServletRequest request, @RequestParam Long schemeMstId) {
		List<ConfigurationMasterDto> dtoList = configurationMasterService.getData(null, schemeMstId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setConfigurationMasterList(dtoList);
		this.getModel().getConfigurtionMasterDto().setSchemeMstId(schemeMstId);
		return new ModelAndView(MainetConstants.SocialSecurity.CONFIGURATION_SUMMARY, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.EDIT, method = RequestMethod.POST)
	public ModelAndView ConfigurationMasterView(final HttpServletRequest request, @RequestParam Long schemeMstId,
			@RequestParam String saveMode) {
		ConfigurationMasterDto configurationMasterDto = configurationMasterService.findSchemeById(schemeMstId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setConfigurtionMasterDto(configurationMasterDto);
		this.getModel().setSaveMode(saveMode);
		return new ModelAndView(MainetConstants.SocialSecurity.CONFIGURATION_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}


}
