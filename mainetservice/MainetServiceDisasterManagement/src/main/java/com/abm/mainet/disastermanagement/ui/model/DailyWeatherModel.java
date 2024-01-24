package com.abm.mainet.disastermanagement.ui.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.disastermanagement.dto.DailyWeatherDTO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DailyWeatherModel extends AbstractFormModel {

	private static final long serialVersionUID = 7122043210507575324L;

	private DailyWeatherDTO dailyWeatherDTO;

	private String department;

	@Autowired
	private IEmployeeService iEmployeeService;

	public DailyWeatherDTO getDailyWeatherDTO() {
		return dailyWeatherDTO;
	}

	public void setDailyWeatherDTO(DailyWeatherDTO dailyWeatherDTO) {
		this.dailyWeatherDTO = dailyWeatherDTO;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public boolean saveForm() {

		Organisation organisation = UserSession.getCurrent().getOrganisation();
		int langId = UserSession.getCurrent().getLanguageId();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

		SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setRegNo(getDailyWeatherDTO().getMinTemperature());
		dto.setContextPath(getDailyWeatherDTO().getMaxTemperature());
		dto.setConNo(getDailyWeatherDTO().getHumidity());
		dto.setContractNo(getDailyWeatherDTO().getRainSpeed());
		dto.setType(getDailyWeatherDTO().getRainFall());
		dto.setCaseID(getDailyWeatherDTO().getWindSpeed());
		dto.setAppDate(Utility.dateToString(getDailyWeatherDTO().getDate()));
		dto.setFrmDt(getDailyWeatherDTO().getFromTime());
		dto.setToDt(getDailyWeatherDTO().getToTime());
		dto.setUserId(getUserSession().getEmployee().getEmpId());

		List<Long> employeIds = Arrays.asList(getDailyWeatherDTO().getEmployee().split(","))
				.stream()
				.map(emp -> Long.valueOf(emp))
				.collect(Collectors.toList());

		String mobileList = iEmployeeService.getEmpDetailListByEmpIdList(employeIds, orgid)
				.stream()
				.map(Employee::getEmpmobno)
				.collect(Collectors.joining(", "));

		dto.setMobnumber(mobileList);

		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM",
				"DailyWeather.html", "GM", dto, organisation, langId);

		this.setSuccessMessage(getAppSession().getMessage("DailyWeatherDTO.save.msg"));

		return true;

	}

}
