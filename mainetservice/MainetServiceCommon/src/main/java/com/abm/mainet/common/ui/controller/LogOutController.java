package com.abm.mainet.common.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.controller.AdminLoginController;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.LogOutModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/LogOut.html")
public class LogOutController extends AbstractController<LogOutModel> {
	private static Logger LOG = Logger.getLogger(LogOutController.class);
	private static final String REDIRECT_HOME_HTML = "redirect:/Home.html";
	private static final String HOME_HTML = "Home.html";
	@Autowired
	private IEmployeeService iEmployeeService;

	@RequestMapping(method = RequestMethod.GET)
	public void index(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		String loggedFromOtherAppl = UserSession.getCurrent().getLoggedFromOtherAppl();
		if (request.getSession(false) != null) {
			iEmployeeService.resetEmployeeLoggedInFlag(UserSession.getCurrent().getEmployee()); 
			// User Story #108649

			if (UserSession.getCurrent().getEmployee() != null
					&& UserSession.getCurrent().getEmployee().getEmpId() != null) {
				try {
					boolean flag = getModel()
							.saveEmployeeSessionInLogOut(UserSession.getCurrent().getEmployee().getEmpId());
					if (flag)
						LOG.info("Employee Session svae succsess fully at the time of logout");
				} catch (Exception e) {
					LOG.error("Exception occured at the time of saving employee session data in logout");
				}
			}

			request.getSession(false).invalidate();
		}

		if(StringUtils.equals(MainetConstants.FlagC, loggedFromOtherAppl)) {
			String TCP_URL = getApplicationSession().getMessage("tcp.haryana.url");
			response.sendRedirect(TCP_URL);
		}else if(StringUtils.equals(MainetConstants.FlagD, loggedFromOtherAppl)) {
			String TCP_URL = getApplicationSession().getMessage("tcp.haryana.department.url");
			response.sendRedirect(TCP_URL);
		}else {
			response.sendRedirect(request.getContextPath() + "/Home.html");
		}
	}
	@RequestMapping(params = "logout", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String logout(final HttpServletRequest request, final HttpServletResponse response) {
		if (request.getSession(false) != null) {
			iEmployeeService.resetEmployeeLoggedInFlag(UserSession.getCurrent().getEmployee());
			// User Story #108649

			if (UserSession.getCurrent().getEmployee() != null
					&& UserSession.getCurrent().getEmployee().getEmpId() != null) {
				try {
					boolean flag = getModel()
							.saveEmployeeSessionInLogOut(UserSession.getCurrent().getEmployee().getEmpId());
					if (flag)
						LOG.info("Employee Session svae succsess fully at the time of logout");
				} catch (Exception e) {
					LOG.error("Exception occured at the time of saving employee session data in logout");
				}
			}

			request.getSession(false).invalidate();
		}

		return HOME_HTML;
	}
	
	@RequestMapping(params = "resetFlag", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody void resetFlag(final HttpServletRequest request, final HttpServletResponse response) {
		final Employee employee = UserSession.getCurrent().getEmployee();
		if ((employee != null) && (employee.getEmploginname() != null) && !employee.getEmploginname()
				.equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
			iEmployeeService.resetEmployeeLoggedInFlag(employee);
		}
		// User Story #108649

		if (employee != null) {
			try {
				boolean flag = getModel()
						.saveEmployeeSessionInLogOut(UserSession.getCurrent().getEmployee().getEmpId());
				if (flag)
					LOG.info("Employee Session svae succsess fully at the time of logout");
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving employee session data in logout");
			}
		}

		request.getSession(false).invalidate();
	}
}
