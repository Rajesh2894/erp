package com.abm.mainet.authentication.admin.ui.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.LandingPageModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewOrgDetails;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Serves requests for Home.html
 *
 */
@Controller
@RequestMapping(value = { "/", "/Home.html" })
@Scope(value="session")
public class LandingPageController extends AbstractFormController<LandingPageModel> {
    private static final Logger LOGGER = Logger.getLogger(LandingPageController.class);

    private IOrganisationService service;

    /**
     * Sets the default language if not already set in session and UserSession. Populates the LandingPageModel based on the
     * Organization.
     * @param request Servlet request
     * @param response Servlet response
     * @return ModelAndView of LandingPage tiles view with LandingPageModel as model
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {
        final int langId = HttpHelper.getSessionLanguageId(request);
        if (request.getSession(false) != null) {
            request.removeAttribute("_csrf");
            request.getSession(false).invalidate();
        }
        final UserSession userSession = UserSession.getCurrent();
        final Organisation superOrg = getApplicationSession().getSuperUserOrganization();
        if (null == userSession.getOrganisation()) {
            userSession.setOrganisation(superOrg);
        }
        if (langId == 0) {
            HttpHelper.setLanguage(request, response, superOrg, false);
        } else {
            HttpHelper.setLanguage(request, response, langId, false);
        }
        
		final LandingPageModel model = getModel();
		String key = UUID.randomUUID().toString();
		String substringKey = key.substring(key.length() - 17, key.length() - 1);
		UserSession.getCurrent().setUniqueKeyId(substringKey);
		return new ModelAndView("index", MainetConstants.FORM_NAME, model);
    }

    /**
     * Redirects the request to AdminHome page if request contains valid organization ID.
     * @param request request to be redirected to citizen home page
     * @param response response object
     * @return Redirects to Citizen home page if organization id is correct else redirects to ULB selection page.
     */
    @RequestMapping(method = RequestMethod.GET, params = "redirect")
    public String changeOrganization(final HttpServletRequest request, final HttpServletResponse response) {
        final int langId = HttpHelper.getSessionLanguageId(request);
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        long districtId = 0;
        String organisationId = "";
        bindModel(request);
        final LandingPageModel model = getModel();
        final UserSession userSession = UserSession.getCurrent();
        userSession.getMapList().clear();
        districtId = Long.valueOf(request.getParameter("distId"));
        organisationId = request.getParameter("orgId");

        LOGGER.debug("###### Came in redirect : Orgid " + organisationId + "....District Id : " + districtId);

        if ((districtId != 0) && (districtId != -1)) {
            if ((organisationId != null) && !organisationId.equalsIgnoreCase("") && !organisationId.equalsIgnoreCase("-1")) {
                try {
                    final long orgId = Long.valueOf(organisationId);
                    model.setSessionAttributeValue(orgId);
                    if (langId == 0) {
                        HttpHelper.setLanguage(request, response, userSession.getOrganisation(), false);
                    } else {
                        HttpHelper.setLanguage(request, response, langId, false);
                    }
                    userSession.setContextName(request.getContextPath());
                    LOGGER.debug("###### redirect to org home : Orgid " + organisationId + "....District Id : " + districtId);
                    return "redirect:/AdminHome.html";

                } catch (final Exception exception) {
                    LOGGER.error(exception);
                }
            } else {
                model.getBindingResult()
                        .addError(new ObjectError("", getApplicationSession().getMessage("eip.landingpage.urbanlocal.msg")));
            }
        } else {
            model.getBindingResult()
                    .addError(new ObjectError("", getApplicationSession().getMessage("eip.landingpage.selectdistrict.msg")));
        }

        return getViewName();
    }

    /**
     * Returns the JSON with list of municipal organizations based on the district Id passed in the request.
     * @param distId The district Id for which municipal organizations are to be fetched
     * @param httpServletRequest The request which need to be served
     * @return JSON
     */
    @RequestMapping(method = RequestMethod.POST, params = "GetULBs")
    public @ResponseBody List<ViewOrgDetails> getOrganisations(@RequestParam("distId") final Long distId,
            final HttpServletRequest httpServletRequest) {

        final long start = System.currentTimeMillis();

        final List<ViewOrgDetails> list = service.getAllMunicipalOrganisationNew(distId);
        final long end = System.currentTimeMillis();
        LOGGER.debug("Time taken to fetch Municipal Organization list----------" + (end - start));
        return list;

    }

    /**
     * Sets the locale as per the 'lang' parameter present in request and redirects to the URL present in 'url' parameter in
     * request
     * @param localeString language, should be 'reg' or 'en'
     * @param url URL to which request needs to be redirected
     * @param request request to be served
     * @param response response object
     * @return String to redirect to the 'url' parameter present in request
     */
    @Override
    @RequestMapping(params = "locale", method = RequestMethod.GET)
    public String welcome(@RequestParam("lang") final String localeString, @RequestParam("url") final String url,
            final HttpServletRequest request,
            final HttpServletResponse response) {
        HttpHelper.setLanguage(request, response, localeString, false);
        return "redirect:" + url;
    }

    @RequestMapping(params = "ulbDomain", method = RequestMethod.POST)
    public @ResponseBody String getOrgDomain(@RequestParam("orgId") final String orgId, final HttpServletRequest request) {
        String url = HttpHelper.getDomainURL(orgId);
        if (null == url) {
            url = "";
        }
        return url;
    }
    @RequestMapping(params = "portalDeptLogin", method = RequestMethod.GET)
    public @ResponseBody String welcome() {
    String portaldeptUrl =	ServiceEndpoints.PORTAL_DEPT_LOGIN_URL;
        return portaldeptUrl;
    }
}