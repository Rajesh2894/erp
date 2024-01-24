package com.abm.mainet.disastermanagement.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.dto.DailyWeatherDTO;
import com.abm.mainet.disastermanagement.ui.model.DailyWeatherModel;


@Controller
@RequestMapping(value="/DailyWeather.html")
public class DailyWeatherController extends AbstractFormController<DailyWeatherModel>{
	
	
    @Autowired
	private IEmployeeService iEmployeeService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model,final HttpServletRequest httpServletRequest) {

		this.sessionCleanup(httpServletRequest);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		Date date= new Date();
		DailyWeatherDTO dto=new DailyWeatherDTO();
		dto.setDate(date);
		this.getModel().setDailyWeatherDTO(dto);
		List<Employee> employeeList=iEmployeeService.findEmpList(orgid);
		model.addAttribute("employees", employeeList);
		
		return index();
		
	}
	

}
