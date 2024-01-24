package com.abm.mainet.common.ui.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbResourceDetDto;
import com.abm.mainet.common.dto.TbResourceMasDto;
import com.abm.mainet.common.service.IResourceService;
import com.abm.mainet.common.ui.model.FieldConfigurationModel;

/**
 * @author sadik.shaikh
 *
 */
@Controller
@RequestMapping("fieldConfiguration.html")
public class FieldConfigurationController extends AbstractFormController<FieldConfigurationModel> {

	@Autowired
	IResourceService resourceService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest); 
		List<TbResourceMasDto> dtos = new ArrayList<TbResourceMasDto>();

		dtos = resourceService.getAllTbResourceMas();

		this.getModel().setMasDtos(dtos);
		this.getModel().setFlag(MainetConstants.FlagV);

		return new ModelAndView("FieldConfiguration", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchResourceDetData", method = RequestMethod.POST)
	public List<TbResourceDetDto> getTbResDetById(@RequestParam("resId") final Long resId) {
		
		List<TbResourceDetDto> detDtos = resourceService.getAllTbResourceDetDto(resId);
		this.getModel().setSaveMode(MainetConstants.FlagE);
		return detDtos;
	}

	@ResponseBody
	@RequestMapping(params = "searchResourceMasData", method = RequestMethod.POST)
	public ModelAndView getTbResMasById(@RequestParam("resId") final Long resId) {

		TbResourceMasDto resourceMasDto = resourceService.getTbResourceMasDto(resId);
		this.getModel().setResourceMasDto(resourceMasDto);
		this.getModel().setFlag(MainetConstants.FlagE);
		return new ModelAndView("FieldConfiguration/form", MainetConstants.FORM_NAME, this.getModel());

	}

}
