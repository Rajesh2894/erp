package com.abm.mainet.orgnization.chart.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.orgnization.chart.dto.OrgnizationChartDto;
import com.abm.mainet.orgnization.chart.service.OrgnizationChartService;
/**
 * 
 * @author vishwanath.s
 *
 */
@Controller
@RequestMapping(value = { "/AsclOrgChart.html"})
public class OrgnizationChartControllar extends AbstractController {

	public OrgnizationChartControllar(Class<? extends AbstractController> controllerClass, String entityName) {
		super(controllerClass, entityName);
	}
	
	public OrgnizationChartControllar() {
		super(OrgnizationChartControllar.class, "AsclOrgnizationChartDto");
		log("AccountBillEntryController created.");
	}

	@Autowired
	private OrgnizationChartService asclOrgnizationChartService;

	private static final Logger LOGGER = Logger.getLogger(OrgnizationChartControllar.class);
	
	@RequestMapping()
	public String  getASCLOrgChart(Model model) {
		String orgniationName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		model.addAttribute("OrgName", orgniationName);
		return "orgChart";
	}

	@RequestMapping(params = "getASCLOrgChartData")
	@ResponseBody
	public List<OrgnizationChartDto>  getASCLOrgCharts() {
		List<OrgnizationChartDto> asclOrgChart=new ArrayList<>();
		try {
	       asclOrgChart = asclOrgnizationChartService.getOrgCharDtata();
		}catch(Exception e) {
			LOGGER.error("Error while fetching data for Organization chart",e);
		}
	  return asclOrgChart;
	}

}
