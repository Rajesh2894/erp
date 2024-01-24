package com.abm.mainet.common.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.LogOutModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/LogOut.html")
public class LogOutController extends AbstractController<LogOutModel> {
	private static final Logger LOG = Logger.getLogger(LogOutController.class);
    @Autowired
    private IEmployeeService iEmployeeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request,
            final HttpServletResponse response) {
        Employee employee = UserSession.getCurrent().getEmployee();
        if ((employee != null)
                && (employee.getEmploginname() != null)
                && !employee.getEmploginname().equalsIgnoreCase(
                        ApplicationSession.getInstance().getMessage(
                                "citizen.noUser.loginName"))) {
            iEmployeeService.resetEmployeeLoggedInFlag(employee);
        }
    	//User Story #108649
		if (employee != null) {
			try {
				boolean flag = getModel().saveEmployeeSessionInLogOut(employee.getEmpId());
				if (flag)
					LOG.info("Employee Session svae succsess fully at the time of logout");
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving employee session data in logout");
			}
		}
        final Organisation org;
        
        final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.COI, MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation());
		if (lookup == null) 
        	org = UserSession.getCurrent().getOrganisation();
		else if (lookup != null && UserSession.getCurrent().getOrganisation().getOrgid()!=Long.parseLong(lookup.getOtherField())) 
        	org = UserSession.getCurrent().getOrganisation();
        else
        	org = ApplicationSession.getInstance().getSuperUserOrganization();
        
        // UserSession.getCurrent().setIsPIO(MainetConstants.IsDeleted.NOT_DELETE);
        final String loginName = ApplicationSession.getInstance().getMessage(
                "citizen.noUser.loginName");
        final Employee emp = getModel().getEmployeeByLoginName(loginName, org);
        final int languageId = UserSession.getCurrent().getLanguageId();
        final Map<Long, String> onlineBankList = UserSession.getCurrent()
                .getOnlineBankList();
        String requestLang = null;
        request.getSession(false).invalidate();

        if ((org != null) && (emp != null)) {
            UserSession.getCurrent().setOrganisation(org);
            UserSession.getCurrent().setEmployee(emp);
            UserSession.getCurrent().setLanguageId(languageId);
            // payment mode
            UserSession.getCurrent().setOnlineBankList(onlineBankList);// store
                                                                       // PayU
                                                                       // BANK
                                                                       // LIST
            if (languageId == 1) {
                requestLang = MainetConstants.DEFAULT_LOCALE_STRING;
            } else {
                requestLang = MainetConstants.DEFAULT_LOCAL_REG_STRING;
            }
            final LocaleResolver localeResolver = RequestContextUtils
                    .getLocaleResolver(request);
            localeResolver.setLocale(request, response,
                    StringUtils.parseLocaleString(requestLang));
            ApplicationSession.getInstance().getSuperUserOrganization()
                    .setLangId(Integer.valueOf(languageId).shortValue());
            return new ModelAndView("redirect:/CitizenHome.html");
        }
        return new ModelAndView("redirect:/CitizenHome.html");

    }
    

    @RequestMapping(params = "logout", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody String logout(final HttpServletRequest request, final HttpServletResponse response) {
        Employee employee = UserSession.getCurrent().getEmployee();
        if ((employee != null)
                && (employee.getEmploginname() != null)
                && !employee.getEmploginname().equalsIgnoreCase(
                        ApplicationSession.getInstance().getMessage(
                                "citizen.noUser.loginName"))) {
            iEmployeeService.resetEmployeeLoggedInFlag(employee);
        }
    	//User Story #108649
		if (employee != null) {
			try {
				boolean flag = getModel().saveEmployeeSessionInLogOut(employee.getEmpId());
				if (flag)
					LOG.info("Employee Session svae succsess fully at the time of logout");
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving employee session data in logout");
			}
		}
        final Organisation org;
        
        final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.COI, MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation());
		if (lookup == null) 
        	org = UserSession.getCurrent().getOrganisation();
		else if (lookup != null && UserSession.getCurrent().getOrganisation().getOrgid()!=Long.parseLong(lookup.getOtherField())) 
        	org = UserSession.getCurrent().getOrganisation();
        else
        	org = ApplicationSession.getInstance().getSuperUserOrganization();
        
        // UserSession.getCurrent().setIsPIO(MainetConstants.IsDeleted.NOT_DELETE);
        final String loginName = ApplicationSession.getInstance().getMessage(
                "citizen.noUser.loginName");
        final Employee emp = getModel().getEmployeeByLoginName(loginName, org);
        final int languageId = UserSession.getCurrent().getLanguageId();
        final Map<Long, String> onlineBankList = UserSession.getCurrent()
                .getOnlineBankList();
        String requestLang = null;
        request.getSession(false).invalidate();

        if ((org != null) && (emp != null)) {
            UserSession.getCurrent().setOrganisation(org);
            UserSession.getCurrent().setEmployee(emp);
            UserSession.getCurrent().setLanguageId(languageId);
            // payment mode
            UserSession.getCurrent().setOnlineBankList(onlineBankList);// store
                                                                       // PayU
                                                                       // BANK
                                                                       // LIST
            if (languageId == 1) {
                requestLang = MainetConstants.DEFAULT_LOCALE_STRING;
            } else {
                requestLang = MainetConstants.DEFAULT_LOCAL_REG_STRING;
            }
            final LocaleResolver localeResolver = RequestContextUtils
                    .getLocaleResolver(request);
            localeResolver.setLocale(request, response,
                    StringUtils.parseLocaleString(requestLang));
            ApplicationSession.getInstance().getSuperUserOrganization()
                    .setLangId(Integer.valueOf(languageId).shortValue());
            return "CitizenHome.html";
        }
        return "CitizenHome.html";

    }
    
    @RequestMapping(params = "resetFlag", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody void resetFlag(final HttpServletRequest request, final HttpServletResponse response) {
        final Employee employee = UserSession.getCurrent().getEmployee();
        if ((employee != null)
                && (employee.getEmploginname() != null)
                && !employee.getEmploginname().equalsIgnoreCase(
                        ApplicationSession.getInstance().getMessage(
                                "citizen.noUser.loginName"))) {
            iEmployeeService.resetEmployeeLoggedInFlag(employee);
        }
    	//User Story #108649
        if (employee != null) {
			try {
				boolean flag = getModel().saveEmployeeSessionInLogOut(employee.getEmpId());
				if (flag)
					LOG.info("Employee Session svae succsess fully at the time of logout");
			} catch (Exception e) {
				LOG.error("Exception occured at the time of saving employee session data in logout");
			}
		}
        request.getSession(false).invalidate();
    }
}
